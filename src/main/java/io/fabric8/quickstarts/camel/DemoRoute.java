package co.com.summan.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class DemoRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		
        from("timer://foo?period=5000")
        .setBody().constant("Message Demo Route Camel Fuse!")
        .log(">>> ${body}");	
        
	}

}
