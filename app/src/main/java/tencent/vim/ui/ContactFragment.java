package tencent.vim.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.tencent.TIMFriendshipManager;
import com.tencent.TIMGroupCacheInfo;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;

import java.util.List;

import tencent.vim.R;
import tencent.vim.com.ViMConstants;
import tencent.vim.present.ContactPresenter;
import tencent.vim.present.view.IContactView;
import tencent.vim.ui.adapter.ContactAdapter;
import tencent.vim.ui.adapter.GroupAdapter;

/**
 * Created by xkazerzhang on 2016/11/3.
 */
public class ContactFragment extends Fragment implements IContactView, View.OnClickListener{
    private final static String TAG = "ViM-ContactFragment";

    private ContactPresenter contactPresenter;
    private ListView lvContact, lvGroup;
    private ImageView ivAddContact, ivAddGroup;
    private ContactAdapter adapterContact;
    private GroupAdapter adapterGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ivAddContact = (ImageView)getView().findViewById(R.id.iv_add_contact);
        ivAddGroup = (ImageView)getView().findViewById(R.id.iv_add_group);

        ivAddContact.setOnClickListener(this);
        ivAddGroup.setOnClickListener(this);

        lvContact = (ListView)getView().findViewById(R.id.lv_contact);
        lvGroup = (ListView)getView().findViewById(R.id.lv_group);
        contactPresenter = new ContactPresenter(this);

        List<TIMUserProfile> contacts = contactPresenter.pullContacts();
        adapterContact = new ContactAdapter(getContext(), contacts);

        List<TIMGroupCacheInfo> groups = contactPresenter.pullGroups();
        adapterGroup = new GroupAdapter(getContext(), groups);

        lvContact.setAdapter(adapterContact);
        lvGroup.setAdapter(adapterGroup);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
        case R.id.iv_add_contact:
            startSearch(ViMConstants.CONTACT_MODE);
            break;
        case R.id.iv_add_group:
            startSearch(ViMConstants.GROUP_MODE);
            break;
        }
    }

    @Override
    public void onContactUpdate(List<TIMUserProfile> contacts) {
        adapterContact.resetDatas(contacts);
    }

    @Override
    public void onGroupUpadate(List<TIMGroupCacheInfo> groups) {
        adapterGroup.resetDatas(groups);
    }

    private void startSearch(String mode){
        Intent intent = new Intent(getContext(), SearchActivity.class);
        intent.putExtra(ViMConstants.SEARCH_MODE, mode);

        startActivityForResult(intent, ViMConstants.ACTIVITY_SEARCH);
    }
}
