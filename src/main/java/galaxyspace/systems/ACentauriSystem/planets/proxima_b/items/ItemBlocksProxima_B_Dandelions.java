package galaxyspace.systems.ACentauriSystem.planets.proxima_b.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.blocks.Proxima_B_Dandelions;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksProxima_B_Dandelions extends GSItemBlockDesc
{
    public ItemBlocksProxima_B_Dandelions(Block block) {
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

        if(metadata > Proxima_B_Dandelions.EnumBlockDandelions.values().length) 
        	return "tile." + Proxima_B_Dandelions.EnumBlockDandelions.byMetadata(0).getName();
        
        return "tile." + Proxima_B_Dandelions.EnumBlockDandelions.byMetadata(metadata).getName();
    }    

}
