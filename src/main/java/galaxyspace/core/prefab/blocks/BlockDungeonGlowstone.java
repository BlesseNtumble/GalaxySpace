package galaxyspace.core.prefab.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockDungeonGlowstone extends Block{

	public static String[] metadata = new String[] {
			"CeresGlowstone",
			"IoGlowstone"
		};
	
	protected IIcon[] textures = new IIcon[this.metadata.length];
	
	public BlockDungeonGlowstone()
    {
        super(Material.glass);
        this.setBlockName("DungeonGlowstone");
        this.setHardness(1.0F);
        this.setStepSound(soundTypeGlass);
        this.setLightLevel(1.0F);
        this.setBlockTextureName("dirt");
    }

	@Override
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTabToDisplayOn() {
		return GSCreativeTabs.GSBlocksTab;
	}
	
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is) {
        world.setBlockMetadataWithNotify(x, y, z, is.getItemDamage(), 3);
    } 

	@Override
    public Item getItemDropped(int meta, Random random, int par3)
    {
    	return GSItems.GlowstoneDusts;
    }
    
    @Override
    public int damageDropped(int metadata) {
        return metadata;
    }
    
    public int quantityDropped(Random random)
    {
        return 2 + random.nextInt(3);
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
	public void registerBlockIcons(IIconRegister iconRegister)
	{
	   	super.registerBlockIcons(iconRegister);
		for(int i = 0; i < this.metadata.length; i++)
	   		this.textures[i] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockdungeonglowstone/" + this.metadata[i].toLowerCase());
	
		
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        if (meta < 0 || meta > this.textures.length)
        {
            return this.textures[0];
        }

        return this.textures[meta];
    }
}
