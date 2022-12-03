package cn.hi.awesome.hirpc.protocol.msg;

import java.io.Serializable;

public class RpcMsg<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private RpcMsgHeader header;
    private T body;

    public RpcMsgHeader getHeader() {
        return header;
    }

    public void setHeader(RpcMsgHeader header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
