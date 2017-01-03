package com.othershe.test;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    private ClearEditTextView mPhoneNum;
    private ClearEditTextView mPassword;
    private ClearEditTextView mMsgCode;

    private Button mSendMsgCode;
    private Button mLogin;

    private CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mPhoneNum = (ClearEditTextView) findViewById(R.id.phone_num_et);
        mPassword = (ClearEditTextView) findViewById(R.id.password_et);
        mMsgCode = (ClearEditTextView) findViewById(R.id.msg_code_et);
        mSendMsgCode = (Button) findViewById(R.id.msg_code_btn);
        mLogin = (Button) findViewById(R.id.login_btn);

        mSendMsgCode.setOnClickListener(this);
        mLogin.setOnClickListener(this);

        mPhoneNum.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.msg_code_btn:
                startCountDownTimer(60 * 1000);
                break;
            case R.id.login_btn:
                startLogin();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() == 11) {
            if (isPhoneNum(s.toString().trim())) {
                mSendMsgCode.setEnabled(true);
            } else {
                mSendMsgCode.setEnabled(false);
                showToast("手机号码格式不正确！");
            }
        } else {
            mSendMsgCode.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private boolean isPhoneNum(String phoneNumber) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(14[7])|(17[0|6|7|8]))\\d{8}$");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    /**
     * 开始倒计时
     *
     * @param time (毫秒)
     */
    private void startCountDownTimer(long time) {
        mCountDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished / 1000 == 55) {
                    mMsgCode.setText(String.valueOf(System.currentTimeMillis()).substring(9, 13));
                    mSendMsgCode.setText("发送验证码");
                    mCountDownTimer.cancel();
                } else {
                    mSendMsgCode.setText(millisUntilFinished / 1000 + " s");
                }
            }

            @Override
            public void onFinish() {
                mSendMsgCode.setText("发送验证码");
            }
        }.start();
    }

    private void startLogin() {
        String phoneNum = mPhoneNum.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String msgCode = mMsgCode.getText().toString().trim();

        if (TextUtils.isEmpty(phoneNum) || !isPhoneNum(phoneNum)) {
            showToast("手机号码格式不正确！");
            mPhoneNum.startShake(3);
            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            showToast("密码长度为6到20位字符！");
            mPassword.startShake(3);
            return;
        }

        if (TextUtils.isEmpty(msgCode) || msgCode.length() < 4) {
            showToast("请输入4位验证码！");
            mMsgCode.startShake(3);
            return;
        }

        showToast("登录成功！");

    }

    private void showToast(String content) {
        Toast.makeText(MainActivity.this, content, Toast.LENGTH_SHORT).show();
    }
}
