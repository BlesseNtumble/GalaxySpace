package galaxyspace.core.prefab.entities;

import galaxyspace.GalaxySpace;
import galaxyspace.core.network.packet.GSPacketSimple;
import galaxyspace.core.network.packet.GSPacketSimple.GSEnumSimplePacket;
import galaxyspace.core.prefab.inventory.InventoryAstroWolf;
import micdoodle8.mods.galacticraft.api.entity.IEntityBreathable;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.items.ItemOxygenTank;
import micdoodle8.mods.galacticraft.core.util.DamageSourceGC;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.OxygenUtil;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityAstroWolf extends EntityWolf implements IEntityBreathable {

	private static final DataParameter<Float> DATA_HEALTH_ID = EntityDataManager.<Float>createKey(EntityWolf.class, DataSerializers.FLOAT);
    
	public InventoryAstroWolf wolfInventory = new InventoryAstroWolf(this);
	private boolean isHelmet;
	
	public boolean needSync = true;
	
	public EntityAstroWolf(World worldIn) {
		super(worldIn);
		
	}
	
	@Override
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);

        if (this.isTamed())
        {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(35.0D);
        }
        else
        {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        }

    }
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		
		if(!world.isRemote)
		{
			if(this.world.provider instanceof IGalacticraftWorldProvider) {
				if(Math.abs(((IGalacticraftWorldProvider)this.world.provider).getThermalLevelModifier()) >= 1.0F)
					if(this.wolfInventory.getStackInSlot(1).isEmpty() && this.ticksExisted % 20 == 0)
						this.attackEntityFrom(DamageSourceGC.thermal, 0.2F);
			
			
				if(!OxygenUtil.isAABBInBreathableAirBlock(this) && this.ticksExisted % 40 == 0)
				{
					if(this.wolfInventory.getStackInSlot(0).getItem() == GCItems.oxMask)
						if(this.wolfInventory.getStackInSlot(2).getItem() instanceof ItemOxygenTank && this.wolfInventory.getStackInSlot(2).getItemDamage() < this.wolfInventory.getStackInSlot(2).getMaxDamage())
							this.wolfInventory.getStackInSlot(2).setItemDamage(this.wolfInventory.getStackInSlot(2).getItemDamage() + 1);
				}
			}
		}
		
		if(needSync) {
			if(FMLCommonHandler.instance().getEffectiveSide().equals(Side.CLIENT)) {
				GalaxySpace.packetPipeline.sendToServer(new GSPacketSimple(GSEnumSimplePacket.S_UPDATE_WOLF_INV, GCCoreUtil.getDimensionID(world), new Object[] { getEntityId() }));
			}
		}
	}
	
	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (this.isTamed())
        {
            if(player.isSneaking()) {
    			if (this.isOwner(player)) {
    				player.openGui(GalaxySpace.MODID, 1005, this.world, this.getEntityId(), 0, 0);
    			}
    			return true;
    		} 

            if (!itemstack.isEmpty())
            {
                if (itemstack.getItem() instanceof ItemFood)
                {
                    ItemFood itemfood = (ItemFood)itemstack.getItem();

                    if (itemfood.isWolfsFavoriteMeat() && this.getHealth() < 35.0F)
                    {
                        if (!player.capabilities.isCreativeMode)
                        {
                            itemstack.shrink(1);
                        }

                        this.heal((float)itemfood.getHealAmount(itemstack));
                        return true;
                    }
                }
            }
        }
        return super.processInteract(player, hand);
    }
	
	@SideOnly(Side.CLIENT)
	public void setWolfInventory(InventoryAstroWolf inv) {
		this.wolfInventory = inv;
	}
	
	@Override
	public boolean canBreath() {
		return !this.wolfInventory.getStackInSlot(0).isEmpty() && !this.wolfInventory.getStackInSlot(2).isEmpty() && this.wolfInventory.getStackInSlot(2).getItemDamage() < this.wolfInventory.getStackInSlot(2).getMaxDamage();
	}
		
	@Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
		super.writeEntityToNBT(nbt);
		ItemStackHelper.saveAllItems(nbt, this.wolfInventory.getInventory());
    }

	@Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        ItemStackHelper.loadAllItems(nbt, this.wolfInventory.getInventory());
    }
	
	@Override
	public void onDeath(DamageSource p_70645_1_) {
		super.onDeath(p_70645_1_);

		if (!this.world.isRemote) {
			this.entityDropItem(this.wolfInventory.getStackInSlot(0), 0.5F);
			this.entityDropItem(this.wolfInventory.getStackInSlot(1), 0.5F);
			this.entityDropItem(this.wolfInventory.getStackInSlot(2), 0.5F);
			
		}
	}
	
	@Override	
	public void setTamed(boolean tamed) {
		super.setTamed(tamed);

		if (tamed) {
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(35.0D);
		} else {
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
		}

		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
	}

}
