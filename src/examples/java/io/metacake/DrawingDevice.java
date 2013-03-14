package io.metacake;

import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.output.OutputDevice;
import io.metacake.core.output.OutputDeviceName;
import io.metacake.core.output.RenderingInstruction;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author florence
 * @author rpless
 */
public class DrawingDevice implements OutputDevice{
    public static final OutputDeviceName NAME = new OutputDeviceName();
    CakeWindow<JFrame> window;
    DrawPannel draw;
    @Override
    public void render(List<RenderingInstruction> r) {
        Graphics g = draw.bufferStrategy.getDrawGraphics();
        g.clearRect(0,0,draw.getWidth(),draw.getHeight());
        for(RenderingInstruction d : r) {
            ((DrawInstruction)d).draw(g);
        }
        g.dispose();
        draw.bufferStrategy.show();
    }

    @Override
    public void startOutputLoop() {
        //TODO syncronus BAD
    }

    @Override
    public void bind(CakeWindow w) {
        window = w;
        draw =  ((Window)window).draw;
    }
}



class DrawInstruction implements RenderingInstruction {
    int pos;

    DrawInstruction(int pos) {
        this.pos = pos;
    }

    void draw(Graphics g) {
        g.fillOval(200,pos,10,10);
    }
}
