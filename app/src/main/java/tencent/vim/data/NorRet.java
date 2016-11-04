package tencent.vim.data;

/**
 * Created by xkazerzhang on 2016/11/1.
 */
public class NorRet<T> {
    private int errCode;
    private String message;
    private T data;

    public NorRet(int code, T result){
        errCode = code;
        data = result;
    }

    public NorRet(int code, String msg){
        errCode = code;
        message = msg;
    }

    public boolean isSuccess(){
        return 0 == errCode;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "NorRet{" +
                "errCode=" + errCode +
                ", message='" + message + '\'' +
                '}';
    }
}
