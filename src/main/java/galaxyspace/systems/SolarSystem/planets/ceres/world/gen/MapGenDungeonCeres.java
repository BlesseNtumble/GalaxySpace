package galaxyspace.systems.SolarSystem.planets.ceres.world.gen;

import asmodeuscore.core.astronomy.dimension.world.gen.dungeons.MapGenGCDungeon;
import asmodeuscore.core.astronomy.dimension.world.gen.dungeons.standart.DungeonConfiguration;
import galaxyspace.systems.SolarSystem.planets.ceres.world.gen.dungeon.CorridorCeres;
import galaxyspace.systems.SolarSystem.planets.ceres.world.gen.dungeon.RoomBossCeres;
import galaxyspace.systems.SolarSystem.planets.ceres.world.gen.dungeon.RoomChestCeres;
import galaxyspace.systems.SolarSystem.planets.ceres.world.gen.dungeon.RoomEmptyCeres;
import galaxyspace.systems.SolarSystem.planets.ceres.world.gen.dungeon.RoomSpawnerCeres;
import galaxyspace.systems.SolarSystem.planets.ceres.world.gen.dungeon.RoomTreasureCeres;
import net.minecraft.world.gen.structure.MapGenStructureIO;

public class MapGenDungeonCeres extends MapGenGCDungeon
{
    private static boolean initialized;

    static
    {
        try
        {
        	MapGenDungeonCeres.initiateStructures();
        }
        catch (Throwable e)
        {

        }
    }

    public MapGenDungeonCeres(DungeonConfiguration configuration)
    {
        super(configuration);
    }
    
    public static void initiateStructures() throws Throwable
    {
        if (!MapGenDungeonCeres.initialized)
        {
        	MapGenStructureIO.registerStructureComponent(CorridorCeres.class, "CeresDungeonCorridor");
        	MapGenStructureIO.registerStructureComponent(RoomEmptyCeres.class, "CeresDungeonEmptyRoom");
        	MapGenStructureIO.registerStructureComponent(RoomSpawnerCeres.class, "CeresDungeonSpawnerRoom");
            MapGenStructureIO.registerStructureComponent(RoomChestCeres.class, "CeresDungeonChestRoom");
            MapGenStructureIO.registerStructureComponent(RoomBossCeres.class, "CeresDungeonBossRoom");
            MapGenStructureIO.registerStructureComponent(RoomTreasureCeres.class, "CeresDungeonTreasureRoom");
        }
        
        MapGenDungeonCeres.initialized = true;
    }
}
