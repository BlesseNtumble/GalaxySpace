package galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon;

import java.lang.reflect.Constructor;
import java.util.Random;

import asmodeuscore.core.astronomy.dimension.world.gen.dungeons.standart.DungeonConfiguration;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.TemplateManager;

public abstract class DirectionalPieceIo extends PieceIo
{
    private EnumFacing direction;

    public DirectionalPieceIo()
    {
    }

    public DirectionalPieceIo(DungeonConfiguration configuration, EnumFacing direction)
    {
        super(configuration);
        this.direction = direction;
    }

    public EnumFacing getDirection()
    {
        return direction;
    }

    public void setDirection(EnumFacing direction)
    {
        this.direction = direction;
    }

    @Override
    protected void writeStructureToNBT(NBTTagCompound tagCompound)
    {
        super.writeStructureToNBT(tagCompound);

        tagCompound.setInteger("direction", this.direction.ordinal());
    }

    @Override
    protected void readStructureFromNBT(NBTTagCompound nbt, TemplateManager manager)
    {
        super.readStructureFromNBT(nbt, manager);

        if (nbt.hasKey("direction"))
        {
            this.direction = EnumFacing.byIndex(nbt.getInteger("direction"));
        }
        else
        {
            this.direction = EnumFacing.NORTH;
        }
    }

    public PieceIo getCorridor(Random rand, DungeonStartIo startPiece, int maxAttempts, boolean small)
    {
    	return getCorridor(rand, startPiece, maxAttempts, small, 6);
    }
    
    public PieceIo getCorridor(Random rand, DungeonStartIo startPiece, int maxAttempts, boolean small, int width)
    {
        EnumFacing randomDir;
        int blockX;
        int blockZ;
        int sizeX;
        int sizeZ;
        boolean valid;
        int attempts = maxAttempts;
        do
        {
            int randDir = rand.nextInt(4);
            randomDir = EnumFacing.byHorizontalIndex((randDir == getDirection().getOpposite().getHorizontalIndex() ? randDir + 1 : randDir) % 4);
            StructureBoundingBox extension = getExtension(randomDir, this.configuration.getHallwayLengthMin() + rand.nextInt(this.configuration.getHallwayLengthMax() - this.configuration.getHallwayLengthMin()), width);
            blockX = extension.minX;
            blockZ = extension.minZ;
            sizeX = extension.maxX - extension.minX;
            sizeZ = extension.maxZ - extension.minZ;
            valid = !startPiece.checkIntersection(extension);
            attempts--;
        }
        while (!valid && attempts > 0);

        if (!valid)
        {
            return null;
        }

        return getRoom(this.configuration.getCorridor(), startPiece, rand, blockX, blockZ, sizeX, small ? 6 : this.configuration.getHallwayHeight(), sizeZ, randomDir);//new Corridor(this.configuration, rand, blockX, blockZ, sizeX, small ? 3 : this.configuration.getHallwayHeight(), sizeZ, randomDir);
    }
    
    private <T extends SizedPieceIo> T getRoom(Class<?> clazz, DungeonStartIo startPiece, Random rand, int blockX, int blockZ, int sizeX, int sizeY, int sizeZ, EnumFacing randomDir)
    {
        try
        {
            /*Constructor<?> c0 = clazz.getConstructor(DungeonConfiguration.class, Random.class, Integer.TYPE, Integer.TYPE, EnumFacing.class);
            T dummy = (T) c0.newInstance(this.configuration, rand, 0, 0, this.getDirection().getOpposite());
            StructureBoundingBox extension = getExtension(this.getDirection(), getDirection().getAxis() == EnumFacing.Axis.X ? dummy.getSizeX() : dummy.getSizeZ(), getDirection().getAxis() == EnumFacing.Axis.X ? dummy.getSizeZ() : dummy.getSizeX());
            if (startPiece.checkIntersection(extension))
            {
                return null;
            }
            int sizeX = extension.maxX - extension.minX;
            int sizeZ = extension.maxZ - extension.minZ;
            int sizeY = small ? 3 : this.configuration.getHallwayHeight();//dummy.sizeY;
            int blockX = extension.minX;
            int blockZ = extension.minZ;*/
            Constructor<?> c1 = clazz.getConstructor(DungeonConfiguration.class, Random.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, EnumFacing.class);
            return (T) c1.newInstance(this.configuration, rand, blockX, blockZ, sizeX, sizeY, sizeZ, randomDir);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
}

