package galaxyspace.systems.SolarSystem.planets.overworld.blocks;

import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFutureGlass extends BlockGlass implements ITerraformableBlock
{

    public BlockFutureGlass()
    {
        super(Material.GLASS, false);    	
        this.setTranslationKey("futureglass_none");
        this.setHardness(2.0F);
        this.setSoundType(SoundType.GLASS);
    }

	@Override
	public boolean isTerraformable(World world, BlockPos pos) {
		return false;
	}
/*	
	@Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityBlockCrystallTE();
    }
	
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.INVISIBLE;
    }
	   */
}