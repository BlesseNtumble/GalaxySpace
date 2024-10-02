package galaxyspace.core.client.animations.MCACommonLibrary.math;

import java.io.Serializable;
import java.util.logging.Logger;

public final class Vector3f implements Cloneable, Serializable {
   static final long serialVersionUID = 1L;
   private static final Logger logger = Logger.getLogger(Vector3f.class.getName());
   public static final Vector3f ZERO = new Vector3f(0.0F, 0.0F, 0.0F);
   public static final Vector3f NAN = new Vector3f(Float.NaN, Float.NaN, Float.NaN);
   public static final Vector3f UNIT_X = new Vector3f(1.0F, 0.0F, 0.0F);
   public static final Vector3f UNIT_Y = new Vector3f(0.0F, 1.0F, 0.0F);
   public static final Vector3f UNIT_Z = new Vector3f(0.0F, 0.0F, 1.0F);
   public static final Vector3f UNIT_XYZ = new Vector3f(1.0F, 1.0F, 1.0F);
   public static final Vector3f POSITIVE_INFINITY = new Vector3f(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
   public static final Vector3f NEGATIVE_INFINITY = new Vector3f(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
   public float x;
   public float y;
   public float z;

   public Vector3f() {
      this.x = this.y = this.z = 0.0F;
   }

   public Vector3f(float x, float y, float z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Vector3f(Vector3f copy) {
      this.set(copy);
   }

   public Vector3f set(float x, float y, float z) {
      this.x = x;
      this.y = y;
      this.z = z;
      return this;
   }

   public Vector3f set(Vector3f vect) {
      this.x = vect.x;
      this.y = vect.y;
      this.z = vect.z;
      return this;
   }

   public Vector3f add(Vector3f vec) {
      if (null == vec) {
         logger.warning("Provided vector is null, null returned.");
         return null;
      } else {
         return new Vector3f(this.x + vec.x, this.y + vec.y, this.z + vec.z);
      }
   }

   public Vector3f add(Vector3f vec, Vector3f result) {
      result.x = this.x + vec.x;
      result.y = this.y + vec.y;
      result.z = this.z + vec.z;
      return result;
   }

   public Vector3f addLocal(Vector3f vec) {
      if (null == vec) {
         logger.warning("Provided vector is null, null returned.");
         return null;
      } else {
         this.x += vec.x;
         this.y += vec.y;
         this.z += vec.z;
         return this;
      }
   }

   public Vector3f add(float addX, float addY, float addZ) {
      return new Vector3f(this.x + addX, this.y + addY, this.z + addZ);
   }

   public Vector3f addLocal(float addX, float addY, float addZ) {
      this.x += addX;
      this.y += addY;
      this.z += addZ;
      return this;
   }

   public Vector3f scaleAdd(float scalar, Vector3f add) {
      this.x = this.x * scalar + add.x;
      this.y = this.y * scalar + add.y;
      this.z = this.z * scalar + add.z;
      return this;
   }

   public Vector3f scaleAdd(float scalar, Vector3f mult, Vector3f add) {
      this.x = mult.x * scalar + add.x;
      this.y = mult.y * scalar + add.y;
      this.z = mult.z * scalar + add.z;
      return this;
   }

   public float dot(Vector3f vec) {
      if (null == vec) {
         logger.warning("Provided vector is null, 0 returned.");
         return 0.0F;
      } else {
         return this.x * vec.x + this.y * vec.y + this.z * vec.z;
      }
   }

   public Vector3f cross(Vector3f v) {
      return this.cross(v, (Vector3f)null);
   }

   public Vector3f cross(Vector3f v, Vector3f result) {
      return this.cross(v.x, v.y, v.z, result);
   }

   public Vector3f cross(float otherX, float otherY, float otherZ, Vector3f result) {
      if (result == null) {
         result = new Vector3f();
      }

      float resX = this.y * otherZ - this.z * otherY;
      float resY = this.z * otherX - this.x * otherZ;
      float resZ = this.x * otherY - this.y * otherX;
      result.set(resX, resY, resZ);
      return result;
   }

   public Vector3f crossLocal(Vector3f v) {
      return this.crossLocal(v.x, v.y, v.z);
   }

   public Vector3f crossLocal(float otherX, float otherY, float otherZ) {
      float tempx = this.y * otherZ - this.z * otherY;
      float tempy = this.z * otherX - this.x * otherZ;
      this.z = this.x * otherY - this.y * otherX;
      this.x = tempx;
      this.y = tempy;
      return this;
   }

   public Vector3f project(Vector3f other) {
      float n = this.dot(other);
      float d = other.lengthSquared();
      return (new Vector3f(other)).normalizeLocal().multLocal(n / d);
   }

   public boolean isUnitVector() {
      float len = this.length();
      return 0.99F < len && len < 1.01F;
   }

   public float length() {
      return FastMath.sqrt(this.lengthSquared());
   }

   public float lengthSquared() {
      return this.x * this.x + this.y * this.y + this.z * this.z;
   }

   public float distanceSquared(Vector3f v) {
      double dx = (double)(this.x - v.x);
      double dy = (double)(this.y - v.y);
      double dz = (double)(this.z - v.z);
      return (float)(dx * dx + dy * dy + dz * dz);
   }

   public float distance(Vector3f v) {
      return FastMath.sqrt(this.distanceSquared(v));
   }

   public Vector3f mult(float scalar) {
      return new Vector3f(this.x * scalar, this.y * scalar, this.z * scalar);
   }

   public Vector3f mult(float scalar, Vector3f product) {
      if (null == product) {
         product = new Vector3f();
      }

      product.x = this.x * scalar;
      product.y = this.y * scalar;
      product.z = this.z * scalar;
      return product;
   }

   public Vector3f multLocal(float scalar) {
      this.x *= scalar;
      this.y *= scalar;
      this.z *= scalar;
      return this;
   }

   public Vector3f multLocal(Vector3f vec) {
      if (null == vec) {
         logger.warning("Provided vector is null, null returned.");
         return null;
      } else {
         this.x *= vec.x;
         this.y *= vec.y;
         this.z *= vec.z;
         return this;
      }
   }

   public Vector3f multLocal(float x, float y, float z) {
      this.x *= x;
      this.y *= y;
      this.z *= z;
      return this;
   }

   public Vector3f mult(Vector3f vec) {
      if (null == vec) {
         logger.warning("Provided vector is null, null returned.");
         return null;
      } else {
         return this.mult(vec, (Vector3f)null);
      }
   }

   public Vector3f mult(Vector3f vec, Vector3f store) {
      if (null == vec) {
         logger.warning("Provided vector is null, null returned.");
         return null;
      } else {
         if (store == null) {
            store = new Vector3f();
         }

         return store.set(this.x * vec.x, this.y * vec.y, this.z * vec.z);
      }
   }

   public Vector3f divide(float scalar) {
      scalar = 1.0F / scalar;
      return new Vector3f(this.x * scalar, this.y * scalar, this.z * scalar);
   }

   public Vector3f divideLocal(float scalar) {
      scalar = 1.0F / scalar;
      this.x *= scalar;
      this.y *= scalar;
      this.z *= scalar;
      return this;
   }

   public Vector3f divide(Vector3f scalar) {
      return new Vector3f(this.x / scalar.x, this.y / scalar.y, this.z / scalar.z);
   }

   public Vector3f divideLocal(Vector3f scalar) {
      this.x /= scalar.x;
      this.y /= scalar.y;
      this.z /= scalar.z;
      return this;
   }

   public Vector3f negate() {
      return new Vector3f(-this.x, -this.y, -this.z);
   }

   public Vector3f negateLocal() {
      this.x = -this.x;
      this.y = -this.y;
      this.z = -this.z;
      return this;
   }

   public Vector3f subtract(Vector3f vec) {
      return new Vector3f(this.x - vec.x, this.y - vec.y, this.z - vec.z);
   }

   public Vector3f subtractLocal(Vector3f vec) {
      if (null == vec) {
         logger.warning("Provided vector is null, null returned.");
         return null;
      } else {
         this.x -= vec.x;
         this.y -= vec.y;
         this.z -= vec.z;
         return this;
      }
   }

   public Vector3f subtract(Vector3f vec, Vector3f result) {
      if (result == null) {
         result = new Vector3f();
      }

      result.x = this.x - vec.x;
      result.y = this.y - vec.y;
      result.z = this.z - vec.z;
      return result;
   }

   public Vector3f subtract(float subtractX, float subtractY, float subtractZ) {
      return new Vector3f(this.x - subtractX, this.y - subtractY, this.z - subtractZ);
   }

   public Vector3f subtractLocal(float subtractX, float subtractY, float subtractZ) {
      this.x -= subtractX;
      this.y -= subtractY;
      this.z -= subtractZ;
      return this;
   }

   public Vector3f normalize() {
      float length = this.x * this.x + this.y * this.y + this.z * this.z;
      if (length != 1.0F && length != 0.0F) {
         length = 1.0F / FastMath.sqrt(length);
         return new Vector3f(this.x * length, this.y * length, this.z * length);
      } else {
         return this.clone();
      }
   }

   public Vector3f normalizeLocal() {
      float length = this.x * this.x + this.y * this.y + this.z * this.z;
      if (length != 1.0F && length != 0.0F) {
         length = 1.0F / FastMath.sqrt(length);
         this.x *= length;
         this.y *= length;
         this.z *= length;
      }

      return this;
   }

   public void maxLocal(Vector3f other) {
      this.x = other.x > this.x ? other.x : this.x;
      this.y = other.y > this.y ? other.y : this.y;
      this.z = other.z > this.z ? other.z : this.z;
   }

   public void minLocal(Vector3f other) {
      this.x = other.x < this.x ? other.x : this.x;
      this.y = other.y < this.y ? other.y : this.y;
      this.z = other.z < this.z ? other.z : this.z;
   }

   public Vector3f zero() {
      this.x = this.y = this.z = 0.0F;
      return this;
   }

   public float angleBetween(Vector3f otherVector) {
      float dotProduct = this.dot(otherVector);
      float angle = FastMath.acos(dotProduct);
      return angle;
   }

   public Vector3f interpolate(Vector3f finalVec, float changeAmnt) {
      this.x = (1.0F - changeAmnt) * this.x + changeAmnt * finalVec.x;
      this.y = (1.0F - changeAmnt) * this.y + changeAmnt * finalVec.y;
      this.z = (1.0F - changeAmnt) * this.z + changeAmnt * finalVec.z;
      return this;
   }

   public Vector3f interpolate(Vector3f beginVec, Vector3f finalVec, float changeAmnt) {
      this.x = (1.0F - changeAmnt) * beginVec.x + changeAmnt * finalVec.x;
      this.y = (1.0F - changeAmnt) * beginVec.y + changeAmnt * finalVec.y;
      this.z = (1.0F - changeAmnt) * beginVec.z + changeAmnt * finalVec.z;
      return this;
   }

   public static boolean isValidVector(Vector3f vector) {
      if (vector == null) {
         return false;
      } else if (!Float.isNaN(vector.x) && !Float.isNaN(vector.y) && !Float.isNaN(vector.z)) {
         return !Float.isInfinite(vector.x) && !Float.isInfinite(vector.y) && !Float.isInfinite(vector.z);
      } else {
         return false;
      }
   }

   public static void generateOrthonormalBasis(Vector3f u, Vector3f v, Vector3f w) {
      w.normalizeLocal();
      generateComplementBasis(u, v, w);
   }

   public static void generateComplementBasis(Vector3f u, Vector3f v, Vector3f w) {
      float fInvLength;
      if (FastMath.abs(w.x) >= FastMath.abs(w.y)) {
         fInvLength = FastMath.invSqrt(w.x * w.x + w.z * w.z);
         u.x = -w.z * fInvLength;
         u.y = 0.0F;
         u.z = w.x * fInvLength;
         v.x = w.y * u.z;
         v.y = w.z * u.x - w.x * u.z;
         v.z = -w.y * u.x;
      } else {
         fInvLength = FastMath.invSqrt(w.y * w.y + w.z * w.z);
         u.x = 0.0F;
         u.y = w.z * fInvLength;
         u.z = -w.y * fInvLength;
         v.x = w.y * u.z - w.z * u.y;
         v.y = -w.x * u.z;
         v.z = w.x * u.y;
      }

   }

   public Vector3f clone() {
      try {
         return (Vector3f)super.clone();
      } catch (CloneNotSupportedException var2) {
         throw new AssertionError();
      }
   }

   public float[] toArray(float[] floats) {
      if (floats == null) {
         floats = new float[3];
      }

      floats[0] = this.x;
      floats[1] = this.y;
      floats[2] = this.z;
      return floats;
   }

   public boolean equals(Object o) {
      if (!(o instanceof Vector3f)) {
         return false;
      } else if (this == o) {
         return true;
      } else {
         Vector3f comp = (Vector3f)o;
         if (Float.compare(this.x, comp.x) != 0) {
            return false;
         } else if (Float.compare(this.y, comp.y) != 0) {
            return false;
         } else {
            return Float.compare(this.z, comp.z) == 0;
         }
      }
   }

   public int hashCode() {
      int hash = 37;
      hash += 37 * hash + Float.floatToIntBits(this.x);
      hash += 37 * hash + Float.floatToIntBits(this.y);
      hash += 37 * hash + Float.floatToIntBits(this.z);
      return hash;
   }

   public String toString() {
      return "(" + this.x + ", " + this.y + ", " + this.z + ")";
   }

   public float getX() {
      return this.x;
   }

   public Vector3f setX(float x) {
      this.x = x;
      return this;
   }

   public float getY() {
      return this.y;
   }

   public Vector3f setY(float y) {
      this.y = y;
      return this;
   }

   public float getZ() {
      return this.z;
   }

   public Vector3f setZ(float z) {
      this.z = z;
      return this;
   }

   public float get(int index) {
      switch(index) {
      case 0:
         return this.x;
      case 1:
         return this.y;
      case 2:
         return this.z;
      default:
         throw new IllegalArgumentException("index must be either 0, 1 or 2");
      }
   }

   public void set(int index, float value) {
      switch(index) {
      case 0:
         this.x = value;
         return;
      case 1:
         this.y = value;
         return;
      case 2:
         this.z = value;
         return;
      default:
         throw new IllegalArgumentException("index must be either 0, 1 or 2");
      }
   }
}
