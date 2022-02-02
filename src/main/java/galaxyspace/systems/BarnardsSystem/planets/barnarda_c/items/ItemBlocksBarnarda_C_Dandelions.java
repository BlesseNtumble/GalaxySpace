package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Dandelions.EnumBlockDandelions;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksBarnarda_C_Dandelions extends GSItemBlockDesc
{
    public ItemBlocksBarnarda_C_Dandelions(Block block) {
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

        if(metadata > EnumBlockDandelions.values().length) 
        	return "tile." + EnumBlockDandelions.byMetadata(0).getName();
        
        return "tile." + EnumBlockDandelions.byMetadata(metadata).getName();
    }    

}
