package cn.hi.awesome.hirpc.common.exception;

public class RpcInvokeException extends RuntimeException {
    public RpcInvokeException(Throwable cause) {
        super(cause);
    }

    public RpcInvokeException(String message) {
        super(message);
    }

    public RpcInvokeException(String message, Throwable cause) {
        super(message, cause);
    }
}
