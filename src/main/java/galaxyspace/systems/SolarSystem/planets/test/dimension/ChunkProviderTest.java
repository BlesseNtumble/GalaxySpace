package galaxyspace.systems.SolarSystem.planets.test.dimension;

 

import java.util.List;

import com.google.common.collect.Lists;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.world.gen.ChunkProviderSpaceLakes;
import galaxyspace.core.world.gen.GSBiomeGenBase;
import galaxyspace.systems.SolarSystem.planets.ceres.world.gen.BiomeDecoratorCeres;
import galaxyspace.systems.SolarSystem.planets.ceres.world.gen.MapGenCavernCeres;
import galaxyspace.systems.SolarSystem.planets.ceres.world.gen.MapGenCavesCeres;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedCreeper;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.chunk.IChunkProvider;


 

public class ChunkProviderTest extends ChunkProviderSpaceLakes {
	
	/*
	private final GSMapGenDungeon dungeonGenerator = new GSMapGenDungeon(GSBlocks.CeresBlocks, 2, 8, 16, 3, 5, 4);
	 
	 {
       this.dungeonGenerator.otherRooms.add(new RoomEmptyCeres(null, 0, 0, 0, ForgeDirection.UNKNOWN));
       this.dungeonGenerator.otherRooms.add(new RoomSpawnerCeres(null, 0, 0, 0, ForgeDirection.UNKNOWN));
       this.dungeonGenerator.otherRooms.add(new RoomSpawnerCeres(null, 0, 0, 0, ForgeDirection.UNKNOWN));
       this.dungeonGenerator.otherRooms.add(new RoomSpawnerCeres(null, 0, 0, 0, ForgeDirection.UNKNOWN));
       this.dungeonGenerator.otherRooms.add(new RoomSpawnerCeres(null, 0, 0, 0, ForgeDirection.UNKNOWN));
       this.dungeonGenerator.otherRooms.add(new RoomSpawnerCeres(null, 0, 0, 0, ForgeDirection.UNKNOWN));
       this.dungeonGenerator.otherRooms.add(new RoomSpawnerCeres(null, 0, 0, 0, ForgeDirection.UNKNOWN));
       this.dungeonGenerator.otherRooms.add(new RoomSpawnerCeres(null, 0, 0, 0, ForgeDirection.UNKNOWN));
       this.dungeonGenerator.otherRooms.add(new RoomSpawnerCeres(null, 0, 0, 0, ForgeDirection.UNKNOWN));
       this.dungeonGenerator.otherRooms.add(new RoomChestsCeres(null, 0, 0, 0, ForgeDirection.UNKNOWN));
       this.dungeonGenerator.otherRooms.add(new RoomChestsCeres(null, 0, 0, 0, ForgeDirection.UNKNOWN));
       this.dungeonGenerator.bossRooms.add(new RoomBossCeres(null, 0, 0, 0, ForgeDirection.UNKNOWN));
       this.dungeonGenerator.treasureRooms.add(new RoomTreasureCeres(null, 0, 0, 0, ForgeDirection.UNKNOWN));
		
	 }
	 */
    
    private BiomeGenBase[] biomesForGeneration = this.getBiomesForGeneration();
    private final MapGenCavesCeres caveGenerator = new MapGenCavesCeres();
    private final MapGenCavernCeres cavernGenerator = new MapGenCavernCeres();

 @Override
    protected List<MapGenBaseMeta> getWorldGenerators()
    {
        List<MapGenBaseMeta> generators = Lists.newArrayList();
        generators.add(this.caveGenerator);
        generators.add(this.cavernGenerator);
        return generators;
    }

public ChunkProviderTest(World par1World, long seed, boolean mapFeaturesEnabled) 

{
	super(par1World, seed, mapFeaturesEnabled);

}

@Override

protected BiomeDecoratorSpace getBiomeGenerator() {


    return new BiomeDecoratorCeres();

}



 //This should be a custom biome for your mod, but I'm opting to go desert instead out of quickness

//and the fact that biomes are outside the scope of this tutorial

@Override

protected BiomeGenBase[] getBiomesForGeneration() {


    return new BiomeGenBase[]{GSBiomeGenBase.GSSpace};

}



@Override
public int getCraterProbability() {

    return 300;

}



@Override
protected SpawnListEntry[] getCreatures() {

 

    return new SpawnListEntry[]{};

}


@Override
public double getHeightModifier() {

    // TODO Auto-generated method stub

    return 20;

}



@Override

protected SpawnListEntry[] getMonsters() {

    SpawnListEntry skele = new SpawnListEntry(EntityEvolvedSkeleton.class, 100, 4, 4);
    SpawnListEntry creeper = new SpawnListEntry(EntityEvolvedCreeper.class, 100, 4, 4);
    SpawnListEntry spider = new SpawnListEntry(EntityEvolvedSpider.class, 100, 4, 4);
    
    return new SpawnListEntry[]{skele, creeper, spider};

}



@Override

public double getMountainHeightModifier() {

    return 0;

}


@Override
public double getSmallFeatureHeightModifier() {

    return 1;

}



@Override

public double getValleyHeightModifier() {

    return 0;

}


@Override
public void onChunkProvider(int cX, int cZ, Block[] blocks, byte[] metadata) {
 
}



@Override

public void onPopulate(IChunkProvider arg0, int arg1, int arg2){	

}


@Override

public boolean chunkExists(int x, int y){

    return false;

}


@Override
protected SpawnListEntry[] getWaterCreatures() {

	return new SpawnListEntry[]{};
}

@Override
protected BlockMetaPair getGrassBlock() {
	return new BlockMetaPair(GSBlocks.CeresBlocks, (byte) 0);
}

@Override
protected BlockMetaPair getDirtBlock() {
	return new BlockMetaPair(GSBlocks.CeresBlocks, (byte) 1);
}

@Override
protected BlockMetaPair getStoneBlock() {
	return new BlockMetaPair(GSBlocks.CeresBlocks, (byte) 1);
}

@Override
protected boolean enableBiomeGenBaseBlock() {
	return false;
}

@Override
public int getWaterLevel() {
	return 80;
}

@Override
public boolean canGenerateWaterBlock() {
	return false;
}

@Override
public boolean canGenerateIceBlock() {
	return false;
}

@Override
protected BlockMetaPair getWaterBlock() {
	return null;
}

@Override
protected GenType getGenType() {
	return GenType.GC;
}

}