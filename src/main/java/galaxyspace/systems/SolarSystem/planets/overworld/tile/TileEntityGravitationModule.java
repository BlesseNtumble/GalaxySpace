package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.HashSet;
import java.util.List;

import galaxyspace.api.BodiesHelper;
import galaxyspace.api.tile.ITileEffects;
import micdoodle8.mods.galacticraft.api.entity.IAntiGrav;
import micdoodle8.mods.galacticraft.api.item.IArmorGravity;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3Dim;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.api.world.IZeroGDimension;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityGravitationModule extends TileBaseElectricBlockWithInventory implements ITileEffects /*ISidedInventory, IPacketReceiver*/
{
    private ItemStack[] containingItems = new ItemStack[2];

    public static HashSet<BlockVec3Dim> loadedTiles = new HashSet();
    
    public boolean shouldRenderEffects = false;
    
    private boolean initialised = false;
    public static boolean check = false;
    
    protected int radius;
    
    private AxisAlignedBB aabb;

    public TileEntityGravitationModule()
    {
       	this.radius = 4;    
       	
       	this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 90 : 45);              
    }


	@Override
    public void validate()
    {
    	super.validate();
        if (!this.worldObj.isRemote) this.loadedTiles.add(new BlockVec3Dim(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId));
    }
	
	@Override
    public void invalidate()
    {
        if (!this.worldObj.isRemote)
        {
            this.loadedTiles.remove(new BlockVec3Dim(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId));
        }

        super.invalidate();
    }
	
	@Override
    public void onChunkUnload()
    {
        this.loadedTiles.remove(new BlockVec3Dim(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId));
    	super.onChunkUnload();
    }
	
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound data = new NBTTagCompound();
        this.writeToNBT(data);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 2, data);
    }
    
    @Override
    public void onDataPacket(NetworkManager netManager, S35PacketUpdateTileEntity packet)
    {
        readFromNBT(packet.func_148857_g());
    }
    
    @Override
    public void updateEntity()
    {      	   	      			
    	 super.updateEntity();
    	
		if (this.canProcess()) {
			smeltItem();
			// this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 5, 3);
		} else {
			// if(this.blockMetadata > 4) this.worldObj.setBlockMetadataWithNotify(xCoord,
			// yCoord, zCoord, 0, 3);
		}
    }          
    

    /**
     * @return Is this machine able to process its specific task?
     */
    public boolean canProcess()
    {
    	return !this.getDisabled(0) && this.hasEnoughEnergyToRun;
    }

    /**
     * Turn one item from the furnace source stack into the appropriate smelted
     * item in the furnace result stack
     */
    public void smeltItem()
    {
    	aabb = AxisAlignedBB.getBoundingBox(xCoord - getGravityRadius(), 
				yCoord - 4, 
				zCoord - getGravityRadius(), 
				
				xCoord + getGravityRadius(), 
				yCoord + 16, 
				zCoord + getGravityRadius());
    	
    	if(this.worldObj.provider instanceof IGalacticraftWorldProvider)
    	{
    		final double g;
    	 	if(this.worldObj.provider instanceof IZeroGDimension) 
    	 		g = 0.2D;
    	 	else 
    	 		g = (1.0 - ((IGalacticraftWorldProvider)worldObj.provider).getGravity()) / 0.08F;
       		
       		final List<Entity> list = worldObj.getEntitiesWithinAABB(Entity.class, aabb);
       			
	       	for(Entity e: list)
	       	{
	       		if(!worldObj.isRemote) {
	       			if(e instanceof IAntiGrav) continue;
	       				       			
	       			Entity entity = (Entity)e;
	       			
	       			//if(!(entity instanceof EntityPlayer)) {
	       				//entity.addVelocity(0.0D, - (g / 200), 0.0D);
	                    // do something with the fall distance
	               // }
	       		 
	       			entity.fallDistance -= g * 10.0F;
	       			if(entity.fallDistance < 0) {
						entity.fallDistance = 0.0F;
	                }	
	       		}
	       		else
				{	       			
	       			if(e instanceof EntityPlayer) {
	       				EntityPlayer p = (EntityPlayer)e;
	       				
	       				if (p.capabilities.isFlying)
							continue;
	       				
	       				//GalaxySpace.debug("" + g);
	       				if (p.inventory.armorItemInSlot(0) != null
								&& p.inventory.armorItemInSlot(0).getItem() instanceof IArmorGravity
								&& ((IArmorGravity) p.inventory.armorItemInSlot(0).getItem())
										.gravityOverrideIfLow(p) > 0)
							continue;
	       				p.motionY -= (((IGalacticraftWorldProvider)worldObj.provider).getGravity() > BodiesHelper.calculateGravity(8.8F) ? g / 200 : g / 500);		
	       			}
				}
       		}
            check = true;
            if(this.shouldRenderEffects && worldObj.isRemote)
            {
	            for(int yy = -4; yy < 16; yy++)   
	            {
	            	for(int ix = -getGravityRadius(); ix <= getGravityRadius() + 1; ix++) {
	            		if(ix == -getGravityRadius() || ix == getGravityRadius() + 1 || yy == 15 || yy == -4) {
	            			worldObj.spawnParticle("magicCrit", this.xCoord + ix + this.worldObj.rand.nextFloat() - 0.5F, this.yCoord + yy + this.worldObj.rand.nextFloat() - 0.5F, this.zCoord - getGravityRadius() + this.worldObj.rand.nextFloat() - 0.5F, 0.0D, 0.0D, 0.0D); 
	            			worldObj.spawnParticle("magicCrit", this.xCoord + ix + this.worldObj.rand.nextFloat() - 0.5F, this.yCoord + yy + this.worldObj.rand.nextFloat() - 0.5F, this.zCoord + getGravityRadius() + 1 + this.worldObj.rand.nextFloat() - 0.5F, 0.0D, 0.0D, 0.0D); 
	            		} 
	            	}
	       				//-// 
	       			for(int iz = -getGravityRadius(); iz <= getGravityRadius() + 1; iz++) { 
	       				if(iz == -getGravityRadius() || iz == getGravityRadius() + 1 || yy == 15 || yy == -4) {
	       					worldObj.spawnParticle("magicCrit", this.xCoord - getGravityRadius() + this.worldObj.rand.nextFloat() - 0.5F, this.yCoord + yy + this.worldObj.rand.nextFloat() - 0.5F, this.zCoord + iz + this.worldObj.rand.nextFloat() - 0.5F, 0.0D, 0.0D, 0.0D); 
	       					worldObj.spawnParticle("magicCrit", this.xCoord + getGravityRadius() + 1 + this.worldObj.rand.nextFloat() - 0.5F, this.yCoord + yy + this.worldObj.rand.nextFloat() - 0.5F, this.zCoord + iz + this.worldObj.rand.nextFloat() - 0.5F, 0.0D, 0.0D, 0.0D); 
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
       
        this.containingItems = this.readStandardItemsFromNBT(par1NBTTagCompound);
        
        if(par1NBTTagCompound.hasKey("gravityradius")) {
            int grav = par1NBTTagCompound.getInteger("gravityradius");
            this.setGravityRadius(grav == 0 ? 1 : grav);
        } 
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {    
    	super.writeToNBT(par1NBTTagCompound);

        this.writeStandardItemsToNBT(par1NBTTagCompound);

        par1NBTTagCompound.setInteger("gravityradius", radius > 16 ? 16 : radius);
    }

    @Override
    protected ItemStack[] getContainingItems()
    {
        return this.containingItems;
    }

    @Override
    public String getInventoryName()
    {
        return GCCoreUtil.translate("tile.GravitationModule_4.name");
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return true;
    }

    /*
     * Returns true if automation is allowed to insert the given stack (ignoring
     * stack size) into the given slot.
     */
    @Override
    public boolean isItemValidForSlot(int slotID, ItemStack itemStack)
    {
    	return true;
    }
/*
    @Override
    public int[] getAccessibleSlotsFromSide(int side)
    {
        return new int[] { 0, 1, 2, 3, 4 };
    }

    @Override
    public boolean canInsertItem(int slotID, ItemStack par2ItemStack, int par3)
    {
        return this.isItemValidForSlot(slotID, par2ItemStack);
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack par2ItemStack, int par3)
    {
        return slotID == 5;
    }*/

    @Override
    public boolean shouldUseEnergy()
    {
        return !this.getDisabled(0);
    }
    
    @Override
    public ForgeDirection getElectricInputDirection()
    {
        return ForgeDirection.getOrientation((this.getBlockMetadata() & 3) + 0);
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
 
	public boolean inGravityZone(World world, EntityPlayer player)
	{
		if(player.posX > xCoord - getGravityRadius() &&
				player.posY > yCoord - 4 &&
				player.posZ > zCoord - getGravityRadius() &&
				
				player.posX < xCoord + getGravityRadius() &&
				player.posY < yCoord + 16 &&
				player.posZ < zCoord + getGravityRadius())
			return true;
		
		return false;
	}

}