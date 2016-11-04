package tencent.vim.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.TIMUserProfile;

import java.util.List;

import tencent.vim.R;

/**
 * Created by xkazerzhang on 2016/11/3.
 */
public class ContactAdapter extends BaseAdapter {
    private Context ctxParent;
    private List<TIMUserProfile> listContact;

    private class ViewHolder{
        ImageView ivIcon;
        TextView tvNick;
        TextView tvId;
        TextView tvSign;
    }

    public ContactAdapter(Context context, List<TIMUserProfile> contacts){
        ctxParent = context;
        listContact = contacts;
    }

    public void resetDatas(List<TIMUserProfile> contacts){
        listContact = contacts;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listContact.size();
    }

    @Override
    public Object getItem(int i) {
        return listContact.get(i);
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
            convertView = LayoutInflater.from(ctxParent).inflate(R.layout.item_contact, null);

            holder = new ViewHolder();
            holder.ivIcon = (ImageView)convertView.findViewById(R.id.iv_icon);
            holder.tvNick = (TextView)convertView.findViewById(R.id.tv_nick);
            holder.tvId = (TextView)convertView.findViewById(R.id.tv_id);
            holder.tvSign = (TextView)convertView.findViewById(R.id.tv_sign);

            convertView.setTag(holder);
        }

        TIMUserProfile profile = listContact.get(position);

        holder.tvNick.setText(profile.getNickName());
        holder.tvId.setText(profile.getIdentifier());
        holder.tvSign.setText(profile.getSelfSignature());

        return convertView;
    }
}
