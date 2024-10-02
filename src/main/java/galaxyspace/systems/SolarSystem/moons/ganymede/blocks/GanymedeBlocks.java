package galaxyspace.systems.SolarSystem.moons.ganymede.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.api.block.IDetectableResource;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class GanymedeBlocks extends Block implements ITerraformableBlock, IDetectableResource
{

	public static String[] metadata = new String[] {
		"GanymedeGrunt",
		"GanymedeSubGrunt",
		"GanymedeMagnesiumOre",
		"GanymedeTitanuimOre"
	};
	
	protected IIcon[] textures = new IIcon[this.metadata.length];
	
    public GanymedeBlocks()
    {
        super(Material.rock);
        this.setBlockName("GanymedeBlocks");
        this.setHardness(3.0F);
        this.setStepSound(soundTypeStone);
        this.setHarvestLevel("pickaxe", 2);
        this.setBlockTextureName("dirt");
        //this.setBlockTextureName(GalaxySpace.ASSET_PREFIX + ":" + "ganymede/ganymedegrunt");
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
    
    @Override
    public int getDamageValue(World world, int x, int y, int z)
    {    	
        return world.getBlockMetadata(x, y, z);
    }
    
    @Override
    public Item getItemDropped(int meta, Random random, int par3)
    {
    	return meta == 3 ? AsteroidsItems.basicItem : Item.getItemFromBlock(this);
    }
    
    @Override
    public int quantityDropped(int meta, int fortune, Random random)
    {
    	if(meta == 3)
    	{
    		if(fortune > 0) return 1 * fortune + random.nextInt(1);
    			
    		return 1 + random.nextInt(1);
    	}
    	
    	return 1;
    }
    @Override
    public int damageDropped(int metadata) {
    	if(metadata == 3) return 4;
        return metadata;
    }
    
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is) {
        world.setBlockMetadataWithNotify(x, y, z, is.getItemDamage(), 3);
    }    
    
    @Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
	   	super.registerBlockIcons(iconRegister);
		for(int i = 0; i < this.metadata.length; i++)
	   		this.textures[i] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/ganymede/" + this.metadata[i].toLowerCase());

	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        if (meta < 0 || meta >= this.textures.length)
        {
            return this.textures[0];
        }

        return this.textures[meta];
    }

    @Override
	public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list)
	{
		for (int i = 0; i < this.textures.length; ++i)
		{
			list.add(new ItemStack(block, 1, i));
		}
	}


	@Override
	public boolean isValueable(int metadata) {
		if(metadata == 2 || metadata == 3) return true;
		return false;
	}
}