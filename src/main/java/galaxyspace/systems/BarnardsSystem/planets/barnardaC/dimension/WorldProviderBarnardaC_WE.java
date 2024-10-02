package galaxyspace.systems.BarnardsSystem.planets.barnardaC.dimension;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.core.world.worldengine.WE_Biome;
import galaxyspace.core.world.worldengine.WE_ChunkProvider;
import galaxyspace.core.world.worldengine.WE_WorldProvider;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_CaveGen;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_RavineGen;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_TerrainGenerator;
import galaxyspace.systems.BarnardsSystem.BarnardsSystemBodies;
import galaxyspace.systems.BarnardsSystem.core.registers.blocks.BRBlocks;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.dimension.sky.SkyProviderBarnardaC;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.BiomeDecoratorBarnardaC;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.we.Barnarda_C_Beach;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.we.Barnarda_C_DeepOcean;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.we.Barnarda_C_Dunes;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.we.Barnarda_C_Forest;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.we.Barnarda_C_Jungle;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.we.Barnarda_C_Mountains;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.we.Barnarda_C_Ocean;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.we.Barnarda_C_Plains;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.we.Barnarda_C_River;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.we.Barnarda_C_SnowPlains;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.world.gen.we.Barnarda_C_Swampland;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

public class WorldProviderBarnardaC_WE extends WE_WorldProvider{
	    
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
        return BarnardsSystemBodies.barnardaC;
    }

    @Override
    public Class<? extends IChunkProvider> getChunkProviderClass() {
        return WE_ChunkProvider.class;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vector3 getFogColor() {
    	float f = 1.0F - this.getStarBrightness(1.0F);
        return new Vector3(140 / 255.0F * f, 167 / 255.0F * f, 207 / 255.0F * f);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vector3 getSkyColor() {

    	float f = 0.5F - this.getStarBrightness(1.0F);
    	if(worldObj.isRaining())
    	{
    		f = 1.0F;
    		return new Vector3(47 / 255.0F * f, 47 / 255.0F * f, 47 / 255.0F * f);
    	}
    	return new Vector3(61 / 255.0F * f, 86 / 255.0F * f, 175 / 255.0F * f);

    }
    
	@Override
	public boolean isSkyColored() {
		return true;
	}

	@Override
    public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {
       return WorldChunkManagerBarnardaC.class;
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
		return super.getCloudRenderer();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IRenderHandler getSkyRenderer() {
		if (super.getSkyRenderer() == null) {
			this.setSkyRenderer(new SkyProviderBarnardaC());
		}

		return super.getSkyRenderer();
	}
	
	@Override
	public void genSettings(WE_ChunkProvider cp) {
		cp.createChunkGen_List.clear(); 
		cp.createChunkGen_InXZ_List.clear(); 
		cp.createChunkGen_InXYZ_List.clear(); 
		cp.decorateChunkGen_List.clear(); 
		
		WE_Biome.setBiomeMap(cp, 1.4D, 4, 6200.0D, 1.0D);
		
		WE_TerrainGenerator terrainGenerator = new WE_TerrainGenerator(); 
		terrainGenerator.worldStoneBlock = BRBlocks.BarnardaCBlocks; 
		terrainGenerator.worldStoneBlockMeta = 1;
		terrainGenerator.worldSeaGen = true;
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
		
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_DeepOcean(-4D, 4D));	
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Ocean(-3.8D, 3.8D, false));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Beach(-3.5D, 3.5D, 1));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Plains(-3.4D, 3.4D));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Forest(-2.9D, 2.9D));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_River(-2.5D, 2.5D));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Swampland(-2.4D, 2.4D));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Jungle(-1.8D, 1.8D));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Dunes(-1.4D, 1.4D));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Mountains(-1.0D, 1.0D, 100, 2.8D, 4));	
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Mountains(-0.8D, 0.8D, 180, 2.4D, 4));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_SnowPlains(-0.6D, 0.6D, 160));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Mountains(-0.3D, 0.3D, 100, 2.8D, 4));	
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Ocean(-0.0D, 0.0D, true));
	}

	@Override
	public BiomeDecoratorSpace getDecorator() {
		return new BiomeDecoratorBarnardaC();
	}
}
