package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.items;

import galaxyspace.core.prefab.items.GSItemBlockDesc;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Falling_Blocks.EnumFallingBlockBarnardaC;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlocksBarnarda_C_Falling extends GSItemBlockDesc
{
    public ItemBlocksBarnarda_C_Falling(Block block) {
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

        if(metadata > EnumFallingBlockBarnardaC.values().length) 
        	return "tile." + EnumFallingBlockBarnardaC.byMetadata(0).getName();
        
        return "tile." + EnumFallingBlockBarnardaC.byMetadata(metadata).getName();
    }    
}

