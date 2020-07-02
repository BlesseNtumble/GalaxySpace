package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.ArrayList;

import galaxyspace.core.GSBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityAdvLandingPadSingle extends TileEntity implements ITickable{
	private int corner = 0;

	@Override
	public void update() {
		if (!this.world.isRemote && this.corner == 0) {
			final ArrayList<TileEntity> attachedLaunchPads = new ArrayList<>();

			for (int x = this.getPos().getX() - 2; x < this.getPos().getX() + 3; x++) {
				for (int z = this.getPos().getZ() - 2; z < this.getPos().getZ() + 3; z++) {
					final TileEntity tile = this.world.getTileEntity(new BlockPos(x, this.getPos().getY(), z));
					
					if (tile instanceof TileEntityAdvLandingPadSingle && !tile.isInvalid()
							&& ((TileEntityAdvLandingPadSingle) tile).corner == 0) {
						
						attachedLaunchPads.add(tile);
					}
				}
			}

			if (attachedLaunchPads.size() == 25) {
				
				for (final TileEntity tile : attachedLaunchPads) {
					this.world.markTileEntityForRemoval(tile);
					((TileEntityAdvLandingPadSingle) tile).corner = 1;
				}

				this.world.setBlockState(this.getPos(), GSBlocks.ADVANCED_LANDING_PAD.getDefaultState(), 2);
			}
		}
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}
}
