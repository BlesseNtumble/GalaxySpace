package galaxyspace.core.hooks;

import asmodeuscore.core.hooklib.minecraft.HookLoader;
import asmodeuscore.core.hooklib.minecraft.PrimaryClassTransformer;

public class GSHooksLoader extends HookLoader{

	@Override
    public String[] getASMTransformerClass() {
        return new String[]{PrimaryClassTransformer.class.getName()};
    }
	
	@Override
	protected void registerHooks() {
		registerHookContainer("galaxyspace.core.hooks.GSHooksManager");
	}

}
