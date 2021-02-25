import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.*;

import java.io.FileWriter;
import java.io.IOException;

public class Movies {

    private static String movies = "http://www.lirmm.fr/ulliana/movies#";
    private static String dbp = "http://dbpedia.org/";

    public static void main(String[] args) throws IOException {
        Model model = ModelFactory.createDefaultModel();
        model.setNsPrefix("movies", movies);
        model.setNsPrefix("dbp", dbp);
        model.setNsPrefix("dc", DC.getURI());
        model.setNsPrefix("rdfs", RDFS.getURI());
        model.setNsPrefix("owl", OWL.getURI());
        model.setNsPrefix("rdf", RDF.getURI());

        Property directedBy = model.createProperty(movies + "directedBy");
        Property title = model.createProperty(movies + "title");
        Property playsIn = model.createProperty(movies + "playsIn");

        Resource Artist = model.createResource(movies + "Artist");
        Resource Movie = model.createResource(movies + "Movie");
        Resource Actor = model.createResource(movies + "Actor");
        Resource Director = model.createResource(movies + "Director");

        Actor.addProperty(RDFS.subClassOf, Artist);
        Director.addProperty(RDFS.subClassOf, Artist);

        directedBy.addProperty(RDFS.domain, Movie);
        directedBy.addProperty(RDFS.range, Director);

        title.addProperty(RDFS.domain, Movie);
        title.addProperty(RDF.type, OWL.DatatypeProperty);

        playsIn.addProperty(RDFS.domain, Actor);
        playsIn.addProperty(RDFS.range, Movie);

        Resource m1 = model.createResource(movies + "m1");
        Resource m2 = model.createResource(movies + "m2");
        Resource m3 = model.createResource(movies + "m3");
        Resource a1 = model.createResource(movies + "a1");

        // Fichier TURTLE
        FileWriter TURTLEOUT = new FileWriter("src/main/resources/Text.ttl");
        try {
            model.write(TURTLEOUT, "TURTLE");
        } finally {
            try {
                TURTLEOUT.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

}
