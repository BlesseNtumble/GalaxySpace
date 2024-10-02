package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerRocketAssemblyMachine;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityRocketAssemblyMachine;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiRocketAssemblyMachine extends GuiContainerGC
{
    private static final ResourceLocation electricFurnaceTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/rocket_assembly.png");
    private TileEntityRocketAssemblyMachine tileEntity;
    private GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion(0, 0, 56, 9, null, 0, 0, this);
    private GuiElementInfoRegion processInfoRegion = new GuiElementInfoRegion(0, 0, 52, 25, null, 0, 0, this);

    public GuiRocketAssemblyMachine(InventoryPlayer par1InventoryPlayer, TileEntityRocketAssemblyMachine tileEntity)
    {
        super(new ContainerRocketAssemblyMachine(par1InventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
        this.xSize = 206;
        this.ySize = 223;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.electricInfoRegion.tooltipStrings = new ArrayList<String>();
        this.electricInfoRegion.xPosition = (this.width - this.xSize) / 2 + 126;
        this.electricInfoRegion.yPosition = (this.height - this.ySize) / 2 + 116;
        this.electricInfoRegion.parentWidth = this.width;
        this.electricInfoRegion.parentHeight = this.height;
        this.infoRegions.add(this.electricInfoRegion);
        List<String> batterySlotDesc = new ArrayList<String>();
        batterySlotDesc.add(GCCoreUtil.translate("gui.batterySlot.desc.0"));
        batterySlotDesc.add(GCCoreUtil.translate("gui.batterySlot.desc.1"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + this.inventorySlots.getSlot(0).xDisplayPosition, (this.height - this.ySize) / 2 + + this.inventorySlots.getSlot(0).yDisplayPosition, 18, 18, batterySlotDesc, this.width, this.height, this));
        this.processInfoRegion.tooltipStrings = new ArrayList<String>();
        this.processInfoRegion.xPosition = (this.width - this.xSize) / 2 + 77;
        this.processInfoRegion.yPosition = (this.height - this.ySize) / 2 + 30;
        this.processInfoRegion.parentWidth = this.width;
        this.processInfoRegion.parentHeight = this.height;
        this.infoRegions.add(this.processInfoRegion);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	this.fontRendererObj.drawString(EnumColor.WHITE + this.tileEntity.getInventoryName(), 60 - (this.fontRendererObj.getStringWidth(this.tileEntity.getInventoryName()) / 2), 7, 4210752);
        String displayText;

        if (this.tileEntity.processTicks > 0)
        {
            displayText = EnumColor.BRIGHT_GREEN + GCCoreUtil.translate("gui.status.running.name");
        }
        else
        {
            displayText = EnumColor.ORANGE + GCCoreUtil.translate("gui.status.idle.name");
        }

        String str = EnumColor.WHITE + GCCoreUtil.translate("gui.message.status.name") + ": " + displayText;
        this.fontRendererObj.drawString(str, 152 - this.fontRendererObj.getStringWidth(str) / 2, 105, 4210752);
        this.fontRendererObj.drawString(EnumColor.WHITE + GCCoreUtil.translate("container.inventory"), 22, this.ySize - 88, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the
     * items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        this.mc.renderEngine.bindTexture(GuiRocketAssemblyMachine.electricFurnaceTexture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int containerWidth = (this.width - this.xSize) / 2;
        int containerHeight = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);
        int scale;

        List<String> electricityDesc = new ArrayList<String>();
        electricityDesc.add(GCCoreUtil.translate("gui.energyStorage.desc.0"));
        EnergyDisplayHelper.getEnergyDisplayTooltip(this.tileEntity.getEnergyStoredGC(), this.tileEntity.getMaxEnergyStoredGC(), electricityDesc);
        this.electricInfoRegion.tooltipStrings = electricityDesc;

        if (this.tileEntity.processTicks > 0)
        {
            scale = (int) ((double) this.tileEntity.processTicks / (double) this.tileEntity.processTimeRequired * 100);
        }
        else
        {
            scale = 0;
        }

        List<String> processDesc = new ArrayList<String>();
        processDesc.clear();
        processDesc.add(GCCoreUtil.translate("gui.electricCompressor.desc.0") + ": " + scale + "%");
        this.processInfoRegion.tooltipStrings = processDesc;

        if (this.tileEntity.processTicks > 0)
        {
            scale = (int) ((double) this.tileEntity.processTicks / (double) this.tileEntity.processTimeRequired * 50);
            GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F);
            this.drawTexturedModalRect(containerWidth + 79, containerHeight + 40, 206, 231, scale, 7);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }

        if (this.tileEntity.getEnergyStoredGC() > 0)
        {
            scale = this.tileEntity.getScaledElecticalLevel(54);
            this.drawTexturedModalRect(containerWidth + 131, containerHeight + 117, 206, 230, scale, 7);
            this.drawTexturedModalRect(containerWidth + 119, containerHeight + 116, 206, 239, 11, 10);
        }       
   
        for(int i = 2; i < this.inventorySlots.inventorySlots.size(); i++)
        {
	        int x = this.inventorySlots.getSlot(i).xDisplayPosition;
	        int y = this.inventorySlots.getSlot(i).yDisplayPosition;

	        
	        GL11.glPushMatrix();
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 225, 203, 20, 21);
	        GL11.glPopMatrix();
	        
	        GL11.glPushMatrix();
	        float[] color = new float[3];
	        if(!(this.inventorySlots.getSlot(i).inventory instanceof InventoryPlayer))
	        {		   
	        	Slot slot = (Slot)this.inventorySlots.inventorySlots.get(i);
	        	
	        	if(slot.slotNumber == 10 || slot.slotNumber == 11 || slot.slotNumber == 12)
		        {
		        	color[0] = 0.2F; //R
			        color[1] = 1.0F; //G
			        color[2] = 0.0F; //B	        
			        
		        }
	        	else
		        {
			        color[0] = 0.2F; //R
			        color[1] = 0.7F; //G
			        color[2] = 1.0F; //B	    
	        	}
		        GL11.glColor4f(color[0], color[1], color[2], 1.0F);
	        }
	        
	        
	        
	        this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 206, 203, 20, 21);	

	        GL11.glPopMatrix();
        } 
        if(GalaxySpace.debug)
	        for(int i = 0; i < this.inventorySlots.inventorySlots.size(); i++)
	        {
	        	int x = this.inventorySlots.getSlot(i).xDisplayPosition;
		        int y = this.inventorySlots.getSlot(i).yDisplayPosition;
	        	this.fontRendererObj.drawString(EnumColor.WHITE + "" + this.inventorySlots.getSlot(i).getSlotIndex(), containerWidth + x + 5, containerHeight + y + 5, 4210752);
	        }
    }
}
