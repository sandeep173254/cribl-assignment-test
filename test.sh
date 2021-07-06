#!/bin/bash
echo "Deploying hosts..."

echo "Deploying targets"

docker network create cribl_assignment
docker run -d --hostname target_1 --net cribl_assignment --name target_1 sandeep173254/assignment:test_nodeapp_target
docker run -d --hostname target_2 --net cribl_assignment --name target_2 sandeep173254/assignment:test_nodeapp_target

echo "Targets 1 & 2 deployed"

echo "Deploying Splitter"

docker run -d --hostname splitter --net cribl_assignment --name splitter sandeep173254/assignment:test_nodeapp_splitter

echo "Splitter  deployed"

echo "Deploying Agent"

docker run -d --hostname agent --net cribl_assignment --name agent sandeep173254/assignment:test_nodeapp_agent

echo "Deploying Automated Test container"

docker run -t -d --hostname automatedtests --net cribl_assignment --name automatedtests sandeep173254/assignment:automatedtests

echo "Copying files to Test Automation container"
docker cp agent:/Users/skhatri/Downloads/assignment/agent/inputs/large_1M_events.log .
docker cp large_1M_events.log automatedtests:/opt/assignment/src/main/resources/input_file.log
docker cp target_1:/Users/skhatri/Downloads/assignment/events.log .
docker cp events.log automatedtests:/opt/assignment/src/main/resources/target_1-events.log
rm events.log
docker cp target_2:/Users/skhatri/Downloads/assignment/events.log .
docker cp events.log automatedtests:/opt/assignment/src/main/resources/target_2-events.log


echo "Starting automation test suite"

docker exec automatedtests java -jar opt/assignment/CriblAssignment.jar opt/assignment/testng.xml



echo "Count Target_1 events.log"
docker exec  target_1 sh -c "cat events.log | wc -l"

echo "Count Target_2 events.log"
docker exec target_2 sh -c "cat events.log | wc -l"

echo "Removing the containers..."
docker stop target_1
docker stop target_2
docker stop splitter
docker rm target_1
docker rm target_2
docker rm splitter
