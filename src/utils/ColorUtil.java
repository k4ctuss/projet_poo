package utils;
import java.awt.Color;

/**
 * Utility to create color gradients.
 */
public final class ColorUtil {
    private ColorUtil() {}

    /**
     * Creates a gradient of n colors from white to black (inclusive).
     * Index 0 = white (255,255,255), index n-1 = black (0,0,0).
     */
    public static Color[] whiteToBlack(int n) {
        if (n <= 0) throw new IllegalArgumentException("n must be > 0");
        Color[] palette = new Color[n];
        for (int i = 0; i < n; i++) {
            // t in [0,1], 0->white, 1->black
            double t = (n == 1) ? 1.0 : (double) i / (double) (n - 1);
            int v = (int) Math.round(255 * (1.0 - t)); // 255..0
            palette[i] = new Color(v, v, v);
        }
        return palette;
    }
}

