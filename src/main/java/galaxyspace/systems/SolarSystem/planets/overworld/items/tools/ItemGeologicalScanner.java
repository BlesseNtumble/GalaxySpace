package galaxyspace.systems.SolarSystem.planets.overworld.items.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import galaxyspace.core.GSFluids;
import galaxyspace.core.events.GSClientTickHandler;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.items.ISortableItem;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryItem;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;

public class ItemGeologicalScanner extends ItemElectricBase implements ISortableItem {

	private int mode = 0;
	public ItemGeologicalScanner()
	{
		super();
		this.setTranslationKey("geo_scanner");  
	    this.setHasSubtypes(true);
	}
	
	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)    
    {
    	if (tab == GSCreativeTabs.GSItemsTab || tab == CreativeTabs.SEARCH)        
    		list.add(new ItemStack(this, 1, this.getMaxDamage()));
        
    }

	@Override
	public void addInformation(ItemStack itemStack, @Nullable World worldIn, List<String> tooltip,	ITooltipFlag flagIn) {
	
		tooltip.add(GCCoreUtil.translate("gui.geological_scanner.desc"));
		
		String color = "";
		float joules = this.getElectricityStored(itemStack);

		if (joules <= this.getMaxElectricityStored(itemStack) / 3) {
			color = "\u00a74";
		} else if (joules > this.getMaxElectricityStored(itemStack) * 2 / 3) {
			color = "\u00a72";
		} else {
			color = "\u00a76";
		}

		tooltip.add(color + EnergyDisplayHelper.getEnergyDisplayS(joules) + "/" + EnergyDisplayHelper.getEnergyDisplayS(this.getMaxElectricityStored(itemStack)));
	}
	 
	@Override
	public EnumSortCategoryItem getCategory(int meta) {
		return EnumSortCategoryItem.TOOLS;
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {       
		ItemStack stack = player.getHeldItem(hand);
		
		if(!world.isRemote && this.getElectricityStored(stack) >= 100.0F)
		{
			if(player.isSneaking())
			{
				if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
				
				if(stack.getTagCompound().hasKey("mode"))
					stack.getTagCompound().setInteger("mode", stack.getTagCompound().getInteger("mode") > 0 ? 0 : 1);
				else 
					stack.getTagCompound().setInteger("mode", 1);
					
								
				player.sendMessage(new TextComponentString(TextFormatting.GREEN + "Mode: " + stack.getTagCompound().getInteger("mode")));
				return EnumActionResult.SUCCESS;	
			}
			if(stack.getTagCompound().getInteger("mode") == 0) {
					Map<IBlockState, Integer> blocks = new HashMap<IBlockState, Integer>();
				
				
					for(BlockPos scan : pos.getAllInBox(pos.add(-2, -2, -2), pos.add(2, 2, 2))) {
						IBlockState block = world.getBlockState(scan);				
						if(world.isAirBlock(scan)) continue;				
							
						if(blocks.containsKey(block)) {
							blocks.put(block, blocks.get(block) + 1);
							continue;
						}
							
						blocks.put(block, 1);
					}
					
					Map<IBlockState, String> blocks_to_render = new HashMap<IBlockState, String>();	
					for(Entry<IBlockState, Integer> block : blocks.entrySet()) {
						ItemStack item = new ItemStack(Item.getItemFromBlock(block.getKey().getBlock()), 1, block.getKey().getBlock().getMetaFromState(block.getKey()));				
	    				
						blocks_to_render.put(block.getKey(), item.getDisplayName() + " x" + block.getValue());
					}
					GSClientTickHandler.blocks = blocks_to_render;
					GSClientTickHandler.ticks = 50;
					
					//GalaxySpace.packetPipeline.sendToServer(new GSPacketSimple(GSEnumSimplePacket.C_SHOW_SCANNER_BLOCK, player.world, new Object[] {0}));
						
					if(!player.capabilities.isCreativeMode)
						this.discharge(stack, 20 * blocks.size(), true);
			}
			else if(stack.getTagCompound().getInteger("mode") == 1) {
				int radius = 8;
				boolean found = false;
				
				for(int y = (int)player.posY; y > 5; y--) {
					
					if(found) break;
					
					for(int x = -radius; x < radius; x++) {
						if(found) break;
						for(int z = -radius; z < radius; z++) {
							IBlockState block = world.getBlockState(new BlockPos(pos.getX() + x, y, pos.getZ() + z));
							if(block.getBlock() == FluidRegistry.getFluidStack("oil", 1).getFluid().getBlock() ||
									block.getBlock() == FluidRegistry.LAVA.getBlock() ||
									block.getBlock() == GSFluids.BLOCK_NATURE_GAS)
							{								
								//player.sendMessage(new TextComponentString(TextFormatting.GREEN + "Y Oil: " + y));
								Map<IBlockState, String> blocks_to_render = new HashMap<IBlockState, String>();
								blocks_to_render.put(block, block.getBlock().getLocalizedName() + " Y Level: " + y);
								GSClientTickHandler.blocks = blocks_to_render;
								GSClientTickHandler.ticks = 100;
								
								found = true;
								break;
							}
						}
					}
				}
				
				if(!player.capabilities.isCreativeMode)
					this.discharge(stack, 20, true);
			}
		}
		return EnumActionResult.SUCCESS;
	}

	@Override
	public float getMaxElectricityStored(ItemStack theItem) {
		return 60000;
	}
	
}
