package galaxyspace.systems.SolarSystem.moons.moon.dimension.sky;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import galaxyspace.GalaxySpace;
import galaxyspace.core.client.render.sky.SkyProviderBase;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class SkyProviderMoon extends SkyProviderBase{

	private static final ResourceLocation overworldTexture = new ResourceLocation(GalacticraftCore.ASSET_PREFIX, "textures/gui/celestialbodies/earth.png");
    
	@Override
	protected void rendererSky(Tessellator tessellator, float f10, float partialTicks) {
		
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		if (!ClientProxyCore.overworldTextureRequestSent)
		{
			GalacticraftCore.packetPipeline.sendToServer(new PacketSimple(PacketSimple.EnumSimplePacket.S_REQUEST_OVERWORLD_IMAGE, new Object[] {}));
			ClientProxyCore.overworldTextureRequestSent = true;
		}
		 // HOME:
        f10 = 10.0F;
        final float earthRotation = (float) (this.mc.theWorld.getSpawnPoint().posZ - mc.thePlayer.posZ - 20000) * 0.01F;
        GL11.glScalef(0.6F, 0.6F, 0.6F);
        GL11.glRotatef(earthRotation, 1.0F, 0.0F, 0.0F);
        //GL11.glRotatef(220F, 0.0F, 1.0F, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);

        if (ClientProxyCore.overworldTexturesValid)
        {
        	GL11.glBindTexture(GL11.GL_TEXTURE_2D, ClientProxyCore.overworldTextureClient.getGlTextureId());
        }
        else
        {
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(SkyProviderMoon.overworldTexture);
        }
        //this.mc.theWorld.getMoonPhase();
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 0.75);
        tessellator.addVertexWithUV(f10, -100.0D, f10, 0.75, 0.75);
        tessellator.addVertexWithUV(f10, -100.0D, -f10, 0.75, 0);
        tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
        tessellator.draw();
   
        float f = 0.59F;
        this.renderAtmo(tessellator, 0, 0F, f10 - 0.5F, new Vector3(88 / 255.0F * f, 122 / 255.0F * f, 180 / 255.0F * f));
        //GL11.glPushMatrix();
	}

	@Override
	protected int modeLight() {
		return 1;
	}

	@Override
	protected boolean enableBaseImages() {
		return true;
	}

	@Override
	protected float sunSize() {
		return 6.0F;
	}

	@Override
	protected ResourceLocation sunImage() {
		return new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sun_blank.png");
	}

	@Override
	protected boolean enableStar() {
		return true;
	}

	@Override
	protected Vector3 colorSunAura() {
		return new Vector3(150, 150, 150);
	}

	@Override
	protected Vector3 getAtmosphereColor() {
		return null;
	}
	
	@Override
	public boolean enableSmoothRender() {return true;}
}
