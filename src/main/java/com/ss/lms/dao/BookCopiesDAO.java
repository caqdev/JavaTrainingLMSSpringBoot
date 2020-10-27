package com.ss.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookCopies;
import com.ss.lms.entity.LibraryBranch;

@Repository
public class BookCopiesDAO extends BaseDAO<BookCopies> implements ResultSetExtractor<List<BookCopies>>{

	public void addBookCopies(BookCopies bc) throws SQLException, ClassNotFoundException {
		jdbcTemplate.update("INSERT INTO tbl_book_copies (bookId, branchId, noOfCopies) VALUES (?, ?, ?)", new Object[] {bc.getBook().getBookId(), bc.getBranch().getBranchId(), bc.getNumberOfCopies()});
	}
	
	public void addBookCopyAtBranch(Book book, LibraryBranch branch) throws SQLException, ClassNotFoundException {
		jdbcTemplate.update("UPDATE tbl_book_copies SET noOfCopies = (noOfCopies+1) WHERE bookID = ? AND branchId = ?;", new Object[] { book.getBookId(), branch.getBranchId() });
		
	}
	public List<BookCopies> readBookCopiesAtBranch(Book book, LibraryBranch libraryBranch) throws SQLException, ClassNotFoundException {
		return jdbcTemplate.query("SELECT * FROM tbl_book_copies tbc INNER JOIN tbl_book tb ON tbc.bookId = tb.bookId INNER JOIN tbl_library_branch tlb ON tbc.branchId = tlb.branchId" +
				" WHERE tbc.bookId = ? AND tbc.branchId = ?", new Object[] { book.getBookId(), libraryBranch.getBranchId() }, this);
	}
	
	public void updateBookCopies(BookCopies bc) throws SQLException, ClassNotFoundException {
		jdbcTemplate.update("UPDATE tbl_book_copies SET noOfCopies = ? WHERE bookId = ? AND branchId = ?", new Object[] { bc.getNumberOfCopies(), bc.getBook().getBookId(), bc.getBranch().getBranchId() });
	}
	
	public List<BookCopies> extractData(ResultSet rs) throws SQLException{
		List<BookCopies> copiesList = new ArrayList<>();
//		AuthorDAO adao = new AuthorDAO(conn);
//		GenreDAO gdao = new GenreDAO(conn);
//		PublisherDAO pdao = new PublisherDAO(conn);
		while (rs.next()) {
			Book b = new Book(rs.getInt("bookId"), rs.getString("title"));
//			b.setAuthors(adao.read("select * from tbl_author where authorId IN (select authorId from tbl_book_authors where bookId = ?)", new Object[] { b.getBookId() }));
//			b.setGenres(gdao.read("SELECT * FROM tbl_genre WHERE genre_id IN (SELECT genre_id FROM tbl_book_genres WHERE bookId = ?)", new Object[] { b.getBookId() }));
//			b.setPublisher(pdao.read("SELECT * FROM tbl_publisher WHERE publisherId = (SELECT pubId FROM tbl_book WHERE bookId = ?)", new Object[] { b.getBookId() }).get(0));
			
			LibraryBranch lb = new LibraryBranch(rs.getInt("branchId"), rs.getString("branchName"), rs.getString("branchAddress"));
			BookCopies bc = new BookCopies(b, lb, rs.getInt("noOfCopies"));
			copiesList.add(bc);
		}
		return copiesList;
	}
	
	public void subtractBookCopyAtBranch(Book book, LibraryBranch branch) throws SQLException, ClassNotFoundException {
		jdbcTemplate.update("UPDATE tbl_book_copies SET noOfCopies = (noOfCopies-1) WHERE bookID = ? AND branchId = ?;", new Object[] { book.getBookId(), branch.getBranchId() });
		
	}
}
