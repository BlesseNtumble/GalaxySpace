package galaxyspace.core.client.particles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class EntityPlasmaBall extends EntityThrowable
{

    public EntityPlasmaBall(World world) 
    {
        super(world);
    }

    public EntityPlasmaBall(World world, double x, double y, double z) 
    {
        super(world, x, y, z);
    }

    public EntityPlasmaBall(World world, EntityLivingBase entity) 
    {
        super(world, entity);
    }
    /*
    @Override
    public void onUpdate()
    {
        super.onUpdate();
        
        this.motionX *= (double)this.getVelocity();
        this.motionY *= (double)this.getVelocity();
        this.motionZ *= (double)this.getVelocity();
        this.setPosition(this.posX, this.posY, this.posZ);
    }
*/
    
    protected float getVelocity()
    {
        return 1.0F; // �������� ���� (� ����� ��������� ����� ������ ����)
    }
    
    protected float getGravityVelocity()
    {
        return 0.01F; // ���������� ���� (� ����� ��������� ����� ��������� ����)
    }

    @Override
    protected void onImpact(MovingObjectPosition mop) 
    {
        if(mop.typeOfHit.equals(MovingObjectType.ENTITY))
        {
            Entity entity = mop.entityHit;

            DamageSource dmgSrc = (DamageSource.causeMobDamage(this.getThrower()));

            int damage = 10; // ���� ���� ��� ���������
            entity.attackEntityFrom(dmgSrc, damage); 
            this.setDead();
        }
        else if(mop.typeOfHit.equals(MovingObjectType.BLOCK))
        {
            if(!this.worldObj.isRemote)
            {
                float explosionForce = 2.0F; // ���� ������

                this.worldObj.newExplosion(this, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, explosionForce, false, false);
                this.setDead();
            }
        }
    }
}
