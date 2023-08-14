package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Glow_Dandelions.EnumBlockGlowDandelions;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksBarnarda_C_Glow_Dandelions extends GSItemBlockDesc
{
    public ItemBlocksBarnarda_C_Glow_Dandelions(Block block) {
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

        if(metadata > EnumBlockGlowDandelions.values().length)
        	return "tile." + EnumBlockGlowDandelions.byMetadata(0).getName();
        
        return "tile." + EnumBlockGlowDandelions.byMetadata(metadata).getName();
    }    

}
