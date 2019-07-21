package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockModernStorageModule;
import micdoodle8.mods.galacticraft.api.item.IItemElectricBase;
import micdoodle8.mods.galacticraft.api.transmission.NetworkType;
import micdoodle8.mods.galacticraft.api.transmission.tile.IConnector;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseUniversalElectricalSource;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;

public class TileEntityModernStorageModule extends TileBaseUniversalElectricalSource implements ISidedInventory, IConnector {
	private final static float BASE_CAPACITY = 5000000;
	
	public final Set<EntityPlayer> playersUsing = new HashSet<EntityPlayer>();
	public int scaledEnergyLevel;
	public int lastScaledEnergyLevel;
	private float lastEnergy = 0;

	public TileEntityModernStorageModule()
	{
	    this(1);
	}

	public TileEntityModernStorageModule(int tier) {

		super("tile.modern_storage_module.name");
		this.setTier(tier);
	}

	private void setTier(int tier) {
		this.storage.setCapacity(BASE_CAPACITY + (BASE_CAPACITY * tier) / 10);
		this.storage.setMaxExtract(1800 + (200 * tier));
		this.inventory = NonNullList.withSize(2, ItemStack.EMPTY);
		this.setTierGC(tier);
	}

	@Override
	public void update() {


		float energy = this.storage.getEnergyStoredGC();
		if (this.getTierGC() == 1 && !this.world.isRemote) {
			if (this.lastEnergy - energy > this.storage.getMaxExtract() - 1) {
				// Deplete faster if being drained at maximum output
				this.storage.extractEnergyGC(25, false);
			}
		}
		this.lastEnergy = energy;

		super.update();

		this.scaledEnergyLevel = (int) Math.floor((this.getEnergyStoredGC() + 49) * 16 / this.getMaxEnergyStoredGC());

		if (this.scaledEnergyLevel != this.lastScaledEnergyLevel) {
			this.world.notifyLightSet(this.getPos());
		}

		if (!this.world.isRemote) {
			this.recharge(this.getInventory().get(0));
			this.discharge(this.getInventory().get(1));
		}

		if (!this.world.isRemote) {
			this.produce();
		}

		this.lastScaledEnergyLevel = this.scaledEnergyLevel;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(nbt, this.getInventory());

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if (this.tierGC == 1 && this.storage.getEnergyStoredGC() > BASE_CAPACITY) {
			this.storage.setEnergyStored(BASE_CAPACITY);
		}

		super.writeToNBT(nbt);

		ItemStackHelper.saveAllItems(nbt, this.getInventory());		

		return nbt;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer par1EntityPlayer) {
		return this.world.getTileEntity(this.getPos()) == this
				&& par1EntityPlayer.getDistanceSq(this.getPos().getX() + 0.5D, this.getPos().getY() + 0.5D,
						this.getPos().getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[0];
	}

	@Override
	public boolean isItemValidForSlot(int slotID, ItemStack itemstack) {
		return ItemElectricBase.isElectricItem(itemstack.getItem());
	}

	@Override
	public boolean canInsertItem(int slotID, ItemStack itemstack, EnumFacing side) {
		if (itemstack.getItem() instanceof IItemElectricBase) {
			if (slotID == 0) {
				return ((IItemElectricBase) itemstack.getItem()).getTransfer(itemstack) > 0;
			} else if (slotID == 1) {
				return ((IItemElectricBase) itemstack.getItem()).getElectricityStored(itemstack) > 0;
			}
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int slotID, ItemStack itemstack, EnumFacing side) {
		if (itemstack.getItem() instanceof IItemElectricBase) {
			if (slotID == 0) {
				return ((IItemElectricBase) itemstack.getItem()).getTransfer(itemstack) <= 0;
			} else if (slotID == 1) {
				return ((IItemElectricBase) itemstack.getItem()).getElectricityStored(itemstack) <= 0
						|| this.getEnergyStoredGC() >= this.getMaxEnergyStoredGC();
			}
		}

		return false;

	}

	@Override
	public EnumSet<EnumFacing> getElectricalInputDirections() {
		return EnumSet.of(getElectricInputDirection());
	}

	@Override
	public EnumSet<EnumFacing> getElectricalOutputDirections() {
		return EnumSet.of(getElectricOutputDirection());
	}

	@Override
	public boolean canConnect(EnumFacing direction, NetworkType type) {
		if (direction == null || type != NetworkType.POWER) {
			return false;
		}

		return getElectricalInputDirections().contains(direction)
				|| getElectricalOutputDirections().contains(direction);
	}

	public EnumFacing getFront() {
		IBlockState state = this.world.getBlockState(getPos());
		if (state.getBlock() instanceof BlockModernStorageModule) {
			return (state.getValue(BlockModernStorageModule.FACING));
		} 
		return EnumFacing.NORTH;
	}

	public EnumFacing getElectricInputDirection() {		
		return getFront().rotateY();
		
	}

	public EnumFacing getElectricOutputDirection() {
		return getFront().rotateYCCW();		
	}
	
}
