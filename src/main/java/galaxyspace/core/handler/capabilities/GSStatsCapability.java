package galaxyspace.core.handler.capabilities;

import java.lang.ref.WeakReference;

import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.core.util.GCLog;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class GSStatsCapability extends StatsCapability {
	public WeakReference<EntityPlayerMP> player;
	public int buildFlags = 0;

	private int[] know_res = new int[256];

	@Override
	public void saveNBTData(NBTTagCompound nbt) {
		nbt.setIntArray("gs_knowledge_research", know_res);		
	}

	@Override
	public void loadNBTData(NBTTagCompound nbt) {
		try {
			this.know_res = nbt.getIntArray("gs_knowledge_research");
		} catch (Exception e) {
			GCLog.severe("Found error in saved Galaxy Space player data for " + player.get().getGameProfile().getName() + " - this should fix itself next relog.");
			e.printStackTrace();
		}

		GalaxySpace.debug("Finished loading Galaxy Space player data for " + player.get().getGameProfile().getName() + " : " + this.buildFlags);
	}

	@Override
	public void copyFrom(StatsCapability oldData, boolean keepInv) {
		if(oldData.getKnowledgeResearches() != null)
			this.know_res = oldData.getKnowledgeResearches();
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
	public int[] getKnowledgeResearches() {
		return this.know_res;
	}

	@Override
	public void setKnowledgeResearch(int id, int k) {
		this.know_res[id] = k;		
	}

	
}
