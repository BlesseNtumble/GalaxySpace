package galaxyspace.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.galaxies.Star;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldProvider;

public class BodiesHelper {

	public enum Galaxies
	{
		/** Our Galaxy. Milky Way  **/
		MILKYWAY("milkyWay"),
		/** Andromeda  **/
		ANDROMEDA("andromeda"),
		/** Small Magellanic Cloud  **/
		SMC("smc"),
		/** Large Magellanic Cloud  **/
		LMC("lmc");
		
		private final String name;
		Galaxies(String name)
		{
			this.name = name;
		}
		
		public String getName()
		{
			return this.name;
		}
	}
	
	//Stars type
	public static String brown = GCCoreUtil.translate("gui.info.brown.name");
	public static String red = GCCoreUtil.translate("gui.info.red.name");
	public static String orange = GCCoreUtil.translate("gui.info.orange.name");
	public static String yellow = GCCoreUtil.translate("gui.info.yellow.name");
	public static String white = GCCoreUtil.translate("gui.info.white.name");
	public static String lightblue = GCCoreUtil.translate("gui.info.lightblue.name");
	//---------------------
	public static String dwarf = GCCoreUtil.translate("gui.info.dwarf.name");	
	public static String subdwarf = GCCoreUtil.translate("gui.info.subdwarf.name");	
	
	public static String giant = GCCoreUtil.translate("gui.info.giant.name");	
	public static String subgiant = GCCoreUtil.translate("gui.info.subgiant.name");	
	//Planets -------------
	public static String selena = GCCoreUtil.translate("gui.info.selena.name");
	public static String desert = GCCoreUtil.translate("gui.info.desert.name");
	public static String terra = GCCoreUtil.translate("gui.info.terra.name");
	public static String gasgiant = GCCoreUtil.translate("gui.info.gasgiant.name");
	public static String icegiant = GCCoreUtil.translate("gui.info.icegiant.name");
	public static String iceworld = GCCoreUtil.translate("gui.info.iceworld.name");
	public static String asteroid = GCCoreUtil.translate("gui.info.asteroid.name");
	public static String oceanide = GCCoreUtil.translate("gui.info.oceanide.name");
	public static String titan = GCCoreUtil.translate("gui.info.titan.name");
	//---------------------
	public static String hot = GCCoreUtil.translate("gui.info.hot.name");
	public static String warm = GCCoreUtil.translate("gui.info.warm.name");
	public static String comfort = GCCoreUtil.translate("gui.info.comfort.name");
	public static String cool = GCCoreUtil.translate("gui.info.cool.name");
	public static String cold = GCCoreUtil.translate("gui.info.cold.name");
	public static String icy = GCCoreUtil.translate("gui.info.icy.name");
	//---------------------
	
	public static Map<String, String> galaxiesImg = new HashMap();
	
	public static HashMap<String, String> galaxies = new HashMap();
	public static HashMap<CelestialBody, BodiesData> data = new HashMap();
	/*
	public static List<CelestialBody> bodies = new ArrayList();
	public static List<String> classPlanet = new ArrayList();
	public static List<Float> gravityPlanet = new ArrayList();
	public static List<Integer> pressurePlanet = new ArrayList();
	public static List<Float> tempPlanet = new ArrayList();
	public static List<Float> windPlanet = new ArrayList();
	public static List<Long> dayPlanet = new ArrayList();
	public static List<Boolean> breathablePlanet = new ArrayList();
	public static List<Boolean> solarPlanet = new ArrayList();
*/
	public static void registerGalaxyImg(String galaxy, String image)
	{
		galaxiesImg.put(galaxy, image);
	}
	
	public static void registerBody(CelestialBody body, BodiesData bodydata, boolean registr)
	{
		if(registr)
		{
			if(body instanceof Planet) GalaxyRegistry.registerPlanet((Planet) body);
			if(body instanceof Moon) GalaxyRegistry.registerMoon((Moon) body);
		}
		registerBodyWithClass(body, bodydata);
	}
	
		
	public static void registerBodyWithClass(CelestialBody body, BodiesData bodydata)
	{	
		data.put(body, bodydata);
	}
	
	public static float calculateGravity(float Si) {
		float i = (9.81F - Si) * (0.085F / 9.81F);
		float k = Math.round(i * 1000);
		return k / 1000;
	}
	
	public static SolarSystem registerSolarSystem(String modid, String name, Galaxies galaxy, Vector3 pos, String starname, float size)
	{
		return registerSolarSystem(modid, name, galaxy.getName(), pos, starname, size);		
	}
	
	public static SolarSystem registerSolarSystem(String modid, String name, String galaxy, Vector3 pos, String starname, float size)
	{
		SolarSystem body = new SolarSystem(name, galaxy);
		body.setMapPosition(pos);
		Star main = new Star(starname);
		main.setParentSolarSystem(body);
		main.setTierRequired(-1);
		main.setRelativeSize(size);
		main.setBodyIcon(new ResourceLocation(modid, "textures/gui/celestialbodies/" + name + "/" + starname + ".png"));
		body.setMainStar(main);
		
		return body;
	}
		
	public static Planet registerPlanet(SolarSystem system, String name, String modid, Class<? extends WorldProvider> provider, int dimID, int tier, float phase, float size, float distancefromcenter, float relativetime)
	{
		Planet body = new Planet(name).setParentSolarSystem(system);
		String path = system.getUnlocalizedName().replace("solarsystem.", "");
		body.setRingColorRGB(0.0F, 0.4F, 0.9F);
		body.setPhaseShift(phase);		
		body.setRelativeSize(size);    	
		body.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(distancefromcenter, distancefromcenter));
		body.setRelativeOrbitTime(relativetime);
		body.setBodyIcon(new ResourceLocation(modid, "textures/gui/celestialbodies/" + path + "/" + name + ".png"));
		if(provider != null) 
		{	
			body.setTierRequired(tier);
			body.setDimensionInfo(dimID, provider);
		}
		
		return body;
	}
	
	public static Moon registerMoon(Planet parent, String name, String modid, Class<? extends WorldProvider> provider, int dimID, int tier, float phase, float size, float distancefromcenter, float relativetime)
	{
		Moon body = new Moon(name).setParentPlanet(parent);
		String path = parent.getParentSolarSystem().getUnlocalizedName().replace("solarsystem.", "");
		body.setRingColorRGB(0.0F, 0.4F, 0.9F);
		body.setPhaseShift(phase);		
		body.setRelativeSize(size);    	
		body.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(distancefromcenter, distancefromcenter));
		body.setRelativeOrbitTime(relativetime);
		body.setBodyIcon(new ResourceLocation(modid, "textures/gui/celestialbodies/" + path + "/moons/" + name + ".png"));
		
		if(provider != null) 
		{	
			body.setTierRequired(tier);
			body.setDimensionInfo(dimID, provider);
		}
		
		return body;
	}

	public static class BodiesData
	{
		private String classplanet;
		private float gravity, temperature, wind;
		private int pressure;
		private long day;
		private boolean breath, solarradiation;
		
		private List<ItemStack> itemsfortravel = new ArrayList();
		
		public BodiesData(String classPlanet, float gravity, int pressure, float temp, float wind, long day, boolean breath, boolean solarrad)
		{
			this.classplanet = classPlanet;
			this.gravity = gravity;
			this.pressure = pressure;
			this.temperature = temp;
			this.wind = wind;
			this.day = day;
			this.breath = breath;
			this.solarradiation = solarrad;
		}
		
		public BodiesData addItemStackList(ItemStack[] itemStacks)
		{
			for(ItemStack stacks : itemStacks)
				this.itemsfortravel.add(stacks);
			
			return this;
		}
		
		public BodiesData addItemStack(ItemStack stack)
		{
			this.itemsfortravel.add(stack);
			return this;
		}
		
		public List<ItemStack> getItemStacks()
		{
			return this.itemsfortravel;
		}
		
		public String getClassPlanet()
		{
			return this.classplanet;
		}
		
		public float getGravityPlanet()
		{
			return this.gravity;
		}
		
		public int getPressurePlanet()
		{
			return this.pressure;
		}
		
		public float getTemperaturePlanet()
		{
			return this.temperature;
		}
		
		public float getWindPlanet()
		{
			return this.wind;
		}
		
		public long getDayLengthPlanet()
		{
			return this.day;
		}
		
		public boolean getBreathablePlanet()
		{
			return this.breath;
		}
		
		public boolean getSolarRadiationPlanet()
		{
			return this.solarradiation;
		}
	}
}
