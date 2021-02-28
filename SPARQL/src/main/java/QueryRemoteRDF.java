import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import com.hp.hpl.jena.query.*;

public class QueryRemoteRDF {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        String link = "<http://www.w3.org/2001/sw/SW-FAQ-feed.rdf>";
        String req1 = "PREFIX vCard: <http://www.w3.org/2001/vcard-rdf/3.0#> "
                + "PREFIX rss: <http://purl.org/rss/1.0/> "
                + "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
                + "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + ""
                + "SELECT ?titre " + "FROM " + link + " WHERE { " + " ?item rdf:type rss:item ." + " ?item rss:title ?titre" + "} ";

        String req2 = "PREFIX    vCard:    <http://www.w3.org/2001/vcard-rdf/3.0#>    "
                + "PREFIX    rss:    <http://purl.org/rss/1.0/>    "
                + "PREFIX    foaf:    <http://xmlns.com/foaf/0.1/>    "
                + "PREFIX    rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + ""
                + "SELECT    ?titre    "
                + "FROM " + link
                + "    WHERE    {    "
                + "    ?item    rdf:type    rss:channel    ."
                + "    ?item    rss:title    ?titre"
                + "}    ";

        String req3 = "PREFIX    vCard:    <http://www.w3.org/2001/vcard-rdf/3.0#>    "
                + "PREFIX    rss:    <http://purl.org/rss/1.0/>    "
                + "PREFIX    foaf:    <http://xmlns.com/foaf/0.1/>    "
                + "PREFIX    rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + ""
                + "SELECT    ?article    "
                + "FROM " + link
                + " WHERE    {    "
                + "    ?article    rdf:type    rss:item    "
                + "}    "
                + "LIMIT    11";

        String req4 = "PREFIX    vCard:    <http://www.w3.org/2001/vcard-rdf/3.0#>    "
                + "PREFIX    rss:    <http://purl.org/rss/1.0/>    " + "PREFIX    foaf:    <http://xmlns.com/foaf/0.1/>    "
                + "PREFIX    rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>" + "" + "SELECT    ?article    "
                + "FROM    " + link
                + "    WHERE    {    "
                + "    ?article    rdf:type    rss:item    " + "}    "
                + "OFFSET 2" + "LIMIT 1";

        String req10 = "PREFIX dc:<http://purl.org/dc/elements/1.1/>" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                " " +
                "SELECT ?f " +
                "FROM " + link
                + " WHERE { " +
                "?f dc:title ?o" +
                " }";

        String req11 = "PREFIX dc:<http://purl.org/dc/elements/1.1/>" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                " " +
                "SELECT DISTINCT ?s " +
                "FROM " + link
                + " WHERE { " +
                "?s dc:subject ?o " +
                " }" +
                "LIMIT 1";
        String req12 = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + " PREFIX dc: <http://purl.org/dc/elements/1.1/>"
                + " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + " PREFIX rss: <http://purl.org/rss/1.0/>"
                + "PREFIX swq: <http://www.w3.org/2001/sw/SW-FAQ#>"
                + "SELECT ?o"
                + " FROM <http://www.w3.org/2001/sw/SW-FAQ-feed.rdf>"
                + "WHERE { ?s dc:date ?o}";
        /**
         *
         * Notice that we do not set a database - this is a class for querying a remote
         * dataset (via HTTP).
         *
         *//*

         *//**
         *
         * Create a query object
         *
         */

        Query query = QueryFactory.create(req12);

        QueryExecution qexec = QueryExecutionFactory.create(query);

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