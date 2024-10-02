package galaxyspace.systems.SolarSystem.moons.enceladus.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.api.block.IDetectableResource;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class EnceladusBlocks extends Block implements ITerraformableBlock, IDetectableResource
{
	public static String[] metadata = new String[] {
		"EnceladusSnow",
		"EnceladusGrunt",
		"EnceladusCoalOre"
	};
	
	protected IIcon[] textures = new IIcon[this.metadata.length];
	
    public EnceladusBlocks()
    {
        super(Material.ground);
        this.setBlockName("EnceladusBlocks");
        this.setHardness(0.5F);
        this.setHarvestLevel("shovel", 1, 0);
        this.setHarvestLevel("pickaxe", 1, 1);
        this.setHarvestLevel("pickaxe", 2, 2);
        this.setBlockTextureName("dirt");

    }
    
    @Override
    public Block setStepSound(Block.SoundType p_149672_1_)
    {
        this.stepSound = this.metadata.length == 1 ? soundTypeSnow : soundTypeStone;
        return this;
    }

 
    @Override
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return GSCreativeTabs.GSBlocksTab;
    }
    
    @Override
    public int getDamageValue(World world, int x, int y, int z)
    {    	
        return world.getBlockMetadata(x, y, z);
    }
    
    @Override
	public boolean isTerraformable(World world, int x, int y, int z) {
		return false;
	}
    
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is) {
        world.setBlockMetadataWithNotify(x, y, z, is.getItemDamage(), 3);
    }    
    
    @Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
	   	super.registerBlockIcons(iconRegister);
		//this.textures[0] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/enceladus/enceladussnow");
		//this.textures[1] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/enceladus/enceladusgrunt");
	 	for(int i = 0; i < this.metadata.length; i++)
	   		this.textures[i] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/enceladus/" + this.metadata[i].toLowerCase());
		
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
    public Item getItemDropped(int meta, Random random, int par3)
    {
    	return meta == 2 ? Items.coal : Item.getItemFromBlock(this);
    }
    
    @Override
    public int quantityDropped(int meta, int fortune, Random random)
    {
    	int qty;
    	switch (meta) {
    	case 2:		// coal
    		qty =1;
    		if(fortune > 0 ) qty += random.nextInt(fortune);
    		break;
    	default:
    		qty = 1;
    	}
        return qty;
    }

	@Override
	public boolean isValueable(int metadata) {
		if(metadata == 2) return true;
		return false;
	}
	
    @Override
    public int damageDropped(int metadata) {
    	if(metadata == 2) return 0;
        return metadata;
    }
}
