package taku.com.kadaiball;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by TAKU on 2017/06/12.
 */

public class Ball extends Paint {

    private float X, Y, dX = 5, dY = 5;
    private float[] move;
    private static final int MARGIN = 10;
    private boolean isRotateX = false, isRotateY = false;

    public Ball(float x, float y, float[] move) {
        setColor(Color.WHITE);
        this.X = x;
        this.Y = y;
        this.move = move;
    }

    public void moveBall(int width, int height) {
        if (move[0] >= MARGIN || -MARGIN >= move[0]) {
            dX = move[0];
            isRotateX = true;
        } else {
            isRotateX = false;
        }

        if (move[1] >= MARGIN || -MARGIN >= move[1]) {
            dY = -move[1];
            isRotateY = true;
        } else {
            isRotateY = false;
        }

        X += dX;
        Y += dY;

        if (X < 0 || width < X) {
            if (isRotateX) {
                if (dX < 0) {
                    X = 0;
                } else {
                    X = width;
                }
            } else {
                dX *= -1;
            }
        }

        if (Y < 0 || height < Y) {
            if (isRotateY) {
                if (dY < 0) {
                    Y = 0;
                } else {
                    Y = height;
                }
            } else {
                dY *= -1;
            }
        }

    }

    public float getBallX() {
        return X;
    }

    public float getBallY() {
        return Y;
    }


}
