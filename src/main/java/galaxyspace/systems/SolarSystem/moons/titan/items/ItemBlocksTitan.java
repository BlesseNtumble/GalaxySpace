package galaxyspace.systems.SolarSystem.moons.titan.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.SolarSystem.moons.titan.blocks.TitanBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksTitan extends GSItemBlockDesc
{
    public ItemBlocksTitan(Block block) {
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

        return "tile." + TitanBlocks.EnumTitanBlocks.byMetadata(metadata).getName();
    }   

}
