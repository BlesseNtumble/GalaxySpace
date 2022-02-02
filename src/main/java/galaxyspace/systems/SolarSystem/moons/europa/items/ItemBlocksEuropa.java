package galaxyspace.systems.SolarSystem.moons.europa.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.SolarSystem.moons.europa.blocks.EuropaBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksEuropa extends GSItemBlockDesc
{
    public ItemBlocksEuropa(Block block) {
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

        return "tile." + EuropaBlocks.EnumEuropaBlocks.byMetadata(metadata).getName();
    }   


}