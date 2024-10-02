package galaxyspace.core.prefab.potions;

import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.potions.GSPotions;
import galaxyspace.core.util.GSAttributePlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class AntiRadiation extends Potion{

	public AntiRadiation(int id, boolean isBad, int color)
	{
		super(id, isBad, color);
		this.setIconIndex(2, 0);
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
		if (this.id == GSPotions.antiradiation.id)
		{
			int k = 20 >> amplifier;
			return k > 0 ? duration % k == 0 : true;
		}
		return false;
	}

	@Override
	public void performEffect(EntityLivingBase living, int food)
	{
		if (this.id == GSPotions.antiradiation.id)
		{
			if(living instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) living;
				IAttributeInstance lvl = player.getEntityAttribute(GSAttributePlayer.RADIATION_LVL);
				if(lvl.getAttributeValue() > 0) lvl.setBaseValue(lvl.getAttributeValue() - 1);
				else if(lvl.getAttributeValue() <= 0) player.removePotionEffect(GSPotions.antiradiation.id);
			}
		}
	}
}
