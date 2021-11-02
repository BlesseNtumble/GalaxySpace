package galaxyspace.core.prefab.tile;

import galaxyspace.core.GSItems;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlock;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;

public abstract class TileEntityUpgradeMachine extends TileBaseElectricBlock {

	private int boost_speed = 0, energy_boost = 0;
	@NetworkedField(targetSide = Side.CLIENT)
    public int processTicks = 0;
	
	public TileEntityUpgradeMachine(String tileName) {
		super(tileName);
	}
	
	@Override
    public void update()
    {
		super.update();
		
		if (!this.world.isRemote) {

			if (this.hasEnoughEnergyToRun)
            {
				if(canProcess()) {

					boost_speed = energy_boost = 0;
					
                	for(int i = 0; i <= upgradeSlots().length - 1; i++)
                	{
                		if(this.getInventory().get(upgradeSlots()[i]).isItemEqual(new ItemStack(GSItems.UPGRADES, 1, 2)))
                			boost_speed++;
                		if(this.getInventory().get(upgradeSlots()[i]).isItemEqual(new ItemStack(GSItems.UPGRADES, 1, 3)))
                			energy_boost++;
                	}
                	
                	 this.processTicks += 1 * (1 + this.getBoost());
                     this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 90 + (60 * this.getBoost()) - (20 * getEnergy()) : 75 + (55 * this.getBoost()) - (15 * this.getEnergy()));
                     
				}
            }
		}
    }
	
	public int getBoost() {
		return boost_speed;
	}
	
	public int getEnergy() {
		return energy_boost;
	}
	
	public abstract boolean canProcess();
	
	public abstract int[] upgradeSlots();

}
