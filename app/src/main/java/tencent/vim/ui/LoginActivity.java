package tencent.vim.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

import tencent.vim.R;
import tencent.vim.data.NorRet;
import tencent.vim.data.UserInfo;
import tencent.vim.present.AccountPresenter;
import tencent.vim.present.view.IAccountView;

/**
 * Created by xkazerzhang on 2016/11/3.
 */
public class LoginActivity extends FragmentActivity implements View.OnClickListener, IAccountView{
    private final static String TAG = "ViM-LoginActivity";

    private AccountPresenter accountPresenter;

    private EditText etUserName;
    private EditText etPassword;
    private boolean bLock = false;
    private String userBak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 加载数据
        UserInfo userInfo = UserInfo.loadData(getApplicationContext());
        if (!TextUtils.isEmpty(userInfo.getUsername()) && !TextUtils.isEmpty(userInfo.getUsersig())){
            loginSDK(userInfo.getUsername(), userInfo.getUsersig());
            bLock = true;
        }else{
            initView();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
        case R.id.btn_login:
            if (!bLock && checkInput()) {
                userBak = etUserName.getText().toString();
                accountPresenter.login(userBak, etPassword.getText().toString());
                bLock = true;
            }
            break;
        case R.id.btn_regist:
            if (!bLock && checkInput()){
                accountPresenter.regist(etUserName.getText().toString(), etPassword.getText().toString());
                bLock = true;
            }
            break;
        }
    }

    @Override
    public void onRegistResult(NorRet ret) {
        Toast.makeText(this, ret.getMessage(), Toast.LENGTH_SHORT).show();
        bLock = false;
    }

    @Override
    public void onLoginResult(NorRet ret) {
        if (ret.isSuccess()){
            Log.v(TAG, "onLoginResult->get sig:"+ret.getMessage());
            loginSDK(userBak, ret.getMessage());
        }else{
            Toast.makeText(this, ret.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initView(){
        if (null == accountPresenter) {
            setContentView(R.layout.activity_login);
            etUserName = (EditText) findViewById(R.id.et_user_name);
            etPassword = (EditText) findViewById(R.id.et_password);

            accountPresenter = new AccountPresenter(this);
        }
    }

    private void JumpHome(){
        startActivity(new Intent(this, HomeActivity.class));
    }


    private void loginSDK(final String user, final String sig){
        ILiveLoginManager.getInstance().iLiveLogin(user, sig, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                UserInfo.getUserInfo().setUsername(user);
                UserInfo.getUserInfo().setUsersig(sig);
                UserInfo.getUserInfo().commitData(LoginActivity.this);
                JumpHome();
                finish();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                bLock = false;
                initView();
            }
        });
    }

    private boolean checkInput(){
        boolean bInput = !TextUtils.isEmpty(etUserName.getText().toString()) && !TextUtils.isEmpty(etPassword.getText().toString());
        if (!bInput){
            Toast.makeText(this, R.string.username_or_password_empty, Toast.LENGTH_SHORT).show();
        }
        return bInput;
    }
}
