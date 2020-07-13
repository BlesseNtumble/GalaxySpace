package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockUniversalRecycler;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.galacticraft.core.network.IPacketReceiver;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.wrappers.IFluidHandlerWrapper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;

public class TileEntityPlasmaProducer extends TileBaseElectricBlockWithInventory implements ISidedInventory, IPacketReceiver {

	public TileEntityPlasmaProducer() {
		super("tile.plasma_producer.name");
		this.storage.setCapacity(100000);
		this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 90 : 75);
		this.inventory = NonNullList.withSize(2, ItemStack.EMPTY);
		this.setTierGC(1);
	}

	@Override
	public void update() {
		super.update();
	}
	
	private boolean canProcess()
    {
		return false;
		
    }
	
	private void smeltItem()
    {
		
    }
	
	@Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
    }
	
	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
       	super.writeToNBT(par1NBTTagCompound);
       	return par1NBTTagCompound;
    }
	
	@Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        return new int[] { 0, 1 };
    }
	
	@Override
    public boolean canInsertItem(int slotID, ItemStack itemstack, EnumFacing side)
    {
        return this.isItemValidForSlot(slotID, itemstack);
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack itemstack, EnumFacing side)
    {
        return slotID == 0;
    }
    
    @Override
    public boolean isItemValidForSlot(int slotID, ItemStack itemstack)
    {
    	switch (slotID)
        {
	        case 0:
	            return ItemElectricBase.isElectricItem(itemstack.getItem());
	        case 1:
	            return true;
        }

        return false;
    }
    
    @Override
	public boolean shouldUseEnergy() {
		return this.canProcess();
	}

	@Override
    public ItemStack getBatteryInSlot()
    {
        return this.getStackInSlot(0);
    }
	
	@Override
	public EnumFacing getFront() {
		IBlockState state = this.world.getBlockState(getPos());
		if (state.getBlock() instanceof BlockUniversalRecycler) {
			return state.getValue(BlockUniversalRecycler.FACING);
		}
		return EnumFacing.NORTH;
	}
}
