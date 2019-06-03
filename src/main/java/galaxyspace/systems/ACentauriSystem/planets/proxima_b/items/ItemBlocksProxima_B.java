package galaxyspace.systems.ACentauriSystem.planets.proxima_b.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.ACentauriSystem.planets.proxima_b.blocks.Proxima_B_Blocks.EnumBlockProximaB;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksProxima_B extends GSItemBlockDesc
{
    public ItemBlocksProxima_B(Block block) {
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
    public String getUnlocalizedName(ItemStack is) {
    	
        int metadata = is.getItemDamage();

        if(metadata > EnumBlockProximaB.values().length) 
        	return "tile." + EnumBlockProximaB.byMetadata(0).getName();
        
        return "tile." + EnumBlockProximaB.byMetadata(metadata).getName();
    }
    
}
