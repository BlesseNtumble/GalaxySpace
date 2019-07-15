package galaxyspace.core.prefab.entities;

import java.util.List;

import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockAdvancedLandingPadFull;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityAdvLandingPad;
import micdoodle8.mods.galacticraft.api.prefab.entity.EntityTieredRocket;
import micdoodle8.mods.galacticraft.api.tile.IFuelDock;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.api.world.IOrbitDimension;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.event.EventLandingPadRemoval;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.PlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class EntityTier5Rocket extends EntityTieredRocket
{
    public EntityTier5Rocket(World par1World)
    {
        super(par1World);
        this.setSize(2.2F, 11F);
        
    }

    public EntityTier5Rocket(World par1World, double par2, double par4, double par6, EnumRocketType rocketType)
    {
        super(par1World, par2, par4, par6);
        this.rocketType = rocketType;
        this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
    }

    public EntityTier5Rocket(World par1World, double par2, double par4, double par6, boolean reversed, EnumRocketType rocketType, NonNullList<ItemStack> inv)
    {
        this(par1World, par2, par4, par6, rocketType);
        this.stacks = inv;
    }

    @Override
    public double getYOffset()
    {
        return 1.5F;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target)
    {
        return new ItemStack(GSItems.ROCKET_TIER_5, 1, this.rocketType.getIndex());
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
    }

    @Override
    public double getMountedYOffset()
    {
        return 0.75D;
    }

    @Override
    public float getRotateOffset()
    {
        return 2.25F;
    }

    @Override
    public double getOnPadYOffset()
    {
        return 0.0D;
    }

    @Override
    public void onLaunch()
    {
    	super.onLaunch();
    	
    	 if (!this.world.isRemote)
         {
         	GCPlayerStats stats = null;
         	
         	if (!this.getPassengers().isEmpty())
         	{
         	    for (Entity player : this.getPassengers())
         	    {
         	        if (player instanceof EntityPlayerMP)
         	        {
         	            stats = GCPlayerStats.get(player);
                        stats.setLaunchpadStack(null);

         	            if (!(this.world.provider instanceof IOrbitDimension))
         	            {
         	                stats.setCoordsTeleportedFromX(player.posX);
         	                stats.setCoordsTeleportedFromZ(player.posZ);
         	            }
         	        }
         	    }

         	    Entity playerMain = this.getPassengers().get(0);
         	    if (playerMain instanceof EntityPlayerMP)
         	        stats = GCPlayerStats.get(playerMain);
         	}

             int amountRemoved = 0;

             PADSEARCH:
             for (int x = MathHelper.floor(this.posX) - 2; x <= MathHelper.floor(this.posX) + 2; x++)
             {
                 for (int y = MathHelper.floor(this.posY) - 3; y <= MathHelper.floor(this.posY) + 1; y++)
                 {
                     for (int z = MathHelper.floor(this.posZ) - 2; z <= MathHelper.floor(this.posZ) + 2; z++)
                     {
                         BlockPos pos = new BlockPos(x, y, z);
                         final Block block = this.world.getBlockState(pos).getBlock();

                         if (block != null && block instanceof BlockAdvancedLandingPadFull)
                         {
                             if (amountRemoved < 25)
                             {
                                 EventLandingPadRemoval event = new EventLandingPadRemoval(this.world, pos);
                                 MinecraftForge.EVENT_BUS.post(event);

                                 if (event.allow)
                                 {
                                     this.world.setBlockToAir(pos);
                                     amountRemoved = 25;
                                 }
                                 break PADSEARCH;
                             }
                         }
                     }
                 }
             }

             //Set the player's launchpad item for return on landing - or null if launchpads not removed
             if (stats != null && amountRemoved == 25)
             {
                 stats.setLaunchpadStack(new ItemStack(GSBlocks.ADVANCED_LANDING_PAD_SINGLE, 25, 0));
             }

             this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
         }
    }
    
    @Override
    public void onUpdate()
    {
        super.onUpdate();

        int i;

        //GalaxySpace.debug(this.getPosition() + "");
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

        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && this.hasValidFuel() && this.rand.nextInt(4) == 0 && !this.getLaunched())
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

                if (this.world.provider instanceof IGalacticraftWorldProvider && ((IGalacticraftWorldProvider) this.world.provider).hasNoAtmosphere())
                {
                    d = Math.min(d * 1.2, 2);
                }
                else
                {
                    d = Math.min(d, 1.4);
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
                this.removeFuel(2);
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
            stats.setRocketItem(GSItems.ROCKET_TIER_5);
            stats.setFuelLevel(this.fuelTank.getFluidAmount());
        }
    }

    protected void spawnParticles(boolean launched)
    {
        if (!this.isDead)
        {
            double sinPitch = Math.sin(this.rotationPitch / Constants.RADIANS_TO_DEGREES_D);
            double x1 = 3.2 * Math.cos(this.rotationYaw / Constants.RADIANS_TO_DEGREES_D) * sinPitch;
            double z1 = 3.2 * Math.sin(this.rotationYaw / Constants.RADIANS_TO_DEGREES_D) * sinPitch;
            double y1 = 3.2 * Math.cos((this.rotationPitch - 180) / Constants.RADIANS_TO_DEGREES_D);
            if (this.launchPhase == EnumLaunchPhase.LANDING.ordinal() && this.targetVec != null)
            {
                double modifier = this.posY - this.targetVec.getY();
                modifier = Math.max(modifier, 180.0);
                x1 *= modifier / 200.0D;
                y1 *= Math.min(modifier / 200.0D, 2.5D);
                z1 *= modifier / 200.0D;
            }

            final double y2 = this.prevPosY + (this.posY - this.prevPosY) + y1 - 0.75 * this.motionY - 0.3 + 1.2D;

            final double x2 = this.posX + x1 + this.motionX;
            final double z2 = this.posZ + z1 + this.motionZ;
            Vector3 motionVec = new Vector3(x1 + this.motionX, y1 + this.motionY, z1 + this.motionZ);
            Vector3 d1 = new Vector3(y1 * 0.1D, -x1 * 0.1D, z1 * 0.1D).rotate(315 - this.rotationYaw, motionVec);
            Vector3 d2 = new Vector3(x1 * 0.1D, -z1 * 0.1D, y1 * 0.1D).rotate(315 - this.rotationYaw, motionVec);
            Vector3 d3 = new Vector3(-y1 * 0.1D, x1 * 0.1D, z1 * 0.1D).rotate(315 - this.rotationYaw, motionVec);
            Vector3 d4 = new Vector3(x1 * 0.1D, z1 * 0.1D, -y1 * 0.1D).rotate(315 - this.rotationYaw, motionVec);
            Vector3 d5 = new Vector3(y1 * 0.1D, -x1 * 0.1D, z1 * 0.1D).rotate(270 - this.rotationYaw, motionVec);
            Vector3 d6 = new Vector3(x1 * 0.1D, -z1 * 0.1D, y1 * 0.1D).rotate(270 - this.rotationYaw, motionVec);
            Vector3 d7 = new Vector3(-y1 * 0.1D, x1 * 0.1D, z1 * 0.1D).rotate(270 - this.rotationYaw, motionVec);
            Vector3 d8 = new Vector3(x1 * 0.1D, z1 * 0.1D, -y1 * 0.1D).rotate(270 - this.rotationYaw, motionVec);
            Vector3 mv1 = motionVec.clone().translate(d1);
            Vector3 mv2 = motionVec.clone().translate(d2);
            Vector3 mv3 = motionVec.clone().translate(d3);
            Vector3 mv4 = motionVec.clone().translate(d4);
            Vector3 mv5 = motionVec.clone().translate(d5);
            Vector3 mv6 = motionVec.clone().translate(d6);
            Vector3 mv7 = motionVec.clone().translate(d7);
            Vector3 mv8 = motionVec.clone().translate(d8);
            //T3 - Four flameballs which spread
            makeFlame(x2 + d1.x, y2 + d1.y, z2 + d1.z, mv1, this.getLaunched());
            makeFlame(x2 + d2.x, y2 + d2.y, z2 + d2.z, mv2, this.getLaunched());
            makeFlame(x2 + d3.x, y2 + d3.y, z2 + d3.z, mv3, this.getLaunched());
            makeFlame(x2 + d4.x, y2 + d4.y, z2 + d4.z, mv4, this.getLaunched());
            
            makeFlame(x2 + d5.x, y2 + d5.y, z2 + d5.z, mv5, this.getLaunched());
            makeFlame(x2 + d6.x, y2 + d6.y, z2 + d6.z, mv6, this.getLaunched());
            makeFlame(x2 + d7.x, y2 + d7.y, z2 + d7.z, mv7, this.getLaunched());
            makeFlame(x2 + d8.x, y2 + d8.y, z2 + d8.z, mv8, this.getLaunched());
            makeFlame(x2, y2, z2, new Vector3(x1, y1, z1), this.getLaunched());
        }
    }

    private void makeFlame(double x2, double y2, double z2, Vector3 motionVec, boolean getLaunched)
    {
        EntityLivingBase riddenByEntity = this.getPassengers().isEmpty() || !(this.getPassengers().get(0) instanceof EntityLivingBase) ? null : (EntityLivingBase) this.getPassengers().get(0);

        if (getLaunched)
        {
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 + 0.4 - this.rand.nextDouble() / 10, y2, z2 + 0.4 - this.rand.nextDouble() / 10), motionVec, new Object[] { riddenByEntity });
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 - 0.4 + this.rand.nextDouble() / 10, y2, z2 + 0.4 - this.rand.nextDouble() / 10), motionVec, new Object[] { riddenByEntity });
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 - 0.4 + this.rand.nextDouble() / 10, y2, z2 - 0.4 + this.rand.nextDouble() / 10), motionVec, new Object[] { riddenByEntity });
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 + 0.4 - this.rand.nextDouble() / 10, y2, z2 - 0.4 + this.rand.nextDouble() / 10), motionVec, new Object[] { riddenByEntity });
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2, y2, z2), motionVec, new Object[] { riddenByEntity });
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 + 0.4, y2, z2), motionVec, new Object[] { riddenByEntity });
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 - 0.4, y2, z2), motionVec, new Object[] { riddenByEntity });
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2, y2, z2 + 0.4D), motionVec, new Object[] { riddenByEntity });
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2, y2, z2 - 0.4D), motionVec, new Object[] { riddenByEntity });
            return;
        }

        if (this.ticksExisted % 2 == 0) return;

        y2 += 1.6D;
        double x1 = motionVec.x;
        double y1 = motionVec.y;
        double z1 = motionVec.z;
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 + 0.4 - this.rand.nextDouble() / 10, y2, z2 + 0.4 - this.rand.nextDouble() / 10), new Vector3(x1 + 0.1D + this.rand.nextDouble() / 10, y1 - 0.3D, z1 + 0.1D + this.rand.nextDouble() / 10), new Object[] { riddenByEntity });
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 - 0.4 + this.rand.nextDouble() / 10, y2, z2 + 0.4 - this.rand.nextDouble() / 10), new Vector3(x1 - 0.1D - this.rand.nextDouble() / 10, y1 - 0.3D, z1 + 0.1D + this.rand.nextDouble() / 10), new Object[] { riddenByEntity });
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 - 0.4 + this.rand.nextDouble() / 10, y2, z2 - 0.4 + this.rand.nextDouble() / 10), new Vector3(x1 - 0.1D - this.rand.nextDouble() / 10, y1 - 0.3D, z1 - 0.1D - this.rand.nextDouble() / 10), new Object[] { riddenByEntity });
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 + 0.4 - this.rand.nextDouble() / 10, y2, z2 - 0.4 + this.rand.nextDouble() / 10), new Vector3(x1 + 0.1D + this.rand.nextDouble() / 10, y1 - 0.3D, z1 - 0.1D - this.rand.nextDouble() / 10), new Object[] { riddenByEntity });
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 + 0.4, y2, z2), new Vector3(x1 + 0.3D, y1 - 0.3D, z1), new Object[] { riddenByEntity });
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 - 0.4, y2, z2), new Vector3(x1 - 0.3D, y1 - 0.3D, z1), new Object[] { riddenByEntity });
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2, y2, z2 + 0.4D), new Vector3(x1, y1 - 0.3D, z1 + 0.3D), new Object[] { riddenByEntity });
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2, y2, z2 - 0.4D), new Vector3(x1, y1 - 0.3D, z1 - 0.3D), new Object[] { riddenByEntity });
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return !this.isDead && par1EntityPlayer.getDistanceSq(this) <= 64.0D;
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
    }

    @Override
    public int getRocketTier()
    {
        return 5;
    }

    @Override
    public int getFuelTankCapacity()
    {
        return 2800;
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
        ItemStack rocket = new ItemStack(GSItems.ROCKET_TIER_5, 1, this.rocketType.getIndex());
        rocket.setTagCompound(new NBTTagCompound());
        rocket.getTagCompound().setInteger("RocketFuel", this.fuelTank.getFluidAmount());
        droppedItems.add(rocket);
        return droppedItems;
    }

    @Override
    public float getRenderOffsetY()
    {
        return -0.15F;
    }
    
    @Override
    public boolean isDockValid(IFuelDock dock)
    {
        return dock instanceof TileEntityAdvLandingPad;
    }
    
    @Override
    public int addFuel(FluidStack liquid, boolean doFill)
    { 
    	return GSUtils.fillWithGCFuel(this.fuelTank, liquid, doFill, this);
    }
}
