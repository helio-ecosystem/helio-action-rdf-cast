package helio.action.rdf.cast.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonObject;

import helio.action.rdf.cast.RdfCast;

public class RdfCastTest {

	// SET TRUE FOR FOLLOWING TRACES
	private boolean showErrorMessages = true;

	
	private String correctDataTTL = "@prefix ex: <http://example.org/ns#> .\n"
			+ "@prefix time: <http://www.w3.org/2006/time#> .\n"
			+ "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
			+ "ex:Now a time:Instant ;\n"
			+ "    time:inXSDDateTime \"1994-11-05T13:15:30Z\"^^xsd:dateTime .\n";
	private RdfCast cast;
	
	@Before
	public void setup() {
		cast = new RdfCast();
	}
	
	
	
	@Test
	public void testNullConfiguration() {
		boolean thrown = false;
		try {
			cast.configure(null);
		}catch(Exception e) {
			if(showErrorMessages)
				System.out.println(e.toString());
			thrown = true; 
		}
		Assert.assertTrue(thrown);
	}
	
	@Test
	public void testEmptyConfiguration() {
		JsonObject config = new JsonObject();
		boolean thrown = false;
		try {
			cast.configure(config);
		}catch(Exception e) {
			if(showErrorMessages)
				System.out.println(e.toString());
			thrown = true; 
		}
		Assert.assertTrue(thrown);
	}
	
	@Test
	public void testIncompleteConfigurationI() {
		JsonObject config = new JsonObject();
		config.addProperty("data-format", "");
		boolean thrown = false;
		try {
			cast.configure(config);
		}catch(Exception e) {
			if(showErrorMessages)
				System.out.println(e.toString());
			thrown = true; 
		}
		Assert.assertTrue(thrown);
	}
	
	@Test
	public void testIncompleteConfigurationII() {
		JsonObject config = new JsonObject();
		config.addProperty("output-format", "");
		boolean thrown = false;
		try {
			cast.configure(config);
		}catch(Exception e) {
			if(showErrorMessages)
				System.out.println(e.toString());
			thrown = true; 
		}
		Assert.assertTrue(thrown);
	}
	
	@Test
	public void testIncorrectDataFormat() {
		JsonObject config = new JsonObject();
		config.addProperty("data-format", "fake");
		boolean thrown = false;
		try {
			cast.configure(config);
		}catch(Exception e) {
			if(showErrorMessages)
				System.out.println(e.toString());
			thrown = true; 
		}
		Assert.assertTrue(thrown);
	}
	
	@Test
	public void testIncorrectOutputFormat() {
		JsonObject config = new JsonObject();
		config.addProperty("output-format", "fake");
		boolean thrown = false;
		try {
			cast.configure(config);
		}catch(Exception e) {
			if(showErrorMessages)
				System.out.println(e.toString());
			thrown = true; 
		}
		Assert.assertTrue(thrown);
	}

	
	@Test
	public void testCastTTL2XML() {
		JsonObject config = new JsonObject();
		config.addProperty("data-format", "turtle");
		config.addProperty("output-format", "rdf/xml");
		boolean thrown = false;
		String result =  "";
		try {
			cast.configure(config);
			result = cast.run(correctDataTTL);
			
		}catch(Exception e) {
			if(showErrorMessages)
				System.out.println(e.toString());
			thrown = true; 
		}
		Assert.assertTrue(!thrown && !result.isBlank());
	}
}
