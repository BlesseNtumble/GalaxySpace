package galaxyspace.core.prefab.entities;

import java.util.ArrayList;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import galaxyspace.GalaxySpace;
import galaxyspace.core.network.packet.GSPacketSimple;
import galaxyspace.core.network.packet.GSPacketSimple.GSEnumSimplePacket;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import micdoodle8.mods.galacticraft.core.tick.TickHandlerServer;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.wrappers.ScheduledDimensionChange;
import net.minecraft.entity.player.EntityPlayerMP;

// следит за действиями "водителя" многоместной ракеты и уведомляет пассажиров о выбранном водителем измерении
public class MultiSeatRocketDriverTracker {

	private static final MultiSeatRocketDriverTracker INSTANCE = new MultiSeatRocketDriverTracker();
	public static MultiSeatRocketDriverTracker get() { return INSTANCE; }
	
	private Map<EntityPlayerMP, ArrayList<EntityPlayerMP>> DRIVER_PASSENGERS_TRACKED = Maps.newHashMap();
	
	private void addTrackedDriver(EntityPlayerMP driver) {
		if(!DRIVER_PASSENGERS_TRACKED.containsKey(driver)) {
			DRIVER_PASSENGERS_TRACKED.put(driver, Lists.newArrayList());
			
			GalaxySpace.info("add driver to tracking " + driver.toString());
		}
	}
	
	public void addPassengerToTrackedDriver(EntityPlayerMP driver, EntityPlayerMP passenger) {
		addTrackedDriver(driver);
		
		DRIVER_PASSENGERS_TRACKED.get(driver).add(passenger);
		
		GalaxySpace.info("add passenger to driver's tracker " + passenger.toString());
	}
	
	public void releaseTrackedDriver(EntityPlayerMP driver) {
		if(DRIVER_PASSENGERS_TRACKED.containsKey(driver)) {
			GalaxySpace.info("released driver " + driver.toString() + " with passengers " + DRIVER_PASSENGERS_TRACKED.get(driver));
			
			DRIVER_PASSENGERS_TRACKED.remove(driver);
		}
		GalaxySpace.info("there is no driver to release! Trying release driver " + driver.toString());
	}
	
	public void update() {
		for(Map.Entry<EntityPlayerMP, ArrayList<EntityPlayerMP>> trackedEntry : DRIVER_PASSENGERS_TRACKED.entrySet()) {
			GCPlayerStats driverStats = GCPlayerStats.get(trackedEntry.getKey());
			
			// if driver has been teleported to new dimension
			if(driverStats.getTeleportCooldown() > 0) {
				for(EntityPlayerMP passenger : trackedEntry.getValue()) {
					// send passenger to same dimension
					TickHandlerServer.scheduleNewDimensionChange(new ScheduledDimensionChange(passenger, trackedEntry.getKey().world.provider.getDimension()));
					GalaxySpace.info("schedule dimension change for " + passenger.toString() + " (call by driver " + trackedEntry.getKey().toString() + ")");
					
					// notify all clients that 'wait for driver' gui must be closed
					GalaxySpace.packetPipeline.sendTo(new GSPacketSimple(GSEnumSimplePacket.C_CLOSE_WAITFORDRIVER_GUI, GCCoreUtil.getDimensionID(passenger.world), new Object[] {}), passenger);
					GalaxySpace.info("notified " + passenger.toString() + " that gui must be closed");
				}
				
				releaseTrackedDriver(trackedEntry.getKey());
			}
		}
	}
}
