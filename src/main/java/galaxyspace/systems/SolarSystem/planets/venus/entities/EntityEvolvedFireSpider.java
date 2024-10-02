package galaxyspace.systems.SolarSystem.planets.venus.entities;

import micdoodle8.mods.galacticraft.api.entity.IEntityBreathable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityEvolvedFireSpider extends EntitySpider implements IEntityBreathable
{
    public EntityEvolvedFireSpider(World par1World)
    {
        super(par1World);
        this.setSize(1.4F, 0.9F);
        this.isImmuneToFire = true;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(22.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.0F);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0F);
    }

    @Override
    public boolean canBreath()
    {
        return true;
    }

    @Override
    protected boolean isAIEnabled()
    {
        return false;
    }
    
    @Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData livingData)
	{
		livingData = super.onSpawnWithEgg(livingData);

		if (this.worldObj.rand.nextInt(100) == 0)
		{
			EntityEvolvedFireSkeleton skeleton = new EntityEvolvedFireSkeleton(this.worldObj);
			skeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
			skeleton.onSpawnWithEgg(null);
			this.worldObj.spawnEntityInWorld(skeleton);
			skeleton.mountEntity(this);
		}

        if (livingData == null)
        {
            livingData = new EntityEvolvedFireSpider.GroupData();

            if (this.worldObj.difficultySetting == EnumDifficulty.HARD && this.worldObj.rand.nextFloat() < 0.1F * this.worldObj.func_147462_b(this.posX, this.posY, this.posZ))
            {
                ((EntityEvolvedFireSpider.GroupData)livingData).func_111104_a(this.worldObj.rand);
            }
        }

        if (livingData instanceof EntityEvolvedFireSpider.GroupData)
        {
            int i = ((EntityEvolvedFireSpider.GroupData)livingData).field_111105_a;

            if (i > 0 && Potion.potionTypes[i] != null)
            {
                this.addPotionEffect(new PotionEffect(i, Integer.MAX_VALUE));
            }
        }

		return livingData;
	}
    
    public void onLivingUpdate()
    {
    	//GalaxySpace.proxy.spawnParticle("largeflame", new Vector3(posX, posY + 0.5D, posZ), new Vector3(0.0D, 0.1D, 0.0D), new Object [] { });
        worldObj.spawnParticle("flame", this.posX + this.rand.nextDouble() - 0.5D * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height + 0.5D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        worldObj.spawnParticle("flame", this.posX + this.rand.nextDouble() - 0.5D * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height + 0.5D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        
    	super.onLivingUpdate();
    }
    /*
    @Override
    protected Item getDropItem()
    {
        return null;
    }
    
    @Override
    protected void dropFewItems(boolean hitPlayer, int level)
    {
    	if (hitPlayer)
        {
    		
            if((this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + level) > 0))
            {
            	if(this.rand.nextBoolean()) this.dropItem(Items.spider_eye, 1);
            	else this.entityDropItem(new ItemStack(GSItems.BasicItems, 1, 13), 0.0F);
            }
        }
    }*/
}
