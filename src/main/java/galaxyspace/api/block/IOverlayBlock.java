package galaxyspace.api.block;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

public interface IOverlayBlock {

    boolean shouldOverlayColor(int meta);
    boolean getIsRenderingOverlay();
    void setIsRenderingOverlay(boolean bool);
    boolean hasBlockOnSide(IBlockAccess world, Block blockToCheckFor, int x, int y, int z, int side);
}
