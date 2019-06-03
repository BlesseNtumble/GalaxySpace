package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import galaxyspace.GalaxySpace;
import galaxyspace.core.prefab.blocks.GSBlockMulti;
import galaxyspace.core.registers.blocks.GSBlocks;
import micdoodle8.mods.galacticraft.api.entity.IDockable;
import micdoodle8.mods.galacticraft.api.entity.IFuelable;
import micdoodle8.mods.galacticraft.api.entity.ILandable;
import micdoodle8.mods.galacticraft.api.tile.IFuelDock;
import micdoodle8.mods.galacticraft.api.tile.ILandingPadAttachable;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.blocks.BlockMulti;
import micdoodle8.mods.galacticraft.core.tile.IMultiBlock;
import micdoodle8.mods.galacticraft.core.tile.TileEntityMulti;
import micdoodle8.mods.galacticraft.planets.mars.tile.TileEntityLaunchController;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityAdvLandingPad extends TileEntityMulti implements IMultiBlock, IFuelable, IFuelDock{

	private IDockable dockedEntity;
    private boolean initialised;
    
	public TileEntityAdvLandingPad()
    {
        super(null);
    }
	
	 @Override
	public void update() {
		if (!this.initialised) {
			if (!this.world.isRemote)
				this.onCreate(this.world, this.getPos());
			this.initialiseMultiTiles(this.getPos(), this.world);
			this.initialised = true;
		}

		if (!this.world.isRemote) {
			final List<Entity> list = this.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(this.getPos().getX() - 0.5D, this.getPos().getY(), this.getPos().getZ() - 0.5D, this.getPos().getX() + 0.5D, this.getPos().getY() + 1.0D, this.getPos().getZ() + 0.5D));

			boolean docked = false;

			for (final Object o : list) {
				if (o instanceof IDockable && !((Entity) o).isDead) {
					final IDockable fuelable = (IDockable) o;

					if (!fuelable.inFlight()) {
						docked = true;

						if (fuelable != this.dockedEntity && fuelable.isDockValid(this)) {
							if (fuelable instanceof ILandable) {
								((ILandable) fuelable).landEntity(this.getPos());
							} else {
								fuelable.setPad(this);
							}
						}

						break;
					}
				}
			}

			if (!docked) {
				this.dockedEntity = null;
			}
		}
	}
	 
	@Override
	public boolean onActivated(EntityPlayer entityPlayer) {
		return false;
	}
	 
	@Override
    public void onCreate(World world, BlockPos placedPosition)
    {
        this.mainBlockPosition = placedPosition;
        this.markDirty();

        List<BlockPos> positions = new ArrayList<>();
        this.getPositions(placedPosition, positions);
        
        for (BlockPos pos : positions)
        {
        	if(pos.getX() == placedPosition.getX() + 2 
        			|| pos.getX() == placedPosition.getX() - 2
        			|| pos.getZ() == placedPosition.getZ() + 2
        			|| pos.getZ() == placedPosition.getZ() - 2)
        		world.setBlockState(pos, GSBlocks.FAKE_BLOCK.getDefaultState().withProperty(GSBlockMulti.MULTI_TYPE, GSBlockMulti.EnumBlockMultiType.ADVANCED_ROCKET_PAD_1), 0);
        
        	else
        		world.setBlockState(pos, GSBlocks.FAKE_BLOCK.getDefaultState().withProperty(GSBlockMulti.MULTI_TYPE, this.getGSMultiType()), 0);
        	
        	world.setTileEntity(pos, new TileEntityMulti(placedPosition));
        }
        //((GSBlockMulti) GSBlocks.FAKE_BLOCK).makeFakeBlock(world, positions, placedPosition, this.getGSMultiType());
    }
	
	@Override
    public BlockMulti.EnumBlockMultiType getMultiType()
    {
        return null;
    }
	
	public GSBlockMulti.EnumBlockMultiType getGSMultiType()
    {
        return GSBlockMulti.EnumBlockMultiType.ADVANCED_ROCKET_PAD;
    }
	
	@Override
    public void getPositions(BlockPos placedPosition, List<BlockPos> positions)
    {
        int y = placedPosition.getY();
        for (int x = -2; x < 3; x++)
        {
            for (int z = -2; z < 3; z++)
            {
                if (x == 0 && z == 0) continue;
                positions.add(new BlockPos(placedPosition.getX() + x, y, placedPosition.getZ() + z));
            }
        }
    }
	
	@Override
    public void onDestroy(TileEntity callingBlock)
    {
        final BlockPos thisBlock = getPos();
        List<BlockPos> positions = new ArrayList<>();
        this.getPositions(thisBlock, positions);

        for (BlockPos pos : positions)
        {
            IBlockState stateAt = this.world.getBlockState(pos);

            if (stateAt.getBlock() == GSBlocks.FAKE_BLOCK)             		
            {
                if (this.world.isRemote && this.world.rand.nextDouble() < 0.1D)
                {
                    FMLClientHandler.instance().getClient().effectRenderer.addBlockDestroyEffects(pos, this.world.getBlockState(pos));
                }
                this.world.destroyBlock(pos, false);
            }
        }
        this.world.destroyBlock(thisBlock, true);

        if (this.dockedEntity != null)
        {
            this.dockedEntity.onPadDestroyed();
            this.dockedEntity = null;
        }

    }
	
	@Override
    public int addFuel(FluidStack liquid, boolean doFill)
    {
        if (this.dockedEntity != null)
        {
        	
            return this.dockedEntity.addFuel(liquid, doFill);
        }

        return 0;
    }

    @Override
    public FluidStack removeFuel(int amount)
    {
        if (this.dockedEntity != null)
        {
            return this.dockedEntity.removeFuel(amount);
        }

        return null;
    }

    @Override
    public HashSet<ILandingPadAttachable> getConnectedTiles()
    {
        HashSet<ILandingPadAttachable> connectedTiles = new HashSet<ILandingPadAttachable>();

        for (int x = this.getPos().getX() - 2; x < this.getPos().getX() + 3; x++)
        {
        	this.testConnectedTile(x, this.getPos().getZ() - 2, connectedTiles);
        	this.testConnectedTile(x, this.getPos().getZ() + 2, connectedTiles);
        }

        for (int z = this.getPos().getZ() -2; z < this.getPos().getZ() + 3; z++)
                {
        	this.testConnectedTile(this.getPos().getX() - 2, z, connectedTiles);
        	this.testConnectedTile(this.getPos().getX() + 2, z, connectedTiles);
        }

        return connectedTiles;
    }
    
	private void testConnectedTile(int x, int z, HashSet<ILandingPadAttachable> connectedTiles) {
		BlockPos testPos = new BlockPos(x, this.getPos().getY(), z);
		if (!this.world.isBlockLoaded(testPos, false))
			return;

		final TileEntity tile = this.world.getTileEntity(testPos);

		if (tile instanceof ILandingPadAttachable
				&& ((ILandingPadAttachable) tile).canAttachToLandingPad(this.world, this.getPos())) {
			connectedTiles.add((ILandingPadAttachable) tile);
			if (GalacticraftCore.isPlanetsLoaded && tile instanceof TileEntityLaunchController) {
				((TileEntityLaunchController) tile).setAttachedPad(this);
			}
		}
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        return new AxisAlignedBB(getPos().getX() - 2, getPos().getY(), getPos().getZ() - 2, getPos().getX() + 3, getPos().getY() + 0.4D, getPos().getZ() + 3);
    }
	
	@Override
    public boolean isBlockAttachable(IBlockAccess world, BlockPos pos)
    {
        TileEntity tile = world.getTileEntity(pos);

        if (tile != null && tile instanceof ILandingPadAttachable)
        {
            return ((ILandingPadAttachable) tile).canAttachToLandingPad(world, this.getPos());
        }

        return false;
    }

    @Override
    public IDockable getDockedEntity()
    {
        return this.dockedEntity;
    }

    @Override
    public void dockEntity(IDockable entity)
    {
        this.dockedEntity = entity;
    }
}
