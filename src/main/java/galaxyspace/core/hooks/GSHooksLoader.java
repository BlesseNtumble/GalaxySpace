package galaxyspace.core.hooks;

import galaxyspace.core.hooklib.minecraft.HookLoader;
import galaxyspace.core.hooklib.minecraft.PrimaryClassTransformer;

public class GSHooksLoader extends HookLoader {

	@Override
    public String[] getASMTransformerClass() {
        return new String[]{PrimaryClassTransformer.class.getName()};
    }
	
	@Override
	protected void registerHooks() {
		registerHookContainer("galaxyspace.core.hooks.GSHooksManager");
	}

}
