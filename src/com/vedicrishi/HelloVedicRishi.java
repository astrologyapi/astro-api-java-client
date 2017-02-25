package com.vedicrishi;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class HelloVedicRishi {

	public static void main(String[] args)	throws UnirestException
	{
		HttpResponse<JsonNode> response = Unirest.post("https://api.vedicrishiastro.com/v1/astro_details")
				.basicAuth("5622", "90678603bba2e4b988ec2fdeba8bf524")
				.field("day", "28")
				.field("month", "12")
				.field("year", "1987")
				.field("hour", "4")
				.field("min", "0")
				.field("lat", "25.67")
				.field("lon", "82.11")
				.field("tzone", "5.5")
				.asJson();
		System.out.println(response.getBody().toString());
	}
}
