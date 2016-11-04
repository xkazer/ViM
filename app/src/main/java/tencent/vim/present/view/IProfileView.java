package tencent.vim.present.view;

import com.tencent.TIMUserProfile;

/**
 * Created by xkazerzhang on 2016/11/4.
 */
public interface IProfileView {
    void onDataUpdate(TIMUserProfile profile);

    void onModifySuccess();
}
