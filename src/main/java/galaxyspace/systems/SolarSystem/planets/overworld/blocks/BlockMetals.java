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

public class BlockMetals extends Block implements ITerraformableBlock
{
    public static String[] metadata = new String[] {		
		"Cobalt",
		"Magnesium",		
		"Nickel"		
	};
    
    protected IIcon[] BlockIconSide = new IIcon[this.metadata.length];

    public BlockMetals()
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
    	
        BlockIconSide[0] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blocksmetals/lead");
      
        BlockIconSide[1] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blocksmetals/adm");
       
        BlockIconSide[2] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blocksmetals/cbt");
        
        BlockIconSide[3] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blocksmetals/mgn");
     
        BlockIconSide[4] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blocksmetals/mtr");
      
        BlockIconSide[5] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blocksmetals/nkl");
      
        BlockIconSide[6] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blocksmetals/orh");
       
        BlockIconSide[7] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blocksmetals/plat");
       
        BlockIconSide[8] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blocksmetals/wrm");

        BlockIconSide[9] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blocksmetals/drlm");

    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
    	return BlockIconSide[meta]; 
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