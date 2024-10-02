package galaxyspace.systems.BarnardsSystem.planets.barnardaE.items;

import galaxyspace.systems.BarnardsSystem.planets.barnardaE.blocks.Barnarda_E_Blocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlocksBarnardaEBlocks extends ItemBlock 
{
    public ItemBlocksBarnardaEBlocks(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }
    
    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
        if (metadata >= 0 && metadata < Barnarda_E_Blocks.metadata.length) {
            return super.getUnlocalizedName() + "." + Barnarda_E_Blocks.metadata[metadata];
        }
        return super.getUnlocalizedName();
    }
}
