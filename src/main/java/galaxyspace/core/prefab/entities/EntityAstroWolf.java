package galaxyspace.core.prefab.entities;

import galaxyspace.GalaxySpace;
import galaxyspace.core.network.packet.GSPacketSimple;
import galaxyspace.core.network.packet.GSPacketSimple.GSEnumSimplePacket;
import galaxyspace.core.prefab.inventory.InventoryAstroWolf;
import micdoodle8.mods.galacticraft.api.entity.IEntityBreathable;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.items.ItemOxygenTank;
import micdoodle8.mods.galacticraft.core.util.DamageSourceGC;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.OxygenUtil;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityAstroWolf extends EntityWolf implements IEntityBreathable {

	public InventoryAstroWolf wolfInventory = new InventoryAstroWolf(this);
	private boolean isHelmet;
	
	public boolean needSync = true;
	
	public EntityAstroWolf(World worldIn) {
		super(worldIn);
	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		
		if(!world.isRemote)
		{
			if(this.world.provider instanceof IGalacticraftWorldProvider)
				if(Math.abs(((IGalacticraftWorldProvider)this.world.provider).getThermalLevelModifier()) >= 1.0F)
					if(this.wolfInventory.getStackInSlot(1).isEmpty() && this.ticksExisted % 20 == 0)
						this.attackEntityFrom(DamageSourceGC.thermal, 0.2F);
			
			if(!OxygenUtil.isAABBInBreathableAirBlock(this) && this.ticksExisted % 40 == 0)
			{
				if(this.wolfInventory.getStackInSlot(2).getItem() instanceof ItemOxygenTank && this.wolfInventory.getStackInSlot(2).getItemDamage() < this.wolfInventory.getStackInSlot(2).getMaxDamage())
					this.wolfInventory.getStackInSlot(2).setItemDamage(this.wolfInventory.getStackInSlot(2).getItemDamage() + 1);
			}
		}
		
		if(needSync) {
			if(FMLCommonHandler.instance().getEffectiveSide().equals(Side.CLIENT)) {
				GalaxySpace.packetPipeline.sendToServer(new GSPacketSimple(GSEnumSimplePacket.S_UPDATE_WOLF_INV, GCCoreUtil.getDimensionID(world), new Object[] { getEntityId() }));
			}
		}
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
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
		
		if(player.isSneaking()) {
			if (player == this.getOwner() && this.isTamed())	{
				player.openGui(GalaxySpace.MODID, 1005, this.world, this.getEntityId(), 0, 0);
			}
			return true;
		}
		else return super.processInteract(player, hand);
		
		
    }
	
	@Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
		super.writeEntityToNBT(nbt);
		ItemStackHelper.saveAllItems(nbt, this.wolfInventory.getInventory());
		//nbt.setTag("WolfInventory", this.wolfInventory.writeToNBT(new NBTTagList()));
    }

	@Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        ItemStackHelper.loadAllItems(nbt, this.wolfInventory.getInventory());
        //this.wolfInventory.readFromNBT(nbt.getTagList("WolfInventory", 10));
    }
	
	@Override
	public void onDeath(DamageSource p_70645_1_) {
		super.onDeath(p_70645_1_);

		if (!this.world.isRemote) {
			//this.wolfInventory.decrStackSize(1, 64);
			this.entityDropItem(this.wolfInventory.getStackInSlot(0), 0.5F);
			this.entityDropItem(this.wolfInventory.getStackInSlot(1), 0.5F);
			this.entityDropItem(this.wolfInventory.getStackInSlot(2), 0.5F);
			
		}
	}
}
