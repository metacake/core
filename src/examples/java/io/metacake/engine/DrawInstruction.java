package io.metacake.engine;

import io.metacake.core.output.RenderingInstruction;

import java.awt.*;

/**
 * @author florence
 * @author rpless
 */
public class DrawInstruction implements RenderingInstruction {
    int pos;

    public DrawInstruction(int pos) {
        this.pos = pos;
    }

    void draw(Graphics g) {
        g.fillOval(200, pos, 10, 10);
    }
}
