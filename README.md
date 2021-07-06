# cribl-assignment-test

This is an automation test suite with an inbuilt test orchestrator. It runs as a docker container and the test results are uploaded in the CI pipeline along with other artifacts ( input/output files from all the hosts)

There are 4 tests ( 1 suite level pre-requisite and 3 test cases ):

Pre-requisite:
Verify whether all the files are present in the container before starting the tests

Test Case 1:

Check the total file count (Target_1 + Target_2) is 1M

Test Case 2:

Check the Splitter functionality

Test Case 3:

Verify data Input/Output
 

**Approach**

The test orchestrator (shell script test.sh) will take care of all the deployment setup / teardown for the tests.
These are the steps performed in the test orchestrator:

Deployment:

1. Deploy Targets
2. Deploy Splitter
3. Deploy Agent
4. Deploy AutomatedTests

Setup:

1. Sorting input file
2. Copying input file into AutomatedTests container
3. Merging and Sorting the output files
4. Copying output file into AutomatedTests container

Run:

1. Start the Tests
2. Download the test results artifacts

Teardown:

1. Stop and Remove Targets
2. Stop and Remove Splitter
3. Stop and Remove Agent
4. Stop and Remove AutomatedTests


**Instructions for test execution outside of the CI environment**

Running the test automation suite is just as simple as calling a shell script. Here are the steps:

1. Clone the project and cd into Project_directory (cribl-assignment-test)
2. sh test.sh ( Run the shell script )
