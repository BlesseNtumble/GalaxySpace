package galaxyspace.core.client.animations.MCACommonLibrary.math;

import java.io.Serializable;

public class Matrix4f implements Serializable {
   public float m00;
   public float m01;
   public float m02;
   public float m03;
   public float m10;
   public float m11;
   public float m12;
   public float m13;
   public float m20;
   public float m21;
   public float m22;
   public float m23;
   public float m30;
   public float m31;
   public float m32;
   public float m33;
   private static final double EPS = 1.0E-8D;

   public Matrix4f(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33) {
      this.m00 = m00;
      this.m01 = m01;
      this.m02 = m02;
      this.m03 = m03;
      this.m10 = m10;
      this.m11 = m11;
      this.m12 = m12;
      this.m13 = m13;
      this.m20 = m20;
      this.m21 = m21;
      this.m22 = m22;
      this.m23 = m23;
      this.m30 = m30;
      this.m31 = m31;
      this.m32 = m32;
      this.m33 = m33;
   }

   public Matrix4f(float[] v) {
      this.m00 = v[0];
      this.m01 = v[1];
      this.m02 = v[2];
      this.m03 = v[3];
      this.m10 = v[4];
      this.m11 = v[5];
      this.m12 = v[6];
      this.m13 = v[7];
      this.m20 = v[8];
      this.m21 = v[9];
      this.m22 = v[10];
      this.m23 = v[11];
      this.m30 = v[12];
      this.m31 = v[13];
      this.m32 = v[14];
      this.m33 = v[15];
   }

   public Matrix4f(Quaternion q1, Vector3f t1, float s) {
      this.m00 = (float)((double)s * (1.0D - 2.0D * (double)q1.y * (double)q1.y - 2.0D * (double)q1.z * (double)q1.z));
      this.m10 = (float)((double)s * 2.0D * (double)(q1.x * q1.y + q1.w * q1.z));
      this.m20 = (float)((double)s * 2.0D * (double)(q1.x * q1.z - q1.w * q1.y));
      this.m01 = (float)((double)s * 2.0D * (double)(q1.x * q1.y - q1.w * q1.z));
      this.m11 = (float)((double)s * (1.0D - 2.0D * (double)q1.x * (double)q1.x - 2.0D * (double)q1.z * (double)q1.z));
      this.m21 = (float)((double)s * 2.0D * (double)(q1.y * q1.z + q1.w * q1.x));
      this.m02 = (float)((double)s * 2.0D * (double)(q1.x * q1.z + q1.w * q1.y));
      this.m12 = (float)((double)s * 2.0D * (double)(q1.y * q1.z - q1.w * q1.x));
      this.m22 = (float)((double)s * (1.0D - 2.0D * (double)q1.x * (double)q1.x - 2.0D * (double)q1.y * (double)q1.y));
      this.m03 = t1.x;
      this.m13 = t1.y;
      this.m23 = t1.z;
      this.m30 = 0.0F;
      this.m31 = 0.0F;
      this.m32 = 0.0F;
      this.m33 = 1.0F;
   }

   public Matrix4f(Matrix4f m1) {
      this.m00 = m1.m00;
      this.m01 = m1.m01;
      this.m02 = m1.m02;
      this.m03 = m1.m03;
      this.m10 = m1.m10;
      this.m11 = m1.m11;
      this.m12 = m1.m12;
      this.m13 = m1.m13;
      this.m20 = m1.m20;
      this.m21 = m1.m21;
      this.m22 = m1.m22;
      this.m23 = m1.m23;
      this.m30 = m1.m30;
      this.m31 = m1.m31;
      this.m32 = m1.m32;
      this.m33 = m1.m33;
   }

   public Matrix4f(Matrix3f m1, Vector3f t1, float s) {
      this.m00 = m1.m00 * s;
      this.m01 = m1.m01 * s;
      this.m02 = m1.m02 * s;
      this.m03 = t1.x;
      this.m10 = m1.m10 * s;
      this.m11 = m1.m11 * s;
      this.m12 = m1.m12 * s;
      this.m13 = t1.y;
      this.m20 = m1.m20 * s;
      this.m21 = m1.m21 * s;
      this.m22 = m1.m22 * s;
      this.m23 = t1.z;
      this.m30 = 0.0F;
      this.m31 = 0.0F;
      this.m32 = 0.0F;
      this.m33 = 1.0F;
   }

   public Matrix4f() {
      this.m00 = 0.0F;
      this.m01 = 0.0F;
      this.m02 = 0.0F;
      this.m03 = 0.0F;
      this.m10 = 0.0F;
      this.m11 = 0.0F;
      this.m12 = 0.0F;
      this.m13 = 0.0F;
      this.m20 = 0.0F;
      this.m21 = 0.0F;
      this.m22 = 0.0F;
      this.m23 = 0.0F;
      this.m30 = 0.0F;
      this.m31 = 0.0F;
      this.m32 = 0.0F;
      this.m33 = 0.0F;
   }

   public String toString() {
      return this.m00 + ", " + this.m01 + ", " + this.m02 + ", " + this.m03 + "\n" + this.m10 + ", " + this.m11 + ", " + this.m12 + ", " + this.m13 + "\n" + this.m20 + ", " + this.m21 + ", " + this.m22 + ", " + this.m23 + "\n" + this.m30 + ", " + this.m31 + ", " + this.m32 + ", " + this.m33 + "\n";
   }

   public final void setIdentity() {
      this.m00 = 1.0F;
      this.m01 = 0.0F;
      this.m02 = 0.0F;
      this.m03 = 0.0F;
      this.m10 = 0.0F;
      this.m11 = 1.0F;
      this.m12 = 0.0F;
      this.m13 = 0.0F;
      this.m20 = 0.0F;
      this.m21 = 0.0F;
      this.m22 = 1.0F;
      this.m23 = 0.0F;
      this.m30 = 0.0F;
      this.m31 = 0.0F;
      this.m32 = 0.0F;
      this.m33 = 1.0F;
   }

   public final void setElement(int row, int column, float value) {
      switch(row) {
      case 0:
         switch(column) {
         case 0:
            this.m00 = value;
            return;
         case 1:
            this.m01 = value;
            return;
         case 2:
            this.m02 = value;
            return;
         case 3:
            this.m03 = value;
            return;
         default:
            throw new ArrayIndexOutOfBoundsException("PROBLEM!!! Matrix4f");
         }
      case 1:
         switch(column) {
         case 0:
            this.m10 = value;
            return;
         case 1:
            this.m11 = value;
            return;
         case 2:
            this.m12 = value;
            return;
         case 3:
            this.m13 = value;
            return;
         default:
            throw new ArrayIndexOutOfBoundsException("PROBLEM!!! Matrix4f");
         }
      case 2:
         switch(column) {
         case 0:
            this.m20 = value;
            return;
         case 1:
            this.m21 = value;
            return;
         case 2:
            this.m22 = value;
            return;
         case 3:
            this.m23 = value;
            return;
         default:
            throw new ArrayIndexOutOfBoundsException("PROBLEM!!! Matrix4f");
         }
      case 3:
         switch(column) {
         case 0:
            this.m30 = value;
            return;
         case 1:
            this.m31 = value;
            return;
         case 2:
            this.m32 = value;
            return;
         case 3:
            this.m33 = value;
            return;
         default:
            throw new ArrayIndexOutOfBoundsException("PROBLEM!!! Matrix4f");
         }
      default:
         throw new ArrayIndexOutOfBoundsException("PROBLEM!!! Matrix4f");
      }
   }

   public final float getElement(int row, int column) {
      switch(row) {
      case 0:
         switch(column) {
         case 0:
            return this.m00;
         case 1:
            return this.m01;
         case 2:
            return this.m02;
         case 3:
            return this.m03;
         default:
            throw new ArrayIndexOutOfBoundsException("PROBLEM!!! Matrix4f");
         }
      case 1:
         switch(column) {
         case 0:
            return this.m10;
         case 1:
            return this.m11;
         case 2:
            return this.m12;
         case 3:
            return this.m13;
         default:
            throw new ArrayIndexOutOfBoundsException("PROBLEM!!! Matrix4f");
         }
      case 2:
         switch(column) {
         case 0:
            return this.m20;
         case 1:
            return this.m21;
         case 2:
            return this.m22;
         case 3:
            return this.m23;
         default:
            throw new ArrayIndexOutOfBoundsException("PROBLEM!!! Matrix4f");
         }
      case 3:
         switch(column) {
         case 0:
            return this.m30;
         case 1:
            return this.m31;
         case 2:
            return this.m32;
         case 3:
            return this.m33;
         }
      }

      throw new ArrayIndexOutOfBoundsException("PROBLEM!!! Matrix4f");
   }

   public final void get(Matrix3f m1) {
      double[] tmp_rot = new double[9];
      double[] tmp_scale = new double[3];
      this.getScaleRotate(tmp_scale, tmp_rot);
      m1.m00 = (float)tmp_rot[0];
      m1.m01 = (float)tmp_rot[1];
      m1.m02 = (float)tmp_rot[2];
      m1.m10 = (float)tmp_rot[3];
      m1.m11 = (float)tmp_rot[4];
      m1.m12 = (float)tmp_rot[5];
      m1.m20 = (float)tmp_rot[6];
      m1.m21 = (float)tmp_rot[7];
      m1.m22 = (float)tmp_rot[8];
   }

   public final void get(Quaternion q1) {
      double[] tmp_rot = new double[9];
      double[] tmp_scale = new double[3];
      this.getScaleRotate(tmp_scale, tmp_rot);
      double ww = 0.25D * (1.0D + tmp_rot[0] + tmp_rot[4] + tmp_rot[8]);
      if (!((ww < 0.0D ? -ww : ww) < 1.0E-30D)) {
         q1.w = (float)Math.sqrt(ww);
         ww = 0.25D / (double)q1.w;
         q1.x = (float)((tmp_rot[7] - tmp_rot[5]) * ww);
         q1.y = (float)((tmp_rot[2] - tmp_rot[6]) * ww);
         q1.z = (float)((tmp_rot[3] - tmp_rot[1]) * ww);
      } else {
         q1.w = 0.0F;
         ww = -0.5D * (tmp_rot[4] + tmp_rot[8]);
         if (!((ww < 0.0D ? -ww : ww) < 1.0E-30D)) {
            q1.x = (float)Math.sqrt(ww);
            ww = 0.5D / (double)q1.x;
            q1.y = (float)(tmp_rot[3] * ww);
            q1.z = (float)(tmp_rot[6] * ww);
         } else {
            q1.x = 0.0F;
            ww = 0.5D * (1.0D - tmp_rot[8]);
            if (!((ww < 0.0D ? -ww : ww) < 1.0E-30D)) {
               q1.y = (float)Math.sqrt(ww);
               q1.z = (float)(tmp_rot[7] / (2.0D * (double)q1.y));
            } else {
               q1.y = 0.0F;
               q1.z = 1.0F;
            }
         }
      }
   }

   public final void get(Vector3f trans) {
      trans.x = this.m03;
      trans.y = this.m13;
      trans.z = this.m23;
   }

   public final void getRotationScale(Matrix3f m1) {
      m1.m00 = this.m00;
      m1.m01 = this.m01;
      m1.m02 = this.m02;
      m1.m10 = this.m10;
      m1.m11 = this.m11;
      m1.m12 = this.m12;
      m1.m20 = this.m20;
      m1.m21 = this.m21;
      m1.m22 = this.m22;
   }

   public final float getScale() {
      double[] tmp_rot = new double[9];
      double[] tmp_scale = new double[3];
      this.getScaleRotate(tmp_scale, tmp_rot);
      return (float)Matrix3d.max3(tmp_scale);
   }

   public final void setRotationScale(Matrix3f m1) {
      this.m00 = m1.m00;
      this.m01 = m1.m01;
      this.m02 = m1.m02;
      this.m10 = m1.m10;
      this.m11 = m1.m11;
      this.m12 = m1.m12;
      this.m20 = m1.m20;
      this.m21 = m1.m21;
      this.m22 = m1.m22;
   }

   public final void setRow(int row, float x, float y, float z, float w) {
      switch(row) {
      case 0:
         this.m00 = x;
         this.m01 = y;
         this.m02 = z;
         this.m03 = w;
         break;
      case 1:
         this.m10 = x;
         this.m11 = y;
         this.m12 = z;
         this.m13 = w;
         break;
      case 2:
         this.m20 = x;
         this.m21 = y;
         this.m22 = z;
         this.m23 = w;
         break;
      case 3:
         this.m30 = x;
         this.m31 = y;
         this.m32 = z;
         this.m33 = w;
         break;
      default:
         throw new ArrayIndexOutOfBoundsException("PROBLEM!!! Matrix4f");
      }

   }

   public final void setRow(int row, float[] v) {
      switch(row) {
      case 0:
         this.m00 = v[0];
         this.m01 = v[1];
         this.m02 = v[2];
         this.m03 = v[3];
         break;
      case 1:
         this.m10 = v[0];
         this.m11 = v[1];
         this.m12 = v[2];
         this.m13 = v[3];
         break;
      case 2:
         this.m20 = v[0];
         this.m21 = v[1];
         this.m22 = v[2];
         this.m23 = v[3];
         break;
      case 3:
         this.m30 = v[0];
         this.m31 = v[1];
         this.m32 = v[2];
         this.m33 = v[3];
         break;
      default:
         throw new ArrayIndexOutOfBoundsException("PROBLEM!!! Matrix4f");
      }

   }

   public final void setColumn(int column, float x, float y, float z, float w) {
      switch(column) {
      case 0:
         this.m00 = x;
         this.m10 = y;
         this.m20 = z;
         this.m30 = w;
         break;
      case 1:
         this.m01 = x;
         this.m11 = y;
         this.m21 = z;
         this.m31 = w;
         break;
      case 2:
         this.m02 = x;
         this.m12 = y;
         this.m22 = z;
         this.m32 = w;
         break;
      case 3:
         this.m03 = x;
         this.m13 = y;
         this.m23 = z;
         this.m33 = w;
         break;
      default:
         throw new ArrayIndexOutOfBoundsException("PROBLEM!!! Matrix4f9");
      }

   }

   public final void setColumn(int column, float[] v) {
      switch(column) {
      case 0:
         this.m00 = v[0];
         this.m10 = v[1];
         this.m20 = v[2];
         this.m30 = v[3];
         break;
      case 1:
         this.m01 = v[0];
         this.m11 = v[1];
         this.m21 = v[2];
         this.m31 = v[3];
         break;
      case 2:
         this.m02 = v[0];
         this.m12 = v[1];
         this.m22 = v[2];
         this.m32 = v[3];
         break;
      case 3:
         this.m03 = v[0];
         this.m13 = v[1];
         this.m23 = v[2];
         this.m33 = v[3];
         break;
      default:
         throw new ArrayIndexOutOfBoundsException("PROBLEM!!! Matrix4f");
      }

   }

   public final void add(float scalar) {
      this.m00 += scalar;
      this.m01 += scalar;
      this.m02 += scalar;
      this.m03 += scalar;
      this.m10 += scalar;
      this.m11 += scalar;
      this.m12 += scalar;
      this.m13 += scalar;
      this.m20 += scalar;
      this.m21 += scalar;
      this.m22 += scalar;
      this.m23 += scalar;
      this.m30 += scalar;
      this.m31 += scalar;
      this.m32 += scalar;
      this.m33 += scalar;
   }

   public final void add(float scalar, Matrix4f m1) {
      this.m00 = m1.m00 + scalar;
      this.m01 = m1.m01 + scalar;
      this.m02 = m1.m02 + scalar;
      this.m03 = m1.m03 + scalar;
      this.m10 = m1.m10 + scalar;
      this.m11 = m1.m11 + scalar;
      this.m12 = m1.m12 + scalar;
      this.m13 = m1.m13 + scalar;
      this.m20 = m1.m20 + scalar;
      this.m21 = m1.m21 + scalar;
      this.m22 = m1.m22 + scalar;
      this.m23 = m1.m23 + scalar;
      this.m30 = m1.m30 + scalar;
      this.m31 = m1.m31 + scalar;
      this.m32 = m1.m32 + scalar;
      this.m33 = m1.m33 + scalar;
   }

   public final void add(Matrix4f m1, Matrix4f m2) {
      this.m00 = m1.m00 + m2.m00;
      this.m01 = m1.m01 + m2.m01;
      this.m02 = m1.m02 + m2.m02;
      this.m03 = m1.m03 + m2.m03;
      this.m10 = m1.m10 + m2.m10;
      this.m11 = m1.m11 + m2.m11;
      this.m12 = m1.m12 + m2.m12;
      this.m13 = m1.m13 + m2.m13;
      this.m20 = m1.m20 + m2.m20;
      this.m21 = m1.m21 + m2.m21;
      this.m22 = m1.m22 + m2.m22;
      this.m23 = m1.m23 + m2.m23;
      this.m30 = m1.m30 + m2.m30;
      this.m31 = m1.m31 + m2.m31;
      this.m32 = m1.m32 + m2.m32;
      this.m33 = m1.m33 + m2.m33;
   }

   public final void add(Matrix4f m1) {
      this.m00 += m1.m00;
      this.m01 += m1.m01;
      this.m02 += m1.m02;
      this.m03 += m1.m03;
      this.m10 += m1.m10;
      this.m11 += m1.m11;
      this.m12 += m1.m12;
      this.m13 += m1.m13;
      this.m20 += m1.m20;
      this.m21 += m1.m21;
      this.m22 += m1.m22;
      this.m23 += m1.m23;
      this.m30 += m1.m30;
      this.m31 += m1.m31;
      this.m32 += m1.m32;
      this.m33 += m1.m33;
   }

   public final void sub(Matrix4f m1, Matrix4f m2) {
      this.m00 = m1.m00 - m2.m00;
      this.m01 = m1.m01 - m2.m01;
      this.m02 = m1.m02 - m2.m02;
      this.m03 = m1.m03 - m2.m03;
      this.m10 = m1.m10 - m2.m10;
      this.m11 = m1.m11 - m2.m11;
      this.m12 = m1.m12 - m2.m12;
      this.m13 = m1.m13 - m2.m13;
      this.m20 = m1.m20 - m2.m20;
      this.m21 = m1.m21 - m2.m21;
      this.m22 = m1.m22 - m2.m22;
      this.m23 = m1.m23 - m2.m23;
      this.m30 = m1.m30 - m2.m30;
      this.m31 = m1.m31 - m2.m31;
      this.m32 = m1.m32 - m2.m32;
      this.m33 = m1.m33 - m2.m33;
   }

   public final void sub(Matrix4f m1) {
      this.m00 -= m1.m00;
      this.m01 -= m1.m01;
      this.m02 -= m1.m02;
      this.m03 -= m1.m03;
      this.m10 -= m1.m10;
      this.m11 -= m1.m11;
      this.m12 -= m1.m12;
      this.m13 -= m1.m13;
      this.m20 -= m1.m20;
      this.m21 -= m1.m21;
      this.m22 -= m1.m22;
      this.m23 -= m1.m23;
      this.m30 -= m1.m30;
      this.m31 -= m1.m31;
      this.m32 -= m1.m32;
      this.m33 -= m1.m33;
   }

   public final Matrix4f transpose() {
      float temp = this.m10;
      this.m10 = this.m01;
      this.m01 = temp;
      temp = this.m20;
      this.m20 = this.m02;
      this.m02 = temp;
      temp = this.m30;
      this.m30 = this.m03;
      this.m03 = temp;
      temp = this.m21;
      this.m21 = this.m12;
      this.m12 = temp;
      temp = this.m31;
      this.m31 = this.m13;
      this.m13 = temp;
      temp = this.m32;
      this.m32 = this.m23;
      this.m23 = temp;
      return this;
   }

   public final void transpose(Matrix4f m1) {
      if (this != m1) {
         this.m00 = m1.m00;
         this.m01 = m1.m10;
         this.m02 = m1.m20;
         this.m03 = m1.m30;
         this.m10 = m1.m01;
         this.m11 = m1.m11;
         this.m12 = m1.m21;
         this.m13 = m1.m31;
         this.m20 = m1.m02;
         this.m21 = m1.m12;
         this.m22 = m1.m22;
         this.m23 = m1.m32;
         this.m30 = m1.m03;
         this.m31 = m1.m13;
         this.m32 = m1.m23;
         this.m33 = m1.m33;
      } else {
         this.transpose();
      }

   }

   public final Matrix4f set(Quaternion q1) {
      this.m00 = 1.0F - 2.0F * q1.y * q1.y - 2.0F * q1.z * q1.z;
      this.m10 = 2.0F * (q1.x * q1.y + q1.w * q1.z);
      this.m20 = 2.0F * (q1.x * q1.z - q1.w * q1.y);
      this.m01 = 2.0F * (q1.x * q1.y - q1.w * q1.z);
      this.m11 = 1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.z * q1.z;
      this.m21 = 2.0F * (q1.y * q1.z + q1.w * q1.x);
      this.m02 = 2.0F * (q1.x * q1.z + q1.w * q1.y);
      this.m12 = 2.0F * (q1.y * q1.z - q1.w * q1.x);
      this.m22 = 1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.y * q1.y;
      this.m03 = 0.0F;
      this.m13 = 0.0F;
      this.m23 = 0.0F;
      this.m30 = 0.0F;
      this.m31 = 0.0F;
      this.m32 = 0.0F;
      this.m33 = 1.0F;
      return this;
   }

   public final void set(AxisAngle4f a1) {
      float mag = (float)Math.sqrt((double)(a1.x * a1.x + a1.y * a1.y + a1.z * a1.z));
      if ((double)mag < 1.0E-8D) {
         this.m00 = 1.0F;
         this.m01 = 0.0F;
         this.m02 = 0.0F;
         this.m10 = 0.0F;
         this.m11 = 1.0F;
         this.m12 = 0.0F;
         this.m20 = 0.0F;
         this.m21 = 0.0F;
         this.m22 = 1.0F;
      } else {
         mag = 1.0F / mag;
         float ax = a1.x * mag;
         float ay = a1.y * mag;
         float az = a1.z * mag;
         float sinTheta = (float)Math.sin((double)a1.angle);
         float cosTheta = (float)Math.cos((double)a1.angle);
         float t = 1.0F - cosTheta;
         float xz = ax * az;
         float xy = ax * ay;
         float yz = ay * az;
         this.m00 = t * ax * ax + cosTheta;
         this.m01 = t * xy - sinTheta * az;
         this.m02 = t * xz + sinTheta * ay;
         this.m10 = t * xy + sinTheta * az;
         this.m11 = t * ay * ay + cosTheta;
         this.m12 = t * yz - sinTheta * ax;
         this.m20 = t * xz - sinTheta * ay;
         this.m21 = t * yz + sinTheta * ax;
         this.m22 = t * az * az + cosTheta;
      }

      this.m03 = 0.0F;
      this.m13 = 0.0F;
      this.m23 = 0.0F;
      this.m30 = 0.0F;
      this.m31 = 0.0F;
      this.m32 = 0.0F;
      this.m33 = 1.0F;
   }

   public final void set(Quaternion q1, Vector3f t1, float s) {
      this.m00 = s * (1.0F - 2.0F * q1.y * q1.y - 2.0F * q1.z * q1.z);
      this.m10 = s * 2.0F * (q1.x * q1.y + q1.w * q1.z);
      this.m20 = s * 2.0F * (q1.x * q1.z - q1.w * q1.y);
      this.m01 = s * 2.0F * (q1.x * q1.y - q1.w * q1.z);
      this.m11 = s * (1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.z * q1.z);
      this.m21 = s * 2.0F * (q1.y * q1.z + q1.w * q1.x);
      this.m02 = s * 2.0F * (q1.x * q1.z + q1.w * q1.y);
      this.m12 = s * 2.0F * (q1.y * q1.z - q1.w * q1.x);
      this.m22 = s * (1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.y * q1.y);
      this.m03 = t1.x;
      this.m13 = t1.y;
      this.m23 = t1.z;
      this.m30 = 0.0F;
      this.m31 = 0.0F;
      this.m32 = 0.0F;
      this.m33 = 1.0F;
   }

   public final void set(Matrix4f m1) {
      this.m00 = m1.m00;
      this.m01 = m1.m01;
      this.m02 = m1.m02;
      this.m03 = m1.m03;
      this.m10 = m1.m10;
      this.m11 = m1.m11;
      this.m12 = m1.m12;
      this.m13 = m1.m13;
      this.m20 = m1.m20;
      this.m21 = m1.m21;
      this.m22 = m1.m22;
      this.m23 = m1.m23;
      this.m30 = m1.m30;
      this.m31 = m1.m31;
      this.m32 = m1.m32;
      this.m33 = m1.m33;
   }

   public final void invert(Matrix4f m1) {
      this.invertGeneral(m1);
   }

   public final void invert() {
      this.invertGeneral(this);
   }

   final void invertGeneral(Matrix4f m1) {
      double[] temp = new double[16];
      double[] result = new double[16];
      int[] row_perm = new int[4];
      temp[0] = (double)m1.m00;
      temp[1] = (double)m1.m01;
      temp[2] = (double)m1.m02;
      temp[3] = (double)m1.m03;
      temp[4] = (double)m1.m10;
      temp[5] = (double)m1.m11;
      temp[6] = (double)m1.m12;
      temp[7] = (double)m1.m13;
      temp[8] = (double)m1.m20;
      temp[9] = (double)m1.m21;
      temp[10] = (double)m1.m22;
      temp[11] = (double)m1.m23;
      temp[12] = (double)m1.m30;
      temp[13] = (double)m1.m31;
      temp[14] = (double)m1.m32;
      temp[15] = (double)m1.m33;
      if (!luDecomposition(temp, row_perm)) {
         try {
            throw new Exception("PROBLEM!!! Matrix4f12");
         } catch (Exception var9) {
            var9.printStackTrace();
         }
      }

      for(int i = 0; i < 16; ++i) {
         result[i] = 0.0D;
      }

      result[0] = 1.0D;
      result[5] = 1.0D;
      result[10] = 1.0D;
      result[15] = 1.0D;
      luBacksubstitution(temp, row_perm, result);
      this.m00 = (float)result[0];
      this.m01 = (float)result[1];
      this.m02 = (float)result[2];
      this.m03 = (float)result[3];
      this.m10 = (float)result[4];
      this.m11 = (float)result[5];
      this.m12 = (float)result[6];
      this.m13 = (float)result[7];
      this.m20 = (float)result[8];
      this.m21 = (float)result[9];
      this.m22 = (float)result[10];
      this.m23 = (float)result[11];
      this.m30 = (float)result[12];
      this.m31 = (float)result[13];
      this.m32 = (float)result[14];
      this.m33 = (float)result[15];
   }

   static boolean luDecomposition(double[] matrix0, int[] row_perm) {
      double[] row_scale = new double[4];
      int i = 0;
      int imax = 0;

      int j;
      double big;
      for(j = 4; j-- != 0; row_scale[imax++] = 1.0D / big) {
         big = 0.0D;
         int var4 = 4;

         while(var4-- != 0) {
            double temp = matrix0[i++];
            temp = Math.abs(temp);
            if (temp > big) {
               big = temp;
            }
         }

         if (big == 0.0D) {
            return false;
         }
      }

      int mtx = 0;

      for(j = 0; j < 4; ++j) {
         int target;
         int p2;
         double sum;
         int k;
         int p1;
         for(i = 0; i < j; ++i) {
            target = mtx + 4 * i + j;
            sum = matrix0[target];
            k = i;
            p1 = mtx + 4 * i;

            for(p2 = mtx + j; k-- != 0; p2 += 4) {
               sum -= matrix0[p1] * matrix0[p2];
               ++p1;
            }

            matrix0[target] = sum;
         }

         big = 0.0D;
         imax = -1;

         double temp;
         for(i = j; i < 4; ++i) {
            target = mtx + 4 * i + j;
            sum = matrix0[target];
            k = j;
            p1 = mtx + 4 * i;

            for(p2 = mtx + j; k-- != 0; p2 += 4) {
               sum -= matrix0[p1] * matrix0[p2];
               ++p1;
            }

            matrix0[target] = sum;
            if ((temp = row_scale[i] * Math.abs(sum)) >= big) {
               big = temp;
               imax = i;
            }
         }

         if (imax < 0) {
            throw new RuntimeException("PROBLEM!!! Matrix4f");
         }

         if (j != imax) {
            k = 4;
            p1 = mtx + 4 * imax;

            for(p2 = mtx + 4 * j; k-- != 0; matrix0[p2++] = temp) {
               temp = matrix0[p1];
               matrix0[p1++] = matrix0[p2];
            }

            row_scale[imax] = row_scale[j];
         }

         row_perm[j] = imax;
         if (matrix0[mtx + 4 * j + j] == 0.0D) {
            return false;
         }

         if (j != 3) {
            temp = 1.0D / matrix0[mtx + 4 * j + j];
            target = mtx + 4 * (j + 1) + j;

            for(i = 3 - j; i-- != 0; target += 4) {
               matrix0[target] *= temp;
            }
         }
      }

      return true;
   }

   static void luBacksubstitution(double[] matrix1, int[] row_perm, double[] matrix2) {
      int rp = 0;

      for(int k = 0; k < 4; ++k) {
         int cv = k;
         int ii = -1;

         int rv;
         for(int i = 0; i < 4; ++i) {
            int ip = row_perm[rp + i];
            double sum = matrix2[cv + 4 * ip];
            matrix2[cv + 4 * ip] = matrix2[cv + 4 * i];
            if (ii >= 0) {
               rv = i * 4;

               for(int j = ii; j <= i - 1; ++j) {
                  sum -= matrix1[rv + j] * matrix2[cv + 4 * j];
               }
            } else if (sum != 0.0D) {
               ii = i;
            }

            matrix2[cv + 4 * i] = sum;
         }

         rv = 12;
         matrix2[cv + 12] /= matrix1[rv + 3];
         rv = rv - 4;
         matrix2[cv + 8] = (matrix2[cv + 8] - matrix1[rv + 3] * matrix2[cv + 12]) / matrix1[rv + 2];
         rv -= 4;
         matrix2[cv + 4] = (matrix2[cv + 4] - matrix1[rv + 2] * matrix2[cv + 8] - matrix1[rv + 3] * matrix2[cv + 12]) / matrix1[rv + 1];
         rv -= 4;
         matrix2[cv + 0] = (matrix2[cv + 0] - matrix1[rv + 1] * matrix2[cv + 4] - matrix1[rv + 2] * matrix2[cv + 8] - matrix1[rv + 3] * matrix2[cv + 12]) / matrix1[rv + 0];
      }

   }

   public final float determinant() {
      float det = this.m00 * (this.m11 * this.m22 * this.m33 + this.m12 * this.m23 * this.m31 + this.m13 * this.m21 * this.m32 - this.m13 * this.m22 * this.m31 - this.m11 * this.m23 * this.m32 - this.m12 * this.m21 * this.m33);
      det -= this.m01 * (this.m10 * this.m22 * this.m33 + this.m12 * this.m23 * this.m30 + this.m13 * this.m20 * this.m32 - this.m13 * this.m22 * this.m30 - this.m10 * this.m23 * this.m32 - this.m12 * this.m20 * this.m33);
      det += this.m02 * (this.m10 * this.m21 * this.m33 + this.m11 * this.m23 * this.m30 + this.m13 * this.m20 * this.m31 - this.m13 * this.m21 * this.m30 - this.m10 * this.m23 * this.m31 - this.m11 * this.m20 * this.m33);
      det -= this.m03 * (this.m10 * this.m21 * this.m32 + this.m11 * this.m22 * this.m30 + this.m12 * this.m20 * this.m31 - this.m12 * this.m21 * this.m30 - this.m10 * this.m22 * this.m31 - this.m11 * this.m20 * this.m32);
      return det;
   }

   public final void set(Matrix3f m1) {
      this.m00 = m1.m00;
      this.m01 = m1.m01;
      this.m02 = m1.m02;
      this.m03 = 0.0F;
      this.m10 = m1.m10;
      this.m11 = m1.m11;
      this.m12 = m1.m12;
      this.m13 = 0.0F;
      this.m20 = m1.m20;
      this.m21 = m1.m21;
      this.m22 = m1.m22;
      this.m23 = 0.0F;
      this.m30 = 0.0F;
      this.m31 = 0.0F;
      this.m32 = 0.0F;
      this.m33 = 1.0F;
   }

   public final void set(float scale) {
      this.m00 = scale;
      this.m01 = 0.0F;
      this.m02 = 0.0F;
      this.m03 = 0.0F;
      this.m10 = 0.0F;
      this.m11 = scale;
      this.m12 = 0.0F;
      this.m13 = 0.0F;
      this.m20 = 0.0F;
      this.m21 = 0.0F;
      this.m22 = scale;
      this.m23 = 0.0F;
      this.m30 = 0.0F;
      this.m31 = 0.0F;
      this.m32 = 0.0F;
      this.m33 = 1.0F;
   }

   public final void set(float[] m) {
      this.m00 = m[0];
      this.m01 = m[1];
      this.m02 = m[2];
      this.m03 = m[3];
      this.m10 = m[4];
      this.m11 = m[5];
      this.m12 = m[6];
      this.m13 = m[7];
      this.m20 = m[8];
      this.m21 = m[9];
      this.m22 = m[10];
      this.m23 = m[11];
      this.m30 = m[12];
      this.m31 = m[13];
      this.m32 = m[14];
      this.m33 = m[15];
   }

   public final void set(Vector3f v1) {
      this.m00 = 1.0F;
      this.m01 = 0.0F;
      this.m02 = 0.0F;
      this.m03 = v1.x;
      this.m10 = 0.0F;
      this.m11 = 1.0F;
      this.m12 = 0.0F;
      this.m13 = v1.y;
      this.m20 = 0.0F;
      this.m21 = 0.0F;
      this.m22 = 1.0F;
      this.m23 = v1.z;
      this.m30 = 0.0F;
      this.m31 = 0.0F;
      this.m32 = 0.0F;
      this.m33 = 1.0F;
   }

   public final void set(float scale, Vector3f t1) {
      this.m00 = scale;
      this.m01 = 0.0F;
      this.m02 = 0.0F;
      this.m03 = t1.x;
      this.m10 = 0.0F;
      this.m11 = scale;
      this.m12 = 0.0F;
      this.m13 = t1.y;
      this.m20 = 0.0F;
      this.m21 = 0.0F;
      this.m22 = scale;
      this.m23 = t1.z;
      this.m30 = 0.0F;
      this.m31 = 0.0F;
      this.m32 = 0.0F;
      this.m33 = 1.0F;
   }

   public final void set(Vector3f t1, float scale) {
      this.m00 = scale;
      this.m01 = 0.0F;
      this.m02 = 0.0F;
      this.m03 = scale * t1.x;
      this.m10 = 0.0F;
      this.m11 = scale;
      this.m12 = 0.0F;
      this.m13 = scale * t1.y;
      this.m20 = 0.0F;
      this.m21 = 0.0F;
      this.m22 = scale;
      this.m23 = scale * t1.z;
      this.m30 = 0.0F;
      this.m31 = 0.0F;
      this.m32 = 0.0F;
      this.m33 = 1.0F;
   }

   public final void set(Matrix3f m1, Vector3f t1, float scale) {
      this.m00 = m1.m00 * scale;
      this.m01 = m1.m01 * scale;
      this.m02 = m1.m02 * scale;
      this.m03 = t1.x;
      this.m10 = m1.m10 * scale;
      this.m11 = m1.m11 * scale;
      this.m12 = m1.m12 * scale;
      this.m13 = t1.y;
      this.m20 = m1.m20 * scale;
      this.m21 = m1.m21 * scale;
      this.m22 = m1.m22 * scale;
      this.m23 = t1.z;
      this.m30 = 0.0F;
      this.m31 = 0.0F;
      this.m32 = 0.0F;
      this.m33 = 1.0F;
   }

   public final void setTranslation(Vector3f trans) {
      this.m03 = trans.x;
      this.m13 = trans.y;
      this.m23 = trans.z;
   }

   public final void rotX(float angle) {
      float sinAngle = (float)Math.sin((double)angle);
      float cosAngle = (float)Math.cos((double)angle);
      this.m00 = 1.0F;
      this.m01 = 0.0F;
      this.m02 = 0.0F;
      this.m03 = 0.0F;
      this.m10 = 0.0F;
      this.m11 = cosAngle;
      this.m12 = -sinAngle;
      this.m13 = 0.0F;
      this.m20 = 0.0F;
      this.m21 = sinAngle;
      this.m22 = cosAngle;
      this.m23 = 0.0F;
      this.m30 = 0.0F;
      this.m31 = 0.0F;
      this.m32 = 0.0F;
      this.m33 = 1.0F;
   }

   public final void rotY(float angle) {
      float sinAngle = (float)Math.sin((double)angle);
      float cosAngle = (float)Math.cos((double)angle);
      this.m00 = cosAngle;
      this.m01 = 0.0F;
      this.m02 = sinAngle;
      this.m03 = 0.0F;
      this.m10 = 0.0F;
      this.m11 = 1.0F;
      this.m12 = 0.0F;
      this.m13 = 0.0F;
      this.m20 = -sinAngle;
      this.m21 = 0.0F;
      this.m22 = cosAngle;
      this.m23 = 0.0F;
      this.m30 = 0.0F;
      this.m31 = 0.0F;
      this.m32 = 0.0F;
      this.m33 = 1.0F;
   }

   public final void rotZ(float angle) {
      float sinAngle = (float)Math.sin((double)angle);
      float cosAngle = (float)Math.cos((double)angle);
      this.m00 = cosAngle;
      this.m01 = -sinAngle;
      this.m02 = 0.0F;
      this.m03 = 0.0F;
      this.m10 = sinAngle;
      this.m11 = cosAngle;
      this.m12 = 0.0F;
      this.m13 = 0.0F;
      this.m20 = 0.0F;
      this.m21 = 0.0F;
      this.m22 = 1.0F;
      this.m23 = 0.0F;
      this.m30 = 0.0F;
      this.m31 = 0.0F;
      this.m32 = 0.0F;
      this.m33 = 1.0F;
   }

   public final void mul(float scalar) {
      this.m00 *= scalar;
      this.m01 *= scalar;
      this.m02 *= scalar;
      this.m03 *= scalar;
      this.m10 *= scalar;
      this.m11 *= scalar;
      this.m12 *= scalar;
      this.m13 *= scalar;
      this.m20 *= scalar;
      this.m21 *= scalar;
      this.m22 *= scalar;
      this.m23 *= scalar;
      this.m30 *= scalar;
      this.m31 *= scalar;
      this.m32 *= scalar;
      this.m33 *= scalar;
   }

   public final void mul(float scalar, Matrix4f m1) {
      this.m00 = m1.m00 * scalar;
      this.m01 = m1.m01 * scalar;
      this.m02 = m1.m02 * scalar;
      this.m03 = m1.m03 * scalar;
      this.m10 = m1.m10 * scalar;
      this.m11 = m1.m11 * scalar;
      this.m12 = m1.m12 * scalar;
      this.m13 = m1.m13 * scalar;
      this.m20 = m1.m20 * scalar;
      this.m21 = m1.m21 * scalar;
      this.m22 = m1.m22 * scalar;
      this.m23 = m1.m23 * scalar;
      this.m30 = m1.m30 * scalar;
      this.m31 = m1.m31 * scalar;
      this.m32 = m1.m32 * scalar;
      this.m33 = m1.m33 * scalar;
   }

   public final void mul(Matrix4f m1) {
      float m00 = this.m00 * m1.m00 + this.m01 * m1.m10 + this.m02 * m1.m20 + this.m03 * m1.m30;
      float m01 = this.m00 * m1.m01 + this.m01 * m1.m11 + this.m02 * m1.m21 + this.m03 * m1.m31;
      float m02 = this.m00 * m1.m02 + this.m01 * m1.m12 + this.m02 * m1.m22 + this.m03 * m1.m32;
      float m03 = this.m00 * m1.m03 + this.m01 * m1.m13 + this.m02 * m1.m23 + this.m03 * m1.m33;
      float m10 = this.m10 * m1.m00 + this.m11 * m1.m10 + this.m12 * m1.m20 + this.m13 * m1.m30;
      float m11 = this.m10 * m1.m01 + this.m11 * m1.m11 + this.m12 * m1.m21 + this.m13 * m1.m31;
      float m12 = this.m10 * m1.m02 + this.m11 * m1.m12 + this.m12 * m1.m22 + this.m13 * m1.m32;
      float m13 = this.m10 * m1.m03 + this.m11 * m1.m13 + this.m12 * m1.m23 + this.m13 * m1.m33;
      float m20 = this.m20 * m1.m00 + this.m21 * m1.m10 + this.m22 * m1.m20 + this.m23 * m1.m30;
      float m21 = this.m20 * m1.m01 + this.m21 * m1.m11 + this.m22 * m1.m21 + this.m23 * m1.m31;
      float m22 = this.m20 * m1.m02 + this.m21 * m1.m12 + this.m22 * m1.m22 + this.m23 * m1.m32;
      float m23 = this.m20 * m1.m03 + this.m21 * m1.m13 + this.m22 * m1.m23 + this.m23 * m1.m33;
      float m30 = this.m30 * m1.m00 + this.m31 * m1.m10 + this.m32 * m1.m20 + this.m33 * m1.m30;
      float m31 = this.m30 * m1.m01 + this.m31 * m1.m11 + this.m32 * m1.m21 + this.m33 * m1.m31;
      float m32 = this.m30 * m1.m02 + this.m31 * m1.m12 + this.m32 * m1.m22 + this.m33 * m1.m32;
      float m33 = this.m30 * m1.m03 + this.m31 * m1.m13 + this.m32 * m1.m23 + this.m33 * m1.m33;
      this.m00 = m00;
      this.m01 = m01;
      this.m02 = m02;
      this.m03 = m03;
      this.m10 = m10;
      this.m11 = m11;
      this.m12 = m12;
      this.m13 = m13;
      this.m20 = m20;
      this.m21 = m21;
      this.m22 = m22;
      this.m23 = m23;
      this.m30 = m30;
      this.m31 = m31;
      this.m32 = m32;
      this.m33 = m33;
   }

   public final void mul(Matrix4f m1, Matrix4f m2) {
      if (this != m1 && this != m2) {
         this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20 + m1.m03 * m2.m30;
         this.m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21 + m1.m03 * m2.m31;
         this.m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22 + m1.m03 * m2.m32;
         this.m03 = m1.m00 * m2.m03 + m1.m01 * m2.m13 + m1.m02 * m2.m23 + m1.m03 * m2.m33;
         this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20 + m1.m13 * m2.m30;
         this.m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21 + m1.m13 * m2.m31;
         this.m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22 + m1.m13 * m2.m32;
         this.m13 = m1.m10 * m2.m03 + m1.m11 * m2.m13 + m1.m12 * m2.m23 + m1.m13 * m2.m33;
         this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20 + m1.m23 * m2.m30;
         this.m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21 + m1.m23 * m2.m31;
         this.m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22 + m1.m23 * m2.m32;
         this.m23 = m1.m20 * m2.m03 + m1.m21 * m2.m13 + m1.m22 * m2.m23 + m1.m23 * m2.m33;
         this.m30 = m1.m30 * m2.m00 + m1.m31 * m2.m10 + m1.m32 * m2.m20 + m1.m33 * m2.m30;
         this.m31 = m1.m30 * m2.m01 + m1.m31 * m2.m11 + m1.m32 * m2.m21 + m1.m33 * m2.m31;
         this.m32 = m1.m30 * m2.m02 + m1.m31 * m2.m12 + m1.m32 * m2.m22 + m1.m33 * m2.m32;
         this.m33 = m1.m30 * m2.m03 + m1.m31 * m2.m13 + m1.m32 * m2.m23 + m1.m33 * m2.m33;
      } else {
         float m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20 + m1.m03 * m2.m30;
         float m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21 + m1.m03 * m2.m31;
         float m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22 + m1.m03 * m2.m32;
         float m03 = m1.m00 * m2.m03 + m1.m01 * m2.m13 + m1.m02 * m2.m23 + m1.m03 * m2.m33;
         float m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20 + m1.m13 * m2.m30;
         float m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21 + m1.m13 * m2.m31;
         float m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22 + m1.m13 * m2.m32;
         float m13 = m1.m10 * m2.m03 + m1.m11 * m2.m13 + m1.m12 * m2.m23 + m1.m13 * m2.m33;
         float m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20 + m1.m23 * m2.m30;
         float m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21 + m1.m23 * m2.m31;
         float m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22 + m1.m23 * m2.m32;
         float m23 = m1.m20 * m2.m03 + m1.m21 * m2.m13 + m1.m22 * m2.m23 + m1.m23 * m2.m33;
         float m30 = m1.m30 * m2.m00 + m1.m31 * m2.m10 + m1.m32 * m2.m20 + m1.m33 * m2.m30;
         float m31 = m1.m30 * m2.m01 + m1.m31 * m2.m11 + m1.m32 * m2.m21 + m1.m33 * m2.m31;
         float m32 = m1.m30 * m2.m02 + m1.m31 * m2.m12 + m1.m32 * m2.m22 + m1.m33 * m2.m32;
         float m33 = m1.m30 * m2.m03 + m1.m31 * m2.m13 + m1.m32 * m2.m23 + m1.m33 * m2.m33;
         this.m00 = m00;
         this.m01 = m01;
         this.m02 = m02;
         this.m03 = m03;
         this.m10 = m10;
         this.m11 = m11;
         this.m12 = m12;
         this.m13 = m13;
         this.m20 = m20;
         this.m21 = m21;
         this.m22 = m22;
         this.m23 = m23;
         this.m30 = m30;
         this.m31 = m31;
         this.m32 = m32;
         this.m33 = m33;
      }

   }

   public final void mulTransposeBoth(Matrix4f m1, Matrix4f m2) {
      if (this != m1 && this != m2) {
         this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02 + m1.m30 * m2.m03;
         this.m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12 + m1.m30 * m2.m13;
         this.m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22 + m1.m30 * m2.m23;
         this.m03 = m1.m00 * m2.m30 + m1.m10 * m2.m31 + m1.m20 * m2.m32 + m1.m30 * m2.m33;
         this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02 + m1.m31 * m2.m03;
         this.m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12 + m1.m31 * m2.m13;
         this.m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22 + m1.m31 * m2.m23;
         this.m13 = m1.m01 * m2.m30 + m1.m11 * m2.m31 + m1.m21 * m2.m32 + m1.m31 * m2.m33;
         this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02 + m1.m32 * m2.m03;
         this.m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12 + m1.m32 * m2.m13;
         this.m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22 + m1.m32 * m2.m23;
         this.m23 = m1.m02 * m2.m30 + m1.m12 * m2.m31 + m1.m22 * m2.m32 + m1.m32 * m2.m33;
         this.m30 = m1.m03 * m2.m00 + m1.m13 * m2.m01 + m1.m23 * m2.m02 + m1.m33 * m2.m03;
         this.m31 = m1.m03 * m2.m10 + m1.m13 * m2.m11 + m1.m23 * m2.m12 + m1.m33 * m2.m13;
         this.m32 = m1.m03 * m2.m20 + m1.m13 * m2.m21 + m1.m23 * m2.m22 + m1.m33 * m2.m23;
         this.m33 = m1.m03 * m2.m30 + m1.m13 * m2.m31 + m1.m23 * m2.m32 + m1.m33 * m2.m33;
      } else {
         float m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02 + m1.m30 * m2.m03;
         float m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12 + m1.m30 * m2.m13;
         float m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22 + m1.m30 * m2.m23;
         float m03 = m1.m00 * m2.m30 + m1.m10 * m2.m31 + m1.m20 * m2.m32 + m1.m30 * m2.m33;
         float m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02 + m1.m31 * m2.m03;
         float m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12 + m1.m31 * m2.m13;
         float m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22 + m1.m31 * m2.m23;
         float m13 = m1.m01 * m2.m30 + m1.m11 * m2.m31 + m1.m21 * m2.m32 + m1.m31 * m2.m33;
         float m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02 + m1.m32 * m2.m03;
         float m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12 + m1.m32 * m2.m13;
         float m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22 + m1.m32 * m2.m23;
         float m23 = m1.m02 * m2.m30 + m1.m12 * m2.m31 + m1.m22 * m2.m32 + m1.m32 * m2.m33;
         float m30 = m1.m03 * m2.m00 + m1.m13 * m2.m01 + m1.m23 * m2.m02 + m1.m33 * m2.m03;
         float m31 = m1.m03 * m2.m10 + m1.m13 * m2.m11 + m1.m23 * m2.m12 + m1.m33 * m2.m13;
         float m32 = m1.m03 * m2.m20 + m1.m13 * m2.m21 + m1.m23 * m2.m22 + m1.m33 * m2.m23;
         float m33 = m1.m03 * m2.m30 + m1.m13 * m2.m31 + m1.m23 * m2.m32 + m1.m33 * m2.m33;
         this.m00 = m00;
         this.m01 = m01;
         this.m02 = m02;
         this.m03 = m03;
         this.m10 = m10;
         this.m11 = m11;
         this.m12 = m12;
         this.m13 = m13;
         this.m20 = m20;
         this.m21 = m21;
         this.m22 = m22;
         this.m23 = m23;
         this.m30 = m30;
         this.m31 = m31;
         this.m32 = m32;
         this.m33 = m33;
      }

   }

   public final void mulTransposeRight(Matrix4f m1, Matrix4f m2) {
      if (this != m1 && this != m2) {
         this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02 + m1.m03 * m2.m03;
         this.m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12 + m1.m03 * m2.m13;
         this.m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22 + m1.m03 * m2.m23;
         this.m03 = m1.m00 * m2.m30 + m1.m01 * m2.m31 + m1.m02 * m2.m32 + m1.m03 * m2.m33;
         this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02 + m1.m13 * m2.m03;
         this.m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12 + m1.m13 * m2.m13;
         this.m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22 + m1.m13 * m2.m23;
         this.m13 = m1.m10 * m2.m30 + m1.m11 * m2.m31 + m1.m12 * m2.m32 + m1.m13 * m2.m33;
         this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02 + m1.m23 * m2.m03;
         this.m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12 + m1.m23 * m2.m13;
         this.m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22 + m1.m23 * m2.m23;
         this.m23 = m1.m20 * m2.m30 + m1.m21 * m2.m31 + m1.m22 * m2.m32 + m1.m23 * m2.m33;
         this.m30 = m1.m30 * m2.m00 + m1.m31 * m2.m01 + m1.m32 * m2.m02 + m1.m33 * m2.m03;
         this.m31 = m1.m30 * m2.m10 + m1.m31 * m2.m11 + m1.m32 * m2.m12 + m1.m33 * m2.m13;
         this.m32 = m1.m30 * m2.m20 + m1.m31 * m2.m21 + m1.m32 * m2.m22 + m1.m33 * m2.m23;
         this.m33 = m1.m30 * m2.m30 + m1.m31 * m2.m31 + m1.m32 * m2.m32 + m1.m33 * m2.m33;
      } else {
         float m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02 + m1.m03 * m2.m03;
         float m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12 + m1.m03 * m2.m13;
         float m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22 + m1.m03 * m2.m23;
         float m03 = m1.m00 * m2.m30 + m1.m01 * m2.m31 + m1.m02 * m2.m32 + m1.m03 * m2.m33;
         float m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02 + m1.m13 * m2.m03;
         float m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12 + m1.m13 * m2.m13;
         float m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22 + m1.m13 * m2.m23;
         float m13 = m1.m10 * m2.m30 + m1.m11 * m2.m31 + m1.m12 * m2.m32 + m1.m13 * m2.m33;
         float m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02 + m1.m23 * m2.m03;
         float m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12 + m1.m23 * m2.m13;
         float m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22 + m1.m23 * m2.m23;
         float m23 = m1.m20 * m2.m30 + m1.m21 * m2.m31 + m1.m22 * m2.m32 + m1.m23 * m2.m33;
         float m30 = m1.m30 * m2.m00 + m1.m31 * m2.m01 + m1.m32 * m2.m02 + m1.m33 * m2.m03;
         float m31 = m1.m30 * m2.m10 + m1.m31 * m2.m11 + m1.m32 * m2.m12 + m1.m33 * m2.m13;
         float m32 = m1.m30 * m2.m20 + m1.m31 * m2.m21 + m1.m32 * m2.m22 + m1.m33 * m2.m23;
         float m33 = m1.m30 * m2.m30 + m1.m31 * m2.m31 + m1.m32 * m2.m32 + m1.m33 * m2.m33;
         this.m00 = m00;
         this.m01 = m01;
         this.m02 = m02;
         this.m03 = m03;
         this.m10 = m10;
         this.m11 = m11;
         this.m12 = m12;
         this.m13 = m13;
         this.m20 = m20;
         this.m21 = m21;
         this.m22 = m22;
         this.m23 = m23;
         this.m30 = m30;
         this.m31 = m31;
         this.m32 = m32;
         this.m33 = m33;
      }

   }

   public final void mulTransposeLeft(Matrix4f m1, Matrix4f m2) {
      if (this != m1 && this != m2) {
         this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20 + m1.m30 * m2.m30;
         this.m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21 + m1.m30 * m2.m31;
         this.m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22 + m1.m30 * m2.m32;
         this.m03 = m1.m00 * m2.m03 + m1.m10 * m2.m13 + m1.m20 * m2.m23 + m1.m30 * m2.m33;
         this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20 + m1.m31 * m2.m30;
         this.m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21 + m1.m31 * m2.m31;
         this.m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22 + m1.m31 * m2.m32;
         this.m13 = m1.m01 * m2.m03 + m1.m11 * m2.m13 + m1.m21 * m2.m23 + m1.m31 * m2.m33;
         this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20 + m1.m32 * m2.m30;
         this.m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21 + m1.m32 * m2.m31;
         this.m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22 + m1.m32 * m2.m32;
         this.m23 = m1.m02 * m2.m03 + m1.m12 * m2.m13 + m1.m22 * m2.m23 + m1.m32 * m2.m33;
         this.m30 = m1.m03 * m2.m00 + m1.m13 * m2.m10 + m1.m23 * m2.m20 + m1.m33 * m2.m30;
         this.m31 = m1.m03 * m2.m01 + m1.m13 * m2.m11 + m1.m23 * m2.m21 + m1.m33 * m2.m31;
         this.m32 = m1.m03 * m2.m02 + m1.m13 * m2.m12 + m1.m23 * m2.m22 + m1.m33 * m2.m32;
         this.m33 = m1.m03 * m2.m03 + m1.m13 * m2.m13 + m1.m23 * m2.m23 + m1.m33 * m2.m33;
      } else {
         float m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20 + m1.m30 * m2.m30;
         float m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21 + m1.m30 * m2.m31;
         float m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22 + m1.m30 * m2.m32;
         float m03 = m1.m00 * m2.m03 + m1.m10 * m2.m13 + m1.m20 * m2.m23 + m1.m30 * m2.m33;
         float m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20 + m1.m31 * m2.m30;
         float m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21 + m1.m31 * m2.m31;
         float m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22 + m1.m31 * m2.m32;
         float m13 = m1.m01 * m2.m03 + m1.m11 * m2.m13 + m1.m21 * m2.m23 + m1.m31 * m2.m33;
         float m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20 + m1.m32 * m2.m30;
         float m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21 + m1.m32 * m2.m31;
         float m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22 + m1.m32 * m2.m32;
         float m23 = m1.m02 * m2.m03 + m1.m12 * m2.m13 + m1.m22 * m2.m23 + m1.m32 * m2.m33;
         float m30 = m1.m03 * m2.m00 + m1.m13 * m2.m10 + m1.m23 * m2.m20 + m1.m33 * m2.m30;
         float m31 = m1.m03 * m2.m01 + m1.m13 * m2.m11 + m1.m23 * m2.m21 + m1.m33 * m2.m31;
         float m32 = m1.m03 * m2.m02 + m1.m13 * m2.m12 + m1.m23 * m2.m22 + m1.m33 * m2.m32;
         float m33 = m1.m03 * m2.m03 + m1.m13 * m2.m13 + m1.m23 * m2.m23 + m1.m33 * m2.m33;
         this.m00 = m00;
         this.m01 = m01;
         this.m02 = m02;
         this.m03 = m03;
         this.m10 = m10;
         this.m11 = m11;
         this.m12 = m12;
         this.m13 = m13;
         this.m20 = m20;
         this.m21 = m21;
         this.m22 = m22;
         this.m23 = m23;
         this.m30 = m30;
         this.m31 = m31;
         this.m32 = m32;
         this.m33 = m33;
      }

   }

   public boolean equals(Matrix4f m1) {
      try {
         return this.m00 == m1.m00 && this.m01 == m1.m01 && this.m02 == m1.m02 && this.m03 == m1.m03 && this.m10 == m1.m10 && this.m11 == m1.m11 && this.m12 == m1.m12 && this.m13 == m1.m13 && this.m20 == m1.m20 && this.m21 == m1.m21 && this.m22 == m1.m22 && this.m23 == m1.m23 && this.m30 == m1.m30 && this.m31 == m1.m31 && this.m32 == m1.m32 && this.m33 == m1.m33;
      } catch (NullPointerException var3) {
         return false;
      }
   }

   public boolean equals(Object t1) {
      try {
         Matrix4f m2 = (Matrix4f)t1;
         return this.m00 == m2.m00 && this.m01 == m2.m01 && this.m02 == m2.m02 && this.m03 == m2.m03 && this.m10 == m2.m10 && this.m11 == m2.m11 && this.m12 == m2.m12 && this.m13 == m2.m13 && this.m20 == m2.m20 && this.m21 == m2.m21 && this.m22 == m2.m22 && this.m23 == m2.m23 && this.m30 == m2.m30 && this.m31 == m2.m31 && this.m32 == m2.m32 && this.m33 == m2.m33;
      } catch (ClassCastException var3) {
         return false;
      } catch (NullPointerException var4) {
         return false;
      }
   }

   public boolean epsilonEquals(Matrix4f m1, float epsilon) {
      boolean status = true;
      if (Math.abs(this.m00 - m1.m00) > epsilon) {
         status = false;
      }

      if (Math.abs(this.m01 - m1.m01) > epsilon) {
         status = false;
      }

      if (Math.abs(this.m02 - m1.m02) > epsilon) {
         status = false;
      }

      if (Math.abs(this.m03 - m1.m03) > epsilon) {
         status = false;
      }

      if (Math.abs(this.m10 - m1.m10) > epsilon) {
         status = false;
      }

      if (Math.abs(this.m11 - m1.m11) > epsilon) {
         status = false;
      }

      if (Math.abs(this.m12 - m1.m12) > epsilon) {
         status = false;
      }

      if (Math.abs(this.m13 - m1.m13) > epsilon) {
         status = false;
      }

      if (Math.abs(this.m20 - m1.m20) > epsilon) {
         status = false;
      }

      if (Math.abs(this.m21 - m1.m21) > epsilon) {
         status = false;
      }

      if (Math.abs(this.m22 - m1.m22) > epsilon) {
         status = false;
      }

      if (Math.abs(this.m23 - m1.m23) > epsilon) {
         status = false;
      }

      if (Math.abs(this.m30 - m1.m30) > epsilon) {
         status = false;
      }

      if (Math.abs(this.m31 - m1.m31) > epsilon) {
         status = false;
      }

      if (Math.abs(this.m32 - m1.m32) > epsilon) {
         status = false;
      }

      if (Math.abs(this.m33 - m1.m33) > epsilon) {
         status = false;
      }

      return status;
   }

   public final void transform(Vector3f normal, Vector3f normalOut) {
      float x = this.m00 * normal.x + this.m01 * normal.y + this.m02 * normal.z;
      float y = this.m10 * normal.x + this.m11 * normal.y + this.m12 * normal.z;
      normalOut.z = this.m20 * normal.x + this.m21 * normal.y + this.m22 * normal.z;
      normalOut.x = x;
      normalOut.y = y;
   }

   public final void transform(Vector3f normal) {
      float x = this.m00 * normal.x + this.m01 * normal.y + this.m02 * normal.z;
      float y = this.m10 * normal.x + this.m11 * normal.y + this.m12 * normal.z;
      normal.z = this.m20 * normal.x + this.m21 * normal.y + this.m22 * normal.z;
      normal.x = x;
      normal.y = y;
   }

   public final void setRotation(Matrix3f m1) {
      double[] tmp_rot = new double[9];
      double[] tmp_scale = new double[3];
      this.getScaleRotate(tmp_scale, tmp_rot);
      this.m00 = (float)((double)m1.m00 * tmp_scale[0]);
      this.m01 = (float)((double)m1.m01 * tmp_scale[1]);
      this.m02 = (float)((double)m1.m02 * tmp_scale[2]);
      this.m10 = (float)((double)m1.m10 * tmp_scale[0]);
      this.m11 = (float)((double)m1.m11 * tmp_scale[1]);
      this.m12 = (float)((double)m1.m12 * tmp_scale[2]);
      this.m20 = (float)((double)m1.m20 * tmp_scale[0]);
      this.m21 = (float)((double)m1.m21 * tmp_scale[1]);
      this.m22 = (float)((double)m1.m22 * tmp_scale[2]);
   }

   public final void setRotation(Quaternion q1) {
      double[] tmp_rot = new double[9];
      double[] tmp_scale = new double[3];
      this.getScaleRotate(tmp_scale, tmp_rot);
      this.m00 = (float)((double)(1.0F - 2.0F * q1.y * q1.y - 2.0F * q1.z * q1.z) * tmp_scale[0]);
      this.m10 = (float)((double)(2.0F * (q1.x * q1.y + q1.w * q1.z)) * tmp_scale[0]);
      this.m20 = (float)((double)(2.0F * (q1.x * q1.z - q1.w * q1.y)) * tmp_scale[0]);
      this.m01 = (float)((double)(2.0F * (q1.x * q1.y - q1.w * q1.z)) * tmp_scale[1]);
      this.m11 = (float)((double)(1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.z * q1.z) * tmp_scale[1]);
      this.m21 = (float)((double)(2.0F * (q1.y * q1.z + q1.w * q1.x)) * tmp_scale[1]);
      this.m02 = (float)((double)(2.0F * (q1.x * q1.z + q1.w * q1.y)) * tmp_scale[2]);
      this.m12 = (float)((double)(2.0F * (q1.y * q1.z - q1.w * q1.x)) * tmp_scale[2]);
      this.m22 = (float)((double)(1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.y * q1.y) * tmp_scale[2]);
   }

   public final void setRotation(AxisAngle4f a1) {
      double[] tmp_rot = new double[9];
      double[] tmp_scale = new double[3];
      this.getScaleRotate(tmp_scale, tmp_rot);
      double mag = Math.sqrt((double)(a1.x * a1.x + a1.y * a1.y + a1.z * a1.z));
      if (mag < 1.0E-8D) {
         this.m00 = 1.0F;
         this.m01 = 0.0F;
         this.m02 = 0.0F;
         this.m10 = 0.0F;
         this.m11 = 1.0F;
         this.m12 = 0.0F;
         this.m20 = 0.0F;
         this.m21 = 0.0F;
         this.m22 = 1.0F;
      } else {
         mag = 1.0D / mag;
         double ax = (double)a1.x * mag;
         double ay = (double)a1.y * mag;
         double az = (double)a1.z * mag;
         double sinTheta = Math.sin((double)a1.angle);
         double cosTheta = Math.cos((double)a1.angle);
         double t = 1.0D - cosTheta;
         double xz = (double)(a1.x * a1.z);
         double xy = (double)(a1.x * a1.y);
         double yz = (double)(a1.y * a1.z);
         this.m00 = (float)((t * ax * ax + cosTheta) * tmp_scale[0]);
         this.m01 = (float)((t * xy - sinTheta * az) * tmp_scale[1]);
         this.m02 = (float)((t * xz + sinTheta * ay) * tmp_scale[2]);
         this.m10 = (float)((t * xy + sinTheta * az) * tmp_scale[0]);
         this.m11 = (float)((t * ay * ay + cosTheta) * tmp_scale[1]);
         this.m12 = (float)((t * yz - sinTheta * ax) * tmp_scale[2]);
         this.m20 = (float)((t * xz - sinTheta * ay) * tmp_scale[0]);
         this.m21 = (float)((t * yz + sinTheta * ax) * tmp_scale[1]);
         this.m22 = (float)((t * az * az + cosTheta) * tmp_scale[2]);
      }

   }

   public final void setZero() {
      this.m00 = 0.0F;
      this.m01 = 0.0F;
      this.m02 = 0.0F;
      this.m03 = 0.0F;
      this.m10 = 0.0F;
      this.m11 = 0.0F;
      this.m12 = 0.0F;
      this.m13 = 0.0F;
      this.m20 = 0.0F;
      this.m21 = 0.0F;
      this.m22 = 0.0F;
      this.m23 = 0.0F;
      this.m30 = 0.0F;
      this.m31 = 0.0F;
      this.m32 = 0.0F;
      this.m33 = 0.0F;
   }

   public final void negate() {
      this.m00 = -this.m00;
      this.m01 = -this.m01;
      this.m02 = -this.m02;
      this.m03 = -this.m03;
      this.m10 = -this.m10;
      this.m11 = -this.m11;
      this.m12 = -this.m12;
      this.m13 = -this.m13;
      this.m20 = -this.m20;
      this.m21 = -this.m21;
      this.m22 = -this.m22;
      this.m23 = -this.m23;
      this.m30 = -this.m30;
      this.m31 = -this.m31;
      this.m32 = -this.m32;
      this.m33 = -this.m33;
   }

   public final void negate(Matrix4f m1) {
      this.m00 = -m1.m00;
      this.m01 = -m1.m01;
      this.m02 = -m1.m02;
      this.m03 = -m1.m03;
      this.m10 = -m1.m10;
      this.m11 = -m1.m11;
      this.m12 = -m1.m12;
      this.m13 = -m1.m13;
      this.m20 = -m1.m20;
      this.m21 = -m1.m21;
      this.m22 = -m1.m22;
      this.m23 = -m1.m23;
      this.m30 = -m1.m30;
      this.m31 = -m1.m31;
      this.m32 = -m1.m32;
      this.m33 = -m1.m33;
   }

   private final void getScaleRotate(double[] scales, double[] rots) {
      double[] tmp = new double[]{(double)this.m00, (double)this.m01, (double)this.m02, (double)this.m10, (double)this.m11, (double)this.m12, (double)this.m20, (double)this.m21, (double)this.m22};
      Matrix3d.compute_svd(tmp, scales, rots);
   }

   public Object clone() {
      Matrix4f m1 = null;

      try {
         m1 = (Matrix4f)super.clone();
         return m1;
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }
   }

   public final float getM00() {
      return this.m00;
   }

   public final void setM00(float m00) {
      this.m00 = m00;
   }

   public final float getM01() {
      return this.m01;
   }

   public final void setM01(float m01) {
      this.m01 = m01;
   }

   public final float getM02() {
      return this.m02;
   }

   public final void setM02(float m02) {
      this.m02 = m02;
   }

   public final float getM10() {
      return this.m10;
   }

   public final void setM10(float m10) {
      this.m10 = m10;
   }

   public final float getM11() {
      return this.m11;
   }

   public final void setM11(float m11) {
      this.m11 = m11;
   }

   public final float getM12() {
      return this.m12;
   }

   public final void setM12(float m12) {
      this.m12 = m12;
   }

   public final float getM20() {
      return this.m20;
   }

   public final void setM20(float m20) {
      this.m20 = m20;
   }

   public final float getM21() {
      return this.m21;
   }

   public final void setM21(float m21) {
      this.m21 = m21;
   }

   public final float getM22() {
      return this.m22;
   }

   public final void setM22(float m22) {
      this.m22 = m22;
   }

   public final float getM03() {
      return this.m03;
   }

   public final void setM03(float m03) {
      this.m03 = m03;
   }

   public final float getM13() {
      return this.m13;
   }

   public final void setM13(float m13) {
      this.m13 = m13;
   }

   public final float getM23() {
      return this.m23;
   }

   public final void setM23(float m23) {
      this.m23 = m23;
   }

   public final float getM30() {
      return this.m30;
   }

   public final void setM30(float m30) {
      this.m30 = m30;
   }

   public final float getM31() {
      return this.m31;
   }

   public final void setM31(float m31) {
      this.m31 = m31;
   }

   public final float getM32() {
      return this.m32;
   }

   public final void setM32(float m32) {
      this.m32 = m32;
   }

   public final float getM33() {
      return this.m33;
   }

   public final void setM33(float m33) {
      this.m33 = m33;
   }

   public final float[] intoArray() {
      float[] m = new float[]{this.m00, this.m01, this.m02, this.m03, this.m10, this.m11, this.m12, this.m13, this.m20, this.m21, this.m22, this.m23, this.m30, this.m31, this.m32, this.m33};
      return m;
   }

   public final boolean isEmptyRotationMatrix() {
      if (this.m00 == 1.0F && this.m11 == 1.0F && this.m22 == 1.0F) {
         float[] m = this.intoArray();
         boolean isEmptyRotationMatrix = true;

         for(int i = 0; i < m.length; ++i) {
            if (i != 0 && i != 5 && i != 10 && i <= 10 && m[i] != 0.0F) {
               isEmptyRotationMatrix = false;
               break;
            }
         }

         return isEmptyRotationMatrix;
      } else {
         return false;
      }
   }
}
