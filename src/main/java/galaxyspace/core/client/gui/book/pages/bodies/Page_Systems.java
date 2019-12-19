package galaxyspace.core.client.gui.book.pages.bodies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import asmodeuscore.api.dimension.IAdvancedSpace;
import asmodeuscore.core.astronomy.BodiesData;
import asmodeuscore.core.astronomy.BodiesHelper;
import asmodeuscore.core.astronomy.gui.book.Page_WithScroll;
import asmodeuscore.core.utils.BookUtils.Book_Cateroies;
import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldProviderSpace;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.util.ColorUtil;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import micdoodle8.mods.galacticraft.planets.asteroids.AsteroidsModule;
import micdoodle8.mods.galacticraft.planets.asteroids.blocks.AsteroidBlocks;
import micdoodle8.mods.galacticraft.planets.mars.MarsModule;
import micdoodle8.mods.galacticraft.planets.mars.blocks.MarsBlocks;
import micdoodle8.mods.galacticraft.planets.venus.VenusBlocks;
import micdoodle8.mods.galacticraft.planets.venus.VenusModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldProvider;

public class Page_Systems extends Page_WithScroll {

	private static ResourceLocation bookPageTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/tablet.png");
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
		
		setResources(SolarSystemBodies.planetMercury, new ItemStack(GSBlocks.MERCURY_BLOCKS, 1, 3), new ItemStack(GSBlocks.MERCURY_BLOCKS, 1, 4), new ItemStack(GSBlocks.MERCURY_BLOCKS, 1, 5));
		setResources(VenusModule.planetVenus, new ItemStack(VenusBlocks.venusBlock, 1, 6), new ItemStack(VenusBlocks.venusBlock, 1, 7), new ItemStack(VenusBlocks.venusBlock, 1, 8), new ItemStack(VenusBlocks.venusBlock, 1, 9), new ItemStack(VenusBlocks.venusBlock, 1, 10), new ItemStack(VenusBlocks.venusBlock, 1, 11), new ItemStack(VenusBlocks.venusBlock, 1, 13)) ;
		setResources(GalacticraftCore.planetOverworld, new ItemStack(Blocks.COAL_ORE, 1, 0), new ItemStack(Blocks.IRON_ORE, 1, 0), new ItemStack(Blocks.GOLD_ORE, 1, 0), new ItemStack(Blocks.REDSTONE_ORE, 1, 0), new ItemStack(Blocks.LAPIS_ORE, 1, 0), new ItemStack(Blocks.DIAMOND_ORE, 1, 0), new ItemStack(Blocks.EMERALD_ORE, 1, 0), new ItemStack(GCBlocks.basicBlock, 1, 5), new ItemStack(GCBlocks.basicBlock, 1, 6), new ItemStack(GCBlocks.basicBlock, 1, 7), new ItemStack(GCBlocks.basicBlock, 1, 8), new ItemStack(GSBlocks.OVERWORLD_ORES, 1, 0), new ItemStack(GSBlocks.OVERWORLD_ORES, 1, 1), new ItemStack(GSBlocks.OVERWORLD_ORES, 1, 2)) ;
		setResources(GalacticraftCore.moonMoon, new ItemStack(GCBlocks.blockMoon, 1, 0), new ItemStack(GCBlocks.blockMoon, 1, 1), new ItemStack(GCBlocks.blockMoon, 1, 2), new ItemStack(GCBlocks.blockMoon, 1, 6));
		setResources(MarsModule.planetMars, new ItemStack(MarsBlocks.marsBlock, 1, 0), new ItemStack(MarsBlocks.marsBlock, 1, 1), new ItemStack(MarsBlocks.marsBlock, 1, 2), new ItemStack(MarsBlocks.marsBlock, 1, 3));
		setResources(SolarSystemBodies.planetCeres, new ItemStack(GSBlocks.CERES_BLOCKS, 1, 2), new ItemStack(GSBlocks.CERES_BLOCKS, 1, 3));
		setResources(AsteroidsModule.planetAsteroids, new ItemStack(AsteroidBlocks.blockBasic, 1, 3), new ItemStack(AsteroidBlocks.blockBasic, 1, 4), new ItemStack(AsteroidBlocks.blockBasic, 1, 5));
		setResources(SolarSystemBodies.ioJupiter, new ItemStack(GSBlocks.IO_BLOCKS, 1, 3), new ItemStack(GSBlocks.IO_BLOCKS, 1, 4), new ItemStack(GSBlocks.IO_BLOCKS, 1, 5));
		setResources(SolarSystemBodies.europaJupiter, new ItemStack(GSBlocks.EUROPA_BLOCKS, 1, 2), new ItemStack(GSBlocks.EUROPA_BLOCKS, 1, 3), new ItemStack(GSBlocks.EUROPA_BLOCKS, 1, 4), new ItemStack(GSBlocks.EUROPA_BLOCKS, 1, 5));
		setResources(SolarSystemBodies.ganymedeJupiter, new ItemStack(GSBlocks.GANYMEDE_BLOCKS, 1, 2), new ItemStack(GSBlocks.GANYMEDE_BLOCKS, 1, 3));
		//setResources(SolarSystemBodies.callistoJupiter, new ItemStack(GSBlocks.GANYMEDE_BLOCKS, 1, 2), new ItemStack(GSBlocks.GANYMEDE_BLOCKS, 1, 3));
		setResources(SolarSystemBodies.enceladusSaturn, new ItemStack(GSBlocks.ENCELADUS_BLOCKS, 1, 2), new ItemStack(GSBlocks.ENCELADUS_CRYSTAL, 1, 0));
		setResources(SolarSystemBodies.titanSaturn, new ItemStack(GSItems.EM_CANISTER, 1, 1));
		setResources(SolarSystemBodies.mirandaUranus, new ItemStack(GSBlocks.MIRANDA_BLOCKS, 1, 3), new ItemStack(GSBlocks.MIRANDA_BLOCKS, 1, 4));
		setResources(SolarSystemBodies.planetKuiperBelt, new ItemStack(GSBlocks.OVERWORLD_ORES, 1, 0), new ItemStack(GSBlocks.OVERWORLD_ORES, 1, 1), new ItemStack(GSBlocks.OVERWORLD_ORES, 1, 2), new ItemStack(Blocks.COAL_ORE, 1, 0));
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
    		
			font.drawString(GCCoreUtil.translate(this.system.getMainStar().getUnlocalizedName()), x + 150 + maxX, y + 45, 0xFFFFFF);
			
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
					
					infos.add(GCCoreUtil.translate("gui.message.breathableatmo") + " " + gcdim.hasBreathableAtmosphere());
					
					for(int k = 0; k < infos.size(); k++, j++) {
						font.drawString(infos.get(k), x + 80 + maxX, y + 70 + (k * font.FONT_HEIGHT) + offsetY, 0xFFFFFF);						
					}
                }
				
				
				if(!selected_body.getReachable()) return;
				
				font.drawString("Resources:", x + 80 + maxX, y + 76 + (j * font.FONT_HEIGHT) + offsetY, 0xFFFFFF);
				
				if(this.resources.containsKey(selected_body))
				{
					int k = 0;
					for(ItemStack stacks : this.resources.get(selected_body)) {
						
						RenderHelper.enableGUIStandardItemLighting();
						if(k >= 14) {					
							this.mc.getRenderItem().renderItemAndEffectIntoGUI(stacks, x + 80 + maxX + ((k - 14) * 20), y + 86 + (j * font.FONT_HEIGHT) + offsetY);
							if (mouseX >= x + 80 + maxX + ((k - 14) * 20) && mouseX <= x + 80 + maxX + ((k - 14) * 20) + 16 && mouseY >= y + 100 + 18 + offsetY && mouseY <= y + 100 + 18 + 16 + offsetY)
							{
								this.drawToolTip(mouseX, y + 81 + (j * font.FONT_HEIGHT) + 18 + offsetY, stacks.getDisplayName());  
							}
						}
						else {
							this.mc.getRenderItem().renderItemAndEffectIntoGUI(stacks, x + 80 + maxX + (k * 20), y + 86 + (j * font.FONT_HEIGHT) + offsetY);
							if (mouseX >= x + 80 + maxX + (k * 20) && mouseX <= x + 80 + maxX + (k * 20) + 16 && mouseY >= y + 86 + (j * font.FONT_HEIGHT) + offsetY && mouseY <= y + 86 + (j * font.FONT_HEIGHT) + 16 + offsetY)
							{
								this.drawToolTip(mouseX, y + 81 + (j * font.FONT_HEIGHT) + offsetY, stacks.getDisplayName());  
							}
						}
						RenderHelper.disableStandardItemLighting();  
						
						k++;
					}
				}
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
		
			if(selected_moon != null) {
				font.drawString(GCCoreUtil.translate("book.page." + system.getUnlocalizedName() + "." + selected_moon.getName().toLowerCase()), x + 80 + maxX, y + 46, 0xFFFFFF);
			
				int offsetY = (font.getStringWidth(GCCoreUtil.translate("book.page." + system.getUnlocalizedName() + "." + selected_moon.getName().toLowerCase())) / 260) * font.FONT_HEIGHT;
				
				WorldProvider dim = WorldUtil.getProviderForDimensionClient(this.selected_moon.getDimensionID());
				
				if(dim instanceof IGalacticraftWorldProvider)
                {
					IGalacticraftWorldProvider gcdim = (IGalacticraftWorldProvider) dim;
					
					String[] infos = new String[] 
					{
						"Gravity: " + gcdim.getGravity(),
						"Thermal Level: " + gcdim.getThermalLevelModifier(),
						"Wind Level: " + gcdim.getWindLevel(),
						"Breathable Atmoshpere: " + gcdim.hasBreathableAtmosphere()
					};
					BodiesData data = BodiesHelper.getData().get(selected_moon);
					if(data != null)
					{
						
					}
					
					for(int k = 0; k < infos.length; k++) {
						font.drawString(infos[k], x + 80 + maxX, y + 70 + (k * font.FONT_HEIGHT) + offsetY, 0xFFFFFF);
					}
                }
				
				if(!selected_moon.getReachable()) return;
				font.drawString("Resources:", x + 80 + maxX, y + 126 + offsetY, 0xFFFFFF);
				
				if(this.resources.containsKey(selected_moon))
				{
					int k = 0;
					for(ItemStack stacks : this.resources.get(selected_moon)) {
						
						RenderHelper.enableGUIStandardItemLighting();
						if(k >= 14) {					
							this.mc.getRenderItem().renderItemAndEffectIntoGUI(stacks, x + 80 + maxX + ((k - 14) * 20), y + 140 + 18 + offsetY);
							if (mouseX >= x + 80 + maxX + ((k - 14) * 20) && mouseX <= x + 80 + maxX + ((k - 14) * 20) + 16 && mouseY >= y + 140 + 18 + offsetY && mouseY <= y + 140 + 18 + 16 + offsetY)
							{
								this.drawToolTip(mouseX, y + 135 + 18 + offsetY, stacks.getDisplayName());  
							}
						}
						else {
							this.mc.getRenderItem().renderItemAndEffectIntoGUI(stacks, x + 80 + maxX + (k * 20), y + 140);
							if (mouseX >= x + 80 + maxX + (k * 20) && mouseX <= x + 80 + maxX + (k * 20) + 16 && mouseY >= y + 140 + offsetY && mouseY <= y + 140 + 16 + offsetY)
							{
								this.drawToolTip(mouseX, y + 135 + offsetY, stacks.getDisplayName());  
							}
						}
						RenderHelper.disableStandardItemLighting();  
						
						k++;
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
	
	private void drawToolTip(int mousePosX, int mousePosY, String tooltip)
	{
		this.drawToolTip(mousePosX, mousePosY, tooltip, this.mc.fontRenderer.getStringWidth(tooltip), 8);
	}
	private void drawToolTip(int mousePosX, int mousePosY, String tooltip, int width, int height)
	{
		GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glPushMatrix();
        GL11.glTranslatef(0, 0, 300);
        int k = width;
        int j2 = mousePosX - k / 2;
        int k2 = mousePosY - 12;
        int i1 = height;
/*
        if (j2 + k > width)
        {
            j2 -= (j2 - width + k);
        }

        if (k2 + i1 + 6 > height)
        {
            k2 = height - i1 - 6;
        }
*/
        int j1 = ColorUtil.to32BitColor(190, 0, 153, 255);
        this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
        this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
        this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
        this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
        this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
        int k1 = ColorUtil.to32BitColor(170, 0, 153, 255);
        int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
        this.drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
        this.drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
        this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
        this.drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);

        this.mc.fontRenderer.drawSplitString(tooltip, j2, k2, 150, ColorUtil.to32BitColor(255, 255, 255, 255));

        GL11.glPopMatrix();   
	}
	
	protected void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor)
    {
        float f = (float)(startColor >> 24 & 255) / 255.0F;
        float f1 = (float)(startColor >> 16 & 255) / 255.0F;
        float f2 = (float)(startColor >> 8 & 255) / 255.0F;
        float f3 = (float)(startColor & 255) / 255.0F;
        float f4 = (float)(endColor >> 24 & 255) / 255.0F;
        float f5 = (float)(endColor >> 16 & 255) / 255.0F;
        float f6 = (float)(endColor >> 8 & 255) / 255.0F;
        float f7 = (float)(endColor & 255) / 255.0F;
        float zLevel = 0.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)right, (double)top, (double)zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double)left, (double)top, (double)zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double)left, (double)bottom, (double)zLevel).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, (double)zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
}
