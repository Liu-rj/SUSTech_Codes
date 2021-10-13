package halma.controller;

import halma.model.ChessBoard;
import halma.model.ChessBoardLocation;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

public class GameConnection {
    private InetAddress group;
    private MulticastSocket socket;
    private ChessBoard model;
    private ChessBoardLocation selectedLocation;
    private Color currentPlayer;
    private int[][] store = new int[17][16];
    private ChessBoardLocation selectedSquare;
    private int time = 30;
    private Boolean withdraw = false;
    private Boolean restart = false;
    private MulticastSocket timesend;
    private MulticastSocket sendwithdraw;
    private MulticastSocket lost;

    public MulticastSocket getLost() {
        return lost;
    }

    public void setLost(MulticastSocket lost) {
        this.lost = lost;
    }

    private int timesendport;
    private int sendwithdrawport;
    private int portlocation;
    private MulticastSocket move;

    public MulticastSocket getMove() {
        return move;
    }

    public void setMove(MulticastSocket move) {
        this.move = move;
    }

    public int getTimesendport() {
        return timesendport;
    }

    public void setTimesendport(int timesendport) {
        this.timesendport = timesendport;
    }

    public int getSendwithdrawport() {
        return sendwithdrawport;
    }

    public void setSendwithdrawport(int sendwithdrawport) {
        this.sendwithdrawport = sendwithdrawport;
    }

    public int getPortlocation() {
        return portlocation;
    }

    public void setPortlocation(int portlocation) {
        this.portlocation = portlocation;
    }

    public MulticastSocket getTimesend() {
        return timesend;
    }

    public void setTimesend(MulticastSocket timesend) {
        this.timesend = timesend;
    }

    public MulticastSocket getSendwithdraw() {
        return sendwithdraw;
    }

    public void setSendwithdraw(MulticastSocket sendwithdraw) {
        this.sendwithdraw = sendwithdraw;
    }

    public Boolean getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(Boolean withdraw) {
        this.withdraw = withdraw;
    }

    public Boolean getRestart() {
        return restart;
    }

    public void setRestart(Boolean restart) {
        this.restart = restart;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Color currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
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

    public GameConnection() { try {
        group = InetAddress.getByName("224.255.10.0");
        socket = new MulticastSocket(9898);timesend=new MulticastSocket(9999);
        timesend.joinGroup(group);sendwithdraw=new MulticastSocket(10000);sendwithdraw.joinGroup(group);
        socket.joinGroup(group);
    } catch (IOException e) {
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
    public void movedsend() throws IOException {
        move=new MulticastSocket(10002);move.joinGroup(group);DatagramPacket packet;
        String moves="has moved";byte[] d=moves.getBytes();
        packet=new DatagramPacket(d,d.length,group,10002);move.send(packet);
    }
    public void sendSelectedLocation() {
        String location1 = selectedLocation.getRow() + " " + selectedLocation.getColumn() + " " + selectedSquare.getRow() + " " + selectedSquare.getColumn();
        DatagramPacket packet;
        int port = 9898;
        byte[] data = location1.getBytes();
        packet = new DatagramPacket(data, data.length, group, port);
      try {
            socket.send(packet);
            Thread.sleep(50);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

}
  /******要加的*/

public void receivelost()  {
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
                    System.out.println(1);
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }}


}







