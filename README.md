# RDF cast

This action allows to translate RDF data into any supported RDF serialization

## Register component

Use the following coordinates for importing this action:

````json
{
    "source": "https://github.com/helio-ecosystem/helio-action-rdf-cast/releases/download/v0.1.0/helio-action-rdf-cast-0.1.0.jar",
    "clazz": "helio.action.rdf.cast.RdfCast",
    "type": "ACTION"
 }
````

### Configuration

This action must be provided with a JSON as configuration, specifying the following:
 - 'data-format' must specify the RDF serialisation of the data. Possible values are turtle, ttl, json-ld, json-ld-11, rdf/xml, n-triples, nt, n3.
 - 'output-format' must specify the output RDF serialisation in which the data will be translated. Possible values are turtle, ttl, json-ld, json-ld-11, rdf/xml, n-triples, nt, n3.
