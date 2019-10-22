package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.api.item.IModificationItem;
import galaxyspace.core.network.packet.GSPacketSimple;
import galaxyspace.core.network.packet.GSPacketSimple.GSEnumSimplePacket;
import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.util.GSUtils;
import galaxyspace.core.util.GSUtils.Module_Type;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerModificationTable;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityModificationTable;
import micdoodle8.mods.galacticraft.api.recipe.SpaceStationRecipe;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.util.ColorUtil;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;

public class GuiModificationTable extends GuiContainerGC{
	
	private static final ResourceLocation guiTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/base_gui_1.png");
	private TileEntityModificationTable tileEntity;
    
	private int countdown = 0;
	//private List<SpaceSuit_Modules> actual_list = new ArrayList<SpaceSuit_Modules>();
	private List<ItemModule> actual_list = new ArrayList<ItemModule>();
	
	private int xOffsetModule = 10;
	
	public GuiModificationTable(InventoryPlayer par1InventoryPlayer, TileEntityModificationTable tileEntity)
    {
        super(new ContainerModificationTable(par1InventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
        this.ySize = 204;        
    }
	
	@Override
    public void initGui()
    {
        super.initGui();
    }
	
	@Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {		
		this.fontRenderer.drawString(EnumColor.WHITE + this.tileEntity.getName(), 86 - (this.fontRenderer.getStringWidth(this.tileEntity.getName()) / 2), 1, 4210752);
		this.fontRenderer.drawString(EnumColor.WHITE + GCCoreUtil.translate("container.inventory"), 15, this.ySize - 88, 4210752);
   
		//int i = modules_number = 0;
		this.actual_list.clear();
		
		if(!this.inventorySlots.getSlot(0).getStack().isEmpty())
		{
			ItemStack stack = this.inventorySlots.getSlot(0).getStack();
			
			
			if(stack.getItem() instanceof IModificationItem)
			{
				
				for(ItemModule module : ((IModificationItem)stack.getItem()).getAvailableModules())
				{
					if(stack.getItem() instanceof ItemArmor)
					{
						ItemArmor item = (ItemArmor) stack.getItem();
						if(module.getType() != Module_Type.ALL && !module.getType().equals(((IModificationItem)stack.getItem()).getType(stack))) continue;					
						
						if(module.getEquipmentSlot() == item.armorType || module.getEquipmentSlot() == null || module.getType() == Module_Type.ALL)
							actual_list.add(module);
					}
					else 
					{
						if(module.getType() == null) continue;
						if(!module.getType().equals(((IModificationItem)stack.getItem()).getType(stack))) continue;					
						
						actual_list.add(module);
					}
				}
			}	
			//this.modules_number = actual_list.size();
			
		}
    }
	
	@Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    { 
		
		this.mc.renderEngine.bindTexture(this.guiTexture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		int containerWidth = (this.width - this.xSize) / 2;
		int containerHeight = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize); // Base Gui
  
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
		        	default: 
		        	{
		        		this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 192, 26, 20, 21);
		        		break;
		        	}	        	
		        }
		        GL11.glPopMatrix();
	        //}
        }
        ///////////////////////
        
        if(!this.inventorySlots.getSlot(0).getStack().isEmpty())
		{
        	ItemStack stack = this.inventorySlots.getSlot(0).getStack();	        
	        
        	 for(int i = 0; i < actual_list.size(); i++)
 	        {
 	        	//texture button
 		        if(stack.hasTagCompound() && (!stack.getTagCompound().hasKey(this.actual_list.get(i).getName()) || !stack.getTagCompound().getBoolean(this.actual_list.get(i).getName())))
 		        	this.drawTexturedModalRect(containerWidth + 30 + xOffsetModule, containerHeight + 20 + (22 * i), 192, 196, 20, 20);
 		        else
 		        	this.drawTexturedModalRect(containerWidth + 30 + xOffsetModule, containerHeight + 20 + (22 * i), 213, 196, 20, 20);
 	        }
        	 
	        for(int i = 0; i < actual_list.size(); i++)
	        {
	        	//ItemStack[] stacks = this.actual_list.get(i).getItemsForModule();//GSUtils.getItemsForModules(this.actual_list.get(i).getName());
	        	
	        	
				//if(stacks != null)
					//for(ItemStack stacklist : stacks)
					//{
						//int amount = this.getAmountInInventory(stacklist);
						
						RenderHelper.enableGUIStandardItemLighting();
	                    this.itemRender.renderItemAndEffectIntoGUI(actual_list.get(i).getIcon(), containerWidth + 32 + xOffsetModule, containerHeight + 22 + (22 * i));
	                    RenderHelper.disableStandardItemLighting();                  	                    
					//}
	        }
	        
	        int mod_count = stack.hasTagCompound() && stack.getTagCompound().hasKey("modification_count") ? stack.getTagCompound().getInteger("modification_count") : 1;
	        this.fontRenderer.drawString("Mod. count: " + mod_count, containerWidth + 58, containerHeight + 105, 0xFFFFFF);
		} 
        else
        {
        	this.fontRenderer.drawSplitString(GCCoreUtil.translate("gui.module.empty_slot"), containerWidth + 58, containerHeight + 60, 80, 0xFFFFFF);
        }
        
        if(GalaxySpace.debug) GSUtils.renderDebugGui(this, containerWidth, containerHeight);
    }
	
	@Override
    public void drawScreen(int mousePosX, int mousePosY, float partialTicks)
    {
		super.drawScreen(mousePosX, mousePosY, partialTicks);
		if(countdown > 0) countdown--;
		
		if(!this.inventorySlots.getSlot(0).getStack().isEmpty())
		{

			int containerWidth = (this.width - this.xSize) / 2;
			int containerHeight = (this.height - this.ySize) / 2;
			ItemStack stack = this.inventorySlots.getSlot(0).getStack();
			
			for(int i = 0; i < actual_list.size(); i++)
	        {				
				if (mousePosX >= containerWidth + xOffsetModule + 30 && mousePosX <= containerWidth + xOffsetModule + 50 && mousePosY >= containerHeight + 20 + (22 * i) && mousePosY <= containerHeight + 40 + (22 * i))
				{	
					GlStateManager.disableLighting();
					this.mc.renderEngine.bindTexture(this.guiTexture);	

					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					this.drawTexturedModalRect(containerWidth - 88, containerHeight + 12, 0, 12, xSize / 2, ySize / 2 + 1);
		        	
					this.fontRenderer.drawSplitString(GCCoreUtil.translate("gui.message.required_equipment.name"), containerWidth - 65, containerHeight + 15, 80, 0xFFFF);
					
					
					
					EnumColor encolor = stack.hasTagCompound() && stack.getTagCompound().getBoolean(this.actual_list.get(i).getName()) ? EnumColor.BRIGHT_GREEN : EnumColor.RED;
					
					this.fontRenderer.drawString(encolor + GCCoreUtil.translate("gui.module." + actual_list.get(i).getName()), containerWidth + 70, containerHeight + 20, 0xFFFFFF);
					
					String mode = actual_list.get(i).isActiveModule() ? EnumColor.PURPLE + GCCoreUtil.translate("gui.module.active") : EnumColor.GREY + GCCoreUtil.translate("gui.module.passive");
					this.fontRenderer.drawString(mode, containerWidth + 70, containerHeight + 28, 0xFFFFFF);
					
					//this.drawToolTip(mousePosX, mousePosY,  encolor + GCCoreUtil.translate("gui.module." + actual_list.get(i)));  
					String s = GCCoreUtil.translate("gui.module." + actual_list.get(i).getName() + ".desc");
					this.fontRenderer.drawSplitString(s, containerWidth + 70, containerHeight + 40, 90,	0xFFFFFF);
					
					/*if(this.mc.gameSettings.isKeyDown(this.mc.gameSettings.keyBindSneak))
					{
						String s = GCCoreUtil.translate("gui.module." + actual_list.get(i) + ".desc");
						this.drawToolTip(mousePosX, mousePosY + 20, s, 150, 8 * (this.fontRenderer.getStringWidth(s) / 100) + 4);
						//GalaxySpace.debug(this.fontRenderer.getStringWidth(s) / 100 + "");
					
					}
					else */
					{
						ItemStack[] stacks = actual_list.get(i).getItemsForModule();//GSUtils.getItemsForModules(this.actual_list.get(i).getName());
			        	
			        	int k = 0;
						if(stacks != null)
							for(ItemStack stacklist : stacks)
							{
								this.mc.renderEngine.bindTexture(this.guiTexture);	
								GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
								//this.drawTexturedModalRect(containerWidth - 55, containerHeight + 35, 192, 196, 20, 20);
								this.drawTexturedModalRect(containerWidth - 55, containerHeight + 35, 192, 26, 20, 21);
				        		
								int amount = this.getAmountInInventory(stacklist);
								int xOffset = -3;
								RenderHelper.enableGUIStandardItemLighting();
			                    this.itemRender.renderItemAndEffectIntoGUI(stacklist, containerWidth - 50 + xOffset, containerHeight + 37);
			                    RenderHelper.disableStandardItemLighting();
			                    
			                    boolean valid = amount >= stacklist.getCount();
			                    int color = valid | this.mc.player.capabilities.isCreativeMode ? ColorUtil.to32BitColor(255, 0, 255, 0) : ColorUtil.to32BitColor(255, 255, 0, 0);
			                    String str = "" + stacklist.getCount();
			                    this.fontRenderer.drawString(str, containerWidth - 35 + xOffset, containerHeight + 47, color);
			                    this.drawToolTip(containerWidth - 40 + xOffset, containerHeight + 73,  stacklist.getDisplayName());  
			                	
			                    k++;
							}
						//this.drawToolTip(mousePosX, mousePosY + 70, GCCoreUtil.translateWithFormat("item_desc.shift.name", GameSettings.getKeyDisplayString(FMLClientHandler.instance().getClient().gameSettings.keyBindSneak.getKeyCode()))); 
						
					}
					
					
					if(this.actual_list.get(i).getForrbidenModules() != null)
					{
						this.fontRenderer.drawSplitString(GCCoreUtil.translate("gui.message.forrbiden_modules.name"), containerWidth - 75, containerHeight + 75, 80, 0xFF0000);
						
						for(ItemModule forbidden : this.actual_list.get(i).getForrbidenModules())
						{
							//ItemStack[] stacks = GSUtils.getItemsForModules(forbidden);
							
							//if(stacks != null)
								//for(ItemStack stacklist : stacks)
								//{
									this.mc.renderEngine.bindTexture(this.guiTexture);	
									GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
									this.drawTexturedModalRect(containerWidth - 55, containerHeight + 88, 192, 196, 20, 20);
						        	
									RenderHelper.enableGUIStandardItemLighting();
									this.itemRender.renderItemAndEffectIntoGUI(forbidden.getIcon(), containerWidth - 53, containerHeight + 90);
									RenderHelper.disableStandardItemLighting();
									
									
								//}
						}
					}
				}
	        }
		}
    }
	
	protected int getAmountInInventory(ItemStack stack)
    {
        int amountInInv = 0;

        for (int x = 0; x < FMLClientHandler.instance().getClientPlayerEntity().inventory.getSizeInventory(); x++)
        {
            final ItemStack slot = FMLClientHandler.instance().getClientPlayerEntity().inventory.getStackInSlot(x);

            if (slot != null)
            {
                if (SpaceStationRecipe.checkItemEquals(stack, slot))
                {
                    amountInInv += slot.getCount();
                }
            }
        }

        return amountInInv;
    }
	
	private void drawToolTip(int mousePosX, int mousePosY, String tooltip)
	{
		this.drawToolTip(mousePosX, mousePosY, tooltip, this.fontRenderer.getStringWidth(tooltip), 8);
	}
	
	private void drawToolTip(int mousePosX, int mousePosY, String tooltip, int width, int height)
	{
		GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glPushMatrix();
        GL11.glTranslatef(0, 0, 300);
        int k = width;
        int j2 = mousePosX - k / 2;
        int k2 = mousePosY - 12;
        int i1 = height;

        if (j2 + k > this.width)
        {
            j2 -= (j2 - this.width + k);
        }

        if (k2 + i1 + 6 > this.height)
        {
            k2 = this.height - i1 - 6;
        }

        int j1 = ColorUtil.to32BitColor(190, 0, 153, 255);
        this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
        this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
        this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
        this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
        this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
        int k1 = ColorUtil.to32BitColor(170, 0, 153, 255);
        int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
        this.drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
        this.drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
        this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
        this.drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);

        this.fontRenderer.drawSplitString(tooltip, j2, k2, 150, ColorUtil.to32BitColor(255, 255, 255, 255));

        GL11.glPopMatrix();   
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button) throws IOException
    {
		super.mouseClicked(mouseX, mouseY, button);
		
		if(this.inventorySlots.getSlot(0).getStack() != null)
		{
			ItemStack stack = this.tileEntity.getStackInSlot(0);
			
			int containerWidth = (this.width - this.xSize) / 2;
			int containerHeight = (this.height - this.ySize) / 2;
			
			for(int i = 0; i < actual_list.size(); i++)
	        {
				if (mouseX >= containerWidth + xOffsetModule + 30 && mouseX <= containerWidth + xOffsetModule + 50 
						&& mouseY >= containerHeight + 20 + (22 * i)
						&& mouseY <= containerHeight + 40 + (22 * i)
						&& countdown <= 0)
				{		
					
					if(!stack.getTagCompound().hasKey(this.actual_list.get(i).getName()) || !stack.getTagCompound().getBoolean(this.actual_list.get(i).getName()))
					{					
						GalaxySpace.packetPipeline.sendToServer(new GSPacketSimple(GSEnumSimplePacket.S_UPDATE_NBT_ITEM_ON_GUI, GCCoreUtil.getDimensionID(this.mc.world), new Object[] {new BlockVec3(this.tileEntity.getPos().getX(), this.tileEntity.getPos().getY(), this.tileEntity.getPos().getZ()), this.actual_list.get(i).getName(), true}));	   
					}
					else if(stack.getTagCompound().hasKey(this.actual_list.get(i).getName()))
					{					
						GalaxySpace.packetPipeline.sendToServer(new GSPacketSimple(GSEnumSimplePacket.S_UPDATE_NBT_ITEM_ON_GUI, GCCoreUtil.getDimensionID(this.mc.world), new Object[] {new BlockVec3(this.tileEntity.getPos().getX(), this.tileEntity.getPos().getY(), this.tileEntity.getPos().getZ()), this.actual_list.get(i).getName(), false}));	   
					}
					
					countdown = 20 * 3;
				}
	        }
		}
    }
}
