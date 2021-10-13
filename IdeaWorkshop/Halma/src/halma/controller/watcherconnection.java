package halma.controller;

import halma.model.ChessBoardLocation;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


public class watcherconnection {
    private InetAddress group;
    private MulticastSocket socket;
    private MulticastSocket timesend;
    private int time;
    private ChessBoardLocation selectedLocation;
    private ChessBoardLocation selectedSquare;

    public ChessBoardLocation getSelectedLocation() {
        return selectedLocation;
    }

    public void setSelectedLocation(ChessBoardLocation selectedLocation) {
        this.selectedLocation = selectedLocation;
    }

    public ChessBoardLocation getSelectedSquare() {
        return selectedSquare;
    }

    public void setSelectedSquare(ChessBoardLocation selectedSquare) {
        this.selectedSquare = selectedSquare;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public InetAddress getGroup() {
        return group;
    }

    public void setGroup(InetAddress group) {
        this.group = group;
    }

    public MulticastSocket getSocket() {
        return socket;
    }

    public void setSocket(MulticastSocket socket) {
        this.socket = socket;
    }

    public MulticastSocket getTimesend() {
        return timesend;
    }

    public void setTimesend(MulticastSocket timesend) {
        this.timesend = timesend;
    }

    public watcherconnection() throws IOException {
        group = InetAddress.getByName("224.255.10.0");
        socket = new MulticastSocket(9898);timesend=new MulticastSocket(9999);socket.joinGroup(group);timesend.joinGroup(group);
    }
    public void receiveTime() {
        int port = 9999;
        try {
            group = InetAddress.getByName("224.255.10.0");
            timesend = new MulticastSocket(port);
            timesend.joinGroup(group);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            DatagramPacket packet;
            byte[] data = new byte[1024];
            packet = new DatagramPacket(data, data.length, group, port);
            try {
                timesend.receive(packet);
                String time = new String(packet.getData(), 0, packet.getLength());
                if (time.length() <= 2) {
                    this.time = Integer.parseInt(time);
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void receiveChessBoardLocation() {
        int port = 9898;
        try {
            group = InetAddress.getByName("224.255.10.0");
            socket = new MulticastSocket(port);
            socket.joinGroup(group);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            DatagramPacket packet;
            byte[] data = new byte[1024];
            packet = new DatagramPacket(data, data.length, group, port);
            try {
                socket.receive(packet);
                String location = new String(packet.getData(), 0, packet.getLength());
                if (location.length() > 2 ) {
                    String[] loca = location.split(" ");
                    selectedLocation = new ChessBoardLocation(Integer.parseInt(loca[0]), Integer.parseInt(loca[1]));
                    selectedSquare = new ChessBoardLocation(Integer.parseInt(loca[2]), Integer.parseInt(loca[3]));
                    if (selectedLocation != null && selectedSquare != null) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
