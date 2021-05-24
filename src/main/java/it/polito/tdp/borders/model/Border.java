package it.polito.tdp.borders.model;

import java.sql.Date;

public class Border {

	private Country c1;
	private Country c2;
	private Integer year;

	public Border(Country c1, Country c2, Integer anno) {
		super();
		this.c1 = c1;
		this.c2 = c2;
		this.year = anno;
	}

	public Country getC1() {
		return c1;
	}

	public void setC1(Country c1) {
		this.c1 = c1;
	}

	public Country getC2() {
		return c2;
	}

	public void setC2(Country c2) {
		this.c2 = c2;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

}
