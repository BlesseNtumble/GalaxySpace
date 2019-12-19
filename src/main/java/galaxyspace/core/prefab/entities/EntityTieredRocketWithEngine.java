package galaxyspace.core.prefab.entities;

import java.util.List;

import asmodeuscore.api.entity.ICustomEngine;
import asmodeuscore.core.astronomy.SpaceData.Engine_Type;
import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemSpaceSuit;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityAdvLandingPad;
import micdoodle8.mods.galacticraft.api.entity.IRocketType.EnumRocketType;
import micdoodle8.mods.galacticraft.api.prefab.entity.EntityTieredRocket;
import micdoodle8.mods.galacticraft.api.prefab.entity.EntitySpaceshipBase.EnumLaunchPhase;
import micdoodle8.mods.galacticraft.api.tile.IFuelDock;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.tile.TileEntityLandingPad;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public abstract class EntityTieredRocketWithEngine extends EntityTieredRocket implements ICustomEngine {
	private Engine_Type engine_type = Engine_Type.FUEL_ENGINE;
	private int tier;
	
	public EntityTieredRocketWithEngine(World par1World, int tier)
    {
        super(par1World);
        this.setSize(2.2F, 11F);
        this.tier = tier;
    }
	
	public EntityTieredRocketWithEngine(World par1World, double par2, double par4, double par6, int tier, EnumRocketType rocketType)
    {
        super(par1World, par2, par4, par6);
        this.rocketType = rocketType;
        this.tier = tier;
        this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
    }
	
    @Override
    protected void entityInit()
    {
        super.entityInit();
    }
    
    @Override
    public void onUpdate()
    {
        super.onUpdate();

        int i;

        if (this.timeUntilLaunch >= 100)
        {
            i = Math.abs(this.timeUntilLaunch / 100);
        }
        else
        {
            i = 1;
        }

        if ((this.getLaunched() || this.launchPhase == EnumLaunchPhase.IGNITED.ordinal() && this.rand.nextInt(i) == 0) && !ConfigManagerCore.disableSpaceshipParticles && this.hasValidFuel())
        {
            if (this.world.isRemote)
            {
                this.spawnParticles(this.getLaunched());
            }
        }

        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && this.hasValidFuel() && this.rand.nextInt(4) == 0 && !this.getLaunched() && this.getEngine() == Engine_Type.FUEL_ENGINE)
        {
        	//GalacticraftCore.proxy.spawnParticle("whiteSmokeLargeLaunched", new Vector3(this.posX + 0.4 - this.rand.nextDouble(), this.posY + 10, this.posZ + 0.4 - this.rand.nextDouble()), new Vector3(0.5D, -1.5D, 0.5D), new Object[] { });
        	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(this.posX + 0.4D + rand.nextDouble() , this.posY - 0.4D + rand.nextDouble(), this.posZ + rand.nextDouble()), new Vector3(0.05D + 0.06D, -0.5D, 0.0D - 0.03D), new Object [] { 20, 5, false, new Vector3(1.0F), 1.0D } );
     	   
        	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(this.posX - 0.8D + rand.nextDouble() , this.posY - 0.4D + rand.nextDouble(), this.posZ + rand.nextDouble()), new Vector3(0.05D - 0.25D, -0.5D, 0.0D - 0.03D), new Object [] { 20, 5, false, new Vector3(1.0F), 1.0D } );
      	   
        	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(this.posX - 0.2D + rand.nextDouble() , this.posY - 0.4D + rand.nextDouble(), this.posZ  + 0.4D + rand.nextDouble()), new Vector3(0.0D, -0.5D, 0.1D + 0.06D), new Object [] { 20, 5, false, new Vector3(1.0F), 1.0D } );
      	   
        	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(this.posX - 0.2D + rand.nextDouble() , this.posY - 0.4D + rand.nextDouble(), this.posZ  - 0.4D + rand.nextDouble()), new Vector3(0.0D, -0.5D, -0.1D - 0.06D), new Object [] { 20, 5, false, new Vector3(1.0F), 1.0D } );
       	   
        }
        
        if (this.launchPhase >= EnumLaunchPhase.LAUNCHED.ordinal() && this.hasValidFuel())
        {
            if (this.launchPhase == EnumLaunchPhase.LAUNCHED.ordinal())
            {
                double d = this.timeSinceLaunch / 150;

                float mod_engine = 1.0F;
            	
                switch(engine_type) {
                	case FUEL_ENGINE:
                		break;
                	case ION_ENGINE:
                		mod_engine = 2.0F;
                		break;
                	case SUBLIGHT_ENGINE:
                		mod_engine = 5.0F;
                		break;
                	default:
                		break;
                }
                
               // if(this.engine_type == Engine_Type.SUBLIGHT_ENGINE) mod_engine = 5;
            	
                if (this.world.provider instanceof IGalacticraftWorldProvider && ((IGalacticraftWorldProvider) this.world.provider).hasNoAtmosphere())
                {
                    d = Math.min(d * 1.2 * mod_engine, 2 * mod_engine);
                }
                else
                {
                    d = Math.min(d * mod_engine, 1.4 * mod_engine);
                }

                if (d != 0.0)
                {  
                    this.motionY = -d * 2.5D * Math.cos((this.rotationPitch - 180) / Constants.RADIANS_TO_DEGREES);
                }
            }
            else
            {
                this.motionY -= 0.008D;
            }

            double multiplier = 1.0D;

            if (this.world.provider instanceof IGalacticraftWorldProvider)
            {
                multiplier = ((IGalacticraftWorldProvider) this.world.provider).getFuelUsageMultiplier();

                if (multiplier <= 0)
                {
                    multiplier = 1;
                }
            }

            if (this.timeSinceLaunch % MathHelper.floor(2 * (1 / multiplier)) == 0)
            {
                this.removeFuel(4);
                if (!this.hasValidFuel())
                {
                    this.stopRocketSound();
                }
            }
        }
        else if (!this.hasValidFuel() && this.getLaunched() && !this.world.isRemote)
        {
            if (Math.abs(Math.sin(this.timeSinceLaunch / 1000)) / 10 != 0.0)
            {
                this.motionY -= Math.abs(Math.sin(this.timeSinceLaunch / 1000)) / 20;
            }
        }
        
        //GalaxySpace.debug(this.getEngine().getName());
    }
    
    @Override
    public void onTeleport(EntityPlayerMP player)
    {
        EntityPlayerMP playerBase = PlayerUtil.getPlayerBaseServerFromPlayer(player, false);

        
        if (playerBase != null)
        {
            GCPlayerStats stats = GCPlayerStats.get(playerBase);
           
            if (this.stacks == null || this.stacks.isEmpty())
            {
                stats.setRocketStacks(NonNullList.withSize(2, ItemStack.EMPTY));
            }
            else
            {
                stats.setRocketStacks(this.stacks);
            }

            stats.setRocketType(this.rocketType.getIndex());            
            stats.setFuelLevel(this.fuelTank.getFluidAmount());            

            
            if(engine_type != Engine_Type.FUEL_ENGINE)
            {
            	ItemStack withengine = getPickedResult(null);
                if(!withengine.hasTagCompound()) withengine.setTagCompound(new NBTTagCompound());
                withengine.getTagCompound().setBoolean(engine_type.getName(), true);
                withengine.getTagCompound().setInteger(ItemSpaceSuit.mod_count, 0);
                
                for(int i = 0; i < stats.getRocketStacks().size(); i++)
                {
                	ItemStack stack = stats.getRocketStacks().get(i);
                	if(i == stats.getRocketStacks().size() - 1)
                	{                		
                		stats.getRocketStacks().set(i, withengine);
                		break;
                	}
                }
              
            	//stats.getRocketStacks().set(1, withengine);
            }
            else
            	stats.setRocketItem(getPickedResult(null).getItem());
           
        }
    }
    

    protected abstract void spawnParticles(boolean launched);
    
    @Override
    public boolean isUsableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return !this.isDead && par1EntityPlayer.getDistanceSq(this) <= 64.0D;
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("engine_type", engine_type.getID());
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.engine_type = Engine_Type.byID(par1NBTTagCompound.getInteger("engine_type"));
    }

    @Override
    public int getRocketTier()
    {
        return this.tier;
    }
    
    @Override
    public int getPreLaunchWait()
    {
        return 400;
    }

    @Override
    public float getCameraZoom()
    {
        return 15.0F;
    }

    @Override
    public boolean defaultThirdPerson()
    {
        return true;
    }
    
    @Override
    public List<ItemStack> getItemsDropped(List<ItemStack> droppedItems)
    {
        super.getItemsDropped(droppedItems);
        ItemStack rocket = getPickedResult(null);
        rocket.setTagCompound(new NBTTagCompound());
        rocket.getTagCompound().setInteger("RocketFuel", this.fuelTank.getFluidAmount());
        droppedItems.add(rocket);
        return droppedItems;
    }
    
    @Override
    public float getRenderOffsetY()
    {
        return -0.2F;
    }
    
    @Override
    public boolean isDockValid(IFuelDock dock)
    {
        return dock instanceof TileEntityLandingPad;
    }
    
    @Override
    public int addFuel(FluidStack liquid, boolean doFill)
    {
    	return GSUtils.fillWithGCFuel(this.fuelTank, liquid, doFill, this);
    }
    
    public void setEngine(Engine_Type type)
    {
    	this.engine_type = type;
    }
    
	@Override
	public Engine_Type getEngine() {
		return this.engine_type;
	}
	
    @Override
    public abstract int getFuelTankCapacity();
    
	@Override
    public abstract double getYOffset();
	
	@Override
    public abstract ItemStack getPickedResult(RayTraceResult target);
    
	@Override
	public abstract double getMountedYOffset();

	@Override
	public abstract float getRotateOffset();

	@Override
	public abstract double getOnPadYOffset();
	

}
