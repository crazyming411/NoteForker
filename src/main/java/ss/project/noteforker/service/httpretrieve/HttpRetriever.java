package ss.project.noteforker.service.httpretrieve;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;


public class HttpRetriever{
	
	private static InputStream instream;
	
	public static String getContent(String url) throws ClientProtocolException, IOException{
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
		    instream = entity.getContent();
		}
		
		BufferedReader reader=new BufferedReader(new InputStreamReader(instream, "UTF-8"));
		StringBuffer result=new StringBuffer();
		String buf=new String();
		while((buf=reader.readLine())!=null){
			result.append(buf+"\n");
		}
		return result.toString();
	}
}
