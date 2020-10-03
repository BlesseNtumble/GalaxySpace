package galaxyspace.systems.SolarSystem.moons.callisto.dimension.sky;

import org.lwjgl.opengl.GL11;

import asmodeuscore.api.dimension.IAdvancedSpace.StarColor;
import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class SkyProviderCallisto extends SkyProviderBase
{

	private static final ResourceLocation jupiterTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/jupiter.png");
	 private static final ResourceLocation europaTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/europa.png");
    private static final ResourceLocation ganymedeTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/ganymede.png");
    boolean test = false;
    int wait = 5;
    
    @Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder worldRenderer, float size, float ticks) {
		
    	GL11.glPushMatrix();
	    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
	    	
	    	float x = (float) (15 * (Math.sin((this.mc.world.getCelestialAngle(ticks) * 360.0F) / 40.0F)));
	    	if(wait == 0 && (x >= 15F || x <= -15F)) 
	    	{
	    		wait = 150;
	    		test = !test;
	    	}
	    	if(!test) {
		    	this.renderImage(jupiterTexture, 0F, 90F, 0F, 20.0F);
		    	float f = 0.9F;
		    	this.renderAtmo(tessellator, 90.0F, 0.0F, 20 - 2, new Vector3(120 / 255.0F * f, 110 / 255.0F * f, 120 / 255.0F * f));
		    }

	        if(wait > 0) wait--;
	        
	        this.renderImage(europaTexture, x, 100F, 0, 1.0F);
	        this.renderImage(ganymedeTexture, x+15, 85, 0, 1.5F);
	        
	        if(test) {
		    	this.renderImage(jupiterTexture, 0F, 90F, 0F, 20.0F);
		    	float f = 0.9F;
		    	this.renderAtmo(tessellator, 90.0F, 0.0F, 20 - 2, new Vector3(120 / 255.0F * f, 110 / 255.0F * f, 120 / 255.0F * f));
		    }
	    GL11.glPopMatrix(); 
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
	protected ModeLight modeLight() {
		return ModeLight.DEFAULT;
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