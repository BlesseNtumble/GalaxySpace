package galaxyspace.systems.SolarSystem.moons.io.entities;

import java.util.List;
import java.util.Random;

import galaxyspace.GalaxySpace;
import galaxyspace.core.GSItems;
import galaxyspace.core.prefab.entities.EntityFlyBossBase;
import galaxyspace.core.prefab.entities.EntityIceSpike;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.entity.IEntityBreathable;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.entities.EntityAIArrowAttack;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.planets.mars.entities.EntityProjectileTNT;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;


public class EntityBossGhast extends EntityFlyBossBase implements IMob, IEntityBreathable
{
    protected long ticks = 0;
    public Entity targetEntity;
   
    public int entitiesWithin;
    public int entitiesWithinLast;

    private Vector3 roomCoords;
    private Vector3 roomSize;
    
    private boolean spawnedMinion;
    
    private static final DataParameter<Boolean> ATTACKING = EntityDataManager.<Boolean>createKey(EntityBossGhast.class, DataSerializers.BOOLEAN);
    

    public EntityBossGhast(World par1World)
    {
        super(par1World);
        this.setSize(4.0F, 3.0F);
        this.isImmuneToFire = true;
        this.moveHelper = new GhastMoveHelper(this);
        
        this.tasks.addTask(3, new AIFireballAttack(this));
        this.tasks.addTask(2, new AIRandomFly(this));
        //this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0D, 25, 20.0F));
        //this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(2, new AILookAround(this));
        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
        
    }

    public void setAttacking(boolean attacking)
    {
        this.dataManager.set(ATTACKING, Boolean.valueOf(attacking));
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
        if ("fireball".equals(par1DamageSource.getDamageType()) && this.getAttackingEntity() instanceof EntityPlayer)
        {
            super.attackEntityFrom(par1DamageSource, 5.0F);
          
            return true;
        }
        
        return false;
        
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0F * ConfigManagerCore.dungeonBossHealthMod);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.05F);
        //this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0F);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0D);
    }

    public EntityBossGhast(World world, Vector3 vec)
    {
        this(world);
        this.setPosition(vec.x, vec.y, vec.z);
    }
    
    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(ATTACKING, Boolean.valueOf(false));
    }

    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_GHAST_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_GHAST_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_GHAST_DEATH;
    }
    
    @Override
    protected void onDeathUpdate()
    {
    	super.onDeathUpdate();
    	
    	if (!this.world.isRemote)
        {    		
            if (this.deathTicks == 100)
            {
                GalacticraftCore.packetPipeline.sendToAllAround(new PacketSimple(PacketSimple.EnumSimplePacket.C_PLAY_SOUND_BOSS_DEATH, GCCoreUtil.getDimensionID(this.world), new Object[] { 1.5F }), new NetworkRegistry.TargetPoint(GCCoreUtil.getDimensionID(this.world), this.posX, this.posY, this.posZ, 40.0D));
            }
        }
    }
    /*
    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase entitylivingbase, float f)
    {
        this.world.playEvent(null, 1024, new BlockPos(this), 0);
        double d3 = this.posX;
        double d4 = this.posY + 5.5D;
        double d5 = this.posZ;
        double d6 = entitylivingbase.posX - d3;
        double d7 = entitylivingbase.posY + entitylivingbase.getEyeHeight() * 0.5D - d4;
        double d8 = entitylivingbase.posZ - d5;
        EntityProjectileTNT projectileTNT = new EntityProjectileTNT(this.world, this, d6 * 0.5D, d7 * 0.5D, d8 * 0.5D);

        projectileTNT.posY = d4;
        projectileTNT.posX = d3;
        projectileTNT.posZ = d5;
        this.world.spawnEntity(projectileTNT);
    }
    */
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

       /* if (this.getHealth() <= this.getMaxHealth() / 2.0 && !spawnedMinion)
        {
        	this.spawnedMinion = true;
        	if(!world.isRemote) {	        	
	        	/*for(int i = 0; i < 4; i++)
	        	{
	        		Entity entity = new EntityEvolvedColdBlaze(world);
	        		entity.setPosition(this.posX + this.world.rand.nextFloat(), this.posY, this.posZ + this.world.rand.nextFloat());
	        		this.world.spawnEntity(entity);
	        	}
	        	//this.setHealth(this.getHealth() + this.getMaxHealth() / 4.0F);
        	}
        	
        	if(world.isRemote)
        	{
        		for(int i = 0; i < 50; i++)
        			GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(this.posX, this.posY + rand.nextDouble(), this.posZ), new Vector3(0.0D + (rand.nextDouble() / 5) * (rand.nextBoolean() ? -1 : 1), 0.3D, 0.0D + (rand.nextDouble() / 5) * (rand.nextBoolean() ? -1 : 1)), new Object [] { 50 , 5, false, new Vector3(1.0F, 1.0F, 1.0F), 1.0D} ); 	
        	}
        }    */    
       
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
                this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5D) + (this.rand.nextDouble() * (this.rand.nextBoolean() ? -1 : 1)) * (double)this.width, this.posY - 2 + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) + (this.rand.nextDouble() * (this.rand.nextBoolean() ? -1 : 1)) * (double)this.width, 0.0D, 0.1D, 0.0D);
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
        List<ItemStack> stackList = GalacticraftRegistry.getDungeonLoot(5);
        return stackList.get(rand.nextInt(stackList.size())).copy();
    }
    
    protected boolean shouldAttack(EntityLivingBase living) {
		return true;
	}
    
    protected void spitFireball() {
		Vec3d vec3d = this.getLook(1.0F);
		double d2 = getAttackTarget().posX - (this.posX + vec3d.x * 4.0D);
		double d3 = getAttackTarget().getEntityBoundingBox().minY + (double) (getAttackTarget().height / 2.0F) - (0.5D + this.posY + (double) (this.height / 2.0F));
		double d4 = getAttackTarget().posZ - (this.posZ + vec3d.z * 4.0D);
		EntityLargeFireball entitylargefireball = new EntityLargeFireball(world, this, d2, d3, d4);
		entitylargefireball.explosionPower = 2;
		entitylargefireball.posX = this.posX + vec3d.x * 4.0D;
		entitylargefireball.posY = this.posY + (double) (this.height / 2.0F) + 0.5D;
		entitylargefireball.posZ = this.posZ + vec3d.z * 4.0D;
		world.spawnEntity(entitylargefireball);

		// when we attack, there is a 1-in-6 chance we decide to stop attacking
		if (rand.nextInt(6) == 0) {
			setAttackTarget(null);
		}
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
   
    
    static class AIRandomFly extends EntityAIBase
    {
        private final EntityBossGhast parentEntity;

        public AIRandomFly(EntityBossGhast ghast)
        {
            this.parentEntity = ghast;
            this.setMutexBits(1);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            EntityMoveHelper entitymovehelper = this.parentEntity.getMoveHelper();

            if (!entitymovehelper.isUpdating())
            {
            	return true;//parentEntity.getAttackTarget() == null;
            }
            else
            {
                double d0 = entitymovehelper.getX() - this.parentEntity.posX;
                double d1 = entitymovehelper.getY() - this.parentEntity.posY;
                double d2 = entitymovehelper.getZ() - this.parentEntity.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                //return parentEntity.getAttackTarget() == null && (d3 < 1.0D || d3 > 3600.0D);
                return d3 < 1.0D || d3 > 3600.0D;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting()
        {
            return false;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            Random random = this.parentEntity.getRNG();
            double d0 = this.parentEntity.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d1 = this.parentEntity.posY + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d2 = this.parentEntity.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
        }
    }

    static class AILookAround extends EntityAIBase
    {
        private final EntityBossGhast parentEntity;

        public AILookAround(EntityBossGhast ghast)
        {
            this.parentEntity = ghast;
            this.setMutexBits(2);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask()
        {
            if (this.parentEntity.getAttackTarget() == null)
            {
                this.parentEntity.rotationYaw = -((float)MathHelper.atan2(this.parentEntity.motionX, this.parentEntity.motionZ)) * (180F / (float)Math.PI);
                this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
            }
            else
            {
                EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
                double d0 = 64.0D;

                if (entitylivingbase.getDistanceSq(this.parentEntity) < 4096.0D)
                {
                    double d1 = entitylivingbase.posX - this.parentEntity.posX;
                    double d2 = entitylivingbase.posZ - this.parentEntity.posZ;
                    this.parentEntity.rotationYaw = -((float)MathHelper.atan2(d1, d2)) * (180F / (float)Math.PI);
                    this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
                }
            }
        }
    }

    public static class AIFireballAttack extends EntityAIBase {
        private final EntityBossGhast parentEntity;
        public int attackTimer;
        public int prevAttackTimer; // TF - add for renderer

        public AIFireballAttack(EntityBossGhast ghast) {
            this.parentEntity = ghast;
        }

        @Override
        public boolean shouldExecute() {
            EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
            if (entitylivingbase == null) {
                return false;
            }

            return true;

           // return this.parentEntity.getAttackTarget() != null && parentEntity.shouldAttack(parentEntity.getAttackTarget());
        }

        @Override
        public void startExecuting() {
            this.attackTimer = this.prevAttackTimer = 0;
        }

        @Override
        public void resetTask() {
            this.parentEntity.setAttacking(false);
        }

        @Override
        public void updateTask() {
            EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();

            if (entitylivingbase.getDistanceSq(this.parentEntity) < 4096.0D && this.parentEntity.getEntitySenses().canSee(entitylivingbase)) {
                World world = this.parentEntity.world;
                this.prevAttackTimer = attackTimer;
                ++this.attackTimer;

                // TF face our target at all times
                this.parentEntity.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10F, this.parentEntity.getVerticalFaceSpeed());

                if (this.attackTimer == 10) {
                    parentEntity.playSound(SoundEvents.ENTITY_GHAST_WARN, 10.0F, parentEntity.getSoundPitch());
                }

                if (this.attackTimer == 20) {
                    if (this.parentEntity.shouldAttack(entitylivingbase)) {
                        // TF - call custom method
                        parentEntity.playSound(SoundEvents.ENTITY_GHAST_SHOOT, 10.0F, parentEntity.getSoundPitch());
                        this.parentEntity.spitFireball();
                        this.prevAttackTimer = attackTimer;
                    }
                    this.attackTimer = -40;
                }
            } else if (this.attackTimer > 0) {
                this.prevAttackTimer = attackTimer;
                --this.attackTimer;
            }

            this.parentEntity.setAttacking(this.attackTimer > 10);
        }
    }

    static class GhastMoveHelper extends EntityMoveHelper
    {
        private final EntityBossGhast parentEntity;
        private int courseChangeCooldown;

        public GhastMoveHelper(EntityBossGhast ghast)
        {
            super(ghast);
            this.parentEntity = ghast;
        }

        public void onUpdateMoveHelper()
        {
            if (this.action == EntityMoveHelper.Action.MOVE_TO)
            {
                double d0 = this.posX - this.parentEntity.posX;
                double d1 = this.posY - this.parentEntity.posY;
                double d2 = this.posZ - this.parentEntity.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                if (this.courseChangeCooldown-- <= 0)
                {
                    this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
                    d3 = (double)MathHelper.sqrt(d3);

                    if (this.isNotColliding(this.posX, this.posY, this.posZ, d3))
                    {
                        this.parentEntity.motionX += d0 / d3 * 0.1D;
                        this.parentEntity.motionY += d1 / d3 * 0.1D;
                        this.parentEntity.motionZ += d2 / d3 * 0.1D;
                    }
                    else
                    {
                        this.action = EntityMoveHelper.Action.WAIT;
                    }
                }
            }
        }

        /**
         * Checks if entity bounding box is not colliding with terrain
         */
        private boolean isNotColliding(double x, double y, double z, double p_179926_7_)
        {
            double d0 = (x - this.parentEntity.posX) / p_179926_7_;
            double d1 = (y - this.parentEntity.posY) / p_179926_7_;
            double d2 = (z - this.parentEntity.posZ) / p_179926_7_;
            AxisAlignedBB axisalignedbb = this.parentEntity.getEntityBoundingBox();

            for (int i = 1; (double)i < p_179926_7_; ++i)
            {
                axisalignedbb = axisalignedbb.offset(d0, d1, d2);

                if (!this.parentEntity.world.getCollisionBoxes(this.parentEntity, axisalignedbb).isEmpty())
                {
                    return false;
                }
            }

            return true;
        }
    }
/*
	@Override
	public void setSwingingArms(boolean swingingArms) {
		
	}
*/
	@Override
	public int getChestTier() {
		return 5;
	}

	@Override
	public void dropKey() {
		this.entityDropItem(new ItemStack(GSItems.DUNGEON_KEYS, 1, 1), 0.5F);
	}

	@Override
	public Color getHealthBarColor() {
		return Color.RED;
	}
}
