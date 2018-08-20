package bezierCurve;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class show {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame windows = new JFrame("Bezier Curve");
		windows.setLayout(new BorderLayout());
        paper PAPER=new paper();
//        JButton btn=new JButton("CLEAR");
//        btn.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				PAPER.reset();
//			}
//		});
//        windows.add(btn,BorderLayout.NORTH);
        windows.add(PAPER,BorderLayout.CENTER);
        
        
        windows.pack();
        windows.setSize(PAPER.windowsWidth,PAPER.windowsHeight);
        windows.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        windows.setLocationRelativeTo(null);
        windows.setVisible(true);
        windows.addMouseListener(PAPER);
        windows.addMouseMotionListener(PAPER);
        
	}

}
