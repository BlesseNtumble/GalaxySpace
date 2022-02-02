package galaxyspace.core.client.render.entity;

import org.lwjgl.opengl.GL11;

import galaxyspace.core.util.GSTrace;
import galaxyspace.systems.SolarSystem.planets.overworld.items.tools.ItemMatterManipulator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLaserBeam<T extends EntityLivingBase>
{
	
	public void onRenderWorldLast(RenderWorldLastEvent event)
	{
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GL11.GL_ONE, GL11.GL_ONE, 0, 0);

		GlStateManager.pushMatrix();
		GlStateManager.translate(-Minecraft.getMinecraft().player.posX, -Minecraft.getMinecraft().player.posY, -Minecraft.getMinecraft().player.posZ);
		renderClient(event.getPartialTicks());
		renderOthers(event.getPartialTicks());
		GlStateManager.popMatrix();

		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableBlend();
		GlStateManager.enableCull();
	}
	
	public void renderOthers(float ticks)
	{
		Minecraft.getMinecraft().world.getLoadedEntityList().stream()
				.filter(o -> o instanceof EntityPlayer)
				.filter(player -> !player.equals(Minecraft.getMinecraft().player))
				.forEach(o -> {
					EntityPlayer player = (EntityPlayer)o;
					if (shouldRenderBeam(player))
					{
						renderRaycastedBeam(player.getPositionEyes(ticks).add(0, player.getEyeHeight(), 0), player.getLook(0), new Vec3d(-0.5, -0.3, 1), player);
					}
					else
					{
						//stopWeaponSound(player);
					}
				});
	}
	
	public void renderClient(float ticks)
	{
		EntityPlayerSP player = Minecraft.getMinecraft().player;

		if (shouldRenderBeam(player))
		{
			Vec3d pos = player.getPositionEyes(1);
			Vec3d look = player.getLook(0);
			renderRaycastedBeam(pos, look, new Vec3d(-0.1, -0.1, 0.15), player);
		}
		else
		{
			//stopWeaponSound(player);
		}
	}
	
	protected boolean shouldRenderBeam(EntityPlayer entity)
	{
		return entity.isHandActive() &&
				entity.getActiveItemStack() != null &&
				(entity.getActiveItemStack().getItem() instanceof ItemMatterManipulator);
	}
	
	protected void onBeamRaycastHit(RayTraceResult hit, EntityPlayer caster)
	{
		ItemStack weaponStack = caster.getActiveItemStack();
		if (weaponStack != null && weaponStack.getItem() instanceof ItemMatterManipulator)
		{
			//((EnergyWeapon)weaponStack.getItem()).onProjectileHit(hit, weaponStack, caster.worldObj, 1);
			if (weaponStack.getItem() instanceof ItemMatterManipulator && hit.typeOfHit == RayTraceResult.Type.BLOCK)
			{
				GlStateManager.pushMatrix();
				//RenderUtils.applyColorWithMultipy(getBeamColor(caster), 0.5f + (float)(1 + Math.sin(caster.worldObj.getWorldTime() * 0.5f)) * 0.5f);
				//Minecraft.getMinecraft().renderEngine.bindTexture(TileEntityRendererStation.glowTexture);
				GlStateManager.translate(hit.getBlockPos().getX() + 0.5, hit.getBlockPos().getY() + 0.5, hit.getBlockPos().getZ() + 0.5);
				GlStateManager.translate(hit.sideHit.getDirectionVec().getX() * 0.5, hit.sideHit.getDirectionVec().getY() * 0.5, hit.sideHit.getDirectionVec().getZ() * 0.5);
				if (hit.sideHit == EnumFacing.SOUTH)
				{
					GlStateManager.rotate(90, 1, 0, 0);

				}
				else if (hit.sideHit == EnumFacing.NORTH)
				{
					GlStateManager.rotate(90, -1, 0, 0);
				}
				else if (hit.sideHit == EnumFacing.EAST)
				{
					GlStateManager.rotate(90, 0, 0, -1);
				}
				else if (hit.sideHit == EnumFacing.WEST)
				{
					GlStateManager.rotate(90, 0, 0, 1);
				}
				else if (hit.sideHit == EnumFacing.DOWN)
				{
					GlStateManager.rotate(180, 1, 0, 0);
				}
				GlStateManager.scale(1, 1.5 + Math.sin(caster.world.getWorldTime() * 0.5) * 0.5, 1);
				GlStateManager.popMatrix();
			}
		}
	}
	
	protected void onBeamRender(EntityPlayer caster)
	{
		
	}
	
	protected float getBeamMaxDistance(EntityPlayer caster)
	{
		return 15.0F;
	}
	
	protected float getBeamThickness(EntityPlayer caster)
	{
		return 0.05F;
	}
	
	protected boolean renderRaycastedBeam(Vec3d direction, Vec3d offset, EntityPlayer caster)
	{
		return renderRaycastedBeam(caster.getPositionEyes(1), direction, offset, caster);
	}

	protected boolean renderRaycastedBeam(Vec3d position, Vec3d direction, Vec3d offset, EntityPlayer caster)
	{
		double maxDistance = getBeamMaxDistance(caster);

		RayTraceResult hit = GSTrace.rayTrace(position, caster.world, maxDistance, 0, new Vec3d(0, 0, 0), false, true, direction, caster);
		if (hit != null && hit.typeOfHit != RayTraceResult.Type.MISS)
		{
			renderBeam(position, hit.hitVec, offset, null, getBeamThickness(caster), caster);
			onBeamRender(caster);
			onBeamRaycastHit(hit, caster);
			return true;
		}
		else
		{

			renderBeam(position, position.add(direction.x * maxDistance, direction.y * maxDistance, direction.z * maxDistance), offset, null, getBeamThickness(caster), caster);
			onBeamRender(caster);
		}
		return false;
	}

	protected void renderBeam(Vec3d from, Vec3d to, Vec3d offest, ResourceLocation texture, float tickness, EntityPlayer viewer)
	{
		if (texture != null)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		}

		GlStateManager.color(0.0F, 1.0F, 1.0F);
		//GlStateManager.disableCull();
		//GlStateManager.enableBlend();
		//GlStateManager.blendFunc(GL_ONE, GL_ONE);
		//GlStateManager.disableLighting();
		double distance = from.subtract(to).length();
		double v = -viewer.world.getWorldTime() * 0.2;

		GlStateManager.pushMatrix();
		GlStateManager.translate(from.x, from.y, from.z);
		GlStateManager.rotate(-viewer.getRotationYawHead(), 0, 1, 0);
		GlStateManager.rotate(viewer.rotationPitch, 1, 0, 0);
		GlStateManager.translate(offest.x, offest.y, offest.z);
		Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder wr = tessellator.getBuffer();
		wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		wr.pos(tickness, 0, 0).tex(0, v).endVertex();
		wr.pos(tickness, 0, distance).tex(0, v + distance * 1.5).endVertex();
		wr.pos(-tickness, 0, distance).tex(1, v + distance * 1.5).endVertex();
		wr.pos(-tickness, 0, 0).tex(1, v).endVertex();

		wr.pos(0, tickness, 0).tex(0, v).endVertex();
		wr.pos(0, tickness, distance).tex(0, v + distance * 1.5).endVertex();
		wr.pos(0, -tickness, distance).tex(1, v + distance * 1.5).endVertex();
		wr.pos(0, -tickness, 0).tex(1, v).endVertex();
		Tessellator.getInstance().draw();
		GlStateManager.popMatrix();

		//GlStateManager.enableCull();
		//GlStateManager.disableBlend();
		//GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}

}
