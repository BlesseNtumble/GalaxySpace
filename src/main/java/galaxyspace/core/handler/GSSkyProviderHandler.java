package galaxyspace.core.handler;

import galaxyspace.systems.SolarSystem.planets.mars.dimension.sky.SkyProviderMars;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.planets.mars.dimension.WorldProviderMars;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GSSkyProviderHandler {


	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onSkyRendererTick(ClientTickEvent event) {
      Minecraft minecraft = FMLClientHandler.instance().getClient();
      WorldClient world = minecraft.world;
      EntityPlayerSP player = minecraft.player;
      
      if(world != null) {
          if(world.provider instanceof WorldProviderMars) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderMars());
              
              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
       	  }             
      }

   }

}
