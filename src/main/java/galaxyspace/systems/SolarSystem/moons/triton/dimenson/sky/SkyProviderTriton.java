package galaxyspace.systems.SolarSystem.moons.triton.dimenson.sky;

import org.lwjgl.opengl.GL11;

import asmodeuscore.api.dimension.IAdvancedSpace.StarClass;
import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.FMLClientHandler;

public class SkyProviderTriton extends SkyProviderBase {

	private static final ResourceLocation neptuneTexture = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/celestialbodies/neptune.png");
	
	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder worldRenderer, float f10, float ticks) {	
		
		GL11.glPushMatrix();
		
		f10 = 10.0F;
		//GL11.glScalef(0.6F, 0.6F, 0.6F);
		//GL11.glRotatef(100.0F, 1.0F, 0.0F, 0.0F);
        //GL11.glRotatef(-75F, 1.0F, 0.0F, 0.0F);
		//GL11.glScalef(0.6F, 0.6F, 0.6F);
        GL11.glRotatef(100.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(20F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(60F, 0.0F, 0.0F, 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.neptuneTexture);
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		worldRenderer.pos(-f10, -100.0D, f10).tex(0, 1.0).endVertex();
		worldRenderer.pos(f10, -100.0D, f10).tex(1.0, 1.0).endVertex();
		worldRenderer.pos(f10, -100.0D, -f10).tex(1.0, 0).endVertex();
		worldRenderer.pos(-f10, -100.0D, -f10).tex(0, 0).endVertex();
		tessellator.draw();   
		
		float f = 0.9F;
		this.renderAtmo(tessellator, 0.0F, 0.0F, f10 - 1.5F, new Vec3d(120 / 255.0F * f, 160 / 255.0F * f, 180 / 255.0F * f));
				
        long time = this.mc.player.getEntityWorld().getWorldTime() % getDayLength();
		double k = (time / 8) / (getDayLength() / 24000L);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.95F);
		
		
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		if(k >= 280 && k < 1500) {
			k -= 280;
			k /= 50;
			
			worldRenderer.pos(-f10, -100.0D, f10).endVertex();
	        worldRenderer.pos(f10, -100.0D, f10).endVertex();
	        worldRenderer.pos(f10, -100.0D, -f10 + k).endVertex();
	        worldRenderer.pos(-f10, -100.0D, -f10 + k).endVertex();
		}
		else if(k < 280)
		{
			worldRenderer.pos(-f10, -100.0D, f10).endVertex();
	        worldRenderer.pos(f10, -100.0D, f10).endVertex();
	        worldRenderer.pos(f10, -100.0D, -f10).endVertex();
	        worldRenderer.pos(-f10, -100.0D, -f10).endVertex();
		}
		tessellator.draw();
	    GL11.glDisable(GL11.GL_BLEND);
	    GL11.glPopMatrix();
	}

	@Override
	protected ModeLight modeLight() {
		return ModeLight.DEFAULT;
	}

	@Override
	protected boolean enableBaseImages() {
		return true;
	}

	@Override
	protected float sunSize() {
		return 1.2F;
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
	protected StarClass colorSunAura() {
		return StarClass.WHITE;
	}

	@Override
	protected Vec3d getAtmosphereColor() {
		return null;
	}	

	@Override
	public boolean enableSmoothRender() {return true;}

}
