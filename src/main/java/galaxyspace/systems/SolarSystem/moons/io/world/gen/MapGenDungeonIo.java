package galaxyspace.systems.SolarSystem.moons.io.world.gen;

import java.util.List;
import java.util.Random;

import asmodeuscore.core.astronomy.dimension.world.gen.dungeons.MapGenGCDungeon;
import asmodeuscore.core.astronomy.dimension.world.gen.dungeons.standart.DungeonConfiguration;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon.CorridorIo;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon.DungeonStartIo;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon.RoomBossIo;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon.RoomChestIo;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon.RoomEmptyIo;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon.RoomSpawnerIo;
import galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon.RoomTreasureIo;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenDungeonIo extends MapGenGCDungeon
{
    private static boolean initialized;

    private DungeonConfiguration configuration;
    
    static
    {
        try
        {
        	MapGenDungeonIo.initiateStructures();
        }
        catch (Throwable e)
        {

        }
    }

    public MapGenDungeonIo(DungeonConfiguration configuration)
    {
        super(configuration);
        this.configuration = configuration;
    }
    
    public static void initiateStructures() throws Throwable
    {
        if (!MapGenDungeonIo.initialized)
        {
            MapGenStructureIO.registerStructure(Start.class, "IoDungeon");
            MapGenStructureIO.registerStructureComponent(DungeonStartIo.class, "IoDungeonStart");
        	MapGenStructureIO.registerStructureComponent(CorridorIo.class, "IoDungeonCorridor");
        	MapGenStructureIO.registerStructureComponent(RoomEmptyIo.class, "IoDungeonEmptyRoom");
        	MapGenStructureIO.registerStructureComponent(RoomSpawnerIo.class, "IoDungeonSpawnerRoom");
            MapGenStructureIO.registerStructureComponent(RoomChestIo.class, "IoDungeonChestRoom");
            MapGenStructureIO.registerStructureComponent(RoomBossIo.class, "IoDungeonBossRoom");
            MapGenStructureIO.registerStructureComponent(RoomTreasureIo.class, "IoDungeonTreasureRoom");
        }
        
        MapGenDungeonIo.initialized = true;
    }
    
    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        return new Start(this.world, this.rand, chunkX, chunkZ, this.configuration);
    }

    @Override
    public String getStructureName()
    {
        return "GS_Dungeon_Io";
    }
    
    @Override
    public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean p_180706_3_)
    {
        return null;
    }
    
    public static class Start extends StructureStart
    {
        private DungeonConfiguration configuration;

        public Start()
        {
        }

        public Start(World worldIn, Random rand, int chunkX, int chunkZ, DungeonConfiguration configuration)
        {
            super(chunkX, chunkZ);
            this.configuration = configuration;
            DungeonStartIo startPiece = new DungeonStartIo(worldIn, configuration, rand, (chunkX << 4) + 2, (chunkZ << 4) + 2);
            startPiece.buildComponent(startPiece, this.components, rand);
            List<StructureComponent> list = startPiece.attachedComponents;

            while (!list.isEmpty())
            {
                int i = rand.nextInt(list.size());
                StructureComponent structurecomponent = list.remove(i);
                structurecomponent.buildComponent(startPiece, this.components, rand);
            }

            this.updateBoundingBox();
        }
    }
}
