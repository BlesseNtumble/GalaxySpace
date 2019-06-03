package galaxyspace.core.sounds;

import asmodeuscore.api.dimension.IAdvancedSpace;
import galaxyspace.core.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.FMLClientHandler;

public class GSMusicTicker extends MusicTicker
{
    public GSMusicTicker(Minecraft mc)
    {
        super(mc);
    }

    @Override
    public void update()
    {
        MusicTicker.MusicType musictype = this.mc.getAmbientMusicType();
        if (FMLClientHandler.instance().getWorldClient() != null && FMLClientHandler.instance().getWorldClient().provider instanceof IAdvancedSpace)
        {
            musictype = ClientProxy.GS_MUSIC;
        }

        if (this.currentMusic != null)
        {
            if (!musictype.getMusicLocation().getSoundName().equals(this.currentMusic.getSoundLocation()))
            {
                this.mc.getSoundHandler().stopSound(this.currentMusic);
                this.timeUntilNextMusic = MathHelper.getInt(this.rand, 0, musictype.getMinDelay() / 2);
            }

            if (!this.mc.getSoundHandler().isSoundPlaying(this.currentMusic))
            {
                this.currentMusic = null;
                this.timeUntilNextMusic = Math.min(MathHelper.getInt(this.rand, musictype.getMinDelay(), musictype.getMaxDelay()), this.timeUntilNextMusic);
            }
        }

        this.timeUntilNextMusic = Math.min(this.timeUntilNextMusic, musictype.getMaxDelay());

        if (this.currentMusic == null && this.timeUntilNextMusic-- <= 0)
        {
            this.playMusic(musictype);
        }
    }
}
