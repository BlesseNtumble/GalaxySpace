package galaxyspace.core.prefab.world.gen;

import galaxyspace.GalaxySpace;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class WorldDataSpace extends WorldSavedData {

	private static final String DATA = GalaxySpace.NAME + "_SpaceData";
	
	public WorldDataSpace() {
	    super(DATA);
	}

	 
	public WorldDataSpace(String name) {
		super(name);
	}

	public static WorldDataSpace get(World world) {
        MapStorage storage = world.getMapStorage();

        if (storage == null)
            throw new IllegalStateException("World#getMapStorage returned null. The following WorldSave failed to save data: " + DATA);

        WorldDataSpace instance = (WorldDataSpace) storage.getOrLoadData(WorldDataSpace.class, DATA);

        if (instance == null) {
            instance = new WorldDataSpace();
            storage.setData(DATA, instance);
        }

        return instance;
    }

	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		return null;
	}

}
