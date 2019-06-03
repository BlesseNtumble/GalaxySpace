package galaxyspace.systems.SolarSystem.planets.overworld.items;

import java.util.List;

import javax.annotation.Nullable;

import asmodeuscore.core.astronomy.dimension.world.gen.features.trees.WorldGenTree_BigJungle;
import asmodeuscore.core.utils.ACAttributePlayer;
import galaxyspace.GalaxySpace;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.registers.potions.GSPotions;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.items.IClickableItem;
import micdoodle8.mods.galacticraft.core.items.ISortableItem;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryItem;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

public class ItemBasicGS extends Item implements ISortableItem{

	public static String[] names = 
	{ 
		"module_smallcanister", 	// 0
		"part_solarflares",		 	// 1
		"solarflares",				// 2
		"dolomite_crystal", 		// 3
		"dolomite_meal", 			// 4
		"wafer_modern", 			// 5
		"hematite", 				// 6
		"sulfur", 					// 7
		"unknow_crystal", 			// 8
		"antiradiation_tablets", 	// 9
		"schematic_box",			// 10
		"module_base",				// 11
		"volcanic_stone", 			// 12
		"meteoric_iron_fragments",  // 13
		"null", 					// 14
		"uranium_fragments",		// 15
		"temp_shield_control", 		// 16
		"ice_bucket",				// 17		
		"emergency_portable_teleport", // 18
		"guide_book", 				// 19
		"advanced_emergency_kit"	// 20
		//"raw_plastic", 				
		//"plastic_stick"				
	};
	
	public static int SHIELD_TIME = 10 * 60;
	private static final int SIZE = 9;
	
	public ItemBasicGS()
	{
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setMaxStackSize(64);
		this.setUnlocalizedName("gs_basic");
		this.setCreativeTab(GSCreativeTabs.GSItemsTab);
	}
	
	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)    
    {
    	if (tab == GSCreativeTabs.GSItemsTab || tab == CreativeTabs.SEARCH)
        {
	        for (int i = 0; i < this.names.length; i++)
	        {
	        	if(!this.names[i].equals("null"))
	        		list.add(new ItemStack(this, 1, i));
	        }
        }
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
		int n = stack.getItemDamage();
		if (n == 4)
			list.add(GCCoreUtil.translate("gui.bonemeal.desc"));
		else if (n == 10)
			list.add(GCCoreUtil.translate("gui.schematic_box.desc"));
		else if (n == 16)
		{
			int time = this.SHIELD_TIME;
			if (stack.hasTagCompound())
            {
                int shieldtime = stack.getTagCompound().getInteger("shieldtime");
                time = shieldtime;
            }
            else
            {
            	time = this.SHIELD_TIME;
            }
			list.add(GCCoreUtil.translate("gui.temp_shield_control.desc"));
			list.add(GCCoreUtil.translate("gui.message.can_find_in_dungeon"));
			list.add(GCCoreUtil.translate("gui.charge") + " " + time + " " + GCCoreUtil.translate("gui.seconds"));
			
		}
		if(n == 18)
		{
			list.add(GCCoreUtil.translate("gui.emergency_portable_teleport.desc"));
			list.add("");
			if(stack.hasTagCompound()) {
				if(stack.getTagCompound().getIntArray("position").length <= 0)
					list.add(GCCoreUtil.translate("gui.not_attached.desc"));
				else
				{
					int[] pos = stack.getTagCompound().getIntArray("position");
					list.add(GCCoreUtil.translate("gui.attached.desc") + " DIM:" + pos[3] + " X:" + pos[0] + ", Y:" + pos[1] + ", Z:" + pos[2]);
				}
				
				if(stack.getTagCompound().getBoolean("turnonoff"))
				{
					list.add(GCCoreUtil.translate("gui.enabled.desc"));
				}
			}
		}
		
	}

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
    	return "item." + this.names[par1ItemStack.getItemDamage()];
    }

    @Override
    public int getMetadata(int par1)
    {
        return par1;
    }

	@Override
	public EnumSortCategoryItem getCategory(int meta) {
		return EnumSortCategoryItem.GENERAL;
	}
	
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		if(world.isRemote)
		{			
			return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
		}
		
		if(stack.getItemDamage() == 15)
		{
			RayTraceResult raytraceresult = this.rayTrace(world, player, true);
			if(raytraceresult == null)
				return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
			
			BlockPos pos = raytraceresult.getBlockPos();
			
			GalaxySpace.debug(pos + "");
			if(world.isAirBlock(pos.up()))
				//new WorldGenTest(3).generate(world, world.rand, pos.up());
				new WorldGenTree_BigJungle(Blocks.LOG.getDefaultState(), Blocks.LEAVES.getDefaultState(), world.rand.nextInt(3)).generate(world, world.rand, pos.up());
		}
		
		else if(stack.getItemDamage() == 19)
		{
			player.openGui(GalaxySpace.MODID, GSConfigCore.guiIDGuideBook, world, 0, 0, 0);
			
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
		}		
		
		else if(stack.getItemDamage() == 9)
		{
			IAttributeInstance lvl = player.getEntityAttribute(ACAttributePlayer.RADIATION_LVL);
			if(lvl.getAttributeValue() > 0) {
				player.addPotionEffect(new PotionEffect(GSPotions.antiradiation, 20*10));
				stack.shrink(1);
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
			}
			else
    		{
				player.sendMessage(new TextComponentString(TextFormatting.DARK_GREEN + GCCoreUtil.translate(("gui.message.noradiation"))));
				return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
    		}
		}
		
		else if(stack.getItemDamage() == 10)
    	{

			for (int i = 0; i < 5; i++)
				if (player.inventory.getFirstEmptyStack() != -1)
					player.inventory.addItemStackToInventory(new ItemStack(GSItems.SCHEMATICS, 1, i));
				else
					world.spawnEntity(new EntityItem(world, player.posX, player.posY, player.posZ,
							new ItemStack(GSItems.SCHEMATICS, 1, i)));

			stack.shrink(1);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    	}
		
		else if(stack.getItemDamage() == 20)
    	{			
			if (player instanceof EntityPlayerMP)
	        {
	            
	            for (int i = 0; i < SIZE; i++)
	            {
	                ItemStack newGear = getContents(i);
	                if (newGear.getItem() instanceof IClickableItem)
	                {
	                    newGear = ((IClickableItem)newGear.getItem()).onItemRightClick(newGear, world, player);
	                }
	                if (newGear.getCount() >= 1)
	                {
	                    ItemHandlerHelper.giveItemToPlayer(player, newGear, 0);
	                }
	            }

	            stack.setCount(0);
	            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	        }
    	}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
	}
		
	//EMERGENCY KIT
	public static ItemStack getContents(int slot)
    {
        switch (slot)
        {
	        case 0: return new ItemStack(GSItems.SPACE_SUIT_HELMET, 1, GSItems.SPACE_SUIT_HELMET.getMaxDamage());
	        case 1: return new ItemStack(GSItems.SPACE_SUIT_BODY, 1, GSItems.SPACE_SUIT_BODY.getMaxDamage());
	        case 2: return new ItemStack(GSItems.SPACE_SUIT_LEGGINS, 1, GSItems.SPACE_SUIT_LEGGINS.getMaxDamage());
	        case 3: return new ItemStack(GSItems.SPACE_SUIT_BOOTS, 1, GSItems.SPACE_SUIT_BOOTS.getMaxDamage());
	        case 4: return new ItemStack(AsteroidsItems.thermalPadding, 1, 0);
	        case 5: return new ItemStack(AsteroidsItems.thermalPadding, 1, 1);
	        case 6: return new ItemStack(AsteroidsItems.thermalPadding, 1, 2);
	        case 7: return new ItemStack(AsteroidsItems.thermalPadding, 1, 3);
	        case 8: return new ItemStack(GCItems.emergencyKit, 1, 0);
	        default: return null;
        }
    }

    public static Object[] getRecipe()
    {
        Object[] result = new Object[]{ "EAB", "CID", "FGH", 'A', null, 'B', null, 'C', null, 'D', null, 'E', null, 'F', null, 'G', null, 'H', null, 'I', null };
        for (int i = 0; i < SIZE; i++)
        {
            result [i * 2 + 4] = getContents(i);
        }        
        return result;
    }
	//END 
}
