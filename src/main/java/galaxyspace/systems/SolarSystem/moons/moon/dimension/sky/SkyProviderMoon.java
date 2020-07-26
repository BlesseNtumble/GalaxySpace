package galaxyspace.systems.SolarSystem.moons.moon.dimension.sky;

import org.lwjgl.opengl.GL11;

import asmodeuscore.api.dimension.IAdvancedSpace.StarColor;
import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import asmodeuscore.core.astronomy.sky.SkyProviderBaseOld;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;

public class SkyProviderMoon extends SkyProviderBase{

	private static final ResourceLocation overworldTexture = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/celestialbodies/earth.png");
    
	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder worldRenderer, float f10, float partialTicks) {
		
		
		GL11.glPushMatrix();

		if (!ClientProxyCore.overworldTextureRequestSent)
        {
            GalacticraftCore.packetPipeline.sendToServer(new PacketSimple(PacketSimple.EnumSimplePacket.S_REQUEST_OVERWORLD_IMAGE, GCCoreUtil.getDimensionID(mc.world), new Object[] {}));
            ClientProxyCore.overworldTextureRequestSent = true;
        }
		
		 // HOME:
        f10 = 14.0F;
        final float earthRotation = (float) (this.mc.world.getSpawnPoint().getZ() - mc.player.posZ - 20000) * 0.01F;
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
            // Overworld texture is 48x48 in a 64x64 .png file
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(SkyProviderMoon.overworldTexture);
        }
        
        //this.mc.theWorld.getMoonPhase();
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(-f10, -100.0D, f10).tex(0, 1.0).endVertex();
        worldRenderer.pos(f10, -100.0D, f10).tex(1.0, 1.0).endVertex();
        worldRenderer.pos(f10, -100.0D, -f10).tex(1.0, 0).endVertex();
        worldRenderer.pos(-f10, -100.0D, -f10).tex(0, 0).endVertex();
        tessellator.draw();
       
        f10 = 11.0F;
        float f = 0.59F;
        this.renderAtmo(tessellator, 0, 0F, f10 + 2.2F, new Vector3(88 / 255.0F * f, 122 / 255.0F * f, 180 / 255.0F * f));

        GL11.glPopMatrix();
	}

	@Override
	protected ModeLight modeLight() {
		return ModeLight.ALWAYS_LIGHT;
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
	protected StarColor colorSunAura() {
		return StarColor.WHITE;
	}

	@Override
	protected Vector3 getAtmosphereColor() {
		return null;
	}
	
	@Override
	public boolean enableSmoothRender() {return true;}
}
