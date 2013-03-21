package io.metacake.engine;

import io.metacake.core.output.RenderingInstruction;

import java.awt.*;

/**
 * @author florence
 * @author rpless
 */
public class DrawInstruction implements RenderingInstruction {
    int x,y;

    public DrawInstruction(int x,int y) {
        this.x = x;
        this.y = y;
    }

    void draw(Graphics g) {
        g.fillOval(x, y, 10, 10);
    }
}
