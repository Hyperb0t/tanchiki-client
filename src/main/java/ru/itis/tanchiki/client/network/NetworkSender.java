package ru.itis.tanchiki.client.network;

import java.io.OutputStream;
import java.io.PrintWriter;

import ru.kpfu.itis.rodsher.tanchiki.models.Direction;
import ru.kpfu.itis.rodsher.tanchiki.protocol.*;

public class NetworkSender {
    private PrintWriter writer;
    private PackageSetter packageSetter;

    public NetworkSender(OutputStream outputStream) {
        writer = new PrintWriter(outputStream, true);
        packageSetter = new PackageSetter();
    }

    public void sendTankMoved(float oldX, float oldY, float x, float y, Direction d) {
        writer.println(packageSetter.setTankMoved(oldX, oldY, x, y, d));
    }
}
