package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSBookComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GuiGuideBook extends GuiScreen
{ 
	   private final int bookImageHeight = 250; //������ �����������
	   private final int bookImageWidth = 450; //������ �����������
	   private int currPage = 0; //�������� ��������
	   private static final int bookTotalPages = 4; //����� ���������� �������
	   private static ResourceLocation[] bookPageTextures = new ResourceLocation[bookTotalPages]; //������������ GUI
	   private static String[] stringPageText = new String[bookTotalPages]; //����� � GUI
	   private GuiButton buttonDone; //������ ��������
	   private NextPageButton buttonNextPage; //���������������� ������ "����������� ��������"
	   private NextPageButton buttonPreviousPage; //���������������� ������� "����������� ��������" 
	   
	   
	   
	   public GuiGuideBook()
	   {
	       bookPageTextures[0] = new ResourceLocation(GalaxySpace.ASSET_PREFIX + ":textures/gui/tablet.png");
	       bookPageTextures[1] = new ResourceLocation(GalaxySpace.ASSET_PREFIX + ":textures/gui/tablet.png");
	       bookPageTextures[2] = new ResourceLocation(GalaxySpace.ASSET_PREFIX + ":textures/gui/tablet.png");
	       
	       
	       stringPageText[0] = "";
	       stringPageText[1] = "��� 2";
	       stringPageText[2]="���3 1\n\n��� 3 3";
	       stringPageText[3]="�����...";
	}

	   /**
	    * ��������� ������ (� ������ �������� ����������) �� ������.
	    */
	   @Override
	   public void initGui() 
	   {
 
	       buttonList.clear();
	      // Keyboard.enableRepeatEvents(true);
	       
	       // ���������� ������ (ID ������, ������� �� �����������, ������� �� ���������, ������ (width) ������ (�� ���������� 200), ������ ������ (�� ��������� 20), ������������ ������) 
	       buttonDone = new GuiButton(0, width / 2 /*+ 2*/ - 49, 32 + bookImageHeight, 98, 20, I18n.format("gui.done", new Object[0]));
	       buttonList.add(buttonDone);
	       
	       int offsetFromScreenLeft = (width - bookImageWidth) / 2;
	       
	       buttonList.add(buttonNextPage = new NextPageButton(1, offsetFromScreenLeft + 120, 156, true));
	       buttonList.add(buttonPreviousPage = new NextPageButton(2, offsetFromScreenLeft + 38, 156, false));
	   
	       
	   }

	   /**
	    * ���������� �� ��������� ����� ���� ��� ���������� ������. ����� �� ������ ������ � ����������� �� ������ �������
	    */
	   @Override
	   public void updateScreen() 
	   {
	       if(buttonDone != null) buttonDone.visible = currPage >= 0;/*(currPage == bookTotalPages - 1);*/
	       buttonNextPage.visible = (currPage < bookTotalPages - 1);
	       buttonPreviousPage.visible = currPage > 0;
	   }

	   /**
	    * ��������� ����������� GUI.
	    */
	   @Override
	   public void drawScreen(int parWidth, int parHeight, float p_73863_3_)
	   {
	       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	       if (currPage < 0 && currPage > 4) {currPage = 0;}
	       //System.out.println("�������: " + currPage);
	       
	       mc.getTextureManager().bindTexture(bookPageTextures[0]);
	       
	       int offsetFromScreenLeft = (width - bookImageWidth ) / 2;
	       int offsetFromScreenTop = (height - bookImageHeight ) / 2;
	       //this.drawFullSizedTexturedRect(offsetFromScreenLeft, offsetFromScreenTop, 450, 250);
	       drawTexturedModalRect(offsetFromScreenLeft, offsetFromScreenTop, 450, 256, 0, 0, 400, 256, false, false, 512, 256);
       
	       GalaxySpace.debug(GSBookComponent.names.get(0) + " | " + GSBookComponent.components.get(0));
	       
	       int widthOfString;
	       String stringPageIndicator = I18n.format("book.pageIndicator", new Object[] {Integer.valueOf(currPage + 1), bookTotalPages});
	      
	       widthOfString = fontRendererObj.getStringWidth(stringPageIndicator);
	       fontRendererObj.drawString(stringPageIndicator, offsetFromScreenLeft - widthOfString + bookImageWidth - 44, 18, 0);
	       fontRendererObj.drawSplitString(stringPageText[currPage], offsetFromScreenLeft + 36, 34, 116, 0);
	       super.drawScreen(parWidth, parHeight, p_73863_3_);

	   }

	   public void drawFullSizedTexturedRect(int x, int y, int width, int height)
	    {
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        Tessellator tessellator = Tessellator.instance;
	        tessellator.startDrawingQuads();
	        tessellator.addVertexWithUV(x, y+height, this.zLevel, 0, 1);
	        tessellator.addVertexWithUV(x+width, y+height, this.zLevel, 1, 1);
	        tessellator.addVertexWithUV(x+width, y, this.zLevel, 1, 0);
	        tessellator.addVertexWithUV(x, y, this.zLevel, 0, 0);
	        tessellator.draw();
	    }
	   
	    public void drawTexturedModalRect(int x, int y, int width, int height, int u, int v, int uWidth, int vHeight, boolean invertX, boolean invertY)
	    {
	        this.drawTexturedModalRect(x, y, width, height, u, v, uWidth, vHeight, invertX, invertY, 512, 512);
	    }

	    public void drawTexturedModalRect(float x, float y, float width, float height, float u, float v, float uWidth, float vHeight, boolean invertX, boolean invertY, float texSizeX, float texSizeY)
	    {
	        GL11.glShadeModel(GL11.GL_FLAT);
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glEnable(GL11.GL_ALPHA_TEST);
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
	        float texModX = 1F / texSizeX;
	        float texModY = 1F / texSizeY;
	        Tessellator tessellator = Tessellator.instance;
	        tessellator.startDrawingQuads();
	        float height0 = invertY ? 0 : vHeight;
	        float height1 = invertY ? vHeight : 0;
	        float width0 = invertX ? uWidth : 0;
	        float width1 = invertX ? 0 : uWidth;
	        tessellator.addVertexWithUV(x, y + height, this.zLevel, (u + width0) * texModX, (v + height0) * texModY);
	        tessellator.addVertexWithUV(x + width, y + height, this.zLevel, (u + width1) * texModX, (v + height0) * texModY);
	        tessellator.addVertexWithUV(x + width, y, this.zLevel, (u + width1) * texModX, (v + height1) * texModY);
	        tessellator.addVertexWithUV(x, y, this.zLevel, (u + width0) * texModX, (v + height1) * texModY);
	        tessellator.draw();
	    }
	    
	   /**
	    * Called when a mouse button is pressed and the mouse is moved around. 
	    * Parameters are : mouseX, mouseY, lastButtonClicked & 
	    * timeSinceMouseClick.
	    */
	   @Override
	   protected void mouseClickMove(int parMouseX, int parMouseY, 
	         int parLastButtonClicked, long parTimeSinceMouseClick) 
	   {
	    
	   }

	   /**
	    * ����� �� ���������, ����� ������ ������
	    */
	   @Override
	   protected void actionPerformed(GuiButton parButton) 
	   {
	    if (parButton == buttonDone)
	    {
	        // You can send a packet to server here if you need server to do 
	        // something
	        mc.displayGuiScreen((GuiScreen)null);
	    }
	       else if (parButton == buttonNextPage)
	       {
	           if (currPage < bookTotalPages - 1)
	           {
	               ++currPage;
	           }
	       }
	       else if (parButton == buttonPreviousPage)
	       {
	           if (currPage > 0)
	           {
	               --currPage;
	           }
	       }
	  }

	   /**
	    * ����������, ����� ����� �����������. 
	    * ������������ ��� ���������� ���������� ���������� �������
	    */
	   @Override
	   public void onGuiClosed() 
	   {
	    
	   }

	   /**
	    * ���������� �������, ���� ��� ������ ������������� ���� (� Single Player)
	    */
	   @Override
	   public boolean doesGuiPauseGame()
	   {
	       return false;
	   }
	   
	   
	   
	   @SideOnly(Side.CLIENT)
	   static class NextPageButton extends GuiButton
	   {
	       private final boolean isNextButton;

	       public NextPageButton(int parButtonId, int parPosX, int parPosY, 
	             boolean parIsNextButton)
	       {
	           super(parButtonId, parPosX, parPosY, 23, 13, "");
	           isNextButton = parIsNextButton;
	       }

	       /**
	        * ������� ��������� ������. ���� �������� 
	        */
	       @Override
	       public void drawButton(Minecraft mc, int parX, int parY)
	       {
	           if (visible)
	           {
	               boolean isButtonPressed = (parX >= xPosition 
	                     && parY >= yPosition 
	                     && parX < xPosition + width 
	                     && parY < yPosition + height);
	               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	               mc.getTextureManager().bindTexture(bookPageTextures[1]);
	               int textureX = 0;
	               int textureY = 192;

	               if (isButtonPressed)
	               {
	                   textureX += 23;
	               }

	               if (!isNextButton)
	               {
	                   textureY += 13;
	               }

	               drawTexturedModalRect(xPosition, yPosition, 
	                     textureX, textureY, 
	                     23, 13);
	           }
	       }
	   }
	}
