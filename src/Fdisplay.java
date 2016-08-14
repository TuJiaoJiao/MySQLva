import java.awt.Color;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

class Fdisplay extends JScrollPane
{
	private static final long serialVersionUID = 1L;
	
	JTextPane textpane=new JTextPane();
	SimpleAttributeSet attrset = new SimpleAttributeSet();
	
	Fdisplay()
	{
		textpane.setEditable(false);
		textpane.setFont(new Font("΢���ź�",0,13));
		this.setViewportView(textpane);
	}
	
	void setText(String sql,String error)
	{
		if(sql==null)
			return;
		
		Document docs = textpane.getDocument();//����ı�����
	    try 
	    {
	    	 docs.insertString(docs.getLength(),sql+"\r\n",null);//���ı�����׷��
	    	 if(error==null)
	    	 {
		    	 docs.insertString(docs.getLength(),"ִ�гɹ���\r\n",null);
	    	 }
	    	 else
	    	 {
	    		 StyleConstants.setForeground(attrset, Color.red);
		    	 docs.insertString(docs.getLength(),"����"+error+"\r\n",attrset);
	    	 } 
		} catch (BadLocationException e) {e.printStackTrace();}
	}
	void setText(String sql,int i,String error)
	{
		if(sql==null)
			return;
		
		Document docs = textpane.getDocument();//����ı�����
	    try 
	    {
	    	docs.insertString(docs.getLength(),sql+"\r\n",null);//���ı�����׷��
	    	docs.insertString(docs.getLength(),"��Ӱ��������"+i+"\r\n",null);
	    	if(error==null)
	    	{
		    	docs.insertString(docs.getLength(),"ִ�гɹ���\r\n",null);
	    	}
	    	else
	    	{
	    		StyleConstants.setForeground(attrset, Color.red);
		    	docs.insertString(docs.getLength(),"����"+error+"\r\n",attrset);
	    	} 
		} catch (BadLocationException e) {e.printStackTrace();}
	}
	void setText(String sql[],int error)
	{
		if(sql==null)
			return;
		
		Document docs = textpane.getDocument();//����ı�����
	    try 
	    {
	    	if(error==-1)
			{
	    		StyleConstants.setForeground(attrset, Color.PINK);
				docs.insertString(docs.getLength(),"����ʧ�ܣ��ѻع����ع��ɹ�\r\n",attrset);//���ı�����׷��
				return;
			}
	    	if(error==-2)
			{
	    		StyleConstants.setForeground(attrset, Color.red);
				docs.insertString(docs.getLength(),"����ʧ�ܣ��ѻع����ع�ʧ��\r\n",attrset);//���ı�����׷��
				return;
			}
	    	for(int i=0;i<sql.length;i++)
	    	{
	    		docs.insertString(docs.getLength(),sql[i]+"\r\n",null);//���ı�����׷��
	    	}
	    	docs.insertString(docs.getLength(),"ȫ��ִ�гɹ���\r\n",null);//���ı�����׷��
		} catch (BadLocationException e) {e.printStackTrace();}
	}
}
