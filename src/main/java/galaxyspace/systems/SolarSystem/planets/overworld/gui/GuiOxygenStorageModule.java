package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.core.client.gui.tile.GuiTileBase;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerOxygenStorageModule;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityAdvOxygenStorageModule;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiOxygenStorageModule extends GuiTileBase
{

    private TileEntityAdvOxygenStorageModule tileEntity;

    public GuiOxygenStorageModule(InventoryPlayer par1InventoryPlayer, TileEntityAdvOxygenStorageModule storageModule)
    {
        super(new ContainerOxygenStorageModule(par1InventoryPlayer, storageModule), 2, 1);
        this.tileEntity = storageModule;
        this.header = 0;
    }

    @Override
    public void initGui()	
    {
        super.initGui();
        List<String> oxygenSlotDesc = new ArrayList<String>();
        oxygenSlotDesc.add(GCCoreUtil.translate("gui.oxygen_slot.desc.0"));
        oxygenSlotDesc.add(GCCoreUtil.translate("gui.oxygen_slot.desc.1"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 16, (this.height - this.ySize) / 2 + 21, 18, 18, oxygenSlotDesc, this.width, this.height, this));
    }
    
/**
     * Draw the foreground layer for the GuiContainer (everything in front of
     * the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	super.drawGuiContainerForegroundLayer(par1, par2);
    	
    	 String displayJoules = (int) (this.tileEntity.getOxygenStored() + 0.5F) + " " + GCCoreUtil.translate("gui.message.of.name");
        String displayMaxJoules = "" + (int) this.tileEntity.getMaxOxygenStored();
        String maxOutputLabel = GCCoreUtil.translate("gui.max_output.desc") + ": " + this.tileEntity.OUTPUT_PER_TICK * 20 + GCCoreUtil.translate("gui.per_second");

        TextFormatting color = getStyle() == Style.MODERN ? TextFormatting.WHITE : TextFormatting.DARK_GRAY;
        
        this.fontRenderer.drawString(EnumColor.YELLOW + displayJoules, 122 - this.fontRenderer.getStringWidth(displayJoules) / 2 - 35, 30, 4210752);
        this.fontRenderer.drawString(color + displayMaxJoules, 122 - this.fontRenderer.getStringWidth(displayMaxJoules) / 2 - 35, 40, 4210752);
        this.fontRenderer.drawString(color + maxOutputLabel, 122 - this.fontRenderer.getStringWidth(maxOutputLabel) / 2 - 35, 60, 4210752);
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
        // Background energy bar
        this.drawTexturedModalRect(containerWidth + 52, containerHeight + 52, 180, 226, 72, 5);
        // Foreground energy bar
        int scale = (int) ((double) this.tileEntity.getOxygenStored() / (double) this.tileEntity.getMaxOxygenStored() * 72);
        this.drawTexturedModalRect(containerWidth + 53, containerHeight + 53, 180, 222, scale, 3);
    
        if(GalaxySpace.debug) GSUtils.renderDebugGui(this, containerWidth, containerHeight);
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
