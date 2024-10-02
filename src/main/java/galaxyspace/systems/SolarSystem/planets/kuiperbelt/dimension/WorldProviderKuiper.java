package galaxyspace.systems.SolarSystem.planets.kuiperbelt.dimension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.TreeMap;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.api.dimension.IAdvancedSpace;
import galaxyspace.core.world.gen.WorldProviderAdvancedSpace;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import galaxyspace.systems.SolarSystem.planets.kuiperbelt.dimension.sky.SkyProviderKuiper;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.api.world.ITeleportType;
import micdoodle8.mods.galacticraft.api.world.IZeroGDimension;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.event.EventHandlerGC;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.GCLog;
import micdoodle8.mods.galacticraft.planets.asteroids.blocks.AsteroidBlocks;
import micdoodle8.mods.galacticraft.planets.asteroids.entities.EntityAstroMiner;
import micdoodle8.mods.galacticraft.planets.asteroids.entities.EntityEntryPod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.client.IRenderHandler;

public class WorldProviderKuiper extends WorldProviderAdvancedSpace implements IZeroGDimension, ISolarLevel, ITeleportType, IAdvancedSpace
{
    
    //Used to list asteroid centres to external code that needs to know them
    private HashSet<AsteroidData> asteroids = new HashSet();
    private boolean dataNotLoaded = true;
    private KuiperSaveData datafile;
	private double solarMultiplier = -1D;
	public boolean doSpinning = true;
	private int pjumpticks = 0;
    private float angularVelocityRadians = 0F;
    private float skyAngularVelocity = (float) (this.angularVelocityRadians * 180 / Math.PI);
    private float angularVelocityTarget = 0F;
    private float angularVelocityAccel = 0F;
    private double spinCentreX;
    private double spinCentreZ;
    private double pPrevMotionX = 0D;
    private double pPrevMotionY = 0D;
    private double pPrevMotionZ = 0D;
    private boolean pWasOnGround = false;
    private int ssBoundsMaxX;
    private int ssBoundsMinX;
    private int ssBoundsMaxY;
    private int ssBoundsMinY;
    private int ssBoundsMaxZ;
    private int ssBoundsMinZ;

    //	@Override
//	public void registerWorldChunkManager()
//	{
//		this.worldChunkMgr = new WorldChunkManagerAsteroids(this.worldObj, 0F);
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
    public boolean canRainOrSnow()
    {
        return false;
    }

    @Override
    public boolean hasSunset()
    {
        return false;
    }

    public boolean isDaytime()
    {
        return true;
    }

    @Override
    public Class<? extends IChunkProvider> getChunkProviderClass()
    {
        return ChunkProviderKuiper.class;
    }

    @Override
    public Class<? extends WorldChunkManager> getWorldChunkManagerClass()
    {
        return WorldChunkManagerKuiper.class;
    }

    @Override
    public boolean shouldForceRespawn()
    {
        return !ConfigManagerCore.forceOverworldRespawn;
    }

    
    
    @Override
    public float calculateCelestialAngle(long par1, float par3)
    {
        return 0.24F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1)
    {
        return 1.0F;
    }

//	@Override
//	public IChunkProvider createChunkGenerator()
//	{
//		return new ChunkProviderAsteroids(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled());
//	}

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

	//Overriding only in case the Galacticraft API is not up-to-date
    //(with up-to-date API this makes zero difference)
    @Override
    public boolean isSurfaceWorld()
    {
        return (this.worldObj == null) ? false : this.worldObj.isRemote;
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
	
	//Overriding only in case the Galacticraft API is not up-to-date
    //(with up-to-date API this makes zero difference)
    @Override
    public int getRespawnDimension(EntityPlayerMP player)
    {
        return this.shouldForceRespawn() ? this.dimensionId : 0;
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
    public float getSoundVolReductionAmount()
    {
        return 10.0F;
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
        this.datafile = (KuiperSaveData) this.worldObj.loadItemData(KuiperSaveData.class, KuiperSaveData.saveDataID);

        if (this.datafile == null)
        {
            this.datafile = new KuiperSaveData("");
            this.worldObj.setItemData(KuiperSaveData.saveDataID, this.datafile);
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

    public BlockVec3 getClosestAsteroidXZ(int x, int y, int z)
    {
        if (this.dataNotLoaded)
        {
            this.loadAsteroidSavedData();
        }

        if (this.asteroids.size() == 0)
        {
            return null;
        }

        BlockVec3 result = null;
        AsteroidData resultRoid = null;
        int lowestDistance = Integer.MAX_VALUE;

        for (AsteroidData test : this.asteroids)
        {
            if ((test.sizeAndLandedFlag & 128) > 0)
            	continue;

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
        	return null;
        
        resultRoid.sizeAndLandedFlag |= 128;
        this.writeToNBT(this.datafile.datacompound);
        return result.clone();
    }

    public ArrayList<BlockVec3> getClosestAsteroidsXZ(int x, int y, int z, int facing, int count)
    {
        if (this.dataNotLoaded)
        {
            this.loadAsteroidSavedData();
        }

        if (this.asteroids.size() == 0)
        {
            return null;
        }

        TreeMap<Integer, BlockVec3> targets = new TreeMap();
        
        for (AsteroidData roid : this.asteroids)
        {
            BlockVec3 test = roid.centre;
        	switch (facing)
            {
            case 2:
            	if (z - 16 < test.z)
            		continue;
            	break;
            case 3:
            	if (z + 16 > test.z)
            		continue;
            	break;
            case 4:
            	if (x - 16 < test.x)
            		continue;
            	break;
            case 5:
            	if (x + 16 > test.x)
            		continue;
            	break;
            }
        	int dx = x - test.x;
            int dz = z - test.z;
            int a = dx * dx + dz * dz;
            if (a < 262144) targets.put(a, test);
        }

        int max = Math.max(count,  targets.size());
        if (max <= 0) return null;
        
        ArrayList<BlockVec3> returnValues = new ArrayList();
        int i = 0;
        int offset = EntityAstroMiner.MINE_LENGTH_AST / 2;
        for (BlockVec3 target : targets.values())
        {
        	BlockVec3 coords = target.clone();
        	GCLog.debug("Found nearby asteroid at "+ target.toString());
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
        	if (++i >= count) break; 
        }
        
        return returnValues;
    }

    @Override
    public int getActualHeight()
    {
        return 256;
    }

    @Override
    public void registerWorldChunkManager()
    {
        super.registerWorldChunkManager();
        this.hasNoSky = true;
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
	    		return this.centre.hashCode();
	    	else
	    		return 0;
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
	    		roid.coreAndSpawnedFlag = tag.getInteger("coreAndFlag");
	    	if (tag.hasKey("sizeAndFlag"))
	    		roid.sizeAndLandedFlag = tag.getInteger("sizeAndFlag");

	    	return roid;
	    }
	}

	@Override
	public boolean useParachute() {

		return false;
	}

    @Override
    public Vector3 getPlayerSpawnLocation(WorldServer world, EntityPlayerMP player)
    {
        if (player != null)
        {
            GCPlayerStats stats = GCPlayerStats.get(player);
            int x = MathHelper.floor_double(stats.coordsTeleportedFromX);
            int z = MathHelper.floor_double(stats.coordsTeleportedFromZ);
            int limit = ConfigManagerCore.otherPlanetWorldBorders - 2;
            if (limit > 20)
            {
                if (x > limit)
                {
                    z *= limit / x;
                    x = limit;
                }
                else if (x < -limit)
                {   
                    z *= -limit / x;
                    x = -limit;
                }
                if (z > limit)
                {
                    x *= limit / z;
                    z = limit;
                }
                else if (z < -limit)
                {
                    x *= - limit / z;
                    z = -limit;
                }
            }

            int attemptCount = 0;

            //Small pre-generate with a chunk loading radius of 3, to make sure some asteroids get generated
            //(if the world is already generated here, this will be very quick)
            this.preGenChunks(world, x >> 4, z >> 4);

            do
            {
                BlockVec3 bv3 = null;
                if (world.provider instanceof WorldProviderKuiper)
                {
                    bv3 = ((WorldProviderKuiper) world.provider).getClosestAsteroidXZ(x, 0, z);
                }

                if (bv3 != null)
                {
                    //Check whether the returned asteroid is too far from the desired entry location in which case, give up
                    if (bv3.distanceSquared(new BlockVec3(x, 128, z)) > 25600)
                    {
                        break;
                    }

                    if (ConfigManagerCore.enableDebug)
                    {
                        GCLog.info("Testing asteroid at x" + (bv3.x) + " y" + (bv3.y) + " z" + bv3.z);
                    }
                    this.loadChunksAround(bv3.x, bv3.z, 2, world.theChunkProviderServer);
                    this.loadChunksAround(bv3.x, bv3.z, -3, world.theChunkProviderServer);

                    if (goodAsteroidEntry(world, bv3.x, bv3.y, bv3.z))
                    {
                        return new Vector3(bv3.x, 310, bv3.z);
                    }
                    if (goodAsteroidEntry(world, bv3.x + 2, bv3.y, bv3.z + 2))
                    {
                        return new Vector3(bv3.x + 2, 310, bv3.z + 2);
                    }
                    if (goodAsteroidEntry(world, bv3.x + 2, bv3.y, bv3.z - 2))
                    {
                        return new Vector3(bv3.x + 2, 310, bv3.z - 2);
                    }
                    if (goodAsteroidEntry(world, bv3.x - 2, bv3.y, bv3.z - 2))
                    {
                        return new Vector3(bv3.x - 2, 310, bv3.z - 2);
                    }
                    if (goodAsteroidEntry(world, bv3.x - 2, bv3.y, bv3.z + 2))
                    {
                        return new Vector3(bv3.x - 2, 310, bv3.z + 2);
                    }

                    //Failed to find an asteroid even though there should be one there
                    if (ConfigManagerCore.enableDebug)
                    {
                        GCLog.info("Removing drilled out asteroid at x" + (bv3.x) + " z" + (bv3.z));
                    }
                    ((WorldProviderKuiper) world.provider).removeAsteroid(bv3.x, bv3.y, bv3.z);
                }

                attemptCount++;
            }
            while (attemptCount < 5);

            FMLLog.info("Failed to find good large asteroid landing spot! Falling back to making a small one.");
            this.makeSmallLandingSpot(world, x, z);
            return new Vector3(x, 310, z);
        }

        FMLLog.severe("Null player when teleporting to Asteroids!");
        return new Vector3(0, 310, 0);
    }

    private boolean goodAsteroidEntry(World world, int x, int yorig, int z)
    {
        for (int k = 208; k > 48; k--)
        {
            if (!world.isAirBlock(x, k, z))
            {
                if (Math.abs(k - yorig) > 20)
                {
                    continue;
                }
                //Clear the downward path of small asteroids and any other asteroid rock
                for (int y = k + 2; y < 256; y++)
                {
                    if (world.getBlock(x, y, z) == AsteroidBlocks.blockBasic)
                    {
                        world.setBlockToAir(x, y, z);
                    }
                    if (world.getBlock(x - 1, y, z) == AsteroidBlocks.blockBasic)
                    {
                        world.setBlockToAir(x - 1, y, z);
                    }
                    if (world.getBlock(x, y, z - 1) == AsteroidBlocks.blockBasic)
                    {
                        world.setBlockToAir(x, y, z - 1);
                    }
                    if (world.getBlock(x - 1, y, z - 1) == AsteroidBlocks.blockBasic)
                    {
                        world.setBlockToAir(x - 1, y, z - 1);
                    }
                }
                if (ConfigManagerCore.enableDebug)
                {
                    GCLog.info("Found asteroid at x" + (x) + " z" + (z));
                }
                return true;
            }
        }
        return false;
    }

    private void makeSmallLandingSpot(World world, int x, int z)
    {
        this.loadChunksAround(x, z, -1, (ChunkProviderServer) world.getChunkProvider());

        for (int k = 255; k > 48; k--)
        {
            if (!world.isAirBlock(x, k, z))
            {
                this.makePlatform(world, x, k - 1, z);
                return;
            }
            if (!world.isAirBlock(x - 1, k, z))
            {
                this.makePlatform(world, x - 1, k - 1, z);
                return;
            }
            if (!world.isAirBlock(x - 1, k, z - 1))
            {
                this.makePlatform(world, x - 1, k - 1, z - 1);
                return;
            }
            if (!world.isAirBlock(x, k, z - 1))
            {
                this.makePlatform(world, x, k - 1, z - 1);
                return;
            }
        }

        this.makePlatform(world, x, 48 + world.rand.nextInt(128), z);
        return;
    }

    private void loadChunksAround(int x, int z, int i, ChunkProviderServer cp)
    {
        cp.loadChunk(x >> 4, z >> 4);
        if ((x + i) >> 4 != x >> 4)
        {
            cp.loadChunk((x + i) >> 4, z >> 4);
            if ((z + i) >> 4 != z >> 4)
            {
                cp.loadChunk(x >> 4, (z + i) >> 4);
                cp.loadChunk((x + i) >> 4, (z + i) >> 4);
            }
        }
        else if ((z + i) >> 4 != z >> 4)
        {
            cp.loadChunk(x >> 4, (z + i) >> 4);
        }
    }

    private void makePlatform(World world, int x, int y, int z)
    {
        for (int xx = -3; xx < 3; xx++)
        {
            for (int zz = -3; zz < 3; zz++)
            {
                if (xx == -3 && (zz == -3 || zz == 2))
                {
                    continue;
                }
                if (xx == 2 && (zz == -3 || zz == 2))
                {
                    continue;
                }
                doBlock(world, x + xx, y, z + zz);
            }
        }
        for (int xx = -2; xx < 2; xx++)
        {
            for (int zz = -2; zz < 2; zz++)
            {
                doBlock(world, x + xx, y - 1, z + zz);
            }
        }
        doBlock(world, x - 1, y - 2, z - 1);
        doBlock(world, x - 1, y - 2, z);
        doBlock(world, x, y - 2, z);
        doBlock(world, x, y - 2, z - 1);
    }

    private void doBlock(World world, int x, int y, int z)
    {
        int meta = (int) (world.rand.nextFloat() * 1.5F);
        if (world.isAirBlock(x, y, z))
        {
            world.setBlock(x, y, z, AsteroidBlocks.blockBasic, meta, 2);
        }
    }

    @Override
    public Vector3 getEntitySpawnLocation(WorldServer world, Entity entity)
    {
        return new Vector3(entity.posX, ConfigManagerCore.disableLander ? 250.0 : 900.0, entity.posZ);
    }

    @Override
    public Vector3 getParaChestSpawnLocation(WorldServer world, EntityPlayerMP player, Random rand)
    {
        return null;
    }

    private void preGenChunks(World w, int cx, int cz)
    {
        this.preGenChunk(w, cx, cz);
        for (int r = 1; r < 3; r++)
        {
            int xmin = cx - r;
            int xmax = cx + r;
            int zmin = cz - r;
            int zmax = cz + r;
            for (int i = -r; i < r; i++)
            {
                this.preGenChunk(w, xmin, cz + i);
                this.preGenChunk(w, xmax, cz - i);
                this.preGenChunk(w, cx - i, zmin);
                this.preGenChunk(w, cx + i, zmax);
            }
        }
    }

    private void preGenChunk(World w, int chunkX, int chunkZ)
    {
        w.getChunkFromChunkCoords(chunkX, chunkZ);
    }

    @Override
    public void onSpaceDimensionChanged(World newWorld, EntityPlayerMP player, boolean ridingAutoRocket)
    {
        if (!ridingAutoRocket && player != null)
        {
            GCPlayerStats stats = GCPlayerStats.get(player);
            
            if (stats.teleportCooldown <= 0)
            {
                if (player.capabilities.isFlying)
                {
                    player.capabilities.isFlying = false;
                }

                if (!newWorld.isRemote)
                {
                    EntityEntryPod entryPod = new EntityEntryPod(player);

                    newWorld.spawnEntityInWorld(entryPod);
                }

                stats.teleportCooldown = 10;
            }
        }
    }

	@Override
	public void setupAdventureSpawn(EntityPlayerMP player)
	{
       
	}

	@Override
	public double getSolarWindMultiplier() 
	{
		double solarMultiplier = -1D;
		if (solarMultiplier < 0D)
		{
			double s = this.getSolarSize();
			solarMultiplier = s * s * s * ConfigManagerCore.spaceStationEnergyScalar;
		}
		if(!getSkyColor().isZero()) return 0D;
		return solarMultiplier;
	}
	
    @Override
    public IRenderHandler getCloudRenderer(){
        return new CloudRenderer();
    }

    @Override
	@SideOnly(Side.CLIENT)
    public IRenderHandler getSkyRenderer()
    {
    	if (super.getSkyRenderer() == null)
		{
			this.setSkyRenderer(new SkyProviderKuiper());
		}

		return super.getSkyRenderer();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public float getSunBrightness(float par1) {
       float f1 = this.worldObj.getCelestialAngle(1.0F);
       float f2 = 1.25F - (MathHelper.cos(f1 * 3.1415927F * 2.0F) * 2.0F + 0.2F);
       float f3 = this.worldObj.getWorldTime();
       if(f2 < 0.0F) {
          f2 = 0.0F;
       }

       if(f2 > 1.0F) {
          f2 = 1.0F;
       }

       f2 = 1.2F - f2;
       return f2 * 1.0F;
    }
}
