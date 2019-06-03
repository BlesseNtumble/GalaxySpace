package galaxyspace.systems.SolarSystem.planets.ceres.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.SolarSystem.planets.ceres.blocks.CeresBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksCeres extends GSItemBlockDesc
{
    public ItemBlocksCeres(Block block) {
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

        return "tile." + CeresBlocks.EnumCeresBlocks.byMetadata(metadata).getName();
    }

}
