package galaxyspace.systems.SolarSystem.planets.mars.render.entities;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;

import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.mars.entities.EntityMarsRover;
import micdoodle8.mods.galacticraft.core.client.model.OBJLoaderGC;
import micdoodle8.mods.galacticraft.core.util.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMarsRover extends Render<EntityMarsRover>
{
    private OBJModel.OBJBakedModel mainModel,fronbase,backbase,ltr,ltl,ltr1,ltl1,ant,solarBaseR,solarR,solarBaseL,solarL,antBase;
    private OBJModel.OBJBakedModel radarDish;
    private OBJModel.OBJBakedModel wheelRight;
    private OBJModel.OBJBakedModel wheelLeft;
    private OBJModel.OBJBakedModel cargoLeft;
    private OBJModel.OBJBakedModel cargoMid;
    private OBJModel.OBJBakedModel cargoRight;

    private float antRotate = 0.0F;
    private void updateModels()
    {
        if (this.mainModel == null)
        {
            try
            {
                IModel model = OBJLoaderGC.instance.loadModel(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "mars_rover.obj"));
                Function<ResourceLocation, TextureAtlasSprite> spriteFunction = location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());

                this.mainModel = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("base"), false), DefaultVertexFormats.ITEM, spriteFunction);
                this.fronbase = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("frontbase"), false), DefaultVertexFormats.ITEM, spriteFunction);
                this.backbase = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("backbase"), false), DefaultVertexFormats.ITEM, spriteFunction);
                this.ltr = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("ltR"), false), DefaultVertexFormats.ITEM, spriteFunction);
                this.ltr1 = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("ltR.001"), false), DefaultVertexFormats.ITEM, spriteFunction);
                this.ltl = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("ltL"), false), DefaultVertexFormats.ITEM, spriteFunction);
                this.ltl1 = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("ltL.001"), false), DefaultVertexFormats.ITEM, spriteFunction);
                this.wheelRight = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("frontWheelR"), false), DefaultVertexFormats.ITEM, spriteFunction);
                this.wheelLeft = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("frontWheelL"), false), DefaultVertexFormats.ITEM, spriteFunction);
                this.antBase = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("antBase"), false), DefaultVertexFormats.ITEM, spriteFunction);
                this.ant = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("ant"), false), DefaultVertexFormats.ITEM, spriteFunction);
                this.solarBaseR = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("solarBaseR"), false), DefaultVertexFormats.ITEM, spriteFunction);
                this.solarBaseL = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("solarBaseL"), false), DefaultVertexFormats.ITEM, spriteFunction);
                this.solarR = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("solarR"), false), DefaultVertexFormats.ITEM, spriteFunction);
                this.solarL = (OBJModel.OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("solarL"), false), DefaultVertexFormats.ITEM, spriteFunction);
                
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    public RenderMarsRover(RenderManager manager)
    {
        super(manager);
        this.shadowSize = 1.0F;
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityMarsRover entity)
    {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }

    @Override
    public void doRender(EntityMarsRover entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        float pitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
        float xrotation = entity.lastTickPosY > entity.posY ? -15F : entity.lastTickPosY < entity.posY ? 15F : 0F;
        GlStateManager.disableRescaleNormal();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.scale(1.0F, 1.0F, 1.0F);
        GlStateManager.rotate(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-pitch, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(xrotation, 1.0F, 0.0F, 0.0F);
       //GlStateManager.scale(0.41F, 0.41F, 0.41F);

        this.updateModels();
        this.bindEntityTexture(entity);

        if (Minecraft.isAmbientOcclusionEnabled())
        {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        }
        else
        {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }
        
       // GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        //GlStateManager.loadIdentity();
       // GlStateManager.translate(0, 4.5F, 0);
        //GlStateManager.scale(2.0F, 2.0F, 2.0F);
        GlStateManager.translate(-1.5F, -2, 0);
        ClientUtil.drawBakedModel(this.mainModel);
        ClientUtil.drawBakedModel(this.ltl);
        ClientUtil.drawBakedModel(this.ltl1);
        ClientUtil.drawBakedModel(this.ltr);
        ClientUtil.drawBakedModel(this.ltr1);
        ClientUtil.drawBakedModel(this.antBase);
        
        ClientUtil.drawBakedModel(this.solarBaseL);
        ClientUtil.drawBakedModel(this.solarL);
        //if((partialTicks * 360) % 100 == 0)
        	antRotate++;
             
        GlStateManager.pushMatrix();  
        //GlStateManager.translate(0F, 0F, -1.05F);
        GlStateManager.rotate(-35, 1, 0, 0); 
        //GlStateManager.rotate(antRotate % 30, 1, 1, 0); 
        GlStateManager.translate(0F, -0.75F, 4.35F);
        ClientUtil.drawBakedModel(this.ant);
        GlStateManager.popMatrix();
       
        float rotation = entity.wheelRotationX;   
        
        //Wheel Front Right
        GlStateManager.pushMatrix();        
        GlStateManager.translate(0, 2.7F, -1.75F);
        GlStateManager.rotate(entity.wheelRotationZ, 0, 1, 0);
        GlStateManager.rotate(rotation, 1, 0, 0); 
        GlStateManager.translate(0, -2.75F, 1.75F);
        ClientUtil.drawBakedModel(this.wheelRight);
        GlStateManager.popMatrix();
        
        //Wheel Front Left
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 2.7F, -1.75F);
        GlStateManager.rotate(entity.wheelRotationZ, 0, 1, 0);
        GlStateManager.rotate(rotation, 1, 0, 0);
        GlStateManager.translate(0, -2.75F, 1.8F);
        ClientUtil.drawBakedModel(this.wheelLeft);
        GlStateManager.popMatrix();
        
        //Wheel Front Base
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, 0);
        GlStateManager.rotate(entity.wheelRotationZ, 0, 1, 0);
        ClientUtil.drawBakedModel(this.fronbase);
        GlStateManager.popMatrix();
        
        //Wheel Back Right
        GlStateManager.pushMatrix();        
        GlStateManager.translate(0, 2.7F, 1.9F);
        GlStateManager.rotate(rotation, 1, 0, 0); 
        GlStateManager.translate(0, -2.75F, 1.75F);
        ClientUtil.drawBakedModel(this.wheelRight);
        GlStateManager.popMatrix();
        
        //Wheel Back Left
        GlStateManager.pushMatrix();        
        GlStateManager.translate(0, 2.7F, 1.9F);
        GlStateManager.rotate(rotation, 1, 0, 0); 
        GlStateManager.translate(0, -2.76F, 1.79F);
        ClientUtil.drawBakedModel(this.wheelLeft);
        GlStateManager.popMatrix();
        
        GlStateManager.pushMatrix();
        ClientUtil.drawBakedModel(this.backbase);
        GlStateManager.popMatrix();               
        
/*
        // Radar Dish
        GlStateManager.pushMatrix();
        GlStateManager.translate(-1.178F, 4.1F, -2.397F);
        int ticks = entity.ticksExisted + entity.getEntityId() * 10000;
        GlStateManager.rotate((float) Math.sin(ticks * 0.05) * 50.0F, 1, 0, 0);
        GlStateManager.rotate((float) Math.cos(ticks * 0.1) * 50.0F, 0, 0, 1);
        ClientUtil.drawBakedModel(this.radarDish);
        GlStateManager.popMatrix();

        if (entity.buggyType > 0)
        {
            ClientUtil.drawBakedModel(this.cargoLeft);

            if (entity.buggyType > 1)
            {
                ClientUtil.drawBakedModel(this.cargoMid);

                if (entity.buggyType > 2)
                {
                    ClientUtil.drawBakedModel(this.cargoRight);
                }
            }
        }*/

        GlStateManager.popMatrix();
        RenderHelper.enableStandardItemLighting();
    }
    
    @Override
    public boolean shouldRender(EntityMarsRover buggy, ICamera camera, double camX, double camY, double camZ)
    {
        AxisAlignedBB axisalignedbb = buggy.getEntityBoundingBox().grow(2D, 1D, 2D);
        return buggy.isInRangeToRender3d(camX, camY, camZ) && camera.isBoundingBoxInFrustum(axisalignedbb);
    }
}
