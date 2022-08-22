package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import javax.annotation.Nullable;

import cofh.redstoneflux.api.IEnergyContainerItem;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import mekanism.api.energy.IEnergizedItem;
import micdoodle8.mods.galacticraft.api.item.IItemElectricBase;
import micdoodle8.mods.galacticraft.api.transmission.NetworkType;
import micdoodle8.mods.galacticraft.core.energy.EnergyConfigHandler;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;

public class TileEntityEnergyPad extends TileBaseElectricBlockWithInventory {
	
	public static int PROCESS_TIME_REQUIRED = 130;

	@NetworkedField(targetSide = Side.CLIENT)
	public int processTimeRequired = 1;

	@NetworkedField(targetSide = Side.CLIENT)
	public static int processTicks = 0;

	@NetworkedField(targetSide = Side.CLIENT)
	public boolean isCollide;
	
	public TileEntityEnergyPad() {
	
		super("tile.energy_pad.name");

		this.storage.setCapacity(15000);
		this.storage.setMaxExtract(0F);
		this.storage.setMaxReceive(500F);
		this.inventory = NonNullList.withSize(1, ItemStack.EMPTY);
    }

	@Override
	public void update() {
		super.update();
		
		//Freezing
		if (this.world.rand.nextInt(4) == 0) 
			this.world.notifyLightSet(this.getPos());
		
		if (this.canProcess() && !this.disabled) {
			if (this.hasEnoughEnergyToRun) {
				//this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 90  : 75);
				//if (this.world.rand.nextInt(4) == 0) 
					//this.isCollide = false;
				
			}
			else this.isCollide = false;
				
		}
	}
	
	/* Disable discharge machine */
	@Override
	public void slowDischarge()
    {       
    }
	
	public static boolean canProcess()
    {
    	return true;
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
	
	public void smeltItem(EntityPlayer player) {	
		if(this.hasEnoughEnergyToRun && this.storage.getEnergyStoredGC() > 200D)
		{
			
			for(ItemStack stack : player.inventory.armorInventory) 		
				if(chargeItem(stack, 350D)) {
					this.storage.extractEnergyGCnoMax(200F, false);
					break;		
				}
			for(ItemStack stack : player.inventory.mainInventory)		
				if(chargeItem(stack, 350D)) {
					this.storage.extractEnergyGCnoMax(200F, false);
					break;
				}
			
		}
		isCollide = isCollideTile(player);	
	}
	
	private boolean isCollideTile(@Nullable EntityPlayer player)
	{		
		if(player != null && this.hasEnoughEnergyToRun)
		{
			//double dis = getPos().distanceSqToCenter(player.posX, player.posY, player.posZ);
			if(player.getPosition().equals(getPos()))			
			{
				return true;
			}
		}
		return false;
	}
		
	private boolean chargeItem(ItemStack stack, double count)
	{
		if(stack.getItem() instanceof IItemElectricBase)
		{
			IItemElectricBase item = (IItemElectricBase)stack.getItem();
			if(item.getElectricityStored(stack) < item.getMaxElectricityStored(stack)) {
				item.recharge(stack, (float)count, true);
				return true;
			}
		}
		
		if(EnergyConfigHandler.isIndustrialCraft2Loaded())			
			if(stack.getItem() instanceof IElectricItem)	
				if(ElectricItem.manager.getCharge(stack) < ElectricItem.manager.getMaxCharge(stack)) {
					ElectricItem.manager.charge(stack, count, 4, false, false);		
					return true;
				}
		
		if(EnergyConfigHandler.isMekanismLoaded())
			if(stack.getItem() instanceof IEnergizedItem)
			{
				IEnergizedItem item = (IEnergizedItem)stack.getItem();
				if(item.getEnergy(stack) < item.getMaxEnergy(stack)) {
					item.setEnergy(stack, item.getEnergy(stack) + count);
					return true;
				}
			}
		
		if(EnergyConfigHandler.isRFAPILoaded())
			if(stack.getItem() instanceof IEnergyContainerItem)
			{
				IEnergyContainerItem item = (IEnergyContainerItem)stack.getItem();
				if(item.getEnergyStored(stack) < item.getMaxEnergyStored(stack)) {
					item.extractEnergy(stack, (int)count, false);
					return true;
				}
			}
		
		return false;
	}
	
	@Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        this.processTicks = par1NBTTagCompound.getInteger("smeltingTicks");
        this.isCollide = par1NBTTagCompound.getBoolean("isCollide");
        
        ItemStackHelper.loadAllItems(par1NBTTagCompound, this.getInventory());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
    {       
    	super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("smeltingTicks", this.processTicks);    
        par1NBTTagCompound.setBoolean("isCollide", this.isCollide);
        ItemStackHelper.saveAllItems(par1NBTTagCompound, this.getInventory());
        
		return par1NBTTagCompound;
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
	
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return null;
	}
}
