package galaxyspace.systems.SolarSystem.moons.triton.dimenson;

import java.util.List;

import asmodeuscore.api.dimension.IProviderFreeze;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_ChunkProviderSpace;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_WorldProviderSpace;
import asmodeuscore.core.utils.worldengine.WE_Biome;
import asmodeuscore.core.utils.worldengine.WE_ChunkProvider;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_CaveGen;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_TerrainGenerator;
import galaxyspace.core.GSBlocks;
import galaxyspace.core.util.GSDimensions;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import galaxyspace.systems.SolarSystem.moons.triton.dimenson.sky.SkyProviderTriton;
import galaxyspace.systems.SolarSystem.moons.triton.world.gen.we.Triton_Mountains;
import galaxyspace.systems.SolarSystem.moons.triton.world.gen.we.Triton_Plains;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.block.Block;
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

public class WorldProviderTriton_WE extends WE_WorldProviderSpace implements IProviderFreeze {
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
    public CelestialBody getCelestialBody() { return SolarSystemBodies.tritonNeptune; }

    @Override
    public Class<? extends IChunkGenerator> getChunkProviderClass() {
    	return WE_ChunkProviderSpace.class;
    }
       
    @Override
    public Vector3 getFogColor()
    {
    	float f = 1.0F - this.getStarBrightness(1.0F);
    	return new Vector3(104 / 255.0F * f, 104 / 255.0F * f, 104 / 255.0F * f);
    }

    @Override
    public Vector3 getSkyColor()
    {       
        return new Vector3(0, 0, 0);
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
    @SideOnly(Side.CLIENT)
    public float getSunBrightness(float par1) {
       float f1 = this.world.getCelestialAngle(1.0F);
       float f2 = 1.25F - (MathHelper.cos(f1 * 3.1415927F * 2.0F) * 2.0F + 0.2F);
       float f3 = this.world.getWorldTime();
       if(f2 < 0.0F) {
          f2 = 0.0F;
       }

       if(f2 > 1.0F) {
          f2 = 1.0F;
       }

       f2 = 1.2F - f2;
       return f2 * 0.35F;
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
			this.setSkyRenderer(new SkyProviderTriton());
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
	
		return GSDimensions.TRITON;
	}

	@Override
	public void genSettings(WE_ChunkProvider cp) {
		cp.createChunkGen_List .clear(); 
		cp.createChunkGen_InXZ_List .clear(); 
		cp.createChunkGen_InXYZ_List.clear(); 
		cp.decorateChunkGen_List .clear(); 
		
		((WE_ChunkProviderSpace)cp).CRATER_PROB = 400;
		
		WE_Biome.setBiomeMap(cp, 1.2D, 4, 1800.0D, 1.0D);	
		
		WE_TerrainGenerator terrainGenerator = new WE_TerrainGenerator(); 
		terrainGenerator.worldStoneBlock = GSBlocks.TRITON_BLOCKS.getStateFromMeta(2); 
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
		 /*
		WE_RavineGen rg = new WE_RavineGen();
		rg.replaceBlocksList    .clear();
		rg.addReplacingBlock(terrainGenerator.worldStoneBlock);
		rg.addReplacingBlock(MarsBlocks.marsBlock.getStateFromMeta(6));
		rg.addReplacingBlock(MarsBlocks.marsBlock.getStateFromMeta(5));
		rg.addReplacingBlock(Blocks.PACKED_ICE.getDefaultState());
		rg.lavaBlock = Blocks.AIR.getDefaultState();
		rg.lavaMaxY = 0;
		cp.createChunkGen_List.add(rg);
		
		MapGenCavernMars cavernGenerator = new MapGenCavernMars();
		cp.worldGenerators.clear();
		cp.worldGenerators.add(cavernGenerator);
		*/
		cp.biomesList.clear();

		WE_Biome.addBiomeToGeneration(cp, new Triton_Plains(0.0D, -0.8D, 1.4D, 74, 16));
		WE_Biome.addBiomeToGeneration(cp, new Triton_Plains(0.6D, -0.3D, 1.9D, 95, 5));
		WE_Biome.addBiomeToGeneration(cp, new Triton_Mountains(0.8D, 0.0D));
		
	}

	@Override
	public boolean enableAdvancedThermalLevel() {
		return super.enableAdvancedThermalLevel();
	}
	
	@Override
	protected float getThermalValueMod() {
		return 0.2F;
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
