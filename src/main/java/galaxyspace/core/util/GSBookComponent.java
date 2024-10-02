package galaxyspace.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import galaxyspace.GalaxySpace;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class GSBookComponent {
	
	public static ArrayList<String> names = new ArrayList();
	public static ArrayList components = new ArrayList();
	
	
	public static void load()
	{
		try {
			JsonParser parser = new JsonParser();
			InputStream path = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "texts/ru_RU.json")).getInputStream();
			
			Object obj = parser.parse(new InputStreamReader(path)); //������ JSON ����
			JsonObject itemsobject = (JsonObject) obj; //������� ��������� JsonObject � ������������� ��� ������� �� JSON �������
			
			
			JsonElement element = itemsobject.get("categories");
			
			
			
			for(int i = 0; i < element.getAsJsonArray().size(); i++)
			{
				JsonElement element1 = element.getAsJsonArray().get(i) ;
				String name = element1.getAsJsonObject().get("name").getAsString();
				names.add(name);
				
				JsonElement element2 = element1.getAsJsonObject().get("components");
				/*
				String[] name1 = new String[4];
				for(int j = 0; j < element2.getAsJsonArray().size(); j++) {
					name1[0] = element2.getAsJsonArray().get(j).getAsJsonObject().get("img").getAsString();
					name1[1] = element2.getAsJsonArray().get(j).getAsJsonObject().get("background_img").getAsString();
					name1[2] = element2.getAsJsonArray().get(j).getAsJsonObject().get("name").getAsString();
					name1[3] = element2.getAsJsonArray().get(j).getAsJsonObject().get("text").getAsString();
					components.add(name1);
				}			
				*/
				
				for(int j = 0; j < element2.getAsJsonArray().size(); j++) {
					components.add(element2.getAsJsonArray().get(j).getAsJsonObject());
				}

			}
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
}
