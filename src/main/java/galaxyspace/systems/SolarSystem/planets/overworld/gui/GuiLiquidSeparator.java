package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.core.network.packet.GSPacketSimple;
import galaxyspace.core.network.packet.GSPacketSimple.GSEnumSimplePacket;
import galaxyspace.core.prefab.inventory.SlotUpgrades;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerLiquidSeparator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityLiquidSeparator;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiLiquidSeparator extends GuiContainerGC
{
    private static final ResourceLocation guiTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/base_gui.png");
    private TileEntityLiquidSeparator tileEntity;
    private GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion(0, 0, 56, 9, null, 0, 0, this);
    private GuiElementInfoRegion processInfoRegion = new GuiElementInfoRegion(0, 0, 52, 25, null, 0, 0, this);
   
    private GuiElementInfoRegion baseTankRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 28, 16, 38, new ArrayList<String>(), this.width, this.height, this);
    
    private GuiElementInfoRegion water1TankRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 28, 16, 38, new ArrayList<String>(), this.width, this.height, this);
    private GuiElementInfoRegion water2TankRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 28, 16, 38, new ArrayList<String>(), this.width, this.height, this);
    
    private GuiButton reverseButton;
    public final int BTN_REVERSE = 8;
        
    public GuiLiquidSeparator(InventoryPlayer par1InventoryPlayer, TileEntityLiquidSeparator tileEntity)
    {
        super(new ContainerLiquidSeparator(par1InventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
        this.ySize = 204;        
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.electricInfoRegion.tooltipStrings = new ArrayList<String>();
        this.electricInfoRegion.xPosition = (this.width - this.xSize) / 2 + 17;
        this.electricInfoRegion.yPosition = (this.height - this.ySize) / 2 + 102;
        this.electricInfoRegion.parentWidth = this.width;
        this.electricInfoRegion.parentHeight = this.height;
        this.infoRegions.add(this.electricInfoRegion);
        List<String> desc = new ArrayList<String>();
        desc.add(GCCoreUtil.translate("gui.battery_slot.desc.0"));
        desc.add(GCCoreUtil.translate("gui.battery_slot.desc.1"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 77, (this.height - this.ySize) / 2 + 93, 18, 18, desc, this.width, this.height, this));
        this.processInfoRegion.tooltipStrings = new ArrayList<String>();
        this.processInfoRegion.xPosition = (this.width - this.xSize) / 2 + 77;
        this.processInfoRegion.yPosition = (this.height - this.ySize) / 2 + 30;
        this.processInfoRegion.parentWidth = this.width;
        this.processInfoRegion.parentHeight = this.height;
        this.infoRegions.add(this.processInfoRegion);
        
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
    
        desc = new ArrayList<String>();
        desc.add(EnumColor.BRIGHT_GREEN + GCCoreUtil.translate("gui.available_modules.desc"));
        desc.add("");
        desc.add("- " + new ItemStack(GSItems.UPGRADES, 1, 2).getDisplayName());
        desc.add("- " + new ItemStack(GSItems.UPGRADES, 1, 3).getDisplayName());
        this.infoRegions.add(new GuiElementInfoRegion((this.width + this.xSize) / 2, (this.height - this.ySize) / 2 + 16, 18, 21 * 4, desc, this.width, this.height, this));
     
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	
        this.fontRenderer.drawString(EnumColor.WHITE + this.tileEntity.getName(), 86 - (this.fontRenderer.getStringWidth(this.tileEntity.getName()) / 2), 1, 4210752);
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

        String str = EnumColor.WHITE + GCCoreUtil.translate("gui.message.status.name") + ": " + displayText;
        this.fontRenderer.drawString(str, 128 - this.fontRenderer.getStringWidth(str) / 2, 104, 4210752);
        this.fontRenderer.drawString(EnumColor.WHITE + GCCoreUtil.translate("container.inventory"), 15, this.ySize - 88, 4210752);
//		str = "" + this.tileEntity.storage.getMaxExtract();
//		this.fontRendererObj.drawString(str, 120 - this.fontRendererObj.getStringWidth(str) / 2, 85, 4210752);
//		//		str = ElectricityDisplay.getDisplay(this.tileEntity.getVoltage(), ElectricUnit.VOLTAGE);
//		this.fontRendererObj.drawString(str, 120 - this.fontRendererObj.getStringWidth(str) / 2, 95, 4210752);

    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the
     * items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {    	
        this.mc.renderEngine.bindTexture(GuiLiquidSeparator.guiTexture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int containerWidth = (this.width - this.xSize) / 2;
        int containerHeight = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize); // Base Gui
        
        
        int scale;

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

        //Process Bar
        List<String> processDesc = new ArrayList<String>();
        processDesc.clear();
        processDesc.add(GCCoreUtil.translate("gui.electric_compressor.desc.0") + ": " + (100 - scale) + "%");
        this.processInfoRegion.tooltipStrings = processDesc;
      
        this.drawTexturedModalRect(containerWidth + (!this.tileEntity.getReverse() ? 110 : 35), containerHeight + 44, 182 + 20, 108, 26, 17);
        this.drawTexturedModalRect(containerWidth + (!this.tileEntity.getReverse() ? 35 : 110), containerHeight + 44, 192, 141, 26, 16);
        
        
        if (this.tileEntity.processTicks > 0)
        {
            scale = (int) ((double) this.tileEntity.processTicks / (double) this.tileEntity.processTimeRequired * 26);
            this.drawTexturedModalRect(containerWidth + (!this.tileEntity.getReverse() ? 110 : 35), containerHeight + 44, 182 + 20, 124, 26, 17);
            this.drawTexturedModalRect(containerWidth + (!this.tileEntity.getReverse() ? 35 : 110), containerHeight + 44, 192, 158, 26, 16);
            
           
           /* if (this.tileEntity.processTicks < this.tileEntity.processTimeRequired / 2)
            {
                this.drawTexturedModalRect(containerWidth + 110, containerHeight + 19, 230, 86, 24, 26);
            }*/
        }

        
        
        //Energy
        this.drawTexturedModalRect(containerWidth + 16, containerHeight + 102, 192, 47, 56, 9);
        this.drawTexturedModalRect(containerWidth + 4, containerHeight + 102, 192, 56, 11, 10);
        if (this.tileEntity.getEnergyStoredGC() > 0)
        {
            scale = this.tileEntity.getScaledElecticalLevel(55);
            this.drawTexturedModalRect(containerWidth + 116 - 99, containerHeight + 103, 192, 0, scale, 7);
            this.drawTexturedModalRect(containerWidth + 3, containerHeight + 102, 192, 7, 11, 10);           
        }
        
        // Slots
        for(int i = 0; i < this.inventorySlots.inventorySlots.size(); i++)
        {
	        int x = this.inventorySlots.getSlot(i).xPos;
	        int y = this.inventorySlots.getSlot(i).yPos;
	        
	       /* if(!(this.inventorySlots.getSlot(i).inventory instanceof InventoryPlayer))
	        {*/
		        
		        GL11.glPushMatrix();
		        switch(i)
		        {
		        	case 0:
		        	{
		        		this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 213, 26, 20, 21);	        		 
		        		break;
		        	}
		        	default: 
		        	{
		        		this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 192, 26, 20, 21);
		        		break;
		        	}	        	
		        }
		        
		        if(this.inventorySlots.getSlot(i) instanceof SlotUpgrades)
		        {
	        		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        		this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 213, 175, 20, 20);
	        	}
		        
		        GL11.glPopMatrix();
	        //}
        }
 
        //FluidTank
        this.drawTexturedModalRect(containerWidth + this.xSize / 2 - 10, containerHeight + 38, 192, 66, 20, 42);
        this.drawTexturedModalRect(containerWidth + 8, containerHeight + 38, 192, 66, 20, 42);
        this.drawTexturedModalRect(containerWidth + this.xSize - 33, containerHeight + 38, 192, 66, 20, 42);
        
        FluidStack fluid = this.tileEntity.baseTank.getFluid();        
        FluidStack fluid1 = this.tileEntity.waterTank1.getFluid();     
        FluidStack fluid2 = this.tileEntity.waterTank2.getFluid();   
        
        if(fluid != null)
        {
	        GL11.glPushMatrix();
	        GSUtils.displayGauge(containerWidth + this.xSize / 2 - 8, containerHeight + 20, tileEntity.getScaledTankLevel(tileEntity.baseTank, 38), tileEntity.baseTank.getFluid(), 0);
			GL11.glPopMatrix();
        }
        
        if(fluid1 != null)
        {
        	GL11.glPushMatrix();
        	GSUtils.displayGauge(containerWidth + this.xSize - 31, containerHeight + 20, tileEntity.getScaledTankLevel(tileEntity.waterTank1, 38), tileEntity.waterTank1.getFluid(), 0);
			GL11.glPopMatrix();
        }
        
        if(fluid2 != null)
        {
        	GL11.glPushMatrix();	 
        	GSUtils.displayGauge(containerWidth + 10, containerHeight + 20, tileEntity.getScaledTankLevel(tileEntity.waterTank2, 38), tileEntity.waterTank2.getFluid(), 0);
			GL11.glPopMatrix();
        }
        
        this.mc.renderEngine.bindTexture(GuiLiquidSeparator.guiTexture);
		this.drawTexturedModalRect(containerWidth + this.xSize / 2 - 10, containerHeight + 38, 192+20, 66, 20, 42);
		this.drawTexturedModalRect(containerWidth + 8, containerHeight + 38, 192+20, 66, 20, 42);
		this.drawTexturedModalRect(containerWidth + this.xSize - 33, containerHeight + 38, 192+20, 66, 20, 42);
		
        
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
        	//this.tileEntity.setReverse(!this.tileEntity.getReverse());
            GalaxySpace.packetPipeline.sendToServer(new GSPacketSimple(GSEnumSimplePacket.S_REVERSE_SEPATATOR, GCCoreUtil.getDimensionID(this.mc.world), new Object[] { new BlockVec3(this.tileEntity.getPos().getX(), this.tileEntity.getPos().getY(), this.tileEntity.getPos().getZ()) }));
            break;
        }
    }
    
   
}
