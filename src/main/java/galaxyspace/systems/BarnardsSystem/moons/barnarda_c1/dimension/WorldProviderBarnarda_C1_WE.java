package galaxyspace.systems.BarnardsSystem.moons.barnarda_c1.dimension;

import asmodeuscore.api.dimension.IProviderFog;
import asmodeuscore.api.dimension.IProviderWeather;
import asmodeuscore.core.astronomy.WeatherData;
import asmodeuscore.core.astronomy.dimension.world.data.FrozenStormSaveData;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_BiomeProvider;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_ChunkProviderSpace;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_WorldProviderSpace;
import asmodeuscore.core.astronomy.dimension.world.worldengine.biome.WE_BaseBiome;
import asmodeuscore.core.utils.worldengine.WE_Biome;
import asmodeuscore.core.utils.worldengine.WE_ChunkProvider;
import asmodeuscore.core.utils.worldengine.standardcustomgen.*;
import galaxyspace.core.configs.GSConfigWorld;
import galaxyspace.core.prefab.world.gen.we.WE_LakesGen;
import galaxyspace.core.util.GSDimensions;
import galaxyspace.systems.BarnardsSystem.BarnardsSystemBodies;
import galaxyspace.systems.BarnardsSystem.core.BRBlocks;
import galaxyspace.systems.BarnardsSystem.moons.barnarda_c1.blocks.Barnarda_C1_Blocks;
import galaxyspace.systems.BarnardsSystem.moons.barnarda_c1.dimension.sky.SkyProviderBarnarda_C1;
import galaxyspace.systems.BarnardsSystem.moons.barnarda_c1.world.gen.WorldGenIcyDripstone;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.dimension.sky.SkyProviderBarnarda_C;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.blocks.BlockBasicMoon;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.world.gen.dungeon.DungeonConfiguration;
import micdoodle8.mods.galacticraft.core.world.gen.dungeon.MapGenDungeon;
import micdoodle8.mods.galacticraft.core.world.gen.dungeon.RoomBoss;
import micdoodle8.mods.galacticraft.core.world.gen.dungeon.RoomTreasure;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class WorldProviderBarnarda_C1_WE extends WE_WorldProviderSpace implements IProviderFog, IProviderWeather {

    private final MapGenDungeon dungeonGeneratorMoon =
            new MapGenDungeon(
                    new DungeonConfiguration(GCBlocks.blockMoon.getDefaultState().withProperty(BlockBasicMoon.BASIC_TYPE_MOON, BlockBasicMoon.EnumBlockBasicMoon.MOON_DUNGEON_BRICK), 25, 8, 16,
                    5, 6, RoomBoss.class, RoomTreasure.class));

    @Override
    public double getHorizon() {
        return 44.0D;
    }

    @Override
    public float getFallDamageModifier() {
        return 0.16F;
    }

    @Override
    public double getFuelUsageMultiplier() {
        return 0.8;
    }

    @Override
    public double getMeteorFrequency() {
        return 0.9;
    }

    @Override
    public float getSoundVolReductionAmount() {
        return Float.MIN_VALUE;
    }

    @Override
    public boolean canSnowAt(BlockPos pos, boolean checkLight) {
        return true;
    }

    @Override
    public CelestialBody getCelestialBody() {
        return BarnardsSystemBodies.Barnarda_C1;
    }

    @Override
    public Class<? extends IChunkGenerator> getChunkProviderClass() {
        return WE_ChunkProviderSpace.class;

    }

    @Override
    public Class<? extends BiomeProvider> getBiomeProviderClass() {
        return WE_BiomeProvider.class;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getCloudHeight() {
        return 180.0F;
    }

    @Override
    public boolean canRespawnHere() {
        return true;
    }

    @Nullable
    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
        return super.calcSunriseSunsetColors(celestialAngle, partialTicks);
      /*  float f = 0.4F;
        float f1 = MathHelper.cos(celestialAngle * ((float)Math.PI * 2F)) - 0.0F;
        float f2 = -0.0F;

        if (f1 >= -0.4F && f1 <= 0.4F)
        {
            float f3 = (f1 - -0.0F) / 0.4F * 0.5F + 0.5F;
            float f4 = 1.0F - (1.0F - MathHelper.sin(f3 * (float)Math.PI)) * 0.99F;
            f4 = f4 * f4;
            this.colorsSunriseSunset[0] = f3 * 0.3F + 0.7F;
            this.colorsSunriseSunset[1] = f3 * f3 * 0.7F + 0.2F;
            this.colorsSunriseSunset[2] = f3 * f3 * 0.0F + 0.2F;
            this.colorsSunriseSunset[3] = f4;
            return this.colorsSunriseSunset;
        }
        else
        {
            return null;
        }*/
    }
    /*
    @Override
    @SideOnly(Side.CLIENT)
    public Vector3 getFogColor() {

        float f = 1.0F - this.getStarBrightness(1.0F);
        return new Vector3(86 / 255.0F * f, 180 / 255.0F * f, 240 / 255.0F * f);
    }

    @Override
    public Vector3 getSkyColor() {
        float f = 0.6F - this.getStarBrightness(1.0F);
        return new Vector3(100 / 255.0F * f, 220 / 255.0F * f, 250 / 255.0F * f);
    }*/

    @Override
    @SideOnly(Side.CLIENT)
    public Vector3 getFogColor() {
        float f = 1.0F - this.getStarBrightness(1.0F);
        FrozenStormSaveData fsd = FrozenStormSaveData.get(this.world, getCelestialBody().getName());

        if (fsd.getStormStrength(1.0F) > 0.0F)
            f = Math.max(0.4F, 1.0F - fsd.getStormStrength(1.0F));

        return new Vector3(160 / 255.0F * f, 167 / 255.0F * f, 190 / 255.0F * f);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vector3 getSkyColor() {

        float f = 0.5F - this.getStarBrightness(1.0F);
        if (world.isRaining()) {
            f = 1.0F;
            return new Vector3(47 / 255.0F * f, 47 / 255.0F * f, 47 / 255.0F * f);
        }
        return new Vector3(161 / 255.0F * f, 146 / 255.0F * f, 175 / 255.0F * f);

    }

    @Override
    public boolean isSkyColored() {
        return true;
    }

    @Override
    public boolean hasSunset() {
        return true;
    }

    @Override
    public boolean shouldForceRespawn() {
        return !ConfigManagerCore.forceOverworldRespawn;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1) {
        float f = this.world.getCelestialAngle(par1);
        float f1 = 1.0F - (MathHelper.cos(f * ((float) Math.PI * 2F)) * 2.0F + 0.25F);
        f1 = MathHelper.clamp(f1, 0.0F, 1.0F);

        FrozenStormSaveData fsd = FrozenStormSaveData.get(this.world, getCelestialBody().getName());
        if (fsd.isFrozenStorm())
            f1 = (float) ((double) f1 * (1.0D - (double) (fsd.getStormStrength(par1) * 5.0F) / 6.0D));

        return f1 * f1 * 0.5F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getSunBrightness(float partialTicks) {

        float f = this.world.getCelestialAngle(partialTicks);
        float f1 = 1.0F - (MathHelper.cos(f * ((float) Math.PI * 2F)) * 2.0F + 0.2F);
        f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
        f1 = 1.0F - f1;
        f1 = (float) ((double) f1 * (1.0D - (double) (this.world.getRainStrength(partialTicks) * 5.0F) / 16.0D));
        f1 = (float) ((double) f1 * (1.0D - (double) (this.world.getThunderStrength(partialTicks) * 5.0F) / 16.0D));

        FrozenStormSaveData fsd = FrozenStormSaveData.get(this.world, getCelestialBody().getName());
        if (fsd.isFrozenStorm())
            f1 = (float) ((double) f1 * (1.0D - (double) (fsd.getStormStrength(partialTicks) * 5.0F) / 6.0D));

        //f2 = 1.2F - f2;
        return f1 * 0.8F + 0.2F;
    }

    @Override
    public IRenderHandler getCloudRenderer() {
        return new CloudRenderer();
    }

    @SideOnly(Side.CLIENT)
    public IRenderHandler getSkyRenderer() {
        if (super.getSkyRenderer() == null) {
            this.setSkyRenderer(new SkyProviderBarnarda_C1());
        }

        return super.getSkyRenderer();
    }

    @Override
    public int getDungeonSpacing() {
        return 704;
    }

    @Override
    public ResourceLocation getDungeonChestType() {
        return RoomTreasure.MOONCHEST;
    }

    @Override
    public List<Block> getSurfaceBlocks() {
        return null;
    }

    @Override
    public DimensionType getDimensionType() {

        return GSDimensions.BARNARDA_C1;
    }

    @Override
    public void genSettings(WE_ChunkProvider cp) {

        cp.createChunkGen_List.clear();
        cp.createChunkGen_InXZ_List.clear();
        cp.createChunkGen_InXYZ_List.clear();
        cp.decorateChunkGen_List.clear();

        WE_Biome.setBiomeMap(cp, 1.2D, 4, 1200.0D, 1.0D);

        WE_TerrainGenerator terrainGenerator = new WE_TerrainGenerator();
        terrainGenerator.worldStoneBlock = BRBlocks.BARNARDA_C1_BLOCKS.getDefaultState().withProperty(Barnarda_C1_Blocks.BASIC_TYPE, Barnarda_C1_Blocks.EnumBlockBarnardaC1.STONE);
        terrainGenerator.worldSeaGen = true;
        terrainGenerator.worldSeaGenBlock = Blocks.ICE.getDefaultState();
        terrainGenerator.worldSeaGenMaxY = 68;
        cp.createChunkGen_List.add(terrainGenerator);

        //-//
        WE_CaveGen cg = new WE_CaveGen();
        cg.replaceBlocksList.clear();
        cg.addReplacingBlock(terrainGenerator.worldStoneBlock);
        cg.lavaMaxY = 0;
        cg.range = 8;
        cp.createChunkGen_List.add(cg);
        //-//

        WE_RavineGen rg = new WE_RavineGen();
        rg.replaceBlocksList.clear();
        rg.addReplacingBlock(terrainGenerator.worldStoneBlock);
        rg.lavaBlock = Blocks.LAVA.getDefaultState();
        rg.lavaMaxY = 15;
        rg.range = 32;
        cp.createChunkGen_List.add(rg);

        WE_LakesGen lakes = new WE_LakesGen();
        lakes.lakeBlock = Blocks.WATER.getDefaultState();
        lakes.chunksForLake = 4;
        lakes.underground = true;
        lakes.maxY = 20;
        cp.decorateChunkGen_List.add(lakes);

        lakes = new WE_LakesGen();
        lakes.lakeBlock = Blocks.LAVA.getDefaultState();
        lakes.chunksForLake = 14;
        lakes.underground = true;
        lakes.maxY = 40;
        cp.decorateChunkGen_List.add(lakes);

        WE_OreGen standardOres = new WE_OreGen();
        standardOres.add(BRBlocks.BARNARDA_C1_BLOCKS.getDefaultState()
                .withProperty(Barnarda_C1_Blocks.BASIC_TYPE, Barnarda_C1_Blocks.EnumBlockBarnardaC1.ICY_SUBSURFACE),
                terrainGenerator.worldStoneBlock, 26, 5, 150, 20);

        standardOres.add(BRBlocks.BARNARDA_C1_BLOCKS.getDefaultState()
                        .withProperty(Barnarda_C1_Blocks.BASIC_TYPE, Barnarda_C1_Blocks.EnumBlockBarnardaC1.SNOWY_SUBSURFACE),
                terrainGenerator.worldStoneBlock, 15, 60, 150, 20);

        standardOres.add(Blocks.ICE.getDefaultState(),
                terrainGenerator.worldStoneBlock, 8, 5, 150, 12);

        if(GSConfigWorld.enableOresGeneration) {
            standardOres.add(BRBlocks.BARNARDA_C1_BLOCKS.getDefaultState()
                            .withProperty(Barnarda_C1_Blocks.BASIC_TYPE, Barnarda_C1_Blocks.EnumBlockBarnardaC1.ONYX_ORE),
                    terrainGenerator.worldStoneBlock, 4, 1, 30, 2);
            standardOres.add(BRBlocks.BARNARDA_C1_BLOCKS.getDefaultState()
                            .withProperty(Barnarda_C1_Blocks.BASIC_TYPE, Barnarda_C1_Blocks.EnumBlockBarnardaC1.IRON_ORE),
                    terrainGenerator.worldStoneBlock, 5, 10, 60, 6);
            standardOres.add(BRBlocks.BARNARDA_C1_BLOCKS.getDefaultState()
                            .withProperty(Barnarda_C1_Blocks.BASIC_TYPE, Barnarda_C1_Blocks.EnumBlockBarnardaC1.COPPER_ORE),
                    terrainGenerator.worldStoneBlock, 6, 30, 120, 10);
            standardOres.add(BRBlocks.BARNARDA_C1_BLOCKS.getDefaultState()
                            .withProperty(Barnarda_C1_Blocks.BASIC_TYPE, Barnarda_C1_Blocks.EnumBlockBarnardaC1.TIN_ORE),
                    terrainGenerator.worldStoneBlock, 6, 30, 120, 8);
        }

        cp.decorateChunkGen_List.add(standardOres);

        ((WE_ChunkProviderSpace) cp).worldGenerators.clear();
        cp.biomesList.clear();

        WE_BiomeLayer layer = new WE_BiomeLayer();
        layer.add(Blocks.BEDROCK.getDefaultState(), 0, 0, 1, 2, true);
        layer.add(BRBlocks.BARNARDA_C1_BLOCKS.getDefaultState().withProperty(Barnarda_C1_Blocks.BASIC_TYPE, Barnarda_C1_Blocks.EnumBlockBarnardaC1.SNOWY_SUBSURFACE), terrainGenerator.worldStoneBlock, -256, 0, -10, -4, true);
        layer.add(BRBlocks.BARNARDA_C1_BLOCKS.getDefaultState().withProperty(Barnarda_C1_Blocks.BASIC_TYPE, Barnarda_C1_Blocks.EnumBlockBarnardaC1.SNOWY_SURFACE), BRBlocks.BARNARDA_C1_BLOCKS.getDefaultState().withProperty(Barnarda_C1_Blocks.BASIC_TYPE, Barnarda_C1_Blocks.EnumBlockBarnardaC1.SNOWY_SUBSURFACE), -256, 0, -2, -1, false);

        WE_BiomeLayer layerIce = new WE_BiomeLayer();
        layerIce.add(Blocks.BEDROCK.getDefaultState(), 0, 0, 1, 2, true);
        layerIce.add(BRBlocks.BARNARDA_C1_BLOCKS.getDefaultState().withProperty(Barnarda_C1_Blocks.BASIC_TYPE, Barnarda_C1_Blocks.EnumBlockBarnardaC1.ICY_SUBSURFACE), terrainGenerator.worldStoneBlock, -256, 0, -10, -4, true);
        layerIce.add(Blocks.PACKED_ICE.getDefaultState(), BRBlocks.BARNARDA_C1_BLOCKS.getDefaultState().withProperty(Barnarda_C1_Blocks.BASIC_TYPE, Barnarda_C1_Blocks.EnumBlockBarnardaC1.ICY_SUBSURFACE), -256, 0, -2, -1, false);

        WorldGenIcyDripstone icyDripstone = new WorldGenIcyDripstone();


        WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(-0.5D, 0D, 1.4F, 4, 60, 80, layer) {
            @Override
            public void decorateBiome(World world, Random rand, int x, int z) {
                for (int i = 0; i < 20; i++) {
                    icyDripstone.generate(world, rand, new BlockPos(x, 0, z));
                }
            }
        });

        WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(0D, 0.3D, 1.5F, 4, 70, 80, layer) {
            @Override
            public void decorateBiome(World world, Random rand, int x, int z) {
                for (int i = 0; i < 150; i++) {
                    int randPosX = x + rand.nextInt(16) + 8;
                    int randPosZ = z + rand.nextInt(16) + 8;
                    BlockPos pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));

                    if (world.isAirBlock(pos) && world.getBlockState(pos.down()) == BRBlocks.BARNARDA_C1_BLOCKS.getDefaultState().withProperty(Barnarda_C1_Blocks.BASIC_TYPE, Barnarda_C1_Blocks.EnumBlockBarnardaC1.SNOWY_SURFACE)) {
                        world.setBlockState(pos, Blocks.SNOW_LAYER.getStateFromMeta(rand.nextInt(3)));
                    }

                }

                for (int i = 0; i < 20; i++) {
                    icyDripstone.generate(world, rand, new BlockPos(x, 0, z));
                }
            }
        });
        WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(0.3D, 0.5D, 2.2F, 4, 90, 140, layer) {
            @Override
            public void decorateBiome(World world, Random rand, int x, int z) {
                for (int i = 0; i < 80; i++) {
                    int randPosX = x + rand.nextInt(16) + 8;
                    int randPosZ = z + rand.nextInt(16) + 8;
                    BlockPos pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));

                    if (world.isAirBlock(pos) && world.getBlockState(pos.down()) == BRBlocks.BARNARDA_C1_BLOCKS.getDefaultState().withProperty(Barnarda_C1_Blocks.BASIC_TYPE, Barnarda_C1_Blocks.EnumBlockBarnardaC1.SNOWY_SURFACE)) {
                        world.setBlockState(pos, Blocks.SNOW_LAYER.getStateFromMeta(rand.nextInt(4)));
                    }

                }

                for (int i = 0; i < 20; i++) {
                    icyDripstone.generate(world, rand, new BlockPos(x, 0, z));
                }
            }
        });
        WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(0.5D, 0.9D, 2.8F, 4, 120, 180, layerIce) {
            @Override
            public void decorateBiome(World world, Random rand, int x, int z) {
                for (int i = 0; i < 60; i++) {
                    int randPosX = x + rand.nextInt(16) + 8;
                    int randPosZ = z + rand.nextInt(16) + 8;
                    BlockPos pos = world.getHeight(new BlockPos(randPosX, 0, randPosZ));

                    if (world.isAirBlock(pos) && world.getBlockState(pos.down()) == BRBlocks.BARNARDA_C1_BLOCKS.getDefaultState().withProperty(Barnarda_C1_Blocks.BASIC_TYPE, Barnarda_C1_Blocks.EnumBlockBarnardaC1.SNOWY_SURFACE)) {
                        world.setBlockState(pos, Blocks.SNOW_LAYER.getStateFromMeta(rand.nextInt(4)));
                    }

                }

                for (int i = 0; i < 20; i++) {
                    icyDripstone.generate(world, rand, new BlockPos(x, 0, z));
                }
            }
        });
        WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(0.9D, 1.8D, 3.8F, 4, 150, 140, layerIce) {
            @Override
            public void decorateBiome(World world, Random rand, int x, int z) {
                for (int i = 0; i < 20; i++) {
                    icyDripstone.generate(world, rand, new BlockPos(x, 0, z));
                }
            }
        });
        /*WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(-0.5D, 0.5D, 2.0F, 6, 150, 3, layer));
        WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(-1.4D, 1.0D, 1.5F, 4, 90, 10, layer));
        WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(-1.0D, 1.5D, 1.5F, 4, 90, 20, layer));
        WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(-2.0D, 2.0D, 1.5F, 4, 40, 3, layer));*/
    }

    @Override
    public boolean enableAdvancedThermalLevel() {
        return true;
    }

    @Override
    protected float getThermalValueMod() {
        return 0.4F;
    }

    @Override
    public void onPopulate(int cX, int cZ) {
        dungeonGeneratorMoon.generateStructure(this.world, this.world.rand, new ChunkPos(cX, cZ));

    }

    @Override
    public void onChunkProvider(int cX, int cZ, ChunkPrimer primer) {
        dungeonGeneratorMoon.generate(this.world, cX, cZ, primer);
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) {
        dungeonGeneratorMoon.generate(this.world, x, z, null);
    }

    @Override
    public float getFogDensity(int x, int y, int z) {
        FrozenStormSaveData fsd = FrozenStormSaveData.get(this.world, getCelestialBody().getName());
        if (fsd.getStormStrength(1.0F) > 0.0F)
            return Math.max(0.3F, 1.0F - fsd.getStormStrength(1.0F));

        return 0.93F;
    }

    @Override
    public WeatherData getWeather() {
        return frozen_storm;
    }
}
