package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import org.lwjgl.opengl.GL11;

import galaxyspace.core.client.gui.tile.GuiTileBase;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerModernStorageModule;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityModernStorageModule;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiModernStorageModule extends GuiTileBase
{
   
    private TileEntityModernStorageModule tileEntity;

    public GuiModernStorageModule(InventoryPlayer par1InventoryPlayer, TileEntityModernStorageModule batteryBox)
    {
        super(new ContainerModernStorageModule(par1InventoryPlayer, batteryBox), 2, 1);
        this.tileEntity = batteryBox;
        this.header = 3;
    }

    @Override
    public void initGui()
    {
        super.initGui();
    }
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of
     * the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	super.drawGuiContainerForegroundLayer(par1, par2);
       
    	float energy = this.tileEntity.getEnergyStoredGC();
        if (energy + 49 > this.tileEntity.getMaxEnergyStoredGC())
        {
            energy = this.tileEntity.getMaxEnergyStoredGC();
        }
        TextFormatting color = getStyle() == Style.MODERN ? TextFormatting.WHITE : TextFormatting.DARK_GRAY;
        
        String displayStr = EnumColor.YELLOW + EnergyDisplayHelper.getEnergyDisplayS(energy);
        this.fontRenderer.drawString(displayStr, 112 - this.fontRenderer.getStringWidth(displayStr) / 2, 25, 4210752);
        displayStr = color + GCCoreUtil.translate("gui.message.of.name") + " " + EnergyDisplayHelper.getEnergyDisplayS(this.tileEntity.getMaxEnergyStoredGC());
        this.fontRenderer.drawString(displayStr, 112 - this.fontRenderer.getStringWidth(displayStr) / 2, 34, 4210752);
        displayStr = color + GCCoreUtil.translate("gui.max_output.desc") + ": " + EnergyDisplayHelper.getEnergyDisplayS(this.tileEntity.storage.getMaxExtract()) + "/t";
        this.fontRenderer.drawString(displayStr, 116 - this.fontRenderer.getStringWidth(displayStr) / 2, 62, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the
     * items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int containerWidth = (this.width - this.xSize) / 2;
        int containerHeight = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(containerWidth + 82, containerHeight + 46, 180, 47, 56, 9);
        
        int scale = (int) ((this.tileEntity.getEnergyStoredGC()) / this.tileEntity.getMaxEnergyStoredGC() * 56);
        this.drawTexturedModalRect(containerWidth + 83, containerHeight + 47, 180, 0, scale, 7);
        
        
    }

	@Override
	protected boolean isModuleSupport() {
		return false;
	}

	@Override
	protected String getName() {
		return tileEntity.getName();
	}

	@Override
	protected Slot getBatterySlot() {
		return null;
	}
}
