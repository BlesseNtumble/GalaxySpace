package galaxyspace.core.prefab.blocks;

import java.util.Random;

import javax.annotation.Nonnull;

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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFluidNatureGas extends BlockFluidFinite {

	public BlockFluidNatureGas(Fluid fluid, Material material) {
		super(fluid, material);
	}

	@Override
	public void updateTick(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state,	@Nonnull Random rand) {
		super.updateTick(world, pos, state, rand);

		if (GSConfigCore.enableGasExplosion) {
			boolean flag = false;
			for (BlockPos around : pos.getAllInBox(-1, -1, -1, 1, 1, 1)) {
				if (world.getBlockState(pos.add(around)).getBlock() == Blocks.FIRE)
					flag = true;
				if (world.getBlockState(pos.add(around)).getBlock() == Blocks.TORCH)
					flag = true;
				if (world.getBlockState(pos.add(around)).getBlock() == Blocks.MAGMA)
					flag = true;

				if (flag) {
					world.newExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 5F, true, true);
					flag = false;
				}
			}
		}
	}
		
	@Override
    @SideOnly (Side.CLIENT)
    public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks)
    {
		return new Vec3d(30D, 30D, 30D);
    }
	
	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}
	
	@Override
	public boolean isReplaceable(IBlockAccess world, BlockPos pos) {
		return ((Integer) world.getBlockState(pos).getValue(LEVEL)).intValue() < 4;
	}
	
	@Override
	public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {

		if (entity instanceof EntityLivingBase) {
			EntityLivingBase living = (EntityLivingBase) entity;
			if (living instanceof EntityPlayerMP) {
				if (!((EntityPlayerMP) living).capabilities.isCreativeMode) {
					if (!living.isPotionActive(MobEffects.BLINDNESS))
						living.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20 * 5, 4));

					if (!OxygenUtil.hasValidOxygenSetup((EntityPlayerMP) living)
							&& !GSEventHandler.isValidOxygenTanks((EntityPlayerMP) living)) {
						if (!living.isPotionActive(MobEffects.POISON)) {
							living.addPotionEffect(new PotionEffect(MobEffects.POISON, 20 * 5, 4));
						}
					} else {
						GSEventHandler.consumeOxygenFromTank((EntityPlayerMP) living, 1);
					}
				}
			}
		}
		
	}
}
