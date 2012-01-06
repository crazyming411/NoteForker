package ss.project.noteforker.mvc.model.business;

import java.util.ArrayList;

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
	 * */
	
	public static ArrayList<String> parseContent(String content){
		
		String[] files=content.split("\n");
		int layer=0; //0 is root!
		StringBuffer path=new StringBuffer("/");
		ArrayList<String> result=new ArrayList<String>();
		
		for(int i=0; i<files.length; i++){
			int start=files[i].indexOf('\t');
			if(start==-1){ //root
				if(layer!=0){
					path.delete(1, path.length());
				}
				System.out.println("/"+files[i]);
				result.add("/"+files[i]);
			}else{
				int end=files[i].lastIndexOf('\t');
				files[i]=files[i].replaceAll("\t", "");
				
				if(end-start+1<layer){
					int diff=layer-(end-start+1);
					for(int j=0; j<=diff; j++){
						path.delete(path.lastIndexOf("/"), path.length());
					}
					path.append("/");
					layer=end-start+1;
				}else if(end-start+1>layer){
					layer=end-start+1;
					path.append(files[i-1]+"/");	
				}
				System.out.println(path.toString()+files[i]);
				result.add(path.toString()+files[i]);
			}
		}
		return null;
	}
}
