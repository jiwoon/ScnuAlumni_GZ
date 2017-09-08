package com.newttl.scnualumni_gz.bean.database;

/**
 * 问答知识类
 * @author lgc
 *
 * 2017年7月18日 下午5:58:19
 */
public class Knowledge {

	//问答知识id
	private int id;
	//问题
	private String question;
	//回答
	private String answer;
	//类型，1-普通聊天，2-笑话，3-上下文
	private int category;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	
}
