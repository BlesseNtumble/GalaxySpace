package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import galaxyspace.core.registers.fluids.GSFluids;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemGSCanisterGeneric;
import galaxyspace.systems.SolarSystem.planets.overworld.recipe.RecyclerRecipes;
import galaxyspace.systems.SolarSystem.planets.overworld.recipe.RecyclerRecipes.RecycleRecipe;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.galacticraft.core.items.ItemCanisterGeneric;
import micdoodle8.mods.galacticraft.core.network.IPacketReceiver;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.planets.asteroids.AsteroidsModule;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityRecycler extends TileBaseElectricBlockWithInventory implements IFluidHandler, ISidedInventory, IPacketReceiver
{
	int PROCESS_TIME_REQUIRED = 130;

    @NetworkedField(targetSide = Side.CLIENT)
    public int processTimeRequired = PROCESS_TIME_REQUIRED;

    @NetworkedField(targetSide = Side.CLIENT)
    public int processTicks = 0;

    private final int tankCapacity = 3000;
    @NetworkedField(targetSide = Side.CLIENT)
    public FluidTank waterTank = new FluidTank(this.tankCapacity);
    @NetworkedField(targetSide = Side.CLIENT)
    public FluidTank waterTank1 = new FluidTank(this.tankCapacity);
    @NetworkedField(targetSide = Side.CLIENT)
    public FluidTank waterTank2 = new FluidTank(this.tankCapacity);

    private ItemStack[] containingItems = new ItemStack[5];
    //public final Set<EntityPlayer> playersUsing = new HashSet<EntityPlayer>();

    //private boolean initialised = false;
    /*
     * @param tier: 1 = Electric Furnace  2 = Electric Arc Furnace
     */
    public TileEntityRecycler()
    {
    	this.storage.setCapacity(25000);
        this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 90 : 75);
        this.setTierGC(1);
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (!this.worldObj.isRemote)
        {
        	checkFluidTankTransfer(2, this.waterTank);
        	
        	if (this.containingItems[4] != null)
            {
            	 FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(this.containingItems[4]);
            		
	                if (liquid != null)
	                {
	                	boolean isWater = FluidRegistry.getFluidName(liquid).startsWith("liquidethanemethane");
		
		                if (isWater)
		                {
		                    if (this.waterTank.getFluid() == null || this.waterTank.getFluid().amount + liquid.amount <= this.waterTank.getCapacity())
		                    {
		                        this.waterTank.fill(new FluidStack(GSFluids.LiquidEthaneMethane, liquid.amount), true);
		
		                        if (FluidContainerRegistry.isBucket(this.containingItems[4]) && FluidContainerRegistry.isFilledContainer(this.containingItems[4]))
		                        {
		                            final int amount = this.containingItems[4].stackSize;
		                            if (amount > 1) this.waterTank.fill(new FluidStack(GSFluids.LiquidEthaneMethane, (amount - 1) * FluidContainerRegistry.BUCKET_VOLUME), true);
		                            this.containingItems[4] = new ItemStack(Items.bucket, amount);
		                        }
		                        else
		                        {
		                            this.containingItems[4].stackSize--;
		
		                            if (this.containingItems[4].stackSize == 0)
		                            {
		                                this.containingItems[4] = null;
		                            }
		                        }
		                    }
		                }
	                }
            }
        	
            if (this.hasEnoughEnergyToRun)
            {
            	if (this.canProcess())
                {
                    //50% extra speed boost for Tier 2 machine if powered by Tier 2 power
                    if (this.tierGC == 2) this.processTimeRequired = 200 / (1 + this.poweredByTierGC);

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
                    if (this.worldObj.rand.nextInt(4) == 0)
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

    private void checkFluidTankTransfer(int slot, FluidTank tank)
    {
    	 
        if (this.isValidContainer(this.containingItems[slot]))
        {
        	//GalaxySpace.debug("true");
        	 final FluidStack liquid = tank.getFluid();

            if (liquid != null)
            {
            	if(liquid.getFluid() == GSFluids.Helium3)
            		this.tryFillContainer(tank, liquid, this.containingItems, slot, GSItems.Helium3Canister);
            	if(liquid.getFluid() == AsteroidsModule.fluidMethaneGas)
            		this.tryFillContainer(tank, liquid, this.containingItems, slot, AsteroidsItems.methaneCanister);
            
            	this.tryFillContainer(tank, liquid, this.containingItems, slot, Items.bucket);
            }
        }      
    }
    
    public static boolean isValidContainer(ItemStack slotItem)
	{
		return slotItem != null && slotItem.stackSize == 1 && (slotItem.getItem() instanceof ItemCanisterGeneric || slotItem.getItem() instanceof ItemGSCanisterGeneric || FluidContainerRegistry.isContainer(slotItem));
	}
    
    public static void tryFillContainer(FluidTank tank, FluidStack liquid, ItemStack[] inventory, int slot, Item canisterType)
	{
		ItemStack slotItem = inventory[slot];
		boolean isCanister = slotItem.getItem() instanceof ItemGSCanisterGeneric || slotItem.getItem() instanceof ItemCanisterGeneric;
		final int amountToFill = Math.min(liquid.amount, isCanister ? slotItem.getItemDamage() - 1 : FluidContainerRegistry.BUCKET_VOLUME);

		if (amountToFill <= 0 || (isCanister && slotItem.getItem() != canisterType && slotItem.getItemDamage() != ItemGSCanisterGeneric.EMPTY))
			return;
		
		if (isCanister)
		{
			inventory[slot] = new ItemStack(canisterType, 1, slotItem.getItemDamage() - amountToFill);
			tank.drain(amountToFill, true);
		}
		else if (amountToFill == FluidContainerRegistry.BUCKET_VOLUME)
		{
			inventory[slot] = FluidContainerRegistry.fillFluidContainer(liquid, inventory[slot]);

			if (inventory[slot] == null)
			{
				//Failed to fill container: restore item that was there before
				inventory[slot] = slotItem;
			}
			else
			{
				tank.drain(amountToFill, true);
			}
		}
	}
    
    /**
     * @return Is this machine able to process its specific task?
     */
    public boolean canProcess()
    {
    	//FluidStack fluid1 = RecyclerRecipes.recycling().getRecyclingFluid1(this.waterTank1.getFluid());
    	//if(fluid1 != null) GalaxySpace.debug(fluid1.getFluid().getName() + "");
    	
    	if(this.containingItems[1] != null)
    	{    	
    		ItemStack input = this.containingItems[1];
    		List<RecycleRecipe> recipes = RecyclerRecipes.recycling().getRecipes(); 
    		RecycleRecipe recipe = null;
    		for(RecycleRecipe rec : recipes)
    		{
    			if(input.isItemEqual(rec.getInput())) 
    			{
    				recipe = rec;
    				break;
    			}
    		}
    		
    		if(recipe != null)
    		//for(RecycleRecipe recipe : recipes)
        	{
    			if(recipe.getInput() == null) 
    				return false;
    			
    			//if(!input.isItemEqual(recipe.getInput())) 
    				//return false;
    			
    			if(input.stackSize < recipe.getInput().stackSize) 
    				return false;
    				
    			if(recipe.getFluidStack() != null)
        		{
    				if (this.waterTank.getFluidAmount() > 0 && !this.waterTank.getFluid().isFluidEqual(recipe.getFluidStack()))
        				return false;
        				
        			if (this.waterTank.getFluidAmount() >= this.waterTank.getCapacity())
        				return false;
        		}
    				
    			if(recipe.getOutput() != null)
        		{
    				if (this.containingItems[3] == null) return true;    				
        			if (!this.containingItems[3].isItemEqual(recipe.getOutput())) return false;
        				
        			int result = this.containingItems[3].stackSize + recipe.getOutput().stackSize;
        			return result <= getInventoryStackLimit() && result <= this.containingItems[3].getMaxStackSize();
        		}
        	}
    		return false; 
    	}
    	
    	return false;
    }

    /**
     * Turn one item from the furnace source stack into the appropriate smelted
     * item in the furnace result stack
     */
    public void smeltItem()
    {
    	
        if (this.canProcess())
        {
        	
        	ItemStack input = this.containingItems[1];
			List<RecycleRecipe> recyclerecipes = RecyclerRecipes.recycling().getRecipes(); 
			RecycleRecipe recipe = null;
			for(RecycleRecipe recipes : recyclerecipes)
    		{
    			if(recipes.getInput().isItemEqual(input))
    			{
    				recipe = recipes;
    				break;
    			}
    		}
			
			if(recipe != null)
    		{
    			boolean hasRand = recipe.hasChance();
    			
    			if(!hasRand) 
    			{
	    			if(this.containingItems[3] == null)
	    				this.containingItems[3] = recipe.getOutput().copy();	 
	    			else if(this.containingItems[3].isItemEqual(recipe.getOutput()))
	    				this.containingItems[3].stackSize += recipe.getOutput().stackSize;  
    			}
    			else
	    		{
	    			if(this.containingItems[3] == null)
	    			{
	    				if(this.worldObj.rand.nextInt(100) <= recipe.getChance()) 
	    					this.containingItems[3] = recipe.getOutput().copy();	 
	    			}
	    			else if(this.containingItems[3].isItemEqual(recipe.getOutput()))
	    			{
	    				if(this.worldObj.rand.nextInt(100) <= recipe.getChance()) 
	    					this.containingItems[3].stackSize += recipe.getOutput().stackSize;
	    			}
	    		}
    			
    			if (this.containingItems[1].stackSize == 1)
    	        	this.containingItems[1] = null;
    	        else 
    	        	this.containingItems[1].stackSize--;  
    			
    			if(recipe.getFluidStack() != null)
    				this.waterTank.fill(new FluidStack(recipe.getFluidStack().getFluid(), recipe.getFluidStack().amount > this.waterTank.getCapacity() ? this.waterTank.getCapacity() : recipe.getFluidStack().amount), true);           
    	        
    		}

	            		
        	
        }        
        
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);

        this.processTicks = par1NBTTagCompound.getInteger("smeltingTicks");
        this.containingItems = this.readStandardItemsFromNBT(par1NBTTagCompound);
        
        if (par1NBTTagCompound.hasKey("waterTank"))        
            this.waterTank.readFromNBT(par1NBTTagCompound.getCompoundTag("waterTank"));
        
        if (par1NBTTagCompound.hasKey("waterTank1"))        
        	this.waterTank1.readFromNBT(par1NBTTagCompound.getCompoundTag("waterTank1"));
        
        if (par1NBTTagCompound.hasKey("waterTank2"))        
        	this.waterTank2.readFromNBT(par1NBTTagCompound.getCompoundTag("waterTank2"));
        
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
       /* if (this.tierGC == 1 && this.storage.getEnergyStoredGC() > EnergyStorageTile.STANDARD_CAPACITY)
        	this.storage.setEnergyStored(EnergyStorageTile.STANDARD_CAPACITY);*/
        
    	super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("smeltingTicks", this.processTicks);
        this.writeStandardItemsToNBT(par1NBTTagCompound);
       
        if (this.waterTank.getFluid() != null)        
            par1NBTTagCompound.setTag("waterTank", this.waterTank.writeToNBT(new NBTTagCompound()));
       
        if (this.waterTank1.getFluid() != null)        
        	par1NBTTagCompound.setTag("waterTank1", this.waterTank1.writeToNBT(new NBTTagCompound()));
        
        if (this.waterTank2.getFluid() != null)        
        	par1NBTTagCompound.setTag("waterTank2", this.waterTank2.writeToNBT(new NBTTagCompound()));
        
    }

    @Override
    protected ItemStack[] getContainingItems()
    {
        return this.containingItems;
    }

    @Override
    public String getInventoryName()
    {
        return GCCoreUtil.translate("tile.Recycler.name");
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return true;
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring
     * stack size) into the given slot.
     */
    @Override
    public boolean isItemValidForSlot(int slotID, ItemStack itemStack)
    {
    	switch (slotID)
        {
        case 0:
            return ItemElectricBase.isElectricItem(itemStack.getItem());
        case 1:
            return true;
        }

        return false;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side)
    {
        return new int[] { 0, 1, 3, 4 };
    }

    @Override
    public boolean canInsertItem(int slotID, ItemStack par2ItemStack, int par3)
    {
        return this.isItemValidForSlot(slotID, par2ItemStack);
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack par2ItemStack, int par3)
    {
        return slotID == 3;
    }

    @Override
    public boolean shouldUseEnergy()
    {
        return this.processTicks > 0;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
    	int used = 0;

    	//if (from.equals(ForgeDirection.getOrientation(this.getBlockMetadata() + 2).getOpposite()))
    	if (from.equals(ForgeDirection.UP))
    	{
            final String liquidName = FluidRegistry.getFluidName(resource);

            if (liquidName != null) used = this.waterTank1.fill(resource, doFill);
        }
        return used;
    }

    @Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) 
	{
    	int metaside = this.getBlockMetadata() + 2;
        int side = from.ordinal();
        if (side == (metaside ^ 1))
        {
            if (resource != null && resource.isFluidEqual(this.waterTank.getFluid()))
                return this.waterTank.drain(resource.amount, doDrain);
        }
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		int metaside = this.getBlockMetadata() + 2;
        int side = from.ordinal();
        if (side == (metaside ^ 1))
        {
            return this.waterTank.drain(maxDrain, doDrain);
        }
		return null;
	}

	@Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
       // return this.waterTank1.getFluid() == null || this.waterTank1.getFluidAmount() < this.waterTank1.getCapacity();
		return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
    	int metaside = this.getBlockMetadata() + 2;
        int side = from.ordinal();
        if (side == (metaside ^ 1))
            return this.waterTank.getFluid() != null && this.waterTank.getFluidAmount() > 0;
            
        return false;
    }

	@Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
		FluidTankInfo[] tankInfo = new FluidTankInfo[] {};
		int metaside = this.getBlockMetadata() + 2;
	    int side = from.ordinal();
	    
	    if (side == (metaside ^ 1))
	    	tankInfo = new FluidTankInfo[] { new FluidTankInfo(this.waterTank) };
	    
	    else if (from.equals(ForgeDirection.UP))
	    	tankInfo = new FluidTankInfo[] { new FluidTankInfo(this.waterTank1) };
	    
        return tankInfo;
    }
	
    public int getScaledFuelLevel(int i)
    {
         return this.waterTank.getFluidAmount() * i / this.waterTank.getCapacity();
    }
    
    @Override
    public ItemStack getBatteryInSlot()
    {
        return this.getStackInSlot(0);
    }
}