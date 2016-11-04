package tencent.vim.present.view;

import com.tencent.TIMConversation;

import java.util.List;

/**
 * Created by xkazerzhang on 2016/11/4.
 */
public interface IConversationView {
    void onConversationUpdate(List<TIMConversation> conversationList);
}
