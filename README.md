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

    $ git clone https://github.com/LiDaKrA/FuhSen-reactive.git

To run the project you need "Activator 1.3.12" installed and execute the following command from the root folder of the project.

    $ activator run

SimMol server will listen on port 9000.

### Example Usage

Send POST request to the following address http://localhost:9000/similarity

### Configuration
In application.config configure either two datasets or just one dataset containing the RDF Molecules of data. The datasets will be loaded in the services at starting time.

```
similarity.function {
 
  model1_location = "Home/Drugbank/dataset_20100405.nt"
  model2_location = ""

}
```

### Request

Send an array of tuples containing the uris of the molecules to be compared

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

* Copyright (C) 2016-2017 EIS Uni-Bonn
* Licensed under the Apache 2.0 License
