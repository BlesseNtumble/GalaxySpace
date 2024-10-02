package galaxyspace.core.client.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelOxygenTank extends ModelBiped{
	
	public ModelRenderer[] OxygenTanks = new ModelRenderer[2];
	
	public ModelOxygenTank()
    {
	    
	    this.OxygenTanks[0] = new ModelRenderer(this, 0, 0);
        this.OxygenTanks[0].addBox(-1.5F, 0F, -1.5F, 3, 7, 3, 0.0067F);
        this.OxygenTanks[0].setRotationPoint(2F, 2F, 3.8F);
        this.OxygenTanks[0].mirror = true;
        
        this.OxygenTanks[1] = new ModelRenderer(this, 0, 0);
        this.OxygenTanks[1].addBox(-1.5F, 0F, -1.5F, 3, 7, 3, 0.0067F);
        this.OxygenTanks[1].setRotationPoint(-2F, 2F, 3.8F);
        this.OxygenTanks[1].mirror = true;
    }

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);			
		
		
	}
	
	public void renderLeft(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		this.OxygenTanks[0].render(par7);
		this.render(par1Entity, par2, par3, par4, par5, par6, par7);
	}
	
	public void renderRight(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		this.OxygenTanks[1].render(par7);
		this.render(par1Entity, par2, par3, par4, par5, par6, par7);
	}
	
	 public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
	        modelRenderer.rotateAngleX = x;
	        modelRenderer.rotateAngleY = y;
	        modelRenderer.rotateAngleZ = z;
	 }
	 
	 public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) { 
	        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity); 
	 }

	 
}
