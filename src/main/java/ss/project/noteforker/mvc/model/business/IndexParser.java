package ss.project.noteforker.mvc.model.business;

import java.util.HashMap;

public class IndexParser {
	
	/**
	 * 	Must Follow "Strict" Index Style
	 * 	1. Each line should end with a non-blank character.
	 * 	2. Use "Tab" to indent.
	 * 	3. Strict File System Hierarchy
	 * 
	 * 	Example for Point 3:
	 * 	
	 * 	[Valid]	
	 * 	Root
	 * 		Layer1
	 * 		Layer2		
	 * 			Layer3		
	 * 		Layer2
	 * 
	 * 	[Invalid]
	 * 	Root
	 * 		Layer1
	 * 			Layer2
	 *		Layer1
	 * 				Layer3
	 * 
	 * [Layer3 has no parent!]
	 * 
	 * 
	 * Return : HashMap<String, String>
	 *   Key  : Full Path of a file/folder
	 *  Value : Identifier to check if it's a file or a folder
	 * 
	 * 
	 * Last Modified: 2012/01/07 12:00 am
	 * 
	 * */
	
	public static HashMap<String, String> parseContent(String content){
		
		String[] files=content.split("\n");
		int layer=0; //0 is root!
		
		StringBuffer path=new StringBuffer("/");
		HashMap<String, String> result=new HashMap<String, String>();
		
		for(int i=0; i<files.length; i++){
			
			files[i]=files[i].replaceAll("(\t)*$", ""); //remove \t which is at the end of the line
			int start=files[i].indexOf('\t');
			
			if(start==-1){ //root layer
				
				if(layer!=0){
					path.delete(1, path.length()-1);
				}else;
				
				if(files[i].indexOf('.')!=-1){
					result.put("/"+files[i], "File");
				}else{
					result.put("/"+files[i], "Folder");
				}
				
			}else{
				
				int end=files[i].lastIndexOf('\t');
				files[i]=files[i].replaceAll("\t", "");
				
				if(end-start+1<layer){ //decrease layer
					
					int diff=layer-(end-start+1);
					for(int j=0; j<=diff; j++){
						path.delete(path.lastIndexOf("/"), path.length());
					}
					path.append('/');
					layer=end-start+1;
					
				}else if(end-start+1>layer){ //increase layer
					
					layer=end-start+1;
					result.put(path+files[i-1], "Folder");
					path.append(files[i-1]+"/");
				
				}else; //just at the layer
				
				result.put(path.toString()+files[i], "File");
			}
		}
		return result;
	}
	
}
