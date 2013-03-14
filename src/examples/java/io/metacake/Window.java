package io.metacake;

import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.common.window.CloseObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * @author florence
 * @author rpless
 */
public class Window implements CakeWindow<JFrame>{
    JFrame frame = new JFrame(); // CONCERN
    DrawPanel draw = new DrawPanel(500,500); //CONCERN

    public Window() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(draw);
        frame.pack();
        frame.setVisible(true);
        draw.createBufferStrategy();
    }

    @Override
    public void close() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getX() {
        return frame.getX();
    }

    @Override
    public int getY() {
        return frame.getY();
    }

    @Override
    public int getWidth() {
        return frame.getWidth();
    }

    @Override
    public int getHeight() {
        return frame.getHeight();
    }

    @Override
    public void addCloseObserver(CloseObserver o) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public JFrame getRawWindow() {
        return frame;
    }
}

class DrawPanel extends JPanel {
    public static final int NUM_BUFFERS = 2;
    private Canvas canvas;
    BufferStrategy bufferStrategy;

    DrawPanel(int w, int h) {
        super();
        this.setSize(w,h);
        canvas = new Canvas();
        canvas.setSize(w,h);
        this.add(canvas);
        this.setPreferredSize(new Dimension(w,h));
    }

    void createBufferStrategy(){
        canvas.createBufferStrategy(NUM_BUFFERS);
        bufferStrategy = canvas.getBufferStrategy();
    }
}