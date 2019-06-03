package galaxyspace.systems.ACentauriSystem.planets.proxima_b.dimension.sky;

import org.lwjgl.opengl.GL11;

import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;

public class SkyProviderProxima_B extends SkyProviderBase {

	private ResourceLocation acentauri_a = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/acentauri/centauri_a.png");
	private ResourceLocation acentauri_b = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/acentauri/centauri_b.png");
		
	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder worldRenderer, float f10, float ticks) {
		
		//GL11.glPopMatrix();
        //GL11.glPushMatrix();

		World world = mc.world;
		int phase = world.provider.getMoonPhase(world.getWorldTime());
		
		
		f10 = 0.8F;
		GL11.glScalef(0.6F, 0.6F, 0.6F);
		GL11.glRotatef(120F, 1.0F, 0.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.acentauri_a);

		if (phase != 0 && phase != 6) {
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			worldRenderer.pos(-f10, -100.0D, f10).tex(0, 1.0).endVertex();
			worldRenderer.pos(f10, -100.0D, f10).tex(1.0, 1.0).endVertex();
			worldRenderer.pos(f10, -100.0D, -f10).tex(1.0, 0).endVertex();
			worldRenderer.pos(-f10, -100.0D, -f10).tex(0, 0).endVertex();
			tessellator.draw();
		}

		f10 = 0.5F;
		GL11.glScalef(0.6F, 0.6F, 0.6F);
		GL11.glRotatef(1F, 0.0F, 0.0F, 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.acentauri_b);

		if (phase != 0 && phase != 6) {
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			worldRenderer.pos(-f10, -100.0D, f10).tex(0, 1.0).endVertex();
			worldRenderer.pos(f10, -100.0D, f10).tex(1.0, 1.0).endVertex();
			worldRenderer.pos(f10, -100.0D, -f10).tex(1.0, 0).endVertex();
			worldRenderer.pos(-f10, -100.0D, -f10).tex(0, 0).endVertex();
			tessellator.draw();
		}
		
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
        this.renderSunAura(tessellator, 0.0F, 0.8F);
        GL11.glRotatef(4F, 0.0F, 0.0F, 1.0F);
        this.renderSunAura(tessellator, 0.0F, 0.5F);
        GL11.glPopMatrix();
	}

	@Override
	protected boolean enableBaseImages() {
		return true;
	}

	@Override
	protected float sunSize() {
		return 8.5F;
	}

	@Override
	protected boolean enableStar() {
		return true;
	}

	@Override
	protected ResourceLocation sunImage() {
		return new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/proxima/proxima.png");
	}

	@Override
	protected int modeLight() {
		switch(mc.world.provider.getMoonPhase(mc.world.getWorldTime()))
		{
			case 0:
			case 6:	return 2;
			default: return 0;
		}
	}

	@Override
	protected Vector3 colorSunAura() {
		return new Vector3(255, 140, 100);
	}

	@Override
	protected Vector3 getAtmosphereColor() {
		return null;
	}

	@Override
	public boolean enableSmoothRender() {return false;}
	
	@Override
	public int addSizeAura() {return 35;}
	
	@Override
	public boolean enableLargeSunAura() {return true;}
}
