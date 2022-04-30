package galaxyspace.core.events;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class UpdateBlockEvent extends Event {

	public final World world;
	public final BlockPos pos;
	public final IBlockState block;
	public final Random rand;
	
	public UpdateBlockEvent(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		this.world = worldIn;
		this.pos = pos;
		this.block = state;
		this.rand = rand;
	}
}
