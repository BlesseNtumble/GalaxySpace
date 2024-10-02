package galaxyspace.core.util;

import java.util.Random;

import cpw.mods.fml.client.FMLClientHandler;
import galaxyspace.api.dimension.IAdvancedSpace;
import galaxyspace.core.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.MathHelper;

public class GSMusicTicker extends MusicTicker
{
	private ISound field_147678_c;
	private final Minecraft field_147677_b;
	private int field_147676_d = 100;
	private final Random field_147679_a = new Random();
	
    public GSMusicTicker(Minecraft mc)
    {
    	super(mc);
    	this.field_147677_b = mc;
    }

    @Override
    public void update()
    {
    	MusicTicker.MusicType musictype = this.field_147677_b.func_147109_W();         
        
        if (FMLClientHandler.instance().getWorldClient() != null && FMLClientHandler.instance().getWorldClient().provider instanceof IAdvancedSpace)
        {
            musictype = ClientProxy.GS_MUSIC;
        }

        if (this.field_147678_c != null)
        {
            if (!musictype.getMusicTickerLocation().equals(this.field_147678_c.getPositionedSoundLocation()))
            {
                this.field_147677_b.getSoundHandler().stopSound(this.field_147678_c);
                this.field_147676_d = MathHelper.getRandomIntegerInRange(this.field_147679_a, 0, musictype.func_148634_b() / 2);
            }

            if (!this.field_147677_b.getSoundHandler().isSoundPlaying(this.field_147678_c))
            {
                this.field_147678_c = null;
                this.field_147676_d = Math.min(MathHelper.getRandomIntegerInRange(this.field_147679_a, musictype.func_148634_b(), musictype.func_148633_c()), this.field_147676_d);
            }
        }

        if (this.field_147678_c == null && this.field_147676_d-- <= 0)
        {
            this.field_147678_c = PositionedSoundRecord.func_147673_a(musictype.getMusicTickerLocation());
            this.field_147677_b.getSoundHandler().playSound(this.field_147678_c);
            this.field_147676_d = Integer.MAX_VALUE;
        }
    }
}