package galaxyspace.systems.SolarSystem.planets.overworld.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItemBlockDesc.IBlockShiftDesc;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityAdvLandingPadSingle;
import micdoodle8.mods.galacticraft.api.block.IPartialSealableBlock;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.blocks.BlockAdvancedTile;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockAdvLandingPad extends BlockAdvancedTile implements IPartialSealableBlock, IBlockShiftDesc
{
    private IIcon[] icons = new IIcon[3];

    public BlockAdvLandingPad(String assetName)
    {
        super(Material.iron);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.2F, 1.0F);
        this.setHardness(1.0F);
        this.setResistance(10.0F);
        this.setStepSound(Block.soundTypeMetal);
        this.setBlockTextureName(GalacticraftCore.TEXTURE_PREFIX + assetName);
        this.setBlockName(assetName);
    }

    @Override
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return GSCreativeTabs.GSBlocksTab;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < 1; i++)
        {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.icons[0] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/adv_launch_pad");
        this.blockIcon = par1IconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "launch_pad");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2)
    {
        if (par2 < 0 || par2 > this.icons.length)
        {
            return this.blockIcon;
        }

        return this.icons[par2];
    }

    @Override
    public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5)
    {
        final Block id = GSBlocks.AdvLandingPad;

        if (par1World.getBlock(par2 + 1, par3, par4) == id 
        		&& par1World.getBlock(par2 + 2, par3, par4) == id 
        		&& par1World.getBlock(par2 + 3, par3, par4) == id 
        		&& par1World.getBlock(par2 + 4, par3, par4) == id
        		&& par1World.getBlock(par2 + 5, par3, par4) == id)
        {
            return false;
        }

        if (par1World.getBlock(par2 - 1, par3, par4) == id 
        		&& par1World.getBlock(par2 - 2, par3, par4) == id 
        		&& par1World.getBlock(par2 - 3, par3, par4) == id 
        		&& par1World.getBlock(par2 - 4, par3, par4) == id
        		&& par1World.getBlock(par2 - 5, par3, par4) == id)
        {
            return false;
        }

        if (par1World.getBlock(par2, par3, par4 + 1) == id 
        		&& par1World.getBlock(par2, par3, par4 + 2) == id 
        		&& par1World.getBlock(par2, par3, par4 + 3) == id 
        		&& par1World.getBlock(par2, par3, par4 + 4) == id
        		&& par1World.getBlock(par2, par3, par4 + 5) == id)
        {
            return false;
        }

        if (par1World.getBlock(par2, par3, par4 - 1) == id 
        		&& par1World.getBlock(par2, par3, par4 - 2) == id 
        		&& par1World.getBlock(par2, par3, par4 - 3) == id 
        		&& par1World.getBlock(par2, par3, par4 - 4) == id
        		&& par1World.getBlock(par2, par3, par4 - 5) == id)
        {
            return false;
        }

        if (par1World.getBlock(par2, par3 - 1, par4) == GSBlocks.AdvLandingPad && par5 == 1)
        {
            return false;
        }
        else
        {
            return this.canPlaceBlockAt(par1World, par2, par3, par4);
        }
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        if (world.isRemote)
        	return null;

        switch (metadata)
        {
        case 0:
        	return new TileEntityAdvLandingPadSingle();
        // case 2:
        // return new GCCoreTileEntityCargoPadSingle();
        default:
            return null;
        }
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
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return null;
    }

    @Override
    public boolean isSealed(World world, int x, int y, int z, ForgeDirection direction)
    {
        return direction == ForgeDirection.UP;
    }

    @Override
    public int damageDropped(int meta)
    {
        return meta;
    }

    @Override
    public String getShiftDescription(int meta)
    {        
    	return GCCoreUtil.translate(this.getUnlocalizedName() + ".desc");       
    }

    @Override
    public boolean showDescription(int meta)
    {
        return true;
    }

	@Override
	public String getDescription(int meta) {
		return null;
	}
}
