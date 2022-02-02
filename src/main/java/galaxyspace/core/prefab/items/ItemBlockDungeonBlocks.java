package galaxyspace.core.prefab.items;

import galaxyspace.core.prefab.blocks.DungeonBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockDungeonBlocks extends GSItemBlockDesc 
{
    public ItemBlockDungeonBlocks(Block block) {
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
    	if(metadata > DungeonBlocks.EnumDungeonBlocks.values().length) 
          	return "tile." + DungeonBlocks.EnumDungeonBlocks.byMetadata(0).getName();
    	return "tile." + DungeonBlocks.EnumDungeonBlocks.byMetadata(is.getItemDamage()).getName();        
    }  

}

