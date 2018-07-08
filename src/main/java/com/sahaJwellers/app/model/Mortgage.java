package com.sahaJwellers.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="mortgage_tbl")
public class Mortgage {
	
	@Version
	@Column(name="version")
	private Long version;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long mortgageId;
	
	@Column(name="bori")
	private Double bori;
	
	@Column(name="ana")
	private Double ana;
	
	@Column(name="ratti")
	private Double ratti;
	
	@Column(name="point")
	private Double point;
	
	@Column(name="gram")
	private Double gram;
	
	@Column(name="interest_rate")
	private Double interestRate;
	
	@Column(name="loan_Amt")
	private Double loanAmount;

	public Mortgage() {
		super();
	}

	@JsonCreator
	public Mortgage(@JsonProperty("version") Long version,@JsonProperty("mortgageId") Long mortgageId,@JsonProperty("bori") Double bori,@JsonProperty("ana") Double ana,
			@JsonProperty("ratti") Double ratti,@JsonProperty("point") Double point,@JsonProperty("gram") Double gram,
			@JsonProperty("interestRate") Double interestRate,@JsonProperty("loanAmount") Double loanAmount) {
		super();
		this.version = version;
		this.mortgageId = mortgageId;
		this.bori = bori;
		this.ana = ana;
		this.ratti = ratti;
		this.point = point;
		this.gram = gram;
		this.interestRate = interestRate;
		this.loanAmount = loanAmount;
	}

	public Long getMortgageId() {
		return mortgageId;
	}

	public void setMortgageId(Long mortgageId) {
		this.mortgageId = mortgageId;
	}

	public Double getBori() {
		return bori;
	}

	public void setBori(Double bori) {
		this.bori = bori;
	}

	public Double getAna() {
		return ana;
	}

	public void setAna(Double ana) {
		this.ana = ana;
	}

	public Double getRatti() {
		return ratti;
	}

	public void setRatti(Double ratti) {
		this.ratti = ratti;
	}

	public Double getPoint() {
		return point;
	}

	public void setPoint(Double point) {
		this.point = point;
	}

	public Double getGram() {
		return gram;
	}

	public void setGram(Double gram) {
		this.gram = gram;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}
}
