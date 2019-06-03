package galaxyspace.systems.SolarSystem.moons.titan.dimension.sky;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.client.FMLClientHandler;

public class CloudProviderTitan extends IRenderHandler
{
	private static Minecraft mc;
	private static final ResourceLocation locationCloudsPng = new ResourceLocation("textures/environment/clouds.png");
	public static int cloudTickCounter = 1;
	private int cloudMode = mc.gameSettings.shouldRenderClouds();
	
	private int texW;
    private int texH;
    
    private static final float PX_SIZE = 1 / 256F;
	
	public CloudProviderTitan()
	{
		mc = FMLClientHandler.instance().getClient();
	}
	
	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc)
	{
		Entity entity = mc.getRenderViewEntity();
		 
		double totalOffset = cloudTickCounter + partialTicks;

		double x = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks + totalOffset * 0.03;
		double y = mc.world.provider.getCloudHeight()
				- (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks) + 0.33;
		double z = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;

		int scale = getScale();
		
		if (cloudMode == 2)
			z += 0.33 * scale;
		
		int offU = fullCoord(x, scale);
        int offV = fullCoord(z, scale);
        
        GlStateManager.pushMatrix();
        GlStateManager.translate((offU * scale) - x, y, (offV * scale) - z);
        
        mc.renderEngine.bindTexture(locationCloudsPng);
        texW = GlStateManager.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);
        texH = GlStateManager.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT);
        
        offU = offU % texW;
        offV = offV % texH;
        
        GlStateManager.matrixMode(GL11.GL_TEXTURE);
        GlStateManager.translate(offU * PX_SIZE, offV * PX_SIZE, 0);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        
        GlStateManager.disableCull();
        
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        
        // Color multiplier.
        Vec3d color = mc.world.getCloudColour(partialTicks);
        float r = (float) color.x;
        float g = (float) color.y;
        float b = (float) color.z;

        if (mc.gameSettings.anaglyph)
        {
            float tempR = r * 0.3F + g * 0.59F + b * 0.11F;
            float tempG = r * 0.3F + g * 0.7F;
            float tempB = r * 0.3F + b * 0.7F;
            r = tempR;
            g = tempG;
            b = tempB;
        }
	}
	
	private int getScale() {
		return cloudMode == 2 ? 12 : 8;
	}
	
	private int fullCoord(double coord, int scale)
    {   // Corrects misalignment of UV offset when on negative coords.
        return ((int) coord / scale) - (coord < 0 ? 1 : 0);
    }
}
