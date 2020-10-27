package com.ss.lms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ss.lms.dao.BookCopiesDAO;
import com.ss.lms.dao.BookDAO;
import com.ss.lms.dao.LibraryBranchDAO;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookCopies;
import com.ss.lms.entity.LibraryBranch;

public class LibrarianService {

	@Autowired
	public BookDAO bdao;
	
	@Autowired
	public BookCopiesDAO bcdao;
	
	@Autowired
	public LibraryBranchDAO lbdao;
	
	public String addBookCopies(BookCopies bc) {
		try {
			bcdao.addBookCopies(bc);
			return "Number of copies successfully updated";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to update number of copies - contact admin";
		}
	}
	public List<Book> getBooks(String searchString) {
		try {
			if (searchString != null) {
				return bdao.readAllBooksByName(searchString);
			} else {
				return bdao.readAllBooks();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public BookCopies getBookCopiesAtBranch(Book book, LibraryBranch libraryBranch) {
		try {
			List<BookCopies> results = bcdao.readBookCopiesAtBranch(book, libraryBranch);
			if(results.size() == 0) {
				return null;
			} else {
				return results.get(0);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<LibraryBranch> getLibraryBranches(String searchString) {
		try {
			if (searchString != null) {
				return lbdao.readAllLibraryBranchesByName(searchString);
			} else {
				return lbdao.readAllLibraryBranches();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String updateBookCopies(BookCopies bc) {
		try {
			bcdao.updateBookCopies(bc);
			return "Number of copies successfully updated";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to update number of copies - contact admin";
		}
	}
	public String updateLibraryBranch(LibraryBranch libraryBranch) {
		try {
			if (libraryBranch.getBranchName() != null && libraryBranch.getBranchName().length() > 45) {
				return "Branch name cannot be empty and should be at most 45 characters in length";
			}
			if(libraryBranch.getBranchAddress() != null && libraryBranch.getBranchAddress().length() > 45) {
				return "Branch address cannot be empty and should be at most 45 characters in length";
			}
			lbdao.updateLibraryBranch(libraryBranch);
			return "Branch updated successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to update branch - contact admin";
		}
	}

}
