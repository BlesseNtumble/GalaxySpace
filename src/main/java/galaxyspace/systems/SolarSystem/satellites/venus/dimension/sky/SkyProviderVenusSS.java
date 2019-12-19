package galaxyspace.systems.SolarSystem.satellites.venus.dimension.sky;

import org.lwjgl.opengl.GL11;

import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldProviderSpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;

public class SkyProviderVenusSS extends SkyProviderBase{
	
	private ResourceLocation venus_texture = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/celestialbodies/venus.png");
	
	public float spinAngle = 0;
    public float spinDeltaPerTick = 0;
    
	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder worldRenderer, float f10, float ticks) {
		GL11.glPopMatrix();
        GL11.glPushMatrix();
        //GL11.glEnable(GL11.GL_BLEND);
        //OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE, GL11.GL_ZERO);
        
        f10 = 180.0F;
        GL11.glScalef(0.8F, 0.8F, 0.8F);
        //GL11.glRotatef(-180.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(0.0f, 0.0F, 0.0F, 1.0F);
        //GL11.glRotatef(-90.0f, 1.0F, 0.0F, 0.0F);
        //GL11.glRotatef(this.mc.world.getCelestialAngle(this.ticks) * 360.0F, 0.0F, 0.0F, 1.0F);
        float color = mc.world.getStarBrightness(ticks);
        GL11.glColor4f(1.0F - color, 1.0F - color, 1.0F - color, 1.0F);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(venus_texture);
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(-f10, -100.0D, f10).tex(0, 1.0).endVertex();
        worldRenderer.pos(f10, -100.0D, f10).tex(1.0, 1.0).endVertex();
        worldRenderer.pos(f10, -100.0D, -f10).tex(1.0, 0).endVertex();
        worldRenderer.pos(-f10, -100.0D, -f10).tex(0, 0).endVertex();
        tessellator.draw();
        //GL11.glDisable(GL11.GL_BLEND);
    
        double mod = ((WorldProviderSpace) this.mc.world.provider).getDayLength() / 24000;
        long time = this.mc.player.getEntityWorld().getWorldTime() % ((WorldProviderSpace) this.mc.world.provider).getDayLength();
		double k = (time / 8) / mod;
		
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ZERO, GL11.GL_DST_ALPHA);
		GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.8F);
		GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
		
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		GalaxySpace.debug(k + " | " + (f10 - k) + " | " + mod);
		if(k >= 0 && k <= f10 * 2) {
					
			worldRenderer.pos(-f10, -100.0D, f10 - k).endVertex();
	        worldRenderer.pos(f10, -100.0D, f10 - k).endVertex();
	        worldRenderer.pos(f10, -100.0D, -f10).endVertex();
	        worldRenderer.pos(-f10, -100.0D, -f10).endVertex();
	        
		}
		else if(k >= f10 * 8 && k <= f10 * 8.5)
		{			
			k -= 1200;
			
			worldRenderer.pos(-f10, -100.0D, f10).endVertex();
	        worldRenderer.pos(f10, -100.0D, f10).endVertex();
	        worldRenderer.pos(f10, -100.0D, 150 - k).endVertex();
	        worldRenderer.pos(-f10, -100.0D, 150 - k).endVertex();
	        
		}
		else if(k > f10 * 8)
		{
			worldRenderer.pos(-f10, -100.0D, f10).endVertex();
	        worldRenderer.pos(f10, -100.0D, f10).endVertex();
	        worldRenderer.pos(f10, -100.0D, -f10).endVertex();
	        worldRenderer.pos(-f10, -100.0D, -f10).endVertex();
		}
		
		tessellator.draw();
	    GL11.glDisable(GL11.GL_BLEND);
	
	}

	@Override
	protected int modeLight() {
		return 0;
	}

	@Override
	protected boolean enableBaseImages() {
		return true;
	}

	@Override
	protected float sunSize() {
		return 4.0F;
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
	protected Vector3 colorSunAura() {
		return null;
	}

	@Override
	protected Vector3 getAtmosphereColor() {
		return null;
	}
	
	@Override
	public boolean enableSmoothRender() {return false;}

	@Override
	public boolean enableRenderPlanet() {return false;}
}

