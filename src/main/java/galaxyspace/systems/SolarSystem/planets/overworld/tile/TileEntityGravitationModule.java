package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Nullable;

import galaxyspace.api.tile.ITileEffects;
import micdoodle8.mods.galacticraft.api.entity.IAntiGrav;
import micdoodle8.mods.galacticraft.api.item.IArmorGravity;
import micdoodle8.mods.galacticraft.api.transmission.NetworkType;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3Dim;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.dimension.WorldProviderSpaceStation;
import micdoodle8.mods.galacticraft.core.energy.tile.EnergyStorageTile;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

public class TileEntityGravitationModule extends TileBaseElectricBlockWithInventory implements ITileEffects {
	
	public static int PROCESS_TIME_REQUIRED = 130;

	@NetworkedField(targetSide = Side.CLIENT)
	public int processTimeRequired = 1;

	@NetworkedField(targetSide = Side.CLIENT)
	public static int processTicks = 0;
	
	private NonNullList<ItemStack> stacks = NonNullList.withSize(2, ItemStack.EMPTY);
	public static HashSet<BlockVec3Dim> loadedTiles = new HashSet();
	
	private int radius;

	private AxisAlignedBB aabb;
	private boolean initialised = false;
	public static boolean check = false;
	
	public boolean shouldRenderEffects = false;

	public TileEntityGravitationModule() {
		this(1);
	}
	
	public TileEntityGravitationModule(int tier)
    {
        this.initialised = true;
        if (tier == 1)
        {
        	this.radius = 4;
        	this.storage.setCapacity(15000);
            this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 60 : 45);
            return;
        }
       
    }
	
	@Override
    public void validate()
    {
    	super.validate();
        if (!this.world.isRemote) this.loadedTiles.add(new BlockVec3Dim(this));
    }
	
	@Override
	public void onLoad() {
		if (!this.world.isRemote)
			this.loadedTiles.add(new BlockVec3Dim(this));
	}
	
	@Override
	public void onChunkUnload() {
		if (!this.world.isRemote)
			this.loadedTiles.remove(new BlockVec3Dim(this));
		
		super.onChunkUnload();
	}

	@Override
	public void invalidate() {
		if (!this.world.isRemote) {
			this.loadedTiles.remove(new BlockVec3Dim(this));
		}

		super.invalidate();
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
	public void update() {
		super.update();
		
		//Freezing
		if (this.world.rand.nextInt(4) == 0) 
			this.world.notifyLightSet(this.getPos());
		
		if (this.canProcess() && !this.disabled) {
			if (this.hasEnoughEnergyToRun) {				
				if (this.processTicks == 0) {
					this.processTicks = this.processTimeRequired;
				} else {
					if (--this.processTicks <= 0) {
						this.smeltItem();
						this.processTicks = this.processTimeRequired;
						// this.world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 5, 2);
					}
				}
			} else if (this.processTicks > 0 && this.processTicks < this.processTimeRequired) {
				// Apply a "cooling down" process if the electric furnace runs out of energy
				// while smelting
				if (this.world.rand.nextInt(4) == 0) {
					this.processTicks++;					
				}
				check = false;

			}
			/*
			 * else { if(this.getBlockMetadata() > 4)
			 * this.world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2); }
			 */
			// BlockGravitationModule.updateFurnaceBlockState(this.hasEnoughEnergyToRun,
			// this.worldObj, this.xCoord, this.yCoord, this.zCoord);

		} else {
			// if(this.getBlockMetadata() > 4) this.world.setBlockMetadataWithNotify(xCoord,
			// yCoord, zCoord, 0, 2);

			this.processTicks = 0;

		}

	}
	
	public static boolean canProcess()
    {
    	return true;
    }
	
	public void smeltItem() {		
		
		aabb = new AxisAlignedBB(this.getPos().getX() - getGravityRadius(), this.getPos().getY() - 4,
				this.getPos().getZ() - getGravityRadius(),

				this.getPos().getX() + getGravityRadius(), this.getPos().getY() + 16,
				this.getPos().getZ() + getGravityRadius());
		
		if (this.world.provider instanceof IGalacticraftWorldProvider) {
			final double g;
			if (this.world.provider instanceof WorldProviderSpaceStation)
				g = 0.80665D;
			else
				g = (1.0 - ((IGalacticraftWorldProvider) world.provider).getGravity()) / 0.08F;

			//if(getGravityRadius() > 14) g /= 2;
			final List list = world.getEntitiesWithinAABB(Entity.class, aabb);
			
			if(!world.isRemote) {				
				for (Object e : list) {
					
					if(e instanceof IAntiGrav) continue;
					//Iterator iterator = list.iterator();
					Entity entity = (Entity) e;
					/*if(!(entity instanceof EntityPlayer))
					{
						entity.addVelocity(0.0D, g / 1000, 0.0D);
					}*/
					entity.fallDistance -= g * 10.0F;
					
					if(entity.fallDistance < 0) {
						entity.fallDistance = 0.0F;
	                }					
				}			
			}
			else
			{
				for(Object e: list) {
					if(e instanceof EntityPlayer) {
						EntityPlayer p = (EntityPlayer)e;
						if (p.capabilities.isFlying)
							continue;
						
						if (!p.inventory.armorItemInSlot(0).isEmpty()
								&& p.inventory.armorItemInSlot(0).getItem() instanceof IArmorGravity
								&& ((IArmorGravity) p.inventory.armorItemInSlot(0).getItem())
										.gravityOverrideIfLow(p) > 0)
							continue;
					 	
					 	p.motionY -= (g / 200);					 	
					 }
				}
			}
			
			check = true;
			if (this.shouldRenderEffects && world.isRemote) {
				for (int yy = -4; yy < 16; yy++) {
					for (int ix = -getGravityRadius(); ix <= getGravityRadius() + 1; ix++) {
						if(ix == -getGravityRadius() || ix == getGravityRadius() + 1 || yy == 15 || yy == -4) {
						world.spawnParticle(EnumParticleTypes.CRIT_MAGIC, this.getPos().getX() + ix + this.world.rand.nextFloat() - 0.5F,
								this.getPos().getY() + yy + this.world.rand.nextFloat() - 0.5F,
								this.getPos().getZ() - getGravityRadius() + this.world.rand.nextFloat() - 0.5F, 0.0D, 0.0D,
								0.0D);
						world.spawnParticle(EnumParticleTypes.CRIT_MAGIC, this.getPos().getX() + ix + this.world.rand.nextFloat() - 0.5F,
								this.getPos().getY() + yy + this.world.rand.nextFloat() - 0.5F,
								this.getPos().getZ() + getGravityRadius() + 1 + this.world.rand.nextFloat() - 0.5F, 0.0D, 0.0D,
								0.0D);
						}
					}
					// -//
					for (int iz = -getGravityRadius(); iz <= getGravityRadius() + 1; iz++) {
						if(iz == -getGravityRadius() || iz == getGravityRadius() + 1 || yy == 15 || yy == -4) {
						world.spawnParticle(EnumParticleTypes.CRIT_MAGIC,
								this.getPos().getX() - getGravityRadius() + this.world.rand.nextFloat() - 0.5F,
								this.getPos().getY() + yy + this.world.rand.nextFloat() - 0.5F,
								this.getPos().getZ() + iz + this.world.rand.nextFloat() - 0.5F, 0.0D, 0.0D, 0.0D);
						world.spawnParticle(EnumParticleTypes.CRIT_MAGIC,
								this.getPos().getX() + getGravityRadius() + 1 + this.world.rand.nextFloat() - 0.5F,
								this.getPos().getY() + yy + this.world.rand.nextFloat() - 0.5F,
								this.getPos().getZ() + iz + this.world.rand.nextFloat() - 0.5F, 0.0D, 0.0D, 0.0D);
						}
					}
				}
			}
		}
	}
	
	@Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        if (this.storage.getEnergyStoredGC() > EnergyStorageTile.STANDARD_CAPACITY)
        {
        	this.initialised = true;
        }
        else
        	this.initialised = false;
        this.processTicks = par1NBTTagCompound.getInteger("smeltingTicks");
        this.stacks = this.readStandardItemsFromNBT(par1NBTTagCompound);
        
        if(par1NBTTagCompound.hasKey("gravityradius")) {
            int grav = par1NBTTagCompound.getInteger("gravityradius");
            this.setGravityRadius(grav == 0 ? 1 : grav);
        } 
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        if (this.tierGC == 1 && this.storage.getEnergyStoredGC() > EnergyStorageTile.STANDARD_CAPACITY)
        	this.storage.setEnergyStored(EnergyStorageTile.STANDARD_CAPACITY);
    	super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("smeltingTicks", this.processTicks);
        this.writeStandardItemsToNBT(par1NBTTagCompound, this.stacks);
      
        par1NBTTagCompound.setInteger("gravityradius", radius > 16 ? 16 : radius);
        
		return par1NBTTagCompound;
    }
    
    @Override
    protected NonNullList<ItemStack> getContainingItems()
    {
        return this.stacks;
    }
    
    @Override
    public String getName()
    {
        return GCCoreUtil.translate("tile.gravitation_module.name");
    }
    
    @Override
    public boolean isItemValidForSlot(int slotID, ItemStack itemStack)
    {
    	return true;
    }
    
    @Override
    public boolean shouldUseEnergy()
    {
        return this.canProcess();
    }
    
    @Override
	public void setEffectsVisible(boolean shouldRender) {
		 this.shouldRenderEffects = shouldRender;
	}

	@Override
	public boolean getEffectsVisible() {
		return this.shouldRenderEffects;
	}
	
	public void setGravityRadius(int radius)
	{
		this.radius = radius;
	}
	
	public int getGravityRadius()
	{
		return this.radius;
	}

	@Override
	public EnumFacing getFront() {
		return EnumFacing.DOWN;
	}
	
	@Override
	public EnumFacing getElectricInputDirection() {
		return EnumFacing.DOWN;
	}
	
	@Override
	public boolean canConnect(EnumFacing direction, NetworkType type) {
		if (direction == null) {
			return false;
		}
		if (type == NetworkType.POWER) {
			return direction == this.getElectricInputDirection();
		}
		return false;
	}
	
	public boolean inGravityZone(World world, EntityPlayer player)
	{
		if(player.posX > this.pos.getX() - getGravityRadius() &&
				player.posY > this.pos.getY() - 4 &&
				player.posZ > this.pos.getZ() - getGravityRadius() &&
				
				player.posX < this.pos.getX() + getGravityRadius() &&
				player.posY < this.pos.getY() + 16 &&
				player.posZ < this.pos.getZ() + getGravityRadius())
			return true;
		
		return false;
	}
}
