package tencent.vim;

import android.app.Application;

import com.tencent.TIMManager;
import com.tencent.ilivesdk.ILiveSDK;

/**
 * Created by xkazerzhang on 2016/11/3.
 */
public class ViMApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ILiveSDK.getInstance().initSdk(this, 1400016949, 8002);
        TIMManager.getInstance().enableGroupInfoStorage(true);
        TIMManager.getInstance().enableFriendshipStorage(true);
    }
}
