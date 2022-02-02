package galaxyspace.systems.SolarSystem.planets.overworld.items.armor;

import java.util.List;

import javax.annotation.Nullable;

import galaxyspace.api.item.IJetpackArmor;
import galaxyspace.core.prefab.items.ItemElectricArmor;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.systems.SolarSystem.planets.overworld.render.item.ItemSpaceSuitModel;
import micdoodle8.mods.galacticraft.api.item.IItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemJetpack extends ItemElectricArmor implements ISpecialArmor, IJetpackArmor{

	public ItemJetpack(ArmorMaterial materialIn, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, equipmentSlotIn);
		this.setTranslationKey("jetpack");	
	}
	
	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)    
    {
    	if (tab == GSCreativeTabs.GSArmorTab || tab == CreativeTabs.SEARCH)
        {
    		list.add(new ItemStack(this, 1, this.getMaxDamage()));	        
        }
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
		
		// ModelBiped model = new ModelJetPack();
		ModelBiped armormodel = new ItemSpaceSuitModel(6);
		if (itemStack.getItem() instanceof ItemJetpack) {
			armormodel = ItemSpaceSuit.fillingArmorModel(armormodel, entityLiving);
		}
		return armormodel;

	}
	
	@Override
	public float getMaxElectricityStored(ItemStack theItem) {
		return 100000;
	}
	
	@Override
	public void consumeFuel(ItemStack stack, int fuel) {
		this.discharge(stack, 5, true);
	}
	
	@Override
	public void decrementFuel(ItemStack stack) {	
		if(stack.getItem() instanceof IItemElectricBase)
			((IItemElectricBase)stack.getItem()).discharge(stack, 1, true);
	}
	
	@Override
	public int getFuel(ItemStack stack) {
		return stack.getTagCompound().getInteger(TAG_FUEL);
	}
	
	@Override
	public boolean canFly(ItemStack stack, EntityPlayer player) {
		
		if(stack.hasTagCompound())
    	{
    		if(stack.getItemDamage() < stack.getMaxDamage())
    		{
    			return true;
    		}
    	}   	
    	
		return false;
	}
	
	@Override
	public boolean isActivated(ItemStack stack) {
		return stack.getTagCompound().getBoolean(TAG_ACT);
	}
	
	@Override
	public void switchState(ItemStack stack, boolean state) {
		
		if(stack.hasTagCompound()) 
			stack.getTagCompound().setBoolean(TAG_ACT, state);
		else
		{
			stack.setTagCompound(new NBTTagCompound());
		}
	}
	
	@Override
	public int getFireStreams(ItemStack stack) {
		return 2;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flagIn)
    {		
		String color = "";
		float joules = this.getElectricityStored(stack);

		if (joules <= this.getMaxElectricityStored(stack) / 3) {
			color = "\u00a74";
		} else if (joules > this.getMaxElectricityStored(stack) * 2 / 3) {
			color = "\u00a72";
		} else {
			color = "\u00a76";
		}

		list.add("");
		list.add(color + EnergyDisplayHelper.getEnergyDisplayS(joules) + "/"
				+ EnergyDisplayHelper.getEnergyDisplayS(this.getMaxElectricityStored(stack)));

    }

	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack)
    {
		return 0x2ed8db;
    }
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
    {
		return true;
    }
	
	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return false;
    }
	
	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		if(source.isUnblockable()) 
			return new ISpecialArmor.ArmorProperties(0, 0.0, 0);
		
		return new ISpecialArmor.ArmorProperties(2, 0.15, Integer.MAX_VALUE);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return this.damageReduceAmount;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		this.discharge(stack, 5, true);
	}
}
