package galaxyspace.core.client.render.sky;

import java.util.Calendar;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import galaxyspace.GalaxySpace;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.util.GSUtils;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProviderSurface;
import net.minecraftforge.client.IRenderHandler;

public abstract class SkyProviderBase extends IRenderHandler
{
	
	//TODO NEED CREATE GLOBAL TESSELATOR!!!!
	
    private static final ResourceLocation sunTexture = new ResourceLocation("textures/environment/sun.png");
    private static final ResourceLocation lmcTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/environment/background/LMC.png");
    private static final ResourceLocation smcTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/environment/background/SMC.png");
    private static final ResourceLocation andromedaTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/environment/background/Andromeda.png");
    private static final ResourceLocation moonTexture = new ResourceLocation("textures/environment/moon_phases.png");
    private static final ResourceLocation barnardaloopTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/environment/background/BarnardaLoop.png");
    //private static final ResourceLocation milkywayTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/environment/background/MilkyWay.png");
    public static final ResourceLocation pumpkinsunTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/environment/pumkinsun.png");
    
    private final ResourceLocation planetToRender = new ResourceLocation(GalacticraftCore.ASSET_PREFIX, "textures/gui/celestialbodies/earth.png");
    
    public int starList;
    public int glSkyList;
    public int glSkyList2;
    private float sunSize;
    protected float ticks;
    public float[] afloat = new float[4];
    
    protected Minecraft mc = Minecraft.getMinecraft();
    
    public SkyProviderBase()
    {
    	int displayLists = GLAllocation.generateDisplayLists(3);
        this.starList = displayLists;
        this.glSkyList = displayLists + 1;
        this.glSkyList2 = displayLists + 2;

        // Bind stars to display list
        GL11.glPushMatrix();
        GL11.glNewList(this.starList, GL11.GL_COMPILE);
        if(enableStar()) this.renderStars();
        GL11.glEndList();
        GL11.glPopMatrix();

        final Tessellator tessellator = Tessellator.instance;
        GL11.glNewList(this.glSkyList, GL11.GL_COMPILE);
        final byte byte2 = 64;
        final int i = 256 / byte2 + 2;
        float f = 16F;

        for (int j = -byte2 * i; j <= byte2 * i; j += byte2)
        {
            for (int l = -byte2 * i; l <= byte2 * i; l += byte2)
            {
                tessellator.startDrawingQuads();
                tessellator.addVertex(j + 0, f, l + 0);
                tessellator.addVertex(j + byte2, f, l + 0);
                tessellator.addVertex(j + byte2, f, l + byte2);
                tessellator.addVertex(j + 0, f, l + byte2);
                tessellator.draw();
            }
        }

        GL11.glEndList();
        GL11.glNewList(this.glSkyList2, GL11.GL_COMPILE);
        f = -16F;
        tessellator.startDrawingQuads();

        for (int k = -byte2 * i; k <= byte2 * i; k += byte2)
        {
            for (int i1 = -byte2 * i; i1 <= byte2 * i; i1 += byte2)
            {
                tessellator.addVertex(k + byte2, f, i1 + 0);
                tessellator.addVertex(k + 0, f, i1 + 0);
                tessellator.addVertex(k + 0, f, i1 + byte2);
                tessellator.addVertex(k + byte2, f, i1 + byte2);
            }
        }

        tessellator.draw();
        GL11.glEndList();
    }

    @Override
    public void render(float partialTicks, WorldClient world, Minecraft mc)
    {
    	this.ticks = partialTicks;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        Vec3 vec3 = world.getSkyColor(mc.renderViewEntity, partialTicks);
        float f1 = (float) vec3.xCoord;
        float f2 = (float) vec3.yCoord;
        float f3 = (float) vec3.zCoord;
        float f6;

        if (mc.gameSettings.anaglyph)
        {
            float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
            float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
            f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
            f1 = f4;
            f2 = f5;
            f3 = f6;
        }

        GL11.glColor3f(f1, f2, f3);
        Tessellator tessellator1 = Tessellator.instance;
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_FOG);
        GL11.glColor3f(f1, f2, f3);
        GL11.glCallList(this.glSkyList);
        GL11.glDisable(GL11.GL_FOG);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        RenderHelper.disableStandardItemLighting();
        float f7;
        float f8;
        float f9 = 0;
        float f10 = 0;

        float rain = 1.0F - world.getRainStrength(partialTicks);       
        float f18 = world.getStarBrightness(partialTicks) * rain;


        if (f18 > 0.0F && !inWater(this.mc.thePlayer))
        {
        	 GL11.glPushMatrix();
        	 GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        	 GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
        	 GL11.glRotatef(-19.0F, 0, 1.0F, 0);
        	 GL11.glColor4f(f18, f18, f18, f18);
        	 GL11.glCallList(this.starList);
        	 GL11.glPopMatrix();
        }

       
        
        GL11.glPushMatrix();
        //TODO Sun Aura
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        float f11;

        afloat[0] = 255 / 255.0F;
        afloat[1] = 194 / 255.0F;
        afloat[2] = 180 / 255.0F;
        afloat[3] = 0.3F;
        if(this.colorSunAura() != null)
        {
        	afloat[0] = this.colorSunAura().intX() / 255.0F;
            afloat[1] = this.colorSunAura().intY()  / 255.0F;
            afloat[2] = this.colorSunAura().intZ()  / 255.0F;
        }
        f6 = afloat[0];
        f7 = afloat[1];
        f8 = afloat[2];
        
        if (mc.gameSettings.anaglyph)
        {
            f9 = (f6 * 30.0F + f7 * 59.0F + f8 * 11.0F) / 100.0F;
            f10 = (f6 * 30.0F + f7 * 70.0F) / 100.0F;
            f11 = (f6 * 30.0F + f8 * 70.0F) / 100.0F;
            f6 = f9;
            f7 = f10;
            f8 = f11;
        }

        f18 = 1.0F - f18;
        
        GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(this.mc.theWorld.getCelestialAngle(this.ticks) * 360.0F, 1.0F, 0.0F, 0.0F);
        f10 = sunSize() + 5.5F;
        if(!inWater(this.mc.thePlayer)) this.renderSunAura(tessellator1, f10 + addSizeAura(), f18);
        GL11.glPopMatrix();
        
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE, GL11.GL_ZERO);
        GL11.glPushMatrix();

        f7 = 0.0F;
        f8 = 0.0F;
        f9 = 0.0F;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F - Minecraft.getMinecraft().theWorld.getRainStrength(partialTicks));
        GL11.glTranslatef(f7, f8, f9);
        GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
        // Render sun
                
        if(this.modeLight() != 2 &&  !inWater(this.mc.thePlayer))
        {
	        f10 = sunSize();
	        Calendar calendar = Calendar.getInstance();
	        if (calendar.get(2) + 1 == 10 && calendar.get(5) >= 30 && calendar.get(5) <= 31 || calendar.get(2) + 1 == 11 && calendar.get(5) <= 1)
	        {
	        	FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.pumpkinsunTexture);
	        }
	        else if(this.sunImage() != null) FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.sunImage());
	        else FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.sunTexture);
	        if(enableSmoothRender()) GL11.glDisable(GL11.GL_BLEND);
	        tessellator1.startDrawingQuads();
	        tessellator1.addVertexWithUV(-f10, 100.0D, -f10, 0.0D, 0.0D);
	        tessellator1.addVertexWithUV(f10, 100.0D, -f10, 1.0D, 0.0D);
	        tessellator1.addVertexWithUV(f10, 100.0D, f10, 1.0D, 1.0D);
	        tessellator1.addVertexWithUV(-f10, 100.0D, f10, 0.0D, 1.0D);
	        tessellator1.draw();
	        if(enableSmoothRender()) GL11.glEnable(GL11.GL_BLEND);
        }
	    if(this.enableBaseImages() && !inWater(this.mc.thePlayer) && GSConfigCore.enableRenderImgOnSky)
	    {
	        if(Minecraft.getMinecraft().theWorld.provider instanceof WorldProviderSurface)
	        {
		        f10 = 20.0F;
		        FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.moonTexture);
		        int k = Minecraft.getMinecraft().theWorld.getMoonPhase();
		        int l = k % 4;
		        int i1 = k / 4 % 2;
		        float f14 = (float)(l + 0) / 4.0F;
		        float f15 = (float)(i1 + 0) / 2.0F;
		        float f16 = (float)(l + 1) / 4.0F;
		        float f17 = (float)(i1 + 1) / 2.0F;
		        tessellator1.startDrawingQuads();
		        tessellator1.addVertexWithUV((double)(-f10), -100.0D, (double)f10, (double)f16, (double)f17);
		        tessellator1.addVertexWithUV((double)f10, -100.0D, (double)f10, (double)f14, (double)f17);
		        tessellator1.addVertexWithUV((double)f10, -100.0D, (double)(-f10), (double)f14, (double)f15);
		        tessellator1.addVertexWithUV((double)(-f10), -100.0D, (double)(-f10), (double)f16, (double)f15);
		        tessellator1.draw();       
	        }
	        float light = 0.0F;
	        
	        if(this.modeLight() == 0) light = FMLClientHandler.instance().getClient().theWorld.getStarBrightness(1.0F) - Minecraft.getMinecraft().theWorld.getRainStrength(partialTicks);
	        if(this.modeLight() == 1) light = 1.0F;
	       	        
	        this.renderImage(this.lmcTexture, -90.0F, 90.0F, 0.0F, 15.0F, true,  light);
		    this.renderImage(this.smcTexture, 0.0F, -40.0F, 0.0F, 5.0F, true,  light);
		    this.renderImage(this.andromedaTexture, 100.0F, -150.0F, 0.0F, 4.0F, true, light);
		    this.renderImage(this.barnardaloopTexture, 200.0F, -70.0F, 0.0F, 40.0F, true, light);
		        
	        //this.renderImage(this.milkywayTexture, 200.0F, -70.0F, 0.0F, 200.0F, true, 1.0F);
        
	        GL11.glDisable(GL11.GL_BLEND);
        
        	this.rendererSky(tessellator1, f10, partialTicks);
	    }
        
        GL11.glPopMatrix(); 
        GL11.glPushMatrix();
        if((float)mc.thePlayer.posY > 200 && enableRenderPlanet())
        {
        	 
        	//GL11.glEnable(GL11.GL_BLEND);
        	GL11.glEnable(GL11.GL_TEXTURE_2D);
        	float i = (float)(Math.round(mc.thePlayer.posY - 200)) / 10;
        	
		    f10 = 116 - (i > 115 ? 115 : i);
		    GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
		    GL11.glRotatef(-360F, 1.0F, 0.0F, 0.0F);
		    //GL11.glScaled(i, i, i);
		    //GL11.glTranslatef(0.0F, -(float)mc.thePlayer.posY / 8, 0.0F);
		        
		   	if(!(mc.theWorld.provider instanceof WorldProviderSurface)) mc.renderEngine.bindTexture(((IGalacticraftWorldProvider)mc.theWorld.provider).getCelestialBody().getBodyIcon());
		   	else mc.renderEngine.bindTexture(this.planetToRender);
		        	       
		   	
		   	GL11.glColor4f(i / 10, i / 10, i / 10, 1.0F);
            
		   	tessellator1.startDrawingQuads(); 
			tessellator1.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
			tessellator1.addVertexWithUV(f10, -100.0D, f10, 1, 1);
			tessellator1.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
			tessellator1.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
			tessellator1.draw();
			  
			this.renderAtmo(tessellator1, -360.0F, 0.0F, f10 - 5, this.getAtmosphereColor());
		   
        }
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_FOG);
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor3f(0.0F, 0.0F, 0.0F);
        double d0 = mc.thePlayer.getPosition(partialTicks).yCoord - world.getHorizon();

        /*if (d0 < 0.0D)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 12.0F, 0.0F);
            GL11.glCallList(this.glSkyList2);
            GL11.glPopMatrix();
            f8 = 1.0F;
            f9 = -((float) (d0 + 65.0D));
            f10 = -f8;
            tessellator1.startDrawingQuads();
            tessellator1.setColorRGBA_I(0, 255);
            tessellator1.addVertex(-f8, f9, f8);
            tessellator1.addVertex(f8, f9, f8);
            tessellator1.addVertex(f8, f10, f8);
            tessellator1.addVertex(-f8, f10, f8);
            tessellator1.addVertex(-f8, f10, -f8);
            tessellator1.addVertex(f8, f10, -f8);
            tessellator1.addVertex(f8, f9, -f8);
            tessellator1.addVertex(-f8, f9, -f8);
            tessellator1.addVertex(f8, f10, -f8);
            tessellator1.addVertex(f8, f10, f8);
            tessellator1.addVertex(f8, f9, f8);
            tessellator1.addVertex(f8, f9, -f8);
            tessellator1.addVertex(-f8, f9, -f8);
            tessellator1.addVertex(-f8, f9, f8);
            tessellator1.addVertex(-f8, f10, f8);
            tessellator1.addVertex(-f8, f10, -f8);
            tessellator1.addVertex(-f8, f10, -f8);
            tessellator1.addVertex(-f8, f10, f8);
            tessellator1.addVertex(f8, f10, f8);
            tessellator1.addVertex(f8, f10, -f8);
            tessellator1.draw();
        }*/

        if (world.provider.isSkyColored())
        {
            GL11.glColor3f(f1 * 0.2F + 0.04F, f2 * 0.2F + 0.04F, f3 * 0.6F + 0.1F);
        }
        else
        {
            GL11.glColor3f(f1, f2, f3);
        }

        //GL11.glPushMatrix();
        //GL11.glTranslatef(0.0F, -((float) (d0 - 16.0D)), 0.0F);
        //GL11.glCallList(this.glSkyList2);
        //GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDepthMask(true);
    }

    public static void renderTestWithUV(Tessellator tess, double yMax, double xMin, double zMin, double xMax, double zMax, double uMin, double uMax, double vMin, double vMax) {
		tess.setNormal(0, 1, 0);
		tess.addVertexWithUV(xMin, yMax, zMin, uMin, vMin);
		tess.addVertexWithUV(xMin, yMax, zMax, uMin, vMax);
		tess.addVertexWithUV(xMax, yMax, zMax, uMax, vMax);
		tess.addVertexWithUV(xMax, yMax, zMin, uMax, vMin);		
	}
    
    private boolean inWater(EntityPlayer player)
    {
    	Vec3 vec = player.getPosition(1.0F);
    	
    	return mc.theWorld.getBlock((int)vec.xCoord, (int)vec.yCoord, (int)vec.zCoord).getMaterial() == Material.water;
    }
    
    protected void renderImage(ResourceLocation image, float x, float y, float z,float f10, boolean withsun)
    {
    	this.renderImage(image, x, y, z, f10, withsun, FMLClientHandler.instance().getClient().theWorld.getStarBrightness(1.0F));
    }
    
    protected void renderImage(ResourceLocation image, float x, float y, float z, float f10, boolean withsun, float alpha)
    {
    	
    		GL11.glEnable(GL11.GL_BLEND);

	    	if(!withsun)
	    	{
		    	GL11.glPopMatrix();
		    	GL11.glPushMatrix();
	    	}
	    	
	    	Tessellator tessellator1 = Tessellator.instance;

	    	GL11.glRotatef(x, 0.0F, 1.0F, 0.0F); //������������ �� X
	    	GL11.glRotatef(y, 1.0F, 0.0F, 0.0F); //������������ �� Y
	    	GL11.glRotatef(z, 0.0F, 0.0F, 1.0F); //������������ �� Z
	    	GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
	    	FMLClientHandler.instance().getClient().renderEngine.bindTexture(image);
	    	tessellator1.startDrawingQuads();
	    	tessellator1.addVertexWithUV(-f10, -100.0D, f10, 0, 1);
	    	tessellator1.addVertexWithUV(f10, -100.0D, f10, 1, 1);
	    	tessellator1.addVertexWithUV(f10, -100.0D, -f10, 1, 0);
	    	tessellator1.addVertexWithUV(-f10, -100.0D, -f10, 0, 0);
	    	
	    	tessellator1.draw();
    	
    	
    }
    protected void renderAtmo(Tessellator tessellator1, float x, float y,float f10, Vector3 vec)
    {    	
    	if(vec != null && GSConfigCore.enableRenderAtmosphere)
		{     
 		   GL11.glEnable(GL11.GL_BLEND);
 		   GL11.glDisable(GL11.GL_TEXTURE_2D);
 		   GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);                
 		        
 		   GL11.glRotatef(y, 0.0F, 0.0F, 1.0F);
	       GL11.glRotatef(x, 1.0F, 0.0F, 0.0F);
	       
 		   double planetOrbitalDistance = 5.0D;
 		    
 		   double dist = (-64D - 4*(planetOrbitalDistance)/2D)-((float)mc.thePlayer.posY / ((float)mc.thePlayer.posY));
 		   double scalingMult = 1D - 0.9*(planetOrbitalDistance);
 				
 		   float Xoffset = (float)((System.currentTimeMillis()/1000000d % 1));
 		
 		   float f14 = 1f + Xoffset;
 		   float f15 = 0f + Xoffset;
 		   float f16 = f15;
 		   float f17 = f14;
 		   float[] color = new float[3];
 				
 		   
 			   color[0] = vec.floatX();
 			   color[1] = vec.floatY();
 			   color[2] = vec.floatZ();
 			   
 			 		  
 			   tessellator1.startDrawingQuads();  
 			   tessellator1.setColorRGBA_F(color[0], color[1], color[2], 0.09F);
 					
 			   for(int i = 0; i < 5 ; i++) {
 				   renderTestWithUV(tessellator1, dist + i*scalingMult, -f10, -f10, 0, 0, f14, f15, f16, f17);
 				   renderTestWithUV(tessellator1, dist + i*scalingMult, 0, 0, f10, f10, f14, f15, f16, f17);
 				   renderTestWithUV(tessellator1, dist + i*scalingMult, -f10, 0, 0, f10, f14, f15, f16, f17);
 				   renderTestWithUV(tessellator1, dist + i*scalingMult, 0, -f10, f10, 0, f14, f15, f16, f17);
 			   }
 			   tessellator1.draw();
 		  }
 	        
 	    
    }
    protected void renderSunAura(Tessellator tessellator1, float f10, float f18)
    {
    	GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
    	Vec3 vec3 = this.mc.theWorld.getSkyColor(mc.renderViewEntity, this.ticks);
		float f1 = (float) vec3.xCoord;
		float f2 = (float) vec3.yCoord;
		float f3 = (float) vec3.zCoord;
		float f6;
		float f7;
		float f8;
		float f9 = 0;
		float f11;

		if (mc.gameSettings.anaglyph) {
			float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
			float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
			f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
			f1 = f4;
			f2 = f5;
			f3 = f6;
		}
		
		afloat[0] = 255 / 255.0F;
		afloat[1] = 194 / 255.0F;
		afloat[2] = 180 / 255.0F;
		afloat[3] = 0.3F;
		if (this.colorSunAura() != null) {
			afloat[0] = this.colorSunAura().intX() / 255.0F;
			afloat[1] = this.colorSunAura().intY() / 255.0F;
			afloat[2] = this.colorSunAura().intZ() / 255.0F;
		}
		
		f6 = afloat[0];
		f7 = afloat[1];
		f8 = afloat[2];

		if (mc.gameSettings.anaglyph) {
			f9 = (f6 * 30.0F + f7 * 59.0F + f8 * 11.0F) / 100.0F;
			f10 = (f6 * 30.0F + f7 * 70.0F) / 100.0F;
			f11 = (f6 * 30.0F + f8 * 70.0F) / 100.0F;
			f6 = f9;
			f7 = f10;
			f8 = f11;
		}
    	
    	//GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
    	GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        
        
        
        if(this.modeLight() != 2)
        {
	        tessellator1.startDrawing(GL11.GL_TRIANGLE_FAN);
	        tessellator1.setColorRGBA_F(f6 * f18, f7 * f18, f8 * f18, afloat[3] * 2 / f18 - Minecraft.getMinecraft().theWorld.getRainStrength(this.ticks));
	        tessellator1.addVertex(0.0D, 100.0D, 0.0D);
	        byte b0 = 16;
	        tessellator1.setColorRGBA_F(afloat[0] * f18, afloat[1] * f18, afloat[2] * f18, 0.0F );

	        // Render sun aura
	        tessellator1.addVertex(-f10, 100.0D, -f10);
	        tessellator1.addVertex(0, 100.0D, (double) -f10 * 1.5F);
	        tessellator1.addVertex(f10, 100.0D, -f10);
	        tessellator1.addVertex((double) f10 * 1.5F, 100.0D, 0);
	        tessellator1.addVertex(f10, 100.0D, f10);
	        tessellator1.addVertex(0, 100.0D, (double) f10 * 1.5F);
	        tessellator1.addVertex(-f10, 100.0D, f10);
	        tessellator1.addVertex((double) -f10 * 1.5F, 100.0D, 0);
	        tessellator1.addVertex(-f10, 100.0D, -f10);
       
	        tessellator1.draw();
        }
	    
        if(enableSmoothRender())
        {
        	GL11.glLineWidth(2);
        	GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        }
	 
        if(enableLargeSunAura()) {
		    tessellator1.startDrawing(GL11.GL_TRIANGLE_FAN);
		    if(enableSmoothRender())
		    	tessellator1.setColorRGBA_F(f6 * f18, f7 * f18, f8 * f18, afloat[3] * 2 / f18 - Minecraft.getMinecraft().theWorld.getRainStrength(this.ticks));
	        else tessellator1.setColorRGBA_F(f6 * f18, f7 * f18, f8 * f18, afloat[3] * f18);
		    tessellator1.addVertex(0.0D, 100.0D, 0.0D);
		    tessellator1.setColorRGBA_F(afloat[0] * f18, afloat[1] * f18, afloat[2] * f18, 0.0F);
	       
	        // Render larger sun aura
	        f10 = f10 + 10.0F;
	        int i = enableSmoothRender() ? 8 : 0;
	        f10 += i;
	        tessellator1.addVertex(-f10, 100.0D, -f10);
	        tessellator1.addVertex(0, 100.0D, (double) -f10 * 1.5F);
	        f10 -= i + i;
	        tessellator1.addVertex(f10, 100.0D, -f10);
	        tessellator1.addVertex((double) f10 * 1.5F, 100.0D, 0);
	        f10 += i;
	        tessellator1.addVertex(f10, 100.0D, f10);
	        tessellator1.addVertex(0, 100.0D, (double) f10 * 1.5F);
	        f10 -= i;
	        tessellator1.addVertex(-f10, 100.0D, f10);
	        tessellator1.addVertex((double) -f10 * 1.5F, 100.0D, 0);
	        tessellator1.addVertex(-f10, 100.0D, -f10);
	
	        tessellator1.draw();
        }
        
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
        
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    private void renderStars()
    {
        final Random rand = new Random(10842L);
        final Tessellator var2 = Tessellator.instance;
	        var2.startDrawingQuads();
	
	        for (int starIndex = 0; starIndex < (ConfigManagerCore.moreStars ? 48000 : 6000); ++starIndex)
	        {
	            double var4 = rand.nextFloat() * 2.0F - 1.0F;
	            double var6 = rand.nextFloat() * 2.0F - 1.0F;
	            double var8 = rand.nextFloat() * 2.0F - 1.0F;
	            final double var10 = 0.15F + rand.nextFloat() * 0.1F;
	            double var12 = var4 * var4 + var6 * var6 + var8 * var8;
	
	            if (var12 < 1.0D && var12 > 0.01D)
	            {
	                var12 = 1.0D / Math.sqrt(var12);
	                var4 *= var12;
	                var6 *= var12;
	                var8 *= var12;
	                final double var14 = var4 * (ConfigManagerCore.moreStars ? rand.nextDouble() * 150D + 130D : 100.0D);
	                final double var16 = var6 * (ConfigManagerCore.moreStars ? rand.nextDouble() * 150D + 130D : 100.0D);
	                final double var18 = var8 * (ConfigManagerCore.moreStars ? rand.nextDouble() * 150D + 130D : 100.0D);
	                final double var20 = Math.atan2(var4, var8);
	                final double var22 = Math.sin(var20);
	                final double var24 = Math.cos(var20);
	                final double var26 = Math.atan2(Math.sqrt(var4 * var4 + var8 * var8), var6);
	                final double var28 = Math.sin(var26);
	                final double var30 = Math.cos(var26);
	                final double var32 = rand.nextDouble() * Math.PI * 2.0D;
	                final double var34 = Math.sin(var32);
	                final double var36 = Math.cos(var32);
	
	                for (int var38 = 0; var38 < 4; ++var38)
	                {
	                    final double var39 = 0.0D;
	                    final double var41 = ((var38 & 2) - 1) * var10;
	                    final double var43 = ((var38 + 1 & 2) - 1) * var10;
	                    final double var47 = var41 * var36 - var43 * var34;
	                    final double var49 = var43 * var36 + var41 * var34;
	                    final double var53 = var47 * var28 + var39 * var30;
	                    final double var55 = var39 * var28 - var47 * var30;
	                    final double var57 = var55 * var22 - var49 * var24;
	                    final double var61 = var49 * var22 + var55 * var24;
	                    var2.addVertex(var14 + var57, var16 + var53, var18 + var61);
	                }
	            }
	        }
	
	        var2.draw();
	}
    

    private Vec3 getCustomSkyColor()
    {
        return Vec3.createVectorHelper(0.26796875D, 0.1796875D, 0.0D);
    }

    public float getSkyBrightness(float par1)
    {
        final float var2 = FMLClientHandler.instance().getClient().theWorld.getCelestialAngle(par1);
        float var3 = 1.0F - (MathHelper.sin(var2 * (float) Math.PI * 2.0F) * 2.0F + 0.25F);

        if (var3 < 0.0F)
        {
            var3 = 0.0F;
        }

        if (var3 > 1.0F)
        {
            var3 = 1.0F;
        }

        return var3 * var3 * 1F;
    }
    
    protected abstract void rendererSky(Tessellator tessellator, float f10, float ticks); 
    protected abstract int modeLight();
    protected abstract boolean enableBaseImages();
    protected abstract float sunSize();
    protected abstract ResourceLocation sunImage();
    protected abstract boolean enableStar();
    protected abstract Vector3 colorSunAura();
    protected abstract Vector3 getAtmosphereColor();
    public boolean enableLargeSunAura() {return true;}
    public boolean enableSmoothRender() {return false;}
    public boolean enableRenderPlanet() {return true;}
    public int addSizeAura() {return 0;}
    
    protected float getCelestialAngle(long daylength)
    {
    	return GSUtils.calculateCelestialAngle(this.mc.theWorld.getWorldTime(), this.ticks, (int) daylength) * 360.0F;
    }
}