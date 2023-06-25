package galaxyspace.systems.SolarSystem.satellites.mars.dimension.sky;

import org.lwjgl.opengl.GL11;

import asmodeuscore.api.dimension.IAdvancedSpace.StarClass;
import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldProviderSpace;
import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class SkyProviderMarsSS extends SkyProviderBase{
	
	private static final ResourceLocation marsTexture = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/celestialbodies/mars.png");
	private static final ResourceLocation phobosTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/phobos.png");
	private static final ResourceLocation deimosTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/deimos.png");
	private static final ResourceLocation overworldTexture = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/celestialbodies/earth.png");
	
	public float spinAngle = 0;
    public float spinDeltaPerTick = 0;
    
	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder buffer, float f10, float ticks) {
		GL11.glPushMatrix();
		this.renderImage(overworldTexture, 40F, -this.mc.world.getCelestialAngle(ticks) * 360F, 0F, 0.8F);
		this.renderImage(phobosTexture, 80F, -(this.mc.world.getCelestialAngle(ticks) * 360F), 40F, 4.0F);
		this.renderImage(deimosTexture, 80F, -(this.mc.world.getCelestialAngle(ticks) * 360F) / 2, 195F, 4.0F);
		
		this.renderImage(marsTexture, 0F, 0F, 0F, 180F, 1.0F, .4F);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_ALPHA_TEST);

		GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F); 
		f10 = 180F;
		float alpha = 0.9F;
		/*
		
		float k = f10;
		float l = 20;
		float angle = mc.world.getCelestialAngle(ticks)*360 % 360;
		
				if (angle > 300 && angle < 360)
			l = -f10 + angle - 300;
		else if (angle > 0 && angle < 50)
			l = f10 - 50 + angle;
		else if (angle > 180 && angle < 300) {
			k = f10 - (angle - 180);
			l = f10;
		} else
			l = f10;
		
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		buffer.pos(-f10, -100.0D, k).color(0.0F, 0.0F, 0.0F, alpha).endVertex();
		buffer.pos(f10, -100.0D, k).color(0.0F, 0.0F, 0.0F, alpha).endVertex();
		buffer.pos(f10, -100.0D, -l).color(0.0F, 0.0F, 0.0F, alpha).endVertex();
		buffer.pos(-f10, -100.0D, -l).color(0.0F, 0.0F, 0.0F, alpha).endVertex();

		tessellator.draw();
		 	*/
		double mod = ((WorldProviderSpace) this.mc.world.provider).getDayLength() / 24000;
		long time = this.mc.player.getEntityWorld().getWorldTime()
				% ((WorldProviderSpace) this.mc.world.provider).getDayLength();
		double k = (time / 8) / mod;
	
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

		if(k >= 0 && k <= f10 * 2) {
					
			buffer.pos(-f10, -100.0D, f10 - k).color(0.0F, 0.0F, 0.0F, alpha).endVertex();
	        buffer.pos(f10, -100.0D, f10 - k).color(0.0F, 0.0F, 0.0F, alpha).endVertex();
	        buffer.pos(f10, -100.0D, -f10).color(0.0F, 0.0F, 0.0F, alpha).endVertex();
	        buffer.pos(-f10, -100.0D, -f10).color(0.0F, 0.0F, 0.0F, alpha).endVertex();
	        
		}
		else if(k >= f10 * 8 && k <= f10 * 9.8)
		{			
			k -= (f10 * 8);
			
			buffer.pos(-f10, -100.0D, f10).color(0.0F, 0.0F, 0.0F, alpha).endVertex();
	        buffer.pos(f10, -100.0D, f10).color(0.0F, 0.0F, 0.0F, alpha).endVertex();
	        buffer.pos(f10, -100.0D, 150 - k).color(0.0F, 0.0F, 0.0F, alpha).endVertex();
	        buffer.pos(-f10, -100.0D, 150 - k).color(0.0F, 0.0F, 0.0F, alpha).endVertex();
	        
		}
		else if(k > f10 * 9.8)
		{
			buffer.pos(-f10, -100.0D, f10).color(0.0F, 0.0F, 0.0F, alpha).endVertex();
	        buffer.pos(f10, -100.0D, f10).color(0.0F, 0.0F, 0.0F, alpha).endVertex();
	        buffer.pos(f10, -100.0D, -f10).color(0.0F, 0.0F, 0.0F, alpha).endVertex();
	        buffer.pos(-f10, -100.0D, -f10).color(0.0F, 0.0F, 0.0F, alpha).endVertex();
		}
		
		tessellator.draw();
	    GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		float f = 1.0F - mc.world.getStarBrightness(ticks);
		this.renderAtmo(tessellator, 90F, 0F, f10 - 8, new Vec3d(120 / 255.0F * f, 90 / 255.0F * f, 90 / 255.0F * f));
		
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
		return 5.2F;
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

	@Override
	public boolean enableRenderPlanet() {return false;}
}

