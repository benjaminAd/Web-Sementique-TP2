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


        String queryMailOfTimsFriends =

                "PREFIX foaf:  <http://xmlns.com/foaf/0.1/> "
                        + "PREFIX card: <http://www.w3.org/People/Berners-Lee/card#> "
                        + "SELECT ?homepage "
                        + "FROM <http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf> "
                        + "WHERE {"
                        + "       card:i foaf:knows ?known ."
                        + "       ?known foaf:homepage ?homepage . }";


        String queryFederatedDataSet =

                "PREFIX movie: <http://data.linkedmdb.org/resource/movie/> "
                        + "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
                        + "PREFIX dbpedia: <http://dbpedia.org/ontology/> "
                        + "SELECT * "
                        + "FROM <http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf>  "
                        + "WHERE {"
                        + "  "
                        + "  SERVICE <http://data.linkedmdb.org/sparql> {"
                        + "    <http://data.linkedmdb.org/resource/film/675> movie:actor ?actor ."
                        + "    ?actor movie:actor_name ?actor_name"
                        + "  }  "
                        + "  BIND(STRLANG(?actor_name_imdb, \"en\") AS ?actor_name_en) "
                        + "   "
                        + "  SERVICE <http://dbpedia-live.openlinksw.com/sparql?timeout=2000> { "
                        + "    SELECT * "
                        + "    WHERE {  ?actor2 a                  foaf:Person ; "
                        + "                     foaf:name          ?actor_name_en ;  "
                        + "                     dbpedia:birthDate  ?birth_date . "
                        + "             FILTER(STR(?actor_name_en) = ?actor_name)"
                        + "    } LIMIT 2"
                        + "  }"
                        + "} LIMIT 2";


        String ListRSSFeedClasses =

                "PREFIX vCard: <http://www.w3.org/2001/vcard-rdf/3.0#> "
                        + "PREFIX rss: <http://purl.org/rss/1.0/> "
                        + "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
                        + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                        + ""
                        + "SELECT * "
                        + "FROM <http://www.w3.org/2001/sw/SW-FAQ-feed.rdf> "
                        + " WHERE { "
                        + " ?channel rdf:type rss:channel ."
                        + " ?channel rss:items ?itemset ."
                        + " ?itemset ?sequenceNumber ?item ."
                        + " ?item rdf:type ?type ."
                        + "} ";


        /**
         *
         * Create a data model and load file
         *
         */


        Model model = ModelFactory.createDefaultModel();

        String dataset = "C:\\Users\\adolp\\OneDrive\\Bureau\\Fac\\Master\\M1\\S2\\Web-Sémentique\\Tp\\TP2\\Movie.ttl";

        InputStream in = FileManager.get().open(dataset);

        model.read(in, null, "TTL");

        /**
         *
         * Create a query object
         *
         */


        Query query = QueryFactory.create(queryMailOfTimsFriends);


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