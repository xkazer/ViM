package tencent.vim.data;

import com.tencent.TIMFriendshipManager;
import com.tencent.TIMFriendshipProxy;
import com.tencent.TIMGroupAssistant;
import com.tencent.TIMGroupCacheInfo;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xkazerzhang on 2016/11/3.
 */
public class CacheData {
    private HashMap<String, TIMUserProfile> users;
    private HashMap<String, TIMGroupCacheInfo> groups;

    private static CacheData instance;

    private CacheData(){
        users = new HashMap<>();
        groups = new HashMap<>();
    }

    public static CacheData getInstance(){
        if (null == instance){
            instance = new CacheData();
        }
        return instance;
    }

    public void addUser(String id, TIMUserProfile user){
        users.put(id, user);
    }

    public void addGroup(String id, TIMGroupCacheInfo group){
        groups.put(id, group);
    }

    public TIMUserProfile getUserInfo(final String id){
        TIMUserProfile profile = null;
        if (users.containsKey(id)) {
            profile = users.get(id);
        }else{
            List<String> userInfo = new ArrayList<>();
            userInfo.add(id);
            // 拉取好友资料
            List<TIMUserProfile> listPro = TIMFriendshipProxy.getInstance().getFriendsById(userInfo);
            if (null != listPro && !listPro.isEmpty()) {
                profile = listPro.get(0);
                users.put(id, profile);
            }else{
                // 拉取用户资料
                TIMFriendshipManager.getInstance().getUsersProfile(userInfo, new TIMValueCallBack<List<TIMUserProfile>>() {
                    @Override
                    public void onError(int i, String s) {
                    }

                    @Override
                    public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                        if (timUserProfiles.isEmpty()) {
                            users.put(id, timUserProfiles.get(0));
                        }
                    }
                });
            }
        }
        return profile;
    }

    public TIMGroupCacheInfo getGroupInfo(String id){
        TIMGroupCacheInfo info = null;
        if (groups.containsKey(id)){
            info = groups.get(id);
        }else{
            List<String> ids = new ArrayList<>();
            List<TIMGroupCacheInfo> infos = TIMGroupAssistant.getInstance().getGroups(ids);
            if (!infos.isEmpty()){
                info = infos.get(0);
                groups.put(id, info);
            }
        }
        return info;
    }
}
