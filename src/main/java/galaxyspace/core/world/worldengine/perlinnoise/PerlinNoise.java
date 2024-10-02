//- By Vamig Aliev.
//- https://vk.com/win_vista.

package galaxyspace.core.world.worldengine.perlinnoise;

/**
 * WorldEngine's Perlin noise class.
 * See: https://en.wikipedia.org/wiki/Perlin_noise; https://habr.com/post/342906/; https://habr.com/post/265775/.
 * @author VamigA
 */
public class PerlinNoise extends GenNoiseTemplate {
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
	public PerlinNoise(long gSeed, double gPersistence, int numOfOctaves, double scl_x, double scl_y, double scl_z, int sum, byte interpolation) {
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
	public PerlinNoise(long gSeed, double gPersistence, int numOfOctaves, double scl_xz, double scl_y, int sum) {
		super(gSeed, gPersistence, numOfOctaves, scl_xz, scl_y, sum);
	}
	
	/**
	 * Noise function (main). Generates noise or one of the octaves of the noise.
	 * @param x - coordinate X.
	 * @param z - coordinate Z.
	 */
	public double noiseOctave2d(double x, double z) {
		long squareStartX = (long)x, squareStartZ = (long)z, xs = 1L, zs = 1L; if(Math.abs(x) != x) xs = -1L; if(Math.abs(z) != z) zs = -1L;
		double pointInQuadX = Math.abs(x) - (double)Math.abs(squareStartX), pointInQuadZ = Math.abs(z) - (double)Math.abs(squareStartZ);
		
		double[]
		topLeft     = rand.smartVecGen2d(squareStartX     , squareStartZ     ),
		topRight    = rand.smartVecGen2d(squareStartX + xs, squareStartZ     ),
		bottomLeft  = rand.smartVecGen2d(squareStartX     , squareStartZ + zs),
		bottomRight = rand.smartVecGen2d(squareStartX + xs, squareStartZ + zs),
		
		distanceToTopLeft     = new double[] {(double)xs * pointInQuadX             , (double)zs * pointInQuadZ             },
		distanceToTopRight    = new double[] {(double)xs * pointInQuadX - (double)xs, (double)zs * pointInQuadZ             },
		distanceToBottomLeft  = new double[] {(double)xs * pointInQuadX             , (double)zs * pointInQuadZ - (double)zs},
		distanceToBottomRight = new double[] {(double)xs * pointInQuadX - (double)xs, (double)zs * pointInQuadZ - (double)zs};
		
		double
		dotTopLeft     = dot2d(topLeft    , distanceToTopLeft    ),
		dotTopRight    = dot2d(topRight   , distanceToTopRight   ),
		dotBottomLeft  = dot2d(bottomLeft , distanceToBottomLeft ),
		dotBottomRight = dot2d(bottomRight, distanceToBottomRight),
		
		line1 = lerp3d(dotTopLeft   , dotTopRight   , autoSmooth1d(pointInQuadX)),
		line2 = lerp3d(dotBottomLeft, dotBottomRight, autoSmooth1d(pointInQuadX));
		return lerp3d(line1, line2, autoSmooth1d(pointInQuadZ));
	}
	
	/**
	 * Noise function (main). Generates noise or one of the octaves of the noise.
	 * @param x - coordinate X.
	 * @param z - coordinate Z.
	 */
	public float noiseOctave2f(float x, float z) {
		int squareStartX = (int)x, squareStartZ = (int)z, xs = 1, zs = 1; if(Math.abs(x) != x) xs = -1; if(Math.abs(z) != z) zs = -1;
		float pointInQuadX = Math.abs(x) - (float)Math.abs(squareStartX), pointInQuadZ = Math.abs(z) - (float)Math.abs(squareStartZ);
		
		float[]
		topLeft     = rand.smartVecGen2f(squareStartX     , squareStartZ     ),
		topRight    = rand.smartVecGen2f(squareStartX + xs, squareStartZ     ),
		bottomLeft  = rand.smartVecGen2f(squareStartX     , squareStartZ + zs),
		bottomRight = rand.smartVecGen2f(squareStartX + xs, squareStartZ + zs),
		
		distanceToTopLeft     = new float[] {(float)xs * pointInQuadX            , (float)zs * pointInQuadZ            },
		distanceToTopRight    = new float[] {(float)xs * pointInQuadX - (float)xs, (float)zs * pointInQuadZ            },
		distanceToBottomLeft  = new float[] {(float)xs * pointInQuadX            , (float)zs * pointInQuadZ - (float)zs},
		distanceToBottomRight = new float[] {(float)xs * pointInQuadX - (float)xs, (float)zs * pointInQuadZ - (float)zs};
		
		float
		dotTopLeft     = dot2f(topLeft    , distanceToTopLeft    ),
		dotTopRight    = dot2f(topRight   , distanceToTopRight   ),
		dotBottomLeft  = dot2f(bottomLeft , distanceToBottomLeft ),
		dotBottomRight = dot2f(bottomRight, distanceToBottomRight),
		
		line1 = lerp3f(dotTopLeft   , dotTopRight   , autoSmooth1f(pointInQuadX)),
		line2 = lerp3f(dotBottomLeft, dotBottomRight, autoSmooth1f(pointInQuadX));
		return lerp3f(line1, line2, autoSmooth1f(pointInQuadZ));
	}
}