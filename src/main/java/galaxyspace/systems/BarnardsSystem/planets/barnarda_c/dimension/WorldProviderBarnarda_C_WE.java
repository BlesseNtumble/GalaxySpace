package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.dimension;

import java.util.List;

import javax.annotation.Nullable;

import asmodeuscore.api.dimension.IProviderFreeze;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_ChunkProviderSpace;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_WorldProviderSpace;
import asmodeuscore.core.utils.worldengine.WE_Biome;
import asmodeuscore.core.utils.worldengine.WE_ChunkProvider;
import asmodeuscore.core.utils.worldengine.perlinnoise.PerlinNoise;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_CaveGen;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_OreGen;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_RavineGen;
import asmodeuscore.core.utils.worldengine.standardcustomgen.WE_TerrainGenerator;
import galaxyspace.core.util.GSDimensions;
import galaxyspace.systems.BarnardsSystem.BarnardsSystemBodies;
import galaxyspace.systems.BarnardsSystem.core.BRBlocks;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.dimension.sky.CloudProviderBarnarda_C;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.dimension.sky.SkyProviderBarnarda_C;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we.Barnarda_C_Beach;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we.Barnarda_C_DeepOcean;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we.Barnarda_C_Dunes;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we.Barnarda_C_Forest;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we.Barnarda_C_Jungle;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we.Barnarda_C_Mountains;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we.Barnarda_C_Ocean;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we.Barnarda_C_Plains;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we.Barnarda_C_River;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we.Barnarda_C_SnowPlains;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we.Barnarda_C_Swampland;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.event.EventHandlerGC;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderBarnarda_C_WE extends WE_WorldProviderSpace implements IProviderFreeze {

	private final float[] colorsSunriseSunset = new float[4];
	public static WE_ChunkProvider chunk;
	
	@Override
    public double getHorizon() {
        return 44.0D;
    }

    @Override
    public float getFallDamageModifier() {
        return 1.0F;
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
        return Float.MIN_VALUE;
    }

    @Override
    public CelestialBody getCelestialBody() {
        return BarnardsSystemBodies.Barnarda_C;
    }

    @Override
    public Class<? extends IChunkGenerator> getChunkProviderClass() {
        return WE_ChunkProviderSpace.class;

    }
    
    @Override 
    @SideOnly(Side.CLIENT)
    public float getCloudHeight()
    {
        return 180.0F;
    }

    @Nullable
    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks)
    {
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
        return new Vector3(140 / 255.0F * f, 167 / 255.0F * f, 207 / 255.0F * f);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vector3 getSkyColor() {

    	float f = 0.5F - this.getStarBrightness(1.0F);
    	if(world.isRaining())
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
	public boolean hasSunset() {
		return true;
	}

    @Override
    public boolean shouldForceRespawn() {
        return !ConfigManagerCore.forceOverworldRespawn;
    }    
    
    @Override
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1)
    {
    	
    	float f = this.world.getCelestialAngle(par1);
        float f1 = 1.0F - (MathHelper.cos(f * ((float)Math.PI * 2F)) * 2.0F + 0.25F);
        f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
        
        if(world.isRaining())
    	{
    		return f1 * f1 * 0.2F;
    	}
        
        return f1 * f1 * 0.5F;   	
        
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public float getSunBrightness(float par1) {
       float f1 = this.world.getCelestialAngle(1.0F);
       float f2 = 1.0F - (MathHelper.cos(f1 * 3.1415927F * 2.0F) * 2.0F + 0.2F);
       f2 = MathHelper.clamp(f2, 0.0F, 1.0F);

       f2 = 1.2F - f2;
       return f2 * 0.8F;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IRenderHandler getCloudRenderer(){
    	
    	if(super.getCloudRenderer() == null)
    		this.setCloudRenderer(new CloudProviderBarnarda_C());
    	
        return super.getCloudRenderer();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IRenderHandler getSkyRenderer()
    {
    	if (super.getSkyRenderer() == null)
		{
			this.setSkyRenderer(new SkyProviderBarnarda_C());
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
	
		return GSDimensions.BARNARDA_C;
	}
	
	@Override
    public boolean canRespawnHere()
    {
    	EventHandlerGC.bedActivated = false;
    	return true;
    }
	
	@Override
    public boolean isSurfaceWorld()
    {
		if(this.world == null) return false;
		
		if(!this.world.isRemote) return false;
		
        return true; //this.world.isRemote;
    }

	public WorldSleepResult canSleepAt(net.minecraft.entity.player.EntityPlayer player, BlockPos pos)
    {
        return (this.canRespawnHere()) ? WorldSleepResult.ALLOW : WorldSleepResult.BED_EXPLODES;
    }
	
	@Override
	public void genSettings(WE_ChunkProvider cp) {		
		chunk = cp;
		
		cp.createChunkGen_List .clear(); 
		cp.createChunkGen_InXZ_List .clear(); 
		cp.createChunkGen_InXYZ_List.clear(); 
		cp.decorateChunkGen_List .clear(); 
		((WE_ChunkProviderSpace)cp).worldGenerators.clear();
		

		
		WE_TerrainGenerator terrainGenerator = new WE_TerrainGenerator(); 
		terrainGenerator.worldStoneBlock = BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(1); 
		terrainGenerator.worldSeaGen = true;
		terrainGenerator.worldSeaGenBlock = Blocks.WATER.getDefaultState();
		terrainGenerator.worldSeaGenMaxY = 64;
		cp.createChunkGen_List.add(terrainGenerator);
		
		//-// 
		WE_CaveGen cg = new WE_CaveGen(); 
		cg.replaceBlocksList .clear(); 
		cg.addReplacingBlock(terrainGenerator.worldStoneBlock); 
		cg.lavaMaxY = 8;
		cp.createChunkGen_List.add(cg); 
		//-// 
		 
		WE_RavineGen rg = new WE_RavineGen();
		rg.replaceBlocksList    .clear();
		rg.addReplacingBlock(terrainGenerator.worldStoneBlock);
		rg.lavaBlock = Blocks.LAVA.getDefaultState();
		rg.lavaMaxY = 15;		
		cp.createChunkGen_List.add(rg);
		
		WE_OreGen standardOres = new WE_OreGen();
		//Coal
		standardOres.add(BRBlocks.BARNARDA_C_ORES.getStateFromMeta(0), terrainGenerator.worldStoneBlock, 15, 5, 150, 20);
		//Iron
		standardOres.add(BRBlocks.BARNARDA_C_ORES.getStateFromMeta(1), terrainGenerator.worldStoneBlock, 6, 5, 70, 15);
		//Gold
		standardOres.add(BRBlocks.BARNARDA_C_ORES.getStateFromMeta(2), terrainGenerator.worldStoneBlock, 6, 5, 45, 10);
		//Redstone
		standardOres.add(BRBlocks.BARNARDA_C_ORES.getStateFromMeta(3), terrainGenerator.worldStoneBlock, 6, 5, 15, 20);
		//Lapis
		standardOres.add(BRBlocks.BARNARDA_C_ORES.getStateFromMeta(4), terrainGenerator.worldStoneBlock, 4, 5, 35, 4);
		//Diamond
		standardOres.add(BRBlocks.BARNARDA_C_ORES.getStateFromMeta(5), terrainGenerator.worldStoneBlock, 5, 5, 12, 8);
		//Silicon
		standardOres.add(BRBlocks.BARNARDA_C_ORES.getStateFromMeta(6), terrainGenerator.worldStoneBlock, 6, 10, 40, 8);
		//Copper
		standardOres.add(BRBlocks.BARNARDA_C_ORES.getStateFromMeta(7), terrainGenerator.worldStoneBlock, 8, 20, 150, 20);
		//Tin
		standardOres.add(BRBlocks.BARNARDA_C_ORES.getStateFromMeta(8), terrainGenerator.worldStoneBlock, 8, 15, 64, 15);
		//Aluminum
		standardOres.add(BRBlocks.BARNARDA_C_ORES.getStateFromMeta(9), terrainGenerator.worldStoneBlock, 5, 5, 30, 15);
		//Quartz
		standardOres.add(BRBlocks.BARNARDA_C_ORES.getStateFromMeta(10), terrainGenerator.worldStoneBlock, 4, 5, 15, 8);
		//Cobaltum
		standardOres.add(BRBlocks.BARNARDA_C_ORES.getStateFromMeta(11), terrainGenerator.worldStoneBlock, 4, 5, 25, 20);
		//Nickel
		standardOres.add(BRBlocks.BARNARDA_C_ORES.getStateFromMeta(12), terrainGenerator.worldStoneBlock, 4, 5, 15, 15);
		
		//Dirt
		standardOres.add(BRBlocks.BARNARDA_C_BLOCKS.getStateFromMeta(0), terrainGenerator.worldStoneBlock, 30, 5, 150, 30);
		//Gravel
		standardOres.add(Blocks.GRAVEL.getDefaultState(), terrainGenerator.worldStoneBlock, 15, 5, 150, 20);
		//Granite
		standardOres.add(Blocks.STONE.getStateFromMeta(1), terrainGenerator.worldStoneBlock, 15, 5, 150, 20);
		//Diorite
		standardOres.add(Blocks.STONE.getStateFromMeta(3), terrainGenerator.worldStoneBlock, 15, 5, 150, 20);
		//Andesite
		standardOres.add(Blocks.STONE.getStateFromMeta(5), terrainGenerator.worldStoneBlock, 15, 5, 150, 20);
				
		cp.decorateChunkGen_List.add(standardOres);
		
		cp.biomesList.clear();
		//WE_Biome.addPrelinNoise(cp, new PerlinNoise(cp.worldObj.getSeed(), 1.0D, 1, 100D, .5D, 0));
		
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_DeepOcean(-4D, 4D));	
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Ocean(-3.8D, 3.8D, false));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Beach(-3.5D, 3.5D, 1));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Plains(-3.4D, 3.4D));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Forest(-2.9D, 2.9D));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_River(-2.5D, 2.5D));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Swampland(-2.4D, 2.4D));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Jungle(-1.9D, 1.9D));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Mountains(-1.4D, 1.4D, 100, 2.8D, 4));	
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Mountains(-1.0D, 1.0D, 180, 3.0D, 4));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_SnowPlains(-0.8D, 0.8D, 160));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Mountains(-0.6D, 0.6D, 100, 2.4D, 4));	
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Dunes(-0.3D, 0.3D));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Ocean(-0.0D, 0.0D, true));
		
		WE_Biome.setBiomeMap(cp, 0.8D, 4, cp.biomesList.size() * 200D, 3.0D);

	}

	@Override
	public boolean enableAdvancedThermalLevel() {
		return true;
	}
	
	@Override
	protected float getThermalValueMod()
	{
		return 0.05F;
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
	
	@Override
	public boolean isColorWorld() { return true; }
}
