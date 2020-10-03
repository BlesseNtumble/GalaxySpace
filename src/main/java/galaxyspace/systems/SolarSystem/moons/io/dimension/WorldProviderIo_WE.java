package galaxyspace.systems.SolarSystem.moons.io.dimension;

import java.util.List;

import asmodeuscore.api.dimension.IProviderFreeze;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_ChunkProviderSpace;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_WorldProviderSpace;
import asmodeuscore.core.utils.worldengine.WE_Biome;
import asmodeuscore.core.utils.worldengine.WE_ChunkProvider;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_CaveGen;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_RavineGen;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_TerrainGenerator;
import galaxyspace.core.GSBlocks;
import galaxyspace.core.util.GSDimensions;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import galaxyspace.systems.SolarSystem.moons.io.dimension.sky.SkyProviderIo;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.BiomeProviderIo;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon.RoomTreasureIo;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.we.Io_Mountains;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.we.Io_Plains;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.we.Io_Volcano;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderIo_WE extends WE_WorldProviderSpace implements IProviderFreeze{
	public static WE_ChunkProvider chunk;
	
	@Override
    public double getHorizon() { return 44.0D; }

    @Override
    public float getFallDamageModifier() { return 0.16F; }

    @Override
    public double getFuelUsageMultiplier() { return 0.8; }

    @Override
    public double getMeteorFrequency() { return 3.0D; } 

    @Override
    public float getSoundVolReductionAmount() { return Float.MAX_VALUE; }

    @Override
    public CelestialBody getCelestialBody() { return SolarSystemBodies.ioJupiter; }
    
    @Override
    public Class<? extends IChunkGenerator> getChunkProviderClass() {
        return WE_ChunkProviderSpace.class;
    }
    
    @Override 
    public Class<? extends BiomeProvider> getBiomeProviderClass() { 
    	return BiomeProviderIo.class; 
    }
    
    @Override
    public Vector3 getFogColor() {	return new Vector3(0, 0, 0);  }
    
    @Override
    public Vector3 getSkyColor() {	return new Vector3(0, 0, 0);  }
    
    @Override
	public boolean isSkyColored() { return false; }
 
	@Override
	public boolean hasSunset() { return false; }

    @Override
    public boolean shouldForceRespawn() { return !ConfigManagerCore.forceOverworldRespawn; }    
    
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
    public IRenderHandler getCloudRenderer(){ return new CloudRenderer(); }

	@SideOnly(Side.CLIENT)
    public IRenderHandler getSkyRenderer()
    {
    	if (super.getSkyRenderer() == null)
		{
			this.setSkyRenderer(new SkyProviderIo());
		}

		return super.getSkyRenderer();
    }

	@Override
	public int getDungeonSpacing() { return 0; }

	@Override
	public ResourceLocation getDungeonChestType() {	return RoomTreasureIo.IOCHEST; }

	@Override
	public List<Block> getSurfaceBlocks() {	return null; }

	@Override
	public DimensionType getDimensionType() { return GSDimensions.IO; }
	
	@Override
	public void genSettings(WE_ChunkProvider cp) {

		chunk = cp;
		
		cp.createChunkGen_List .clear(); 
		cp.createChunkGen_InXZ_List .clear(); 
		cp.createChunkGen_InXYZ_List.clear(); 
		cp.decorateChunkGen_List .clear(); 

		WE_Biome.setBiomeMap(cp, 1.2D, 6, 850.0D, 0.275D);	
		
		WE_TerrainGenerator terrainGenerator = new WE_TerrainGenerator(); 
		terrainGenerator.worldStoneBlock = GSBlocks.IO_BLOCKS.getStateFromMeta(1); 
		terrainGenerator.worldSeaGen = false;
		cp.createChunkGen_List.add(terrainGenerator);
		
		//-// 
		WE_CaveGen cg = new WE_CaveGen(); 
		cg.replaceBlocksList .clear(); 
		cg.addReplacingBlock(terrainGenerator.worldStoneBlock); 
		cg.lavaBlock = Blocks.LAVA.getDefaultState(); 
		cp.createChunkGen_List.add(cg); 
		//-// 
		 
		WE_RavineGen rg = new WE_RavineGen();
		rg.replaceBlocksList    .clear();
		rg.addReplacingBlock(terrainGenerator.worldStoneBlock);
		rg.lavaBlock = Blocks.LAVA.getDefaultState();
		cp.createChunkGen_List.add(rg);
		
		((WE_ChunkProviderSpace)cp).worldGenerators.clear();
		cp.biomesList.clear();
		
		WE_Biome.addBiomeToGeneration(cp, new Io_Plains());
		WE_Biome.addBiomeToGeneration(cp, new Io_Mountains(0, 1.4D, 1.9D, 120, 35));
		WE_Biome.addBiomeToGeneration(cp, new Io_Mountains(1, 1.9D, 2.4D, 125, 35));
		WE_Biome.addBiomeToGeneration(cp, new Io_Volcano());
	}

	@Override
	public boolean enableAdvancedThermalLevel() {
		return false;
	}

	@Override
	public void onPopulate(int cX, int cZ) {		
	}

	@Override
	public void onChunkProvider(int cX, int cZ, ChunkPrimer primer) {		
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {		
	}
}
