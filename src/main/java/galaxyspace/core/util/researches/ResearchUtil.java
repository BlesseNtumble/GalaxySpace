package galaxyspace.core.util.researches;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import galaxyspace.api.IResearch;
import galaxyspace.core.prefab.researches.TestResearch;

public class ResearchUtil {
		
	//private static Map<String, Set<IResearch>> know_research_list = new HashMap<String, Set<IResearch>>();
	
	private static List<IResearch> list = new ArrayList<IResearch>();
	public static void initResearches()
	{
		addReserach(new TestResearch(1));
		addReserach(new TestResearch(5));
		addReserach(new TestResearch(6));
	}
	
	private static void addReserach(IResearch research)
	{
		if(!list.isEmpty())
			for(IResearch res : list)			
				if(research.getID() == res.getID())				
					throw new RuntimeException("Research ID " + research.getID() + " already have. Change ID.");		
		
		list.add(research);
	}
	
	public static List<IResearch> getReserachList()
	{
		return ImmutableList.copyOf(list);
	}
	
	/*
	public static Map<String, Set<IResearch>> getResearchList()
	{
		return know_research_list;
	}*/
}
