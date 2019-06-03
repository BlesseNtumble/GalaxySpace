package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import javax.annotation.Nullable;

import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockGasBurner;
import micdoodle8.mods.galacticraft.api.transmission.NetworkType;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.galacticraft.core.network.IPacketReceiver;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.OxygenUtil;
import micdoodle8.mods.galacticraft.core.wrappers.FluidHandlerWrapper;
import micdoodle8.mods.galacticraft.core.wrappers.IFluidHandlerWrapper;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;

public class TileEntityGasBurner extends TileBaseElectricBlockWithInventory implements IFluidHandlerWrapper, ISidedInventory, IPacketReceiver{

	public static final int PROCESS_TIME_REQUIRED_BASE = 20;
    @NetworkedField(targetSide = Side.CLIENT)
    public int processTimeRequired = PROCESS_TIME_REQUIRED_BASE;
    @NetworkedField(targetSide = Side.CLIENT)
    public int processTicks = 0;
    
    private final int tankCapacity = 3000;
    
    @NetworkedField(targetSide = Side.CLIENT)
    public FluidTank gasTank = new FluidTank(this.tankCapacity);
    
    private NonNullList<ItemStack> stacks = NonNullList.withSize(2, ItemStack.EMPTY);

    
    public TileEntityGasBurner()
    {
    	this.storage.setCapacity(15000);
        this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 45 : 25);
        this.setTierGC(1);
    }
    
    @Override
    public void update()
    {
        super.update();

        if (!this.world.isRemote)
        {
        	if (this.canProcess())
            {
        		
    			if (this.hasEnoughEnergyToRun)
                {
    				
    				if (this.processTicks == 0)
                    {
                        this.processTicks = this.processTimeRequired;
                    }
                    else
                    {
                        if (--this.processTicks <= 0)
                        {
                            this.smeltItem();
                            this.processTicks = this.canProcess() ? this.processTimeRequired : 0;
                        }
                    }
                }
                else if (this.processTicks > 0 && this.processTicks < this.processTimeRequired)
                {
                    //Apply a "cooling down" process if the electric furnace runs out of energy while smelting
                    if (this.world.rand.nextInt(4) == 0)
                    {
                        this.processTicks++;
                    }
                }
            }
            else
            {
                this.processTicks = 0;
            }
        }
    }
    
    public boolean canProcess()
    {
    	if(this.gasTank.getFluidAmount() <= 0) 
    		return false;  	
		
    	return true;
    }
    
    public void smeltItem()
    {
    	if(OxygenUtil.isAABBInBreathableAirBlock(world, new AxisAlignedBB(getPos().add(0, 1, 0))))
    	{
    		this.world.setBlockState(this.getPos().up(), Blocks.FIRE.getDefaultState());
    	}    	
    	this.gasTank.drain(100, true);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        
        this.processTicks = par1NBTTagCompound.getInteger("smeltingTicks");
        this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        
        if (par1NBTTagCompound.hasKey("gasTank"))        
            this.gasTank.readFromNBT(par1NBTTagCompound.getCompoundTag("gasTank"));
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
       	super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("smeltingTicks", this.processTicks);
        ItemStackHelper.saveAllItems(par1NBTTagCompound, this.stacks);
       
        if (this.gasTank.getFluid() != null)        
            par1NBTTagCompound.setTag("gasTank", this.gasTank.writeToNBT(new NBTTagCompound()));
        
        return par1NBTTagCompound;
    }
    
    @Override
    public boolean hasCustomName()
    {
        return true;
    }
    
    @Override
    public String getName()
    {
        return GCCoreUtil.translate("tile.gas_burner.name");
    }
    
    @Override
    public int getSizeInventory()
    {
        return this.stacks.size();
    }

    @Override
    public ItemStack getStackInSlot(int var1)
    {
        return this.stacks.get(var1);
    }
    
    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        return new int[] { 0 };
    }
    
    @Override
    public boolean canInsertItem(int slotID, ItemStack itemstack, EnumFacing side)
    {
        return this.isItemValidForSlot(slotID, itemstack);
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack itemstack, EnumFacing side)
    {
        return slotID == 3;
    }

    @Override
    public boolean isItemValidForSlot(int slotID, ItemStack itemstack)
    {
    	switch (slotID)
        {
        case 0:
            return ItemElectricBase.isElectricItem(itemstack.getItem());
        }

        return false;
    }

	@Override
	protected NonNullList<ItemStack> getContainingItems() {
		return this.stacks;
	}
	
	@Override
	public boolean shouldUseEnergy() {
		return this.canProcess();
	}

	@Override
    public ItemStack getBatteryInSlot() {
        return this.getStackInSlot(0);
    }
	
	@Override
	public boolean canFill(EnumFacing from, Fluid fluid) {
		if(from == this.getPipe() && fluid.isGaseous())
		{
			return this.gasTank.getFluid() == null || this.gasTank.getFluidAmount() < this.gasTank.getCapacity();
		}
		return false;
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid) {		
		return false;
	}
	
	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
		int used = 0;
		
		if (resource != null && this.canFill(from, resource.getFluid()))       
	    {	  
    		if(from == this.getPipe()) {
 	            final String liquidName = FluidRegistry.getFluidName(resource);	
 	            if (liquidName != null) 
 	            	used = this.gasTank.fill(resource, doFill);
    		}
	    }
    		
		return used;
	}
	
	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
		return null;
	}
	
	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
		return null;
	}
	
	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from) {

		return new FluidTankInfo[] { new FluidTankInfo(this.gasTank) };
	}
	
	@Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }
	
	@Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return (T) new FluidHandlerWrapper(this, facing);
        }
        return super.getCapability(capability, facing);
    
    }
	
	public EnumFacing getFront() {
		IBlockState state = this.world.getBlockState(getPos());
		if (state.getBlock() instanceof BlockGasBurner) {
			return state.getValue(BlockGasBurner.FACING);
		}
		return EnumFacing.NORTH;
	}
	
	@Override
    public boolean canConnect(EnumFacing direction, NetworkType type)
    {
        if (direction == null)
        {
            return false;
        } 
        if (type == NetworkType.POWER)
        {
            return direction == this.getElectricInputDirection();
        }
        if (type == NetworkType.FLUID)
        {
            EnumFacing pipeSide = getPipe();
            return direction == pipeSide;
        }
        return false;
    }
	
	private EnumFacing getPipe()
    {
        return getFront().rotateYCCW();
    }
	
	@Override
	public EnumFacing getElectricInputDirection() {
		//return getFront().rotateY();
		return EnumFacing.DOWN;
	}
    
	public int getScaledTankLevel(int i)
    {
         return this.gasTank.getFluidAmount() * i / this.gasTank.getCapacity();
    }
}
