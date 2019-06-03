package galaxyspace.systems.SolarSystem.planets.mercury.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.SolarSystem.planets.mercury.blocks.MercuryBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksMercury extends GSItemBlockDesc
{
    public ItemBlocksMercury(Block block) {
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

        return "tile." + MercuryBlocks.EnumBlockMercury.byMetadata(metadata).getName();
    }
    
}
