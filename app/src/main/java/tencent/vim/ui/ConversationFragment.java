package tencent.vim.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tencent.TIMConversation;

import java.util.List;

import tencent.vim.R;
import tencent.vim.present.ConversationPresenter;
import tencent.vim.present.view.IConversationView;
import tencent.vim.ui.adapter.ConversationAdapter;

/**
 * Created by xkazerzhang on 2016/11/3.
 */
public class ConversationFragment extends Fragment implements IConversationView{
    private final static String TAG = "ViM-ConversationFragment";

    private ConversationPresenter conversationPresenter;
    private ListView lvConversation;
    private ConversationAdapter conversationAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lvConversation = (ListView)getView().findViewById(R.id.lv_conversation);

        conversationPresenter = new ConversationPresenter(this);
        conversationAdapter = new ConversationAdapter(getContext(), conversationPresenter.pullConversations());
        lvConversation.setAdapter(conversationAdapter);
    }

    @Override
    public void onConversationUpdate(List<TIMConversation> conversationList) {
        conversationAdapter.resetDatas(conversationList);
    }
}
