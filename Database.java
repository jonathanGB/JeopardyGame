import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import javax.swing.JOptionPane;

// Author: Jonathan Guillotte-Blouin


/**
* Used to store the questions and answers for the Jeopardy game
*/
public class Database {

	/**
	* Stores the different question categories
	*/
	private String categories[];

	/**
	* Stores a 2D representation of the <code>Question</code>s
	*/
	private Question questions[][];

	/**
	* Constructs a <code>Database</code>, which stores <code>Question</code>s and the categories of <code>Question</code>
	* @param m the number of categories
	* @param n the number of questions per category
	*/
	private Database(int m, int n) {
		categories = new String[m];
		questions = new Question[n][m];
	}

	/**
	* Static method that reads information from a file and returns a <code>Database</code> object with the information in it.
	* @param name Relative path to the file
	*/
	static Database readQuestions(String name) {

		// creates a File object from which we need to read information
		File file = new File(name);

		// initialize a Database object
		Database database;

		// initialize a Scanner, which will scan the File
		Scanner sc;

		// make sure the file is valid, otherwise alert the user and close the program
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			alertProblem(2);
	        return null;
	    }

	    // will store the number of categories (m) and the number of questions per category (n) written in the File
		int m, n;

		// read m & n, and make sure they are integers
		if (sc.hasNextInt()) {
			m = Integer.parseInt(sc.nextLine());

			if (sc.hasNextInt())
				n = Integer.parseInt(sc.nextLine());
			else {
				alertProblem(1);
				return null;
			}
		} // if not, alert the user and close the program
		else {
			alertProblem(1);
			return null;
		}

		// instanciate a Database of size m & n
		database = new Database(m,n);

		// read the categories, and store them in the Database
		for (int i =0; i < m; i++) {
			if (sc.hasNextLine())
				database.categories[i] = sc.nextLine();
			else  { // if there aren't enough lines in the file, alert the user and close the program
				alertProblem(1);
				return null;
			}
		}

		// read the questions & answers, and store them in the Database
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if(sc.hasNextLine()) {

					// after reading two lines, we should have an answer and a question
					// if there aren't enough lines, alert the user and close the program
					String tmpAnswer = sc.nextLine(), tmpQuestion;

					if (sc.hasNextLine())
						tmpQuestion = sc.nextLine();
					else {
						alertProblem(1);
						return null;
					}

					// the two previous lines temporarily stored make up a Question: store this Question into the database
					// at its right place in the 2D representation of the Jeopardy board 
					database.questions[i][j] = new Question(tmpAnswer, tmpQuestion);

				} else {
					alertProblem(1);
					return null;
				}
			}
		}

		// checking if the file contains more content than needed
		if(sc.hasNextLine()) {
			alertProblem(3);
			return null;
		}

		// if we've made it so far, the File is OK
		// tell the user it is a success, and return the Database
		alertProblem(4);
		return database;
	}

	/**
	* getter that returns the category found at position index of this <code>Database</code>
	* @param index index of the category we are interested for
	* @return String representing the specified category
	*/
	public String getCategory(int index) {
		return categories[index];
	}

	/**
	* setter that replaces the category at position index to category
	* @param index index of the category we want to replace
	* @param category the category which replaces the presently stored
	*/
	public void setCategory(int index, String category) {
		categories[index] = category;
	}

	/**
	* getter that returns the <code>Question</code> at position index for the given category
	* @param category the category in which the <code>Question</code> we want to get, is
	* @param index the index at which the <code>Question</code> can be found
	* @return the <code>Question</code> at position index for the given category
	*/
	public Question getQuestion(int category, int index) {
		return questions[index][category];
	}

	/**
	* setter that replaces the <code>Question</code> for a given category and index by another <code>Question</code>
	* @param category the category in which the <code>Question</code> we want to replace, is
	* @param index the index at which the <code>Question</code> we want to replace can be found
	* @param question the <code>Question</code> which replaces the presently stored for a given category and index
	*/
	public void setQuestion(int category, int index, Question question) {
		questions[index][category] = question;
	}

	/**
	* getter that returns the number of categories for this game
	* @return the number of the categories of this game
	*/
	public int getNumCategories() {
		return categories.length;
	}

	/**
	* getter that returns the number of questions per category
	* @return the number of questions per category
	*/
	public int getNumQuestions() {
		return questions.length;
	}

	/**
	* Given a number value representing a case problem, this method prompts an error or success message 
	* to the user using a <code>JOptionPane</code>
	* @param caseProblem arbitrary number value of the case problem we want to alert
	*/
	private static void alertProblem(int caseProblem) {
		switch(caseProblem) {
			case 1: 
				JOptionPane.showMessageDialog(null,"The end of file has been reached without having all the necessary information!");
				break;

			case 2:
	        	JOptionPane.showMessageDialog(null,"This is not a valid file!");
	        	break;

	        case 3:
	        	JOptionPane.showMessageDialog(null,"The file contains more stuff than needed!");
				break;

			case 4:
				JOptionPane.showMessageDialog(null,"Request to database successful!");
				break;
	    }
	}
	

	/* Used for returning the index of a category if equals to a given String */
	// private int getCategoryIndex(String category) {
	// 	for (int i = 0; i < categories.length; i++) {
	// 		if (categories[i].equals(category))
	// 			return i;
	// 	}
	//
	// 	// not found, return impossible index
	// 	return -1;
	// }
}
