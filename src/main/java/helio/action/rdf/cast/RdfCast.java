package helio.action.rdf.cast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;

import com.google.gson.JsonObject;

import helio.blueprints.Action;
import helio.blueprints.exceptions.ActionException;

public class RdfCast implements Action {

	private static final String DATA_FORMAT_TOKEN = "data-format";
	private static final String OUTPUT_FORMAT_TOKEN = "output-format";
	private static final String NT_TOKEN = "nt";
	private static final String TTL_TOKEN = "ttl";
	private Lang dataFormat;
	private Lang outputFormat;
	
	public void configure(JsonObject configuration) {
		if(configuration==null || configuration.keySet().isEmpty())
			throw new IllegalArgumentException("Provide a valid configuration that includes a 'data-format' and 'output-format' keys, which values can be any of (case insensitive): turtle, ttl, json-ld, json-ld-11, n3, N-Triples, rdf/xml");
		if(configuration.has(DATA_FORMAT_TOKEN)) {
			dataFormat = parseFormat(configuration.get(DATA_FORMAT_TOKEN).getAsString());
		}else {
			throw new IllegalArgumentException("Provide a configuration that includes the 'data-format' key and which value can be any of (case insensitive): turtle, ttl, json-ld, json-ld-11, n3, N-Triples, rdf/xml");
		}
		if(configuration.has(OUTPUT_FORMAT_TOKEN)) {
			outputFormat = parseFormat(configuration.get(OUTPUT_FORMAT_TOKEN).getAsString());
		}else {
			throw new IllegalArgumentException("Provide a configuration that includes the 'output-format' key and which value can be any of (case insensitive): turtle, ttl, json-ld, json-ld-11, n3, N-Triples, rdf/xml");
		}
	}

	public String run(String values) throws ActionException {
		Model model = ModelFactory.createDefaultModel();
		String result = "";
		try {
			model.read(new ByteArrayInputStream(values.getBytes()), null, dataFormat.getName());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			model.write(out, outputFormat.getName());
			result = new String(out.toByteArray());
		}catch(Exception e) {
			e.printStackTrace();
			throw new ActionException(e.getMessage());
		}
		
		return result;
	}
	
	private Lang parseFormat(String format) {
		if(Lang.TURTLE.getName().equalsIgnoreCase(format))
			return Lang.TURTLE;
		if(Lang.JSONLD.getName().equalsIgnoreCase(format))
			return Lang.JSONLD;
		if(Lang.JSONLD11.getName().equalsIgnoreCase(format))
			return Lang.JSONLD11;
		if(Lang.N3.getName().equalsIgnoreCase(format))
			return Lang.N3;
		if(Lang.NTRIPLES.getName().equalsIgnoreCase(format))
			return Lang.NTRIPLES;
		if(format.equalsIgnoreCase(NT_TOKEN))
			return Lang.NT;
		if(format.equalsIgnoreCase(TTL_TOKEN))
			return Lang.TTL;
		if(Lang.RDFXML.getName().equalsIgnoreCase(format))
			return Lang.RDFXML;
		throw new IllegalArgumentException("Provided format ('"+format+"') is not supported, choose one from (case insensitive): turtle, ttl, json-ld, json-ld-11, n3, N-Triples, rdf/xml");
	}

}
