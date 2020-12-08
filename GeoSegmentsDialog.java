package homework1;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
	
	// the endpoint of the most recently added segment. 
	private GeoPoint endOfLastAddedSegment;
	
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
		
		// create components.
		lstSegments = new JList<>(ExampleGeoSegments.segments);
		lstSegments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrlSegments = new JScrollPane(lstSegments);

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
				if (endOfLastAddedSegment == null || 
					endOfLastAddedSegment.equals(selectedSegment.getP1())) {
					endOfLastAddedSegment = selectedSegment.getP2();
					parent.addSegment(selectedSegment);
				}
			}
		});
	    
		// layout components.
		scrlSegments.setPreferredSize(new Dimension(450, 150));
		scrlSegments.setAlignmentX(LEFT_ALIGNMENT);
		
		// lay out the label and scroll pane from top to bottom.
		JPanel listPane = new JPanel();
		listPane.setLayout(new BoxLayout(listPane, BoxLayout.Y_AXIS));
		listPane.add(lblSegments);
		listPane.add(scrlSegments);
		listPane.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	    
		// lay out the buttons.
	    JPanel buttonPane = new JPanel();
	    buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
	    buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
	    buttonPane.add(btnAdd);
	    buttonPane.add(Box.createHorizontalGlue());
	    buttonPane.add(btnCancle);
	    
	    // put everything together, using the content pane's BorderLayout.
	    Container contentPane = getContentPane();
	    contentPane.add(listPane, BorderLayout.CENTER);
	    contentPane.add(buttonPane, BorderLayout.SOUTH);
	}
}
