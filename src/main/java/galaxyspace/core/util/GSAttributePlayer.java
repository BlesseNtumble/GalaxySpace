package galaxyspace.core.util;

import net.minecraft.entity.ai.attributes.BaseAttribute;
import net.minecraft.entity.ai.attributes.IAttribute;

public class GSAttributePlayer {
	
	public static final IAttribute TOGGLE_HELMET = new BaseAttribute(null, "helmetkey", 0)
	{
		@Override
		public double clampValue(double value)
		{
			return value;
		}
	}.setShouldWatch(true);
	
	public static final IAttribute TOGGLE_CHEST = new BaseAttribute(null, "chestkey", 0)
	{
		@Override
		public double clampValue(double value)
		{
			return value;
		}
	}.setShouldWatch(true);
	
	public static final IAttribute TOGGLE_LEGS = new BaseAttribute(null, "legskey", 0)
	{
		@Override
		public double clampValue(double value)
		{
			return value;
		}
	}.setShouldWatch(true);
	
	public static final IAttribute TOGGLE_BOOTS = new BaseAttribute(null, "bootskey", 0)
	{
		@Override
		public double clampValue(double value)
		{
			return value;
		}
	}.setShouldWatch(true);
	
}
