package cribl.assignment.test;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TargetFileValidationTests {
	
	private File target1_file;
	private File target2_file;
	
	
	
	private static final Logger logger =
		      LogManager.getLogger(TargetFileValidationTests.class);
	
	@BeforeSuite(alwaysRun=true)
	public void verifyTargetFilePresent() throws Exception
	{
		System.out.println("*****Before Suite Setup*****");
		System.out.println("Verifying Target Files are present");
		String target_1fileName="target_1-events.log";
		String target_2fileName="target_2-events.log";
		ClassLoader classLoader = getClass().getClassLoader();
		target1_file = new File(classLoader.getResource(target_1fileName).getFile());
		target2_file = new File(classLoader.getResource(target_2fileName).getFile());
		
		String target1_absolutePath = target1_file.getAbsolutePath();
		String target2_absolutePath = target2_file.getAbsolutePath();
		

		System.out.println(target1_absolutePath);
		System.out.println(target2_absolutePath);

		Assert.assertTrue(target1_absolutePath.endsWith("/target_1-events.log"));
		Assert.assertTrue(target2_absolutePath.endsWith("/target_2-events.log"));
	}
		
		
	
	
	@Test(enabled=true ,description = "Verify file count for both Target ", retryAnalyzer = RetryAnalyzer.class)
	public void verifyFileCount() throws IOException
	{
		int target1_count = getFileCount(target1_file);
		int target2_count = getFileCount(target2_file);
		int total_count = target1_count + target2_count;
		
		Assert.assertEquals(total_count, 10);
	}
		
	
	public int getFileCount(File fileName) throws IOException
	{
		InputStream inputStream = null;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(fileName));
			byte[] c = new byte[1024];
	        int count = 1;
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
		
	
	

}
