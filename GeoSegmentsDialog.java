package homework1;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 * A JDailog GUI for choosing a GeoSegemnt and adding it to the route shown
 * by RoutDirectionGUI.
 * <p>
 * A figure showing this GUI can be found in homework assignment #1.
 */
public class GeoSegmentsDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	// the RouteDirectionsGUI that this JDialog was opened from
	private RouteFormatterGUI parent;
	
	// a control contained in this 
	private JList<GeoSegment> lstSegments;
	
	// the endpoint of the most resently selcted segment. 
	private GeoPoint endOfLastSelectedSegment;
	
	/**
	 * Creates a new GeoSegmentsDialog JDialog.
	 * @effects Creates a new GeoSegmentsDialog JDialog with owner-frame
	 * 			owner and parent pnlParent
	 */
	public GeoSegmentsDialog(Frame owner, RouteFormatterGUI pnlParent) {
		// create a modal JDialog with the an owner Frame (a modal window
		// in one that doesn't allow other windows to be active at the
		// same time).
		super(owner, "Please choose a GeoSegment", true);
		
		this.parent = pnlParent;
		
		// create components
		lstSegments = new JList<>(ExampleGeoSegments.segments);
		lstSegments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrlSegments = new JScrollPane(lstSegments);
		scrlSegments.setPreferredSize(new Dimension(450, 100));

		JLabel lblSegments = new JLabel("GeoSegments:");
		lblSegments.setLabelFor(lstSegments);
		
		JButton btnCancle = new JButton("Cancle");
		btnCancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeoSegment selectedSegment = lstSegments.getSelectedValue();
				if (endOfLastSelectedSegment == null || 
					endOfLastSelectedSegment.equals(selectedSegment.getP1())) {
					endOfLastSelectedSegment = selectedSegment.getP2();
					parent.addSegment(selectedSegment);
				}
			}
		});
		
		// arrange components on grid	
	    JPanel panel = new JPanel();
	    
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
	    panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
	    panel.setLayout(gridbag); 

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(0,0,0,0);
		gridbag.setConstraints(lblSegments, c);
		panel.add(lblSegments);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.insets = new Insets(0,0,0,0);
		gridbag.setConstraints(scrlSegments, c);
	    panel.add(scrlSegments);
	    
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(0,20,0,0);
		gridbag.setConstraints(btnAdd, c);
	    panel.add(btnAdd);
	    
		c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(0,20,0,0);
		gridbag.setConstraints(btnCancle, c);
	    panel.add(btnCancle);
	    
		getContentPane().add(panel);
	
	}
}
