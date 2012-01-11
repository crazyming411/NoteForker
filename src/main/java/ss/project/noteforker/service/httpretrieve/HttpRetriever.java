package ss.project.noteforker.service.httpretrieve;

import org.apache.http.client.ClientProtocolException;

import java.io.*;
import java.net.URL;


public class HttpRetriever{
	
	private static InputStream instream;
	
	public static String getContent(String urlString) throws ClientProtocolException, IOException{
		
		URL url=new URL(urlString);
		instream = url.openStream();
		/*HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
		    instream = entity.getContent();
		}
		*/
		BufferedReader reader=new BufferedReader(new InputStreamReader(instream, "UTF-8"));
		StringBuffer result=new StringBuffer();
		String buf=new String();
		while((buf=reader.readLine())!=null){
			result.append(buf+"\n");
		}
		return result.toString();
	}
}
