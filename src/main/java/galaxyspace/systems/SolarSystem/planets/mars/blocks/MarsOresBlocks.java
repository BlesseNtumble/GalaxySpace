package galaxyspace.systems.SolarSystem.planets.mars.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MarsOresBlocks extends Block{

	private Random random = new Random();
	public static String[] metadata = new String[] {
		"Diamond",		//0
		"Gold",			//1
		"Coal",			//2
		"Redstone",		//3
		"Silicon",		//4
		"Aluminum"		//5
	};
	
	protected IIcon[] BlockIconSide = new IIcon[this.metadata.length];
	
	public MarsOresBlocks()
    {
        super(Material.rock);
        this.setBlockName("MarsOreBlocks");
        this.setHardness(2.0F);
        this.setStepSound(soundTypeStone);
        this.setHarvestLevel("pickaxe", 2, 0);
        this.setHarvestLevel("pickaxe", 2, 1);
        this.setHarvestLevel("pickaxe", 0, 2);  
        this.setHarvestLevel("pickaxe", 2, 3);
        this.setHarvestLevel("pickaxe", 2, 4);
        this.setHarvestLevel("pickaxe", 2, 5);
        this.setBlockTextureName("dirt");
    }

	@SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
    	super.registerBlockIcons(par1IconRegister);
    	
    	for(int i = 0; i < metadata.length; i++)
    	{
    		BlockIconSide[i] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/mars/" + metadata[i]);
    	}
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
    public Item getItemDropped(int meta, Random random, int par3)
    {
    	return meta == 0 ? Items.diamond 
    			: meta == 2 ? Items.coal
    			: meta == 3 ? Items.redstone 
    			: meta == 4 ? GCItems.basicItem 
    			: Item.getItemFromBlock(this);
    }
    
    @Override
    public int damageDropped(int metadata) {
    	if(metadata == 0) return 0;
    	if(metadata == 2) return 0;
    	if(metadata == 3) return 0;
    	if(metadata == 4) return 2;
        return metadata;
    }
    
    @Override
    public int getDamageValue(World world, int x, int y, int z)
    {    	
        return world.getBlockMetadata(x, y, z);
    }
    
    @Override
	public int quantityDropped(int meta, int fortune, Random random)
	{
    	int qty;
		switch (meta) {
		case 0:		// diamond
			qty =1;
			if(fortune >0) qty += random.nextInt(fortune);
			break;
		case 2:		// coal
			qty =1;
			if(fortune >0) qty += random.nextInt(fortune);			  
			break;
		case 3:		// redstone
			  qty =4 + random.nextInt(2);
			  if(fortune >0) qty += random.nextInt(fortune);			  
			  break;
		  case 4:
			  qty =1 + random.nextInt(1);
			  if(fortune >0) qty += random.nextInt(fortune);
		  default:
			  qty = 1;
		  }
		  return qty;		
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
    
    @Override
    public int getExpDrop(IBlockAccess world, int meta, int fortune)
    {
 
    	if (this.getItemDropped(meta, this.random, fortune) != Item.getItemFromBlock(this))
        {
    		return MathHelper.getRandomIntegerInRange(this.random, 3, 5);   
        }
        return 0;
    }
}
