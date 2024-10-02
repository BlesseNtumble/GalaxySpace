package galaxyspace.systems.SolarSystem.moons.ganymede.dimension.sky;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import galaxyspace.GalaxySpace;
import galaxyspace.core.client.render.sky.SkyProviderBase;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldProviderSpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class SkyProviderGanymede extends SkyProviderBase{

	private static final ResourceLocation jupiterTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/jupiter.png");
    private static final ResourceLocation ioTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/io.png");
    private static final ResourceLocation europaTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/europa.png");
    private static final ResourceLocation callistoTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/callisto.png");

	@Override
	protected void rendererSky(Tessellator tessellator, float f10, float partialTicks) 
	{

		
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		
		// Render io
		f10 = 0.5F;
		GL11.glScalef(0.6F, 0.6F, 0.6F);			
		GL11.glRotatef(-30.0F, 0.0F, 0.0F, 1.0F);		
		GL11.glRotatef(190F, 1.0F, 0.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.ioTexture);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
		tessellator.addVertexWithUV(f10, -100.0D, f10, 1, 1);
		tessellator.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
		tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
		tessellator.draw();
				
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		
		// Render europa
		f10 = 1.5F;
		GL11.glScalef(0.6F, 0.6F, 0.6F);			
		GL11.glRotatef(30.0F, 0.0F, 0.0F, 1.0F);		
		GL11.glRotatef(170F, 1.0F, 0.0F, 0.0F);
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
		
		// Render callisto
		f10 = 1.0F;
		GL11.glScalef(0.6F, 0.6F, 0.6F);			
		GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);	
		GL11.glRotatef(150.0F, 1.0F, 0.0F, 0.0F);	
		GL11.glRotatef(this.mc.thePlayer.getEntityWorld().getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.callistoTexture);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
		tessellator.addVertexWithUV(f10, -100.0D, f10, 1, 1);
		tessellator.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
		tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
		tessellator.draw();

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);                
		
		GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.8F);
		tessellator.startDrawingQuads();
		tessellator.addVertex(-f10,  -100.0D, f10);
		tessellator.addVertex(f10, -100.0D, f10);
		tessellator.addVertex(f10, -100.0D, -f10 + 0.5);
		tessellator.addVertex(-f10, -100.0D, -f10 + 0.5);
		tessellator.draw();
		GL11.glDisable(GL11.GL_BLEND);

			
		GL11.glPopMatrix();			
		GL11.glPushMatrix();
		// Render jupiter
		f10 = 33.0F;
		GL11.glScalef(0.6F, 0.6F, 0.6F);
		//GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.1F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.jupiterTexture);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
		tessellator.addVertexWithUV(f10, -100.0D, f10, 1, 1);
		tessellator.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
		tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
		tessellator.draw();
		
		float f = 0.9F;
	    this.renderAtmo(tessellator, 0.0F, 0.0F, f10 - 4, new Vector3(120 / 255.0F * f, 110 / 255.0F * f, 120 / 255.0F * f));

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		f10 = 33.0F;
		long time = this.mc.thePlayer.getEntityWorld().getWorldTime() % ((WorldProviderSpace) this.mc.theWorld.provider).getDayLength();
		int k = (int) (time / 200);
		if(k >= 43 && k < 75) k = 43;
		//GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.8F);
		
		if(k <= 43)
		{		
			tessellator.startDrawingQuads();
			tessellator.addVertex(-k + 10, -100.0D, f10);
			tessellator.addVertex(f10, -100.0D, f10);
			tessellator.addVertex(f10, -100.0D, -f10);
			tessellator.addVertex(-k + 10, -100.0D, -f10);
			tessellator.draw();
		}
		else if(k >= 75)
		{
			tessellator.startDrawingQuads();
			tessellator.addVertex(-f10, -100.0D, f10);
			tessellator.addVertex(f10 - k + 75 , -100.0D, f10);
			tessellator.addVertex(f10 - k + 75, -100.0D, -f10);
			tessellator.addVertex(-f10, -100.0D, -f10);
			tessellator.draw();
		}
		if(k >= 230)
		{		
			tessellator.startDrawingQuads();
			tessellator.addVertex(-k + 230 + 35, -100.0D, f10);
			tessellator.addVertex(f10, -100.0D, f10);
			tessellator.addVertex(f10, -100.0D, -f10);
			tessellator.addVertex(-k + 230 + 35, -100.0D, -f10);
			tessellator.draw();
		}

		//GL11.glDisable(GL11.GL_BLEND);
		
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