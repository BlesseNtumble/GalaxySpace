package galaxyspace.core.client.animations.MCACommonLibrary.math;

import java.io.Serializable;

public class Quaternion implements Serializable {
   public static final Quaternion EMPTY = new Quaternion(0.0F, 0.0F, 0.0F, 0.0F);
   static final double EPS = 1.0E-6D;
   public float x;
   public float y;
   public float z;
   public float w;

   public Quaternion(float x, float y, float z, float w) {
      float mag = (float)(1.0D / Math.sqrt((double)(x * x + y * y + z * z + w * w)));
      this.x = x * mag;
      this.y = y * mag;
      this.z = z * mag;
      this.w = w * mag;
   }

   public Quaternion() {
      this(0.0F, 0.0F, 0.0F, 1.0F);
   }

   public Quaternion(Quaternion q1) {
      this.x = q1.x;
      this.y = q1.y;
      this.z = q1.z;
      this.w = q1.w;
   }

   public Quaternion(Vector3f axis, float angle) {
      double s = Math.sin((double)(angle / 2.0F));
      this.x = (float)((double)axis.x * s);
      this.y = (float)((double)axis.y * s);
      this.z = (float)((double)axis.z * s);
      this.w = (float)Math.cos((double)(angle / 2.0F));
   }

   public Quaternion(Matrix4f mat) {
      double T = (double)(1.0F + mat.m00 + mat.m11 + mat.m22);
      double S;
      if (T > 1.0E-8D) {
         S = Math.sqrt(T) * 2.0D;
         this.x = (float)((double)(mat.m12 - mat.m21) / S);
         this.y = (float)((double)(mat.m02 - mat.m20) / S);
         this.z = (float)((double)(mat.m10 - mat.m01) / S);
         this.w = (float)(0.25D * S);
      } else if (T == 0.0D) {
         if (mat.m00 > mat.m11 && mat.m00 > mat.m22) {
            S = Math.sqrt(1.0D + (double)mat.m00 - (double)mat.m11 - (double)mat.m22) * 2.0D;
            this.x = (float)(0.25D * S);
            this.y = (float)((double)(mat.m10 + mat.m01) / S);
            this.z = (float)((double)(mat.m02 + mat.m20) / S);
            this.w = (float)((double)(mat.m21 - mat.m12) / S);
         } else if (mat.m11 > mat.m22) {
            S = Math.sqrt(1.0D + (double)mat.m11 - (double)mat.m00 - (double)mat.m22) * 2.0D;
            this.x = (float)((double)(mat.m10 + mat.m01) / S);
            this.y = (float)(0.25D * S);
            this.z = (float)((double)(mat.m21 + mat.m12) / S);
            this.w = (float)((double)(mat.m02 - mat.m20) / S);
         } else {
            S = Math.sqrt(1.0D + (double)mat.m22 - (double)mat.m00 - (double)mat.m11) * 2.0D;
            this.x = (float)((double)(mat.m02 + mat.m20) / S);
            this.y = (float)((double)(mat.m21 + mat.m12) / S);
            this.z = (float)(0.25D * S);
            this.w = (float)((double)(mat.m10 - mat.m01) / S);
         }
      }

   }

   public Quaternion set(Quaternion q) {
      this.x = q.x;
      this.y = q.y;
      this.z = q.z;
      this.w = q.w;
      return this;
   }

   public final void conjugate(Quaternion q1) {
      this.x = -q1.x;
      this.y = -q1.y;
      this.z = -q1.z;
      this.w = q1.w;
   }

   public final void conjugate() {
      this.x = -this.x;
      this.y = -this.y;
      this.z = -this.z;
   }

   public final void mul(Quaternion q1) {
      this.mul(this, q1);
   }

   public final void mul(Quaternion q1, Quaternion q2) {
      if (this != q1 && this != q2) {
         this.w = q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z;
         this.x = q1.w * q2.x + q2.w * q1.x + q1.y * q2.z - q1.z * q2.y;
         this.y = q1.w * q2.y + q2.w * q1.y - q1.x * q2.z + q1.z * q2.x;
         this.z = q1.w * q2.z + q2.w * q1.z + q1.x * q2.y - q1.y * q2.x;
      } else {
         float w = q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z;
         float x = q1.w * q2.x + q2.w * q1.x + q1.y * q2.z - q1.z * q2.y;
         float y = q1.w * q2.y + q2.w * q1.y - q1.x * q2.z + q1.z * q2.x;
         this.z = q1.w * q2.z + q2.w * q1.z + q1.x * q2.y - q1.y * q2.x;
         this.w = w;
         this.x = x;
         this.y = y;
      }

   }

   public final void mulInverse(Quaternion q1, Quaternion q2) {
      Quaternion tempQuat = new Quaternion(q2);
      tempQuat.inverse();
      this.mul(q1, tempQuat);
   }

   public final void inverse(Quaternion q1) {
      float norm = 1.0F / (q1.w * q1.w + q1.x * q1.x + q1.y * q1.y + q1.z * q1.z);
      this.w = norm * q1.w;
      this.x = -norm * q1.x;
      this.y = -norm * q1.y;
      this.z = -norm * q1.z;
   }

   public final void inverse() {
      float norm = 1.0F / (this.w * this.w + this.x * this.x + this.y * this.y + this.z * this.z);
      this.w *= norm;
      this.x *= -norm;
      this.y *= -norm;
      this.z *= -norm;
   }

   public final void normalize(Quaternion q1) {
      float norm = q1.x * q1.x + q1.y * q1.y + q1.z * q1.z + q1.w * q1.w;
      if (norm > 0.0F) {
         norm = 1.0F / (float)Math.sqrt((double)norm);
         this.x = norm * q1.x;
         this.y = norm * q1.y;
         this.z = norm * q1.z;
         this.w = norm * q1.w;
      } else {
         this.x = 0.0F;
         this.y = 0.0F;
         this.z = 0.0F;
         this.w = 0.0F;
      }

   }

   public final void normalize() {
      float norm = this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
      if (norm > 0.0F) {
         norm = 1.0F / (float)Math.sqrt((double)norm);
         this.x *= norm;
         this.y *= norm;
         this.z *= norm;
         this.w *= norm;
      } else {
         this.x = 0.0F;
         this.y = 0.0F;
         this.z = 0.0F;
         this.w = 0.0F;
      }

   }

   public Quaternion add(Quaternion q) {
      this.x += q.x;
      this.y += q.y;
      this.z += q.z;
      this.w += q.w;
      return this;
   }

   public Quaternion subtract(Quaternion q) {
      this.x -= q.x;
      this.y -= q.y;
      this.z -= q.z;
      this.w -= q.w;
      return this;
   }

   public float dot(Quaternion q) {
      return this.w * q.w + this.x * q.x + this.y * q.y + this.z * q.z;
   }

   public float norm() {
      return this.w * this.w + this.x * this.x + this.y * this.y + this.z * this.z;
   }

   public Quaternion normalizeLocal() {
      float n = FastMath.invSqrt(this.norm());
      this.x *= n;
      this.y *= n;
      this.z *= n;
      this.w *= n;
      return this;
   }

   public Quaternion slerp(Quaternion q1, Quaternion q2, float t) {
      if (q1.x == q2.x && q1.y == q2.y && q1.z == q2.z && q1.w == q2.w) {
         this.set(q1);
         return this;
      } else {
         float result = q1.x * q2.x + q1.y * q2.y + q1.z * q2.z + q1.w * q2.w;
         if (result < 0.0F) {
            q2.x = -q2.x;
            q2.y = -q2.y;
            q2.z = -q2.z;
            q2.w = -q2.w;
            result = -result;
         }

         float scale0 = 1.0F - t;
         float scale1 = t;
         if (1.0F - result > 0.1F) {
            float theta = FastMath.acos(result);
            float invSinTheta = 1.0F / FastMath.sin(theta);
            scale0 = FastMath.sin((1.0F - t) * theta) * invSinTheta;
            scale1 = FastMath.sin(t * theta) * invSinTheta;
         }

         this.x = scale0 * q1.x + scale1 * q2.x;
         this.y = scale0 * q1.y + scale1 * q2.y;
         this.z = scale0 * q1.z + scale1 * q2.z;
         this.w = scale0 * q1.w + scale1 * q2.w;
         return this;
      }
   }

   public void slerp(Quaternion q2, float changeAmnt) {
      if (this.x != q2.x || this.y != q2.y || this.z != q2.z || this.w != q2.w) {
         float result = this.x * q2.x + this.y * q2.y + this.z * q2.z + this.w * q2.w;
         if (result < 0.0F) {
            q2.x = -q2.x;
            q2.y = -q2.y;
            q2.z = -q2.z;
            q2.w = -q2.w;
            result = -result;
         }

         float scale0 = 1.0F - changeAmnt;
         float scale1 = changeAmnt;
         if (1.0F - result > 0.1F) {
            float theta = FastMath.acos(result);
            float invSinTheta = 1.0F / FastMath.sin(theta);
            scale0 = FastMath.sin((1.0F - changeAmnt) * theta) * invSinTheta;
            scale1 = FastMath.sin(changeAmnt * theta) * invSinTheta;
         }

         this.x = scale0 * this.x + scale1 * q2.x;
         this.y = scale0 * this.y + scale1 * q2.y;
         this.z = scale0 * this.z + scale1 * q2.z;
         this.w = scale0 * this.w + scale1 * q2.w;
      }
   }

   public void nlerp(Quaternion q2, float blend) {
      float dot = this.dot(q2);
      float blendI = 1.0F - blend;
      if (dot < 0.0F) {
         this.x = blendI * this.x - blend * q2.x;
         this.y = blendI * this.y - blend * q2.y;
         this.z = blendI * this.z - blend * q2.z;
         this.w = blendI * this.w - blend * q2.w;
      } else {
         this.x = blendI * this.x + blend * q2.x;
         this.y = blendI * this.y + blend * q2.y;
         this.z = blendI * this.z + blend * q2.z;
         this.w = blendI * this.w + blend * q2.w;
      }

      this.normalizeLocal();
   }
}
