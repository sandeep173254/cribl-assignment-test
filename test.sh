#!/bin/bash
echo "Deploying hosts..."

echo "Deploying targets"

docker network create cribl_assignment
docker run --hostname target_1 --net cribl_assignment --name target_1 sandeep173254/assignment:test_nodeapp_target
docker run --hostname target_2 --net cribl_assignment --name target_2 sandeep173254/assignment:test_nodeapp_target


echo "Removing the containers..."
docker stop target_1
docker stop target_2
docker rm target_1
docker rm target_2
