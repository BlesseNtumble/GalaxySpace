package galaxyspace.core.handler;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.systems.SolarSystem.moons.moon.dimension.sky.SkyProviderMoon;
import galaxyspace.systems.SolarSystem.planets.asteroids.dimension.sky.SkyProviderAsteroids;
import galaxyspace.systems.SolarSystem.planets.mars.dimension.sky.SkyProviderMars;
import galaxyspace.systems.SolarSystem.planets.overworld.dimension.sky.SkyProviderOverworld;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.dimension.WorldProviderMoon;
import micdoodle8.mods.galacticraft.planets.asteroids.dimension.WorldProviderAsteroids;
import micdoodle8.mods.galacticraft.planets.mars.dimension.WorldProviderMars;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.WorldProviderSurface;

public class GSSkyProviderHandler {


	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onSkyRendererTick(ClientTickEvent event) {
      Minecraft minecraft = FMLClientHandler.instance().getClient();
      WorldClient world = minecraft.theWorld;
      EntityClientPlayerMP player = minecraft.thePlayer;
      
      if(world != null) {   
    	  /*
          if(world.provider instanceof WorldProviderMercury) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderMercury());

              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
          }
          if(world.provider instanceof WorldProviderVenus) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderVenus());

              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudProviderVenus());
              
              if(world.provider.getWeatherRenderer() == null)
	              world.provider.setWeatherRenderer(new WeatherProviderVenus());
          }
          if(world.provider instanceof WorldProviderVenusSS) 
          {
              if(world.provider.getSkyRenderer() == null)
                 world.provider.setSkyRenderer(new SkyProviderVenusSS());
              
              if(world.provider.getCloudRenderer() == null) 
                 world.provider.setCloudRenderer(new CloudRenderer());
          }*/
          if(world.provider instanceof WorldProviderSurface && GSConfigCore.enableSkyOverworld) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderOverworld());             
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
                 world.provider.setSkyRenderer(new SkyProviderMars((IGalacticraftWorldProvider) world.provider));
              
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
          /*
          if(world.provider instanceof WorldProviderMarsSS) 
          {
              if(world.provider.getSkyRenderer() == null)
                 world.provider.setSkyRenderer(new SkyProviderMarsSS());
              
              if(world.provider.getCloudRenderer() == null) 
                 world.provider.setCloudRenderer(new CloudRenderer());
          }
          if(world.provider instanceof WorldProviderCeres) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderCeres());

              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
          }
          if(world.provider instanceof WorldProviderPluto) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderPluto());

              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
          }
          if(world.provider instanceof WorldProviderKuiper) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderKuiper((IGalacticraftWorldProvider) world.provider));

              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
          }
          if(world.provider instanceof WorldProviderHaumea) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderHaumea());

              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
          }
          if(world.provider instanceof WorldProviderMakemake) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderMakemake());

              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
          }
          if(world.provider instanceof WorldProviderEris) 
          {
        	  if(world.provider.getSkyRenderer() == null) 
        		  world.provider.setSkyRenderer(new SkyProviderEris());
        	  
        	  if(world.provider.getCloudRenderer() == null)
        		  world.provider.setCloudRenderer(new CloudRenderer());
          }
          if(world.provider instanceof WorldProviderPhobos) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderPhobos());

              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
          }
          if(world.provider instanceof WorldProviderDeimos) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderDeimos());

              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
          }
          if(world.provider instanceof WorldProviderIo) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderIo());

              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
          }
          if(world.provider instanceof WorldProviderEuropa) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderEuropa());

              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
          }
          if(world.provider instanceof WorldProviderGanymede) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderGanymede());

              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
          }
          if(world.provider instanceof WorldProviderCallisto) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderCallisto());

              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
          }
          if(world.provider instanceof WorldProviderEnceladus) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderEnceladus());

              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
          }
          if(world.provider instanceof WorldProviderTitan) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderTitan());

              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
          }
          if(world.provider instanceof WorldProviderMiranda) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderMiranda());

              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
          }
          if(world.provider instanceof WorldProviderOberon) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderOberon());

              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
          }
          if(world.provider instanceof WorldProviderProteus) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderProteus());

              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
          }
          if(world.provider instanceof WorldProviderTriton) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderTriton());

              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
          }
         /* 
          if(world.provider instanceof WorldProviderACentauri) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderACentauriBb());

              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
          }
          
          if(world.provider instanceof WorldProviderBarnardaC) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderBarnardaC());

              if(world.provider.getCloudRenderer() == null)
              {
            	 FMLCommonHandler.instance().bus().register(new CloudProviderBarnardaC());
                 world.provider.setCloudRenderer(new CloudProviderBarnardaC());
              }

          }
          
          if(world.provider instanceof WorldProviderBarnardaE) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderBarnardaE());

              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
          }
          if(world.provider instanceof WorldProviderBarnardaF) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderBarnardaF());

              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
          }
          if(world.provider instanceof WorldProviderVegaB) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderVegaB());

              if(world.provider.getCloudRenderer() == null)
                 world.provider.setCloudRenderer(new CloudRenderer());
          }
          if(world.provider instanceof WorldProviderTCetiE) 
          {
              if(world.provider.getSkyRenderer() == null) 
                 world.provider.setSkyRenderer(new SkyProviderTCetiE());

              if(world.provider.getCloudRenderer() == null)
              {
            	 FMLCommonHandler.instance().bus().register(new CloudProviderTCetiE());
                 world.provider.setCloudRenderer(new CloudProviderTCetiE());
              }
          }*/
            
      }

   }


}
