package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Ores.EnumBlockBarnardaCOres;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksBarnarda_C_Ores extends GSItemBlockDesc
{
    public ItemBlocksBarnarda_C_Ores(Block block) {
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

        if(metadata > EnumBlockBarnardaCOres.values().length) 
        	return "tile." + EnumBlockBarnardaCOres.byMetadata(0).getName();
        
        return "tile." + EnumBlockBarnardaCOres.byMetadata(metadata).getName();
    }    

}

