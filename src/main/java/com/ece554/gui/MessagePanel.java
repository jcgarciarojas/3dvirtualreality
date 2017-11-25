package com.ece554.gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.ece554.schedule.HelpCommand;
import com.ece554.schedule.HelpProcess;
import com.ece554.schedule.Scheduler;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */

public class MessagePanel extends JPanel implements ActionListener {
	 public static final long serialVersionUID =0;
	 
	 public static MessagePanel MESSAGE_PANEL; 
	
	//private JTextArea textArea;
	private JTextField textField;
	private JTextPane textPane = null;
	private JScrollPane paneScrollPane = null; 
	private StyledDocument doc = null;
	
	public static final String DAILY_EVENT ="daily";
	public static final String IDLE_EVENT ="idle";
	public static final String MOUSE_EVENT ="mouse";
	public static final String HELP_EVENT ="help";

	/**
	 * 
	 */
	public MessagePanel() {
		super();
		addComponents();
		MESSAGE_PANEL = this;
	}
	/**
	 * 
	 */
	private void addComponents() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		textPane = createTextPane();
		textPane.setEditable(false);
        paneScrollPane = new JScrollPane(textPane);
        paneScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        paneScrollPane.setPreferredSize(new Dimension(250, 400));
        paneScrollPane.setMinimumSize(new Dimension(10, 10));

        //JScrollPane areaScrollPane = new JScrollPane(textArea);
        //areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //areaScrollPane.setPreferredSize(new Dimension(250, 400));
        /*areaScrollPane.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Sentence "),
                                BorderFactory.createEmptyBorder(5,5,5,5)),
                areaScrollPane.getBorder()));*/
        JPanel textPanel = new JPanel(); 
        textPanel.add(paneScrollPane);

		textField = new JTextField(15);
        textField.setActionCommand("enter");
        textField.addActionListener(this);
		//textField.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.CYAN));

        panel.add(textPanel, BorderLayout.CENTER);
        panel.add(textField, BorderLayout.SOUTH);
        this.add(panel);
	}

	/**
	 * 
	 */
    public synchronized void actionPerformed(ActionEvent e) {
    	if ("enter".equals(e.getActionCommand())) {
 		    Scheduler.getInstance().addHelpCommand(new HelpCommand(new HelpProcess(this, textField.getText())));
    		textField.setText("");
    	}
    }
    
    public void setMessage(String msgType, String m) {
    	try {
	        doc.insertString(doc.getLength(), m+"\n", doc.getStyle(msgType));
	        doc.insertString(doc.getLength(), "-------------------------------------------------------\n", doc.getStyle(msgType));
	        textPane.selectAll();
	        int x = textPane.getSelectionEnd();
	        textPane.select(x,x);
	        
	        VirtualWorld.canvas3D.requestFocus();
    	} catch(Exception dbe) {
    		dbe.printStackTrace();
    	}
    	//textArea.append(m);
    	//textArea.append("\n");
    }
    
    protected JTextPane createTextPane() {
    	
    	 JTextPane textPane = new JTextPane();
    	 textPane.setBackground(Color.DARK_GRAY);
         doc = textPane.getStyledDocument();
         addStylesToDocument(doc);
         return textPane;
    }

    protected void addStylesToDocument(StyledDocument doc) {

    	Style def = StyleContext.getDefaultStyleContext().
    	getStyle(StyleContext.DEFAULT_STYLE);

    	Style daily = doc.addStyle(DAILY_EVENT, def);
        StyleConstants.setItalic(daily, true);
    	StyleConstants.setFontFamily(daily, "SansSerif");
    	StyleConstants.setFontSize(daily, 12);
        StyleConstants.setForeground(daily, Color.GREEN); 	
    	
    	Style idle = doc.addStyle(IDLE_EVENT, daily);
    	StyleConstants.setFontFamily(idle, "SansSerif");
    	StyleConstants.setFontSize(idle, 12);
        StyleConstants.setForeground(idle, Color.RED); 	

    	Style mouse = doc.addStyle(MOUSE_EVENT, idle);
    	StyleConstants.setFontFamily(mouse, "SansSerif");
    	StyleConstants.setFontSize(mouse, 12);
        StyleConstants.setForeground(mouse, Color.CYAN); 	

    	Style msg = doc.addStyle(HELP_EVENT, mouse);
    	StyleConstants.setFontFamily(msg, "SansSerif");
        StyleConstants.setForeground(msg, Color.ORANGE); 	
    	StyleConstants.setFontSize(msg, 12);

    }
}
