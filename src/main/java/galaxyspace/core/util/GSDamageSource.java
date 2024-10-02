package galaxyspace.core.util;

import net.minecraft.util.DamageSource;

public class GSDamageSource extends DamageSource{
	
	public static final GSDamageSource solar = (GSDamageSource) new GSDamageSource("solar").setDamageBypassesArmor();
	public static final GSDamageSource pressure = (GSDamageSource) new GSDamageSource("pressure").setDamageBypassesArmor();
	public static final GSDamageSource acid = (GSDamageSource) new GSDamageSource("acid").setDamageIsAbsolute();
	
    public GSDamageSource(String damageType)
    {
        super(damageType);
    }
}
