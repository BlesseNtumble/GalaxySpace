package galaxyspace.systems.SolarSystem.planets.ceres.entities;

import java.util.List;
import java.util.Random;

import galaxyspace.GalaxySpace;
import galaxyspace.core.prefab.entities.EntityEvolvedColdBlaze;
import galaxyspace.core.prefab.entities.EntityIceSpike;
import galaxyspace.core.registers.items.GSItems;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.entity.IEntityBreathable;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.entities.EntityBossBase;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;


public class EntityBossBlaze extends EntityBossBase implements IEntityBreathable, IRangedAttackMob
{
    protected long ticks = 0;
    public Entity targetEntity;
   
    public int entitiesWithin;
    public int entitiesWithinLast;

    private Vector3 roomCoords;
    private Vector3 roomSize;
    
    private boolean spawnedMinion;

    public EntityBossBlaze(World par1World)
    {
        super(par1World);
        this.setSize(1.5F, 7.0F);
        this.isImmuneToFire = true;
        
        this.tasks.addTask(4, new EntityBossBlaze.AIFireballAttack(this));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D, 0.0F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
        if (this.getIsInvulnerable())
        {
            return false;
        }
        else if ("fireball".equals(par1DamageSource.getDamageType()) && this.getAttackingEntity() instanceof EntityPlayer)
        {
            super.attackEntityFrom(par1DamageSource, 5.0F);
          
            return true;
        }
        else if(par2 > 3.0F) {
        	return super.attackEntityFrom(par1DamageSource, 3.0F);
        }
        else
        {
            return super.attackEntityFrom(par1DamageSource, 3.0F);
        }
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0F * ConfigManagerCore.dungeonBossHealthMod);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.05F);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0F);
    }

    public EntityBossBlaze(World world, Vector3 vec)
    {
        this(world);
        this.setPosition(vec.x, vec.y, vec.z);
    }

    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_BLAZE_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_BLAZE_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_BLAZE_DEATH;
    }
    
    @Override
    protected void onDeathUpdate()
    {
    	super.onDeathUpdate();
    	
    	if (!this.world.isRemote)
        {
    		for (int i = 0; i < Math.min(2, this.ticksExisted); i++) {
    			EntityIceSpike entitysmallfireball = new EntityIceSpike(this.world, this);
    			entitysmallfireball.posY = this.posY + (double)(this.height / 2.0F) + 1.5D;
    			entitysmallfireball.shoot(this, this.rotationPitch + rand.nextInt(360), this.rotationYawHead + rand.nextInt(360), 0.0F, 0.8F, 1.0F);
    			this.world.spawnEntity(entitysmallfireball);
    		}
            if (this.deathTicks == 100)
            {
                GalacticraftCore.packetPipeline.sendToAllAround(new PacketSimple(PacketSimple.EnumSimplePacket.C_PLAY_SOUND_BOSS_DEATH, GCCoreUtil.getDimensionID(this.world), new Object[] { 1.5F }), new NetworkRegistry.TargetPoint(GCCoreUtil.getDimensionID(this.world), this.posX, this.posY, this.posZ, 40.0D));
            }
        }
    }

    @Override
    public boolean isInWater()
    {
        return false;
    }

    @Override
    public boolean handleWaterMovement()
    {
        return false;
    }
    
    @Override
    public void knockBack(Entity par1Entity, float par2, double par3, double par5)
    {
    }
    
    @Override
    public void onLivingUpdate()
    {
        if (this.ticks >= Long.MAX_VALUE)
        {
            this.ticks = 1;
        }

        this.ticks++;

        if (this.getHealth() <= this.getMaxHealth() / 2.0 && !spawnedMinion)
        {
        	this.spawnedMinion = true;
        	if(!world.isRemote) {	        	
	        	for(int i = 0; i < 4; i++)
	        	{
	        		Entity entity = new EntityEvolvedColdBlaze(world);
	        		entity.setPosition(this.posX + this.world.rand.nextFloat(), this.posY, this.posZ + this.world.rand.nextFloat());
	        		this.world.spawnEntity(entity);
	        	}
	        	this.setHealth(this.getHealth() + this.getMaxHealth() / 4.0F);
        	}
        	
        	if(world.isRemote)
        	{
        		for(int i = 0; i < 50; i++)
        			GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(this.posX, this.posY + rand.nextDouble(), this.posZ), new Vector3(0.0D + (rand.nextDouble() / 5) * (rand.nextBoolean() ? -1 : 1), 0.3D, 0.0D + (rand.nextDouble() / 5) * (rand.nextBoolean() ? -1 : 1)), new Object [] { 50 , 5, false, new Vector3(1.0F, 1.0F, 1.0F), 1.0D} ); 	
        	}
        }        

        final EntityPlayer player = this.world.getClosestPlayer(this.posX, this.posY, this.posZ, 20.0, false);

        if (player != null && !player.equals(this.targetEntity))
        {
            if (this.getDistanceSq(player) < 400.0D)
            {
                this.getNavigator().getPathToEntityLiving(player);
                this.targetEntity = player;
            }
        }
        else
        {
            this.targetEntity = null;
        }

        if (this.world.isRemote)
        {          
            for (int i = 0; i < 5; ++i)
            {
                this.world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, this.posX + (this.rand.nextDouble() - 0.5D) + (this.rand.nextDouble() * (this.rand.nextBoolean() ? -1 : 1)) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) + (this.rand.nextDouble() * (this.rand.nextBoolean() ? -1 : 1)) * (double)this.width, 0.0D, 0.0D, 0.0D);
            }
        }
        
        super.onLivingUpdate();
    }

    @Override
    protected void dropFewItems(boolean par1, int par2)
    {
    }

    @Override
    public EntityItem entityDropItem(ItemStack par1ItemStack, float par2)
    {
        final EntityItem entityitem = new EntityItem(this.world, this.posX, this.posY + par2, this.posZ, par1ItemStack);
        entityitem.motionY = -2.0D;
        entityitem.setDefaultPickupDelay();
        if (this.captureDrops)
        {
            this.capturedDrops.add(entityitem);
        }
        else
        {
            this.world.spawnEntity(entityitem);
        }
        return entityitem;
    }

     @Override
    public boolean canBreath()
    {
        return true;
    }

    public ItemStack getGuaranteedLoot(Random rand)
    {
        List<ItemStack> stackList = GalacticraftRegistry.getDungeonLoot(4);
        return stackList.get(rand.nextInt(stackList.size())).copy();
    }
/*
    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);

        if (this.roomCoords != null)
        {
            nbt.setDouble("roomCoordsX", this.roomCoords.x);
            nbt.setDouble("roomCoordsY", this.roomCoords.y);
            nbt.setDouble("roomCoordsZ", this.roomCoords.z);
            nbt.setDouble("roomSizeX", this.roomSize.x);
            nbt.setDouble("roomSizeY", this.roomSize.y);
            nbt.setDouble("roomSizeZ", this.roomSize.z);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        this.roomCoords = new Vector3();
        this.roomCoords.x = nbt.getDouble("roomCoordsX");
        this.roomCoords.y = nbt.getDouble("roomCoordsY");
        this.roomCoords.z = nbt.getDouble("roomCoordsZ");
        this.roomSize = new Vector3();
        this.roomSize.x = nbt.getDouble("roomSizeX");
        this.roomSize.y = nbt.getDouble("roomSizeY");
        this.roomSize.z = nbt.getDouble("roomSizeZ");
    }
    */
    static class AIFireballAttack extends EntityAIBase
    {
        private final EntityBossBlaze blaze;
        private int attackStep;
        private int attackTime;

        public AIFireballAttack(EntityBossBlaze blazeIn)
        {
            this.blaze = blazeIn;
            this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            EntityLivingBase entitylivingbase = this.blaze.getAttackTarget();
            return entitylivingbase != null && entitylivingbase.isEntityAlive();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            this.attackStep = 0;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
       /* public void resetTask()
        {
            this.blaze.setOnFire(false);
        }
*/
        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask()
        {
            --this.attackTime;
            EntityLivingBase entitylivingbase = this.blaze.getAttackTarget();
            double d0 = this.blaze.getDistanceSq(entitylivingbase);

            if (d0 < 4.0D)
            {
                if (this.attackTime <= 0)
                {
                    this.attackTime = 20;
                    this.blaze.attackEntityAsMob(entitylivingbase);
                }

                this.blaze.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
            }
            else if (d0 < this.getFollowDistance() * this.getFollowDistance())
            {
                double d1 = entitylivingbase.posX - this.blaze.posX;
                double d2 = entitylivingbase.getEntityBoundingBox().minY + (double)(entitylivingbase.height / 2.0F) - (this.blaze.posY + (double)(this.blaze.height / 2.0F));
                double d3 = entitylivingbase.posZ - this.blaze.posZ;

                if (this.attackTime <= 0)
                {
                    ++this.attackStep;

                    if (this.attackStep == 1)
                    {
                        this.attackTime = 40;
                        //this.blaze.setOnFire(true);
                    }
                    else if (this.attackStep <= 4)
                    {
                        this.attackTime = 6;
                    }
                    else
                    {
                        this.attackTime = 40;
                        this.attackStep = 0;
                        //this.blaze.setOnFire(false);
                    }

                    if (this.attackStep > 1)
                    {
                        float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5F;
                        this.blaze.world.playEvent((EntityPlayer)null, 1018, new BlockPos((int)this.blaze.posX, (int)this.blaze.posY, (int)this.blaze.posZ), 0);

                        for (int i = 0; i < 1; ++i)
                        {
                        	/*
                            EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.blaze.world, this.blaze, d1 + this.blaze.getRNG().nextGaussian() * (double)f, d2, d3 + this.blaze.getRNG().nextGaussian() * (double)f);
                            entitysmallfireball.posY = this.blaze.posY + (double)(this.blaze.height / 2.0F) + 0.5D;
                            this.blaze.world.spawnEntity(entitysmallfireball);
                            */
                            EntityIceSpike entitysmallfireball = new EntityIceSpike(this.blaze.world, this.blaze);
                            entitysmallfireball.posY = this.blaze.posY + (double)(this.blaze.height / 2.0F) + 1.6D;
                            entitysmallfireball.shoot(this.blaze, this.blaze.rotationPitch + 10, this.blaze.rotationYawHead, 1.0F, 0.8F, 1.0F);
                            this.blaze.world.spawnEntity(entitysmallfireball);
                        }
                    }
                }

                this.blaze.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
            }
            else
            {
                this.blaze.getNavigator().clearPath();
                this.blaze.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
            }

            super.updateTask();
        }

        private double getFollowDistance()
        {
            IAttributeInstance iattributeinstance = this.blaze.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
            return iattributeinstance == null ? 16.0D : iattributeinstance.getAttributeValue();
        }
    }

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
		
	}

	@Override
	public void setSwingingArms(boolean swingingArms) {
		
	}

	@Override
	public int getChestTier() {
		return 4;
	}

	@Override
	public void dropKey() {
		this.entityDropItem(new ItemStack(GSItems.DUNGEON_KEYS, 1, 0), 0.5F);
	}

	@Override
	public Color getHealthBarColor() {
		return Color.BLUE;
	}
}
