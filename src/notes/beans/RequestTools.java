package notes.beans;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

@SuppressWarnings( "deprecation" )
public class RequestTools {
    private final static String USER_AGENT = "Mozilla/5.0";

    @SuppressWarnings( "resource" )
    public static StringBuffer sendGet( String cookie, String ID ) throws Exception {

        String url = "https://portail.eseo.fr/+CSCO+10756767633A2F2F726672622D617267++/OpDotNet/Eplug/FPC/Process/Annuaire/Parcours/pDetailParcours.aspx?idProcess=13739&idUser="
                + ID + "&idIns=249461&idProcessUC=5772&typeRef=module";
        String certificatesTrustStorePath = "/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/security/cacerts";
        System.setProperty( "javax.net.ssl.trustStore", certificatesTrustStorePath );
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet request = new HttpGet( url );

        request.addHeader( "Cookie", cookie );
        request.addHeader( "User-Agent", USER_AGENT );
        HttpResponse response = null;
        try {
            response = httpclient.execute( request );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        System.out.println( "\nSending 'GET' request to URL : " + url );
        for ( Header tmp : request.getHeaders( "Cookie" ) ) {
            System.out.println( tmp );
        }
        // System.out.println( "Response Code : "
        // +response.getStatusLine().getStatusCode() );

        BufferedReader rd = new BufferedReader(
                new InputStreamReader( response.getEntity().getContent() ) );

        StringBuffer resultat = new StringBuffer();
        String line = "";
        while ( ( line = rd.readLine() ) != null ) {
            resultat.append( line );
        }

        return resultat;
    }

}
