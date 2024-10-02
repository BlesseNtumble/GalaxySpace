//- By Vamig Aliev.
//- https://vk.com/win_vista.

package galaxyspace.core.world.worldengine.perlinnoise;

/**
 * WorldEngine's value noise class.
 * See: https://en.wikipedia.org/wiki/Value_noise; https://habr.com/post/142592/ (<= In fact, it is not Perlin noise).
 * @author VamigA
 */
public class ValueNoise extends GenNoiseTemplate {
	/**
	 * Constructor.
	 * @param gSeed - seed.
	 * @param gPersistence - persistence (amplitude multiplier of each octave relative to the previous one).
	 * @param numOfOctaves - number of octaves.
	 * @param scl_x - scale multiplier X of the whole wave.
	 * @param scl_y - scale multiplier Y of the whole wave.
	 * @param scl_z - scale multiplier Z of the whole wave.
	 * @param sum - height (it will be added to noise result).
	 * @param interpolation - interpolation type (0 - linear; 1 - smoothstep; 2 - smootherstep).
	 */
	public ValueNoise(long gSeed, double gPersistence, int numOfOctaves, double scl_x, double scl_y, double scl_z, int sum, byte interpolation) {
		super(gSeed, gPersistence, numOfOctaves, scl_x, scl_y, scl_z, sum, interpolation);
	}
	
	/**
	 * Constructor (scl_x = scl_z).
	 * @param gSeed - seed.
	 * @param gPersistence - persistence (amplitude multiplier of each octave relative to the previous one).
	 * @param numOfOctaves - number of octaves.
	 * @param scl_xz - scale multiplier X and Z of the whole wave.
	 * @param scl_y - scale multiplier Y of the whole wave.
	 * @param sum - height (it will be added to noise result).
	 */
	public ValueNoise(long gSeed, double gPersistence, int numOfOctaves, double scl_xz, double scl_y, int sum) {
		super(gSeed, gPersistence, numOfOctaves, scl_xz, scl_y, sum);
	}
	
	/**
	 * Noise function (main). Generates noise or one of the octaves of the noise.
	 * @param x - coordinate X.
	 * @param z - coordinate Z.
	 */
	public double noiseOctave2d(double x, double z) {
		long squareStartX = (long)x, squareStartZ = (long)z, xs = 1L, zs = 1L; if(Math.abs(x) != x) xs = -1L; if(Math.abs(z) != z) zs = -1L;
		double pointInQuadX = Math.abs(x) - (double)Math.abs(squareStartX), pointInQuadZ = Math.abs(z) - (double)Math.abs(squareStartZ),
		
		topLeft     = rand.smartGen2d(squareStartX     , squareStartZ     ),
		topRight    = rand.smartGen2d(squareStartX + xs, squareStartZ     ),
		bottomLeft  = rand.smartGen2d(squareStartX     , squareStartZ + zs),
		bottomRight = rand.smartGen2d(squareStartX + xs, squareStartZ + zs),
		
		line1 = lerp3d(topLeft   , topRight   , autoSmooth1d(pointInQuadX)),
		line2 = lerp3d(bottomLeft, bottomRight, autoSmooth1d(pointInQuadX));
		return lerp3d(line1, line2, autoSmooth1d(pointInQuadZ));
	}
	
	/**
	 * Noise function (main). Generates noise or one of the octaves of the noise.
	 * @param x - coordinate X.
	 * @param z - coordinate Z.
	 */
	public float noiseOctave2f(float x, float z) {
		int squareStartX = (int)x, squareStartZ = (int)z, xs = 1, zs = 1; if(Math.abs(x) != x) xs = -1; if(Math.abs(z) != z) zs = -1;
		float pointInQuadX = Math.abs(x) - (float)Math.abs(squareStartX), pointInQuadZ = Math.abs(z) - (float)Math.abs(squareStartZ),
		
		topLeft     = rand.smartGen2f(squareStartX     , squareStartZ     ),
		topRight    = rand.smartGen2f(squareStartX + xs, squareStartZ     ),
		bottomLeft  = rand.smartGen2f(squareStartX     , squareStartZ + zs),
		bottomRight = rand.smartGen2f(squareStartX + xs, squareStartZ + zs),
		
		line1 = lerp3f(topLeft   , topRight   , autoSmooth1f(pointInQuadX)),
		line2 = lerp3f(bottomLeft, bottomRight, autoSmooth1f(pointInQuadX));
		return lerp3f(line1, line2, autoSmooth1f(pointInQuadZ));
	}
}