package cn.hi.awesome.hirpc.common.protocol.common;

public class RpcResponseMsg extends RpcReqRespBase {
    private static final long serialVersionUID = 1L;

    private Object response;

    private boolean err;

    private int errCode;

    private String errMsg;

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public boolean isErr() {
        return err;
    }

    public void setErr(boolean err) {
        this.err = err;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
