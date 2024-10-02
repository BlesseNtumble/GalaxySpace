package galaxyspace.core.client.gui.overlay;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.core.util.ClientUtil;
import micdoodle8.mods.galacticraft.core.util.ColorUtil;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.OxygenUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class OverlayDetectors {
	
	   private static Minecraft minecraft = FMLClientHandler.instance().getClient();

	    /**
	     * Render the GUI that displays oxygen level in tanks
	     */
	public static void renderPressureIndicator(int pressureLevel, boolean invalid, boolean right, boolean top) {
		final EntityPlayerSP player = minecraft.thePlayer;
		final ResourceLocation guiTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/gui.png");

		final ScaledResolution scaledresolution = ClientUtil.getScaledRes(minecraft, minecraft.displayWidth,
				minecraft.displayHeight);
		final int width = scaledresolution.getScaledWidth();
		final int height = scaledresolution.getScaledHeight();
		minecraft.entityRenderer.setupOverlayRendering();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(guiTexture);

		int minLeftX = 0;
		int maxLeftX = 0;
		int minRightX = 0;
		int maxRightX = 0;
		double bottomY = 0;
		double topY = 0;
		double zLevel = -190.0D;
		float texMod = 0.00390625F;

		if (top) {
			topY = 0;
		} else {
			topY = height - 70;
		}

		if (OxygenUtil.noAtmosphericCombustion(player.worldObj.provider))
			bottomY = topY + 58.5;
		else
			bottomY = topY + 1.5;

		if (right) {
			minLeftX = width - 49;
			maxLeftX = width - 40;
			minRightX = width - 59;
			maxRightX = width - 74;
		} else {
			minLeftX = 10;
			maxLeftX = 29;
			minRightX = 10;
			maxRightX = 49;
		}

	    if(pressureLevel > 48) pressureLevel = 48;
	    drawTexturedModalRect((float) minRightX, (float)bottomY, 49, 9, 0, 0, 64, 9, false, false, 256, 256);
	    drawTexturedModalRect((float) minRightX, (float)bottomY + 1, pressureLevel, 6, 0, 9, pressureLevel + 14, 6, false, false, 256, 256);
	    drawTexturedModalRect((float) minRightX, (float)bottomY + 3, 50, 9, 0, 15, 64, 9, false, false, 256, 256);


		if (invalid) {
			String value = GCCoreUtil.translate("gui.warning.atmosphericPressure");
			if (right)
				OverlayDetectors.minecraft.fontRenderer.drawString(value,
						minLeftX + 40 - OverlayDetectors.minecraft.fontRenderer.getStringWidth(value),
						(int) bottomY + 14 - OverlayDetectors.minecraft.fontRenderer.FONT_HEIGHT / 2 - 1,
						ColorUtil.to32BitColor(255, 255, 10, 10));
			else
				OverlayDetectors.minecraft.fontRenderer.drawString(value, minLeftX + 60,
						(int) bottomY + 14 - OverlayDetectors.minecraft.fontRenderer.FONT_HEIGHT / 2 - 1,
						ColorUtil.to32BitColor(255, 255, 10, 10));

		}

		GL11.glColor3f(1.0F, 1.0F, 1.0F);

		GL11.glPushMatrix();
		GL11.glPopMatrix();

	}
	    
	public static void renderRadiationIndicator(int radLevel, boolean invalid, boolean right, boolean top) {
		final EntityPlayerSP player = minecraft.thePlayer;
		final ResourceLocation guiTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/gui.png");

		final ScaledResolution scaledresolution = ClientUtil.getScaledRes(minecraft, minecraft.displayWidth,
				minecraft.displayHeight);
		final int width = scaledresolution.getScaledWidth();
		final int height = scaledresolution.getScaledHeight();
		minecraft.entityRenderer.setupOverlayRendering();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(guiTexture);

		int minLeftX = 0;
		int maxLeftX = 0;
		int minRightX = 0;
		int maxRightX = 0;
		double bottomY = 0;
		double topY = 0;
		double zLevel = -190.0D;
		float texMod = 0.00390625F;

		if (top) {
			topY = 0;
		} else {
			topY = height - 70;
		}

		if (OxygenUtil.noAtmosphericCombustion(player.worldObj.provider))
			bottomY = topY + 10.5;
		else
			bottomY = topY + 1.5;

		if (right) {
			minLeftX = width - 49;
			maxLeftX = width - 40;
			minRightX = width - 79;
			maxRightX = width - (OxygenUtil.noAtmosphericCombustion(player.worldObj.provider) ? 69 : 9);
		} else {
			minLeftX = 10;
			maxLeftX = 29;
			minRightX = 69;
			maxRightX = 1;
		}

		final Tessellator var9 = Tessellator.instance;
		var9.startDrawingQuads();
		var9.addVertexWithUV(maxRightX, bottomY + 56, zLevel, 65 * texMod, 56 * texMod);
		var9.addVertexWithUV(maxRightX + 8, bottomY + 56, zLevel, 73 * texMod, 56 * texMod);
		var9.addVertexWithUV(maxRightX + 8, bottomY, zLevel, 73 * texMod, 0 * texMod);
		var9.addVertexWithUV(maxRightX, bottomY, zLevel, 65 * texMod, 0 * texMod);
		var9.draw();

		var9.startDrawingQuads();
		var9.addVertexWithUV(maxRightX, bottomY + 56, zLevel, 73 * texMod, 56 * texMod);
		var9.addVertexWithUV(maxRightX + 8, bottomY + 56, zLevel, 81 * texMod, 56 * texMod);
		var9.addVertexWithUV(maxRightX + 8, bottomY + 56 - radLevel, zLevel, 81 * texMod, (56 - radLevel) * texMod);
		var9.addVertexWithUV(maxRightX, bottomY + 56 - radLevel, zLevel, 73 * texMod, (56 - radLevel) * texMod);
		var9.draw();

		if (invalid) {
			String value = GCCoreUtil.translate("gui.warning.solarRadiation");
			if (right)
				OverlayDetectors.minecraft.fontRenderer.drawString(value,
						minLeftX - 22 - OverlayDetectors.minecraft.fontRenderer.getStringWidth(value),
						(int) bottomY + 14 - OverlayDetectors.minecraft.fontRenderer.FONT_HEIGHT / 2 - 1,
						ColorUtil.to32BitColor(255, 255, 255, 10));
			else
				OverlayDetectors.minecraft.fontRenderer.drawString(value, minLeftX + 75,
						(int) bottomY + 7 - OverlayDetectors.minecraft.fontRenderer.FONT_HEIGHT / 2 - 1,
						ColorUtil.to32BitColor(255, 255, 255, 10));

		}

		GL11.glColor3f(1.0F, 1.0F, 1.0F);

		GL11.glPushMatrix();
		GL11.glPopMatrix();

	}

	public static void drawTexturedModalRect(float x, float y, float width, float height, float u, float v, float uWidth,
			float vHeight, boolean invertX, boolean invertY, float texSizeX, float texSizeY) {
		double zLevel = -190.0D;

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		float texModX = 1F / texSizeX;
		float texModY = 1F / texSizeY;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		float height0 = invertY ? 0 : vHeight;
		float height1 = invertY ? vHeight : 0;
		float width0 = invertX ? uWidth : 0;
		float width1 = invertX ? 0 : uWidth;
		tessellator.addVertexWithUV(x, y + height, zLevel, (u + width0) * texModX, (v + height0) * texModY);
		tessellator.addVertexWithUV(x + width, y + height, zLevel, (u + width1) * texModX, (v + height0) * texModY);
		tessellator.addVertexWithUV(x + width, y, zLevel, (u + width1) * texModX, (v + height1) * texModY);
		tessellator.addVertexWithUV(x, y, zLevel, (u + width0) * texModX, (v + height1) * texModY);
		tessellator.draw();
	}
}
