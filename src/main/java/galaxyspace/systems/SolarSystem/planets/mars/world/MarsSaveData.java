package galaxyspace.systems.SolarSystem.planets.mars.world;

import galaxyspace.GalaxySpace;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class MarsSaveData extends WorldSavedData {

	private static final String DATA_NAME = GalaxySpace.MODID + "_MarsSaveData";
	
	public boolean isDustStorm;
	public float prevStormStrength, stormStrength = 0.0F;
	public int tickDustStorm = 0;
	public int clearWeatherTime = 24000 * 3;
	
	public MarsSaveData(String name) {
		super(name);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		isDustStorm = nbt.getBoolean("isDustStorm");
		tickDustStorm = nbt.getInteger("tickDustStorm");
		prevStormStrength = nbt.getFloat("prevStormStrength");
		stormStrength = nbt.getFloat("stormStrength");
		
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("isDustStorm", isDustStorm);
		compound.setInteger("tickDustStorm", tickDustStorm);
		compound.setFloat("prevStormStrength", prevStormStrength);
		compound.setFloat("stormStrength", stormStrength);
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
	
	public float getStormStrength(float delta)
    {
        return this.prevStormStrength + (this.stormStrength - this.prevStormStrength) * delta;
    }
}
