package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import galaxyspace.core.GSItems;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockLiquidExtractor;
import micdoodle8.mods.galacticraft.api.transmission.NetworkType;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.galacticraft.core.network.IPacketReceiver;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.wrappers.FluidHandlerWrapper;
import micdoodle8.mods.galacticraft.core.wrappers.IFluidHandlerWrapper;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;

public class TileEntityLiquidExtractor extends TileBaseElectricBlockWithInventory implements IFluidHandlerWrapper, ISidedInventory, IPacketReceiver {

	private int PROCESS_TIME_REQUIRED = 30;
	
	@NetworkedField(targetSide = Side.CLIENT)
    public int processTimeRequired = PROCESS_TIME_REQUIRED;
	
	@NetworkedField(targetSide = Side.CLIENT)
    public int processTicks = 0;
	
	private final int tankCapacity = 3000;
    @NetworkedField(targetSide = Side.CLIENT)
    public FluidTank waterTank = new FluidTank(this.tankCapacity);
    
   // private NonNullList<ItemStack> stacks = NonNullList.withSize(3 + 4, ItemStack.EMPTY);
    
    private static HashMap<Block, FluidStack> extractfluid = new HashMap();
    
    
    private List<BlockPos> blocks = new ArrayList<BlockPos>();
    private int number = 0;
       
    public TileEntityLiquidExtractor()
    {
    	super("tile.liquid_extractor.name");
    	this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 60 : 45);
    	this.inventory = NonNullList.withSize(3 + 4, ItemStack.EMPTY);
    	this.setTierGC(1);
    	
    	extractfluid.put(Blocks.ICE, new FluidStack(FluidRegistry.WATER, 100));
    }
    
    public static void addBlockAndFluid(Block block, FluidStack fluidstack)
    {
    	extractfluid.put(block, fluidstack);
    }
    
    @Override
    public void update()
    {
        super.update();
        if (!this.world.isRemote)
        {       	
        	int range = 2;
        	GSUtils.checkFluidTankTransfer(this.getInventory(), 1, this.waterTank);
        	
            if (this.canProcess())
            {            	
            
            	 int energy_boost = 0;
                 
                 for(int i = 0; i <= 3; i++)
             	{
             		if(this.getInventory().get(3 + i).isItemEqual(new ItemStack(GSItems.UPGRADES, 1, 0)))
             			range += 2;
             		if(this.getInventory().get(3 + i).isItemEqual(new ItemStack(GSItems.UPGRADES, 1, 3)))
             			energy_boost++;              		
             		
             	}
             	this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 60 - (20 * energy_boost) : 45 - (15 * energy_boost));
             	    
             	
            	blocks.clear();
            	BlockPos pos1 = new BlockPos(this.getPos().getX() - range, this.getPos().getY(), this.getPos().getZ() - range);
            	BlockPos pos2 = new BlockPos(this.getPos().getX() + range, this.getPos().getY(), this.getPos().getZ() + range);
            	this.getPos().getAllInBox(pos1, pos2).forEach((block) -> blocks.add(block));    
            	
            	if(number > blocks.size() - 1) number = 0;
            	
            	if (this.hasEnoughEnergyToRun)
                {
                    //50% extra speed boost for Tier 2 machine if powered by Tier 2 power
                   // if (this.tierGC == 2) this.processTimeRequired = 200 / (1 + this.poweredByTierGC);

                   
                	
                    if (this.processTicks == 0)
                    {
                        this.processTicks = this.processTimeRequired;
                    }
                    else
                    {
                        if (--this.processTicks <= 0)
                        {       
                            this.smeltItem(number++);
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
    	if(this.waterTank.getFluidAmount() >= this.waterTank.getCapacity())
    		return false;
    	
    	return true;
    }
    
    public void smeltItem(int num)
    {
        if (this.canProcess())
        {	    	
        	
        	BlockPos pos = this.blocks.get(num).down();
        	IBlockState state = this.world.getBlockState(pos);
        	Block block = state.getBlock();
        	int meta = state.getBlock().getMetaFromState(state);
        	      	
        	//FluidStack ext = this.extractfluid.get(this.world.getBlockState(pos).getBlock());
        	/*if(ext != null)
        	{         		
        		Fluid water = ext.getFluid();
        		this.waterTank.fill(new FluidStack(water, ext.amount), true);
        		//if(this.waterTank.getFluid().isFluidEqual(new FluidStack(FluidRegistry.lookupFluidForBlock(block), 0))) this.world.setBlockToAir(pos);
        	} */
        	
        	if(meta == 0) { 
        		
	        	if(block == Blocks.WATER)
	        	{ 
	        		//this.waterTank.fill(new FluidStack(!this.stacks.get(2).isEmpty() && this.stacks.get(2) == new ItemStack(GSItems.BASIC, 1, 3) ? GSFluids.Hydrogen2 : FluidRegistry.WATER, block == Blocks.ice ? 20 : this.containingItems[2] != null ? 100 : 1000), true);
	        		
	        		Fluid water = /*!this.stacks.get(2).isEmpty() && this.stacks.get(2).isItemEqual(new ItemStack(GSItems.BASIC, 1, 11)) ? GSFluids.Hydrogen2 :*/ FluidRegistry.WATER;
	        		this.waterTank.fill(new FluidStack(water, Fluid.BUCKET_VOLUME), true);
	        		if(this.waterTank.getFluid().isFluidEqual(new FluidStack(FluidRegistry.lookupFluidForBlock(block), 0))) this.world.setBlockToAir(pos);
	        		return;
	        	}	        	
	        	else if(block == Blocks.LAVA)
	        	{ 
	        		this.waterTank.fill(new FluidStack(FluidRegistry.LAVA, Fluid.BUCKET_VOLUME), true);
	        		if(this.waterTank.getFluid().isFluidEqual(new FluidStack(FluidRegistry.lookupFluidForBlock(block), 0))) this.world.setBlockToAir(pos);
				    return;
	        	}
	        	else if(block instanceof BlockFluidClassic)
        		{
        			Fluid fluid = ((BlockFluidClassic)block).getFluid();
        			this.waterTank.fill(new FluidStack(fluid, Fluid.BUCKET_VOLUME), true);
        			if(this.waterTank.getFluid().isFluidEqual(new FluidStack(FluidRegistry.lookupFluidForBlock(block), 0))) this.world.setBlockToAir(pos);
    			    return;
        		}
			    
        		else if(block instanceof IFluidBlock)
			    {       
			    	IFluidBlock fluid = (IFluidBlock)block;
			
			    	this.waterTank.fill(new FluidStack(fluid.getFluid(), Fluid.BUCKET_VOLUME), true);
			    	if(this.waterTank.getFluid().isFluidEqual(new FluidStack(FluidRegistry.lookupFluidForBlock(block), 0))) this.world.setBlockToAir(pos);
				    return;
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
        
        this.number = par1NBTTagCompound.getInteger("number");
        //this.z = par1NBTTagCompound.getInteger("posZ");
        
        if (par1NBTTagCompound.hasKey("waterTank"))        
        	this.waterTank.readFromNBT(par1NBTTagCompound.getCompoundTag("waterTank"));
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("smeltingTicks", this.processTicks);
        ItemStackHelper.saveAllItems(par1NBTTagCompound, this.getInventory());
        
        par1NBTTagCompound.setInteger("number", this.number);

        if (this.waterTank.getFluid() != null)
        {
            par1NBTTagCompound.setTag("waterTank", this.waterTank.writeToNBT(new NBTTagCompound()));
        }
        
        return par1NBTTagCompound;
    }
   
    public EnumFacing getFront()
    {
        IBlockState state = this.world.getBlockState(getPos()); 
        if (state.getBlock() instanceof BlockLiquidExtractor)
        {
            return state.getValue(BlockLiquidExtractor.FACING);
        }
        return EnumFacing.NORTH;
    }

    
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public ItemStack getStackInSlot(int var1)
    {
        return this.getInventory().get(var1);
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
		//return EnumFacing.UP;
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

