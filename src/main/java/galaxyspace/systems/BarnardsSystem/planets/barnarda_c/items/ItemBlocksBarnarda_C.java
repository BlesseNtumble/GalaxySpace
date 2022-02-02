package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Blocks.EnumBlockBarnardaC;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksBarnarda_C extends GSItemBlockDesc
{
    public ItemBlocksBarnarda_C(Block block) {
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

        if(metadata > EnumBlockBarnardaC.values().length) 
        	return "tile." + EnumBlockBarnardaC.byMetadata(0).getName();
        
        return "tile." + EnumBlockBarnardaC.byMetadata(metadata).getName();
    }    
}
