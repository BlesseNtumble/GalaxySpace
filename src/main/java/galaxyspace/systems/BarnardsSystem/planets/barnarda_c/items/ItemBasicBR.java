package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items;

import java.util.List;

import javax.annotation.Nullable;

import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.systems.BarnardsSystem.core.registers.BRBlocks;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Blocks;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Dandelions;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Grass;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Logs;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.WorldGenTree_Swampland;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.WorldGenTree_Swampland_2;
import micdoodle8.mods.galacticraft.core.items.ISortableItem;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryItem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBasicBR extends Item implements ISortableItem{

	public static String[] names = 
	{ 
		"violet_reeds",
		"water_grass",
		"debugger"
	};
	
	public ItemBasicBR()
	{
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setMaxStackSize(64);
		this.setUnlocalizedName("br_basic");
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
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack itemstack = player.getHeldItem(hand);
		RayTraceResult raytraceresult = this.rayTrace(world, player, true);
		if(raytraceresult == null)
			return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
		
		BlockPos pos = raytraceresult.getBlockPos();
		
		if(itemstack.getItemDamage() == 0) {
			if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK)
            {
				if (!world.isBlockModifiable(player, pos) || !player.canPlayerEdit(pos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack)) {
					return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
				}
				
				boolean hasWater = false;
				for(BlockPos water : BlockPos.getAllInBox(pos.add(-1, 0, -1), pos.add(1, 0, 1)))
				{
					if(world.getBlockState(water).getBlock() == FluidRegistry.WATER.getBlock())
					{
						hasWater = true;
						break;
					}
				}
				
				BlockPos blockpos1 = pos.up();
				IBlockState iblockstate = world.getBlockState(pos);
				
				if(hasWater && world.isAirBlock(blockpos1) && 
						( iblockstate == BRBlocks.BARNARDA_C_GRASS.getDefaultState().withProperty(Barnarda_C_Grass.BASIC_TYPE, Barnarda_C_Grass.EnumBlockGrass.GRASS)
						|| iblockstate == BRBlocks.BARNARDA_C_BLOCKS.getDefaultState().withProperty(Barnarda_C_Blocks.BASIC_TYPE, Barnarda_C_Blocks.EnumBlockBarnardaC.DIRT)))
				{
					world.setBlockState(blockpos1, BRBlocks.BARNARDA_C_DANDELIONS.getDefaultState().withProperty(Barnarda_C_Dandelions.BASIC_TYPE, Barnarda_C_Dandelions.EnumBlockDandelions.REEDS));
					return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
				}
            }
		}		
		if(itemstack.getItemDamage() == 2) {
			if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK)
            {
				if (!world.isBlockModifiable(player, pos) || !player.canPlayerEdit(pos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack)) {
					return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
				}
				
				BlockPos blockpos1 = pos.up();
                IBlockState iblockstate = world.getBlockState(pos);
                
                if (iblockstate.getMaterial() == Material.WATER && ((Integer)iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0 && world.isAirBlock(blockpos1))
                {
                	world.setBlockState(blockpos1, BRBlocks.BARNARDA_C_WATER_GRASS.getDefaultState());
                	
                	world.playSound(player, pos, SoundEvents.BLOCK_WATERLILY_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
                }
            }
		}
		if(itemstack.getItemDamage() == 3) {
			GalaxySpace.debug(pos + "");
			if(world.isAirBlock(pos.up())) {
				new WorldGenTree_Swampland_2(BRBlocks.BARNARDA_C_VIOLET_LOG.getDefaultState().withProperty(Barnarda_C_Logs.LOG_AXIS, Barnarda_C_Logs.EnumAxis.NONE), BRBlocks.BARNARDA_C_LEAVES.getStateFromMeta(0), world.rand.nextInt(3)).generate(world, world.rand, pos.up());
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
			}
		}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
	
	}
	
	@Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
    	return "item." + this.names[par1ItemStack.getItemDamage()];
    }
	
	@Override
	public int getMetadata(int par1) {
		return par1;
	}
	
	@Override
	public EnumSortCategoryItem getCategory(int meta) {
		return EnumSortCategoryItem.GENERAL;
	}		
}
