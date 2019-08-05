package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.dimension.sky;

import org.lwjgl.opengl.GL11;

import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import galaxyspace.GalaxySpace;
import galaxyspace.systems.BarnardsSystem.BarnardsSystemBodies;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldProviderSpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;

public class SkyProviderBarnarda_C extends SkyProviderBase{

	private static final ResourceLocation barnarda_c1_Texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/barnards/moons/barnarda_c1.png");
	private static final ResourceLocation barnarda_c2_Texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/barnards/moons/barnarda_c2.png");
	
	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder buffer, float f10, float ticks) {
		GL11.glPopMatrix();
        GL11.glPushMatrix();
        
       
        
        long daylength = ((WorldProviderSpace) this.mc.world.provider).getDayLength();
        
        f10 = 5.5F;

        GL11.glRotatef(this.getCelestialAngle(daylength), 0.0F, 0.0F, 1.0F);	        
        GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);	
        GL11.glRotatef(0.0F, 1.0F, 0.0F, 0.0F);	
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.barnarda_c1_Texture);
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(-f10, -100.0D, f10).tex(0, 1.0).endVertex();
        buffer.pos(f10, -100.0D, f10).tex(1.0, 1.0).endVertex();
        buffer.pos(f10, -100.0D, -f10).tex(1.0, 0).endVertex();
        buffer.pos(-f10, -100.0D, -f10).tex(0, 0).endVertex();
        tessellator.draw();
        
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        
        GL11.glEnable(GL11.GL_BLEND);
        //OpenGlHelper.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        
        f10 = 2.5F;
        
        GL11.glRotatef(this.getCelestialAngle((long) (daylength * 1.2)), 0.0F, 0.0F, 1.0F);	        
        GL11.glRotatef(80.0F, 0.0F, 0.0F, 1.0F);	
        GL11.glRotatef(40.0F, 1.0F, 0.0F, 0.0F);	
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.barnarda_c2_Texture);
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(-f10, -100.0D, f10).tex(0, 1.0).endVertex();
        buffer.pos(f10, -100.0D, f10).tex(1.0, 1.0).endVertex();
        buffer.pos(f10, -100.0D, -f10).tex(1.0, 0).endVertex();
        buffer.pos(-f10, -100.0D, -f10).tex(0, 0).endVertex();
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
		return 6;
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
	protected Vector3 colorSunAura() {
		return new Vector3(255, 200, 100);
	}

	@Override
	protected Vector3 getAtmosphereColor() {
		return null;
	}

}
