package galaxyspace.systems.SolarSystem.planets.mars.world;

import galaxyspace.GalaxySpace;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class MarsSaveData extends WorldSavedData {

	private static final String DATA_NAME = GalaxySpace.MODID + "_MarsSaveData";
	
	public boolean isDustStorm;
	public int tickDustStorm = 0;
	public int clearWeatherTime = 24000 * 3;
	
	public MarsSaveData(String name) {
		super(name);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		isDustStorm = nbt.getBoolean("isDustStorm");
		tickDustStorm = nbt.getInteger("tickDustStorm");
		
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("isDustStorm", isDustStorm);
		compound.setInteger("tickDustStorm", tickDustStorm);
		return compound;
	}

	public static MarsSaveData get(World world) {
		MapStorage storage = world.getPerWorldStorage();
		MarsSaveData instance = (MarsSaveData) storage.getOrLoadData(MarsSaveData.class, DATA_NAME);

		if (instance == null) {
			instance = new MarsSaveData(DATA_NAME);
			storage.setData(DATA_NAME, instance);
		}
		return instance;
	}
}
