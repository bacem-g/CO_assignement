package com.crossover.trial.weather.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Basic airport information.
 *
 * @author code test administrator
 */
public class AirportData {

	/** the three letter IATA code */
	private String iata;

	/** latitude value in degrees */
	private double latitude;

	/** longitude value in degrees */
	private double longitude;

	public AirportData() {
	}

	public AirportData(String iata) {
		super();
		this.iata = iata;
	}

	public AirportData(String iata, double latitude, double longitude) {
		super();
		this.iata = iata;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getIata() {
		return iata;
	}

	public void setIata(String iata) {
		this.iata = iata;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public boolean equals(Object other) {
		if (other instanceof AirportData) {
			return ((AirportData) other).getIata().equals(this.getIata());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return iata.hashCode();
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}
}
