package galaxyspace.core.client.gui.book.pages.general;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import asmodeuscore.core.astronomy.gui.book.Page_WithScroll;
import asmodeuscore.core.utils.BookUtils.Book_Cateroies;
import galaxyspace.GalaxySpace;
import galaxyspace.api.IPage;
import galaxyspace.core.util.GSThreadVersionCheck;
import galaxyspace.core.util.GSUtils;
import micdoodle8.mods.galacticraft.core.util.ColorUtil;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

@IPage
public class Page_ActualUpdate extends Page_WithScroll{

	private Minecraft mc = Minecraft.getMinecraft();
	private int count = 0;
	//List<String> changelog = new ArrayList<String>();
	private boolean ex = false;
	
	private static ResourceLocation bookPageTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/tablet.png");
		
	public Page_ActualUpdate() {}
	
	public Page_ActualUpdate(boolean exit) {
		this.ex = exit;
	}
	
	@Override
	public String titlePage() {
		return "actual_update";
	}

	@Override
	public ResourceLocation iconTitle() {
		return null;
	}

	@Override
	public boolean hookBackButton()
	{
		if(this.ex)
		{
			Minecraft.getMinecraft().setIngameFocus();
			return true;
		}
		return false;
	}
	
	@Override
	public void drawPage(int x, int y, FontRenderer font, int mouseX, int mouseY) {	
		if(GSUtils.changelog.size() > 12) super.drawPage(x, y, font, mouseX, mouseY);
		
		String ver = GSThreadVersionCheck.remoteMajVer + "." + GSThreadVersionCheck.remoteMinVer + "." + GSThreadVersionCheck.remoteBuildVer;
		
		this.drawText(I18n.format("gs.success.name") + " " + EnumColor.YELLOW + ver, x, y - 40, 0, font);
		if(GSThreadVersionCheck.newversion)
		{			
			font.drawString(I18n.format("gs.update.available"), x + 180, y + 40, ColorUtil.to32BitColor(255, mc.player.ticksExisted % 5 == 0 ? 150 : 50, 200, 255));
		}
		
		this.drawText(EnumColor.BRIGHT_GREEN + "Changelog", x + 160, y - 40, 0, font);
		
		for(int i = getScroll(), k = 0; i < GSUtils.changelog.size(); i++, k++)
		{
			if(k < 12)
				this.drawText(GSUtils.changelog.get(i), x, y - 10 + ( (i - getScroll()) * font.FONT_HEIGHT), 0, font);
		}
		
		this.mc.getTextureManager().bindTexture(bookPageTexture);
		
		int uV = 0, posX = 280, posY = 45;
		if(mouseX >= x + posX && mouseX < x + posX + 120 && mouseY >= y + posY && mouseY <= y + posY + 16)
			uV = 17;
		drawTexturedModalRect(x + posX, y + posY, 120, 16, 400, uV, 87, 15, false, false, 512, 256);
		
		this.drawText(EnumColor.YELLOW + "Download", x + 280, y - 40, 0, font);
		/*if(GSUtils.changelog.isEmpty())
			start();*/
		
	}

	@Override
	public void mouseClick(int mouseX, int mouseY, int mouseButton, int x, int y) {	
		if(GSUtils.changelog.size() > 12)
			super.mouseClick(mouseX, mouseY, mouseButton, x, y);
		
		if(checkClick(mouseX, mouseY, x + 280, y + 45, 120, 16))
			try {
				Desktop.getDesktop().browse(new URI("https://minecraft.curseforge.com/projects/galaxy-space-addon-for-galacticraft/files"));
			} catch (IOException | URISyntaxException e) {
				
				e.printStackTrace();
			}
	}
	
	private boolean checkClick(int mouseX, int mouseY, int x, int y, int sizeX, int sizeY)
	{		
		return mouseX >= x && mouseX <= x + sizeX && mouseY >= y && mouseY <= y + sizeY;
	}

	@Override
	public String getCategory() {
		return Book_Cateroies.GENERAL.getName();
	}
	/*
	private void start() {
		final ScaledResolution scaledresolution = ClientUtil.getScaledRes(mc, mc.displayWidth, mc.displayHeight);

		final int width = scaledresolution.getScaledWidth();
		final int height = scaledresolution.getScaledHeight();

		try {
			URL url = new URL(
					"https://raw.githubusercontent.com/BlesseNtumble/GalaxySpace/1.12.2/1.12.2/changelog.txt");
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.addRequestProperty("User-Agent", "Mozilla/4.76");
			BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
			String str;

			int i = 0;
			while ((str = in.readLine()) != null) {
				if (this.mc.getLanguageManager().getCurrentLanguage().getLanguageCode().startsWith("ru_")) {
					i++;
					String str2 = new String(str.getBytes(), "UTF-8");
					if (str2.startsWith("r.")) {
						str2 = str2.replace("r.", "");
						GSUtils.changelog.add(str2);
						if(str2.length() > 70) GSUtils.changelog.add("");
						//font.drawString(str2, x, y + 11 * i + 10,
								//ColorUtil.to32BitColor(255, 150, 200, 255), true);
					}
				} else {//if (this.mc.getLanguageManager().getCurrentLanguage().getLanguageCode().startsWith("en_")) {
					i++;
					String str2 = new String(str.getBytes(), "UTF-8");
					if (str2.startsWith("e.")) {
						str2 = str.replace("e.", "");
						GSUtils.changelog.add(str2);
						if(str2.length() > 70) GSUtils.changelog.add("");
						//font.drawString(str2, x, y + 12 * i - 185,
								//ColorUtil.to32BitColor(255, 150, 200, 255), true);
					}
				} 
			}
			count = i;
			in.close();
		} catch (Exception e) {
		}
	}*/

	@Override
	public int getMaxScroll() {
		if(GSUtils.changelog.size() > 12) 
			return GSUtils.changelog.size() - 12;
		return 0;
	}
}
