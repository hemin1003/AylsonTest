package com.aylson.aylsonProtest.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.accloud.cloudservice.AC;
import com.accloud.cloudservice.PayloadCallback;
import com.accloud.service.ACDeviceMsg;
import com.accloud.service.ACDeviceSecurityMode;
import com.accloud.service.ACException;
import com.aylson.aylsonProtest.util.ConfigUtil;
import com.aylson.aylsonProtest.LoginActivity;
import com.aylson.aylsonProtest.R;
import com.aylson.aylsonProtest.util.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*   真实设备测试方法*/
public class MainTestActivity extends AppCompatActivity {
    private ScrollView mActivityMainTest;
    private EditText mEditText;
    private Button mBtSend;
    private EditText mEvResult;
    private ProgressBar mPb;
    private List<String> list = new ArrayList<>();
    private SpinnerPop spinnerPop;
    private Button mBt;
    private String[] command = new String[]{
            "{\"command\":[{\"cmd\":[{\"action\":\"getStatus\"}],\"cmdId\":\"0001011fa001\",\"destinationId\":\"011fa0\",\"sourceId\":\"00fefc\"}]}",
            "command=[{\"sourceId\":\"00fefc\",\"destinationId\":\"4f0139\",\"cmdId\":\"00014f013901\",\"cmd\":[{\"subType\":\"temp\",\"action\":\"getStatus\"},{\"subType\":\"humidity\",\"action\":\"getStatus\"}]}",
            "{\"command\":[{\"cmd\":[{\"action\":\"110\"}],\"cmdId\":\"00f9fef900\",\"destinationId\":\"f9fef9\",\"sourceId\":\"00fefc\"}]}"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        mEditText = (EditText) findViewById(R.id.editText);
        mBtSend = (Button) findViewById(R.id.btSend);
        mEvResult = (EditText) findViewById(R.id.etResult);
        mPb = (ProgressBar) findViewById(R.id.pb);
        mBt = (Button) findViewById(R.id.bt);
        list.add("门的设备状态指令");
        list.add("传感器");
        list.add("阳光房撤防发送指令");
        spinnerPop = new SpinnerPop(MainTestActivity.this);
        spinnerPop.refreshData(list, 0);


//        mEditText.setText("{\"command\":[{\"cmd\":[{\"action\":\"open\",\"password\":\"1234\"}],\"destinationId\":\"1f01a0\",\"cmdId\":\"00011f01a001\",\"sourceId\":\"00fefc\"}]}");

        mBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerPop.setWidth(mBt.getWidth());
                spinnerPop.showAsDropDown(mBt);
                spinnerPop.setItemListener(new SpinnerPop.OnItemSelectListener() {
                    @Override
                    public void onItemClick(int position) {
                        mEditText.setText("");
                        mEditText.setText(command[position]);
                        mBt.setText(list.get(position));
                    }

                    @Override
                    public void onBtSelectClick(String result) {
                    }
                });
            }
        });

        mBtSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiddenKeyboard(MainTestActivity.this, mEditText);
                mPb.setVisibility(View.VISIBLE);
                sendMsg2Device();
            }
        });
    }

    private void sendMsg2Device() {
        String msg = mEditText.getText().toString().trim();
        if (TextUtils.isEmpty(msg)) {
            showToast("请在输入框输入发送数据");
            return;
        }
        showToast("已发送指令");
        mEvResult.setText("");
        sendMsgToDevice(msg);
    }

    // 发送指令到设备
    private void sendMsgToDevice(String msg) {
        ACDeviceMsg deviceMsg = new ACDeviceMsg(65, msg.getBytes(), "发送指令到设备");
        deviceMsg.setSecurityMode(ACDeviceSecurityMode.STATIC_ENCRYPTED);
        AC.SEND_TO_LOCAL_DEVICE_DEFAULT_TIMEOUT = 3000; // 超时
        AC.bindMgr().sendToDeviceWithOption(
                Const.SUB_DOMAIN_NAME, ConfigUtil.getString(MainTestActivity.this, Const.PHYSICAL_DEVICE_ID), deviceMsg, AC.LOCAL_FIRST,
                new PayloadCallback<ACDeviceMsg>() {
                    @Override
                    public void success(ACDeviceMsg deviceMsg) {
                        showToast("发送指令到设备成功  ");
                        mPb.setVisibility(View.GONE);
                        try { // TODO 根据设备返回的数据更新开关状态
                            JSONObject resp = new JSONObject(new String(deviceMsg.getContent()));
                            mEvResult.setText(resp.toString());
                        } catch (JSONException e) {
                            showToast("请求超时");
                        }
                    }

                    @Override
                    public void error(ACException e) {
                        mPb.setVisibility(View.GONE);
                        if (e.getErrorCode() == 1998 || e.getErrorCode() == 1999 || e.getErrorCode() == 1986) {
                            mEvResult.setText("网络连接异常" + e.getDescription());
                        } else if (e.getErrorCode() == 3807) {
                            mEvResult.setText("设备不在线" + e.getDescription());
                        } else if (e.getErrorCode() == 1993) {
                            mEvResult.setText("请求超时" + e.getDescription());
                        } else {
                            mEvResult.setText(e.getDescription());
                        }
                    }
                });
    }


    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            if (AC.accountMgr().isLogin()) {
                AC.accountMgr().logout();
                Intent intent = new Intent(MainTestActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
            return true;
        } else if (id == R.id.action_newBt) {
            Intent it = new Intent(MainTestActivity.this, MainTest2Activity.class);
            startActivity(it);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 隐藏键盘
     */
    public void hiddenKeyboard(@NonNull Context context, @NonNull View view) {
        InputMethodManager imm =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
