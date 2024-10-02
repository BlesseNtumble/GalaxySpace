package galaxyspace.systems.SolarSystem.moons.europa.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
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
import net.minecraft.world.World;

public class EuropaBlocks extends Block implements ITerraformableBlock
{
	public static String[] metadata = new String[] {
		"EuropaGrunt",
		"EuropaStone",
		"EuropaBrownIce",
		"EuropaEmeraldOre",
		"EuropaSiliconOre",
		"EuropaAluminumOre"
	};
	
	protected IIcon[] textures = new IIcon[this.metadata.length];

    public EuropaBlocks()
    {
        super(Material.rock);
        this.setBlockName("EuropaBlocks");
        this.setHardness(1.0F);
        this.setStepSound(soundTypeStone);
        this.setHarvestLevel("pickaxe", 3);
        this.setBlockTextureName("dirt");
        //this.setBlockTextureName(GalaxySpace.ASSET_PREFIX + ":" + "europa/europagrunt");
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
		return true;
	}
    
    @Override
    public Item getItemDropped(int meta, Random random, int par3)
    {
    	return meta == 3 ? Items.emerald
    			: meta == 4 ? GCItems.basicItem		
    			: Item.getItemFromBlock(this);
    }
    
    @Override
    public int damageDropped(int metadata) {    	
    	if(metadata == 3) return 0;
    	if(metadata == 4) return 2;
        return metadata;
    }
    
	@Override
	public int quantityDropped(int meta, int fortune, Random random) {
		int qty;
		switch (meta) {		
		case 3: // emerald
			qty = 1;
			if (fortune > 0)
				qty += random.nextInt(fortune);
			break;
		case 4:
			qty = 1 + random.nextInt(1);
			if (fortune > 0)
				qty += random.nextInt(fortune);
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
	public void registerBlockIcons(IIconRegister iconRegister)
	{
	   	super.registerBlockIcons(iconRegister);
		for(int i = 0; i < this.metadata.length; i++)
	   		this.textures[i] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/europa/" + this.metadata[i].toLowerCase());


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
}