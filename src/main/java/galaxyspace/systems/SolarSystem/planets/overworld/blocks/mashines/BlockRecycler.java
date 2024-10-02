package galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines;

import java.util.List;
import java.util.Random;

import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.items.GSItemBlockDesc.IBlockShiftDesc;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityRecycler;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.blocks.BlockTileGC;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseUniversalElectrical;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockRecycler extends BlockTileGC implements IBlockShiftDesc
{
    private IIcon iconMachineSide;
    private IIcon iconOutput;
    private IIcon iconInput;
    private IIcon iconCompressor;
    private IIcon iconCompressor1;

    public BlockRecycler()
    {
        super(GCBlocks.machine);
        this.setBlockName("Recycler");
        this.setHardness(1.0F);
        this.setStepSound(Block.soundTypeMetal);
        this.setBlockTextureName(GalacticraftCore.TEXTURE_PREFIX + "machine");
    }

    @Override
    public CreativeTabs getCreativeTabToDisplayOn()
    {
    	return GSCreativeTabs.GSBlocksTab;
    }

    @Override
    public int getRenderType()
    {
        return GalaxySpace.proxy.getBlockRender(this);
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine");
        this.iconInput = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_input");
        this.iconOutput = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/machine_gelium_output");
        this.iconMachineSide = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_side");

        this.iconCompressor = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/machine_recycler");
        this.iconCompressor1 = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/machine_recycler_2");
    }

    @Override
    public void randomDisplayTick(World par1World, int x, int y, int z, Random par5Random)
    {
        TileEntity tile = par1World.getTileEntity(x, y, z);
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        return this.getIcon(side, world.getBlockMetadata(x, y, z));
    }

    @Override
    public IIcon getIcon(int side, int metadata)
    {
        if (side == 1 || side == 0)
        {
            return this.blockIcon;
        }

        if (metadata == 0 && side == 4 || metadata == 1 && side == 5 || metadata == 2 && side == 3 || metadata == 3 && side == 2)
        {
        	return this.iconCompressor;
        }
        
        if (metadata == 0 && side == 5 || metadata == 1 && side == 4 || metadata == 2 && side == 2 || metadata == 3 && side == 3)
        {
        	return this.iconCompressor1;
        }
        
        if (side == ForgeDirection.getOrientation(metadata + 2).ordinal())
        {
            return this.iconInput;
        }
        
        return this.iconOutput;
    }

    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack)
    {
        int metadata = world.getBlockMetadata(x, y, z);

        int angle = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        int change = 0;

        switch (angle)
        {
        case 0:
            change = 3;
            break;
        case 1:
            change = 1;
            break;
        case 2:
            change = 2;
            break;
        case 3:
            change = 0;
            break;
        }

        world.setBlockMetadataWithNotify(x, y, z, (metadata & 12) + change, 3);
    }

    @Override
    public boolean onUseWrench(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ)
    {
        int metadata = par1World.getBlockMetadata(x, y, z);
        int original = metadata & 3;
        int change = 0;

        // Re-orient the block
        switch (original)
        {
        case 0:
            change = 3;
            break;
        case 3:
            change = 1;
            break;
        case 1:
            change = 2;
            break;
        case 2:
            change = 0;
            break;
        }


        TileEntity te = par1World.getTileEntity(x, y, z);
        if (te instanceof TileBaseUniversalElectrical)
        {
            ((TileBaseUniversalElectrical) te).updateFacing();
        }
        

        par1World.setBlockMetadataWithNotify(x, y, z, (metadata & 12) + change, 3);
        return true;
    }

    /**
     * Called when the block is right clicked by the player
     */
    @Override
    public boolean onMachineActivated(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ)
    {
        int metadata = par1World.getBlockMetadata(x, y, z);

        if (!par1World.isRemote)
        {

        	par5EntityPlayer.openGui(GalaxySpace.instance, -1, par1World, x, y, z);
        	return true;
            
        }

        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        return new TileEntityRecycler();
         
    }

    public ItemStack getCompressor()
    {
        return new ItemStack(this, 1, 0);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
         par3List.add(this.getCompressor());
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
    {
        int metadata = this.getDamageValue(world, x, y, z);

        return new ItemStack(this, 1, metadata);
    }

	@Override
	public String getDescription(int meta) {
		return null;
	}
	
    @Override
    public String getShiftDescription(int meta)
    {
    	return GCCoreUtil.translate("tile.Recycler.desc");
    }

    @Override
    public boolean showDescription(int meta)
    {
        return true;
    }


}

