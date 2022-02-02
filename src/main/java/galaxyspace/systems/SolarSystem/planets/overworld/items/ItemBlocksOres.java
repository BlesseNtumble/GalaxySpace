package galaxyspace.systems.SolarSystem.planets.overworld.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockOres;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksOres extends GSItemBlockDesc 
{
    public ItemBlocksOres(Block block) {
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
    	return "tile." + BlockOres.EnumBlockOres.byMetadata(is.getItemDamage()).getName();        
    }  

}
