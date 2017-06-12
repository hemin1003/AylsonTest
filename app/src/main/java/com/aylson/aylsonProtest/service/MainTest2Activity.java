package com.aylson.aylsonProtest.service;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.accloud.cloudservice.AC;
import com.accloud.cloudservice.PayloadCallback;
import com.accloud.service.ACException;
import com.accloud.service.ACMsg;
import com.aylson.aylsonProtest.util.Const;
import com.aylson.aylsonProtest.R;

/*   真实设备测试方法*/
public class MainTest2Activity extends AppCompatActivity {
    private ScrollView mActivityMainTest;
    private EditText mEditText;
    private EditText mEditId;
    private Button mBtSend;
    private EditText mEvResult;
    private ProgressBar mPb;
    private CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test2);
        mEditText = (EditText) findViewById(R.id.editText);
        mEditId = (EditText) findViewById(R.id.editId);
        mBtSend = (Button) findViewById(R.id.btSend);
        mEvResult = (EditText) findViewById(R.id.etResult);
        mPb = (ProgressBar) findViewById(R.id.pb);

        mBtSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiddenKeyboard(MainTest2Activity.this,mEditText);
                mPb.setVisibility(View.VISIBLE);
                sendMsg2Uds();
            }
        });
    }

    private void sendMsg2Uds() {
        String msg = mEditText.getText().toString().trim();
        if (TextUtils.isEmpty(msg)){
            showToast("请在输入框输入手机号码");
            return;
        }
        String userId = mEditId.getText().toString().trim();
        if (TextUtils.isEmpty(userId)){
            showToast("请在输入框输入对应用户userId");
            return;
        }
        showToast( "已发送指令");
        mEvResult.setText("");
        testInitData(msg,userId);
    }


    private void testInitData(String phone,String userId){
        ACMsg req = new ACMsg();
        req.setName("testInitData");
        req.put("phoneNum",phone);
//        req.put("physicalDeviceId", ConfigUtil.getString(MainTest2Activity.this, Const.PHYSICAL_DEVICE_ID));
        req.put("userId",Long.valueOf(userId));
        AC.sendToService(Const.SUB_DOMAIN_NAME, Const.UDS_NAME, 1, req, new PayloadCallback<ACMsg>() {
            @Override
            public void success(ACMsg resp) {
                showToast("操作成功");
                mPb.setVisibility(View.GONE);
                mEvResult.setText(resp.toString());
            }

            @Override
            public void error(ACException e) {
                showToast("操作异常");
                mPb.setVisibility(View.GONE);
                mEvResult.setText(e.toString());
            }
        });
    }



    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * 隐藏键盘
     */
    public  void hiddenKeyboard(@NonNull Context context, @NonNull View view) {
        InputMethodManager imm =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private int count = 1;
    private int flag = 100;

    //一分钟倒计时
    private void countDownMin() {
        timer = new CountDownTimer(10 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                if (count <=flag) {
                    mPb.setVisibility(View.VISIBLE);
                    sendMsg2Uds();
                    count++;
                }
            }
        };
        timer.start();
    }

}
