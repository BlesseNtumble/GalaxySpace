package galaxyspace.systems.SolarSystem.planets.overworld.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelSolarPanel - Either Mojang or a mod author
 * Created using Tabula 4.1.1
 */
public class ModelWindGen extends ModelBase {
    public ModelRenderer pole;
    public ModelRenderer base;
    public ModelRenderer base1;
    public ModelRenderer base2;
    public ModelRenderer base3;
    public ModelRenderer base4;
    public ModelRenderer shape17;
    public ModelRenderer shape16;
    public ModelRenderer shape15;
    public ModelRenderer shape18;
    public ModelRenderer shape19;
    public ModelRenderer shape20;
    public ModelRenderer shape21;
    public ModelRenderer shape22;
    public ModelRenderer shape23;

    public ModelWindGen() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.base4 = new ModelRenderer(this, 0, 60);
        this.base4.setRotationPoint(-6.0F, -2.0F, -0.5F);
        this.base4.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, 0.0F);
        this.shape18 = new ModelRenderer(this, 50, 0);
        this.shape18.setRotationPoint(-5.0F, -1.1F, -0.1F);
        this.shape18.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1, 0.0F);
        this.shape20 = new ModelRenderer(this, 50, 0);
        this.shape20.setRotationPoint(-5.0F, -1.1F, -0.5F);
        this.shape20.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1, 0.0F);
        this.base = new ModelRenderer(this, 0, 20);
        this.base.setRotationPoint(-3.0F, -2.0F, -1.5F);
        this.base.addBox(0.0F, 0.0F, 0.0F, 7, 2, 3, 0.0F);
        this.base3 = new ModelRenderer(this, 0, 50);
        this.base3.setRotationPoint(3.1F, -0.9F, -1.0F);
        this.base3.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2, 0.0F);
        this.shape16 = new ModelRenderer(this, 30, 0);
        this.shape16.setRotationPoint(-5.0F, -5.0F, 4.6F);
        this.shape16.addBox(0.0F, -2.0F, 0.0F, 1, 1, 1, 0.0F);
        this.shape23 = new ModelRenderer(this, 60, 0);
        this.shape23.setRotationPoint(-5.0F, -3.7F, -6.6F);
        this.shape23.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1, 0.0F);
        this.setRotateAngle(shape23, 1.1344640137963142F, 0.0F, 0.0F);
        this.base2 = new ModelRenderer(this, 0, 40);
        this.base2.setRotationPoint(3.0F, -3.0F, -1.0F);
        this.base2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 2, 0.0F);
        this.setRotateAngle(base2, 0.0F, 0.0F, 0.7853981633974483F);
        this.shape17 = new ModelRenderer(this, 30, 0);
        this.shape17.setRotationPoint(-5.0F, -1.7F, -0.1F);
        this.shape17.addBox(0.0F, 0.0F, 0.0F, 1, 1, 7, 0.0F);
        this.setRotateAngle(shape17, 0.7853981633974483F, 0.0F, 0.0017453292519943296F);
        this.shape19 = new ModelRenderer(this, 60, 0);
        this.shape19.setRotationPoint(-5.0F, 5.9F, -0.5F);
        this.shape19.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(shape19, 0.7853981633974483F, 0.0F, 0.0F);
        this.pole = new ModelRenderer(this, 94, 50);
        this.pole.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.pole.addBox(-1.5F, 0.0F, -1.5F, 3, 26, 3, 0.0F);
        this.base1 = new ModelRenderer(this, 0, 30);
        this.base1.setRotationPoint(-3.0F, -3.0F, -1.0F);
        this.base1.addBox(0.0F, 0.0F, 0.0F, 6, 1, 2, 0.0F);
        this.shape21 = new ModelRenderer(this, 60, 0);
        this.shape21.setRotationPoint(-5.0F, -4.1F, -6.6F);
        this.shape21.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1, 0.0F);
        this.setRotateAngle(shape21, 1.1344640137963142F, 0.0F, 0.0F);
        this.shape22 = new ModelRenderer(this, 70, 0);
        this.shape22.setRotationPoint(-5.0F, -4.6F, -7.0F);
        this.shape22.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(shape22, 0.39269908169872414F, 0.0F, 0.0F);
        this.shape15 = new ModelRenderer(this, 30, 0);
        this.shape15.setRotationPoint(-5.0F, -2.0F, -0.4F);
        this.shape15.addBox(0.0F, 0.0F, 0.0F, 1, 1, 7, 0.0F);
        this.setRotateAngle(shape15, 0.7853981633974483F, 0.0F, 0.0017453292519943296F);
        
        this.setRotateAngle(shape19, 0.7853981633974483F, 0.0F, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        
    	super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

    public float getRotation(double angle)
	{
		return ((float)angle/(float)180)*(float)Math.PI;
	}

	public double getAbsoluteAngle(double angle)
	{
		return angle % 360;
	}
    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    public void renderPole()
    {
        this.pole.render(0.0625F);
    }
    
    public void renderPanel()
    {
    	this.base.render(0.0625F);
    	this.base1.render(0.0625F);
    	this.base2.render(0.0625F);
    	this.base3.render(0.0625F);
    	this.base4.render(0.0625F);
    }
    
    public void renderFlares(double angle)
    {
    	this.shape15.render(0.0625F);
    	this.shape16.render(0.0625F);
    	this.shape17.render(0.0625F);
    	this.shape18.render(0.0625F);
    	this.shape19.render(0.0625F);
    	this.shape20.render(0.0625F);
    	this.shape21.render(0.0625F);
    	this.shape22.render(0.0625F);
    	this.shape23.render(0.0625F);
    	
    	//setRotateAngle(shape15, 0.0F, 0.0F, getRotation(getAbsoluteAngle(120 + angle)));
    	//setRotateAngle(shape16, 0.0F, 0.0F, getRotation(getAbsoluteAngle(120 + angle)));
    	//setRotateAngle(shape17, 0.0F, 0.0F, getRotation(getAbsoluteAngle(120 + angle)));
    	
    	/*setRotateAngle(shape15, 0.0F, 0.0F, getRotation(getAbsoluteAngle(angle)));
    	setRotateAngle(shape16, 0.0F, 0.0F, getRotation(getAbsoluteAngle(angle)));
    	setRotateAngle(shape17, 0.0F, 0.0F, getRotation(getAbsoluteAngle(angle)));
    	setRotateAngle(shape18, 0.0F, 0.0F, getRotation(getAbsoluteAngle(angle)));
    	setRotateAngle(shape19, 0.0F, 0.0F, getRotation(getAbsoluteAngle(angle)));
    	setRotateAngle(shape20, 0.0F, 0.0F, getRotation(getAbsoluteAngle(angle)));
    	setRotateAngle(shape21, 0.0F, 0.0F, getRotation(getAbsoluteAngle(angle)));
    	setRotateAngle(shape22, 0.0F, 0.0F, getRotation(getAbsoluteAngle(angle)));
    	setRotateAngle(shape23, 0.0F, 0.0F, getRotation(getAbsoluteAngle(angle)));*/
    	
    	//setRotateAngle(shape21, 0.0F, 0.0F, getRotation(getAbsoluteAngle(240 + angle)));
    	//setRotateAngle(shape22, 0.0F, 0.0F, getRotation(getAbsoluteAngle(240 + angle)));
    	//setRotateAngle(shape23, 0.0F, 0.0F, getRotation(getAbsoluteAngle(240 + angle)));
    }
}
