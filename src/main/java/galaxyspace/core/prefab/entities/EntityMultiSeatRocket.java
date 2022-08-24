package galaxyspace.core.prefab.entities;

import java.lang.reflect.Field;

import com.mojang.realmsclient.gui.ChatFormatting;

import asmodeuscore.core.handler.capabilities.ACStatsCapability;
import asmodeuscore.core.handler.capabilities.IStatsCapability;
import galaxyspace.GalaxySpace;
import galaxyspace.api.entity.IMultiSeatEntity;
import galaxyspace.core.GSItems;
import galaxyspace.core.network.packet.GSPacketSimple;
import galaxyspace.core.network.packet.GSPacketSimple.GSEnumSimplePacket;
import galaxyspace.core.network.trackers.MultiSeatRocketDriverTracker;
import micdoodle8.mods.galacticraft.api.prefab.entity.EntityAutoRocket;
import micdoodle8.mods.galacticraft.api.prefab.entity.EntityTieredRocket;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IExitHeight;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.entities.EntityCelestialFake;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.GCLog;
import micdoodle8.mods.galacticraft.core.util.PlayerUtil;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;

/**
 * RUS: Базовый класс для существ с несколькими посадочными местами
 * EN: Parent class for entities with multiply seats
 * @author gug2
 */
public abstract class EntityMultiSeatRocket extends EntityTieredRocket implements IMultiSeatEntity {

	static Field marsConfigAllDimsAllowed;

    static {
        try {
            Class<?> marsConfig = Class.forName("micdoodle8.mods.galacticraft.planets.mars.ConfigManagerMars");
            marsConfigAllDimsAllowed = marsConfig.getField("launchControllerAllDims");
        } catch (Exception ignore) {}
    }
    
	public EntityMultiSeatRocket(World par1World) {
		super(par1World);
	}
	
	public EntityMultiSeatRocket(World world, double posX, double posY, double posZ) {
        super(world, posX, posY, posZ);
    }
	
	@Override
	public void onUpdate() {
		if(getWaitForPlayer()) {
			if(!getPassengers().isEmpty()) {
				for(Entity passenger : getPassengers()) {
					if(ticks >= 40) {
						if(!world.isRemote) {
	                        removePassengers();
	                        passenger.startRiding(this, true);
	                        GCLog.debug("Remounting player in rocket.");
	                    }

	                    setWaitForPlayer(false);
	                    motionY = -0.5D;
					} else {
						motionX = motionY = motionZ = 0.0D;
						passenger.motionX = passenger.motionY = passenger.motionZ = 0.0D;
					}
				}
			} else {
				motionX = motionY = motionZ = 0.0D;
			}
		}
		
		super.onUpdate();
		
		int i;

        if(timeUntilLaunch >= 100) {
            i = Math.abs(timeUntilLaunch / 100);
        } else {
            i = 1;
        }

        if((getLaunched() || launchPhase == EnumLaunchPhase.IGNITED.ordinal() && rand.nextInt(i) == 0) && !ConfigManagerCore.disableSpaceshipParticles && hasValidFuel()) {
            if (world.isRemote) {
                spawnParticles(getLaunched());
            }
        }

        if(launchPhase >= EnumLaunchPhase.LAUNCHED.ordinal() && hasValidFuel()) {
            if(launchPhase == EnumLaunchPhase.LAUNCHED.ordinal()) {
                double d = timeSinceLaunch / 150;

                if(world.provider instanceof IGalacticraftWorldProvider && ((IGalacticraftWorldProvider) world.provider).hasNoAtmosphere()) {
                    d = Math.min(d * 1.2, 2);
                } else {
                    d = Math.min(d, 1.4);
                }

                if(d != 0.0) {
                    motionY = -d * 2.5D * Math.cos((rotationPitch - 180) / Constants.RADIANS_TO_DEGREES);
                }
            } else {
                motionY -= 0.008D;
            }

            double multiplier = 1.0D;

            if(world.provider instanceof IGalacticraftWorldProvider) {
                multiplier = ((IGalacticraftWorldProvider) world.provider).getFuelUsageMultiplier();

                if(multiplier <= 0) {
                    multiplier = 1;
                }
            }

            if(timeSinceLaunch % MathHelper.floor(2 * (1 / multiplier)) == 0) {
                removeFuel(1);
                if(!hasValidFuel()) {
                    stopRocketSound();
                }
            }
        } else if(!hasValidFuel() && getLaunched() && !world.isRemote) {
            if(Math.abs(Math.sin(timeSinceLaunch / 1000)) / 10 != 0.0) {
                motionY -= Math.abs(Math.sin(timeSinceLaunch / 1000)) / 20;
            }
        }
	}
	
	protected void spawnParticles(boolean launched) {
        if(!isDead) {
            double sinPitch = Math.sin(rotationPitch / Constants.RADIANS_TO_DEGREES_D);
            double x1 = 3.2 * Math.cos(rotationYaw / Constants.RADIANS_TO_DEGREES_D) * sinPitch;
            double z1 = 3.2 * Math.sin(rotationYaw / Constants.RADIANS_TO_DEGREES_D) * sinPitch;
            double y1 = 3.2 * Math.cos((rotationPitch - 180) / Constants.RADIANS_TO_DEGREES_D);
            if(launchPhase == EnumLaunchPhase.LANDING.ordinal() && targetVec != null) {
                double modifier = posY - targetVec.getY();
                modifier = Math.max(modifier, 180.0);
                x1 *= modifier / 200.0D;
                y1 *= Math.min(modifier / 200.0D, 2.5D);
                z1 *= modifier / 200.0D;
            }

            final double y2 = prevPosY + (posY - prevPosY) + y1 - 0.75 * motionY - 0.3 + 1.2D;

            final double x2 = posX + x1 + motionX;
            final double z2 = posZ + z1 + motionZ;
            Vector3 motionVec = new Vector3(x1 + motionX, y1 + motionY, z1 + motionZ);
            Vector3 d1 = new Vector3(y1 * 0.1D, -x1 * 0.1D, z1 * 0.1D).rotate(315 - rotationYaw, motionVec);
            Vector3 d2 = new Vector3(x1 * 0.1D, -z1 * 0.1D, y1 * 0.1D).rotate(315 - rotationYaw, motionVec);
            Vector3 d3 = new Vector3(-y1 * 0.1D, x1 * 0.1D, z1 * 0.1D).rotate(315 - rotationYaw, motionVec);
            Vector3 d4 = new Vector3(x1 * 0.1D, z1 * 0.1D, -y1 * 0.1D).rotate(315 - rotationYaw, motionVec);
            Vector3 mv1 = motionVec.clone().translate(d1);
            Vector3 mv2 = motionVec.clone().translate(d2);
            Vector3 mv3 = motionVec.clone().translate(d3);
            Vector3 mv4 = motionVec.clone().translate(d4);
            //T3 - Four flameballs which spread
            EntityLivingBase riddenByEntity = getDriver();
            Object[] rider = new Object[] { riddenByEntity };
            makeFlame(x2 + d1.x, y2 + d1.y, z2 + d1.z, mv1, getLaunched(), rider);
            makeFlame(x2 + d2.x, y2 + d2.y, z2 + d2.z, mv2, getLaunched(), rider);
            makeFlame(x2 + d3.x, y2 + d3.y, z2 + d3.z, mv3, getLaunched(), rider);
            makeFlame(x2 + d4.x, y2 + d4.y, z2 + d4.z, mv4, getLaunched(), rider);
        }
    }
	
	private void makeFlame(double x2, double y2, double z2, Vector3 motionVec, boolean getLaunched, Object[] rider) {
        if(getLaunched) {
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 + 0.4 - rand.nextDouble() / 10D, y2, z2 + 0.4 - rand.nextDouble() / 10D), motionVec, rider);
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 - 0.4 + rand.nextDouble() / 10D, y2, z2 + 0.4 - rand.nextDouble() / 10D), motionVec, rider);
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 - 0.4 + rand.nextDouble() / 10D, y2, z2 - 0.4 + rand.nextDouble() / 10D), motionVec, rider);
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 + 0.4 - rand.nextDouble() / 10D, y2, z2 - 0.4 + rand.nextDouble() / 10D), motionVec, rider);
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2, y2, z2), motionVec, rider);
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 + 0.4, y2, z2), motionVec, rider);
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 - 0.4, y2, z2), motionVec, rider);
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2, y2, z2 + 0.4D), motionVec, rider);
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2, y2, z2 - 0.4D), motionVec, rider);
            return;
        }

        if(ticksExisted % 2 == 0) return;

        y2 += 1.6D;
        double x1 = motionVec.x;
        double y1 = motionVec.y;
        double z1 = motionVec.z;
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 + 0.4 - rand.nextDouble() / 10D, y2, z2 + 0.4 - rand.nextDouble() / 10D), new Vector3(x1 + 0.1D + rand.nextDouble() / 10D, y1 - 0.3D, z1 + 0.1D + rand.nextDouble() / 10D), rider);
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 - 0.4 + rand.nextDouble() / 10D, y2, z2 + 0.4 - rand.nextDouble() / 10D), new Vector3(x1 - 0.1D - rand.nextDouble() / 10D, y1 - 0.3D, z1 + 0.1D + rand.nextDouble() / 10D), rider);
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 - 0.4 + rand.nextDouble() / 10D, y2, z2 - 0.4 + rand.nextDouble() / 10D), new Vector3(x1 - 0.1D - rand.nextDouble() / 10D, y1 - 0.3D, z1 - 0.1D - rand.nextDouble() / 10D), rider);
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 + 0.4 - rand.nextDouble() / 10D, y2, z2 - 0.4 + rand.nextDouble() / 10D), new Vector3(x1 + 0.1D + rand.nextDouble() / 10D, y1 - 0.3D, z1 - 0.1D - rand.nextDouble() / 10D), rider);
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
				
				GCPlayerStats stats = GCPlayerStats.get(player);
				stats.setChatCooldown(0);
				
				// display controls in chat for rocket driver
				if(getPassengers().size() == 1) {
					GalacticraftCore.packetPipeline.sendTo(new PacketSimple(EnumSimplePacket.C_DISPLAY_ROCKET_CONTROLS, world.provider.getDimension(), new Object[] {}), (EntityPlayerMP) player);
				} else {
					IStatsCapability asCaps = ACStatsCapability.get( (EntityPlayerMP) player);
					asCaps.setSecondPassenger(true);
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
	public void onReachAtmosphere() {
		//Launch controlled
        if(destinationFrequency != -1) {
            if(world.isRemote) {
            	//stop the sounds on the client - but do not reset, the rocket may start again
            	this.stopRocketSound();
                return;
            }

            this.setTarget(true, this.destinationFrequency);

            if(targetVec != null) {
                if(targetDimension != world.provider.getDimension()) {
                    WorldProvider targetDim = WorldUtil.getProviderForDimensionServer(this.targetDimension);               
                    if(targetDim != null && targetDim.world instanceof WorldServer) {
                    	boolean dimensionAllowed = this.targetDimension == ConfigManagerCore.idDimensionOverworld;

                    	if(targetDim instanceof IGalacticraftWorldProvider) {
                    		if (((IGalacticraftWorldProvider) targetDim).canSpaceshipTierPass(this.getRocketTier()))
                    			dimensionAllowed = true;
                    		else
                    			dimensionAllowed = false;
                    	} else
                    		//No rocket flight to non-Galacticraft dimensions other than the Overworld allowed unless config
                    		if((this.targetDimension > 1 || this.targetDimension < -1) && marsConfigAllDimsAllowed != null) {
                    			try {
                    				if (marsConfigAllDimsAllowed.getBoolean(null))
                    				{
                    					dimensionAllowed = true;
                    				}
                    			} catch (Exception e) { e.printStackTrace(); }
                    		}

                    	if(dimensionAllowed) {
                    		if(!this.getPassengers().isEmpty()) {
                    		    for(Entity passenger : this.getPassengers()) {
                    		        if(passenger instanceof EntityPlayerMP) {
                    		            WorldUtil.transferEntityToDimension(passenger, this.targetDimension, (WorldServer) targetDim.world, false, this);
                    		        }
                    		    }
                    		} else {
                    		    Entity e = WorldUtil.transferEntityToDimension(this, this.targetDimension, (WorldServer)targetDim.world, false, null);
                    		    if(e instanceof EntityAutoRocket) {
                    		        e.setPosition(this.targetVec.getX() + 0.5F, this.targetVec.getY() + 800, this.targetVec.getZ() + 0.5f);
                    		        ((EntityAutoRocket)e).setLaunchPhase(EnumLaunchPhase.LANDING);
                    		        ((EntityAutoRocket)e).setWaitForPlayer(false);
                    		    } else {
                    		        GCLog.info("Error: failed to recreate the unmanned rocket in landing mode on target planet.");
                    		        e.setDead();
                    		        this.setDead();
                    		    }
                    		}
                    		return;
                    	}
                    }
                    //No destination world found - in this situation continue into regular take-off (as if Not launch controlled)
                } else {
                	//Same dimension controlled rocket flight
                	this.setPosition(targetVec.getX() + 0.5F, targetVec.getY() + 800, targetVec.getZ() + 0.5F);
                    //Stop any lateral motion, otherwise it will update to an incorrect x,z position first tick after spawning above target
                    motionX = motionZ = 0.0D;
                    //Small upward motion initially, to keep clear of own flame trail from launch
                    motionY = 0.1D;
                    for(Entity passenger : this.getPassengers()) {
                        if(passenger instanceof EntityPlayerMP) {
                            WorldUtil.forceMoveEntityToPos(passenger, (WorldServer) this.world, new Vector3(this.targetVec.getX() + 0.5F, this.targetVec.getY() + 800, this.targetVec.getZ() + 0.5F), false);
                            this.setWaitForPlayer(true);
                            GCLog.debug("Rocket repositioned, waiting for player");
                        }
                    }
                    this.setLaunchPhase(EnumLaunchPhase.LANDING);
                    //Do not destroy the rocket, we still need it!
                    return;
                }
            } else {
                //Launch controlled launch but no valid target frequency = rocket loss [INVESTIGATE]
            	GCLog.info("Error: the launch controlled rocket failed to find a valid landing spot when it reached space.");
            	fuelTank.drain(Integer.MAX_VALUE, true);
            	posY = Math.max(255, (world.provider instanceof IExitHeight ? ((IExitHeight) world.provider).getYCoordinateToTeleport() : 1200) - 200);
                return;
            }
        }

        //Not launch controlled
        if(!world.isRemote) {
        	for(Entity passenger : getPassengers()) {
        		if(passenger instanceof EntityPlayerMP) {
        			EntityPlayerMP player = (EntityPlayerMP) passenger;
        			
        			onTeleport(player);
        			GCPlayerStats stats = GCPlayerStats.get(player);
        			
        			if(player.equals(getDriver())) {
        				WorldUtil.toCelestialSelection(player, stats, this.getRocketTier());
        			} else {
        				IStatsCapability asCaps = ACStatsCapability.get(player);
        				waitForRocketDriver(player, stats, asCaps, this.getRocketTier());
        			}
        		}
        	}

            //Destroy any rocket which reached the top of the atmosphere and is not controlled by a Launch Controller
            this.setDead();
        }
        
        //Client side, non-launch controlled, do nothing - no reason why it can't continue flying until the GUICelestialSelection activates
	}
	
	private void waitForRocketDriver(EntityPlayerMP player, GCPlayerStats stats, IStatsCapability asCaps, int tier) {
		player.dismountRidingEntity();
		stats.setSpaceshipTier(tier);
		
		stats.setUsingPlanetSelectionGui(false);
		stats.setSavedPlanetList("");
		asCaps.setSecondPassenger(false);
		
		// TODO: show wait for driver GUI...
		GalaxySpace.packetPipeline.sendTo(new GSPacketSimple(GSEnumSimplePacket.C_OPEN_WAITFORDRIVER_GUI, GCCoreUtil.getDimensionID(player.world), new Object[] {}), player);
		//
		
		// add rocket driver to tracker
		MultiSeatRocketDriverTracker.get().addPassengerToTrackedDriver( (EntityPlayerMP) getDriver(), player);
		//
		
		Entity fakeEntity = new EntityCelestialFake(player.world, player.posX, player.posY, player.posZ);
        player.world.spawnEntity(fakeEntity);
        player.startRiding(fakeEntity);
	}
	
	@Override
	public void onTeleport(EntityPlayerMP player) {
		EntityPlayerMP playerBase = PlayerUtil.getPlayerBaseServerFromPlayer(player, false);

        if(playerBase != null) {
            // if passenger -> no items in landing pad
        	if(playerBase.equals( (EntityPlayerMP) getDriver() )) {
        		GCPlayerStats stats = GCPlayerStats.get(playerBase);
                
                if(stacks == null || stacks.isEmpty()) {
                    stats.setRocketStacks(NonNullList.withSize(2, ItemStack.EMPTY));
                } else {
                    stats.setRocketStacks(stacks);
                }

                stats.setRocketType(rocketType.getIndex());
                stats.setRocketItem(GSItems.ROCKET_MULTI_SEAT);
                stats.setFuelLevel(fuelTank.getFluidAmount());
            }
        }
	}
	
	@Override
	protected boolean canFitPassenger(Entity passenger) {
		return getPassengers().size() < getMaxSeats();
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
