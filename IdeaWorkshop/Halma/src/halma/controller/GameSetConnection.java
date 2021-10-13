package halma.controller;

import halma.model.ChessBoard;
import halma.model.ChessBoardLocation;
import halma.view.ChessBoardComponent;
import halma.view.ChessComponent;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;


public class GameSetConnection {
    int port = 9898;
    private InetAddress group;
    private MulticastSocket socket;
    private MulticastSocket timesend;
    private MulticastSocket sendwithdraw;
    private int player;
    private MulticastSocket lost;

    public MulticastSocket getLost() {
        return lost;
    }

    public void setLost(MulticastSocket lost) {
        this.lost = lost;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public MulticastSocket getSendwithdraw() {
        return sendwithdraw;
    }

    public void setSendwithdraw(MulticastSocket sendwithdraw) {
        this.sendwithdraw = sendwithdraw;
    }

    public MulticastSocket getTimesend() {
        return timesend;
    }

    public void setTimesend(MulticastSocket timesend) {
        this.timesend = timesend;
    }

    private int time = 30;
    private ChessBoard model;
    private ChessBoardLocation selectedLocation;
    private int[][] store = new int[17][16];
    private ChessBoardLocation selectedSquare;


    public MulticastSocket getMove() {
        return move;
    }

    public void setMove(MulticastSocket move) {
        this.move = move;
    }

    private Color currentPlayer;
    private Boolean withdraw = false;
    private Boolean restart = false;
    private MulticastSocket move;

    public Boolean getRestart() {
        return restart;
    }

    public void setRestart(Boolean restart) {
        this.restart = restart;
    }

    public Boolean getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(Boolean withdraw) {
        this.withdraw = withdraw;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Color currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public ChessBoardLocation getSelectedSquare() {
        return selectedSquare;
    }

    public void setSelectedSquare(ChessBoardLocation selectedSquare) {
        this.selectedSquare = selectedSquare;
    }

    public ChessBoard getModel() {
        return model;
    }

    public void setModel(ChessBoard model) {
        this.model = model;
    }

    public ChessBoardLocation getSelectedLocation() {
        return selectedLocation;
    }

    public void setSelectedLocation(ChessBoardLocation selectedLocation) {
        this.selectedLocation = selectedLocation;
    }

    public GameSetConnection() {
        try {
            group = InetAddress.getByName("224.255.10.0");
            socket = new MulticastSocket(port);
            timesend = new MulticastSocket(9999);
            timesend.joinGroup(group);
            sendwithdraw = new MulticastSocket(10000);
            sendwithdraw.joinGroup(group);
            socket.joinGroup(group);
            move = new MulticastSocket(10002);
            move.joinGroup(group);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public Boolean receivemoved() throws IOException {
        byte[] d = new byte[1024];
        DatagramPacket packet;
        packet = new DatagramPacket(d, d.length, group, 10002);
        while (true) {
            move.receive(packet);
            String hasmove = new String(packet.getData(), 0, packet.getLength());
            if (hasmove.equals("has moved")) {
                return true;
            }
        }
    }

    public void movedsend() throws IOException {
        move = new MulticastSocket(10002);
        move.joinGroup(group);
        DatagramPacket packet;
        String moves = "has moved";
        byte[] d = moves.getBytes();
        packet = new DatagramPacket(d, d.length, group, 10002);
        move.send(packet);
    }

    public void sendTime() {
        DatagramPacket packet;
        String time1 = String.valueOf(time);
        byte[] data = time1.getBytes();
        packet = new DatagramPacket(data, data.length, group, 9999);
        try {
            timesend.send(packet);
            Thread.sleep(500);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void sendSelectedLocation() {
        String location1 = selectedLocation.getRow() + " " + selectedLocation.getColumn() + " " + selectedSquare.getRow() + " " + selectedSquare.getColumn();
        DatagramPacket packet;
        byte[] data = location1.getBytes();
        packet = new DatagramPacket(data, data.length, group, port);
        try {
            socket.send(packet);
            Thread.sleep(50);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
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
                if (location.equals("false")) {
                    break;
                }
                if (location.length() > 2) {
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

    /******要加的*/
    public void sendlost() throws IOException {
        MulticastSocket lost = new MulticastSocket(10006);
        lost.joinGroup(group);
        String l = "lostconnect";
        DatagramPacket packet;
        byte[] data = l.getBytes();
        packet = new DatagramPacket(data, data.length, group, 10006);
        lost.send(packet);
    }

    public void receivelost() {
        int port = 10006;
        try {
            group = InetAddress.getByName("224.255.10.0");
            lost = new MulticastSocket(port);
            lost.joinGroup(group);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            DatagramPacket packet;
            byte[] data = new byte[1024];
            packet = new DatagramPacket(data, data.length, group, port);
            try {
                lost.receive(packet);
                String l = new String(packet.getData(), 0, packet.getLength());
                if (l.equals("lostconnect")) {
                    JOptionPane.showMessageDialog(new JFrame(), "someone has lose connection!");
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
