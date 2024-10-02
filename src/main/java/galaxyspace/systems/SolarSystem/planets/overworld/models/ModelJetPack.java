package galaxyspace.systems.SolarSystem.planets.overworld.models;

import java.util.ArrayList;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * JetPack.tcn - AlexSocol
 * Created using Tabula 4.1.1
 */
public class ModelJetPack extends ModelBiped {
    public ModelRenderer GasLeft;
    public ModelRenderer GasRight;
    public ModelRenderer Back;
    public ModelRenderer GasBLeft;
    public ModelRenderer GasBRight;
    public ModelRenderer GasPipeLeft;
    public ModelRenderer GasPipeRight;
    public ModelRenderer GasPipeMiddle;
    public ModelRenderer Mixer;
    public ModelRenderer GasPipeCross;
    public ModelRenderer GasPipeLead;
    public ModelRenderer GasPipeSystem;
    public ModelRenderer ArmLeftUp;
    public ModelRenderer ArmRightUp;
    public ModelRenderer ArmFrontUp;
    public ModelRenderer ArmLeft;
    public ModelRenderer ArmFront;
    public ModelRenderer ArmRight;
    public ModelRenderer ArmMiddle;
    public ModelRenderer PipeLeftBase;
    public ModelRenderer PipeMiddleBase;
    public ModelRenderer PipeRightBase;
    public ModelRenderer PipeLeftEx;
    public ModelRenderer PipeMiddleEx;
    public ModelRenderer PipeRightEx;
    public ModelRenderer ThrusterLeft;
    public ModelRenderer ThrusterRight;
    public ModelRenderer SideLeft;
    public ModelRenderer SideRight;
    public ModelRenderer Top;
    
    private ArrayList<ModelRenderer> modelList = new ArrayList<ModelRenderer>();
    
    

    public ModelJetPack() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.Top = new ModelRenderer(this, 8, 11);
        this.Top.setRotationPoint(-4.0F, 1.0F, 3.0F);
        this.Top.addBox(0.0F, 0.0F, 0.0F, 8, 2, 3, 0.0F);
        this.GasBLeft = new ModelRenderer(this, 24, 0);
        this.GasBLeft.setRotationPoint(1.0F, 11.5F, 3.0F);
        this.GasBLeft.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
       
        this.GasPipeLeft = new ModelRenderer(this, 124, 0);
        this.GasPipeLeft.setRotationPoint(2.0F, 12.5F, 4.0F);
        this.GasPipeLeft.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
      
        this.Back = new ModelRenderer(this, 56, 19);
        this.Back.setRotationPoint(-3.5F, 0.5F, 2.0F);
        this.Back.addBox(0.0F, 0.0F, 0.0F, 7, 10, 1, 0.0F);
        this.GasPipeSystem = new ModelRenderer(this, 115, 3);
        this.GasPipeSystem.setRotationPoint(-0.5F, 10.0F, 8.0F);
        this.GasPipeSystem.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(GasPipeSystem, -2.356194490192345F, -0.0F, 0.0F);
        this.PipeMiddleBase = new ModelRenderer(this, 0, 6);
        this.PipeMiddleBase.setRotationPoint(1.0F, 1.0F, 5.5F);
        this.PipeMiddleBase.addBox(-2.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(PipeMiddleBase, 1.5817919010824606F, 0.6632251157578453F, -1.5639895427121187F);
        this.ArmRight = new ModelRenderer(this, 72, 2);
        this.ArmRight.setRotationPoint(-5.0F, 9.0F, -2.5F);
        this.ArmRight.addBox(0.0F, 0.0F, -1.0F, 8, 1, 1, 0.0F);
        this.setRotateAngle(ArmRight, 0.0F, -1.5707963267948966F, 0.0F);
        this.ArmMiddle = new ModelRenderer(this, 48, 0);
        this.ArmMiddle.setRotationPoint(-0.5F, 6.0F, -3.0F);
        this.ArmMiddle.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.GasBRight = new ModelRenderer(this, 24, 4);
        this.GasBRight.setRotationPoint(-4.0F, 11.5F, 3.0F);
        this.GasBRight.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
        this.SideRight = new ModelRenderer(this, 16, 0);
        this.SideRight.setRotationPoint(-5.0F, 3.0F, 3.0F);
        this.SideRight.addBox(0.0F, 0.0F, 0.0F, 1, 8, 3, 0.0F);
        this.GasLeft = new ModelRenderer(this, 8, 16);
        this.GasLeft.setRotationPoint(0.5F, 2.0F, 2.5F);
        this.GasLeft.addBox(0.0F, 0.0F, 0.0F, 4, 10, 4, 0.0F);
        
        this.GasPipeCross = new ModelRenderer(this, 116, 3);
        this.GasPipeCross.setRotationPoint(-0.5F, 13.5F, 4.5F);
        this.GasPipeCross.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
        this.Mixer = new ModelRenderer(this, 40, 17);
        this.Mixer.setRotationPoint(-2.5F, 1.5F, 4.0F);
        this.Mixer.addBox(0.0F, 0.0F, 0.0F, 5, 10, 3, 0.0F);
        this.GasPipeRight = new ModelRenderer(this, 120, 0);
        this.GasPipeRight.setRotationPoint(-3.0F, 12.5F, 4.0F);
        this.GasPipeRight.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.GasPipeLead = new ModelRenderer(this, 124, 2);
        this.GasPipeLead.setRotationPoint(-0.5F, 10.0F, 7.0F);
        this.GasPipeLead.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
        this.ArmFrontUp = new ModelRenderer(this, 52, 0);
        this.ArmFrontUp.setRotationPoint(-4.5F, 5.0F, -3.0F);
        this.ArmFrontUp.addBox(0.0F, 0.0F, 0.0F, 9, 1, 1, 0.0F);
    
        this.SideLeft = new ModelRenderer(this, 8, 0);
        this.SideLeft.setRotationPoint(4.0F, 3.0F, 3.0F);
        this.SideLeft.addBox(0.0F, 0.0F, 0.0F, 1, 8, 3, 0.0F);
        this.PipeRightBase = new ModelRenderer(this, 0, 12);
        this.PipeRightBase.setRotationPoint(-2.5F, 1.0F, 3.5F);
        this.PipeRightBase.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(PipeRightBase, 0.0F, -0.0F, 2.234021442552742F);
        this.ThrusterRight = new ModelRenderer(this, 0, 41);
        this.ThrusterRight.setRotationPoint(-4.5F, 6.5F, 3.5F);
        this.ThrusterRight.addBox(0.0F, 0.0F, 0.0F, 2, 2, 3, 0.0F);
        this.setRotateAngle(ThrusterRight, 0.0F, -0.7330382858376184F, 0.0F);
        this.PipeMiddleEx = new ModelRenderer(this, 0, 24);
        this.PipeMiddleEx.setRotationPoint(1.0F, 1.0F, 5.5F);
        this.PipeMiddleEx.addBox(1.62F, 1.7F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(PipeMiddleEx, 1.580570170606065F, -0.47123889803846897F, -1.5751596499248823F);
        this.ArmRightUp = new ModelRenderer(this, 72, 0);
        this.ArmRightUp.setRotationPoint(-5.0F, 5.0F, -2.5F);
        this.ArmRightUp.addBox(0.0F, 0.0F, -1.0F, 8, 1, 1, 0.0F);
        this.setRotateAngle(ArmRightUp, 0.0F, -1.5707963267948966F, 0.0F);
      
        this.ArmLeft = new ModelRenderer(this, 90, 2);
        this.ArmLeft.setRotationPoint(4.0F, 9.0F, -2.5F);
        this.ArmLeft.addBox(0.0F, 0.0F, -1.0F, 8, 1, 1, 0.0F);
        this.setRotateAngle(ArmLeft, 0.0F, -1.5707963267948966F, 0.0F);
        this.ArmFront = new ModelRenderer(this, 52, 2);
        this.ArmFront.setRotationPoint(-4.5F, 9.0F, -3.0F);
        this.ArmFront.addBox(0.0F, 0.0F, 0.0F, 9, 1, 1, 0.0F);
        this.PipeLeftBase = new ModelRenderer(this, 0, 0);
        this.PipeLeftBase.setRotationPoint(2.5F, 1.0F, 3.5F);
        this.PipeLeftBase.addBox(-2.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(PipeLeftBase, 0.0F, -0.0F, -2.2340214252471924F);
        this.PipeLeftEx = new ModelRenderer(this, 0, 18);
        this.PipeLeftEx.setRotationPoint(2.5F, 1.0F, 3.5F);
        this.PipeLeftEx.addBox(1.63F, 1.69F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(PipeLeftEx, 0.0F, -0.0F, -1.0995574287564276F);
        this.PipeRightEx = new ModelRenderer(this, 0, 30);
        this.PipeRightEx.setRotationPoint(-2.5F, 1.0F, 3.5F);
        this.PipeRightEx.addBox(-3.62F, 1.69F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(PipeRightEx, 0.0F, -0.0F, 1.0995574287564276F);
        this.GasRight = new ModelRenderer(this, 24, 16);
        this.GasRight.setRotationPoint(-4.5F, 2.0F, 2.5F);
        this.GasRight.addBox(0.0F, 0.0F, 0.0F, 4, 10, 4, 0.0F);
        this.ArmLeftUp = new ModelRenderer(this, 90, 0);
        this.ArmLeftUp.setRotationPoint(4.0F, 5.0F, -2.5F);
        this.ArmLeftUp.addBox(0.0F, 0.0F, -1.0F, 8, 1, 1, 0.0F);
        this.setRotateAngle(ArmLeftUp, 0.0F, -1.5707963267948966F, -0.031415926535897934F);
        this.GasPipeMiddle = new ModelRenderer(this, 108, 0);
        this.GasPipeMiddle.setRotationPoint(-2.5F, 13.0F, 4.0F);
        this.GasPipeMiddle.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1, 0.0F);
        this.ThrusterLeft = new ModelRenderer(this, 0, 36);
        this.ThrusterLeft.setRotationPoint(4.5F, 6.5F, 3.5F);
        this.ThrusterLeft.addBox(-2.0F, 0.0F, 0.0F, 2, 2, 3, 0.0F);
        this.setRotateAngle(ThrusterLeft, 0.0F, 0.7330382858376184F, 0.0F);
        
        modelList.add(Top);
        modelList.add(GasBLeft);
        modelList.add(GasPipeLeft);
        modelList.add(Back);
        modelList.add(GasPipeSystem);
        modelList.add(PipeMiddleBase);
        modelList.add(ArmRight);
        modelList.add(ArmMiddle);
        modelList.add(GasBRight);
        modelList.add(SideRight);
        modelList.add(GasLeft);
        modelList.add(GasPipeCross);
        modelList.add(Mixer);
        modelList.add(GasPipeRight);
        modelList.add(GasPipeLead);
        modelList.add(ArmFrontUp);
        modelList.add(SideLeft);
        modelList.add(PipeRightBase);
        modelList.add(ThrusterRight);
        modelList.add(PipeMiddleEx);
        modelList.add(ArmRightUp);
        modelList.add(ArmLeft);
        modelList.add(ArmFront);
        modelList.add(PipeLeftBase);
        modelList.add(PipeLeftEx);
        modelList.add(PipeRightEx);
        modelList.add(GasRight);
        modelList.add(ArmLeftUp);
        modelList.add(GasPipeMiddle);
        modelList.add(ThrusterLeft);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity); //� �����
        this.Top.render(f5);
        this.GasBLeft.render(f5);
        this.GasPipeLeft.render(f5);
        this.Back.render(f5);
        this.GasPipeSystem.render(f5);
        this.PipeMiddleBase.render(f5);
        this.ArmRight.render(f5);
        this.ArmMiddle.render(f5);
        this.GasBRight.render(f5);
        this.SideRight.render(f5);
        this.GasLeft.render(f5);
        this.GasPipeCross.render(f5);
        this.Mixer.render(f5);
        this.GasPipeRight.render(f5);
        this.GasPipeLead.render(f5);
        this.ArmFrontUp.render(f5);
        this.SideLeft.render(f5);
        this.PipeRightBase.render(f5);
        this.ThrusterRight.render(f5);
        this.PipeMiddleEx.render(f5);
        this.ArmRightUp.render(f5);
        this.ArmLeft.render(f5);
        this.ArmFront.render(f5);
        this.PipeLeftBase.render(f5);
        this.PipeLeftEx.render(f5);
        this.PipeRightEx.render(f5);
        this.GasRight.render(f5);
        this.ArmLeftUp.render(f5);
        this.GasPipeMiddle.render(f5);
        this.ThrusterLeft.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
   
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) { //����� ��������� �������� entity
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity); //� �����
    }
}
