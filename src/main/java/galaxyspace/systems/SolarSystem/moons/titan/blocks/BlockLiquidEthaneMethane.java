package galaxyspace.systems.SolarSystem.moons.titan.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.registers.fluids.GSFluids;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLiquidEthaneMethane extends BlockFluidClassic{

	public BlockLiquidEthaneMethane() {
		super(GSFluids.LiquidEthaneMethane, GSFluids.LEMethane);
		this.setQuantaPerBlock(9);
        //this.setLightLevel(0.1F);
        //this.needsRandomTick = true;
        this.setUnlocalizedName("block_liquidethanemethane");
	}

	@Override
    @Nullable
    public Boolean isEntityInsideMaterial(IBlockAccess world, BlockPos pos, IBlockState state, Entity entity, double yToTest, Material material, boolean testingHead)
    {
        return true;
    }
	
	@Override
    public boolean canDisplace(IBlockAccess world, BlockPos pos)
    {
        if (world.getBlockState(pos).getMaterial().isLiquid())
        {
            return false;
        }
        return super.canDisplace(world, pos);
    }

    @Override
    public boolean displaceIfPossible(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getMaterial().isLiquid())
        {
            return false;
        }
        return super.displaceIfPossible(world, pos);
    }
    
    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entity)
    {
    	if(entity instanceof EntityLivingBase)
    		entity.attackEntityFrom(DamageSource.GENERIC, 0.5F);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
    {
    	if(world.isAirBlock(pos.up()) && GSConfigCore.enableMethaneParticle)
    	{
    		for(int i = 0;i < 2;i++)
    			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + world.rand.nextDouble(), pos.getY() + 1.5D, pos.getZ() + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
    	}
    }
}
