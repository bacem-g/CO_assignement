package com.crossover.trial.airport.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.crossover.trial.weather.domain.AirportData;

/**
 * A simple airport loader which reads a file from disk and sends entries to the
 * webservice
 *
 * 
 * @author code test administrator
 */
public class AirportLoader {

	public final static String QUERY_TARGET = "http://localhost:9090/query";
	public final static String COLLECT_TARGET = "http://localhost:9090/collect";

	/** end point to supply updates */
	private WebTarget collect;

	public AirportLoader() {
		Client client = ClientBuilder.newClient();
		collect = client.target(COLLECT_TARGET);
	}

	public void upload(InputStream airportDataStream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(airportDataStream));
		String l = null;
		String[] airportDataArray = new String[9];
		List<AirportData> airportDataList = new ArrayList<>();
		WebTarget airportsPath = collect.path("/airports");
		
		while ((l = reader.readLine()) != null) {
			airportDataArray = l.split(",");
			AirportData airportData = new AirportData(airportDataArray[4].replaceAll("\"", ""),
					Double.parseDouble(airportDataArray[6]), Double.parseDouble(airportDataArray[7]));
			airportDataList.add(airportData);
			if(airportDataList.size() == 100) {
				airportsPath.request().post(Entity.entity(airportDataList, MediaType.APPLICATION_JSON));
				airportDataList = new ArrayList<>();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(!airportDataList.isEmpty()) {
			airportsPath.request().post(Entity.entity(airportDataList, MediaType.APPLICATION_JSON));
			airportDataList = new ArrayList<>();
		}
	}

	public static void main(String args[]) throws IOException {
		File airportDataFile = new File(args[0]);
		if (!airportDataFile.exists() || airportDataFile.length() == 0) {
			System.err.println(airportDataFile + " is not a valid input");
			System.exit(1);
		}

		AirportLoader al = new AirportLoader();
		al.upload(new FileInputStream(airportDataFile));
		System.exit(0);
	}
}
