package galaxyspace.core.client.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import asmodeuscore.core.utils.Utils;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSThreadVersionCheck;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.client.GuiModList;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GSGuiMainMenu extends GuiScreen implements GuiYesNoCallback
{
    private static final Logger logger = LogManager.getLogger();
    /** The RNG used by the Main Menu Screen. */
    private static final Random rand = new Random();
    /** Counts the number of screen updates. */
    private float updateCounter;
    /** The splash message. */
    private String splashText;
    private GuiButton buttonResetDemo;
    /** Timer used to rotate the panorama, increases every tick. */
    private float panoramaTimer;
    /** Texture allocated for the current viewport of the main menu's panorama background. */
    private DynamicTexture viewportTexture;
    private final Object theardLock = new Object();
    private String field_92025_p;
    private String field_146972_A;
    private String field_104024_v;

    private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
    private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");
    private static final ResourceLocation mainmenu1 = new ResourceLocation(GalaxySpace.ASSET_PREFIX + ":" + "textures/gui/guimenu.png");
    private static final ResourceLocation edition = new ResourceLocation("textures/gui/title/edition.png");
    
    /** An array of all the paths to the panorama pictures. */
    private static final ResourceLocation[] background = new ResourceLocation[] 
    {
    	new ResourceLocation(GalaxySpace.ASSET_PREFIX + ":"+ "textures/gui/backgrounds/bg1.png"), 
    	new ResourceLocation(GalaxySpace.ASSET_PREFIX + ":"+ "textures/gui/backgrounds/bg2.png"),
    	new ResourceLocation(GalaxySpace.ASSET_PREFIX + ":"+ "textures/gui/backgrounds/bg3.png"),
    	new ResourceLocation(GalaxySpace.ASSET_PREFIX + ":"+ "textures/gui/backgrounds/bgH.png")
    };
    
    private static final ResourceLocation[] PANORAMA_PATHS = new ResourceLocation[] 
    {
    		new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/backgrounds/panorama/panorama_0.png"), 
    		new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/backgrounds/panorama/panorama_1.png"), 
    		new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/backgrounds/panorama/panorama_2.png"), 
    		new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/backgrounds/panorama/panorama_3.png"), 
    		new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/backgrounds/panorama/panorama_4.png"), 
    		new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/backgrounds/panorama/panorama_5.png")
    };
    
    
    public static final String field_96138_a = "Please click " + TextFormatting.UNDERLINE + "here" + TextFormatting.RESET + " for more information.";
    private int field_92024_r;
    private int field_92023_s;
    private int field_92022_t;
    private int field_92021_u;
    private int field_92020_v;
    private int field_92019_w;
    private ResourceLocation backgroundTexture;
    //private int number;

    public GSGuiMainMenu()
    {
        this.field_146972_A = field_96138_a;
        this.splashText = "missingno";
        BufferedReader bufferedreader = null;
        //number = rand.nextInt(3);
        try
        {
            ArrayList<String> arraylist = new ArrayList<String>();
            bufferedreader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(), Charsets.UTF_8));
            String s;

            while ((s = bufferedreader.readLine()) != null)
            {
                s = s.trim();

                if (!s.isEmpty())
                {
                    arraylist.add(s);
                }
            }

            if (!arraylist.isEmpty())
            {
                do
                {
                    this.splashText = (String)arraylist.get(rand.nextInt(arraylist.size()));
                }
                while (this.splashText.hashCode() == 125780783);
            }
        }
        catch (IOException ioexception1)
        {
            ;
        }
        finally
        {
            if (bufferedreader != null)
            {
                try
                {
                    bufferedreader.close();
                }
                catch (IOException ioexception)
                {
                    ;
                }
            }
        }

        this.updateCounter = rand.nextFloat();
        this.field_92025_p = "";

        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported())
        {
            this.field_92025_p = I18n.format("title.oldgl1", new Object[0]);
            this.field_146972_A = I18n.format("title.oldgl2", new Object[0]);
            this.field_104024_v = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char p_73869_1_, int p_73869_2_) {}

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.viewportTexture = new DynamicTexture(256, 256);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        if (calendar.get(2) + 1 == 11 && calendar.get(5) == 9)
        {
            this.splashText = "Happy birthday, ez!";
        }
        else if (calendar.get(2) + 1 == 6 && calendar.get(5) == 1)
        {
            this.splashText = "Happy birthday, Notch!";
        }
        else if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24)
        {
            this.splashText = "Merry X-mas!";
        }
        else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1)
        {
            this.splashText = "Happy new year!";
        }
        else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
        {
            this.splashText = "OOoooOOOoooo! Spooky!";
        }

        //boolean flag = true;
        int i = this.height / 2 - 118;

        if (this.mc.isDemo())
        {
            this.addDemoButtons(i, 24);
        }
        else
        {
            this.addSingleplayerMultiplayerButtons(i, 24);
        }


    	int l;
    	l = this.width - 150;

    	this.buttonList.add(new GSGuiButton(0, l, i + 71 + 24*3, 100, 20, I18n.format("menu.options", new Object[0])));
    	this.buttonList.add(new GSGuiButton(4, l, i + 71 + 24*4, 100, 20, I18n.format("menu.quit", new Object[0])));   
    	
    	
        //this.buttonList.add(new GuiButtonLanguage(5, this.width / 2 - 124, i + 72 + 12));
        //Object object = this.theardLock;

        synchronized (this.theardLock)
        {
            this.field_92023_s = this.fontRenderer.getStringWidth(this.field_92025_p);
            this.field_92024_r = this.fontRenderer.getStringWidth(this.field_146972_A);
            int j = Math.max(this.field_92023_s, this.field_92024_r);
            this.field_92022_t = (this.width - j) / 2;
            this.field_92021_u = ((GuiButton)this.buttonList.get(0)).y - 24;
            this.field_92020_v = this.field_92022_t + j;
            this.field_92019_w = this.field_92021_u + 24;
        }
    }

    /**
     * Adds Singleplayer and Multiplayer buttons on Main Menu for players who have bought the game.
     */
    private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_)
    {
    	int i;
    	i = this.width - 150;
        this.buttonList.add(new GSGuiButton(1, i, (p_73969_1_ + p_73969_2_) + 50, 100, 20, I18n.format("menu.singleplayer", new Object[0])));
        this.buttonList.add(new GSGuiButton(2, i, (p_73969_1_ + p_73969_2_ * 2) + 49, 100, 20, I18n.format("menu.multiplayer", new Object[0])));
        if(GSThreadVersionCheck.newversion) this.buttonList.add(new GSGuiButton(3, this.width / 2 - 65, 80, 150, 20, I18n.format("menu.update", new Object[0])));
        GuiButton realmsButton = new GuiButton(14, i, p_73969_1_ + p_73969_2_ * 2, I18n.format("menu.online", new Object[0]));
        int j = 5;
        GuiButton fmlModButton = new GSGuiButton(6, i, p_73969_1_ + p_73969_2_ * j, 100, 20,"Mods");
        realmsButton.width = 98;
        //this.buttonList.add(realmsButton);
        this.buttonList.add(fmlModButton);
        
        this.buttonList.add(new GSGuiButton(7, this.width - 65, this.height - 25, 60, 20, I18n.format("VK Group", new Object[0])));
        this.buttonList.add(new GSGuiButton(8, this.width - 65, this.height - 48, 60, 20, I18n.format("Discord", new Object[0])));
        //this.buttonList.add(new GSGuiButton(9, this.width - 70, this.height - 22, 60, 20, I18n.format("GC Forum", new Object[0])));
    }

    /**
     * Adds Demo buttons on Main Menu for players who are playing Demo.
     */
    private void addDemoButtons(int p_73972_1_, int p_73972_2_)
    {
        this.buttonList.add(new GuiButton(11, this.width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo", new Object[0])));
        this.buttonList.add(this.buttonResetDemo = new GuiButton(12, this.width / 2 - 100, p_73972_1_ + p_73972_2_ * 1, I18n.format("menu.resetdemo", new Object[0])));
        ISaveFormat isaveformat = this.mc.getSaveLoader();
        WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

        if (worldinfo == null)
        {
            this.buttonResetDemo.enabled = false;
        }
    }

    protected void actionPerformed(GuiButton p_146284_1_)
    {
        if (p_146284_1_.id == 0)
        {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        
        if (p_146284_1_.id == 3)
        {
        	this.urlopen("https://www.curseforge.com/minecraft/mc-mods/galaxy-space-addon-for-galacticraft");
        }

        if (p_146284_1_.id == 5)
        {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }

        if (p_146284_1_.id == 1)
        {
            this.mc.displayGuiScreen(new GuiWorldSelection(this));
        }
        
        if (p_146284_1_.id == 2)
        {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        	//FMLClientHandler.instance().connectToServer(new GuiMultiplayer(this), new ServerData("SpaceTechnology", "46.174.49.40:25681"));
        }

        if (p_146284_1_.id == 14)
        {
            this.func_140005_i();
        }

        if (p_146284_1_.id == 4)
        {
            this.mc.shutdown();
        }

        if (p_146284_1_.id == 6)
        {
            this.mc.displayGuiScreen(new GuiModList(this));
        }
/*
        if (p_146284_1_.id == 11)
        {
            this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.DEMO_WORLD_SETTINGS);
        }*/

        if (p_146284_1_.id == 12)
        {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

            if (worldinfo != null)
            {
                this.mc.displayGuiScreen(new GuiYesNo(this, I18n.format("selectWorld.deleteQuestion", new Object[0]), "\'" + worldinfo.getWorldName() + "\' " + I18n.format("selectWorld.deleteWarning", new Object[0]), I18n.format("selectWorld.deleteButton", new Object[0]), I18n.format("gui.cancel", new Object[0]), 12));
            }
        }
        
        if (p_146284_1_.id == 7)
        {
        	this.urlopen("https://vk.com/addon.galaxyspace");
        }
        
        if (p_146284_1_.id == 8)
        {
        	this.urlopen("https://discordapp.com/invite/8EHZdFZ");
        }
        
        if (p_146284_1_.id == 9)
        {
        	this.urlopen("https://forum.micdoodle8.com/index.php?threads/1-7-10-galaxy-space-stable.5298/");
        }
    }

    private void urlopen(String url)
    {
        try
        {
            Class<?> oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
            oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI(url)});
        }
        catch (Throwable throwable)
        {
            logger.error("Couldn\'t open link", throwable);
        }
    }
    
    private void func_140005_i()
    {
        RealmsBridge realmsbridge = new RealmsBridge();
        realmsbridge.switchToRealms(this);
    }

    public void confirmClicked(boolean p_73878_1_, int p_73878_2_)
    {
        if (p_73878_1_ && p_73878_2_ == 12)
        {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            isaveformat.flushCache();
            isaveformat.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        }
        else if (p_73878_2_ == 13)
        {
            if (p_73878_1_)
            {
                try
                {
                    Class<?> oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                    oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI(this.field_104024_v)});
                }
                catch (Throwable throwable)
                {
                    logger.error("Couldn\'t open link", throwable);
                }
            }

            this.mc.displayGuiScreen(this);
        }
    }

    /**
     * Draws the main menu panorama
     */
    private void drawPanorama(int mouseX, int mouseY, float partialTicks)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        int i = 8;

        for (int j = 0; j < 64; ++j)
        {
            GlStateManager.pushMatrix();
            float f = ((float)(j % 8) / 8.0F - 0.5F) / 64.0F;
            float f1 = ((float)(j / 8) / 8.0F - 0.5F) / 64.0F;
            float f2 = 0.0F;
            GlStateManager.translate(f, f1, 0.0F);
            GlStateManager.rotate(MathHelper.sin(this.panoramaTimer / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-this.panoramaTimer * 0.1F, 0.0F, 1.0F, 0.0F);

            for (int k = 0; k < 6; ++k)
            {
                GlStateManager.pushMatrix();

                if (k == 1)
                {
                    GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (k == 2)
                {
                    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                }

                if (k == 3)
                {
                    GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (k == 4)
                {
                    GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                }

                if (k == 5)
                {
                    GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
                }

                this.mc.getTextureManager().bindTexture(PANORAMA_PATHS[k]);
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                int l = 255 / (j + 1);
                bufferbuilder.pos(-1.0D, -1.0D, 1.0D).tex(0.0D, 0.0D).color(255, 255, 255, l).endVertex();
                bufferbuilder.pos(1.0D, -1.0D, 1.0D).tex(1.0D, 0.0D).color(255, 255, 255, l).endVertex();
                bufferbuilder.pos(1.0D, 1.0D, 1.0D).tex(1.0D, 1.0D).color(255, 255, 255, l).endVertex();
                bufferbuilder.pos(-1.0D, 1.0D, 1.0D).tex(0.0D, 1.0D).color(255, 255, 255, l).endVertex();
                tessellator.draw();
                GlStateManager.popMatrix();
            }

            GlStateManager.popMatrix();
            GlStateManager.colorMask(true, true, true, false);
        }

        bufferbuilder.setTranslation(0.0D, 0.0D, 0.0D);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
    }

    /**
     * Rotate and blurs the skybox view in the main menu
     */
    private void rotateAndBlurSkybox()
    {
        this.mc.getTextureManager().bindTexture(this.backgroundTexture);
        GlStateManager.glTexParameteri(3553, 10241, 9729);
        GlStateManager.glTexParameteri(3553, 10240, 9729);
        GlStateManager.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.colorMask(true, true, true, false);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        GlStateManager.disableAlpha();
        int i = 3;

        for (int j = 0; j < i; ++j)
        {
            float f = 1.0F / (float)(j + 1);
            int k = this.width;
            int l = this.height;
            float f1 = (float)(j - 1) / 256.0F;
            bufferbuilder.pos((double)k, (double)l, (double)this.zLevel).tex((double)(0.0F + f1), 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            bufferbuilder.pos((double)k, 0.0D, (double)this.zLevel).tex((double)(1.0F + f1), 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            bufferbuilder.pos(0.0D, 0.0D, (double)this.zLevel).tex((double)(1.0F + f1), 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            bufferbuilder.pos(0.0D, (double)l, (double)this.zLevel).tex((double)(0.0F + f1), 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
        }

        tessellator.draw();
        GlStateManager.enableAlpha();
        GlStateManager.colorMask(true, true, true, true);
    }

    /**
     * Renders the skybox in the main menu
     */
    private void renderSkybox(int mouseX, int mouseY, float partialTicks)
    {
        this.mc.getFramebuffer().unbindFramebuffer();
        GlStateManager.viewport(0, 0, 256, 256);
        this.drawPanorama(mouseX, mouseY, partialTicks);
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();

        this.mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        float f = 120.0F / (float)(this.width > this.height ? this.width : this.height);
        float f1 = (float)this.height * f / 256.0F;
        float f2 = (float)this.width * f / 256.0F;
        int i = this.width;
        int j = this.height;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(0.0D, (double)j, (double)this.zLevel).tex((double)(0.5F - f1), (double)(0.5F + f2)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        bufferbuilder.pos((double)i, (double)j, (double)this.zLevel).tex((double)(0.5F - f1), (double)(0.5F - f2)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        bufferbuilder.pos((double)i, 0.0D, (double)this.zLevel).tex((double)(0.5F + f1), (double)(0.5F - f2)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, (double)this.zLevel).tex((double)(0.5F + f1), (double)(0.5F + f2)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        tessellator.draw();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
    	this.panoramaTimer += partialTicks;
    	
        GlStateManager.disableAlpha();
        this.renderSkybox(mouseX, mouseY, partialTicks);
        GlStateManager.enableAlpha();
        /*
        Calendar calendar = Calendar.getInstance();
        
        if (calendar.get(2) + 1 == 10 && calendar.get(5) >= 30 && calendar.get(5) <= 31 || calendar.get(2) + 1 == 11 && calendar.get(5) <= 1)
        {
        	mc.renderEngine.bindTexture(background[3]);
        }
        else mc.renderEngine.bindTexture(background[number]);
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(0 + 0, 0 + height, zLevel).tex(0,1).endVertex();
        vertexbuffer.pos(0 + width, 0 + height, zLevel).tex(1, 1).endVertex();
        vertexbuffer.pos(0 + width, 0 + 0, zLevel).tex(1,0).endVertex();
        vertexbuffer.pos(0 + 0, 0 + 0, zLevel).tex(0, 0).endVertex();
		tessellator.draw();
		*/
		//rotateAndBlurSkybox();
		
        short short1 = 274;
        int k = this.width / 2 - short1 / 2;
        byte b0 = 30;
        //this.drawGradientRect(0, 0, this.width, this.height, -2130706433, 16777215);
        //this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
        this.mc.getTextureManager().bindTexture(minecraftTitleTextures);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        if ((double)this.updateCounter < 1.0E-4D)
        {
            this.drawTexturedModalRect(k + 0, b0 + 0, 0, 0, 99, 44);
            this.drawTexturedModalRect(k + 99, b0 + 0, 129, 0, 27, 44);
            this.drawTexturedModalRect(k + 99 + 26, b0 + 0, 126, 0, 3, 44);
            this.drawTexturedModalRect(k + 99 + 26 + 3, b0 + 0, 99, 0, 26, 44);
            this.drawTexturedModalRect(k + 155, b0 + 0, 0, 45, 155, 44);
        }
        else
        {
            this.drawTexturedModalRect(k + 0, b0 + 0, 0, 0, 155, 44);
            this.drawTexturedModalRect(k + 155, b0 + 0, 0, 45, 155, 44);
        }
            
        
        this.mc.getTextureManager().bindTexture(edition);
        drawModalRectWithCustomSizedTexture(k + 88, 67, 0.0F, 0.0F, 98, 14, 128.0F, 16.0F);
        
        List<String> brandings = Lists.reverse(FMLCommonHandler.instance().getBrandings(true));
        
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(0.0F, 0.6F, 1.0F, 0.8F);
        this.mc.getTextureManager().bindTexture(mainmenu1);
        this.drawTexturedModalRect(this.width - 181, this.height / 2 - 110, 189, 224, 299, 60, 222, 374, false, false, 512, 512);
        
        //Shadow
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 1);
        this.drawTexturedModalRect(this.width - 59, this.height / 2 - 87, 290, 50, 440, 5, 290, 50, false, false, 512, 512);
        this.drawTexturedModalRect(this.width - 188, this.height / 2 - 87, 180, 50, 250, 5, 180, 50, false, false, 512, 512);
                
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1);
        this.drawTexturedModalRect(this.width - 191, this.height / 2 - 90, 180, 50, 250, 5, 180, 50, false, false, 512, 512);
       
        GL11.glColor4f(0.0F, 0.6F, 1.0F, 1);
        this.drawTexturedModalRect(this.width - 61, this.height / 2 - 90, 290, 50, 440, 5, 290, 50, false, false, 512, 512);
        
        GL11.glColor4f(0.0F, 0.6F, 1.0F, 0.8F);
        //this.drawTexturedModalRect(-10, this.height - 20 * brandings.size(), 220, 112, 299, 60, 222, 374 / 2 - 50, true, false, 512, 512);
       
        this.drawTexturedModalRect(this.width - 220, this.height - 55, 194, 224 / 2 - 30, 299, 60, 222, 374 / 2 - 50, false, false, 512, 512);
        this.drawTexturedModalRect(this.width - 34, this.height - 73, 35, 224 / 2 - 30, 359, 60, 60, 374 / 2 - 50, false, false, 512, 512);
        
        //this.drawTexturedModalRect(-20, this.height - 225, 119, 84, 299, 60, 122, 114, true, false, 512, 512);
        //this.drawTexturedModalRect(-20, this.height - 141, 119, 54, 299, 330, 122, 84, true, false, 512, 512);
       
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.width / 2 + 90), 70.0F, 0.0F);
        GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
        float f1 = 1.8F - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0F * (float)Math.PI * 2.0F) * 0.1F);
        f1 = f1 * 100.0F / (float)(this.fontRenderer.getStringWidth(this.splashText) + 32);
        GL11.glScalef(f1, f1, f1);
       // this.drawCenteredString(this.fontRendererObj, this.splashText, 0, -8, -512);
        GL11.glPopMatrix();
               
        String s1 = "Copyright Mojang AB. Do not distribute!";
        List<String> brandings_new = new ArrayList<String>();
        brandings_new.add(brandings.get(0));
        brandings_new.add(brandings.get(1));
        brandings_new.add(brandings.get(3) + " | " +brandings.get(2));
        for (int i = 0; i < brandings_new.size(); i++)
        {        	
            String brd = brandings_new.get(i);            
            if (!Strings.isNullOrEmpty(brd))
            {
                this.drawString(this.fontRenderer, brd, this.width - 185, this.height - ( 10 + i * (this.fontRenderer.FONT_HEIGHT + 1)), 16777215);
            }
        }
        //ForgeHooksClient.renderMainMenu(this, fontRendererObj, width, height);
      
        this.drawString(this.fontRenderer, s1, 2, this.height - 10, -1);

        this.drawString(this.fontRenderer, "Ver. " + GalaxySpace.VERSION, this.width - this.fontRenderer.getStringWidth(GalaxySpace.VERSION) - 35, this.height / 2 - 53, -1);
        
        this.drawString(this.fontRenderer, "AsmodeusCore", this.width - 130 , this.height - 56, Utils.getIntColorWHC(0, 250, 200, 255));
        String version = "0.0.0";
        for (ModContainer mod : Loader.instance().getModList())
        {
        	if(mod.getName().contains("AsmodeusCore")) {
        		version = mod.getVersion();
        		break;
        	}
        }
        this.drawString(this.fontRenderer, "Ver. " + version, this.width - version.length() * 5 - 100 , this.height - 46, -1);

        if (this.field_92025_p != null && this.field_92025_p.length() > 0)
        {
            drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 1428160512);
            this.drawString(this.fontRenderer, this.field_92025_p, this.field_92022_t, this.field_92021_u, -1);
            this.drawString(this.fontRenderer, this.field_146972_A, (this.width - this.field_92024_r) / 2, ((GuiButton)this.buttonList.get(0)).y - 12, -1);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        //Object object = this.theardLock;

        synchronized (this.theardLock)
        {
            if (this.field_92025_p.length() > 0 && mouseX >= this.field_92022_t && mouseX <= this.field_92020_v && mouseY >= this.field_92021_u && mouseY <= this.field_92019_w)
            {
                GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.field_104024_v, 13, true);
                guiconfirmopenlink.disableSecurityWarning();
                this.mc.displayGuiScreen(guiconfirmopenlink);
            }
        }
/*
        if (mouseX > this.widthCopyrightRest && mouseX < this.widthCopyrightRest + this.widthCopyright && mouseY > this.height - 10 && mouseY < this.height)
        {
            this.mc.displayGuiScreen(new GuiWinGame(false, Runnables.doNothing()));
        } */
    }

    
    public void drawTexturedModalRect(float x, float y, float width, float height, float u, float v, float uWidth, float vHeight, boolean invertX, boolean invertY, float texSizeX, float texSizeY)
    {
       // GL11.glShadeModel(GL11.GL_FLAT);
       // GL11.glEnable(GL11.GL_BLEND);
        //GL11.glEnable(GL11.GL_ALPHA_TEST);
        //GL11.glEnable(GL11.GL_TEXTURE_2D);
        float texModX = 1F / texSizeX;
        float texModY = 1F / texSizeY;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        float height0 = invertY ? 0 : vHeight;
        float height1 = invertY ? vHeight : 0;
        float width0 = invertX ? uWidth : 0;
        float width1 = invertX ? 0 : uWidth;
        vertexbuffer.pos(x, y + height, this.zLevel).tex((u + width0) * texModX, (v + height0) * texModY).endVertex();
        vertexbuffer.pos(x + width, y + height, this.zLevel).tex((u + width1) * texModX, (v + height0) * texModY).endVertex();
        vertexbuffer.pos(x + width, y, this.zLevel).tex((u + width1) * texModX, (v + height1) * texModY).endVertex();
        vertexbuffer.pos(x, y, this.zLevel).tex((u + width0) * texModX, (v + height1) * texModY).endVertex();
        tessellator.draw();
    }
}