package galaxyspace.systems.VegaSystem.planets.vegaB.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;

public class VegaBBlockSubGrunt extends Block implements ITerraformableBlock
{

    public VegaBBlockSubGrunt()
    {
        super(Material.rock);
        this.setBlockName("VegaBSubGrunt");
        this.setHardness(3.0F);
        this.setStepSound(soundTypeStone);
        this.setHarvestLevel("pickaxe", 3);
        this.setBlockTextureName(GalaxySpace.ASSET_PREFIX + ":" + "vegasystem/vegaB/subgrunt");
    }
    
 
    @Override
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return GSCreativeTabs.GSBlocksTab;
    }
    
    @Override
	public boolean isTerraformable(World world, int x, int y, int z) {
		return true;
	}
    
}