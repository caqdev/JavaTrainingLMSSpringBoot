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

import com.ss.lms.entity.Publisher;

/**
 * @publisher caq
 *
 */
@Repository
public class PublisherDAO extends BaseDAO<Publisher> implements ResultSetExtractor<List<Publisher>>{

	public void addPublisher(Publisher publisher) throws ClassNotFoundException, SQLException { //Still needs to be edited
		jdbcTemplate.update("INSERT INTO tbl_publisher (publisherName, publisherAddress) VALUES (?, ?)", new Object[] { publisher.getPublisherName(), publisher.getPublisherAddress() });
	}
	
	public Integer addPublisherWithPk(Publisher publisher) throws ClassNotFoundException, SQLException {//Still needs to be edited
		return jdbcTemplate.update("INSERT INTO tbl_publisher (publisherName, publisherAddress) VALUES (?, ?)", new Object[] { publisher.getPublisherName(), publisher.getPublisherAddress() });
	}

	public void updatePublisher(Publisher publisher) throws ClassNotFoundException, SQLException {//Still needs to be edited
		jdbcTemplate.update("UPDATE tbl_publisher SET publisherName = ?, publisherAddress = ? WHERE publisherId = ?",
				new Object[] { publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherId() });
	}

	public void deletePublisher(Publisher publisher) throws ClassNotFoundException, SQLException {//Still needs to be edited
		jdbcTemplate.update("DELETE FROM tbl_publisher WHERE publisherId = ?", new Object[] { publisher.getPublisherId() });
	}
	
	public List<Publisher> readAllPublishers() throws SQLException, ClassNotFoundException {
		return jdbcTemplate.query("SELECT * FROM tbl_publisher", this);
	}
	
	public List<Publisher> readAllPublishersByName(String searchString) throws SQLException, ClassNotFoundException {
		searchString = "%"+searchString+"%";
		return jdbcTemplate.query("SELECT * FROM tbl_publisher WHERE publisherName LIKE ?", new Object[] {searchString}, this);
	}
	
	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		List<Publisher> publishers = new ArrayList<>();
		while (rs.next()) {
			Publisher p = new Publisher(rs.getInt("publisherId"), rs.getString("publisherName"), rs.getString("publisherAddress"));
			publishers.add(p);
		}
		return publishers;
	}

}
