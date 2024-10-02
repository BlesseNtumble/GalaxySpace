package galaxyspace.systems.BarnardsSystem.planets.barnardaC.dimension;

import java.util.Random;

import galaxyspace.core.prefab.entity.EntityEntryPod;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.ITeleportType;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TeleportTypeBarnardaC implements ITeleportType{

	@Override
	public Vector3 getEntitySpawnLocation(WorldServer arg0, Entity arg1) {

		return new Vector3(arg1.posX, ConfigManagerCore.disableLander ? 250.0 : 900.0, arg1.posZ);

	}

	@Override
	public Vector3 getParaChestSpawnLocation(WorldServer arg0, EntityPlayerMP arg1, Random arg2) {

		// Randomize parachest spawn location

		if (ConfigManagerCore.disableLander) {
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

		if (player != null && GCPlayerStats.get(player).teleportCooldown <= 0) {
			if (player.capabilities.isFlying) {
				player.capabilities.isFlying = false;
			}

			EntityEntryPod lander = new EntityEntryPod(player);

			if (!arg0.isRemote) {
				arg0.spawnEntityInWorld(lander);
			}

			GCPlayerStats.get(player).teleportCooldown = 10;
		}
	}

	@Override
	public boolean useParachute() {
		//return true;
		return ConfigManagerCore.disableLander;

	}

	@Override
	public void setupAdventureSpawn(EntityPlayerMP player) {
		// TODO Auto-generated method stub

	}
}
