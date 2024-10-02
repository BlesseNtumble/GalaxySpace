package galaxyspace.core.client.gui.overlay;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.core.client.gui.overlay.Overlay;
import micdoodle8.mods.galacticraft.core.util.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class OverlayGUIPlus extends Overlay
{
    private static Minecraft minecraft = FMLClientHandler.instance().getClient();

    /**
     * Render the GUI when player is in inventory
     */
    public static void renderSpaceshipOverlay()
    { 
    	final EntityPlayerSP player = minecraft.thePlayer;
    	final ResourceLocation guiTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/RocketsGUI.png");

        final ScaledResolution scaledresolution = ClientUtil.getScaledRes(OverlayGUIPlus.minecraft, OverlayGUIPlus.minecraft.displayWidth, OverlayGUIPlus.minecraft.displayHeight);
        scaledresolution.getScaledWidth();
        final int height = scaledresolution.getScaledHeight();
        OverlayGUIPlus.minecraft.entityRenderer.setupOverlayRendering();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(guiTexture);

        float var1 = 0F;
        float var2 = height / 2 - 170 / 2;
        float var3 = 0.0F;
        float var3b = 0.0F;
        float var4 = 0.0F;
        float var5 = 1.0F;
        float var6 = 1.0F;
        float var7 = 1.0F;
        float var8 = 1.0F;
        float sizeScale = 0.65F;

        final Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV(var1 + 0, var2 + 242.0F * sizeScale, 0.0, (var3 + 0) * var7, (var4 + var6) * var8);
        var9.addVertexWithUV(var1 + 33.0F * sizeScale, var2 + 242.0F * sizeScale, 0.0, (var3 + var5) * var7, (var4 + var6) * var8);
        var9.addVertexWithUV(var1 + 33.0F * sizeScale, var2 + 0, 0.0, (var3 + var5) * var7, (var4 + 0) * var8);
        var9.addVertexWithUV(var1 + 0, var2 + 0, 0.0, (var3 + 0) * var7, (var4 + 0) * var8);
        var9.draw();

        GL11.glColor3f(1.0F, 1.0F, 1.0F);

        GL11.glPushMatrix();
        GL11.glPopMatrix();

    }
}
