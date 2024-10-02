package galaxyspace.core.world.gen;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class WorldGenCustomStructure extends WorldGenerator {

	/**
	 * @return 3d matrix
	**/
	protected abstract byte[][][] getLayersMatrix();

	/**
     * @return true, если нужна центровка
     */
    protected boolean needCentering()
    {
        return true;
    }
    
    protected int rotate()
    {
    	return 0;
    }
    /**
     * @param x, y, z - позиция блока
     * @param matrixValue - значение матрицы для этого блока
     * @return
     */
    protected abstract void generateBlock(World world, Random random, int x, int y, int z, int matrixValue);
    
    @Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		byte[][][] matrix = getLayersMatrix();
        for (int py = 0; py < matrix.length; ++py) {
            for (int px = 0; px < matrix[py].length; ++px) {
                for (int pz = 0; pz < matrix[py][px].length; ++pz) {
                    int px2 = px, py2 = py, pz2 = pz;
                    if (needCentering()) {
                        px2 -= matrix[py].length / 2;
                        pz2 -= matrix[py][px].length / 2;
                    }
                    
                    int matrix1 = matrix[py][px][pz];
                    
                    switch(rotate())
                    {
                    	case 0: 
                    		break;
                    	case 1:
                    		matrix1 = matrix[py][pz][px];
                    		break;
                    	case 2:
                    		matrix1 = matrix[py][(matrix[py].length - 1) - px][(matrix[py][px].length - 1) - pz];
                    	case 3:  
                    		matrix1 = matrix[py][(matrix[py][px].length - 1) - pz][(matrix[py].length - 1) - px];
                    		break;
                    }
                    generateBlock(world, rand, x + px2, y + py2, z + pz2, matrix1);
                }
            }
        }
        return true;
	}
}
