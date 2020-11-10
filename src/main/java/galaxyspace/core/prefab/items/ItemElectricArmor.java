package galaxyspace.core.prefab.items;

import cofh.redstoneflux.api.IEnergyContainerItem;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import ic2.api.item.IElectricItem;
import ic2.api.item.IElectricItemManager;
import ic2.api.item.ISpecialElectricItem;
import mekanism.api.energy.IEnergizedItem;
import micdoodle8.mods.galacticraft.api.item.IItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.EnergyConfigHandler;
import micdoodle8.mods.galacticraft.core.energy.item.ElectricItemManagerIC2;
import micdoodle8.mods.galacticraft.core.items.ItemBatteryInfinite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(modid = "redstoneflux", iface = "cofh.redstoneflux.api.IEnergyContainerItem")
@Optional.Interface(modid = "mekanism", iface = "mekanism.api.energy.IEnergizedItem")
@Optional.Interface(modid = "ic2", iface = "ic2.api.item.IElectricItem")
@Optional.Interface(modid = "ic2", iface = "ic2.api.item.ISpecialElectricItem")
public class ItemElectricArmor extends ItemArmor implements IEnergyContainerItem, IElectricItem, ISpecialElectricItem, IEnergizedItem, IItemElectricBase {

	public float transferMax = 200.0F;
	private static Object itemManagerIC2;
	
	public ItemElectricArmor(ArmorMaterial materialIn, EntityEquipmentSlot armorIndex) {
		super(materialIn, 0, armorIndex);
		this.setMaxDamage(200);
		this.setNoRepair();
		
		if (EnergyConfigHandler.isIndustrialCraft2Loaded()) {
			itemManagerIC2 = new ElectricItemManagerIC2();
		}

	}
	
	@Override
    public CreativeTabs getCreativeTab()
    {
        return GSCreativeTabs.GSArmorTab;
    }
	
	@Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
		return GalaxySpace.TEXTURE_PREFIX + "textures/model/armor/nullarmor.png";
    }
	
	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		this.setElectricity(itemStack, 0.0F);			   
	}
	
	@Override
	public float recharge(ItemStack stack, float energy, boolean doRecharge) {
		if(!stack.hasTagCompound()) { stack.setTagCompound(new NBTTagCompound());}

		float rejectedElectricity = Math.max(this.getElectricityStored(stack) + energy - this.getMaxElectricityStored(stack), 0.0F);
		float energyToReceive = energy - rejectedElectricity;
		if (energyToReceive > this.transferMax) {
			float var10000 = rejectedElectricity + (energyToReceive - this.transferMax);
			energyToReceive = this.transferMax;
		}

		if (doRecharge) {
			this.setElectricity(stack, this.getElectricityStored(stack) + energyToReceive);
		}

		return energyToReceive;	    
	}
	
	@Override
	public float discharge(ItemStack stack, float energy, boolean doDischarge) {
		
		float energyToTransfer = Math.min(Math.min(this.getElectricityStored(stack), energy), this.transferMax);
	    if(doDischarge) {
	    	this.setElectricity(stack, this.getElectricityStored(stack) - energyToTransfer);
	    }
	    
	    return energyToTransfer;	      
	}
	
	@Override
	public float getElectricityStored(ItemStack stack) {
		if(!stack.hasTagCompound()) { stack.setTagCompound(new NBTTagCompound());}

		float energyStored = 0.0F;
		if (stack.getTagCompound().hasKey("electricity")) {
			NBTBase obj = stack.getTagCompound().getTag("electricity");
			if (obj instanceof NBTTagDouble) {
				energyStored = ((NBTTagDouble) obj).getFloat();
			} else if (obj instanceof NBTTagFloat) {
				energyStored = ((NBTTagFloat) obj).getFloat();
			}
		}

		stack.setItemDamage((int) ((float) stack.getMaxDamage() - energyStored / this.getMaxElectricityStored(stack) * (float) stack.getMaxDamage()));
		return energyStored;
	}
	
	@Override
	public float getMaxElectricityStored(ItemStack stack) {
		return 200000.0F;		
	}
	
	@Override
	public void setElectricity(ItemStack stack, float joules) {
		if(!stack.hasTagCompound()) { stack.setTagCompound(new NBTTagCompound());}

		float electricityStored = Math.max(Math.min(joules, this.getMaxElectricityStored(stack)), 0.0F);
		stack.getTagCompound().setFloat("electricity", electricityStored);
		stack.setItemDamage((int) (stack.getMaxDamage()	- electricityStored / this.getMaxElectricityStored(stack) * (float) stack.getMaxDamage()));

	}
	
	@Override
	public float getTransfer(ItemStack stack) {
		if(!stack.hasTagCompound()) { stack.setTagCompound(new NBTTagCompound());}
		return Math.min(this.transferMax, this.getMaxElectricityStored(stack) - this.getElectricityStored(stack));
	}
	
	@Override
	public int getTierGC(ItemStack itemStack) {
		return 1;
	}
	
	@Override
	public float getMaxTransferGC(ItemStack itemStack) {
		return this.transferMax;
	}

	public static boolean isElectricItem(Item item) {
		if (item instanceof IItemElectricBase) {
			return true;
		}

		if (EnergyConfigHandler.isIndustrialCraft2Loaded()) {
			if (item instanceof ic2.api.item.ISpecialElectricItem) {
				return true;
			}
		}

		return false;
	}

	public static boolean isElectricItemEmpty(ItemStack itemstack) {
		if (itemstack.isEmpty()) {
			return false;
		}
		Item item = itemstack.getItem();

		if (item instanceof IItemElectricBase) {
			return ((IItemElectricBase) item).getElectricityStored(itemstack) <= 0;
		}

		if (EnergyConfigHandler.isIndustrialCraft2Loaded()) {
			if (item instanceof ic2.api.item.IElectricItem) {
				return !((ic2.api.item.IElectricItem) item).canProvideEnergy(itemstack);
			}
		}

		return false;
	}

	public static boolean isElectricItemCharged(ItemStack itemstack) {
		if (itemstack == null)
			return false;
		Item item = itemstack.getItem();

		if (item instanceof IItemElectricBase) {
			return ((IItemElectricBase) item).getElectricityStored(itemstack) > 0;
		}

		if (EnergyConfigHandler.isIndustrialCraft2Loaded()) {
			if (item instanceof ic2.api.item.IElectricItem) {
				return ((ic2.api.item.IElectricItem) item).canProvideEnergy(itemstack);
			}
		}

		return false;
	}

	// RF Compact
	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		return (int) (this.recharge(container, maxReceive * EnergyConfigHandler.RF_RATIO, !simulate) / EnergyConfigHandler.RF_RATIO);
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		return (int) (this.discharge(container, maxExtract / EnergyConfigHandler.TO_RF_RATIO, !simulate) * EnergyConfigHandler.TO_RF_RATIO);
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		return (int) (this.getElectricityStored(container) * EnergyConfigHandler.TO_RF_RATIO);
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		return (int) (this.getMaxElectricityStored(container) * EnergyConfigHandler.TO_RF_RATIO);
	}

	// Mekanism Compat
	@Override
	public double getEnergy(ItemStack itemStack) {
		return this.getElectricityStored(itemStack) * EnergyConfigHandler.TO_MEKANISM_RATIO;
	}

	@Override
	public void setEnergy(ItemStack itemStack, double amount) {
		this.setElectricity(itemStack, (float) amount * EnergyConfigHandler.MEKANISM_RATIO);
	}

	@Override
	public double getMaxEnergy(ItemStack itemStack) {
		return this.getMaxElectricityStored(itemStack) * EnergyConfigHandler.TO_MEKANISM_RATIO;
	}

	@Override
	public double getMaxTransfer(ItemStack itemStack) {
		return this.transferMax * EnergyConfigHandler.TO_MEKANISM_RATIO;
	}

	@Override
	public boolean canReceive(ItemStack itemStack) {
		return (itemStack != null && !(itemStack.getItem() instanceof ItemBatteryInfinite));
	}

	public boolean canSend(ItemStack itemStack) {
		return true;
	}

	// IC2 Compact
	@Override
	public IElectricItemManager getManager(ItemStack itemstack) {
		return (IElectricItemManager) this.itemManagerIC2;
	}

	@Override
	public boolean canProvideEnergy(ItemStack itemStack) {
		return true;
	}

	@Override
	public int getTier(ItemStack itemStack) {
		return 1;
	}

	@Override
	public double getMaxCharge(ItemStack itemStack) {
		return this.getMaxElectricityStored(itemStack) / EnergyConfigHandler.IC2_RATIO;
	}

	@Override
	public double getTransferLimit(ItemStack itemStack) {
		return this.transferMax * EnergyConfigHandler.TO_IC2_RATIO;
	}

	// Forge Energy Compact
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new EnergyCapabilityProvider(stack, this);
	}

	private class EnergyCapabilityProvider implements ICapabilityProvider {
		EnergyStorage storage;
		public EnergyCapabilityProvider(ItemStack stack, ItemElectricArmor item) {
			storage = new EnergyStorage((int) (item.getMaxElectricityStored(stack) * EnergyConfigHandler.TO_RF_RATIO), (int) item.transferMax) {
				@Override
				public int receiveEnergy(int maxReceive, boolean simulate) {
					if (!this.canReceive()) {
						return 0;
					}
					return (int) (item.recharge(stack, maxReceive * EnergyConfigHandler.RF_RATIO, !simulate) / EnergyConfigHandler.RF_RATIO);
				}

				@Override
				public int extractEnergy(int maxExtract, boolean simulate) {
					if (!canExtract()) {
						return 0;
					}
					return (int) (item.discharge(stack, maxReceive / EnergyConfigHandler.RF_RATIO, !simulate) * EnergyConfigHandler.RF_RATIO);
				}

				@Override
				public int getEnergyStored() {
					return (int) (item.getMaxElectricityStored(stack) * EnergyConfigHandler.TO_RF_RATIO);
				}
			};
		}

		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			if (capability == CapabilityEnergy.ENERGY) {
				return true;
			} else
				return false;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			if (hasCapability(capability, facing)) {
				return CapabilityEnergy.ENERGY.cast(storage);
			} else {
				return null;
			}
		}
	}
}
