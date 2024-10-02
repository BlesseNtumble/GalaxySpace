package galaxyspace.systems.SolarSystem.moons.enceladus.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCrystal extends ModelBase
	{
		  //fields
		    ModelRenderer Shape1;
		    ModelRenderer Shape2;
		    ModelRenderer Shape3;
		    ModelRenderer Shape5;
		    ModelRenderer Shape6;
		    ModelRenderer Shape7;
		    ModelRenderer Shape8;
		    ModelRenderer Shape9;
		    ModelRenderer Shape10;
		    ModelRenderer Shape11;
		    ModelRenderer Shape12;
		    ModelRenderer Shape13;
		    ModelRenderer Shape14;
		    ModelRenderer Shape15;
		  
		  public ModelCrystal()
		  {
		    textureWidth = 64;
		    textureHeight = 32;
		    
		      Shape1 = new ModelRenderer(this, 20, 0);
		      Shape1.addBox(0F, -16F, 0F, 3, 16, 3);
		      Shape1.setRotationPoint(-1.5F, 24F, 0F);
		      Shape1.setTextureSize(64, 32);
		      Shape1.mirror = true;
		      setRotation(Shape1, 0.2845197F, -0.2792527F, 0.122173F);
		      Shape2 = new ModelRenderer(this, 0, 0);
		      Shape2.addBox(0F, -7F, 0F, 1, 7, 1);
		      Shape2.setRotationPoint(0F, 24F, 3F);
		      Shape2.setTextureSize(64, 32);
		      Shape2.mirror = true;
		      setRotation(Shape2, -0.1858931F, 0.3141593F, 0.0698132F);
		      Shape3 = new ModelRenderer(this, 0, 0);
		      Shape3.addBox(0F, -7F, 0F, 2, 7, 2);
		      Shape3.setRotationPoint(-2F, 24F, 3F);
		      Shape3.setTextureSize(64, 32);
		      Shape3.mirror = true;
		      setRotation(Shape3, -0.122173F, 0.1570796F, -0.2094395F);
		      Shape5 = new ModelRenderer(this, 0, 0);
		      Shape5.addBox(0F, -5F, 0F, 2, 5, 2);
		      Shape5.setRotationPoint(0F, 24F, 0F);
		      Shape5.setTextureSize(64, 32);
		      Shape5.mirror = true;
		      setRotation(Shape5, -0.4833219F, 2.044824F, -0.0371786F);
		      Shape6 = new ModelRenderer(this, 0, 0);
		      Shape6.addBox(0F, -9F, 0F, 2, 9, 2);
		      Shape6.setRotationPoint(-2F, 24F, 3F);
		      Shape6.setTextureSize(64, 32);
		      Shape6.mirror = true;
		      setRotation(Shape6, -0.0607251F, 3.141593F, 0.2997009F);
		      Shape7 = new ModelRenderer(this, 0, 0);
		      Shape7.addBox(0F, -4F, 0F, 3, 4, 3);
		      Shape7.setRotationPoint(-2F, 24F, 1F);
		      Shape7.setTextureSize(64, 32);
		      Shape7.mirror = true;
		      setRotation(Shape7, 0F, 2.700407F, 0.1396263F);
		      Shape8 = new ModelRenderer(this, 0, 0);
		      Shape8.addBox(0F, -7F, 0F, 1, 7, 1);
		      Shape8.setRotationPoint(-2F, 24F, -2F);
		      Shape8.setTextureSize(64, 32);
		      Shape8.mirror = true;
		      setRotation(Shape8, 0.4712389F, 0.0698132F, 0.0349066F);
		      Shape8 = new ModelRenderer(this, 0, 0);
		      Shape8.addBox(0F, -6F, 0F, 1, 6, 1);
		      Shape8.setRotationPoint(-1F, 24F, -2F);
		      Shape8.setTextureSize(64, 32);
		      Shape8.mirror = true;
		      setRotation(Shape8, 0.2443461F, 0.2792527F, 0.1919862F);
		      Shape9 = new ModelRenderer(this, 0, 0);
		      Shape9.addBox(0F, -6F, 0F, 1, 6, 1);
		      Shape9.setRotationPoint(1F, 24F, 2F);
		      Shape9.setTextureSize(64, 32);
		      Shape9.mirror = true;
		      setRotation(Shape9, 0F, -0.2617994F, 0.9599311F);
		      Shape9 = new ModelRenderer(this, 0, 0);
		      Shape9.addBox(0F, -9F, 0F, 1, 9, 1);
		      Shape9.setRotationPoint(1F, 24F, 1F);
		      Shape9.setTextureSize(64, 32);
		      Shape9.mirror = true;
		      setRotation(Shape9, 0F, 0.2094395F, 0.3665191F);
		      Shape10 = new ModelRenderer(this, 0, 0);
		      Shape10.addBox(-1F, -5F, 0F, 1, 5, 1);
		      Shape10.setRotationPoint(2F, 24F, 0F);
		      Shape10.setTextureSize(64, 32);
		      Shape10.mirror = true;
		      setRotation(Shape10, 0F, 0.4537856F, 0.7679449F);
		      Shape11 = new ModelRenderer(this, 0, 0);
		      Shape11.addBox(0F, -6F, 0F, 1, 6, 1);
		      Shape11.setRotationPoint(-3F, 24F, 3F);
		      Shape11.setTextureSize(64, 32);
		      Shape11.mirror = true;
		      setRotation(Shape11, -0.1919862F, 0F, -0.3316126F);
		      Shape12 = new ModelRenderer(this, 0, 0);
		      Shape12.addBox(0F, -2F, 0F, 2, 2, 2);
		      Shape12.setRotationPoint(1.6F, 24F, 1F);
		      Shape12.setTextureSize(64, 32);
		      Shape12.mirror = true;
		      setRotation(Shape12, 0.122173F, 0.122173F, 0.4712389F);
		      Shape13 = new ModelRenderer(this, 0, 0);
		      Shape13.addBox(0F, -3F, 0F, 1, 3, 1);
		      Shape13.setRotationPoint(1F, 24F, 1.8F);
		      Shape13.setTextureSize(64, 32);
		      Shape13.mirror = true;
		      setRotation(Shape13, -0.4886922F, 0.1047198F, 0.1919862F);
		      Shape14 = new ModelRenderer(this, 0, 0);
		      Shape14.addBox(-1F, -3F, -1F, 1, 3, 1);
		      Shape14.setRotationPoint(-0.5F, 24F, -2F);
		      Shape14.setTextureSize(64, 32);
		      Shape14.mirror = true;
		      setRotation(Shape14, 0.8028515F, -0.1396263F, -0.0174533F);
		      Shape15 = new ModelRenderer(this, 0, 0);
		      Shape15.addBox(0F, -7F, 0F, 1, 7, 1);
		      Shape15.setRotationPoint(-3F, 24F, 0F);
		      Shape15.setTextureSize(64, 32);
		      Shape15.mirror = true;
		      setRotation(Shape15, 0F, -0.3839724F, -0.2974289F);
		      Shape1 = new ModelRenderer(this, 33, 0);
		      Shape1.addBox(0F, -16F, 0F, 4, 16, 4);
		      Shape1.setRotationPoint(-2F, 24F, -1F);
		      Shape1.setTextureSize(64, 32);
		      Shape1.mirror = true;
		      setRotation(Shape1, 0.2496131F, -0.2792527F, 0.122173F);
		  }
		  
		  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
		  {
		    super.render(entity, f, f1, f2, f3, f4, f5);
		    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		    Shape1.render(f5);
		    Shape2.render(f5);
		    Shape3.render(f5);
		    Shape5.render(f5);
		    Shape6.render(f5);
		    Shape7.render(f5);
		    Shape8.render(f5);
		    Shape8.render(f5);
		    Shape9.render(f5);
		    Shape9.render(f5);
		    Shape10.render(f5);
		    Shape11.render(f5);
		    Shape12.render(f5);
		    Shape13.render(f5);
		    Shape14.render(f5);
		    Shape15.render(f5);
		    Shape1.render(f5);
		  }
		  
		  private void setRotation(ModelRenderer model, float x, float y, float z)
		  {
		    model.rotateAngleX = x;
		    model.rotateAngleY = y;
		    model.rotateAngleZ = z;
		  }
		  
		  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
		  {
		    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		  }

		}

