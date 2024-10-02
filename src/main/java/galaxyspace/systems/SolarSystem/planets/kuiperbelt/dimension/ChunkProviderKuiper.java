/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  micdoodle8.mods.galacticraft.api.vector.BlockVec3
 *  micdoodle8.mods.galacticraft.core.blocks.GCBlocks
 *  micdoodle8.mods.galacticraft.core.entities.EntityEvolvedCreeper
 *  micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton
 *  micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider
 *  micdoodle8.mods.galacticraft.core.entities.EntityEvolvedZombie
 *  micdoodle8.mods.galacticraft.core.perlin.NoiseModule
 *  micdoodle8.mods.galacticraft.core.perlin.generator.Billowed
 *  micdoodle8.mods.galacticraft.core.perlin.generator.Gradient
 *  micdoodle8.mods.galacticraft.core.util.ConfigManagerCore
 *  micdoodle8.mods.galacticraft.planets.asteroids.ConfigManagerAsteroids
 *  micdoodle8.mods.galacticraft.planets.asteroids.blocks.AsteroidBlocks
 *  micdoodle8.mods.galacticraft.planets.asteroids.world.gen.SpecialAsteroidBlock
 *  micdoodle8.mods.galacticraft.planets.asteroids.world.gen.SpecialAsteroidBlockHandler
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockAir
 *  net.minecraft.block.BlockFalling
 *  net.minecraft.entity.EnumCreatureType
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.IProgressUpdate
 *  net.minecraft.world.EnumSkyBlock
 *  net.minecraft.world.World
 *  net.minecraft.world.biome.BiomeGenBase$SpawnListEntry
 *  net.minecraft.world.chunk.Chunk
 *  net.minecraft.world.chunk.IChunkProvider
 *  net.minecraft.world.chunk.storage.ExtendedBlockStorage
 *  net.minecraft.world.gen.ChunkProviderGenerate
 *  net.minecraft.world.gen.feature.WorldGenFlowers
 *  net.minecraft.world.gen.feature.WorldGenLakes
 *  net.minecraft.world.gen.feature.WorldGenTallGrass
 *  net.minecraft.world.gen.feature.WorldGenTrees
 */
package galaxyspace.systems.SolarSystem.planets.kuiperbelt.dimension;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.world.gen.GSBiomeGenBase;
import galaxyspace.systems.SolarSystem.moons.europa.entities.EntityEvolvedColdBlaze;
import galaxyspace.systems.SolarSystem.planets.kuiperbelt.dimension.WorldProviderKuiper;
import galaxyspace.systems.SolarSystem.planets.kuiperbelt.world.gen.MapGenSpaceship;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedCreeper;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedZombie;
import micdoodle8.mods.galacticraft.core.perlin.NoiseModule;
import micdoodle8.mods.galacticraft.core.perlin.generator.Billowed;
import micdoodle8.mods.galacticraft.core.perlin.generator.Gradient;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.planets.asteroids.ConfigManagerAsteroids;
import micdoodle8.mods.galacticraft.planets.asteroids.blocks.AsteroidBlocks;
import micdoodle8.mods.galacticraft.planets.asteroids.world.gen.SpecialAsteroidBlock;
import micdoodle8.mods.galacticraft.planets.asteroids.world.gen.SpecialAsteroidBlockHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class ChunkProviderKuiper
extends ChunkProviderGenerate {
    final Block ASTEROID_STONE = AsteroidBlocks.blockBasic;
    final byte ASTEROID_STONE_META_0 = 0;
    final byte ASTEROID_STONE_META_1 = 1;
    final byte ASTEROID_STONE_META_2 = (byte)2;
    final Block ASTEROID_ICE = AsteroidBlocks.blockDenseIce;
    final Block DIRT = Blocks.dirt;
    final byte DIRT_META = 0;
    final Block GRASS = Blocks.grass;
    final byte GRASS_META = 0;
    final Block LIGHT = Blocks.glowstone;
    final byte LIGHT_META = 0;
    final Block TALL_GRASS = Blocks.tallgrass;
    final byte TALL_GRASS_META = 1;
    final Block FLOWER = Blocks.red_flower;
    final Block LAVA = Blocks.lava;
    final byte LAVA_META = 0;
    final Block WATER = Blocks.water;
    final byte WATER_META = 0;
    private final Random rand;
    private final World worldObj;
    private final NoiseModule asteroidDensity;
    private final NoiseModule asteroidTurbulance;
    private final NoiseModule asteroidSkewX;
    private final NoiseModule asteroidSkewY;
    private final NoiseModule asteroidSkewZ;
    private final SpecialAsteroidBlockHandler coreHandler;
    private final SpecialAsteroidBlockHandler shellHandler;
    private static final int CHUNK_SIZE_X = 16;
    private static final int CHUNK_SIZE_Y = 256;
    private static final int CHUNK_SIZE_Z = 16;
    private static final int MAX_ASTEROID_RADIUS = 15;
    private static final int MIN_ASTEROID_RADIUS = 5;
    private static final int MAX_ASTEROID_SKEW = 8;
    private static final int MIN_ASTEROID_Y = 48;
    private static final int MAX_ASTEROID_Y = 208;
    private static final int ASTEROID_CHANCE = 2400;
    private static final int ASTEROID_CORE_CHANCE = 2;
    private static final int ASTEROID_SHELL_CHANCE = 2;
    private static final int MIN_BLOCKS_PER_CHUNK = 50;
    private static final int MAX_BLOCKS_PER_CHUNK = 200;
    private static final int ILMENITE_CHANCE = 400;
    private static final int IRON_CHANCE = 300;
    private static final int ALUMINUM_CHANCE = 250;
    private static final int RANDOM_BLOCK_FADE_SIZE = 32;
    private static final int FADE_BLOCK_CHANCE = 5;
    private static final int NOISE_OFFSET_SIZE = 256;
    private static final float MIN_HOLLOW_SIZE = 0.6f;
    private static final float MAX_HOLLOW_SIZE = 0.8f;
    private static final int HOLLOW_CHANCE = 10;
    private static final int MIN_RADIUS_FOR_HOLLOW = 15;
    private static final float HOLLOW_LAVA_SIZE = 0.12f;
    private static final int TREE_CHANCE = 2;
    private static final int TALL_GRASS_CHANCE = 2;
    private static final int FLOWER_CHANCE = 2;
    private static final int WATER_CHANCE = 2;
    private static final int LAVA_CHANCE = 2;
    private static final int GLOWSTONE_CHANCE = 20;
    private ArrayList<AsteroidData> largeAsteroids = new ArrayList();
    private int largeCount = 0;
    private static HashSet<BlockVec3> chunksDone = new HashSet();
    private int largeAsteroidsLastChunkX;
    private int largeAsteroidsLastChunkZ;
    private final MapGenSpaceship spaceship = new MapGenSpaceship();

    public ChunkProviderKuiper(World par1World, long par2, boolean par4) {
        super(par1World, par2, par4);
        this.worldObj = par1World;
        this.rand = new Random(par2);
        this.asteroidDensity = new Billowed(this.rand.nextLong(), 2, 0.25f);
        this.asteroidDensity.setFrequency(0.009f);
        this.asteroidDensity.amplitude = 0.6f;
        this.asteroidTurbulance = new Gradient(this.rand.nextLong(), 1, 0.2f);
        this.asteroidTurbulance.setFrequency(0.08f);
        this.asteroidTurbulance.amplitude = 0.5f;
        this.asteroidSkewX = new Gradient(this.rand.nextLong(), 1, 1.0f);
        this.asteroidSkewX.amplitude = 8.0f;
        this.asteroidSkewX.frequencyX = 0.005f;
        this.asteroidSkewY = new Gradient(this.rand.nextLong(), 1, 1.0f);
        this.asteroidSkewY.amplitude = 8.0f;
        this.asteroidSkewY.frequencyY = 0.005f;
        this.asteroidSkewZ = new Gradient(this.rand.nextLong(), 1, 1.0f);
        this.asteroidSkewZ.amplitude = 8.0f;
        this.asteroidSkewZ.frequencyZ = 0.005f;
        this.coreHandler = new SpecialAsteroidBlockHandler();
        this.coreHandler.addBlock(new SpecialAsteroidBlock(this.ASTEROID_STONE, this.ASTEROID_STONE_META_2, 5, 0.3));
        ((Object)((Object)this)).getClass();
        this.coreHandler.addBlock(new SpecialAsteroidBlock(this.ASTEROID_STONE, (byte) 1, 7, 0.3));
        ((Object)((Object)this)).getClass();
        this.coreHandler.addBlock(new SpecialAsteroidBlock(this.ASTEROID_STONE, (byte) 0, 11, 0.25));
        this.coreHandler.addBlock(new SpecialAsteroidBlock(GSBlocks.Ores, (byte) 0, 3, 0.13));
        this.coreHandler.addBlock(new SpecialAsteroidBlock(GSBlocks.Ores, (byte) 1, 2, 0.5));
        this.coreHandler.addBlock(new SpecialAsteroidBlock(GSBlocks.Ores, (byte) 2, 1, 0.3));
        this.coreHandler.addBlock(new SpecialAsteroidBlock(GSBlocks.Ores, (byte) 3, 1, 0.1));
        this.coreHandler.addBlock(new SpecialAsteroidBlock(GSBlocks.CeresBlocks, (byte) 2, 3, 0.14));
        this.shellHandler = new SpecialAsteroidBlockHandler();
        ((Object)((Object)this)).getClass();
        this.shellHandler.addBlock(new SpecialAsteroidBlock(this.ASTEROID_STONE, (byte) 0, 1, 0.15));
        ((Object)((Object)this)).getClass();
        this.shellHandler.addBlock(new SpecialAsteroidBlock(this.ASTEROID_STONE, (byte) 1, 3, 0.15));
        this.shellHandler.addBlock(new SpecialAsteroidBlock(this.ASTEROID_STONE, this.ASTEROID_STONE_META_2, 1, 0.15));
        this.shellHandler.addBlock(new SpecialAsteroidBlock(AsteroidBlocks.blockDenseIce, (byte) 0, 1, 0.15));
    }

    public void generateTerrain(int chunkX, int chunkZ, Block[] idArray, byte[] metaArray, boolean flagDataOnly) {
        this.largeAsteroids.clear();
        this.largeCount = 0;
        Random random = new Random();
        int asteroidChance = 2400;
        int rangeY = 160;
        int rangeSize = 10;
        for (int i = chunkX - 3; i < chunkX + 3; ++i) {
            int minX = i * 16;
            int maxX = minX + 16;
            for (int k = chunkZ - 3; k < chunkZ + 3; ++k) {
                int minZ = k * 16;
                int maxZ = minZ + 16;
                for (int x = minX; x < maxX; x += 2) {
                    for (int z = minZ; z < maxZ; z += 2) {
                        if (!((double)this.randFromPointPos(x, z) < ((double)this.asteroidDensity.getNoise((float)x, (float)z) + 0.4) / 2400.0)) continue;
                        random.setSeed(x + z * 3067);
                        int y = random.nextInt(160) + 48;
                        int size = random.nextInt(10) + 5;
                        this.generateAsteroid(random, x, y, z, chunkX << 4, chunkZ << 4, size, idArray, metaArray, flagDataOnly);
                        ++this.largeCount;
                    }
                }
            }
        }
    }

    private void generateAsteroid(Random rand, int asteroidX, int asteroidY, int asteroidZ, int chunkX, int chunkZ, int size, Block[] blockArray, byte[] metaArray, boolean flagDataOnly) {
        int indexBase;
        int distanceZ;
        float sizeY;
        int indexBaseX;
        int distanceX;
        int indexXZ;
        int indexXY;
        int x;
        SpecialAsteroidBlock core = this.coreHandler.getBlock(rand, size);
        SpecialAsteroidBlock shell = null;
        if (rand.nextInt(2) == 0) {
            shell = this.shellHandler.getBlock(rand, size);
        }
        boolean isHollow = false;
        float hollowSize = rand.nextFloat() * 0.19999999f + 0.6f;
        if (rand.nextInt(10) == 0 && size >= 15) {
            isHollow = true;
            shell = new SpecialAsteroidBlock(AsteroidBlocks.blockDenseIce, (byte) 0, 1, 0.15);
        }
        ((WorldProviderKuiper)this.worldObj.provider).addAsteroid(asteroidX, asteroidY, asteroidZ, size, isHollow ? -1 : core.index);
        int xMin = this.clamp(Math.max(chunkX, asteroidX - size - 8 - 2) - chunkX, 0, 16);
        int zMin = this.clamp(Math.max(chunkZ, asteroidZ - size - 8 - 2) - chunkZ, 0, 16);
        int yMin = asteroidY - size - 8 - 2;
        int yMax = asteroidY + size + 8 + 2;
        int xMax = this.clamp(Math.min(chunkX + 16, asteroidX + size + 8 + 2) - chunkX, 0, 16);
        int zMax = this.clamp(Math.min(chunkZ + 16, asteroidZ + size + 8 + 2) - chunkZ, 0, 16);
        int xSize = xMax - xMin;
        int ySize = yMax - yMin;
        int zSize = zMax - zMin;
        if (xSize <= 0 || ySize <= 0 || zSize <= 0) {
            return;
        }
        float noiseOffsetX = this.randFromPoint(asteroidX, asteroidY, asteroidZ) * 256.0f + (float)chunkX;
        float noiseOffsetY = this.randFromPoint(asteroidX * 7, asteroidY * 11, asteroidZ * 13) * 256.0f;
        float noiseOffsetZ = this.randFromPoint(asteroidX * 17, asteroidY * 23, asteroidZ * 29) * 256.0f + (float)chunkZ;
        this.setOtherAxisFrequency(1.0f / ((float)size * 2.0f / 2.0f));
        float[] sizeXArray = new float[ySize * zSize];
        float[] sizeZArray = new float[xSize * ySize];
        float[] sizeYArray = new float[xSize * zSize];
        for (int x2 = 0; x2 < xSize; ++x2) {
            int xx = x2 * zSize;
            float xxx = (float)x2 + noiseOffsetX;
            for (int z = 0; z < zSize; ++z) {
                sizeYArray[xx + z] = this.asteroidSkewY.getNoise(xxx, (float)z + noiseOffsetZ);
            }
        }
        AsteroidData asteroidData = new AsteroidData(isHollow, sizeYArray, xMin, zMin, xMax, zMax, zSize, size, asteroidX, asteroidY, asteroidZ);
        this.largeAsteroids.add(asteroidData);
        this.largeAsteroidsLastChunkX = chunkX;
        this.largeAsteroidsLastChunkZ = chunkZ;
        if (flagDataOnly) {
            return;
        }
        for (int y = 0; y < ySize; ++y) {
            int yy = y * zSize;
            float yyy = (float)y + noiseOffsetY;
            for (int z = 0; z < zSize; ++z) {
                sizeXArray[yy + z] = this.asteroidSkewX.getNoise(yyy, (float)z + noiseOffsetZ);
            }
        }
        for (int x3 = 0; x3 < xSize; ++x3) {
            int xx = x3 * ySize;
            float xxx = (float)x3 + noiseOffsetX;
            for (int y = 0; y < ySize; ++y) {
                sizeZArray[xx + y] = this.asteroidSkewZ.getNoise(xxx, (float)y + noiseOffsetY);
            }
        }
        double shellThickness = 0.0;
        int terrainY = 0;
        int terrainYY = 0;
        if (shell != null) {
            shellThickness = 1.0 - shell.thickness;
        }
        for (x = xMax - 1; x >= xMin; --x) {
            indexXY = (x - xMin) * ySize - yMin;
            indexXZ = (x - xMin) * zSize - zMin;
            distanceX = asteroidX - (x + chunkX);
            indexBaseX = x * 256 << 4;
            float xx = x + chunkX;
            for (int z = zMin; z < zMax; ++z) {
                if (isHollow) {
                    float sizeModY = sizeYArray[indexXZ + z];
                    terrainY = this.getTerrainHeightFor(sizeModY, asteroidY, size);
                    terrainYY = this.getTerrainHeightFor(sizeModY, asteroidY - 1, size);
                }
                sizeY = (float)size + sizeYArray[indexXZ + z];
                sizeY *= sizeY;
                distanceZ = asteroidZ - (z + chunkZ);
                indexBase = indexBaseX | z * 256;
                float zz = z + chunkZ;
                for (int y = yMin; y < yMax; ++y) {
                    int index;
                    float distance;
                    float dSizeX = (float)distanceX / ((float)size + sizeXArray[(y - yMin) * zSize + z - zMin]);
                    float dSizeZ = (float)distanceZ / ((float)size + sizeZArray[indexXY + y]);
                    dSizeX *= dSizeX;
                    dSizeZ *= dSizeZ;
                    int distanceY = asteroidY - y;
                    distanceY *= distanceY;
                    float distanceAbove = distance = dSizeX + (float)distanceY / sizeY + dSizeZ;
                    distance += this.asteroidTurbulance.getNoise(xx, (float)y, zz);
                    if (isHollow && distance <= hollowSize && (distanceAbove += this.asteroidTurbulance.getNoise(xx, (float)(y + 1), zz)) <= 1.0f && y - 1 == terrainYY) {
                        index = indexBase | y + 1;
                        blockArray[index] = this.LIGHT;
                        ((Object)((Object)this)).getClass();
                        metaArray[index] = 0;
                    }
                    if (!(distance <= 1.0f)) continue;
                    index = indexBase | y;
                    if (isHollow && distance <= hollowSize) {
                        if (y == terrainY) {
                            blockArray[index] = this.GRASS;
                            ((Object)((Object)this)).getClass();
                            metaArray[index] = 0;
                            continue;
                        }
                        if (y < terrainY) {
                            blockArray[index] = this.DIRT;
                            ((Object)((Object)this)).getClass();
                            metaArray[index] = 0;
                            continue;
                        }
                        blockArray[index] = Blocks.air;
                        metaArray[index] = 0;
                        continue;
                    }
                    if ((double)distance <= core.thickness) {
                        if (rand.nextBoolean()) {
                            blockArray[index] = core.block;
                            metaArray[index] = core.meta;
                            continue;
                        }
                        blockArray[index] = this.ASTEROID_STONE;
                        ((Object)((Object)this)).getClass();
                        metaArray[index] = 0;
                        continue;
                    }
                    if (shell != null && (double)distance >= shellThickness) {
                        blockArray[index] = shell.block;
                        metaArray[index] = shell.meta;
                        continue;
                    }
                    blockArray[index] = this.ASTEROID_ICE;
                    ((Object)((Object)this)).getClass();
                    metaArray[index] = 1;
                }
            }
        }
        if (isHollow) {
            shellThickness = 0.0;
            if (shell != null) {
                shellThickness = 1.0 - shell.thickness;
            }
            for (x = xMin; x < xMax; ++x) {
                indexXY = (x - xMin) * ySize - yMin;
                indexXZ = (x - xMin) * zSize - zMin;
                distanceX = asteroidX - (x + chunkX);
                distanceX *= distanceX;
                indexBaseX = x * 256 << 4;
                for (int z = zMin; z < zMax; ++z) {
                    float sizeModY = sizeYArray[indexXZ + z];
                    sizeY = (float)size + sizeYArray[indexXZ + z];
                    sizeY *= sizeY;
                    distanceZ = asteroidZ - (z + chunkZ);
                    distanceZ *= distanceZ;
                    indexBase = indexBaseX | z * 256;
                    for (int y = yMin; y < yMax; ++y) {
                        float sizeX = (float)size + sizeXArray[(y - yMin) * zSize + z - zMin];
                        float sizeZ = (float)size + sizeZArray[indexXY + y];
                        sizeX *= sizeX;
                        sizeZ *= sizeZ;
                        int distanceY = asteroidY - y;
                        distanceY *= distanceY;
                        float distance = (float)distanceX / sizeX + (float)distanceY / sizeY + (float)distanceZ / sizeZ;
                        if (!((distance += this.asteroidTurbulance.getNoise((float)(x + chunkX), (float)y, (float)(z + chunkZ))) <= 1.0f)) continue;
                        int index = indexBase | y;
                        int indexAbove = indexBase | y + 1;
                        if (Blocks.air != blockArray[indexAbove] || blockArray[index] != this.ASTEROID_STONE && blockArray[index] != this.GRASS || this.rand.nextInt(20) != 0) continue;
                        blockArray[index] = this.LIGHT;
                        ((Object)((Object)this)).getClass();
                        metaArray[index] = 0;
                    }
                }
            }
        }
    }

    private final void setOtherAxisFrequency(float frequency) {
        this.asteroidSkewX.frequencyY = frequency;
        this.asteroidSkewX.frequencyZ = frequency;
        this.asteroidSkewY.frequencyX = frequency;
        this.asteroidSkewY.frequencyZ = frequency;
        this.asteroidSkewZ.frequencyX = frequency;
        this.asteroidSkewZ.frequencyY = frequency;
    }

    private final int clamp(int x, int min, int max) {
        if (x < min) {
            x = min;
        } else if (x > max) {
            x = max;
        }
        return x;
    }

    private final double clamp(double x, double min, double max) {
        if (x < min) {
            x = min;
        } else if (x > max) {
            x = max;
        }
        return x;
    }

    private final int getTerrainHeightFor(float yMod, int asteroidY, int asteroidSize) {
        return (int)((float)(asteroidY - asteroidSize / 4) + yMod * 1.5f);
    }

    private final int getTerrainHeightAt(int x, int z, float[] yModArray, int xMin, int zMin, int zSize, int asteroidY, int asteroidSize) {
        int index = (x - xMin) * zSize - zMin;
        if (index < yModArray.length && index >= 0) {
            float yMod = yModArray[index];
            return this.getTerrainHeightFor(yMod, asteroidY, asteroidSize);
        }
        return 1;
    }

    public Chunk provideChunk(int par1, int par2) {
        this.rand.setSeed((long)par1 * 341873128712L + (long)par2 * 132897987541L);
        Block[] ids = new Block[65536];
        byte[] meta = new byte[65536];
        this.generateTerrain(par1, par2, ids, meta, false);
        this.spaceship.generate((IChunkProvider)this, this.worldObj, par1, par2, ids, meta);
        Chunk var4 = new Chunk(this.worldObj, ids, meta, par1, par2);
        byte[] var5 = var4.getBiomeArray();
        for (int var6 = 0; var6 < var5.length; ++var6) {
            var5[var6] = (byte)GSBiomeGenBase.GSSpace.biomeID;
        }
        this.generateSkylightMap(var4, par1, par2);
        return var4;
    }

    private int getIndex(int x, int y, int z) {
        return x * 256 * 16 | z * 256 | y;
    }

    private String timeString(long time1, long time2) {
        int ms100 = (int)((time2 - time1) / 10000L);
        int msdecimal = ms100 % 100;
        String msd = (ms100 < 10 ? "0" : "") + ms100;
        return "" + ms100 / 100 + "." + msd + "ms";
    }

    private float randFromPoint(int x, int y, int z) {
        int n = x + z * 57 + y * 571;
        n ^= n << 13;
        n = n * (n * n * 15731 + 789221) + 1376312589 & Integer.MAX_VALUE;
        return 1.0f - (float)n / 1.07374182E9f;
    }

    private float randFromPoint(int x, int z) {
        int n = x + z * 57;
        n ^= n << 13;
        n = n * (n * n * 15731 + 789221) + 1376312589 & Integer.MAX_VALUE;
        return 1.0f - (float)n / 1.07374182E9f;
    }

    private float randFromPointPos(int x, int z) {
        int n = x + z * 57;
        n ^= n << 13;
        n = n * (n * n * 15731 + 789221) + 1376312589 & 0x3FFFFFFF;
        return 1.0f - (float)n / 1.07374182E9f;
    }

    public boolean chunkExists(int par1, int par2) {
        return true;
    }

    public void populate(IChunkProvider par1IChunkProvider, int chunkX, int chunkZ) {
        long var9;
        long var7;
        int z;
        int x;
        block24: {
            x = chunkX << 4;
            z = chunkZ << 4;
            if (!chunksDone.add(new BlockVec3(x, 0, z))) {
                return;
            }
            BlockFalling.fallInstantly = true;
            this.worldObj.getBiomeGenForCoords(x + 16, z + 16);
            BlockFalling.fallInstantly = false;
            this.rand.setSeed(this.worldObj.getSeed());
            var7 = this.rand.nextLong() / 2L * 2L + 1L;
            var9 = this.rand.nextLong() / 2L * 2L + 1L;
            this.rand.setSeed((long)chunkX * var7 + (long)chunkZ * var9 ^ this.worldObj.getSeed());
            if (!this.rand.nextBoolean()) break block24;
            double density = (double)this.asteroidDensity.getNoise((float)(chunkX * 16), (float)(chunkZ * 16)) * 0.54;
            double numOfBlocks = this.clamp(this.randFromPoint(chunkX, chunkZ), 0.4, 1.0) * 200.0 * density + 50.0;
            int y0 = this.rand.nextInt(2);
            int yRange = 160;
            int i = 0;
            while ((double)i < numOfBlocks) {
                block25: {
                    int meta;
                    Block block;
                    int pz;
                    int px;
                    int y;
                    block27: {
                        block28: {
                            block26: {
                                y = this.rand.nextInt(yRange) + 48;
                                if (y0 != y / 16 % 2) break block25;
                                px = x + this.rand.nextInt(16);
                                pz = z + this.rand.nextInt(16);
                                block = this.ASTEROID_ICE;
                                ((Object)((Object)this)).getClass();
                                meta = 1;
                                if (this.rand.nextInt(400) != 0) break block26;
                                meta = 4;
                                if (!ConfigManagerAsteroids.disableIlmeniteGen) break block27;
                                break block25;
                            }
                            if (this.rand.nextInt(300) != 0) break block28;
                            meta = 5;
                            if (!ConfigManagerAsteroids.disableIronGen) break block27;
                            break block25;
                        }
                        if (this.rand.nextInt(250) != 0) break block27;
                        meta = 3;
                        if (ConfigManagerAsteroids.disableAluminumGen) break block25;
                    }
                    this.worldObj.setBlock(px, y, pz, block, meta, 2);
                    int count = 9;
                    if (!(this.worldObj.getBlock(px - 1, y, pz) instanceof BlockAir)) {
                        count = 1;
                    } else if (!(this.worldObj.getBlock(px - 2, y, pz) instanceof BlockAir)) {
                        count = 3;
                    } else if (!(this.worldObj.getBlock(px - 3, y, pz) instanceof BlockAir)) {
                        count = 5;
                    } else if (!(this.worldObj.getBlock(px - 4, y, pz) instanceof BlockAir)) {
                        count = 7;
                    }
                    this.worldObj.setLightValue(EnumSkyBlock.Block, px, y - 1, pz, count);
                }
                ++i;
            }
        }
        if (this.largeAsteroidsLastChunkX != chunkX || this.largeAsteroidsLastChunkZ != chunkZ) {
            this.generateTerrain(chunkX, chunkZ, null, null, true);
        }
        this.rand.setSeed((long)chunkX * var7 + (long)chunkZ * var9 ^ this.worldObj.getSeed());
        if (!this.largeAsteroids.isEmpty()) {
            for (AsteroidData asteroidIndex : this.largeAsteroids) {
                int i;
                if (!asteroidIndex.isHollow) continue;
                float[] sizeYArray = asteroidIndex.sizeYArray;
                int xMin = asteroidIndex.xMinArray;
                int zMin = asteroidIndex.zMinArray;
                int zSize = asteroidIndex.zSizeArray;
                int asteroidY = asteroidIndex.asteroidYArray;
                int asteroidSize = asteroidIndex.asteroidSizeArray;
                boolean treesdone = false;
                if (ConfigManagerCore.challengeMode || this.rand.nextInt(2) == 0) {
                    int k;
                    int i2;
                    int treeType = this.rand.nextInt(3);
                    if (treeType == 1) {
                        treeType = 0;
                    }
                    WorldGenTrees wg = new WorldGenTrees(false, 2, 0, 0, false);
                    for (int tries = 0; tries < 5 && !wg.generate(this.worldObj, this.rand, i2 = this.rand.nextInt(16) + x + 8, this.getTerrainHeightAt(i2 - x, (k = this.rand.nextInt(16) + z + 8) - z, sizeYArray, xMin, zMin, zSize, asteroidY, asteroidSize), k); ++tries) {
                    }
                    treesdone = true;
                }
                if (!treesdone || this.rand.nextInt(2) == 0) {
                    i = this.rand.nextInt(16) + x + 8;
                    int k = this.rand.nextInt(16) + z + 8;
                    ((Object)((Object)this)).getClass();
                    new WorldGenTallGrass(this.TALL_GRASS, 1).generate(this.worldObj, this.rand, i, this.getTerrainHeightAt(i - x, k - z, sizeYArray, xMin, zMin, zSize, asteroidY, asteroidSize), k);
                }
                if (this.rand.nextInt(2) == 0) {
                    i = this.rand.nextInt(16) + x + 8;
                    int k = this.rand.nextInt(16) + z + 8;
                    new WorldGenFlowers(this.FLOWER).generate(this.worldObj, this.rand, i, this.getTerrainHeightAt(i - x, k - z, sizeYArray, xMin, zMin, zSize, asteroidY, asteroidSize), k);
                }
                if (this.rand.nextInt(2) == 0) {
                    i = this.rand.nextInt(16) + x + 8;
                    int k = this.rand.nextInt(16) + z + 8;
                    new WorldGenLakes(this.LAVA).generate(this.worldObj, this.rand, i, this.getTerrainHeightAt(i - x, k - z, sizeYArray, xMin, zMin, zSize, asteroidY, asteroidSize), k);
                }
                if (this.rand.nextInt(2) != 0) continue;
                i = this.rand.nextInt(16) + x + 8;
                int k = this.rand.nextInt(16) + z + 8;
                new WorldGenLakes(this.WATER).generate(this.worldObj, this.rand, i, this.getTerrainHeightAt(i - x, k - z, sizeYArray, xMin, zMin, zSize, asteroidY, asteroidSize), k);
            }
        }
        for (int xx = 0; xx < 16; ++xx) {
            int xPos = x + xx;
            for (int zz = 0; zz < 16; ++zz) {
                int zPos = z + zz;
                for (int y = 16; y < 240; ++y) {
                    this.worldObj.updateLightByType(EnumSkyBlock.Block, xPos, y, zPos);
                }
            }
        }
    }

    public void generateSkylightMap(Chunk chunk, int cx, int cz) {
        World w = chunk.worldObj;
        boolean flagXChunk = w.getChunkProvider().chunkExists(cx - 1, cz);
        boolean flagZUChunk = w.getChunkProvider().chunkExists(cx, cz + 1);
        boolean flagZDChunk = w.getChunkProvider().chunkExists(cx, cz - 1);
        boolean flagXZUChunk = w.getChunkProvider().chunkExists(cx - 1, cz + 1);
        boolean flagXZDChunk = w.getChunkProvider().chunkExists(cx - 1, cz - 1);
        for (int j = 0; j < 16; ++j) {
            if (chunk.getBlockStorageArray()[j] != null) continue;
            chunk.getBlockStorageArray()[j] = new ExtendedBlockStorage(j << 4, false);
        }
        int i = chunk.getTopFilledSegment();
        chunk.heightMapMinimum = Integer.MAX_VALUE;
        for (int j = 0; j < 16; ++j) {
            block2: for (int k = 0; k < 16; ++k) {
                chunk.precipitationHeightMap[j + (k << 4)] = -999;
                for (int y = i + 15; y > 0; --y) {
                    if (chunk.func_150808_b(j, y - 1, k) == 0) {
                        continue;
                    }
                    chunk.heightMap[k << 4 | j] = y;
                    if (y >= chunk.heightMapMinimum) continue block2;
                    chunk.heightMapMinimum = y;
                    continue block2;
                }
            }
        }
        for (AsteroidData a : this.largeAsteroids) {
            int yMin = a.asteroidYArray - a.asteroidSizeArray;
            int yMax = a.asteroidYArray + a.asteroidSizeArray;
            int xMin = a.xMinArray;
            if (yMin < 0) {
                yMin = 0;
            }
            if (yMax > 255) {
                yMax = 255;
            }
            if (xMin == 0) {
                xMin = 1;
            }
            for (int x = a.xMax - 1; x >= xMin; --x) {
                for (int z = a.zMinArray; z < a.zMax; ++z) {
                    for (int y = yMin; y < yMax; ++y) {
                        if (!(chunk.getBlock(x - 1, y, z) instanceof BlockAir) || chunk.getBlock(x, y, z) instanceof BlockAir) continue;
                        int count = 2;
                        if (x > 1 && chunk.getBlock(x - 2, y, z) instanceof BlockAir) {
                            count += 2;
                        }
                        if (x > 2) {
                            if (chunk.getBlock(x - 3, y, z) instanceof BlockAir) {
                                count += 2;
                            }
                            if (chunk.getBlock(x - 3, y + 1, z) instanceof BlockAir) {
                                ++count;
                            }
                            if (chunk.getBlock(x - 3, y + 1, z) instanceof BlockAir) {
                                ++count;
                            }
                            if (z > 0 && chunk.getBlock(x - 3, y, z - 1) instanceof BlockAir) {
                                ++count;
                            }
                            if (z < 15 && chunk.getBlock(x - 3, y, z + 1) instanceof BlockAir) {
                                ++count;
                            }
                        }
                        if (x > 3) {
                            if (chunk.getBlock(x - 4, y, z) instanceof BlockAir) {
                                count += 2;
                            }
                            if (chunk.getBlock(x - 4, y + 1, z) instanceof BlockAir) {
                                ++count;
                            }
                            if (chunk.getBlock(x - 4, y + 1, z) instanceof BlockAir) {
                                ++count;
                            }
                            if (z > 0 && !(chunk.getBlock(x - 4, y, z - 1) instanceof BlockAir)) {
                                ++count;
                            }
                            if (z < 15 && !(chunk.getBlock(x - 4, y, z + 1) instanceof BlockAir)) {
                                ++count;
                            }
                        }
                        if (count > 12) {
                            count = 12;
                        }
                        chunk.func_150807_a(x - 1, y & 0xF, z, GCBlocks.brightAir, 13 - count);
                        ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[y >> 4];
                        if (extendedblockstorage == null) continue;
                        extendedblockstorage.setExtBlocklightValue(x - 1, y & 0xF, z, count + 2);
                    }
                }
            }
        }
        chunk.isModified = true;
    }

    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate) {
        return true;
    }

    public boolean canSave() {
        return true;
    }

    public String makeString() {
        return "RandomLevelSource";
    }

    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int i, int j, int k) {
        if (par1EnumCreatureType == EnumCreatureType.monster) {
            ArrayList<BiomeGenBase.SpawnListEntry> monsters = new ArrayList<BiomeGenBase.SpawnListEntry>();
            monsters.add(new BiomeGenBase.SpawnListEntry(EntityEvolvedZombie.class, 3000, 1, 3));
            monsters.add(new BiomeGenBase.SpawnListEntry(EntityEvolvedSpider.class, 2000, 1, 2));
            monsters.add(new BiomeGenBase.SpawnListEntry(EntityEvolvedSkeleton.class, 1500, 1, 1));
            monsters.add(new BiomeGenBase.SpawnListEntry(EntityEvolvedCreeper.class, 2000, 1, 1));
            monsters.add(new BiomeGenBase.SpawnListEntry(EntityEvolvedColdBlaze.class, 2000, 1, 1));
            return monsters;
        }
        return null;
    }

    public BlockVec3 isLargeAsteroidAt(int x0, int z0) {
        for (int i0 = 0; i0 <= 32; ++i0) {
            for (int i1 = -i0; i1 <= i0; ++i1) {
                int xToCheck = (x0 >> 4) + i0;
                int zToCheck = (z0 >> 4) + i1;
                if (this.isLargeAsteroidAt0(xToCheck * 16, zToCheck * 16)) {
                    return new BlockVec3(xToCheck * 16, 0, zToCheck * 16);
                }
                xToCheck = (x0 >> 4) + i0;
                zToCheck = (z0 >> 4) - i1;
                if (this.isLargeAsteroidAt0(xToCheck * 16, zToCheck * 16)) {
                    return new BlockVec3(xToCheck * 16, 0, zToCheck * 16);
                }
                xToCheck = (x0 >> 4) - i0;
                zToCheck = (z0 >> 4) + i1;
                if (this.isLargeAsteroidAt0(xToCheck * 16, zToCheck * 16)) {
                    return new BlockVec3(xToCheck * 16, 0, zToCheck * 16);
                }
                xToCheck = (x0 >> 4) - i0;
                zToCheck = (z0 >> 4) - i1;
                if (!this.isLargeAsteroidAt0(xToCheck * 16, zToCheck * 16)) continue;
                return new BlockVec3(xToCheck * 16, 0, zToCheck * 16);
            }
        }
        return null;
    }

    private boolean isLargeAsteroidAt0(int x0, int z0) {
        for (int x = x0; x < x0 + 16; x += 2) {
            for (int z = z0; z < z0 + 16; z += 2) {
                if (!((double)Math.abs(this.randFromPoint(x, z)) < ((double)this.asteroidDensity.getNoise((float)x, (float)z) + 0.4) / 2400.0)) continue;
                return true;
            }
        }
        return false;
    }

    private class AsteroidData {
        public boolean isHollow;
        public float[] sizeYArray;
        public int xMinArray;
        public int zMinArray;
        public int xMax;
        public int zMax;
        public int zSizeArray;
        public int asteroidSizeArray;
        public int asteroidXArray;
        public int asteroidYArray;
        public int asteroidZArray;

        public AsteroidData(boolean hollow, float[] sizeYArray2, int xMin, int zMin, int xmax, int zmax, int zSize, int size, int asteroidX, int asteroidY, int asteroidZ) {
            this.isHollow = hollow;
            this.sizeYArray = (float[])sizeYArray2.clone();
            this.xMinArray = xMin;
            this.zMinArray = zMin;
            this.xMax = xmax;
            this.zMax = zmax;
            this.zSizeArray = zSize;
            this.asteroidSizeArray = size;
            this.asteroidXArray = asteroidX;
            this.asteroidYArray = asteroidY;
            this.asteroidZArray = asteroidZ;
        }
    }
}

