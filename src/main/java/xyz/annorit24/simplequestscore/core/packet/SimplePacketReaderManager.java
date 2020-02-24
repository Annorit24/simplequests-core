package xyz.annorit24.simplequestscore.core.packet;

import org.bukkit.entity.Player;
import xyz.annorit24.simplequestsapi.packet.IPacketReader;
import xyz.annorit24.simplequestsapi.packet.PacketReaderManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Annorit24
 * Created on 24/01/2020
 */
public class SimplePacketReaderManager extends PacketReaderManager {

    private Map<Player,PacketListener> listeners;
    private List<IPacketReader> readers;

    public SimplePacketReaderManager(){
        this.listeners = new HashMap<>();
        this.readers = new ArrayList<>();
    }

    public void inject(Player player){
        if(listeners.containsKey(player))return;
        PacketListener listener = new PacketListener(player,this);
        listener.inject();
        listeners.put(player,listener);
    }

    public void unInject(Player player){
        if(!listeners.containsKey(player))return;
        listeners.get(player).unInject();
        listeners.remove(player);
    }

    public void registerPacketReader(IPacketReader reader){
        if(readers.contains(reader))return;
        readers.add(reader);
    }

    public void unregisterPacketReader(IPacketReader reader){
        if(!readers.contains(reader))return;
        readers.remove(reader);
    }

    void readPackets(Player player, Object packet) {
        readers.forEach(reader -> reader.readPacket(player,packet));
    }

}
