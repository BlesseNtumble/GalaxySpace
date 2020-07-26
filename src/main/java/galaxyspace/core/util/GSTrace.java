package galaxyspace.core.util;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class GSTrace {

	public static RayTraceResult rayTrace(EntityLivingBase viewer, World world, double distance, float ticks, Vec3d offset)
	{
		return rayTrace(viewer, world, distance, ticks, offset, false, false);
	}

	public static RayTraceResult rayTrace(EntityLivingBase viewer, World world, double distance, float ticks, Vec3d offset, boolean checkBlockCollision, boolean onlySolid)
	{
		return rayTrace(viewer, world, distance, ticks, offset, checkBlockCollision, onlySolid, null);
	}

	public static RayTraceResult rayTrace(EntityLivingBase viewer, World world, double distance, float ticks, Vec3d offset, boolean checkBlockCollision, boolean onlySolid, Vec3d dir)
	{
		return rayTrace(getPosition(viewer, ticks), world, distance, ticks, offset, checkBlockCollision, onlySolid, dir, viewer);
	}

	public static RayTraceResult rayTrace(Vec3d fromPos, World world, double distance, float ticks, Vec3d offset, boolean checkBlockCollision, boolean onlySolid, Vec3d dir, EntityLivingBase viewer)
	{
		RayTraceResult objectMouseOver = null;
		Entity pointedEntity = null;

		if (world != null)
		{
			if (dir == null)
			{
				dir = viewer.getLook(ticks);
			}

			objectMouseOver = rayTraceForBlocks(fromPos, world, distance, ticks, offset, checkBlockCollision, onlySolid, dir);
			double d1 = distance;
			Vec3d Vec3d = fromPos;
			if (offset != null)
			{
				Vec3d = Vec3d.addVector(offset.x, offset.y, offset.z);
			}

			if (objectMouseOver != null)
			{
				d1 = objectMouseOver.hitVec.distanceTo(Vec3d);
			}

			Vec3d Vec3d2 = Vec3d.addVector(dir.x * distance, dir.y * distance, dir.z * distance);
			Vec3d Vec3d3 = null;
			float f1 = 1.0F;
			List list = world.getEntitiesWithinAABBExcludingEntity(viewer, viewer.getEntityBoundingBox().grow(dir.x * distance, dir.y * distance, dir.z * distance).expand((double)f1, (double)f1, (double)f1));
			double d2 = d1;

			for (Object aList : list)
			{
				Entity entity = (Entity)aList;

				if (entity.canBeCollidedWith())
				{
					float f2 = entity.getCollisionBorderSize();
					AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand((double)f2, (double)f2, (double)f2);
					RayTraceResult movingobjectposition = axisalignedbb.calculateIntercept(Vec3d, Vec3d2);

					if (axisalignedbb.contains(Vec3d))
					{
						if (0.0D < d2 || d2 == 0.0D)
						{
							pointedEntity = entity;
							Vec3d3 = movingobjectposition == null ? Vec3d : movingobjectposition.hitVec;
							d2 = 0.0D;
						}
					}
					else if (movingobjectposition != null)
					{
						double d3 = Vec3d.distanceTo(movingobjectposition.hitVec);

						if (d3 < d2 || d2 == 0.0D)
						{
							pointedEntity = entity;
							Vec3d3 = movingobjectposition.hitVec;
							d2 = d3;
						}
					}
				}
			}

			if (pointedEntity != null && d2 < d1)
			{
				if (objectMouseOver != null)
				{
					objectMouseOver.typeOfHit = RayTraceResult.Type.ENTITY;
					objectMouseOver.entityHit = pointedEntity;
					objectMouseOver.hitVec = Vec3d3;
				}
				else
				{
					objectMouseOver = new RayTraceResult(pointedEntity, Vec3d3);
				}

			}
		}
		return objectMouseOver;
	}
	
	public static RayTraceResult rayTraceForBlocks(EntityLivingBase viewer, World world, double distance, float ticks, Vec3d offset, boolean collisionCheck, boolean onlySolid)
	{
		return rayTraceForBlocks(viewer, world, distance, ticks, offset, collisionCheck, onlySolid, null);
	}

	public static RayTraceResult rayTraceForBlocks(EntityLivingBase viewer, World world, double distance, float ticks, Vec3d offset, boolean collisionCheck, boolean onlySolid, Vec3d dir)
	{
		return rayTraceForBlocks(getPosition(viewer, ticks), world, distance, ticks, offset, collisionCheck, onlySolid, dir == null ? viewer.getLook(ticks) : dir);
	}

	public static RayTraceResult rayTraceForBlocks(Vec3d fromPosition, World world, double distance, float ticks, Vec3d offset, boolean collisionCheck, boolean onlySolid, Vec3d dir)
	{
		Vec3d Vec3d = new Vec3d(fromPosition.x, fromPosition.y, fromPosition.z);
		if (offset != null)
		{
			Vec3d = Vec3d.addVector(offset.x, offset.y, offset.z);
		}
		Vec3d Vec3d2 = Vec3d.addVector(dir.x * distance, dir.y * distance, dir.z * distance);
		return world.rayTraceBlocks(Vec3d, Vec3d2, collisionCheck, onlySolid, true);
	}
	
	public static Vec3d getPosition(EntityLivingBase entity, float p_70666_1_)
	{
		if (p_70666_1_ == 1.0F)
		{
			return new Vec3d(entity.posX, entity.posY, entity.posZ);
		}
		else
		{
			double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)p_70666_1_;
			double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * (double)p_70666_1_;
			double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)p_70666_1_;
			return new Vec3d(d0, d1, d2);
		}
	}
}
