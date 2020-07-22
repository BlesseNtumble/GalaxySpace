package galaxyspace.core.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import galaxyspace.GalaxySpace;
import galaxyspace.core.configs.GSConfigCore;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class GSThreadVersionCheck extends Thread
{
	public static GSThreadVersionCheck INSTANCE = new GSThreadVersionCheck();
	private int count = 0;
	
	public static Map<String, String> GSIP = new HashMap<String, String>();
	
	private String URLMF = "http://minecraftforum.net/forums/thread/2412548";
	private String URLMC = "https://forum.micdoodle8.com/index.php?threads/1-7-10-galaxy-space-stable.5298/";
	private String URLVK = "https://vk.com/addon.galaxyspace?w=page-83247747_51934589";

	public static int remoteMajVer;
	public static int remoteMinVer;
	public static int remoteBuildVer;
	public static String information;
	public static boolean newversion = false;
	
	public GSThreadVersionCheck()
	{
		super("Galaxy Space Version Check Thread");
	}

	public static void startCheck()
	{
		final Thread thread = new Thread(GSThreadVersionCheck.INSTANCE);
		thread.start();				
	}

	@Override
	public void run()
	{		
		final Side sideToCheck = FMLCommonHandler.instance().getSide();
		if (sideToCheck == null)
		{
			return;
		}

		while (this.count < 1)
		{
			try
			{
				URL url = new URL("https://raw.githubusercontent.com/BlesseNtumble/GalaxySpace/1.12.2/1.12.2/version.txt");
				HttpURLConnection http = (HttpURLConnection) url.openConnection();
				http.addRequestProperty("User-Agent", "Mozilla/4.76");
				BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
				String str;
				String str2[] = null;

				while ((str = in.readLine()) != null)
				{
					if (str.contains("Version") && GSConfigCore.enableCheckVersion)
					{
						str = str.replace("Version=", "");
						str2 = str.split("#");

						if (str2.length == 3)
						{
							remoteMajVer = Integer.parseInt(str2[0]);
							remoteMinVer = Integer.parseInt(str2[1]);
							remoteBuildVer = Integer.parseInt(str2[2]);
						}


						if (remoteMajVer >= GalaxySpace.major_version && remoteMinVer >= GalaxySpace.minor_version && remoteBuildVer > GalaxySpace.build_version)
						{
							this.newversion = true;
							Thread.sleep(5000);
							
							if (sideToCheck.equals(Side.CLIENT)) {
								GSUtils.changelog.clear();
								GSUtils.start();	
							
								FMLClientHandler.instance().getClient().player.sendMessage(new TextComponentString(EnumColor.GREY + "New " + EnumColor.DARK_AQUA + GalaxySpace.NAME + EnumColor.GREY + " version available! v" + String.valueOf(remoteMajVer) + "." + String.valueOf(remoteMinVer) + "." +String.valueOf(remoteBuildVer) + EnumColor.DARK_BLUE + " http://micdoodle8.com/"));
							}
							else if (sideToCheck.equals(Side.SERVER))
							{
								GalaxySpace.info("New version available! v" + String.valueOf(remoteMajVer) + "." + String.valueOf(remoteMinVer) + "." + String.valueOf(remoteBuildVer) + " ");
							}
						}
						
					}
				}
			}
			
			catch (Exception e) {}

			/*if (remoteBuildVer == 0)
			{
				try
				{
					GalaxySpace.severe(StatCollector.translateToLocal("gs.failed.name"));
					Thread.sleep(15000);
				}
				catch (InterruptedException e) {}
			}
			else
			{*/
				GalaxySpace.info(GCCoreUtil.translate("gs.success.name") + " " + remoteMajVer + "." + remoteMinVer + "." + remoteBuildVer);
			//}
			this.count++;
		}
	}
	

}
