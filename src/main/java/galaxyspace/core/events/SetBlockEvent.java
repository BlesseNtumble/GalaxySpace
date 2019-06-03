package galaxyspace.core.events;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class SetBlockEvent extends Event{

	public final World world;
	public final IBlockState block;
	public final BlockPos pos;
	public final int flags;
	
	public SetBlockEvent(World world, BlockPos pos, IBlockState state, int flags) {
		this.world = world;
		this.pos = pos;
		this.block = state;
		this.flags = flags;
	}
}
