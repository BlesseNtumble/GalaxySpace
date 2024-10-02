package galaxyspace.api.dimension;

public interface IAdvancedSpace
{
	public enum ClassBody
	{
		STONE,
		OCEANIDE,
		GASGIANT,
		ASTEROID,
		TITAN,
		SPACESTATION;
	}
	
	public int AtmosphericPressure();
	public boolean SolarRadiation();
	public double getSolarWindMultiplier();
	public ClassBody getClassBody();
}
