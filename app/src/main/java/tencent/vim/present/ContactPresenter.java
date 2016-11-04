package tencent.vim.present;

import com.tencent.TIMFriendGroup;
import com.tencent.TIMFriendshipProxy;
import com.tencent.TIMFriendshipProxyListener;
import com.tencent.TIMFriendshipProxyStatus;
import com.tencent.TIMGroupAssistant;
import com.tencent.TIMGroupAssistantListener;

import com.tencent.TIMGroupCacheInfo;
import com.tencent.TIMGroupMemberInfo;
import com.tencent.TIMManager;
import com.tencent.TIMSNSChangeInfo;
import com.tencent.TIMUserProfile;

import java.util.List;

import tencent.vim.data.CacheData;
import tencent.vim.present.view.IContactView;

/**
 * Created by xkazerzhang on 2016/11/3.
 */
public class ContactPresenter implements TIMGroupAssistantListener, TIMFriendshipProxyListener {
    private final static String TAG = "ViM-ContactPresenter";

    private IContactView contactView;
    private boolean bLoadContact = false, bLoadGroup = false;

    public ContactPresenter(IContactView view){
        contactView = view;

        TIMManager.getInstance().setGroupAssistantListener(this);
        TIMManager.getInstance().setFriendshipProxyListener(this);
    }

    @Override
    public void OnProxyStatusChange(TIMFriendshipProxyStatus timFriendshipProxyStatus) {}

    @Override
    public void OnAddFriends(List<TIMUserProfile> list) {
        for (TIMUserProfile profile:list){
            CacheData.getInstance().addUser(profile.getIdentifier(), profile);
        }
        contactView.onContactUpdate(pullContacts());
    }

    @Override
    public void OnDelFriends(List<String> list) {
        contactView.onContactUpdate(pullContacts());
    }

    @Override
    public void OnFriendProfileUpdate(List<TIMUserProfile> list) {
        contactView.onContactUpdate(pullContacts());
    }

    @Override
    public void OnAddFriendReqs(List<TIMSNSChangeInfo> list) {

    }

    @Override
    public void OnAddFriendGroups(List<TIMFriendGroup> list) {}

    @Override
    public void OnDelFriendGroups(List<String> list) {}

    @Override
    public void OnFriendGroupUpdate(List<TIMFriendGroup> list) {}

    @Override
    public void onMemberJoin(String s, List<TIMGroupMemberInfo> list) {}

    @Override
    public void onMemberQuit(String s, List<String> list) {}

    @Override
    public void onMemberUpdate(String s, List<TIMGroupMemberInfo> list) {}

    @Override
    public void onGroupAdd(TIMGroupCacheInfo timGroupCacheInfo) {
        CacheData.getInstance().addGroup(timGroupCacheInfo.getGroupInfo().getGroupId(), timGroupCacheInfo);
        contactView.onGroupUpadate(pullGroups());
    }

    @Override
    public void onGroupDelete(String s) {
        contactView.onGroupUpadate(pullGroups());
    }

    @Override
    public void onGroupUpdate(TIMGroupCacheInfo timGroupCacheInfo) {

    }

    public List<TIMUserProfile> pullContacts(){
        List<TIMUserProfile> listProfiles = TIMFriendshipProxy.getInstance().getFriends();
        if (!bLoadContact && !listProfiles.isEmpty()){
            for (TIMUserProfile profile : listProfiles){
                CacheData.getInstance().addUser(profile.getIdentifier(), profile);
            }
            bLoadContact = true;
        }
        return listProfiles;
    }

    public List<TIMGroupCacheInfo> pullGroups(){
        List<TIMGroupCacheInfo> listInfos = TIMGroupAssistant.getInstance().getGroups(null);
        if (!bLoadGroup && !listInfos.isEmpty()){
            for (TIMGroupCacheInfo info : listInfos){
                CacheData.getInstance().addGroup(info.getGroupInfo().getGroupId(), info);
            }
            bLoadGroup = true;
        }
        return listInfos;
    }
}
