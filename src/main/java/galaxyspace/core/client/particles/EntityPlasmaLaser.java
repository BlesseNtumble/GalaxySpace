package galaxyspace.core.client.particles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class EntityPlasmaLaser extends EntityThrowable
{

    public EntityPlasmaLaser(World world) 
    {
        super(world);
    }

    public EntityPlasmaLaser(World world, double x, double y, double z) 
    {
        super(world, x, y, z);
    }

    public EntityPlasmaLaser(World world, EntityLivingBase entity) 
    {
        super(world, entity);
    }
    
    @Override
    public void onUpdate()
    {
        super.onUpdate();
        
        this.motionX *= (double)this.getVelocity();
        this.motionY *= (double)this.getVelocity();
        this.motionZ *= (double)this.getVelocity();
        this.setPosition(this.posX, this.posY, this.posZ);
    }

    protected float getVelocity()
    {
        return 1.0F; // �������� ���� (� ����� ��������� ����� ������ ����)
    }
    
    protected float getGravityVelocity()
    {
        return 0.001F; // ���������� ���� (� ����� ��������� ����� ��������� ����)
    }

    @Override
    protected void onImpact(MovingObjectPosition mop) 
    {
        if(mop.typeOfHit.equals(MovingObjectType.ENTITY))
        {
            Entity entity = mop.entityHit;

            DamageSource dmgSrc = (DamageSource.causeMobDamage(this.getThrower()));

            int damage = 8; // ���� ���� ��� ���������
            entity.attackEntityFrom(dmgSrc, damage); 
            this.setDead();
        }
        else if(mop.typeOfHit.equals(MovingObjectType.BLOCK))
        {
            if(!this.worldObj.isRemote)
            {
                float explosionForce = 1.0F; // ���� ������

                //this.worldObj.newExplosion(this, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, explosionForce, false, false);
                this.setDead();
            }
        }
    }
}
