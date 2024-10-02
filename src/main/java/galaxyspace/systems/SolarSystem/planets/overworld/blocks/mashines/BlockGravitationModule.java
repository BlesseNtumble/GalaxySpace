package galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItemBlockDesc.IBlockShiftDesc;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityGravitationModule;
import micdoodle8.mods.galacticraft.core.blocks.BlockTileGC;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockGravitationModule extends BlockTileGC implements IBlockShiftDesc {

    protected IIcon BlockIconBottom;
    protected IIcon BlockIconSide;
    protected IIcon BlockIconTop;
    protected IIcon BlockIconTopOn;
    
	public BlockGravitationModule() {		
		super(Material.iron);

		this.setBlockName("GravitationModule");
		this.setHardness(4.0F);
		this.setHarvestLevel("pickaxe", 2);
		this.setStepSound(soundTypeMetal);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
		
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        BlockIconBottom = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/gravitation_bottom");
        BlockIconSide = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/gravitation_side");
        BlockIconTop = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/gravitation_top_0");
        BlockIconTopOn = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/gravitation_top_1");
        
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        
        if (side == 0) { return BlockIconBottom; }
        else if(side == 1) { return meta <= 4 ? BlockIconTopOn : BlockIconTop; }
        else { return BlockIconSide; }        
    }
    
	@Override
	public CreativeTabs getCreativeTabToDisplayOn()
	{
	   	return GSCreativeTabs.GSBlocksTab;
	}
	 
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityGravitationModule();
	}
	
	@Override
	public boolean onMachineActivated(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ)
	{
		int metadata = par1World.getBlockMetadata(x, y, z);

	    if (!par1World.isRemote)
	    {
	    	par5EntityPlayer.openGui(GalaxySpace.instance, -1, par1World, x, y, z);
	    	return true;	            
	    }

	    return true;
	}
	/*
	@Override
	public int getRenderType() {    //����������� -1
	       return -1;
	}
	*/
	@Override
	public boolean isOpaqueCube() {return false;}
	
	@Override
	public boolean renderAsNormalBlock() {return false;}
	 
	@Override
	public String getShiftDescription(int meta) {
		return GCCoreUtil.translate("tile.GravitationModule.desc");
	}

	@Override
	public String getDescription(int meta) {
		return null;
	}

	@Override
	public boolean showDescription(int meta) {
		return true;
	}
	
    @Override
    public Item getItemDropped(int meta, Random random, int par3)
    {
    	return Item.getItemFromBlock(GSBlocks.GravitationModule);
    }
}