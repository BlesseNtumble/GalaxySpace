package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockOxygenStorageModule;
import micdoodle8.mods.galacticraft.api.item.IItemOxygenSupply;
import micdoodle8.mods.galacticraft.core.tile.TileEntityOxygen;
import micdoodle8.mods.galacticraft.core.util.FluidUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;	

public class TileEntityAdvOxygenStorageModule extends TileEntityOxygen implements ISidedInventory{

	public final Set<EntityPlayer> playersUsing = new HashSet<EntityPlayer>();
    public int scaledOxygenLevel;
    private int lastScaledOxygenLevel;

    public static final int OUTPUT_PER_TICK = 600;
    public static final int OXYGEN_CAPACITY = 240000;
   // private NonNullList<ItemStack> stacks = NonNullList.withSize(1, ItemStack.EMPTY);
    
    public TileEntityAdvOxygenStorageModule()
    {
    	this(1);
    }
    
    public TileEntityAdvOxygenStorageModule(int tier)
    {
        super("tile.oxygen_module_storage.name", OXYGEN_CAPACITY * tier, 40);
        this.storage.setCapacity(0);
        this.storage.setMaxExtract(0);
        this.inventory = NonNullList.withSize(1, ItemStack.EMPTY);
    }
    
    @Override
    public void update()
    {
        if (!this.world.isRemote)
        {
            ItemStack oxygenItemStack = this.getStackInSlot(0);
            if (oxygenItemStack != null && oxygenItemStack.getItem() instanceof IItemOxygenSupply)
            {
                IItemOxygenSupply oxygenItem = (IItemOxygenSupply) oxygenItemStack.getItem();
                int oxygenDraw = (int) Math.floor(Math.min(this.oxygenPerTick * 2.5F, this.getMaxOxygenStored() - this.getOxygenStored()));
                this.setOxygenStored(getOxygenStored() + oxygenItem.discharge(oxygenItemStack, oxygenDraw));
                if (this.getOxygenStored() > this.getMaxOxygenStored())
                {
                    this.setOxygenStored(this.getOxygenStored());
                }
            }
        }

        super.update();

        this.scaledOxygenLevel = this.getScaledOxygenLevel(16);

        if (this.scaledOxygenLevel != this.lastScaledOxygenLevel)
        {
            this.world.notifyLightSet(this.getPos());
        }

        this.lastScaledOxygenLevel = this.scaledOxygenLevel;
        
        this.produceOxygen(getFront().rotateY().getOpposite());
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, this.getInventory());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        ItemStackHelper.saveAllItems(nbt, this.getInventory());
        return nbt;
    }

    @Override
    public EnumSet<EnumFacing> getElectricalInputDirections()
    {
        return EnumSet.noneOf(EnumFacing.class);
    }

    @Override
    public EnumSet<EnumFacing> getElectricalOutputDirections()
    {
        return EnumSet.noneOf(EnumFacing.class);
    }

    @Override
    public boolean shouldPullEnergy()
    {
        return false;
    }

    @Override
    public boolean shouldUseEnergy()
    {
        return false;
    }

    @Override
    public EnumFacing getElectricInputDirection()
    {
        return null;
    }

    @Override
    public ItemStack getBatteryInSlot()
    {
        return ItemStack.EMPTY;
    }
    
    @Override
    public boolean shouldUseOxygen()
    {
        return false;
    }

    @Override
    public int getOxygenProvide(EnumFacing direction)
    {
        return this.getOxygenOutputDirections().contains(direction) ? Math.min(TileEntityAdvOxygenStorageModule.OUTPUT_PER_TICK, this.getOxygenStored()) : 0;
    }

    @Override   
    public EnumFacing getFront() {
		IBlockState state = this.world.getBlockState(getPos());
		if (state.getBlock() instanceof BlockOxygenStorageModule) {
			return (state.getValue(BlockOxygenStorageModule.FACING));
		} 
		return EnumFacing.NORTH;
	}
    
    @Override
    public int getSizeInventory()
    {
        return this.getInventory().size();
    }

    @Override
    public ItemStack getStackInSlot(int var1)
    {
        return this.getInventory().get(var1);
    }

    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        ItemStack itemstack = ItemStackHelper.getAndSplit(this.getInventory(), index, count);

        if (!itemstack.isEmpty())
        {
            this.markDirty();
        }

        return itemstack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        ItemStack oldstack = ItemStackHelper.getAndRemove(this.getInventory(), index);
        if (!oldstack.isEmpty())
        {
        	this.markDirty();
        }
    	return oldstack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        this.getInventory().set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

        this.markDirty();
    }
    
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }
    
    @Override
    public boolean isUsableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.world.getTileEntity(this.getPos()) == this && par1EntityPlayer.getDistanceSq(this.getPos().getX() + 0.5D, this.getPos().getY() + 0.5D, this.getPos().getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public boolean isItemValidForSlot(int slotID, ItemStack itemstack)
    {
        return slotID == 0 && itemstack != null && itemstack.getItem() instanceof IItemOxygenSupply;
    }

    //ISidedInventory
    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        return new int[] { 0 };
    }

    @Override
    public boolean canInsertItem(int slotID, ItemStack itemstack, EnumFacing side)
    {
        if (slotID == 0 && this.isItemValidForSlot(slotID, itemstack))
        {
            return itemstack.getItemDamage() < itemstack.getItem().getMaxDamage();
        }
        return false;
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack itemstack, EnumFacing side)
    {
        if (slotID == 0 && itemstack != null)
        {
            return FluidUtil.isEmptyContainer(itemstack);
        }
        return false;
    }

    //IFluidHandler methods - to allow this to accept Liquid Oxygen
    @Override
    public boolean canDrain(EnumFacing from, Fluid fluid)
    {
        return super.canDrain(from, fluid);
    }

    @Override
    public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain)
    {
        return super.drain(from, resource, doDrain);
    }

    @Override
    public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain)
    {
        return super.drain(from, maxDrain, doDrain);
    }

    @Override
    public boolean canFill(EnumFacing from, Fluid fluid)
    {
        return super.canFill(from, fluid);
    }

    @Override
    public int fill(EnumFacing from, FluidStack resource, boolean doFill)
    {
        return super.fill(from, resource, doFill);
    }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing from)
    {
        return super.getTankInfo(from);
    }

    @Override
    public EnumSet<EnumFacing> getOxygenInputDirections()
    {
    	 return EnumSet.of(getFront().rotateY());
    }
    
    @Override
    public EnumSet<EnumFacing> getOxygenOutputDirections()
    {
    	return EnumSet.of(getFront().rotateYCCW());
    }
}
