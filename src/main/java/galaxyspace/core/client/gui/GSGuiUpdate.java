package galaxyspace.core.client.gui;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSThreadVersionCheck;
import micdoodle8.mods.galacticraft.core.util.ClientUtil;
import micdoodle8.mods.galacticraft.core.util.ColorUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GSGuiUpdate extends GuiScreen{

	public static ResourceLocation guiMain = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/guiupdate.png");
	
	@Override
	public void initGui() 
	{
		super.initGui();
	}
	
	@Override
	public void drawScreen(int parWidth, int parHeight, float p_73863_3_)
	{
		this.drawDefaultBackground();
		
		final ScaledResolution scaledresolution = ClientUtil.getScaledRes(mc, mc.displayWidth, mc.displayHeight);
	    final int width = scaledresolution.getScaledWidth();
	    final int height = scaledresolution.getScaledHeight();

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		//GL11.glColor4f(0.0F, 0.6F, 1.0F, 1);
		this.mc.getTextureManager().bindTexture(this.guiMain);

		this.drawTexturedModalRect(100, 15, 800, 470, 0, 0, 400, 350, false, false);
	    
		String ver = GSThreadVersionCheck.remoteMajVer + "." + GSThreadVersionCheck.remoteMinVer + "." + GSThreadVersionCheck.remoteBuildVer;
		this.fontRendererObj.drawString(StatCollector.translateToLocal("gs.success.name") + " " + ver, 118, 67, ColorUtil.to32BitColor(255, 150, 200, 255));
		this.fontRendererObj.drawString(StatCollector.translateToLocal("gs.update.available"), 290, 37, ColorUtil.to32BitColor(255, mc.thePlayer.ticksExisted % 2 == 0 ? 150 : 50, 200, 255));
		this.fontRendererObj.drawString("Download New Version", 420, 67, ColorUtil.to32BitColor(255, 0 ,mc.thePlayer.ticksExisted % 2 == 0 ? 255 : 150, 0));
		this.fontRendererObj.drawString("CHANGELOG", 302, 101, ColorUtil.to32BitColor(255, 150 , 200, 255));
		
		this.start(145, 78);
		
	    GL11.glDisable(GL11.GL_BLEND);
	    GL11.glPopMatrix();
	}

	@Override
	protected void mouseMovedOrUp(int x, int y, int which)
	{

	}
	@Override
    protected void mouseClicked(int x, int y, int button)
    {
		if(x > 414 && x < 543 && y > 64 && y < 78)
		{
			
			try {
				Desktop.getDesktop().browse(new URI("https://forum.micdoodle8.com/index.php?threads/1-7-10-galaxy-space-stable.5298/"));
			
			} catch (IOException | URISyntaxException e) {
				
				e.printStackTrace();
			}
		}
    }
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
	
    public void drawTexturedModalRect(int x, int y, int width, int height, int u, int v, int uWidth, int vHeight, boolean invertX, boolean invertY)
    {
        this.drawTexturedModalRect(x, y, width, height, u, v, uWidth, vHeight, invertX, invertY, 512, 512);
    }

    public void drawTexturedModalRect(float x, float y, float width, float height, float u, float v, float uWidth, float vHeight, boolean invertX, boolean invertY, float texSizeX, float texSizeY)
    {
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        float texModX = 1F / texSizeX;
        float texModY = 1F / texSizeY;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        float height0 = invertY ? 0 : vHeight;
        float height1 = invertY ? vHeight : 0;
        float width0 = invertX ? uWidth : 0;
        float width1 = invertX ? 0 : uWidth;
        tessellator.addVertexWithUV(x, y + height, this.zLevel, (u + width0) * texModX, (v + height0) * texModY);
        tessellator.addVertexWithUV(x + width, y + height, this.zLevel, (u + width1) * texModX, (v + height0) * texModY);
        tessellator.addVertexWithUV(x + width, y, this.zLevel, (u + width1) * texModX, (v + height1) * texModY);
        tessellator.addVertexWithUV(x, y, this.zLevel, (u + width0) * texModX, (v + height1) * texModY);
        tessellator.draw();
    }
    
    private void start(int x, int y)
    {
    	final ScaledResolution scaledresolution = ClientUtil.getScaledRes(mc, mc.displayWidth, mc.displayHeight);
	    
    	final int width = scaledresolution.getScaledWidth();
	    final int height = scaledresolution.getScaledHeight();
	    
		try
		{
			URL url = new URL("https://raw.githubusercontent.com/BlesseNtumble/GalaxySpace/master/changelog.txt");
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.addRequestProperty("User-Agent", "Mozilla/4.76");
			BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
			String str;

			int i = 0;
			while ((str = in.readLine()) != null)
			{		
				if(mc.getLanguageManager().getCurrentLanguage().getLanguageCode().startsWith("en_"))
				{
					i++;				
					String str2=new String(str.getBytes(),"UTF-8");	
					if(str2.startsWith("e."))
					{
						str2 = str.replace("e.", "");
						this.fontRendererObj.drawString(str2, x, y + 12 * i - 100, ColorUtil.to32BitColor(255, 150, 200, 255), true);		
					}
				}				
				else if(mc.getLanguageManager().getCurrentLanguage().getLanguageCode().startsWith("ru_"))
				{
					i++;				
					String str2=new String(str.getBytes(),"UTF-8");	
					if(str2.startsWith("r."))
					{
						str2 = str2.replace("r.", "");
						this.fontRendererObj.drawString(str2, x, y + 11 * i + 10, ColorUtil.to32BitColor(255, 150, 200, 255), true);		
					}
				}				
			}
			in.close();
		}
		catch (Exception e) {}
    }
	
}
