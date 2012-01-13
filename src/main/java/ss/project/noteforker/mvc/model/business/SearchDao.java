package ss.project.noteforker.mvc.model.business;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import ss.project.noteforker.mvc.ModelAwareServlet;
import ss.project.noteforker.mvc.model.domain.Note;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class SearchDao extends ModelAwareServlet<Note>{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		String queryString=req.getParameter("q");
		System.out.println("QueryString = "+ queryString);
		
		Objectify ofy = ObjectifyService.begin();
		List<Note> noteList = ofy.query(Note.class).list();
		
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
		
		Directory index = new RAMDirectory();
		
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35, analyzer);
	    IndexWriter indexWriter = new IndexWriter(index, config);
	    
	    for(Note note : noteList){
	    	addDoc(indexWriter, note.getTitle(), note.getContent());
	    }
	    
	    indexWriter.close();
	    
	    Query queryTitle=null;
	    Query queryContent=null;
	    try {
	    	queryTitle = new QueryParser(Version.LUCENE_35, "title", analyzer).parse(queryString);
	    	queryContent = new QueryParser(Version.LUCENE_35, "content", analyzer).parse(queryString);
	    	
	    } catch (ParseException e) {
			
			e.printStackTrace();
		}
	    
	  //TODO - SEO
	    
	    queryTitle.setBoost(2.0f);
	    queryContent.setBoost(1.0f);
	    
	    Query query = queryTitle.combine(new Query[]{queryContent, queryTitle});
	    
	    IndexReader indexReader = IndexReader.open(index);
	    int hitsPerPage = 10;
	    
	    IndexSearcher indexSearcher = new IndexSearcher(indexReader);
	    
	    TopScoreDocCollector topDocsCollector = TopScoreDocCollector.create(hitsPerPage, true);
	    
	    indexSearcher.search(query, topDocsCollector);
	    
	    
	    for(ScoreDoc sd : topDocsCollector.topDocs().scoreDocs){
	    	Document d=indexSearcher.doc(sd.doc);
	    	System.out.println(sd.score +": "+d.get("title")+"\n"+d.get("content"));
	    }
	    
	    indexSearcher.close();
	    
	    //TODO - Response
	}
	
	private static void addDoc(IndexWriter iw, String title, String content) throws IOException {
	    Document doc = new Document();
	    doc.add(new Field("title", title, Field.Store.YES, Field.Index.ANALYZED));
	    doc.add(new Field("content", content, Field.Store.YES, Field.Index.ANALYZED));
	    iw.addDocument(doc);
	  }
}
