package galaxyspace.systems.SolarSystem.moons.triton.dimension.sky;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import galaxyspace.GalaxySpace;
import galaxyspace.core.client.render.sky.SkyProviderBase;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldProviderSpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class SkyProviderTriton extends SkyProviderBase{

	private static final ResourceLocation neptuneTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/neptune.png");
        
	@Override
	protected void rendererSky(Tessellator tessellator, float f10, float ticks) 
	{
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        
        // Render uranus
        f10 = 15.0F;
        GL11.glScalef(0.6F, 0.6F, 0.6F);
        GL11.glRotatef(100.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(20F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(60F, 0.0F, 0.0F, 1.0F);        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.neptuneTexture);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
        tessellator.addVertexWithUV(f10, -100.0D, f10, 1, 1);
        tessellator.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
        tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
        tessellator.draw();

        float f = 0.7F;
        this.renderAtmo(tessellator, 0, 0F, f10 - 3, new Vector3(122 / 255.0F * f, 122 / 255.0F * f, 142 / 255.0F * f));
	
        long mod = ((WorldProviderSpace) this.mc.theWorld.provider).getDayLength() / 24000;
        long time = this.mc.thePlayer.getEntityWorld().getWorldTime() % ((WorldProviderSpace) this.mc.theWorld.provider).getDayLength();
		double k = (time / 8) / mod;
		
		
	
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.95F);
		
		if(k >= 280 && k < 1500) {
			k -= 280;
			k /= 50;

			tessellator.startDrawingQuads();
			tessellator.addVertex(-f10, -100.0D, f10);
			tessellator.addVertex(f10, -100.0D, f10);
			tessellator.addVertex(f10, -100.0D, -f10 + k);
			tessellator.addVertex(-f10, -100.0D, -f10 + k);
			tessellator.draw();
		}
		else if(k < 280)
		{
			tessellator.startDrawingQuads();
			tessellator.addVertex(-f10, -100.0D, f10);
			tessellator.addVertex(f10, -100.0D, f10);
			tessellator.addVertex(f10, -100.0D, -f10);
			tessellator.addVertex(-f10, -100.0D, -f10);
			tessellator.draw();
		}
		
		if(k >= 2000) {
			k -= 2000;
			k /= 50;

			tessellator.startDrawingQuads();
			tessellator.addVertex(-f10, -100.0D, -f10 + k);
			tessellator.addVertex(f10, -100.0D, -f10 + k);
			tessellator.addVertex(f10, -100.0D, -f10);
			tessellator.addVertex(-f10, -100.0D, -f10);
			tessellator.draw();
		}
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	@Override
	protected boolean enableBaseImages() {
		return true;
	}

	@Override
	protected float sunSize() {
		return 1.5F;
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