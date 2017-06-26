package taku.com.kadaiball;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by TAKU on 2017/06/12.
 */

public class Ball extends Paint {

    private float X, Y, dX, dY;
    private float[] move;

    public Ball(float x, float y, float[] move) {
        setColor(Color.WHITE);
        this.X = x;
        this.Y = y;
        this.move = move;
    }

    public void moveBall(int width, int height) {
        dX = dX + (move[0] * 5);
        dY = dY + (move[1] * 5);
        X += dX;
        Y += dY;
        if (X < 0 || width < X) {
            dX *= -1;
        }
        if (Y < 0 || height < Y) {
            dY *= -1;
        }
    }

    public float getBallX() {
        return X;
    }

    public float getBallY() {
        return Y;
    }


}
