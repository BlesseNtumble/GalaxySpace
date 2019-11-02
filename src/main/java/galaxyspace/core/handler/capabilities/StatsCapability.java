package galaxyspace.core.handler.capabilities;

import java.lang.ref.WeakReference;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public abstract class StatsCapability {

	public static StatsCapability get(Entity entity)
    {
        return entity.getCapability(GSCapabilityStatsHandler.GS_STATS_CAPABILITY, null);
    }
	
	public abstract void saveNBTData(NBTTagCompound nbt);
	public abstract void loadNBTData(NBTTagCompound nbt);
	public abstract void copyFrom(StatsCapability oldData, boolean keepInv);
	public abstract WeakReference<EntityPlayerMP> getPlayer();
	public abstract void setPlayer(WeakReference<EntityPlayerMP> player);
	
	public abstract int[] getKnowledgeResearches();
	public abstract void setKnowledgeResearch(int id, int k);
}
