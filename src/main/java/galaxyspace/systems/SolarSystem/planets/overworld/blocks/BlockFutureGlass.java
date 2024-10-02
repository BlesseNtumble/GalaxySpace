package galaxyspace.systems.SolarSystem.planets.overworld.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockFutureGlass extends BlockGlass implements ITerraformableBlock
{

	protected IIcon textures;
	
    public BlockFutureGlass()
    {
        super(Material.glass, false);
        this.setBlockName("FutureGlass_None");
        this.setHardness(2.0F);
        this.setStepSound(soundTypeGlass);
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
     
    public boolean renderAsNormalBlock()
    {
        return false;
    }
        
    @Override
    public boolean isNormalCube() {
    	return true;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return GSCreativeTabs.GSBlocksTab;
    }


	@Override
	public boolean isTerraformable(World world, int x, int y, int z) {
		return false;
	}
	
    @Override
    public void registerBlockIcons(IIconRegister register) {
       	this.textures = register.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockfutureglass/futureglass_none");
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {

       return this.textures;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 1;
	}
    
}