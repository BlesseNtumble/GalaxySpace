package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.ArrayList;

import galaxyspace.core.registers.blocks.GSBlocks;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;

public class TileEntityAdvLandingPadSingle extends TileEntity
{
    @Override
    public void updateEntity()
    {
        if (!this.worldObj.isRemote)
        {
	    	final ArrayList<TileEntity> attachedLaunchPads = new ArrayList<TileEntity>();
	
	        for (int x = this.xCoord - 2; x < this.xCoord + 3; x++)
	        {
	            for (int z = this.zCoord - 2; z < this.zCoord + 3; z++)
	            {
	                final TileEntity tile = this.worldObj.getTileEntity(x, this.yCoord, z);
	
	                if (tile instanceof TileEntityAdvLandingPadSingle)
	                {
	                    attachedLaunchPads.add(tile);
	                }
	            }
	        }
	
	        if (attachedLaunchPads.size() == 25)
	        {
	            for (final TileEntity tile : attachedLaunchPads)
	            {
	                tile.invalidate();
	                tile.getWorldObj().setBlock(tile.xCoord, tile.yCoord, tile.zCoord, Blocks.air, 0, 3);

	            }
	
            	this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, GSBlocks.AdvLandingPadFull, 0, 3);
	         
	            final TileEntityAdvLandingPad tilePadFull = (TileEntityAdvLandingPad) this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord);
	
	            if (tilePadFull != null)
	            {
	                tilePadFull.onCreate(new BlockVec3(this.xCoord, this.yCoord, this.zCoord));
	            }
	        }
        }
    }
}
