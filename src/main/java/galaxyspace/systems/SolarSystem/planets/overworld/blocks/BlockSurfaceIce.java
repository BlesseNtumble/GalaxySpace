package galaxyspace.systems.SolarSystem.planets.overworld.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockSurfaceIce extends Block{

	public BlockSurfaceIce() {
		super(Material.packedIce);
		this.slipperiness = 0.98F;
		this.setStepSound(soundTypeGlass);
		this.setHardness(1.0F);
		this.setBlockName("SurfaceIce");
		this.setBlockTextureName(GalaxySpace.ASSET_PREFIX + ":" + "overworld/surfaceice");		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTabToDisplayOn()
	{
		return GSCreativeTabs.GSBlocksTab;
	}
	    

}
