# RDF Molecules Similarity Service - SimMol

## Description
SimMol provides different state of the art RDF Molecules similarity metrics such as GADES as REST Service

## Dependencies
SimMol project depends on the following software

* JDK 1.8
* Play Web Framework 2.5.12 "Streamy" and Activator 1.3.12

Download Play: https://www.playframework.com/download

## Install and build from the source code  
To obtain the latest version of the project please clone the github repository

    $ git clone https://github.com/RDF-Molecules/sim_service.git

To run the project you need "Activator 1.3.12" installed and execute the following command from the root folder of the project.

    $ activator run

SimMol server will listen on port 9000.

### Example Usage

Send POST request to the following address http://localhost:9000/similarity/(function_name)

### Configuration
The SimMol service requires one RDF graph containing the RDF molecules to be compared.
The path of the RDF graph has to be configured before using the service, there are two ways of configuring the service:

1. **Configuration file:** In application.config configure either two datasets or just one dataset containing the RDF Molecules of data. The datasets will be loaded in the services at starting time.

```
similarity.function {
 
  model1_location = "/some_path/rdf_graph_with_molecules.nt"
  model2_location = ""
  set_uris = ""

}
```
2. **Initialize service:** A service to initialize the RDF graph is provided as well. 

    http://localhost:9000/similarity/initialize?model_1=/some_path/rdf_graph_with_molecules.nt

Examples of RDF graphs can be found here:

* DBpedia - Drugbank Drugs molecules

    https://github.com/RDF-Molecules/Test-DataSets/blob/master/DrugBank/together_enriched.zip

### Request

Currently we support two similarity functions: jaccard and gades 
You can choose any of them by adding the name of the function in the url 

http://localhost:9000/similarity/jaccard
or
http://localhost:9000/similarity/gades

The body of the request should contain JSON with an array of tuples containing the uris of the molecules to be compared

```json
{
 "tasks" : [ {
    "uri1" : "http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugs/DB01050",
    "uri2" : "http://dbpedia.org/resource/Ibuprofen"
  }, {
    "uri1" : "http://dbpedia.org/resource/Pindolol",
    "uri2" : "http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugs/DB00960"
  } ]
}
```


**References**
[1] Ignacio Traverso-Ribón, Maria-Esther Vidal, Benedikt Kämpgen and York Sure-Vetter. GADES: A Graph-based Semantic Similarity Measure. Semantics 2016, Leipzig.

### Response

An extra element "value" will be added to the json containing the similarity value between both entities.

```json
[
  {
    "uri1": "http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugs/DB01050",
    "uri2": "http://dbpedia.org/resource/Ibuprofen",
    "value": 0.096
  },
  {
    "uri1": "http://dbpedia.org/resource/Pindolol",
    "uri2": "http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugs/DB00960",
    "value": 0.137
  }
]
```

## License

* Copyright (C) 2016-2018 EIS/SDA Uni-Bonn
* Licensed under the Apache 2.0 License
