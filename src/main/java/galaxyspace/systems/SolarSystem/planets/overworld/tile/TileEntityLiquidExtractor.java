package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.HashSet;
import java.util.Set;

import cpw.mods.fml.relauncher.Side;
import galaxyspace.core.registers.fluids.GSFluids;
import galaxyspace.core.registers.items.GSItems;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.EnergyStorageTile;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.core.network.IPacketReceiver;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.FluidUtil;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityLiquidExtractor extends TileBaseElectricBlockWithInventory implements IFluidHandler, ISidedInventory, IPacketReceiver
{
	int PROCESS_TIME_REQUIRED = 10;

    @NetworkedField(targetSide = Side.CLIENT)
    public int processTimeRequired = PROCESS_TIME_REQUIRED;

    @NetworkedField(targetSide = Side.CLIENT)
    public int processTicks = 0;

    private final int tankCapacity = 3000;
    @NetworkedField(targetSide = Side.CLIENT)
    public FluidTank waterTank = new FluidTank(this.tankCapacity);
    
    private ItemStack[] containingItems = new ItemStack[4];
    public final Set<EntityPlayer> playersUsing = new HashSet<EntityPlayer>();

    private boolean initialised = false;

    @NetworkedField(targetSide = Side.CLIENT)
    private int x = -2;
    @NetworkedField(targetSide = Side.CLIENT)
    private int z = -2;
	
    public TileEntityLiquidExtractor()
    {
        this(1);
    }

    /*
     * @param tier: 1 = Electric Furnace  2 = Electric Arc Furnace
     */
    public TileEntityLiquidExtractor(int tier)
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

    @Override
    public void updateEntity()
    {
        if (!this.initialised )
        {
            int metadata = this.getBlockMetadata();
            //for version update compatibility
            Block b = this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord);
            if (b == GCBlocks.machineBase)
            {
                this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, GCBlocks.machineTiered, 4, 2);
            }
            else if (metadata >= 8)
            {
            	this.setTier2();
            }
            this.initialised = true;
        }

        super.updateEntity();
        
        if (!this.worldObj.isRemote)
        {       	
        	
        	checkFluidTankTransfer(1, this.waterTank);
        	
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
            	else if(liquid.getFluid() == GSFluids.HeliumHydrogen)
            		FluidUtil.tryFillContainer(tank, liquid, this.containingItems, slot, GSItems.HeliumHydrogenBucket);
            	else if(liquid.getFluid().getName().contains("hydrogen"))
            		FluidUtil.tryFillContainer(tank, liquid, this.containingItems, slot, GSItems.HydrogenCanister);
            	else if(liquid.getFluid().getName().contains("liquidoxygen"))
            		FluidUtil.tryFillContainer(tank, liquid, this.containingItems, slot, AsteroidsItems.canisterLOX);
            	else if(liquid.getFluid().getName().contains("oil"))
            		FluidUtil.tryFillContainer(tank, liquid, this.containingItems, slot, GCItems.oilCanister);
            	else if(liquid.getFluid().getName().contains("fuel"))
            		FluidUtil.tryFillContainer(tank, liquid, this.containingItems, slot, GCItems.fuelCanister);
            	else if(liquid.getFluid().getName().contains("nirtogen"))
            		FluidUtil.tryFillContainer(tank, liquid, this.containingItems, slot, AsteroidsItems.canisterLN2);            	
            	
            	else FluidUtil.tryFillContainer(tank, liquid, this.containingItems, slot, Items.bucket);
            }
        }      
    }
    
    /**
     * @return Is this machine able to process its specific task?
     */
    public boolean canProcess()
    {
    	
    	if(this.waterTank.getFluidAmount() < this.waterTank.getCapacity())
    		return true;
    	//if(this.containingItems[2] != null)
    		//if(this.containingItems[2].stackSize >= 64) return false;

    	//if(!(this.worldObj.getBlock(xCoord, yCoord - 1, zCoord) instanceof IFluidBlock)) return false;
    	
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
        	Block block = this.worldObj.getBlock(this.xCoord + x, this.yCoord - 1, this.zCoord + z);
        	int meta = this.worldObj.getBlockMetadata(this.xCoord + x, this.yCoord - 1, this.zCoord + z);
        	      	
        	if(meta == 0) {       		
	        	
	        	if(block == Blocks.water || block == Blocks.ice)
	        	{ 
	        		this.waterTank.fill(new FluidStack(this.containingItems[2] != null && this.containingItems[2] == new ItemStack(GSItems.BasicItems, 1, 3) ? GSFluids.Hydrogen2 : FluidRegistry.WATER, block == Blocks.ice ? 20 : this.containingItems[2] != null ? 100 : 1000), true);
	        		if(block != Blocks.ice && this.waterTank.getFluid().isFluidEqual(new FluidStack(FluidRegistry.lookupFluidForBlock(block), 0))) this.worldObj.setBlockToAir(this.xCoord + x, this.yCoord - 1, this.zCoord + z);
	        	}
	        	
	        	if(block == Blocks.lava)
	        	{ 
	        		this.waterTank.fill(new FluidStack(FluidRegistry.LAVA, 1000), true);
	        		this.worldObj.setBlockToAir(this.xCoord + x, this.yCoord - 1, this.zCoord + z);
	        	}
	        	
	
			    if(block instanceof IFluidBlock)
			    {       
			    	IFluidBlock fluid = (IFluidBlock)block;
			
			    	this.waterTank.fill(new FluidStack(fluid.getFluid(), 1000), true);
			    	this.worldObj.setBlockToAir(this.xCoord + x, this.yCoord - 1, this.zCoord + z);	    	
			    } 
        	}
        	
        	if(x < 2) x++;
        	else {
        		x = -2;
        		z++;
        	}
        	
        	if(z > 2) z = -2;   

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
        
        this.processTicks = par1NBTTagCompound.getInteger("smeltingTicks");
        this.containingItems = this.readStandardItemsFromNBT(par1NBTTagCompound);
        
        this.x = par1NBTTagCompound.getInteger("posX");
        this.z = par1NBTTagCompound.getInteger("posZ");
        
        if (par1NBTTagCompound.hasKey("waterTank"))
        {
            this.waterTank.readFromNBT(par1NBTTagCompound.getCompoundTag("waterTank"));
        }
        
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        /*if (this.tierGC == 1 && this.storage.getEnergyStoredGC() > EnergyStorageTile.STANDARD_CAPACITY)
        	this.storage.setEnergyStored(EnergyStorageTile.STANDARD_CAPACITY);*/
    	super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("smeltingTicks", this.processTicks);
        
        par1NBTTagCompound.setInteger("posX", this.x);
        par1NBTTagCompound.setInteger("posZ", this.z);
        
        this.writeStandardItemsToNBT(par1NBTTagCompound);
        if (this.waterTank.getFluid() != null)
        {
            par1NBTTagCompound.setTag("waterTank", this.waterTank.writeToNBT(new NBTTagCompound()));
        }
    }

    
    @Override
    protected ItemStack[] getContainingItems()
    {
        return this.containingItems;
    }

    @Override
    public String getInventoryName()
    {
        return GCCoreUtil.translate("tile.LiquidExtractor.name");
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
        return new int[] { 0, 1 };
    }

    @Override
    public boolean canInsertItem(int slotID, ItemStack par2ItemStack, int par3)
    {
        return this.isItemValidForSlot(slotID, par2ItemStack);
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack par2ItemStack, int par3)
    {
        return slotID == 1;
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
        return new FluidTankInfo[] { new FluidTankInfo(this.waterTank) };
    }
	
    public int getScaledFuelLevel(int i)
    {
         return this.waterTank.getFluidAmount() * i / this.waterTank.getCapacity();
    }
}