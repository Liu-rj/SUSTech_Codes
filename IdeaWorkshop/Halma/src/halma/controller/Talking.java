package halma.controller;

import halma.view.TextBorderUtlis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class Talking extends JPanel {
    private InetAddress group;
    private MulticastSocket socket;
    JTextArea textA = new JTextArea(4, 17);
    JTextArea area = new JTextArea(8, 17);
    private int port = 10001;

    private MulticastSocket sendmessage;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Icon icon = new ImageIcon("C:\\Users\\21548\\IdeaProjects\\Halma\\src\\halma\\images/send.png");
    //private Icon icon = new ImageIcon("D:\\Halma\\src\\halma\\images/send.png");

    public MulticastSocket getSendmessage() {
        return sendmessage;
    }

    public void setSendmessage(MulticastSocket sendmessage) {
        this.sendmessage = sendmessage;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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


    public Talking(int id) {this.id=id;
        setOpaque(false);
        setVisible(true);
        setSize(300, 380);
        TextBorderUtlis borderUtlis = new TextBorderUtlis(new Color(192,192,192),5, true);
        textA.setEditable(true);
        textA.setLineWrap(true);
        textA.setFont(new Font(Font.DIALOG,Font.BOLD,20));
        //textA.setBorder(borderUtlis);
        JScrollPane jScrollPane = new JScrollPane(textA);
        Dimension dimension = textA.getPreferredSize();
        jScrollPane.setBounds(0,255,dimension.width,dimension.height);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);//设置无垂直滚动条
        jScrollPane.setBorder(borderUtlis);//添加滚动条的jtextarea设置无边框
        jScrollPane.setOpaque(false); //设置背景透明
        setLayout(null);

        area.setEditable(false);
        area.setLineWrap(true);    //设置文本域中的文本为自动换行
        area.setFont(new Font(Font.DIALOG,Font.BOLD,20));    //修改字体样式
        //area.setBorder(borderUtlis);
        JScrollPane scrollPane = new JScrollPane(area);
        Dimension size = area.getPreferredSize();
        scrollPane.setBounds(0,0,size.width,size.height);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);//设置无垂直滚动条
        scrollPane.setBorder(borderUtlis);//添加滚动条的jtextarea设置无边框
        scrollPane.setOpaque(false); //设置背景透明

        Dimension preferredSize = new Dimension(32, 32);
        JButton send = new JButton(icon);
        send.setContentAreaFilled(false);
        send.setBorderPainted(false);
        send.setVisible(false);
        send.setSize(32,32);
        send.setLocation(230,215);
        send.setVisible(true);
        send.setPreferredSize(preferredSize);
        send.repaint();
        send.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        add(send);
        add(scrollPane);
        add(jScrollPane);

        send.addActionListener(e -> {
                area.setText(area.getText() + "\n" + id+":"+ textA.getText());
                sendme();
                textA.setText(null);
        });
    }

    public void receive() {
        try {
            group = InetAddress.getByName("224.255.10.0");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            sendmessage = new MulticastSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            sendmessage.joinGroup(group);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            DatagramPacket packet;
            byte[] data = new byte[1024];
            packet = new DatagramPacket(data, data.length, group, port);

            try {
                sendmessage.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String ifmessage = new String(packet.getData(), 0, packet.getLength());String x=String.valueOf(id);
            if (ifmessage != null&&ifmessage.indexOf(x)!=0) {
                area.setText(area.getText() + "\n"  + ifmessage);
            }
        }

    }

    public void sendme() {
        String message = id+":"+textA.getText();
        DatagramPacket packet;

        byte data[] = message.getBytes();
        packet = new DatagramPacket(data, data.length, group, port);
        try {
            sendmessage.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
