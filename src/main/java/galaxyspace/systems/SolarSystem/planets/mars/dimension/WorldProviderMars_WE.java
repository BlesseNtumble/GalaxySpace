package galaxyspace.systems.SolarSystem.planets.mars.dimension;

import java.util.List;

import javax.annotation.Nullable;

import asmodeuscore.api.dimension.IProviderFreeze;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_ChunkProviderSpace;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_WorldProviderSpace;
import asmodeuscore.core.utils.worldengine.WE_Biome;
import asmodeuscore.core.utils.worldengine.WE_ChunkProvider;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_BiomeLayer;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_CaveGen;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_RavineGen;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_TerrainGenerator;
import galaxyspace.core.prefab.world.gen.we.biomes.WE_BaseBiome;
import galaxyspace.systems.SolarSystem.planets.mars.dimension.sky.SkyProviderMars;
import galaxyspace.systems.SolarSystem.planets.mars.world.gen.we.Mars_High_Plains;
import galaxyspace.systems.SolarSystem.planets.mars.world.gen.we.Mars_Mountains;
import galaxyspace.systems.SolarSystem.planets.mars.world.gen.we.Mars_Plains;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.world.gen.dungeon.DungeonConfiguration;
import micdoodle8.mods.galacticraft.core.world.gen.dungeon.MapGenDungeon;
import micdoodle8.mods.galacticraft.planets.GCPlanetDimensions;
import micdoodle8.mods.galacticraft.planets.mars.MarsModule;
import micdoodle8.mods.galacticraft.planets.mars.blocks.BlockBasicMars;
import micdoodle8.mods.galacticraft.planets.mars.blocks.MarsBlocks;
import micdoodle8.mods.galacticraft.planets.mars.world.gen.BiomeDecoratorMars;
import micdoodle8.mods.galacticraft.planets.mars.world.gen.BiomeProviderMars;
import micdoodle8.mods.galacticraft.planets.mars.world.gen.MapGenCavernMars;
import micdoodle8.mods.galacticraft.planets.mars.world.gen.MapGenDungeonMars;
import micdoodle8.mods.galacticraft.planets.mars.world.gen.RoomBossMars;
import micdoodle8.mods.galacticraft.planets.mars.world.gen.RoomTreasureMars;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderMars_WE extends WE_WorldProviderSpace implements IProviderFreeze {
	
	private final MapGenDungeon dungeonGenerator = new MapGenDungeonMars(new DungeonConfiguration(MarsBlocks.marsBlock.getDefaultState().withProperty(BlockBasicMars.BASIC_TYPE, BlockBasicMars.EnumBlockBasic.DUNGEON_BRICK), 30, 8, 16, 7, 7, RoomBossMars.class, RoomTreasureMars.class));
	private final float[] colorsSunriseSunset = new float[4];
	
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
    public CelestialBody getCelestialBody() { return MarsModule.planetMars; }

    @Override
    public Class<? extends IChunkGenerator> getChunkProviderClass() {
    	return WE_ChunkProviderSpace.class;
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

        return var3 * var3 * 0.5F + 0.1F;
    }
    
    @Nullable
    @SideOnly(Side.CLIENT)
    @Override
    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks)
    {
        float f1 = MathHelper.cos(celestialAngle * ((float)Math.PI * 2F)) - 0.0F;

        //GalaxySpace.debug("" + f1);
        if (f1 >= 0.05F && f1 <= 0.4F)
        {
            float f3 = (f1 - -0.0F) / 0.4F * 0.5F + 0.6F;
            float f4 = 1.0F - (1.0F - MathHelper.sin(f3 * (float)Math.PI)) * 0.99F;
            f4 = f4 * f4;
            this.colorsSunriseSunset[0] = f3 * 0.3F + 0.35F;
            this.colorsSunriseSunset[1] = f3 * f3 * 0.7F + 0.2F;
            this.colorsSunriseSunset[2] = f3 * f3 * 0.0F + 1.0F;
            this.colorsSunriseSunset[3] = f4;
            return this.colorsSunriseSunset;
        }
        else
        {
            return null;
        }
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
		return 704;
	}

	@Override
	public ResourceLocation getDungeonChestType() {
		return RoomTreasureMars.MARSCHEST;
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
		
		((WE_ChunkProviderSpace)cp).CRATER_PROB = 2000;
		
		WE_Biome.setBiomeMap(cp, 1.5D, 4, 1200.0D, 1.0D);	
		
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
		//cp.createChunkGen_List.add(rg);
		
		MapGenCavernMars cavernGenerator = new MapGenCavernMars();
		((WE_ChunkProviderSpace)cp).worldGenerators.clear();
		((WE_ChunkProviderSpace)cp).worldGenerators.add(cavernGenerator);
		
		cp.biomesList.clear();
		
		//WE_Biome.addBiomeToGeneration(cp, new Mars_Triangle_Mountains(-1.0D, 0.0D));
		//WE_Biome.addBiomeToGeneration(cp, new Mars_Ravine(-1.0D, 0.0D));
		WE_BiomeLayer layer = new WE_BiomeLayer();
		layer.add(MarsBlocks.marsBlock.getStateFromMeta(6), MarsBlocks.marsBlock.getStateFromMeta(9), -256, 0,   -4, -6,  true);
		layer.add(MarsBlocks.marsBlock.getStateFromMeta(5), MarsBlocks.marsBlock.getStateFromMeta(6), -256, 0,   -2, -1,  true);
		layer.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		
		WE_BiomeLayer mount_layer = new WE_BiomeLayer();
		mount_layer.add(MarsBlocks.marsBlock.getStateFromMeta(6), MarsBlocks.marsBlock.getStateFromMeta(9), -256, 0,   -4, -6,  true);
		mount_layer.add(MarsBlocks.marsBlock.getStateFromMeta(5), MarsBlocks.marsBlock.getStateFromMeta(6), -256, 0,   -2, -1,  true);
		mount_layer.add(Blocks.BEDROCK.getDefaultState(), 0, 2, 0, 0, true);
		
		WE_Biome.addBiomeToGeneration(cp, new Mars_Plains(-0.0D, 0.0D));
		WE_Biome.addBiomeToGeneration(cp, new Mars_High_Plains(-0.8D, 0.8D));
		WE_Biome.addBiomeToGeneration(cp, new Mars_Mountains(-1.2D, 1.2D));		
		WE_Biome.addBiomeToGeneration(cp, new Mars_High_Plains(-1.8D, 1.8D));
		WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(-2.2D, 2.2D, 2.4F, 4, 180, 30, layer));
		/*WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(0D, 0D, 2.8F, 3, 95, 25, layer));
		WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(-0.4D, 0.4D, 11.4F, 3, 155, 5, mount_layer));
		WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(-1.4D, 1.1D, 2.8F, 3, 95, 15, layer));*/
		//WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(-1.8D, 1.8D, 2.8F, 2, 90, 5, layer));
		//WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(-2.6D, 2.6D, 1.2F, 3, 45, 10, mount_layer).setSize(128.0D, 1.5D));
		/*WE_BiomeRavineGen biome_ravine = new WE_BiomeRavineGen();
		biome_ravine.addReplacingBlock(terrainGenerator.worldStoneBlock);
		biome_ravine.addReplacingBlock(MarsBlocks.marsBlock.getStateFromMeta(6));
		biome_ravine.addReplacingBlock(MarsBlocks.marsBlock.getStateFromMeta(5));
		biome_ravine.addReplacingBlock(Blocks.PACKED_ICE.getDefaultState());
		biome_ravine.lavaBlock = Blocks.AIR.getDefaultState();
		biome_ravine.lavaMaxY = 0;
		WE_Biome.addBiomeToGeneration(cp, new WE_BaseBiome(-1.8D, 1.8D, 2.8F, 2, 90, 5, layer).addChunkGen(biome_ravine));
		 */
	}

	@Override
	public boolean enableAdvancedThermalLevel() {
		return super.enableAdvancedThermalLevel();
	}
	
	@Override
	protected float getThermalValueMod() {
		return 0.85F;
	}

	@Override
	public void onPopulate(int cX, int cZ) {
		dungeonGenerator.generateStructure(this.world, this.world.rand, new ChunkPos(cX, cZ));
		new BiomeDecoratorMars().decorate(this.world, this.world.rand, cX * 16, cZ * 16);
	}

	@Override
	public void onChunkProvider(int cX, int cZ, ChunkPrimer primer) {	
		dungeonGenerator.generate(this.world, cX, cZ, primer);
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {		
		dungeonGenerator.generate(this.world, x, z, null);
	}
	

}
