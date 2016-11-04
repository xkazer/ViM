package tencent.vim.present;


import com.tencent.TIMConversation;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageListener;

import java.util.List;

import tencent.vim.present.view.IContactView;
import tencent.vim.present.view.IConversationView;

/**
 * Created by xkazerzhang on 2016/11/4.
 */
public class ConversationPresenter implements TIMMessageListener {
    private final static String TAG = "ViM-ContactPresenter";

    private IConversationView contactView;


    public ConversationPresenter(IConversationView view){
        contactView = view;

        TIMManager.getInstance().addMessageListener(this);
    }

    public List<TIMConversation> pullConversations(){
        return TIMManager.getInstance().getConversionList();
    }

    @Override
    public boolean onNewMessages(List<TIMMessage> list) {
        contactView.onConversationUpdate(pullConversations());
        return false;
    }
}
