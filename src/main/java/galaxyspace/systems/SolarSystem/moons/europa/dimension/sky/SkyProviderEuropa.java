package galaxyspace.systems.SolarSystem.moons.europa.dimension.sky;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import galaxyspace.GalaxySpace;
import galaxyspace.core.client.render.sky.SkyProviderBase;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldProviderSpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class SkyProviderEuropa extends SkyProviderBase{

	private static final ResourceLocation jupiterTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/jupiter.png");
	private static final ResourceLocation ioTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/io.png");
    private static final ResourceLocation ganymedeTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/ganymede.png");
    private static final ResourceLocation callistoTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/callisto.png");
    boolean test = false;
    int wait = 5;
	
	@Override
	protected void rendererSky(Tessellator tessellator, float f10, float partialTicks)
	{
		long daylength = ((WorldProviderSpace) this.mc.theWorld.provider).getDayLength();
		
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		
		
		// Render ganymede
		f10 = 1.5F;
		GL11.glScalef(0.6F, 0.6F, 0.6F);			
		GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);		
		GL11.glRotatef(this.getCelestialAngle(daylength / 2), 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(100F, 1.0F, 0.0F, 0.0F);

		
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
		GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);	
		GL11.glRotatef(this.getCelestialAngle(daylength * 2), 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(100F, 1.0F, 0.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.callistoTexture);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
		tessellator.addVertexWithUV(f10, -100.0D, f10, 1, 1);
		tessellator.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
		tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
		tessellator.draw();
		
        GL11.glPopMatrix();
        float x = (float) (40 * (Math.sin((this.mc.theWorld.getCelestialAngle(this.ticks) * 360.0F) / 10.0F)));
        //GalaxySpace.debug(x + "");
        
        if(wait == 0 && (x >= 39.99F || x <= -39.99F)) 
        {
        	wait = 150;
        	test = !test;
        }
        
        if(wait > 0) wait--;
        if(!test) {
			GL11.glPushMatrix();
	
	        // Render jupiter
	        f10 = 80.0F;
	        GL11.glScalef(0.6F, 0.6F, 0.6F);
	        GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
	        GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
	        FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.jupiterTexture);
	        tessellator.startDrawingQuads();
	        tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
	        tessellator.addVertexWithUV(f10, -100.0D, f10, 1, 1);
	        tessellator.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
	        tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
	        tessellator.draw();
	        
	        float f = 0.9F;
	        this.renderAtmo(tessellator, 0.0F, 0.0F, f10 - 8, new Vector3(120 / 255.0F * f, 110 / 255.0F * f, 120 / 255.0F * f));
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
			
	        
			GL11.glPopMatrix();
        }
        GL11.glPushMatrix();      
		
		// Render io
		f10 = 2.0F;
		GL11.glRotatef(x, 0.0F, 1.0F, 0.0F);
		// GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(100F, 1.0F, 0.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.ioTexture);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
		tessellator.addVertexWithUV(f10, -100.0D, f10, 1, 1);
		tessellator.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
		tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
		tessellator.draw();
		
		GL11.glPopMatrix();
		if(test) {
			GL11.glPushMatrix();
	
	        // Render jupiter
	        f10 = 80.0F;
	        GL11.glScalef(0.6F, 0.6F, 0.6F);
	        GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
	        GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
	        FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.jupiterTexture);
	        tessellator.startDrawingQuads();
	        tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
	        tessellator.addVertexWithUV(f10, -100.0D, f10, 1, 1);
	        tessellator.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
	        tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
	        tessellator.draw();
	        
	        float f = 0.9F;
	        this.renderAtmo(tessellator, 0.0F, 0.0F, f10 - 8, new Vector3(120 / 255.0F * f, 110 / 255.0F * f, 120 / 255.0F * f));
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
			
	        
			GL11.glPopMatrix();
        }
        
		GL11.glPushMatrix();
	}

	@Override
	protected boolean enableBaseImages() {
		return true;
	}

	@Override
	protected float sunSize() {
		return 5.0F;
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