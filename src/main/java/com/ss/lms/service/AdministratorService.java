package com.ss.lms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ss.lms.dao.AuthorDAO;
import com.ss.lms.dao.BookDAO;
import com.ss.lms.dao.BookLoanDAO;
import com.ss.lms.dao.BorrowerDAO;
import com.ss.lms.dao.LibraryBranchDAO;
import com.ss.lms.dao.GenreDAO;
import com.ss.lms.dao.PublisherDAO;
import com.ss.lms.entity.Author;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookLoan;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.Genre;
import com.ss.lms.entity.LibraryBranch;
import com.ss.lms.entity.Publisher;

public class AdministratorService {

	@Autowired
	public AuthorDAO adao;
	
	@Autowired
	public BookDAO bdao;
	
	@Autowired
	public BookLoanDAO bldao;
	
	@Autowired
	public BorrowerDAO bordao;
	
	@Autowired
	public GenreDAO gdao;
	
	@Autowired
	public LibraryBranchDAO lbdao;
	
	@Autowired
	public PublisherDAO pdao;

	public String addBook(Book book) {
		try {
			if (book.getTitle() != null && book.getTitle().length() > 45) {
				return "Book Title cannot be empty and should be at most 45 characters in length";
			}
			book.setBookId(bdao.addBook(book));//formerly addBookWithPk
			for (Author a : book.getAuthors()) {
				adao.addBookAuthors(book.getBookId(), a.getAuthorId());
			}
			for(Genre g: book.getGenres()) {
				gdao.addBookGenres(book.getBookId(), g.getGenreId());
			}
			return "Book added successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to add book - contact admin.";
		} 
	}
	
	public String addAuthor(Author author) {
		try {
			if (author.getAuthorName() != null && author.getAuthorName().length() > 45) {
				return "Author name cannot be empty and should be at most 45 characters in length";
			}
			author.setAuthorId(adao.addAuthor(author));//Formerly addAuthorWithPK
			return "Author added successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to add author - contact admin";
		}
	}

	public String addBorrower(Borrower borrower) {
		try {
			if(borrower.getBorrowerAddress() != null && borrower.getBorrowerAddress().length() > 45) {
				return "Borrower address cannot be empty and should be at most 45 characters in length";
			}
			if(borrower.getBorrowerName() != null && borrower.getBorrowerName().length() > 45) {
				return "Borrower name cannot be empty and should be at most 45 characters in length";
			}
			if(borrower.getBorrowerPhone() != null && borrower.getBorrowerPhone().length() > 45) {
				return "Borrower phone cannot be empty and should be at most 45 characters in length";
			}
			borrower.setBorrowerCardNo(bordao.addBorrower(borrower));//formerly addBorrowerWithPk
			return "Borrower added successfully, their card # is: " + borrower.getBorrowerCardNo();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to add borrower - contact admin";
		}
	}

	public String addGenre(Genre genre) {
		try {
			if (genre.getGenreName() != null && genre.getGenreName().length() > 45) {
				return "Author name cannot be empty and should be at most 45 characters in length";
			}
			genre.setGenreId(gdao.addGenre(genre));//formerly addGenreWithPk
			return "Genre added successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to add genre - contact admin";
		}
	}

	public String addLibraryBranch(LibraryBranch libraryBranch) {
		try {
			if (libraryBranch.getBranchName() != null && libraryBranch.getBranchName().length() > 45) {
				return "Branch name cannot be empty and should be at most 45 characters in length";
			}
			if(libraryBranch.getBranchAddress() != null && libraryBranch.getBranchAddress().length() > 45) {
				return "Branch address cannot be empty and should be at most 45 characters in length";
			}
			libraryBranch.setBranchId(lbdao.addLibraryBranch(libraryBranch));//formally addLibraryBranchWithPk
			return "Branch added successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to add branch - contact admin";
		}
	}

	public String addPublisher(Publisher publisher) {
		try {
			if (publisher.getPublisherName() != null && publisher.getPublisherName().length() > 45) {
				return "Publisher name cannot be empty and should be at most 45 characters in length";
			}
			if(publisher.getPublisherAddress() != null && publisher.getPublisherAddress().length() > 45) {
				return "Publisher address cannot be empty and should be at most 45 characters in length";
			}
			publisher.setPublisherId(pdao.addPublisher(publisher));//formerly addPublisherWithPk
			return "Publisher added successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to add publisher - contact admin";
		}
	}

	public String deleteAuthor(Author author) {
		try {
			adao.deleteAuthor(author);
			return "Author deleted successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to delete author - contact admin";
		} 
	}

	public String deleteBook(Book book) {
		try {
			bdao.deleteBook(book);
			return "Book deleted successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to delete book - contact admin";
		} 
	}

	public String deleteBorrower(Borrower borrower) {
		try {
			bordao.deleteBorrower(borrower);
			return "Borrower deleted successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to delete borrower - contact admin";
		} 
	}

	public String deleteGenre(Genre genre) {
		try {
			gdao.deleteGenre(genre);
			return "Genre deleted successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to delete genre - contact admin";
		} 
	}

	public String deleteLibraryBranch(LibraryBranch branch) {
		try {
			lbdao.deleteLibraryBranch(branch);
			return "Library Branch deleted successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to delete library branch - contact admin";
		}
	}

	public String deletePublisher(Publisher publisher) {
		try {
			pdao.deletePublisher(publisher);
			return "Library Branch deleted successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to delete library branch - contact admin";
		}
	}

	public String extendLoan(BookLoan bookLoan, int daysToExtend) {
		try {
			bldao.extendLoanDueDate(bookLoan, daysToExtend); 
			BookLoan updatedLoan = bldao.readBookLoan(bookLoan);
			return "Due date has been extended until " + updatedLoan.getDueDate().toString();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to extend loan - contact admin";
		}
	}

	public List<BookLoan> getActiveBookLoans() {
		try {
			return bldao.readActiveBookLoans();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Author> getAuthors(String searchString) {
		try {
			if (searchString != null) {
				return adao.readAllAuthorsByName(searchString);
			} else {
				return adao.readAllAuthors();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
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
	
	public Book getBookById(Integer bookId) {
		try {
			return bdao.readBookById(bookId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Borrower> getBorrowers() {
		try {
			return bordao.readAllBorrowers();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Borrower getBorrowerByCardNo(Integer cardNo) {
		try {
			List<Borrower> borrowers = bordao.readBorrowerById(cardNo);
			if(borrowers.size() == 0) {
				return null;
			}else {
				return borrowers.get(0);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Genre> getGenres(String searchString) {
		try {
			if (searchString != null) {
				return gdao.readAllGenresByName(searchString);
			} else {
				return gdao.readAllGenres();
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

	public LibraryBranch getLibraryBrancheById(Integer branchId) {
		try {
			return lbdao.readLibraryBranchById(branchId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Publisher> getPublishers(String searchString) {
		try {
			if (searchString != null) {
				return pdao.readAllPublishersByName(searchString);
			} else {
				return pdao.readAllPublishers();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String updateAuthor(Author author) {
		try {
			if(author.getAuthorName() != null && author.getAuthorName().length() > 45) {
				return "Author name must not be null and should be at most 45 characters in length";
			}
			adao.updateAuthor(author);
			return "Author updated successfully.";
		} catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String updateBook(Book book) {
		try {
			if (book.getTitle() != null && book.getTitle().length() > 45) {
				return "Book Title cannot be empty and should be at most 45 characters in length";
			}
			bdao.updateBook(book);
			Book oldBookFromDB = bdao.readBookById(book.getBookId());
			for(Author a: book.getAuthors()) {
				if(!oldBookFromDB.getAuthors().contains(a)) {
					oldBookFromDB.getAuthors().add(a);
					adao.addBookAuthors(book.getBookId(), a.getAuthorId());
				}
			}
			for(Author a: oldBookFromDB.getAuthors()) {
				if(!book.getAuthors().contains(a)) {
					adao.removeBookAuthors(book.getBookId(), a.getAuthorId());
				}
			}
			for(Genre g: book.getGenres()) {
				if(!oldBookFromDB.getGenres().contains(g)) {
					oldBookFromDB.getGenres().add(g);
					gdao.addBookGenres(book.getBookId(), g.getGenreId());
				}
			}
			for(Genre g: oldBookFromDB.getGenres()) {
				if(!book.getGenres().contains(g)) {
					gdao.removeBookGenres(book.getBookId(), g.getGenreId());
				}
			}
			
			return "Book Updated Successfully";
		} catch (ClassNotFoundException | IndexOutOfBoundsException | SQLException e) {
			e.printStackTrace();
			return "Unable to update book - contact admin";
		} 
	}

	public String updateBorrower(Borrower borrower) {
		try {
			if(borrower.getBorrowerAddress() != null && borrower.getBorrowerAddress().length() > 45) {
				return "Borrower address cannot be empty and should be at most 45 characters in length";
			}
			if(borrower.getBorrowerName() != null && borrower.getBorrowerName().length() > 45) {
				return "Borrower name cannot be empty and should be at most 45 characters in length";
			}
			if(borrower.getBorrowerPhone() != null && borrower.getBorrowerPhone().length() > 45) {
				return "Borrower phone cannot be empty and should be at most 45 characters in length";
			}
			bordao.updateBorrower(borrower);
			return "Borrower updated successfully, their card # is: " + borrower.getBorrowerCardNo();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to update borrower - contact admin";
		}
	}

	public String updateGenre(Genre genre) {
		try {
			if (genre.getGenreName() != null && genre.getGenreName().length() > 45) {
				return "Author name cannot be empty and should be at most 45 characters in length";
			}
			gdao.updateGenre(genre);
			return "Genre Updated successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to update genre - contact admin";
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

	public String updatePublisher(Publisher publisher) {
		try {
			if (publisher.getPublisherName() != null && publisher.getPublisherName().length() > 45) {
				return "Publisher name cannot be empty and should be at most 45 characters in length";
			}
			if(publisher.getPublisherAddress() != null && publisher.getPublisherAddress().length() > 45) {
				return "Publisher address cannot be empty and should be at most 45 characters in length";
			}
			pdao.updatePublisher(publisher);
			return "Publisher updated successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to update publisher - contact admin";
		}
	}

}
