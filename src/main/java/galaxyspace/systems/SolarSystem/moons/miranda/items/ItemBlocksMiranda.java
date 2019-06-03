package galaxyspace.systems.SolarSystem.moons.miranda.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.SolarSystem.moons.miranda.blocks.MirandaBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksMiranda extends GSItemBlockDesc 
{
    public ItemBlocksMiranda(Block block) {
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

        if(metadata == 9 || metadata == 10) return "tile.miranda_grunt";
        if(metadata == 11 || metadata == 12) return "tile.miranda_subgrunt";
        if(metadata == 13 || metadata == 14) return "tile.miranda_stone";
        
        if(metadata > MirandaBlocks.EnumMirandaBlocks.values().length) 
        	return "tile." + MirandaBlocks.EnumMirandaBlocks.byMetadata(0).getName();
        
        else return "tile." + MirandaBlocks.EnumMirandaBlocks.byMetadata(metadata).getName();
    }
    
}

