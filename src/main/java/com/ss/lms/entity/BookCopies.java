package com.ss.lms.entity;

public class BookCopies {

	private Book book;
	private LibraryBranch branch;
	private Integer numberOfCopies;
	
	public BookCopies(Book book, LibraryBranch branch, Integer numberOfCopies) {
		super();
		this.book = book;
		this.branch = branch;
		this.numberOfCopies = numberOfCopies;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public LibraryBranch getBranch() {
		return branch;
	}

	public void setBranch(LibraryBranch branch) {
		this.branch = branch;
	}

	public Integer getNumberOfCopies() {
		return numberOfCopies;
	}

	public void setNumberOfCopies(Integer numberOfCopies) {
		this.numberOfCopies = numberOfCopies;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((numberOfCopies == null) ? 0 : numberOfCopies.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookCopies other = (BookCopies) obj;
		if (book == null) {
			if (other.book != null)
				return false;
		} else if (!book.equals(other.book))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (numberOfCopies == null) {
			if (other.numberOfCopies != null)
				return false;
		} else if (!numberOfCopies.equals(other.numberOfCopies))
			return false;
		return true;
	}

	
	
	
}
