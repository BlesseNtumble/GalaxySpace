package galaxyspace.systems.SolarSystem.moons.enceladus.dimension.sky;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import galaxyspace.GalaxySpace;
import galaxyspace.core.client.render.sky.SkyProviderBase;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class SkyProviderEnceladus extends SkyProviderBase{

	private static final ResourceLocation saturnTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/saturn.png");
	private static final ResourceLocation saturnRingsTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/saturn_rings.png");
		
	private static final ResourceLocation titanTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/titan.png");
	
	@Override
	protected void rendererSky(Tessellator tessellator, float f10, float ticks) 
	{
		GL11.glPopMatrix();
		GL11.glPushMatrix();
			
		// Render titan
		f10 = 0.5F;
		GL11.glScalef(0.6F, 0.6F, 0.6F);			
		GL11.glRotatef(5.0F, 0.0F, 0.0F, 1.0F);		
		GL11.glRotatef(this.mc.theWorld.getCelestialAngle(this.ticks) * 360.0F / 2, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-110F, 1.0F, 0.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.titanTexture);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
		tessellator.addVertexWithUV(f10, -100.0D, f10, 1, 1);
		tessellator.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
		tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
		tessellator.draw();
		
        GL11.glPopMatrix();
        GL11.glPushMatrix();
               
        // Render saturn
        f10 = 50.0F;
        GL11.glScalef(0.6F, 0.6F, 0.6F);
        //GL11.glRotatef(180.0F, 100.0F, 0.0F, 0.0F);
        GL11.glRotatef(270F, 1.0F, 0.0F, 0.0F);
        
        GL11.glRotatef(-this.mc.theWorld.getCelestialAngle(this.ticks) * 360.0F + 90, 0.0F, 1.0F, 0.0F);
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.saturnTexture);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
        tessellator.addVertexWithUV(f10, -100.0D, f10, 1, 1);
        tessellator.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
        tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
        tessellator.draw();
        
        GL11.glPopMatrix();
        GL11.glPushMatrix();  
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        
        // Render saturn Rings
        f10 = 125.0F;
        GL11.glScalef(0.6F, 0.6F, 0.6F);
        //GL11.glRotatef(180.0F, 100.0F, 1.0F, 0.0F);
        GL11.glRotatef(270F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(-this.mc.theWorld.getCelestialAngle(this.ticks) * 360.0F + 90, 0.0F, 1.0F, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.saturnRingsTexture);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
        tessellator.addVertexWithUV(f10, -100.0D, f10, 1, 1);
        tessellator.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
        tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
        tessellator.draw();

        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);
		
	}

	@Override
	protected boolean enableBaseImages() {
		return true;
	}

	@Override
	protected float sunSize() {
		return 6.5F;
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
	protected Vector3  colorSunAura() {	
		return null;
	}

	@Override
	protected Vector3 getAtmosphereColor() {
		return null;
	}

}