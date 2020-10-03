package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.dimension.sky;

import org.lwjgl.opengl.GL11;

import asmodeuscore.api.dimension.IAdvancedSpace.StarColor;
import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import galaxyspace.GalaxySpace;
import galaxyspace.systems.BarnardsSystem.BarnardsSystemBodies;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.dimension.WorldProviderBarnarda_C_WE;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class SkyProviderBarnarda_C extends SkyProviderBase{

	private static final ResourceLocation barnarda_c1_Texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/barnards/moons/barnarda_c1.png");
	private static final ResourceLocation barnarda_c2_Texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/barnards/moons/barnarda_c2.png");
	
	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder buffer, float size, float ticks) {
		GL11.glPushMatrix();         
        //GL11.glEnable(GL11.GL_BLEND);
        
        if(!this.mc.world.isRaining()) {
        	this.renderImage(barnarda_c1_Texture, 0, 0, this.getCelestialAngle(getDayLength()), 5.5F, 1.0F, 0.55F);        	
        	this.renderImage(barnarda_c2_Texture, 40, 0, this.getCelestialAngle((long) (getDayLength() * 1.2)) + 80F, 1.5F);
        	

    		float f = 0.4F;
        	this.renderAtmo(tessellator, 0.0F, this.getCelestialAngle(getDayLength()), 5.2F, new Vector3(120 / 255.0F * f, 160 / 255.0F * f, 180 / 255.0F * f));
    		
        }
        /*
        float f10 = 5.5F;
        GL11.glEnable(GL11.GL_BLEND);    

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        //GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        
		GL11.glRotatef(this.getCelestialAngle(getDayLenght()), 0.0F, 0.0F, 1.0F); // ������������ �� Z
		GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.35F);
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		buffer.pos(-f10, -100.0D, f10).endVertex();
		buffer.pos(f10, -100.0D, f10).endVertex();
		buffer.pos(f10, -100.0D, -f10).endVertex();
        buffer.pos(-f10, -100.0D, -f10).endVertex();
        tessellator.draw();
        GL11.glDisable(GL11.GL_BLEND);
*/
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
