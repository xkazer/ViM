package tencent.vim.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMGroupCacheInfo;
import com.tencent.TIMMessage;
import com.tencent.TIMUserProfile;

import java.util.List;

import tencent.vim.R;
import tencent.vim.com.ViMFunc;
import tencent.vim.data.CacheData;

/**
 * Created by xkazerzhang on 2016/11/3.
 */
public class ConversationAdapter extends BaseAdapter{
    private Context ctxParent;
    private List<TIMConversation> listConversation;

    private class ViewHolder{
        ImageView ivIcon;
        TextView tvName;
        TextView tvType;
        TextView tvInfo;
    }

    public ConversationAdapter(Context context, List<TIMConversation> conversations){
        ctxParent = context;
        listConversation = conversations;
    }

    public void resetDatas(List<TIMConversation> groups){
        listConversation = groups;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listConversation.size();
    }

    @Override
    public Object getItem(int i) {
        return listConversation.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;

        if (null != convertView){
            holder = (ViewHolder)convertView.getTag();
        }else{
            convertView = LayoutInflater.from(ctxParent).inflate(R.layout.item_conversation, null);

            holder = new ViewHolder();
            holder.ivIcon = (ImageView)convertView.findViewById(R.id.iv_icon);
            holder.tvName = (TextView)convertView.findViewById(R.id.tv_name);
            holder.tvType = (TextView)convertView.findViewById(R.id.tv_type);
            holder.tvInfo = (TextView)convertView.findViewById(R.id.tv_info);

            convertView.setTag(holder);
        }

        TIMConversation conversation = listConversation.get(position);

        if (TIMConversationType.C2C == conversation.getType()){
            TIMUserProfile user = CacheData.getInstance().getUserInfo(conversation.getPeer());
            if (null != user) {
                holder.tvName.setText(user.getNickName());
            }else{
                holder.tvName.setText(conversation.getPeer());
            }
        }else if (TIMConversationType.Group == conversation.getType()){
            TIMGroupCacheInfo info = CacheData.getInstance().getGroupInfo(conversation.getPeer());
            if (null != info){
                holder.tvName.setText(info.getGroupInfo().getGroupName());
            }else{
                holder.tvName.setText(conversation.getPeer());
            }
        }
        List<TIMMessage> messageList = conversation.getLastMsgs(1);
        if (!messageList.isEmpty()){
            TIMMessage msg = messageList.get(0);
            TIMUserProfile profile = msg.getSenderProfile();
            if (null == profile){
                profile = CacheData.getInstance().getUserInfo(msg.getSender());
            }
            if (null == profile){
                holder.tvInfo.setText(msg.getSender()+":"+ ViMFunc.getShowMessage(msg));
            }else{
                holder.tvInfo.setText(profile.getNickName()+":"+ ViMFunc.getShowMessage(msg));
            }
        }

        return convertView;
    }
}
