package galaxyspace.systems.SolarSystem.satellites.mars.dimension.sky;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import galaxyspace.GalaxySpace;
import galaxyspace.core.client.render.sky.SkyProviderBase;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldProviderSpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class SkyProviderMarsSS extends SkyProviderBase{

	private static final ResourceLocation marsTexture = new ResourceLocation(GalacticraftCore.ASSET_PREFIX, "textures/gui/celestialbodies/mars.png");
	private static final ResourceLocation overworldTexture = new ResourceLocation(GalacticraftCore.ASSET_PREFIX, "textures/gui/celestialbodies/earth.png");
	public float spinDeltaPerTick;
	
	@Override
	protected void rendererSky(Tessellator tessellator, float f10, float partialTicks) {
		
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		if (!ClientProxyCore.overworldTextureRequestSent)
		{
			GalacticraftCore.packetPipeline.sendToServer(new PacketSimple(PacketSimple.EnumSimplePacket.S_REQUEST_OVERWORLD_IMAGE, new Object[] {}));
			ClientProxyCore.overworldTextureRequestSent = true;
		}
		 // HOME:
        f10 = 0.8F;
        //final float earthRotation = (float) (this.mc.theWorld.getSpawnPoint().posZ - mc.thePlayer.posZ - 20000) * 0.01F;
        GL11.glScalef(0.6F, 0.6F, 0.6F);
        GL11.glRotatef(-40.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(80.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(this.mc.theWorld.getCelestialAngle(this.ticks) * 360.0F, 1.0F, 0.0F, 0.0F);

        if (ClientProxyCore.overworldTexturesValid)
        {
        	GL11.glBindTexture(GL11.GL_TEXTURE_2D, ClientProxyCore.overworldTextureClient.getGlTextureId());
        }
        else
        {
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(overworldTexture);
        }
        //this.mc.theWorld.getMoonPhase();
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
        tessellator.addVertexWithUV(f10, -100.0D, f10, 1, 1);
        tessellator.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
        tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
        tessellator.draw();

        float f = 0.59F;
        this.renderAtmo(tessellator, 0, 0F, f10, new Vector3(88 / 255.0F * f, 122 / 255.0F * f, 180 / 255.0F * f));
        GL11.glPopMatrix();
		GL11.glPushMatrix();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		//MARS
		f10 = 150.0F;
		
		GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
	    GL11.glRotatef(0.0F, 1.0F, 0.0F, 0.0F);
	    FMLClientHandler.instance().getClient().renderEngine.bindTexture(marsTexture);
	    
	    tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
        tessellator.addVertexWithUV(f10, -100.0D, f10, 1, 1);
        tessellator.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
        tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
        tessellator.draw();

        f = 0.39F;
        this.renderAtmo(tessellator, 0, 0F, f10, new Vector3(122 / 255.0F * f, 122 / 255.0F * f, 142 / 255.0F * f));
		
        double mod = ((WorldProviderSpace) this.mc.theWorld.provider).getDayLength() / 24000;
        long time = this.mc.thePlayer.getEntityWorld().getWorldTime() % ((WorldProviderSpace) this.mc.theWorld.provider).getDayLength();
		double k = (time / 8) / mod;
		
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.8F);
		GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
		
		if(k >= 0 && k < 300) {
			tessellator.startDrawingQuads();
			tessellator.addVertex(-f10, -100.0D, f10);
			tessellator.addVertex(f10 - k, -100.0D, f10);
			tessellator.addVertex(f10 - k, -100.0D, -f10);
			tessellator.addVertex(-f10, -100.0D, -f10);
			tessellator.draw();
		}
		else if(k >= 1200 && k <= 1500)
		{
			
			k -= 1200;
			
			tessellator.startDrawingQuads();
			tessellator.addVertex(150 - k, -100.0D, f10);
			tessellator.addVertex(f10, -100.0D, f10);
			tessellator.addVertex(f10, -100.0D, -f10);
			tessellator.addVertex(150 - k, -100.0D, -f10);
			tessellator.draw();
		}
		else if(k > 1500)
		{
			tessellator.startDrawingQuads();
			tessellator.addVertex(-f10, -100.0D, f10);
			tessellator.addVertex(f10, -100.0D, f10);
			tessellator.addVertex(f10, -100.0D, -f10);
			tessellator.addVertex(-f10, -100.0D, -f10);
			tessellator.draw();
		}
	
	}

	@Override
	protected int modeLight() {
		return 1;
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
		return new Vector3(150, 150, 150);
	}

	@Override
	protected Vector3 getAtmosphereColor() {
		return null;
	}
	
	@Override
	public boolean enableSmoothRender() {return true;}
}
