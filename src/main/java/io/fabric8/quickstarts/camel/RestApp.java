package io.fabric8.quickstarts.camel;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class RestApp extends RouteBuilder{
	
	@Override
	public void configure() throws Exception {
		/*
        restConfiguration()
        	.contextPath("/camel-rest-sql")
        	.apiContextPath("/api-doc")
            .apiProperty("api.title", "Camel REST API")
            .apiProperty("api.version", "1.0")
            .apiProperty("cors", "true")
            .apiContextRouteId("doc-api")
        .component("servlet")
        .bindingMode(RestBindingMode.json);
        */
		
		restConfiguration()
			.apiContextRouteId("doc-api")
			.contextPath("/movie")			
			.apiContextPath("/doc-api")  //swagger
			.apiProperty("api.title", "Movie REST API")
			.apiProperty("api.version", "1.0")
			
		    .apiProperty("cors", "false")		    
			.component("jetty")				
		    .host("localhost")
			.port(8082)
		    .dataFormatProperty("json.in.disableFeatures", "FAIL_ON_UNKNOWN_PROPERTIES")
		    .dataFormatProperty("prettyPrint", "true")
		    
		    //.bindingMode(RestBindingMode.json)
		    .bindingMode(RestBindingMode.off);
			
		rest("/pupular")
			.description("Get current popular movies")
			.get("name/{name}")
				.description("Get Movie by name")
				.produces("application/json")
				.to("direct:popularMovie")				
				
			.get("list")
				.description("The list all movies")
				.produces("application/json")
				.route()
				.to("velocity:vm/listMovies.vm")
				;
				
		
		from("direct:popularMovie")	
			.transform().simple("{\n" + 
					"      \"popularity\": 41.448,\n" + 
					"      \"vote_count\": 673,\n" + 
					"      \"video\": false,\n" + 
					"      \"poster_path\": \"/oyG9TL7FcRP4EZ9Vid6uKzwdndz.jpg\",\n" + 
					"      \"id\": 696374,\n" + 
					"      \"adult\": false,\n" + 
					"      \"backdrop_path\": \"/w2uGvCpMtvRqZg6waC1hvLyZoJa.jpg\",\n" + 
					"      \"original_language\": \"en\",\n" + 
					"      \"original_title\": \"${header.name}\",\n" + 
					"      \"genre_ids\": [\n" + 
					"        10749\n" + 
					"      ],\n" + 
					"      \"title\": \"${header.name}\",\n" + 
					"      \"vote_average\": 8.9,\n" + 
					"      \"overview\": \"An intriguing and sinful exploration of seduction, forbidden love, and redemption, Gabriel's Inferno is a captivating and wildly passionate tale of one man's escape from his own personal hell as he tries to earn the impossible--forgiveness and love.\",\n" + 
					"      \"release_date\": \"2020-05-29\"\n" + 
					"    }")
			.setHeader(Exchange.CONTENT_TYPE, simple("application/json"))
		.end();
		

	}
	


}
