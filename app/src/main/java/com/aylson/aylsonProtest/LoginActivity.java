package com.aylson.aylsonProtest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.accloud.cloudservice.AC;
import com.accloud.cloudservice.PayloadCallback;
import com.accloud.service.ACException;
import com.accloud.service.ACUserDevice;
import com.accloud.service.ACUserInfo;
import com.aylson.aylsonProtest.service.MainTestActivity;
import com.aylson.aylsonProtest.util.ConfigUtil;
import com.aylson.aylsonProtest.util.Const;

import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    private LoginActivity activity = null;
    private ProgressBar mPb;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.aylson.aylsonProtest.R.layout.activity_login);
        activity = this;
        if (AC.accountMgr().isLogin()) {
            Intent intent = new Intent(LoginActivity.this, MainTestActivity.class);
            startActivity(intent);
            activity.finish();
        }
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(com.aylson.aylsonProtest.R.id.email);
        mPasswordView = (EditText) findViewById(com.aylson.aylsonProtest.R.id.password);
        mPb = (ProgressBar) findViewById(com.aylson.aylsonProtest.R.id.pb);
        findViewById(com.aylson.aylsonProtest.R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailView.getText().toString();
                String password = mPasswordView.getText().toString();
                hiddenKeyboard(activity,mEmailView);
                // Check for a valid email address.
                if (TextUtils.isEmpty(email)) {
                    showToast("请输入帐号");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    showToast("请输入密码");
                    return;
                }
                showToast("正在登录");
                mPb.setVisibility(View.VISIBLE);
                doLogin(email, password);
            }
        });

    }

    private void doLogin(String acc, String psw) {
        AC.accountMgr().login(acc, psw, new PayloadCallback<ACUserInfo>() {
            @Override
            public void success(ACUserInfo acUserInfo) {
                getDevList(acUserInfo.getUserId());
            }

            @Override
            public void error(ACException e) {
                showToast("登录失败");
            }
        });

    }

    private void getDevList(final Long uId) {

        AC.bindMgr().listDevices(new PayloadCallback<List<ACUserDevice>>() {
            @Override
            public void success(final List<ACUserDevice> acUserDevices) {
                if (acUserDevices.size() >= 0) {
                    String[] deviceNames = new String[acUserDevices.size()];
                    for (int i = 0; i < acUserDevices.size(); i++) {
                        ACUserDevice device = acUserDevices.get(i);
                        deviceNames[i] = device.getName() + device.deviceId;
                    }
                    mPb.setVisibility(View.GONE);
                    getListDialog(LoginActivity.this, deviceNames, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            saveConfig(acUserDevices.get(i));
                        }
                    }, true);
                //} else if (acUserDevices.size() > 0) {
                //    saveConfig(acUserDevices.get(0));
                } else {
                    saveConfig(null);
                }
            }

            @Override
            public void error(ACException e) {

            }
        });
    }

    private void saveConfig(ACUserDevice userDevice) {
        if (userDevice != null) {
            ConfigUtil.saveString(activity, Const.PHYSICAL_DEVICE_ID, userDevice.getPhysicalDeviceId());
            ConfigUtil.saveString(activity, Const.GATEWAY_NAME, userDevice.getName());
            ConfigUtil.saveLong(activity, Const.DEVICE_ID, userDevice.getDeviceId());
        } else {
            ConfigUtil.saveString(activity, Const.PHYSICAL_DEVICE_ID, "");
            ConfigUtil.saveString(activity, Const.GATEWAY_NAME, "");
            ConfigUtil.saveLong(activity, Const.DEVICE_ID, -1L);
        }
        Intent intent = new Intent(LoginActivity.this, MainTestActivity.class);
        startActivity(intent);
        activity.finish();
    }


    public static Dialog getListDialog(
            Activity activity, String[] items, final AdapterView.OnItemClickListener itemClickListener, boolean show) {
        View view = activity.getLayoutInflater().inflate(com.aylson.aylsonProtest.R.layout.view_list_dialog, null);
        ListView dialogListView = (ListView) view.findViewById(com.aylson.aylsonProtest.R.id.dialogListView);
        final Dialog dialog = new AlertDialog.Builder(
                activity).setCancelable(true).setView(view).create();

        if (items != null && items.length > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_expandable_list_item_1, items);
            dialogListView.setAdapter(adapter);
            dialogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(parent, view, position, id);
                    }
                    dialog.dismiss();
                }
            });
        } else {
            dialogListView.setVisibility(View.GONE);
        }
        if (show) dialog.show();
        return dialog;
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * 隐藏键盘
     */
    public  void hiddenKeyboard(@NonNull Context context, @NonNull View view) {
        InputMethodManager imm =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

