import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

class Fmenu extends JMenuBar
{
	private static final long serialVersionUID = 1L;
	
	JMenu m1=new JMenu("�� ��     ");
	JMenu m2=new JMenu("ִ ��     ");
	JMenu m3=new JMenu("�� ��     ");
	
	JMenuItem m11=new JMenuItem(" + �� �� ");
	JMenuItem m12=new JMenuItem(" + �� �� ");
	JMenuItem m21=new JMenuItem(" ִ �� �� �� ");
	JMenuItem m31=new JMenuItem(" �� �� �� ֵ �� ʾ ");
	JMenuItem m32=new JMenuItem(" �� �� ҳ �� �� �� �� ");
	
	Fmenu()
	{
		m11.addActionListener(new itemProc());
		m12.addActionListener(new itemProc());
		m1.add(m11);
		m1.add(m12);
		
		m21.addActionListener(new itemProc());
		m2.add(m21);
		
		m31.addActionListener(new itemProc());
		m32.addActionListener(new itemProc());
		m3.add(m31);
		m3.add(m32);
		
		this.add(m1);
		this.add(m2);	
		this.add(m3);
	}
	class itemProc implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(e.getSource()==m11)
			{
				new Mcdb(Fmain.f);
				return;
			}
			if(e.getSource()==m12)
			{
				new Mctable(Fmain.f);
				return;
			}
			if(e.getSource()==m21)
			{
				new Msql(Fmain.f);
				return;
			}
			if(e.getSource()==m31)
			{
				new Mnull(Fmain.f);
				return;
			}
			if(e.getSource()==m32)
			{
				Fmain.fdb.addtree();
				Fmain.ft.removeAll();
				Fmain.ft.add(Fmain.ft.ta,"Welcome");
				Fmain.fdi.textpane.setText(null);
				Ttableview.kongzhi="Null(1)";
			}
		}	
	}
}
