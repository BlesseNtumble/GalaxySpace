package galaxyspace.systems.SolarSystem.moons.ganymede.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.SolarSystem.moons.ganymede.blocks.GanymedeBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksGanymede extends GSItemBlockDesc 
{
    public ItemBlocksGanymede(Block block) {
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

        return "tile." + GanymedeBlocks.EnumGanymedeBlocks.byMetadata(metadata).getName();
    }    

}
