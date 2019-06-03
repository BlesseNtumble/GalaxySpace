package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockHydroponicBase;
import micdoodle8.mods.galacticraft.api.transmission.NetworkType;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.galacticraft.core.network.IPacketReceiver;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.wrappers.FluidHandlerWrapper;
import micdoodle8.mods.galacticraft.core.wrappers.IFluidHandlerWrapper;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.relauncher.Side;

public class TileEntityHydroponicBase extends TileBaseElectricBlockWithInventory implements IFluidHandlerWrapper, ISidedInventory, IPacketReceiver {

	public static final int PROCESS_TIME_REQUIRED_BASE = 12000;
	
	@NetworkedField(targetSide = Side.CLIENT)
    public int processTimeRequired = PROCESS_TIME_REQUIRED_BASE;
	
    @NetworkedField(targetSide = Side.CLIENT)
    public int processTicks = 0;	   
	
    private final int tankCapacity = 3000;
    @NetworkedField(targetSide = Side.CLIENT)
	public FluidTank waterTank = new FluidTank(this.tankCapacity);    

    private NonNullList<ItemStack> stacks = NonNullList.withSize(20, ItemStack.EMPTY);
    
    @NetworkedField(targetSide = Side.CLIENT)
    public int moduleLevel;	
    
    //private static Map list = new HashMap<Block, Integer>();
    private static List<SeedData> seeds = new ArrayList<SeedData>();
    
    static {
    	//list.put(Blocks.CARROTS, 3);
    	//list.put(Blocks.POTATOES, 3);
    	//list.put(Blocks.WHEAT, 7);
    }
    
    public TileEntityHydroponicBase()
    {
    	this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 60 : 45);
    	this.setTierGC(1);
    }
    ///////////////////////////////
    static class SeedData
    {
    	private ItemStack seed, product;
    	private ItemStack secondproduct;
    	private Block block;
    	private int stages, secondchance;
    	private boolean[] hasRandCount = new boolean[2];
    	
    	public SeedData(ItemStack seed, ItemStack product, ItemStack secondproduct, int secondchance, Block block, int stages, boolean[] rand)
    	{
    		this.seed = seed;
    		this.product = product;
    		this.secondproduct = secondproduct;
    		this.secondchance = secondchance;
    		this.block = block;
    		this.stages = stages;
    		this.hasRandCount = rand;
    	}
    	
    	public ItemStack getSeed()
    	{
    		return this.seed;
    	}
    	
    	public ItemStack getProduct(boolean second)
    	{
    		if(second) return this.secondproduct;
    		return this.product;
    	}
    	
    	public Block getBlock()
    	{
    		return this.block;
    	}
    	
    	public int getStages()
    	{
    		return this.stages;
    	}
    	
    	public boolean[] hasRandCount()
    	{
    		return this.hasRandCount;
    	}
    	
    }
    /////////////////////////////////
    
    public static void addPlant(ItemStack seed, ItemStack product, ItemStack secproduct, int secchance, Block block, int stages, boolean[] rand)
    {
    	if(secproduct == null) secproduct = ItemStack.EMPTY;
    	SeedData data = new SeedData(seed, product, secproduct, secchance, block, stages, rand);
    	seeds.add(data);
    }
    
    public List getSeeds()
    {
    	return this.seeds;
    }
    
    public SeedData getData(ItemStack seed)
    {
    	for(SeedData data : this.seeds)
    		if(seed.isItemEqual(data.getSeed()))
    			return data;
    	
    	return null;
    }
    
    @Override
    public void update()
    {	
		super.update();
		
		this.checkBlock(world);
		
		if (!this.world.isRemote)
        {			
			if (!this.stacks.get(1).isEmpty())
            {
				 FluidStack liquid = FluidUtil.getFluidContained(this.stacks.get(1));
            		
	                if (liquid != null)
	                {
	                	boolean isWater = FluidRegistry.getFluidName(liquid).startsWith("water");
		
		                if (isWater)
		                {
		                    if (this.waterTank.getFluid() == null || this.waterTank.getFluid().amount + liquid.amount <= this.waterTank.getCapacity())
		                    {
		                        this.waterTank.fill(new FluidStack(FluidRegistry.WATER, liquid.amount), true);
		                        ItemStack bucket = this.stacks.get(1);
		                        
		                        if (bucket.getItem() != Items.WATER_BUCKET && micdoodle8.mods.galacticraft.core.util.FluidUtil.isBucket(this.stacks.get(1)) || micdoodle8.mods.galacticraft.core.util.FluidUtil.isFilledContainer(this.stacks.get(1)))
		                        {
		                        	final int amount = this.stacks.get(1).getCount();
		                            if (amount > 1) 
		                            	this.waterTank.fill(new FluidStack(FluidRegistry.WATER, (amount - 1) * Fluid.BUCKET_VOLUME), true);
		                            
		                            else {
		                            	IFluidHandlerItem handlerItem = net.minecraftforge.fluids.FluidUtil.getFluidHandler(bucket);
		                            	this.stacks.set(1, new ItemStack(handlerItem.getContainer().getItem(), amount));
		                            }
		                        }
		                        if (bucket.getItem() == Items.WATER_BUCKET)
		                        {
		                        	final int amount = this.stacks.get(1).getCount();
		                            if (amount > 1) 
		                            	this.waterTank.fill(new FluidStack(FluidRegistry.WATER, (amount - 1) * Fluid.BUCKET_VOLUME), true);
		             
		                            this.stacks.set(1, new ItemStack(Items.BUCKET, amount));
		                        }
		                        else
		                        {
		                        	this.stacks.get(1).shrink(1);
		                    		
		                            if (this.stacks.get(1).getCount() == 0)
		                            {
		                            	this.stacks.set(1, ItemStack.EMPTY);
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
                        
                        this.waterTank.drain(1, true);
                        
                        if(!this.stacks.get(8).isEmpty() 
                        		&& (this.stacks.get(8).getItem() == Items.DYE && this.stacks.get(8).getItemDamage() == 15 
                        		|| this.stacks.get(8).getItem() == GSItems.BASIC && this.stacks.get(8).getItemDamage() == 4))
                        {
                        	
                        	if (this.processTicks % 100 == 0) {
                        		this.processTicks -= 2000;                        		
                        		if(this.stacks.get(8).getCount() > 1) this.stacks.get(8).shrink(1);
                        		else this.stacks.set(8, ItemStack.EMPTY);
                        	}
                        	
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
		if (this.waterTank.getFluidAmount() < 10 * this.getModuleLevel())        
      		return false;
				
		for(int i = 1; i <= this.getModuleLevel(); i++)
		{
			if(!this.stacks.get(i*2+1).isEmpty() 
					&& this.stacks.get(i*2+1).getCount() >= 64 &&					
					!this.stacks.get(i+8).isEmpty() 
					&& this.stacks.get(i+8).getCount() >= 64) return false;
			
			if(this.stacks.get(i*2).isEmpty()) return false;

			
			ItemStack stack = this.stacks.get(i*2);
			SeedData data = getSeedData(stack);
			
			if(data == null) return false;
			if(!this.stacks.get(i*2+1).isEmpty() && this.stacks.get(i*2+1).getItem() != data.getProduct(false).getItem()) return false;
			
			/*
			if(!this.stacks.get(i*2).isEmpty() && this.stacks.get(i*2).getItem() != Items.WHEAT_SEEDS
					&& !this.stacks.get(i*2+1).isEmpty() && (!this.stacks.get(i*2+1).isItemEqual(this.stacks.get(i*2)))) {						
					return false;
			}
			if(!this.stacks.get(i*2).isEmpty() && this.stacks.get(i*2).getItem() == Items.WHEAT_SEEDS)
			{
				if(!this.stacks.get(i*2+1).isEmpty() && this.stacks.get(i*2+1).getItem() != Items.WHEAT) return false;
			}
			*/
			
		}
        
		return true;		
    }
    
    public void smeltItem()
    {
    	
    	
		for(int i = 1; i <= this.getModuleLevel(); i++)
			if(!this.stacks.get(i*2).isEmpty()) {
				
				/*SeedData seed = null;
				for(SeedData seeds : this.seeds)
				{
					if (this.stacks.get(i*2).isItemEqual(seeds.getSeed()))
					{
						seed = seeds;
						break;
					}
				}*/
				
				Random rand = new Random();
				SeedData seed = this.getSeedData(this.stacks.get(i*2));
				
				if (seed != null && this.stacks.get(i*2).isItemEqual(seed.getSeed())) 
				{
					if(this.stacks.get(i*2+1).isEmpty()) {
						ItemStack stack = seed.getProduct(false).copy();
						stack.setCount(1 + (seed.hasRandCount[0] ? rand.nextInt(3) : 0));						
						this.stacks.set(i*2+1, stack);						
					}
					else 
						this.stacks.get(i*2+1).grow(1 + (seed.hasRandCount[0] ? rand.nextInt(3) : 0));
						
					if(!seed.getProduct(true).isEmpty() && rand.nextInt(101 - seed.secondchance) == 0)
					{
						
						if(this.stacks.get(i+8).isEmpty()) {
							ItemStack stack = seed.getProduct(true).copy();
							stack.setCount(1 + (seed.hasRandCount[1] ? rand.nextInt(3) : 0));
							this.stacks.set(i+8, stack);
						}
						else {
							this.stacks.get(i+8).grow(1 + (seed.hasRandCount[1] ? rand.nextInt(3) : 0));		
						}
					}
						
				}	
				
				if(this.stacks.get(i*2).getCount() > 1) this.stacks.get(i*2).shrink(1);
				else this.stacks.set(i*2, ItemStack.EMPTY);	
			}

    }
    
    public void checkBlock(World world)
	{
		
		boolean[] check = new boolean[4];

		for (int i = 0; world.getBlockState(pos.up(i+1)).getBlock() == GSBlocks.HYDROPONIC_FARM; i++) {
			check[i] = true;
		}

		int k = 0;

		for (int i = 0; i < 3; i++)
			if (check[i])
				k++;

		this.setModuleLevel(k);
		
		
		
		
		for(int i = 1; i <= this.getModuleLevel(); i++)
		{
			if(world.getTileEntity(pos.up(i)) instanceof TileEntityHydroponicFarm)
			{
				TileEntityHydroponicFarm farm = (TileEntityHydroponicFarm) world.getTileEntity(pos.up(i));
				
				if(farm != null) 
				{
					
					if(!this.stacks.get(i * 2).isEmpty()) {
						
						SeedData data = getSeedData(this.stacks.get(i * 2));
						
						if(data != null) {
							int scale = processTicks > 0 ? (int) ((double) this.processTicks / (double) this.processTimeRequired * (int) data.getStages()) : (int) data.getStages();
						
							farm.setPlant(data.getBlock());
							farm.setMetaPlant(data.getStages() - scale);
							farm.markDirty();
						}
						else
						{
							farm.setPlant(null);
							farm.setMetaPlant(0);
							farm.markDirty();
						}
					}
					else {
						farm.setPlant(null);
						farm.setMetaPlant(0);
						farm.markDirty();
					}	
					
				}
				
				if(i > this.getModuleLevel())
				{
					if(!this.stacks.get(i * 2).isEmpty()) {
						this.stacks.set(i * 2, ItemStack.EMPTY);
					}
				}
			}
		}
	}	
    
    private SeedData getSeedData(ItemStack stack)
    {
    	for(SeedData seeds : this.seeds)
    	{
    		if(seeds.getSeed().isItemEqual(stack))
    			return seeds;
    	}
    	return null;
    }

    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket()    
    {
    	NBTTagCompound nbttagcompound = new NBTTagCompound();
    	writeToNBT(nbttagcompound);
    	return new SPacketUpdateTileEntity(pos, 1, nbttagcompound);
    }
    
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		super.onDataPacket(net, packet);
		readFromNBT(packet.getNbtCompound());		
	}
	
	@Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        
        this.moduleLevel = par1NBTTagCompound.getInteger("moduleTier");
        this.processTicks = par1NBTTagCompound.getInteger("smeltingTicks");
        this.stacks = this.readStandardItemsFromNBT(par1NBTTagCompound);
        
        
        if (par1NBTTagCompound.hasKey("waterTank"))
        {
            this.waterTank.readFromNBT(par1NBTTagCompound.getCompoundTag("waterTank"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("smeltingTicks", this.processTicks);
        par1NBTTagCompound.setInteger("moduleTier", this.moduleLevel);
        this.writeStandardItemsToNBT(par1NBTTagCompound, this.stacks);
        
        if (this.waterTank.getFluid() != null)
        {
            par1NBTTagCompound.setTag("waterTank", this.waterTank.writeToNBT(new NBTTagCompound()));
        }
        return par1NBTTagCompound;
    }
    
    public int getModuleLevel()
    {
        return this.moduleLevel;
    }

    public void setModuleLevel(int newTier)
    {
        this.moduleLevel = newTier;
    }
    
    @Override
    public boolean hasCustomName()
    {
        return true;
    }
    
    @Override
    public String getName()
    {
        return GCCoreUtil.translate("tile.hydroponic_base.name");
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
        return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
    }

    @Override
    public boolean canInsertItem(int slotID, ItemStack itemstack, EnumFacing side)
    {
        return this.isItemValidForSlot(slotID, itemstack);
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack itemstack, EnumFacing side)
    {
        return slotID == 1 || slotID == 3 || slotID == 5 || slotID == 7 || slotID == 9 || slotID == 10 || slotID == 11;
    }

    @Override
    public boolean isItemValidForSlot(int slotID, ItemStack itemstack)
    {
    	switch (slotID)
        {
        case 0:
            return ItemElectricBase.isElectricItem(itemstack.getItem());
        case 1: return itemstack.getItem() == Items.WATER_BUCKET;
        case 2:
		case 4:
		case 6: 
			return itemstack.getItem() == Items.WHEAT_SEEDS || itemstack.getItem() == Items.CARROT || itemstack.getItem() == Items.POTATO;
		case 8: 
			return itemstack.getItem() == Items.DYE && itemstack.getItemDamage() == 15 || itemstack.getItem() == GSItems.BASIC && itemstack.getItemDamage() == 4;
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
	public FluidTankInfo[] getTankInfo(EnumFacing from) {

		return new FluidTankInfo[] { new FluidTankInfo(this.waterTank) };
	}

	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
		int used = 0;

		if (this.getPipe().equals(from) && resource != null) {
			final String liquidName = FluidRegistry.getFluidName(resource);

			if (liquidName != null) {
				if (liquidName.startsWith("water"))
					used = this.waterTank.fill(resource, doFill);
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
	public boolean canFill(EnumFacing from, Fluid fluid) {
		if (from == this.getPipe())
        {			
            return this.waterTank.getFluid() == null || this.waterTank.getFluidAmount() < this.waterTank.getCapacity();
        }		
		return false;
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid) {
		return false;
	}

	public EnumFacing getFront() {
		IBlockState state = this.world.getBlockState(getPos());
		if (state.getBlock() instanceof BlockHydroponicBase) {
			return state.getValue(BlockHydroponicBase.FACING);
		}
		return EnumFacing.NORTH;
	}

	@Override
	public EnumFacing getElectricInputDirection() {
		return EnumFacing.DOWN;
	}

	private EnumFacing getPipe() {
		return getFront().getOpposite();
	}

	@Override
	public boolean canConnect(EnumFacing direction, NetworkType type) {
		if (direction == null) {
			return false;
		}
		if (type == NetworkType.POWER) {
			return direction == this.getElectricInputDirection();
		}
		if (type == NetworkType.FLUID) {
			return direction == this.getPipe();
		}
		return false;
	}
	
	public int getScaledFluidLevel(int i)
    {
         return this.waterTank.getFluidAmount() * i / this.waterTank.getCapacity();
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
}
