package galaxyspace.systems.SolarSystem.planets.ceres.dimension.sky;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import galaxyspace.GalaxySpace;
import galaxyspace.core.client.render.sky.SkyProviderBase;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class SkyProviderCeres extends SkyProviderBase
{
	private static final ResourceLocation meteor1Texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/environment/background/meteor1.png");
	private static final ResourceLocation meteor2Texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/environment/background/meteor2.png");
	private static final ResourceLocation meteor3Texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/environment/background/meteor3.png");
	private static final ResourceLocation meteorpoleTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/environment/background/meteorpole.png");
	  	    
	@Override
	protected void rendererSky(Tessellator tessellator, float f10, float partialTicks) {
		
		        
        for(int i = 0; i < 3; i++)
        {
        	GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            
	        // Render meteor1
	        f10 = 100.0F * i;
	        GL11.glScalef(0.6F, 0.6F, 0.6F);
	        GL11.glRotatef(90.0F, 90.0F, 1.0F, 0.0F);
	        GL11.glRotatef(90F, 90.0F, 0.0F, 0.0F);
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
	        FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.meteorpoleTexture);
	        tessellator.startDrawingQuads();
	        tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
	        tessellator.addVertexWithUV(f10, -100.0D, f10, 1, 1);
	        tessellator.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
	        tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
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
		return 10.5F;
	}

	@Override
	protected boolean enableStar() {
		return true;
	}

	@Override
	protected ResourceLocation sunImage() {
		return null;
	}

	@Override
	protected int modeLight() {
		return 0;
	}

	@Override
	protected Vector3 colorSunAura() {		
		return null;
	}

	@Override
	protected Vector3 getAtmosphereColor() {
		return null;
	}
}