import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;

// Author: Jonathan Guillotte-Blouin


/**
* A specialized <code>JFrame</code> as well as the controller for this application. The questions for the game are kept in a <code>Database</code> object.
*/
public class Jeopardy extends JFrame implements ActionListener {

	/**
	* A <code>Database</code> object which will store the <code>Question</code>s associated to the game
	*/
	private Database db;

	/**
	* booleans to store the state of the components of the board. Used to make conditional actions.
	*/
	private boolean buttonsGridRevealed = false, answersGridRevealed = false, questionRevealed = false, answerRevealed = false;

	/**
	* Reference throughout Jeopardy to the three <code>JPanel</code>s forming the <code>JFrame</code>
	*/
	private JPanel topPanel, midPanel, bottomPanel;

	/**
	* Reference throughout Jeopardy to the <code>JLabel</code> containing the "question" that switches from a hidden state to a visible state after clicking "Reveal"
	*/
	private JLabel questionText;



	/**
	* The constructor of the class creates the layout of the application. Adding all the necessary buttons, adding itself as the listener for these buttons.
	*/
	public Jeopardy() {
		// displays "Jeopardy" in the title bar
		super("Jeopardy");

		// shut the program down when the "x" is clicked
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// styling
		setBackground(Color.WHITE);

		// create a JButton that will reveal the "question" when clicked
		JButton revealButton = new JButton("Reveal");
		// styling
		revealButton.setFocusPainted(false);
		// make the controller the listener to the event associated to the JButton
		revealButton.addActionListener(this);

		// create a JButton that will load a new grid of buttons when clicked
		JButton loadButton = new JButton("Load");
		// styling
		loadButton.setFocusPainted(false);
		// make the controller the listener to the event associated to the JButton
		loadButton.addActionListener(this);

		// create a JPanel that will contain the "Reveal" & "Load" JButtons
		bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.WHITE);
		bottomPanel.add(revealButton);
		bottomPanel.add(loadButton);
		
		// add the bottomPanel to the bottom of the JFrame
		add(bottomPanel, BorderLayout.SOUTH);

		// styling
		pack();
		// display the JFrame
		setVisible(true);
		//styling
		setResizable(false);
	}

	/**
	* The constructor of the class creates the layout of the application. Adding all the necessary buttons, adding itself as the listener for these buttons.
	* This special constructor automatically creates the grid, given the relative path to the file, without having to input the relative path using the "Load" dialog message.
	* @param pathToFile String containing the relative path to the file which contains the information needed to build the board.
	*/
	public Jeopardy(String pathToFile) {
		// displays "Jeopardy" in the title bar
		super("Jeopardy");

		// shut the program down when the "x" is clicked
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// styling
		setBackground(Color.WHITE);

		// create a JButton that will reveal the "question" when clicked
		JButton revealButton = new JButton("Reveal");
		// styling
		revealButton.setFocusPainted(false);
		// make the controller the listener to the event associated to the JButton
		revealButton.addActionListener(this);

		// create a JButton that will load a new grid of buttons when clicked
		JButton loadButton = new JButton("Load");
		// styling
		loadButton.setFocusPainted(false);
		// make the controller the listener to the event associated to the JButton
		loadButton.addActionListener(this);

		// create a JPanel that will contain the "Reveal" & "Load" JButtons
		bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.WHITE);
		bottomPanel.add(revealButton);
		bottomPanel.add(loadButton);
		
		// add the bottomPanel to the bottom of the JFrame
		add(bottomPanel, BorderLayout.SOUTH);

		// store the information found at "pathToFile" into db
		db = Database.readQuestions(pathToFile);

		// if everything worked fine, db should point to the Database object. Otherwise, there was a problem, and db is pointing to null.
		// if it is the case, we can't build the buttons Grid
		if (db != null) 
			buildButtonsGrid();

		// styling
		pack();
		// display the JFrame
		setVisible(true);
		//styling
		setResizable(false);
	}

	/**
	* Called when a JButton or JeopardyButton, in this case, is clicked. Required by the ActionListener interface.
	* @param e catches a reference to the Object
	*/
	public void actionPerformed(ActionEvent e) {

		// if the button clicked is part of the grid, so if it's a JeopardyButton...
		if (e.getSource() instanceof JeopardyButton) {
			// we don't want to show another "answer" if the question has not been shown
			// we also want to show an "answer" if no answers are currently displayed 
			if (questionRevealed || !answersGridRevealed) {
				// cast the source of the vent to a JeopardyButton
				JeopardyButton selected = (JeopardyButton) e.getSource();
				// styling
				selected.setFocusPainted(false);

				// we don't display an "answer" that has already been done
				if (!selected.getText().equals("-"))
					buildAnswersGrid(selected); // builds the answers grid, displaying only the "answer"
				else
					JOptionPane.showMessageDialog(null,"This question has already been done!");
			}
			else
				JOptionPane.showMessageDialog(null,"You haven't looked at the question!");
		}
		// if the button clicked is a JButton, but not a JeopardyButton (so "Reveal" or "Load")
		else if (e.getSource() instanceof JButton && !(e.getSource() instanceof JeopardyButton)) {
			// get the text of the button, so we can detect which of "Reveal" or "Load" it is
			String revealOrLoad = ((JButton)e.getSource()).getText();

			// if it is "Load", or if it's "Reveal"...
			switch (revealOrLoad) {
				case "Load":
					// ask for the relative path to the file that the user wants to load
					String pathToFile = JOptionPane.showInputDialog("Input the relative path to the file", "questions/actualJeopardyQuestions.txt");

					// update the database with the new file, if it is not null
					if (pathToFile != null) {
						db = Database.readQuestions(pathToFile);

						// if everything worked fine, db should point to the Database object. Otherwise, there was a problem, and db is pointing to null.
						// if it is the case, we can't build the buttons Grid
						if (db != null) 
							buildButtonsGrid();
					}

					break;

				case "Reveal":
					// displays the question if the related "answer" is already shown
					if (answerRevealed) {
						// make the "question" visible
						questionText.setVisible(true);
						// update the flag
						questionRevealed = true;
					}
					else
						JOptionPane.showMessageDialog(null,"You haven't selected a question yet!");

					break;
			}
		}
    }

    /**
    * Given a <code>JeopardyButton</code>, this method builds the related <code>JPanel</code>(midPanel) related to the button.
    * Once built, it displays the "answer", but hides, for the moment, the "question".
    * @param jpButton JeopardyButton clicked containing the related <code>Question</code> object.
    */
    private void buildAnswersGrid(JeopardyButton jpButton) {
    	// update flag
    	questionRevealed = false;

    	// get the index of the category and of the question of the JeopardyButton clicked
    	int categoryIndex = jpButton.getCategory(), questionIndex = jpButton.getQuestion();
    	// get the Question object at category index and question index
    	Question questionObject = db.getQuestion(categoryIndex, questionIndex);
    	// get the question and the answer of the Question object
    	String answerToShow = questionObject.getResponse(), questionToShow = questionObject.getQuestion();

    	// change the Text of the jpButton clicked to "-"
    	jpButton.setText("-");

		// if a midPanel (JPanel containing the answer and the question) is already in the JFrame, remove it
		if (answersGridRevealed)
    		getContentPane().remove(midPanel);

    	// create JLabels with the question and the answer retrieved
		JLabel answerText = new JLabel(answerToShow, SwingConstants.LEFT);
		questionText = new JLabel(questionToShow, SwingConstants.LEFT);
		// make the "question" invisible for the moment
		questionText.setVisible(false);

		// make a new JPanel, which contains the question and the answer
		midPanel = new JPanel();
		midPanel.setLayout(new GridLayout(2,1)); // this layout positions two JComponents vertically
		midPanel.add(answerText);
		midPanel.add(questionText);

		// add the new JPanel to the JFrame
		getContentPane().add(midPanel, BorderLayout.CENTER);
		// styling
		pack();

		// update flags
		answersGridRevealed = answerRevealed = true;
    }

    /**
    * This methods build the grid containing the <code>JeopardyButton</code>s, according to the information from the <code>Database</code>.
    * Once built, it displays the grid of <code>JeopardyButton</code>s in the Jeopardy frame.
    */
    private void buildButtonsGrid() {
    	// store the number of categories, and the number of questions per category
    	int numCategories = db.getNumCategories(), numQuestions = db.getNumQuestions();

    	// if a topPanel (containing the grid of buttons) is already in the JFrame, remove it
    	if (buttonsGridRevealed)
    		getContentPane().remove(topPanel);

    	// if a midPanel (JPanel containing the answer and the question) is already in the JFrame, remove it
    	if (answerRevealed)
    		getContentPane().remove(midPanel);


    	// Instanciate a new topPannel using GridLayout, of rows "numQuestions + 1" (for the row occupied by the title of the categories)
    	// and columns "numCategories"
    	topPanel = new JPanel(new GridLayout(numQuestions + 1, numCategories));
    	//styling
    	topPanel.setBackground(Color.WHITE);

    	// loop through categories array in the database, create JLabels out of the various categories, style them, then add them to the JPanel (topPanel)
    	for (int i = 0; i < numCategories; i++) {
    		JLabel category = new JLabel(db.getCategory(i));
    		category.setOpaque(true);
    		category.setBackground(Color.decode("#FED000"));
    		category.setHorizontalAlignment(JLabel.CENTER);
    		topPanel.add(category);
    	}

    	// stores the amount of money for a given question
    	int amount = 100;

    	// Loop through the 2D array, instanciating a new JeoparyButton for every cell, giving the reference to the ActionListener 
    	// for each of them to the Jeopardy Frame, and finally adding the button to the topPanel
    	for (int i =0; i < numQuestions; i++) {
    		for (int j =0; j < numCategories; j++) {
    			JeopardyButton gridButton = new JeopardyButton(this, j, i, amount);
    			topPanel.add(gridButton);
    		}

    		// every row, we increment the amount by 100
    		amount += 100;
    	}
    	
    	// add the new JPanel to the JFrame
   		getContentPane().add(topPanel, BorderLayout.NORTH);
   		// styling
   		pack();

   		// update flags
   		buttonsGridRevealed = true;
   		answersGridRevealed = questionRevealed = answerRevealed = false;

    }

    /**
    * Where the Jeopardy game is played. It first prints the StudentInfo, then launch the game if the call to it is valid.
    * @param args stores the command-line arguments: here, we expect 0 or 1, 1 being if the user directly inputs the relative path to the file, which
    * results by automatically building the buttons grid.
    */
    public static void main(String args[]) {
    	// print the Student Info
    	StudentInfo.display();

    	// depending on the number of arguments, instanciate one of the two constructors of Jeopardy
    	if (args.length == 1)
    		new Jeopardy(args[0]);
    	else if (args.length == 0)
        	new Jeopardy();
        // if none of the two previous conditions are respected, the user has entered more than 1 argument: the user is alerted, and the game is not launched
        else
        	JOptionPane.showMessageDialog(null,"This is not a valid entry. This takes 0 or 1 argument, if you include a relative path to the file.");
    }
}
