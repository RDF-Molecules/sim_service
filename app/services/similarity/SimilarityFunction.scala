package services.similarity

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, PipedInputStream, PipedOutputStream}

import org.apache.jena.rdf.model.ModelFactory
import play.Logger
import services.similarity.ontologyManagement.MyOWLOntology

/**
  * Created by dcollarana on 2/24/2017.
  */
trait SimilarityTrait {

  def initialize(model_1 :String, model_2 : String)

  def similarity(uri_1 :String, uri_2: String) : Double

}

object GADES extends SimilarityTrait {

  private var o : MyOWLOntology = null

  override def initialize(model_1 :String, model_2 : String) {

    //Loading models
    val m1 = ModelFactory.createDefaultModel
    Logger.info("Loading model 1")
    m1.read(model_1)

    if (!model_2.isEmpty) {
        Logger.info("Loading model 2")
        val m2 = ModelFactory.createDefaultModel
        m2.read(model_2)
        m1.add(m2)
        m2.close()
    }

    //Preparing the merged InputStream
    val outstr = new ByteArrayOutputStream()
    m1.write(outstr,"NT")
    val instr = new ByteArrayInputStream(outstr.toByteArray)

    //Loading the model on memory
    o = new MyOWLOntology(instr, "http://dbpedia.org", "HermiT")

    m1.close()
    outstr.close()
    instr.close()

    Logger.info("GADES successfully initialized!!!")

  }

  override def similarity(uri_1 :String, uri_2: String) : Double = {
    Logger.info(s"Computing similarity for $uri_1 and $uri_2")
    val individual_1 = o.getMyOWLIndividual(uri_1)
    val individual_2 = o.getMyOWLIndividual(uri_2)

    //Logger.info("taxSim:  "+individual_1.taxonomicSimilarity(individual_2))
    //Logger.info("neighSim:  "+individual_1.similarityNeighbors(individual_2))

    individual_1.similarity(individual_2)

  }

}