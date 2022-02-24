package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.core.GSItems;
import galaxyspace.core.client.gui.tile.GuiTileBase;
import galaxyspace.core.network.packet.GSPacketSimple;
import galaxyspace.core.network.packet.GSPacketSimple.GSEnumSimplePacket;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerGravitationModule;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityGravitationModule;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementCheckbox;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementCheckbox.ICheckBoxCallback;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementTextBox;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementTextBox.ITextBoxCallback;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import micdoodle8.mods.galacticraft.core.util.ColorUtil;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiGravitationModule extends GuiTileBase implements ITextBoxCallback, ICheckBoxCallback
{
   
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
        super(new ContainerGravitationModule(par1InventoryPlayer, tileEntity), 2, 1);
        this.tileEntity = tileEntity;
        this.ySize = 204;
        moduleInfoX = this.inventorySlots.getSlotFromInventory(tileEntity, 1).xPos;
		moduleInfoY = this.inventorySlots.getSlotFromInventory(tileEntity, 1).yPos;
        radius = tileEntity.getGravityRadius();
        moduleList = new ItemStack[] {new ItemStack(GSItems.UPGRADES, 1, 0), new ItemStack(GSItems.UPGRADES, 1, 3)};
    }

    @Override
    public void initGui()
    {
        super.initGui();

        this.buttonList.clear();
        final int var5 = (this.width - this.xSize) / 2;
        final int var6 = (this.height - this.ySize) / 2;
        
        disableButton = new GuiButton(BTN_ENABLE, var5 + 110, var6 + 63, 50, 20, GCCoreUtil.translate("gui.button.disable.name"));

        this.checkboxRenderEffects = new GuiElementCheckbox(0, this, var5 + 10, var6 + 65, EnumColor.WHITE + GCCoreUtil.translate("gui.message.effect_visible.name"));
        this.buttonList.add(this.checkboxRenderEffects);
        this.buttonList.add(disableButton);
        
        this.strengthField = new GuiElementTextBox(FIELD_STRENGTH, this, var5 + 110, var6 + 41, 38, 18, "0", true, 2, true);
        this.addInputField(this.strengthField);

    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of
     * the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	super.drawGuiContainerForegroundLayer(par1, par2);
    	TextFormatting color = getStyle() == Style.MODERN ? TextFormatting.WHITE : TextFormatting.DARK_GRAY;
        
    	this.checkboxRenderEffects.displayString = color + GCCoreUtil.translate("gui.message.effect_visible.name");
    	this.fontRenderer.drawString(color + GCCoreUtil.translate("gui.gravity.radius.name"), 11, 46, 4210752);
        
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


        this.fontRenderer.drawString(color + GCCoreUtil.translate("gui.message.status.name") + ": " + displayText, 100, 104, 4210752);
    
        for(int i = 0; i < 4; i++)
        	if(this.tileEntity.getStackInSlot(i+1).isItemEqual(new ItemStack(GSItems.UPGRADES, 1, 1))) {
        		this.fontRenderer.drawSplitString(EnumColor.BRIGHT_GREEN + GCCoreUtil.translate("gui.message.active_pressure_shield.name"), 10, 20, 160, 4210752);
        		break;
        	}
       
        this.strengthField.text = tileEntity.getGravityRadius() + "";
       // this.fontRenderer.drawString(EnumColor.BRIGHT_GREEN + "" + tileEntity.getGravityRadius() , 30, 5, 0xFFFFFF);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
    	super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int containerWidth = (this.width - this.xSize) / 2;
        int containerHeight = (this.height - this.ySize) / 2;

        //Energy
        this.renderEnergyBar(containerWidth + 4, containerHeight + 102, this.tileEntity.getScaledElecticalLevel(55), this.tileEntity.getEnergyStoredGC(), this.tileEntity.getMaxEnergyStoredGC());

        this.checkboxRenderEffects.isSelected = this.tileEntity.shouldRenderEffects;
                       
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

        if(actualStrength > 16) actualStrength = 16;
        
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
		return false;
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
