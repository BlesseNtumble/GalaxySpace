package galaxyspace.systems.SolarSystem.planets.overworld.items.tools;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.prefab.items.ItemSwordGS;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemAncientSword extends ItemSwordGS{
	
	public static String charge = "charge";	
	protected IIcon[] icons = new IIcon[2];
	private String name;
	private final Item.ToolMaterial material;
	public static boolean mode;
	private float damage;
	
	public ItemAncientSword(String assetName, ToolMaterial material) {
		super(assetName, material);
		this.name = assetName;
		this.material = material;
		this.damage = 4.0F + material.getDamageVsEntity();
		this.setNoRepair();
		
	}

	@Override
    @SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
    	this.icons[0] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":tools/" + name);
   		this.icons[1] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":tools/" + name + "_1");
	}
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass)
    {
		return stack.stackTagCompound != null && stack.stackTagCompound.getBoolean(charge) ? this.icons[1] : this.icons[0];
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack)
    {
		return stack.stackTagCompound != null && stack.stackTagCompound.getBoolean(charge) ? this.icons[1] : this.icons[0];
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List par2List, boolean b)
    { 			
		par2List.add(GCCoreUtil.translate("gui.ancient_item"));
		par2List.add(GCCoreUtil.translate("gui.ancient_sword_1"));  
		par2List.add(GCCoreUtil.translate("gui.ancient_sword_2")); 
		par2List.add(GCCoreUtil.translate("gui.ancient_sword_3")); 
    }
    
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
		
		if (!world.isRemote && player.isSneaking()) {
			if(!stack.stackTagCompound.getBoolean(charge))
			{
				stack.stackTagCompound.setBoolean(charge, true);
				
				player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocal("gui.sword.chargeon")));
			}
			else
			{
				stack.stackTagCompound.setBoolean(charge, false);
				player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("gui.sword.chargeoff")));     			   
			}
			
			this.mode = stack.stackTagCompound.getBoolean(charge);
		}
		return super.onItemRightClick(stack, world, player);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase victim, EntityLivingBase attacker)
    {
		if(!mode) {
			if(attacker.worldObj.rand.nextInt(5) == 0) 
				stack.damageItem(1, attacker);			
		}
		else
		{
			stack.damageItem(4, attacker);
		}
        return true;
    }

	@Override
	public Multimap getItemAttributeModifiers()
    {
		float damage = this.damage;
		if(this.mode) 
			damage += damage * 0.9F;
		
        Multimap multimap = HashMultimap.create();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)damage, 0));
        return multimap;
    }
}
