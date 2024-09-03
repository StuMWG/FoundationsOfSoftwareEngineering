package core;

public class Book {
	private String title;
	private String author;
	private String publish;
	private int copiesSold;
	
	//default constructor
	public Book() {
		super();
	}
	
	// constructor
	public Book(String title, String author, String publish, int copiesSold) {
		this.title = title;
		this.author = author;
		this.publish = publish;
		this.copiesSold = copiesSold;
	}
	
	// set title based on provided title
	public void setTitle(String title) {
		this.title = title;
	}
	
	// gets the title 
	public String getTitle() {
		return title;
	}
	
	// sets the author based on the provided author name
	public void setAuthor(String author) {
		this.author = author;
	}
	
	// gets the author name
	public String getAuthor() {
		return author;
	}
	
	// sets the publisher based on the provided publisher
	public void setPublish(String publish) {
		this.publish = publish;
	}
	
	// gets the publisher name
	public String getPublish() {
		return publish;
	}
	
	// sets the total amount of copies sold
	public void setCopiesSold(int copiesSold) {
		this.copiesSold = copiesSold;
	}
	
	// gets the total amount of copies sold
	public int getCopiesSold() {
		return copiesSold;
	}

}
