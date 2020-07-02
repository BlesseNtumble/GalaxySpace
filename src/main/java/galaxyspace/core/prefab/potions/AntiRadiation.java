package galaxyspace.core.prefab.potions;

import asmodeuscore.core.utils.ACAttributePlayer;
import galaxyspace.GalaxySpace;
import galaxyspace.core.GSPotions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AntiRadiation extends Potion{

	private final ResourceLocation iconTexture;
	
	public AntiRadiation(boolean isBad, int color)
	{
		super(isBad, color);
		this.setPotionName(this, "antiradiation");
		iconTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/potions/radiation.png");		
	}
	
	public static void setPotionName(Potion potion, final String potionName) {
		potion.setRegistryName(GalaxySpace.MODID, potionName);
		potion.setPotionName("effect." + potionName);
	}

	@Override
	public boolean hasStatusIcon() {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc)
	{
		super.renderInventoryEffect(x, y, effect, mc);
		if (mc.currentScreen != null)
		{
			mc.getTextureManager().bindTexture(iconTexture);
			Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha)
	{
		super.renderHUDEffect(x, y, effect, mc, alpha);
		mc.getTextureManager().bindTexture(iconTexture);
		Gui.drawModalRectWithCustomSizedTexture(x + 4, y + 4, 0, 0, 18, 18, 18, 18);
	}	
	
	@Override
	public boolean isReady(int duration, int amplifier)
	{
		if (this == GSPotions.antiradiation)
		{
			int k = 20 >> amplifier;
			return k > 0 ? duration % k == 0 : true;
		}
		return false;
	}

	@Override
	public void performEffect(EntityLivingBase living, int food)
	{
		if (this == GSPotions.antiradiation)
		{
			if(living instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) living;
				IAttributeInstance lvl = player.getEntityAttribute(ACAttributePlayer.RADIATION_LVL);
				if(lvl.getAttributeValue() > 0) lvl.setBaseValue(lvl.getAttributeValue() - 1);
				else if(lvl.getAttributeValue() <= 0) player.removePotionEffect(this);
			}
		}
	}
}
