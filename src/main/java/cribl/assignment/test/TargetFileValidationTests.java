package cribl.assignment.test;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import cribl.assignment.util.FileOperations;
import cribl.assignment.util.RetryAnalyzer;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import java.io.File;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TargetFileValidationTests {
	
	private File target1_file;
	private File target2_file;
	private File input_file;
	FileOperations fo = new FileOperations();
	
	
	
	private static final Logger logger =
		      LogManager.getLogger(TargetFileValidationTests.class);
	
	@BeforeSuite(alwaysRun=true)
	public void verifyTargetFilePresent() throws Exception
	{
		System.out.println("*****Before Suite Setup*****");
		System.out.println("Verifying Target Files are present");
		String target_1fileName="target_1-events.log";
		String target_2fileName="target_2-events.log";
		String input_fileName="input_file.log";
		ClassLoader classLoader = getClass().getClassLoader();
		target1_file = new File(classLoader.getResource(target_1fileName).getFile());
		target2_file = new File(classLoader.getResource(target_2fileName).getFile());
		input_file = new File(classLoader.getResource(input_fileName).getFile());
		
		String target1_absolutePath = target1_file.getAbsolutePath();
		String target2_absolutePath = target2_file.getAbsolutePath();
		

		System.out.println(target1_absolutePath);
		System.out.println(target2_absolutePath);
		

		Assert.assertTrue(target1_absolutePath.endsWith("/target_1-events.log"));
		Assert.assertTrue(target2_absolutePath.endsWith("/target_2-events.log"));
	}
		
		
	
	
	@Test(enabled=true ,singleThreaded=true,description = "Verify file count for both Target ", retryAnalyzer = RetryAnalyzer.class)
	public void verifyTotalFileCount() throws IOException
	{
		int target1_count = fo.getFileCount(target1_file);
		int target2_count = fo.getFileCount(target2_file);
		int total_count = target1_count + target2_count;
		
		Assert.assertEquals(total_count, 10);
	}
	
	
	@Test(enabled=true ,singleThreaded=true, description = "Verify Random Splitting Ranges ")
	public void verifyFileSplittingRangesTarget1() throws IOException
	{
		boolean fileSplittingWithinRange=false;
		int target1_count = fo.getFileCount(target1_file);
		int target2_count = fo.getFileCount(target2_file);
		if((target1_count >=20000 && target1_count <= 70000) && (target2_count >=20000 && target2_count <= 70000))
			fileSplittingWithinRange=true;
		
		Assert.assertTrue(fileSplittingWithinRange);
	}
	
	@Test(enabled=true ,singleThreaded=true,description = "Verify File Data Input/Output Match ")
	public void verifyFileDataInputOutput() throws IOException
	{
		//File output_file = fo.fileMerge(target1_file,target2_file);
		boolean file_match = fo.checkFileDataMatches();
		Assert.assertTrue(file_match);
	}
	
	
		
	
	

}
