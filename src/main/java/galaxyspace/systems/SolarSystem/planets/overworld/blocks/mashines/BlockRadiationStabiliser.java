package galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.items.GSItemBlockDesc.IBlockShiftDesc;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityRadiationStabiliser;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
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

public class BlockRadiationStabiliser extends BlockTileGC implements IBlockShiftDesc{

	public static final int RADIATION_STABILISER_METADATA = 0;
	
	private IIcon iconMachineSide;
    private IIcon iconOutput;
    private IIcon iconInput;
    private IIcon iconRadiationStabiliser;
    
    public BlockRadiationStabiliser()
    {
        super(GCBlocks.machine);
        this.setBlockName("radiation_stabiliser");
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
        this.iconOutput = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/machine_water_output");
        this.iconMachineSide = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_side");

        this.iconRadiationStabiliser = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/radiation_stabiliser");
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
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        int metadata = world.getBlockMetadata(x, y, z);
        int type = metadata & 4;
        int metaside = (metadata & 3) + 2;
        TileEntity tile = world.getTileEntity(x, y, z);
        
        if (metadata == 0 && side == 4 || metadata == 1 && side == 5 || metadata == 2 && side == 3 || metadata == 3 && side == 2)
        {
           return this.iconRadiationStabiliser;
        }
        
        return this.getIcon(side, world.getBlockMetadata(x, y, z));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int metadata)
    {
        if (side == 1)
        {
            return this.blockIcon;
        }
        
                
        if (metadata >= this.RADIATION_STABILISER_METADATA)
        {
            // If it is the front side
            if (side == metadata + 2)
            {
                return this.iconInput;
            }
            /*
            else if (side == ForgeDirection.getOrientation(metadata + 2).getOpposite().ordinal())
            {
                return this.iconOutput;
            }
            */
            
            // If it is the back side
            if (metadata == 0 && side == 4 || metadata == 1 && side == 5 || metadata == 2 && side == 3 || metadata == 3 && side == 2)
            {
                return this.iconRadiationStabiliser;
            }

        }

        return this.iconMachineSide;
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
        metadata &= 12;
        if (metadata == this.RADIATION_STABILISER_METADATA)
        {
            return new TileEntityRadiationStabiliser();
        }
		return null;
    }
    
    public ItemStack getItem()
    {
        return new ItemStack(this, 1, this.RADIATION_STABILISER_METADATA);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(this.getItem());
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
    	return GCCoreUtil.translate("tile.radiation_stabiliser.desc");
    }

    @Override
    public boolean showDescription(int meta)
    {
        return true;
    }
    
}

