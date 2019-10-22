package galaxyspace.core.handler.capabilities;

import net.minecraft.entity.Entity;

public abstract class StatsCapabilityClient {

	public static StatsCapabilityClient get(Entity entity)
    {
        return entity.getCapability(GSCapabilityStatsHandler.GS_STATS_CAPABILITY_CLIENT, null);
    }
	
	public abstract int[] getKnowledgeResearches();
	public abstract void setKnowledgeResearch(int id, int k);
}
