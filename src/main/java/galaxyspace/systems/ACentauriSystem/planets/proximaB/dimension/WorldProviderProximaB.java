package galaxyspace.systems.ACentauriSystem.planets.proximaB.dimension;

 


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.api.dimension.IPlanetFog;
import galaxyspace.api.dimension.IProviderFreeze;
import galaxyspace.core.world.worldengine.WE_Biome;
import galaxyspace.core.world.worldengine.WE_ChunkProvider;
import galaxyspace.core.world.worldengine.WE_WorldProvider;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_CaveGen;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_RavineGen;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_TerrainGenerator;
import galaxyspace.systems.ACentauriSystem.ACentauriSystemBodies;
import galaxyspace.systems.ACentauriSystem.core.registers.blocks.ACBlocks;
import galaxyspace.systems.ACentauriSystem.planets.proximaB.dimension.sky.CloudProviderProximaB;
import galaxyspace.systems.ACentauriSystem.planets.proximaB.dimension.sky.SkyProviderProximaB;
import galaxyspace.systems.ACentauriSystem.planets.proximaB.world.BiomeDecoratorProximaB;
import galaxyspace.systems.ACentauriSystem.planets.proximaB.world.we.Proxima_B_Beach;
import galaxyspace.systems.ACentauriSystem.planets.proximaB.world.we.Proxima_B_Forest;
import galaxyspace.systems.ACentauriSystem.planets.proximaB.world.we.Proxima_B_Ice_Plains;
import galaxyspace.systems.ACentauriSystem.planets.proximaB.world.we.Proxima_B_Mountains;
import galaxyspace.systems.ACentauriSystem.planets.proximaB.world.we.Proxima_B_Ocean;
import galaxyspace.systems.ACentauriSystem.planets.proximaB.world.we.Proxima_B_Plains;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

 

public class WorldProviderProximaB extends WE_WorldProvider implements IProviderFreeze, IPlanetFog{

	private final float[] colorsSunriseSunset = new float[4];
	public static WE_ChunkProvider chunk;
	
	@Override
	public double getSolarEnergyMultiplier()
	{
		double solarMultiplier = -1D;
		if (solarMultiplier < 0D)
		{
			double s = this.getSolarSize();
			solarMultiplier = s * 3;
		}
		return solarMultiplier;
	}     

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
    //Gravity is tricky. The only working values I have found are between 0.04 and 0.075 exclusive.
    //Anything else tends to be a bit wonky. Feel free to experiment.
    //Also a higher value means less gravity
    
	@Override
	public CelestialBody getCelestialBody() {
	    return ACentauriSystemBodies.proximaB;
	}	    
    
    @Override
    public double getMeteorFrequency() {
        return 7.0;
    }

    @Override
    public float getSoundVolReductionAmount() {
        return Float.MAX_VALUE;
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
    
    //Created later
    @Override
    public Class<? extends IChunkProvider> getChunkProviderClass() {
        return ChunkProviderProximaB.class;
    } 
        
    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks)
    {
        float f = 0.4F;
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
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Vector3 getFogColor() {
    	float f = 0.6F - this.getStarBrightness(1.0F);
        return new Vector3(120 / 255.0F * f, 110 / 255.0F * f, 150 / 255.0F * f);
    }

    @Override
    public Vector3 getSkyColor() {
    	float f = 0.4F - this.getStarBrightness(1.0F);
        return new Vector3(110 / 255.0F * f, 123 / 255.0F * f, 140 / 255.0F * f);
    }
    
    @Override
    public boolean isSkyColored() {
        return true;
    }
    //Created Later
    @Override
    public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {
        return WorldChunkManagerProximaB.class;
    }

    @Override
    public boolean hasSunset() {
        return false;
    }
 
    @Override
    public boolean canCoordinateBeSpawn(int var1, int var2) {
        return true;
    }
    //Can players respawn here?
    @Override
    public boolean shouldForceRespawn() {
        return !ConfigManagerCore.forceOverworldRespawn;
    }

    @SideOnly(Side.CLIENT)
    public IRenderHandler getCloudRenderer()
    {
    	if (super.getCloudRenderer() == null)
		{
			this.setCloudRenderer(new CloudProviderProximaB());
		}

		return super.getCloudRenderer();
    }
    
    @SideOnly(Side.CLIENT)
    public IRenderHandler getSkyRenderer()
    {
    	if (super.getSkyRenderer() == null)
		{
			this.setSkyRenderer(new SkyProviderProximaB());
		}

		return super.getSkyRenderer();
    }
    
    
    @Override
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1)
    {
        final float var2 = this.worldObj.getCelestialAngle(par1);
        float var3 = 1.0F - (MathHelper.cos(var2 * (float) Math.PI * 2.0F) * 2.0F + 0.25F);

        if (var3 < 0.0F)
        {
            var3 = 0.0F;
        }

        if (var3 > 1.0F)
        {
            var3 = 1.0F;
        }

        return var3 * var3 * 0.1F + 0.1F;
    }   
    
    @Override
    @SideOnly(Side.CLIENT)
    public float getSunBrightness(float par1) {
       float f1 = this.worldObj.getCelestialAngle(1.0F);
       float f2 = 1.0F - (MathHelper.cos(f1 * 3.1415927F * 2.0F) * 2.0F + 0.2F);
       f2 = MathHelper.clamp_float(f2, 0.0F, 1.0F);
       
       f2 = 1.2F - f2;
       return f2 * 0.3F;
    }
    
	@Override
	public float getFogDensity(int x, int y, int z) 
	{
		switch(this.getMoonPhase(this.getWorldTime()))
		{
			case 0: return 0.35F;
			case 1: return 0.75F;
			
			case 3: return 0.95F;
			
			case 5: return 0.5F;
			case 6: return 0.2F;
			
			default: return 1.0F;
		}
	}

	@Override
	public int getFogColor(int x, int y, int z) {
		int A = 10 * 256 * 256 * 256;
		int R = 100 * 256 * 256;
		int G = 100 * 256;
		int B = 100;
		int color = A+R+G+B;	
		
	    return color - 16777216;
	}
	
	@Override
	public void genSettings(WE_ChunkProvider cp) {
		chunk = cp;
		
		cp.createChunkGen_List .clear(); 
		cp.createChunkGen_InXZ_List .clear(); 
		cp.createChunkGen_InXYZ_List.clear(); 
		cp.decorateChunkGen_List .clear(); 
	
		WE_Biome.setBiomeMap(cp, 1.2D, 4, 1400.0D, 0.675D);	
		
		WE_TerrainGenerator terrainGenerator = new WE_TerrainGenerator(); 
		terrainGenerator.worldStoneBlock = ACBlocks.ProximaBBlocks; 
		terrainGenerator.worldStoneBlockMeta = 2;
		terrainGenerator.worldSeaGen = true;
		terrainGenerator.worldSeaGenBlock = Blocks.water;
		terrainGenerator.worldSeaGenMaxY = 64;
		cp.createChunkGen_List.add(terrainGenerator);
		
		//-// 
		WE_CaveGen cg = new WE_CaveGen(); 
		cg.replaceBlocksList .clear(); 
		cg.replaceBlocksMetaList.clear(); 
		cg.addReplacingBlock(terrainGenerator.worldStoneBlock, (byte)terrainGenerator.worldStoneBlockMeta); 
		//cg.lavaBlock = CW_Main.bfLava2; 
		cp.createChunkGen_List.add(cg); 
		//-// 
		 
		WE_RavineGen rg = new WE_RavineGen();
		rg.replaceBlocksList    .clear();
		rg.replaceBlocksMetaList.clear();
		rg.addReplacingBlock(ACBlocks.ProximaBBlocks, (byte)2);
		rg.lavaBlock = Blocks.lava;
		cp.createChunkGen_List.add(rg);
		
		WE_Biome.addBiomeToGeneration(cp, new Proxima_B_Plains(cp)); 
		WE_Biome.addBiomeToGeneration(cp, new Proxima_B_Ice_Plains(cp));
		WE_Biome.addBiomeToGeneration(cp, new Proxima_B_Mountains()); 
		WE_Biome.addBiomeToGeneration(cp, new Proxima_B_Ocean()); 
		WE_Biome.addBiomeToGeneration(cp, new Proxima_B_Beach()); 
		WE_Biome.addBiomeToGeneration(cp, new Proxima_B_Forest(cp)); 
	}

	@Override
	public BiomeDecoratorSpace getDecorator() {
		return new BiomeDecoratorProximaB();
	}
}