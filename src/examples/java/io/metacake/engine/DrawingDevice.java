package io.metacake.engine;

import io.metacake.core.common.TimedObserverThread;
import io.metacake.core.common.window.CakeWindow;
import io.metacake.core.output.system.OutputDevice;
import io.metacake.core.output.OutputDeviceName;
import io.metacake.core.output.RenderingInstruction;

import java.awt.*;
import java.util.List;

/**
 * @author florence
 * @author rpless
 */
public class DrawingDevice implements OutputDevice {
    public static final OutputDeviceName NAME = new OutputDeviceName();
    Window window;
    DrawPanel draw;
    SyncState sync = new SyncState();

    @Override
    public void render(List<RenderingInstruction> r) {
        sync.setState(r);
    }

    @Override
    public void startOutputLoop() {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                draw(sync.getState());
            }
        };
        TimedObserverThread drawingThread = new TimedObserverThread(run,window);

        drawingThread.start();
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

