package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.EnumSet;

import cpw.mods.fml.relauncher.Side;
import galaxyspace.core.configs.GSConfigEnergy;
import galaxyspace.core.registers.items.GSItems;
import micdoodle8.mods.galacticraft.api.transmission.NetworkType;
import micdoodle8.mods.galacticraft.api.transmission.tile.IConnector;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseUniversalElectricalSource;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.core.items.ItemCanisterGeneric;
import micdoodle8.mods.galacticraft.core.network.IPacketReceiver;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.planets.asteroids.AsteroidsModule;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
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

public class TileEntityPortableNuclearReactor extends TileBaseUniversalElectricalSource implements IFluidHandler, IInventory, ISidedInventory, IPacketReceiver, IConnector
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
    public static final float MAX_GENERATE_GJ_PER_TICK = 1024 * GSConfigEnergy.coefficientNuclearGenerator;
    public static final float MIN_GENERATE_GJ_PER_TICK = 1;

    private static final float BASE_ACCELERATION = 0.3f;

    private final int tankCapacity = 4000;
   /* 
    @NetworkedField(targetSide = Side.CLIENT)
    public FluidTank fuelTank = new FluidTank(this.tankCapacity / 4);
    */
    @NetworkedField(targetSide = Side.CLIENT)
    public FluidTank waterTank = new FluidTank(this.tankCapacity);
    
    public float prevGenerateWatts = 0;

    @NetworkedField(targetSide = Side.CLIENT)
    public float heatGJperTick = 0;
    
    @NetworkedField(targetSide = Side.CLIENT)
    public int heatTick = 0;


    /**
     * The number of ticks that a fresh copy of the currently-burning item would
     * keep the furnace burning for
     */
    @NetworkedField(targetSide = Side.CLIENT)
    public int itemCookTime = 0;
    /**
     * The ItemStacks that hold the items currently being used in the battery
     * box
     */
    private ItemStack[] containingItems = new ItemStack[6];

    public TileEntityPortableNuclearReactor()
    {
    	this.storage.setCapacity(500000);
        this.storage.setMaxExtract(TileEntityPortableNuclearReactor.MAX_GENERATE_GJ_PER_TICK - TileEntityPortableNuclearReactor.MIN_GENERATE_GJ_PER_TICK);
    }
    
    public int getScaledWaterLevel(int i)
    {
    	return this.waterTank.getFluidAmount() * i / this.waterTank.getCapacity();
    }
    
    @Override
    public void updateEntity()
    {
        if (this.heatGJperTick - TileEntityPortableNuclearReactor.MIN_GENERATE_GJ_PER_TICK > 0)
        {
            this.receiveEnergyGC(null, (this.heatGJperTick - TileEntityPortableNuclearReactor.MIN_GENERATE_GJ_PER_TICK), false);
        }

        super.updateEntity();

        if (!this.worldObj.isRemote)
        {
        	for(int i = 0; i < 4; i++)
        		this.recharge(this.containingItems[2+i]);
        	
        	if(this.containingItems[0] != null) {
        		
				if (this.containingItems[0].getItem() == Items.water_bucket) {
					FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(this.containingItems[0]);
					if (this.waterTank.getFluid() == null
							|| this.waterTank.getFluid().amount + liquid.amount <= this.waterTank.getCapacity()) {
						this.waterTank.fill(new FluidStack(FluidRegistry.WATER, 1000), true);
						this.containingItems[0] = new ItemStack(Items.bucket);
					}
	
				}
				
				else if (this.containingItems[0].getItem() == AsteroidsItems.canisterLN2)
	            {
	            	int originalDamage = this.containingItems[0].getItemDamage();
	            	int used = this.waterTank.fill(new FluidStack(AsteroidsModule.fluidLiquidNitrogen, ItemCanisterGeneric.EMPTY - originalDamage), true);
	            	if (originalDamage + used == ItemCanisterGeneric.EMPTY)
	            		this.containingItems[0] = new ItemStack(GCItems.oilCanister, 1, ItemCanisterGeneric.EMPTY);
	            	else
	            		this.containingItems[0] = new ItemStack(AsteroidsItems.canisterLN2, 1, originalDamage + used);
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
        /*	final int fuelAmount = this.fuelTank.getFluidAmount();
            final int fuelSpace = (this.fuelTank.getCapacity() - this.fuelTank.getFluidAmount());
*/
            //final int amountToDrain = Math.min(Math.min(fuelAmount, fuelSpace), 1);
            
    		if(itemCookTime == 0)
    		{
    			if(this.containingItems[1].stackSize == 1)
    			{
    				this.containingItems[1] = null;
    			}
    			else this.containingItems[1].splitStack(1);
    			
    			itemCookTime = 1000;
    		}
    		else if(this.worldObj.rand.nextInt(10) > 5) itemCookTime--;
    		/*
    		if(this.ticks % (20*60 * 1) == 0) 
    		{
    			if(this.containingItems[1].stackSize == 1)
    			{
    				this.containingItems[1] = null;
    			}
    			else this.containingItems[1].splitStack(1);
    		}*/
    		
            if (this.heatTick < 550 && this.ticks % 20 == 0)
            {        
            	if(this.waterTank.getFluidAmount() <= 0)
            	{
            		this.heatTick++;
            	}
            	else
            	{
            		if(this.heatTick > 450) 
            		{
            			this.heatTick--;            			
            		}
            		else this.heatTick++;            		
            	}
            }
            
            if (this.heatTick >= 500)            	
            {
            	worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
            	worldObj.createExplosion(null, this.xCoord, this.yCoord + 1, this.zCoord, 0.5F, false);
            }
            
            if(this.waterTank.getFluidAmount() > 0) {
            	if(this.waterTank.getFluid().getFluid().getName().contains("nitrogen") && this.ticks % 20 == 0) this.waterTank.drain(1, true);
            	if(this.waterTank.getFluid().getFluid().getName().contains("water") && this.ticks % 2 == 0) this.waterTank.drain(1, true);
            }
            
            this.heatGJperTick = Math.min(this.heatGJperTick + Math.max(this.heatGJperTick * 0.5F, TileEntityPortableNuclearReactor.BASE_ACCELERATION), TileEntityPortableNuclearReactor.MAX_GENERATE_GJ_PER_TICK  + 3 * this.heatTick);
        }
    	
    }
    
    public boolean canProcess()
    {
        /*if (this.waterTank.getFluidAmount() <= 0)
        {
        	if(this.heatGJperTick > 0) this.heatGJperTick--;
        	return false;
        }*/
        
    	if(this.containingItems[1] == null && this.itemCookTime == 0) 
    	{
    		if(this.heatGJperTick > 0) this.heatGJperTick = 0;
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
        this.heatTick = par1NBTTagCompound.getInteger("reactorTemp");
        this.itemCookTime = par1NBTTagCompound.getInteger("itemCookTime");
        this.heatGJperTick = par1NBTTagCompound.getFloat("generateRate");
        
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
        
        if (par1NBTTagCompound.hasKey("waterTank"))
        {
            this.waterTank.readFromNBT(par1NBTTagCompound.getCompoundTag("waterTank"));
        }
        
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("reactorTemp", this.heatTick);
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
        if (this.waterTank.getFluid() != null)
        {
            par1NBTTagCompound.setTag("waterTank", this.waterTank.writeToNBT(new NBTTagCompound()));
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
        return GCCoreUtil.translate("tile.PortableNuclearReactor.name");
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
    	switch (slotID) {
    		case 0: return itemstack.getItem() == Items.water_bucket || itemstack.getItem() == AsteroidsItems.canisterLN2;
    		case 1: return itemstack.getItem() == GSItems.Ingots && itemstack.getItemDamage() == 3;
    		case 2: 
    		case 3: 
    		case 4: 
    		case 5: 
    			return true;
    	}
        return false;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1)
    {
        return new int[] { 0, 1, 2, 3, 4, 5 };
    }

    @Override
    public boolean canInsertItem(int slotID, ItemStack itemstack, int j)
    {
        return this.isItemValidForSlot(slotID, itemstack);
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack itemstack, int j)
    {
        return slotID == 1 || slotID == 2 || slotID == 3 || slotID == 4 || slotID == 5;
    }

    @Override
    public float receiveElectricity(ForgeDirection from, float energy, int tier, boolean doReceive)
    {
        return 0;
    }

    @Override
    public EnumSet<ForgeDirection> getElectricalInputDirections()
    {
        return EnumSet.noneOf(ForgeDirection.class);
    }

    @Override
    public EnumSet<ForgeDirection> getElectricalOutputDirections()
    {
        return EnumSet.of(ForgeDirection.getOrientation(0));
    }

    
    @Override
    public ForgeDirection getElectricalOutputDirectionMain()
    {
        return ForgeDirection.getOrientation(0);
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

        //if (from.equals(ForgeDirection.getOrientation((this.getBlockMetadata() & 3) + 2)))
        if (from.equals(ForgeDirection.UP))
        {
            final String liquidName = FluidRegistry.getFluidName(resource);

            if (liquidName != null && (liquidName.startsWith("water") || liquidName.startsWith("nitrogen")) && this.waterTank.getFluidAmount() < this.waterTank.getCapacity()) used = this.waterTank.fill(resource, doFill);
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
		/*if(this.fuelTank.getFluid() == null || this.fuelTank.getFluidAmount() < this.fuelTank.getCapacity())
			return true;
		*/
		if(this.waterTank.getFluid() == null || this.waterTank.getFluidAmount() < this.waterTank.getCapacity())
			return true;
		
        return false;
    }

	@Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        return new FluidTankInfo[] { new FluidTankInfo(this.waterTank) };
    }
	
	
}
