package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import galaxyspace.GalaxySpace;
import galaxyspace.core.GSItems;
import galaxyspace.core.client.gui.tile.GuiTileBase;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerAssembler;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityAssembler;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAssembler extends GuiTileBase
{
    //private static final ResourceLocation electricFurnaceTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/assembly_machine.png");
    private TileEntityAssembler tileEntity;
   
    //private GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion(0, 0, 56, 9, null, 0, 0, this);
    //private GuiElementInfoRegion processInfoRegion = new GuiElementInfoRegion(0, 0, 52, 25, null, 0, 0, this);

    public GuiAssembler(InventoryPlayer par1InventoryPlayer, TileEntityAssembler tileEntity)
    {
        super(new ContainerAssembler(par1InventoryPlayer, tileEntity), 2, 1);
        this.tileEntity = tileEntity;
        this.ySize = 199;
        moduleList = new ItemStack[] {new ItemStack(GSItems.UPGRADES, 1, 2), new ItemStack(GSItems.UPGRADES, 1, 3)};
    }

    @Override
    public void initGui()
    {
        super.initGui();
        /*this.electricInfoRegion.tooltipStrings = new ArrayList<String>();
        this.electricInfoRegion.xPosition = (this.width - this.xSize) / 2 + 17;
        this.electricInfoRegion.yPosition = (this.height - this.ySize) / 2 + 95;
        this.electricInfoRegion.parentWidth = this.width;
        this.electricInfoRegion.parentHeight = this.height;
        this.infoRegions.add(this.electricInfoRegion);*/
        /*
        List<String> desc = new ArrayList<String>();
        desc.add(GCCoreUtil.translate("gui.battery_slot.desc.0"));
        desc.add(GCCoreUtil.translate("gui.battery_slot.desc.1"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 77, (this.height - this.ySize) / 2 + 93, 18, 18, desc, this.width, this.height, this));
      
        this.processInfoRegion.tooltipStrings = new ArrayList<String>();
        this.processInfoRegion.xPosition = (this.width - this.xSize) / 2 + 77;
        this.processInfoRegion.yPosition = (this.height - this.ySize) / 2 + 30;
        this.processInfoRegion.parentWidth = this.width;
        this.processInfoRegion.parentHeight = this.height;
        this.infoRegions.add(this.processInfoRegion);
        
        desc = new ArrayList<String>();
        desc.add(EnumColor.BRIGHT_GREEN + GCCoreUtil.translate("gui.available_modules.desc"));
        desc.add("");
        desc.add("- " + new ItemStack(GSItems.UPGRADES, 1, 2).getDisplayName());
        desc.add("- " + new ItemStack(GSItems.UPGRADES, 1, 3).getDisplayName());
        this.infoRegions.add(new GuiElementInfoRegion((this.width + this.xSize) / 2, (this.height - this.ySize) / 2 + 16, 18, 21 * 4, desc, this.width, this.height, this));
       */
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        super.drawGuiContainerForegroundLayer(par1, par2);
        String displayText;

        if (this.tileEntity.processTicks > 0)
        {
            displayText = EnumColor.BRIGHT_GREEN + GCCoreUtil.translate("gui.status.running.name");
        }
        else
        {
            displayText = EnumColor.ORANGE + GCCoreUtil.translate("gui.status.idle.name");
        }

        TextFormatting color = getStyle() == Style.MODERN ? TextFormatting.WHITE : TextFormatting.DARK_GRAY;
        String str = color + GCCoreUtil.translate("gui.message.status.name") + ": " + displayText;
        this.fontRenderer.drawString(str, 135 - this.fontRenderer.getStringWidth(str) / 2, 85, 4210752);
       //		str = "" + this.tileEntity.storage.getMaxExtract();
//		this.fontRendererObj.drawString(str, 120 - this.fontRendererObj.getStringWidth(str) / 2, 85, 4210752);
//		//		str = ElectricityDisplay.getDisplay(this.tileEntity.getVoltage(), ElectricUnit.VOLTAGE);
//		this.fontRendererObj.drawString(str, 120 - this.fontRendererObj.getStringWidth(str) / 2, 95, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the
     * items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
    	super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        this.mc.renderEngine.bindTexture(getTexture());
       /* GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);*/
        int containerWidth = (this.width - this.xSize) / 2;
        int containerHeight = (this.height - this.ySize) / 2;
        
        //arrow
        //this.drawTexturedModalRect(containerWidth + 82, containerHeight + 38, 176, 73, 52, 16);
        
        
        int scale;
/*
        List<String> electricityDesc = new ArrayList<String>();
        electricityDesc.add(GCCoreUtil.translate("gui.energy_storage.desc.0"));
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
        processDesc.add(GCCoreUtil.translate("gui.electric_compressor.desc.0") + ": " + scale + "%");
        this.processInfoRegion.tooltipStrings = processDesc;

        if (this.tileEntity.processTicks > 0)
        {
            scale = (int) ((double) this.tileEntity.processTicks / (double) this.tileEntity.processTimeRequired * 54);
            this.drawTexturedModalRect(containerWidth + 82, containerHeight + 38, 176, 13, scale, 17);
        }
*/
        this.renderProgressArray(containerWidth + 86, containerHeight + 38, 35, tileEntity.processTicks, tileEntity.processTimeRequired);
        this.renderEnergyBar(containerWidth + 5, containerHeight + 85, this.tileEntity.getScaledElecticalLevel(54), this.tileEntity.getEnergyStoredGC(), this.tileEntity.getMaxEnergyStoredGC());
       
        /*if (this.tileEntity.getEnergyStoredGC() > 0)
        {
            scale = this.tileEntity.getScaledElecticalLevel(54);
            this.drawTexturedModalRect(containerWidth + 116 - 98, containerHeight + 96, 176, 30, scale, 7);
            this.drawTexturedModalRect(containerWidth + 4, containerHeight + 95, 176, 37, 11, 10);
        }*/

        //assembler elements
        this.drawTexturedModalRect(containerWidth + 94, containerHeight + 29, 200, 19, 17, 13);
        this.drawTexturedModalRect(containerWidth + 98, containerHeight + 50, 197, 40, 16, 6);
        if (this.tileEntity.processTicks > this.tileEntity.processTimeRequired / 2)
        {
            this.drawTexturedModalRect(containerWidth + 94, containerHeight + 29, 182, 19, 17, 13);
            this.drawTexturedModalRect(containerWidth + 98, containerHeight + 50, 180, 40, 16, 6);
        } 

        
        if(GalaxySpace.debug) GSUtils.renderDebugGui(this, containerWidth, containerHeight);
    }
    
    @Override
    protected boolean isModuleSupport() {
		return true;
	}
	
    @Override
	protected String getName() {
		return tileEntity.getName();
	}

	@Override
	protected Slot getBatterySlot() {
		return inventorySlots.getSlotFromInventory(tileEntity, 0);
	}	
}
