package galaxyspace.systems.TCetiSystem.planets.tcetiF.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.systems.TCetiSystem.core.registers.blocks.TCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class TCetiFBlockDandelions extends Block implements IShearable
{
	public static String[] dandelion = new String[] {
		"TCetiFDandelion1",
		"TCetiFDandelion2",
		"TCetiFDandelion3",
		"TCetiFDandelion4",
		"TCetiFDandelion5",
		"TCetiFDandelion6"
	};
	
	protected IIcon[] textures = new IIcon[this.dandelion.length];
	
    public TCetiFBlockDandelions()    
    {
        super(Material.water);        
        this.setBlockName("TCetiFBlockDandelions");
        this.setStepSound(Block.soundTypeGrass);
        this.setBlockTextureName("dirt");
        
    }
    
    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        return 15;
    }
      
    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_)
    {
        float f = 0.0625F;
        return AxisAlignedBB.getBoundingBox((double)((float)p_149633_2_ + f), (double)p_149633_3_, (double)((float)p_149633_4_ + f), (double)((float)(p_149633_2_ + 1) - f), (double)(p_149633_3_ + 1), (double)((float)(p_149633_4_ + 1) - f));
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }
    
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        this.check(world, x, y, z);
    }
    
    protected final boolean check(World world, int x, int y, int z)
    {
    	if (world.getBlock(x, y - 1, z) == Blocks.air)
    	{
    		this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
            return false;
    	}	  
        else
        {
            return true;
        }
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

		for (int i = 0; i < this.textures.length; ++i)
		{
			this.textures[i] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "taucetisystem/tcetif/dandelion" + i);
		}

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
    public Item getItemDropped(int meta, Random random, int par3)
    {
        switch (meta)
        {
        default:
            return super.getItemDropped(meta, random, par3);
        }
    }
    
    public int quantityDropped(Random random)
    {
        return 0;
    }
    
    @Override
	public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list)
	{
		for (int i = 0; i < this.textures.length; ++i)
		{
			if(i != 1 && i != 2) list.add(new ItemStack(block, 1, i));
		}
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return GSCreativeTabs.GSBlocksTab;
    }
    
    /*
    @Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
	{
		return this.isValidPosition(world, x, y, z, -1);
	}
    
    public boolean isValidPosition(World world, int x, int y, int z, int metadata)
	{
		Block block = world.getBlock(x, y - 1, z);

		if (world.getBlock(x, y - 1, z) == Blocks.air)
		{
			return false;
		}
		
		
		return block == Blocks.sand;
	}
    */
    
    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        if(world.getBlock(x, y - 1, z) == TCBlocks.TCetiEBlocks && world.getBlockMetadata(x, y - 1, z) == 1) return true;
        
        return false;
    }

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
		
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z)));
		return ret;
	}
	
	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }

	@Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

	@Override
    public int getRenderType()
    {
        return 1;
    }

}
