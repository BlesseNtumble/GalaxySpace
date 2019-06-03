package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.HashSet;
import java.util.List;

import galaxyspace.GalaxySpace;
import galaxyspace.core.prefab.entities.EntityIceSpike;
import galaxyspace.core.registers.items.GSItems;
import io.netty.buffer.ByteBuf;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3Dim;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.galacticraft.core.entities.EntityMeteor;
import micdoodle8.mods.galacticraft.core.entities.IBubbleProviderColored;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityPlanetShield extends TileBaseElectricBlockWithInventory implements ISidedInventory, IBubbleProviderColored{

	public float bubbleSize;
	public static HashSet<BlockVec3Dim> loadedTiles = new HashSet<>();
	private NonNullList<ItemStack> stacks = NonNullList.withSize(1, ItemStack.EMPTY);
	
	@NetworkedField(targetSide = Side.CLIENT)
    public boolean shouldRenderBubble = true;
	
	public TileEntityPlanetShield()
    {
		this.storage.setCapacity(20000F);
        this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 250 : 205);
    }
	
	@Override
    public void validate()
    {
    	super.validate();
        if (!this.world.isRemote) this.loadedTiles.add(new BlockVec3Dim(this));
    }
	
	@Override
    public void onLoad()
    {
        if (!this.world.isRemote) TileEntityPlanetShield.loadedTiles.add(new BlockVec3Dim(this));
    }

    @Override
    public void onChunkUnload()
    {
    	TileEntityPlanetShield.loadedTiles.remove(new BlockVec3Dim(this));
        super.onChunkUnload();
    }
    
    @Override
    public void invalidate()
    {
        if (!this.world.isRemote)
        {          
        	TileEntityPlanetShield.loadedTiles.remove(new BlockVec3Dim(this));
        }

        super.invalidate();
    }
    
    @Override
    public double getPacketRange()
    {
        return 64.0F;
    }
    
    @Override
    public void addExtraNetworkedData(List<Object> networkedList)
    {
        if (!this.world.isRemote && !this.isInvalid())
        {
//            networkedList.add(this.oxygenBubble != null);
//            if (this.oxygenBubble != null)
//            {
//                networkedList.add(this.oxygenBubble.getEntityId());
//            }
            if (this.world.getMinecraftServer().isDedicatedServer())
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
            //Signal integrated server, do not clear loadedTiles
            {
                networkedList.add(-1);
            }
            networkedList.add(this.bubbleSize);
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        return new AxisAlignedBB(this.getPos().getX() - this.bubbleSize, this.getPos().getY() - this.bubbleSize, this.getPos().getZ() - this.bubbleSize, this.getPos().getX() + this.bubbleSize, this.getPos().getY() + this.bubbleSize, this.getPos().getZ() + this.bubbleSize);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared()
    {
        return Constants.RENDERDISTANCE_LONG;
    }
    
    @Override
    public void readExtraNetworkedData(ByteBuf dataStream)
    {
        if (this.world.isRemote)
        {
//            if (dataStream.readBoolean())
//            {
//                this.oxygenBubble = (EntityBubble) worldObj.getEntityByID(dataStream.readInt());
//            }
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
                    if (i1 == -1 && i2 == -1 && i3 == -1 && i4 == -1)
                    {
                        continue;
                    }
                    loadedTiles.add(new BlockVec3Dim(i1, i2, i3, i4));
                }
            }
            this.bubbleSize = dataStream.readFloat();
        }
    }

    public int getDistanceFromServer(int par1, int par3, int par5)
    {
        final int d3 = this.getPos().getX() - par1;
        final int d4 = this.getPos().getY() - par3;
        final int d5 = this.getPos().getZ() - par5;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }
    
    @Override
    public void update()
    {
    	super.update();
    	
    	if (!this.world.isRemote)
        {
            if (this.getEnergyStoredGC() > 0.0F && this.hasEnoughEnergyToRun && !this.disabled)
            {
                this.bubbleSize = 30F;
            }
            else
            {
                this.bubbleSize = 0F;
            }

            this.bubbleSize = Math.min(Math.max(this.bubbleSize, 0.0F), 30.0F);
            
            BlockPos pos1 = new BlockPos(pos.getX() - bubbleSize, pos.getY(), pos.getZ() - bubbleSize);
            BlockPos pos2 = new BlockPos(pos.getX() + bubbleSize, pos.getY() + bubbleSize, pos.getZ() + bubbleSize);
            for(Entity entities : world.getEntitiesWithinAABB(EntityMeteor.class, new AxisAlignedBB(pos1, pos2)))
            {
            	
				EntityMeteor meteor = (EntityMeteor) entities;
				//GalaxySpace.debug("123");
				if (this.inBubble(meteor.lastTickPosX, meteor.lastTickPosY, meteor.lastTickPosZ)) {
					//this.shoot(meteor);
					this.world.createExplosion(meteor, meteor.lastTickPosX, meteor.lastTickPosY, meteor.lastTickPosZ, 5, true);
					if(world.rand.nextInt(3) == 0) 
					{
						this.world.spawnEntity(new EntityItem(world, meteor.lastTickPosX, meteor.lastTickPosY, meteor.lastTickPosZ, new ItemStack(GSItems.BASIC, 1 + world.rand.nextInt(3), 13)));
					}
					meteor.setDead();
					this.storage.extractEnergyGC(500F, false);
				}
            	
            }
        }
    }
    
    public void shoot(EntityMeteor meteor) {

		//this.energy -= this.ONE_SHOT;
		double spawnX = this.pos.getX() + 0.5;
		double spawnY = this.pos.getY() + 1.5;
		double spawnZ = this.pos.getZ() + 0.5;

		EntityIceSpike arrow = new EntityIceSpike(this.world, spawnX, spawnY, spawnZ);
		
		double X = meteor.posX - spawnX;
		double Y = meteor.posY + 0.5 - spawnY;
		double Z = meteor.posZ - spawnZ;
		double length = 0.1; //Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2) + Math.pow(Z, 2));
		
		arrow.motionX = X / length;
		arrow.motionY = Y / length;
		arrow.motionZ = Z / length;
		this.world.spawnEntity(arrow);
	}
    
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        if (nbt.hasKey("bubbleVisible"))
        {
            this.setBubbleVisible(nbt.getBoolean("bubbleVisible"));
        }

        if (nbt.hasKey("bubbleSize"))
        {
            this.bubbleSize = nbt.getFloat("bubbleSize");
        }
//        this.hasValidBubble = nbt.getBoolean("hasValidBubble");

        this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, this.stacks);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        nbt.setBoolean("bubbleVisible", this.shouldRenderBubble);
        nbt.setFloat("bubbleSize", this.bubbleSize);
//        nbt.setBoolean("hasValidBubble", this.hasValidBubble);
        ItemStackHelper.saveAllItems(nbt, this.stacks);
        return nbt;
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
    public ItemStack decrStackSize(int index, int count)
    {
        ItemStack itemstack = ItemStackHelper.getAndSplit(this.stacks, index, count);

        if (!itemstack.isEmpty())
        {
            this.markDirty();
        }

        return itemstack;
    }
    
    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        ItemStack oldstack = ItemStackHelper.getAndRemove(this.stacks, index);
        if (!oldstack.isEmpty())
        {
        	this.markDirty();
        }
    	return oldstack;
    }
    
    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        this.stacks.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

        this.markDirty();
    }

    @Override
    public boolean isEmpty()
    {
        for (ItemStack itemstack : this.stacks)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public String getName()
    {
        return GCCoreUtil.translate("tile.planet_shield.name");
    }
    
    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }
    
    @Override
    public boolean isUsableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.world.getTileEntity(this.getPos()) == this && par1EntityPlayer.getDistanceSq(this.getPos().getX() + 0.5D, this.getPos().getY() + 0.5D, this.getPos().getZ() + 0.5D) <= 64.0D;
    }
    
    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        return new int[] { 0, 1 };
    }
    
    @Override
    public boolean canInsertItem(int slotID, ItemStack itemstack, EnumFacing side)
    {
        if (this.isItemValidForSlot(slotID, itemstack))
        {
            switch (slotID)
            {
            case 0:
                return ItemElectricBase.isElectricItemCharged(itemstack);           
            default:
                return false;
            }
        }
        return false;
    }
    
    @Override
    public boolean canExtractItem(int slotID, ItemStack itemstack, EnumFacing side)
    {
        switch (slotID)
        {
        case 0:
            return ItemElectricBase.isElectricItemEmpty(itemstack);       
        default:
            return false;
        }
    }
    
    @Override
    public boolean hasCustomName()
    {
        return true;
    }
    
    @Override
    public boolean isItemValidForSlot(int slotID, ItemStack itemstack)
    {
        if (itemstack.isEmpty())
        {
            return false;
        }
        if (slotID == 0)
        {
            return ItemElectricBase.isElectricItem(itemstack.getItem());
        }       
        return false;
    }

    @Override
    public boolean shouldUseEnergy()
    {
        return this.hasEnoughEnergyToRun;
    }
    
    @Override
    public EnumFacing getFront()
    {
        return EnumFacing.DOWN;
    }
    
    @Override
    public EnumFacing getElectricInputDirection()
    {
        return getFront();
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
        double d3 = this.getPos().getX() + 0.5D - pX;
        d3 *= d3;
        if (d3 > r)
        {
            return false;
        }
        double d4 = this.getPos().getZ() + 0.5D - pZ;
        d4 *= d4;
        if (d3 + d4 > r)
        {
            return false;
        }
        double d5 = this.getPos().getY() + 0.5D - pY;
        return d3 + d4 + d5 * d5 < r;
    }
    
    @Override
    public void setBubbleVisible(boolean shouldRender)
    {
        this.shouldRenderBubble = shouldRender;
    }
    
    @Override
    public float getBubbleSize()
    {
        return this.bubbleSize;
    }
    
    @Override
    public boolean getBubbleVisible()
    {
        return this.shouldRenderBubble;
    }

    @Override
    public Vector3 getColor()
    {
        return new Vector3(0.0F, 1.0F, 1.0F);
    }
    
    @Override
	protected NonNullList<ItemStack> getContainingItems() {
		return this.stacks;
	}
    
}
