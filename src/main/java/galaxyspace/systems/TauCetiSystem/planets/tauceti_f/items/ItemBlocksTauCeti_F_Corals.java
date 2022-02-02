package galaxyspace.systems.TauCetiSystem.planets.tauceti_f.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.TauCetiSystem.planets.tauceti_f.blocks.TauCeti_F_Corals.EnumBlockCorals;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksTauCeti_F_Corals extends GSItemBlockDesc
{
    public ItemBlocksTauCeti_F_Corals(Block block) {
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

        if(metadata > EnumBlockCorals.values().length) 
        	return "tile." + EnumBlockCorals.byMetadata(0).getName();
        
        return "tile." + EnumBlockCorals.byMetadata(metadata).getName();
    }    

}
