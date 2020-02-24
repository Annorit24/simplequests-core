package xyz.annorit24.simplequestscore.core.packet;

import xyz.annorit24.simplequestscore.utils.PacketUtils;

import java.util.HashMap;

/**
 * Created by Annorit24 on 29/10/17.
 */
public abstract class AbstractNmsPacket {

    protected Object packet;
    @SuppressWarnings("rawtypes")
	protected Class packetClazz;
    protected HashMap<String,Object> fields;

    public AbstractNmsPacket(String packetClass){
        this.packetClazz = PacketUtils.getNmsClass(packetClass);
    }

    public abstract Object buildPacket();

    public void setField(String fieldName,Object value){
        fields.put(fieldName,value);
    }

}
