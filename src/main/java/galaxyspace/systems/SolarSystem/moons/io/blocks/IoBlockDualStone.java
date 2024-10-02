package galaxyspace.systems.SolarSystem.moons.io.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class IoBlockDualStone extends Block implements ITerraformableBlock
{

    @SideOnly(Side.CLIENT)
    protected IIcon BlockIconFront;
    protected IIcon BlockIconSide;
    protected IIcon BlockIconTop;
    protected IIcon BlockIconBottom;
    
    public IoBlockDualStone()
    {
        super(Material.rock);
        this.setBlockName("IODualStone");
        this.setHardness(3.0F);
        this.setStepSound(soundTypeStone);
        this.setHarvestLevel("pickaxe", 3);
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
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        BlockIconFront = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/io/iodualstone_front");
        BlockIconSide = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/io/iodualstone_front");
        BlockIconTop = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/io/ioash");
        BlockIconBottom = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/io/iogrunt");
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int par1int, int par2int)
    {
        if(par1int == 0) { return BlockIconBottom; }
        else if (par1int == 1) { return BlockIconTop; }
        else if (par1int != par2int) { return BlockIconSide; }
        else { return BlockIconFront; }
    }
}