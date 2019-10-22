package galaxyspace.core.handler.capabilities;

public class GSStatsCapabilityClient extends StatsCapabilityClient{

	private int[] know_res = new int[256];
	
	@Override
	public int[] getKnowledgeResearches() {
		return know_res;
	}

	@Override
	public void setKnowledgeResearch(int id, int k) {	
		this.know_res[id] = k;
	}

}
