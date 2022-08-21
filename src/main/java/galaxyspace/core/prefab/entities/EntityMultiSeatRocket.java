package galaxyspace.core.prefab.entities;

import com.mojang.realmsclient.gui.ChatFormatting;

import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.api.prefab.entity.EntityTieredRocket;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public abstract class EntityMultiSeatRocket extends EntityTieredRocket implements IMultiSeatEntity {
	
	public EntityMultiSeatRocket(World par1World) {
		super(par1World);
	}
	
	public EntityMultiSeatRocket(World world, double posX, double posY, double posZ) {
        super(world, posX, posY, posZ);
    }
	
	@Override
	public void onUpdate() {
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
                this.removeFuel(1);
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
            Vector3 mv1 = motionVec.clone().translate(d1);
            Vector3 mv2 = motionVec.clone().translate(d2);
            Vector3 mv3 = motionVec.clone().translate(d3);
            Vector3 mv4 = motionVec.clone().translate(d4);
            //T3 - Four flameballs which spread
            EntityLivingBase riddenByEntity = this.getPassengers().isEmpty() || !(this.getPassengers().get(0) instanceof EntityLivingBase) ? null : (EntityLivingBase) this.getPassengers().get(0);
            Object[] rider = new Object[] { riddenByEntity };
            makeFlame(x2 + d1.x, y2 + d1.y, z2 + d1.z, mv1, this.getLaunched(), rider);
            makeFlame(x2 + d2.x, y2 + d2.y, z2 + d2.z, mv2, this.getLaunched(), rider);
            makeFlame(x2 + d3.x, y2 + d3.y, z2 + d3.z, mv3, this.getLaunched(), rider);
            makeFlame(x2 + d4.x, y2 + d4.y, z2 + d4.z, mv4, this.getLaunched(), rider);
        }
    }
	
	private void makeFlame(double x2, double y2, double z2, Vector3 motionVec, boolean getLaunched, Object[] rider)
    {
        if (getLaunched)
        {
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 + 0.4 - this.rand.nextDouble() / 10D, y2, z2 + 0.4 - this.rand.nextDouble() / 10D), motionVec, rider);
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 - 0.4 + this.rand.nextDouble() / 10D, y2, z2 + 0.4 - this.rand.nextDouble() / 10D), motionVec, rider);
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 - 0.4 + this.rand.nextDouble() / 10D, y2, z2 - 0.4 + this.rand.nextDouble() / 10D), motionVec, rider);
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 + 0.4 - this.rand.nextDouble() / 10D, y2, z2 - 0.4 + this.rand.nextDouble() / 10D), motionVec, rider);
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2, y2, z2), motionVec, rider);
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 + 0.4, y2, z2), motionVec, rider);
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 - 0.4, y2, z2), motionVec, rider);
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2, y2, z2 + 0.4D), motionVec, rider);
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2, y2, z2 - 0.4D), motionVec, rider);
            return;
        }

        if (this.ticksExisted % 2 == 0) return;

        y2 += 1.6D;
        double x1 = motionVec.x;
        double y1 = motionVec.y;
        double z1 = motionVec.z;
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 + 0.4 - this.rand.nextDouble() / 10D, y2, z2 + 0.4 - this.rand.nextDouble() / 10D), new Vector3(x1 + 0.1D + this.rand.nextDouble() / 10D, y1 - 0.3D, z1 + 0.1D + this.rand.nextDouble() / 10D), rider);
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 - 0.4 + this.rand.nextDouble() / 10D, y2, z2 + 0.4 - this.rand.nextDouble() / 10D), new Vector3(x1 - 0.1D - this.rand.nextDouble() / 10D, y1 - 0.3D, z1 + 0.1D + this.rand.nextDouble() / 10D), rider);
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 - 0.4 + this.rand.nextDouble() / 10D, y2, z2 - 0.4 + this.rand.nextDouble() / 10D), new Vector3(x1 - 0.1D - this.rand.nextDouble() / 10D, y1 - 0.3D, z1 - 0.1D - this.rand.nextDouble() / 10D), rider);
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 + 0.4 - this.rand.nextDouble() / 10D, y2, z2 - 0.4 + this.rand.nextDouble() / 10D), new Vector3(x1 + 0.1D + this.rand.nextDouble() / 10D, y1 - 0.3D, z1 - 0.1D - this.rand.nextDouble() / 10D), rider);
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 + 0.4, y2, z2), new Vector3(x1 + 0.3D, y1 - 0.3D, z1), rider);
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 - 0.4, y2, z2), new Vector3(x1 - 0.3D, y1 - 0.3D, z1), rider);
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2, y2, z2 + 0.4D), new Vector3(x1, y1 - 0.3D, z1 + 0.3D), rider);
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2, y2, z2 - 0.4D), new Vector3(x1, y1 - 0.3D, z1 - 0.3D), rider);
    }
	
	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
		if(getMaxSeats() > 1) {
			return interactSpecial(player, hand);
		} else {
			return super.processInitialInteract(player, hand);
		}
	}
	
	private boolean interactSpecial(EntityPlayer player, EnumHand hand) {
		if(hand != EnumHand.MAIN_HAND) {
			return false;
		}
		
		if(launchPhase >= EnumLaunchPhase.LAUNCHED.ordinal()) {
			return false;
		}
		
		if(getPassengers().size() >= getMaxSeats()) {
			player.sendStatusMessage(new TextComponentString(ChatFormatting.RED + "Not enough seats in rocket!"), true);
			return false;
		}
		
		if(!world.isRemote && player instanceof EntityPlayerMP && !isPassenger(player)) {
            // if riding successfully -> add to passengers list on server side and update list on all clients
			if(player.startRiding(this)) {
				GalaxySpace.info(String.format("Add passenger to rocket %s [%s]", player.toString(), getPassengers().toString()));
				
				// display controls in chat for rocket driver
				if(getPassengers().size() == 1/*passengers.size() == 1*/) {
					GalacticraftCore.packetPipeline.sendTo(new PacketSimple(EnumSimplePacket.C_DISPLAY_ROCKET_CONTROLS, world.provider.getDimension(), new Object[] {}), (EntityPlayerMP) player);
				}
				
				return true;
			}
		}
				
		return false;
	}
	
	@Override
	protected void removePassenger(Entity passenger) {
		super.removePassenger(passenger);
		
		// if dismount successfully
		if(passenger.getRidingEntity() != this) {
			GalaxySpace.info(String.format("Remove passenger %s", passenger.toString()));
		}
	}
	
	@Override
	protected boolean canFitPassenger(Entity passenger) {
		return getPassengers().size() < getMaxSeats();//passengers.size() < getMaxSeats();
	}
	
	@Override
	public void updatePassenger(Entity passenger) {
		if(isPassenger(passenger)) {
			passenger.setPosition(posX, posY + getMountedYOffset(passenger), posZ);
		}
	}
	
	public double getMountedYOffset(Entity passenger) {
		return (getPassengers().size() - 1 - getPassengers().indexOf(passenger)) * 1.5D;
	}
	
	@Override
	public boolean shouldRiderSit() {
		return true;
	}
	
	public EntityPlayer getDriver() {
		int lastIndex = getPassengers().size() - 1;
		return getPassengers().isEmpty() || !(getPassengers().get(lastIndex) instanceof EntityPlayer) ? null : (EntityPlayer) getPassengers().get(lastIndex);
	}
}
