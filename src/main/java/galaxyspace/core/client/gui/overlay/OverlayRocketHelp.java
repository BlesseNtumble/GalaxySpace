package galaxyspace.core.client.gui.overlay;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.core.util.ClientUtil;
import micdoodle8.mods.galacticraft.core.util.ColorUtil;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class OverlayRocketHelp {

	private static Minecraft minecraft = FMLClientHandler.instance().getClient();
	static int i = 0;
	
	public static void renderSpaceshipOverlay()
    {
		
		final ResourceLocation guiTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialselection2.png");
		final ScaledResolution scaledresolution = ClientUtil.getScaledRes(minecraft, minecraft.displayWidth, minecraft.displayHeight);
        final int width = scaledresolution.getScaledWidth();
        final int height = scaledresolution.getScaledHeight();
        minecraft.entityRenderer.setupOverlayRendering();

        
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(guiTexture);
        GL11.glColor4f(0.0F, 0.6F, 1.0F, 1);
        float x = 0F;
        float y = 0;
        float xSize = 350.0F;
        float ySize = 10.0F;
        
        float var3 = 0.0F;
        
        float var3b = 0.0F;
        float var4 = 0.64F;
        
        float var5 = 1.0F;
        float var6 = 0.1F;
        
        float var7 = 0.8F; // �� X
        float var8 = 0.71F; // �� Y
        
        float sizeScale = 1.0F;

        draw(x, y, xSize, ySize, sizeScale, var3, var3b, var4, var5, var6, var7, var8);
        
        y += 10;
        ySize = 40.0F;
        var4 = 0.9F;
        var8 = 1.0F; // �� Y
        
        draw(x, y, xSize, ySize, sizeScale, var3, var3b, var4, var5, var6, var7, var8);
        String info = "Info";
        minecraft.fontRenderer.drawString(info, 5, 1, ColorUtil.to32BitColor(255, 150, 200, 255));
        
        if(minecraft.thePlayer.ticksExisted % 200 == 0) i = minecraft.theWorld.rand.nextInt(5);
        info = GCCoreUtil.translate("gui.message.info" + i + ".name");
        minecraft.fontRenderer.drawString(info, 5, 10, ColorUtil.to32BitColor(255, 255, 255, 255));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
	
	private static void draw(float x, float y, float xSize, float ySize, float sizeScale, float var3, float var3b, float var4, float var5, float var6, float var7, float var8)
	{
		final Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV(x + 0, y + ySize * sizeScale, 0.0, (var3 + 0) * var7, (var4 + var6) * var8);
        var9.addVertexWithUV(x + xSize * sizeScale, y + ySize * sizeScale, 0.0, (var3 + var5) * var7, (var4 + var6) * var8);
        var9.addVertexWithUV(x + xSize * sizeScale, y + 0, 0.0, (var3 + var5) * var7, (var4 + 0) * var8);
        var9.addVertexWithUV(x + 0, y + 0, 0.0, (var3 + 0) * var7, (var4 + 0) * var8);
        var9.draw();
	}
}
