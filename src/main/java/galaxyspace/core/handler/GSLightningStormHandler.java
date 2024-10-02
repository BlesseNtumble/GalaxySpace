package galaxyspace.core.handler;

import galaxyspace.api.dimension.ILightning;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GSLightningStormHandler
{
	
	public static void spawnLightning(EntityPlayer player)
	{
		World world = player.worldObj;
		if (world.provider instanceof ILightning)
		{
			if (((ILightning)world.provider).getLightningStormFrequency() > 0)
			{
				int f = (int)(((ILightning)world.provider).getLightningStormFrequency() * 100D);

				if (world.rand.nextInt(f) == 0)
				{
					createLightning(player, world);			
				}

				if (world.rand.nextInt(f * 3) == 0)
				{
					createLightning(player, world);			
				}
			}
		}
	}
	
	private static void createLightning(EntityPlayer player, World world)
	{
		EntityPlayer closestPlayer = world.getClosestPlayerToEntity(player, 100);

		if (closestPlayer == null || closestPlayer.getEntityId() <= player.getEntityId())
		{
			//double x = player.posX + world.rand.nextInt(45) - 50;
			//double z = player.posZ + world.rand.nextInt(45) - 50;
			
			int x = (int) (player.posX + player.worldObj.rand.nextInt(64) - 32);
			int z = (int) (player.posZ + player.worldObj.rand.nextInt(64) - 32);			
			
			//double y = world.getTopSolidOrLiquidBlock((int)x, (int)z) - 1;//player.posY + (int)(((ILightning)world.provider).getYPosLightning());
			double y = world.getTopSolidOrLiquidBlock((int)x, (int)z) + (int)(((ILightning)world.provider).getYPosLightning());
			
			EntityLightningBolt lightning = new EntityLightningBolt(world, x, y, z);
			
			//world.addWeatherEffect(lightning);
			
			world.spawnEntityInWorld(lightning);
	
		}
	}
}