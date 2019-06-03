package galaxyspace.systems.SolarSystem.moons.io.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.SolarSystem.moons.io.blocks.IoBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksIo extends GSItemBlockDesc 
{
    public ItemBlocksIo(Block block) {
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
    public String getUnlocalizedName(ItemStack is) {
    	
        int metadata = is.getItemDamage();

        return "tile." + IoBlocks.EnumIoBlocks.byMetadata(metadata).getName();
    }   

}
