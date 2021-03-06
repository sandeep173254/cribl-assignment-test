package cribl.assignment.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class FileOperations {
	
	
	private static final int MIN_RANGE = 200000;
	private static final int MAX_RANGE = 700000;
	public int getFileCount(File fileName) throws IOException
	{
		InputStream inputStream = null;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(fileName));
			byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = inputStream.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        return (count == 0 && !empty) ? 1 : count;
	}
		finally {
		    if (inputStream != null) {
		        try {
		            inputStream.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}
	}
	
	public boolean isTargetCountWithinRange(int target_count)
	{
		boolean countWithinRange=false;
		if(target_count >= MIN_RANGE && target_count <= MAX_RANGE)
				countWithinRange=true;
		return countWithinRange;
				
	}
			
	
	public boolean checkFileDataMatches(File input_file , File output_file)
	{
		boolean verifyFileDataMatches=true;
		
		
        try (BufferedReader bf1 = new BufferedReader(new FileReader(input_file));
             BufferedReader bf2 = new BufferedReader(new FileReader(output_file))) {
 
                String line;
                while ((line = bf1.readLine()) != null)
                {
                    
                    String line2=bf2.readLine();
                	if (!line.equalsIgnoreCase(line2)) {
                    	verifyFileDataMatches=false;
                    	return verifyFileDataMatches;
                        
                    }
                }
            } catch (IOException e) {
            e.printStackTrace();
        }
        
		return verifyFileDataMatches;
	}
	
	
	/* Not Using File Merge Anymore
	 * 
	public File fileMerge(File target1_file, File target2_file) throws IOException
	{
		PrintWriter pw = new PrintWriter("opt/assignment/src/main/resources/output_events.log");
        BufferedReader reader = new BufferedReader(new FileReader(target1_file));
          
        String line = reader.readLine();
          
        while (line != null)
        {
            pw.println(line);
            line = reader.readLine();
        }
        reader.close();
          
        reader = new BufferedReader(new FileReader(target2_file));
          
        line = reader.readLine();
          
        while(line != null)
        {
            pw.println(line);
            line = reader.readLine();
        }
          
        pw.flush();
          
        // closing resources
        reader.close();
        pw.close();
          
        System.out.println("Merged Target file 1 and Target file 2 into output_events.log");
		return new File("opt/assignment/src/main/resources/output_events.log");
	}*/

}
