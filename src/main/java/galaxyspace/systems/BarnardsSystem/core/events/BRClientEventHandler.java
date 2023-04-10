package galaxyspace.systems.BarnardsSystem.core.events;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.systems.BarnardsSystem.BarnardsSystemBodies;
import galaxyspace.systems.BarnardsSystem.moons.barnarda_c1.dimension.sky.WeatherRendererBarnarda_C1;
import micdoodle8.mods.galacticraft.api.event.client.CelestialBodyRenderEvent;
import micdoodle8.mods.galacticraft.core.client.gui.screen.GuiCelestialSelection;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BRClientEventHandler {

	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		
		if (!mc.isGamePaused() && mc.world != null && mc.world.provider.getWeatherRenderer() instanceof WeatherRendererBarnarda_C1) {
			((WeatherRendererBarnarda_C1) mc.world.provider.getWeatherRenderer()).tick();
		}
	}
	
	@SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderPlanetPost(CelestialBodyRenderEvent.Post event)
    {
		final Minecraft minecraft = FMLClientHandler.instance().getClient();
		final ResourceLocation ringTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/rings_type_1.png");
	    
		if (minecraft.currentScreen instanceof GuiCelestialSelection)
        {
			if (event.celestialBody == BarnardsSystemBodies.Barnarda_B)
        	{
				GL11.glEnable(GL11.GL_BLEND);
                minecraft.renderEngine.bindTexture(ringTexture);
                float size = ((GuiCelestialSelection)minecraft.currentScreen).getWidthForCelestialBody(event.celestialBody) / 6.1F;
                ((GuiCelestialSelection)minecraft.currentScreen).drawTexturedModalRect(-8.6F * size, -.7F * size, 17.9F * size, 3.5F * size, 0, 0, 32, 7, false, false, 32, 7);
            }
        }
    }
}
