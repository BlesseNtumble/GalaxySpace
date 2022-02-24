package galaxyspace.core.handler;

import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import asmodeuscore.core.astronomy.gui.screen.NewGuiCelestialSelection;
import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import micdoodle8.mods.galacticraft.api.event.client.CelestialBodyRenderEvent;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GSMapHandler
{
	
	//private static final ResourceLocation saturnRingTexture = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/celestialbodies/saturn_rings.png");    
    //private static final ResourceLocation uranusRingTexture = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/celestialbodies/uranus_rings.png");
    private static final ResourceLocation haumeaRingTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/haumea_rings.png");
	
    //private static final ResourceLocation acrdisk = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/acrdisk.png");
	
    final Minecraft minecraft = FMLClientHandler.instance().getClient();
    public Random rand;
      
    
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onBodyRender(CelestialBodyRenderEvent.Pre renderEvent)
	{
		if (renderEvent.celestialBody.equals(SolarSystemBodies.planetKuiperBelt)) 
		{
			GL11.glRotatef(Sys.getTime() / 10.0F % 360, 0, 0, 1);
		}
		
		if (renderEvent.celestialBody.equals(SolarSystemBodies.planetHaumea)) 
		{
			GL11.glRotatef(Sys.getTime() / 1000.0F % 360, 0, 0, 1);
		}
		
		if (renderEvent.celestialBody.getRingColorR() == 1.0F && renderEvent.celestialBody.getRingColorG() == 0.0F && renderEvent.celestialBody.getRingColorB() == 0.0F) 
		{
			GL11.glRotatef(Sys.getTime() / 10.0F % 360, 0, 0, 1);
		}
	}
	
	@SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderPlanetPost(CelestialBodyRenderEvent.Post event)
    {
    	
        //FIXME TEMP
        if (minecraft.currentScreen instanceof NewGuiCelestialSelection)
        {

        	if (event.celestialBody == SolarSystemBodies.planetSaturn)
        	{
                minecraft.renderEngine.bindTexture(ClientProxyCore.saturnRingTexture);
                float size = ((NewGuiCelestialSelection)minecraft.currentScreen).getWidthForCelestialBody(event.celestialBody) / 6.0F;
                ((NewGuiCelestialSelection)minecraft.currentScreen).drawTexturedModalRect(-7.5F * size, -1.75F * size, 16.0F * size, 3.5F * size, 0, 0, 30, 2, false, false, 30, 7);
        	}
        	else if (event.celestialBody == SolarSystemBodies.planetUranus)
        	{
                minecraft.renderEngine.bindTexture(ClientProxyCore.uranusRingTexture);
                float size = ((NewGuiCelestialSelection)minecraft.currentScreen).getWidthForCelestialBody(event.celestialBody) / 6.0F;
                ((NewGuiCelestialSelection)minecraft.currentScreen).drawTexturedModalRect(-1.75F * size, -7.0F * size, 8.5F * size, 16.0F * size, 0, 0, 28, 7, false, false, 28, 7);
        	}
        	else if (event.celestialBody == SolarSystemBodies.planetHaumea)
        	{
                minecraft.renderEngine.bindTexture(haumeaRingTexture);
                float size = ((NewGuiCelestialSelection)minecraft.currentScreen).getWidthForCelestialBody(event.celestialBody) / 6.0F;
                GL11.glRotatef(45F, 0, 0, 1);
                ((NewGuiCelestialSelection)minecraft.currentScreen).drawTexturedModalRect(-4.45F * size, -8.0F * size, 9.5F * size, 16.0F * size, 0, 0, 28, 7, false, false, 28, 7);
        	}
        }
    }
  }