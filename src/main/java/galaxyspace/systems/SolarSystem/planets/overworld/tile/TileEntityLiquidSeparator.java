package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.fluids.GSFluids;
import galaxyspace.core.registers.items.GSItems;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.EnergyStorageTile;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.galacticraft.core.items.ItemCanisterGeneric;
import micdoodle8.mods.galacticraft.core.network.IPacketReceiver;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.FluidUtil;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
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

public class TileEntityLiquidSeparator extends TileBaseElectricBlockWithInventory implements IFluidHandler, ISidedInventory, IPacketReceiver
{
	int PROCESS_TIME_REQUIRED = 7;

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
    
    
    private ItemStack[] containingItems = new ItemStack[4];
    //public final Set<EntityPlayer> playersUsing = new HashSet<EntityPlayer>();

    private static List<Liquids> liquids = new ArrayList<Liquids>();
    private boolean initialised = false;
    
    @NetworkedField(targetSide = Side.CLIENT)
    public boolean reverse;

    static {
    	liquids.add(new Liquids(new FluidStack(FluidRegistry.WATER, 10), FluidRegistry.getFluidStack("hydrogen", 2), FluidRegistry.getFluidStack("liquidoxygen", 1)));
    	liquids.add(new Liquids(new FluidStack(GSFluids.LiquidEthaneMethane, 10), FluidRegistry.getFluidStack("methane", 5), FluidRegistry.getFluidStack("ethane", 1)));
    	liquids.add(new Liquids(new FluidStack(GSFluids.Hydrogen2, 10), FluidRegistry.getFluidStack("liquidoxygen", 1), FluidRegistry.getFluidStack("deuterium", 1)));
    	liquids.add(new Liquids(new FluidStack(GSFluids.HeliumHydrogen, 10), new FluidStack(GSFluids.Helium3, 2), FluidRegistry.getFluidStack("hydrogen", 8)));
    	
    	/*
    	ETANEMETHANE(0, "liquidethanemethane", "methane", 5, "ethane", 1),
    	WATER(1, "water", "hydrogen", 2, "liquidoxygen", 1),
    	HEAVYWATER(2, "heavywater", "liquidoxygen", 1, "deuterium", 1),
    	HYDROGEN2(3, "hydrogen2", "liquidoxygen", 1, "deuterium", 1),
    	 */
    }
    
    static class Liquids
    {
    	private FluidStack input, output1, output2;
    	Liquids(FluidStack input, FluidStack component_1, FluidStack component_2)
    	{
    		this.input = input;
    		this.output1 = component_1;
    		this.output2 = component_2;
    	}
    	
    	public FluidStack getFluid()
    	{
    		return this.input;
    	}
    	
    	public FluidStack getFirstComponent()
    	{
    		return this.output1;
    	}
    	
    	public FluidStack getSecondComponent()
    	{
    		return this.output2;
    	}
    }
    /*
    public enum TankLiqu1ids
    {
    	ETANEMETHANE(0, "liquidethanemethane", "methane", 5, "ethane", 1),
    	WATER(1, "water", "hydrogen", 2, "liquidoxygen", 1),
    	HEAVYWATER(2, "heavywater", "liquidoxygen", 1, "deuterium", 1),
    	HYDROGEN2(3, "hydrogen2", "liquidoxygen", 1, "deuterium", 1),
    	//ROCKETFUEL2(4, "heliumhydrogen", "helium3", 2, "hydrogen", 8),
    	
    	ETANEMETHANE(0, new FluidStack(GSFluids.LiquidEthaneMethane, 10), new FluidStack(AsteroidsModule.fluidMethaneGas ,5), FluidRegistry.getFluidStack("ethane", 1)),
    	WATER(1, new FluidStack(FluidRegistry.WATER, 10), FluidRegistry.getFluidStack("hydrogen", 2), FluidRegistry.getFluidStack("liquidoxygen", 1)),
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
    */
    public TileEntityLiquidSeparator()
    {
        this(1);
    }

    /*
     * @param tier: 1 = Electric Furnace  2 = Electric Arc Furnace
     */
    public TileEntityLiquidSeparator(int tier)
    {
        this.initialised = true;
        if (tier == 1)
        {
            this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 60 : 45);
            return;
        }

        this.setTier2();
    }
    
    private void setTier2()
    {
        this.storage.setCapacity(25000);
        this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 90 : 60);
        this.processTimeRequired = 100;
        this.setTierGC(2);   	
    }
    
    public boolean containsFluid(FluidStack stack, boolean reverse)
	{
    	if(stack != null) {
			if(!reverse) {
				for(Liquids fluid : liquids)
					if(fluid.input.equals(stack))
						return true;
			}
			else
				for(Liquids fluid : liquids)
					if(fluid.output1.equals(stack))
						return true;
    	}
		return false;
	}
    
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        
        if (!this.worldObj.isRemote)
        {   
        	if(!this.getReverse())
        	{
	        	checkFluidTankTransfer(1, this.waterTank2);
	        	checkFluidTankTransfer(3, this.waterTank1);
	        	
				if (this.containingItems[2] != null) {
					FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(this.containingItems[2]);
					
					if (liquid != null) {
						
						if (this.baseTank.getFluid() == null || this.baseTank.getFluid().amount + liquid.amount <= this.baseTank.getCapacity()) {
							this.baseTank.fill(new FluidStack(liquid.getFluid(), liquid.amount), true);

							if (FluidContainerRegistry.isBucket(this.containingItems[2]) && FluidContainerRegistry.isFilledContainer(this.containingItems[2])) {
								final int amount = this.containingItems[2].stackSize;
								if (amount > 1)
									this.baseTank.fill(new FluidStack(liquid.getFluid(), (amount - 1) * FluidContainerRegistry.BUCKET_VOLUME), true);
								this.containingItems[2] = new ItemStack(Items.bucket, amount);
							} else {
								this.containingItems[2].stackSize--;

								if (this.containingItems[2].stackSize == 0) {
									this.containingItems[2] = null;
								}
							}

						}
					}
				}
        	}
        	else 
        	{
        		checkFluidTankTransfer(2, this.baseTank);
        		
        		if (this.containingItems[1] != null) {
					FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(this.containingItems[1]);

					if (liquid != null) {

						GalaxySpace.debug(liquid.getUnlocalizedName());
						if (this.waterTank2.getFluid() == null
								|| this.waterTank2.getFluid().amount + liquid.amount <= this.waterTank2.getCapacity()) {
							this.waterTank2.fill(new FluidStack(liquid.getFluid(), liquid.amount), true);

							if (FluidContainerRegistry.isBucket(this.containingItems[1])
									&& FluidContainerRegistry.isFilledContainer(this.containingItems[1])) {
								final int amount = this.containingItems[1].stackSize;
								if (amount > 1)
									this.waterTank2.fill(new FluidStack(liquid.getFluid(), (amount - 1) * FluidContainerRegistry.BUCKET_VOLUME), true);
								this.containingItems[1] = new ItemStack(Items.bucket, amount);
							} else {
								this.containingItems[1].stackSize--;

								if (this.containingItems[1].stackSize == 0) {
									this.containingItems[1] = null;
								}
							}

						}
					}
				}
        		
        		if (this.containingItems[3] != null) {
					FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(this.containingItems[3]);

					if (liquid != null) {

						if (this.waterTank1.getFluid() == null
								|| this.waterTank1.getFluid().amount + liquid.amount <= this.waterTank1.getCapacity()) {
							this.waterTank1.fill(new FluidStack(liquid.getFluid(), liquid.amount), true);

							if (FluidContainerRegistry.isBucket(this.containingItems[3])
									&& FluidContainerRegistry.isFilledContainer(this.containingItems[3])) {
								final int amount = this.containingItems[3].stackSize;
								if (amount > 1)
									this.waterTank1.fill(new FluidStack(liquid.getFluid(),
											(amount - 1) * FluidContainerRegistry.BUCKET_VOLUME), true);
								this.containingItems[3] = new ItemStack(Items.bucket, amount);
							} else {
								this.containingItems[3].stackSize--;

								if (this.containingItems[3].stackSize == 0) {
									this.containingItems[3] = null;
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
        if (FluidUtil.isValidContainer(this.containingItems[slot]))
        {
            final FluidStack liquid = tank.getFluid();

            if (liquid != null)
            {
            	if(liquid.getFluid().getName().contains("methane"))
            		FluidUtil.tryFillContainer(tank, liquid, this.containingItems, slot, AsteroidsItems.methaneCanister);
            	else if(liquid.getFluid() == GSFluids.Helium3)
            		FluidUtil.tryFillContainer(tank, liquid, this.containingItems, slot, GSItems.Helium3Canister);
            	else if(liquid.getFluid() == GSFluids.HeliumHydrogen && !(this.containingItems[slot].getItem() instanceof ItemCanisterGeneric))
            		FluidUtil.tryFillContainer(tank, liquid, this.containingItems, slot, GSItems.HeliumHydrogenBucket);
            	else if(liquid.getFluid() == GSFluids.HeliumHydrogen && (this.containingItems[slot].getItem() instanceof ItemCanisterGeneric))
            		FluidUtil.tryFillContainer(tank, liquid, this.containingItems, slot, GSItems.HeliumHydrogenCanister);
            	else if(liquid.getFluid().getName().contains("hydrogen") && liquid.getFluid() != GSFluids.HeliumHydrogen)
            		FluidUtil.tryFillContainer(tank, liquid, this.containingItems, slot, GSItems.HydrogenCanister);
            	else if(liquid.getFluid().getName().contains("liquidoxygen"))
            		FluidUtil.tryFillContainer(tank, liquid, this.containingItems, slot, AsteroidsItems.canisterLOX);
            	
            	//else FluidUtil.tryFillContainer(tank, liquid, this.containingItems, slot, Items.bucket);
            }
        }      
    }
    
    /**
     * @return Is this machine able to process its specific task?
     */
    public boolean canProcess()
    {
    	if(!this.getReverse())
    	{
    		if(this.baseTank.getFluidAmount() <= 0) 
    			return false;
    		if(this.waterTank1.getFluidAmount() >= this.waterTank1.getCapacity() || this.waterTank2.getFluidAmount() >= this.waterTank2.getCapacity())
    			return false;
    		
    		return this.containsFluid(this.baseTank.getFluid(), false);
    	}
    	else
    	{
    		if(this.waterTank1.getFluidAmount() <= 0 && this.waterTank2.getFluidAmount() <= 0) return false;
    		if(this.baseTank.getFluidAmount() >= this.baseTank.getCapacity()) return false;
    		if(this.waterTank1.getFluid() == null || this.waterTank2.getFluid() == null) return false;
    		
    		if(FluidRegistry.getFluidName(this.waterTank1.getFluid()) == null || FluidRegistry.getFluidName(this.waterTank2.getFluid()) == null) return false;
    	
    		return this.containsFluid(this.waterTank1.getFluid(), true);
    	}
    	      	
    }

    /**
     * Turn one item from the furnace source stack into the appropriate smelted
     * item in the furnace result stack
     */
    private Liquids getLiquidClass(FluidStack stack, FluidStack stack2, boolean component)
    {
    	for(Liquids l : liquids)
		{
			if(stack == null) return null;
    		if(component)
    		{
    			if(stack2 != null) {
	    			if(l.getFirstComponent().isFluidEqual(stack) && l.getSecondComponent().isFluidEqual(stack2) || l.getFirstComponent().isFluidEqual(stack2) && l.getSecondComponent().isFluidEqual(stack))
	    			{
	    				return l;
	    			}
    			}
    			else if(stack2 == null && l.getFirstComponent().isFluidEqual(stack))
    			{
    				return l;
    			}
    		}    		
    		else if(l.getFluid().isFluidEqual(stack))
				return l;
		}
    	return null;
    }
    
    public void smeltItem()
    {
        if (this.canProcess())
        {	  
        	int i = 0;
        	
        	if(!this.getReverse())
        	{
        		Liquids getRecipe = getLiquidClass(this.baseTank.getFluid(), null, false);
        		
        		if(getRecipe == null)
        	       	return;
        		
        		if(getRecipe.getFirstComponent() != null) {
        			this.waterTank2.fill(getRecipe.getFirstComponent().copy(), true);  
        			
        			if(getRecipe.getSecondComponent() != null) {
        				
        				this.waterTank1.fill(getRecipe.getSecondComponent().copy(), true);
        			}        		
        			this.baseTank.drain(getRecipe.getFluid().amount, true);
        		}
        	}
        	else
        	{
        		Liquids getRecipe = getLiquidClass(this.waterTank1.getFluid(), this.waterTank2.getFluid(), true);
        		
        		
        		if(getRecipe == null)
        	       	return;
        	
        		if(getRecipe.getFirstComponent() != null && getRecipe.getSecondComponent() != null) {
        			
        			if(this.waterTank1.getFluid().isFluidEqual(getRecipe.getFirstComponent()) && this.waterTank2.getFluid().isFluidEqual(getRecipe.getSecondComponent()))
        			{
        				this.baseTank.fill(getRecipe.getFluid(), true);
    			        this.waterTank1.drain(getRecipe.getFirstComponent().amount, true);
    			        this.waterTank2.drain(getRecipe.getSecondComponent().amount, true);
        			} 
        			else if(this.waterTank2.getFluid().isFluidEqual(getRecipe.getFirstComponent()) && this.waterTank1.getFluid().isFluidEqual(getRecipe.getSecondComponent()))
            		{
        				
            			this.baseTank.fill(getRecipe.getFluid(), true);
        			    this.waterTank2.drain(getRecipe.getFirstComponent().amount, true);
        			    this.waterTank1.drain(getRecipe.getSecondComponent().amount, true);
            		}  
        		}
        	}
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        if (this.storage.getEnergyStoredGC() > EnergyStorageTile.STANDARD_CAPACITY)
        {
        	this.setTier2();
        	this.initialised = true;
        }
        else
        	this.initialised = false;
        
        this.reverse = par1NBTTagCompound.getBoolean("reverse");
        this.processTicks = par1NBTTagCompound.getInteger("smeltingTicks");
        this.containingItems = this.readStandardItemsFromNBT(par1NBTTagCompound); 
        
        if (par1NBTTagCompound.hasKey("baseTank"))        
        	this.baseTank.readFromNBT(par1NBTTagCompound.getCompoundTag("baseTank"));
        if (par1NBTTagCompound.hasKey("waterTank1"))        
        	this.waterTank1.readFromNBT(par1NBTTagCompound.getCompoundTag("waterTank1"));
        if (par1NBTTagCompound.hasKey("waterTank2"))        
            this.waterTank2.readFromNBT(par1NBTTagCompound.getCompoundTag("waterTank2"));
        
        
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        /*if (this.tierGC == 1 && this.storage.getEnergyStoredGC() > EnergyStorageTile.STANDARD_CAPACITY)
        	this.storage.setEnergyStored(EnergyStorageTile.STANDARD_CAPACITY);*/
    	super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("smeltingTicks", this.processTicks);
                
        this.writeStandardItemsToNBT(par1NBTTagCompound);
        
        par1NBTTagCompound.setBoolean("reverse", this.reverse);
        
        if (this.baseTank.getFluid() != null)        
            par1NBTTagCompound.setTag("baseTank", this.baseTank.writeToNBT(new NBTTagCompound()));
        
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
        return GCCoreUtil.translate("tile.LiquidSeparator.name");
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
        return new int[] { 0, 1, 2 };
    }

    @Override
    public boolean canInsertItem(int slotID, ItemStack par2ItemStack, int par3)
    {
        return this.isItemValidForSlot(slotID, par2ItemStack);
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack par2ItemStack, int par3)
    {
        return false;
    }

    @Override
    public boolean shouldUseEnergy()
    {
        return this.canProcess();
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        int used = 0;
        if(!this.getReverse())
        {
	        if (from.equals(ForgeDirection.UP))       
	        {
	            final String liquidName = FluidRegistry.getFluidName(resource);
	
	            if (liquidName != null) used = this.baseTank.fill(resource, doFill);
	        }
        }
        else
        {
        	int metaside = this.getBlockMetadata() + 2;
            int side = from.ordinal();
            
            
            if (side == (metaside))
	        {
            	final String liquidName = FluidRegistry.getFluidName(resource);
            	
	            if (liquidName != null) used = this.waterTank2.fill(resource, doFill);
	        }
            
            if (side == (metaside ^ 1))
	        {
            	final String liquidName = FluidRegistry.getFluidName(resource);
            	
	            if (liquidName != null) used = this.waterTank1.fill(resource, doFill);
	        }
        }
        
        return used;
    }

    @Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) 
	{
    	int metaside = this.getBlockMetadata() + 2;
        int side = from.ordinal();
        if(!this.getReverse())
        {
	        if (side == (metaside))
	        {
	            if (resource != null && !resource.getFluid().isGaseous() &&resource.isFluidEqual(this.waterTank1.getFluid()))
	                return this.waterTank2.drain(resource.amount, doDrain);
	        }
	        
	        if (side == (metaside ^ 1))
	        {
	            if (resource != null && !resource.getFluid().isGaseous()  && resource.isFluidEqual(this.waterTank2.getFluid()))
	                return this.waterTank1.drain(resource.amount, doDrain);
	        }
        }
        else
        {
        	if (from.equals(ForgeDirection.UP)) 
        		 if (resource != null && !resource.getFluid().isGaseous() && resource.isFluidEqual(this.baseTank.getFluid()))
 	                return this.baseTank.drain(resource.amount, doDrain);
        }
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		int metaside = this.getBlockMetadata() + 2;
        int side = from.ordinal();
        if(!this.getReverse())
        {
	        if (side == (metaside))
	        {
	            return this.waterTank2.drain(maxDrain, true);
	        }
	        
	        if (side == (metaside ^ 1))
	        {
	            return this.waterTank1.drain(maxDrain, true);
	        }
        }        
        else
        {
        	if (from.equals(ForgeDirection.UP)) 
	        	return  this.baseTank.drain(maxDrain, true);
        }
        
		return null;
	}

	@Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
		if(!this.getReverse())
			return this.baseTank.getFluid() == null || this.baseTank.getFluidAmount() < this.baseTank.getCapacity();
    
		return (this.waterTank1.getFluid() == null || this.waterTank1.getFluidAmount() < this.waterTank1.getCapacity()) || (this.waterTank2.getFluid() == null || this.waterTank2.getFluidAmount() < this.waterTank2.getCapacity());
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
    	int metaside = this.getBlockMetadata() + 2;
        int side = from.ordinal();
        if(!this.getReverse())
        {
	        if (side == (metaside ^ 1))
	            return this.waterTank1.getFluid() != null && this.waterTank1.getFluidAmount() > 0;
	            
	        if (side == (metaside))
	        	return this.waterTank2.getFluid() != null && this.waterTank2.getFluidAmount() > 0;
        }   
        else
        {
        	if (from.equals(ForgeDirection.UP)) 
	        	return this.baseTank.getFluid() != null && this.baseTank.getFluidAmount() > 0;
        }
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
    	FluidTankInfo[] tankInfo = new FluidTankInfo[] { };
    	if(from == ForgeDirection.UP) tankInfo = new FluidTankInfo[] { new FluidTankInfo(this.baseTank) };
    	else if(from == ForgeDirection.getOrientation(this.getBlockMetadata() + 2)) tankInfo = new FluidTankInfo[] { new FluidTankInfo(this.waterTank1) };
    	else if(from == ForgeDirection.getOrientation((this.getBlockMetadata() + 2) ^ 1)) tankInfo = new FluidTankInfo[] { new FluidTankInfo(this.waterTank2) };
    	
    	return tankInfo;
    }
    
    @Override
    public ForgeDirection getElectricInputDirection()
    {
        return ForgeDirection.DOWN;
    }
    
    public void setReverse(boolean rev)
    {
    	this.reverse = rev;
    }
    
    public boolean getReverse()
    {
    	return this.reverse;
    }
    
	/*
    public int getScaledFuelLevel(int i)
    {
         return this.waterTank.getFluidAmount() * i / this.waterTank.getCapacity();
    }*/
}