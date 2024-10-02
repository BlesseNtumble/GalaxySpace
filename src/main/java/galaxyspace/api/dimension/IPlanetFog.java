package galaxyspace.api.dimension;

public interface IPlanetFog
{
	public float getFogDensity(int x, int y, int z);
	public int getFogColor(int x, int y, int z);
}