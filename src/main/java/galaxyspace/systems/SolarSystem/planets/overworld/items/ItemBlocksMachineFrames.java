package galaxyspace.systems.SolarSystem.planets.overworld.items;

import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockMachineFrames;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlocksMachineFrames extends ItemBlock 
{
    public ItemBlocksMachineFrames(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack is) {
        int metadata = is.getItemDamage();
        if (metadata >= 0 && metadata < BlockMachineFrames.metadata.length) {
            return "tile." + BlockMachineFrames.metadata[metadata];
        }
        return super.getUnlocalizedName();
    }
    
}
