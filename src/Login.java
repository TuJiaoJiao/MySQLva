import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.*;

public class Login 
{
	static String lookandfeel="javax.swing.plaf.nimbus.NimbusLookAndFeel";
	//�������ݿ�ı���
	static String url=new String();
	String name=new String();
	String password=new String();
	String IP=new String();
	//��½����Ҫ����� 
	static JFrame fdenglu;
	bJPanel jp0;
	JPanel jp1;
	JPanel jp2;
	JPanel jp3;
	JPanel jp4;
	JLabel label1;
	JLabel label2;
	JLabel label3;
	JTextField namef;
	JPasswordField passwordf;
	JTextField IPf;
	JButton enter;
			
	public Login()
	{
		try 
		{
			UIManager.setLookAndFeel(lookandfeel);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException| UnsupportedLookAndFeelException e) {e.printStackTrace();}
		
		fdenglu=new JFrame("MySQLva ��¼");
		jp0=new bJPanel();
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		jp4=new JPanel();
		label1=new JLabel("��  ��");
		label2=new JLabel("��  ��");
		label3=new JLabel("��  ַ");
		namef=new JTextField(20);
		passwordf=new JPasswordField(20);
		IPf=new JTextField(20);
		enter=new JButton("��   ¼");
		enter.addActionListener(new enterProc());
		
		jp1.add(label1);
		jp1.add(namef);
		jp2.add(label2);
		jp2.add(passwordf);
		jp3.add(label3);
		jp3.add(IPf);
		jp4.setLayout(null);
		jp4.add(enter);
		enter.setBounds(200,5,100,35);
		
		fdenglu.setLayout(null);
		fdenglu.add(jp0);
		fdenglu.add(jp1);
		fdenglu.add(jp2);
		fdenglu.add(jp3);
		fdenglu.add(jp4);
		jp0.setBounds(0,0,500,160);
		jp3.setBounds(0,160,500,50);
		jp1.setBounds(0,210,500,50);
		jp2.setBounds(0,260,500,50);
		jp4.setBounds(0,310,500,90);
		//����һЩ��ɫ����������	
		IPf.setText("localhost:3306");
		jp1.setBackground(new Color(235,235,235));
		jp2.setBackground(new Color(235,235,235));
		jp3.setBackground(new Color(235,235,235));
		jp4.setBackground(new Color(235,235,235));
		label1.setFont(new Font("΢���ź�",0,15));
		label2.setFont(new Font("΢���ź�",0,15));
		label3.setFont(new Font("΢���ź�",0,15));
		namef.setFont(new Font("΢���ź�",0,15));
		passwordf.setFont(new Font("΢���ź�",0,15));
		IPf.setFont(new Font("΢���ź�",0,15));
		enter.setFont(new Font("΢���ź�",0,16));
		enter.setBackground(Color.WHITE);
		
		Image image;
		if(new File("image/1.png").exists())
			image=new ImageIcon("image/1.png").getImage();
		else
			image=new javax.swing.ImageIcon(getClass().getResource("/image/1.png")).getImage();
		fdenglu.setIconImage(image);
		
		fdenglu.setResizable(false);
		fdenglu.setSize(500,400);		
		//��������������Ļ�м�
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = fdenglu.getSize();
		int x = (screenSize.width - size.width) / 2;
		int y = (screenSize.height - size.height) / 2;
		fdenglu.setLocation( x, y );		
		fdenglu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		fdenglu.setVisible(true);
		namef.requestFocus();
	}
	static public void setconn()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("�ɹ�����Mysql��������");
			Fdblist.conn= DriverManager.getConnection(url);
			Ttableview.conn= DriverManager.getConnection(url);
			Mcdb.conn=DriverManager.getConnection(url);
			Msql.conn=DriverManager.getConnection(url);
		}
		catch(ClassNotFoundException e1)
		{
			System.out.println("�Ҳ���Mysql��������");
			e1.printStackTrace();
		} 
		catch (SQLException e1) 
		{
			e1.printStackTrace();
		}
	}
	class enterProc  implements ActionListener 
	{
		public void actionPerformed(ActionEvent e)
		{
			enter.setText("������...");
			name=namef.getText();
			password=new String(passwordf.getPassword());
			IP=IPf.getText();
			url="jdbc:mysql://"+IP+"/?user="+name+"&password="+password+"&useSSL=false&useUnicode=true&characterEncoding=UTF8&zeroDateTimeBehavior=convertToNull";
			setconn();		
			if(Fdblist.conn!=null&&Ttableview.conn!=null&&Mcdb.conn!=null&&Msql.conn!=null)
			{
				new Fmain();
			}	
			else
			{
				JOptionPane.showMessageDialog(fdenglu, "�û������������", "��¼ʧ��", JOptionPane.CLOSED_OPTION);
				enter.setText("��   ¼");
			}
		}
	}
	public static void main(String [] args)
	{
		new Login();
	}
	//��������JPanel
	class bJPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;
		
		Image image;
		bJPanel()
		{
			if(new File("image/2.png").exists())
				image=new ImageIcon("image/2.png").getImage();
			else
				image=new javax.swing.ImageIcon(getClass().getResource("/image/2.png")).getImage();
		}
		public void paintComponent(Graphics g) 
		{
			super.paintComponent(g); 
			image.getWidth(this);
			image.getHeight(this);
			g.drawImage(image,0,0,null);//����ͼƬ 
		}
	}
}
