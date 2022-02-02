package galaxyspace.systems.SolarSystem.planets.overworld.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockFutureGlasses;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksFutureGlasses extends GSItemBlockDesc 
{
    public ItemBlocksFutureGlasses(Block block) {
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

        if(metadata > BlockFutureGlasses.EnumBlockFutureGlass.values().length) 
        	return "tile." + BlockFutureGlasses.EnumBlockFutureGlass.byMetadata(0).getName();
        
        else return "tile." + BlockFutureGlasses.EnumBlockFutureGlass.byMetadata(metadata).getName();
    }
    
}
