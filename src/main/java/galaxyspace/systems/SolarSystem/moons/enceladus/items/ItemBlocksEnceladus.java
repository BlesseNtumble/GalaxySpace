package galaxyspace.systems.SolarSystem.moons.enceladus.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.SolarSystem.moons.enceladus.blocks.EnceladusBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksEnceladus extends GSItemBlockDesc 
{
    public ItemBlocksEnceladus(Block block) {
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
        return "tile." + EnceladusBlocks.EnumEnceladusBlocks.byMetadata(metadata).getName();
    }   
}
