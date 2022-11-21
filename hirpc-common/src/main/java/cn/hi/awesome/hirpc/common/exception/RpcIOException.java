package cn.hi.awesome.hirpc.common.exception;

public class RpcIOException extends RuntimeException {
    public RpcIOException(Throwable cause) {
        super(cause);
    }

    public RpcIOException(String message) {
        super(message);
    }

    public RpcIOException(String message, Throwable cause) {
        super(message, cause);
    }
}
