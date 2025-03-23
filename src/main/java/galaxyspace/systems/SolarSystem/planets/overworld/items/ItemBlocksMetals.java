package galaxyspace.systems.SolarSystem.planets.overworld.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockDecoMetals;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockMetals;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksMetals extends GSItemBlockDesc
{

	public ItemBlocksMetals(Block block) {
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
		return "tile." + BlockMetals.EnumBlockMetals.byMetadata(metadata).getName().replace("_1", "").replace("_2", "");
	}
}
