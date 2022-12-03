package cn.hi.awesome.hirpc.protocol.codec;

import cn.hi.awesome.hirpc.protocol.codec.serialize.JdkSerialization;
import cn.hi.awesome.hirpc.protocol.msg.RpcMsg;
import cn.hi.awesome.hirpc.protocol.msg.RpcMsgHeader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RpcProtocolEncoder extends MessageToByteEncoder<RpcMsg<?>> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcMsg<?> rpcMsg, ByteBuf byteBuf) throws Exception {
        RpcMsgHeader header = rpcMsg.getHeader();
        Object body = rpcMsg.getBody();
        JdkSerialization serialization = JdkSerialization.getInstance();
        byte[] bodyData = serialization.serialize(body);

        header.setLen(bodyData.length);
        // TODO: 充血模型 放入header写入
        byteBuf.writeShort(header.getMagicNumber());
        byteBuf.writeShort(header.getMsgType());
        byteBuf.writeByte(header.getStatus());
        byteBuf.writeLong(header.getReqId());
        byteBuf.writeByte(header.getSerialType());
        byteBuf.writeInt(header.getLen());



        byteBuf.writeBytes(bodyData);
    }
}
