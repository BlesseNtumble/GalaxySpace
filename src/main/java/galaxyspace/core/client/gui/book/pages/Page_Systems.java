package galaxyspace.core.client.gui.book.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import asmodeuscore.api.dimension.IAdvancedSpace;
import asmodeuscore.api.space.IExBody;
import asmodeuscore.core.astronomy.BodiesData;
import asmodeuscore.core.astronomy.BodiesHelper;
import asmodeuscore.core.astronomy.gui.book.Page_WithScroll;
import asmodeuscore.core.utils.BookUtils.Book_Cateroies;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldProviderSpace;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldProvider;

public class Page_Systems extends Page_WithScroll {

	private static ResourceLocation starTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/book/star_bg.png");
	
	enum Mode {
		LIST,
		BODY,
		MOON
	}	
	
	private Mode current_mode = Mode.LIST;
	private static List<CelestialBody> planets = new LinkedList<CelestialBody>();	
	private Minecraft mc = Minecraft.getMinecraft();
	private int maxX = 0;
	private CelestialBody selected_body;
	private Moon selected_moon;
	
	private Map<CelestialBody, ItemStack[]> resources = new HashMap<CelestialBody, ItemStack[]>();
	private SolarSystem system;
	
	public Page_Systems(SolarSystem system)
	{		
		this.system = system;		
	}
	
	@Override
	public String titlePage() {
		return system.getUnlocalizedName();
	}
	
	@Override
	public ResourceLocation iconTitle() {
		return this.system.getMainStar().getBodyIcon();
	}

	@Override
	public boolean rawTitle()
	{
		return true;
	}
	
	@Override
	public void drawPage(int x, int y, FontRenderer font, int mouseX, int mouseY) {
				
		super.drawPage(x, y, font, mouseX, mouseY);
		
		if(current_mode == Mode.LIST) {
			planets.clear();
			
			for(Planet planet : GalaxyRegistry.getPlanetsForSolarSystem(this.system)) {
				//if(planet.getParentSolarSystem().equals(GalacticraftCore.solarSystemSol))
					planets.add(planet);
			}
			
			int posY = 0;
			
			for(CelestialBody s : planets)
			{
				int currX = font.getStringWidth(s.getLocalizedName());
				maxX = maxX < currX ? currX : maxX;
			}
			
			int offsetX = 21;
			//scroll = 0;
			for(int i = getScroll(); i < planets.size(); i++)
			{
				if(i < 8 + getScroll()) {
					
					int yOffset = i - getScroll();
					this.mc.getTextureManager().bindTexture(bookPageTexture);
					posY = 0;
					if(mouseX >= x + 44 && mouseX < x + 40 + 25 + maxX && mouseY >= y + 41 + (offsetX * yOffset) && mouseY <= y + 41 + (offsetX * yOffset) + 16)
						posY = 17;
					
					this.mc.getTextureManager().bindTexture(bookPageTexture);
    				drawTexturedModalRect(x + 20, y + 41 + (offsetX * yOffset), 21, 21, 461, 35, 18, 21, false, false, 512, 256);
    	    		
    				this.mc.getTextureManager().bindTexture(planets.get(i).getBodyIcon());
    				drawTexturedModalRect(x + 25, y + 45 + (offsetX * yOffset), 12, 12, 0, 0, 16, 16, false, false, 16, 16);
    	    		
    				this.mc.getTextureManager().bindTexture(bookPageTexture);
					drawTexturedModalRect(x + 40, y + 41 + (offsetX * yOffset), 25, 16, 400, posY, 20, 15, false, false, 512, 256);
					drawTexturedModalRect(x + 40 + 16, y + 41 + (offsetX * yOffset), maxX, 16, 410, posY, 70, 15, false, false, 512, 256);
					drawTexturedModalRect(x + 40 + maxX, y + 41 + (offsetX * yOffset), 25, 16, 467, posY, 20, 15, false, false, 512, 256);
					
					font.drawString(planets.get(i).getLocalizedName(), x + 52, y + 46 + (offsetX * yOffset), 0xFFFFFF);
				}
			}
						
			this.mc.getTextureManager().bindTexture(starTexture);
			this.drawTexturedModalRect(x + 80 + maxX, y + 45, 60, 60, 0, 0, 256, 256, false, false, 256, 256);
			
			this.mc.getTextureManager().bindTexture(this.system.getMainStar().getBodyIcon());
			drawTexturedModalRect(x + 80 + maxX + 30 - 6, y + 45 + 30 - 6, 12, 12, 0, 0, 16, 16, false, false, 16, 16);
    		
			drawText(GCCoreUtil.translate("book.page." + this.system.getMainStar().getUnlocalizedName() + ".desc"), x + 110 + maxX, y - 40, 0, 200, font);
			
		}
		else if(current_mode == Mode.BODY)
		{
			int offsetX = 0, posY = 17, i = 1;
			
			this.mc.getTextureManager().bindTexture(bookPageTexture);
			drawTexturedModalRect(x + 20, y + 41 + offsetX, 21, 21, 461, 35, 18, 21, false, false, 512, 256);
    		
			this.mc.getTextureManager().bindTexture(selected_body.getBodyIcon());
			drawTexturedModalRect(x + 25, y + 45 + offsetX, 12, 12, 0, 0, 16, 16, false, false, 16, 16);
    		
			this.mc.getTextureManager().bindTexture(bookPageTexture);
			drawTexturedModalRect(x + 40, y + 41 + offsetX, 25, 16, 400, posY, 20, 15, false, false, 512, 256);
			drawTexturedModalRect(x + 40 + 16, y + 41 + offsetX, maxX, 16, 410, posY, 70, 15, false, false, 512, 256);
			drawTexturedModalRect(x + 40 + maxX, y + 41 + offsetX, 25, 16, 467, posY, 20, 15, false, false, 512, 256);
			
			font.drawString(selected_body.getLocalizedName(), x + 52, y + 46 + offsetX, 0xFFFFFF);
			
		
			if(selected_body instanceof Planet)
			{
				offsetX = 21;
				for(Moon moon : GalaxyRegistry.getMoonsForPlanet((Planet) selected_body))
				{
					posY = 0;
					if(mouseX >= x + 44 && mouseX < x + 40 + 25 + maxX && mouseY >= y + 41 + (offsetX * i) && mouseY <= y + 41 + (offsetX * i) + 16)
						posY = 17;
					
					this.mc.getTextureManager().bindTexture(bookPageTexture);
					drawTexturedModalRect(x + 20, y + 41 + (offsetX * i), 21, 21, 461, 35, 18, 21, false, false, 512, 256);
		    		
					this.mc.getTextureManager().bindTexture(moon.getBodyIcon());
					drawTexturedModalRect(x + 25, y + 45 + (offsetX * i), 12, 12, 0, 0, 16, 16, false, false, 16, 16);
		    		
					this.mc.getTextureManager().bindTexture(bookPageTexture);
					drawTexturedModalRect(x + 40, y + 41 + offsetX * i, 25, 16, 400, posY, 20, 15, false, false, 512, 256);
					drawTexturedModalRect(x + 40 + 16, y + 41 + offsetX * i, maxX, 16, 410, posY, 70, 15, false, false, 512, 256);
					drawTexturedModalRect(x + 40 + maxX, y + 41 + offsetX * i, 25, 16, 467, posY, 20, 15, false, false, 512, 256);
					
					font.drawSplitString(moon.getLocalizedName(), x + 52, y + 46 + offsetX * i, 260, 0xFFFFFF);
					
					i++;
				}
			}
			
			if(selected_body != null) {
				font.drawSplitString(GCCoreUtil.translate("book.page." + system.getUnlocalizedName() + "." + selected_body.getName().toLowerCase()), x + 80 + maxX, y + 46, 260, 0xFFFFFF);
				
				int offsetY = (font.getStringWidth(GCCoreUtil.translate("book.page." + system.getUnlocalizedName() + "." + selected_body.getName().toLowerCase())) / 260) * font.FONT_HEIGHT;
				
				offsetY -= getScroll() * 10;
				
				WorldProvider dim = WorldUtil.getProviderForDimensionClient(this.selected_body.getDimensionID());
				
				int j = 0;
				if(dim instanceof IGalacticraftWorldProvider)
                {
					IGalacticraftWorldProvider gcdim = (IGalacticraftWorldProvider) dim;
					
					List<String> infos = new ArrayList<String>(); 
					
					float grav = (0.1F - ((IGalacticraftWorldProvider)dim).getGravity()) * 1000 ;
					if(((IGalacticraftWorldProvider)dim).getGravity() == 0.0F) grav = 0.0F;
										
					infos.add(GCCoreUtil.translate("gui.message.daylength") + " " + ((WorldProviderSpace) gcdim).getDayLength() / 1000 + "h " + ((WorldProviderSpace) gcdim).getDayLength() % 1000 + "m");
					infos.add("Distance from star: " + selected_body.getRelativeDistanceFromCenter().unScaledDistance);
					infos.add(GCCoreUtil.translate("gui.message.gravity") + " " + (int)grav + "%");
					infos.add(GCCoreUtil.translate("gui.message.temperature") + " " + gcdim.getCelestialBody().atmosphere.thermalLevel() * 20 + " C");
					infos.add(GCCoreUtil.translate("gui.message.windspeed") + " " + gcdim.getCelestialBody().atmosphere.windLevel());					
					if(dim instanceof ISolarLevel) 
						infos.add(GCCoreUtil.translate("gui.message.solarenergy") + " " + Math.round((((ISolarLevel)dim).getSolarEnergyMultiplier() - 1) * 1000) / 10.0F + "%");
					
					if(gcdim instanceof IAdvancedSpace)					
						infos.add(GCCoreUtil.translate("gui.message.windenergy") + " " + Math.round(((IAdvancedSpace)dim).getSolarWindMultiplier() * 1000) / 1000F);
					
					BodiesData data = BodiesHelper.getData().get(selected_body);
					if(data != null)
					{									
						infos.add(GCCoreUtil.translate("gui.message.atmopressure") + " " + data.getPressurePlanet());
						infos.add(GCCoreUtil.translate("gui.message.solarradiation") + " " + data.getSolarRadiationPlanet());
					}
					
					if(selected_body instanceof IExBody)
					{
						infos.add(GCCoreUtil.translate("gui.message.atmopressure") + " " + ((IExBody)selected_body).getAtmosphericPressure());
						infos.add(GCCoreUtil.translate("gui.message.solarradiation") + " " + ((IExBody)selected_body).isSolarRadiation());
					
					}
					
					infos.add(GCCoreUtil.translate("gui.message.breathableatmo") + " " + gcdim.hasBreathableAtmosphere());
					infos.add("");
					infos.add(GCCoreUtil.translate("gui.message.resources"));
					for(int k = getScroll(); k < infos.size(); k++, j++) {
						if(!infos.get(k).isEmpty() && k < 8 + getScroll()) {
							this.mc.getTextureManager().bindTexture(bookPageTexture);
							drawTexturedModalRect(x + 78 + maxX, y + 80 + (k * 12) + offsetY, 136, 13, 401, 119, 85, 15, false, false, 512, 256);
							
							font.drawString(infos.get(k), x + 84 + maxX, y + 82 + (k * 12) + offsetY, 0xFFFFFF);
						}
					}
					
					if(this.resources.containsKey(selected_body) && getScroll() > 3)
					{
						int k = 0;
						for(ItemStack stacks : this.resources.get(selected_body)) {
							this.mc.renderEngine.bindTexture(this.bookPageTexture);	
							GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
							this.drawTexturedModalRect(x + 78 + maxX + (k * 22), y + 108 + infos.size() * 10 + offsetY, 20, 20, 452, 94, 20, 20, false, false, 576, 250);
							this.drawItemStack(mc, stacks, x + 80 + maxX + (k++ * 22), y + 110 + infos.size() * 10 + offsetY, mouseX, mouseY, true);
						}
					}
                }
				
				
				//if(!selected_body.getReachable()) return;								
			}
		}
		else if(current_mode == Mode.MOON)
		{
			this.mc.getTextureManager().bindTexture(bookPageTexture);
			drawTexturedModalRect(x + 20, y + 41, 21, 21, 461, 35, 18, 21, false, false, 512, 256);
    		
			this.mc.getTextureManager().bindTexture(selected_moon.getBodyIcon());
			drawTexturedModalRect(x + 25, y + 45, 12, 12, 0, 0, 16, 16, false, false, 16, 16);
    		
			this.mc.getTextureManager().bindTexture(bookPageTexture);
			drawTexturedModalRect(x + 40, y + 41, 25, 16, 400, 17, 20, 15, false, false, 512, 256);
			drawTexturedModalRect(x + 40 + 16, y + 41 , maxX, 16, 410, 17, 70, 15, false, false, 512, 256);
			drawTexturedModalRect(x + 40 + maxX, y + 41, 25, 16, 467, 17, 20, 15, false, false, 512, 256);
			
			font.drawString(selected_moon.getLocalizedName(), x + 52, y + 46, 0xFFFFFF);
		
			int j = 0;
			if(selected_moon != null) {
				font.drawString(GCCoreUtil.translate("book.page." + system.getUnlocalizedName() + "." + selected_moon.getName().toLowerCase()), x + 80 + maxX, y + 46, 0xFFFFFF);
			
				int offsetY = (font.getStringWidth(GCCoreUtil.translate("book.page." + system.getUnlocalizedName() + "." + selected_moon.getName().toLowerCase())) / 260) * font.FONT_HEIGHT;
				offsetY -= getScroll() * 10;
				
				WorldProvider dim = WorldUtil.getProviderForDimensionClient(this.selected_moon.getDimensionID());
				
				if(dim instanceof IGalacticraftWorldProvider)
                {
					IGalacticraftWorldProvider gcdim = (IGalacticraftWorldProvider) dim;
					
					List<String> infos = new ArrayList<String>(); 
					
					float grav = (0.1F - ((IGalacticraftWorldProvider)dim).getGravity()) * 1000 ;
					if(((IGalacticraftWorldProvider)dim).getGravity() == 0.0F) grav = 0.0F;
										
					infos.add(GCCoreUtil.translate("gui.message.daylength") + " " + ((WorldProviderSpace) gcdim).getDayLength() / 1000 + "h " + ((WorldProviderSpace) gcdim).getDayLength() % 1000 + "m");
					infos.add("Distance from planet: " + selected_moon.getRelativeDistanceFromCenter().unScaledDistance);
					infos.add(GCCoreUtil.translate("gui.message.gravity") + " " + (int)grav + "%");
					infos.add(GCCoreUtil.translate("gui.message.temperature") + " " + gcdim.getCelestialBody().atmosphere.thermalLevel() * 20 + " C");
					infos.add(GCCoreUtil.translate("gui.message.windspeed") + " " + gcdim.getCelestialBody().atmosphere.windLevel());					
					if(dim instanceof ISolarLevel) 
						infos.add(GCCoreUtil.translate("gui.message.solarenergy") + " " + Math.round((((ISolarLevel)dim).getSolarEnergyMultiplier() - 1) * 1000) / 10.0F + "%");
					
					if(gcdim instanceof IAdvancedSpace)					
						infos.add(GCCoreUtil.translate("gui.message.windenergy") + " " + Math.round(((IAdvancedSpace)dim).getSolarWindMultiplier() * 1000) / 1000F);
					
					BodiesData data = BodiesHelper.getData().get(selected_moon);
					if(data != null)
					{									
						infos.add(GCCoreUtil.translate("gui.message.atmopressure") + " " + data.getPressurePlanet());
						infos.add(GCCoreUtil.translate("gui.message.solarradiation") + " " + data.getSolarRadiationPlanet());
					}
					
					/*
					if(selected_body instanceof ExPlanet)
					{
						infos.add(GCCoreUtil.translate("gui.message.atmopressure") + " " + ((ExPlanet)selected_moon).getAtmosphericPressure());
						infos.add(GCCoreUtil.translate("gui.message.solarradiation") + " " + ((ExPlanet)selected_moon).isSolarRadiation());
					
					}
					*/
					
					infos.add(GCCoreUtil.translate("gui.message.breathableatmo") + " " + gcdim.hasBreathableAtmosphere());
					infos.add("");
					infos.add(GCCoreUtil.translate("gui.message.resources"));
					for(int k = getScroll(); k < infos.size(); k++, j++) {
						if(!infos.get(k).isEmpty() && k < 8 + getScroll()) {
							this.mc.getTextureManager().bindTexture(bookPageTexture);
							drawTexturedModalRect(x + 78 + maxX, y + 80 + (k * 12) + offsetY, 146, 13, 401, 119, 85, 15, false, false, 512, 256);
							
							font.drawString(infos.get(k), x + 84 + maxX, y + 82 + (k * 12) + offsetY, 0xFFFFFF);
						}
					}
					
					if(this.resources.containsKey(selected_moon) && getScroll() > 3)
					{
						int k = 0;
						for(ItemStack stacks : this.resources.get(selected_moon)) {
							this.mc.renderEngine.bindTexture(this.bookPageTexture);	
							GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
							if(k < 12) {
								this.drawTexturedModalRect(x + 78 + maxX + (k * 22), y + 108 + infos.size() * 10 + offsetY, 20, 20, 452, 94, 20, 20, false, false, 576, 250);
								this.drawItemStack(mc, stacks, x + 80 + maxX + (k * 22), y + 110 + infos.size() * 10 + offsetY, mouseX, mouseY, true);
							}
							else
							{
								this.drawTexturedModalRect(x + 78 + maxX + ((k - 12) * 22), y + 108 + infos.size() * 10 + offsetY + 22, 20, 20, 452, 94, 20, 20, false, false, 576, 250);
								this.drawItemStack(mc, stacks, x + 80 + maxX + ((k - 12) * 22), y + 110 + infos.size() * 10 + offsetY + 22, mouseX, mouseY, true);
							}
							k++;
						}
					}
                }
			}
		}
		
	}
	
	@Override
	public void mouseClick(int mouseX, int mouseY, int mouseButton, int x, int y) {
		super.mouseClick(mouseX, mouseY, mouseButton, x, y);
		
		int offsetX = 21;
		if(current_mode == Mode.LIST)
			for(int i = getScroll(); i < planets.size(); i++)
			{
				if(i < 8 + getScroll()) {
					
					int yOffset = i - getScroll();
					if(mouseX >= x + 44 && mouseX < x + 40 + 25 + maxX && mouseY >= y + 41 + (offsetX * yOffset) && mouseY <= y + 41 + (offsetX * yOffset) + 16)
					{
						this.selected_body = planets.get(i);
						current_mode = Mode.BODY;
					}
				}
			}
		else if(current_mode == Mode.BODY)
		{
			for(int i = 1; i < GalaxyRegistry.getMoonsForPlanet((Planet) selected_body).size() + 1; i++)
			{
				if(mouseX >= x + 44 && mouseX < x + 40 + 25 + maxX && mouseY >= y + 41 + (offsetX * i) && mouseY <= y + 41 + (offsetX * i) + 16)
				{
					this.selected_moon = GalaxyRegistry.getMoonsForPlanet((Planet) selected_body).get(i - 1);
					current_mode = Mode.MOON;
				}
			}
		}
	}

	@Override
	public boolean hookBackButton()
	{
		if(current_mode == Mode.BODY)
		{
			current_mode = Mode.LIST;
			this.setScroll(0);
			return true;
		}
		else if(current_mode == Mode.MOON)
		{
			current_mode = Mode.BODY;
			return true;
		}
		return false;
	}
	
	@Override
	public String getCategory() {
		return Book_Cateroies.BODIES.getName();
	}
	
	@Override
	public int getMaxScroll() {
		
		if(current_mode == Mode.LIST && planets.size() > 8)
			return planets.size() % 8;
		
		if(current_mode == Mode.BODY || current_mode == Mode.MOON)
			return 5;
		
		return 0;
	}	
	
	protected void setResources(CelestialBody body, ItemStack... stack)
	{
		/*List<ItemStack> stacks = new ArrayList<ItemStack>();
		stacks.add(new ItemStack(GSBlocks.MERCURY_BLOCKS, 1, 3));
		stacks.add(new ItemStack(GSBlocks.MERCURY_BLOCKS, 1, 4));
		stacks.add(new ItemStack(GSBlocks.MERCURY_BLOCKS, 1, 5));	*/	
		this.resources.put(body, stack);
	}	
}
