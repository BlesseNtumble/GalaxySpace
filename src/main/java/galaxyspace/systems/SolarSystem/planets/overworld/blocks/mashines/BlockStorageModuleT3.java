package galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines;

import java.util.List;
import java.util.Random;

import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.items.GSItemBlockDesc.IBlockShiftDesc;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityStorageModule;
import micdoodle8.mods.galacticraft.core.blocks.BlockTileGC;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.tile.IMultiBlock;
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

public class BlockStorageModuleT3 extends BlockTileGC implements IBlockShiftDesc
{
    public static final int STORAGE_MODULE_T3 = 0;
    public static final int STORAGE_MODULE_T4 = 8;

    private IIcon iconMachineT4;
    
    private IIcon iconMachineSide;
    private IIcon iconInput;
    private IIcon iconOutput;

    private IIcon[] iconStorageModuleT3 = new IIcon[17];
    private IIcon[] iconStorageModuleT4 = new IIcon[17];

    private int level = 0;
    public BlockStorageModuleT3(String assetName)
    {
        super(GCBlocks.machine);
        this.setHardness(1.0F);
        this.setStepSound(Block.soundTypeMetal);
        //this.setBlockTextureName(GalacticraftCore.TEXTURE_PREFIX + assetName);
        this.setBlockName(assetName);

    }

    @Override
    public void breakBlock(World var1, int var2, int var3, int var4, Block var5, int var6)
    {
        final TileEntity var9 = var1.getTileEntity(var2, var3, var4);

        if (var9 instanceof IMultiBlock)
        {
            ((IMultiBlock) var9).onDestroy(var9);
        }

        super.breakBlock(var1, var2, var3, var4, var5, var6);
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
        this.blockIcon = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/machine");
        this.iconInput = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/machine_input");
        this.iconOutput = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/machine_output");
        this.iconMachineSide = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/machine_side");
        
        for (int i = 0; i < this.iconStorageModuleT3.length; i++)
        {
        	this.iconStorageModuleT3[i] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/storagemodule/module_t3_" + i);
        }
        /*for (int i = 0; i < this.iconStorageModuleT4.length; i++)
        {
        	this.iconStorageModuleT4[i] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/storagemodule/module_t4_" + i);
        }*/
    }

    @Override
    public void randomDisplayTick(World par1World, int x, int y, int z, Random par5Random)
    {
        TileEntity tile = par1World.getTileEntity(x, y, z);

       
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        int metadata = world.getBlockMetadata(x, y, z);
        int type = metadata & 4;
        int metaside = (metadata & 3) + 2;

        if (type == this.STORAGE_MODULE_T3)
        {
            if (side == 0 || side == 1)
            {
                if (metadata >= 8)
                {
                    return this.blockIcon;
                }
                return this.blockIcon;
            }

            // If it is the front side
            if (side == metaside)
            {
                if (metadata >= 8)
                {
                    return this.iconOutput;
                }
                return this.iconOutput;
            }
            // If it is the back side
            else if (side == (metaside ^ 1))
            {
                if (metadata >= 8)
                {
                    return this.iconInput;
                }
                return this.iconInput;
            }

            TileEntity tile = world.getTileEntity(x, y, z);

            int level = 0;
            if (tile instanceof TileEntityStorageModule)
            {
                level = ((TileEntityStorageModule) tile).scaledEnergyLevel;
            }

            if (metadata >= 8)
            {
                return this.iconStorageModuleT4[level];
            }
            return this.iconStorageModuleT3[level];
        }

        return this.getIcon(side, metadata);
    }

    @Override
    public IIcon getIcon(int side, int metadata)
    {
        int metaside = (metadata & 3) + 2;

        if (side == 0 || side == 1)
        {
            if (metadata >= 8)
            {
                return this.blockIcon;
            }
            return this.blockIcon;
        }
        if ((metadata & 4) == this.STORAGE_MODULE_T3)
        {
            // If it is the front side
            if (side == metaside)
            {
                if (metadata >= 8)
                {
                    return this.iconOutput;
                }
                return this.iconOutput;
            }
            // If it is the back side
            else if (side == (metaside ^ 1))
            {
                if (metadata >= 8)
                {
                    return this.iconInput;
                }
                return this.iconInput;
            }

          /*  if (metadata >= 8)
            {
                return this.iconStorageModuleT4[16];
            }*/
            return this.iconStorageModuleT3[16];
        }

        if (metadata >= 8)
        {
            return this.iconMachineSide;
        }
        return this.iconMachineSide;
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
           /* if (metadata >= BlockGeothermalGenerator.COMPRESSOR_METADATA)
            {
                par5EntityPlayer.openGui(GalaxySpace.instance, -1, par1World, x, y, z);
                return true;
            }
            else
            {*/
                par5EntityPlayer.openGui(GalaxySpace.instance, -1, par1World, x, y, z);
                return true;
            //}
        }

        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        int tier = metadata / 8 + 1;
        return new TileEntityStorageModule(tier);       
    }

    
    public ItemStack getEnergyStorageModule()
    {
        return new ItemStack(this, 1, 0);
    }

    public ItemStack getEnergyStorageCluster()
    {
        return new ItemStack(this, 1, 8 + 0);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(this.getEnergyStorageModule());
        //par3List.add(this.getEnergyStorageCluster());
    }

    @Override
    public int damageDropped(int metadata)
    {
        return metadata & 12;
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
    	return GCCoreUtil.translate("tile.StorageModule.desc");
    }

    @Override
    public boolean showDescription(int meta)
    {
        return true;
    }
}
