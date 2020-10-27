package com.ss.lms.entity;

public class Borrower {

	String borrowerAddress;
	Integer borrowerCardNo;
	String borrowerName;
	String borrowerPhone;
	
	public Borrower() {
		super();
	}

	public Borrower(Integer borrowerCardNo, String borrowerName, String borrowerAddress, String borrowerPhone) {
		super();
		this.borrowerAddress = borrowerAddress;
		this.borrowerCardNo = borrowerCardNo;
		this.borrowerName = borrowerName;
		this.borrowerPhone = borrowerPhone;
	}

	public String getBorrowerAddress() {
		return borrowerAddress;
	}

	public void setBorrowerAddress(String borrowerAddress) {
		this.borrowerAddress = borrowerAddress;
	}

	public Integer getBorrowerCardNo() {
		return borrowerCardNo;
	}

	public void setBorrowerCardNo(Integer borrowerCardNo) {
		this.borrowerCardNo = borrowerCardNo;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public String getBorrowerPhone() {
		return borrowerPhone;
	}

	public void setBorrowerPhone(String borrowerPhone) {
		this.borrowerPhone = borrowerPhone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((borrowerAddress == null) ? 0 : borrowerAddress.hashCode());
		result = prime * result + ((borrowerCardNo == null) ? 0 : borrowerCardNo.hashCode());
		result = prime * result + ((borrowerName == null) ? 0 : borrowerName.hashCode());
		result = prime * result + ((borrowerPhone == null) ? 0 : borrowerPhone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Borrower other = (Borrower) obj;
		if (borrowerAddress == null) {
			if (other.borrowerAddress != null)
				return false;
		} else if (!borrowerAddress.equals(other.borrowerAddress))
			return false;
		if (borrowerCardNo == null) {
			if (other.borrowerCardNo != null)
				return false;
		} else if (!borrowerCardNo.equals(other.borrowerCardNo))
			return false;
		if (borrowerName == null) {
			if (other.borrowerName != null)
				return false;
		} else if (!borrowerName.equals(other.borrowerName))
			return false;
		if (borrowerPhone == null) {
			if (other.borrowerPhone != null)
				return false;
		} else if (!borrowerPhone.equals(other.borrowerPhone))
			return false;
		return true;
	}
	
	
}
