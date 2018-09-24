
package Data;

import java.io.File;
import javax.swing.JFileChooser;


public class MoFile {
     public static String st1="";

	    public MoFile() { 
	    	
	    } 
	    
	    public static void readFile() {
	    	JFileChooser fileChooser = new JFileChooser(); 

	    	int returnValue = fileChooser.showOpenDialog(null); 

	    	//Neu file duoc chon 
	    	if(returnValue==JFileChooser.APPROVE_OPTION) { 
	    	    File selectedFile = fileChooser.getSelectedFile(); 

	    	//in thu duong dan file 
	    	System.out.println("getPath : " + selectedFile.getPath()); 
	    	System.out.println("getAbsolutePath : " + selectedFile.getAbsolutePath()); 
	    	System.out.println("getName : " + selectedFile.getName()); 

	    	System.out.println("getName : " + fileChooser.getCurrentDirectory().getPath()); 
	    	String st=selectedFile.getPath(); 
	    	System.out.print("lay duong dan: "+st);

	    	for(int i=0;i<st.length();i++)
	    	    if(st.substring(i,i+1).equals("\\"))
	    	        st1=st1+"/";
	    	    else
	    	        st1=st1+st.substring(i,i+1);
	    	System.out.println("\nchuoi sau: "+st1);
	    	}  
}
            public static void main (String[] args) { 
		readFile();
	}
	

}
