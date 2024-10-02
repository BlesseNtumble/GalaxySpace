package galaxyspace.core.events;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.block.Block;
import net.minecraft.world.World;

@Cancelable
public class SetBlockEvent extends Event {

	public final World world;
	public final Block block;
	public final int x, y, z, meta, flags;

	public SetBlockEvent(World world, int x, int y, int z, Block block, int meta, int flags) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = block;
		this.meta = meta;
		this.flags = flags;
	}
}
