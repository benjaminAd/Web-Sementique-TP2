import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

public class QueryLocalRDF {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {


        String q2_1 =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                        + "PREFIX mov: <http://www.lirmm.fr/ulliana/movies#> "
                        + "SELECT * "
                        + "WHERE {?s ?p ?o}";


        String q2_2 =

                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                        + "PREFIX mov: <http://www.lirmm.fr/ulliana/movies#> "
                        + "SELECT ?movies "
                        + "WHERE{{ ?movies rdf:type mov:Movie .  }"
                        + "  UNION { ?movies mov:title ?x . }"
                        + "  UNION { ?movies mov:directedBy ?x . }"
                        + "  UNION { ?x mov:playsIn ?movies . }}";

        String q2_3 =
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
                        + "PREFIX mov: <http://www.lirmm.fr/ulliana/movies#> "
                        + "SELECT ?s "
                        + "WHERE {?s rdfs:subClassOf mov:Artist}";

        /**
         *
         * Create a data model and load file
         *
         */


        Model model = ModelFactory.createDefaultModel();

        //Remplacer par le chemin correspondant
        String dataset = "C:\\Users\\adolp\\OneDrive\\Bureau\\Fac\\Master\\M1\\S2\\Web-Sémentique\\Tp\\TP2\\Movies.ttl";

        InputStream in = FileManager.get().open(dataset);

        model.read(in, null, "TTL");

        /**
         *
         * Create a query object
         *
         */


        Query query = QueryFactory.create(q2_3);


        QueryExecution qexec = QueryExecutionFactory.create(query, model);


        /**
         *
         * Execute Query and print result
         *
         */

        try {

            ResultSet rs = qexec.execSelect();

            ResultSetFormatter.out(System.out, rs, query);

        } finally {

            qexec.close();
        }


    }
}