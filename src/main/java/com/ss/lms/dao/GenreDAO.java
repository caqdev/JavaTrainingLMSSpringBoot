/**
 * 
 */
package com.ss.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.ss.lms.entity.Genre;

/**
 * @genre caq
 *
 */
public class GenreDAO extends BaseDAO<Genre> implements ResultSetExtractor<List<Genre>>{

		public Integer addGenre(Genre genre) throws ClassNotFoundException, SQLException {
			return jdbcTemplate.update("INSERT INTO tbl_genre (genre_name) VALUES (?)", new Object[] { genre.getGenreName() });
		}
		
//		public Integer addGenreWithPk(Genre genre) throws ClassNotFoundException, SQLException {
//			return jdbcTemplate.update("INSERT INTO tbl_genre (genre_name) VALUES (?)", new Object[] { genre.getGenreName() });
//		}

		public void updateGenre(Genre genre) throws ClassNotFoundException, SQLException {
			jdbcTemplate.update("UPDATE tbl_genre SET genre_name = ? WHERE genre_id = ?",
					new Object[] { genre.getGenreName(), genre.getGenreId() });
		}

		public void deleteGenre(Genre genre) throws ClassNotFoundException, SQLException {
			jdbcTemplate.update("DELETE FROM tbl_genre WHERE genre_id = ?", new Object[] { genre.getGenreId() });
		}

		public List<Genre> readAllGenres() throws SQLException, ClassNotFoundException {
			return jdbcTemplate.query("SELECT * FROM tbl_genre", this);
		}
		
		public List<Genre> readAllGenresByName(String searchString) throws SQLException, ClassNotFoundException {
			searchString = "%"+searchString+"%";
			return jdbcTemplate.query("SELECT * FROM tbl_genre WHERE genre_name LIKE ?", new Object[] {searchString}, this);
		}

		public void addBookGenres(Integer bookId, Integer genreId) throws ClassNotFoundException, SQLException {
			jdbcTemplate.update("INSERT INTO tbl_book_genres VALUES (?, ?)", new Object[] { genreId, bookId });
		}

		public void removeBookGenres(Integer bookId, Integer genreId) throws ClassNotFoundException, SQLException {
			jdbcTemplate.update("DELETE FROM tbl_book_genres WHERE genre_id = ? AND bookId = ?", new Object[] { genreId, bookId });
		}
		
		@Override
		public List<Genre> extractData(ResultSet rs) throws SQLException {
			List<Genre> genres = new ArrayList<>();
			while (rs.next()) {
				Genre g = new Genre(rs.getInt("genre_id"), rs.getString("genre_name"));
				genres.add(g);
			}
			return genres;
		}

}
