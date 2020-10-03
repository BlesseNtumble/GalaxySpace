package galaxyspace.systems.SolarSystem.planets.overworld.inventory;

import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityResearchTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class ContainerResearchTable extends Container{

	private final TileEntityResearchTable tile;
	
	public ContainerResearchTable(InventoryPlayer inventoryPlayer, TileEntityResearchTable tileEntity)
	{
		this.tile = tileEntity;
		/*
		int var3;
		for (var3 = 0; var3 < 3; ++var3) {
			for (int var4 = 0; var4 < 9; ++var4) {
				this.addSlotToContainer(
						new Slot(inventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 127 + var3 * 18));
			}
		}

		for (var3 = 0; var3 < 9; ++var3) {
			this.addSlotToContainer(new Slot(inventoryPlayer, var3, 8 + var3 * 18, 185));
		}*/
	}
	
	@Override
	public void onContainerClosed(EntityPlayer entityplayer) {
		super.onContainerClosed(entityplayer);
	}
	
	@Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.tile.isUsableByPlayer(par1EntityPlayer);
    }
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par1)
    {
		return ItemStack.EMPTY;
    }
}
