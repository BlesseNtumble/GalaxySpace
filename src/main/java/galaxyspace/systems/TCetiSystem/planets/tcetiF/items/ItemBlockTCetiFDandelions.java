package galaxyspace.systems.TCetiSystem.planets.tcetiF.items;

import galaxyspace.systems.TCetiSystem.planets.tcetiF.blocks.TCetiFBlockDandelions;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

public class ItemBlockTCetiFDandelions extends ItemMultiTexture{

	public ItemBlockTCetiFDandelions(Block block) {
        super(block, block, TCetiFBlockDandelions.dandelion);
    }
}
