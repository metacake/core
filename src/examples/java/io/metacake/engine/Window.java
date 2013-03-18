package io.metacake.engine;

import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.common.window.CloseObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;
import java.util.List;

/**
 * @author florence
 * @author rpless
 */
public class Window implements CakeWindow<JFrame>{
    JFrame frame = new JFrame(); // CONCERN
    DrawPanel draw = new DrawPanel(500,500); //CONCERN
    List<CloseObserver> observers = new LinkedList<>();

    public Window() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(draw);
        frame.pack();
        frame.setVisible(true);
        draw.createBufferStrategy();
    }

    @Override
    public void close() {
        for(CloseObserver c : observers) {
            c.onClose();
        }
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
        observers.add(o);
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