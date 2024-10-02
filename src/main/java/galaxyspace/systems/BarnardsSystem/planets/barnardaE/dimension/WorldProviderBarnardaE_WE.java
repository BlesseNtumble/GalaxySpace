package galaxyspace.systems.BarnardsSystem.planets.barnardaE.dimension;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.core.world.worldengine.WE_Biome;
import galaxyspace.core.world.worldengine.WE_ChunkProvider;
import galaxyspace.core.world.worldengine.WE_WorldProvider;
import galaxyspace.core.world.worldengine.biomes.WE_BaseBiome;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_BiomeLayer;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_CaveGen;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_RavineGen;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_TerrainGenerator;
import galaxyspace.systems.BarnardsSystem.BarnardsSystemBodies;
import galaxyspace.systems.BarnardsSystem.core.registers.blocks.BRBlocks;
import galaxyspace.systems.BarnardsSystem.planets.barnardaE.dimension.sky.SkyProviderBarnardaE;
import galaxyspace.systems.BarnardsSystem.planets.barnardaE.world.gen.BiomeDecoratorBarnardaE;
import galaxyspace.systems.BarnardsSystem.planets.barnardaE.world.gen.MapGenRavineBarnardaE;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

public class WorldProviderBarnardaE_WE extends WE_WorldProvider{
	    
    @Override
	public double getSolarEnergyMultiplier()
	{
		double solarMultiplier = -1D;
		if (solarMultiplier < 0D)
		{
			double s = this.getSolarSize();
			solarMultiplier = s * s * s * ConfigManagerCore.spaceStationEnergyScalar;
		}
		return solarMultiplier;
	}

    @Override
    public double getHorizon() {
        return 44.0D;
    }

    @Override
    public float getFallDamageModifier() {
        return 0.9F;
    }

    @Override
    public double getFuelUsageMultiplier() {
        return 1.0;
    }

    @Override
    public double getMeteorFrequency() {
        return 0.0;
    }

    @Override
    public float getSoundVolReductionAmount() {
        return 0.4F;
    }

    @Override
    public boolean canRainOrSnow() {
        return true;
    }

    @Override
    public boolean canBlockFreeze(int x, int y, int z, boolean byWater)
    {
		return false;
    }
    
    @Override
    public CelestialBody getCelestialBody() {
        return BarnardsSystemBodies.barnardaE;
    }

    @Override
    public Class<? extends IChunkProvider> getChunkProviderClass() {
        return WE_ChunkProvider.class;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vector3 getFogColor() {
    	float f = 0.2F;
        return new Vector3(243 / 255.0F * f, 98 / 255.0F * f, 35 / 255.0F * f);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vector3 getSkyColor() {

    	float f = 0.01F - this.getStarBrightness(1.0F);
    	//float f = 0.05F;
        return new Vector3(255 / 255.0F * f, 130 / 255.0F * f, 67 / 255.0F * f);

    }   
    
	@Override
	public boolean isSkyColored() {
		return true;
	}

	@Override
    public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {
       return WorldChunkManagerBarnardaE.class;
    }

	@Override
	public boolean hasSunset() {
		return true;
	}
	
	@Override 
    @SideOnly(Side.CLIENT)
    public float getCloudHeight()
    {
        return 180.0F;
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public IRenderHandler getCloudRenderer() {		
		return new CloudRenderer();//super.getCloudRenderer();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IRenderHandler getSkyRenderer() {
		if (super.getSkyRenderer() == null) {
			this.setSkyRenderer(new SkyProviderBarnardaE());
		}

		return super.getSkyRenderer();
	}
	
	@Override
	public void genSettings(WE_ChunkProvider cp) {
		cp.createChunkGen_List.clear(); 
		cp.createChunkGen_InXZ_List.clear(); 
		cp.createChunkGen_InXYZ_List.clear(); 
		cp.decorateChunkGen_List.clear(); 
		cp.worldGenerators.clear();
		
		cp.worldGenerators.add(new MapGenRavineBarnardaE());
		
		WE_Biome.setBiomeMap(cp, 1.6D, 4, 1200.0D, 1.0D);
		
		WE_TerrainGenerator terrainGenerator = new WE_TerrainGenerator(); 
		terrainGenerator.worldStoneBlock = BRBlocks.BarnardaEBlocks; 
		terrainGenerator.worldStoneBlockMeta = 1;
		terrainGenerator.worldSeaGen = false;
		terrainGenerator.worldSeaGenBlock = Blocks.water;
		terrainGenerator.worldSeaGenMaxY = 64;
		cp.createChunkGen_List.add(terrainGenerator);
		
		WE_CaveGen cg = new WE_CaveGen(); 
		cg.replaceBlocksList .clear(); 
		cg.replaceBlocksMetaList.clear(); 
		cg.addReplacingBlock(terrainGenerator.worldStoneBlock, (byte)terrainGenerator.worldStoneBlockMeta); 
		cg.lavaBlock = Blocks.lava; 
		cg.lavaMaxY = 15;
		cp.createChunkGen_List.add(cg); 
		
		WE_RavineGen rg = new WE_RavineGen();
		rg.replaceBlocksList    .clear();
		rg.replaceBlocksMetaList.clear();
		rg.addReplacingBlock(terrainGenerator.worldStoneBlock, (byte)terrainGenerator.worldStoneBlockMeta);
		rg.lavaBlock = Blocks.lava;
		rg.lavaMaxY = 15;
		cp.createChunkGen_List.add(rg);
		
		
		WE_BiomeLayer standardBiomeLayers = new WE_BiomeLayer();
		//standardBiomeLayers.add(BRBlocks.sandstone, (byte)0, BRBlocks.BarnardaCBlocks, (byte)1, -256, 0,   -4, -1,  true);
		standardBiomeLayers.add(BRBlocks.BarnardaEBlocks, (byte)0, terrainGenerator.worldStoneBlock, (byte)terrainGenerator.worldStoneBlockMeta, -256, 0, -256,  0, false);
		standardBiomeLayers.add(Blocks.bedrock, (byte)0, 0, 2, 0, 0, true);
		
		WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(-2D, 2D, 2.8D, 4, 120, 15, standardBiomeLayers));	
		WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(-1D, 1D, 1.8D, 4, 90, 15, standardBiomeLayers));	

	}

	@Override
	public BiomeDecoratorSpace getDecorator() {
		return new BiomeDecoratorBarnardaE();
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
       return f2 * 0.25F;
    }
}
