package galaxyspace.core.util.researches;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import galaxyspace.api.IResearch;
import galaxyspace.core.prefab.researches.TestResearch;

public class ResearchUtil {
	
	private static List<IResearch> list = new ArrayList<IResearch>();
	public static void initResearches()
	{
		addReserach(new TestResearch(1, 15, 15));
		addReserach(new TestResearch(5, 35, 15));
		addReserach(new TestResearch(6, 48, 38));
		addReserach(new TestResearch(10, 55, 38));
		addReserach(new TestResearch(11, 66, 50));
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
	
	public static IResearch getResearch(int id)
	{
		if(!list.isEmpty())
			for(IResearch res : list)
				if(res.getID() == id)
					 return res;
		return null;
	}

}
