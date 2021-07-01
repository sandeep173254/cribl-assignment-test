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

docker run --hostname agent --net cribl_assignment --name agent sandeep173254/assignment:test_nodeapp_agent

echo "Count Target_1 event.log"
docker exec -it target_1 sh -c cat events.log | wc -l

echo "Count Target_2 event.log"
docker exec -it target_2 sh -c cat events.log | wc -l

echo "Removing the containers..."
docker stop target_1
docker stop target_2
docker rm target_1
docker rm target_2
