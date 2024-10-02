package galaxyspace.core.prefab.energy.item;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.api.item.ElectricItemHelper;
import micdoodle8.mods.galacticraft.core.energy.EnergyConfigHandler;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.items.ItemBatteryInfinite;
import micdoodle8.mods.miccore.Annotations.AltForVersion;
import micdoodle8.mods.miccore.Annotations.RuntimeInterface;
import micdoodle8.mods.miccore.Annotations.VersionSpecific;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;


public class ItemElectricGun extends ItemElectricBase 
{
	public float transferMax;
	private float maxPower;
	private int count = 0;
	
	public ItemElectricGun(String name, float maxPower)
    {
        super();
        this.setMaxDamage(100);
        this.setNoRepair();
        this.setMaxTransfer();
        this.setUnlocalizedName(name);
        this.maxPower = maxPower;
        this.setTextureName(GalaxySpace.ASSET_PREFIX + ":tools/" + name);
      
    }
	
/*
	@Override
    public void registerIcons(IIconRegister iconRegister) {

        for (int i = 0; i < this.icons.length; ++i) {
        	this.icons[i] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":tools/" + this.getUnlocalizedName() + "_" + i);
        }
    }

    @Override
    public IIcon getIconFromDamage(int par1)
    {
            return par1 == 100 ? this.icons[1] : this.icons[0];
    }
*/
	protected void setMaxTransfer()
    {
        this.transferMax = 200;
    }
	
	@Override
    public float getMaxElectricityStored(ItemStack itemStack)
    {
        return this.maxPower;
    }

	@Override
    public boolean hitEntity(ItemStack itemStack, EntityLivingBase living, EntityLivingBase holder)
    {
        if (this.getElectricityStored(itemStack) != 0.0F)
        {
            this.setElectricity(itemStack, this.getElectricityStored(itemStack) - 10.5F);

            return true;
        }
        return false;
    }
	/*
	@Override
	public boolean onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase entity)
    {
        if (block.getBlockHardness(world, x, y ,z) != 0.0D)
        {
            if (this.getElectricityStored(itemStack) != 0.0F)
            {
                this.setElectricity(itemStack, this.getElectricityStored(itemStack) - 10.0F);
            }
        }
        return true;
    }*/
	
    @Override
    public EnumAction getItemUseAction(ItemStack itemStack)
    {
        if (this.getElectricityStored(itemStack) != 0.0F)
        {
            return EnumAction.bow;
        }
        return EnumAction.bow;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack itemStack)
    {
        if (this.getElectricityStored(itemStack) != 0.0F)
        {
            return 72000;
        }
        return 0;
    }
   
    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entity, World world, int par1, int par2, int par3, int par4, float par5, float par6, float par7)
    {
		return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
    	if(!world.isRemote)
        {
    		if (this.getElectricityStored(itemStack) != 0.0F)
			{
    			/*if(itemStack.getItem() == GSItems.PlasmaGun)
    			{
    				float fire1 = 1.5F;
    				float fire2 = 100.5F;
    				if(Minecraft.getMinecraft().gameSettings.keyBindSneak.getIsKeyPressed())
    				{
    					count++;
    				}
    				if(count > 5 && Minecraft.getMinecraft().gameSettings.keyBindSneak.getIsKeyPressed() && this.getElectricityStored(itemStack) > fire2)
    				{
    					count = 0;
    			
    					world.spawnEntityInWorld(new EntityPlasmaBall(world, player));
    					player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
    					this.setElectricity(itemStack, this.getElectricityStored(itemStack) - fire2);
    					return itemStack;
    			
    				}
    				if(!Minecraft.getMinecraft().gameSettings.keyBindSneak.getIsKeyPressed() && this.getElectricityStored(itemStack) > fire1)
    				{
    					world.spawnEntityInWorld(new EntityPlasmaLaser(world, player));
    					
    					this.setElectricity(itemStack, this.getElectricityStored(itemStack) - fire1);
    				}
    			}*/
			}
    		
        }
    	return itemStack;
    }
    
    
    @Override
    public Multimap getAttributeModifiers(ItemStack itemStack)
    {
        if (this.getElectricityStored(itemStack) != 0.0F)
        {
            return super.getAttributeModifiers(itemStack);
        }
        return HashMultimap.create();
    }
    
    @Override
    public CreativeTabs getCreativeTab()
    {
        return GSCreativeTabs.GSArmorTab;
    }

    @Override
    public boolean getIsRepairable(ItemStack itemStack, ItemStack itemStack2)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
    {
    	//list.add(ElectricItemHelper.getUncharged(new ItemStack(this)));
        list.add(ElectricItemHelper.getWithCharge(new ItemStack(this), this.getMaxElectricityStored(new ItemStack(this))));
    }
    
    @RuntimeInterface(clazz = "mekanism.api.energy.IEnergizedItem", modID = "Mekanism")
    public double getEnergy(ItemStack itemStack)
    {
        return this.getElectricityStored(itemStack) * EnergyConfigHandler.TO_MEKANISM_RATIO;
    }

    @RuntimeInterface(clazz = "mekanism.api.energy.IEnergizedItem", modID = "Mekanism")
    public void setEnergy(ItemStack itemStack, double amount)
    {
        this.setElectricity(itemStack, (float) amount * EnergyConfigHandler.MEKANISM_RATIO);
    }

    @RuntimeInterface(clazz = "mekanism.api.energy.IEnergizedItem", modID = "Mekanism")
    public double getMaxEnergy(ItemStack itemStack)
    {
        return this.getMaxElectricityStored(itemStack) * EnergyConfigHandler.TO_MEKANISM_RATIO;
    }

    @RuntimeInterface(clazz = "mekanism.api.energy.IEnergizedItem", modID = "Mekanism")
    public double getMaxTransfer(ItemStack itemStack)
    {
        return this.transferMax * EnergyConfigHandler.TO_MEKANISM_RATIO;
    }

    @RuntimeInterface(clazz = "mekanism.api.energy.IEnergizedItem", modID = "Mekanism")
    public boolean canReceive(ItemStack itemStack)
    {
        return itemStack != null && !(itemStack.getItem() instanceof ItemBatteryInfinite);
    }

    /*@RuntimeInterface(clazz = "ic2.api.item.ISpecialElectricItem", modID = "IC2")
	public IElectricItemManager getManager(ItemStack itemstack)
	{
		return (IElectricItemManager)itemManagerIC2;
	}*/

    @RuntimeInterface(clazz = "ic2.api.item.ISpecialElectricItem", modID = "IC2")
    public boolean canProvideEnergy(ItemStack itemStack)
    {
        return true;
    }

    @RuntimeInterface(clazz = "ic2.api.item.ISpecialElectricItem", modID = "IC2")
    public Item getChargedItem(ItemStack itemStack)
    {
        return itemStack.getItem();
    }

    @RuntimeInterface(clazz = "ic2.api.item.ISpecialElectricItem", modID = "IC2")
    public Item getEmptyItem(ItemStack itemStack)
    {
        return itemStack.getItem();
    }

    @RuntimeInterface(clazz = "ic2.api.item.ISpecialElectricItem", modID = "IC2")
    public int getTier(ItemStack itemStack)
    {
        return 1;
    }

    @VersionSpecific(version = "[1.7.10]")
    @RuntimeInterface(clazz = "ic2.api.item.ISpecialElectricItem", modID = "IC2")
    public double getMaxCharge(ItemStack itemStack)
    {
        return this.getMaxElectricityStored(itemStack) / EnergyConfigHandler.IC2_RATIO;
    }

    @AltForVersion(version = "[1.7.2]")
    @RuntimeInterface(clazz = "ic2.api.item.ISpecialElectricItem", modID = "IC2")
    public int getMaxChargeB(ItemStack itemStack)
    {
        return (int) (this.getMaxElectricityStored(itemStack) / EnergyConfigHandler.IC2_RATIO);
    }

    @VersionSpecific(version = "[1.7.10]")
    @RuntimeInterface(clazz = "ic2.api.item.ISpecialElectricItem", modID = "IC2")
    public double getTransferLimit(ItemStack itemStack)
    {
        return this.transferMax * EnergyConfigHandler.TO_IC2_RATIO;
    }

    @VersionSpecific(version = "[1.7.2]")
    @RuntimeInterface(clazz = "ic2.api.item.ISpecialElectricItem", modID = "IC2")
    public int getTransferLimitB(ItemStack itemStack)
    {
        return (int) (this.transferMax * EnergyConfigHandler.TO_IC2_RATIO);
    }

	@Override
	public float recharge(ItemStack itemStack, float energy, boolean doRecharge) 
	{
        float rejectedElectricity = Math.max(this.getElectricityStored(itemStack) + energy - this.getMaxElectricityStored(itemStack), 0);
        float energyToReceive = energy - rejectedElectricity;

        if (energyToReceive > this.transferMax)
        {
            rejectedElectricity += energyToReceive - this.transferMax;
            energyToReceive = this.transferMax;
        }
        if (doRecharge)
        {
            this.setElectricity(itemStack, this.getElectricityStored(itemStack) + energyToReceive);
        }
        return energyToReceive;
    }

	@Override
	public float discharge(ItemStack itemStack, float energy, boolean doDischarge) 
	{
        float energyToTransfer = Math.min(Math.min(this.getElectricityStored(itemStack), energy), this.transferMax);

        if (doDischarge)
        {
            this.setElectricity(itemStack, this.getElectricityStored(itemStack) - energyToTransfer);
        }
        return energyToTransfer;
    }

	@Override
	public float getElectricityStored(ItemStack itemStack) 
	{
        if (itemStack.getTagCompound() == null)
        {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        float energyStored = 0f;
        if (itemStack.getTagCompound().hasKey("electricity"))
        {
            NBTBase obj = itemStack.getTagCompound().getTag("electricity");
            if (obj instanceof NBTTagDouble)
            {
                energyStored = ((NBTTagDouble) obj).func_150288_h();
            }
            else if (obj instanceof NBTTagFloat)
            {
                energyStored = ((NBTTagFloat) obj).func_150288_h();
            }
        }

        /** Sets the damage as a percentage to render the bar properly. */
        itemStack.setItemDamage((int) (100 - energyStored / this.getMaxElectricityStored(itemStack) * 100));
        return energyStored;
    }

	@Override
	public void setElectricity(ItemStack itemStack, float joules) 
	{
        if (itemStack.getTagCompound() == null)
        {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        float electricityStored = Math.max(Math.min(joules, this.getMaxElectricityStored(itemStack)), 0);
        itemStack.getTagCompound().setFloat("electricity", electricityStored);
        itemStack.setItemDamage((int) (100 - electricityStored / this.getMaxElectricityStored(itemStack) * 100));
    }

	@Override
	public float getTransfer(ItemStack itemStack) 
	{
        return Math.min(this.transferMax, this.getMaxElectricityStored(itemStack) - this.getElectricityStored(itemStack));
    }

	@Override
	public int getTierGC(ItemStack itemStack) 
	{

		return 1;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean advanced)
    {
        EnumChatFormatting color = null;
        float joules = this.getElectricityStored(itemStack);

        if (joules <= this.getMaxElectricityStored(itemStack) / 3)
        {
            color = EnumChatFormatting.DARK_RED;
        }
        else if (joules > this.getMaxElectricityStored(itemStack) * 2 / 3)
        {
            color = EnumChatFormatting.DARK_GREEN;
        }
        else
        {
            color = EnumChatFormatting.GOLD;
        }
        list.add(color + EnergyDisplayHelper.getEnergyDisplayS(joules) + "/" + EnergyDisplayHelper.getEnergyDisplayS(this.getMaxElectricityStored(itemStack)));
    }

	@Override
    public void onCreated(ItemStack itemStack, World world, EntityPlayer player)
    {
        this.setElectricity(itemStack, 0);
    }
}