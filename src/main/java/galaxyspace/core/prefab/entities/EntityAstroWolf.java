package galaxyspace.core.prefab.entities;

import micdoodle8.mods.galacticraft.api.entity.IEntityBreathable;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.World;

public class EntityAstroWolf extends EntityWolf implements IEntityBreathable {

	public EntityAstroWolf(World worldIn) {
		super(worldIn);
	}

	@Override
	public boolean canBreath() {
		return true;
	}

}
