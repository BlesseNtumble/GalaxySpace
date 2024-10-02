package galaxyspace.core.client.animations.MCACommonLibrary.animation;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;
import galaxyspace.core.client.animations.MCACommonLibrary.IMCAnimatedEntity;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;

public class AnimTickHandler {
   public static ArrayList<IMCAnimatedEntity> activeEntities = new ArrayList();
   public static ArrayList<IMCAnimatedEntity> removableEntities = new ArrayList();

   public static void init() {
      FMLCommonHandler.instance().bus().register(new AnimTickHandler());
   }

   public void addEntity(IMCAnimatedEntity entity) {
      activeEntities.add(entity);
   }

   public static void onClientTick(ClientTickEvent event) {
      EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
      Minecraft minecraft = FMLClientHandler.instance().getClient();
      WorldClient world = minecraft.theWorld;
      if (Side.CLIENT != null && world != null && event.phase == Phase.END && !activeEntities.isEmpty()) {
         try {
            Iterator var4 = activeEntities.iterator();

            IMCAnimatedEntity entity;
            while(var4.hasNext()) {
               entity = (IMCAnimatedEntity)var4.next();
               entity.getAnimationHandler().animationsUpdate();
               if (((Entity)entity).isDead) {
                  removableEntities.add(entity);
               }
            }

            var4 = removableEntities.iterator();

            while(var4.hasNext()) {
               entity = (IMCAnimatedEntity)var4.next();
               activeEntities.remove(entity);
            }
         } catch (Exception var6) {
            var6.printStackTrace();
         }

         removableEntities.clear();
      }

   }

   public void onServerTick(ServerTickEvent event) {
      if (Side.SERVER != null && event.phase == Phase.START && !activeEntities.isEmpty()) {
         Iterator var2 = activeEntities.iterator();

         IMCAnimatedEntity entity;
         while(var2.hasNext()) {
            entity = (IMCAnimatedEntity)var2.next();
            entity.getAnimationHandler().animationsUpdate();
            if (((Entity)entity).isDead) {
               removableEntities.add(entity);
            }
         }

         var2 = removableEntities.iterator();

         while(var2.hasNext()) {
            entity = (IMCAnimatedEntity)var2.next();
            activeEntities.remove(entity);
         }

         removableEntities.clear();
      }

   }

   @SubscribeEvent
   public void onRenderTick(RenderTickEvent event) {
   }

   @SubscribeEvent
   public void onWorldTick(WorldTickEvent event) {
   }
}
