/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.IIcon
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.model.AdvancedModelLoader
 *  net.minecraftforge.client.model.IModelCustom
 *  org.lwjgl.opengl.GL11
 */
package galaxyspace.systems.SolarSystem.planets.overworld.render.tile;

import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityHydroponicFarm;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class TileEntityHydroponicFarmRenderer
extends TileEntitySpecialRenderer {
    private static final IModelCustom model = AdvancedModelLoader.loadModel((ResourceLocation)new ResourceLocation(GalaxySpace.ASSET_PREFIX, "models/tepl.obj"));
    private static ResourceLocation texture1 = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/waterTower_1.png");
    private static ResourceLocation texture2 = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/waterTower_2.png");
    private Block block = null;

    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
        TileEntityHydroponicFarm tile = (TileEntityHydroponicFarm)tileEntity;
        this.block = tile.getPlant();
        GL11.glPushMatrix();
        GL11.glPushMatrix();
        GL11.glTranslated((double)(x + 0.5), (double)(y - 0.3), (double)(z + 0.5));
        double factor = 0.65;
        GL11.glScaled((double)factor, (double)factor, (double)factor);
        int wheatState = 70;
        if (this.block != null) {
            this.renderWheat(this.block, tile.getMetaPlant(), 0.0 / factor - 0.5, 0.65 / factor, 0.0 / factor - 0.5);
        }
        GL11.glPopMatrix();
        GL11.glEnable((int)32826);
        GL11.glTranslated((double)(x + 0.5), (double)(y - 1.0), (double)(z + 0.5));
        GL11.glScalef((float)0.01f, (float)0.01f, (float)0.01f);
        if (tile.blockMetadata == 1) {
            this.bindTexture(texture2);
        } else {
            this.bindTexture(texture1);
        }
        model.renderPart("ferma_2");
        GL11.glPopMatrix();
    }

    protected void renderWheat(Block block, int meta, double x, double y, double z) {
        Tessellator tessellator = Tessellator.instance;
        IIcon iicon = block.getIcon(0, meta);
        ResourceLocation resourcelocation = Minecraft.getMinecraft().renderEngine.getResourceLocation(0);
        Minecraft.getMinecraft().renderEngine.bindTexture(resourcelocation);
        double d3 = iicon.getMinU();
        double d4 = iicon.getMinV();
        double d5 = iicon.getMaxU();
        double d6 = iicon.getMaxV();
        double d7 = x + 0.5 - 0.25;
        double d8 = x + 0.5 + 0.25;
        double d9 = z + 0.5 - 0.5;
        double d10 = z + 0.5 + 0.5;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(d7, y + 1.0, d9, d3, d4);
        tessellator.addVertexWithUV(d7, y + 0.0, d9, d3, d6);
        tessellator.addVertexWithUV(d7, y + 0.0, d10, d5, d6);
        tessellator.addVertexWithUV(d7, y + 1.0, d10, d5, d4);
        tessellator.addVertexWithUV(d7, y + 1.0, d10, d3, d4);
        tessellator.addVertexWithUV(d7, y + 0.0, d10, d3, d6);
        tessellator.addVertexWithUV(d7, y + 0.0, d9, d5, d6);
        tessellator.addVertexWithUV(d7, y + 1.0, d9, d5, d4);
        tessellator.addVertexWithUV(d8, y + 1.0, d10, d3, d4);
        tessellator.addVertexWithUV(d8, y + 0.0, d10, d3, d6);
        tessellator.addVertexWithUV(d8, y + 0.0, d9, d5, d6);
        tessellator.addVertexWithUV(d8, y + 1.0, d9, d5, d4);
        tessellator.addVertexWithUV(d8, y + 1.0, d9, d3, d4);
        tessellator.addVertexWithUV(d8, y + 0.0, d9, d3, d6);
        tessellator.addVertexWithUV(d8, y + 0.0, d10, d5, d6);
        tessellator.addVertexWithUV(d8, y + 1.0, d10, d5, d4);
        d7 = x + 0.5 - 0.5;
        d8 = x + 0.5 + 0.5;
        d9 = z + 0.5 - 0.25;
        d10 = z + 0.5 + 0.25;
        tessellator.addVertexWithUV(d7, y + 1.0, d9, d3, d4);
        tessellator.addVertexWithUV(d7, y + 0.0, d9, d3, d6);
        tessellator.addVertexWithUV(d8, y + 0.0, d9, d5, d6);
        tessellator.addVertexWithUV(d8, y + 1.0, d9, d5, d4);
        tessellator.addVertexWithUV(d8, y + 1.0, d9, d3, d4);
        tessellator.addVertexWithUV(d8, y + 0.0, d9, d3, d6);
        tessellator.addVertexWithUV(d7, y + 0.0, d9, d5, d6);
        tessellator.addVertexWithUV(d7, y + 1.0, d9, d5, d4);
        tessellator.addVertexWithUV(d8, y + 1.0, d10, d3, d4);
        tessellator.addVertexWithUV(d8, y + 0.0, d10, d3, d6);
        tessellator.addVertexWithUV(d7, y + 0.0, d10, d5, d6);
        tessellator.addVertexWithUV(d7, y + 1.0, d10, d5, d4);
        tessellator.addVertexWithUV(d7, y + 1.0, d10, d3, d4);
        tessellator.addVertexWithUV(d7, y + 0.0, d10, d3, d6);
        tessellator.addVertexWithUV(d8, y + 0.0, d10, d5, d6);
        tessellator.addVertexWithUV(d8, y + 1.0, d10, d5, d4);
        tessellator.draw();
    }
}

