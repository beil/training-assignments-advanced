package com.jme3.util;

import java.nio.FloatBuffer;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;

public class BufferManipulationUtils {

    /**
     * Sets the data contained in the given color into the FloatBuffer at the
     * specified index.
     *
     * @param color
     *            the data to insert
     * @param buf
     *            the buffer to insert into
     * @param index
     *            the postion to place the data; in terms of colors not floats
     */
    public static void setInBuffer(ColorRGBA color, FloatBuffer buf, int index) {
        buf.position(index * 4);
        buf.put(color.r);
        buf.put(color.g);
        buf.put(color.b);
        buf.put(color.a);
    }

    /**
     * Sets the data contained in the given quaternion into the FloatBuffer at
     * the specified index.
     *
     * @param quat
     *            the {@link Quaternion} to insert
     * @param buf
     *            the buffer to insert into
     * @param index
     *            the position to place the data; in terms of quaternions not
     *            floats
     */
    public static void setInBuffer(Quaternion quat, FloatBuffer buf, int index) {
        buf.position(index * 4);
        buf.put(quat.getX());
        buf.put(quat.getY());
        buf.put(quat.getZ());
        buf.put(quat.getW());
    }

    /**
     * Sets the data contained in the given vector4 into the FloatBuffer at the
     * specified index.
     *
     * @param vec
     *            the {@link Vector4f} to insert
     * @param buf
     *            the buffer to insert into
     * @param index
     *            the position to place the data; in terms of vector4 not floats
     */
    public static void setInBuffer(Vector4f vec, FloatBuffer buf, int index) {
        buf.position(index * 4);
        buf.put(vec.getX());
        buf.put(vec.getY());
        buf.put(vec.getZ());
        buf.put(vec.getW());
    }

    /**
     * Sets the data contained in the given Vector3F into the FloatBuffer at the
     * specified index.
     *
     * @param vector
     *            the data to insert
     * @param buf
     *            the buffer to insert into
     * @param index
     *            the postion to place the data; in terms of vectors not floats
     */
    public static void setInBuffer(Vector3f vector, FloatBuffer buf, int index) {
        if (buf == null) {
            return;
        }
        if (vector == null) {
            buf.put(index * 3, 0);
            buf.put((index * 3) + 1, 0);
            buf.put((index * 3) + 2, 0);
        } else {
            buf.put(index * 3, vector.x);
            buf.put((index * 3) + 1, vector.y);
            buf.put((index * 3) + 2, vector.z);
        }
    }

    /**
     * Updates the values of the given vector from the specified buffer at the
     * index provided.
     *
     * @param vector
     *            the vector to set data on
     * @param buf
     *            the buffer to read from
     * @param index
     *            the position (in terms of vectors, not floats) to read from
     *            the buf
     */
    public static void populateFromBuffer(Vector3f vector, FloatBuffer buf, int index) {
        vector.x = buf.get(index * 3);
        vector.y = buf.get(index * 3 + 1);
        vector.z = buf.get(index * 3 + 2);
    }

    /**
     * Updates the values of the given vector from the specified buffer at the
     * index provided.
     *
     * @param vector
     *            the vector to set data on
     * @param buf
     *            the buffer to read from
     * @param index
     *            the position (in terms of vectors, not floats) to read from
     *            the buf
     */
    public static void populateFromBuffer(Vector4f vector, FloatBuffer buf, int index) {
        vector.x = buf.get(index * 4);
        vector.y = buf.get(index * 4 + 1);
        vector.z = buf.get(index * 4 + 2);
        vector.w = buf.get(index * 4 + 3);
    }


    /**
     * Normalize a Vector3f in-buffer.
     *
     * @param buf
     *            the buffer to find the Vector3f within
     * @param index
     *            the position (in terms of vectors, not floats) of the vector
     *            to normalize
     */
    public static void normalizeVector3(FloatBuffer buf, int index) {
        TempVars vars = TempVars.get();
        Vector3f tempVec3 = vars.vect1;
        populateFromBuffer(tempVec3, buf, index);
        tempVec3.normalizeLocal();
        setInBuffer(tempVec3, buf, index);
        vars.release();
    }

    /**
     * Add to a Vector3f in-buffer.
     *
     * @param toAdd
     *            the vector to add from
     * @param buf
     *            the buffer to find the Vector3f within
     * @param index
     *            the position (in terms of vectors, not floats) of the vector
     *            to add to
     */
    public static void addInBuffer(Vector3f toAdd, FloatBuffer buf, int index) {
        TempVars vars = TempVars.get();
        Vector3f tempVec3 = vars.vect1;
        populateFromBuffer(tempVec3, buf, index);
        tempVec3.addLocal(toAdd);
        setInBuffer(tempVec3, buf, index);
        vars.release();
    }

    /**
     * Multiply and store a Vector3f in-buffer.
     *
     * @param toMult
     *            the vector to multiply against
     * @param buf
     *            the buffer to find the Vector3f within
     * @param index
     *            the position (in terms of vectors, not floats) of the vector
     *            to multiply
     */
    public static void multInBuffer(Vector3f toMult, FloatBuffer buf, int index) {
        TempVars vars = TempVars.get();
        Vector3f tempVec3 = vars.vect1;
        populateFromBuffer(tempVec3, buf, index);
        tempVec3.multLocal(toMult);
        setInBuffer(tempVec3, buf, index);
        vars.release();
    }


    /**
     * Checks to see if the given Vector3f is equals to the data stored in the
     * buffer at the given data index.
     *
     * @param check
     *            the vector to check against - null will return false.
     * @param buf
     *            the buffer to compare data with
     * @param index
     *            the position (in terms of vectors, not floats) of the vector
     *            in the buffer to check against
     * @return true if the data is equivalent, otherwise false.
     */
    public static boolean equals(Vector3f check, FloatBuffer buf, int index) {
        TempVars vars = TempVars.get();
        Vector3f tempVec3 = vars.vect1;
        populateFromBuffer(tempVec3, buf, index);
        boolean eq = tempVec3.equals(check);
        vars.release();
        return eq;
    }

    /**
     * Copies a Vector3f from one position in the buffer to another. The index
     * values are in terms of vector number (eg, vector number 0 is positions
     * 0-2 in the FloatBuffer.)
     *
     * @param buf
     *            the buffer to copy from/to
     * @param fromPos
     *            the index of the vector to copy
     * @param toPos
     *            the index to copy the vector to
     */
    public static void copyInternalVector3(FloatBuffer buf, int fromPos, int toPos) {
        copyInternal(buf, fromPos * 3, toPos * 3, 3);
    }

    /**
     * Copies a Vector2f from one position in the buffer to another. The index
     * values are in terms of vector number (eg, vector number 0 is positions
     * 0-1 in the FloatBuffer.)
     *
     * @param buf
     *            the buffer to copy from/to
     * @param fromPos
     *            the index of the vector to copy
     * @param toPos
     *            the index to copy the vector to
     */
    public static void copyInternalVector2(FloatBuffer buf, int fromPos, int toPos) {
        copyInternal(buf, fromPos * 2, toPos * 2, 2);
    }

    /**
     * Copies floats from one position in the buffer to another.
     *
     * @param buf
     *            the buffer to copy from/to
     * @param fromPos
     *            the starting point to copy from
     * @param toPos
     *            the starting point to copy to
     * @param length
     *            the number of floats to copy
     */
    public static void copyInternal(FloatBuffer buf, int fromPos, int toPos, int length) {
        float[] data = new float[length];
        buf.position(fromPos);
        buf.get(data);
        buf.position(toPos);
        buf.put(data);
    }


    /**
     * Sets the data contained in the given Vector2F into the FloatBuffer at the
     * specified index.
     *
     * @param vector
     *            the data to insert
     * @param buf
     *            the buffer to insert into
     * @param index
     *            the position to place the data; in terms of vectors not floats
     */
    public static void setInBuffer(Vector2f vector, FloatBuffer buf, int index) {
        buf.put(index * 2, vector.x);
        buf.put((index * 2) + 1, vector.y);
    }

    /**
     * Updates the values of the given vector from the specified buffer at the
     * index provided.
     *
     * @param vector
     *            the vector to set data on
     * @param buf
     *            the buffer to read from
     * @param index
     *            the position (in terms of vectors, not floats) to read from
     *            the buf
     */
    public static void populateFromBuffer(Vector2f vector, FloatBuffer buf, int index) {
        vector.x = buf.get(index * 2);
        vector.y = buf.get(index * 2 + 1);
    }

    /**
     * Normalize a Vector2f in-buffer.
     *
     * @param buf
     *            the buffer to find the Vector2f within
     * @param index
     *            the position (in terms of vectors, not floats) of the vector
     *            to normalize
     */
    public static void normalizeVector2(FloatBuffer buf, int index) {
        TempVars vars = TempVars.get();
        Vector2f tempVec2 = vars.vect2d;
        populateFromBuffer(tempVec2, buf, index);
        tempVec2.normalizeLocal();
        setInBuffer(tempVec2, buf, index);
        vars.release();
    }

    /**
     * Add to a Vector2f in-buffer.
     *
     * @param toAdd
     *            the vector to add from
     * @param buf
     *            the buffer to find the Vector2f within
     * @param index
     *            the position (in terms of vectors, not floats) of the vector
     *            to add to
     */
    public static void addInBuffer(Vector2f toAdd, FloatBuffer buf, int index) {
        TempVars vars = TempVars.get();
        Vector2f tempVec2 = vars.vect2d;
        populateFromBuffer(tempVec2, buf, index);
        tempVec2.addLocal(toAdd);
        setInBuffer(tempVec2, buf, index);
        vars.release();
    }

    /**
     * Multiply and store a Vector2f in-buffer.
     *
     * @param toMult
     *            the vector to multiply against
     * @param buf
     *            the buffer to find the Vector2f within
     * @param index
     *            the position (in terms of vectors, not floats) of the vector
     *            to multiply
     */
    public static void multInBuffer(Vector2f toMult, FloatBuffer buf, int index) {
        TempVars vars = TempVars.get();
        Vector2f tempVec2 = vars.vect2d;
        populateFromBuffer(tempVec2, buf, index);
        tempVec2.multLocal(toMult);
        setInBuffer(tempVec2, buf, index);
        vars.release();
    }
}
