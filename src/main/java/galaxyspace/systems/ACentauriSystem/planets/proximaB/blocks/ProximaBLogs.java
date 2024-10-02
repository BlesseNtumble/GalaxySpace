package galaxyspace.systems.ACentauriSystem.planets.proximaB.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;

public class ProximaBLogs extends BlockLog {

    protected IIcon[] BlockIconSide = new IIcon[2];
	
	private String name;
	
	public ProximaBLogs(String name)
    {
        super();
        this.name = name;
        this.setBlockName(name);
        this.setHardness(1.0F);
        this.setStepSound(soundTypeWood);
        this.setHarvestLevel("axe", 1);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTabToDisplayOn() {
		return GSCreativeTabs.GSBlocksTab;
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        BlockIconSide[0] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "proximasystem/proxima_b/proxima_b_" + this.name.toLowerCase() + "_top");
        BlockIconSide[1] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "proximasystem/proxima_b/proxima_b_" + this.name.toLowerCase() + "_side");
        
    }

	@SideOnly(Side.CLIENT)
	@Override
    protected IIcon getSideIcon(int p_150163_1_)
    {
        return this.BlockIconSide[1];
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected IIcon getTopIcon(int p_150161_1_)
    {
        return this.BlockIconSide[0];
    }
}
