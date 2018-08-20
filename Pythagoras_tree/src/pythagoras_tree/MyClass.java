package pythagoras_tree;



import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

public class MyClass {
    public static int mode=1;
    public static JRadioButton mode1=new JRadioButton("3:4:5",true);
    public static JRadioButton mode2=new JRadioButton("5:12:13",false);
    public static JButton btn=new JButton("ENTER");
    public static JButton save=new JButton("SAVE");
    public static paper PAPER;
    public static void main(String[] arg){
        paper.initial();
        JFrame windows = new JFrame("Jwindows");
        windows.setLayout(new GridBagLayout());
        //////////////////////////////////////////////////////
        ButtonGroup group=new ButtonGroup();
        group.add(mode1);
        group.add(mode2);
        JPanel control = new JPanel();// 宣告一個JPanel來布置畫面
        control.setLayout(new GridLayout(1, 4, 10, 10));// 設置扁面配置3列4欄
        control.add(mode1);
        control.add(mode2);
        control.add(btn);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("click");
                if(mode1.isSelected()){
                    mode=1;
                }else{
                    mode=2;
                }
                PAPER.level=1;
                paper.initial();
                PAPER.repaint();
//                    PAPER.removeAll();

//                System.out.println(mode1.isSelected());
            }
        });
        control.add(save);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("save");
                save();
            }
        });
        control.setBorder(new TitledBorder("control"));
       // control.setPreferredSize(new Dimension(500, 300));
        //////////////////////////////////////////////////////
        PAPER=new paper();
        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 0;
        c2.gridy = 1;
        windows.add(control,c2);
        GridBagConstraints c3 = new GridBagConstraints();
        c3.gridx = 0;
        c3.gridy = 2;
        windows.add(PAPER,c3);

        windows.pack();
        windows.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        windows.setLocationRelativeTo(null);
        windows.setVisible(true);

        windows.addMouseMotionListener(PAPER);
        windows.addMouseListener(PAPER);
    }
    public static void save(){
        BufferedImage image = new BufferedImage(PAPER.getWidth(), PAPER.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        PAPER.paint(g);
        try {
            ImageIO.write(image, "png", new File("MY_TREE.png"));
        } catch (IOException ex) {
            //Logger.getLogger(CustomApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
