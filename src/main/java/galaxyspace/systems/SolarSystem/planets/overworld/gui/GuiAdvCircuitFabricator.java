package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerAdvCircuitFabricator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityAdvCircuitFabricator;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAdvCircuitFabricator extends GuiContainerGC
{
   
	private static final ResourceLocation guiTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/base_gui_1.png");
	private static final ResourceLocation circuitFabricatorTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/circuit_fabricator.png");
    private TileEntityAdvCircuitFabricator tileEntity;
    private GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion(0, 0, 56, 9, null, 0, 0, this);
    private GuiElementInfoRegion processInfoRegion = new GuiElementInfoRegion(0, 0, 53, 12, null, 0, 0, this);

    public GuiAdvCircuitFabricator(InventoryPlayer par1InventoryPlayer, TileEntityAdvCircuitFabricator tileEntity)
    {
        super(new ContainerAdvCircuitFabricator(par1InventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
        this.ySize = 204;
    }
    
    @Override
    public void initGui()
    {
        super.initGui();
        this.electricInfoRegion.tooltipStrings = new ArrayList<String>();
        this.electricInfoRegion.xPosition = (this.width - this.xSize) / 2 + 17;
        this.electricInfoRegion.yPosition = (this.height - this.ySize) / 2 + 102;
        this.electricInfoRegion.parentWidth = this.width;
        this.electricInfoRegion.parentHeight = this.height;
        this.infoRegions.add(this.electricInfoRegion);
        List<String> desc = new ArrayList<String>();
        desc.add(GCCoreUtil.translate("gui.battery_slot.desc.0"));
        desc.add(GCCoreUtil.translate("gui.battery_slot.desc.1"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 5, (this.height - this.ySize) / 2 + 68, 18, 18, desc, this.width, this.height, this));
        this.processInfoRegion.tooltipStrings = new ArrayList<String>();
        this.processInfoRegion.xPosition = (this.width - this.xSize) / 2 + 87;
        this.processInfoRegion.yPosition = (this.height - this.ySize) / 2 + 19;
        this.processInfoRegion.parentWidth = this.width;
        this.processInfoRegion.parentHeight = this.height;
        this.infoRegions.add(this.processInfoRegion);
        
        desc = new ArrayList<String>();
        desc.add(EnumColor.BRIGHT_GREEN + GCCoreUtil.translate("gui.available_modules.desc"));
        desc.add("");
        desc.add("- " + new ItemStack(GSItems.UPGRADES, 1, 2).getDisplayName());
        desc.add("- " + new ItemStack(GSItems.UPGRADES, 1, 3).getDisplayName());
        this.infoRegions.add(new GuiElementInfoRegion((this.width + this.xSize) / 2, (this.height - this.ySize) / 2 + 16, 18, 21 * 4, desc, this.width, this.height, this));
     
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString(EnumColor.WHITE + this.tileEntity.getName(), 50 - this.tileEntity.getName().length() / 2, 1, 4210752);
        String displayText;

        if (this.tileEntity.processTicks > 0)
        {
            displayText = EnumColor.BRIGHT_GREEN + GCCoreUtil.translate("gui.status.running.name");
        }
        else
        {
            displayText = EnumColor.ORANGE + GCCoreUtil.translate("gui.status.idle.name");
        }

        String str = GCCoreUtil.translate("gui.message.status.name") + ":";
        this.fontRenderer.drawString(EnumColor.WHITE + str, 115 - this.fontRenderer.getStringWidth(str) / 2, 90, 4210752);
        displayText = this.tileEntity.getGUIstatus(displayText, null, true);
        this.fontRenderer.drawString(displayText, 115 - this.fontRenderer.getStringWidth(displayText) / 2, 100, 4210752);
        this.fontRenderer.drawString(EnumColor.WHITE + GCCoreUtil.translate("container.inventory"), 16, this.ySize - 87, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        this.mc.renderEngine.bindTexture(guiTexture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int containerWidth = (this.width - this.xSize) / 2;
        int containerHeight = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);
       // this.drawTexturedModalRect(containerWidth + 5, containerHeight + 68, 176, 47, 18, 18);
       
        
        this.drawTexturedModalRect(containerWidth + 85, containerHeight + 22, 192, 47, 56, 9);
        this.mc.renderEngine.bindTexture(circuitFabricatorTexture);
        this.drawTexturedModalRect(containerWidth + 30, containerHeight + 22, 0, 0, 131, 61);
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(guiTexture);        
        int scale;
        List<String> electricityDesc = new ArrayList<String>();
        electricityDesc.add(GCCoreUtil.translate("gui.energy_storage.desc.0"));
        EnergyDisplayHelper.getEnergyDisplayTooltip(this.tileEntity.getEnergyStoredGC(), this.tileEntity.getMaxEnergyStoredGC(), electricityDesc);
        this.electricInfoRegion.tooltipStrings = electricityDesc;

        if (this.tileEntity.processTicks > 0)
        {
            scale = (this.tileEntity.processTicks * 100) / this.tileEntity.getProcessTimeRequired();
        }
        else
        {
            scale = 0;
        }

        List<String> processDesc = new ArrayList<String>();
        processDesc.clear();
        processDesc.add(GCCoreUtil.translate("gui.electric_compressor.desc.0") + ": " + scale + "%");
        this.processInfoRegion.tooltipStrings = processDesc;

        if (this.tileEntity.processTicks > 0)
        {
        	this.mc.renderEngine.bindTexture(circuitFabricatorTexture);
            scale = (this.tileEntity.processTicks * 52) / this.tileEntity.getProcessTimeRequired();
            this.drawTexturedModalRect(containerWidth + 86, containerHeight + 23, 192, 1 + this.tileEntity.processTicks % 9 / 3 * 10, scale, 7);
        }
        
        this.mc.renderEngine.bindTexture(guiTexture);
        
        //Energy
        this.drawTexturedModalRect(containerWidth + 16, containerHeight + 102, 192, 47, 56, 9);
        this.drawTexturedModalRect(containerWidth + 4, containerHeight + 102, 192, 56, 10, 8);
        if (this.tileEntity.getEnergyStoredGC() > 0)
        {
            scale = this.tileEntity.getScaledElecticalLevel(54);
            this.drawTexturedModalRect(containerWidth + 116 - 99, containerHeight + 103, 192, 0, scale, 7);
            this.drawTexturedModalRect(containerWidth + 3, containerHeight + 102, 192, 7, 11, 10);           
        }
        
        // Slots
        for(int i = 0; i < this.inventorySlots.inventorySlots.size(); i++)
        {
	        int x = this.inventorySlots.getSlot(i).xPos;
	        int y = this.inventorySlots.getSlot(i).yPos;
	        
	        if(!(this.inventorySlots.getSlot(i).inventory instanceof InventoryPlayer))
	        {
		        
		        GL11.glPushMatrix();
		        switch(i)
		        {
		        	case 1:
		        	{
		        		this.mc.renderEngine.bindTexture(guiTexture);
		        		this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 192, 26, 20, 20);
		        		
		        		this.mc.renderEngine.bindTexture(circuitFabricatorTexture);
		        		this.drawTexturedModalRect(containerWidth + x, containerHeight + y, 141, 1, 16, 16);	        		 
		        		
		        		break;
		        	}
		        	case 2:
		        	case 3:
		        	{
		        		this.mc.renderEngine.bindTexture(guiTexture);
		        		this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 192, 26, 20, 20);
		        		
		        		this.mc.renderEngine.bindTexture(circuitFabricatorTexture);
		        		this.drawTexturedModalRect(containerWidth + x, containerHeight + y, 158, 1, 16, 16);	        		 
		        		
		        		break;
		        	}
		        	case 4:
		        	{
		        		this.mc.renderEngine.bindTexture(guiTexture);
		        		this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 192, 26, 20, 20);
		        		
		        		this.mc.renderEngine.bindTexture(circuitFabricatorTexture);
		        		this.drawTexturedModalRect(containerWidth + x, containerHeight + y, 175, 1, 16, 16);	        		 
		        		
		        		break;
		        	}
		        	case 7:
		        	case 8:
		        	case 9:
		        	case 10:
		        	{
		        		this.mc.renderEngine.bindTexture(guiTexture);
		        		this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 213, 175, 20, 20);	        		 
		        		break;
		        	}	        	
		        	default:
		        	{
		        		this.mc.renderEngine.bindTexture(guiTexture);
		        		this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 192, 26, 20, 20);
		        		break;
		        	}	        	
		        }
		        GL11.glPopMatrix();
	        }
        }
        
        if(GalaxySpace.debug) GSUtils.renderDebugGui(this, containerWidth, containerHeight);
    }
}
