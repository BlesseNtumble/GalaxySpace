package galaxyspace.systems.SolarSystem.planets.overworld.blocks.mashines;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.items.GSItemBlockDesc.IBlockShiftDesc;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityHydroponicBase;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityOxygenFiller;
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
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockOxygenFiller extends BlockTileGC implements IBlockShiftDesc {

	private IIcon[] icons = new IIcon[6];

	public BlockOxygenFiller(String assetName) {
		super(GCBlocks.machine);
		this.setHardness(1.0F);
		this.setStepSound(Block.soundTypeMetal);
		this.setBlockTextureName(GalacticraftCore.TEXTURE_PREFIX + "machine");
		this.setBlockName(assetName);

	}

	@Override
	public CreativeTabs getCreativeTabToDisplayOn() {
		return GSCreativeTabs.GSBlocksTab;
	}
	
	@Override
    public int getRenderType()
    {
        return GalaxySpace.proxy.getBlockRender(this);
    }

	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.icons[0] = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine");
		this.icons[1] = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_input");
		this.icons[2] = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine");
		this.icons[3] = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_oxygen_input");
		this.icons[4] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/oxygen_filler");
		//this.iconOxygenInput = par1IconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_oxygen_input");
        //this.iconOxygenOutput = par1IconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_oxygen_output");
        
		this.blockIcon = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine");
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return this.getIcon(side, world.getBlockMetadata(x, y, z));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (side == 1) {
			return this.blockIcon;
		}
		else if(side == 0) return this.icons[3];
	
		if (side == meta + 2) {
			return this.icons[1];
		} else if (side == ForgeDirection.getOrientation(meta + 2).getOpposite().ordinal()) {
			return this.icons[3];
		}
		else if (meta == 0 && side == 4 || meta == 1 && side == 5 || meta == 2 && side == 3 || meta == 3 && side == 2)
        {
             return this.icons[4];
        }
		
		return this.icons[0];

		//return this.blockIcon;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {
		int metadata = itemStack.getItemDamage();

		int angle = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		int change = 0;

		switch (angle) {
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

		world.setBlockMetadataWithNotify(x, y, z, 0 + change, 3);
	}

	@Override
	public boolean onUseWrench(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side,
			float hitX, float hitY, float hitZ) {
		int metadata = par1World.getBlockMetadata(x, y, z);
		int original = metadata & 3;
		int change = 0;

		// Re-orient the block
		switch (original) {
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
		if (te instanceof TileBaseUniversalElectrical) {
			((TileBaseUniversalElectrical) te).updateFacing();
		}

		par1World.setBlockMetadataWithNotify(x, y, z, (metadata & 12) + change, 3);
		return true;
	}

	@Override
	public boolean onMachineActivated(World world, int x, int y, int z, EntityPlayer par5EntityPlayer, int side,
			float hitX, float hitY, float hitZ) {

		if (!world.isRemote && world.getBlockMetadata(x, y, z) < 4) {
			par5EntityPlayer.openGui(GalaxySpace.instance, -1, world, x, y, z);
			return true;
		}
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileEntityOxygenFiller();
	}

	@Override
	public String getShiftDescription(int meta) {
		return GCCoreUtil.translate("tile.OxygenFiller.desc");
	}

	@Override
	public boolean showDescription(int meta) {
		return true;
	}

	@Override
	public String getDescription(int meta) {
		return null;
	}
}
