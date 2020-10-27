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

import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.LibraryBranch;

/**
 * @libraryBranch ppradhan, caq
 *
 */
@Repository
public class LibraryBranchDAO extends BaseDAO<LibraryBranch> implements ResultSetExtractor<List<LibraryBranch>>{

	public void addLibraryBranch(LibraryBranch libraryBranch) throws ClassNotFoundException, SQLException { //Still needs to be edited
		jdbcTemplate.update("INSERT INTO tbl_library_branch (branchName, branchAddress) VALUES (?, ?)", new Object[] { libraryBranch.getBranchName(), libraryBranch.getBranchAddress() });
	}
	
	public Integer addLibraryBranchWithPk(LibraryBranch libraryBranch) throws ClassNotFoundException, SQLException {//Still needs to be edited
		return jdbcTemplate.update("INSERT INTO tbl_library_branch (branchName, branchAddress) VALUES (?, ?)", new Object[] { libraryBranch.getBranchName(), libraryBranch.getBranchAddress() });
	}

	public void updateLibraryBranch(LibraryBranch libraryBranch) throws ClassNotFoundException, SQLException {//Still needs to be edited
		jdbcTemplate.update("UPDATE tbl_library_branch SET branchName = ?, branchAddress = ? WHERE branchId = ?",
				new Object[] { libraryBranch.getBranchName(), libraryBranch.getBranchAddress(), libraryBranch.getBranchId() });
	}

	public void deleteLibraryBranch(LibraryBranch libraryBranch) throws ClassNotFoundException, SQLException {//Still needs to be edited
		jdbcTemplate.update("DELETE FROM tbl_library_branch WHERE branchId = ?", new Object[] { libraryBranch.getBranchId() });
	}
	
	public List<LibraryBranch> readAllLibraryBranches() throws SQLException, ClassNotFoundException {
		return jdbcTemplate.query("SELECT * FROM tbl_library_branch", this);
	}
	
	public LibraryBranch readLibraryBranchById(Integer branchId) throws ClassNotFoundException, SQLException {
		return jdbcTemplate.query("SELECT * FROM tbl_library_branch WHERE branchId = ?", new Object[] { branchId }, this).get(0);
	}

	public List<LibraryBranch> readAllLibraryBranchesByName(String searchString) throws SQLException, ClassNotFoundException {
		searchString = "%"+searchString+"%";
		return jdbcTemplate.query("SELECT * FROM tbl_library_branch WHERE branchName LIKE ?", new Object[] {searchString}, this);
	}	
	
	
	public List<LibraryBranch> readAllLibraryBranchesWithLoanedBooks(Borrower borrower) throws SQLException, ClassNotFoundException {
		return jdbcTemplate.query("SELECT DISTINCT tlb.* FROM tbl_library_branch tlb INNER JOIN tbl_book_loans tbl ON tlb.branchId = tbl.branchId WHERE tbl.cardNo = ? AND tbl.dateIn IS NULL", 
				new Object[] { borrower.getBorrowerCardNo() }, this);
	}

	@Override
	public List<LibraryBranch> extractData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		List<LibraryBranch> branches = new ArrayList<>();
		while (rs.next()) {
			LibraryBranch lb = new LibraryBranch(rs.getInt("branchId"), rs.getString("branchName"), rs.getString("branchAddress"));
			branches.add(lb);
		}
		return branches;
	}
}
