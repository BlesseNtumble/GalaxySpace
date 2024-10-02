package galaxyspace.core.handler;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.proxy.ClientProxy;
import galaxyspace.systems.SolarSystem.SolarSystemBodies;
import micdoodle8.mods.galacticraft.api.event.client.CelestialBodyRenderEvent;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.client.gui.screen.GuiCelestialSelection;
import micdoodle8.mods.galacticraft.core.client.render.ThreadDownloadImageDataGC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class GSColorRingClient
{
	
	private static final ResourceLocation saturnRingTexture = new ResourceLocation(GalacticraftCore.ASSET_PREFIX, "textures/gui/celestialbodies/saturnRings.png");    
    private static final ResourceLocation uranusRingTexture = new ResourceLocation(GalacticraftCore.ASSET_PREFIX, "textures/gui/celestialbodies/uranusRings.png");
	private static final ResourceLocation acrdisk = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/acrdisk.png");
	
    final Minecraft minecraft = FMLClientHandler.instance().getClient();
    public Random rand;

   
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRingRender(CelestialBodyRenderEvent.CelestialRingRenderEvent.Pre renderEvent)
    {
 	
    	if(renderEvent.celestialBody.getRingColorR() == 1.1F
    			&& renderEvent.celestialBody.getRingColorG() == 0.0F
    			&& renderEvent.celestialBody.getRingColorB() == 0.0F) 
    	{
    		this.RingRender(renderEvent, renderEvent.celestialBody, 0.7F, 0.0F, 0.0F);
    	}    	
    	
    	if(renderEvent.celestialBody.getRingColorG() == 1.1F
    			&& renderEvent.celestialBody.getRingColorB() == 0.0F
    			&& renderEvent.celestialBody.getRingColorR() == 0.0F) {
            this.RingRender(renderEvent, renderEvent.celestialBody, 0.0F, 0.7F, 0.0F);
        }

    	if(renderEvent.celestialBody.getRingColorB() == 1.1F
    			&& renderEvent.celestialBody.getRingColorG() == 0.0F
    			&& renderEvent.celestialBody.getRingColorR() == 0.0F) {
            this.RingRender(renderEvent, renderEvent.celestialBody, 0.0F, 0.0F, 0.7F);
        }
    	
    	if(renderEvent.celestialBody.getRingColorR() == 0.0F
    			&& renderEvent.celestialBody.getRingColorG() == 0.0F
    			&& renderEvent.celestialBody.getRingColorB() == 0.0F) {
            this.RingRenderNull(renderEvent, renderEvent.celestialBody);
        }
    	
    	if(renderEvent.celestialBody.equals(GalacticraftCore.planetOverworld)) {
            this.RingRender(renderEvent, renderEvent.celestialBody, 0.0F, 0.7F, 0.0F);
        }
    }
    
    public void RingRenderNull(CelestialBodyRenderEvent.CelestialRingRenderEvent.Pre renderEvent, CelestialBody aroundBody)
    {
    	renderEvent.setCanceled(true);
    }

    public void RingRender(CelestialBodyRenderEvent.CelestialRingRenderEvent.Pre renderEvent, CelestialBody aroundBody, float par1, float par2, float par3)
    {
    	Vector3f mapPos = renderEvent.parentOffset;
        
        float xOffset = (float) mapPos.x;
        float yOffset = (float) mapPos.y;
        
           
        	if (FMLClientHandler.instance().getClient().currentScreen instanceof GuiCelestialSelection)
        		GL11.glColor4f(par1, par2, par3, 0.5F);
           	else
        		GL11.glColor4f(0.3F, 0.1F, 0.1F, 0.0F);
            renderEvent.setCanceled(true);
            GL11.glBegin(GL11.GL_LINE_LOOP);
            
            final float theta = (float) (2 * Math.PI / 90);
            final float cos = (float) Math.cos(theta);
            final float sin = (float) Math.sin(theta);

            float min = 72.0F;
            float max = 78.0F;

            if(aroundBody instanceof Planet) {
                min = 72.F;
                max = 78.F;
             } else if(aroundBody instanceof Moon) {
                max = 1 / 1.5F;
                min = 1 / 1.9F;     
             }
            
            float x = max * renderEvent.celestialBody.getRelativeDistanceFromCenter().unScaledDistance;
            float y = 0;

            float temp;
            for (int i = 0; i < 90; i++)
            {
               
                GL11.glVertex2f(x+xOffset, y+yOffset);
               
                temp = x;
                x = cos * x - sin * y;
                y = sin * temp + cos * y;

            }
            
            GL11.glEnd();
            GL11.glBegin(GL11.GL_LINE_LOOP);

            x = min * renderEvent.celestialBody.getRelativeDistanceFromCenter().scaledDistance;
            y = 0;

            for (int i = 0; i < 90; i++)
            {
                GL11.glVertex2f(x+xOffset, y+yOffset);

                temp = x;
                x = cos * x - sin * y;
                y = sin * temp + cos * y;
            }
            GL11.glEnd();
            GL11.glColor4f(par1, par2, par3, 0.1F);
            GL11.glBegin(GL11.GL_QUADS);
            x = min * renderEvent.celestialBody.getRelativeDistanceFromCenter().scaledDistance;
            y = 0;
            float x2 = max * renderEvent.celestialBody.getRelativeDistanceFromCenter().unScaledDistance;
            float y2 = 0;
            
            for (int i = 0; i < 90; i++)
            {
            	
                GL11.glVertex2f(x2+xOffset, y2+yOffset);
                GL11.glVertex2f(x+xOffset, y+yOffset);

                temp = x;
                x = cos * x - sin * y;
                y = sin * temp + cos * y;
                temp = x2;
                x2 = cos * x2 - sin * y2;
                y2 = sin * temp + cos * y2;

                GL11.glVertex2f(x+xOffset, y+yOffset);
                GL11.glVertex2f(x2+xOffset, y2+yOffset);
            }
            GL11.glEnd();
            
       
	}

      
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onBodyRender(CelestialBodyRenderEvent.Pre renderEvent)
    {
        if (renderEvent.celestialBody.equals(SolarSystemBodies.planetKuiperBelt))
        {        	
            GL11.glRotatef(Sys.getTime() / 10.0F % 360, 0, 0, 1);
        }
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderPlanetPost(CelestialBodyRenderEvent.Post event)
    {
    	
        if (this.minecraft.currentScreen instanceof GuiCelestialSelection)
        {
        	if (event.celestialBody.getTierRequirement() == -2)
        	{
        		this.minecraft.renderEngine.bindTexture(this.acrdisk);
        		float size = GuiCelestialSelection.getWidthForCelestialBodyStatic(event.celestialBody) / 3.0F;
        		size = size * event.celestialBody.getRelativeSize();
                ((GuiCelestialSelection)this.minecraft.currentScreen).drawTexturedModalRect(-7.3F * size, -3.0F * size, 15.0F * size, 7.5F * size, 0, 0, 30, 7, false, false, 30, 7);
        	}
        	if (event.celestialBody == SolarSystemBodies.planetSaturn)
        	{
                this.minecraft.renderEngine.bindTexture(saturnRingTexture);
                float size = GuiCelestialSelection.getWidthForCelestialBodyStatic(event.celestialBody) / 6.0F;
                ((GuiCelestialSelection)this.minecraft.currentScreen).drawTexturedModalRect(-7.5F * size, -1.75F * size, 15.0F * size, 3.5F * size, 0, 0, 30, 7, false, false, 30, 7);
        	}
        	else if (event.celestialBody == SolarSystemBodies.planetUranus)
        	{
                this.minecraft.renderEngine.bindTexture(uranusRingTexture);
                float size = GuiCelestialSelection.getWidthForCelestialBodyStatic(event.celestialBody) / 6.0F;
                ((GuiCelestialSelection)this.minecraft.currentScreen).drawTexturedModalRect(-1.75F * size, -7.0F * size, 3.5F * size, 14.0F * size, 0, 0, 28, 7, false, false, 28, 7);
        	}
        }
    }
    
    public static void onPostRender(RenderPlayerEvent.Specials.Post event)
    {
        AbstractClientPlayer player = (AbstractClientPlayer) event.entityPlayer;
      
        
        boolean flag = ClientProxy.GScapeMap.containsKey(event.entityPlayer.getCommandSenderName());
        float f4;

        if(event.entityPlayer.getCommandSenderName().equals("BlesseNtumble") && !player.isInvisible())
        {
        	player.func_152121_a(Type.SKIN, new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/test.png"));
        	
        }

        if (flag && !player.isInvisible() && !player.getHideCape())
        {
            String url = ClientProxy.GScapeMap.get(player.getCommandSenderName());
            ResourceLocation capeLoc = ClientProxy.GScapesMap.get(url);
            if (!ClientProxy.GScapesMap.containsKey(url))
            {
                try
                {
                    String dirName = Minecraft.getMinecraft().mcDataDir.getAbsolutePath();
                    File directory = new File(dirName, "assets");
                    boolean success = true;
                    if (!directory.exists())
                    {
                        success = directory.mkdir();
                    }
                    if (success)
                    {
                        directory = new File(directory, "gcCapes");
                        if (!directory.exists())
                        {
                            success = directory.mkdir();
                        }

                        if (success)
                        {
                            String hash = String.valueOf(player.getCommandSenderName().hashCode());
                            File file1 = new File(directory, hash.substring(0, 2));
                            File file2 = new File(file1, hash);
                            final ResourceLocation resourcelocation = new ResourceLocation("gcCapes/" + hash);
                            ThreadDownloadImageDataGC threaddownloadimagedata = new ThreadDownloadImageDataGC(file2, url, null, new IImageBuffer()
                            {
                                public BufferedImage parseUserSkin(BufferedImage p_78432_1_)
                                {
                                    if (p_78432_1_ == null)
                                    {
                                        return null;
                                    }
                                    else
                                    {
                                        BufferedImage bufferedimage1 = new BufferedImage(512, 256, 2);
                                        Graphics graphics = bufferedimage1.getGraphics();
                                        graphics.drawImage(p_78432_1_, 0, 0, null);
                                        graphics.dispose();
                                        p_78432_1_ = bufferedimage1;
                                    }
                                    return p_78432_1_;
                                }

                                public void func_152634_a()
                                {
                                }
                            });
                            
                            if (ClientProxy.mc.getTextureManager().loadTexture(resourcelocation, threaddownloadimagedata))
                            {
                                capeLoc = resourcelocation;
                                
                            }
                           
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                ClientProxy.GScapesMap.put(url, capeLoc);
                
            }
            
            if (capeLoc != null)
            {
            	ClientProxy.mc.getTextureManager().bindTexture(capeLoc);
                GL11.glPushMatrix();
                GL11.glTranslatef(0.0F, 0.0F, 0.125F);
                double d3 = player.field_71091_bM + (player.field_71094_bP - player.field_71091_bM) * event.partialRenderTick - (player.prevPosX + (player.posX - player.prevPosX) * event.partialRenderTick);
                double d4 = player.field_71096_bN + (player.field_71095_bQ - player.field_71096_bN) * event.partialRenderTick - (player.prevPosY + (player.posY - player.prevPosY) * event.partialRenderTick);
                double d0 = player.field_71097_bO + (player.field_71085_bR - player.field_71097_bO) * event.partialRenderTick - (player.prevPosZ + (player.posZ - player.prevPosZ) * event.partialRenderTick);
                f4 = (player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * event.partialRenderTick) / 57.29578F;
                double d1 = MathHelper.sin(f4);
                double d2 = -MathHelper.cos(f4);
                float f5 = (float) d4 * 10.0F;

                if (f5 < -6.0F)
                {
                    f5 = -6.0F;
                }

                if (f5 > 32.0F)
                {
                    f5 = 32.0F;
                }

                float f6 = (float) (d3 * d1 + d0 * d2) * 100.0F;
                float f7 = (float) (d3 * d2 - d0 * d1) * 100.0F;

                if (f6 < 0.0F)
                {
                    f6 = 0.0F;
                }

                float f8 = player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * event.partialRenderTick;
                f5 += MathHelper.sin((player.prevDistanceWalkedModified + (player.distanceWalkedModified - player.prevDistanceWalkedModified) * event.partialRenderTick) * 6.0F) * 32.0F * f8;

                if (player.isSneaking())
                {
                    f5 += 25.0F;
                }

                GL11.glRotatef(6.0F + f6 / 2.0F + f5, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(f7 / 2.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-f7 / 2.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                event.renderer.modelBipedMain.renderCloak(0.0625F);
                GL11.glPopMatrix();
            }
            
         
        }
        
     }
    
  }


