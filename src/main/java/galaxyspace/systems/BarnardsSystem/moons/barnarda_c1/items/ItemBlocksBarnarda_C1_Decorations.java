package galaxyspace.systems.BarnardsSystem.moons.barnarda_c1.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.BarnardsSystem.moons.barnarda_c1.blocks.Barnarda_C1_Blocks;
import galaxyspace.systems.BarnardsSystem.moons.barnarda_c1.blocks.Barnarda_C1_Decorations;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksBarnarda_C1_Decorations extends GSItemBlockDesc
{
    public ItemBlocksBarnarda_C1_Decorations(Block block) {
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

        if(metadata > Barnarda_C1_Decorations.EnumBlockBarnardaC1Decoration.values().length)
        	return "tile." + Barnarda_C1_Decorations.EnumBlockBarnardaC1Decoration.byMetadata(0).getName();
        
        return "tile." + Barnarda_C1_Decorations.EnumBlockBarnardaC1Decoration.values()[metadata % 5].getName();
    }    
}
