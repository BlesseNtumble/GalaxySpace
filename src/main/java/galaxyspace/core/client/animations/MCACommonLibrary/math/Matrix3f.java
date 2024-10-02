package galaxyspace.core.client.animations.MCACommonLibrary.math;

import java.io.Serializable;
import java.nio.FloatBuffer;
import java.util.logging.Logger;

public final class Matrix3f implements Cloneable, Serializable {
   static final long serialVersionUID = 1L;
   private static final Logger logger = Logger.getLogger(Matrix3f.class.getName());
   protected float m00;
   protected float m01;
   protected float m02;
   protected float m10;
   protected float m11;
   protected float m12;
   protected float m20;
   protected float m21;
   protected float m22;
   public static final Matrix3f ZERO = new Matrix3f(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
   public static final Matrix3f IDENTITY = new Matrix3f();

   public Matrix3f() {
      this.loadIdentity();
   }

   public Matrix3f(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
      this.m00 = m00;
      this.m01 = m01;
      this.m02 = m02;
      this.m10 = m10;
      this.m11 = m11;
      this.m12 = m12;
      this.m20 = m20;
      this.m21 = m21;
      this.m22 = m22;
   }

   public Matrix3f(Matrix3f mat) {
      this.set(mat);
   }

   public void absoluteLocal() {
      this.m00 = FastMath.abs(this.m00);
      this.m01 = FastMath.abs(this.m01);
      this.m02 = FastMath.abs(this.m02);
      this.m10 = FastMath.abs(this.m10);
      this.m11 = FastMath.abs(this.m11);
      this.m12 = FastMath.abs(this.m12);
      this.m20 = FastMath.abs(this.m20);
      this.m21 = FastMath.abs(this.m21);
      this.m22 = FastMath.abs(this.m22);
   }

   public Matrix3f set(Matrix3f matrix) {
      if (null == matrix) {
         this.loadIdentity();
      } else {
         this.m00 = matrix.m00;
         this.m01 = matrix.m01;
         this.m02 = matrix.m02;
         this.m10 = matrix.m10;
         this.m11 = matrix.m11;
         this.m12 = matrix.m12;
         this.m20 = matrix.m20;
         this.m21 = matrix.m21;
         this.m22 = matrix.m22;
      }

      return this;
   }

   public float get(int i, int j) {
      switch(i) {
      case 0:
         switch(j) {
         case 0:
            return this.m00;
         case 1:
            return this.m01;
         case 2:
            return this.m02;
         }
      case 1:
         switch(j) {
         case 0:
            return this.m10;
         case 1:
            return this.m11;
         case 2:
            return this.m12;
         }
      case 2:
         switch(j) {
         case 0:
            return this.m20;
         case 1:
            return this.m21;
         case 2:
            return this.m22;
         }
      default:
         logger.warning("Invalid matrix index.");
         throw new IllegalArgumentException("Invalid indices into matrix.");
      }
   }

   public void get(float[] data, boolean rowMajor) {
      if (data.length == 9) {
         if (rowMajor) {
            data[0] = this.m00;
            data[1] = this.m01;
            data[2] = this.m02;
            data[3] = this.m10;
            data[4] = this.m11;
            data[5] = this.m12;
            data[6] = this.m20;
            data[7] = this.m21;
            data[8] = this.m22;
         } else {
            data[0] = this.m00;
            data[1] = this.m10;
            data[2] = this.m20;
            data[3] = this.m01;
            data[4] = this.m11;
            data[5] = this.m21;
            data[6] = this.m02;
            data[7] = this.m12;
            data[8] = this.m22;
         }
      } else {
         if (data.length != 16) {
            throw new IndexOutOfBoundsException("Array size must be 9 or 16 in Matrix3f.get().");
         }

         if (rowMajor) {
            data[0] = this.m00;
            data[1] = this.m01;
            data[2] = this.m02;
            data[4] = this.m10;
            data[5] = this.m11;
            data[6] = this.m12;
            data[8] = this.m20;
            data[9] = this.m21;
            data[10] = this.m22;
         } else {
            data[0] = this.m00;
            data[1] = this.m10;
            data[2] = this.m20;
            data[4] = this.m01;
            data[5] = this.m11;
            data[6] = this.m21;
            data[8] = this.m02;
            data[9] = this.m12;
            data[10] = this.m22;
         }
      }

   }

   public Vector3f getColumn(int i) {
      return this.getColumn(i, (Vector3f)null);
   }

   public Vector3f getColumn(int i, Vector3f store) {
      if (store == null) {
         store = new Vector3f();
      }

      switch(i) {
      case 0:
         store.x = this.m00;
         store.y = this.m10;
         store.z = this.m20;
         break;
      case 1:
         store.x = this.m01;
         store.y = this.m11;
         store.z = this.m21;
         break;
      case 2:
         store.x = this.m02;
         store.y = this.m12;
         store.z = this.m22;
         break;
      default:
         logger.warning("Invalid column index.");
         throw new IllegalArgumentException("Invalid column index. " + i);
      }

      return store;
   }

   public Vector3f getRow(int i) {
      return this.getRow(i, (Vector3f)null);
   }

   public Vector3f getRow(int i, Vector3f store) {
      if (store == null) {
         store = new Vector3f();
      }

      switch(i) {
      case 0:
         store.x = this.m00;
         store.y = this.m01;
         store.z = this.m02;
         break;
      case 1:
         store.x = this.m10;
         store.y = this.m11;
         store.z = this.m12;
         break;
      case 2:
         store.x = this.m20;
         store.y = this.m21;
         store.z = this.m22;
         break;
      default:
         logger.warning("Invalid row index.");
         throw new IllegalArgumentException("Invalid row index. " + i);
      }

      return store;
   }

   public FloatBuffer fillFloatBuffer(FloatBuffer fb, boolean columnMajor) {
      if (columnMajor) {
         fb.put(this.m00).put(this.m10).put(this.m20);
         fb.put(this.m01).put(this.m11).put(this.m21);
         fb.put(this.m02).put(this.m12).put(this.m22);
      } else {
         fb.put(this.m00).put(this.m01).put(this.m02);
         fb.put(this.m10).put(this.m11).put(this.m12);
         fb.put(this.m20).put(this.m21).put(this.m22);
      }

      return fb;
   }

   public void fillFloatArray(float[] f, boolean columnMajor) {
      if (columnMajor) {
         f[0] = this.m00;
         f[1] = this.m10;
         f[2] = this.m20;
         f[3] = this.m01;
         f[4] = this.m11;
         f[5] = this.m21;
         f[6] = this.m02;
         f[7] = this.m12;
         f[8] = this.m22;
      } else {
         f[0] = this.m00;
         f[1] = this.m01;
         f[2] = this.m02;
         f[3] = this.m10;
         f[4] = this.m11;
         f[5] = this.m12;
         f[6] = this.m20;
         f[7] = this.m21;
         f[8] = this.m22;
      }

   }

   public Matrix3f setColumn(int i, Vector3f column) {
      if (column == null) {
         logger.warning("Column is null. Ignoring.");
         return this;
      } else {
         switch(i) {
         case 0:
            this.m00 = column.x;
            this.m10 = column.y;
            this.m20 = column.z;
            break;
         case 1:
            this.m01 = column.x;
            this.m11 = column.y;
            this.m21 = column.z;
            break;
         case 2:
            this.m02 = column.x;
            this.m12 = column.y;
            this.m22 = column.z;
            break;
         default:
            logger.warning("Invalid column index.");
            throw new IllegalArgumentException("Invalid column index. " + i);
         }

         return this;
      }
   }

   public Matrix3f setRow(int i, Vector3f row) {
      if (row == null) {
         logger.warning("Row is null. Ignoring.");
         return this;
      } else {
         switch(i) {
         case 0:
            this.m00 = row.x;
            this.m01 = row.y;
            this.m02 = row.z;
            break;
         case 1:
            this.m10 = row.x;
            this.m11 = row.y;
            this.m12 = row.z;
            break;
         case 2:
            this.m20 = row.x;
            this.m21 = row.y;
            this.m22 = row.z;
            break;
         default:
            logger.warning("Invalid row index.");
            throw new IllegalArgumentException("Invalid row index. " + i);
         }

         return this;
      }
   }

   public Matrix3f set(int i, int j, float value) {
      switch(i) {
      case 0:
         switch(j) {
         case 0:
            this.m00 = value;
            return this;
         case 1:
            this.m01 = value;
            return this;
         case 2:
            this.m02 = value;
            return this;
         }
      case 1:
         switch(j) {
         case 0:
            this.m10 = value;
            return this;
         case 1:
            this.m11 = value;
            return this;
         case 2:
            this.m12 = value;
            return this;
         }
      case 2:
         switch(j) {
         case 0:
            this.m20 = value;
            return this;
         case 1:
            this.m21 = value;
            return this;
         case 2:
            this.m22 = value;
            return this;
         }
      default:
         logger.warning("Invalid matrix index.");
         throw new IllegalArgumentException("Invalid indices into matrix.");
      }
   }

   public Matrix3f set(float[][] matrix) {
      if (matrix.length == 3 && matrix[0].length == 3) {
         this.m00 = matrix[0][0];
         this.m01 = matrix[0][1];
         this.m02 = matrix[0][2];
         this.m10 = matrix[1][0];
         this.m11 = matrix[1][1];
         this.m12 = matrix[1][2];
         this.m20 = matrix[2][0];
         this.m21 = matrix[2][1];
         this.m22 = matrix[2][2];
         return this;
      } else {
         throw new IllegalArgumentException("Array must be of size 9.");
      }
   }

   public void fromAxes(Vector3f uAxis, Vector3f vAxis, Vector3f wAxis) {
      this.m00 = uAxis.x;
      this.m10 = uAxis.y;
      this.m20 = uAxis.z;
      this.m01 = vAxis.x;
      this.m11 = vAxis.y;
      this.m21 = vAxis.z;
      this.m02 = wAxis.x;
      this.m12 = wAxis.y;
      this.m22 = wAxis.z;
   }

   public Matrix3f set(float[] matrix) {
      return this.set(matrix, true);
   }

   public Matrix3f set(float[] matrix, boolean rowMajor) {
      if (matrix.length != 9) {
         throw new IllegalArgumentException("Array must be of size 9.");
      } else {
         if (rowMajor) {
            this.m00 = matrix[0];
            this.m01 = matrix[1];
            this.m02 = matrix[2];
            this.m10 = matrix[3];
            this.m11 = matrix[4];
            this.m12 = matrix[5];
            this.m20 = matrix[6];
            this.m21 = matrix[7];
            this.m22 = matrix[8];
         } else {
            this.m00 = matrix[0];
            this.m01 = matrix[3];
            this.m02 = matrix[6];
            this.m10 = matrix[1];
            this.m11 = matrix[4];
            this.m12 = matrix[7];
            this.m20 = matrix[2];
            this.m21 = matrix[5];
            this.m22 = matrix[8];
         }

         return this;
      }
   }

   public void loadIdentity() {
      this.m01 = this.m02 = this.m10 = this.m12 = this.m20 = this.m21 = 0.0F;
      this.m00 = this.m11 = this.m22 = 1.0F;
   }

   public boolean isIdentity() {
      return this.m00 == 1.0F && this.m01 == 0.0F && this.m02 == 0.0F && this.m10 == 0.0F && this.m11 == 1.0F && this.m12 == 0.0F && this.m20 == 0.0F && this.m21 == 0.0F && this.m22 == 1.0F;
   }

   public void fromAngleAxis(float angle, Vector3f axis) {
      Vector3f normAxis = axis.normalize();
      this.fromAngleNormalAxis(angle, normAxis);
   }

   public void fromAngleNormalAxis(float angle, Vector3f axis) {
      float fCos = FastMath.cos(angle);
      float fSin = FastMath.sin(angle);
      float fOneMinusCos = 1.0F - fCos;
      float fX2 = axis.x * axis.x;
      float fY2 = axis.y * axis.y;
      float fZ2 = axis.z * axis.z;
      float fXYM = axis.x * axis.y * fOneMinusCos;
      float fXZM = axis.x * axis.z * fOneMinusCos;
      float fYZM = axis.y * axis.z * fOneMinusCos;
      float fXSin = axis.x * fSin;
      float fYSin = axis.y * fSin;
      float fZSin = axis.z * fSin;
      this.m00 = fX2 * fOneMinusCos + fCos;
      this.m01 = fXYM - fZSin;
      this.m02 = fXZM + fYSin;
      this.m10 = fXYM + fZSin;
      this.m11 = fY2 * fOneMinusCos + fCos;
      this.m12 = fYZM - fXSin;
      this.m20 = fXZM - fYSin;
      this.m21 = fYZM + fXSin;
      this.m22 = fZ2 * fOneMinusCos + fCos;
   }

   public Matrix3f mult(Matrix3f mat) {
      return this.mult((Matrix3f)mat, (Matrix3f)null);
   }

   public Matrix3f mult(Matrix3f mat, Matrix3f product) {
      if (product == null) {
         product = new Matrix3f();
      }

      float temp00 = this.m00 * mat.m00 + this.m01 * mat.m10 + this.m02 * mat.m20;
      float temp01 = this.m00 * mat.m01 + this.m01 * mat.m11 + this.m02 * mat.m21;
      float temp02 = this.m00 * mat.m02 + this.m01 * mat.m12 + this.m02 * mat.m22;
      float temp10 = this.m10 * mat.m00 + this.m11 * mat.m10 + this.m12 * mat.m20;
      float temp11 = this.m10 * mat.m01 + this.m11 * mat.m11 + this.m12 * mat.m21;
      float temp12 = this.m10 * mat.m02 + this.m11 * mat.m12 + this.m12 * mat.m22;
      float temp20 = this.m20 * mat.m00 + this.m21 * mat.m10 + this.m22 * mat.m20;
      float temp21 = this.m20 * mat.m01 + this.m21 * mat.m11 + this.m22 * mat.m21;
      float temp22 = this.m20 * mat.m02 + this.m21 * mat.m12 + this.m22 * mat.m22;
      product.m00 = temp00;
      product.m01 = temp01;
      product.m02 = temp02;
      product.m10 = temp10;
      product.m11 = temp11;
      product.m12 = temp12;
      product.m20 = temp20;
      product.m21 = temp21;
      product.m22 = temp22;
      return product;
   }

   public Vector3f mult(Vector3f vec) {
      return this.mult((Vector3f)vec, (Vector3f)null);
   }

   public Vector3f mult(Vector3f vec, Vector3f product) {
      if (null == product) {
         product = new Vector3f();
      }

      float x = vec.x;
      float y = vec.y;
      float z = vec.z;
      product.x = this.m00 * x + this.m01 * y + this.m02 * z;
      product.y = this.m10 * x + this.m11 * y + this.m12 * z;
      product.z = this.m20 * x + this.m21 * y + this.m22 * z;
      return product;
   }

   public Matrix3f multLocal(float scale) {
      this.m00 *= scale;
      this.m01 *= scale;
      this.m02 *= scale;
      this.m10 *= scale;
      this.m11 *= scale;
      this.m12 *= scale;
      this.m20 *= scale;
      this.m21 *= scale;
      this.m22 *= scale;
      return this;
   }

   public Vector3f multLocal(Vector3f vec) {
      if (vec == null) {
         return null;
      } else {
         float x = vec.x;
         float y = vec.y;
         vec.x = this.m00 * x + this.m01 * y + this.m02 * vec.z;
         vec.y = this.m10 * x + this.m11 * y + this.m12 * vec.z;
         vec.z = this.m20 * x + this.m21 * y + this.m22 * vec.z;
         return vec;
      }
   }

   public Matrix3f multLocal(Matrix3f mat) {
      return this.mult(mat, this);
   }

   public Matrix3f transposeLocal() {
      float tmp = this.m01;
      this.m01 = this.m10;
      this.m10 = tmp;
      tmp = this.m02;
      this.m02 = this.m20;
      this.m20 = tmp;
      tmp = this.m12;
      this.m12 = this.m21;
      this.m21 = tmp;
      return this;
   }

   public Matrix3f invert() {
      return this.invert((Matrix3f)null);
   }

   public Matrix3f invert(Matrix3f store) {
      if (store == null) {
         store = new Matrix3f();
      }

      float det = this.determinant();
      if (FastMath.abs(det) <= 1.1920929E-7F) {
         return store.zero();
      } else {
         store.m00 = this.m11 * this.m22 - this.m12 * this.m21;
         store.m01 = this.m02 * this.m21 - this.m01 * this.m22;
         store.m02 = this.m01 * this.m12 - this.m02 * this.m11;
         store.m10 = this.m12 * this.m20 - this.m10 * this.m22;
         store.m11 = this.m00 * this.m22 - this.m02 * this.m20;
         store.m12 = this.m02 * this.m10 - this.m00 * this.m12;
         store.m20 = this.m10 * this.m21 - this.m11 * this.m20;
         store.m21 = this.m01 * this.m20 - this.m00 * this.m21;
         store.m22 = this.m00 * this.m11 - this.m01 * this.m10;
         store.multLocal(1.0F / det);
         return store;
      }
   }

   public Matrix3f invertLocal() {
      float det = this.determinant();
      if (FastMath.abs(det) <= 1.1920929E-7F) {
         return this.zero();
      } else {
         float f00 = this.m11 * this.m22 - this.m12 * this.m21;
         float f01 = this.m02 * this.m21 - this.m01 * this.m22;
         float f02 = this.m01 * this.m12 - this.m02 * this.m11;
         float f10 = this.m12 * this.m20 - this.m10 * this.m22;
         float f11 = this.m00 * this.m22 - this.m02 * this.m20;
         float f12 = this.m02 * this.m10 - this.m00 * this.m12;
         float f20 = this.m10 * this.m21 - this.m11 * this.m20;
         float f21 = this.m01 * this.m20 - this.m00 * this.m21;
         float f22 = this.m00 * this.m11 - this.m01 * this.m10;
         this.m00 = f00;
         this.m01 = f01;
         this.m02 = f02;
         this.m10 = f10;
         this.m11 = f11;
         this.m12 = f12;
         this.m20 = f20;
         this.m21 = f21;
         this.m22 = f22;
         this.multLocal(1.0F / det);
         return this;
      }
   }

   public Matrix3f adjoint() {
      return this.adjoint((Matrix3f)null);
   }

   public Matrix3f adjoint(Matrix3f store) {
      if (store == null) {
         store = new Matrix3f();
      }

      store.m00 = this.m11 * this.m22 - this.m12 * this.m21;
      store.m01 = this.m02 * this.m21 - this.m01 * this.m22;
      store.m02 = this.m01 * this.m12 - this.m02 * this.m11;
      store.m10 = this.m12 * this.m20 - this.m10 * this.m22;
      store.m11 = this.m00 * this.m22 - this.m02 * this.m20;
      store.m12 = this.m02 * this.m10 - this.m00 * this.m12;
      store.m20 = this.m10 * this.m21 - this.m11 * this.m20;
      store.m21 = this.m01 * this.m20 - this.m00 * this.m21;
      store.m22 = this.m00 * this.m11 - this.m01 * this.m10;
      return store;
   }

   public float determinant() {
      float fCo00 = this.m11 * this.m22 - this.m12 * this.m21;
      float fCo10 = this.m12 * this.m20 - this.m10 * this.m22;
      float fCo20 = this.m10 * this.m21 - this.m11 * this.m20;
      float fDet = this.m00 * fCo00 + this.m01 * fCo10 + this.m02 * fCo20;
      return fDet;
   }

   public Matrix3f zero() {
      this.m00 = this.m01 = this.m02 = this.m10 = this.m11 = this.m12 = this.m20 = this.m21 = this.m22 = 0.0F;
      return this;
   }

   public Matrix3f transpose() {
      return this.transposeLocal();
   }

   public Matrix3f transposeNew() {
      Matrix3f ret = new Matrix3f(this.m00, this.m10, this.m20, this.m01, this.m11, this.m21, this.m02, this.m12, this.m22);
      return ret;
   }

   public String toString() {
      StringBuilder result = new StringBuilder("Matrix3f\n[\n");
      result.append(" ");
      result.append(this.m00);
      result.append("  ");
      result.append(this.m01);
      result.append("  ");
      result.append(this.m02);
      result.append(" \n");
      result.append(" ");
      result.append(this.m10);
      result.append("  ");
      result.append(this.m11);
      result.append("  ");
      result.append(this.m12);
      result.append(" \n");
      result.append(" ");
      result.append(this.m20);
      result.append("  ");
      result.append(this.m21);
      result.append("  ");
      result.append(this.m22);
      result.append(" \n]");
      return result.toString();
   }

   public int hashCode() {
      int hash = 37;
      hash = 37 * hash + Float.floatToIntBits(this.m00);
      hash = 37 * hash + Float.floatToIntBits(this.m01);
      hash = 37 * hash + Float.floatToIntBits(this.m02);
      hash = 37 * hash + Float.floatToIntBits(this.m10);
      hash = 37 * hash + Float.floatToIntBits(this.m11);
      hash = 37 * hash + Float.floatToIntBits(this.m12);
      hash = 37 * hash + Float.floatToIntBits(this.m20);
      hash = 37 * hash + Float.floatToIntBits(this.m21);
      hash = 37 * hash + Float.floatToIntBits(this.m22);
      return hash;
   }

   public boolean equals(Object o) {
      if (o instanceof Matrix3f && o != null) {
         if (this == o) {
            return true;
         } else {
            Matrix3f comp = (Matrix3f)o;
            if (Float.compare(this.m00, comp.m00) != 0) {
               return false;
            } else if (Float.compare(this.m01, comp.m01) != 0) {
               return false;
            } else if (Float.compare(this.m02, comp.m02) != 0) {
               return false;
            } else if (Float.compare(this.m10, comp.m10) != 0) {
               return false;
            } else if (Float.compare(this.m11, comp.m11) != 0) {
               return false;
            } else if (Float.compare(this.m12, comp.m12) != 0) {
               return false;
            } else if (Float.compare(this.m20, comp.m20) != 0) {
               return false;
            } else if (Float.compare(this.m21, comp.m21) != 0) {
               return false;
            } else {
               return Float.compare(this.m22, comp.m22) == 0;
            }
         }
      } else {
         return false;
      }
   }

   public void fromStartEndVectors(Vector3f start, Vector3f end) {
      Vector3f v = new Vector3f();
      start.cross(end, v);
      float e = start.dot(end);
      float f = e < 0.0F ? -e : e;
      float c1;
      float c2;
      float c3;
      if (f > 0.9999F) {
         Vector3f u = new Vector3f();
         Vector3f x = new Vector3f();
         x.x = (double)start.x > 0.0D ? start.x : -start.x;
         x.y = (double)start.y > 0.0D ? start.y : -start.y;
         x.z = (double)start.z > 0.0D ? start.z : -start.z;
         if (x.x < x.y) {
            if (x.x < x.z) {
               x.x = 1.0F;
               x.y = x.z = 0.0F;
            } else {
               x.z = 1.0F;
               x.x = x.y = 0.0F;
            }
         } else if (x.y < x.z) {
            x.y = 1.0F;
            x.x = x.z = 0.0F;
         } else {
            x.z = 1.0F;
            x.x = x.y = 0.0F;
         }

         u.x = x.x - start.x;
         u.y = x.y - start.y;
         u.z = x.z - start.z;
         v.x = x.x - end.x;
         v.y = x.y - end.y;
         v.z = x.z - end.z;
         c1 = 2.0F / u.dot(u);
         c2 = 2.0F / v.dot(v);
         c3 = c1 * c2 * u.dot(v);

         for(int i = 0; i < 3; ++i) {
            float val;
            for(int j = 0; j < 3; ++j) {
               val = -c1 * u.get(i) * u.get(j) - c2 * v.get(i) * v.get(j) + c3 * v.get(i) * u.get(j);
               this.set(i, j, val);
            }

            val = this.get(i, i);
            this.set(i, i, val + 1.0F);
         }
      } else {
         float h = 1.0F / (1.0F + e);
         float hvx = h * v.x;
         float hvz = h * v.z;
         c1 = hvx * v.y;
         c2 = hvx * v.z;
         c3 = hvz * v.y;
         this.set(0, 0, e + hvx * v.x);
         this.set(0, 1, c1 - v.z);
         this.set(0, 2, c2 + v.y);
         this.set(1, 0, c1 + v.z);
         this.set(1, 1, e + h * v.y * v.y);
         this.set(1, 2, c3 - v.x);
         this.set(2, 0, c2 - v.y);
         this.set(2, 1, c3 + v.x);
         this.set(2, 2, e + hvz * v.z);
      }

   }

   public void scale(Vector3f scale) {
      this.m00 *= scale.x;
      this.m10 *= scale.x;
      this.m20 *= scale.x;
      this.m01 *= scale.y;
      this.m11 *= scale.y;
      this.m21 *= scale.y;
      this.m02 *= scale.z;
      this.m12 *= scale.z;
      this.m22 *= scale.z;
   }

   static boolean equalIdentity(Matrix3f mat) {
      if ((double)Math.abs(mat.m00 - 1.0F) > 1.0E-4D) {
         return false;
      } else if ((double)Math.abs(mat.m11 - 1.0F) > 1.0E-4D) {
         return false;
      } else if ((double)Math.abs(mat.m22 - 1.0F) > 1.0E-4D) {
         return false;
      } else if ((double)Math.abs(mat.m01) > 1.0E-4D) {
         return false;
      } else if ((double)Math.abs(mat.m02) > 1.0E-4D) {
         return false;
      } else if ((double)Math.abs(mat.m10) > 1.0E-4D) {
         return false;
      } else if ((double)Math.abs(mat.m12) > 1.0E-4D) {
         return false;
      } else if ((double)Math.abs(mat.m20) > 1.0E-4D) {
         return false;
      } else {
         return !((double)Math.abs(mat.m21) > 1.0E-4D);
      }
   }

   public Matrix3f clone() {
      try {
         return (Matrix3f)super.clone();
      } catch (CloneNotSupportedException var2) {
         throw new AssertionError();
      }
   }
}
