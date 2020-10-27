/**
 * 
 */
package com.ss.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.ss.lms.entity.Book;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.LibraryBranch;

/**
 * @book ppradhan, caq
 *
 */
@Repository
public class BookDAO extends BaseDAO<Book> implements ResultSetExtractor<List<Book>>{

	public void addBook(Book book) throws ClassNotFoundException, SQLException {
		 jdbcTemplate.update("INSERT INTO tbl_book (title, pubId) VALUES (?, ?)", new Object[] { book.getTitle(), book.getPublisher().getPublisherId() });
	}
	
	public Integer addBookWithPk(Book book) throws ClassNotFoundException, SQLException {
		return jdbcTemplate.update("INSERT INTO tbl_book (title, pubId) VALUES (?, ?)", new Object[] { book.getTitle(), book.getPublisher().getPublisherId() });
	}

	public void updateBook(Book book) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("UPDATE tbl_book SET title = ?, pubId = ? WHERE bookId = ?",
				new Object[] { book.getTitle(), book.getPublisher().getPublisherId(), book.getBookId() });
	}

	public void deleteBook(Book book) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("DELETE FROM tbl_book WHERE bookId = ?", new Object[] { book.getBookId() });
	}

	public List<Book> readAllBooks() throws SQLException, ClassNotFoundException {
		return jdbcTemplate.query("SELECT * FROM tbl_book", this);
	}
	
	public List<Book> readAllBooksByName(String searchString) throws SQLException, ClassNotFoundException {
		searchString = "%"+searchString+"%";
		return jdbcTemplate.query("SELECT * FROM tbl_book WHERE title LIKE ?", new Object[] {searchString}, this);
	}
	
	public Book readBookById(Integer bookId) throws SQLException, ClassNotFoundException, IndexOutOfBoundsException {
		List<Book> results = jdbcTemplate.query("SELECT * FROM tbl_book WHERE bookId = ?", new Object[] { bookId }, this);
		return results.get(0);
	}
	
	public List<Book> readAvailableBooksAtBranch(LibraryBranch libraryBranch) throws SQLException, ClassNotFoundException {
		return jdbcTemplate.query("SELECT tb.* FROM tbl_book tb INNER JOIN tbl_book_copies tbc ON tb.bookId=tbc.bookId INNER JOIN tbl_library_branch tlb ON tbc.branchId=tlb.branchId"
				+ " WHERE tbc.noOfCopies > 0 AND tbc.branchId = ?;", new Object[] { libraryBranch.getBranchId()}, this );
	}
	
	public List<Book> readCheckedOutBooksAtBranchForReturn(LibraryBranch libraryBranch, Borrower borrower) throws SQLException, ClassNotFoundException {
		return jdbcTemplate.query("SELECT tb.* FROM tbl_book tb INNER JOIN tbl_book_loans tbl ON tb.bookId=tbl.bookId WHERE tbl.branchId = ? AND tbl.cardNo = ? AND tbl.dateIn IS NULL",
				new Object[] { libraryBranch.getBranchId(), borrower.getBorrowerCardNo()}, this);
	}
	
	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
//		AuthorDAO adao = new AuthorDAO(conn);
//		GenreDAO gdao = new GenreDAO(conn);
//		PublisherDAO pdao = new PublisherDAO(conn);
		while (rs.next()) {
			Book b = new Book(rs.getInt("bookId"), rs.getString("title"));
//			b.setAuthors(adao.read("select * from tbl_author where authorId IN (select authorId from tbl_book_authors where bookId = ?)", new Object[] { b.getBookId() }));
//			b.setGenres(gdao.read("SELECT * FROM tbl_genre WHERE genre_id IN (SELECT genre_id FROM tbl_book_genres WHERE bookId = ?)", new Object[] { b.getBookId() }));
//			b.setPublisher(pdao.read("SELECT * FROM tbl_publisher WHERE publisherId = (SELECT pubId FROM tbl_book WHERE bookId = ?)", new Object[] { b.getBookId() }).get(0));
			books.add(b);
		}
		return books;
	}
}
