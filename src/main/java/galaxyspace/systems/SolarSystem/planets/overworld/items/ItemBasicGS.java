package galaxyspace.systems.SolarSystem.planets.overworld.items;

import java.util.List;

import javax.annotation.Nullable;

import asmodeuscore.core.utils.ACAttributePlayer;
import galaxyspace.GalaxySpace;
import galaxyspace.core.GSItems;
import galaxyspace.core.GSPotions;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.configs.GSConfigSchematics;
import galaxyspace.core.handler.capabilities.GSStatsCapability;
import galaxyspace.core.handler.capabilities.StatsCapability;
import galaxyspace.core.network.packet.GSPacketSimple;
import galaxyspace.core.network.packet.GSPacketSimple.GSEnumSimplePacket;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.items.IClickableItem;
import micdoodle8.mods.galacticraft.core.items.ISortableItem;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryItem;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.planets.asteroids.blocks.AsteroidBlocks;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import micdoodle8.mods.galacticraft.planets.asteroids.tile.TileEntityShortRangeTelepad;
import micdoodle8.mods.galacticraft.planets.mars.blocks.MarsBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
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
		"blank_schematic", 			// 14
		"uranium_fragments",		// 15
		"temp_shield_control", 		// 16
		"ice_bucket",				// 17		
		"emergency_portable_teleport", // 18
		"guide_book", 				// 19
		"advanced_emergency_kit",	// 20
		"thermal_cloth_t3",			// 21
		"thermal_cloth_t4",			// 22
		"oxygen_ice_crystal",		// 23
		"nitrogen_ice_crystal",		// 24
		"methane_ice_crystal",		// 25
		"hydrogen_ice_crystal",		// 26
		"dry_ice_crystal",			// 27
		"colonist_kit"				// 28
		//"plutonium_shard",			// 29
		//"plutonium_pellet",			// 30
		//"small_gen_field",		
		//"empty_plasma_cell",		
		//"filled_plasma_cell"		
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
		else if (n == 14 && !GSConfigSchematics.enableDuplicateSchematic)
		{
			list.add(EnumColor.DARK_RED + "Disabled in config.");
		}
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
    public String getUnlocalizedName(ItemStack stack)
    {
    	if(stack.getItemDamage() == 21 || stack.getItemDamage() == 22) return "item.thermal_cloth";
    	return "item." + this.names[stack.getItemDamage()];
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
		else if(stack.getItemDamage() == 27)
		{
		}
		/*
		else if(stack.getItemDamage() == 28)
		{
			RayTraceResult ray = this.getRay(world, player, true);
			Map<IBlockState, Integer> blocks = new HashMap<IBlockState, Integer>();
			
			if(ray != null) {		
				for(BlockPos scan : ray.getBlockPos().getAllInBox(ray.getBlockPos().add(-2, -2, -2), ray.getBlockPos().add(2, 2, 2))) {
					IBlockState block = world.getBlockState(scan);				
					if(world.isAirBlock(scan)) continue;				
					
					if(blocks.containsKey(block)) {
						blocks.put(block, blocks.get(block) + 1);
						continue;
					}
					
					blocks.put(block, 1);
				}
				
				for(Entry<IBlockState, Integer> block : blocks.entrySet()) {
					ItemStack item = new ItemStack(Item.getItemFromBlock(block.getKey().getBlock()), 1, block.getKey().getBlock().getMetaFromState(block.getKey()));				
						
					player.sendMessage(new TextComponentString(TextFormatting.GREEN + item.getDisplayName() + " x" + block.getValue()));
				}//GalaxySpace.debug("" + blocks);
				player.sendMessage(new TextComponentString(TextFormatting.GREEN + "#################################"));
				
			}
			
		}*/
		else if(stack.getItemDamage() == 4)
		{
			RayTraceResult ray = this.getRay(world, player, true);
			if (ray != null && ItemDye.applyBonemeal(stack, world, ray.getBlockPos(), player, hand)) {
				if (!world.isRemote) {
					world.playEvent(2005, ray.getBlockPos(), 0);
				}

				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
			}
			return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
		}
		else if(stack.getItemDamage() == 6)
		{
			/*StatsCapability gs_stats = GSStatsCapability.get(player);
			gs_stats.setKnowledgeResearch(1, 6);
			GalaxySpace.packetPipeline.sendTo(new GSPacketSimple(GSEnumSimplePacket.C_UPDATE_RESEARCH, player.world, new Object[] {1, 6}), (EntityPlayerMP) player);
			*/
			
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
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
		else if(stack.getItemDamage() == 18)
		{
			if(!player.isSneaking())
				return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
			
			if(!stack.hasTagCompound())				
				stack.setTagCompound(new NBTTagCompound());
			
			RayTraceResult ray = player.rayTrace(5, player.ticksExisted);
			Block block = world.getBlockState(ray.getBlockPos()).getBlock();
			
			if(block == AsteroidBlocks.shortRangeTelepad) 
			{
				TileEntityShortRangeTelepad tile = (TileEntityShortRangeTelepad) world.getTileEntity(ray.getBlockPos());
				
				if(tile.hasEnoughEnergyToRun && tile.addressValid) 
				{
					stack.getTagCompound().setIntArray("position", new int[] {ray.getBlockPos().getX(), ray.getBlockPos().getY(), ray.getBlockPos().getZ(), world.provider.getDimension()});		
				}
				else
				{
					player.sendMessage(new TextComponentString(TextFormatting.DARK_RED + GCCoreUtil.translate(("gui.message.invalid_teleport_conf"))));
				}
			}
			else
			{				
				stack.getTagCompound().setBoolean("turnonoff", !stack.getTagCompound().getBoolean("turnonoff"));					
			}
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

	            stack.shrink(1);
	            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	        }
    	}
		else if(stack.getItemDamage() == 28)
		{
			if (player instanceof EntityPlayerMP)
	        {
				ItemStack[] stacks = new ItemStack[] {
						new ItemStack(GCBlocks.oxygenDistributor, 1), new ItemStack(GCBlocks.oxygenCollector, 1), new ItemStack(GCBlocks.oxygenCompressor, 1), 
						new ItemStack(GCBlocks.solarPanel, 1, 4),  new ItemStack(GSItems.BASIC, 1, 20),  new ItemStack(GCBlocks.solarPanel, 1, 4),
						new ItemStack(GCBlocks.machineTiered, 1, 0), new ItemStack(MarsBlocks.machine, 1, 0), new ItemStack(GCBlocks.machineBase2, 1, 8)
				};
				
				for(ItemStack items : stacks)				
					ItemHandlerHelper.giveItemToPlayer(player, items, 0);
				
				stack.shrink(1);
	            return new ActionResult<>(EnumActionResult.SUCCESS, stack);				
	        }
		}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
	}

	public static Object[] getColonistKitRecipe()
	{
		ItemStack[] stacks = new ItemStack[] {
				new ItemStack(GCBlocks.oxygenDistributor, 1), new ItemStack(GCBlocks.oxygenCollector, 1), new ItemStack(GCBlocks.oxygenCompressor, 1), 
				new ItemStack(GCBlocks.solarPanel, 1, 4), new ItemStack(GSItems.BASIC, 1, 20), new ItemStack(GCBlocks.solarPanel, 1, 4), 
				new ItemStack(GCBlocks.machineTiered, 1, 0), new ItemStack(MarsBlocks.machine, 1, 0), new ItemStack(GCBlocks.machineBase2, 1, 8)
		};
		
		Object[] result = new Object[]{ "ABC", "DEF", "GHI", 'A', null, 'B', null, 'C', null, 'D', null, 'E', null, 'F', null, 'G', null, 'H', null, 'I', null };
		for (int i = 0; i < stacks.length; i++)
        {
			result [i * 2 + 4] = stacks[i];
        }
		
		return result;
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
    
    @Override
    public int getItemBurnTime(ItemStack itemStack)
    {
    	if(itemStack.getItemDamage() == 12) return 128000;
        return -1;
    }
    
    public static RayTraceResult getRay(World world, EntityPlayer player, boolean useLiquids)
	{
		float f = player.rotationPitch;
		float f1 = player.rotationYaw;
		double d0 = player.posX;
		double d1 = player.posY + (double) player.getEyeHeight();
		double d2 = player.posZ;
		Vec3d vec3d = new Vec3d(d0, d1, d2);
		float f2 = MathHelper.cos(-f1 * 0.017453292F - (float) Math.PI);
		float f3 = MathHelper.sin(-f1 * 0.017453292F - (float) Math.PI);
		float f4 = -MathHelper.cos(-f * 0.017453292F);
		float f5 = MathHelper.sin(-f * 0.017453292F);
		float f6 = f3 * f4;
		float f7 = f2 * f4;
		double d3 = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue() + 10;
		Vec3d vec3d1 = vec3d.addVector((double) f6 * d3, (double) f5 * d3, (double) f7 * d3);
		return world.rayTraceBlocks(vec3d, vec3d1, useLiquids, !useLiquids, false);
	}
}
