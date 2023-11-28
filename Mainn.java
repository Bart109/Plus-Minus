import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Mainn extends JFrame {
    private JButton button1;
    String path;
    String pathh;
    String everything;
    int i =0;
    private JTextArea textArea1;
    private JPanel root;
    private JButton button2;
    private JButton loadDataButton;
    private JCheckBox awayCheckBox;


    public void count(String in, String out)
    {
        int value = 0 ;
        int valuee = 0;
        int valueee = 0;
        if(out==null)
        {
            out="Quarter: 0  Time Remaining: 0:00  Trailblazers 0  Nets 0";
        }
        in = in.replace(",","");
        out = out.replace(",","");
        String[] num = in.split(" ");
        String[] numm = out.split(" ");
        if(numm.length<12)
        {
            int aa = Integer.valueOf(num[8]);
            int bb = Integer.valueOf(num[11]);
            int aaa = Integer.valueOf(numm[4]);
            int bbb = Integer.valueOf(numm[6]);
            value = aaa - aa;
            valuee = bbb - bb;
            valueee = value - valuee;
            i = i + valueee;
            //System.out.println(i);
        }
        else {
            int aa = Integer.valueOf(num[8]);
            int bb = Integer.valueOf(num[11]);
            int aaa = Integer.valueOf(numm[8]);
            int bbb = Integer.valueOf(numm[11]);
            value = aa - aaa;
            valuee = bb - bbb;
            valueee = value - valuee; // to trzeba zmienic
            i = i + valueee;
            //System.out.println(i);
        }
    }

    public void read(String a) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(a));
        try {
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                line = br.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        } finally {
            br.close();
        }
    }

    public void search(String team,String player)
    {
        String a = null;
        String b= null;
        String c = null;
        int sub = 0;
        int subb = 0;
        boolean s=false;
        Scanner scanner = new Scanner(everything);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(line.equals("Center - "+player)) {
                s=true;
            }
            if(line.equals("Power Forward - "+player)) {
                s=true;
            }
            if(line.equals("Small Forward - "+player)) {
                s=true;
            }
            if(line.equals("Shooting Guard - "+player)) {
                s=true;
            }
            if(line.equals("Point Guard - "+player)) {
                s=true;
            }
                if(line.equals(player+" heads to the bench.") && sub==0)
                {
                    sub=1;
                }
                if(sub==1)
                {
                    if(line.matches(".*\\b"+team+"\\b.*")) {
                        a = line;
                        count(a,b);
                        sub=4;
                        subb=0;
                    }
                }
                if(line.equals(player+" comes into the game at C.") && subb==0)
                {
                    subb=1;
                }
                if(line.equals(player+" comes into the game at PF.") && subb==0)
                {
                subb=1;
                }
            if(line.equals(player+" comes into the game at SF.") && subb==0)
            {
                subb=1;
            }
            if(line.equals(player+" comes into the game at SG.") && subb==0)
            {
                subb=1;
            }
            if(line.equals(player+" comes into the game at PG.") && subb==0)
            {
                subb=1;
            }
                if(subb==1)
                {
                if(line.matches(".*\\b"+team+"\\b.*")) {
                    b = line;
                    subb=4;
                    sub =0;
                    //System.out.println(a+" R "+b); off
                    //count(a,b);
                }
            }
                if(line.matches(".*\\bFinal Score\\b.*"))
                {
                    c = line;
                }
        }
        if(sub==0 && subb==4 && s==true)
        {
            count(b,c);
        }
        if(sub==0 && subb==4 && s==false)
        {
            count(b,c);
        }
        scanner.close();
    }
    public Mainn() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("txt","txt");
                fileChooser.setFileFilter(filter);
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
                int result = fileChooser.showOpenDialog(root);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    path = selectedFile.getAbsolutePath();
                }
                try {
                    read(path);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("txt","txt");
                fileChooser.setFileFilter(filter);
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
                int result = fileChooser.showOpenDialog(root);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    pathh = selectedFile.getAbsolutePath();
                }
            }
        });
        loadDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String t=null;
                int j=1;
                try {
                    read(pathh);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                Scanner scanner = new Scanner(everything);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if(j==1)
                    {
                        t=line;
                    }
                    if(j>1) {
                        try {
                            read(path);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        search(t,line);
                        if(awayCheckBox.isSelected())
                        {
                            i=-i;
                        }
                        if(i>0) {
                            textArea1.append(line + " + " + i + "\n");
                        }
                        else
                        {
                            textArea1.append(line + " " + i + "\n");
                        }
                        //System.out.println(line+" "+i);
                        i=0;
                    }
                    j++;
                }
                scanner.close();
            }
        });
    }

    public static void main(String args[]){
        Mainn g = new Mainn();
        g.setContentPane(g.root);
        g.setTitle("Plus / Minus simbasket version beta");
        g.setSize(400,400);
        g.setVisible(true);
        JPanel fields = new JPanel(new GridLayout(2, 1));
        JTextField field = new JTextField(10);
        fields.add(field);
        int result = JOptionPane.showConfirmDialog(null, fields, "Write a code", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (result) {
            case JOptionPane.OK_OPTION:
                String h = field.getText();
                if(h.equals("WielkiePortlandTrailBlazers"))
                {
                    
                }
                else
                {
                    System.exit(1);
                }
                break;
            case JOptionPane.CANCEL_OPTION:
                System.exit(1);
                break;
        }
    }
}