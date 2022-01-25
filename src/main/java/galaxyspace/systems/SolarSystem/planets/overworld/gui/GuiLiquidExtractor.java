package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.core.client.gui.tile.GuiTileBase;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerLiquidExtractor;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityLiquidExtractor;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiLiquidExtractor extends GuiTileBase
{
    private TileEntityLiquidExtractor tileEntity;
    private GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion(0, 0, 56, 9, null, 0, 0, this);
    private GuiElementInfoRegion processInfoRegion = new GuiElementInfoRegion(0, 0, 52, 25, null, 0, 0, this);
    private GuiElementInfoRegion fuelTankRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 28, 16, 38, new ArrayList<String>(), this.width, this.height, this);
    
    public GuiLiquidExtractor(InventoryPlayer par1InventoryPlayer, TileEntityLiquidExtractor tileEntity)
    {
        super(new ContainerLiquidExtractor(par1InventoryPlayer, tileEntity), 2, 1);
        this.tileEntity = tileEntity;
        this.ySize = 190;
        moduleInfoX = this.inventorySlots.getSlotFromInventory(tileEntity, 3).xPos;
		moduleInfoY = this.inventorySlots.getSlotFromInventory(tileEntity, 3).yPos;
    }

    @Override
    public void initGui()
    {
        super.initGui();
       
        
        List<String> fuelTankDesc = new ArrayList<String>();
        int fuelLevel = this.tileEntity.waterTank != null && this.tileEntity.waterTank.getFluid() != null ? this.tileEntity.waterTank.getFluid().amount : 0;
        int fuelCapacity = this.tileEntity.waterTank != null ? this.tileEntity.waterTank.getCapacity() : 0;
        fuelTankDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.message.fuel.name") + ": " + fuelLevel + " / " + fuelCapacity);
        this.fuelTankRegion.tooltipStrings = fuelTankDesc;
        this.fuelTankRegion.xPosition = (this.width - this.xSize) / 2 + 80;
        this.fuelTankRegion.yPosition = (this.height - this.ySize) / 2 + 20;
        this.fuelTankRegion.parentWidth = this.width;
        this.fuelTankRegion.parentHeight = this.height;
        this.infoRegions.add(this.fuelTankRegion);        
      
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
        this.fontRenderer.drawString(str, 135 - this.fontRenderer.getStringWidth(str) / 2, 84, 4210752);

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
               

       
       this.drawTexturedModalRect(containerWidth + 45, containerHeight + 36, 221, 87, 22, 22);
       this.drawTexturedModalRect(containerWidth + 45, containerHeight + 56, 221, 87, 22, 22);
    
       this.drawTexturedModalRect(containerWidth + 105, containerHeight + 36, 221, 87, 22, 22);
       this.drawTexturedModalRect(containerWidth + 105, containerHeight + 56, 221, 87, 22, 22);
       

       // this.drawTexturedModalRect(containerWidth + 105, containerHeight + 36, 230, 60+26, 24, 26);
       // this.drawTexturedModalRect(containerWidth + 105, containerHeight + 56, 230, 60+26, 24, 26);

       
        
        //Energy
        this.renderEnergyBar(containerWidth + 30, containerHeight + 84, this.tileEntity.getScaledElecticalLevel(55), this.tileEntity.getEnergyStoredGC(), this.tileEntity.getMaxEnergyStoredGC());
                    
 
        //FluidTank
        this.drawTexturedModalRect(containerWidth + this.xSize / 2 - 20, containerHeight + 18, 180, 67, 20, 42);	
        
        FluidStack fluid = this.tileEntity.waterTank.getFluid();        
        if(fluid != null)       
	        GSUtils.displayGauge(containerWidth + this.xSize / 2 - 19, containerHeight - 1, tileEntity.getScaledTankLevel(38), tileEntity.waterTank.getFluid(), 0);
		        
        this.mc.renderEngine.bindTexture(this.getTexture());
		this.drawTexturedModalRect(containerWidth + this.xSize / 2 - 20, containerHeight + 18, 200, 67, 16, 42);
        
        //Info FLuidTank
        List<String> fuelTankDesc = new ArrayList<String>();
        int fuelLevel = this.tileEntity.waterTank != null && this.tileEntity.waterTank.getFluid() != null ? this.tileEntity.waterTank.getFluid().amount : 0;
        int fuelCapacity = this.tileEntity.waterTank != null ? this.tileEntity.waterTank.getCapacity() : 0;
        if(fluid != null) fuelTankDesc.add(EnumColor.YELLOW + this.tileEntity.waterTank.getFluid().getLocalizedName() + ": " + fuelLevel + " / " + fuelCapacity);       
        this.fuelTankRegion.tooltipStrings = fuelTankDesc;
       
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
