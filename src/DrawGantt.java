import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

public class 
DrawGantt implements ActionListener
{
	
	int noOfJobs = 0;
	int orderCount = 0;
	int jobNumber [];
	int orderOfJobs [];
	double orderOfBursts [];
	double grandData [][];
	double flagTime [] = new double [99];
	double ttAve = 0.00;
	double wtAve = 0.00;
	boolean idle = false;
	
	/* Frame Elements */
	Container contentPane;
	JFrame frame 					= 	new JFrame ("CPU Scheduling Algorithm Solver: Gantt Chart");
	
	JSplitPane sectionSplit 		= 	new JSplitPane (JSplitPane.VERTICAL_SPLIT);
	JScrollPane scrollPaneGantt 	= 	new JScrollPane ();
	JScrollPane scrollPaneSolution 	= 	new JScrollPane ();
	JPanel pnlButton 				= 	new JPanel (new GridLayout (1, 1));
	JButton btnBack 			 	= 	new JButton ("Back");
	
	Font stdFont 					= 	new Font ("Courier New", Font.PLAIN, 12);
	Font stdBold 					= 	new Font ("Courier New", Font.BOLD, 14);
	
	/* Gantt Chart Elements */
	JPanel pnlCompact 				= 	new JPanel (new GridLayout (2, 1));
	JPanel pnlArrayOfBox 			= 	new JPanel ();
	JPanel pnlTime 					= 	new JPanel();
	JPanel pnlIdle 					= 	new JPanel ();
	JPanel pnlInv[] 				= 	new JPanel [99];
	JPanel pnlBox[] 				= 	new JPanel[99];
	JPanel pnlFill[] 				= 	new JPanel [2];
	JPanel pnlFill2[] 				= 	new JPanel [6];
	
	JLabel lblIdle 					= 	new JLabel ("INIT");
	JLabel lblJob[] 				= 	new JLabel [99];
	JLabel lblBurst[] 				= 	new JLabel [99];
	JLabel lblTime[] 				= 	new JLabel [99];
	
	/* Solution Table Elements */
	JPanel pnlSolution 				= 	new JPanel (new GridLayout (99, 1));
	JPanel pnlHead 					= 	new JPanel (new GridLayout (1, 6));
	JPanel pnlLine [] 				= 	new JPanel [99];
	JPanel pnlClearance 			= 	new JPanel ();
	JPanel pnlAverage 				= 	new JPanel (new GridLayout (1, 2));
	
	JLabel lblJobNo 				= 	new JLabel (" JOB ");
	JLabel lblExit 					= 	new JLabel (" EXIT TIME ");
	JLabel lblArrival 				= 	new JLabel (" ARRIVAL TIME ");
	JLabel lblTurn 					= 	new JLabel (" TURNAROUND TIME ");
	JLabel lblBurstDisp 			= 	new JLabel (" BURST TIME ");
	JLabel lblWait 					= 	new JLabel (" WAITING TIME ");
	JLabel lblTurnAve 				= 	new JLabel (" Average Turnaround Time: ");
	JLabel lblWaitAve 				= 	new JLabel ("Average Waiting Time: ");
	JLabel lblGrandData [][] 		= 	new JLabel [99][6];
	
	/**
		* Constructors
	*/
	/* Default Constructor */
	protected 
	DrawGantt () 
	{
		
	}
	
	/* FCFS, SJF, PRO, Deadline */
	protected 
	DrawGantt (double grandData [][], int jobNumber [], double ttAve, double wtAve, double flagTime [], int orderOfJobs [], int noOfJobs, boolean idle) 
	{
		this.grandData = new double [noOfJobs][ProcessConstants.GRAND_ARRAY];
		this.jobNumber = new int [noOfJobs];
		this.flagTime = flagTime;
		this.orderOfJobs = new int [noOfJobs];
		
		this.grandData = grandData;
		this.jobNumber = jobNumber;
		this.orderOfJobs = orderOfJobs;
		this.noOfJobs = noOfJobs;
		this.ttAve = ttAve;
		this.wtAve = wtAve;
		this.idle = idle;
		
		this.pnlBox 			= 	new JPanel [noOfJobs + (idle ? 3 : 2)];
		this.pnlArrayOfBox 		= 	new JPanel (new GridLayout (1, noOfJobs + (idle ? 3 : 2)));
		this.pnlTime 			= 	new JPanel (new GridLayout (1, noOfJobs + (idle ? 2 : 1)));
		this.pnlInv 			= 	new JPanel [noOfJobs + (idle ? 3 : 2)];
		this.pnlFill [0] 		= 	new JPanel (new GridLayout (1, noOfJobs + 2));
		this.pnlFill [1] 		= 	new JPanel (new GridLayout (1, noOfJobs + 1));
		this.pnlFill2 [0] 		= 	new JPanel ();
		this.pnlFill2 [1] 		= 	new JPanel ();
		this.pnlFill2 [2] 		= 	new JPanel ();
		this.pnlFill2 [3] 		= 	new JPanel ();
		this.pnlFill2 [4] 		= 	new JPanel ();
		this.pnlFill2 [5] 		= 	new JPanel ();
		this.lblJob 			= 	new JLabel [noOfJobs];
		this.lblBurst 			= 	new JLabel [noOfJobs];
		this.lblTime 			= 	new JLabel [noOfJobs + (idle ? 2 : 1)];
		
		this.lblGrandData 		= 	new JLabel [noOfJobs][6];
		
		this.pnlSolution 		= 	new JPanel (new GridLayout (noOfJobs + 3, 6));
		this.pnlLine 			= 	new JPanel [noOfJobs];
	}
	
	/* PPRIO, SRTF */
	protected 
	DrawGantt (double grandData [][], int jobNumber [], double ttAve, double wtAve, double flagTime [], double orderOfBursts [], int orderOfJobs [], int orderCount, int noOfJobs, boolean idle) 
	{
		this.grandData = new double [noOfJobs][ProcessConstants.GRAND_ARRAY];
		this.jobNumber = new int [noOfJobs];
		this.flagTime = flagTime;
		this.orderOfBursts = new double [orderCount];
		this.orderOfJobs = new int [orderCount];
		this.orderOfBursts = orderOfBursts;
		this.orderCount = orderCount;
		
		this.grandData = grandData;
		this.jobNumber = jobNumber;
		this.orderOfJobs = orderOfJobs;
		this.noOfJobs = noOfJobs;
		this.ttAve = ttAve;
		this.wtAve = wtAve;
		this.idle = idle;
		
		this.pnlBox 			= 	new JPanel [orderCount + (idle ? 3 : 2)];
		this.pnlArrayOfBox 		= 	new JPanel (new GridLayout (1, orderCount + (idle ? 3 : 2)));
		this.pnlTime 			= 	new JPanel (new GridLayout (1, orderCount + (idle ? 2 : 1)));
		this.pnlInv 			= 	new JPanel [orderCount + (idle ? 3 : 2)];
		this.pnlFill [0] 		= 	new JPanel (new GridLayout (1, orderCount + 2));
		this.pnlFill [1] 		= 	new JPanel (new GridLayout (1, orderCount + 1));
		this.pnlFill2 [0] 		= 	new JPanel ();
		this.pnlFill2 [1] 		= 	new JPanel ();
		this.pnlFill2 [2] 		= 	new JPanel ();
		this.pnlFill2 [3] 		= 	new JPanel ();
		this.pnlFill2 [4] 		= 	new JPanel ();
		this.pnlFill2 [5] 		= 	new JPanel ();
		this.lblJob 			= 	new JLabel [99];
		this.lblBurst 			= 	new JLabel [99];
		this.lblTime 			= 	new JLabel [orderCount + (idle ? 2 : 1)];
		
		this.lblGrandData 		= 	new JLabel [orderCount][6];
		
		this.pnlSolution 		= 	new JPanel (new GridLayout (orderCount + 3, 6));
		this.pnlLine 			= 	new JPanel [orderCount];
	}
	
	/* launchFrame() for non-preemptive algorithms */
	protected void 
	launchFrame () 
	{
		int count = 0;
		
		contentPane = frame.getContentPane();
		sectionSplit.setResizeWeight(0.30);
		
		pnlBox [0] = new JPanel ();
		pnlBox [0].add (pnlFill [0]);
		pnlBox [0].setBorder (BorderFactory.createEmptyBorder (5, 5, 5, 5));
		pnlArrayOfBox.add (pnlBox [0]);
		
		/* Box Area */
		/* Add IDLE box if idle is true */
		if (idle) {
			
			JPanel pnlInit 			= 	new JPanel (new GridLayout (2, 1));
			JLabel lblInit 			= 	new JLabel ("IDLE");
			JLabel lblInitBurst 	= 	new JLabel (grandData [orderOfJobs[0]][0] + "");
			lblInit.setFont (stdFont);
			lblInitBurst.setFont (stdFont);
			lblInit.setHorizontalAlignment (SwingConstants.LEFT);
			lblInitBurst.setHorizontalAlignment (SwingConstants.RIGHT);
			pnlInit.add (lblInit);
			pnlInit.add (lblInitBurst);
			pnlInit.setBorder (BorderFactory.createEtchedBorder (EtchedBorder.RAISED, Color.BLACK, Color.BLACK));
			pnlArrayOfBox.add (pnlInit);
			
		}
		
		for (count = 0; count < noOfJobs; count++) {
			
			pnlBox [count] 		= 	new JPanel ();
			
			lblJob [count] 		= 	new JLabel ("Job # " + jobNumber [orderOfJobs [count]]);
			lblBurst [count] 	= 	new JLabel (String.format ("%.2f", grandData [orderOfJobs [count]][1]));
			lblJob [count].setHorizontalAlignment(SwingConstants.LEFT);
			lblBurst [count].setHorizontalAlignment(SwingConstants.RIGHT);
			
			lblJob [count].setFont (new Font ("Courier New", Font.PLAIN, 12));
			lblBurst [count].setFont (new Font ("Courier New", Font.PLAIN, 12));
			
			pnlBox [count].setLayout (new GridLayout (2, 1));
			pnlBox [count].add (lblJob[count]);
			pnlBox [count].add (lblBurst[count]);
			pnlBox [count].setBorder (BorderFactory.createEtchedBorder (EtchedBorder.RAISED, Color.BLACK, Color.BLACK));
			
			pnlArrayOfBox.add (pnlBox [count]);
			
		}
			
		pnlBox [noOfJobs + 1] = new JPanel ();
		pnlBox [noOfJobs + 1].add (pnlFill [1]);
		pnlBox [noOfJobs + 1].setBorder (BorderFactory.createEmptyBorder (5, 5, 5, 5));
		pnlArrayOfBox.add (pnlBox [noOfJobs + 1]);
		
		/* Time Area */
		pnlTime.add (pnlFill2[0]);
		if (idle) {
			
			for (count = 1; count < noOfJobs + 3; count++) {
				
				lblTime [count - 1] 	= 	new JLabel (String.format ("%.2f", flagTime [count - 1]));
				lblTime [count - 1].setHorizontalAlignment (SwingConstants.CENTER);
				lblTime [count - 1].setFont (new Font ("Courier New", Font.PLAIN, 12));
				
				pnlInv [count] 			= 	new JPanel ();
				pnlInv [count].add (lblTime [count - 1]);
				
				pnlTime.add (pnlInv [count], BorderLayout.CENTER);
				
			}
			
		} else {
			
			for (count = 0; count < noOfJobs + 1; count++) {
				
				lblTime [count] 	= 	new JLabel (String.format ("%.2f", flagTime [count]));
				lblTime [count].setHorizontalAlignment (SwingConstants.CENTER);
				lblTime [count].setFont (new Font ("Courier New", Font.PLAIN, 12));
				
				pnlInv [count] 		= 	new JPanel ();
				pnlInv [count].add (lblTime [count]);
				
				pnlTime.add (pnlInv [count]);
				
			}
			
		}
		
		pnlTime.add (pnlFill2 [1]);
		
		pnlCompact.add (pnlArrayOfBox);
		pnlCompact.add (pnlTime);
		
		// Solution Area
		lblJobNo.setFont (stdBold);
		lblExit.setFont (stdBold);
		lblArrival.setFont (stdBold);
		lblTurn.setFont (stdBold);
		lblBurstDisp.setFont (stdBold);
		lblWait.setFont (stdBold);
		
		lblJobNo.setForeground (Color.BLACK);
		lblExit.setForeground (Color.BLACK);
		lblArrival.setForeground (Color.BLACK);
		lblTurn.setForeground (Color.BLACK);
		lblBurstDisp.setForeground (Color.BLACK);
		lblWait.setForeground (Color.BLACK);
		
		lblJobNo.setHorizontalAlignment (SwingConstants.CENTER);
		lblExit.setHorizontalAlignment (SwingConstants.CENTER);
		lblArrival.setHorizontalAlignment (SwingConstants.CENTER);
		lblTurn.setHorizontalAlignment( SwingConstants.CENTER);
		lblBurstDisp.setHorizontalAlignment (SwingConstants.CENTER);
		lblWait.setHorizontalAlignment (SwingConstants.CENTER);
		
		pnlHead.add (lblJobNo);
		pnlHead.add (lblExit);
		pnlHead.add (lblArrival);
		pnlHead.add (lblTurn);
		pnlHead.add (lblBurstDisp);
		pnlHead.add (lblWait);
		pnlSolution.add (pnlHead);
		
		/* Sort the data based on job number */
		for (count = 0; count < noOfJobs; count++) {
			
			for (int count2 = 0; count2 < noOfJobs - 1; count2++) {
				
				if (jobNumber [count2 + 1] < jobNumber [count2]) {
					
					double temp = 0.00;
					int intTemp = 0;
					
					intTemp = jobNumber [count2 + 1];
					jobNumber [count2 + 1] = jobNumber [count2];
					jobNumber [count2] = intTemp;
					
					temp = grandData [count2 + 1][0];
					grandData [count2 + 1][0] = grandData [count2][0];
					grandData [count2][0] = temp;
					
					temp = grandData [count2 + 1][1];
					grandData [count2 + 1][1] = grandData [count2][1];
					grandData [count2][1] = temp;
					
					temp = grandData [count2 + 1][2];
					grandData [count2 + 1][2] = grandData [count2][2];
					grandData [count2][2] = temp;
					
					temp = grandData [count2 + 1][3];
					grandData [count2 + 1][3] = grandData [count2][3];
					grandData [count2][3] = temp;
					
					temp = grandData [count2 + 1][4];
					grandData [count2 + 1][4] = grandData [count2][4];
					grandData [count2][4] = temp;
					
					temp = grandData [count2 + 1][5];
					grandData [count2 + 1][5] = grandData [count2][5];
					grandData [count2][5] = temp;
					
					temp = grandData [count2 + 1][6];
					grandData [count2 + 1][6] = grandData [count2][6];
					grandData [count2][6] = temp;
					
				}
				
			}
			
		} // for() sort job number
		
		/* Add grandData to Window */
		for (count = 0; count < noOfJobs; count++) {
			pnlLine[count] = new JPanel (new GridLayout (1, 6));
			
			lblGrandData [count][0] = new JLabel (String.format (jobNumber [count] + ""));
			lblGrandData [count][1] = new JLabel (String.format ("%.2f", grandData [count][4]));
			lblGrandData [count][2] = new JLabel (grandData [count][0] + "");
			lblGrandData [count][3] = new JLabel (String.format ("%.2f", grandData [count][5]));
			lblGrandData [count][4] = new JLabel (grandData [count][1] + "");
			lblGrandData [count][5] = new JLabel (String.format ("%.2f", grandData [count][6]));
			
			lblGrandData [count][0].setFont (stdFont);
			lblGrandData [count][1].setFont (stdFont);
			lblGrandData [count][2].setFont (stdFont);
			lblGrandData [count][3].setFont (stdFont);
			lblGrandData [count][4].setFont (stdFont);
			lblGrandData [count][5].setFont (stdFont);
			
			lblGrandData [count][0].setHorizontalAlignment(SwingConstants.CENTER);
			lblGrandData [count][1].setHorizontalAlignment(SwingConstants.CENTER);
			lblGrandData [count][2].setHorizontalAlignment(SwingConstants.CENTER);
			lblGrandData [count][3].setHorizontalAlignment(SwingConstants.CENTER);
			lblGrandData [count][4].setHorizontalAlignment(SwingConstants.CENTER);
			lblGrandData [count][5].setHorizontalAlignment(SwingConstants.CENTER);
			
			pnlLine[count].add (lblGrandData [count][0]);
			pnlLine[count].add (lblGrandData [count][1]);
			pnlLine[count].add (lblGrandData [count][2]);
			pnlLine[count].add (lblGrandData [count][3]);
			pnlLine[count].add (lblGrandData [count][4]);
			pnlLine[count].add (lblGrandData [count][5]);
			
			pnlSolution.add (pnlLine [count]);
		}
		
		/* Truncate Decimal to two places */
		lblTurnAve.setText (String.format ("%s   %.2f", lblTurnAve.getText(), ProcessConstants.decimalTruncate (ttAve)));
		lblWaitAve.setText (String.format ("%s   %.2f", lblWaitAve.getText(), ProcessConstants.decimalTruncate (wtAve)));
		
		lblTurnAve.setFont (stdFont);
		lblWaitAve.setFont (stdFont);
		
		pnlAverage.add (lblTurnAve);
		pnlAverage.add (lblWaitAve);
		
		pnlSolution.add (pnlClearance);
		pnlSolution.add (pnlAverage);
		
		scrollPaneGantt = new JScrollPane (pnlCompact);
		scrollPaneSolution = new JScrollPane (pnlSolution);
		
		btnBack.addActionListener (this);
		pnlButton.add (btnBack);
		
		sectionSplit.add (scrollPaneGantt);
		sectionSplit.add (scrollPaneSolution);
		sectionSplit.setEnabled (false);
		contentPane.add (sectionSplit, BorderLayout.CENTER);
		contentPane.add (pnlButton, BorderLayout.SOUTH);
		
		frame.setSize (500, 350);
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.setVisible (true);
	}
	
	/* launchFrame() for preemptive algorithms */
	protected void 
	plaunchFrame () 
	{
		int count = 0;
		
		contentPane = frame.getContentPane();
		sectionSplit.setResizeWeight(0.30);
		
		pnlBox [0] = new JPanel ();
		pnlBox [0].add (pnlFill [0]);
		pnlBox [0].setBorder (BorderFactory.createEmptyBorder (5, 5, 5, 5));
		pnlArrayOfBox.add (pnlBox [0]);
		
		/* Box Area */
		/* Add IDLE box if idle is true */
		if (idle) {
			
			JPanel pnlInit 			= 	new JPanel (new GridLayout (2, 1));
			JLabel lblInit 			= 	new JLabel ("IDLE");
			JLabel lblInitBurst 	= 	new JLabel (grandData [orderOfJobs[0]][0] + "");
			lblInit.setFont (stdFont);
			lblInitBurst.setFont (stdFont);
			lblInit.setHorizontalAlignment (SwingConstants.LEFT);
			lblInitBurst.setHorizontalAlignment (SwingConstants.RIGHT);
			pnlInit.add (lblInit);
			pnlInit.add (lblInitBurst);
			pnlInit.setBorder (BorderFactory.createEtchedBorder (EtchedBorder.RAISED, Color.BLACK, Color.BLACK));
			pnlArrayOfBox.add (pnlInit);
			
		}
		
		for (count = 0; count < orderCount; count++) {
			pnlBox [count] 		= 	new JPanel ();
			
			lblJob [count] 		= 	new JLabel ("Job # " + jobNumber [orderOfJobs [count]]);
			lblBurst [count] 	= 	new JLabel (String.format ("%.2f", orderOfBursts [count]));
			lblJob [count].setHorizontalAlignment(SwingConstants.LEFT);
			lblBurst [count].setHorizontalAlignment(SwingConstants.RIGHT);
			
			lblJob [count].setFont (new Font ("Courier New", Font.PLAIN, 12));
			lblBurst [count].setFont (new Font ("Courier New", Font.PLAIN, 12));
			
			pnlBox [count].setLayout (new GridLayout (2, 1));
			pnlBox [count].add (lblJob[count]);
			pnlBox [count].add (lblBurst[count]);
			pnlBox [count].setBorder (BorderFactory.createEtchedBorder (EtchedBorder.RAISED, Color.BLACK, Color.BLACK));
			
			pnlArrayOfBox.add (pnlBox [count]);
			
		}
			
		pnlBox [orderCount + 1] = new JPanel ();
		pnlBox [orderCount + 1].add (pnlFill [1]);
		pnlBox [orderCount + 1].setBorder (BorderFactory.createEmptyBorder (5, 5, 5, 5));
		pnlArrayOfBox.add (pnlBox [orderCount + 1]);
		
		/* Time Area */
		pnlTime.add (pnlFill2[0]);
		if (idle) {
			
			for (count = 1; count < orderCount + 3; count++) {
				
				lblTime [count - 1] 	= 	new JLabel (String.format ("%.2f", flagTime [count - 1]));
				lblTime [count - 1].setHorizontalAlignment (SwingConstants.CENTER);
				lblTime [count - 1].setFont (new Font ("Courier New", Font.PLAIN, 12));
				
				pnlInv [count] 			= 	new JPanel ();
				pnlInv [count].add (lblTime [count - 1]);
				
				pnlTime.add (pnlInv [count], BorderLayout.CENTER);
				
			}
			
		} else {
			
			for (count = 0; count < orderCount + 1; count++) {
				
				lblTime [count] 	= 	new JLabel (String.format ("%.2f", flagTime [count]));
				lblTime [count].setHorizontalAlignment (SwingConstants.CENTER);
				lblTime [count].setFont (new Font ("Courier New", Font.PLAIN, 12));
				
				pnlInv [count] 		= 	new JPanel ();
				pnlInv [count].add (lblTime [count]);
				
				pnlTime.add (pnlInv [count]);
				
			}
			
		}
		
		pnlTime.add (pnlFill2 [1]);
		
		pnlCompact.add (pnlArrayOfBox);
		pnlCompact.add (pnlTime);
		
		// Solution Area
		lblJobNo.setFont (stdBold);
		lblExit.setFont (stdBold);
		lblArrival.setFont (stdBold);
		lblTurn.setFont (stdBold);
		lblBurstDisp.setFont (stdBold);
		lblWait.setFont (stdBold);
		
		lblJobNo.setForeground (Color.BLACK);
		lblExit.setForeground (Color.BLACK);
		lblArrival.setForeground (Color.BLACK);
		lblTurn.setForeground (Color.BLACK);
		lblBurstDisp.setForeground (Color.BLACK);
		lblWait.setForeground (Color.BLACK);
		
		lblJobNo.setHorizontalAlignment (SwingConstants.CENTER);
		lblExit.setHorizontalAlignment (SwingConstants.CENTER);
		lblArrival.setHorizontalAlignment (SwingConstants.CENTER);
		lblTurn.setHorizontalAlignment( SwingConstants.CENTER);
		lblBurstDisp.setHorizontalAlignment (SwingConstants.CENTER);
		lblWait.setHorizontalAlignment (SwingConstants.CENTER);
		
		pnlHead.add (lblJobNo);
		pnlHead.add (lblExit);
		pnlHead.add (lblArrival);
		pnlHead.add (lblTurn);
		pnlHead.add (lblBurstDisp);
		pnlHead.add (lblWait);
		pnlSolution.add (pnlHead);
		
		/* Sort the data based on job number */
		for (count = 0; count < noOfJobs; count++) {
			
			for (int count2 = 0; count2 < noOfJobs - 1; count2++) {
				
				if (jobNumber [count2 + 1] < jobNumber [count2]) {
					
					double temp = 0.00;
					int intTemp = 0;
					
					intTemp = jobNumber [count2 + 1];
					jobNumber [count2 + 1] = jobNumber [count2];
					jobNumber [count2] = intTemp;
					
					temp = grandData [count2 + 1][0];
					grandData [count2 + 1][0] = grandData [count2][0];
					grandData [count2][0] = temp;
					
					temp = grandData [count2 + 1][1];
					grandData [count2 + 1][1] = grandData [count2][1];
					grandData [count2][1] = temp;
					
					temp = grandData [count2 + 1][2];
					grandData [count2 + 1][2] = grandData [count2][2];
					grandData [count2][2] = temp;
					
					temp = grandData [count2 + 1][3];
					grandData [count2 + 1][3] = grandData [count2][3];
					grandData [count2][3] = temp;
					
					temp = grandData [count2 + 1][4];
					grandData [count2 + 1][4] = grandData [count2][4];
					grandData [count2][4] = temp;
					
					temp = grandData [count2 + 1][5];
					grandData [count2 + 1][5] = grandData [count2][5];
					grandData [count2][5] = temp;
					
					temp = grandData [count2 + 1][6];
					grandData [count2 + 1][6] = grandData [count2][6];
					grandData [count2][6] = temp;
					
				}
				
			}
			
		} // for() sort job number
		
		/* Add grandData to Window */
		for (count = 0; count < noOfJobs; count++) {
			pnlLine[count] = new JPanel (new GridLayout (1, 6));
			
			lblGrandData [count][0] = new JLabel (String.format (jobNumber [count] + ""));
			lblGrandData [count][1] = new JLabel (String.format ("%.2f", grandData [count][4]));
			lblGrandData [count][2] = new JLabel (grandData [count][0] + "");
			lblGrandData [count][3] = new JLabel (String.format ("%.2f", grandData [count][5]));
			lblGrandData [count][4] = new JLabel (grandData [count][1] + "");
			lblGrandData [count][5] = new JLabel (String.format ("%.2f", grandData [count][6]));
			
			lblGrandData [count][0].setFont (stdFont);
			lblGrandData [count][1].setFont (stdFont);
			lblGrandData [count][2].setFont (stdFont);
			lblGrandData [count][3].setFont (stdFont);
			lblGrandData [count][4].setFont (stdFont);
			lblGrandData [count][5].setFont (stdFont);
			
			lblGrandData [count][0].setHorizontalAlignment(SwingConstants.CENTER);
			lblGrandData [count][1].setHorizontalAlignment(SwingConstants.CENTER);
			lblGrandData [count][2].setHorizontalAlignment(SwingConstants.CENTER);
			lblGrandData [count][3].setHorizontalAlignment(SwingConstants.CENTER);
			lblGrandData [count][4].setHorizontalAlignment(SwingConstants.CENTER);
			lblGrandData [count][5].setHorizontalAlignment(SwingConstants.CENTER);
			
			pnlLine[count].add (lblGrandData [count][0]);
			pnlLine[count].add (lblGrandData [count][1]);
			pnlLine[count].add (lblGrandData [count][2]);
			pnlLine[count].add (lblGrandData [count][3]);
			pnlLine[count].add (lblGrandData [count][4]);
			pnlLine[count].add (lblGrandData [count][5]);
			
			pnlSolution.add (pnlLine [count]);
		}
		
		/* Truncate Decimal to two places */
		lblTurnAve.setText (String.format ("%s   %.2f", lblTurnAve.getText(), ProcessConstants.decimalTruncate (ttAve)));
		lblWaitAve.setText (String.format ("%s   %.2f", lblWaitAve.getText(), ProcessConstants.decimalTruncate (wtAve)));
		
		lblTurnAve.setFont (stdFont);
		lblWaitAve.setFont (stdFont);
		
		pnlAverage.add (lblTurnAve);
		pnlAverage.add (lblWaitAve);
		
		pnlSolution.add (pnlClearance);
		pnlSolution.add (pnlAverage);
		
		scrollPaneGantt = new JScrollPane (pnlCompact);
		scrollPaneSolution = new JScrollPane (pnlSolution);
		
		btnBack.addActionListener (this);
		pnlButton.add (btnBack);
		
		sectionSplit.add (scrollPaneGantt);
		sectionSplit.add (scrollPaneSolution);
		sectionSplit.setEnabled (false);
		contentPane.add (sectionSplit, BorderLayout.CENTER);
		contentPane.add (pnlButton, BorderLayout.SOUTH);
		
		frame.setSize (500, 350);
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.setVisible (true);
	}
	
	@Override
	public void 
	actionPerformed (ActionEvent ae) 
	{
		if (ae.getSource () == btnBack) {
			UserInterface ui = new UserInterface (grandData, jobNumber, noOfJobs);
			frame.dispose ();
			ui.launchFrame ();
		}
	}
	
}