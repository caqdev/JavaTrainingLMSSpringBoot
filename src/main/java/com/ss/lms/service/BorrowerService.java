package com.ss.lms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ss.lms.dao.BookCopiesDAO;
import com.ss.lms.dao.BookDAO;
import com.ss.lms.dao.BookLoanDAO;
import com.ss.lms.dao.BorrowerDAO;
import com.ss.lms.dao.LibraryBranchDAO;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookLoan;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.LibraryBranch;

public class BorrowerService {

	
	@Autowired
	public BookDAO bdao;
	
	@Autowired
	public BookCopiesDAO bcdao;
	
	@Autowired
	public BookLoanDAO bldao;
	
	@Autowired
	public BorrowerDAO bordao;
	
	@Autowired
	public LibraryBranchDAO lbdao;
	
	
	public List<Book> getBooksAvailableFromBranch(LibraryBranch branch) {
		try {
			List<Book> booksAvailable = bdao.readAvailableBooksAtBranch(branch);
			return booksAvailable;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Book> getBooksAvailableToReturnToBranch(LibraryBranch libraryBranch, Borrower borrower) {
		try {
			List<Book> booksAvailable = bdao.readCheckedOutBooksAtBranchForReturn(libraryBranch, borrower);
			return booksAvailable;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Borrower getBorrowerById(Integer cardNo) {
		try {
			List<Borrower> dbResults = bordao.readBorrowerById(cardNo);
			if(dbResults.size() == 0) {
				return null;
			} else {
				return dbResults.get(0);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<LibraryBranch> getLibraryBranches() {
		try {
			return lbdao.readAllLibraryBranches();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<LibraryBranch> getLibraryBranchesForReturn(Borrower borrower) {
		try {
			return lbdao.readAllLibraryBranchesWithLoanedBooks(borrower);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public BookLoan getLoan(Book book, LibraryBranch branch, Borrower borrower) {
		try {
			return bldao.readBookLoan(book, branch, borrower);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String addNewBookLoan(BookLoan loan) {
		try {
			bldao.addBookLoan(loan); 
			BookLoan updated = bldao.readBookLoan(loan);
			bcdao.subtractBookCopyAtBranch(updated.getBook(), updated.getBranch());
			return "Loan has processed your due date is " + updated.getDueDate().toString();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to process loan - contact admin";
		}
		
	}

	public String returnBook(BookLoan loan) {
		try {
			bldao.returnBookLoan(loan); 
			bcdao.addBookCopyAtBranch(loan.getBook(), loan.getBranch());
			return "Book has been returned";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to return book - contact admin";
		}
	}

}
