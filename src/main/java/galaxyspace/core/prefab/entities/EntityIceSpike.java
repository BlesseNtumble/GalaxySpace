package galaxyspace.core.prefab.entities;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityIceSpike extends EntityThrowable{

	public EntityIceSpike(World world) 
    {
        super(world);
    }
	
	public EntityIceSpike(World world, double x, double y, double z) 
    {
        super(world, x, y, z);
    }

    public EntityIceSpike(World world, EntityLivingBase entity) 
    {
        super(world, entity);
    }
    
    @Override
	public void onUpdate() {
		super.onUpdate();

		

		if (this.ticksExisted <= 2) {
			return;
		}

		if (this.world.isRemote) {
			this.spawnParticles(this.posX, this.posY, this.posZ);
		}

//		this.igniteBlocks(this.posX, this.posY, this.posZ);

	}
    
    public void spawnParticles(final double posX, final double posY, final double posZ) {

		final Random rand = new Random();

		for (int i = 0; i < Math.min(20, this.ticksExisted); i++) {
			final double randXMotion = rand.nextGaussian() * 0.0025 * this.ticksExisted;
			final double randYMotion = rand.nextDouble() * 0.025 * Math.min(25, this.ticksExisted);
			final double randZMotion = rand.nextGaussian() * 0.0025 * this.ticksExisted;

			this.world.spawnParticle(EnumParticleTypes.SNOWBALL, true, posX, posY, posZ, randXMotion, randYMotion, randZMotion);
		}
	}
    
    @Override
    protected float getGravityVelocity()
    {
        return 0.0003F;
    }
    
	@Override
	protected void onImpact(RayTraceResult result) {
		
		Entity hit = result.entityHit;
		if(hit != null)
		{
			Entity entity = result.entityHit;

			DamageSource dmgSrc = (DamageSource.causeMobDamage(this.getThrower()));
			int damage = 10;
			entity.attackEntityFrom(dmgSrc, damage); 
			if(entity instanceof EntityPlayer) ((EntityPlayer) entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 120));

		}
		else
		{
			this.setDead();
		}
	}
	
	@Override
	public float getBrightness()
    {
        return 1.0F;
    }

	@Override
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }

}
