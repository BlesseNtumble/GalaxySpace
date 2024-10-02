package galaxyspace.systems.SolarSystem.moons.io.tile;

import java.util.ArrayList;
import java.util.List;

import galaxyspace.systems.SolarSystem.moons.io.entities.EntityBossGhast;
import galaxyspace.systems.SolarSystem.planets.venus.entities.EntityEvolvedFireBlaze;
import galaxyspace.systems.SolarSystem.planets.venus.entities.EntityEvolvedFireCreeper;
import galaxyspace.systems.SolarSystem.planets.venus.entities.EntityEvolvedFireSkeleton;
import galaxyspace.systems.SolarSystem.planets.venus.entities.EntityEvolvedFireSpider;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedCreeper;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import micdoodle8.mods.galacticraft.core.tile.TileEntityDungeonSpawner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;


public class TileEntityDungeonSpawnerIo extends TileEntityDungeonSpawner
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
        list.add(EntityEvolvedSpider.class);
        list.add(EntityEvolvedCreeper.class);
        
        list.add(EntityEvolvedFireCreeper.class);
        list.add(EntityEvolvedFireSkeleton.class);
        list.add(EntityEvolvedFireSpider.class);
        list.add(EntityEvolvedFireBlaze.class);
        return list;
        
    }

    @Override
    public void playSpawnSound(Entity entity)
    {
        this.worldObj.playSoundAtEntity(entity, GalacticraftCore.TEXTURE_PREFIX + "ambience.scaryscape", 9.0F, 1.4F);
    }
}
