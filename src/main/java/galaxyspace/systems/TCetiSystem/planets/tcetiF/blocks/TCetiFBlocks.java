package galaxyspace.systems.TCetiSystem.planets.tcetiF.blocks;

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

public class TCetiFBlocks extends Block implements ITerraformableBlock{
	
	public static String[] metadata = new String[] {
		"TCetiFGrunt",
		"TCetiFSubGrunt",
		"TCetiFStone"
	};
	
	protected IIcon[] textures = new IIcon[this.metadata.length];
	
	
    public TCetiFBlocks()
    {
        super(Material.sand);
        this.setBlockName("TCetiFBlocks");
        this.setHardness(2.0F);
        this.setHarvestLevel("shovel", 1);
        this.setBlockTextureName("dirt");
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
	public void registerBlockIcons(IIconRegister iconRegister)
	{
	   	super.registerBlockIcons(iconRegister);
		this.textures[0] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "taucetisystem/tcetif/grunt");
		this.textures[1] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "taucetisystem/tcetif/subgrunt");
		this.textures[2] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "taucetisystem/tcetif/stone");
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

    @Override
	public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list)
	{
		for (int i = 0; i < this.textures.length; ++i)
		{
			list.add(new ItemStack(block, 1, i));
		}
	}

}
