package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.Nullable;

import galaxyspace.core.configs.GSConfigEnergy;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockGasGenerator;
import micdoodle8.mods.galacticraft.api.transmission.NetworkType;
import micdoodle8.mods.galacticraft.api.transmission.tile.IConnector;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseUniversalElectricalSource;
import micdoodle8.mods.galacticraft.core.util.FluidUtil;
import micdoodle8.mods.galacticraft.core.wrappers.FluidHandlerWrapper;
import micdoodle8.mods.galacticraft.core.wrappers.IFluidHandlerWrapper;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;

public class TileEntityGasGenerator extends TileBaseUniversalElectricalSource implements IFluidHandlerWrapper, ISidedInventory, IConnector
{
    public static final float GENERATE_GJ_PER_TICK = 180 * GSConfigEnergy.coefficientGasGenerator;

    private static final float BASE_ACCELERATION = 0.3f;

    private final int tankCapacity = 5000;
    
    @NetworkedField(targetSide = Side.CLIENT)
    public FluidTank fuelTank = new FluidTank(this.tankCapacity);
    
    public float prevGenerateWatts = 0;

    @NetworkedField(targetSide = Side.CLIENT)
    public float heatGJperTick = 0;

    @NetworkedField(targetSide = Side.CLIENT)
    public int itemCookTime = 0;
   
    private float mod = 1.0F;
    private static List<Fuel> fuel = new ArrayList<Fuel>();
    private Fuel current_fuel;
            
    public static void registerNewFuel(Fluid fluid, int burn_time, float mod_energy, int drain_amount)
    {
    	if(!fluid.isGaseous()) return;
    	
    	fuel.add(new Fuel(fluid, burn_time, mod_energy, drain_amount));
    }
    
    public static void removeFuel(Fluid fluid)
    {
    	for(Fuel fluids : fuel)
    	{
    		if(fluids.fuel.equals(fluid))
    			fuel.remove(fluids);
    	}
    }
    
    public TileEntityGasGenerator()
    {
    	super("tile.gas_generator.name");
        this.storage.setMaxExtract(GENERATE_GJ_PER_TICK - 1);
        this.inventory = NonNullList.withSize(1, ItemStack.EMPTY); 
    }

    public int getScaledFuelLevel(int i)
    {
         return this.fuelTank.getFluidAmount() * i / this.fuelTank.getCapacity();
    }
    
    @Override
    public void update()
    {   	  
        if (this.heatGJperTick > 0)
        {
            this.receiveEnergyGC(null, (this.heatGJperTick), false);
        }
        
        super.update();

        if (!this.world.isRemote)
        {	           
        	final FluidStack liquid = FluidUtil.getFluidContained(this.getInventory().get(0));
            
			for (Fuel fuel : fuel) {
				if (FluidUtil.isFluidStrict(liquid, fuel.getFluid().getName())) {

					if (fuelTank.getFluid() == null	|| fuelTank.getFluid() != null && fuelTank.getFluid().getFluid().equals(liquid.getFluid()))
						FluidUtil.loadFromContainer(fuelTank, fuel.getFluid(), this.getInventory(), 0, liquid.amount);
					
				}
			}
   
			
			for (Fuel fuel : fuel) {
				if(fuelTank.getFluid() != null && fuelTank.getFluid().getFluid().equals(fuel.getFluid())) {
					current_fuel = fuel;
					break;
				}
			}
			
        	this.produce();
            this.smeltItem();
            
        }
      
    }

    
    public void smeltItem()
    {

    	if (this.canProcess())
        {
        	//final int lavaAmount = this.fuelTank.getFluidAmount();
            //final int fuelSpace = (this.fuelTank.getCapacity() - this.fuelTank.getFluidAmount());
/*
            for(Fuel fluid : fuel)
            {
            	if(this.fuelTank.getFluid().getFluid() == fluid.getFluid())
            	{
            		if(this.ticks % fluid.getDuration() == 0) {
            			this.fuelTank.drain(fluid.getDrainAmount(), true);
            			mod = fluid.getModificator(); 
            			  
            		}
            		break;            		
            	}
            }*/
            
    		if(this.fuelTank.getFluid().getFluid() == current_fuel.getFluid())
        	{
        		if(this.ticks % current_fuel.getDuration() == 0) {
        			this.fuelTank.drain(current_fuel.getDrainAmount(), true);
        			mod = current_fuel.getModificator(); 
        			  
        		}           		
        	}
    		
            this.heatGJperTick = GENERATE_GJ_PER_TICK * mod;
            this.storage.setMaxExtract(GENERATE_GJ_PER_TICK * mod + 1);
     
        }
    	
    }
    
    public boolean canProcess()
    {    	
    	if(current_fuel == null) return false;
    	
    	if(this.fuelTank.getFluid() != null && !this.fuelTank.getFluid().getFluid().isGaseous()) return false;
    	
        if (this.fuelTank.getFluidAmount() <= current_fuel.getDrainAmount())
        {        	
        	this.heatGJperTick = 0;
        	return false;
        }
        
        if(this.storage.getEnergyStoredGC() >= this.storage.getCapacityGC())
        	return false;

        return true;

    }
    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        this.itemCookTime = par1NBTTagCompound.getInteger("itemCookTime");
        this.heatGJperTick = par1NBTTagCompound.getInteger("generateRateInt");
        
        this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(par1NBTTagCompound, this.getInventory());

        
        if (par1NBTTagCompound.hasKey("fuelTank"))
        {
            this.fuelTank.readFromNBT(par1NBTTagCompound.getCompoundTag("fuelTank"));
        }
        
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("itemCookTime", this.itemCookTime);
        par1NBTTagCompound.setFloat("generateRate", this.heatGJperTick);
        if (this.fuelTank.getFluid() != null)
        {
            par1NBTTagCompound.setTag("fuelTank", this.fuelTank.writeToNBT(new NBTTagCompound()));
        }
        
        ItemStackHelper.saveAllItems(par1NBTTagCompound, this.getInventory());        
        return par1NBTTagCompound;
        
        
    }

    @Override
    public int getSizeInventory()
    {
        return this.getInventory().size();
    }

    @Override
    public ItemStack getStackInSlot(int par1)
    {
        return this.getInventory().get(par1);
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
        return false;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        return new int[] { 0 };
    }

    @Override
    public boolean canInsertItem(int slotID, ItemStack itemstack, EnumFacing direction)
    {
        return this.isItemValidForSlot(slotID, itemstack);
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack itemstack, EnumFacing direction)
    {
        return slotID == 0;
    }

    @Override
    public float receiveElectricity(EnumFacing from, float energy, int tier, boolean doReceive)
    {
        return 0;
    }

    @Override
    public EnumSet<EnumFacing> getElectricalInputDirections()
    {
        return EnumSet.noneOf(EnumFacing.class);
    }

    @Override
    public EnumSet<EnumFacing> getElectricalOutputDirections()
    {
        return EnumSet.of(this.getElectricOutputDirection());
    }

    public EnumFacing getFront()
    {
        IBlockState state = this.world.getBlockState(getPos()); 
        if (state.getBlock() instanceof BlockGasGenerator)
        {
            return state.getValue(BlockGasGenerator.FACING);
        }
        return EnumFacing.NORTH;
    }
    
    @Override
    public EnumFacing getElectricOutputDirection()
    {
        return getFront().rotateYCCW();
    }

    private EnumFacing getPipe()
    {
        return getFront().rotateY();
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
            return direction == this.getElectricOutputDirection();
        }
        if (type == NetworkType.FLUID)
        {
            return direction == this.getPipe();
        }
        return false;
    }
    
    @Override
    public boolean canDrain(EnumFacing from, Fluid fluid)
    {
        return false;
    }

    @Override
    public int fill(EnumFacing from, FluidStack resource, boolean doFill)
    {
        int used = 0;

        if (this.getPipe().equals(from) && resource != null)
        {
            final String liquidName = FluidRegistry.getFluidName(resource);

            if(liquidName != null)
            {
            	 for(Fuel fluid : fuel)
                 {
                 	if(liquidName.startsWith(fluid.getFluid().getName()))
                 	{
                 		used = this.fuelTank.fill(resource, doFill);
                 		break;            		
                 	}
                 }       
            }
        }

        return used;
    }

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) 
	{
		return null;
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain)
	{
		return null;
	}

	@Override
    public boolean canFill(EnumFacing from, Fluid fluid)
    {
		if (from == this.getPipe())
        {			
            return this.fuelTank.getFluid() == null || this.fuelTank.getFluidAmount() < this.fuelTank.getCapacity();
        }

        return false;
    }

	@Override
    public FluidTankInfo[] getTankInfo(EnumFacing from)
    {
        return new FluidTankInfo[] { new FluidTankInfo(this.fuelTank) };
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
    
    ////////////////////////////////////////////////////////////////////////////////////////
    
    private static class Fuel {
    	private Fluid fuel;
    	private int duration;
    	private float modificator;
    	private int drain_amount;
    	
    	Fuel(Fluid fluid, int duration, float modificator, int drain)
    	{
    		this.fuel = fluid;
    		this.duration = duration;
    		this.modificator = modificator;
    		this.drain_amount = drain;
    	}
    	
    	public Fluid getFluid()
    	{
    		return this.fuel;
    	}
    	
    	public int getDuration()
    	{
    		return this.duration;
    	}
    	
    	public float getModificator()
    	{
    		return this.modificator;
    	}
    	
    	public int getDrainAmount() {
    		return this.drain_amount;
    	}
    }
	
}
