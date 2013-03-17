package io.metacake.engine;

import io.metacake.core.common.MilliTimer;
import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.common.window.CloseObserver;
import io.metacake.core.output.OutputDevice;
import io.metacake.core.output.OutputDeviceName;
import io.metacake.core.output.RenderingInstruction;

import java.awt.*;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author florence
 * @author rpless
 */
public class DrawingDevice implements OutputDevice {
    public static final OutputDeviceName NAME = new OutputDeviceName();
    Window window;
    DrawPanel draw;
    LinkedBlockingDeque<List<RenderingInstruction>> queue = new LinkedBlockingDeque<>();

    @Override
    public void render(List<RenderingInstruction> r) {
        queue.offer(r);
    }

    @Override
    public void startOutputLoop() {
        DrawingThread drawingThread = new DrawingThread(this);

        drawingThread.start();
        window.addCloseObserver(new DrawingCloseObserver(drawingThread));
    }

    @Override
    public void bind(CakeWindow w) {
        window = (Window) w;
        draw = window.draw;
    }

    private MilliTimer t = new MilliTimer();

    void draw(List<RenderingInstruction> r) {
        System.out.print("starting draw: " + t.update() + "\t");
        Graphics g = draw.bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, draw.getWidth(), draw.getHeight());
        System.out.print(t.update() + "\t");
        for (RenderingInstruction d : r) {
            ((DrawInstruction) d).draw(g);
        }
        System.out.print(t.update() + "\t");
        g.dispose();
        System.out.print(t.update() + "\t");
        draw.bufferStrategy.show();
        System.out.println(t.update());
    }
}

// TODO: generic no shitty thread. FUCK YOU ORACLE
class DrawingThread extends Thread {
    volatile DrawingDevice d;
    volatile boolean running = true;

    DrawingThread(DrawingDevice d) {
        this.d = d;
    }

    @Override
    public void run() {
        MilliTimer t = new MilliTimer();
        for (;running; ) {
            try {
                d.draw(d.queue.take());
            } catch (InterruptedException e) {
                this.running = false;
                e.printStackTrace();
            }
        }
    }

    public void stopO() {
        running = false;
        this.interrupt();
    }
}

// TODO: generic observer for threads
class DrawingCloseObserver implements CloseObserver {
    DrawingThread t;

    DrawingCloseObserver(DrawingThread t) {
        this.t = t;
    }

    @Override
    public void onClose() {
        t.stopO();
    }
}

