package galaxyspace.systems.SolarSystem.planets.ceres.dimension.sky;

import org.lwjgl.opengl.GL11;

import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;

public class SkyProviderCeres extends SkyProviderBase
{
	private static final ResourceLocation meteor1Texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/environment/background/meteor1.png");
	private static final ResourceLocation meteor2Texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/environment/background/meteor2.png");
	private static final ResourceLocation meteor3Texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/environment/background/meteor3.png");
	private static final ResourceLocation meteorpoleTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/environment/background/meteorpole.png");
	
	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder worldRenderer, float f10, float ticks) {
		
		for(int i = 0; i < 3; i++)
        {
			GL11.glPopMatrix();
	        GL11.glPushMatrix();
	        GL11.glEnable(GL11.GL_BLEND);
	        
	        f10 = 80.0F * i;
	        GL11.glScalef(0.8F, 0.6F, 0.8F);
	        //GL11.glRotatef(-180.0F, 1.0F, 0.0F, 0.0F);
	        GL11.glRotatef(-90.0f, 0.0F, 0.0F, 1.0F);
	        //GL11.glRotatef(-90.0f, 1.0F, 0.0F, 0.0F);
	        GL11.glRotatef(this.mc.world.getCelestialAngle(this.ticks) * 360.0F, 0.0F, 0.0F, 1.0F);
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.4F);
	        FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.meteorpoleTexture);
	        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
	        worldRenderer.pos(-f10, -100.0D, f10).tex(0, 1.0).endVertex();
	        worldRenderer.pos(f10, -100.0D, f10).tex(1.0, 1.0).endVertex();
	        worldRenderer.pos(f10, -100.0D, -f10).tex(1.0, 0).endVertex();
	        worldRenderer.pos(-f10, -100.0D, -f10).tex(0, 0).endVertex();
	        tessellator.draw();
	        GL11.glDisable(GL11.GL_BLEND);
        }
	}

	@Override
	protected boolean enableBaseImages() {
		return true;
	}

	@Override
	protected float sunSize() {
		return 4.5F;
	}

	@Override
	protected boolean enableStar() {
		return true;
	}

	@Override
	protected ResourceLocation sunImage() {
		return new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sun_blank.png");
	}

	@Override
	protected int modeLight() {
		return 0;
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