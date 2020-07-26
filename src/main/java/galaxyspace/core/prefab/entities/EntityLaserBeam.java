package galaxyspace.core.prefab.entities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityLaserBeam extends EntityThrowable {

	protected EntityLivingBase shooter;
	private int lifetime = 20*2;
	private RayTraceResult trace;
	
	public EntityLaserBeam(World worldIn) {
		super(worldIn);
		//this.isImmuneToFire = true;
		//this.setNoGravity(true);
		//this.height = 0.5f;
		//this.setSize(0.25F, 0.25F);
	}

	public EntityLaserBeam(World world, double x, double y, double z) 
    {
        super(world, x, y, z);
    }

    public EntityLaserBeam(World world, EntityLivingBase entity, RayTraceResult trace) 
    {
        super(world, entity);
        this.trace = trace;
    }

	@Override
	public void onUpdate() {
		super.onUpdate();
		
		//if(--this.lifetime <= 0)
			//this.setDead();
		/*
		if(world.isRemote)
			GalaxySpace.debug(this.rotationYaw + " | " + this.rotationPitch);*/
	}
	
	public RayTraceResult getTrace()
	{
		return this.trace;
	}
	
	@Override
    protected float getGravityVelocity()
    {
        return 0.0F;
    }

	@Override
	protected void onImpact(RayTraceResult result) {
		
		if(result.getBlockPos() != null) {
			IBlockState state = this.world.getBlockState(result.getBlockPos());
					
			if(state != null)
			{
				this.world.createExplosion(null, result.getBlockPos().getX(), result.getBlockPos().getY(), result.getBlockPos().getZ(), 5, false);
			}			
		}
		
		this.setDead();
	}
}
