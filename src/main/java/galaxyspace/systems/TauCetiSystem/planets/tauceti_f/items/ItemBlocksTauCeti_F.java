package galaxyspace.systems.TauCetiSystem.planets.tauceti_f.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.TauCetiSystem.planets.tauceti_f.blocks.TauCeti_F_Blocks.EnumBlockTauCetiF;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksTauCeti_F extends GSItemBlockDesc
{
    public ItemBlocksTauCeti_F(Block block) {
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

        if(metadata > EnumBlockTauCetiF.values().length) 
        	return "tile." + EnumBlockTauCetiF.byMetadata(0).getName();
        
        return "tile." + EnumBlockTauCetiF.byMetadata(metadata).getName();
    }    
}
