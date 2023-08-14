package galaxyspace.core.prefab.blocks;

import galaxyspace.core.util.GSCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockTrapDoorGS extends BlockTrapDoor {

    public BlockTrapDoorGS(String name)
    {
        super(Material.WOOD);
        this.setTranslationKey(name);
        this.setSoundType(SoundType.WOOD);
        this.setHardness(3.0F);
    }

    @Override
    public Block setTranslationKey(String name)
    {
        return super.setTranslationKey(name);
    }

    @Override
    public CreativeTabs getCreativeTab()
    {
        return GSCreativeTabs.GSBlocksTab;
    }

}
