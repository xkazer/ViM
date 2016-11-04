package tencent.vim.present;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import tencent.vim.data.NorRet;
import tencent.vim.present.view.IAccountView;

/**
 * Created by xkazerzhang on 2016/11/3.
 */
public class AccountPresenter {
    private final static String TAG = "ViM-AccountPresenter";

    private final static String REGIST_PATH = "http://182.254.234.225:8085/regist";
    private final static String LOGIN_PATH = "http://182.254.234.225:8085/login";

    private IAccountView accountView;


    public AccountPresenter(IAccountView view){
        accountView = view;
    }

    public void regist(String username, String password){
        new RegistTask().execute(username, password);
    }

    public void login(String username, String password){
        new LoginTask().execute(username, password);
    }


    /**
     * 获取HTTP的Get请求返回值(阻塞)
     * @param strAction
     * @return
     * @throws Exception
     */
    public static String getHttpGetRsp(String strAction) throws Exception{
        Log.v(TAG, "getHttpGetRsp->request: \n" + strAction);
        URL url = new URL(strAction.replace(" ", "%20"));
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setDoInput(true);
        conn.setConnectTimeout(1000 * 5);
        conn.setReadTimeout(1000 * 10);
        conn.setRequestMethod("GET");

        int rspCode = conn.getResponseCode();
        if (rspCode == 200){
            InputStreamReader isReader = new InputStreamReader(conn.getInputStream());
            BufferedReader inReader = new BufferedReader(isReader);
            StringBuffer strBuf = new StringBuffer();
            String line = null;
            while (null != (line = inReader.readLine())){
                strBuf.append(line+"\n");
            }

            inReader.close();
            isReader.close();
            conn.disconnect();
            Log.v(TAG, "getHttpGetRsp->response info: " + strBuf.toString());
            return strBuf.toString();
        }else{
            Log.v(TAG, "getHttpGetRsp->response code: " + rspCode);
        }

        return null;
    }

    public class RegistTask extends AsyncTask<String, Void, NorRet>{
        @Override
        protected NorRet doInBackground(String... params) {
            String rsp;
            try {
                rsp = getHttpGetRsp(REGIST_PATH + "?account=" + params[0] + "&password=" + params[1]);
            }catch (Exception e){
                return new NorRet(1, e.toString());
            }
            return new NorRet(0, rsp);
        }

        @Override
        protected void onPostExecute(NorRet norRet) {
            super.onPostExecute(norRet);
            accountView.onRegistResult(norRet);
        }
    }

    public class LoginTask extends AsyncTask<String, Void, NorRet>{
        @Override
        protected NorRet doInBackground(String... params) {
            String rsp;
            try {
                rsp = getHttpGetRsp(LOGIN_PATH + "?account=" + params[0] + "&password=" + params[1]);
            }catch (Exception e){
                return new NorRet(1, e.toString());
            }
            return new NorRet(0, rsp);
        }

        @Override
        protected void onPostExecute(NorRet norRet) {
            super.onPostExecute(norRet);
            accountView.onLoginResult(norRet);
        }
    }
}
