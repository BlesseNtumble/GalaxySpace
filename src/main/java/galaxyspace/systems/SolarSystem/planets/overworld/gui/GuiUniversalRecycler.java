package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.core.client.gui.tile.GuiTileBase;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerUniversalRecycler;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityUniversalRecycler;
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
public class GuiUniversalRecycler extends GuiTileBase
{
    private TileEntityUniversalRecycler tileEntity;
    private GuiElementInfoRegion fuelTankRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 28, 16, 38, new ArrayList<String>(), this.width, this.height, this);
    
    public GuiUniversalRecycler(InventoryPlayer par1InventoryPlayer, TileEntityUniversalRecycler tileEntity)
    {
        super(new ContainerUniversalRecycler(par1InventoryPlayer, tileEntity), 2, 1);
        this.tileEntity = tileEntity;
        this.ySize = 204;
        this.header = 0;
        moduleInfoX = this.inventorySlots.getSlotFromInventory(tileEntity, 5).xPos;
		moduleInfoY = this.inventorySlots.getSlotFromInventory(tileEntity, 5).yPos;
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
        this.fuelTankRegion.xPosition = (this.width - this.xSize) / 2 + 148;
        this.fuelTankRegion.yPosition = (this.height - this.ySize) / 2 + 40;
        this.fuelTankRegion.parentWidth = this.width;
        this.fuelTankRegion.parentHeight = this.height;
        this.infoRegions.add(this.fuelTankRegion);
       /* 
        List<String> water1TankDesc = new ArrayList<String>();
        int water1Level = this.tileEntity.waterTank1 != null && this.tileEntity.waterTank1.getFluid() != null ? this.tileEntity.waterTank1.getFluid().amount : 0;
        int water1Capacity = this.tileEntity.waterTank1 != null ? this.tileEntity.waterTank1.getCapacity() : 0;
        water1TankDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.message.fuel.name") + ": " + water1Level + " / " + water1Capacity);
        this.waterTankRegion.tooltipStrings = water1TankDesc;
        this.waterTankRegion.xPosition = (this.width - this.xSize) - 226;
        this.waterTankRegion.yPosition = (this.height - this.ySize) / 2 + 40;
        this.waterTankRegion.parentWidth = this.width;
        this.waterTankRegion.parentHeight = this.height;
        this.infoRegions.add(this.waterTankRegion);*/        
        
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
        this.fontRenderer.drawSplitString(str, 105, 95, 80, 4210752);
              
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
                
        int scale;
   
        if (this.tileEntity.processTicks > 0)
        {
            scale = (int) ((double) this.tileEntity.processTicks / (double) this.tileEntity.processTimeRequired * 100);
        }
        else
        {
            scale = 0;
        }

        //Process Bar

        this.renderProgressArray(containerWidth + 81, containerHeight + 44, 10, this.tileEntity.processTicks, this.tileEntity.processTimeRequired);
        //this.drawTexturedModalRect(containerWidth + 55, containerHeight + 36, 230, 60, 24, 26);
        
        //this.drawTexturedModalRect(containerWidth + 84, containerHeight + 44, 192 + 20, 108, 16, 17);
        for(int i = 0; i < 8 ; i++) 
        {
        	this.drawTexturedModalRect(containerWidth + 44 + i, containerHeight + 49, 203, 114, 4, 4);        	
        	this.drawTexturedModalRect(containerWidth + 52 + (i * 4), containerHeight + 66, 203, 114, 4, 4);
        
        	this.drawTexturedModalRect(containerWidth + 52, containerHeight + 52 + (i * 2), 203, 114, 3, 4);
        	this.drawTexturedModalRect(containerWidth + 81, containerHeight + 50 + (i * 2), 203, 114, 3, 4);
        }  
        
        if (this.tileEntity.processTicks > 0)
        {
            scale = (int) ((double) this.tileEntity.processTicks / (double) this.tileEntity.processTimeRequired * 16);
            //this.drawTexturedModalRect(containerWidth + 84, containerHeight + 44, 192 + 20, 124, scale, 17);
       
            
            for(int i = 0; i < 8 ; i++) 
            {
            	this.drawTexturedModalRect(containerWidth + 44 + i, containerHeight + 49, 203, 130, 4, 5);        	
            	this.drawTexturedModalRect(containerWidth + 52 + (i * 4), containerHeight + 66, 203, 130, 4, 5);
            
            	this.drawTexturedModalRect(containerWidth + 52, containerHeight + 51 + (i * 2), 203, 130, 3, 4);
            	this.drawTexturedModalRect(containerWidth + 81, containerHeight + 50 + (i * 2), 203, 130, 3, 4);
            }  
            
            /*if (this.tileEntity.processTicks < this.tileEntity.processTimeRequired / 2)
            {*/
                //this.drawTexturedModalRect(containerWidth + 55, containerHeight + 39, 230, 86, 24, 26);
            //}           
        }

        
        //Energy
        this.renderEnergyBar(containerWidth + 6, containerHeight + 95, this.tileEntity.getScaledElecticalLevel(55), this.tileEntity.getEnergyStoredGC(), this.tileEntity.getMaxEnergyStoredGC());
      
 
        //FluidTank
        this.drawTexturedModalRect(containerWidth + this.xSize - 49, containerHeight + 38, 180, 67, 20, 42);	

        
        FluidStack fluid = this.tileEntity.waterTank.getFluid();        
        //FluidStack fluid1 = this.tileEntity.waterTank1.getFluid();  
       
        GSUtils.displayGauge(containerWidth + this.xSize - 48, containerHeight + 19, tileEntity.getScaledTankLevel(38), tileEntity.waterTank.getFluid(), 0);


        this.mc.renderEngine.bindTexture(this.getTexture());
        drawTexturedModalRect(containerWidth + this.xSize - 49, containerHeight + 38, 200, 67, 16, 42);
        //this.drawTexturedModalRect(containerWidth + 4, containerHeight + 38, 192+20, 66, 20, 42);
        
        //Info FLuidTank
        List<String> fuelTankDesc = new ArrayList<String>();
        int fuelLevel = this.tileEntity.waterTank != null && this.tileEntity.waterTank.getFluid() != null ? this.tileEntity.waterTank.getFluid().amount : 0;
        int fuelCapacity = this.tileEntity.waterTank != null ? this.tileEntity.waterTank.getCapacity() : 0;
        if(fluid != null) fuelTankDesc.add(EnumColor.YELLOW + fluid.getLocalizedName() + ": " + fuelLevel + " / " + fuelCapacity);       
        this.fuelTankRegion.tooltipStrings = fuelTankDesc;
        /*
        List<String> water1TankDesc = new ArrayList<String>();
        int water1Level = this.tileEntity.waterTank1 != null && this.tileEntity.waterTank1.getFluid() != null ? this.tileEntity.waterTank1.getFluid().amount : 0;
        int water1Capacity = this.tileEntity.waterTank1 != null ? this.tileEntity.waterTank1.getCapacity() : 0;
        if(fluid1 != null) water1TankDesc.add(EnumColor.YELLOW + fluid1.getLocalizedName() + ": " + water1Level + " / " + water1Capacity);       
        this.waterTankRegion.tooltipStrings = water1TankDesc;       
        */
        
       /* RecycleRecipe recipe = RecyclerRecipes.recycling().getRecipe(this.tileEntity.getStackInSlot(1));

        if(recipe != null)
        {
        	this.mc.fontRenderer.drawString("Chance: " + recipe.getChance() + "%", containerWidth + 75, containerHeight + 29, 0xFFFFFF);
        }*/
        
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
