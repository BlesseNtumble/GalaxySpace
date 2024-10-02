package galaxyspace.core.client.animations.MCACommonLibrary.math;

public class FastMath {
   public static final double DBL_EPSILON = 2.220446049250313E-16D;
   public static final float FLT_EPSILON = 1.1920929E-7F;
   public static final float ZERO_TOLERANCE = 1.0E-4F;
   public static final float PI = 3.1415927F;
   public static final float TWO_PI = 6.2831855F;
   public static final float HALF_PI = 1.5707964F;
   public static final float QUARTER_PI = 0.7853982F;

   public static float sqrt(float fValue) {
      return (float)Math.sqrt((double)fValue);
   }

   public static float invSqrt(float fValue) {
      return (float)(1.0D / Math.sqrt((double)fValue));
   }

   public static float abs(float fValue) {
      return fValue < 0.0F ? -fValue : fValue;
   }

   public static float sin(float v) {
      return (float)Math.sin((double)v);
   }

   public static float cos(float v) {
      return (float)Math.cos((double)v);
   }

   public static float tan(float fValue) {
      return (float)Math.tan((double)fValue);
   }

   public static float asin(float fValue) {
      if (-1.0F < fValue) {
         return fValue < 1.0F ? (float)Math.asin((double)fValue) : 1.5707964F;
      } else {
         return -1.5707964F;
      }
   }

   public static float acos(float fValue) {
      if (-1.0F < fValue) {
         return fValue < 1.0F ? (float)Math.acos((double)fValue) : 0.0F;
      } else {
         return 3.1415927F;
      }
   }

   public static float atan(float fValue) {
      return (float)Math.atan((double)fValue);
   }

   public static float atan2(float fY, float fX) {
      return (float)Math.atan2((double)fY, (double)fX);
   }

   public static float ceil(float fValue) {
      return (float)Math.ceil((double)fValue);
   }

   public static float floor(float fValue) {
      return (float)Math.floor((double)fValue);
   }
}
