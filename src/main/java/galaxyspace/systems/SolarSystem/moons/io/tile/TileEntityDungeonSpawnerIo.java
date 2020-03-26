package galaxyspace.systems.SolarSystem.moons.io.tile;

import java.util.ArrayList;
import java.util.List;

import galaxyspace.systems.SolarSystem.moons.io.entities.EntityBossGhast;
import galaxyspace.systems.SolarSystem.planets.ceres.entities.EntityBossBlaze;
import micdoodle8.mods.galacticraft.core.client.sounds.GCSounds;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedZombie;
import micdoodle8.mods.galacticraft.core.tile.TileEntityDungeonSpawner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.SoundCategory;

public class TileEntityDungeonSpawnerIo extends TileEntityDungeonSpawner<EntityBossGhast>
{
    public TileEntityDungeonSpawnerIo()
    {
        super(EntityBossGhast.class);
    }

    @Override
    public List<Class<? extends EntityLiving>> getDisabledCreatures()
    {
        List<Class<? extends EntityLiving>> list = new ArrayList<Class<? extends EntityLiving>>();
        list.add(EntityEvolvedSkeleton.class);
        list.add(EntityEvolvedZombie.class);
        list.add(EntityEvolvedSpider.class);
        return list;
    }

    @Override
    public void playSpawnSound(Entity entity)
    {
        this.world.playSound(null, entity.posX, entity.posY, entity.posZ, GCSounds.scaryScape, SoundCategory.AMBIENT, 9.0F, 1.4F);
    }
}
