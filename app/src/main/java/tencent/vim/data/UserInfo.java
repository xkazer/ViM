package tencent.vim.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by xkazerzhang on 2016/11/1.
 */
public class UserInfo {
    private final static String CFG_USER_NAME = "user_name";
    private final static String CFG_USER_SIG = "git_server";

    private static UserInfo instance = null;

    private String username;
    private String usersig;

    private UserInfo(){};

    public static UserInfo loadData(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        if (null == instance){
            instance = new UserInfo();
        }

        instance.username = sharedPreferences.getString(CFG_USER_NAME, null);
        instance.usersig = sharedPreferences.getString(CFG_USER_SIG, null);

        return instance;
    }

    public static UserInfo getUserInfo(){
        return instance;
    }

    public void commitData(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CFG_USER_NAME, username);
        editor.putString(CFG_USER_SIG, usersig);
        editor.commit();
    }

    public String getUsername() {
        return username;
    }

    public String getUsersig() {
        return usersig;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUsersig(String usersig) {
        this.usersig = usersig;
    }
}
