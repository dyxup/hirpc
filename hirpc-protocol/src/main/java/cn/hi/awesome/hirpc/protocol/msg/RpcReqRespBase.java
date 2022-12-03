package cn.hi.awesome.hirpc.protocol.msg;

import java.io.Serializable;

public class RpcReqRespBase implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean async;

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }
}
