package galaxyspace.systems.SolarSystem.planets.mercury.dimension;

 


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.api.dimension.IProviderFreeze;
import galaxyspace.core.world.gen.WorldProviderAdvancedSpace;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import galaxyspace.systems.SolarSystem.planets.mercury.dimension.sky.SkyProviderMercury;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

 

public class WorldProviderMercury extends WorldProviderAdvancedSpace implements IProviderFreeze{

	
    @Override
    public double getHorizon()
    {
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

        return 3.0;

    }

 

    @Override

    public float getSoundVolReductionAmount() {

        return Float.MAX_VALUE;

    }

    @Override
    public boolean canRainOrSnow() {
        return false;
    }

 

    @Override

    public CelestialBody getCelestialBody() {

        return SolarSystemBodies.planetMercury;

    }

 

     //Created later

    @Override

    public Class<? extends IChunkProvider> getChunkProviderClass() {

        return ChunkProviderMercury.class;

    }
    
    @Override
    public Vector3 getFogColor() {

    	return new Vector3(0, 0, 0);

    }

    @Override
    public Vector3 getSkyColor() {

    	return new Vector3(0, 0, 0);

    }

     
     @Override
     public boolean isSkyColored()
     {
         return false;
     }
 

     //Created Later

    @Override

    public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {

        return WorldChunkManagerMercury.class;

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
    
    @Override
    public IRenderHandler getCloudRenderer(){
        return new CloudRenderer();
    }

    @Override
	@SideOnly(Side.CLIENT)
    public IRenderHandler getSkyRenderer()
    {
    	if (super.getSkyRenderer() == null)
		{
			this.setSkyRenderer(new SkyProviderMercury());
		}

		return super.getSkyRenderer();
    }

}