package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.EnumSet;

import cpw.mods.fml.relauncher.Side;
import galaxyspace.core.configs.GSConfigEnergy;
import micdoodle8.mods.galacticraft.api.transmission.NetworkType;
import micdoodle8.mods.galacticraft.api.transmission.tile.IConnector;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseUniversalElectricalSource;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.core.items.ItemCanisterGeneric;
import micdoodle8.mods.galacticraft.core.network.IPacketReceiver;
import micdoodle8.mods.galacticraft.core.util.FluidUtil;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityFuelGenerator extends TileBaseUniversalElectricalSource implements IFluidHandler, IInventory, ISidedInventory, IPacketReceiver, IConnector
{
    //New energy rates:
    //
    //Tier 1 machine typically consumes 600 gJ/s = 30 gJ/t

    //Coal generator on max heat can power up to 4 Tier 1 machines
    //(fewer if one of them is an Electric Furnace)
    //Basic solar gen in full sun can power 1 Tier 1 machine

    //1 lump of coal is equivalent to 38400 gJ
    //because on max heat it produces 120 gJ/t over 320 ticks

    //Below the min_generate, all heat is wasted
    //At max generate, 100% efficient conversion coal energy -> electric makes 120 gJ/t
    public static final float MAX_GENERATE_GJ_PER_TICK = 81 * GSConfigEnergy.coefficientFuelGenerator;
    public static final float MIN_GENERATE_GJ_PER_TICK = 1;

    private static final float BASE_ACCELERATION = 0.3f;

    private final int tankCapacity = 4000;
    @NetworkedField(targetSide = Side.CLIENT)
    public FluidTank fuelTank = new FluidTank(this.tankCapacity);
    public float prevGenerateWatts = 0;

    @NetworkedField(targetSide = Side.CLIENT)
    public float heatGJperTick = 0;


    @NetworkedField(targetSide = Side.CLIENT)
    public int itemCookTime = 0;

    private ItemStack[] containingItems = new ItemStack[1];

    public TileEntityFuelGenerator()
    {
        this.storage.setMaxExtract(TileEntityFuelGenerator.MAX_GENERATE_GJ_PER_TICK - TileEntityFuelGenerator.MIN_GENERATE_GJ_PER_TICK);
    }

    public int getScaledFuelLevel(int i)
    {
         return this.fuelTank.getFluidAmount() * i / this.fuelTank.getCapacity();
    }
    
    @Override
    public void updateEntity()
    {
        if (this.heatGJperTick - TileEntityFuelGenerator.MIN_GENERATE_GJ_PER_TICK > 0)
        {
            this.receiveEnergyGC(null, (this.heatGJperTick - TileEntityFuelGenerator.MIN_GENERATE_GJ_PER_TICK), false);
        }

        super.updateEntity();

        if (!this.worldObj.isRemote)
        {
        	
            if (this.containingItems[0] != null)
            {
            	if (this.containingItems[0].getItem() == Items.lava_bucket)
                {
            		if(this.fuelTank.getFluid() != null && this.fuelTank.getFluid().getFluid() == GalacticraftCore.fluidFuel) return;
                	FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(this.containingItems[0]);
                	if (this.fuelTank.getFluid() == null || this.fuelTank.getFluid().amount + liquid.amount <= this.fuelTank.getCapacity())
                	{  
                		this.fuelTank.fill(new FluidStack(FluidRegistry.LAVA, 1000), true);
                		this.containingItems[0] = new ItemStack(Items.bucket);
                	}
           
                }
            	
                if (this.containingItems[0].getItem() == GCItems.bucketFuel)
                {
                	if(this.fuelTank.getFluid() != null && this.fuelTank.getFluid().getFluid() == FluidRegistry.LAVA) return;
                	
                	FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(this.containingItems[0]);
                	if (this.fuelTank.getFluid() == null || this.fuelTank.getFluid().amount + liquid.amount <= this.fuelTank.getCapacity())
                	{  
                		//this.itemCookTime = 320 * 16;
                		//this.decrStackSize(0, 1);
                		this.fuelTank.fill(new FluidStack(GalacticraftCore.fluidFuel, 1000), true);
                		this.containingItems[0] = new ItemStack(Items.bucket);
                	}
           
                }
                if (this.containingItems[0].getItem() == GCItems.fuelCanister)
                {
                	int originalDamage = this.containingItems[0].getItemDamage();
                	int used = this.fuelTank.fill(new FluidStack(GalacticraftCore.fluidFuel, ItemCanisterGeneric.EMPTY - originalDamage), true);
                	if (originalDamage + used == ItemCanisterGeneric.EMPTY)
                		this.containingItems[0] = new ItemStack(GCItems.oilCanister, 1, ItemCanisterGeneric.EMPTY);
                	else
                		this.containingItems[0] = new ItemStack(GCItems.fuelCanister, 1, originalDamage + used);
                }
                else
                {
	                FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(this.containingItems[0]);
	
	                if (liquid != null)
	                {
	                	boolean isFuel = FluidUtil.testFuel(FluidRegistry.getFluidName(liquid)); 
	                	boolean isLava = FluidRegistry.getFluidName(liquid).startsWith("lava");
		
	                	if (isFuel || isLava)
                		{
                			if (this.fuelTank.getFluid() == null || this.fuelTank.getFluid().amount + liquid.amount <= this.fuelTank.getCapacity())
                			{
                				if(this.fuelTank.getFluid() != null && this.fuelTank.getFluid().getFluid() == (!isLava ? GalacticraftCore.fluidFuel : FluidRegistry.LAVA)) return;
                            	
                				this.fuelTank.fill(new FluidStack(isLava ? FluidRegistry.LAVA : GalacticraftCore.fluidFuel, liquid.amount), true);

                				if (FluidContainerRegistry.isBucket(this.containingItems[0]) && FluidContainerRegistry.isFilledContainer(this.containingItems[0]))
                				{
                					final int amount = this.containingItems[0].stackSize;
                					if (amount > 1) this.fuelTank.fill(new FluidStack(isLava ? FluidRegistry.LAVA : GalacticraftCore.fluidFuel, (amount - 1) * FluidContainerRegistry.BUCKET_VOLUME), true);
                					
                					this.containingItems[0] = new ItemStack(Items.bucket, amount);
                				}
                				else
                				{
                					this.containingItems[0].stackSize--;

                					if (this.containingItems[0].stackSize == 0)
                					{
                						this.containingItems[0] = null;
                					}
                				}
                			}
                		}
		                
	                }
                }
            }

            this.produce();
            this.smeltItem();

            this.heatGJperTick = Math.min(Math.max(this.heatGJperTick, 0.0F), this.getMaxEnergyStoredGC());
           
            
        }
    }

    
    public void smeltItem()
    {

    	if (this.canProcess())
        {
        	final int lavaAmount = this.fuelTank.getFluidAmount();
            final int fuelSpace = (this.fuelTank.getCapacity() - this.fuelTank.getFluidAmount());

            final int amountToDrain = Math.min(Math.min(lavaAmount, fuelSpace), 1);

            if(this.fuelTank.getFluid().getFluid() == FluidRegistry.LAVA && this.ticks % 10 == 0) this.fuelTank.drain(2, true);
            else if(this.ticks % 100 == 0) this.fuelTank.drain(1, true);
            	
            
            
            this.heatGJperTick = Math.min(this.heatGJperTick + Math.max(this.heatGJperTick * 0.005F, TileEntityFuelGenerator.BASE_ACCELERATION), TileEntityFuelGenerator.MAX_GENERATE_GJ_PER_TICK);
        }
    	
    }
    
    public boolean canProcess()
    {
        if (this.fuelTank.getFluidAmount() <= 0)
        {
        	
        		this.heatGJperTick = 0;
        		return false;

        }
        if(this.storage.getEnergyStoredGC() >= this.storage.getCapacityGC())
        	return false;

        return true;

    }
    
    @Override
    public void openInventory()
    {
    }

    @Override
    public void closeInventory()
    {
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
        NBTTagList var2 = par1NBTTagCompound.getTagList("Items", 10);
        this.containingItems = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            int var5 = var4.getByte("Slot") & 255;

            if (var5 < this.containingItems.length)
            {
                this.containingItems[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
        
        if (par1NBTTagCompound.hasKey("fuelTank"))
        {
            this.fuelTank.readFromNBT(par1NBTTagCompound.getCompoundTag("fuelTank"));
        }
        
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("itemCookTime", this.itemCookTime);
        par1NBTTagCompound.setFloat("generateRate", this.heatGJperTick);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.containingItems.length; ++var3)
        {
            if (this.containingItems[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var3);
                this.containingItems[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        if (this.fuelTank.getFluid() != null)
        {
            par1NBTTagCompound.setTag("fuelTank", this.fuelTank.writeToNBT(new NBTTagCompound()));
        }
        par1NBTTagCompound.setTag("Items", var2);
    }

    @Override
    public int getSizeInventory()
    {
        return this.containingItems.length;
    }

    @Override
    public ItemStack getStackInSlot(int par1)
    {
        return this.containingItems[par1];
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.containingItems[par1] != null)
        {
            ItemStack var3;

            if (this.containingItems[par1].stackSize <= par2)
            {
                var3 = this.containingItems[par1];
                this.containingItems[par1] = null;
                return var3;
            }
            else
            {
                var3 = this.containingItems[par1].splitStack(par2);

                if (this.containingItems[par1].stackSize == 0)
                {
                    this.containingItems[par1] = null;
                }

                return var3;
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (this.containingItems[par1] != null)
        {
            ItemStack var2 = this.containingItems[par1];
            this.containingItems[par1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.containingItems[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName()
    {
        return GCCoreUtil.translate("tile.FuelGenerator.name");
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    
    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && par1EntityPlayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return true;
    }

    @Override
    public boolean isItemValidForSlot(int slotID, ItemStack itemstack)
    {
        return itemstack.getItem() == GCItems.bucketFuel || itemstack.getItem() == GCItems.fuelCanister || itemstack.getItem() == Items.lava_bucket;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1)
    {
        return new int[] { 0 };
    }

    @Override
    public boolean canInsertItem(int slotID, ItemStack itemstack, int j)
    {
        return this.isItemValidForSlot(slotID, itemstack);
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack itemstack, int j)
    {
        return slotID == 0;
    }

    @Override
    public float receiveElectricity(ForgeDirection from, float energy, int tier, boolean doReceive)
    {
        return 0;
    }

	/*
    @Override
	public float getRequest(ForgeDirection direction)
	{
		return 0;
	}
	*/

    @Override
    public EnumSet<ForgeDirection> getElectricalInputDirections()
    {
        return EnumSet.noneOf(ForgeDirection.class);
    }

    @Override
    public EnumSet<ForgeDirection> getElectricalOutputDirections()
    {
        return EnumSet.of(ForgeDirection.getOrientation(this.getBlockMetadata() + 2));
    }

    @Override
    public ForgeDirection getElectricalOutputDirectionMain()
    {
        return ForgeDirection.getOrientation(this.getBlockMetadata() + 2);
    }

    @Override
    public boolean canConnect(ForgeDirection direction, NetworkType type)
    {
        if (direction == null || direction.equals(ForgeDirection.UNKNOWN) || type != NetworkType.POWER)
        {
            return false;
        }

        return direction == this.getElectricalOutputDirectionMain();
    }
    
    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        return false;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        int used = 0;

        if (from.equals(ForgeDirection.getOrientation(this.getBlockMetadata() + 2).getOpposite()))
        {
            final String liquidName = FluidRegistry.getFluidName(resource);

            if (liquidName != null && liquidName.startsWith("fuel")) used = this.fuelTank.fill(resource, doFill);
            if (liquidName != null && liquidName.startsWith("biofuel")) used = this.fuelTank.fill(resource, doFill);
            if (liquidName != null && liquidName.startsWith("lava")) used = this.fuelTank.fill(resource, doFill);
        }

        return used;
    }

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) 
	{
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		return null;
	}

	@Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        return this.fuelTank.getFluid() == null || this.fuelTank.getFluidAmount() < this.fuelTank.getCapacity();
    }

	@Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        return new FluidTankInfo[] { new FluidTankInfo(this.fuelTank) };
    }
	
	
}
