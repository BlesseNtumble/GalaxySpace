package galaxyspace.systems.SolarSystem.moons.ganymede.dimension.sky;

import org.lwjgl.opengl.GL11;

import asmodeuscore.api.dimension.IAdvancedSpace.StarColor;
import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;

public class SkyProviderGanymede extends SkyProviderBase
{

	private static final ResourceLocation jupiterTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/jupiter.png");
	private static final ResourceLocation ioTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/io.png");
    private static final ResourceLocation europaTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/europa.png");
    private static final ResourceLocation callistoTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/callisto.png");
    boolean test = false;
    int wait = 5;
    
	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder worldRenderer, float f10, float ticks) {
		
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		
		f10 = 1.8F;
		GL11.glScalef(0.8F, 0.8F, 0.8F);
		GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);		
		GL11.glRotatef(this.mc.world.getCelestialAngle(this.ticks) * 360.0F / 2, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(100F, 1.0F, 0.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.callistoTexture);
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		worldRenderer.pos(-f10, -100.0D, f10).tex(0, 1.0).endVertex();
		worldRenderer.pos(f10, -100.0D, f10).tex(1.0, 1.0).endVertex();
		worldRenderer.pos(f10, -100.0D, -f10).tex(1.0, 0).endVertex();
		worldRenderer.pos(-f10, -100.0D, -f10).tex(0, 0).endVertex();
		tessellator.draw();        

        GL11.glPopMatrix();
        

        if(!test) {
        	GL11.glPushMatrix();
			f10 = 40.0F;
			GL11.glScalef(0.8F, 0.8F, 0.8F);
			GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.jupiterTexture);
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			worldRenderer.pos(-f10, -100.0D, f10).tex(0, 1.0).endVertex();
			worldRenderer.pos(f10, -100.0D, f10).tex(1.0, 1.0).endVertex();
			worldRenderer.pos(f10, -100.0D, -f10).tex(1.0, 0).endVertex();
			worldRenderer.pos(-f10, -100.0D, -f10).tex(0, 0).endVertex();
			tessellator.draw();
        
			float f = 0.9F;
			this.renderAtmo(tessellator, 0.0F, 0.0F, f10 - 7, new Vector3(120 / 255.0F * f, 110 / 255.0F * f, 120 / 255.0F * f));
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glPopMatrix();   
        }
		     
        float x = (float) (25 * (Math.sin((this.mc.world.getCelestialAngle(this.ticks) * 360.0F) / 40.0F)));

        if(wait == 0 && (x >= 25F || x <= -25F)) 
        {
        	wait = 150;
        	test = !test;
        }
        if(wait > 0) wait--;
        
        GL11.glPushMatrix();   
        
        f10 = 1.0F;
		GL11.glScalef(0.8F, 0.8F, 0.8F);
		GL11.glRotatef(x, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(100F, 1.0F, 0.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.ioTexture);
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		worldRenderer.pos(-f10, -100.0D, f10).tex(0, 1.0).endVertex();
		worldRenderer.pos(f10, -100.0D, f10).tex(1.0, 1.0).endVertex();
		worldRenderer.pos(f10, -100.0D, -f10).tex(1.0, 0).endVertex();
		worldRenderer.pos(-f10, -100.0D, -f10).tex(0, 0).endVertex();
		tessellator.draw();
		
		GL11.glPopMatrix(); 
		
		GL11.glPushMatrix();

		f10 = 1.5F;
		GL11.glScalef(0.8F, 0.8F, 0.8F);
		GL11.glRotatef(x + 15, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(85F, 1.0F, 0.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.europaTexture);
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		worldRenderer.pos(-f10, -100.0D, f10).tex(0, 1.0).endVertex();
		worldRenderer.pos(f10, -100.0D, f10).tex(1.0, 1.0).endVertex();
		worldRenderer.pos(f10, -100.0D, -f10).tex(1.0, 0).endVertex();
		worldRenderer.pos(-f10, -100.0D, -f10).tex(0, 0).endVertex();
		tessellator.draw();

		GL11.glPopMatrix();
		
		if(test)
		{
			GL11.glPushMatrix();
			f10 = 40.0F;
			GL11.glScalef(0.8F, 0.8F, 0.8F);
			GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.jupiterTexture);
			worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			worldRenderer.pos(-f10, -100.0D, f10).tex(0, 1.0).endVertex();
			worldRenderer.pos(f10, -100.0D, f10).tex(1.0, 1.0).endVertex();
			worldRenderer.pos(f10, -100.0D, -f10).tex(1.0, 0).endVertex();
			worldRenderer.pos(-f10, -100.0D, -f10).tex(0, 0).endVertex();
			tessellator.draw();
        
			float f = 0.9F;
			this.renderAtmo(tessellator, 0.0F, 0.0F, f10 - 7, new Vector3(120 / 255.0F * f, 110 / 255.0F * f, 120 / 255.0F * f));
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