package tencent.vim.present.view;

import tencent.vim.data.NorRet;

/**
 * Created by xkazerzhang on 2016/11/3.
 */
public interface IAccountView {
    void onRegistResult(NorRet ret);

    void onLoginResult(NorRet ret);
}
