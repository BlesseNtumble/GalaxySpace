package galaxyspace.systems.SolarSystem.moons.callisto.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.SolarSystem.moons.callisto.blocks.CallistoBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksCallisto extends GSItemBlockDesc 
{
    public ItemBlocksCallisto(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int meta)
    {
        return meta;
    }
    
    @Override
    public String getTranslationKey(ItemStack is) {
    	
        int metadata = is.getItemDamage();

        return "tile." + CallistoBlocks.EnumCallistoBlocks.byMetadata(metadata).getName();
    }   
    
}
