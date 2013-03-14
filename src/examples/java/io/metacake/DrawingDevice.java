package io.metacake;

import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.common.window.CloseObserver;
import io.metacake.core.output.OutputDevice;
import io.metacake.core.output.OutputDeviceName;
import io.metacake.core.output.RenderingInstruction;

import javax.swing.*;
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

    void draw(List<RenderingInstruction> r) {
        Graphics g = draw.bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, draw.getWidth(), draw.getHeight());
        for (RenderingInstruction d : r) {
            ((DrawInstruction) d).draw(g);
        }
        g.dispose();
        draw.bufferStrategy.show();
    }
}

// TODO: generic no shitty thread. FUCK YOU SUN
class DrawingThread extends Thread {
    DrawingDevice d;
    volatile boolean running = false;

    DrawingThread(DrawingDevice d) {
        this.d = d;
    }

    @Override
    public void run() {
        for (;running; ) {
            try {
                d.draw(d.queue.take());
            } catch (InterruptedException e) {
                this.running = false;
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

class DrawInstruction implements RenderingInstruction {
    int pos;

    DrawInstruction(int pos) {
        this.pos = pos;
    }

    void draw(Graphics g) {
        g.fillOval(200, pos, 10, 10);
    }
}
