package galaxyspace.core.prefab.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class ItemBowGS extends ItemBow {

	@SideOnly(Side.CLIENT) 
	private IIcon[] iconArray;
	public static final String[] bowPullIconNameArray = new String[] {"0", "1", "2"};
	
	private ItemStack arrow = new ItemStack(Items.arrow);
		
	public ItemBowGS(String name, int maxDamage, ItemStack arrowToUse) {
		this.maxStackSize = 1;
		this.setMaxDamage(maxDamage);
		this.setUnlocalizedName(name);
		this.arrow = arrowToUse;
	}

	@Override
	public CreativeTabs getCreativeTab() {
		return GSCreativeTabs.GSArmorTab;
	}
	
	@SideOnly(Side.CLIENT)
	private IIcon[] Texture;

	public void registerIcons(IIconRegister iconRegister)
	{
		itemIcon = iconRegister
				.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "tools/" + this.getUnlocalizedName().toLowerCase());
		Texture = new IIcon[4];
		for (int N = 1; N < 4; N++) {
			this.Texture[N] = iconRegister.registerIcon(
					GalaxySpace.ASSET_PREFIX + ":" + "tools/" + this.getUnlocalizedName().toLowerCase() + "_" + N);

		}
	}

	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		if (player.getItemInUse() == null)
			return this.itemIcon;
		int time = stack.getMaxItemUseDuration() - useRemaining;
		if (time >= 18) {
			return Texture[3];
		} else if (time > 13) {
			return Texture[2];
		} else if (time > 0) {
			return Texture[1];
		}
		return Texture[0];
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.bow;
	}	

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		ArrowNockEvent event = new ArrowNockEvent(player, stack);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled()) {
			return event.result;
		}

		if (player.capabilities.isCreativeMode || player.inventory.hasItem(this.arrow.getItem())) {
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}

		return stack;
	}	

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) {
		int j = getMaxItemUseDuration(par1ItemStack) - par4;

		ArrowLooseEvent event = new ArrowLooseEvent(par3EntityPlayer, par1ItemStack, j);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled()) {
			return;
		}
		j = event.charge;

		boolean flag = par3EntityPlayer.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;

		if (flag || par3EntityPlayer.inventory.hasItem(this.arrow.getItem())) {
			float f = (float) j / 20.0F;
			f = (f * f + f * 2.0F) / 3.0F;

			if ((double) f < 0.1D) {
				return;
			}

			if (f > 1.0F) {
				f = 1.0F;
			}

			EntityArrow entityarrow = new EntityArrow(par2World, par3EntityPlayer, f * 2.0F);

			if (f == 1.0F) {
				entityarrow.setIsCritical(true);
			}

			int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);

			if (k > 0) {
				entityarrow.setDamage(entityarrow.getDamage() + (double) k * 0.5D + 0.5D);
			}

			int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);

			if (l > 0) {
				entityarrow.setKnockbackStrength(l);
			}

			entityarrow.setDamage(entityarrow.getDamage() + 1.5D);
			entityarrow.setFire(100);
			par1ItemStack.damageItem(1, par3EntityPlayer);
			par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F,
					1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

			if (flag) {
				entityarrow.canBePickedUp = 2;
			} else {
				par3EntityPlayer.inventory.consumeInventoryItem(this.arrow.getItem());
			}

			if (!par2World.isRemote) {
				par2World.spawnEntityInWorld(entityarrow);
			}
		}
	}


}
