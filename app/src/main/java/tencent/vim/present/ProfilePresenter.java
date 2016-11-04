package tencent.vim.present;

import android.util.Log;

import com.tencent.TIMCallBack;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;

import tencent.vim.present.view.IProfileView;

/**
 * Created by xkazerzhang on 2016/11/4.
 */
public class ProfilePresenter {
    private final static String TAG = "ViM-ProfilePresenter";

    private IProfileView profileView;

    public ProfilePresenter(IProfileView view){
        profileView = view;
    }


    public void pullMyProfile(){
        TIMFriendshipManager.getInstance().getSelfProfile(new TIMValueCallBack<TIMUserProfile>() {
            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "getSelfProfile->failed: "+i+"|"+s);
            }

            @Override
            public void onSuccess(TIMUserProfile profile) {
                profileView.onDataUpdate(profile);
            }
        });
    }

    public void setNickName(String nickName){
        TIMFriendshipManager.getInstance().setNickName(nickName, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "setNickName->failed:"+i+"|"+s);
                pullMyProfile();
            }

            @Override
            public void onSuccess() {
                profileView.onModifySuccess();
            }
        });
    }

    public void setSign(String sign){
        TIMFriendshipManager.getInstance().setSelfSignature(sign, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "setSelfSignature->failed:"+i+"|"+s);
                pullMyProfile();
            }

            @Override
            public void onSuccess() {
                profileView.onModifySuccess();
            }
        });
    }
}
