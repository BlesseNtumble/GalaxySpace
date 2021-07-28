package galaxyspace.api.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public interface IEnergyGeyser {
	public abstract Fluid getFluidForWork(World world, IBlockState state, BlockPos pos);
}
