package galaxyspace.systems.SolarSystem.planets.kuiperbelt.dimension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

import asmodeuscore.core.astronomy.dimension.world.gen.WorldProviderAdvancedSpace;
import galaxyspace.core.util.GSDimensions;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import galaxyspace.systems.SolarSystem.planets.kuiperbelt.dimension.sky.SkyProviderKuiperBelt;
import galaxyspace.systems.SolarSystem.planets.kuiperbelt.world.gen.BiomeProviderKuiperBelt;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.api.world.IZeroGDimension;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.event.EventHandlerGC;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.GCLog;
import micdoodle8.mods.galacticraft.planets.asteroids.entities.EntityAstroMiner;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderKuiperBelt extends WorldProviderAdvancedSpace implements ISolarLevel, IZeroGDimension
{
    //Used to list asteroid centres to external code that needs to know them
    private HashSet<AsteroidData> asteroids = new HashSet<>();
    private boolean dataNotLoaded = true;
    private KuiperBeltSaveData datafile;
    private double solarMultiplier = -1D;

    //	@Override
//	public void registerWorldChunkManager()
//	{
//		this.worldChunkMgr = new WorldChunkManagerAsteroids(this.world, 0F);
//	}

    @Override
    public CelestialBody getCelestialBody()
    {
        return SolarSystemBodies.planetKuiperBelt;
    }

    @Override
    public Vector3 getFogColor()
    {
        return new Vector3(0, 0, 0);
    }

    @Override
    public Vector3 getSkyColor()
    {
        return new Vector3(0, 0, 0);
    }

    @Override
    public boolean hasSunset()
    {
        return false;
    }

    @Override
    public long getDayLength()
    {
        return 0;
    }

    @Override
    public boolean isDaytime()
    {
        return true;
    }

    @Override
    public Class<? extends IChunkGenerator> getChunkProviderClass()
    {
        return ChunkProviderKuiperBelt.class;
    }

    @Override 
    public Class<? extends BiomeProvider> getBiomeProviderClass() { 
    	return BiomeProviderKuiperBelt.class; 
    }
    
    @Override
    public float calculateCelestialAngle(long par1, float par3)
    {
        return 0.22F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1)
    {
        final float var2 = this.world.getCelestialAngle(par1);
        float var3 = 1.0F - (MathHelper.cos(var2 * (float) Math.PI * 2.0F) * 2.0F + 0.25F);

        if (var3 < 0.0F)
        {
            var3 = 0.0F;
        }

        if (var3 > 1.0F)
        {
            var3 = 1.0F;
        }

        return var3 * var3 * 0.5F + 0.3F;
    }

    @Override
    public double getHorizon()
    {
        return 44.0D;
    }

    @Override
    public int getAverageGroundLevel()
    {
        return 96;
    }

    @Override
    public boolean canCoordinateBeSpawn(int var1, int var2)
    {
        return true;
    }

    //Overriding so that beds do not explode on Asteroids
    @Override
    public boolean canRespawnHere()
    {
        if (EventHandlerGC.bedActivated)
        {
            EventHandlerGC.bedActivated = false;
            return true;
        }
        return false;
    }

    @Override
    public double getMeteorFrequency()
    {
        return 10.0D;
    }

    @Override
    public double getFuelUsageMultiplier()
    {
        return 0.9D;
    }

    @Override
    public float getFallDamageModifier()
    {
        return 0.1F;
    }

    @Override
    public IRenderHandler getCloudRenderer(){
        return new CloudRenderer();
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IRenderHandler getSkyRenderer()
    {
    	if (super.getSkyRenderer() == null)
		{
			this.setSkyRenderer(new SkyProviderKuiperBelt());
		}

		return super.getSkyRenderer();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public float getSunBrightness(float par1) {
       float f1 = this.world.getCelestialAngle(1.0F);
       float f2 = 1.25F - (MathHelper.cos(f1 * 3.1415927F * 2.0F) * 2.0F + 0.2F);
       float f3 = this.world.getWorldTime();
       if(f2 < 0.0F) {
          f2 = 0.0F;
       }

       if(f2 > 1.0F) {
          f2 = 1.0F;
       }

       f2 = 1.2F - f2;
       return f2 * 1.0F;
    }
    
    public void addAsteroid(int x, int y, int z, int size, int core)
    {
        AsteroidData coords = new AsteroidData(x, y, z, size, core);
        if (!this.asteroids.contains(coords))
        {
            if (this.dataNotLoaded)
            {
                this.loadAsteroidSavedData();
            }
            if (!this.asteroids.contains(coords))
            {
                this.addToNBT(this.datafile.datacompound, coords);
                this.asteroids.add(coords);
            }
        }
    }

    public void removeAsteroid(int x, int y, int z)
    {
        AsteroidData coords = new AsteroidData(x, y, z);
        if (this.asteroids.contains(coords))
        {
            this.asteroids.remove(coords);

            if (this.dataNotLoaded)
            {
                this.loadAsteroidSavedData();
            }
            this.writeToNBT(this.datafile.datacompound);
        }
    }

    private void loadAsteroidSavedData()
    {
        this.datafile = (KuiperBeltSaveData) this.world.loadData(KuiperBeltSaveData.class, KuiperBeltSaveData.saveDataID);

        if (this.datafile == null)
        {
            this.datafile = new KuiperBeltSaveData("");
            this.world.setData(KuiperBeltSaveData.saveDataID, this.datafile);
            this.writeToNBT(this.datafile.datacompound);
        }
        else
        {
            this.readFromNBT(this.datafile.datacompound);
        }

        this.dataNotLoaded = false;
    }

    private void readFromNBT(NBTTagCompound nbt)
    {
        NBTTagList coordList = nbt.getTagList("coords", 10);
        if (coordList.tagCount() > 0)
        {
            for (int j = 0; j < coordList.tagCount(); j++)
            {
                NBTTagCompound tag1 = coordList.getCompoundTagAt(j);

                if (tag1 != null)
                {
                    this.asteroids.add(AsteroidData.readFromNBT(tag1));
                }
            }
        }
    }

    private void writeToNBT(NBTTagCompound nbt)
    {
        NBTTagList coordList = new NBTTagList();
        for (AsteroidData coords : this.asteroids)
        {
            NBTTagCompound tag = new NBTTagCompound();
            coords.writeToNBT(tag);
            coordList.appendTag(tag);
        }
        nbt.setTag("coords", coordList);
        this.datafile.markDirty();
    }

    private void addToNBT(NBTTagCompound nbt, AsteroidData coords)
    {
        NBTTagList coordList = nbt.getTagList("coords", 10);
        NBTTagCompound tag = new NBTTagCompound();
        coords.writeToNBT(tag);
        coordList.appendTag(tag);
        nbt.setTag("coords", coordList);
        this.datafile.markDirty();
    }


    public boolean checkHasAsteroids()
    {
        if (this.dataNotLoaded)
        {
            this.loadAsteroidSavedData();
        }

        if (this.asteroids.size() == 0)
        {
            return false;
        }
        
        return true;
    }

    public BlockVec3 getClosestAsteroidXZ(int x, int y, int z, boolean mark)
    {
        if (!this.checkHasAsteroids())
        {
            return null;
        }

        BlockVec3 result = null;
        AsteroidData resultRoid = null;
        int lowestDistance = Integer.MAX_VALUE;

        for (AsteroidData test : this.asteroids)
        {
            if (mark && (test.sizeAndLandedFlag & 128) > 0)
            {
                continue;
            }

            int dx = x - test.centre.x;
            int dz = z - test.centre.z;
            int a = dx * dx + dz * dz;
            if (a < lowestDistance)
            {
                lowestDistance = a;
                result = test.centre;
                resultRoid = test;
            }
        }

        if (result == null)
        {
            return null;
        }

        if (mark)
        {
            resultRoid.sizeAndLandedFlag |= 128;
            this.writeToNBT(this.datafile.datacompound);
        }
        result = result.clone();
        result.sideDoneBits = resultRoid.sizeAndLandedFlag & 127;
        return result;
    }

    public ArrayList<BlockVec3> getClosestAsteroidsXZ(int x, int y, int z, int facing, int count)
    {
        if (!this.checkHasAsteroids())
        {
            return null;
        }

        TreeMap<Integer, BlockVec3> targets = new TreeMap<>();

        for (AsteroidData roid : this.asteroids)
        {
            BlockVec3 test = roid.centre;
            switch (facing)
            {
            case 2:
                if (z - 16 < test.z)
                {
                    continue;
                }
                break;
            case 3:
                if (z + 16 > test.z)
                {
                    continue;
                }
                break;
            case 4:
                if (x - 16 < test.x)
                {
                    continue;
                }
                break;
            case 5:
                if (x + 16 > test.x)
                {
                    continue;
                }
                break;
            }
            int dx = x - test.x;
            int dz = z - test.z;
            int a = dx * dx + dz * dz;
            if (a < 262144)
            {
                targets.put(a, test);
            }
        }

        int max = Math.max(count, targets.size());
        if (max <= 0)
        {
            return null;
        }

        ArrayList<BlockVec3> returnValues = new ArrayList<>();
        int i = 0;
        int offset = EntityAstroMiner.MINE_LENGTH_AST / 2;
        for (BlockVec3 target : targets.values())
        {
            BlockVec3 coords = target.clone();
            GCLog.debug("Found nearby asteroid at " + target.toString());
            switch (facing)
            {
            case 2:
                coords.z += offset;
                break;
            case 3:
                coords.z -= offset;
                break;
            case 4:
                coords.x += offset;
                break;
            case 5:
                coords.x -= offset;
                break;
            }
            returnValues.add(coords);
            if (++i >= count)
            {
                break;
            }
        }

        return returnValues;
    }

    @Override
    public int getActualHeight()
    {
        return 256;
    }

    @Override
    protected void init()
    {
        super.init();
        this.nether = true;
    }

    @Override
    public double getSolarEnergyMultiplier()
    {
        if (this.solarMultiplier < 0D)
        {
            double s = this.getSolarSize();
            this.solarMultiplier = s * s * s * ConfigManagerCore.spaceStationEnergyScalar;
        }
        return this.solarMultiplier;
    }

    private static class AsteroidData
    {
        protected BlockVec3 centre;
        protected int sizeAndLandedFlag = 15;
        protected int coreAndSpawnedFlag = -2;

        public AsteroidData(int x, int y, int z)
        {
            this.centre = new BlockVec3(x, y, z);
        }

        public AsteroidData(int x, int y, int z, int size, int core)
        {
            this.centre = new BlockVec3(x, y, z);
            this.sizeAndLandedFlag = size;
            this.coreAndSpawnedFlag = core;
        }

        public AsteroidData(BlockVec3 bv)
        {
            this.centre = bv;
        }

        @Override
        public int hashCode()
        {
            if (this.centre != null)
            {
                return this.centre.hashCode();
            }
            else
            {
                return 0;
            }
        }

        @Override
        public boolean equals(Object o)
        {
            if (o instanceof AsteroidData)
            {
                BlockVec3 vector = ((AsteroidData) o).centre;
                return this.centre.x == vector.x && this.centre.y == vector.y && this.centre.z == vector.z;
            }

            if (o instanceof BlockVec3)
            {
                BlockVec3 vector = (BlockVec3) o;
                return this.centre.x == vector.x && this.centre.y == vector.y && this.centre.z == vector.z;
            }

            return false;
        }

        public NBTTagCompound writeToNBT(NBTTagCompound tag)
        {
            tag.setInteger("x", this.centre.x);
            tag.setInteger("y", this.centre.y);
            tag.setInteger("z", this.centre.z);
            tag.setInteger("coreAndFlag", this.coreAndSpawnedFlag);
            tag.setInteger("sizeAndFlag", this.sizeAndLandedFlag);
            return tag;
        }

        public static AsteroidData readFromNBT(NBTTagCompound tag)
        {
            BlockVec3 tempVector = new BlockVec3();
            tempVector.x = tag.getInteger("x");
            tempVector.y = tag.getInteger("y");
            tempVector.z = tag.getInteger("z");

            AsteroidData roid = new AsteroidData(tempVector);
            if (tag.hasKey("coreAndFlag"))
            {
                roid.coreAndSpawnedFlag = tag.getInteger("coreAndFlag");
            }
            if (tag.hasKey("sizeAndFlag"))
            {
                roid.sizeAndLandedFlag = tag.getInteger("sizeAndFlag");
            }

            return roid;
        }
    }

    @Override
    public int getDungeonSpacing()
    {
        return 0;
        //Used for generating Abandoned Base 
    }

    @Override
    public DimensionType getDimensionType()
    {
        return GSDimensions.KUIPER_BELT;
    }

    @Override
    public float getArrowGravity()
    {
        return 0.002F;
    }

    @Override
    public ResourceLocation getDungeonChestType()
    {
        return null;
    }
    
    @Override
    public boolean hasSkyLight()
    {
        return false;
    }

    @Override
    public List<Block> getSurfaceBlocks()
    {
        return null;
    }

	@Override
	public boolean inFreefall(Entity entity) {
		return true;
	}

	@Override
	public void setInFreefall(Entity entity) {
		
	}

	@Override
	public boolean enableAdvancedThermalLevel() {
		return true;
	}
	
	@Override
	protected float getThermalValueMod()
	{
		return 0.01F;
	}
}
