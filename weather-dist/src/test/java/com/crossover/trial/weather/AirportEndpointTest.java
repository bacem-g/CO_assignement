package com.crossover.trial.weather;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import com.crossover.trial.weather.domain.AirportData;
import com.crossover.trial.weather.rest.WeatherCollectorEndpoint;
import com.crossover.trial.weather.rest.impl.RestWeatherCollectorEndpoint;
import com.crossover.trial.weather.rest.impl.RestWeatherQueryEndpoint;

public class AirportEndpointTest {

	private WeatherCollectorEndpoint _collector = new RestWeatherCollectorEndpoint();
	
	@Before
	public void setUp() {
		RestWeatherQueryEndpoint.init();
	}
	
	@Test
	public void testPing() {
		Response response = _collector.ping();
		assertEquals("ready", response.getEntity());
	}
	
	@Test
	public void testGetAirports() {
		Response response = _collector.getAirports();
		assertEquals(5, ((Set<String>)response.getEntity()).size());
	}
	
	@Test
	public void testGetAirport() {
		Response response = _collector.getAirport("BOS");
		AirportData airportData = (AirportData) response.getEntity();
		assertEquals(42.364347, airportData.getLatitude(), 0);
	}
	
	@Test
	public void testAddAirport() {
		_collector.addAirport("STN", "51.885", "0.235");
		Response response = _collector.getAirport("STN");
		AirportData airportData = (AirportData)response.getEntity();
		assertEquals("STN", airportData.getIata());
	}
	
	@Test
	public void testAddAirports() {
		List<AirportData> airports = new ArrayList<>();
		airports.add(new AirportData("STN", 51.885, 0.235));
		airports.add(new AirportData("LPL", 53.333611, -2.849722));
		_collector.addAirports(airports);
		Response response = _collector.getAirports();
		assertEquals(7, ((Set<String>)response.getEntity()).size());
	}
	
	@Test
	public void testDeleteAirport() {
		_collector.deleteAirport("BOS");
		Response response = _collector.getAirports();
		Set<String> airports = (Set<String>)response.getEntity();
		assertFalse(airports.contains("BOS"));
	}
}
