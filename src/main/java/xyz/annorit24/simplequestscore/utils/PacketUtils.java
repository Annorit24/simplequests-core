package xyz.annorit24.simplequestscore.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.annorit24.simplequestscore.SimpleQuestsCore;
import xyz.annorit24.simplequestscore.core.packet.AbstractNmsPacket;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Annorit24 on 29/10/17.
 */
public class PacketUtils {

    private AbstractNmsPacket packetUtils;

    public PacketUtils(AbstractNmsPacket packetUtils) {
        this.packetUtils = packetUtils;
    }

    public void sendPacketToAllPlayer(){
        Bukkit.getOnlinePlayers().forEach(o -> {
            sendPacketToPlayer(o);
        });
    }

    public void sendPacketToPlayer(Player player){
        Object packet = packetUtils.buildPacket();
        sendPacketToPlayer(packet,player);
    }

    public static void sendPacketToPlayer(Object packet, Player player){
        Object connectionHandle = getConnection(player);
        try {
            connectionHandle.getClass().getMethod("sendPacket",getNmsClass("Packet")).invoke(connectionHandle,packet);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void sendPacketToAllPlayer(Object packet){
        Bukkit.getOnlinePlayers().forEach(player -> sendPacketToPlayer(packet,player));
    }

    public static Object getChannel(Player player){
        try {
            Object connection = getConnection(player);
            if(connection == null)return null;
            Field f = connection.getClass().getDeclaredField("networkManager");
            f.setAccessible(true);
            Object networkManager = f.get(connection);
            Field f1 = networkManager.getClass().getDeclaredField("channel");
            f1.setAccessible(true);
            return f1.get(networkManager);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getConnection(Player player){
        try {
            Object playerHandle = getCraftObject(player);
            Field f = playerHandle.getClass().getDeclaredField("playerConnection");
            f.setAccessible(true);
            return f.get(playerHandle);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getCraftObject(Object object){
        try {
            Object craftObject = object.getClass().getMethod("getHandle").invoke(object);
            return craftObject;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getIChatBaseComponent(String str){

        try {
            Class<?> chatSerializer = getNmsClass("IChatBaseComponent$ChatSerializer");
            Object iChatBaseComponent =  chatSerializer.getMethod("a",String.class).invoke(chatSerializer,"{\"text\": \""+str+"\"}");
            return iChatBaseComponent;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


	public static Class getNmsClass(String name){
        String version = SimpleQuestsCore.getInstance().getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            Class clazz = Class.forName("net.minecraft.server." + version + "." + name);
            return clazz;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

	public static Class getObcClass(String name){
        String version = SimpleQuestsCore.getInstance().getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            Class clazz = Class.forName("org.bukkit.craftbukkit." + version + "." + name);
            return clazz;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void registerBukkitCommand(String name, Command command){
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(name, command);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

	public static Object convertItemStackToCraftItemStack(ItemStack item){
        Class clazz = getObcClass("inventory.CraftItemStack");
        try {
            Method m = clazz.getMethod("asNMSCopy", ItemStack.class);
            return m.invoke(null,item);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setField(Object edit, String fieldName, Object value)
    {
        try
        {
            Field field = edit.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(edit, value);
        }
        catch (NoSuchFieldException|IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    public static Object getField(Object obj,String fieldName){
        return getSuperClassField(obj,fieldName,0);
    }

    public static Object getSuperClassField(Object obj,String fieldName,int time){
        try{
            Class<?> clazz = obj.getClass();
            for (int i = 0 ;i < time ; i++) {
                clazz = clazz.getSuperclass();
            }
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Object invokeMethod(Object object,String name,Object... args){
        return invokeSuperClassMethod(object,name,0,args);
    }

	public static Object invokeSuperClassMethod(Object object, String name, int time,Object... args){
        List<Class> clazz = new ArrayList<>();
        Class[] c = {};
        for (Object arg : args) {
            clazz.add(arg.getClass());
        }

        Class<?> mainClazz = object.getClass();
        for(int i = 0; i<time; i++){
            mainClazz = mainClazz.getSuperclass();
        }

        try {
            Method method = mainClazz.getDeclaredMethod(name,clazz.toArray(c));
            method.invoke(object,args);
            return object;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object invokeGetMethod(Object object, String name,Object... args) {
        return invokeSuperClassGetMethod(object,name,0,args);
    }

	public static Object invokeSuperClassGetMethod(Object object, String name,int time,Object... args){
        List<Class> clazz = new ArrayList<>();
        Class[] c = {};
        for (Object arg : args) {
            clazz.add(arg.getClass());
        }

        Class<?> clazzMain = object.getClass();
        for(int i = 0; i<time; i++){
            clazzMain = clazzMain.getSuperclass();
        }

        try {
            Method method = clazzMain.getDeclaredMethod(name,clazz.toArray(c));
            return method.invoke(object,args);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println(e.getTargetException().toString());
            e.printStackTrace();
        }
        return null;
    }
}
