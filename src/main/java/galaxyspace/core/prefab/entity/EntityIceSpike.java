package galaxyspace.core.prefab.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class EntityIceSpike extends EntityThrowable
{

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
    /*
    protected float getVelocity()
    {
        return 0.1F; // �������� ���� (� ����� ��������� ����� ������ ����)
    }
    
    protected float getGravityVelocity()
    {
        return 0.0F; // ���������� ���� (� ����� ��������� ����� ��������� ����)
    }
*/
    @Override
    protected void onImpact(MovingObjectPosition mop) 
    {
        if(mop.typeOfHit.equals(MovingObjectType.ENTITY))
        {
            Entity entity = mop.entityHit;
            
            DamageSource dmgSrc = (DamageSource.causeMobDamage(this.getThrower()));

            int damage = 10; // ���� ���� ��� ���������
            entity.attackEntityFrom(dmgSrc, damage); 
            if(entity instanceof EntityPlayerMP) ((EntityPlayerMP) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 120));
            this.setDead();
        }
        else if(mop.typeOfHit.equals(MovingObjectType.BLOCK))
        {
        	this.setDead();
        }
    }
}

