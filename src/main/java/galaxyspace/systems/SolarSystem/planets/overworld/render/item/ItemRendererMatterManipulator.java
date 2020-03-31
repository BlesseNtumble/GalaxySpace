package galaxyspace.systems.SolarSystem.planets.overworld.render.item;

import javax.vecmath.AxisAngle4f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import micdoodle8.mods.galacticraft.core.wrappers.ModelTransformWrapper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraftforge.common.model.TRSRTransformation;

public class ItemRendererMatterManipulator extends ModelTransformWrapper{
	
	public ItemRendererMatterManipulator(IBakedModel modelToWrap) {
		super(modelToWrap);
	}
	
	 @Override
	    protected Matrix4f getTransformForPerspective(TransformType cameraTransformType)
	    {
	        if (cameraTransformType == TransformType.GUI)
	        {
	            Matrix4f ret = new Matrix4f();
	            ret.setIdentity();
	            Matrix4f mul = new Matrix4f();
	            mul.setIdentity();
	            mul.setTranslation(new Vector3f(0.28F, 0.45F, 0.0F));
	            ret.mul(mul);
	            mul.setIdentity();
	            mul.setScale(0.7F);
	            mul.setRotation(new AxisAngle4f(0, 1, 0, 0.7F));
	            ret.mul(mul);
	            return ret;
	        }

	        if (cameraTransformType == TransformType.THIRD_PERSON_RIGHT_HAND || cameraTransformType == TransformType.THIRD_PERSON_LEFT_HAND)
	        {
	            Matrix4f ret = new Matrix4f();
	            ret.setIdentity();
	            Matrix4f mul = new Matrix4f();
	            mul.setIdentity();
	            Quat4f rot = TRSRTransformation.quatFromXYZDegrees(new Vector3f(38, 0, 0));
	            mul.setRotation(rot);
	            ret.mul(mul);
	            mul.setIdentity();
	            mul.setScale(0.68F);
	            ret.mul(mul);
	            mul.setIdentity();
	            mul.setTranslation(new Vector3f(0.5F, 0.4F, -0.1F));
	            ret.mul(mul);
	            return ret;
	        }

	        if (cameraTransformType == TransformType.FIRST_PERSON_RIGHT_HAND || cameraTransformType == TransformType.FIRST_PERSON_LEFT_HAND)
	        {
	            Vector3f trans = new Vector3f(0.0F, 1.3F, 0.8F);
	            Matrix4f ret = new Matrix4f();
	            ret.setIdentity();
	            Matrix4f mul = new Matrix4f();
	            mul.setIdentity();
	            Quat4f rot = TRSRTransformation.quatFromXYZDegrees(new Vector3f(-10, 0, 0));
	            mul.setRotation(rot);
	            ret.mul(mul);
	            mul.setIdentity();
	            mul.setScale(0.38F);
	            ret.mul(mul);
	            mul.setIdentity();
	            mul.setTranslation(trans);
	            ret.mul(mul);
	            return ret;
	        }

	        if (cameraTransformType == TransformType.GROUND)
	        {
	            Matrix4f ret = new Matrix4f();
	            ret.setIdentity();
	            Matrix4f mul = new Matrix4f();
	            mul.setIdentity();
	            Quat4f rot = TRSRTransformation.quatFromXYZDegrees(new Vector3f(0, 0, 0));
	            mul.setRotation(rot);
	            ret.mul(mul);
	            mul.setIdentity();
	            mul.setScale(0.65F);
	            ret.mul(mul);
	            mul.setIdentity();
	            mul.setTranslation(new Vector3f(0.5F, 1.0F, 0.0F));
	            ret.mul(mul);
	            return ret;
	        }

	        if (cameraTransformType == TransformType.FIXED)
	        {
	            Matrix4f ret = new Matrix4f();
	            ret.setIdentity();
	            Matrix4f mul = new Matrix4f();
	            mul.setIdentity();
	            Quat4f rot = TRSRTransformation.quatFromXYZDegrees(new Vector3f(30, 0, 0));
	            mul.setRotation(rot);
	            ret.mul(mul);
	            mul.setIdentity();
	            mul.setScale(0.8F);
	            ret.mul(mul);
	            mul.setIdentity();
	            mul.rotY(3.15F);
	            ret.mul(mul);
	            mul.setIdentity();
	            mul.setTranslation(new Vector3f(0.0F, 0.0F, 0.4F));
	            ret.mul(mul);
	            return ret;
	        }

	        return null;
	    }
}
