package galaxyspace.systems.SolarSystem.moons.miranda.dimension.sky;

import org.lwjgl.opengl.GL11;

import asmodeuscore.api.dimension.IAdvancedSpace.StarColor;
import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.FMLClientHandler;

public class SkyProviderMiranda extends SkyProviderBase
{

	private static final ResourceLocation uranusTexture = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/celestialbodies/uranus.png");
	private static final ResourceLocation uranusRingTexture = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/celestialbodies/uranus_rings.png");
		  
	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder worldRenderer, float f10, float ticks) {
		
		GL11.glPushMatrix();
		
		f10 = 40.0F;
		//GL11.glScalef(0.6F, 0.6F, 0.6F);
		GL11.glRotatef(100.0F, 1.0F, 0.0F, 0.0F);
        //GL11.glRotatef(-75F, 1.0F, 0.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.uranusTexture);
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		worldRenderer.pos(-f10, -100.0D, f10).tex(0, 1.0).endVertex();
		worldRenderer.pos(f10, -100.0D, f10).tex(1.0, 1.0).endVertex();
		worldRenderer.pos(f10, -100.0D, -f10).tex(1.0, 0).endVertex();
		worldRenderer.pos(-f10, -100.0D, -f10).tex(0, 0).endVertex();
		tessellator.draw();   
		
		float f = 0.9F;
		this.renderAtmo(tessellator, 0.0F, 0.0F, f10 - 6, new Vec3d(120 / 255.0F * f, 160 / 255.0F * f, 180 / 255.0F * f));
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_ALPHA_TEST);        
        //GL11.glEnable(GL11.GL_BLEND);
        
		f10 = 106F;
		GL11.glScalef(0.6F, 1.0F, 1.0F);
        GL11.glRotatef(100.0F, 1.0F, 0.0F, 0.0F);
        GL11.glTranslatef(50, 0, 13.5F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.uranusRingTexture);
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		worldRenderer.pos(-f10, -100.0D, f10).tex(0, 1.0).endVertex();
		worldRenderer.pos(f10, -100.0D, f10).tex(1.0, 1.0).endVertex();
		worldRenderer.pos(f10, -100.0D, -f10).tex(1.0, 0).endVertex();
		worldRenderer.pos(-f10, -100.0D, -f10).tex(0, 0).endVertex();
		tessellator.draw();      

		GL11.glDisable(GL11.GL_ALPHA_TEST);
        //GL11.glDisable(GL11.GL_BLEND);

		GL11.glPopMatrix();
	}

	@Override
	protected boolean enableBaseImages() {
		return true;
	}

	@Override
	protected float sunSize() {
		return 3.5F;
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
	protected ModeLight modeLight() {
		return ModeLight.DEFAULT;
	}

	@Override
	protected StarColor colorSunAura() {
		return StarColor.WHITE;
	}

	@Override
	protected Vec3d getAtmosphereColor() {
		return null;
	}
	
	@Override
	public boolean enableSmoothRender() {return true;}

}