package galaxyspace.systems.SolarSystem.planets.test.dimension;

import java.util.List;

import asmodeuscore.core.astronomy.dimension.world.gen.WorldProviderAdvancedSpace;
import asmodeuscore.core.utils.worldengine2.util.WE_Interpolation;
import asmodeuscore.core.utils.worldengine2.world.biome.WE_Biome;
import asmodeuscore.core.utils.worldengine2.world.biome.WE_BiomeProvider;
import asmodeuscore.core.utils.worldengine2.world.gen.WE_ChunkProvider;
import asmodeuscore.core.utils.worldengine2.world.gen.custom.WE_TerrainGenerator;
import asmodeuscore.core.utils.worldengine2.world.properties.WE_AbstactProperties.GenReliefConditions.PrimitiveCondition;
import asmodeuscore.core.utils.worldengine2.world.properties.WE_BiomeProperties;
import asmodeuscore.core.utils.worldengine2.world.properties.WE_WorldProperties;
import galaxyspace.core.util.GSDimensions;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderTest_WE2 extends WorldProviderAdvancedSpace {

	private WE_WorldProperties WEWorldProps = new WE_WorldProperties();

	@Override
	public double getMeteorFrequency() {
		return 3.0;
	}

	@Override
	public float getSoundVolReductionAmount() {
		return Float.MAX_VALUE;
	}
	    
	@Override
	public double getFuelUsageMultiplier() {
		return 1;
	}

	@Override
	public float getFallDamageModifier() {
		return 1;
	}

	@Override
	public CelestialBody getCelestialBody() {
		return SolarSystemBodies.planetTest;
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
	public List<Block> getSurfaceBlocks() {
		return null;
	}

	@Override
	public Vector3 getFogColor() {
		 float f = 1.0F - this.getStarBrightness(1.0F);
		 return new Vector3(0F / 255F * f, 0F / 255F * f, 0F / 255F * f);    
	}

	@Override
	public Vector3 getSkyColor() {
		float f = 1.0F - this.getStarBrightness(1.0F);
        return new Vector3(0 / 255.0F * f, 0 / 255.0F * f, 0 / 255.0F * f);
	}

	@Override
	public boolean hasSunset() {
		return false;
	}

	@Override
	protected void init() {
		
		this.hasSkyLight = true;
		WE_TerrainGenerator terraingen = new WE_TerrainGenerator();
		
		WEWorldProps.addReliefLayer(true, 4, 400.0, 400.0, WE_Interpolation.I_VALUEFUNC_SMOOTHERSTEP, 16, 16, WE_Interpolation.I_VALUEFUNC_SMOOTHERSTEP, true);
		WEWorldProps.addMapLayer(false, 0.5, 4, 100.0, 10, 100.0, 0, WE_Interpolation.I_VALUEFUNC_SMOOTHERSTEP);
		WEWorldProps.addCCGXZ(terraingen);
		
		WE_BiomeProperties p1 = new WE_BiomeProperties();
		p1.genConditions.addCon(PrimitiveCondition.PC_ACTION_MOREEQUAL);
		p1.genConditions.getLast().getFirstMath().addNum(0, true);
		p1.genConditions.getLast().getSecondMath().addNum(0, false);
		p1.add(false, 1.0D, 32, 96);
		
		WE_BiomeProperties p2 = new WE_BiomeProperties();
		p2.genConditions.addCon(PrimitiveCondition.PC_ACTION_LESS);
		p2.genConditions.getLast().getFirstMath().addNum(0, true);
		p2.genConditions.getLast().getSecondMath().addNum(0, false);
		p2.add(false, 0.4D, 32, 56);
		
		WE_Biome b1 = new WE_Biome(p1), b2 = new WE_Biome(p2);
		b1.topBlock = galaxyspace.core.GSBlocks.CERES_BLOCKS.getStateFromMeta(0);
		b1.fillerBlock = galaxyspace.core.GSBlocks.CERES_BLOCKS.getStateFromMeta(1);
		
		WEWorldProps.addBiome(b1);
		WEWorldProps.addBiome(b2);
		
		this.biomeProvider = getBiomeProvider();		
	}
	
	@Override
	public IChunkGenerator createChunkGenerator() {
		return new WE_ChunkProvider(world, WEWorldProps);
	}
	
	@Override
	public BiomeProvider getBiomeProvider()
    {
        return new WE_BiomeProvider(world, WEWorldProps);
    }
	
	@Override
    public Class<? extends BiomeProvider> getBiomeProviderClass()
    {
        return null;
    }

	@Override
	public Class<? extends IChunkGenerator> getChunkProviderClass() {
		return null;
	}
	
	@Override
	public DimensionType getDimensionType() {
		return GSDimensions.TEST;
	}

}
