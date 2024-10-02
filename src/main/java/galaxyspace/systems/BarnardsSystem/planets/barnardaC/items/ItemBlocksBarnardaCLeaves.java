package galaxyspace.systems.BarnardsSystem.planets.barnardaC.items;

import galaxyspace.systems.BarnardsSystem.planets.barnardaC.blocks.Barnarda_C_Leaves;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlocksBarnardaCLeaves extends ItemBlock 
{
    public ItemBlocksBarnardaCLeaves(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }
    
    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
        if (metadata >= 0 && metadata < Barnarda_C_Leaves.metadata.length) {
            return super.getUnlocalizedName() + "." + Barnarda_C_Leaves.metadata[metadata];
        }
        return super.getUnlocalizedName();
    }
}
