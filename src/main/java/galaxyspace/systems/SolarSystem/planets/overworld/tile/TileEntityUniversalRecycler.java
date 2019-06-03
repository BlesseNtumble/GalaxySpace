package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.fluids.GSFluids;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockUniversalRecycler;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.RecyclerRecipes;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.RecyclerRecipes.RecycleRecipe;
import micdoodle8.mods.galacticraft.api.transmission.NetworkType;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.galacticraft.core.network.IPacketReceiver;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.wrappers.FluidHandlerWrapper;
import micdoodle8.mods.galacticraft.core.wrappers.IFluidHandlerWrapper;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
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
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;

public class TileEntityUniversalRecycler extends TileBaseElectricBlockWithInventory implements IFluidHandlerWrapper, ISidedInventory, IPacketReceiver{

	public static final int PROCESS_TIME_REQUIRED_BASE = 130;
    @NetworkedField(targetSide = Side.CLIENT)
    public int processTimeRequired = PROCESS_TIME_REQUIRED_BASE;
    @NetworkedField(targetSide = Side.CLIENT)
    public int processTicks = 0;
    
    private final int tankCapacity = 3000;
    @NetworkedField(targetSide = Side.CLIENT)
    public FluidTank waterTank = new FluidTank(this.tankCapacity);
    @NetworkedField(targetSide = Side.CLIENT)
    public FluidTank waterTank1 = new FluidTank(this.tankCapacity);
    @NetworkedField(targetSide = Side.CLIENT)
    public FluidTank waterTank2 = new FluidTank(this.tankCapacity);
    
    private NonNullList<ItemStack> stacks = NonNullList.withSize(5 + 4, ItemStack.EMPTY);

    
    public TileEntityUniversalRecycler()
    {
    	this.storage.setCapacity(20000);
        this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 90 : 75);
        this.setTierGC(1);
    }
    
    @Override
    public void update()
    {
        super.update();

        if (!this.world.isRemote)
        {
        	GSUtils.checkFluidTankTransfer(this.stacks, 2, this.waterTank);
        	
        	if (!this.stacks.get(4).isEmpty())
            {
            	 FluidStack liquid = FluidUtil.getFluidContained(this.stacks.get(4));
            		
	                if (liquid != null)
	                {
	                	boolean isWater = FluidRegistry.getFluidName(liquid).startsWith("liquidethanemethane");
		
		                if (isWater)
		                {
		                    if (this.waterTank.getFluid() == null || this.waterTank.getFluid().amount + liquid.amount <= this.waterTank.getCapacity())
		                    {
		                        this.waterTank.fill(new FluidStack(GSFluids.LiquidEthaneMethane, liquid.amount), true);
		
		                        if (micdoodle8.mods.galacticraft.core.util.FluidUtil.isBucket(this.stacks.get(4)) && micdoodle8.mods.galacticraft.core.util.FluidUtil.isFilledContainer(this.stacks.get(4)))
		                        {
		                            final int amount = this.stacks.get(4).getCount();
		                            if (amount > 1) this.waterTank.fill(new FluidStack(GSFluids.LiquidEthaneMethane, (amount - 1) * Fluid.BUCKET_VOLUME), true);
		                            this.stacks.set(4, new ItemStack(Items.BUCKET, amount));
		                        }
		                        else
		                        {
		                        	this.stacks.get(4).shrink(1);
		
		                            if (this.stacks.get(4).getCount() == 0)
		                            {
		                            	this.stacks.set(4, ItemStack.EMPTY);
		                            }
		                        }
		                    }
		                }
	                }
            }
        	
            if (this.canProcess())
            {
            	if (this.hasEnoughEnergyToRun)
                {
            	   	int boost_speed = 0, energy_boost = 0;
            	   	
            	   	//////////

                	for(int i = 0; i <= 3; i++)
                	{
                		if(this.stacks.get(4 + i).isItemEqual(new ItemStack(GSItems.UPGRADES, 1, 2)))
                			boost_speed++;
                		if(this.stacks.get(4 + i).isItemEqual(new ItemStack(GSItems.UPGRADES, 1, 3)))
                			energy_boost++;
                	}
                	
                    this.processTicks += 1 * (1 + boost_speed);
                    this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 90 + (60 * boost_speed) - (20 * energy_boost) : 75 + (55 * boost_speed) - (15 * energy_boost));
                    /////////////
                    
                    //50% extra speed boost for Tier 2 machine if powered by Tier 2 power
                    //if (this.tierGC == 2) this.processTimeRequired = 200 / (1 + this.poweredByTierGC);

                    
                    if (this.processTicks >= this.processTimeRequired)
                    {
                    	this.smeltItem();   
                        this.processTicks = 0;
                    }
                    
                }
                /*else if (this.processTicks > 0 && this.processTicks < this.processTimeRequired)
                {
                    //Apply a "cooling down" process if the electric furnace runs out of energy while smelting
                    if (this.world.rand.nextInt(4) == 0)
                    {
                        this.processTicks++;
                    }
                }*/
            }            
            else
            {
                this.processTicks = 0;
            }
            
        }
    }
      
    public boolean canProcess()
    {
    	//FluidStack fluid1 = RecyclerRecipes.recycling().getRecyclingFluid1(this.waterTank1.getFluid());
    	//if(fluid1 != null) GalaxySpace.debug(fluid1.getFluid().getName() + "");
    	
    	if(this.stacks.get(1).isEmpty())
    	{
    		return false;
    	}
    	else
    	{    		
    		ItemStack input = this.stacks.get(1);
    		//Map<ItemStack, RecycleRecipe> recipes = RecyclerRecipes.recycling().getRecipes(); 
    		//for(Entry<ItemStack, RecycleRecipe> recipe : recipes.entrySet())
    		RecycleRecipe recipe = RecyclerRecipes.recycling().getRecipe(input);
    		if(recipe != null)
    		{   	    					
    			if(input.getCount() < recipe.getInput().getCount()) return false;
    			
    			if(recipe.getFluidStack() != null)
    			{
    				if (this.waterTank.getFluidAmount() > 0 && !this.waterTank.getFluid().isFluidEqual(recipe.getFluidStack()))
    					return false;
    				
    				if (this.waterTank.getFluidAmount() >= this.waterTank.getCapacity())
    					return false;
    			}
    			
    			if(!recipe.getOutput().isEmpty())
    			{
    				if (!this.stacks.get(3).isEmpty() && !this.stacks.get(3).isItemEqual(recipe.getOutput()))
    					return false;
    				
    				int result = this.stacks.get(3).getCount() + recipe.getOutput().getCount();
    				return result <= getInventoryStackLimit() && result <= this.stacks.get(3).getMaxStackSize();
    			}

    		}
    		else return false;

			    		
    	}
    	
    	return true;
    }
    
    public void smeltItem()
    {
    	
        if (this.canProcess())
        {
        	ItemStack input = this.stacks.get(1);
    		//List<RecycleRecipe> recyclerecipes = RecyclerRecipes.recycling().getRecipes(); 
    		RecycleRecipe recipe = RecyclerRecipes.recycling().getRecipe(input);
    		
    		/*for(RecycleRecipe recipes : recyclerecipes)
    		{
    			if(recipes.getInput().isItemEqual(input))
    			{
    				recipe = recipes;
    				break;
    			}
    		}*/
    		
    		if(recipe != null)
    		{
    			boolean hasRand = recipe.hasChance();
    			
    				
    			if(!hasRand) 
    			{
	    			if(this.stacks.get(3).isEmpty())
	           			this.stacks.set(3, recipe.getOutput().copy());	 
	    			else if(this.stacks.get(3).isItemEqual(recipe.getOutput()))
	    				this.stacks.get(3).grow(recipe.getOutput().getCount());  
    			}
	    		else
	    		{
	    			if(this.stacks.get(3).isEmpty())
	    			{
	    				if(this.world.rand.nextInt(100) <= recipe.getChance()) 
	    					this.stacks.set(3, recipe.getOutput().copy());	
	    			}
	    			else if(this.stacks.get(3).isItemEqual(recipe.getOutput()))
	    			{
	    				if(this.world.rand.nextInt(100) <= recipe.getChance()) 
	    					this.stacks.get(3).grow(recipe.getOutput().getCount());
	    			}
	    		}
    				
    	        this.stacks.get(1).shrink(recipe.getInput().getCount());
    	        
    	        //if (this.stacks.get(1).getCount() <= 0)
    	        	//this.stacks.set(1, ItemStack.EMPTY);
    	        
    	        if(recipe.getFluidStack() != null)
            		this.waterTank.fill(new FluidStack(recipe.getFluidStack().getFluid(), recipe.getFluidStack().amount > this.waterTank.getCapacity() ? this.waterTank.getCapacity() : recipe.getFluidStack().amount), true);           
            	
    			
    		}
        /*	ItemStack first = RecyclerRecipes.recycling().getRecyclingSlot(this.stacks.get(1));        	
        	ItemStack itemstack = RecyclerRecipes.recycling().getRecyclingResult(this.stacks.get(1));
        	
        	int count = RecyclerRecipes.recycling().getRecyclingCount(this.stacks.get(1));
        	Fluid fluid = RecyclerRecipes.recycling().getRecyclingFluid(this.stacks.get(1));
        	 
        	if(itemstack != null)
        	{    
        		
        		boolean rand = RecyclerRecipes.recycling().getRecyclingRand(first);
        		int randChance = RecyclerRecipes.recycling().getRecyclingRandChance(first);
        		
	        	if (this.stacks.get(3).isEmpty())
	        	{
	        		GalaxySpace.debug("" + itemstack);
	        		if(!rand) 
	        			this.stacks.set(3, itemstack);	        		
	        		else if(this.world.rand.nextInt(randChance) == 0) 
	        			this.stacks.set(3, itemstack);	
	        	}	            
	            else if (this.stacks.get(3).getItem() == itemstack.getItem() && !rand)	 
	            {
	                if(!rand) 
	                	this.stacks.get(3).grow(itemstack.getCount());       	
	                else if(this.world.rand.nextInt(randChance) == 0) 
	                	this.stacks.get(3).grow(itemstack.getCount());
	            }
	        }
	        if(this.stacks.get(1).getCount() >= first.getCount())
	        	this.stacks.get(1).shrink(first.getCount());
	
	        if (this.stacks.get(1).getCount() <= 0)
	        	this.stacks.set(1, ItemStack.EMPTY);

        	if(count > 0 && fluid != null)
        		this.waterTank.fill(new FluidStack(fluid, count > this.waterTank.getCapacity() ? this.waterTank.getCapacity() : count), true);           
        	
        	*/
        	
        }        
        
    }
    
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        
        this.processTicks = par1NBTTagCompound.getInteger("smeltingTicks");
        this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        
        if (par1NBTTagCompound.hasKey("waterTank"))        
            this.waterTank.readFromNBT(par1NBTTagCompound.getCompoundTag("waterTank"));
        
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
        ItemStackHelper.saveAllItems(par1NBTTagCompound, this.stacks);
       
        if (this.waterTank.getFluid() != null)        
            par1NBTTagCompound.setTag("waterTank", this.waterTank.writeToNBT(new NBTTagCompound()));
       
        if (this.waterTank1.getFluid() != null)        
        	par1NBTTagCompound.setTag("waterTank1", this.waterTank1.writeToNBT(new NBTTagCompound()));
        
        if (this.waterTank2.getFluid() != null)        
        	par1NBTTagCompound.setTag("waterTank2", this.waterTank2.writeToNBT(new NBTTagCompound()));
        
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
        return GCCoreUtil.translate("tile.universal_recycler.name");
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
        return new int[] { 0, 1, 3, 4 };
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
        case 1:
            return true;
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
    public ItemStack getBatteryInSlot()
    {
        return this.getStackInSlot(0);
    }
	
	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {

		return 0;
	}

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {

		
		if (from == getPipe())
        {
			if (resource != null && resource.isFluidEqual(this.waterTank.getFluid()))
                return this.waterTank.drain(resource.amount, doDrain);
        }
		return null;
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {

		if (from == getPipe())
        {
			return this.waterTank.drain(maxDrain, doDrain);
        }
		return null;
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid) {

		return false;
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid) {

		if (from == getPipe())
        {
			
			return this.waterTank.getFluid() != null && this.waterTank.getFluidAmount() > 0;
        }
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from) {

		return new FluidTankInfo[] { new FluidTankInfo(this.waterTank) };
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
	
	private EnumFacing getPipe()
    {
        return getFront().rotateYCCW();
    }
	
	@Override
	public EnumFacing getElectricInputDirection() {
		return getFront().rotateY();
	}

	public EnumFacing getFront() {
		IBlockState state = this.world.getBlockState(getPos());
		if (state.getBlock() instanceof BlockUniversalRecycler) {
			return state.getValue(BlockUniversalRecycler.FACING);
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
	
	public int getScaledTankLevel(int i)
    {
         return this.waterTank.getFluidAmount() * i / this.waterTank.getCapacity();
    }
}
