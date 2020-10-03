package galaxyspace.systems.SolarSystem.moons.europa.dimension;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_ChunkProviderSpace;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_WorldProviderSpace;
import asmodeuscore.core.utils.worldengine.WE_Biome;
import asmodeuscore.core.utils.worldengine.WE_ChunkProvider;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_BiomeLayer;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_TerrainGenerator;
import galaxyspace.core.GSBlocks;
import galaxyspace.core.prefab.world.gen.we.biomes.WE_BaseBiome;
import galaxyspace.core.util.GSDimensions;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import galaxyspace.systems.SolarSystem.moons.europa.dimension.sky.SkyProviderEuropa;
import galaxyspace.systems.SolarSystem.moons.europa.world.gen.we.Europa_HillPlains;
import galaxyspace.systems.SolarSystem.moons.europa.world.gen.we.Europa_Plains;
import galaxyspace.systems.SolarSystem.moons.europa.world.gen.we.Europa_Ravine;
import galaxyspace.systems.SolarSystem.moons.europa.world.gen.we.Europa_Spikes;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderEuropa_WE extends WE_WorldProviderSpace {

	@Override
	public double getFuelUsageMultiplier() {
		return 0;
	}

	@Override
	public float getFallDamageModifier() {
		return 0.5F;
	}

	@Override
    public double getMeteorFrequency() {
        return 3.0;
    }
	
	@Override
    public float getSoundVolReductionAmount() {
        return Float.MAX_VALUE;
    }
		
	@Override
	public CelestialBody getCelestialBody() {
		return SolarSystemBodies.europaJupiter;
	}

	@Override
	public int getDungeonSpacing() {
		return 0;
	}

	@Override
	public ResourceLocation getDungeonChestType() {
		return null;
	}

	@Override
	public boolean hasSunset() {
		return false;
	}

	@Override
	public Vector3 getFogColor() {
		return new Vector3(0, 0, 0);
	}

	@Override
	public Vector3 getSkyColor() {
		return new Vector3(0, 0, 0);
	}
	
	@Override
	public boolean shouldForceRespawn() {
		return !ConfigManagerCore.forceOverworldRespawn;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getCloudRenderer() {
		return new CloudRenderer();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer() {
		if (super.getSkyRenderer() == null) {
			this.setSkyRenderer(new SkyProviderEuropa());
		}

		return super.getSkyRenderer();
	}
	 
	@Override
	public Class<? extends IChunkGenerator> getChunkProviderClass() {
		return null;
	}

	@Override
	public void onChunkProvider(int cX, int cZ, ChunkPrimer primer) {
		
	}

	@Override
	public void onPopulate(int cX, int cZ) {
		
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {		
	}


	@Override
	public boolean isFreeze() {
		return false;
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

        return var3 * var3 * 1.5F + 0.4F;
    }
	
	@Override
	public void genSettings(WE_ChunkProvider cp) {
		cp.createChunkGen_List.clear(); 
		cp.createChunkGen_InXZ_List.clear(); 
		cp.createChunkGen_InXYZ_List.clear(); 
		cp.decorateChunkGen_List.clear(); 
		((WE_ChunkProviderSpace)cp).worldGenerators.clear();
		cp.biomesList.clear();
		
		WE_Biome.setBiomeMap(cp, 1.0D, 4, 1300.0D, 6.0D);
		
		WE_TerrainGenerator terrainGenerator = new WE_TerrainGenerator(); 
		terrainGenerator.worldStoneBlock = Blocks.WATER.getDefaultState();
		terrainGenerator.worldSeaGen = false;
		terrainGenerator.worldSeaGenMaxY = 64;
		cp.createChunkGen_List.add(terrainGenerator);
		

		WE_BiomeLayer layer = new WE_BiomeLayer();
		layer.add(Blocks.PACKED_ICE.getDefaultState(), terrainGenerator.worldStoneBlock, -256, 0,   65, -6,  true);
		layer.add(GSBlocks.SURFACE_ICE.getStateFromMeta(4), Blocks.PACKED_ICE.getDefaultState(), -256, 0,   -2, -1,  true);
		layer.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		
		
		WE_Biome.addBiomeToGeneration(cp, new Europa_Ravine(terrainGenerator));
		WE_Biome.addBiomeToGeneration(cp, new Europa_Plains(terrainGenerator, 0.4D));
		WE_Biome.addBiomeToGeneration(cp, new Europa_HillPlains(terrainGenerator, 1.0D));
		WE_Biome.addBiomeToGeneration(cp, new Europa_Spikes(terrainGenerator));
		WE_Biome.addBiomeToGeneration(cp, new Europa_Plains(terrainGenerator, 5.0D));
		
		
	}

	@Override
	public DimensionType getDimensionType() {
		return GSDimensions.EUROPA;
	}
	
	@Override
	public boolean enableAdvancedThermalLevel() {
		return true;
	}

}
