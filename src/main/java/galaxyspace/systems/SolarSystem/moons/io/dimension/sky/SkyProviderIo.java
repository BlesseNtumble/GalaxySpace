package galaxyspace.systems.SolarSystem.moons.io.dimension.sky;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import galaxyspace.GalaxySpace;
import galaxyspace.core.client.render.sky.SkyProviderBase;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class SkyProviderIo extends SkyProviderBase{

	private static final ResourceLocation jupiterTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/jupiter.png");
	private static final ResourceLocation callistoTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/callisto.png");
	private static final ResourceLocation europaTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/europa.png");
	private static final ResourceLocation ganymedeTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/ganymede.png");
   
	@Override
	protected void rendererSky(Tessellator tessellator, float f10, float patrialTicks) 
	{
				
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		
		// Render europa
		f10 = 1.5F;
		GL11.glScalef(0.6F, 0.6F, 0.6F);			
		GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);		
		//GL11.glRotatef(-35.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(this.mc.theWorld.getCelestialAngle(this.ticks) * 360.0F * 2, 0.0F, 1.0F, 0.0F);
		
		GL11.glRotatef(-100F, 1.0F, 0.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.europaTexture);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
		tessellator.addVertexWithUV(f10, -100.0D, f10, 1, 1);
		tessellator.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
		tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
		tessellator.draw();
		
		GL11.glPopMatrix();
		GL11.glPushMatrix();
			
		// Render ganymede
		f10 = 1.0F;
		GL11.glScalef(0.6F, 0.6F, 0.6F);			
		GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);		
		//GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(this.mc.theWorld.getCelestialAngle(this.ticks) * 360.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-95F, 1.0F, 0.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.ganymedeTexture);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
		tessellator.addVertexWithUV(f10, -100.0D, f10, 1, 1);
		tessellator.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
		tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
		tessellator.draw();
		
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		
		// Render callisto
		f10 = 0.8F;
		GL11.glScalef(0.6F, 0.6F, 0.6F);	
		GL11.glRotatef(-44.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(this.mc.theWorld.getCelestialAngle(this.ticks) * 360.0F / 2, 0.0F, 1.0F, 0.0F);
		//GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);		
		GL11.glRotatef(-105F, 1.0F, 0.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.callistoTexture);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
		tessellator.addVertexWithUV(f10, -100.0D, f10, 1, 1);
		tessellator.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
		tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
		tessellator.draw();
		
				
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        
        // Render jupiter
        f10 = 60.0F;
        //GL11.glScalef(0.6F, 0.6F, 0.6F);
        GL11.glRotatef(-180.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(100F, 1.0F, 0.0F, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.jupiterTexture);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
        tessellator.addVertexWithUV(f10, -100.0D, f10, 1, 1);
        tessellator.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
        tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
        tessellator.draw();
        
        float f = 1.0F;
        this.renderAtmo(tessellator, 0.0F, 0.0F, f10 - 10, new Vector3(120 / 255.0F * f, 110 / 255.0F * f, 120 / 255.0F * f));

	}

	@Override
	protected boolean enableBaseImages() {
		return true;
	}

	@Override
	protected float sunSize() {
		return 5F;
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
	protected int modeLight() {
		return 0;
	}

	@Override
	protected Vector3 colorSunAura() {	
		return new Vector3(150, 150, 150);
	}

	@Override
	protected Vector3 getAtmosphereColor() {
		return null;
	}
	
	@Override
	public boolean enableSmoothRender() {return true;}
}