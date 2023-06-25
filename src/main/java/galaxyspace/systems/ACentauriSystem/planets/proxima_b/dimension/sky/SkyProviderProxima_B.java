package galaxyspace.systems.ACentauriSystem.planets.proxima_b.dimension.sky;

import org.lwjgl.opengl.GL11;

import asmodeuscore.api.dimension.IAdvancedSpace.StarClass;
import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import galaxyspace.GalaxySpace;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SkyProviderProxima_B extends SkyProviderBase {

	private ResourceLocation acentauri_a = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/acentauri/centauri_a.png");
	private ResourceLocation acentauri_b = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/acentauri/centauri_b.png");
		
	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder worldRenderer, float size, float ticks) {
		
		float rain = 1.0F - this.mc.world.getRainStrength(ticks);  
        
        if(rain < 0.3F) rain = 0.3F;
		float sunBrightness = this.mc.world.isRaining() ? this.mc.world.getSunBrightness(ticks) * rain : this.mc.world.getSunBrightness(ticks);

		GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glShadeModel(GL11.GL_SMOOTH);
	        GL11.glEnable(GL11.GL_BLEND);
	        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
	    	GL11.glRotatef(this.mc.world.getCelestialAngle(ticks) * 360.0F, 0.0F, 0.0F, 1.0F);   
	        GL11.glRotatef(60F, 1.0F, 0.0F, 0.0F);
	        GL11.glRotatef(1.5F, 0.0F, 0.0F, 1.0F);
			this.renderSunAura(tessellator, 0.1F, sunBrightness, StarClass.YELLOW);
			GL11.glRotatef(9F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-1.5F, 0.0F, 0.0F, 1.0F);
			this.renderSunAura(tessellator, 0.1F, sunBrightness, StarClass.YELLOW);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glShadeModel(GL11.GL_FLAT);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();		
			World world = mc.world;
		
			GL11.glRotatef(this.mc.world.getCelestialAngle(ticks) * 360.0F, 0.0F, 0.0F, 1.0F);   
			if(!this.mc.world.isRaining()) {
				this.renderImage(acentauri_a, -90F, 182F, 60F, 1.2F, sunBrightness);
				this.renderImage(acentauri_b, -90F, 180F, 69F, 1.0F, sunBrightness);	
			}
		
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
	protected ModeLight modeLight() {
		
		if(mc.world.isRaining()) return ModeLight.WITHOUT_SUN;
		return ModeLight.DEFAULT;	
		
	}

	@Override
	protected StarClass colorSunAura() {
		return StarClass.ORANGE;
	}

	@Override
	protected Vec3d getAtmosphereColor() {
		return null;
	}

	@Override
	public boolean enableSmoothRender() {return false;}
	
	@Override
	public int expandSizeAura() {return 25;}
	
	@Override
	public boolean enableLargeSunAura() {return true;}
}
