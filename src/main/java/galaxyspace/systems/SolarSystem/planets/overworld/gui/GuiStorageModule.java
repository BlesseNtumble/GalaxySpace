package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerStorageModule;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityStorageModule;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiStorageModule extends GuiContainer
{
    private static final ResourceLocation batteryBoxTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/storage_module.png");

    private TileEntityStorageModule tileEntity;

    public GuiStorageModule(InventoryPlayer par1InventoryPlayer, TileEntityStorageModule batteryBox)
    {
        super(new ContainerStorageModule(par1InventoryPlayer, batteryBox));
        this.tileEntity = batteryBox;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of
     * the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRendererObj.drawString(EnumColor.WHITE + this.tileEntity.getInventoryName(), this.xSize / 2 - this.fontRendererObj.getStringWidth(this.tileEntity.getInventoryName()) / 2, 4, 4210752);
        float energy = this.tileEntity.getEnergyStoredGC();
        if (energy + 49 > this.tileEntity.getMaxEnergyStoredGC())
        {
            energy = this.tileEntity.getMaxEnergyStoredGC();
        }
        String displayStr = EnumColor.YELLOW + EnergyDisplayHelper.getEnergyDisplayS(energy);
        this.fontRendererObj.drawString(displayStr, 122 - this.fontRendererObj.getStringWidth(displayStr) / 2, 25, 4210752);
        displayStr = EnumColor.WHITE + GCCoreUtil.translate("gui.message.of.name") + " " + EnergyDisplayHelper.getEnergyDisplayS(this.tileEntity.getMaxEnergyStoredGC());
        this.fontRendererObj.drawString(displayStr, 122 - this.fontRendererObj.getStringWidth(displayStr) / 2, 34, 4210752);
        displayStr = EnumColor.WHITE + GCCoreUtil.translate("gui.maxOutput.desc") + ": " + EnergyDisplayHelper.getEnergyDisplayS(this.tileEntity.storage.getMaxExtract()) + "/t";
        this.fontRendererObj.drawString(displayStr, 126 - this.fontRendererObj.getStringWidth(displayStr) / 2, 62, 4210752);
        this.fontRendererObj.drawString(EnumColor.WHITE + GCCoreUtil.translate("container.inventory"), 14, this.ySize - 94 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the
     * items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        this.mc.renderEngine.bindTexture(GuiStorageModule.batteryBoxTexture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int containerWidth = (this.width - this.xSize) / 2;
        int containerHeight = (this.height - this.ySize) / 2;
        // Background energy bar
        this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);
        // Foreground energy bar
        int scale = (int) ((this.tileEntity.getEnergyStoredGC() + 49) / this.tileEntity.getMaxEnergyStoredGC() * 72);
        this.drawTexturedModalRect(containerWidth + 87, containerHeight + 52, 176, 0, scale, 5);
    }
}
