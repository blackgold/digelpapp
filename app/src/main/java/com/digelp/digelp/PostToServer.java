package com.digelp.digelp;

import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import android.util.Log;
import java.io.DataOutputStream;
import android.os.StrictMode;



public class PostToServer {
    private String surveyJson;
    private String serverUri;
    private HttpClient client;
    private HttpPost post;
    private static final String TAG = "PostToServer";


    public PostToServer(String _url, String body) {
        Log.e("MAIN", "PSTSVR()" + body);
        serverUri = _url;
        surveyJson = body;
    }


    public void post() {
        int serverResponseCode = 0;
        HttpsURLConnection conn = null;
        DataOutputStream dos = null;

        Log.e("MAIN", "PSTSVR POST" );
        try {
            conn = setUpHttpsConnection(serverUri);
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            //conn.setRequestProperty("Content-Type","application/json");
            //conn.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Auth", "xxxx");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.connect();

            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeUTF(URLEncoder.encode(surveyJson, "UTF-8"));
            //dos.writeChars(surveyJson);
            dos.flush();
            dos.close();

            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();

            Log.i(TAG, "HTTP Response is : "
                    + serverResponseMessage + ": " + serverResponseCode);
            /*
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(),"utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            System.out.println(""+sb.toString());
            */
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e.getMessage(), e);
        }
    }

    public static HttpsURLConnection setUpHttpsConnection(String urlString)
    {
        try
        {
            Log.e("MAIN", "getconn");
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = new BufferedInputStream(MainActivity.mContext.getAssets().open("deigelp.pem"));
            Certificate ca = cf.generateCertificate(caInput);
            System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());

            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            // Create an SSLContext that uses our TrustManager
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);

            // Tell the URLConnection to use a SocketFactory from our SSLContext
            URL url = new URL(urlString);
            HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
            urlConnection.setSSLSocketFactory(context.getSocketFactory());

            return urlConnection;
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Failed to establish SSL connection to server: " + ex.toString());
            return null;
        }
    }
}
