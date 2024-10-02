/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  micdoodle8.mods.galacticraft.api.galaxies.CelestialBody
 *  micdoodle8.mods.galacticraft.api.vector.Vector3
 *  micdoodle8.mods.galacticraft.api.world.IExitHeight
 *  micdoodle8.mods.galacticraft.api.world.ISolarLevel
 *  micdoodle8.mods.galacticraft.api.world.ITeleportType
 *  micdoodle8.mods.galacticraft.api.world.IZeroGDimension
 *  micdoodle8.mods.galacticraft.core.client.CloudRenderer
 *  micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats
 *  micdoodle8.mods.galacticraft.core.util.ConfigManagerCore
 *  micdoodle8.mods.galacticraft.planets.mars.entities.EntityLandingBalloons
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.util.MathHelper
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldServer
 *  net.minecraft.world.biome.WorldChunkManager
 *  net.minecraft.world.chunk.IChunkProvider
 *  net.minecraftforge.client.IRenderHandler
 */
package galaxyspace.systems.SolarSystem.moons.phobos.dimension;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.api.dimension.IAdvancedSpace;
import galaxyspace.api.dimension.IProviderFreeze;
import galaxyspace.core.world.gen.WorldProviderAdvancedSpace;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import galaxyspace.systems.SolarSystem.moons.phobos.dimension.ChunkProviderPhobos;
import galaxyspace.systems.SolarSystem.moons.phobos.dimension.WorldChunkManagerPhobos;
import galaxyspace.systems.SolarSystem.moons.phobos.dimension.sky.SkyProviderPhobos;
import java.util.Random;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IExitHeight;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.api.world.ITeleportType;
import micdoodle8.mods.galacticraft.api.world.IZeroGDimension;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.planets.mars.entities.EntityLandingBalloons;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

public class WorldProviderPhobos
extends WorldProviderAdvancedSpace
implements IZeroGDimension,
IProviderFreeze,
IExitHeight,
ISolarLevel,
ITeleportType,
IAdvancedSpace {
    @Override
    public double getSolarEnergyMultiplier() {
        double solarMultiplier = -1.0;
        if (solarMultiplier < 0.0) {
            double s = this.getSolarSizeForMoon();
            solarMultiplier = s * s * s * ConfigManagerCore.spaceStationEnergyScalar;
        }
        return solarMultiplier;
    }

    public double getHorizon() {
        return 44.0;
    }

    public float getFallDamageModifier() {
        return 0.16f;
    }

    public double getFuelUsageMultiplier() {
        return 0.8;
    }

    public double getMeteorFrequency() {
        return 3.0;
    }

    public float getSoundVolReductionAmount() {
        return Float.MAX_VALUE;
    }

    public boolean canRainOrSnow() {
        return false;
    }

    public CelestialBody getCelestialBody() {
        return SolarSystemBodies.phobosMars;
    }

    public Class<? extends IChunkProvider> getChunkProviderClass() {
        return ChunkProviderPhobos.class;
    }

    public Vector3 getFogColor() {
        return new Vector3(0.0, 0.0, 0.0);
    }

    public Vector3 getSkyColor() {
        return new Vector3(0.0, 0.0, 0.0);
    }

    public boolean isSkyColored() {
        return false;
    }

    public Class<? extends WorldChunkManager> getWorldChunkManagerClass() {
        return WorldChunkManagerPhobos.class;
    }

    public boolean hasSunset() {
        return false;
    }

    public boolean shouldForceRespawn() {
        return !ConfigManagerCore.forceOverworldRespawn;
    }

    @Override
    public double getYCoordinateToTeleport() {
        return 1000.0;
    }

    public Vector3 getEntitySpawnLocation(WorldServer arg0, Entity arg1) {
        return new Vector3(arg1.posX, ConfigManagerCore.disableLander ? 250.0 : 900.0, arg1.posZ);
    }

    public Vector3 getParaChestSpawnLocation(WorldServer arg0, EntityPlayerMP arg1, Random arg2) {
        if (ConfigManagerCore.disableLander) {
            double x = (arg2.nextDouble() * 2.0 - 1.0) * 5.0;
            double z = (arg2.nextDouble() * 2.0 - 1.0) * 5.0;
            return new Vector3(x, 220.0, z);
        }
        return null;
    }

    public Vector3 getPlayerSpawnLocation(WorldServer arg0, EntityPlayerMP arg1) {
        if (arg1 != null) {
            GCPlayerStats stats = GCPlayerStats.get((EntityPlayerMP)arg1);
            return new Vector3((double)(-5 + arg0.rand.nextInt(15)), ConfigManagerCore.disableLander ? 250.0 : 900.0, (double)(-5 + arg0.rand.nextInt(15)));
        }
        return null;
    }

    public void onSpaceDimensionChanged(World arg0, EntityPlayerMP player, boolean arg2) {
        if (player != null && GCPlayerStats.get((EntityPlayerMP)player).teleportCooldown <= 0) {
            if (player.capabilities.isFlying) {
                player.capabilities.isFlying = false;
            }
            EntityLandingBalloons lander = new EntityLandingBalloons(player);
            if (!arg0.isRemote) {
                arg0.spawnEntityInWorld((Entity)lander);
            }
            GCPlayerStats.get((EntityPlayerMP)player).teleportCooldown = 10;
        }
    }

    public boolean useParachute() {
        return ConfigManagerCore.disableLander;
    }

    @SideOnly(value=Side.CLIENT)
    public float getStarBrightness(float par1) {
        float var2 = this.worldObj.getCelestialAngle(par1);
        float var3 = 1.0f - (MathHelper.cos((float)(var2 * (float)Math.PI * 2.0f)) * 2.0f + 0.25f);
        if (var3 < 0.0f) {
            var3 = 0.0f;
        }
        if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        return var3 * var3 * 0.5f + 0.3f;
    }

    @SideOnly(value=Side.CLIENT)
    public float getSunBrightness(float par1) {
        float f1 = this.worldObj.getCelestialAngle(1.0f);
        float f2 = 1.25f - (MathHelper.cos((float)(f1 * (float)Math.PI * 2.0f)) * 2.0f + 0.2f);
        float f3 = this.worldObj.getWorldTime();
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        if (f2 > 1.0f) {
            f2 = 1.0f;
        }
        f2 = 1.2f - f2;
        if (f3 > (float)(this.getDayLength() / 4L - 1500L) && f3 < (float)(this.getDayLength() / 4L + 1600L)) {
            return f2 * 0.1f;
        }
        return f2 * 1.0f;
    }

    public IRenderHandler getCloudRenderer() {
        return new CloudRenderer();
    }

    public void setupAdventureSpawn(EntityPlayerMP player) {
    }

    public int getAverageGroundLevel() {
        return 50;
    }

    @Override
    public double getSolarWindMultiplier() {
        double solarMultiplier = -1.0;
        if (solarMultiplier < 0.0) {
            double s = this.getSolarSizeForMoon();
            solarMultiplier = s * s * s * ConfigManagerCore.spaceStationEnergyScalar;
        }
        return solarMultiplier;
    }

    @SideOnly(value=Side.CLIENT)
    public IRenderHandler getSkyRenderer() {
        if (super.getSkyRenderer() == null) {
            this.setSkyRenderer(new SkyProviderPhobos());
        }
        return super.getSkyRenderer();
    }
}

