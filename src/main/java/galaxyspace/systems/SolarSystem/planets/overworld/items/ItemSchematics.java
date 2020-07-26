package galaxyspace.systems.SolarSystem.planets.overworld.items;

import java.util.List;

import javax.annotation.Nullable;

import galaxyspace.GalaxySpace;
import galaxyspace.core.GSItems;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.api.recipe.ISchematicItem;
import micdoodle8.mods.galacticraft.api.recipe.SchematicRegistry;
import micdoodle8.mods.galacticraft.core.items.ISortableItem;
import micdoodle8.mods.galacticraft.core.items.ItemSchematic;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryItem;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSchematics extends ItemSchematic implements ISchematicItem, ISortableItem{

	private static int indexOffset = 0;
	
	public ItemSchematics()
    {
        super("schematics");
    }
	
	@Override
    public CreativeTabs getCreativeTab()
    {
        return GSCreativeTabs.GSItemsTab;
    }	

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	return EnumActionResult.FAIL;
    }
    
	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        if (tab == GSCreativeTabs.GSItemsTab || tab == CreativeTabs.SEARCH)
        {
            for (int i = 0; i < 7; i++)
            {
                list.add(new ItemStack(this, 1, i));
            }
        }
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flagIn)
    {		
		int meta = stack.getItemDamage();
		
		switch(meta)
		{
			case 0: list.add(GCCoreUtil.translate("item.rocket_cone.name")); break;
			case 1: list.add(GCCoreUtil.translate("item.rocket_body.name")); break;
			case 2: list.add(GCCoreUtil.translate("item.rocket_engine.name")); break;
			case 3: list.add(GCCoreUtil.translate("item.rocket_booster.name")); break;
			case 4: list.add(GCCoreUtil.translate("item.rocket_stabiliser.name")); break;
			case 5: list.add(GCCoreUtil.translate("item.oxygen_tank_epp_1.name")); break;
			case 6: list.add(GCCoreUtil.translate("tile.port_nuclear_reactor.name")); break;
		}
    }
	
	@Override
    public EnumSortCategoryItem getCategory(int meta)
    {
        return EnumSortCategoryItem.SCHEMATIC;
    }
	
	@Override
    protected int getIndex(int damage)
    {
        return damage + indexOffset;
    }
		
	public static void registerSchematicItems()
    {
		SchematicRegistry.registerSchematicItem(new ItemStack(GSItems.SCHEMATICS, 1, 0));
		SchematicRegistry.registerSchematicItem(new ItemStack(GSItems.SCHEMATICS, 1, 1));
		SchematicRegistry.registerSchematicItem(new ItemStack(GSItems.SCHEMATICS, 1, 2));
		SchematicRegistry.registerSchematicItem(new ItemStack(GSItems.SCHEMATICS, 1, 3));
		SchematicRegistry.registerSchematicItem(new ItemStack(GSItems.SCHEMATICS, 1, 4));
		SchematicRegistry.registerSchematicItem(new ItemStack(GSItems.SCHEMATICS, 1, 5));
		SchematicRegistry.registerSchematicItem(new ItemStack(GSItems.SCHEMATICS, 1, 6));
    }
	
	@SideOnly(value=Side.CLIENT)
    public static void registerTextures()
    {
		SchematicRegistry.registerTexture(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/items/schematics/schematic_1.png"));
		SchematicRegistry.registerTexture(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/items/schematics/schematic_2.png"));
		SchematicRegistry.registerTexture(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/items/schematics/schematic_3.png"));
		SchematicRegistry.registerTexture(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/items/schematics/schematic_4.png"));
		SchematicRegistry.registerTexture(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/items/schematics/schematic_5.png"));
		SchematicRegistry.registerTexture(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/items/schematics/schematic_6.png"));
		SchematicRegistry.registerTexture(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/items/schematics/schematic_7.png"));

    }
		
}
