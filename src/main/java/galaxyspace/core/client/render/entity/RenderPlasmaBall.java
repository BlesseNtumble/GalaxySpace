package galaxyspace.core.client.render.entity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.client.particles.EntityPlasmaBall;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderPlasmaBall extends Render
{
    private float field_77002_a;
    private static final String __OBFID = "CL_00000995";
    private static final ResourceLocation ballTextures = new ResourceLocation(GalaxySpace.ASSET_PREFIX + ":"+ "textures/model/plasmaBall.png");
    
    public RenderPlasmaBall(float p_i1254_1_)
    {
        this.field_77002_a = p_i1254_1_;
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityPlasmaBall p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
    	 this.bindEntityTexture(p_76986_1_);
         GL11.glPushMatrix();
         GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
         GL11.glRotatef(p_76986_1_.prevRotationYaw + (p_76986_1_.rotationYaw - p_76986_1_.prevRotationYaw) * p_76986_9_ - 90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(p_76986_1_.prevRotationPitch + (p_76986_1_.rotationPitch - p_76986_1_.prevRotationPitch) * p_76986_9_, 0.0F, 0.0F, 1.0F);
         Tessellator tessellator = Tessellator.instance;
         byte b0 = 0;
         float f2 = 0.0F;
         float f3 = 0.5F;
         float f4 = (float)(0 + b0 * 10) / 32.0F;
         float f5 = (float)(5 + b0 * 10) / 32.0F;
         float f6 = 0.0F;
         float f7 = 0.15625F;
         float f8 = (float)(5 + b0 * 10) / 32.0F;
         float f9 = (float)(10 + b0 * 10) / 32.0F;
         float f10 = 0.05625F;
         GL11.glEnable(GL11.GL_BLEND);
         GL11.glEnable(GL12.GL_RESCALE_NORMAL);
         GL11.glScalef(f10, f10, f10);
         GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
         GL11.glNormal3f(f10, 0.0F, 0.0F);
         GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
         tessellator.startDrawingQuads();
         tessellator.addVertexWithUV(-1.0D, -7.0D, -7.0D, 0.0F, 0.0F);
         tessellator.addVertexWithUV(-1.0D, -7.0D, 7.0D, 1.0F, 0.0F);
         tessellator.addVertexWithUV(-1.0D, 7.0D, 7.0D, 1.0F, 1.0F);
         tessellator.addVertexWithUV(-1.0D, 7.0D, -7.0D, 0.0F, 1.0F);
         tessellator.draw();
         GL11.glDisable(GL11.GL_BLEND);
         GL11.glDisable(GL12.GL_RESCALE_NORMAL);
         GL11.glPopMatrix();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityPlasmaBall p_110775_1_)
    {
        return ballTextures;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return this.getEntityTexture((EntityPlasmaBall)p_110775_1_);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((EntityPlasmaBall)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}