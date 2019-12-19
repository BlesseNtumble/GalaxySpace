package galaxyspace.core.client.fx;

import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GSEffectHandler {

	public static void spawnParticle(String particleID, Vector3 position, Vector3 motion, Object... otherInfo)
    {
		Minecraft mc = FMLClientHandler.instance().getClient();
		
		if (mc != null && mc.getRenderViewEntity() != null && mc.effectRenderer != null) {

			double dX = mc.getRenderViewEntity().posX - position.x;
			double dY = mc.getRenderViewEntity().posY - position.y;
			double dZ = mc.getRenderViewEntity().posZ - position.z;
			
			Particle particle = null;
			double viewDistance = 64.0D;
			
			if(particleID.equals("waterbubbles")) {
				particle = new EntityParticles(mc.world, position, motion, (int) otherInfo[0], (int) otherInfo[1], (boolean) otherInfo[2], (Vector3) otherInfo[3], (double) otherInfo[4]);
				
			}
			else if(particleID.equals("waterbubbles1")) {
				particle = new EntityParticles(mc.world, position, motion, (int) otherInfo[0], (int) otherInfo[1], (boolean) otherInfo[2], (Vector3) otherInfo[3], (double) otherInfo[4], (double) otherInfo[5]);
				
			}
			else if (particleID.equals("launchFlameLaunched"))
            {
                particle = new GSParticleLaunchFlame(mc.world, position, motion, true, (EntityLivingBase) otherInfo[0], (Vector3) otherInfo[1]);
            }	
			
			int setting = mc.gameSettings.particleSetting;
			
			if (particle != null && mc.world.rand.nextInt(setting == 0 ? 1 : setting + 1) == 0)
            {
				mc.effectRenderer.addEffect(particle);
            }
		}	
		
    }
}
