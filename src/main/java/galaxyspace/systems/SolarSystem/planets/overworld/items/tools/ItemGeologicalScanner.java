package galaxyspace.systems.SolarSystem.planets.overworld.items.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.items.ISortableItem;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemGeologicalScanner extends ItemElectricBase implements ISortableItem {

	public ItemGeologicalScanner()
	{
		super();
		this.setUnlocalizedName("geo_scanner");  
	    this.setHasSubtypes(true);
	}
	
	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)    
    {
    	if (tab == GSCreativeTabs.GSItemsTab || tab == CreativeTabs.SEARCH)
        {
    		list.add(new ItemStack(this, 1, 0));
    		list.add(new ItemStack(this, 1, this.getMaxDamage()));
        }
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
			//RayTraceResult ray = ItemBasicGS.getRay(world, player, true);
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
					
				for(Entry<IBlockState, Integer> block : blocks.entrySet()) {
					ItemStack item = new ItemStack(Item.getItemFromBlock(block.getKey().getBlock()), 1, block.getKey().getBlock().getMetaFromState(block.getKey()));				
							
					player.sendMessage(new TextComponentString(TextFormatting.GREEN + item.getDisplayName() + " x" + block.getValue()));
				}//GalaxySpace.debug("" + blocks);
				player.sendMessage(new TextComponentString(TextFormatting.GREEN + "#################################"));
				if(!player.capabilities.isCreativeMode)
					this.discharge(stack, 20 * blocks.size(), true);
		}
		return EnumActionResult.SUCCESS;
	}

	@Override
	public float getMaxElectricityStored(ItemStack theItem) {
		return 30000;
	}
	
}
