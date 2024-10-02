package galaxyspace.core.prefab.potions;

import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.potions.GSPotions;
import galaxyspace.core.util.GSDamageSource;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class Radiation extends Potion
{
	public Radiation(int id, boolean isBad, int color)
	{
		super(id, isBad, color);
		this.setIconIndex(1, 0);
	}

	@Override
	public int getStatusIconIndex()
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(GalaxySpace.ASSET_PREFIX + ":" + "textures/gui/potions/GSPotionFX.png"));
		return 0;
	}

	@Override
	public boolean isReady(int duration, int amplifier)
	{
		if (this.id == GSPotions.radiation.id)
		{
			int k = 20 >> amplifier;
			return k > 0 ? duration % k == 0 : true;
		}
		return false;
	}

	@Override
	public void performEffect(EntityLivingBase living, int food)
	{
		if (this.id == GSPotions.radiation.id)
		{
			living.attackEntityFrom(GSDamageSource.solar, 3.0F);
		}
	}
}