package galaxyspace.core.sounds;

import galaxyspace.GalaxySpace;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class GSSounds
{

    public static SoundEvent music;

    public static void registerSounds(IForgeRegistry<SoundEvent> registry)
    {
        music = registerSound("galaxyspace.music_space", registry);
    }

    private static SoundEvent registerSound(String soundName, IForgeRegistry<SoundEvent> registry)
    {
        ResourceLocation soundID = new ResourceLocation(GalaxySpace.ASSET_PREFIX, soundName);
        SoundEvent result = new SoundEvent(soundID).setRegistryName(soundID);
        registry.register(result);
        return result;
    }
}
