package galaxyspace.core.client.render.block;

import galaxyspace.api.block.IOverlayBlock;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import galaxyspace.api.block.IGlowBlock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class LayerBlockRender implements ISimpleBlockRenderingHandler {


	public static final int glowBlockID = RenderingRegistry.getNextAvailableRenderId();
	
	static {
		RenderingRegistry.registerBlockHandler(glowBlockID, new LayerBlockRender());
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		int meta = world.getBlockMetadata(x, y, z);
		Tessellator tessellator = Tessellator.instance;
        if (Minecraft.isAmbientOcclusionEnabled() && block.getLightValue() == 0) {
            if (renderer.partialRenderBounds)
                renderer.renderStandardBlockWithAmbientOcclusionPartial(block, x, y, z, 1, 1, 1);
            else
                renderer.renderStandardBlockWithAmbientOcclusion(block, x, y, z, 1, 1, 1);
        } else
            renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 1, 1, 1);

        if (block instanceof IOverlayBlock && ((IOverlayBlock) block).shouldOverlayColor(meta)) {
            ((IOverlayBlock) block).setIsRenderingOverlay(true);
            renderer.renderStandardBlock(block, x, y, z);
            ((IOverlayBlock) block).setIsRenderingOverlay(false);
        }

        else if (block instanceof IGlowBlock) {
            tessellator.setColorRGBA_F(1F, 1F, 1F, 1F);
            if (((IGlowBlock) block).enableGlow(meta))
                tessellator.setBrightness(((IGlowBlock) block).alphaGlow(meta));
            IIcon c = ((IGlowBlock)block).getOverlayForSide(0, meta);
            float u = c.getMinU();
            float v = c.getMinV();
            float U = c.getMaxU();
            float V = c.getMaxV();

            //DOWN
            if (block.shouldSideBeRendered(world, x, y - 1, z, 0)) {

                tessellator.addVertexWithUV(0, 0, 1, u, V);
                tessellator.addVertexWithUV(0, 0, 0, u, v);
                tessellator.addVertexWithUV(1, 0, 0, U, v);
                tessellator.addVertexWithUV(1, 0, 1, U, V);
            }
            // UP
            if (block.shouldSideBeRendered(world, x, y + 1, z, 1)) {
                tessellator.addVertexWithUV(0, 1, 0, u, v);
                tessellator.addVertexWithUV(0, 1, 1, u, V);
                tessellator.addVertexWithUV(1, 1, 1, U, V);
                tessellator.addVertexWithUV(1, 1, 0, U, v);
            }
            // NORTH
            if (block.shouldSideBeRendered(world, x, y, z - 1, 2)) {
                tessellator.addVertexWithUV(1, 0, 0, u, V);
                tessellator.addVertexWithUV(0, 0, 0, U, V);
                tessellator.addVertexWithUV(0, 1, 0, U, v);
                tessellator.addVertexWithUV(1, 1, 0, u, v);
            }
            // SOUTH
            if (block.shouldSideBeRendered(world, x, y, z + 1, 3)) {
                tessellator.addVertexWithUV(0, 1, 1, u, v);
                tessellator.addVertexWithUV(0, 0, 1, u, V);
                tessellator.addVertexWithUV(1, 0, 1, U, V);
                tessellator.addVertexWithUV(1, 1, 1, U, v);
            }
            // WEST
            if (block.shouldSideBeRendered(world, x - 1, y, z, 4)) {
                tessellator.addVertexWithUV(0, 0, 0, u, V);
                tessellator.addVertexWithUV(0, 0, 1, U, V);
                tessellator.addVertexWithUV(0, 1, 1, U, v);
                tessellator.addVertexWithUV(0, 1, 0, u, v);
            }
            // EAST
            if (block.shouldSideBeRendered(world, x + 1, y, z, 5)) {
                tessellator.addVertexWithUV(1, 0, 1, u, V);
                tessellator.addVertexWithUV(1, 0, 0, U, V);
                tessellator.addVertexWithUV(1, 1, 0, U, v);
                tessellator.addVertexWithUV(1, 1, 1, u, v);
            }
        }

		return true;
	}

	@Override
	public int getRenderId() {
		return this.glowBlockID;
	}
	

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        renderInvNormalBlock(renderer, block, metadata);
    }
    

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}
	
    public static void renderInvNormalBlock(RenderBlocks var0, Block block, int meta)
    {
        final Tessellator tes = Tessellator.instance;
        GL11.glRotatef(90, 0, 1, 0);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        var0.setRenderBounds(0F, 0F, 0F, 1F, 1F, 1F);
        
        tes.startDrawingQuads();
        tes.setNormal(0.0F, 0.8F, 0.0F);
        var0.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));
        tes.draw();
        
        tes.startDrawingQuads();
        tes.setNormal(0.0F, 0.8F, 0.0F);
        var0.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));
        tes.draw();
        
        tes.startDrawingQuads();
        tes.setNormal(0.0F, 0.0F, 1.0F);
        var0.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));
        tes.draw();
        
        tes.startDrawingQuads();
        tes.setNormal(0.0F, 0.0F, -1.0F);
        var0.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));
        tes.draw();
        
        tes.startDrawingQuads();
        tes.setNormal(0.0F, 0.0F, 0.0F);
        var0.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, meta));
        tes.draw();
        
        tes.startDrawingQuads();
        tes.setNormal(-0.5F, 0.0F, 0.0F);
        var0.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, meta));
        tes.draw();

        if(block instanceof IGlowBlock) {
            IIcon yneg = ((IGlowBlock)block).getOverlayForSide(0, meta);
            if(yneg != null) {
                tes.startDrawingQuads();
                tes.setNormal(0.0F, 0.8F, 0.0F);
                var0.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, yneg);
                tes.draw();
            }
            IIcon ypos = ((IGlowBlock) block).getOverlayForSide(1, meta);
            if (ypos != null) {
                tes.startDrawingQuads();
                tes.setNormal(0.0F, 0.8F, 0.0F);
                var0.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, ypos);
                tes.draw();
            }

            IIcon xpos = ((IGlowBlock) block).getOverlayForSide(2, meta);
            if (xpos != null) {
                tes.startDrawingQuads();
                tes.setNormal(0.0F, 0.0F, 1.0F);
                var0.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, xpos);
                tes.draw();
            }

            IIcon xneg = ((IGlowBlock) block).getOverlayForSide(3, meta);
            if (xneg != null) {
                tes.startDrawingQuads();
                tes.setNormal(0.0F, 0.0F, -1.0F);
                var0.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, xneg);
                tes.draw();
            }

            IIcon zneg = ((IGlowBlock) block).getOverlayForSide(4, meta);
            if (zneg != null) {
                tes.startDrawingQuads();
                tes.setNormal(0.0F, 0.0F, 0.0F);
                var0.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, zneg);
                tes.draw();
            }

            IIcon zpos = ((IGlowBlock) block).getOverlayForSide(5, meta);
            if (zpos != null) {
                tes.startDrawingQuads();
                tes.setNormal(-0.5F, 0.0F, 0.0F);
                var0.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, zpos);
                tes.draw();
            }
        }
    }
}
