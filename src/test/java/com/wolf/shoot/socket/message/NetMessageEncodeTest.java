package com.wolf.shoot.socket.message;

import com.wolf.shoot.net.message.AbstractNetMessage;
import com.wolf.shoot.net.message.NetMessageBody;
import com.wolf.shoot.net.message.NetMessageHead;
import com.wolf.shoot.net.message.decoder.NetTcpMessageDecoderFactory;
import com.wolf.shoot.net.message.encoder.NetMessageEncoderFactory;
import com.wolf.shoot.net.message.logic.tcp.online.OnlineHeartMessage;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;

/**
 * Created by jwp on 2017/1/26.
 */
public class NetMessageEncodeTest {

    public static void main(String[] args) throws Exception{
        AbstractNetMessage abstractNetMessage = new OnlineHeartMessage();
        NetMessageHead netMessageHead = new NetMessageHead();
        netMessageHead.setSerial(5);
        netMessageHead.setCmd((short) 2);
        netMessageHead.setVersion((byte) 3);

        NetMessageBody netMessageBody = new NetMessageBody();
        byte[] bytes = "hello world".getBytes(CharsetUtil.UTF_8);
        netMessageBody.setBytes(bytes);

        netMessageHead.setLength(1+2+4+ bytes.length);
        abstractNetMessage.setNetMessageBody(netMessageBody);
        abstractNetMessage.setNetMessageHead(netMessageHead);

        ByteBuf byteBuf = new NetMessageEncoderFactory().createByteBuf(abstractNetMessage);
        System.out.println(byteBuf.array());

        //解析编码
        AbstractNetMessage decoderMesage = new NetTcpMessageDecoderFactory().praseMessage(byteBuf);
        netMessageHead = decoderMesage.getNetMessageHead();
        netMessageBody = decoderMesage.getNetMessageBody();
        System.out.println(netMessageHead.getSerial());
        System.out.println(netMessageHead.getCmd());
        System.out.println(netMessageHead.getVersion());
        String requestString = new String(netMessageBody.getBytes(), CharsetUtil.UTF_8);
        System.out.println(requestString);
        System.out.println(decoderMesage);
    }
}
