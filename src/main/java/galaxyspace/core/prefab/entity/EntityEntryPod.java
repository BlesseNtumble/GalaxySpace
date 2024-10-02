package galaxyspace.core.prefab.entity;

import java.util.Map;
import java.util.Random;

import micdoodle8.mods.galacticraft.api.entity.ICameraZoomEntity;
import micdoodle8.mods.galacticraft.api.entity.IIgnoreShift;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.entities.EntityLanderBase;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityEntryPod extends EntityLanderBase implements ICameraZoomEntity, IIgnoreShift{

	private Integer groundPosY = null;
	
	public EntityEntryPod(World world) {
		super(world, 0F);
		this.setSize(2.0F, 2.0F);

	}

	public EntityEntryPod(EntityPlayerMP player)
    {
        super(player, 0F);
        this.setSize(2.0F, 2.0F);
    }
	
	@Override
    public double getMountedYOffset()
    {
        return this.height - 1.2D;
    }
	
	@Override
	public double getInitialMotionY()
	{
	    return -14.5F;
	}
	
	@Override
    public float getRotateOffset()
    {
        return -20F;
    }
	 
	@Override
    public boolean shouldSpawnParticles()
    {
        return false;
    }
	
	@Override
    public void tickInAir()
    {
        super.tickInAir();

        if (this.worldObj.isRemote)
        {
            if (!this.onGround)
            {
                this.motionY -= 0.014D;

                if (this.motionY < -0.7F)
                {
                    this.motionY *= 0.994F;
                }

                if (this.posY <= 242.0F)
                {
                    if (groundPosY == null)
                    {
                        this.groundPosY = this.worldObj.getTopSolidOrLiquidBlock((int)this.posX, (int)this.posZ);
                    }

                    if (this.posY - this.groundPosY > 5.0F)
                    {
                        this.motionY *= 0.995F;
                    }
                    else
                        this.motionY *= 0.7F;
                }
            }
        }
    }
	
	@Override
	public String getInventoryName() {
		return GCCoreUtil.translate("container.entry_pod.name");
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public boolean pressKey(int key) {
		return false;
	}

	@Override
	public float getCameraZoom() {
		return 15.0F;
	}

	@Override
	public boolean defaultThirdPerson() {
		return true;
	}

	@Override
	public Map<Vector3, Vector3> getParticleMap() {
		return null;
	}

	@Override
	public EntityFX getParticle(Random rand, double x, double y, double z, double motX, double motY, double motZ) {
		return null;
	}

	@Override
	public void tickOnGround() {
	
	}

	@Override
	public void onGroundHit() {
		//if(this.riddenByEntity != null)
			//this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 2.0F, true);
	}

	@Override
    public Vector3 getMotionVec()
    {
        if (this.onGround)
        {
            return new Vector3(0, 0, 0);
        }

        if (this.ticks >= 40 && this.ticks < 45)
        {
            this.motionY = this.getInitialMotionY();
        }

        if (!this.shouldMove())
        {
            return new Vector3(0, 0, 0);
        }

        return new Vector3(this.motionX, this.motionY, this.motionZ);
    }

	@Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity par1Entity)
    {
        return null;
    }
    
    @Override
    public boolean canBePushed()
    {
        return false;
    }
    
    @Override
    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }
    
    @Override
    public boolean interactFirst(EntityPlayer player)
    {
    	if (this.worldObj.isRemote)
        {
            if (!this.onGround)
            {
                return false;
            }

            if (this.riddenByEntity != null)
            {
                this.riddenByEntity.mountEntity(this);
            }

            return true;
        }

        if (this.riddenByEntity == null && player instanceof EntityPlayerMP)
        {
            GCCoreUtil.openParachestInv((EntityPlayerMP) player, this);
            return true;
        }
        else if (player instanceof EntityPlayerMP)
        {
            if (!this.onGround)
            {
                return false;
            }

            player.mountEntity(null);
            return true;
        }
        else
        {
            return true;
        }
    }
    
    @Override
    public boolean shouldIgnoreShiftExit()
    {
        return !this.onGround;
    }

    public Integer getGroundPosY()
    {
        return groundPosY;
    }
}
