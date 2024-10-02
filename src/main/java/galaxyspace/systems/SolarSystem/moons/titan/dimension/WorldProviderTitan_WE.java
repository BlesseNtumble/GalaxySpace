package galaxyspace.systems.SolarSystem.moons.titan.dimension;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.api.dimension.IAdvancedSpace;
import galaxyspace.api.dimension.IPlanetFog;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.fluids.GSFluids;
import galaxyspace.core.world.worldengine.WE_Biome;
import galaxyspace.core.world.worldengine.WE_ChunkProvider;
import galaxyspace.core.world.worldengine.WE_WorldProvider;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_CaveGen;
import galaxyspace.core.world.worldengine.standardcustomgen.WE_TerrainGenerator;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import galaxyspace.systems.SolarSystem.moons.titan.dimension.sky.CloudProviderTitan;
import galaxyspace.systems.SolarSystem.moons.titan.dimension.sky.SkyProviderTitan;
import galaxyspace.systems.SolarSystem.moons.titan.world.gen.BiomeDecoratorTitanOre;
import galaxyspace.systems.SolarSystem.moons.titan.world.gen.we.WE_TitanBiome;
import galaxyspace.systems.SolarSystem.moons.titan.world.gen.we.WE_TitanHills;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IExitHeight;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.api.world.ITeleportType;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedCreeper;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import micdoodle8.mods.galacticraft.core.entities.EntityLander;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

public class WorldProviderTitan_WE extends WE_WorldProvider implements IGalacticraftWorldProvider, IExitHeight, ISolarLevel, ITeleportType, IAdvancedSpace, IPlanetFog{

    @Override
	public double getSolarEnergyMultiplier()
	{
		double solarMultiplier = -1D;
		if (solarMultiplier < 0D)
		{
			double s = this.getSolarSizeForMoon();
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
        return 0.16F;
    }

 

    @Override

    public double getFuelUsageMultiplier() {

        return 0.8;

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
    public CelestialBody getCelestialBody() {
        return SolarSystemBodies.titanSaturn;
    }

 

     //Created later

    @Override

    public Class<? extends IChunkProvider> getChunkProviderClass() {
        return WE_ChunkProvider.class;
    }

    @Override
    public Vector3 getFogColor() {

    	float f = 1.0F;
        return new Vector3(219 / 255.0F * f, 77 / 255.0F * f, 91 / 255.0F * f);

    }

    @Override
    public Vector3 getSkyColor() {

    	float f = 1.1F - this.getStarBrightness(1.0F);
    	//return new Vector3(31 / 255.0F * f, 34 / 255.0F * f, 100 / 255.0F * f);
    	return new Vector3(224 / 255.0F * f, 149 / 255.0F * f, 43 / 255.0F * f);
    }

     
    @Override
    public boolean isSkyColored()
    {
        return true;
    }
 

    @Override
    public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {
       return WorldChunkManagerTitan.class;
    }
     
    @Override
    public boolean hasSunset() {
        return false;
    }

 

    //Can players respawn here?

    @Override

    public boolean shouldForceRespawn() {

        return !ConfigManagerCore.forceOverworldRespawn;

    }

    @Override
    public Vector3 getEntitySpawnLocation(WorldServer arg0, Entity arg1) {
    	return new Vector3(arg1.posX, ConfigManagerCore.disableLander ? 250.0 : 900.0, arg1.posZ);
    }

 

    @Override

    public Vector3 getParaChestSpawnLocation(WorldServer arg0,

            EntityPlayerMP arg1, Random arg2) {

        //Randomize parachest spawn location

    	if (ConfigManagerCore.disableLander)
        {
            final double x = (arg2.nextDouble() * 2 - 1.0D) * 5.0D;
            final double z = (arg2.nextDouble() * 2 - 1.0D) * 5.0D;
            return new Vector3(x, 220.0D, z);
        }

        return null;

    }

 

    @Override
    public Vector3 getPlayerSpawnLocation(WorldServer world, EntityPlayerMP player)
    {
        if (player != null)
        {
            GCPlayerStats stats = GCPlayerStats.get(player);
            double x = stats.coordsTeleportedFromX;
            double z = stats.coordsTeleportedFromZ;
            int limit = ConfigManagerCore.otherPlanetWorldBorders - 2;
            if (limit > 20)
            {
                if (x > limit)
                {
                    z *= limit / x;
                    x = limit;
                }
                else if (x < -limit)
                {   
                    z *= -limit / x;
                    x = -limit;
                }
                if (z > limit)
                {
                    x *= limit / z;
                    z = limit;
                }
                else if (z < -limit)
                {
                    x *= - limit / z;
                    z = -limit;
                }
            }
            return new Vector3(x, ConfigManagerCore.disableLander ? 250.0 : 900.0, z);
        }

        return null;
    }

 

    @Override

    public void onSpaceDimensionChanged(World arg0, EntityPlayerMP player, boolean arg2) {
    	
        if (player != null && GCPlayerStats.get(player).teleportCooldown <= 0)
        {
            if (player.capabilities.isFlying)
            {
                player.capabilities.isFlying = false;
            }

            EntityLander lander = new EntityLander(player);

            if (!arg0.isRemote)
            {
                arg0.spawnEntityInWorld(lander);
            }

            GCPlayerStats.get(player).teleportCooldown = 10;
        }
    }

    @Override

    public boolean useParachute() {

    	return ConfigManagerCore.disableLander;

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

        return var3 * var3 * 0.5F + 0.3F;
    }
    /*
    @Override
    @SideOnly(Side.CLIENT)
    public float getSunBrightness(float par1) {
       float f1 = this.worldObj.getCelestialAngle(1.0F);
       float f2 = 1.25F - (MathHelper.cos(f1 * 3.1415927F * 2.0F) * 2.0F + 0.2F);
       if(f2 < 0.0F) {
          f2 = 0.0F;
       }

       if(f2 > 1.0F) {
          f2 = 1.0F;
       }

       f2 = 1.2F - f2;
       return f2 * 0.1F;
    }
    */
    @SideOnly(Side.CLIENT)
    public IRenderHandler getCloudRenderer()
    {
    	if (super.getCloudRenderer() == null)
		{
			this.setCloudRenderer(new CloudProviderTitan());
		}

		return super.getCloudRenderer();
    }
    
    @SideOnly(Side.CLIENT)
    public IRenderHandler getSkyRenderer()
    {
    	if (super.getSkyRenderer() == null)
		{
			this.setSkyRenderer(new SkyProviderTitan());
		}

		return super.getSkyRenderer();
    }
    /*
    @SideOnly(Side.CLIENT)
    public IRenderHandler getWeatherRenderer()
    {
    	if (super.getWeatherRenderer() == null)
		{
			this.setWeatherRenderer(new WeatherProviderTitan());
		}

		return super.getWeatherRenderer();
    }
    
    public float getRainStrength(float var1)
    {
		return 1.0F;
    	
    }

    @Override
    public void updateWeather()
    {
        super.updateWeather();
    }
    */
    @Override
    public boolean canRainOrSnow() {
        return false;
    }
    /*
    @Override
    public boolean canBlockFreeze(int x, int y, int z, boolean byWater) {
        return false;
    }
    */
	@Override
	public void setupAdventureSpawn(EntityPlayerMP player) {
		
	}
	
	@Override
	public double getSolarWindMultiplier() 
	{
		double solarMultiplier = -1D;
		if (solarMultiplier < 0D)
		{
			double s = this.getSolarSizeForMoon();
			solarMultiplier = s * s * s * ConfigManagerCore.spaceStationEnergyScalar;
		}
		return solarMultiplier;
	}

	@Override
	public void genSettings(WE_ChunkProvider cp) {
		cp.createChunkGen_List .clear(); 
		cp.createChunkGen_InXZ_List .clear(); 
		cp.createChunkGen_InXYZ_List.clear(); 
		cp.decorateChunkGen_List .clear(); 

		WE_Biome.setBiomeMap(cp, 1.2D, 4, 800.5D, 0.458D);
		
		WE_TerrainGenerator terrainGenerator = new WE_TerrainGenerator(); 
		terrainGenerator.worldStoneBlock = GSBlocks.TitanBlocks; 
		terrainGenerator.worldStoneBlockMeta = 2;
		terrainGenerator.worldSeaGen = true;
		terrainGenerator.worldSeaGenBlock = GSFluids.BlockLiquidMethane;
		terrainGenerator.worldSeaGenMaxY = 64;
		cp.createChunkGen_List.add(terrainGenerator);
		
		//-// 
		WE_CaveGen cg = new WE_CaveGen(); 
		cg.replaceBlocksList .clear(); 
		cg.replaceBlocksMetaList.clear(); 
		cg.addReplacingBlock(terrainGenerator.worldStoneBlock, (byte)terrainGenerator.worldStoneBlockMeta);  
		cp.createChunkGen_List.add(cg); 
		//-// 
		 
		WE_Biome.addBiomeToGeneration(cp, new WE_TitanHills(cp)); 
		//WE_Biome.addBiomeToGeneration(cp, new WE_TitanRiver()); 
		WE_Biome.addBiomeToGeneration(cp, new WE_TitanBiome(cp));
		//WE_Biome.addBiomeToGeneration(cp, new WE_TitanOcean());
		

		
	}
	
	protected SpawnListEntry[] getMonsters() {	
	    SpawnListEntry skele = new SpawnListEntry(EntityEvolvedSkeleton.class, 100, 4, 4);
	    SpawnListEntry creeper = new SpawnListEntry(EntityEvolvedCreeper.class, 100, 4, 4);
	    SpawnListEntry spider = new SpawnListEntry(EntityEvolvedSpider.class, 100, 4, 4);
	    
	    return new SpawnListEntry[]{skele, creeper, spider};
	}

	@Override
	public float getFogDensity(int x, int y, int z) {
		return 0.4F;
	}

	@Override
	public int getFogColor(int x, int y, int z) {
		int R = 214 * 256 * 256;
		int G = 152 * 256;
		int B = 44;
		int color = R+G+B;	
		
	    return color - 16777216;
	}

	@Override
	public BiomeDecoratorSpace getDecorator() {
		return new BiomeDecoratorTitanOre();
	}

}
