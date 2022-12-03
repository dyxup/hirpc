package cn.hi.awesome.hirpc.protocol.codec.serialize;

import cn.hi.awesome.hirpc.common.exception.RpcIOException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class JdkSerialization implements RpcSerialization {

    private JdkSerialization() {}

    public static JdkSerialization getInstance() {
        return Instance.INSTANCE;
    }

    private static class Instance {
        private static final JdkSerialization INSTANCE = new JdkSerialization();
    }

    @Override
    public <T> byte[] serialize(T o) {
        if(o == null) {
            throw new RpcIOException("JdkSerialization serialize, object is null");
        }
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(os);
            out.writeObject(o);
            return os.toByteArray();
        } catch (Exception e) {
            throw new RpcIOException("JdkSerialization serialize error", e);
        }
    }

    @Override
    public <T> T deserialize(byte[] data) {
        if(data == null) {
            throw new RpcIOException("JdkSerialization deserialize, data is null");
        }
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(data);
            ObjectInputStream in = new ObjectInputStream(is);
            return (T) in.readObject();
        } catch (Exception e) {
            throw new RpcIOException("JdkSerialization deserialize error", e);
        }
    }
}
