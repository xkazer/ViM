package tencent.vim.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.TIMGroupCacheInfo;

import java.util.List;

import tencent.vim.R;

/**
 * Created by xkazerzhang on 2016/11/3.
 */
public class GroupAdapter extends BaseAdapter{
    private Context ctxParent;
    private List<TIMGroupCacheInfo> listGroups;

    private class ViewHolder{
        ImageView ivIcon;
        TextView tvName;
        TextView tvType;
        TextView tvDesc;
    }

    public GroupAdapter(Context context, List<TIMGroupCacheInfo> groups){
        ctxParent = context;
        listGroups = groups;
    }

    public void resetDatas(List<TIMGroupCacheInfo> groups){
        listGroups = groups;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listGroups.size();
    }

    @Override
    public Object getItem(int i) {
        return listGroups.get(i);
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
            holder.tvName = (TextView)convertView.findViewById(R.id.tv_name);
            holder.tvType = (TextView)convertView.findViewById(R.id.tv_type);
            holder.tvDesc = (TextView)convertView.findViewById(R.id.tv_desc);

            convertView.setTag(holder);
        }

        TIMGroupCacheInfo profile = listGroups.get(position);

        holder.tvName.setText(profile.getGroupInfo().getGroupName());
        holder.tvType.setText(profile.getGroupInfo().getGroupType());
        holder.tvDesc.setText(profile.getGroupInfo().getGroupIntroduction());

        return convertView;
    }
}
