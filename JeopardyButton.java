import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

// Author: Jonathan Guillotte-Blouin


/**
* A specialized <code>JButton</code> that stores two integers, category and question. It has getters for these two attributes.
* These <code>JeopardyButton</code>s are the main piece of the Jeopardy board, which the user will click to display the "answer".
*/
public class JeopardyButton extends JButton {

	/**
	* Stores the index of the category in which the <code>JeopardyButton</code> can be found
	*/
	private int category;

	/**
	* Stores the index in which the <code>JeopardyButton</code> can be found vertically in the 2D representation of the board
	*/
	private int question;

	/**
	* the constructor initializes the attributes of the object, adds a listener, sets the label of the button to 
	* represent the amount of money associated with the question
	* @param listener reference to the Object which will handle the <code>ActionEvent</code> when the <code>JeopardyButton</code> is clicked
	* @param category the index of the category in which the <code>JeopardyButton</code> can be found
	* @param question the index in which the <code>JeopardyButton</code> can be found vertically in the 2D representation of the board
	* @param amount the amount of money related to the <code>Question</code> of the <code>JeopardyButton</code>
	*/
	public JeopardyButton(ActionListener listener, int category, int question, int amount) {
		this.category = category;
		this.question = question;

		// change the display of the JeopardyButton
		setOpaque(true);
		setBackground(Color.decode("#F3F3F3"));
		setText("$"+Integer.toString(amount));

		// reference to the Object "listener", which will handle the ActionEvent
		addActionListener(listener);
	}

	/**
	* getter that returns the index of the category in which the JeopardyButton can be found
	* @return the index of the category in which the JeopardyButton can be found
	*/
	public int getCategory() {
		return category;
	}

	/**
	* getter that returns the index in which the JeopardyButton can be found vertically in the 2D representation of the board
	* @return the index in which the JeopardyButton can be found vertically in the 2D representation of the board
	*/
	public int getQuestion() {
		return question;
	}
}
