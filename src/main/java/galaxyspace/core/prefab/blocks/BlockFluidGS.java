package galaxyspace.core.prefab.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidGS extends BlockFluidClassic
{

	@SideOnly(Side.CLIENT)
	protected IIcon stillIcon;
	@SideOnly(Side.CLIENT)
	protected IIcon flowingIcon;

	private boolean isDamaged = false;

	public BlockFluidGS(Fluid fluid, Material material, boolean isDamage) {
		super(fluid, material);
		this.isDamaged = isDamage;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public CreativeTabs getCreativeTabToDisplayOn() {
		return null;
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return (side == 0 || side == 1) ? stillIcon : flowingIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) {
		stillIcon = register
				.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockfluids/" + this.fluidName.toLowerCase() + "still");
		flowingIcon = register
				.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockfluids/" + this.fluidName.toLowerCase() + "flowing");
	}

	@Override
	public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
		if (world.getBlock(x, y, z).getMaterial().isLiquid())
			return false;
		return super.canDisplace(world, x, y, z);
	}

	@Override
	public boolean displaceIfPossible(World world, int x, int y, int z) {
		if (world.getBlock(x, y, z).getMaterial().isLiquid())
			return false;
		return super.displaceIfPossible(world, x, y, z);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) 
	{
		if(this.isDamaged)
			entity.attackEntityFrom(DamageSource.generic, 0.5F);
	}
     
     /*
     @SideOnly(Side.CLIENT)
     @Override
     public void randomDisplayTick(World world, int x, int y, int z, Random par5Random)
     {
    	 if(world.getBlock(x, y + 1, z) == Blocks.air)
    	 {
    		 for(int i = 0;i < 2;i++)
    		 {
    			world.spawnParticle("smoke", x + world.rand.nextDouble(), y + 1.5D, z + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
    		 }
    	 }
     }*/
}