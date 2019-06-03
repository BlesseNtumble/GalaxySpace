package galaxyspace.core.handler.capabilities;

import java.lang.ref.WeakReference;

import asmodeuscore.core.astronomy.SpaceData.Engine_Type;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.core.util.GCLog;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class GSStatsCapability implements IStatsCapability {
	public WeakReference<EntityPlayerMP> player;
	public int buildFlags = 0;
	
	public Engine_Type rocket_engine;

	public static IStatsCapability get(Entity entity)
    {
        return entity.getCapability(GSCapabilityStatsHandler.GS_STATS_CAPABILITY, null);
    }
	
	@Override
	public void saveNBTData(NBTTagCompound nbt) {
		nbt.setInteger("rocket_engine", this.rocket_engine.getID());
	}

	@Override
	public void loadNBTData(NBTTagCompound nbt) {
		try {
			
			this.rocket_engine = Engine_Type.byID(nbt.getInteger("rocket_engine"));
		} catch (Exception e) {
			GCLog.severe("Found error in saved Galaxy Space player data for " + player.get().getGameProfile().getName() + " - this should fix itself next relog.");
			e.printStackTrace();
		}

		GalaxySpace.debug("Finished loading Galaxy Space player data for " + player.get().getGameProfile().getName() + " : " + this.buildFlags);
	}

	@Override
	public void copyFrom(IStatsCapability oldData, boolean keepInv) {
		//this.radiationLevel = oldData.getRadiationLevel();
		this.rocket_engine = oldData.getEngineType();
	}

	@Override
	public WeakReference<EntityPlayerMP> getPlayer() {
		return player;
	}

	@Override
	public void setPlayer(WeakReference<EntityPlayerMP> player) {
		this.player = player;
	}

	@Override
	public void setEngineType(Engine_Type type) {
		this.rocket_engine = type;
	}

	@Override
	public Engine_Type getEngineType() {
		return this.rocket_engine;
	}
}
