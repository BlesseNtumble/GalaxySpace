package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.core.GSItems;
import galaxyspace.core.client.gui.tile.GuiTileBase;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerGasCollector;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityGasCollector;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiGasCollector extends GuiTileBase
{
    private TileEntityGasCollector tileEntity;

    private GuiElementInfoRegion gasTankRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 28, 16, 38, new ArrayList<String>(), this.width, this.height, this);
    
    public GuiGasCollector(InventoryPlayer par1InventoryPlayer, TileEntityGasCollector tileEntity)
    {
        super(new ContainerGasCollector(par1InventoryPlayer, tileEntity), 2, 1);
        this.tileEntity = tileEntity;
        this.ySize = 204;
        moduleInfoX = this.inventorySlots.getSlotFromInventory(tileEntity, 3).xPos;
		moduleInfoY = this.inventorySlots.getSlotFromInventory(tileEntity, 3).yPos;
		moduleList = new ItemStack[] {new ItemStack(GSItems.UPGRADES, 1, 0)};
    }

    @Override
    public void initGui()
    {
        super.initGui();
        
        List<String> fuelTankDesc = new ArrayList<String>();
        int fuelLevel = this.tileEntity.gasTank != null && this.tileEntity.gasTank.getFluid() != null ? this.tileEntity.gasTank.getFluid().amount : 0;
        int fuelCapacity = this.tileEntity.gasTank != null ? this.tileEntity.gasTank.getCapacity() : 0;
        fuelTankDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.message.fuel.name") + ": " + fuelLevel + " / " + fuelCapacity);
        this.gasTankRegion.tooltipStrings = fuelTankDesc;
        this.gasTankRegion.xPosition = (this.width - this.xSize) / 2 + 80;
        this.gasTankRegion.yPosition = (this.height - this.ySize) / 2 + 20;
        this.gasTankRegion.parentWidth = this.width;
        this.gasTankRegion.parentHeight = this.height;
        this.infoRegions.add(this.gasTankRegion);
        
        List<String> desc = new ArrayList<String>();
        desc = new ArrayList<String>();
        desc.add(EnumColor.BRIGHT_GREEN + GCCoreUtil.translate("gui.available_modules.desc"));
        desc.add("");
        desc.add("- " + new ItemStack(GSItems.UPGRADES, 1, 0).getDisplayName());
        desc.add("- " + new ItemStack(GSItems.UPGRADES, 1, 3).getDisplayName());
        this.infoRegions.add(new GuiElementInfoRegion((this.width + this.xSize) / 2, (this.height - this.ySize) / 2 + 16, 18, 21 * 4, desc, this.width, this.height, this));
     
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	super.drawGuiContainerForegroundLayer(par1, par2);
    	
     
        String displayText;

        if (this.tileEntity.hasEnoughEnergyToRun && this.tileEntity.canProcess())
        {
            displayText = EnumColor.BRIGHT_GREEN + GCCoreUtil.translate("gui.status.running.name");
        }
        else
        {
            displayText = EnumColor.ORANGE + GCCoreUtil.translate("gui.status.idle.name");
        }

        String str = EnumColor.WHITE + GCCoreUtil.translate("gui.message.status.name") + ": " + displayText;
        this.fontRenderer.drawString(str, 128 - this.fontRenderer.getStringWidth(str) / 2, 104, 4210752);

    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the
     * items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
    	super.drawGuiContainerBackgroundLayer(par1, par2, par3);
    	
       // this.mc.renderEngine.bindTexture(GuiGasCollector.guiTexture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int containerWidth = (this.width - this.xSize) / 2;
        int containerHeight = (this.height - this.ySize) / 2;
        //this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize); // Base Gui
        
        
 /*       int scale;

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
            scale = 100;
        }
        /*this.drawTexturedModalRect(containerWidth + 45, containerHeight + 36, 230, 60+26, 24, 26);
        this.drawTexturedModalRect(containerWidth + 45, containerHeight + 56, 230, 60+26, 24, 26);

        this.drawTexturedModalRect(containerWidth + 105, containerHeight + 36, 230, 60+26, 24, 26);
        this.drawTexturedModalRect(containerWidth + 105, containerHeight + 56, 230, 60+26, 24, 26);
*/
       
        
        //Energy
        this.renderEnergyBar(containerWidth + 7, containerHeight + 102, this.tileEntity.getScaledElecticalLevel(55), this.tileEntity.getEnergyStoredGC(), this.tileEntity.getMaxEnergyStoredGC()); 
        
 
        //FluidTank
        this.drawTexturedModalRect(containerWidth + this.xSize / 2 - 19, containerHeight + 18, 180, 67, 20, 42);
        
        FluidStack fluid = this.tileEntity.gasTank.getFluid();      
        
        if(fluid != null)        
	        GSUtils.displayGauge(containerWidth + this.xSize / 2 - 18, containerHeight + 0, tileEntity.getScaledTankLevel(38), tileEntity.gasTank.getFluid(), 0);
	        
        this.mc.renderEngine.bindTexture(getTexture());
		this.drawTexturedModalRect(containerWidth + this.xSize / 2 - 19, containerHeight + 18, 200, 67, 20, 42);
		
        
        //Info FLuidTank
        List<String> fuelTankDesc = new ArrayList<String>();
        int fuelLevel = this.tileEntity.gasTank != null && this.tileEntity.gasTank.getFluid() != null ? this.tileEntity.gasTank.getFluid().amount : 0;
        int fuelCapacity = this.tileEntity.gasTank != null ? this.tileEntity.gasTank.getCapacity() : 0;
        if(fluid != null) fuelTankDesc.add(EnumColor.YELLOW + this.tileEntity.gasTank.getFluid().getLocalizedName() + ": " + fuelLevel + " / " + fuelCapacity);       
        this.gasTankRegion.tooltipStrings = fuelTankDesc;
       
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
