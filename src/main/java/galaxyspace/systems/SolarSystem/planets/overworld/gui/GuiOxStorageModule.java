package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerOxStorageModule;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityOxStorageModule;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiOxStorageModule extends GuiContainerGC
{
    private static final ResourceLocation batteryBoxTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/oxstorage_module.png");

    private TileEntityOxStorageModule tileEntity;

    public GuiOxStorageModule(InventoryPlayer par1InventoryPlayer, TileEntityOxStorageModule storageModule)
    {
        super(new ContainerOxStorageModule(par1InventoryPlayer, storageModule));
        this.tileEntity = storageModule;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        List<String> oxygenSlotDesc = new ArrayList<String>();
        oxygenSlotDesc.add(GCCoreUtil.translate("gui.oxygenSlot.desc.0"));
        oxygenSlotDesc.add(GCCoreUtil.translate("gui.oxygenSlot.desc.1"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 16, (this.height - this.ySize) / 2 + 21, 18, 18, oxygenSlotDesc, this.width, this.height, this));
    }
    
/**
     * Draw the foreground layer for the GuiContainer (everything in front of
     * the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	this.fontRendererObj.drawString(EnumColor.WHITE + this.tileEntity.getInventoryName(), this.xSize / 2 - this.fontRendererObj.getStringWidth(this.tileEntity.getInventoryName()) / 2, 4, 4210752);
        String displayJoules = (int) (this.tileEntity.storedOxygen + 0.5F) + " " + GCCoreUtil.translate("gui.message.of.name");
        String displayMaxJoules = "" + (int) this.tileEntity.maxOxygen;
        String maxOutputLabel = GCCoreUtil.translate("gui.maxOutput.desc") + ": " + TileEntityOxStorageModule.OUTPUT_PER_TICK * 20 + GCCoreUtil.translate("gui.perSecond");

        this.fontRendererObj.drawString(EnumColor.YELLOW + displayJoules, 122 - this.fontRendererObj.getStringWidth(displayJoules) / 2 - 35, 30, 4210752);
        this.fontRendererObj.drawString(EnumColor.WHITE + displayMaxJoules, 122 - this.fontRendererObj.getStringWidth(displayMaxJoules) / 2 - 35, 40, 4210752);
        this.fontRendererObj.drawString(EnumColor.WHITE + maxOutputLabel, 122 - this.fontRendererObj.getStringWidth(maxOutputLabel) / 2 - 35, 60, 4210752);
        this.fontRendererObj.drawString(EnumColor.WHITE + GCCoreUtil.translate("container.inventory"), 14, this.ySize - 94 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the
     * items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        this.mc.renderEngine.bindTexture(GuiOxStorageModule.batteryBoxTexture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int containerWidth = (this.width - this.xSize) / 2;
        int containerHeight = (this.height - this.ySize) / 2;
        // Background energy bar
        this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);
        // Foreground energy bar
        int scale = (int) ((double) this.tileEntity.storedOxygen / (double) this.tileEntity.maxOxygen * 72);
        this.drawTexturedModalRect(containerWidth + 52, containerHeight + 52, 176, 0, scale, 3);
    }
}
