package galaxyspace.systems.SolarSystem.planets.overworld.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockDecoMetals;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksDecoMetals extends GSItemBlockDesc
{
	
	public ItemBlocksDecoMetals(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public String getTranslationKey(ItemStack is) {
		int metadata = is.getItemDamage();
		return "tile." + BlockDecoMetals.EnumBlockDecoMetals.byMetadata(metadata).getName();
	}
}
