package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.core.GSItems;
import galaxyspace.core.prefab.inventory.SlotUpgrades;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerUniversalRecycler;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.RecyclerRecipes;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.RecyclerRecipes.RecycleRecipe;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityUniversalRecycler;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiUniversalRecycler extends GuiContainerGC
{
    private static final ResourceLocation guiTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/base_gui.png");
    private TileEntityUniversalRecycler tileEntity;
    private GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion(0, 0, 56, 9, null, 0, 0, this);
    private GuiElementInfoRegion processInfoRegion = new GuiElementInfoRegion(0, 0, 52, 25, null, 0, 0, this);
    private GuiElementInfoRegion fuelTankRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 28, 16, 38, new ArrayList<String>(), this.width, this.height, this);
    private GuiElementInfoRegion waterTankRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 28, 16, 38, new ArrayList<String>(), this.width, this.height, this);
    
    public GuiUniversalRecycler(InventoryPlayer par1InventoryPlayer, TileEntityUniversalRecycler tileEntity)
    {
        super(new ContainerUniversalRecycler(par1InventoryPlayer, tileEntity));
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
        this.processInfoRegion.xPosition = (this.width - this.xSize) / 2 + 27;
        this.processInfoRegion.yPosition = (this.height - this.ySize) / 2 + 30;
        this.processInfoRegion.parentWidth = this.width;
        this.processInfoRegion.parentHeight = this.height;
        this.infoRegions.add(this.processInfoRegion);
        
        List<String> fuelTankDesc = new ArrayList<String>();
        int fuelLevel = this.tileEntity.waterTank != null && this.tileEntity.waterTank.getFluid() != null ? this.tileEntity.waterTank.getFluid().amount : 0;
        int fuelCapacity = this.tileEntity.waterTank != null ? this.tileEntity.waterTank.getCapacity() : 0;
        fuelTankDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.message.fuel.name") + ": " + fuelLevel + " / " + fuelCapacity);
        this.fuelTankRegion.tooltipStrings = fuelTankDesc;
        this.fuelTankRegion.xPosition = (this.width - this.xSize) - 84;
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
        this.fontRenderer.drawString(EnumColor.WHITE + this.tileEntity.getName(), 35, 1, 4210752);
        String displayText;

        if (this.tileEntity.processTicks > 0)
        {
            displayText = EnumColor.BRIGHT_GREEN + GCCoreUtil.translate("gui.status.running.name");
        }
        else
        {
            displayText = EnumColor.ORANGE + GCCoreUtil.translate("gui.status.idle.name");
        }

        String str = EnumColor.WHITE + GCCoreUtil.translate("gui.message.status.name") + ": " + displayText;
        this.fontRenderer.drawSplitString(str, 110, 100, 40, 4210752);
        this.fontRenderer.drawString(EnumColor.WHITE + GCCoreUtil.translate("container.inventory"), 15, this.ySize - 88, 4210752);
          
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the
     * items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        this.mc.renderEngine.bindTexture(GuiUniversalRecycler.guiTexture);
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
            scale = 0;
        }

        //Process Bar
        List<String> processDesc = new ArrayList<String>();
        processDesc.clear();
        processDesc.add(GCCoreUtil.translate("gui.electric_compressor.desc.0") + ": " + scale + "%");
        this.processInfoRegion.tooltipStrings = processDesc;
        this.drawTexturedModalRect(containerWidth + 55, containerHeight + 36, 230, 60, 24, 26);
        
        this.drawTexturedModalRect(containerWidth + 84, containerHeight + 44, 192 + 20, 108, 16, 17);
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
            this.drawTexturedModalRect(containerWidth + 84, containerHeight + 44, 192 + 20, 124, scale, 17);
       
            
            for(int i = 0; i < 8 ; i++) 
            {
            	this.drawTexturedModalRect(containerWidth + 44 + i, containerHeight + 49, 203, 130, 4, 5);        	
            	this.drawTexturedModalRect(containerWidth + 52 + (i * 4), containerHeight + 66, 203, 130, 4, 5);
            
            	this.drawTexturedModalRect(containerWidth + 52, containerHeight + 52 + (i * 2), 203, 130, 3, 4);
            	this.drawTexturedModalRect(containerWidth + 81, containerHeight + 50 + (i * 2), 203, 130, 3, 4);
            }  
            
            /*if (this.tileEntity.processTicks < this.tileEntity.processTimeRequired / 2)
            {*/
                this.drawTexturedModalRect(containerWidth + 55, containerHeight + 39, 230, 86, 24, 26);
            //}           
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
        this.drawTexturedModalRect(containerWidth + this.xSize - 30, containerHeight + 38, 192, 66, 20, 42);
       // this.drawTexturedModalRect(containerWidth + 4, containerHeight + 38, 192, 66, 20, 42);
        
        FluidStack fluid = this.tileEntity.waterTank.getFluid();        
        //FluidStack fluid1 = this.tileEntity.waterTank1.getFluid();  
        GL11.glPushMatrix();        
        
        if(fluid != null)
        {
        	GL11.glPushMatrix();	
        	GSUtils.displayGauge(containerWidth + this.xSize - 28, containerHeight + 20, tileEntity.getScaledTankLevel(38), tileEntity.waterTank.getFluid(), 0);
 	        //GSUtils.drawFluid(fluid, (this.width - this.xSize) / 2 + 148, this.height / 2 - 62, 16, 38, this.tileEntity.waterTank.getCapacity());
 			GL11.glPopMatrix();
        }
        /*
        if(fluid1 != null)
        {
        	
			GL11.glPushMatrix();	        
	        GSUtils.drawFluid(fluid1, (this.width - this.xSize) / 2 + 6, this.height / 2 - 62, 16, 38, this.tileEntity.waterTank1.getCapacity());
			GL11.glPopMatrix();
        }*/
        GL11.glPopMatrix();

        this.mc.renderEngine.bindTexture(this.guiTexture);
        this.drawTexturedModalRect(containerWidth + 146, containerHeight + 38, 192+20, 66, 20, 42);
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
   
}
