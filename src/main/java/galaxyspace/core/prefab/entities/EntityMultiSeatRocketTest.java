package galaxyspace.core.prefab.entities;

import java.util.List;

import galaxyspace.core.GSItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityMultiSeatRocketTest extends EntityMultiSeatRocket {

	public EntityMultiSeatRocketTest(World par1World) {
        super(par1World);
        this.setSize(2.0F, 10.0F);
    }

    public EntityMultiSeatRocketTest(World par1World, double par2, double par4, double par6, EnumRocketType rocketType) {
        super(par1World, par2, par4, par6);
        this.rocketType = rocketType;
        this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
    }

    public EntityMultiSeatRocketTest(World par1World, double par2, double par4, double par6, boolean reversed, EnumRocketType rocketType, NonNullList<ItemStack> inv) {
        this(par1World, par2, par4, par6, rocketType);
        this.stacks = inv;
    }

    @Override
    public double getYOffset() {
        return 1.5F;
    }
    
    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(GSItems.ROCKET_MULTI_SEAT, 1, this.rocketType.getIndex());
    }

    @Override
    public float getRotateOffset() {
        return 1.5F;
    }

    @Override
    public double getOnPadYOffset() {
        return 0.0D;
    }
    
    @Override
    public List<ItemStack> getItemsDropped(List<ItemStack> droppedItems) {
        super.getItemsDropped(droppedItems);
        ItemStack rocket = new ItemStack(GSItems.ROCKET_MULTI_SEAT, 1, this.rocketType.getIndex());
        rocket.setTagCompound(new NBTTagCompound());
        rocket.getTagCompound().setInteger("RocketFuel", this.fuelTank.getFluidAmount());
        droppedItems.add(rocket);
        return droppedItems;
    }

    @Override
    public float getRenderOffsetY() {
        return -1F;
    }
    
	@Override
	public int getMaxSeats() {
		return 2;
	}

	@Override
	public int getRocketTier() {
		return 4;
	}

	@Override
	public float getCameraZoom() {
		return 15.0F;
	}

	@Override
	public boolean defaultThirdPerson() {
		return true;
	}

	@Override
	public int getFuelTankCapacity() {
		return 1500;
	}

	@Override
	public int getPreLaunchWait() {
		return 400;
	}
	
	@Override
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
    }
}
