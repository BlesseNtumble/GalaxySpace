package galaxyspace.systems.SolarSystem.planets.mars.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.SolarSystem.planets.mars.blocks.MarsOresBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksMarsOres extends GSItemBlockDesc
{
    public ItemBlocksMarsOres(Block block) {
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

        return "tile." + MarsOresBlocks.EnumMarsOresBlocks.byMetadata(metadata).getName();
    }    

}
