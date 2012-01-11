package ss.project.noteforker.service.httpretrieve;


import java.io.*;
import java.net.URL;


public class HttpRetriever{
	
	private static InputStream instream;
	
	public static String getContent(String urlString) throws IOException{
		
		URL url=new URL(urlString);
		instream = url.openStream();
		
		BufferedReader reader=new BufferedReader(new InputStreamReader(instream, "UTF-8"));
		StringBuffer result=new StringBuffer();
		String buf=new String();
		while((buf=reader.readLine())!=null){
			result.append(buf+"\n");
		}
		return result.toString();
	}
}
