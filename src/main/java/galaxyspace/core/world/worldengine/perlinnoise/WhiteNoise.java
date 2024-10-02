//- By Vamig Aliev.
//- https://vk.com/win_vista.

package galaxyspace.core.world.worldengine.perlinnoise;

import net.minecraft.util.MathHelper;

/**
 * WorldEngine's white noise class.
 * The functions returns the same pseudo-random number from -1.0 to 1.0 at seed, x and z.
 * @author VamigA
 */
public class WhiteNoise {
	/** Generation seed. */
	public long seed;
	
	/**
	 * Constructor.
	 * @param genSeed - seed.
	 */
	public WhiteNoise(long genSeed) {
		seed = (long)Math.pow(genSeed, 11L) * 17L + 514L;
	}
	
	/**
	 * Returns the same (double) pseudo-random number from -1.0 to 1.0 at (long) x and (long) z.
	 * @param x - coordinate X.
	 * @param z - coordinate Z.
	 */
	public double gen2d(long x, long z) {
		long n = seed + x * 4L + z * 341L; n = (n << 13L) ^ n;
	    return 1.0 - (double)((n * (n * n * 15731L + 789221L) + 1376312589L) & 2147483647L) / 1073741824.0;
	}
	
	/**
	 * Returns the same (float) pseudo-random number from -1.0 to 1.0 at (int) x and (int) z.
	 * @param x - coordinate X.
	 * @param z - coordinate Z.
	 */
	public float gen2f(int x, int z) {
		int n = (int)seed + x * 4 + z * 341; n = (n << 13) ^ n;
	    return 1.0F - (float)((n * (n * n * 15731 + 789221) + 1376312589) & 2147483647) / 1073741824.0F;
	}
	
	/**
	 * Returns the same (double) pseudo-random vector (length: 1) at (long) x and (long) z.
	 * @param x - coordinate X.
	 * @param z - coordinate Z.
	 */
	public double[] vecGen2d(long x, long z) {
		double angle = gen2d(x, z) * Math.PI;
		return new double[] {(double)MathHelper.cos((float)angle), (double)MathHelper.sin((float)angle)};
	}
	
	/**
	 * Returns the same (float) pseudo-random vector (length: 1) at (int) x and (int) z.
	 * @param x - coordinate X.
	 * @param z - coordinate Z.
	 */
	public float[] vecGen2f(int x, int z) {
		float angle = gen2f(x, z) * (float)Math.PI;
		return new float[] {MathHelper.cos(angle), MathHelper.sin(angle)};
	}
	
	/**
	 * Smart version of function gen2d(x, z): temporarily writes the noise data of every position for further using and processor time economy. TODO: Code that smart function!
	 * @param x - coordinate X.
	 * @param z - coordinate Z.
	 */
	public double smartGen2d(long x, long z) {
		return gen2d(x, z);
	}
	
	/**
	 * Smart version of function gen2f(x, z): temporarily writes the noise data of every position for further using and processor time economy. TODO: Code that smart function!
	 * @param x - coordinate X.
	 * @param z - coordinate Z.
	 */
	public float smartGen2f(int x, int z) {
		return gen2f(x, z);
	}
	
	/**
	 * Smart version of function vecGen2d(x, z): temporarily writes the noise data of every position for further using and processor time economy. TODO: Code that smart function!
	 * @param x - coordinate X.
	 * @param z - coordinate Z.
	 */
	public double[] smartVecGen2d(long x, long z) {
		return vecGen2d(x, z);
	}
	
	/**
	 * Smart version of function vecGen2f(x, z): temporarily writes the noise data of every position for further using and processor time economy. TODO: Code that smart function!
	 * @param x - coordinate X.
	 * @param z - coordinate Z.
	 */
	public float[] smartVecGen2f(int x, int z) {
		return vecGen2f(x, z);
	}
}