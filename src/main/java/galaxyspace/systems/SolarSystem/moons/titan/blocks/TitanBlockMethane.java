package galaxyspace.systems.SolarSystem.moons.titan.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.configs.GSConfigCore;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class TitanBlockMethane extends BlockFluidClassic
{

	 @SideOnly(Side.CLIENT)
     protected IIcon stillIcon;
     @SideOnly(Side.CLIENT)
     protected IIcon flowingIcon;
     
     public TitanBlockMethane(Fluid fluid, Material material) {
             super(fluid, material);
     }
     
     @SideOnly(Side.CLIENT)
     @Override
     public CreativeTabs getCreativeTabToDisplayOn()
     {
         return null;
     }
     
     @Override
     public IIcon getIcon(int side, int meta) {
             return (side == 0 || side == 1)? stillIcon : flowingIcon;
     }
     
     @SideOnly(Side.CLIENT)
     @Override
     public void registerBlockIcons(IIconRegister register) {
             stillIcon = register.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/titan/methanestill");
             flowingIcon = register.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/titan/methaneflowing");
     }
     
     @Override
     public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
             if (world.getBlock(x,  y,  z).getMaterial().isLiquid()) return false;
             return super.canDisplace(world, x, y, z);
     }
     
     @Override
     public boolean displaceIfPossible(World world, int x, int y, int z) {
             if (world.getBlock(x,  y,  z).getMaterial().isLiquid()) return false;
             return super.displaceIfPossible(world, x, y, z);
     }
     
     @Override
     public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
     {
    	p_149670_5_.attackEntityFrom(DamageSource.generic, 0.5F);
     }
     
     
     @SideOnly(Side.CLIENT)
     @Override
     public void randomDisplayTick(World world, int x, int y, int z, Random par5Random)
     {
    	 if(world.getBlock(x, y + 1, z) == Blocks.air && GSConfigCore.enableMethaneParticle)
    	 {
    		 for(int i = 0;i < 2;i++)
    		 {
    			world.spawnParticle("smoke", x + world.rand.nextDouble(), y + 1.5D, z + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
    		 }
    	 }
     }
}