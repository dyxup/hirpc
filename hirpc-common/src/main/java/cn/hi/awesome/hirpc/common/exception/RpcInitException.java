package cn.hi.awesome.hirpc.common.exception;

public class RpcInitException extends RuntimeException {
    public RpcInitException(Throwable cause) {
        super(cause);
    }

    public RpcInitException(String message) {
        super(message);
    }

    public RpcInitException(String message, Throwable cause) {
        super(message, cause);
    }
}
