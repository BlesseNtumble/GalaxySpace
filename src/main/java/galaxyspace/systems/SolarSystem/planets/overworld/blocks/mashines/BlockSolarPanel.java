package galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.configs.GSConfigEnergy;
import galaxyspace.core.registers.items.GSItemBlockDesc.IBlockShiftDesc;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntitySolarPanel;
import micdoodle8.mods.galacticraft.api.block.IPartialSealableBlock;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.blocks.BlockTileGC;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseUniversalElectrical;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSolarPanel extends BlockTileGC implements IBlockShiftDesc, IPartialSealableBlock
{
    public static final int HYBRID_METADATA = 0;
    public static final int ULTRA_METADATA = 4;

    public static String[] names = { "hybrid", "ultra" };

    private IIcon[] icons = new IIcon[6];

    public BlockSolarPanel(String assetName)
    {
        super(Material.iron);
        this.setHardness(1.0F);
        this.setStepSound(Block.soundTypeMetal);
        this.setBlockTextureName(GalacticraftCore.TEXTURE_PREFIX + assetName + 1);
        this.setBlockName(assetName);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, BlockSolarPanel.HYBRID_METADATA));
       // par3List.add(new ItemStack(par1, 1, BlockGybridSolarPanel.ULTRA_METADATA));
    }

    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.icons[0] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/solar_hybrid_0");
        this.icons[1] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/solar_hybrid_1");
        //this.icons[2] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/solar_ultra_0");
       // this.icons[3] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/solar_ultra_1");
        this.icons[4] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/machine");
        this.icons[5] = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/machine_output");
        this.blockIcon = this.icons[0];
    }

    @Override
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return GSCreativeTabs.GSBlocksTab;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
       /* if (meta >= BlockSolarPanel.ULTRA_METADATA)
        {
            int shiftedMeta = meta -= BlockSolarPanel.ULTRA_METADATA;

            if (side == ForgeDirection.getOrientation(shiftedMeta + 2).getOpposite().ordinal())
            {
                return this.icons[5];
            }
            else if (side == ForgeDirection.UP.ordinal())
            {
                return this.icons[2];
            }
            else if (side == ForgeDirection.DOWN.ordinal())
            {
                return this.icons[4];
            }
            else
            {
                return this.icons[3];
            }
        }
        else*/ if (meta >= BlockSolarPanel.HYBRID_METADATA)
        {
            int shiftedMeta = meta -= BlockSolarPanel.HYBRID_METADATA;

            if (side == ForgeDirection.getOrientation(shiftedMeta + 2).getOpposite().ordinal())
            {
                return this.icons[5];
            }
            else if (side == ForgeDirection.UP.ordinal())
            {
                return this.icons[0];
            }
            else if (side == ForgeDirection.DOWN.ordinal())
            {
                return this.icons[4];
            }
            else
            {
                return this.icons[1];
            }
        }

        return this.blockIcon;
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, int x1, int y1, int z1, int side)
    {
        for (int y = 1; y <= 3; y++)
        {
            for (int x = -1; x <= 1; x++)
            {
                for (int z = -1; z <= 1; z++)
                {
                	if(y1 >= 250) return false;
                	
                    Block block = world.getBlock(x1 + (y == 3 ? x : 0), y1 + y, z1 + (y == 3 ? z : 0));

                    if (block.getMaterial() != Material.air && !block.isReplaceable(world, x1 + x, y1 + y, z1 + z))
                    {
                        return false;
                    }
                }
            }
        }

        return new BlockVec3(x1, y1, z1).newVecSide(side ^ 1).getBlock(world) != GCBlocks.fakeBlock;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack)
    {
        int metadata = world.getBlockMetadata(x, y, z);

        int angle = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        int change = 0;

        switch (angle)
        {
        case 0:
            change = 1;
            break;
        case 1:
            change = 2;
            break;
        case 2:
            change = 0;
            break;
        case 3:
            change = 3;
            break;
        }

        if (metadata >= BlockSolarPanel.ULTRA_METADATA)
        {
            world.setBlockMetadataWithNotify(x, y, z, BlockSolarPanel.ULTRA_METADATA + change, 3);
        }
        else
        {
            world.setBlockMetadataWithNotify(x, y, z, BlockSolarPanel.HYBRID_METADATA + change, 3);
        }

        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof TileEntitySolarPanel)
        {
            ((TileEntitySolarPanel) tile).onCreate(new BlockVec3(x, y, z));
        }
    }

    @Override
    public void breakBlock(World var1, int var2, int var3, int var4, Block var5, int var6)
    {
        final TileEntity var9 = var1.getTileEntity(var2, var3, var4);

        if (var9 instanceof TileEntitySolarPanel)
        {
            ((TileEntitySolarPanel) var9).onDestroy(var9);
        }

        super.breakBlock(var1, var2, var3, var4, var5, var6);
    }

    @Override
    public boolean onUseWrench(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ)
    {
        int metadata = par1World.getBlockMetadata(x, y, z);
        int original = metadata;

        int change = 0;

        if (metadata >= BlockSolarPanel.ULTRA_METADATA)
        {
            original -= BlockSolarPanel.ULTRA_METADATA;
        }

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

        if (metadata >= BlockSolarPanel.ULTRA_METADATA)
        {
            change += BlockSolarPanel.ULTRA_METADATA;
        }

        TileEntity te = par1World.getTileEntity(x, y, z);
        if (te instanceof TileBaseUniversalElectrical)
        {
            ((TileBaseUniversalElectrical) te).updateFacing();
        }

        par1World.setBlockMetadataWithNotify(x, y, z, change, 3);
        return true;
    }

    @Override
    public boolean onMachineActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ)
    {
        entityPlayer.openGui(GalaxySpace.instance, -1, world, x, y, z);
        return true;
    }

    @Override
    public int damageDropped(int metadata)
    {
        if (metadata >= BlockSolarPanel.ULTRA_METADATA)
        {
            return BlockSolarPanel.ULTRA_METADATA;
        }
        else
        {
            return BlockSolarPanel.HYBRID_METADATA;
        }
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
    {
        int metadata = this.getDamageValue(world, x, y, z);

        return new ItemStack(this, 1, metadata);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {

       return new TileEntitySolarPanel();
        
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public String getShiftDescription(int meta)
    {
        switch (meta)
        {
        case HYBRID_METADATA:
            return GCCoreUtil.translate("tile.SolarHybrid.desc");
        case ULTRA_METADATA:
            return GCCoreUtil.translate("tile.solarUltra.desc");
        }
        return "";
    }

    @Override
    public boolean showDescription(int meta)
    {
        return true;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean isSealed(World world, int x, int y, int z, ForgeDirection direction)
    {
        return true;
    }

	@Override
	public String getDescription(int meta) {
		return GCCoreUtil.translate("tile.coefficient.desc") + ": x" + GSConfigEnergy.coefficientSolarPanel;
	}
}
