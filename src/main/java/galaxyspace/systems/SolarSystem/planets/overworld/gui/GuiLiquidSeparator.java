package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.core.client.gui.tile.GuiTileBase;
import galaxyspace.core.network.packet.GSPacketSimple;
import galaxyspace.core.network.packet.GSPacketSimple.GSEnumSimplePacket;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerLiquidSeparator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityLiquidSeparator;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiLiquidSeparator extends GuiTileBase
{
    private TileEntityLiquidSeparator tileEntity;
  
    private GuiElementInfoRegion baseTankRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 28, 16, 38, new ArrayList<String>(), this.width, this.height, this);
    
    private GuiElementInfoRegion water1TankRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 28, 16, 38, new ArrayList<String>(), this.width, this.height, this);
    private GuiElementInfoRegion water2TankRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 28, 16, 38, new ArrayList<String>(), this.width, this.height, this);
    
    private GuiButton reverseButton;
    public final int BTN_REVERSE = 8;
        
    public GuiLiquidSeparator(InventoryPlayer par1InventoryPlayer, TileEntityLiquidSeparator tileEntity)
    {
        super(new ContainerLiquidSeparator(par1InventoryPlayer, tileEntity), 3, 0);
        this.tileEntity = tileEntity;
        this.ySize = 205;      
        moduleInfoX = this.inventorySlots.getSlotFromInventory(tileEntity, 4).xPos;
      	moduleInfoY = this.inventorySlots.getSlotFromInventory(tileEntity, 4).yPos;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        

        
        List<String> baseTankDesc = new ArrayList<String>();
        int baseLevel = this.tileEntity.baseTank != null && this.tileEntity.baseTank.getFluid() != null ? this.tileEntity.baseTank.getFluid().amount : 0;
        int baseCapacity = this.tileEntity.baseTank != null ? this.tileEntity.baseTank.getCapacity() : 0;
        baseTankDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.message.fuel.name") + ": " + baseLevel + " / " + baseCapacity);
        this.baseTankRegion.tooltipStrings = baseTankDesc;
        this.baseTankRegion.xPosition = (this.width / 2 ) - 9;
        this.baseTankRegion.yPosition = (this.height - this.ySize) / 2 + 40;
        this.baseTankRegion.parentWidth = this.width;
        this.baseTankRegion.parentHeight = this.height;
        this.infoRegions.add(this.baseTankRegion);
        
        baseTankDesc = new ArrayList<String>();
        int fuelLevel = this.tileEntity.waterTank1 != null && this.tileEntity.waterTank1.getFluid() != null ? this.tileEntity.waterTank1.getFluid().amount : 0;
        int fuelCapacity = this.tileEntity.waterTank1 != null ? this.tileEntity.waterTank1.getCapacity() : 0;
        baseTankDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.message.fuel.name") + ": " + fuelLevel + " / " + fuelCapacity);
        this.water1TankRegion.tooltipStrings = baseTankDesc;
        this.water1TankRegion.xPosition = (this.width / 2 + this.xSize / 2) - 30;
        this.water1TankRegion.yPosition = (this.height - this.ySize) / 2 + 40;
        this.water1TankRegion.parentWidth = this.width;
        this.water1TankRegion.parentHeight = this.height;
        this.infoRegions.add(this.water1TankRegion);
        
        baseTankDesc = new ArrayList<String>();
        int fuel1Level = this.tileEntity.waterTank2 != null && this.tileEntity.waterTank2.getFluid() != null ? this.tileEntity.waterTank2.getFluid().amount : 0;
        int fuel1Capacity = this.tileEntity.waterTank2 != null ? this.tileEntity.waterTank2.getCapacity() : 0;
        baseTankDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.message.fuel.name") + ": " + fuel1Level + " / " + fuel1Capacity);
        this.water2TankRegion.tooltipStrings = baseTankDesc;
        this.water2TankRegion.xPosition = (this.width / 2) - 78;
        this.water2TankRegion.yPosition = (this.height - this.ySize) / 2 + 40;
        this.water2TankRegion.parentWidth = this.width;
        this.water2TankRegion.parentHeight = this.height;
        this.infoRegions.add(this.water2TankRegion);
        
        
        this.buttonList.clear();
        final int var5 = (this.width - this.xSize) / 2;
        final int var6 = (this.height - this.ySize) / 2;
        
        reverseButton   = new GuiButton(BTN_REVERSE, var5 + 110, var6 + 65, 20, 20, GCCoreUtil.translate("gui.button.disable.name"));

        this.buttonList.add(reverseButton);

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	super.drawGuiContainerForegroundLayer(par1, par2);
    	String displayText;
        
        reverseButton.displayString = this.tileEntity.getReverse() ?  "R-" : "R+";

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
        this.fontRenderer.drawString(str, 128 - this.fontRenderer.getStringWidth(str) / 2, 95, 4210752);
 
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
       

        //Process Bar

        this.renderProgressArray(containerWidth + (!this.tileEntity.getReverse() ? 105 : 45), containerHeight + 44, 20, this.tileEntity.processTicks, this.tileEntity.processTimeRequired);
        this.renderProgressArray(containerWidth + (!this.tileEntity.getReverse() ? 45 : 108), containerHeight + 44, 20, this.tileEntity.processTicks, this.tileEntity.processTimeRequired, true, false);
      
        
        
        //Energy
        this.renderEnergyBar(containerWidth + 6, containerHeight + 95, this.tileEntity.getScaledElecticalLevel(55), this.tileEntity.getEnergyStoredGC(), this.tileEntity.getMaxEnergyStoredGC());
        
        
 
        //FluidTank
               
        this.drawTexturedModalRect(containerWidth + this.xSize / 2 - 20, containerHeight + 38, 180, 67, 20, 42);	
        this.drawTexturedModalRect(containerWidth + this.xSize - 33 - 19, containerHeight + 38, 180, 67, 20, 42);	
        this.drawTexturedModalRect(containerWidth + 9, containerHeight + 38, 180, 67, 20, 42);	
        
        FluidStack fluid = this.tileEntity.baseTank.getFluid();        
        FluidStack fluid1 = this.tileEntity.waterTank1.getFluid();     
        FluidStack fluid2 = this.tileEntity.waterTank2.getFluid();   
        
	    GSUtils.displayGauge(containerWidth + this.xSize / 2 - 8, containerHeight + 20, tileEntity.getScaledTankLevel(tileEntity.baseTank, 38), tileEntity.baseTank.getFluid(), 0);
	    GSUtils.displayGauge(containerWidth + this.xSize - 31, containerHeight + 20, tileEntity.getScaledTankLevel(tileEntity.waterTank1, 38), tileEntity.waterTank1.getFluid(), 0);
		GSUtils.displayGauge(containerWidth + 10, containerHeight + 20, tileEntity.getScaledTankLevel(tileEntity.waterTank2, 38), tileEntity.waterTank2.getFluid(), 0);
			
        
        this.mc.renderEngine.bindTexture(this.getTexture());
        drawTexturedModalRect(containerWidth + this.xSize / 2 - 20, containerHeight + 38, 200, 67, 16, 42);
        drawTexturedModalRect(containerWidth + this.xSize - 33 - 19, containerHeight + 38, 200, 67, 16, 42);
        drawTexturedModalRect(containerWidth + 9, containerHeight + 38, 200, 67, 16, 42);
        
		
        
        //Info FLuidTank
		List<String> baseTankDesc = new ArrayList<String>();
        int baseLevel = this.tileEntity.baseTank != null && this.tileEntity.baseTank.getFluid() != null ? this.tileEntity.baseTank.getFluid().amount : 0;
        int baseCapacity = this.tileEntity.baseTank != null ? this.tileEntity.baseTank.getCapacity() : 0;
        if(fluid != null) baseTankDesc.add(EnumColor.YELLOW + this.tileEntity.baseTank.getFluid().getLocalizedName() + ": " + baseLevel + " / " + baseCapacity);       
        this.baseTankRegion.tooltipStrings = baseTankDesc;
        
        List<String> fuelTankDesc = new ArrayList<String>();
        int fuelLevel = this.tileEntity.waterTank1 != null && this.tileEntity.waterTank1.getFluid() != null ? this.tileEntity.waterTank1.getFluid().amount : 0;
        int fuelCapacity = this.tileEntity.waterTank1 != null ? this.tileEntity.waterTank1.getCapacity() : 0;
        if(fluid1 != null) fuelTankDesc.add(EnumColor.YELLOW + this.tileEntity.waterTank1.getFluid().getLocalizedName() + ": " + fuelLevel + " / " + fuelCapacity);       
        this.water1TankRegion.tooltipStrings = fuelTankDesc;
        
        List<String> fuel1TankDesc = new ArrayList<String>();
        int fuel1Level = this.tileEntity.waterTank2 != null && this.tileEntity.waterTank2.getFluid() != null ? this.tileEntity.waterTank2.getFluid().amount : 0;
        int fuel1Capacity = this.tileEntity.waterTank2 != null ? this.tileEntity.waterTank2.getCapacity() : 0;
        if(fluid2 != null) fuel1TankDesc.add(EnumColor.YELLOW + this.tileEntity.waterTank2.getFluid().getLocalizedName() + ": " + fuel1Level + " / " + fuel1Capacity);       
        this.water2TankRegion.tooltipStrings = fuel1TankDesc;
      
        if(GalaxySpace.debug) GSUtils.renderDebugGui(this, containerWidth, containerHeight);
    }
    
    @Override
    protected void actionPerformed(GuiButton btn)
    {
        switch(btn.id) {
        case BTN_REVERSE:
            GalaxySpace.packetPipeline.sendToServer(new GSPacketSimple(GSEnumSimplePacket.S_REVERSE_SEPATATOR, GCCoreUtil.getDimensionID(this.mc.world), new Object[] { new BlockVec3(this.tileEntity.getPos().getX(), this.tileEntity.getPos().getY(), this.tileEntity.getPos().getZ()) }));
            break;
        }
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
