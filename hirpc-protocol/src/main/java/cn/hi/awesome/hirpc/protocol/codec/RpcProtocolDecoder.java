package cn.hi.awesome.hirpc.protocol.codec;

import cn.hi.awesome.hirpc.protocol.codec.serialize.JdkSerialization;
import cn.hi.awesome.hirpc.protocol.msg.*;
import cn.hi.awesome.hirpc.protocol.types.RpcMsgType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RpcProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes() < RpcMsgConstants.RPC_MSG_HEADER_LEN) {
            log.debug("RpcProtocolDecoder decode, discard message because of len < header");
            return;
        }

        byteBuf.markReaderIndex();

        short magic = byteBuf.readShort();
        if(magic != RpcMsgConstants.RPC_MSG_HEADER_MAGIC_NUM) {
            log.debug("RpcProtocolDecoder decode, discard message because of magic number");
            return;
        }

        short msgType = byteBuf.readShort();
        byte status = byteBuf.readByte();
        long reqId = byteBuf.readLong();
        byte serialType = byteBuf.readByte();

        int len = byteBuf.readInt();
        if(byteBuf.readableBytes() < len) {
            log.debug("RpcProtocolDecoder decode, discard message because of data length");
            byteBuf.resetReaderIndex();
            return;
        }

        byte[] data = new byte[len];
        byteBuf.readBytes(data);

        RpcMsgType msgTypeEnum = RpcMsgType.getByCode(msgType);
        if(msgTypeEnum == null) {
            log.debug("RpcProtocolDecoder decode, discard message because of msgTypeEnum");
            return;
        }

        RpcMsgHeader header = new RpcMsgHeader();
        header.setLen(len);
        header.setMsgType(msgType);
        header.setStatus(status);
        header.setSerialType(serialType);
        header.setMagicNumber(magic);
        header.setReqId(reqId);

        JdkSerialization serialization = JdkSerialization.getInstance();

        if(RpcMsgType.REQUEST.equals(msgTypeEnum)) {
            RpcRequestMsg requestMsg = serialization.deserialize(data);
            RpcProtocol<RpcRequestMsg> msg = new RpcProtocol<>();
            msg.setHeader(header);
            msg.setBody(requestMsg);
            list.add(msg);
        } else if(RpcMsgType.RESPONSE.equals(msgTypeEnum)) {
            RpcResponseMsg responseMsg = serialization.deserialize(data);
            RpcProtocol<RpcResponseMsg> msg = new RpcProtocol<>();
            msg.setHeader(header);
            msg.setBody(responseMsg);
            list.add(msg);
        } else if(RpcMsgType.HEART_BEAT.equals(msgTypeEnum)) {
            // TODO:
        } else {
            log.debug("RpcProtocolDecoder decode, discard message because of msgType not defined");
        }
    }
}
