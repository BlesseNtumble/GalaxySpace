package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.core.client.gui.tile.GuiTileBase;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerFuelGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityFuelGenerator;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiFuelGenerator extends GuiTileBase
{
	private GuiElementInfoRegion fuelTankRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 28, 16, 38, new ArrayList<String>(), this.width, this.height, this);
    private TileEntityFuelGenerator tileEntity;

    public GuiFuelGenerator(InventoryPlayer par1InventoryPlayer, TileEntityFuelGenerator tileEntity)
    {
        super(new ContainerFuelGenerator(par1InventoryPlayer, tileEntity), 2, 1);
        this.tileEntity = tileEntity;
        this.ySize = 170;
        this.header = 4;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        List<String> fuelTankDesc = new ArrayList<String>();
      
        this.fuelTankRegion.tooltipStrings = fuelTankDesc;
        this.fuelTankRegion.xPosition = (this.width - this.xSize) / 2 + 16;
        this.fuelTankRegion.yPosition = (this.height - this.ySize) / 2 + 35;
        this.fuelTankRegion.parentWidth = this.width;
        this.fuelTankRegion.parentHeight = this.height;
        this.infoRegions.add(this.fuelTankRegion);
    }
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of
     * the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	super.drawGuiContainerForegroundLayer(par1, par2);
    	int yOffset = -18;
    	         
        String displayText = "";
     
        EnumColor standart_color = getStyle() == Style.CLASSIC ? EnumColor.DARK_GREY : EnumColor.WHITE;
        if (this.tileEntity.heatGJperTick <= 0)
        {
            displayText = standart_color + GCCoreUtil.translate("gui.status.not_generating.name");
        }
        else if(tileEntity.getEnergyStoredGC() == tileEntity.getMaxEnergyStoredGC()) {
        	 displayText = EnumColor.ORANGE + GCCoreUtil.translate("gui.status.energy_storage_is_full.name");
        }
        else
        {
            displayText = (getStyle() == Style.CLASSIC ? EnumColor.DARK_GREEN : EnumColor.BRIGHT_GREEN) + GCCoreUtil.translate("gui.status.generating.name") + " " +  EnergyDisplayHelper.getEnergyDisplayS(this.tileEntity.heatGJperTick - TileEntityFuelGenerator.MIN_GENERATE_GJ_PER_TICK) + "/t";
        }
        if (this.tileEntity.fuelTank.getFluid() == null && this.tileEntity.heatGJperTick <= 0)
        {
            displayText = EnumColor.RED + GCCoreUtil.translate("gui.status.nofuelgenerator.name");
        }
        this.fontRenderer.drawSplitString(standart_color + GCCoreUtil.translate("gui.message.status.name") + ": " + displayText, 72, 40 + yOffset, 100, 4210752);

        String displayStr = standart_color + GCCoreUtil.translate("gui.max_output.desc") + ": " + EnergyDisplayHelper.getEnergyDisplayS(this.tileEntity.storage.getMaxExtract()) + "/t";
        this.fontRenderer.drawString(displayStr, 116 - this.fontRenderer.getStringWidth(displayStr) / 2, 72, 4210752);
       
        
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
        
        FluidStack fuel = this.tileEntity.fuelTank.getFluid();
        
        this.renderProgressArray(containerWidth + 45, containerHeight + 40, 15, 0, 10);        
		this.drawTexturedModalRect(containerWidth + 15, containerHeight + 35, 180, 67, 20, 42);		
              
        if(fuel != null)       
        	GSUtils.displayGauge(containerWidth + 16, containerHeight + 16, tileEntity.getScaledFuelLevel(38), tileEntity.fuelTank.getFluid(), 0);

        this.mc.renderEngine.bindTexture(getTexture());
        drawTexturedModalRect(containerWidth + 15, containerHeight + 35, 200, 67, 16, 42);
               
        
        List<String> fuelTankDesc = new ArrayList<String>();
        int fuelLevel = this.tileEntity.fuelTank != null && this.tileEntity.fuelTank.getFluid() != null ? this.tileEntity.fuelTank.getFluid().amount : 0;
        int fuelCapacity = this.tileEntity.fuelTank != null ? this.tileEntity.fuelTank.getCapacity() : 0;
        if(fuel != null) 
        {
        	 if(this.tileEntity.fuelTank.getFluid() != null) 
             	fuelTankDesc.add(EnumColor.YELLOW + this.tileEntity.fuelTank.getFluid().getLocalizedName() + ": " + fuelLevel + " / " + fuelCapacity);
        	 this.fuelTankRegion.tooltipStrings = fuelTankDesc;
            
        }
        
        this.renderEnergyBar(containerWidth + 80, containerHeight + 43, (int) Math.floor(tileEntity.getEnergyStoredGC() * 54 / tileEntity.getMaxEnergyStoredGC()), tileEntity.getEnergyStoredGC(), tileEntity.getMaxEnergyStoredGC());
        
        

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
