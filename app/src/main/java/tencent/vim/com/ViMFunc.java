package tencent.vim.com;

import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMMessage;
import com.tencent.TIMTextElem;

/**
 * Created by xkazerzhang on 2016/11/4.
 */
public class ViMFunc {
    static public String getShowMessage(TIMMessage message){
        String strMsg = "";
        for (int i=0; i<message.getElementCount(); i++){
            TIMElem ele = message.getElement(i);
            if (TIMElemType.Text == ele.getType()){
                TIMTextElem textElem = (TIMTextElem)ele;
                strMsg += textElem.getText();
            }else{
                strMsg += "["+ele.getType().toString()+"]";
            }
        }
        return strMsg;
    }
}
