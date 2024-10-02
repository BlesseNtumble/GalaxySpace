//- By Vamig Aliev.
//- https://vk.com/win_vista.

package galaxyspace.core.world.worldengine.perlinnoise;

/**
 * It's a template for "Value Noise" and "Perlin Noise" classes.
 * @author VamigA
 */
public abstract class GenNoiseTemplate {
	/** Generation seed. */
	public long seed;
	/** Persistence - amplitude multiplier of each octave relative to the previous one. Scale(X...Y...Z) - scale multipliers of the whole wave. */
	public double persistence, scaleX, scaleY, scaleZ;
	/** Octaves - number of octaves. Height - it will be added to noise result. */
	public int octaves, height;
	/** Using interpolation type (0 - linear; 1 - smoothstep; 2 - smootherstep). */
	public byte iType;
	
	/** Pseudo-random generator. */
	public WhiteNoise rand;
	
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
	public GenNoiseTemplate(long gSeed, double gPersistence, int numOfOctaves, double scl_x, double scl_y, double scl_z, int sum, byte interpolation) {
		seed = gSeed; persistence = gPersistence; octaves = numOfOctaves; scaleX = scl_x; scaleY = scl_y; scaleZ = scl_z; height = sum; iType = interpolation;
		rand = new WhiteNoise(gSeed);
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
	public GenNoiseTemplate(long gSeed, double gPersistence, int numOfOctaves, double scl_xz, double scl_y, int sum) {
		this(gSeed, gPersistence, numOfOctaves, scl_xz, scl_y, scl_xz, sum, (byte)2);
	}
	
	/**
	 * Linear interpolation (gives a straight). See: https://en.wikipedia.org/wiki/Linear_interpolation.
	 * @param a - value A.
	 * @param b - value B.
	 * @param n - some value from 0.0 to 1.0 (from A to B).
	 */
	public double lerp3d(double a, double b, double n) {
		return a + (b - a) * n;
	}
	
	/**
	 * Linear interpolation (gives a straight). See: https://en.wikipedia.org/wiki/Linear_interpolation.
	 * @param a - value A.
	 * @param b - value B.
	 * @param n - some value from 0.0 to 1.0 (from A to B).
	 */
	public float lerp3f(float a, float b, float n) {
		return a + (b - a) * n;
	}
	
	/**
	 * Smoothstep function. From the straight makes in graph a beautiful curve flattened at the ends (0.0 and 1.0).
	 * See: https://en.wikipedia.org/wiki/Smoothstep.
	 * @param n - some value from 0.0 to 1.0.
	 */
	public double smoothstep1d(double n) {
		return n * n * (3.0 - 2.0 * n);
	}
	
	/**
	 * Smoothstep function. From the straight makes in graph a beautiful curve flattened at the ends (0.0 and 1.0).
	 * See: https://en.wikipedia.org/wiki/Smoothstep.
	 * @param n - some value from 0.0 to 1.0.
	 */
	public float smoothstep1f(float n) {
		return n * n * (3.0F - 2.0F * n);
	}
	
	/**
	 * Smootherstep function. From the straight makes in graph a beautiful curve flattened at the ends (0.0 and 1.0). Better than Smoothstep.
	 * See: https://en.wikipedia.org/wiki/Smoothstep (you can found there information about Smootherstep).
	 * @param n - some value from 0.0 to 1.0.
	 */
	public double smootherstep1d(double n) {
		return n * n * n * (n * (n * 6.0 - 15.0) + 10.0);
	}
	
	/**
	 * Smootherstep function. From the straight makes in graph a beautiful curve flattened at the ends (0.0 and 1.0). Better than Smoothstep.
	 * See: https://en.wikipedia.org/wiki/Smoothstep (you can found there information about Smootherstep).
	 * @param n - some value from 0.0 to 1.0.
	 */
	public float smootherstep1f(float n) {
		return n * n * n * (n * (n * 6.0F - 15.0F) + 10.0F);
	}
	
	/**
	 * Picks an interpolation way from "iType" variable.
	 * @param n - some value from 0.0 to 1.0.
	 */
	public double autoSmooth1d(double n) {
		switch(iType) {
		case 2: return smootherstep1d(n);
		case 1: return smoothstep1d(n);
		default: return n;
		}
	}
	
	/**
	 * Picks an interpolation way from "iType" variable.
	 * @param n - some value from 0.0 to 1.0.
	 */
	public float autoSmooth1f(float n) {
		switch(iType) {
		case 2: return smootherstep1f(n);
		case 1: return smoothstep1f(n);
		default: return n;
		}
	}
	
	/**
	 * Scalar product of vectors. See: https://en.wikipedia.org/wiki/Dot_product.
	 * @param vec1 - vector 1.
	 * @param vec2 - vector 2.
	 */
	public double dot2d(double[] vec1, double[] vec2) {
		return vec1[0] * vec2[0] + vec1[1] * vec2[1];
	}
	
	/**
	 * Scalar product of vectors. See: https://en.wikipedia.org/wiki/Dot_product.
	 * @param vec1 - vector 1.
	 * @param vec2 - vector 2.
	 */
	public float dot2f(float[] vec1, float[] vec2) {
		return vec1[0] * vec2[0] + vec1[1] * vec2[1];
	}
	
	/**
	 * Noise function (main). Generates noise or one of the octaves of the noise.
	 * @param x - coordinate X.
	 * @param z - coordinate Z.
	 */
	public abstract double noiseOctave2d(double x, double z);
	
	/**
	 * Noise function (main). Generates noise or one of the octaves of the noise.
	 * @param x - coordinate X.
	 * @param z - coordinate Z.
	 */
	public abstract float noiseOctave2f(float x, float z);
	
	/**
	 * Generates noise WITH OCTAVES. You should CALL IT.
	 * @param x - coordinate X.
	 * @param z - coordinate Z.
	 */
	public double genNoise2d(double x, double z) {
		double result = 0.0, amplitudeMultiplier = 1.0, nowX = x, nowZ = z;
		for(int i = 0; i < octaves; i++) {
			result += noiseOctave2d(nowX / scaleX, nowZ / scaleZ) * amplitudeMultiplier;
			amplitudeMultiplier *= persistence;
			nowX *= 2.0; nowZ *= 2.0;
		}
		return (double)height + result * scaleY;
	}
	
	/**
	 * Generates noise WITH OCTAVES. You should CALL IT.
	 * @param x - coordinate X.
	 * @param z - coordinate Z.
	 */
	public float genNoise2f(float x, float z) {
		float result = 0.0F, amplitudeMultiplier = 1.0F, nowX = x, nowZ = z;
		for(int i = 0; i < octaves; i++) {
			result += noiseOctave2f(nowX / (float)scaleX, nowZ / (float)scaleZ) * amplitudeMultiplier;
			amplitudeMultiplier *= (float)persistence;
			nowX *= 2.0F; nowZ *= 2.0F;
		}
		return (float)height + result * (float)scaleY;
	}
}