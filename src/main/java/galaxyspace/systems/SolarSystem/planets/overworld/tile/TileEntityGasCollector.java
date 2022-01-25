package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import galaxyspace.GalaxySpace;
import galaxyspace.api.tile.ITileEffects;
import galaxyspace.core.GSItems;
import galaxyspace.core.prefab.tile.TileEntityUpgradeMachine;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockGasCollector;
import micdoodle8.mods.galacticraft.api.transmission.NetworkType;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.network.IPacketReceiver;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.wrappers.FluidHandlerWrapper;
import micdoodle8.mods.galacticraft.core.wrappers.IFluidHandlerWrapper;
import micdoodle8.mods.galacticraft.planets.GalacticraftPlanets;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
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

public class TileEntityGasCollector extends TileEntityUpgradeMachine implements ITileEffects, ISidedInventory, IFluidHandlerWrapper, IPacketReceiver{

	private int PROCESS_TIME_REQUIRED = 30;
	
	@NetworkedField(targetSide = Side.CLIENT)
    public int processTimeRequired = PROCESS_TIME_REQUIRED;

	private final int tankCapacity = 3000;
    @NetworkedField(targetSide = Side.CLIENT)
    public FluidTank gasTank = new FluidTank(this.tankCapacity);
    
	private boolean visible = false;
	
	private int radius = 2;
	
    private int drainX = -radius, drainY = 0, drainZ = -radius;
    
    
	public TileEntityGasCollector() {
		super("tile.gas_collector.name");
		this.storage.setCapacity(20000);
		this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 60 : 45);
		this.inventory = NonNullList.withSize(3 + 4, ItemStack.EMPTY);
		this.setTierGC(1);
	}
	
	@Override
    public void update()
    {
		
        super.update();
        
        radius = 2;
		if(upgradeSlots() != null) {
        	for(int i = 0; i <= upgradeSlots().length - 1; i++)
        	{
        		if(this.getInventory().get(upgradeSlots()[i]).isItemEqual(new ItemStack(GSItems.UPGRADES, 1, 0)))
        			radius += 2;
        	}
		}
		
        if (!this.world.isRemote)
        {       	
        	GSUtils.checkFluidTankTransfer(this.getInventory(), 1, this.gasTank);
        	
        	
    		
        	if(canProcess() && hasEnoughEnergyToRun) {
        		
        		
				if (this.processTicks >= this.processTimeRequired) {
					this.smeltItem();
					this.processTicks = 0;
				}
               
            }
            else
            {
                this.processTicks = 0;            
        	}        	
        }
        
        if(world.isRemote)
		{
			if(this.visible) {
				int xMin = -radius, xMax = radius+1;
				int zMin = -radius, zMax = radius+1;
				
				for(int x = xMin; x <= xMax; x++)
					for(int z = zMin; z <= zMax; z++)
						for(int y = 0; y <= radius+1; y++)
						{
							if(y == 0 || y == radius+1) {
								if(x == xMin || x == xMax|| z == zMin || z == zMax)
									world.spawnParticle(EnumParticleTypes.REDSTONE, this.getPos().getX() + x, this.getPos().getY() + y, this.getPos().getZ() + z, 0.0D, 0.0D, 0.0D);
							}
							else
								if((x == xMin || x == xMax) && (z == zMin || z == zMax))
									world.spawnParticle(EnumParticleTypes.REDSTONE, this.getPos().getX() + x, this.getPos().getY() + y, this.getPos().getZ() + z, 0.0D, 0.0D, 0.0D);
							
						}
			}
			
			if(canProcess() && this.ticks % 10 == 0 && hasEnoughEnergyToRun) {
	        
	              for (int particleCount = 0; particleCount < 50; particleCount++)
	              {
	                    double x2 = pos.getX() + this.world.rand.nextFloat();
	                    double y2 = pos.getY() + this.world.rand.nextFloat() + 1;
	                    double z2 = pos.getZ() + this.world.rand.nextFloat();
	                    double mX = 0.0D;
	                    double mY = 0.0D;
	                    double mZ = 0.0D;
	                    int dir = this.world.rand.nextInt(2) * 2 - 1;
	                    mX = (this.world.rand.nextFloat() - 0.5D) * 0.5D;
	                    mY = (this.world.rand.nextFloat() - 0.5D) * 0.5D;
	                    mZ = (this.world.rand.nextFloat() - 0.5D) * 0.5D;

	                    
	                    z2 = pos.getZ() + 0.5D + 0.25D * dir;
	                    mZ = this.world.rand.nextFloat() * 2.0F * dir;
	                    

	                    GalacticraftCore.proxy.spawnParticle("oxygen", new Vector3(x2, y2, z2), new Vector3(mX, mY, mZ), new Object[] { new Vector3(0.8D, 0.7D, 0.6D) });
	               }
	              
					
			}
		}
    }

	@Override
	public boolean canProcess()
    {    	
    	if(this.gasTank.getFluidAmount() >= this.gasTank.getCapacity())
    		return false;
    	
    	if(this.getStackInSlot(2).isEmpty()) return false;
    	
    	return true;
    }
	
	public void smeltItem()
    {
		BlockPos pos = this.getPos().add(drainX, drainY, drainZ);
    	IBlockState state = this.world.getBlockState(pos);
    	Block block = state.getBlock();
    	int meta = state.getBlock().getMetaFromState(state);
    	
    	if(meta == 0) { 
    		if(block instanceof BlockFluidClassic)
    		{
    			Fluid fluid = ((BlockFluidClassic)block).getFluid();
    			if(!fluid.isGaseous()) return;
    			this.gasTank.fill(new FluidStack(fluid, Fluid.BUCKET_VOLUME), true);
    			if(this.gasTank.getFluid().isFluidEqual(new FluidStack(FluidRegistry.lookupFluidForBlock(block), 0))) this.world.setBlockToAir(pos);
			    return;
    		}
		    
    		else if(block instanceof IFluidBlock)
		    {       
		    	IFluidBlock fluid = (IFluidBlock)block;
		    	if(!fluid.getFluid().isGaseous()) return;
		    	this.gasTank.fill(new FluidStack(fluid.getFluid(), Fluid.BUCKET_VOLUME), true);
		    	if(this.gasTank.getFluid().isFluidEqual(new FluidStack(FluidRegistry.lookupFluidForBlock(block), 0))) this.world.setBlockToAir(pos);
			    return;
		    } 
    	}
    	
    	
    	
    	if(drainX++ == radius) {
    		drainZ++;
    		drainX = -radius;
    	}
    	
    	if(drainZ == radius+1) {
    		drainY++;
    		drainZ = -radius;
    	}
    	
    	if(drainY == radius+1) {
    		drainY = 0;
    		drainX = -radius;
    		drainZ = -radius;
    	}   	
    	//System.out.println(drainX + " | " + drainY + " | " + drainZ);
    }

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);

		this.processTicks = par1NBTTagCompound.getInteger("smeltingTicks");
		ItemStackHelper.loadAllItems(par1NBTTagCompound, this.getInventory());

		this.radius = par1NBTTagCompound.getInteger("radius");
		
		if (par1NBTTagCompound.hasKey("gasTank"))
			this.gasTank.readFromNBT(par1NBTTagCompound.getCompoundTag("gasTank"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("smeltingTicks", this.processTicks);
		par1NBTTagCompound.setInteger("radius", this.radius);
		
		ItemStackHelper.saveAllItems(par1NBTTagCompound, this.getInventory());

		if (this.gasTank.getFluid() != null) {
			par1NBTTagCompound.setTag("gasTank", this.gasTank.writeToNBT(new NBTTagCompound()));
		}

		return par1NBTTagCompound;
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
        case 2:
            return true;
        }

        return false;
    }

	@Override
	public int[] upgradeSlots() {
		return new int[] {3, 4, 5, 6};
	}
	

	@Override
	public EnumFacing getElectricInputDirection() {
		return EnumFacing.DOWN;
	}

	@Override
	public EnumFacing getFront() {
		IBlockState state = this.world.getBlockState(getPos());
		if (state.getBlock() instanceof BlockGasCollector) {
			return state.getValue(BlockGasCollector.FACING);
		}
		return EnumFacing.NORTH;
	}

	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
		return 0;
	}
	
	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {

		if (from == getPipe())
        {
			if (resource != null && resource.isFluidEqual(this.gasTank.getFluid()))
                return this.gasTank.drain(resource.amount, doDrain);
        }
		return null;
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {

		if (from == getPipe())
        {
			return this.gasTank.drain(maxDrain, doDrain);
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
			return this.gasTank.getFluid() != null && this.gasTank.getFluidAmount() > 0;
        }
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from) {

		return new FluidTankInfo[] { new FluidTankInfo(this.gasTank) };
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
         return this.gasTank.getFluidAmount() * i / this.gasTank.getCapacity();
    }

	@Override
	public void setEffectsVisible(boolean shouldRender) {	
		this.visible = shouldRender;
	}

	@Override
	public boolean getEffectsVisible() {
		return this.visible;
	}
}
