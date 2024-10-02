package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.HashSet;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import micdoodle8.mods.galacticraft.api.tile.IDisableableMachine;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3Dim;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.galacticraft.core.entities.IBubbleProvider;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public class TileEntityRadiationStabiliser extends TileBaseElectricBlockWithInventory implements ISidedInventory, IDisableableMachine, IBubbleProvider {

	public float bubbleSize;
	public static HashSet<BlockVec3Dim> loadedTiles = new HashSet();
	private ItemStack[] containingItems = new ItemStack[2];
	
	@NetworkedField(targetSide = Side.CLIENT)
    public boolean shouldRenderBubble = true;
	
	public TileEntityRadiationStabiliser()
    {
        this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 90 : 45);
    }
	
	public double getDistanceFromServer(double par1, double par3, double par5)
    {
        final double d3 = this.xCoord + 0.5D - par1;
        final double d4 = this.yCoord + 0.5D - par3;
        final double d5 = this.zCoord + 0.5D - par5;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }
	
	@Override
    public void validate()
    {
    	super.validate();
        if (!this.worldObj.isRemote) this.loadedTiles.add(new BlockVec3Dim(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId));
    }
	
	@Override
    public void onChunkUnload()
    {
        this.loadedTiles.remove(new BlockVec3Dim(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId));
    	super.onChunkUnload();
    }
	
	@Override
    public void invalidate()
    {
        if (!this.worldObj.isRemote/* && this.oxygenBubble != null*/)
        {
        	int bubbleR = MathHelper.ceiling_double_int(bubbleSize);
            int bubbleR2 = (int) (bubbleSize * bubbleSize);
        	for (int x = this.xCoord - bubbleR; x < this.xCoord + bubbleR; x++)
            {
                for (int y = this.yCoord - bubbleR; y < this.yCoord + bubbleR; y++)
                {
                    for (int z = this.zCoord - bubbleR; z < this.zCoord + bubbleR; z++)
                    {
                        Block block = this.worldObj.getBlock(x, y, z);
/*
                        if (block instanceof IOxygenReliantBlock && this.getDistanceFromServer(x, y, z) <= bubbleR2)
                        {
                        	this.worldObj.scheduleBlockUpdateWithPriority(x, y, z, block, 1, 0);
                        }*/
                    }
                }
            }
//        	this.oxygenBubble.setDead();
            this.loadedTiles.remove(new BlockVec3Dim(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId));
        }

        super.invalidate();
    }
	
	@Override
    public double getPacketRange()
    {
        return 64.0F;
    }
	
	@Override
    public void updateEntity()
    {
        super.updateEntity();
        
		if (!this.worldObj.isRemote) {
			
			if (this.getEnergyStoredGC() > 0.0F && this.hasEnoughEnergyToRun && !this.disabled) {
				this.bubbleSize += 0.01F;
			} else {
				this.bubbleSize -= 0.05F;
			}

			this.bubbleSize = Math.min(Math.max(this.bubbleSize, 0.0F),	this.containingItems[1] != null ? 20.0F : 10.0F);
		}
    }
	
	public void addExtraNetworkedData(List<Object> networkedList)
    {
        if (!this.worldObj.isRemote && !this.isInvalid())
        {
        	if (MinecraftServer.getServer().isDedicatedServer())
        	{
        		networkedList.add(loadedTiles.size());
        		//TODO: Limit this to ones in the same dimension as this tile?
        		for (BlockVec3Dim distributor : loadedTiles)
        		{
        			if (distributor == null)
        			{
        				networkedList.add(-1);
        				networkedList.add(-1);
        				networkedList.add(-1);
        				networkedList.add(-1);
        			}
        			else
        			{
        				networkedList.add(distributor.x);
        				networkedList.add(distributor.y);
        				networkedList.add(distributor.z);
        				networkedList.add(distributor.dim);
        			}
        		}
        	}
        	else
        		networkedList.add(-1);
            networkedList.add(this.bubbleSize);
        }
    }
	
	@Override
    public void readExtraNetworkedData(ByteBuf dataStream)
    {
        if (this.worldObj.isRemote)
        {
            int size = dataStream.readInt();
            if (size >= 0)
            {
            	loadedTiles.clear();
	            for (int i = 0; i < size; ++i)
	            {
	            	int i1 = dataStream.readInt();
	            	int i2 = dataStream.readInt();
	            	int i3 = dataStream.readInt();
	            	int i4 = dataStream.readInt();
	            	if (i1 == -1 && i2 == -1 && i3 == -1 && i4 == -1) continue;
	            	this.loadedTiles.add(new BlockVec3Dim(i1, i2, i3, i4));
	            }
            }
            this.bubbleSize = dataStream.readFloat();
        }
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        return AxisAlignedBB.getBoundingBox(this.xCoord - this.bubbleSize, this.yCoord - this.bubbleSize, this.zCoord - this.bubbleSize, this.xCoord + this.bubbleSize, this.yCoord + this.bubbleSize, this.zCoord + this.bubbleSize);
    }
	
	@Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        if (nbt.func_150296_c().contains("bubbleVisible"))
        {
            this.setBubbleVisible(nbt.getBoolean("bubbleVisible"));
        }

        if (nbt.func_150296_c().contains("bubbleSize"))
        {
            this.bubbleSize = nbt.getFloat("bubbleSize");
        }
//        this.hasValidBubble = nbt.getBoolean("hasValidBubble");

        final NBTTagList var2 = nbt.getTagList("Items", 10);
        this.containingItems = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            final NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            final int var5 = var4.getByte("Slot") & 255;

            if (var5 < this.containingItems.length)
            {
                this.containingItems[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
    }
	
	@Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        nbt.setBoolean("bubbleVisible", this.shouldRenderBubble);
        nbt.setFloat("bubbleSize", this.bubbleSize);
//        nbt.setBoolean("hasValidBubble", this.hasValidBubble);

        final NBTTagList list = new NBTTagList();

        for (int var3 = 0; var3 < this.containingItems.length; ++var3)
        {
            if (this.containingItems[var3] != null)
            {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var3);
                this.containingItems[var3].writeToNBT(var4);
                list.appendTag(var4);
            }
        }

        nbt.setTag("Items", list);
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
            final ItemStack var2 = this.containingItems[par1];
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
    public boolean hasCustomInventoryName()
    {
        return true;
    }
	
	@Override
    public String getInventoryName()
    {
        return GCCoreUtil.translate("tile.radiation_stabiliser.name");
    }
	
	@Override
    public int getInventoryStackLimit()
    {
        return 1;
    }
	
	@Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && par1EntityPlayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
    }
	
	@Override
    public int[] getAccessibleSlotsFromSide(int side)
    {
        return new int[] { 0 };
    }
	
	@Override
    public boolean canInsertItem(int slotID, ItemStack par2ItemStack, int par3)
    {
        return this.isItemValidForSlot(slotID, par2ItemStack);
    }
	
	@Override
	public boolean isItemValidForSlot(int slotID, ItemStack itemStack) {
		
		switch (slotID) {
			case 0:
				return ItemElectricBase.isElectricItem(itemStack.getItem());
		}

		return false;
	}

	@Override
	public float getBubbleSize() {
		return this.bubbleSize;
	}

	@Override
	public void setBubbleVisible(boolean shouldRender) {
		this.shouldRenderBubble = shouldRender;		
	}

	@Override
	public boolean getBubbleVisible() {
		return this.shouldRenderBubble;
	}

	@Override
	public boolean canExtractItem(int slotID, ItemStack par2ItemStack, int par3) {
		return slotID == 0;
	}

	@Override
	protected ItemStack[] getContainingItems() {
		return this.containingItems;
	}

	@Override
	public boolean shouldUseEnergy() {
		return this.hasEnoughEnergyToRun;
	}
	
	@Override
    public ItemStack getBatteryInSlot()
    {
        return this.getStackInSlot(0);
    }
	
	public boolean inBubble(double pX, double pY, double pZ)
    {
        double r = bubbleSize;
        r *= r;
        double d3 = this.xCoord + 0.5D - pX;
        d3 *= d3;
        if (d3 > r) return false;
        double d4 = this.zCoord + 0.5D - pZ;
        d4 *= d4;
        if (d3 + d4 > r) return false;
        double d5 = this.yCoord + 0.5D - pY;
        return d3 + d4 + d5 * d5 < r;
    }
}
