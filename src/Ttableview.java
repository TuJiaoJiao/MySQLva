import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

class Ttableview extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	//�������ݿ����õı�������Ҫ��ֵ
	static Connection conn;//�踳ֵ
	String databasename;//�踳ֵ
	String tablename;//�踳ֵ
	//һЩȫ�ֱ���
	int tablecnum;//�����еĸ���
	String tablecname[];//�����е�����
	//��ֵ��ʾ�����������õĿ�ֵ��ʾ���ڱ����ʾ��ʱ�򣬺�""���ַ������ֿ���������ʾ�����ӣ��޸ģ���ѯ�����ж��Ὣ���ַ�����Ϊ��ֵ����
	static String kongzhi="Null(1)";
	Object oldvalue[][];//���汻�޸�ǰ�ľ�ֵ������ȷ��Ψһ���޸Ķ���ע�⣬ÿ�ű���Ӧһ����ֵ����������ʱ��ᱻͬ��ˢ�¡�
	//����������õ����
	JPanel panel=new JPanel();
	JScrollPane spanel=new JScrollPane();	
	//�˴�����������filltable()�����г�ʼ��
	DefaultTableModel tablemodel;	
	JTable table;
	JTable table2;//�������
	
	JPanel buttonpanel=new JPanel();
	JButton add=new JButton("����");
	JButton delete=new JButton("ɾ��");
	JButton select=new JButton("����");
	JButton refresh=new JButton("ˢ��");
	
	Ttableview(String dname,String tname)
	{	
		databasename=dname;
		tablename=tname;
		if(conn==null)
			return;
		
		filltable();
		table.setFont(new Font("΢���ź�",0,13));
		table2.setFont(new Font("΢���ź�",0,13));
		spanel.setViewportView(table);
		panel.setLayout(new BorderLayout());
		panel.add(spanel);
		panel.add(table2,BorderLayout.SOUTH);
		
		add.addActionListener(new buttonProc());
		delete.addActionListener(new buttonProc());
		select.addActionListener(new buttonProc());
		refresh.addActionListener(new buttonProc());
		buttonpanel.add(add);
		buttonpanel.add(delete);
		buttonpanel.add(select);
		buttonpanel.add(refresh);
		
		this.setLayout(new BorderLayout());
		this.add(panel);
		this.add(buttonpanel,BorderLayout.SOUTH);
		//����һЩ��ɫ����������	
		add.setBackground(Color.WHITE);//new Color(245,245,245));
		delete.setBackground(Color.WHITE);
		select.setBackground(Color.WHITE);
		refresh.setBackground(Color.WHITE);
	}
	//=======================================================================================================��ʼ�����
	void filltable()
	{
		try 
		{
			Statement stmt= conn.createStatement();
			String sql="select column_name from information_schema.columns where table_name='"+tablename+"'and table_schema ='"+databasename+"'";
			ResultSet rs= stmt.executeQuery(sql);
			
			while(rs.next())
			{
				tablecnum++;
			}
			tablecname=new String[tablecnum];//��ŵ�ǰ�������
			table2=new JTable(1,tablecnum);
			oldvalue=new Object[100][tablecnum];//���Ϊ100�У�����һ��������飬���ž�ֵ
			
			rs=stmt.executeQuery(sql);//��Ϊrs.next()ͳ�Ƹ��������³�ʼ��			
			for(int j=0;j<tablecnum;j++)
			{
				rs.next();
				tablecname[j]=new String(rs.getString("column_name"));	
			}
			
			tablemodel=new DefaultTableModel(null,tablecname);
			table=new JTable(tablemodel)
					{
						private static final long serialVersionUID = 1L;
						//Implement table cell tool tips.��������ʾ
			            public String getToolTipText(MouseEvent e) 
			            {
			                String tip = null;
			                java.awt.Point p = e.getPoint();
			                int rowIndex = rowAtPoint(p);
			                int colIndex = columnAtPoint(p);
			                tip=(String) this.getValueAt(rowIndex,colIndex);
			                return tip;
			            }

						//Implement table header tool tips. ���ӱ�ͷ��ʾ
						protected JTableHeader createDefaultTableHeader() 
						{
							return new JTableHeader(columnModel) 
							{
								private static final long serialVersionUID = 1L;
								public String getToolTipText(MouseEvent e) 
								{
									java.awt.Point p = e.getPoint();
									int index = columnModel.getColumnIndexAtX(p.x);
									int realIndex = columnModel.getColumn(index).getModelIndex();
									return tablecname[realIndex];
								}
							};
						}
					};
			//����һЩ�������
			table.getTableHeader().setReorderingAllowed(false);//��ֹ�϶���ͷ���򣬲�Ȼ������޸Ĵ���
			table2.setSelectionBackground(new Color(100,200,200));
			//����Ŀǰ���룬��������Ѿ���ʼ����ϣ�������tablecnum��
			//��ʼ�����������
			String sql2="select * from "+databasename+"."+tablename+" limit 0,100";
			//System.out.println(sql2);
			rs=stmt.executeQuery(sql2);	
			Object rowvalue[]=new Object[tablecnum];
			
			int i=0;
			while(rs.next())
			{		
				for(int j=0;j<tablecnum;j++)
				{
					rowvalue[j]=rs.getString(tablecname[j]);
					if(rowvalue[j]==null)
						rowvalue[j]=kongzhi;
					
					oldvalue[i][j]=rowvalue[j];//�浽�������
				}
				i++;
				tablemodel.addRow(rowvalue);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		tablemodel.addTableModelListener(new tableProc());
	}
//================================================================================================================����ˢ��
	void shuaxintable()//ˢ�±��
	{
		//������ͼ�������������ȡ���༭״̬���ᴥ���޸�������ɳ���ʧ�ܣ�Ӧ����ĳ������֮ǰȡ���༭״̬
		try 
		{
			Statement stmt= conn.createStatement();
			tablemodel.setRowCount(0);//�����
					
			String sql="select * from "+databasename+"."+tablename+" limit 0,100";	
			ResultSet rs=stmt.executeQuery(sql);		
			Object rowvalue[]=new Object[tablecnum];		
			int i=0;
			while(rs.next())
			{		
				for(int j=0;j<tablecnum;j++)
				{
					rowvalue[j]=rs.getString(tablecname[j]);
					if(rowvalue[j]==null)
						rowvalue[j]=kongzhi;
					
					oldvalue[i][j]=rowvalue[j];//�浽�������
				}
				i++;
				tablemodel.addRow(rowvalue);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}		
	}
//================================================================================================================�޸Ĺ���
	class tableProc implements TableModelListener
	{
		public void tableChanged(TableModelEvent e)
		{
			if(e.getType()==TableModelEvent.UPDATE)
			{
				int hang=table.getSelectedRow();//��
				int lie=table.getSelectedColumn();//��
				if(hang<0)//��������ж��Ǳ�Ҫ��
					return;	
				String sql=null;
				int i=0;
				String error=null;
				try 
				{
					Statement stmt= conn.createStatement();	
					Object value;
					value=table.getValueAt(hang,lie);
					if(value.equals(oldvalue[hang][lie]))
						return;
					
					if(kongzhi.equals(value))
						sql="update "+databasename+"."+tablename+" set "+tablecname[lie]+" = null where ";
					else
						sql="update "+databasename+"."+tablename+" set "+tablecname[lie]+" = '"+value+"' where ";
						
					value=null;//��ǵڼ���,�˴�������null���б𣬶����ӹ����в�����
					for(int j=0;j<tablecnum;j++)
					{
						if(value!=null)
								sql=sql+" and ";
						value=table.getValueAt(hang,j);
						if(j==lie)
								value=oldvalue[hang][lie];
						if(kongzhi.equals(value))
							sql=sql+table.getColumnName(j)+" is null ";
						else
							sql=sql+table.getColumnName(j)+" = '"+value+"' ";	
					}
					//System.out.println(sql);//���sql����Ƿ���ȷ
					i=stmt.executeUpdate(sql);
					//�˴�֮����䣬��stmt.executeUpdate(sql);�׳��쳣��ִ�в���
					stmt.close();
				} 
				catch (SQLException e1) 
				{
					error=e1.getMessage();
					e1.printStackTrace();	
				}
				
				Fmain.fdi.setText(sql,i,error);
				shuaxintable();//ˢ�µ�ǰ���ĵı���ֹ�޸�ʧ��
			}
		}
	}
	
	class buttonProc implements ActionListener 
	{
		public void actionPerformed(ActionEvent e)
		{
//================================================================================================================���ӹ���
			if(e.getSource()==add)
			{	
				new Addbatch();
			}
//================================================================================================================ɾ������
			if(e.getSource()==delete)
			{
				int hang[]=table.getSelectedRows();
				if(hang.length<=0)
					return;
				
				int n=JOptionPane.showConfirmDialog(null, "ȷ��ɾ����?", "ɾ��", JOptionPane.YES_NO_OPTION);  
		        if (n!=JOptionPane.YES_OPTION)  
		            return; 
		        
				if(table.isEditing())
					table.getCellEditor().stopCellEditing();//���Ҫɾ����Ŀ�ı����ڱ༭״̬����ֹͣ�༭״̬
				//����ɾ��
				for(int which=0;which<hang.length;which++)
				{
					String sql="delete from "+databasename+"."+tablename+"  where ";
					Object value=null;
					for(int j=0;j<tablecnum;j++)
					{
						if(value!=null)
						{
							sql=sql+" and ";
						}
						value=table.getValueAt(hang[which],j);
						if(kongzhi.equals(value))
							sql=sql+table.getColumnName(j)+" is null ";
						else
							sql=sql+table.getColumnName(j)+" = '"+table.getValueAt(hang[which],j)+"' ";
					}
					//System.out.println(sql);//���sql����Ƿ���ȷ
					int i=0;
					String error=null;
					try 
					{
						Statement stmt= conn.createStatement();
						i=stmt.executeUpdate(sql);
						stmt.close();
					}
					catch (SQLException e1) 
					{
						error=e1.getMessage();
						e1.printStackTrace();
					}
					Fmain.fdi.setText(sql,i,error);
				}
				shuaxintable();
			}
//================================================================================================================���ҹ���
			if(e.getSource()==select)
			{
				try
				{
					if(table.isEditing())
						table.getCellEditor().stopCellEditing();//���Ҫ������Ŀ�ı����ڱ༭״̬����ֹͣ�༭״̬
					if(table2.isEditing())
						table2.getCellEditor().stopCellEditing();//���Ҫ������Ŀ�ı����ڱ༭״̬����ֹͣ�༭״̬
					
					tablemodel.setRowCount(0);//�����
					
					String sql="select * from "+databasename+"."+tablename;
					String value=null;
					for(int j=0;j<tablecnum;j++)
					{
						if(table2.getValueAt(0,j)!=null&&!table2.getValueAt(0,j).equals(""))//���Կ�ֵ����ַ������в���
						{
							if(value==null)
								sql=sql+" where ";
							else
								sql=sql+" and ";
							value=(String)table2.getValueAt(0,j);
							if(kongzhi.equals(value))
								sql=sql+table.getColumnName(j)+" is null ";
							else
								sql=sql+table.getColumnName(j)+" = '"+value+"' ";
						}
					}
					sql=sql+" limit 0,100";
					//System.out.println(sql);//���sql����Ƿ���ȷ
					Statement stmt= conn.createStatement();
					ResultSet rs=stmt.executeQuery(sql);
					Object rowvalue[]=new Object[tablecnum];	
					int i=0;
					while(rs.next())
					{		
						for(int j=0;j<tablecnum;j++)
						{
							rowvalue[j]=rs.getString(tablecname[j]);
							if(rowvalue[j]==null)
								rowvalue[j]=kongzhi;
							
							oldvalue[i][j]=rowvalue[j];//�浽�������
						}
						i++;
						tablemodel.addRow(rowvalue);
					}
					rs.close();
					stmt.close();
				}
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
//================================================================================================================ˢ�¹���
			if(e.getSource()==refresh)
			{
				if(table.isEditing())
					table.getCellEditor().stopCellEditing();//���Ҫˢ�µı����ڱ༭״̬����ֹͣ�༭״̬
				if(table2.isEditing())
					table2.getCellEditor().stopCellEditing();//���Ҫˢ�µı����ڱ༭״̬����ֹͣ�༭״̬	
				table.clearSelection();
				table2.clearSelection();
				shuaxintable();
			}
		}
	}
	//���ӹ��ܵĵ���
	class Addbatch extends JDialog
	{
		private static final long serialVersionUID = 1L;
		
		JLabel l=new JLabel();
		
		JScrollPane sp=new JScrollPane();
		Object data[][]=new Object[20][tablecnum];
		DefaultTableModel addmodel=new DefaultTableModel(data,tablecname);
		JTable add=new JTable(addmodel);
		
		JPanel p=new JPanel();
		JButton	b1=new JButton("ȫ   ѡ");
		JButton	b2=new JButton("��   ��");
		JButton	b3=new JButton("��չʮ��");
		JLabel	b4=new JLabel("<HTML><U>����...</U></HTML>");
		
		Addbatch() 
		{
			super(Fmain.f,false);
			
			l.setText("ѡ��Ҫ���ӵ���,�ɶ�ѡ");
			sp.setViewportView(add);
			b1.addActionListener(new bProc());
			b2.addActionListener(new bProc());
			b3.addActionListener(new bProc());
			b4.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent arg0)
				{
					if(add.isEditing())
						add.getCellEditor().stopCellEditing();//���Ҫˢ�µı����ڱ༭״̬����ֹͣ�༭״̬	
					add.clearSelection();
					addmodel.setRowCount(0);
					addmodel.setRowCount(20);
				}
			});
			p.add(b2);
			p.add(b1);
			p.add(b3);
			p.add(b4);
			this.setLayout(new BorderLayout());
			this.add(l,BorderLayout.NORTH);
			this.add(sp);
			this.add(p,BorderLayout.SOUTH);
			
			//����һЩ����
			b1.setBackground(Color.WHITE);
			b2.setBackground(Color.WHITE);
			b3.setBackground(Color.WHITE);
			add.getTableHeader().setReorderingAllowed(false);//��ֹ�϶���ͷ���򣬲�Ȼ������޸Ĵ���
			
			this.setSize(800,500);
			//��������������Ļ�м�
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension size = this.getSize();
			int x = (screenSize.width - size.width) / 2;
			int y = (screenSize.height - size.height) / 2;
			this.setLocation( x+50, y+100);	
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); 
			this.setModal(true);
			this.setVisible(true);	
		}
		class bProc implements ActionListener
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(e.getSource()==b1)
				{
					add.selectAll();
				}
				if(e.getSource()==b2)
				{
					int hang[]=add.getSelectedRows();
					if(hang.length<=0)
						return;
					if(hang.length==1)
					{
						int n=JOptionPane.showConfirmDialog(null, "ֻѡ����һ�У�����ִ����", "��ʾ", JOptionPane.YES_NO_OPTION);  
						if (n!=JOptionPane.YES_OPTION)  
							return; 
					}
					if(table.isEditing())
					{
						table.getCellEditor().stopCellEditing();//���Ҫ������Ŀ�ı����ڱ༭״̬����ֹͣ�༭״̬
					}
					if(add.isEditing())
					{
						add.getCellEditor().stopCellEditing();//���Ҫ������Ŀ�ı����ڱ༭״̬����ֹͣ�༭״̬
					}
					//��������
					String sql="insert into "+databasename+"."+tablename+"  values(";
					int cishu1=0;//��ǵڼ���
					for(int which=0;which<hang.length;which++)
					{
						if(cishu1!=0)//�Ӷ���
							sql=sql+",(";
						Object value=null;
						int cishu2=0;//��ǵڼ���
						for(int j=0;j<tablecnum;j++)
						{
							if(cishu2!=0)//�Ӷ���
								sql=sql+" , ";
							value=add.getValueAt(hang[which],j);
							if(kongzhi.equals(value))//����null��ֵ
								sql=sql+"null";	
							else
							{
								if(value==null)//�������Ĭ�ϵ�null(��ֵ)��Ϊ''(�հ��ַ���)
									sql=sql+"''";
								else
									sql=sql+"'"+value+"'";		
							}
							cishu2++;
						}
						sql=sql+")";
						cishu1++;
						//System.out.println(sql);//���sql����Ƿ���ȷ
					}
					int i=0;
					String error=null;
					try
					{	
						Statement stmt= conn.createStatement();
						i=stmt.executeUpdate(sql);
						stmt.close();
					}
					catch (SQLException e1)
					{
						error=e1.getMessage();
						e1.printStackTrace();
					}
					Fmain.fdi.setText(sql,i,error);
					shuaxintable();
				}
				if(e.getSource()==b3)
				{
					int i=addmodel.getRowCount();
					addmodel.setRowCount(i+10);
				}
			}
		}
	}
}
