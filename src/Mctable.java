import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class Mctable extends JDialog
{
	private static final long serialVersionUID = 1L;
	JPanel p=new JPanel();
	JLabel l1=new JLabel("����");
	JTextField tf1=new JTextField();
	JLabel l2=new JLabel("����");
	JTextField tf2=new JTextField();
	JLabel l3=new JLabel("���� ���� ,...");
	JTextField tf3=new JTextField();
	JLabel l4=new JLabel("����");
	JTextField tf4=new JTextField();
	JButton b=new JButton("ִ   ��");
	
	Mctable(JFrame f) 
	{
		super(f,false);
		p.setLayout(null);
		p.add(l1);
		p.add(tf1);
		p.add(l2);
		p.add(tf2);
		p.add(l3);
		p.add(tf3);;
		p.add(l4);
		p.add(tf4);
		p.add(b);
		
		b.addActionListener(new bProc());
		l1.setBounds(40, 20, 100, 30);
		tf1.setBounds(140,20, 260, 30);
		l2.setBounds(40, 70, 100, 30);
		tf2.setBounds(140,70, 260, 30);
		l3.setBounds(40, 120, 100, 30);
		tf3.setBounds(140,120, 260, 30);;
		l4.setBounds(40, 170, 100, 30);
		tf4.setBounds(140,170, 260, 30);
		b.setBounds(200,210, 100, 30);
		this.add(p);
		
		//����һЩ����
		b.setBackground(Color.WHITE);
				
		this.setSize(500,300);
		//��������������Ļ�м�
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = this.getSize();
		int x = (screenSize.width - size.width) / 2;
		int y = (screenSize.height - size.height) / 2;
		this.setLocation( x, y );	
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); 
		this.setModal(true);
		this.setVisible(true);	
	}
	class bProc implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(e.getSource()==b)
			{
				String sql ="create table ";
				if(tf1.getText().equals("")||tf2.getText().equals(""))
				{
					return;
				}
				sql=sql+tf1.getText()+"."+tf2.getText()+" ( "+tf3.getText();
				
				if(!tf4.getText().equals(""))
				{
					sql=sql+", PRIMARY KEY("+tf4.getText()+")";
				}
				sql=sql+" )";
				
				String error=null;
				try 
				{
					Statement stmt=Mcdb.conn.createStatement();
					stmt.executeUpdate(sql);
					stmt.close();
					dispose();//�����ɹ��͹رմ���
				}
				catch (SQLException e1) 
				{
					error=e1.getMessage();
					e1.printStackTrace();
				}
				Fmain.fdi.setText(sql,error);
				Fmain.fdb.addtree();
			}
		}
		
	}
}

