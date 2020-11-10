package galaxyspace.core.util;

import net.minecraft.util.DamageSource;

public class GSDamageSource extends DamageSource{
	
	public static final GSDamageSource acid = (GSDamageSource) new GSDamageSource("acid").setDamageIsAbsolute();
	
    public GSDamageSource(String damageType)
    {
        super(damageType);
    }
}
