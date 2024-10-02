package galaxyspace.core.prefab.items;

import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.versioning.DefaultArtifactVersion;
import cpw.mods.fml.common.versioning.VersionParser;
import cpw.mods.fml.relauncher.FMLInjectionData;
import ic2.api.item.IElectricItem;
import ic2.api.item.IElectricItemManager;
import ic2.api.item.ISpecialElectricItem;
import mekanism.api.energy.IEnergizedItem;
import micdoodle8.mods.galacticraft.api.item.IItemElectric;
import micdoodle8.mods.galacticraft.api.item.IItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.EnergyConfigHandler;
import micdoodle8.mods.galacticraft.core.energy.item.ElectricItemManagerIC2;
import micdoodle8.mods.galacticraft.core.energy.item.ElectricItemManagerIC2_1710;
import micdoodle8.mods.galacticraft.core.items.ItemBatteryInfinite;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;

@Optional.InterfaceList({
	@Optional.Interface(modid = "CoFHAPI", iface = "cofh.api.energy.IEnergyContainerItem"),
	@Optional.Interface(modid = "Mekanism", iface = "mekanism.api.energy.IEnergizedItem"),
	@Optional.Interface(modid = "IC2", iface = "ic2.api.item.IElectricItem"),
	@Optional.Interface(modid = "IC2", iface = "ic2.api.item.ISpecialElectricItem")
})
public abstract class ItemElectricArmor extends ItemArmor implements IItemElectricBase, IEnergyContainerItem, IElectricItem, ISpecialElectricItem, IEnergizedItem {

	private static Object itemManagerIC2;
	public float transferMax = 200.0F;
	
	private DefaultArtifactVersion mcVersion = null;
	
	public ItemElectricArmor(ArmorMaterial material, int renderIndex, int armorIndex, String assetSuffix) {
		super(material, renderIndex, armorIndex);
		
		this.setUnlocalizedName(assetSuffix);
		this.setMaxStackSize(1);
		this.setMaxDamage(100);
		this.setNoRepair();

		this.mcVersion = new DefaultArtifactVersion((String) FMLInjectionData.data()[4]);

        if (EnergyConfigHandler.isIndustrialCraft2Loaded())
        {
            if (VersionParser.parseRange("[1.7.2]").containsVersion(mcVersion))
            {
                itemManagerIC2 = new ElectricItemManagerIC2();
            }
            else
            {
                itemManagerIC2 = new ElectricItemManagerIC2_1710();
            }
        }
	}

	@Override
	public float recharge(ItemStack itemStack, float energy, boolean doRecharge) {

        float rejectedElectricity = Math.max(this.getElectricityStored(itemStack) + energy - this.getMaxElectricityStored(itemStack), 0);
        float energyToReceive = energy - rejectedElectricity;
        if (energyToReceive > this.transferMax)
        {
            rejectedElectricity += energyToReceive - this.transferMax;
            energyToReceive = this.transferMax;
        }

        if (doRecharge)
        {
            this.setElectricity(itemStack, this.getElectricityStored(itemStack) + energyToReceive);
        }

        return energyToReceive;
	}

	@Override
	public float discharge(ItemStack itemStack, float energy, boolean doDischarge) {
		float energyToTransfer = Math.min(Math.min(this.getElectricityStored(itemStack), energy), this.transferMax);

		if (doDischarge) {
			this.setElectricity(itemStack, this.getElectricityStored(itemStack) - energyToTransfer);
		}

		return energyToTransfer;
	}

	@Override
	public float getElectricityStored(ItemStack itemStack) {
		if (itemStack.getTagCompound() == null) {
			itemStack.setTagCompound(new NBTTagCompound());
		}
		float energyStored = 0f;
		if (itemStack.getTagCompound().hasKey("electricity")) {
			NBTBase obj = itemStack.getTagCompound().getTag("electricity");
			if (obj instanceof NBTTagDouble) {
				energyStored = ((NBTTagDouble) obj).func_150288_h();
			} else if (obj instanceof NBTTagFloat) {
				energyStored = ((NBTTagFloat) obj).func_150288_h();
			}
		}

		/** Sets the damage as a percentage to render the bar properly. */
		itemStack.setItemDamage((int) (100 - energyStored / this.getMaxElectricityStored(itemStack) * 100));
		return energyStored;
	}

	@Override
	public abstract float getMaxElectricityStored(ItemStack theItem);

	@Override
	public void setElectricity(ItemStack itemStack, float joules) {
		 // Saves the frequency in the ItemStack
        if (itemStack.getTagCompound() == null)
        {
            itemStack.setTagCompound(new NBTTagCompound());
        }

        float electricityStored = Math.max(Math.min(joules, this.getMaxElectricityStored(itemStack)), 0);
        itemStack.getTagCompound().setFloat("electricity", electricityStored);

        /** Sets the damage as a percentage to render the bar properly. */
        itemStack.setItemDamage((int) (100 - electricityStored / this.getMaxElectricityStored(itemStack) * 100));
	}

	@Override
	public float getTransfer(ItemStack itemStack) {
		return Math.min(this.transferMax,
				this.getMaxElectricityStored(itemStack) - this.getElectricityStored(itemStack));
	}

	@Override
	public int getTierGC(ItemStack itemStack) {
		return 1;
	}
	
//For RF compatibility
    
	@Override
	@Optional.Method(modid = "CoFHAPI")
    public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate)
    {
    	return (int) (this.recharge(container, maxReceive * EnergyConfigHandler.RF_RATIO, !simulate) / EnergyConfigHandler.RF_RATIO);
    }
    
	@Override
	@Optional.Method(modid = "CoFHAPI")
    public int extractEnergy(ItemStack container, int maxExtract, boolean simulate)
    {
    	return (int) (this.discharge(container, maxExtract / EnergyConfigHandler.TO_RF_RATIO, !simulate) * EnergyConfigHandler.TO_RF_RATIO);
    }
    
	@Override
	@Optional.Method(modid = "CoFHAPI")
    public int getEnergyStored(ItemStack container)
    {
    	return (int) (this.getElectricityStored(container) * EnergyConfigHandler.TO_RF_RATIO);
    }
    
	@Override
	@Optional.Method(modid = "CoFHAPI")
    public int getMaxEnergyStored(ItemStack container)
    {
    	return (int) (this.getMaxElectricityStored(container) * EnergyConfigHandler.TO_RF_RATIO);
    }
    
    //The following seven methods are for Mekanism compatibility

	@Override
	@Optional.Method(modid = "Mekanism")
    public double getEnergy(ItemStack itemStack)
    {
        return this.getElectricityStored(itemStack) * EnergyConfigHandler.TO_MEKANISM_RATIO;
    }

	@Override
	@Optional.Method(modid = "Mekanism")
    public void setEnergy(ItemStack itemStack, double amount)
    {
        this.setElectricity(itemStack, (float) amount * EnergyConfigHandler.MEKANISM_RATIO);
    }

	@Override
	@Optional.Method(modid = "Mekanism")
    public double getMaxEnergy(ItemStack itemStack)
    {
        return this.getMaxElectricityStored(itemStack) * EnergyConfigHandler.TO_MEKANISM_RATIO;
    }

	@Override
	@Optional.Method(modid = "Mekanism")
    public double getMaxTransfer(ItemStack itemStack)
    {
        return this.transferMax * EnergyConfigHandler.TO_MEKANISM_RATIO;
    }

	@Override
	@Optional.Method(modid = "Mekanism")
    public boolean canReceive(ItemStack itemStack)
    {
        return (itemStack != null && !(itemStack.getItem() instanceof ItemBatteryInfinite));
    }

	@Override
	@Optional.Method(modid = "Mekanism")
    public boolean canSend(ItemStack itemStack)
    {
        return true;
    }

    //All the following methods are for IC2 compatibility

    @Override
    @Optional.Method(modid = "IC2")
    public IElectricItemManager getManager(ItemStack itemstack)
    {
        return (IElectricItemManager) itemManagerIC2;
    }

    @Override
    @Optional.Method(modid = "IC2")
    public boolean canProvideEnergy(ItemStack itemStack)
    {
        return true;
    }

    @Override
    @Optional.Method(modid = "IC2")
    public Item getChargedItem(ItemStack itemStack)
    {
        return itemStack.getItem();
    }

    @Override
    @Optional.Method(modid = "IC2")
    public Item getEmptyItem(ItemStack itemStack)
    {
        return itemStack.getItem();
    }

    @Override
    @Optional.Method(modid = "IC2")
    public int getTier(ItemStack itemStack)
    {
        return 1;
    }

    @Override
    @Optional.Method(modid = "IC2")
    public double getMaxCharge(ItemStack itemStack)
    {
        return 0.0F;//this.getMaxElectricityStored(itemStack) / EnergyConfigHandler.IC2_RATIO;
    }

    @Override
    @Optional.Method(modid = "IC2")
    public double getTransferLimit(ItemStack itemStack)
    {
        return this.transferMax * EnergyConfigHandler.TO_IC2_RATIO;
    }

    @Override
	public float getMaxTransferGC(ItemStack itemStack) {
		return this.transferMax;
	}

}
