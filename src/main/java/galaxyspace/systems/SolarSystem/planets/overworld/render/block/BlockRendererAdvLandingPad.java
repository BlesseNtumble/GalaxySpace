/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
 *  micdoodle8.mods.galacticraft.api.tile.IFuelDock
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockHopper
 *  net.minecraft.client.renderer.EntityRenderer
 *  net.minecraft.client.renderer.RenderBlocks
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.IBlockAccess
 *  org.lwjgl.opengl.GL11
 */
package galaxyspace.systems.SolarSystem.planets.overworld.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockAdvLandingPadFull;
import micdoodle8.mods.galacticraft.api.tile.IFuelDock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class BlockRendererAdvLandingPad
implements ISimpleBlockRenderingHandler {
    final int renderID;

    public BlockRendererAdvLandingPad(int var1) {
        this.renderID = var1;
    }

    public boolean renderWorldBlock(IBlockAccess var1, int var2, int var3, int var4, Block var5, int var6, RenderBlocks var7) {
        this.renderBlockLandingPad(var7, var5, var1, var2, var3, var4);
        return true;
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    public int getRenderId() {
        return this.renderID;
    }

    public static void renderInvNormalBlock(RenderBlocks var0, Block var1, int var2) {
        Tessellator var3 = Tessellator.instance;
        GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        var0.setRenderBounds((double)0.15f, (double)0.15f, (double)0.15f, (double)0.85f, (double)0.85f, (double)0.85f);
        var3.startDrawingQuads();
        var3.setNormal(0.0f, -0.8f, 0.0f);
        var0.renderFaceYNeg(var1, 0.0, 0.0, 0.0, var1.getIcon(0, var2));
        var3.draw();
        var3.startDrawingQuads();
        var3.setNormal(0.0f, 0.8f, 0.0f);
        var0.renderFaceYPos(var1, 0.0, 0.0, 0.0, var1.getIcon(1, var2));
        var3.draw();
        var3.startDrawingQuads();
        var3.setNormal(0.0f, 0.0f, -0.8f);
        var0.renderFaceXPos(var1, 0.0, 0.0, 0.0, var1.getIcon(2, var2));
        var3.draw();
        var3.startDrawingQuads();
        var3.setNormal(0.0f, 0.0f, 0.8f);
        var0.renderFaceXNeg(var1, 0.0, 0.0, 0.0, var1.getIcon(3, var2));
        var3.draw();
        var3.startDrawingQuads();
        var3.setNormal(-0.8f, 0.0f, 0.0f);
        var0.renderFaceZNeg(var1, 0.0, 0.0, 0.0, var1.getIcon(4, var2));
        var3.draw();
        var3.startDrawingQuads();
        var3.setNormal(0.8f, 0.0f, 0.0f);
        var0.renderFaceZPos(var1, 0.0, 0.0, 0.0, var1.getIcon(5, var2));
        var3.draw();
    }

    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        BlockRendererAdvLandingPad.renderInvNormalBlock(renderer, block, metadata);
    }

    public void renderBlockLandingPad(RenderBlocks renderBlocks, Block par1Block, IBlockAccess block, int x, int y, int z) {
        IFuelDock landingPad;
        renderBlocks.setRenderBounds(-2.0, 0.0, -2.0, 3.0, (double)0.2f, 3.0);
        renderBlocks.renderStandardBlock(par1Block, x, y, z);
        if (block.getBlockMetadata(x, y, z) == 0) {
            renderBlocks.setRenderBounds((double)-1.3f, (double)0.2f, (double)-1.3f, (double)2.3f, (double)0.3f, (double)2.3f);
            renderBlocks.renderStandardBlock(par1Block, x, y, z);
            renderBlocks.setRenderBounds(-0.5, (double)0.3f, -0.5, 1.5, (double)0.4f, 1.5);
            renderBlocks.renderStandardBlock(par1Block, x, y, z);
        }
        if ((landingPad = (IFuelDock)block.getTileEntity(x, y, z)) != null) {
            if (landingPad.isBlockAttachable(block, x + 3, y, z - 2)) {
                renderBlocks.setRenderBounds(2.5, (double)0.2f, (double)-1.9f, 3.0, (double)0.901f, (double)-1.1f);
                renderBlocks.renderStandardBlock(par1Block, x, y, z);
            }
            if (landingPad.isBlockAttachable(block, x + 3, y, z - 1)) {
                renderBlocks.setRenderBounds(2.5, (double)0.2f, (double)-0.9f, 3.0, (double)0.901f, (double)-0.1f);
                renderBlocks.renderStandardBlock(par1Block, x, y, z);
            }
            if (landingPad.isBlockAttachable(block, x + 3, y, z)) {
                renderBlocks.setRenderBounds(2.5, (double)0.2f, (double)0.1f, 3.0, (double)0.901f, (double)0.9f);
                renderBlocks.renderStandardBlock(par1Block, x, y, z);
            }
            if (landingPad.isBlockAttachable(block, x + 3, y, z + 1)) {
                renderBlocks.setRenderBounds(2.5, (double)0.2f, (double)1.1f, 3.0, (double)0.901f, (double)1.9f);
                renderBlocks.renderStandardBlock(par1Block, x, y, z);
            }
            if (landingPad.isBlockAttachable(block, x + 3, y, z + 2)) {
                renderBlocks.setRenderBounds(2.5, (double)0.2f, (double)2.1f, 3.0, (double)0.901f, (double)2.9f);
                renderBlocks.renderStandardBlock(par1Block, x, y, z);
            }
            if (landingPad.isBlockAttachable(block, x - 3, y, z - 2)) {
                renderBlocks.setRenderBounds(-2.0, (double)0.2f, (double)-1.9f, -1.5, (double)0.901f, (double)-1.1f);
                renderBlocks.renderStandardBlock(par1Block, x, y, z);
            }
            if (landingPad.isBlockAttachable(block, x - 3, y, z - 1)) {
                renderBlocks.setRenderBounds(-2.0, (double)0.2f, (double)-0.9f, -1.5, (double)0.901f, (double)-0.1f);
                renderBlocks.renderStandardBlock(par1Block, x, y, z);
            }
            if (landingPad.isBlockAttachable(block, x - 3, y, z)) {
                renderBlocks.setRenderBounds(-2.0, (double)0.2f, (double)0.1f, -1.5, (double)0.901f, (double)0.9f);
                renderBlocks.renderStandardBlock(par1Block, x, y, z);
            }
            if (landingPad.isBlockAttachable(block, x - 3, y, z + 1)) {
                renderBlocks.setRenderBounds(-2.0, (double)0.2f, (double)1.1f, -1.5, (double)0.901f, (double)1.9f);
                renderBlocks.renderStandardBlock(par1Block, x, y, z);
            }
            if (landingPad.isBlockAttachable(block, x - 3, y, z + 2)) {
                renderBlocks.setRenderBounds(-2.0, (double)0.2f, (double)2.1f, -1.5, (double)0.901f, (double)2.9f);
                renderBlocks.renderStandardBlock(par1Block, x, y, z);
            }
            if (landingPad.isBlockAttachable(block, x + 2, y, z - 3)) {
                renderBlocks.setRenderBounds((double)2.1f, (double)0.2f, -2.0, (double)2.9f, (double)0.901f, -1.5);
                renderBlocks.renderStandardBlock(par1Block, x, y, z);
            }
            if (landingPad.isBlockAttachable(block, x + 1, y, z - 3)) {
                renderBlocks.setRenderBounds((double)1.1f, (double)0.2f, -2.0, (double)1.9f, (double)0.901f, -1.5);
                renderBlocks.renderStandardBlock(par1Block, x, y, z);
            }
            if (landingPad.isBlockAttachable(block, x, y, z - 3)) {
                renderBlocks.setRenderBounds((double)0.1f, (double)0.2f, -2.0, (double)0.9f, (double)0.901f, -1.5);
                renderBlocks.renderStandardBlock(par1Block, x, y, z);
            }
            if (landingPad.isBlockAttachable(block, x - 1, y, z - 3)) {
                renderBlocks.setRenderBounds((double)-0.9f, (double)0.2f, -2.0, (double)-0.1f, (double)0.901f, -1.5);
                renderBlocks.renderStandardBlock(par1Block, x, y, z);
            }
            if (landingPad.isBlockAttachable(block, x - 2, y, z - 3)) {
                renderBlocks.setRenderBounds((double)-1.9f, (double)0.2f, -2.0, (double)-1.1f, (double)0.901f, -1.5);
                renderBlocks.renderStandardBlock(par1Block, x, y, z);
            }
            if (landingPad.isBlockAttachable(block, x + 2, y, z + 3)) {
                renderBlocks.setRenderBounds((double)2.1f, (double)0.2f, 2.5, (double)2.9f, (double)0.901f, 3.0);
                renderBlocks.renderStandardBlock(par1Block, x, y, z);
            }
            if (landingPad.isBlockAttachable(block, x + 1, y, z + 3)) {
                renderBlocks.setRenderBounds((double)1.1f, (double)0.2f, 2.5, (double)1.9f, (double)0.901f, 3.0);
                renderBlocks.renderStandardBlock(par1Block, x, y, z);
            }
            if (landingPad.isBlockAttachable(block, x, y, z + 3)) {
                renderBlocks.setRenderBounds((double)0.1f, (double)0.2f, 2.5, (double)0.9f, (double)0.901f, 3.0);
                renderBlocks.renderStandardBlock(par1Block, x, y, z);
            }
            if (landingPad.isBlockAttachable(block, x - 1, y, z + 3)) {
                renderBlocks.setRenderBounds((double)-0.9f, (double)0.2f, 2.5, (double)-0.1f, (double)0.901f, 3.0);
                renderBlocks.renderStandardBlock(par1Block, x, y, z);
            }
            if (landingPad.isBlockAttachable(block, x - 2, y, z + 3)) {
                renderBlocks.setRenderBounds((double)-1.9f, (double)0.2f, 2.5, (double)-1.1f, (double)0.901f, 3.0);
                renderBlocks.renderStandardBlock(par1Block, x, y, z);
            }
        }
        if (block.getBlockMetadata(x, y, z) == 2) {
            Tessellator tessellator = Tessellator.instance;
            tessellator.setBrightness(Blocks.hopper.getMixedBrightnessForBlock(block, x, y, z));
            float f1 = 1.0f;
            int j1 = Blocks.hopper.colorMultiplier(block, x, y, z);
            float f = (float)(j1 >> 16 & 0xFF) / 255.0f;
            float f2 = (float)(j1 >> 8 & 0xFF) / 255.0f;
            float f3 = (float)(j1 & 0xFF) / 255.0f;
            if (EntityRenderer.anaglyphEnable) {
                float f4 = (f * 30.0f + f2 * 59.0f + f3 * 11.0f) / 100.0f;
                float f5 = (f * 30.0f + f2 * 70.0f) / 100.0f;
                float f6 = (f * 30.0f + f3 * 70.0f) / 100.0f;
                f = f4;
                f2 = f5;
                f3 = f6;
            }
            tessellator.setColorOpaque_F(f1 * f, f1 * f2, f1 * f3);
            renderBlocks.clearOverrideBlockTexture();
            IIcon icon = BlockHopper.getHopperIcon((String)"hopper");
            BlockHopper.getHopperIcon((String)"hopper_inside");
            f = 0.125f;
            double d0 = 0.625;
            renderBlocks.setOverrideBlockTexture(icon);
            renderBlocks.setRenderBounds(0.0, d0, 0.0, 1.0, 0.9, 1.0);
            renderBlocks.renderStandardBlock((Block)Blocks.hopper, x, y, z);
            renderBlocks.setOverrideBlockTexture(((BlockAdvLandingPadFull)par1Block).getIcon(0, 0));
            renderBlocks.setRenderBounds(-0.1, 0.0, -0.1, 0.0, 1.0, 0.0);
            renderBlocks.renderStandardBlock((Block)Blocks.hopper, x, y, z);
            renderBlocks.setRenderBounds(-0.1, 0.0, 1.0, 0.0, 1.0, 1.1);
            renderBlocks.renderStandardBlock((Block)Blocks.hopper, x, y, z);
            renderBlocks.setRenderBounds(1.0, 0.0, -0.1, 1.1, 1.0, 0.0);
            renderBlocks.renderStandardBlock((Block)Blocks.hopper, x, y, z);
            renderBlocks.setRenderBounds(1.0, 0.0, 1.0, 1.1, 1.0, 1.1);
            renderBlocks.renderStandardBlock((Block)Blocks.hopper, x, y, z);
            renderBlocks.setRenderBounds(0.0, 0.9, 0.0, 1.0, 1.0, 1.0);
            renderBlocks.renderStandardBlock(par1Block, x, y, z);
            renderBlocks.setOverrideBlockTexture(icon);
            double d1 = 0.1;
            double d2 = 0.1;
            renderBlocks.setRenderBounds(d1, d2, d1, 1.0 - d1, d0 - 0.002, 1.0 - d1);
            renderBlocks.renderStandardBlock((Block)Blocks.hopper, x, y, z);
        }
        renderBlocks.clearOverrideBlockTexture();
        par1Block.setBlockBoundsForItemRender();
        renderBlocks.uvRotateTop = 0;
    }
}

