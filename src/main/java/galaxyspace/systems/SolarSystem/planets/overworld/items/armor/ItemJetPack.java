package galaxyspace.systems.SolarSystem.planets.overworld.items.armor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.api.item.IJetpack;
import galaxyspace.core.prefab.items.ItemElectricArmor;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.systems.SolarSystem.planets.overworld.render.item.ItemSpaceSuitModel;
import micdoodle8.mods.galacticraft.api.item.IItemElectricBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemJetPack extends ItemElectricArmor implements IJetpack {

	public float transferMax;

	public ItemJetPack(int armorIndex) {
		super(GSItems.JETPACK, GalaxySpace.proxy.getJetpackArmorRenderIndex(), armorIndex, "JetPack");
		this.setTextureName("arrow");		
	}
	
	@Override
	public CreativeTabs getCreativeTab() {
		return GSCreativeTabs.GSArmorTab;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {

		// ModelBiped model = new ModelJetPack();
		ModelBiped model = new ItemSpaceSuitModel(6);
		if (itemStack.getItem() instanceof ItemJetPack) {
			model = ItemSpaceArmors.fillingArmorModel(model, entityLiving);
		}
		return model;

	}

	@Override
	public float getMaxElectricityStored(ItemStack theItem) {
		return 100000;
	}

	@Override
	public void consumeFuel(ItemStack stack, int fuel) {
		stack.setItemDamage(stack.getItemDamage() + 1);
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
		stack.getTagCompound().setBoolean(TAG_ACT, state);
	}
	
	@Override
	public int getFireStreams(ItemStack stack) {
		return 2;
	}
}