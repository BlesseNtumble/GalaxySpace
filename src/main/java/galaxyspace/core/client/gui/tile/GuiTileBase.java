package galaxyspace.core.client.gui.tile;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import asmodeuscore.core.utils.Utils;
import galaxyspace.GalaxySpace;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.prefab.inventory.SlotUpgrades;
import galaxyspace.core.util.GSConstants;
import galaxyspace.core.util.GSUtils;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.inventory.SlotSpecific;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class GuiTileBase extends GuiContainerGC {

	protected enum Style {
		CLASSIC,
		MODERN
	}
	
	private GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion(0, 0, 56, 9, null, 0, 0, this);
	private GuiElementInfoRegion processInfoRegion = new GuiElementInfoRegion(0, 0, 52, 25, null, 0, 0, this);
	private GuiElementInfoRegion batteryInfoRegion = new GuiElementInfoRegion(0, 0, 18, 18, null, 0, 0, this);
	private GuiElementInfoRegion moduleInfoRegion = new GuiElementInfoRegion(0, 0, 18, 21 * 4, null, 0, 0, this);
	private GuiElementInfoRegion fluidTankRegion = new GuiElementInfoRegion(0, 0, 16, 38, null, 0, 0, this);
    
	private final int header_type, split_type;
	protected int header;
	protected int moduleInfoX, moduleInfoY;
	
	public GuiTileBase(Container container, int header, int split) {
		super(container);
		this.xSize = isModuleSupport() ? 176 + 20 : 176;
		this.ySize = 166;
		this.header_type = header;
		this.split_type = split;
		this.header = -1;
		moduleInfoX = 0;
		moduleInfoY = 0;
	}

	@Override
	public void initGui() {
		super.initGui();
		
		electricInfoRegion.tooltipStrings = new ArrayList<String>();
		electricInfoRegion.parentWidth = this.width;
		electricInfoRegion.parentHeight = this.height;
		this.infoRegions.add(electricInfoRegion);

		this.processInfoRegion.tooltipStrings = new ArrayList<String>();		
		this.processInfoRegion.parentWidth = this.width;
		this.processInfoRegion.parentHeight = this.height;
		this.infoRegions.add(this.processInfoRegion);
		
		this.fluidTankRegion.tooltipStrings = new ArrayList<String>();		
		this.fluidTankRegion.parentWidth = this.width;
		this.fluidTankRegion.parentHeight = this.height;
		this.infoRegions.add(this.fluidTankRegion);
	        
		List<String> desc = new ArrayList<String>();
		if(getBatterySlot() != null) {
			desc.add(GCCoreUtil.translate("gui.battery_slot.desc.0"));
			desc.add(GCCoreUtil.translate("gui.battery_slot.desc.1"));
			batteryInfoRegion.tooltipStrings = desc;		
			batteryInfoRegion.xPosition = (this.width - this.xSize) / 2 + getBatterySlot().xPos;
			batteryInfoRegion.yPosition = (this.height - this.ySize) / 2 + getBatterySlot().yPos;
			batteryInfoRegion.parentWidth = this.width;
			batteryInfoRegion.parentHeight = this.height;
			this.infoRegions.add(batteryInfoRegion);	
		}
		
		if (isModuleSupport()) {
			desc = new ArrayList<String>();
			desc.add(TextFormatting.GREEN + GCCoreUtil.translate("gui.available_modules.desc"));
			desc.add("");
			//desc.add("- " + new ItemStack(GSItems.UPGRADES, 1, 2).getDisplayName());
			//desc.add("- " + new ItemStack(GSItems.UPGRADES, 1, 3).getDisplayName());
			moduleInfoRegion.tooltipStrings = desc;
			moduleInfoRegion.xPosition = (this.width - this.xSize) / 2 + moduleInfoX;
			moduleInfoRegion.yPosition = (this.height - this.ySize) / 2 + moduleInfoY;
			moduleInfoRegion.parentWidth = this.width;
			moduleInfoRegion.parentHeight = this.height;
			this.infoRegions.add(moduleInfoRegion);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		//int containerWidth = (this.width - this.xSize) / 2;
        //int containerHeight = (this.height - this.ySize) / 2;
        
		if(getStyle() == Style.MODERN) {
			int xOffset = 0;
			int yOffset = 0;
			int xInvOffset = 0;
			int yInvOffset = 0;
			
			if(header == 0) {
				xOffset = -15;
				yOffset = -3;
			}
			if(header == 4) {
				xOffset = 30;
				yOffset = -3;
			}
			if(header_type == 3) {
				xInvOffset = -8;
				yInvOffset = 3;
			}
			if(header_type == 4) {
				xInvOffset = -5;
				yInvOffset = 2;
			}
			
			this.fontRenderer.drawString(TextFormatting.WHITE + this.getName(), 176 / 2 - fontRenderer.getStringWidth(this.getName()) / 2 + xOffset, 5 + yOffset, 4210752);
			this.fontRenderer.drawString(TextFormatting.WHITE + GCCoreUtil.translate("container.inventory"), 15 + xInvOffset, this.ySize - 24*3 - 13 + yInvOffset, 4210752);
		
		} else {
			this.fontRenderer.drawString(TextFormatting.DARK_GRAY + this.getName(), 176 / 2 - fontRenderer.getStringWidth(this.getName()) / 2, 5, 4210752);
			this.fontRenderer.drawString(TextFormatting.DARK_GRAY + GCCoreUtil.translate("container.inventory"), 8, this.ySize - 24*3 - 8, 4210752);
		}
		

	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.mc.renderEngine.bindTexture(getTexture());
		GlStateManager.color(1, 1, 1);
		
		int containerWidth = (this.width - this.xSize) / 2;
        int containerHeight = (this.height - this.ySize) / 2;
        
        int xSize = 176;
        //HEADER
        if(header == -1 || getStyle() == Style.CLASSIC)
        	this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, xSize, 12);
        else
        	this.drawTexturedModalRect(containerWidth, containerHeight, 0, 149 + (13 * header), xSize, 12);
        //BODY
        //(float x, float y, float width, float height, float u, float v, float uWidth, float vHeight, boolean invertX, boolean invertY, float texSizeX, float texSizeY)
        
        Utils.drawTexturedModalRect(containerWidth, containerHeight + 12, xSize, this.ySize - 27*4 , 0, 12, xSize, 12, false, false, 256, 256);
        
        renderInventoryPanel(containerWidth, containerHeight, header_type, split_type);
        //FOOTER
        this.drawTexturedModalRect(containerWidth, containerHeight + this.ySize, 0, 60-12, xSize, 13);
        
        if(isModuleSupport()) {        	
        	renderUpgradesPanel(containerWidth, containerHeight);
        }
        
        // Slots
        for(int i = 0; i < this.inventorySlots.inventorySlots.size(); i++)
        {
	        int x = this.inventorySlots.getSlot(i).xPos - 1;
	        int y = this.inventorySlots.getSlot(i).yPos - 1;
	        
	       /* if(!(this.inventorySlots.getSlot(i).inventory instanceof InventoryPlayer))
	        {*/
		        
		        GL11.glPushMatrix();
		        
		        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		        if(this.inventorySlots.getSlot(i) instanceof SlotUpgrades)
		        {
	        		this.drawTexturedModalRect(containerWidth + x, containerHeight + y, 84, 62, 18, 18);
	        	}
		        else if(this.inventorySlots.getSlot(i) instanceof SlotSpecific) {
		        	SlotSpecific slot = (SlotSpecific) this.inventorySlots.getSlot(i);
		        	
		        	if(slot.isItemValid(new ItemStack(GCItems.battery)))
		        		this.drawTexturedModalRect(containerWidth + x, containerHeight + y, 21, 62, 18, 18);
		        	else if(slot.isItemValid(new ItemStack(Items.DYE, 1, 15)))
		        		this.drawTexturedModalRect(containerWidth + x, containerHeight + y, 105, 62, 18, 18);		        	
		        	else
		        		this.drawTexturedModalRect(containerWidth + x, containerHeight + y, 0, 62, 18, 18);	
		        }
		        else 
		        	this.drawTexturedModalRect(containerWidth + x, containerHeight + y, 0, 62, 18, 18);		        	
		        
		        GL11.glPopMatrix();
	        //}
        }
        
        if(GalaxySpace.debug) GSUtils.renderDebugGui(this, containerWidth, containerHeight);
        this.mc.renderEngine.bindTexture(getTexture());
	}
	
	private void renderInventoryPanel(int x, int y, int header_type, int split_type) {
		int xSize = 176;

		if(getStyle() == Style.CLASSIC) {
			Utils.drawTexturedModalRect(x, y + this.ySize - 27*4, xSize, 115 , 0, 12, xSize, 12, false, false, 256, 256);
			
		}
		else {
			if(header_type == -1) {			
				Utils.drawTexturedModalRect(x, y + this.ySize / 2 + 1, xSize, this.ySize / 2 , 0, 12, xSize, 12, false, false, 256, 256);
			}
			else {
				
				//SPLIT
					this.drawTexturedModalRect(x, y + this.ySize - 27*4 + 11, 0, 244 - (13 * split_type), xSize, 13);
				//HEADER
					this.drawTexturedModalRect(x, y + this.ySize - 27*4 + 23, 0, 149 + (13 * header_type), xSize, 12);
				
					Utils.drawTexturedModalRect(x, y + this.ySize - 27*4 + 35, xSize, 80 , 0, 12, xSize, 12, false, false, 256, 256);
				
			}
		}
	}
	
	private void renderUpgradesPanel(int x, int y) {
		int xSize = 176;
		
		this.drawTexturedModalRect(x + xSize - 1, y + 12, 0, 120, 26, 6);		
		Utils.drawTexturedModalRect(x + xSize - 1, y + 18, 26, 20*4, 0, 126, 26, 12, false, false, 256, 256);
		this.drawTexturedModalRect(x + xSize - 1, y + 24*4, 0, 147-6, 26, 7);
	}
	
	protected void renderProgressArray(int x, int y, int size, int ticks, int max) {
		this.renderProgressArray(x, y, size, ticks, max, false, false);
	}
	
	protected void renderProgressArray(int x, int y, int size, int ticks, int max, boolean invertX, boolean invertY) {
		Utils.drawTexturedModalRect(x, y, size, 15, 181, 109, 8, 15, invertX, invertY, 256, 256);
		if(invertX)
			Utils.drawTexturedModalRect(x - 8, y, 8, 15, 209, 109, 8, 15, invertX, invertY, 256, 256);
		else
			Utils.drawTexturedModalRect(x + size, y, 8, 15, 209, 109, 8, 15, invertX, invertY, 256, 256);
		int scale = 0;
		if (ticks > 0) {
			scale = (int) ((double) ticks / (double) max * (size + 8));
			
			Utils.drawTexturedModalRect(x, y, scale, 15, 181, 125, 8, 15, invertX, invertY, 256, 256);
			if(scale > size - 8)
				this.drawTexturedModalRect(x + size, y, 209, 125, scale - size, 16);
			

			List<String> processDesc = new ArrayList<String>();
		    processDesc.clear();
		    processDesc.add(GCCoreUtil.translate("gui.electric_compressor.desc.0") + ": " + scale + "%");
		    this.processInfoRegion.tooltipStrings = processDesc;
		    this.processInfoRegion.xPosition = x;
			this.processInfoRegion.yPosition = y;
		}
	}		
		
	
	protected void renderEnergyBar(int x, int y, int scale, float energy, float maxEnergy) {
		
		List<String> electricityDesc = new ArrayList<String>();
		electricityDesc.add(GCCoreUtil.translate("gui.energy_storage.desc.0"));
		EnergyDisplayHelper.getEnergyDisplayTooltip(energy, maxEnergy, electricityDesc);
		electricInfoRegion.tooltipStrings = electricityDesc;
		electricInfoRegion.xPosition = x + 10;
		electricInfoRegion.yPosition = y;
		
		this.drawTexturedModalRect(x, y, 180, 56, 9, 8);
		this.drawTexturedModalRect(x + 11, y, 180, 47, 56, 9);
		if(energy > 0) {			
			this.drawTexturedModalRect(x-1, y, 180, 7, 11, 10);
            this.drawTexturedModalRect(x + 12, y + 1, 180, 0, scale, 7);
		}
	}

	protected void renderFluidTank(int x, int y, FluidTank tank, int scale) {
		this.drawTexturedModalRect(x, y, 180, 67, 20, 42);	
		GSUtils.displayGauge(x + 1, y - 19, scale, tank.getFluid(), 0);
		this.mc.renderEngine.bindTexture(this.getTexture());
        drawTexturedModalRect(x, y, 200, 67, 16, 42);
        
        List<String> processDesc = new ArrayList<String>();
	    processDesc.clear();

	    int fuelLevel = tank != null && tank.getFluid() != null ? tank.getFluid().amount : 0;
        int fuelCapacity = tank != null ? tank.getCapacity() : 0;
        processDesc.add(EnumColor.YELLOW + tank.getFluid().getLocalizedName() + ": " + fuelLevel + " / " + fuelCapacity);
       
	    this.fluidTankRegion.tooltipStrings = processDesc;
	    this.fluidTankRegion.xPosition = x;
		this.fluidTankRegion.yPosition = y;
	}
	
	protected abstract boolean isModuleSupport();
	protected abstract String getName();
	protected abstract Slot getBatterySlot();
	
	protected Style getStyle() {
		return GSConfigCore.enableModernGUI ? Style.MODERN : Style.CLASSIC;
	}
	
	protected ResourceLocation getTexture() {
		return GSConfigCore.enableModernGUI ? GSConstants.GUI_MACHINE_MODERN : GSConstants.GUI_MACHINE_CLASSIC;
	}

	
	
}
