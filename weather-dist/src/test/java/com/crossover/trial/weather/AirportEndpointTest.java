package com.crossover.trial.weather;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.google.gson.Gson;

public class AirportEndpointTest {

	private WeatherCollectorEndpoint _update = new RestWeatherCollectorEndpoint();
	private Gson _gson = new Gson();
	
	@Test
	public void testPing() {
		Response response = _update.ping();
		assertEquals("ready", response.getEntity());
	}
	
	@Test
	public void testGetAirports() {
		
	}
}
