package galaxyspace.systems.SolarSystem.planets.overworld.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.items.GSItemBlockDesc.IBlockShiftDesc;
import galaxyspace.core.util.GSCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFutureLamp extends Block implements IBlockShiftDesc{
	
	@SideOnly(Side.CLIENT)
    public static IIcon texture, lights;

	public BlockFutureLamp() {
		super(Material.iron);
		this.setBlockName("SpaceLamp");
		this.setBlockTextureName(GalaxySpace.ASSET_PREFIX + ":" + "overworld/futurelamp");
		this.setLightOpacity(0);
		this.setStepSound(soundTypeGlass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTabToDisplayOn()
	{
	    return GSCreativeTabs.GSBlocksTab;
	}
	  
	@Override
	public IIcon getIcon(int side, int meta) {
		return texture;
	}
	
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
    	texture = reg.registerIcon(this.getTextureName());
    	lights = reg.registerIcon(this.getTextureName() + "_lights");
    }
	
    @Override
	public void setBlockBoundsForItemRender() {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
    
    @Override
	public boolean renderAsNormalBlock() {
    	return false;
	}
    
    @Override
    public int damageDropped(int metadata) {
    	return metadata;
    }
    
    @Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is) {
		world.setBlockMetadataWithNotify(x, y, z, is.getItemDamage(), 3);
	}
    
    @Override
	public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list)
	{
		for (int i = 0; i < 8; ++i)
		{
			list.add(new ItemStack(block, 1, i));
		}
	}
    
	@Override
	public int getRenderType() {
		return GalaxySpace.proxy.getBlockRender(this);
	}
	
	@Override
	public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {		
		return true;
	}

	@Override
	public boolean canProvidePower()
    {
        return true;
    }
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b) {
	}

	@Override
	public String getShiftDescription(int meta) {
		return null;
	}

	@Override
	public String getDescription(int meta) {
		return "Work In Process (WIP)";
	}

	@Override
	public boolean showDescription(int meta) {
		return true;
	}
	
}
