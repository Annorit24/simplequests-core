package xyz.annorit24.simplequestscore.core.packet;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.bukkit.entity.Player;
import xyz.annorit24.simplequestsapi.packet.PacketReaderManager;
import xyz.annorit24.simplequestscore.utils.PacketUtils;

import java.util.List;

/**
 * @author Annorit24
 * Created on 24/01/2020
 */
public class PacketListener {

    private Player player;
    private Channel channel;
    private SimplePacketReaderManager packetReaderManager;

    PacketListener(Player player, SimplePacketReaderManager packetReaderManager) {
        this.player = player;
        this.packetReaderManager = packetReaderManager;
    }

    void inject() {
        channel = (Channel) PacketUtils.getChannel(player);
        if (channel != null) {
            channel.pipeline().addAfter("decoder", "PacketInjector",
                    new MessageToMessageDecoder<Object>() {
                        @Override
                        protected void decode(ChannelHandlerContext arg0, Object packet, List<Object> arg2) throws Exception {
                            arg2.add(packet);
                            packetReaderManager.readPackets(player,packet);
                        }
                    });
        }
    }

    void unInject() {
        if (channel.pipeline().get("PacketInjector") != null) {
            channel.pipeline().remove("PacketInjector");
        }
    }
}
