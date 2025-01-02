package galaxyspace.systems.ACentauriSystem.planets.proximaB.dimension.sky;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import galaxyspace.GalaxySpace;
import galaxyspace.core.client.render.sky.SkyProviderBase;
import galaxyspace.core.world.gen.WorldProviderAdvancedSpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SkyProviderProximaB extends SkyProviderBase
{
	private ResourceLocation acentauri_a = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/aсentauri/centauri_a.png");
	private ResourceLocation acentauri_b = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/aсentauri/centauri_b.png");
	
	@Override
	protected void rendererSky(Tessellator tessellator, float f10, float partialTicks) {
		World world = mc.theWorld;
		int phase = ((WorldProviderAdvancedSpace)world.provider).getMoonPhase(world.getWorldTime());
		
		GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE, GL11.GL_ZERO);
        
		f10 = 0.8F;
		GL11.glScalef(0.6F, 0.6F, 0.6F);
		GL11.glRotatef(120F, 1.0F, 0.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F - Minecraft.getMinecraft().theWorld.getRainStrength(partialTicks));
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.acentauri_a);
		
		if (phase != 0 && phase != 6) {
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
			tessellator.addVertexWithUV(f10, -100.0D, f10, 1, 1);
			tessellator.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
			tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
			tessellator.draw();
		}
		
		f10 = 0.5F;
		GL11.glScalef(0.6F, 0.6F, 0.6F);
		GL11.glRotatef(2F, 0.0F, 0.0F, 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F - Minecraft.getMinecraft().theWorld.getRainStrength(partialTicks));
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.acentauri_b);
		if (phase != 0 && phase != 6) {
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
			tessellator.addVertexWithUV(f10, -100.0D, f10, 1, 1);
			tessellator.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
			tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
			tessellator.draw();
		}
		
		GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glRotatef(-1F, 0.0F, 0.0F, 1.0F);
        this.renderSunAura(tessellator, 0.0F, 0.9F);
       // GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F);
        //GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
       // this.renderSunAura(tessellator, 0.0F, 0.5F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
	}

	@Override
	protected boolean enableBaseImages() {
		return true;
	}

	@Override
	protected float sunSize() {
		return 8.5F;
	}

	@Override
	protected boolean enableStar() {
		return true;
	}

	@Override
	protected ResourceLocation sunImage() {
		return new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/proxima/proxima.png");
	}

	@Override
	protected int modeLight() {
		switch(((WorldProviderAdvancedSpace)mc.theWorld.provider).getMoonPhase(mc.theWorld.getWorldTime()))
		{
			case 0:
			case 6:	return 2;
			default: return 0;
		}
		
	}

	@Override
	protected Vector3 colorSunAura() {		
		return new Vector3(255, 140, 100);
	}

	@Override
	protected Vector3 getAtmosphereColor() {
		return null;
	}
	
	@Override
	public int addSizeAura() { return 35; }
	
	@Override
	public boolean enableLargeSunAura() {return true;}
}