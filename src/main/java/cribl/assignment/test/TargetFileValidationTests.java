package cribl.assignment.test;

import org.testng.annotations.Test;
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
	public static final String FILE_DIRECTORY = "/opt/assignment/src/main/resources/";
	FileOperations fo = new FileOperations();
	
	
	
	private static final Logger logger =
		      LogManager.getLogger(TargetFileValidationTests.class);
	
	@BeforeSuite(alwaysRun=true)
	public void verifyTargetFilePresent() throws Exception
	{
	
		System.out.print("*****Before Suite Setup*****");
		System.out.print("\nVerifying Target Files are present");
		target1_file = new File(FILE_DIRECTORY+"target_1-events.log");
		target2_file = new File(FILE_DIRECTORY+"target_2-events.log");
		input_file = new File(FILE_DIRECTORY+"input_file.log");
		
		String target1_absolutePath = target1_file.getAbsolutePath();
		String target2_absolutePath = target2_file.getAbsolutePath();
		String input_file_absolutePath = input_file.getAbsolutePath();
		
		Assert.assertTrue(target1_absolutePath.endsWith("/target_1-events.log"));
		Assert.assertTrue(target2_absolutePath.endsWith("/target_2-events.log"));
		Assert.assertTrue(input_file_absolutePath.endsWith("/input_file.log"));
		
	}
		
		
	
	
	@Test(enabled=true ,singleThreaded=true,description = "Verify file count for both Target ",retryAnalyzer = RetryAnalyzer.class)
	public void verifyTotalFileCount() throws IOException
	{
		System.out.print("\n*******Running Test Cases******");
		System.out.print("\nVerifying file total count for both Targets");
		int target1_count = fo.getFileCount(target1_file);
		int target2_count = fo.getFileCount(target2_file);
		int total_count = target1_count + target2_count;
		
		Assert.assertEquals(total_count, 1000000);
	}
	
	
	@Test(enabled=true ,singleThreaded=true, description = "Verify Random Splitting Ranges " ,dependsOnMethods = { "verifyTotalFileCount" },retryAnalyzer = RetryAnalyzer.class)
	public void verifyFileSplittingRangesTargets() throws IOException
	{
		System.out.print("\nVerifying file splitting ranges for both Targets");
		boolean fileSplittingWithinRange=false;
		int target1_count = fo.getFileCount(target1_file);
		int target2_count = fo.getFileCount(target2_file);
		if((target1_count >=200000 && target1_count <= 700000) && (target2_count >=200000 && target2_count <= 700000))
			fileSplittingWithinRange=true;
		
		Assert.assertTrue(fileSplittingWithinRange);
	}
	
	@Test(enabled=true ,singleThreaded=true,description = "Verify File Data Input/Output Match ", dependsOnMethods = { "verifyFileSplittingRangesTargets" },retryAnalyzer = RetryAnalyzer.class)
	public void verifyFileDataInputOutput() throws IOException
	{
		System.out.print("\nVerifying file data matches between both Targets and Agent");
		File output_file = new File (FILE_DIRECTORY+"/output_events.log");
		boolean file_match = fo.checkFileDataMatches(input_file,output_file);
		Assert.assertTrue(file_match);
	}
	
	
		
	
	

}
