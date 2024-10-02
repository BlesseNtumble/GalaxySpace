package galaxyspace.core.client.animations.MCACommonLibrary;

import galaxyspace.core.client.animations.MCACommonLibrary.math.Matrix4f;
import galaxyspace.core.client.animations.MCACommonLibrary.math.Quaternion;
import galaxyspace.core.client.animations.MCACommonLibrary.math.Vector3f;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Utils {
   public static FloatBuffer makeFloatBuffer(float[] arr) {
      ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
      bb.order(ByteOrder.nativeOrder());
      FloatBuffer fb = bb.asFloatBuffer();
      fb.put(arr);
      fb.position(0);
      return fb;
   }

   public static ByteBuffer makeByteBuffer(byte[] arr) {
      ByteBuffer bb = ByteBuffer.allocateDirect(arr.length);
      bb.order(ByteOrder.nativeOrder());
      bb.put(arr);
      bb.position(0);
      return bb;
   }

   public static Quaternion getQuaternionFromMatrix(Matrix4f matrix) {
      Matrix4f copy = new Matrix4f(matrix);
      return new Quaternion(copy.transpose());
   }

   public static Quaternion getQuaternionFromEulers(float x, float y, float z) {
      Quaternion quatX = new Quaternion(Vector3f.UNIT_X, (float)Math.toRadians((double)x));
      Quaternion quatY = new Quaternion(Vector3f.UNIT_Y, (float)Math.toRadians((double)y));
      Quaternion quatZ = new Quaternion(Vector3f.UNIT_Z, (float)Math.toRadians((double)z));
      quatY.mul(quatY, quatX);
      quatZ.mul(quatZ, quatY);
      return quatZ;
   }
}
