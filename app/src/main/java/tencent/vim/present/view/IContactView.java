package tencent.vim.present.view;

import com.tencent.TIMGroupCacheInfo;
import com.tencent.TIMUserProfile;

import java.util.List;


/**
 * Created by xkazerzhang on 2016/11/3.
 */
public interface IContactView {

    void onContactUpdate(List<TIMUserProfile> contacts);

    void onGroupUpadate(List<TIMGroupCacheInfo> groups);
}
