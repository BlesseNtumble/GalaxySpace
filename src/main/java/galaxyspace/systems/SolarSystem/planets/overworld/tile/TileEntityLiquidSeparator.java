package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import javax.annotation.Nullable;

import galaxyspace.core.GSFluids;
import galaxyspace.core.GSItems;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockLiquidSeparator;
import micdoodle8.mods.galacticraft.api.transmission.NetworkType;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.galacticraft.core.network.IPacketReceiver;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.FluidUtil;
import micdoodle8.mods.galacticraft.core.wrappers.FluidHandlerWrapper;
import micdoodle8.mods.galacticraft.core.wrappers.IFluidHandlerWrapper;
import micdoodle8.mods.galacticraft.planets.asteroids.AsteroidsModule;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;

public class TileEntityLiquidSeparator extends TileBaseElectricBlockWithInventory implements IFluidHandlerWrapper, ISidedInventory, IPacketReceiver {

	int PROCESS_TIME_REQUIRED = 50;

    @NetworkedField(targetSide = Side.CLIENT)
    public int processTimeRequired = PROCESS_TIME_REQUIRED;

    @NetworkedField(targetSide = Side.CLIENT)
    public int processTicks = 0;

    private final int tankCapacity = 3000;
    @NetworkedField(targetSide = Side.CLIENT)
    public FluidTank baseTank = new FluidTank(this.tankCapacity);
    
    @NetworkedField(targetSide = Side.CLIENT)
    public FluidTank waterTank1 = new FluidTank(this.tankCapacity);
    
    @NetworkedField(targetSide = Side.CLIENT)
    public FluidTank waterTank2 = new FluidTank(this.tankCapacity);
    
    @NetworkedField(targetSide = Side.CLIENT)
    public boolean reverse;
    
    //TODO: NEED rewrite to class for add support custom recipe;
    public enum TankLiquids
    {
    	/*ETANEMETHANE(0, "liquidethanemethane", "methane", 5, "ethane", 1),
    	WATER(1, "water", "hydrogen", 2, "liquidoxygen", 1),
    	HEAVYWATER(2, "heavywater", "liquidoxygen", 1, "deuterium", 1),
    	HYDROGEN2(3, "hydrogen2", "liquidoxygen", 1, "deuterium", 1),
    	//ROCKETFUEL2(4, "heliumhydrogen", "helium3", 2, "hydrogen", 8),*/
    	
    	ETANEMETHANE(0, new FluidStack(GSFluids.LiquidEthaneMethane, 10), new FluidStack(AsteroidsModule.fluidMethaneGas ,5), FluidRegistry.getFluidStack("ethane", 5)),
    	WATER(1, new FluidStack(FluidRegistry.WATER, 50), FluidRegistry.getFluidStack("hydrogen", 100), FluidRegistry.getFluidStack("oxygen", 50)),
    	ROCKETFUEL22(2, new FluidStack(GSFluids.HeliumHydrogen, 10), new FluidStack(GSFluids.Helium3, 2), FluidRegistry.getFluidStack("hydrogen", 8));
    	
    	int index;  
        FluidStack input, output1, output2;

    	TankLiquids(int id, FluidStack input, FluidStack output1, FluidStack output2)
    	{
    		this.index = id;
    		this.input = input;
    		this.output1 = output1;
    		this.output2 = output2;
    	}
    	
    	
    	public FluidStack getFluid()
    	{
    		return this.input;
    	}
    	
    	public FluidStack getOutFluid()
    	{
    		return this.output1;
    	}
    	
    	public FluidStack getOutFluid2()
    	{
    		return this.output2;
    	}
    }
    
    public TileEntityLiquidSeparator()
    {
    	super("tile.liquid_separator.name");        
        this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 60 : 45);
        this.inventory = NonNullList.withSize(4 + 4, ItemStack.EMPTY);
    }
    
    @Override
    public void update()
    {
        super.update();
        if (!this.world.isRemote)
        {
        	if(!this.getReverse())
        	{
        		GSUtils.checkFluidTankTransfer(this.getInventory(), 1, this.waterTank2);
        		GSUtils.checkFluidTankTransfer(this.getInventory(), 3, this.waterTank1);
	        	
	        	if (!this.getInventory().get(2).isEmpty()) {
	        		FluidStack liquid = FluidUtil.getFluidContained(this.getInventory().get(2));
					if (liquid != null) {						
						FluidUtil.loadFromContainer(this.baseTank, liquid.getFluid(), this.getInventory(), 2, liquid.amount);
					}
	        	}
        	}
        	else 
        	{
        		GSUtils.checkFluidTankTransfer(this.getInventory(), 2, this.baseTank);
        		  
        		if (!this.getInventory().get(1).isEmpty()) {
	        		FluidStack liquid = FluidUtil.getFluidContained(this.getInventory().get(1));
					if (liquid != null) {						
						FluidUtil.loadFromContainer(this.waterTank2, liquid.getFluid(), this.getInventory(), 1, liquid.amount);
					}
	        	}
        		
        		if (!this.getInventory().get(3).isEmpty()) {
	        		FluidStack liquid = FluidUtil.getFluidContained(this.getInventory().get(3));
					if (liquid != null) {						
						FluidUtil.loadFromContainer(this.waterTank1, liquid.getFluid(), this.getInventory(), 3, liquid.amount);
					}
	        	}
        		
        	}
        	
        	if (this.hasEnoughEnergyToRun)
            {
        		if (this.canProcess())
                {
        			//////////
                	int boost_speed = 0;
                	int energy_boost = 0;
                	
                	for(int i = 0; i <= 3; i++)
                	{
                		if(this.getInventory().get(getInventory().size() - i - 1).isItemEqual(new ItemStack(GSItems.UPGRADES, 1, 2)))
                			boost_speed++;
                		if(this.getInventory().get(getInventory().size() - i - 1).isItemEqual(new ItemStack(GSItems.UPGRADES, 1, 3)))
                			energy_boost++;
                	}
                	
                    this.processTicks += 2 + boost_speed;
                    this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 90 + (40 * boost_speed) - (20 * energy_boost): 75 + (35 * boost_speed) - (15 * energy_boost));
                    /////////////

                    if (this.processTicks >= processTimeRequired)
                    {
                        this.processTicks = 0;
                        this.smeltItem();
                    }
                }
				else {
					this.processTicks = 0;
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
    	if(!this.getReverse())
    	{
    		if(this.baseTank.getFluidAmount() <= 0) return false;
    		
    		if(this.waterTank1.getFluidAmount() >= this.waterTank1.getCapacity() || this.waterTank2.getFluidAmount() >= this.waterTank2.getCapacity())
    			return false;
    	}
    	else
    	{
    		if(this.waterTank1.getFluidAmount() <= 0 && this.waterTank2.getFluidAmount() <= 0) return false;
    		if(this.baseTank.getFluidAmount() >= this.baseTank.getCapacity()) return false;
    		if(this.waterTank1.getFluid() == null || this.waterTank2.getFluid() == null) return false;
    		
    		if(FluidRegistry.getFluidName(this.waterTank1.getFluid()) == null || FluidRegistry.getFluidName(this.waterTank2.getFluid()) == null) return false;

    	}
      	
    	return true;
    }
    
    public void smeltItem()
    {
        if (this.canProcess())
        {	  
        	int i = 0;
        	TankLiquids[] ar = TankLiquids.values();
        	
        	if(!this.getReverse())
        	{
	        	
	        	for(int j = 0; j < ar.length; j++)
	        	{
	        		if(ar[j].getFluid().equals(this.baseTank.getFluid()))
	        			i = j;
	        	}	
	        		
	        	
		        if(this.baseTank.getFluid().equals(ar[i].getFluid()))
		        {
		        	if(ar[i].getOutFluid() != null) {
			        	this.waterTank1.fill(ar[i].getOutFluid(), true);
			        	if(ar[i].getOutFluid2() != null) 
			        		this.waterTank2.fill(ar[i].getOutFluid2(), true);
			        	this.baseTank.drain(ar[i].getFluid().amount, true);
		        	}
		        }
        	}       	
        	else
        	{        		
	        	for(int j = 0; j < ar.length; j++)
	        	{
        			if(ar[j].getOutFluid().equals(this.waterTank1.getFluid()) && ar[j].getOutFluid2().equals(this.waterTank2.getFluid()))
	        			i = j;
        			else if(ar[j].getOutFluid().equals(this.waterTank2.getFluid()) && ar[j].getOutFluid2().equals(this.waterTank1.getFluid()))
	        			i = j;
	        	}
	        	
	        	if(this.waterTank1.getFluid().equals(ar[i].getOutFluid()) && this.waterTank2.getFluid().equals(ar[i].getOutFluid2()))
		        {	        		
			        this.baseTank.fill(ar[i].getFluid(), true);
			        this.waterTank1.drain(ar[i].getOutFluid().amount, true);
			        this.waterTank2.drain(ar[i].getOutFluid2().amount, true);
		        }
	        	
	        	else if(this.waterTank2.getFluid().equals(ar[i].getOutFluid()) && this.waterTank1.getFluid().equals(ar[i].getOutFluid2()))
	        	{
	        		this.baseTank.fill(ar[i].getFluid(), true);
			        this.waterTank1.drain(ar[i].getOutFluid2().amount, true);
			        this.waterTank2.drain(ar[i].getOutFluid().amount, true);
	        	}
        	}
        }
    }
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        
        this.processTicks = par1NBTTagCompound.getInteger("smeltingTicks");
        ItemStackHelper.loadAllItems(par1NBTTagCompound, this.getInventory());
        
        this.reverse = par1NBTTagCompound.getBoolean("reverse");
        
        if (par1NBTTagCompound.hasKey("baseTank"))        
        	this.baseTank.readFromNBT(par1NBTTagCompound.getCompoundTag("baseTank"));
        if (par1NBTTagCompound.hasKey("waterTank1"))        
        	this.waterTank1.readFromNBT(par1NBTTagCompound.getCompoundTag("waterTank1"));
        if (par1NBTTagCompound.hasKey("waterTank2"))        
            this.waterTank2.readFromNBT(par1NBTTagCompound.getCompoundTag("waterTank2"));
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("smeltingTicks", this.processTicks);
        ItemStackHelper.saveAllItems(par1NBTTagCompound, this.getInventory());
        
        par1NBTTagCompound.setBoolean("reverse", this.reverse);
        
        if (this.baseTank.getFluid() != null)        
            par1NBTTagCompound.setTag("baseTank", this.baseTank.writeToNBT(new NBTTagCompound()));
        
        if (this.waterTank1.getFluid() != null)        
            par1NBTTagCompound.setTag("waterTank1", this.waterTank1.writeToNBT(new NBTTagCompound()));
        
        if (this.waterTank2.getFluid() != null)        
            par1NBTTagCompound.setTag("waterTank2", this.waterTank2.writeToNBT(new NBTTagCompound()));
        
        return par1NBTTagCompound;
    }

    
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        return new int[] { 0, 1, 2 };
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
		return this.processTicks > 0;
	}
    
    private EnumFacing getPipe()
    {
        return getFront().rotateYCCW();
    }
    
    private EnumFacing getPipeUp()
    {
        return getFront().UP;
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
            return direction == getPipe() || direction == getFront().rotateY() || direction == getPipeUp();
        }
        return false;
    }
    
    @Override
	public boolean canFill(EnumFacing from, Fluid fluid) {
    	    	
    	if(!this.getReverse() && from == this.getPipeUp())
    	{    		
			return this.baseTank.getFluid() == null || this.baseTank.getFluidAmount() < this.baseTank.getCapacity();
    	}
    	else if(this.getReverse())
    	{
    		if(from == this.getPipe())
    		{
    			return this.waterTank1.getFluid() == null || this.waterTank1.getFluidAmount() < this.waterTank1.getCapacity();
    		}
    		else if(from == this.getFront().rotateY())
    		{
    			return this.waterTank2.getFluid() == null || this.waterTank2.getFluidAmount() < this.waterTank2.getCapacity();
    	    	
    		}
    	}
		return false;
    }
    
    @Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {

    	int used = 0;
        if(!this.getReverse())
        {
	        if (resource != null && this.canFill(from, resource.getFluid()))       
	        {	        	
	            final String liquidName = FluidRegistry.getFluidName(resource);	
	            if (liquidName != null) used = this.baseTank.fill(resource, doFill);
	        }
        }
        else
        {
        	if (resource != null && this.canFill(from, resource.getFluid()))       
 	        {	  
        		if(from == this.getPipe()) {
	 	            final String liquidName = FluidRegistry.getFluidName(resource);	
	 	            if (liquidName != null) 
	 	            	used = this.waterTank1.fill(resource, doFill);
        		}
        		
        		else if(from == this.getFront().rotateY()) {
	 	            final String liquidName = FluidRegistry.getFluidName(resource);	
	 	            if (liquidName != null) 
	 	            	used = this.waterTank2.fill(resource, doFill);
        		}
 	        }
        }       
        
		return used;
	}
    
    @Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
        
        if(!this.getReverse())
        {
	        if (from == this.getFront().rotateY())
	        {
	            if (resource != null && resource.isFluidEqual(this.waterTank2.getFluid()))
	                return this.waterTank2.drain(resource.amount, doDrain);
	        }
	        
	        if (from == getPipe())
	        {
	            if (resource != null && resource.isFluidEqual(this.waterTank1.getFluid()))
	                return this.waterTank1.drain(resource.amount, doDrain);
	        }
        }
        else
        {
        	if (from == getPipe().UP) 
        		 if (resource != null && resource.isFluidEqual(this.baseTank.getFluid()))
 	                return this.baseTank.drain(resource.amount, doDrain);
        }
    	return null;
    }
    
    @Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {

        if(!this.getReverse())
        {
	        if (from == this.getFront().rotateY())
	        {
	            return this.waterTank2.drain(maxDrain, doDrain);
	        }
	        
	        if (from == getPipe())
	        {
	            return this.waterTank1.drain(maxDrain, doDrain);
	        }
        }        
        else
        {
        	if (from == getPipe().UP) 
	        	return  this.baseTank.drain(maxDrain, doDrain);
        }
        
		return null;
    }
        
    @Override
	public boolean canDrain(EnumFacing from, Fluid fluid) {
    
        if(!this.getReverse())
        {
	        if (from == getPipe())
	            return this.waterTank1.getFluid() != null && this.waterTank1.getFluidAmount() > 0;
	            
	        if (from == this.getFront().rotateY())
	        	return this.waterTank2.getFluid() != null && this.waterTank2.getFluidAmount() > 0;
        }   
        else
        {
        	if (from == getPipe().UP) 
	        	return this.baseTank.getFluid() != null && this.baseTank.getFluidAmount() > 0;
        }
        return false;
    }
    
    @Override
	public FluidTankInfo[] getTankInfo(EnumFacing from) {
    	FluidTankInfo[] tankInfo = new FluidTankInfo[] { };
    	if(from == getPipe().UP) tankInfo = new FluidTankInfo[] { new FluidTankInfo(this.baseTank) };
    	else if(from == getPipe()) tankInfo = new FluidTankInfo[] { new FluidTankInfo(this.waterTank1) };
    	else if(from == this.getFront().rotateY()) tankInfo = new FluidTankInfo[] { new FluidTankInfo(this.waterTank2) };
    	
    	return tankInfo;
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
	
	@Override
	public EnumFacing getElectricInputDirection() {
		//return getFront().rotateY();
		return EnumFacing.DOWN;
	}
	
	@Override
	public EnumFacing getFront()
    {
        IBlockState state = this.world.getBlockState(getPos()); 
        if (state.getBlock() instanceof BlockLiquidSeparator)
        {
            return state.getValue(BlockLiquidSeparator.FACING);
        }
        return EnumFacing.NORTH;
    }
	
    public void setReverse(boolean rev)
    {
    	this.reverse = rev;
    }   
    
    public boolean getReverse()
    {
    	return this.reverse;
    }
    
    public int getScaledTankLevel(FluidTank tank, int i)
    {
         return tank.getFluidAmount() * i / tank.getCapacity();
    }
}
