package galaxyspace.core.handler;

import galaxyspace.systems.SolarSystem.moons.moon.dimension.sky.SkyProviderMoon;
import galaxyspace.systems.SolarSystem.planets.asteroids.dimension.sky.SkyProviderAsteroids;
import galaxyspace.systems.SolarSystem.planets.mars.dimension.sky.SkyProviderMars;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.dimension.WorldProviderMoon;
import micdoodle8.mods.galacticraft.planets.asteroids.dimension.WorldProviderAsteroids;
import micdoodle8.mods.galacticraft.planets.mars.dimension.WorldProviderMars;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.WorldProviderSurface;
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
    	  // custom clouds
    	  if(world.provider instanceof WorldProviderSurface)
    	  {
    		  if(world.provider.getCloudRenderer() == null) {
    			  CustomCloudRender clouds = new CustomCloudRender(new float[] { 40.0F, 100.0F });
    			  world.provider.setCloudRenderer(clouds);
    		  }
    	  }
    	  
          if(world.provider instanceof WorldProviderMoon) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderMoon());
              
              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
       	  } 
          
          if(world.provider instanceof WorldProviderMars) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderMars());
              
              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
       	  } 
          
          if(world.provider instanceof WorldProviderAsteroids) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderAsteroids());
              
              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
       	  }  
          
      }

   }

}
