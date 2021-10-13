package halma;

import halma.controller.GameSetConnection;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class gamesever {
    private ServerSocket sever;
    private Socket client1;
    private Socket client2;
    private Socket client3;
    private Socket client4;
    private Socket[] watcher=new Socket[3];
    private static Thread stop;

    public static Thread getStop() {
        return stop;
    }

    public static void setStop(Thread stop) {
        gamesever.stop = stop;
    }

    public Socket[] getWatcher() {
        return watcher;
    }

    public void setWatcher(Socket[] watcher) {
        this.watcher = watcher;
    }
 private int playernow;

    public int getPlayernow() {
        return playernow;
    }

    public void setPlayernow(int playernow) {
        this.playernow = playernow;
    }

    private int player;
    private static Thread thread;
    private static Thread time;
    private Socket[] cl=new Socket[4];
    private int count=30;
    private static Thread sendtime;
    private Boolean countdown;
    private String locationnow;
    private static Thread receivetime;
    private static Thread servewatch;
    private static  Thread thread1;private static Thread thread2;private static Thread thread3;

    public static Thread getServewatch() {
        return servewatch;
    }

    public static void setServewatch(Thread servewatch) {
        gamesever.servewatch = servewatch;
    }

    public static Thread getReceivetime() {
        return receivetime;
    }

    public static void setReceivetime(Thread receivetime) {
        gamesever.receivetime = receivetime;
    }

    public String getLocationnow() {
        return locationnow;
    }

    public void setLocationnow(String locationnow) {
        this.locationnow = locationnow;
    }

    public Boolean getCountdown() {
        return countdown;
    }

    public void setCountdown(Boolean countdown) {
        this.countdown = countdown;
    }

    public static Thread getSendtime() {
        return sendtime;
    }

    public static void setSendtime(Thread sendtime) {
        gamesever.sendtime = sendtime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Socket[] getCl() {
        return cl;
    }

    public void setCl(Socket[] cl) {
        this.cl = cl;
    }

    public static Thread getThread() {
        return thread;
    }

    public static void setThread(Thread thread) {
        gamesever.thread = thread;
    }

    public ServerSocket getSever() {
        return sever;
    }

    public void setSever(ServerSocket sever) {
        this.sever = sever;
    }

    public Socket getClient1() {
        return client1;
    }

    public void setClient1(Socket client1) {
        this.client1 = client1;
    }

    public Socket getClient2() {
        return client2;
    }

    public void setClient2(Socket client2) {
        this.client2 = client2;
    }

    public Socket getClient3() {
        return client3;
    }

    public void setClient3(Socket client3) {
        this.client3 = client3;
    }

    public Socket getClient4() {
        return client4;
    }

    public void setClient4(Socket client4) {
        this.client4 = client4;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public gamesever() throws IOException {
        GameSetConnection setConnection=new GameSetConnection();ServerSocket server=new ServerSocket(1100);
    client1=server.accept();cl[0]=client1;
        InputStream playernum=client1.getInputStream();byte[] bt = new byte[1024];
        int len = playernum.read(bt);
        String number = new String(bt, 0, len);this.player=Integer.parseInt(number);
        if (player==2){client2=server.accept();cl[1]=client2;}
        if (player==4){client2=server.accept();cl[1]=client2;client3=server.accept();cl[2]=client3;client4=server.accept();cl[3]=client4;}
       for (int i=0;i<player;i++){String s="connect successfully!";OutputStream success=cl[i].getOutputStream();success.write(s.getBytes());}
     countdown=true;
        thread=new Thread(() -> {while (true){
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


                try {
                   locationnow= receiveheart(client1);               //接受坐标信息
                } catch (IOException e) {
                    try {System.out.println(15);
                        setConnection.sendlost();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    try {playernow++;
                        client1.close();countdown=false;client1=server.accept();playernow--;
                        OutputStream connect=client1.getOutputStream();connect.write(locationnow.getBytes());     //传递坐标信息
                        countdown=true;
                    } catch (IOException ex) {
                        e.printStackTrace();
                    }
                }

        }

        });
        thread1=new Thread(() -> {while (true){
            try {
                locationnow= receiveheart(client2);               //接受坐标信息
            } catch (IOException e) {
                try { System.out.println(15);
                    setConnection.sendlost();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {  playernow++;
                    client2.close();countdown=false;client2=server.accept();playernow--;
                    OutputStream connect=client2.getOutputStream();connect.write(locationnow.getBytes());     //传递坐标信息
                    countdown=true;
                } catch (IOException ex) {
                    e.printStackTrace();
                }
            }
        }

        });
        thread2=new Thread(() -> {while (true){
            try {
                locationnow= receiveheart(client3);               //接受坐标信息
            } catch (IOException e) {playernow++;
                try {
                    setConnection.sendlost();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    client3.close();countdown=false;client3=server.accept();playernow--;
                    OutputStream connect=client3.getOutputStream();connect.write(locationnow.getBytes());     //传递坐标信息
                    countdown=true;
                } catch (IOException ex) {
                    e.printStackTrace();
                }
            }
        }

        });
        thread3=new Thread(() -> {while (true){
            try {
                locationnow= receiveheart(client4);               //接受坐标信息
            } catch (IOException e) {
                try {
                    setConnection.sendlost();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                playernow++; try {
                    client4.close();countdown=false;client4=server.accept();playernow--;
                    OutputStream connect=client4.getOutputStream();connect.write(locationnow.getBytes());     //传递坐标信息
                    countdown=true;
                } catch (IOException ex) {
                    e.printStackTrace();
                }
            }
        }

        });
      stop=new Thread(() -> {while (true){
          try {
              stop.sleep(500);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }if (playernow==player){System.exit(0);}
      }});


        time=new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }if (countdown){count--;}if (count==0){setConnection.setTime(count);setConnection.sendTime();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    count=30;}
            }


        });
        sendtime=new Thread(() -> {while (true){

            setConnection.setTime(count);setConnection.sendTime();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        });
        receivetime=new Thread(() ->{while (true){
            try {
              if (setConnection.receivemoved()){count=30;}   } catch (IOException e) {
                e.printStackTrace();
            }}
        });
        servewatch=new Thread(() ->{int i=0;while (true){
            try {
                watcher[i]=server.accept();OutputStream lo=watcher[i].getOutputStream();lo.write(locationnow.getBytes());
      i++;      } catch (IOException e) {
                e.printStackTrace();
            }}



        });


      stop.start();  thread.start();time.start();sendtime.start();receivetime.start();servewatch.start();thread1.start();if (player==4){thread2.start();thread3.start();}}

    public String receiveheart(Socket client)throws IOException{ InputStream inputStream = client.getInputStream();
        byte[] bt = new byte[1024];
        int len = inputStream.read(bt);
        String location = new String(bt, 0, len);
       if (location!=null){return location;}return null;

    }

    public static void main(String[] args) {
        try {
            gamesever gamesever=new gamesever();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
