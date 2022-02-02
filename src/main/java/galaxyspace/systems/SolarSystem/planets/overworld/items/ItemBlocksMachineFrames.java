package galaxyspace.systems.SolarSystem.planets.overworld.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockMachineFrames;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksMachineFrames extends GSItemBlockDesc
{

	public ItemBlocksMachineFrames(Block block) {
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
		return "tile." + BlockMachineFrames.EnumBlockMachineFrames.byMetadata(metadata).getName();
	}
}
