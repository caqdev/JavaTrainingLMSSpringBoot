package com.ss.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.ss.lms.entity.Borrower;

/**
 * @borrower ppradhan, caq
 *
 */
@Repository
public class BorrowerDAO extends BaseDAO<Borrower> implements ResultSetExtractor<List<Borrower>>{

	public void addBorrower(Borrower borrower) throws ClassNotFoundException, SQLException { //Still needs to be edited
		jdbcTemplate.update("INSERT INTO tbl_borrower (name, address, phone) VALUES (?, ?, ?)", new Object[] { borrower.getBorrowerName(), borrower.getBorrowerAddress(), borrower.getBorrowerPhone() });
	}
	
	public Integer addBorrowerWithPk(Borrower borrower) throws ClassNotFoundException, SQLException {//Still needs to be edited
		return jdbcTemplate.update("INSERT INTO tbl_borrower (name, address, phone) VALUES (?, ?, ?)", new Object[] { borrower.getBorrowerName(), borrower.getBorrowerAddress(), borrower.getBorrowerPhone() });
	}

	public void updateBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {//Still needs to be edited
		jdbcTemplate.update("UPDATE tbl_borrower SET name = ?, address = ?, phone = ? WHERE cardNo = ?",
				new Object[] { borrower.getBorrowerName(), borrower.getBorrowerAddress(), borrower.getBorrowerPhone(), borrower.getBorrowerCardNo() });
	}

	public void deleteBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {//Still needs to be edited
		jdbcTemplate.update("DELETE FROM tbl_borrower WHERE cardNo = ?", new Object[] { borrower.getBorrowerCardNo() });
	}
	
	public List<Borrower> readAllBorrowers() throws SQLException, ClassNotFoundException {
		return jdbcTemplate.query("SELECT * FROM tbl_borrower", this);
	}
	
	public List<Borrower> readAllBorrowersByName(String searchString) throws SQLException, ClassNotFoundException {
		searchString = "%"+searchString+"%";
		return jdbcTemplate.query("SELECT * FROM tbl_borrower WHERE name LIKE ?", new Object[] {searchString}, this);
	}
	
	public List<Borrower> readBorrowerById(Integer cardNo) throws SQLException, ClassNotFoundException {
		return jdbcTemplate.query("SELECT * FROM tbl_borrower WHERE cardNo = ?", new Object[] { cardNo }, this);
	}
	
	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		List<Borrower> borrowers = new ArrayList<>();
		while (rs.next()) {
			Borrower b = new Borrower(rs.getInt("cardNo"), rs.getString("name"), rs.getString("address"), rs.getString("phone"));
			borrowers.add(b);
		}
		return borrowers;
	}
}
