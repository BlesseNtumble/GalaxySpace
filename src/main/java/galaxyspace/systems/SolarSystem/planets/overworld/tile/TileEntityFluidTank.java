package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import galaxyspace.core.registers.blocks.GSBlocks;
import io.netty.buffer.ByteBuf;
import micdoodle8.mods.galacticraft.core.tile.TileEntityAdvanced;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityFluidTank extends TileEntityAdvanced implements IFluidHandler, ISidedInventory
{

    public final static int tankCapacity = 60000;
    
    @NetworkedField(targetSide = Side.CLIENT)
    public FluidTank waterTank = new FluidTank(this.tankCapacity);
    

    public int getScaledWaterLevel(int i)
    {
    	return this.waterTank.getFluidAmount() * i / this.waterTank.getCapacity();
    }
    
    @Override
    public void updateEntity()
    {
    	super.updateEntity();
    	
    	if(!this.worldObj.isRemote)
    	{
	    	if(this.worldObj.getBlock(xCoord, yCoord + 1, zCoord) == GSBlocks.FluidTank && this.worldObj.getBlockMetadata(xCoord, yCoord + 1, zCoord) == 1 && this.getBlockMetadata() == 1)
	    	{
		    	TileEntityFluidTank fluidUp = (TileEntityFluidTank) this.worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
		    	if(fluidUp != null && this.waterTank.getFluidAmount() >= this.waterTank.getCapacity())
		    	{
		    		//if(fluidDown.waterTank.getFluid() != null) this.waterTank.fill(new FluidStack(fluidDown.waterTank.getFluid().getFluid(), 1000), true);
		    		//if(this.waterTank.getFluidAmount() < this.waterTank.getCapacity()) fluidDown.drain(ForgeDirection.UP, 1000, true);
		    		if(this.waterTank.getFluid() != null) fluidUp.fill(ForgeDirection.DOWN, new FluidStack(this.waterTank.getFluid().getFluid(), 1000), true);
		    		this.waterTank.drain(1000, true);
		    	
		    	}
	    	}
	    	
	    	if(this.worldObj.getBlock(xCoord, yCoord - 1, zCoord) == GSBlocks.FluidTank && this.worldObj.getBlockMetadata(xCoord, yCoord - 1, zCoord) == 0 && this.getBlockMetadata() == 0)
	    	{
		    	TileEntityFluidTank fluidDown = (TileEntityFluidTank) this.worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
		    	if(fluidDown != null && fluidDown.waterTank.getFluidAmount() < fluidDown.waterTank.getCapacity())
		    	{
		    		if(this.waterTank.getFluid() != null) fluidDown.fill(ForgeDirection.UP, new FluidStack(this.waterTank.getFluid().getFluid(), 1000), true);
		    		this.waterTank.drain(1000, true);
		    	}
	    	}
    	}
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
        
        if(par1NBTTagCompound.hasKey("waterTank"))
		{
			waterTank.readFromNBT(par1NBTTagCompound.getCompoundTag("waterTank"));
		}
    }

    
    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        
        if(waterTank.getFluid() != null)
		{
        	par1NBTTagCompound.setTag("waterTank", waterTank.writeToNBT(new NBTTagCompound()));
        }

    }
    
    @Override
    public int getSizeInventory()
    {
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int par1)
    {
        return null;
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2)
    {
    	return null;        
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        return null;        
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
    }

    @Override
    public String getInventoryName()
    {
        return GCCoreUtil.translate("tile.FluidTank.name");
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 0;
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
        return false;
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

	/*
    @Override
	public float getRequest(ForgeDirection direction)
	{
		return 0;
	}
	*/

    
    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
    	if(this.getBlockMetadata() == 0 && from.equals(ForgeDirection.DOWN) || this.getBlockMetadata() == 1 && from.equals(ForgeDirection.UP))
    		return this.waterTank.getFluid() != null && this.waterTank.getFluidAmount() > 0;
        
    	return false;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        int used = 0;

        if (this.getBlockMetadata() == 0 && from.equals(ForgeDirection.UP) || this.getBlockMetadata() == 1 && from.equals(ForgeDirection.DOWN))
        {
            final String liquidName = FluidRegistry.getFluidName(resource);

            if (liquidName != null) used = this.waterTank.fill(resource, doFill);
        }
        return used;
    }

    @Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) 
	{
    	if(this.getBlockMetadata() == 0 && from.equals(ForgeDirection.DOWN) || this.getBlockMetadata() == 1 && from.equals(ForgeDirection.UP))
    	{        	
        	return this.waterTank.drain(resource.amount, true);
        }
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		if(this.getBlockMetadata() == 0 && from.equals(ForgeDirection.DOWN) || this.getBlockMetadata() == 1 && from.equals(ForgeDirection.UP))
	    {
            return this.waterTank.drain(maxDrain, true);
        }
		return null;
	}

	@Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        return this.waterTank.getFluid() == null || this.waterTank.getFluidAmount() < this.waterTank.getCapacity();
    }

	@Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        return new FluidTankInfo[] { new FluidTankInfo(this.waterTank) };
    }

	@Override
	public double getPacketRange() {

		return 64.0F;
	}

	@Override
	public int getPacketCooldown() {

		return 3;
	}

	@Override
	public boolean isNetworkedTile() {

		return true;
	}
	
	
	public void addExtraNetworkedData(List<Object> networkedList)
	{
		
		if(waterTank.getFluid() != null)
		{
			networkedList.add(1);
			networkedList.add(waterTank.getInfo().fluid.getFluidID());
			networkedList.add(waterTank.getFluidAmount());
		}
		else
		{
			networkedList.add(0);
		}
	}
	

    @Override
	public void readExtraNetworkedData(ByteBuf dataStream)
	{
		if(this.worldObj.isRemote && dataStream.readInt() == 1)
		{
			waterTank.setCapacity(this.tankCapacity);
			waterTank.setFluid(new FluidStack(FluidRegistry.getFluid(dataStream.readInt()), dataStream.readInt()));
		}
	}
}
