package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Grass.EnumBlockGrass;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksBarnarda_C_Grasses extends GSItemBlockDesc
{
    public ItemBlocksBarnarda_C_Grasses(Block block) {
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

        if(metadata > EnumBlockGrass.values().length) 
        	return "tile." + EnumBlockGrass.byMetadata(0).getName();
        
        return "tile." + EnumBlockGrass.byMetadata(metadata).getName();
    }    
}
