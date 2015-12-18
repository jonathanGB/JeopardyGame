import javax.swing.JOptionPane;

/**
* Displays information about the author of this Jeopardy
*/
public class StudentInfo {

// Author: Jonathan Guillotte-Blouin


	/**
	* Method to display the information about the author.
	*/
	public static void display() {
		System.out.println("Jonathan Guillotte-Blouin \n\nThis was compiling using the command-line.\nThe JavaDoc is not included, but can be generated in the terminal using \"javadoc -d doc *.java\" when in the same directory as the project.\nThis implementation checks for a valid file format for the Database.\nIt also handles creating a grid directly from the command-line, using the relative path to the file as an argument, rather than having to input it through the \"Load\" dialog box.\ne.g. java Jeopardy questions/actualJeopardyQuestions.txt");
		JOptionPane.showMessageDialog(null, "Jonathan Guillotte-Blouin \n\nThis was compiling using the command-line.\nThe JavaDoc is not included, but can be generated in the terminal using \"javadoc -d doc *.java\" when in the same directory as the project.\nThis implementation checks for a valid file format for the Database.\nIt also handles creating a grid directly from the command-line, using the relative path to the file as an argument, rather than having to input it through the \"Load\" dialog box.\ne.g. java Jeopardy questions/actualJeopardyQuestions.txt");
	}
}
