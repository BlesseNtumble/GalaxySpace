package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import galaxyspace.core.registers.items.GSItems;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.galacticraft.core.network.IPacketReceiver;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityHydroponicBase extends TileBaseElectricBlockWithInventory implements IFluidHandler, ISidedInventory, IPacketReceiver
{

	public static final int PROCESS_TIME_REQUIRED_BASE = 12000;
	
	@NetworkedField(targetSide = Side.CLIENT)
    public int processTimeRequired = PROCESS_TIME_REQUIRED_BASE;
    @NetworkedField(targetSide = Side.CLIENT)
    public int processTicks = 0;
	   
	
    private final int tankCapacity = 3000;
    @NetworkedField(targetSide = Side.CLIENT)
	public FluidTank waterTank = new FluidTank(this.tankCapacity);
	private ItemStack[] containingItems = new ItemStack[20];

	public int moduleLevel;	
	
	private static List<SeedData> seeds = new ArrayList<SeedData>();
	   
	//private static Map list = new HashMap<Block, Integer>();
	 /*   
	static {
	    	list.put(Blocks.carrots, 3);
	    	list.put(Blocks.potatoes, 3);
	    	list.put(Blocks.wheat, 7);
	}
	    */
	///////////////////////////////
	static class SeedData {
		private ItemStack seed, product;
		private ItemStack secondproduct;
		private Block block;
		private int stages, secondchance;
		private boolean[] hasRandCount = new boolean[2];

		public SeedData(ItemStack seed, ItemStack product, ItemStack secondproduct, int secondchance, Block block,
				int stages, boolean[] rand) {
			this.seed = seed;
			this.product = product;
			this.secondproduct = secondproduct;
			this.secondchance = secondchance;
			this.block = block;
			this.stages = stages;
			this.hasRandCount = rand;
		}

		public ItemStack getSeed() {
			return this.seed;
		}

		public ItemStack getProduct(boolean second) {
			if (second)
				return this.secondproduct;
			return this.product;
		}

		public Block getBlock() {
			return this.block;
		}

		public int getStages() {
			return this.stages;
		}

		public boolean[] hasRandCount() {
			return this.hasRandCount;
		}

	}

	public static void addPlant(ItemStack seed, ItemStack product, ItemStack secproduct, int secchance, Block block, int stages, boolean[] rand)
    {
    	SeedData data = new SeedData(seed, product, secproduct, secchance, block, stages, rand);
    	seeds.add(data);
    }
    
    public static List getSeeds()
    {
    	return seeds;
    }
    
    public static SeedData getData(ItemStack seed)
    {
    	for(SeedData data : seeds)
    		if(seed.isItemEqual(data.getSeed()))
    			return data;
    	
    	return null;
    }
    
	/////////////////////////////////
	public TileEntityHydroponicBase()
	{
		
		this.storage.setCapacity(25000);
		this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 60 : 45);
	}
	
	@Override
    public void updateEntity()
    {	
		super.updateEntity();
		
		this.checkBlock(worldObj, xCoord, yCoord, zCoord);
		
		if (!this.worldObj.isRemote)
        {			
			if (this.containingItems[1] != null)
            {
            	 FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(this.containingItems[1]);
            		
	                if (liquid != null)
	                {
	                	boolean isWater = FluidRegistry.getFluidName(liquid).startsWith("water");
		
		                if (isWater)
		                {
		                    if (this.waterTank.getFluid() == null || this.waterTank.getFluid().amount + liquid.amount <= this.waterTank.getCapacity())
		                    {
		                        this.waterTank.fill(new FluidStack(FluidRegistry.WATER, liquid.amount), true);
		
		                        if (FluidContainerRegistry.isBucket(this.containingItems[1]) && FluidContainerRegistry.isFilledContainer(this.containingItems[1]))
		                        {
		                            final int amount = this.containingItems[1].stackSize;
		                            if (amount > 1) this.waterTank.fill(new FluidStack(FluidRegistry.WATER, (amount - 1) * FluidContainerRegistry.BUCKET_VOLUME), true);
		                            this.containingItems[1] = new ItemStack(Items.bucket, amount);
		                        }
		                        else
		                        {
		                            this.containingItems[1].stackSize--;
		
		                            if (this.containingItems[1].stackSize == 0)
		                            {
		                                this.containingItems[1] = null;
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
                        
                        if (this.processTicks % 50 == 0)
                        	this.waterTank.drain(1, true);
    					
                        if(this.containingItems[8] != null 
                        		&& (this.containingItems[8].getItem() == Items.dye && this.containingItems[8].getItemDamage() == 15 
                        		|| this.containingItems[8].getItem() == GSItems.BasicItems && this.containingItems[8].getItemDamage() == 5))
                        {
                        	
                        	if (this.processTicks % 100 == 0) {
                        		this.processTicks -= 2000;
                        		if(this.containingItems[8].stackSize > 1) this.containingItems[8].stackSize--;
                        		else this.containingItems[8] = null;
                        	}
                        	
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
	
	public void checkBlock(World world, int x, int y, int z)
	{
		
		boolean[] check = new boolean[4];

		for (int i = 0; world.getTileEntity(x, y + i + 1, z) instanceof TileEntityHydroponicFarm; i++) {
			check[i] = true;
		}

		int k = 0;

		for (int i = 0; i < 3; i++)
			if (check[i])
				k++;

		this.setModuleLevel(k);
		
		for(int i = 1; i < 4; i++)
		{
			if(world.getTileEntity(x, y + i, z) instanceof TileEntityHydroponicFarm) 
			{
				TileEntityHydroponicFarm farm = (TileEntityHydroponicFarm) world.getTileEntity(x, y + i, z);
				if(farm != null) 
				{
					
					if(this.containingItems[i * 2] != null) {
						
						SeedData data = getSeedData(this.containingItems[i * 2]);
						
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
					if(this.containingItems[i * 2] != null) {
						this.containingItems[i * 2] = null;
					}
				}
			}
		}
	}	
	
	public boolean canProcess()
    {
		if (this.waterTank.getFluidAmount() < 10 * this.getModuleLevel())        
      		return false;
		
		for(int i = 1; i <= this.getModuleLevel(); i++)
		{
			if(this.containingItems[i*2+1] != null && this.containingItems[i*2+1].stackSize >= 64) return false;
			
			if(this.containingItems[i*2] != null && this.containingItems[i*2].getItem() != Items.wheat_seeds
					&& this.containingItems[i*2+1] != null && (!this.containingItems[i*2+1].isItemEqual(containingItems[i*2]))) {						
					return false;
			}
		
			if(this.containingItems[i*2] == null) return false;
			
			ItemStack stack = this.containingItems[i*2];
			SeedData data = getSeedData(stack);
			
			if(data == null) return false;
			if(this.containingItems[i*2+1] != null && this.containingItems[i*2+1].getItem() != data.getProduct(false).getItem()) return false;
			
		}
		
		
		
		/*
		if(this.getModuleLevel() == 4)
			if(this.containingItems[2] == null || this.containingItems[4] == null || this.containingItems[6] == null || this.containingItems[8] == null)    	
				return false;*/
        
		return true;		
    }
	
	public void smeltItem()
    {
		for(int i = 1; i < 4; i++)
			if(this.containingItems[i*2] != null) {
				
				Random rand = new Random();
				SeedData seed = this.getSeedData(this.containingItems[i*2]);
				
				if (seed != null && this.containingItems[i*2].isItemEqual(seed.getSeed())) 
				{
					if(this.containingItems[i*2+1] == null) {
						ItemStack stack = seed.getProduct(false).copy();
						stack.stackSize = (1 + (seed.hasRandCount[0] ? rand.nextInt(3) : 0));						
						this.containingItems[i*2+1] = stack;						
					}
					else 
						this.containingItems[i*2+1].stackSize += (1 + (seed.hasRandCount[0] ? rand.nextInt(3) : 0));
				/*
					if(seed.getProduct(true) != null && rand.nextInt(101 - seed.secondchance) == 0)
					{
						
						if(this.stacks.get(i+8).isEmpty()) {
							ItemStack stack = seed.getProduct(true).copy();
							stack.setCount(1 + (seed.hasRandCount[1] ? rand.nextInt(3) : 0));
							this.stacks.set(i+8, stack);
						}
						else {
							this.stacks.get(i+8).grow(1 + (seed.hasRandCount[1] ? rand.nextInt(3) : 0));		
						}
					}*/
					
					if(this.containingItems[i*2].stackSize > 1) this.containingItems[i*2].stackSize--;
					else this.containingItems[i*2] = null;	
				}
				/*
				if (this.containingItems[i*2].getItem() == Items.wheat_seeds) {
					if(this.containingItems[i*2+1] == null)
						this.containingItems[i*2+1] = new ItemStack(Items.wheat);
					else 
						this.containingItems[i*2+1].stackSize++;		
					
					if(this.containingItems[i+8] == null) {
						this.containingItems[i+8] = new ItemStack(Items.wheat_seeds);	
						this.containingItems[i+8].stackSize += 1 + rand.nextInt(2);	
					}
					else 
						this.containingItems[i+8].stackSize += 1 + rand.nextInt(2);	
				}
				
				if (this.containingItems[i*2].getItem() == Items.carrot) {
					if(this.containingItems[i*2+1] == null) 
					{
						this.containingItems[i*2+1] = new ItemStack(Items.carrot);
						this.containingItems[i*2+1].stackSize += this.worldObj.rand.nextInt(3) + 1;
					}
					else this.containingItems[i*2+1].stackSize += this.worldObj.rand.nextInt(3) + 1;
				}
				
				if (this.containingItems[i*2].getItem() == Items.potato) {
					if(this.containingItems[i*2+1] == null)
					{
						this.containingItems[i*2+1] = new ItemStack(Items.potato);
						this.containingItems[i*2+1].stackSize += this.worldObj.rand.nextInt(3) + 1;
					}
					else 
						this.containingItems[i*2+1].stackSize += this.worldObj.rand.nextInt(3) + 1;
					
					if(this.worldObj.rand.nextInt(98) == 0)
						if(this.containingItems[i+8] == null)						
							this.containingItems[i+8] = new ItemStack(Items.poisonous_potato);	
						else 
							this.containingItems[i+8].stackSize += 1;	
				}
				
				if(this.containingItems[i*2].stackSize > 1) this.containingItems[i*2].stackSize--;
				else this.containingItems[i*2] = null;		*/	
				
				
			}
    }
	
	private SeedData getSeedData(ItemStack stack) {
		for (SeedData seeds : this.seeds) {
			if (seeds.getSeed().isItemEqual(stack))
				return seeds;
		}
		return null;
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		writeToNBT(nbttagcompound);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbttagcompound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		super.onDataPacket(net, packet);
		readFromNBT(packet.func_148857_g());		
	}
	
	@Override
	public String getInventoryName() {
		return GCCoreUtil.translate("tile.HydroponicBase.name");
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		
		switch(slot)
		{
			case 0: return stack.getItem() instanceof ItemElectricBase;
			case 1: return stack.getItem() == Items.water_bucket;
			case 2:
			case 4:
			case 6: 
				return stack.getItem() == Items.wheat_seeds || stack.getItem() == Items.carrot || stack.getItem() == Items.potato;
			case 8:
				return stack.getItem() == Items.dye && stack.getItemDamage() == 15 || stack.getItem() == GSItems.BasicItems && stack.getItemDamage() == 5;
	
		}
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
	}

    @Override
    public boolean canInsertItem(int slotID, ItemStack par2ItemStack, int side)
    {
        return this.isItemValidForSlot(slotID, par2ItemStack);
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack par2ItemStack, int side)
    {
        return slotID == 1 || slotID == 3 || slotID == 5 || slotID == 7;
    }

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		 int used = 0;
		 
		 int metaside = this.getBlockMetadata() + 2;
		 int side = from.ordinal();
		 
		 if (7 - (metaside ^ (metaside > 3 ? 0 : 1)) == (side ^ 1))
		 {
			 final String liquidName = FluidRegistry.getFluidName(resource);
			 if (liquidName != null && liquidName.startsWith("water")) used = this.waterTank.fill(resource, doFill);
	     }
		 return used;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) 
	{
	    /*int metaside = this.getBlockMetadata() + 2;
	    int side = from.ordinal();
	    if (side == (metaside ^ 1))
	    {
	    	if (resource != null && resource.isFluidEqual(this.waterTank.getFluid()))
	    		return this.waterTank.drain(resource.amount, doDrain);
	    }*/
	    return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		/*int metaside = this.getBlockMetadata() + 2;
	    int side = from.ordinal();
	    if (side == (metaside ^ 1))
	    {
	        return this.waterTank.drain(maxDrain, doDrain);
	    }*/
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return this.waterTank.getFluid() == null || this.waterTank.getFluidAmount() < this.waterTank.getCapacity();
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {            
        return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] { new FluidTankInfo(this.waterTank) };
	}

	@Override
    public int getSizeInventory()
    {
        return this.getContainingItems().length;
    }
	
	@Override
    protected ItemStack[] getContainingItems()
    {
        return this.containingItems;
    }

	@Override
    public boolean shouldUseEnergy()
    {
        return this.canProcess();
    }
	
	public int getScaledFluidLevel(int i)
    {
         return this.waterTank.getFluidAmount() * i / this.waterTank.getCapacity();
    }
	
	@Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        
        this.moduleLevel = par1NBTTagCompound.getInteger("moduleTier");
        this.processTicks = par1NBTTagCompound.getInteger("smeltingTicks");
        this.containingItems = this.readStandardItemsFromNBT(par1NBTTagCompound);  
        
        
        if (par1NBTTagCompound.hasKey("waterTank"))
        {
            this.waterTank.readFromNBT(par1NBTTagCompound.getCompoundTag("waterTank"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("smeltingTicks", this.processTicks);
        par1NBTTagCompound.setInteger("moduleTier", this.moduleLevel);
        this.writeStandardItemsToNBT(par1NBTTagCompound); 
        
        if (this.waterTank.getFluid() != null)
        {
            par1NBTTagCompound.setTag("waterTank", this.waterTank.writeToNBT(new NBTTagCompound()));
        }
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
    public ForgeDirection getElectricInputDirection()
    {
    	return ForgeDirection.DOWN;
    }
    
    public ItemStack getItemForFarm(int slot)
    {
    	return this.containingItems[slot];
    }

}
