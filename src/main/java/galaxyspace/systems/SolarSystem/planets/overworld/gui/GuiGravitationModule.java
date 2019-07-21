package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.core.network.packet.GSPacketSimple;
import galaxyspace.core.network.packet.GSPacketSimple.GSEnumSimplePacket;
import galaxyspace.core.prefab.inventory.SlotUpgrades;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerGravitationModule;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityGravitationModule;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementCheckbox;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementCheckbox.ICheckBoxCallback;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementTextBox;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementTextBox.ITextBoxCallback;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import micdoodle8.mods.galacticraft.core.util.ColorUtil;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiGravitationModule extends GuiContainerGC implements ITextBoxCallback, ICheckBoxCallback
{
    private static final ResourceLocation electricFurnaceTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/base_gui.png");
    private GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion(0, 0, 56, 9, null, 0, 0, this);
     
    private GuiElementCheckbox checkboxRenderEffects;
    
    private TileEntityGravitationModule tileEntity;
    
    protected List<GuiElementTextBox> inputFieldList = new ArrayList();
    private GuiButton disableButton;
    private GuiElementTextBox strengthField;
    public final int BTN_ENABLE = 6;
    public final int FIELD_STRENGTH= 10;
    
    private int radius; 
    
    
    public GuiGravitationModule(InventoryPlayer par1InventoryPlayer, TileEntityGravitationModule tileEntity)
    {
        super(new ContainerGravitationModule(par1InventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
        this.ySize = 204;
        radius = tileEntity.getGravityRadius();
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
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 76, (this.height - this.ySize) / 2 + 98, 18, 18, desc, this.width, this.height, this));
   
        desc = new ArrayList<String>();
        desc.add(GCCoreUtil.translate("gui.gravitation_module_stabilisation_1.desc"));
        desc.add(GCCoreUtil.translate("gui.gravitation_module_stabilisation_2.desc"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2, (this.height - this.ySize) / 2 + 12, 10, 10, desc, this.width, this.height, this));
   
        this.buttonList.clear();
        final int var5 = (this.width - this.xSize) / 2;
        final int var6 = (this.height - this.ySize) / 2;
        
        disableButton = new GuiButton(BTN_ENABLE, var5 + 110, var6 + 63, 50, 20, GCCoreUtil.translate("gui.button.disable.name"));

        this.checkboxRenderEffects = new GuiElementCheckbox(0, this, var5 + 10, var6 + 65, EnumColor.WHITE + GCCoreUtil.translate("gui.message.effect_visible.name"));
        this.buttonList.add(this.checkboxRenderEffects);
        this.buttonList.add(disableButton);
        
        this.strengthField = new GuiElementTextBox(FIELD_STRENGTH, this, var5 + 110, var6 + 41, 38, 18, "0", true, 2, true);
        this.addInputField(this.strengthField);
        
        desc = new ArrayList<String>();
        desc.add(EnumColor.BRIGHT_GREEN + GCCoreUtil.translate("gui.available_modules.desc"));
        desc.add("");
        desc.add("- " + new ItemStack(GSItems.UPGRADES, 1, 1).getDisplayName());
        desc.add("- " + new ItemStack(GSItems.UPGRADES, 1, 3).getDisplayName());
        this.infoRegions.add(new GuiElementInfoRegion((this.width + this.xSize) / 2, (this.height - this.ySize) / 2 + 16, 18, 21 * 4, desc, this.width, this.height, this));
     
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of
     * the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	this.fontRenderer.drawString(EnumColor.WHITE + this.tileEntity.getName(), 86 - (this.fontRenderer.getStringWidth(this.tileEntity.getName()) / 2), 1, 4210752);
    	this.fontRenderer.drawString(EnumColor.WHITE + GCCoreUtil.translate("gui.gravity.radius.name"), 11, 46, 4210752);
        
        String displayText = "";

        if(tileEntity.getDisabled(0)) {

        	displayText = EnumColor.RED + GCCoreUtil.translate("gui.status.disabled.name");
            disableButton.displayString = GCCoreUtil.translate("gui.button.enable.name");
        } else {
        	
        	if (this.tileEntity.hasEnoughEnergyToRun)
            {
                displayText = EnumColor.BRIGHT_GREEN + GCCoreUtil.translate("gui.status.running.name");
            }
        	else
        	{
        		displayText = EnumColor.ORANGE + GCCoreUtil.translate("gui.status.idle.name");
        	}
            disableButton.displayString = GCCoreUtil.translate("gui.button.disable.name");

        }


        this.fontRenderer.drawString(EnumColor.WHITE + GCCoreUtil.translate("gui.message.status.name") + ": " + displayText, 100, 104, 4210752);
        this.fontRenderer.drawString(EnumColor.WHITE + GCCoreUtil.translate("container.inventory"), 13, this.ySize - 88, 4210752);
   
        if(this.inventorySlots.getSlot(1).getHasStack()) 
        	this.fontRenderer.drawString(EnumColor.BRIGHT_GREEN + GCCoreUtil.translate("gui.message.active_pressure_shield.name"), 30, 24, 4210752);
        
       
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the
     * items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        this.mc.renderEngine.bindTexture(GuiGravitationModule.electricFurnaceTexture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int containerWidth = (this.width - this.xSize) / 2;
        int containerHeight = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);
        int scale;

        List<String> electricityDesc = new ArrayList<String>();
        electricityDesc.add(GCCoreUtil.translate("gui.energy_storage.desc.0"));
        EnergyDisplayHelper.getEnergyDisplayTooltip(this.tileEntity.getEnergyStoredGC(), this.tileEntity.getMaxEnergyStoredGC(), electricityDesc);
        this.electricInfoRegion.tooltipStrings = electricityDesc;
/*
        if (this.tileEntity.processTicks > 0)
        {
            scale = (int) ((double) this.tileEntity.processTicks / (double) this.tileEntity.processTimeRequired * 54);
            this.drawTexturedModalRect(containerWidth + 59, containerHeight + 21, 176, 38, 69 - scale, 31);
        }
*/
        //Info
        this.drawTexturedModalRect(containerWidth, containerHeight + 12, 215, 7, 10, 10);  
        //Energy
        this.drawTexturedModalRect(containerWidth + 16, containerHeight + 102, 192, 47, 56, 9);
        this.drawTexturedModalRect(containerWidth + 4, containerHeight + 102, 192, 56, 11, 10);
        if (this.tileEntity.getEnergyStoredGC() > 0)
        {
            scale = this.tileEntity.getScaledElecticalLevel(55);
            this.drawTexturedModalRect(containerWidth + 116 - 99, containerHeight + 103, 192, 0, scale, 7);
            this.drawTexturedModalRect(containerWidth + 3, containerHeight + 102, 192, 7, 11, 10);           
        }

        this.checkboxRenderEffects.isSelected = this.tileEntity.shouldRenderEffects;
        
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
        
        if(GalaxySpace.debug) GSUtils.renderDebugGui(this, containerWidth, containerHeight);
     }

	@Override
	public void onSelectionChanged(GuiElementCheckbox checkbox, boolean newSelected) {
		this.tileEntity.shouldRenderEffects = newSelected;
        GalaxySpace.packetPipeline.sendToServer(new GSPacketSimple(GSEnumSimplePacket.S_ON_ADVANCED_GUI_CLICKED_INT, GCCoreUtil.getDimensionID(this.mc.world),new Object[] { 6, this.tileEntity.getPos().getX(), this.tileEntity.getPos().getY(), this.tileEntity.getPos().getZ(), newSelected ? 1 : 0 }));
   
	}
	
	protected void sendDataToServer()
    {
        BlockVec3 pos = new BlockVec3(tileEntity);
        int actualStrength = radius;

        GalaxySpace.packetPipeline.sendToServer(new GSPacketSimple(GSEnumSimplePacket.S_GRAVITY_RADIUS, GCCoreUtil.getDimensionID(this.mc.world), pos, actualStrength));
        tileEntity.setGravityRadius(actualStrength);
    }

	@Override
    protected void actionPerformed(GuiButton btn)
    {
        switch(btn.id) {
        case BTN_ENABLE:
            GalacticraftCore.packetPipeline.sendToServer(new PacketSimple(EnumSimplePacket.S_UPDATE_DISABLEABLE_BUTTON, GCCoreUtil.getDimensionID(this.mc.world), new Object[] { this.tileEntity.getPos(), 0 }));
            break;
        }
    }
	
	@Override
	protected void keyTyped(char keyChar, int keyID) throws IOException {
		if (keyID != Keyboard.KEY_ESCAPE /* && keyID != this.mc.gameSettings.keyBindInventory.getKeyCode() */) {
			// do the fields
			for (GuiElementTextBox box : inputFieldList) {
				if (box.keyTyped(keyChar, keyID)) {
					return;
				}
			}
		}

		super.keyTyped(keyChar, keyID);
	}
	 
	@Override
	public boolean canPlayerEdit(GuiElementCheckbox checkbox, EntityPlayer player) {
		return true;
	}

	@Override
	public boolean getInitiallySelected(GuiElementCheckbox checkbox) {
		return this.tileEntity.shouldRenderEffects;
	}

	@Override
	public void onIntruderInteraction() {
		
	}
	
	protected void addInputField(GuiElementTextBox box)
    {
        this.buttonList.add(box);
        this.inputFieldList.add(box);
    }

	@Override
	public boolean canPlayerEdit(GuiElementTextBox textBox, EntityPlayer player) {
		return true;
	}

	@Override
	public void onTextChanged(GuiElementTextBox textBox, String newText) {
		if(newText == null) {
            // don't do anything
            return;
        }
        double newValue;
        try
        {
            newValue = Double.parseDouble(newText);
            //newValue = Integer.parseInt(newText);
        } catch(NumberFormatException wat) {
            // this is ridiculous
            return;
        }
        if(newValue < 0) {
            return;
        }
        
        switch(textBox.id) {
        case FIELD_STRENGTH:
            radius = (int) newValue;
            break;
        default:
            return;
        }
        this.sendDataToServer();
	}

	@Override
	public String getInitialText(GuiElementTextBox textBox) {
		switch(textBox.id) {
		case FIELD_STRENGTH:
            return Integer.toString(radius);
		}

		return Integer.toString(textBox.id);
	}

	@Override
	public int getTextColor(GuiElementTextBox textBox) {
		return ColorUtil.to32BitColor(255, 20, 255, 20);
	}

	@Override
	public void onIntruderInteraction(GuiElementTextBox textBox) {
		
	}
}
