package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Leaves.EnumBlockLeaves;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksBarnarda_C_Leaves extends GSItemBlockDesc
{
    public ItemBlocksBarnarda_C_Leaves(Block block) {
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

        if(metadata > EnumBlockLeaves.values().length) 
        	return "tile." + EnumBlockLeaves.byMetadata(0).getName();
        
        return "tile." + EnumBlockLeaves.byMetadata(metadata).getName();
    }    
}
