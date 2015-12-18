// Author: Jonathan Guillotte-Blouin


/**
* Stores a pair of String objects, question and answer. It also has two getters
*/

public class Question {

	/**
	* A string storing a question
	*/
	private String question;

	/**
	* A string storing the answer to the question
	*/
	private String response;

	/**
	* Creates a <code> Question </code>, which consists of a question and the answer to this question
	* @param response the answer to the question
	* @param question the question
	*/
	public Question (String response, String question) {
		this.question = question;
		this.response = response;
	}

	/**
	* getter that returns the question
	* @return the question
	*/
	public String getQuestion() {
		return question;
	}

	/**
	* getter that returns the answer to the question
	* @return the answer to the question
	*/
	public String getResponse() {
		return response;
	}
}
