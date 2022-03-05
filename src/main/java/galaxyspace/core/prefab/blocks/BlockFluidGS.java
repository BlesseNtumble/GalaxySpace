package galaxyspace.core.prefab.blocks;

import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import galaxyspace.core.GSFluids;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.events.GSEventHandler;
import micdoodle8.mods.galacticraft.core.util.OxygenUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class BlockFluidGS extends BlockFluidClassic{

	private boolean isDamaged;
	private boolean isFlammable;
	
	public BlockFluidGS(Fluid fluid, Material material, boolean isDamage) {
		this(fluid, material, isDamage, false);
	}
	
	public BlockFluidGS(Fluid fluid, Material material, boolean isDamage, boolean isFlammable) {
		super(fluid, material);
		this.setQuantaPerBlock(fluid.isGaseous() ? 0 : 9);
		this.setTranslationKey("block_" + fluid.getName());
		
		if (this.density <= FluidRegistry.WATER.getDensity()) {
			this.displacements.put(Blocks.WATER, false);
			this.displacements.put(Blocks.FLOWING_WATER, false);
		}
		if (this.density <= FluidRegistry.LAVA.getDensity()) {
			this.displacements.put(Blocks.LAVA, false);
			this.displacements.put(Blocks.FLOWING_LAVA, false);
		}
	        
		this.isDamaged = isDamage;
		this.isFlammable = isFlammable;
	}

	@Override
	public void updateTick(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state,	@Nonnull Random rand) {
		super.updateTick(world, pos, state, rand);
		
		if(isFlammable && GSConfigCore.enableGasExplosion) {
			boolean flag = false;
			for(BlockPos around : pos.getAllInBox(-1, -1, -1, 1, 1, 1)) {
				if(world.getBlockState(pos.add(around)).getBlock() == Blocks.FIRE) flag = true;
				if(world.getBlockState(pos.add(around)).getBlock() == Blocks.TORCH) flag = true;
				if(world.getBlockState(pos.add(around)).getBlock() == Blocks.MAGMA) flag = true;
				
				if(flag) {
					world.newExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 5F, true, true);
					flag = false;
				}
			}
		}
	}
	
	@Override
    @Nullable
    public Boolean isEntityInsideMaterial(IBlockAccess world, BlockPos pos, IBlockState state, Entity entity, double yToTest, Material material, boolean testingHead)
    {
        return !stack.getFluid().isGaseous();
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
	public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity)    
	{
		if(this.isDamaged)
			entity.attackEntityFrom(DamageSource.GENERIC, 0.5F);
		
		if(stack.getFluid() == GSFluids.NatureGas) {
			boolean flag = false;
			if(entity instanceof EntityLivingBase)
			{
				EntityLivingBase living = (EntityLivingBase) entity;
				if(living instanceof EntityPlayerMP) {
					if(!((EntityPlayerMP) living).capabilities.isCreativeMode) {
						if(!living.isPotionActive(MobEffects.BLINDNESS)) 
							living.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20 * 5, 4));
						
						if(!OxygenUtil.hasValidOxygenSetup((EntityPlayerMP) living) && !GSEventHandler.isValidOxygenTanks((EntityPlayerMP) living)) {
							if(!living.isPotionActive(MobEffects.POISON)) {
								living.addPotionEffect(new PotionEffect(MobEffects.POISON, 20 * 5, 4));
							}
						}
						else {
							GSEventHandler.consumeOxygenFromTank((EntityPlayerMP) living, 1);
						}
					}
				}				
			}
		}
	}
}
