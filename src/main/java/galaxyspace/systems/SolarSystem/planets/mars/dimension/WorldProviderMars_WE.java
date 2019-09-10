package galaxyspace.systems.SolarSystem.planets.mars.dimension;

import java.util.List;

import asmodeuscore.api.dimension.IProviderFreeze;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_ChunkProvider;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_WorldProvider;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_CaveGen;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_RavineGen;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_TerrainGenerator;
import galaxyspace.systems.SolarSystem.planets.mars.dimension.sky.SkyProviderMars;
import galaxyspace.systems.SolarSystem.planets.mars.world.gen.we.Mars_High_Plains;
import galaxyspace.systems.SolarSystem.planets.mars.world.gen.we.Mars_Mountains;
import galaxyspace.systems.SolarSystem.planets.mars.world.gen.we.Mars_Plains;
import galaxyspace.systems.SolarSystem.planets.mars.world.gen.we.Mars_Ravine;
import galaxyspace.systems.SolarSystem.planets.mars.world.gen.we.Mars_Triangle_Mountains;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.planets.GCPlanetDimensions;
import micdoodle8.mods.galacticraft.planets.mars.MarsModule;
import micdoodle8.mods.galacticraft.planets.mars.blocks.MarsBlocks;
import micdoodle8.mods.galacticraft.planets.mars.world.gen.BiomeDecoratorMars;
import micdoodle8.mods.galacticraft.planets.mars.world.gen.BiomeProviderMars;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderMars_WE extends WE_WorldProvider implements IProviderFreeze {
	
    @Override
    public double getHorizon()   { return 44.0D; }

    @Override
    public float getFallDamageModifier() { return 0.16F; }
    
    @Override
    public double getFuelUsageMultiplier() { return 0.8; }

    @Override
    public double getMeteorFrequency() { return 3.0; }
 
    @Override
    public float getSoundVolReductionAmount() { return Float.MAX_VALUE; }

    @Override
    public boolean canRainOrSnow() { return false; } 

    @Override
    public CelestialBody getCelestialBody() { return MarsModule.planetMars; }

    @Override
    public Class<? extends IChunkGenerator> getChunkProviderClass() {
    	return WE_ChunkProvider.class;
    }
    
    @Override 
    public Class<? extends BiomeProvider> getBiomeProviderClass() { 
    	return BiomeProviderMars.class; 
    }
    
    @Override
    public Vector3 getFogColor()
    {
        float f = 1.0F - this.getStarBrightness(1.0F);
        return new Vector3(210F / 255F * f, 120F / 255F * f, 59F / 255F * f);
    }

    @Override
    public Vector3 getSkyColor()
    {
        float f = 1.0F - this.getStarBrightness(1.0F);
        return new Vector3(154 / 255.0F * f, 114 / 255.0F * f, 66 / 255.0F * f);
    }
     
	@Override
	public boolean isSkyColored() { return false; }
 
	@Override
	public boolean hasSunset() { return false; }

    @Override
    public boolean shouldForceRespawn() {
        return !ConfigManagerCore.forceOverworldRespawn;
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

    public IRenderHandler getCloudRenderer(){
        return new CloudRenderer();
    }

	@SideOnly(Side.CLIENT)
    public IRenderHandler getSkyRenderer()
    {
    	if (super.getSkyRenderer() == null)
		{
			this.setSkyRenderer(new SkyProviderMars());
		}
    	
		return super.getSkyRenderer();
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
	public DimensionType getDimensionType() {
	
		return GCPlanetDimensions.MARS;
	}

	@Override
	public void genSettings(WE_ChunkProvider cp) {
		cp.createChunkGen_List .clear(); 
		cp.createChunkGen_InXZ_List .clear(); 
		cp.createChunkGen_InXYZ_List.clear(); 
		cp.decorateChunkGen_List .clear(); 
		
		cp.CRATER_PROB = 400;
		
		WE_Biome.setBiomeMap(cp, 1.2D, 4, 800.0D, 1.0D);	
		
		WE_TerrainGenerator terrainGenerator = new WE_TerrainGenerator(); 
		terrainGenerator.worldStoneBlock = MarsBlocks.marsBlock.getStateFromMeta(9); 
		terrainGenerator.worldSeaGen = false;
		terrainGenerator.worldSeaGenBlock = Blocks.WATER.getDefaultState();
		terrainGenerator.worldSeaGenMaxY = 64;
		cp.createChunkGen_List.add(terrainGenerator);
		
		//-// 
		WE_CaveGen cg = new WE_CaveGen(); 
		cg.replaceBlocksList .clear(); 
		cg.addReplacingBlock(terrainGenerator.worldStoneBlock); 
		cg.lavaMaxY = 0;
		cp.createChunkGen_List.add(cg); 
		//-// 
		 
		WE_RavineGen rg = new WE_RavineGen();
		rg.replaceBlocksList    .clear();
		rg.addReplacingBlock(terrainGenerator.worldStoneBlock);
		rg.addReplacingBlock(MarsBlocks.marsBlock.getStateFromMeta(6));
		rg.addReplacingBlock(MarsBlocks.marsBlock.getStateFromMeta(5));
		rg.addReplacingBlock(Blocks.PACKED_ICE.getDefaultState());
		rg.lavaBlock = Blocks.AIR.getDefaultState();
		rg.lavaMaxY = 0;
		cp.createChunkGen_List.add(rg);
		
		cp.biomesList.clear();
		
		//WE_Biome.addBiomeToGeneration(cp, new Mars_Triangle_Mountains(-1.0D, 0.0D));
		//WE_Biome.addBiomeToGeneration(cp, new Mars_Ravine(-1.0D, 0.0D));
		WE_Biome.addBiomeToGeneration(cp, new Mars_Plains(-1.0D, 0.0D));
		WE_Biome.addBiomeToGeneration(cp, new Mars_High_Plains(-0.5D, 0.5D));
		WE_Biome.addBiomeToGeneration(cp, new Mars_Mountains(-0.0D, 1.0D));
		
	}

	@Override
	public BiomeDecoratorSpace getDecorator() {
		return new BiomeDecoratorMars();
	}

	@Override
	public boolean enableAdvancedThermalLevel() {
		return false;
	}
}
