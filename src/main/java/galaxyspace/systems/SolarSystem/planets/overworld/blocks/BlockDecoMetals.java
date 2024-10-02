package galaxyspace.systems.SolarSystem.planets.overworld.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockDecoMetals extends Block implements ITerraformableBlock
{
    public static String[] metadata = new String[] {		
		"DecoCobaltum",
		"DecoMagnesium",		
		"DecoNickel",		
		"DecoCopper"
	};
    
    protected IIcon[] BlockIconSide = new IIcon[this.metadata.length];
    protected IIcon[] BlockIconTop = new IIcon[this.metadata.length];
    protected IIcon[] BlockIconBottom = new IIcon[this.metadata.length];

    public BlockDecoMetals()
    {
        super(Material.rock);
        this.setBlockName("MetalsBlocks");
        this.setHardness(2.0F);
        this.setStepSound(soundTypeStone);
        this.setHarvestLevel("pickaxe", 2);
        this.setBlockTextureName("dirt");
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
    	super.registerBlockIcons(par1IconRegister);

        BlockIconSide[0] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blocksdecometals/deco_cbt_1");
        BlockIconTop[0] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blocksdecometals/deco_cbt_2");
        BlockIconBottom[0] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blocksdecometals/deco_cbt_2");
        
        BlockIconSide[1] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blocksdecometals/deco_mgn_1");
        BlockIconTop[1] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blocksdecometals/deco_mgn_2");
        BlockIconBottom[1] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blocksdecometals/deco_mgn_2");

        BlockIconSide[2] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blocksdecometals/deco_nkl_1");
        BlockIconTop[2] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blocksdecometals/deco_nkl_2");
        BlockIconBottom[2] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blocksdecometals/deco_nkl_2");

        BlockIconSide[3] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blocksdecometals/deco_copper_1");
        BlockIconTop[3] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blocksdecometals/deco_copper_2");
        BlockIconBottom[3] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blocksdecometals/deco_copper_2");
   
    }
    
    @Override
    public int getDamageValue(World world, int x, int y, int z)
    {    	
        return world.getBlockMetadata(x, y, z);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        if (meta < 0 || meta >= this.BlockIconSide.length)
        {
        	 if (side == 1 ) { return BlockIconTop[0]; }
             else if (side == 0) { return BlockIconBottom[0]; }
             else if (side != meta) { return BlockIconSide[0]; }
             else { return BlockIconSide[0]; }
        }

        if (side == 1 ) { return BlockIconTop[meta]; }
        else if (side == 0) { return BlockIconBottom[meta]; }
        else if (side != meta) { return BlockIconSide[meta]; }
        else { return BlockIconSide[meta]; }
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
		for (int i = 0; i < this.BlockIconSide.length; ++i)
		{
			list.add(new ItemStack(block, 1, i));
		}
	}
}