package galaxyspace.systems.BarnardsSystem.planets.barnardaC.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Barnarda_C_Log extends BlockLog {

	@SideOnly(Side.CLIENT)
    protected IIcon BlockIconSide, BlockIconTop;
	
	public Barnarda_C_Log()
    {
        super();
        this.setBlockName("BarnardaCLog");
        this.setHardness(1.0F);
        this.setStepSound(soundTypeWood);
        this.setHarvestLevel("axe", 0);
    }

	@Override
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTabToDisplayOn() {
		return GSCreativeTabs.GSBlocksTab;
	}
		
	@Override
	public int damageDropped(int metadata) {
		return 0;	 
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        BlockIconSide = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "barnardssystem/barnardaC/log_oak");
        BlockIconTop = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "barnardssystem/barnardaC/log_oak_top");
    }
	
	@SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int par1int, int par2int)
    {       
		int k = par2int & 12;
		int l = par2int & 3;
		
        /*if (par1int == 0 || par1int == 1 ) { return BlockIconTop; }
        else { return BlockIconSide; }*/
        
        return k == 0 && (par1int == 1 || par1int == 0) ? this.BlockIconTop : (k == 4 && (par1int == 5 || par1int == 4) ? this.BlockIconTop : (k == 8 && (par1int == 2 || par1int == 3) ? this.BlockIconTop : this.BlockIconSide));
        
    }
	
	
	@Override
	public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z)
	{
		return true;
	}

}
