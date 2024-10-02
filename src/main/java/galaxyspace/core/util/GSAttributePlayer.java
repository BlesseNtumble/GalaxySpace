package galaxyspace.core.util;

import net.minecraft.entity.ai.attributes.BaseAttribute;
import net.minecraft.entity.ai.attributes.IAttribute;

public class GSAttributePlayer {
	
	public static final IAttribute OXTANKS_LEFT = new BaseAttribute("noleft", 0)
	{
	    @Override
	    public double clampValue(double value)
	    {
	        return value;
	    }
	}.setShouldWatch(true);
	
	public static final IAttribute OXTANKS_RIGHT = new BaseAttribute("noright", 0)
	{
	    @Override
	    public double clampValue(double value)
	    {
	        return value;
	    }
	}.setShouldWatch(true);
	
	public static final IAttribute RADIATION_LVL = new BaseAttribute("radiation", 0)
	{
		@Override
		public double clampValue(double value)
		{
			return value;
		}
	}.setShouldWatch(true);
	
	public static final IAttribute PRESSURE_PROTECT = new BaseAttribute("pressureprotect", 0)
	{
		@Override
		public double clampValue(double value)
		{
			return value;
		}
	}.setShouldWatch(true);
	
	public static final IAttribute TOGGLE_HELMET = new BaseAttribute("helmetglosses", 0)
	{
		@Override
		public double clampValue(double value)
		{
			return value;
		}
	}.setShouldWatch(true);
	
	public static final IAttribute TOGGLE_JETPACK = new BaseAttribute("jetpack", 0)
	{
		@Override
		public double clampValue(double value)
		{
			return value;
		}
	}.setShouldWatch(true);
	
	public static final IAttribute RACE = new BaseAttribute("race", 0)
	{
		@Override
		public double clampValue(double value)
		{
			return value;
		}
	}.setShouldWatch(true);
	
	
}
