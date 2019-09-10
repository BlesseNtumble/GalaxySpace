package galaxyspace.core.util.researches;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;

import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;

public class ResearchUtil {

	@Nonnull
	private static List<IResearch> list = new ArrayList<IResearch>();
	
	//private static Map<String, Set<IResearch>> know_research_list = new HashMap<String, Set<IResearch>>();
	
	public void initResearches()
	{
		addReserach(new TestResearch(0));
	}
	
	private void addReserach(IResearch research)
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
