package com.ss.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookLoan;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.LibraryBranch;

/**
 * @bookLoan ppradhan, caq
 *
 */
public class BookLoanDAO extends BaseDAO<BookLoan> implements ResultSetExtractor<List<BookLoan>>{
	
	public void addBookLoan(BookLoan bookLoan) throws SQLException, ClassNotFoundException {
		jdbcTemplate.update("INSERT INTO tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) VALUES (?, ?, ?, now(),date_add(now(), INTERVAL 7 DAY))", 
				new Object[] { bookLoan.getBook().getBookId(), bookLoan.getBranch().getBranchId(), bookLoan.getBorrower().getBorrowerCardNo()});	
	}

	public void extendLoanDueDate(BookLoan bookLoan, int daysToExtend) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("UPDATE tbl_book_loans SET dueDate = (DATE_ADD(dueDate, INTERVAL ? DAY)) WHERE bookId = ? AND branchId = ? AND cardNo = ?",
			new Object[] { daysToExtend, bookLoan.getBook().getBookId(), bookLoan.getBranch().getBranchId(), bookLoan.getBorrower().getBorrowerCardNo() });
		
	}


	public List<BookLoan> readActiveBookLoans() throws ClassNotFoundException, SQLException {
		return jdbcTemplate.query("SELECT * FROM tbl_book_loans tbl INNER JOIN tbl_book tb ON tbl.bookId = tb.bookId INNER JOIN tbl_library_branch tlb ON tbl.branchId = tlb.branchId"
				+ " INNER JOIN tbl_borrower tbo ON tbl.cardNo = tbo.cardNo WHERE tbl.dateIn IS NULL", new Object[] {}, this);
	}


	public BookLoan readBookLoan(BookLoan bookLoan) throws SQLException, ClassNotFoundException {
		return jdbcTemplate.query("SELECT * FROM tbl_book_loans tbl INNER JOIN tbl_book tb ON tbl.bookId = tb.bookId INNER JOIN tbl_library_branch tlb ON tbl.branchId = tlb.branchId" 
				+ " INNER JOIN tbl_borrower tbo ON tbl.cardNo = tbo.cardNo WHERE tbl.bookId = ? AND tbl.branchId = ? AND tbl.cardNo = ?", 
				new Object[] { bookLoan.getBook().getBookId(), bookLoan.getBranch().getBranchId(), bookLoan.getBorrower().getBorrowerCardNo() }, this).get(0);
	}
	
	public BookLoan readBookLoan(Book book, LibraryBranch branch, Borrower borrower) throws SQLException, ClassNotFoundException {
		return jdbcTemplate.query("SELECT * FROM tbl_book_loans tbl INNER JOIN tbl_book tb ON tbl.bookId = tb.bookId INNER JOIN tbl_library_branch tlb ON tbl.branchId = tlb.branchId" 
				+ " INNER JOIN tbl_borrower tbo ON tbl.cardNo = tbo.cardNo WHERE tbl.bookId = ? AND tbl.branchId = ? AND tbl.cardNo = ?", 
				new Object[] { book.getBookId(), branch.getBranchId(), borrower.getBorrowerCardNo() }, this).get(0);
	}
	
	public void returnBookLoan(BookLoan bookLoan) throws SQLException, ClassNotFoundException {
		jdbcTemplate.update("UPDATE tbl_book_loans SET dateIn = now() WHERE bookId = ? AND branchId = ? AND cardNo = ?",
				new Object[] { bookLoan.getBook().getBookId(), bookLoan.getBranch().getBranchId(), bookLoan.getBorrower().getBorrowerCardNo() });
	}
	
	@Override
	public List<BookLoan> extractData(ResultSet rs) throws SQLException {
		List<BookLoan> loans = new ArrayList<>();
		while(rs.next()) {
//			AuthorDAO adao = new AuthorDAO(conn);
//			GenreDAO gdao = new GenreDAO(conn);
//			PublisherDAO pdao = new PublisherDAO(conn);
			
			Book book = new Book(rs.getInt("bookId"), rs.getString("title"));
//			book.setAuthors(adao.read("select * from tbl_author where authorId IN (select authorId from tbl_book_authors where bookId = ?)", new Object[] { book.getBookId() }));
//			book.setGenres(gdao.read("SELECT * FROM tbl_genre WHERE genre_id IN (SELECT genre_id FROM tbl_book_genres WHERE bookId = ?)", new Object[] { book.getBookId() }));
//			book.setPublisher(pdao.read("SELECT * FROM tbl_publisher WHERE publisherId = (SELECT pubId FROM tbl_book WHERE bookId = ?)", new Object[] { book.getBookId() }).get(0));
			
			LibraryBranch branch = new LibraryBranch(rs.getInt("branchId"), rs.getString("branchName"), rs.getString("branchAddress"));
			Borrower borrower = new Borrower(rs.getInt("cardNo"), rs.getString("name"), rs.getString("address"), rs.getString("phone"));
			
			BookLoan l = new BookLoan(book, branch, borrower, rs.getDate("dateOut"), rs.getDate("dueDate"), rs.getDate("dateIn"));
			loans.add(l);
		}
		return loans;
	}
}
