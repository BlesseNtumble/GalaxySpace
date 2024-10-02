package galaxyspace.core.util.astronomy;

import galaxyspace.api.BodiesHelper;
import galaxyspace.api.BodiesHelper.BodiesData;
import galaxyspace.api.dimension.IAdvancedSpace;
import galaxyspace.api.dimension.IAdvancedSpace.ClassBody;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.IChildBody;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldProviderSpace;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.world.WorldProvider;

public class AstronomyUtil {

	public static String classPlanet(CelestialBody body, WorldProvider dim)
	{	
		BodiesData data = BodiesHelper.data.get(body);
		
		if(body.getReachable() && body != GalacticraftCore.planetOverworld)
		{
			//float temp = ((IGalacticraftWorldProvider) dim).getThermalLevelModifier();
			//if(BodiesInfo.bodies.indexOf(this.selectedBody) != -1) return BodiesInfo.classPlanet.get(BodiesInfo.bodies.indexOf(this.selectedBody));
			float temp = 0.0F;
             
            try {	   
            	  temp = ((IGalacticraftWorldProvider) dim).getThermalLevelModifier();
            } catch (Exception e) {}
              
            boolean breath = false;
            try {	   
            	breath = ((IGalacticraftWorldProvider)dim).hasBreathableAtmosphere();
            } catch (Exception e) {}
            
            String type = "";
            // 2.0 --- +oo
            if(temp >= 2.0F) type = BodiesHelper.hot;
            //1.0 --- 1.9
            if(temp >= 1.0F && temp < 2.0) type = BodiesHelper.warm;
            //-0.9 --- 0.9 T
            if(temp > -1.0F && temp < 1.0F) type = BodiesHelper.comfort;
            //-1.9 --- -1.0F
            if(temp > -2.0 && temp <= -1.0F) type = BodiesHelper.cool;
            //-oo --- -2.0F
            if(temp <= -2.0F) type = BodiesHelper.cold;
            
            	
			if(!breath && body.atmosphere.isEmpty())
			{		
				
				if(((WorldProviderSpace)dim).getDayLength() == 0.0 || dim instanceof IAdvancedSpace && ((IAdvancedSpace)dim).getClassBody() == ClassBody.ASTEROID) return BodiesHelper.asteroid;

				return type + " " + BodiesHelper.selena;	
			}
			else
			{
				if(breath)
				{
					return type + " " + BodiesHelper.terra;
				}
				
				if(!breath)
				{
					
					if(temp > -5.0 && dim instanceof IAdvancedSpace && ((IAdvancedSpace)dim).getClassBody() == ClassBody.TITAN)
					{
						if(temp > 3.0F) type = BodiesHelper.hot;							
						else if(temp < -3.0F) type = BodiesHelper.icy;	
						else type = BodiesHelper.comfort;
						return type + " " + BodiesHelper.titan;
					}					
				}
					
				if(dim instanceof IAdvancedSpace && ((IAdvancedSpace)dim).getClassBody() == ClassBody.OCEANIDE) return BodiesHelper.oceanide;
				if(dim instanceof IAdvancedSpace && ((IAdvancedSpace)dim).getClassBody() == ClassBody.GASGIANT) return BodiesHelper.gasgiant;
				
				if(temp < -3.0F) return BodiesHelper.iceworld;
				return type + " " + BodiesHelper.desert;	
			}
		}
		else if(body == GalacticraftCore.planetOverworld) return BodiesHelper.comfort + " " + BodiesHelper.terra;
		else if(data != null && data.getClassPlanet() != null)
        {        	
        	return data.getClassPlanet();
        }
		
		return GCCoreUtil.translate("gui.message.unknown");
	}
	
	public static boolean isEqualGalaxy(CelestialBody start, CelestialBody end)
	{
		if(start instanceof Planet)
		{
			Planet s = (Planet) start;			
			
			if(end instanceof IChildBody)
			{
				IChildBody e = (IChildBody) end;
				return s.getParentSolarSystem().getUnlocalizedParentGalaxyName().equals(e.getParentPlanet().getParentSolarSystem().getUnlocalizedParentGalaxyName());
			}
			else
			{
				Planet e = (Planet) end;
				return s.getParentSolarSystem().getUnlocalizedParentGalaxyName().equals(e.getParentSolarSystem().getUnlocalizedParentGalaxyName());

			}	
		}
		else if(start instanceof IChildBody)
		{
			IChildBody s = (IChildBody) start;
			
			if(end instanceof Planet)
			{
				Planet e = (Planet) end;
				return s.getParentPlanet().getParentSolarSystem().getUnlocalizedParentGalaxyName().equals(e.getParentSolarSystem().getUnlocalizedParentGalaxyName());
			}
			else
			{
				IChildBody e = (IChildBody) end;			
				return s.getParentPlanet().getParentSolarSystem().getUnlocalizedParentGalaxyName().equals(e.getParentPlanet().getParentSolarSystem().getUnlocalizedParentGalaxyName());
			}
		}
		
		return false;
	}
	
	public static class PathsSolarSystems
	{
		public SolarSystem start, end;
		
		public PathsSolarSystems(SolarSystem s1, SolarSystem s2)
		{
			start = s1;
			end = s2;
		}
		
		public SolarSystem getStartSystem()
		{
			return start;
		}
		
		public SolarSystem getEndSystem()
		{
			return end;
		}
	}
}
