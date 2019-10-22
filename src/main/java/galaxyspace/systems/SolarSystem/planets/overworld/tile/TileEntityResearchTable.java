package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import micdoodle8.mods.galacticraft.core.tile.TileEntityAdvanced;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;

public class TileEntityResearchTable extends TileEntityAdvanced {

	public TileEntityResearchTable()
	{
		super("tile.research_table.name");
		this.inventory = NonNullList.withSize(0, ItemStack.EMPTY);
	}

	@Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        ItemStackHelper.loadAllItems(par1NBTTagCompound, this.getInventory());               
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.writeToNBT(par1NBTTagCompound);     
    	ItemStackHelper.saveAllItems(par1NBTTagCompound, this.getInventory());		
        return par1NBTTagCompound;
    }
    
	@Override
    public int getInventoryStackLimit()
    {
        return 1;
    }
	
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[] {0};
	}

	@Override
	public double getPacketRange() {
		return 0;
	}

	@Override
	public int getPacketCooldown() {
		return 0;
	}

	@Override
	public boolean isNetworkedTile() {
		return false;
	}
}
