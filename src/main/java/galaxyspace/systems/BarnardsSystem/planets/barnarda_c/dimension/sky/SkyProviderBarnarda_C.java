package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.dimension.sky;

import org.lwjgl.opengl.GL11;

import asmodeuscore.api.dimension.IAdvancedSpace.StarColor;
import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import galaxyspace.GalaxySpace;
import galaxyspace.systems.BarnardsSystem.BarnardsSystemBodies;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.dimension.WorldProviderBarnarda_C_WE;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class SkyProviderBarnarda_C extends SkyProviderBase{

	private static final ResourceLocation barnarda_c1_Texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/barnards/moons/barnarda_c1.png");
	private static final ResourceLocation barnarda_c2_Texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/barnards/moons/barnarda_c2.png");
	
	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder buffer, float size, float ticks) {
		GL11.glPushMatrix();         
		        
        if(!this.mc.world.isRaining()) {
        	
           	this.renderImage(barnarda_c1_Texture, 0, 0, this.getCelestialAngle(getDayLength()), 5.5F, 1.0F, 0.55F);   
           	this.renderImage(barnarda_c2_Texture, 10, 40, this.getCelestialAngle((long) (getDayLength() * 1.2)) + 80F, 2.5F, 0.4F);
        	

    		float f = 0.4F;
        	this.renderAtmo(tessellator, -90.0F, this.getCelestialAngle(getDayLength()), 5.2F, new Vector3(120 / 255.0F * f, 160 / 255.0F * f, 180 / 255.0F * f));
        }        
     
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
		return 4;
	}

	@Override
	protected ResourceLocation sunImage() {
		return BarnardsSystemBodies.BarnardsSystem.getMainStar().getBodyIcon();
	}

	@Override
	protected boolean enableStar() {
		return true;
	}

	@Override
	protected StarColor colorSunAura() {
		return StarColor.ORANGE;
	}

	@Override
	protected Vector3 getAtmosphereColor() {
		return ((WorldProviderBarnarda_C_WE)this.mc.world.provider).getSkyColor();
	}

}
