package galaxyspace.core.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import galaxyspace.GalaxySpace;
import galaxyspace.core.configs.GSConfigCore;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent.Serializer;
import net.minecraft.util.StatCollector;

public class GSThreadVersionCheck extends Thread
{
	public static GSThreadVersionCheck INSTANCE = new GSThreadVersionCheck();
	private int count = 0;
	
	//public static Map<String, String> GSIP = new HashMap<String, String>();
	
	private String URLMF = "http://minecraftforum.net/forums/thread/2412548";
	private String URLMC = "https://forum.micdoodle8.com/index.php?threads/1-7-10-galaxy-space-stable.5298/";
	private String URLVK = "https://vk.com/addon.galaxyspace?w=page-83247747_51934589";

	public static int remoteMajVer;
	public static int remoteMinVer;
	public static int remoteBuildVer;
	public static String information;
	public static boolean newversion = false;
	//public static String version;
	
	public GSThreadVersionCheck()
	{
		super("Galaxy Space Version Check Thread");
	}

	public static void startCheck()
	{
		Thread thread = new Thread(GSThreadVersionCheck.INSTANCE);
		thread.start();				
	}

	@Override
	public void run()
	{
		
		Side sideToCheck = FMLCommonHandler.instance().getSide();
		if (sideToCheck == null)
		{
			return;
		}
		
		if(!GSConfigCore.enableCheckVersion) return;

		while (this.count < 2)
		{
			try
			{
				URL url = new URL("https://raw.githubusercontent.com/BlesseNtumble/GalaxySpace/master/version.txt");
				HttpURLConnection http = (HttpURLConnection) url.openConnection();
				http.addRequestProperty("User-Agent", "Mozilla/4.76");
				BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
				String str;
				String str2[] = null;

				while ((str = in.readLine()) != null)
				{
					if (str.contains("Version"))
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

							if (sideToCheck.equals(Side.CLIENT))
							{
								FMLClientHandler.instance().getClient().thePlayer.addChatMessage(Serializer.func_150699_a("[{text:\"" + EnumChatFormatting.GRAY + "New \",extra:[{text:\"" + EnumChatFormatting.AQUA + "Galaxy Space\"},{text:\"" + EnumChatFormatting.GRAY + " version available!\"},{text:\"" + EnumChatFormatting.GREEN + EnumChatFormatting.BOLD + " v" + String.valueOf(remoteMajVer) + "." + String.valueOf(remoteMinVer) + "." + String.valueOf(remoteBuildVer) + " \"}]}]"));
								FMLClientHandler.instance().getClient().thePlayer.addChatMessage(Serializer.func_150699_a("[{text:\"" + EnumChatFormatting.RED + "Download: " + EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + "Minecraftforum\",hoverEvent:{action:show_text,value:\"" + EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + "Download Latest Version\"},clickEvent:{action:open_url,value:\"" + this.URLMF + "\"}}]"));
								FMLClientHandler.instance().getClient().thePlayer.addChatMessage(Serializer.func_150699_a("[{text:\"" + EnumChatFormatting.RED + "Download: " + EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + "Micdoodle8 Forum\",hoverEvent:{action:show_text,value:\"" + EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + "Download Latest Version\"},clickEvent:{action:open_url,value:\"" + this.URLMC + "\"}}]"));
								FMLClientHandler.instance().getClient().thePlayer.addChatMessage(Serializer.func_150699_a("[{text:\"" + EnumChatFormatting.RED + "Download: [RUS] " + EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + "[VK] Galaxy Space Group\",hoverEvent:{action:show_text,value:\"" + EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + "Download Latest Version\"},clickEvent:{action:open_url,value:\"" + this.URLVK + "\"}}]"));
										
								//this.version = String.valueOf(remoteMajVer) + "." + String.valueOf(remoteMinVer) + "." + String.valueOf(remoteBuildVer);
								
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
				GalaxySpace.info(StatCollector.translateToLocal("gs.success.name") + " " + remoteMajVer + "." + remoteMinVer + "." + remoteBuildVer);
			//}
			this.count++;
		}
	}
	

}
