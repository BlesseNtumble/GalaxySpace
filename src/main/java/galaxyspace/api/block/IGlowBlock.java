package galaxyspace.api.block;

import net.minecraft.util.IIcon;

public interface IGlowBlock {
	
	public IIcon getOverlayForSide(int side, int meta);
	public boolean enableGlow(int meta);
	public int alphaGlow(int meta);
}