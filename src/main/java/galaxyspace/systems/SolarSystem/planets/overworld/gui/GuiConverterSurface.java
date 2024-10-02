package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerConverterSurface;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityConverterSurface;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiConverterSurface extends GuiContainerGC
{
    private static final ResourceLocation electricFurnaceTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/converter_surface.png");
    private GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion(0, 0, 56, 9, null, 0, 0, this);
    private GuiElementInfoRegion waterTankRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 28, 16, 38, new ArrayList<String>(), this.width, this.height, this);
    
    private TileEntityConverterSurface tileEntity;

    public GuiConverterSurface(InventoryPlayer par1InventoryPlayer, TileEntityConverterSurface tileEntity)
    {
        super(new ContainerConverterSurface(par1InventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
        this.ySize = 196;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.electricInfoRegion.tooltipStrings = new ArrayList<String>();
        this.electricInfoRegion.xPosition = (this.width - this.xSize) / 2 + 17;
        this.electricInfoRegion.yPosition = (this.height - this.ySize) / 2 + 95;
        this.electricInfoRegion.parentWidth = this.width;
        this.electricInfoRegion.parentHeight = this.height;
        this.infoRegions.add(this.electricInfoRegion);
        List<String> batterySlotDesc = new ArrayList<String>();
        batterySlotDesc.add(GCCoreUtil.translate("gui.batterySlot.desc.0"));
        batterySlotDesc.add(GCCoreUtil.translate("gui.batterySlot.desc.1"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 96, 18, 18, batterySlotDesc, this.width, this.height, this));
    
        List<String> fuelTankDesc = new ArrayList<String>();
        int fuelLevel = this.tileEntity.waterTank != null && this.tileEntity.waterTank.getFluid() != null ? this.tileEntity.waterTank.getFluid().amount : 0;
        int fuelCapacity = this.tileEntity.waterTank != null ? this.tileEntity.waterTank.getCapacity() : 0;
        fuelTankDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.message.water.name") + ": " + fuelLevel + " / " + fuelCapacity);
        this.waterTankRegion.tooltipStrings = fuelTankDesc;
        this.waterTankRegion.xPosition = (this.width - this.xSize) / 2 + 7;
        this.waterTankRegion.yPosition = (this.height - this.ySize) / 2 + 28;
        this.waterTankRegion.parentWidth = this.width;
        this.waterTankRegion.parentHeight = this.height;
        this.infoRegions.add(this.waterTankRegion);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of
     * the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRendererObj.drawString(EnumColor.WHITE + this.tileEntity.getInventoryName(), 70, 1, 4210752);
        String displayText = "";

        if (this.tileEntity.processTicks > 0)
        {
            displayText = EnumColor.BRIGHT_GREEN + GCCoreUtil.translate("gui.status.running.name");
        }
        else
        {
            displayText = EnumColor.ORANGE + GCCoreUtil.translate("gui.status.idle.name");
        }

        this.fontRendererObj.drawString(EnumColor.WHITE + GCCoreUtil.translate("gui.message.status.name") + ": " + displayText, 87, 52, 4210752);
//		this.fontRendererObj.drawString("" + this.tileEntity.storage.getMaxExtract(), 97, 56, 4210752);
//		this.fontRendererObj.drawString("Voltage: " + (int) (this.tileEntity.getVoltage() * 1000.0F), 97, 68, 4210752);
        this.fontRendererObj.drawString(EnumColor.WHITE + GCCoreUtil.translate("container.inventory"), 13, this.ySize - 88, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the
     * items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        this.mc.renderEngine.bindTexture(GuiConverterSurface.electricFurnaceTexture);
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
            scale = (int) ((double) this.tileEntity.processTicks / (double) this.tileEntity.processTimeRequired * 54);
            this.drawTexturedModalRect(containerWidth + 59, containerHeight + 21, 176, 38, 69 - scale, 31);
        }

        if (this.tileEntity.getEnergyStoredGC() > 0)
        {
            scale = this.tileEntity.getScaledElecticalLevel(54);
            this.drawTexturedModalRect(containerWidth + 18, containerHeight + 96, 192, 0, scale, 7);
            this.drawTexturedModalRect(containerWidth + 5, containerHeight + 96, 192, 8, 11, 10);
        }
        
        final int fuelgraphLevel = this.tileEntity.getScaledWaterLevel(38);
        this.drawTexturedModalRect((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 12 + 54 - fuelgraphLevel, 176, 38 - fuelgraphLevel, 16, fuelgraphLevel);

        List<String> waterTankDesc = new ArrayList<String>();
        int waterLevel = this.tileEntity.waterTank != null && this.tileEntity.waterTank.getFluid() != null ? this.tileEntity.waterTank.getFluid().amount : 0;
        int waterCapacity = this.tileEntity.waterTank != null ? this.tileEntity.waterTank.getCapacity() : 0;
        waterTankDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.message.water.name") + ": " + waterLevel + " / " + waterCapacity);
        this.waterTankRegion.tooltipStrings = waterTankDesc;
    }
}
