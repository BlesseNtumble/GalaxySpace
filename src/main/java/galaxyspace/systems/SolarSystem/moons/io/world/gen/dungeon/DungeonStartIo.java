package galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import asmodeuscore.core.astronomy.dimension.world.gen.dungeons.standart.DungeonConfiguration;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraftforge.fml.common.FMLLog;

public class DungeonStartIo extends RoomEntranceIo
{
    public List<StructureComponent> attachedComponents = Lists.newArrayList();
    public List<StructureBoundingBox> componentBounds = Lists.newArrayList();

    public DungeonStartIo()
    {
    }

    public DungeonStartIo(World world, DungeonConfiguration configuration, Random rand, int blockPosX, int blockPosZ)
    {
        super(world, configuration, rand, blockPosX, blockPosZ);
    }

    @Override
    public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand)
    {
        boolean validAttempt = false;
        final int maxAttempts = 10;
        int attempts = 0;
        while (!validAttempt && attempts < maxAttempts)
        {
            attachedComponents.clear();
            componentBounds.clear();
            componentBounds.add(this.boundingBox);
            listIn.clear();
            listIn.add(this);
            PieceIo next = getNextPiece(this, rand);
            //GalaxySpace.debug("" + next.toString());
            while (next != null)
            {
                listIn.add(next);
                attachedComponents.add(next);
                componentBounds.add(next.getBoundingBox());

                next = next.getNextPiece(this, rand);
            }
            if (attachedComponents.size() >= 3 && attachedComponents.get(attachedComponents.size() - 1) instanceof RoomTreasureIo &&
                    attachedComponents.get(attachedComponents.size() - 3) instanceof RoomBossIo)
            {
            	//GalaxySpace.debug("validet");
                validAttempt = true;
            }
            
            /*attachedComponents.forEach((s) -> {
            	GalaxySpace.debug("" + s);
            });*/
            
            attempts++;

        }

        FMLLog.warning("GS Dungeon generation took " + attempts + " attempt(s)");

        if (!validAttempt)
        {
            int xPos = this.boundingBox.minX + (this.boundingBox.maxX - this.boundingBox.minX) / 2;
            int zPos = this.boundingBox.minZ + (this.boundingBox.maxZ - this.boundingBox.minZ) / 2;
            System.err.println("Could not find valid dungeon layout! This is a bug, please report it, including your world seed (/seed) and dungeon location (" + xPos + ", " + zPos + ")");
        }

        super.buildComponent(componentIn, listIn, rand);
    }

    public boolean checkIntersection(int blockX, int blockZ, int sizeX, int sizeZ)
    {
        return this.checkIntersection(new StructureBoundingBox(blockX, blockZ, blockX + sizeX, blockZ + sizeZ));
    }

    public boolean checkIntersection(StructureBoundingBox bounds)
    {
        for (int i = 0; i < componentBounds.size() - 1; ++i)
        {
            StructureBoundingBox boundingBox = componentBounds.get(i);
            if (boundingBox.intersectsWith(bounds))
            {
                return true;
            }
        }

        return false;
    }
}