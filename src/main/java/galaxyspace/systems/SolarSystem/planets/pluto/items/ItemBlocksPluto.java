package galaxyspace.systems.SolarSystem.planets.pluto.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.SolarSystem.planets.pluto.blocks.PlutoBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksPluto extends GSItemBlockDesc
{
    public ItemBlocksPluto(Block block) {
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
        if(metadata <= 4) return "tile.pluto_grunt";
        
        return "tile." + PlutoBlocks.EnumPlutoBlocks.byMetadata(metadata).getName();
    }

}
