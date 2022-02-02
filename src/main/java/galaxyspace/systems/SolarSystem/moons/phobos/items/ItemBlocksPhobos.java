package galaxyspace.systems.SolarSystem.moons.phobos.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.SolarSystem.moons.phobos.blocks.PhobosBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksPhobos extends GSItemBlockDesc 
{
    public ItemBlocksPhobos(Block block) {
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

        return "tile." + PhobosBlocks.EnumPhobosBlocks.byMetadata(metadata).getName();
    }   
    
}
