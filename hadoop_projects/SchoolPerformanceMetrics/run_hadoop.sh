#!/bin/bash

# 🟡 Hadoop School Performance Metrics Setup Script

echo "🟡 Step 1: Setting environment variables"
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk
export HADOOP_HOME=/usr/local/hadoop
export PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin:$JAVA_HOME/bin

echo "🟡 Step 2: Verifying installations"
echo "Java version:"
java -version
echo "Hadoop version:"
hadoop version

echo "🟡 Step 3: Creating sample data"
echo "StudentID,Score,Attendance" > data.csv
echo "1,89,92" >> data.csv
echo "2,75,85" >> data.csv
echo "3,90,98" >> data.csv
echo "4,65,78" >> data.csv
echo "5,45,60" >> data.csv

echo "🟡 Step 4: Starting Hadoop services"
start-dfs.sh
start-yarn.sh

echo "🟡 Step 5: Creating HDFS directories"
hdfs dfs -mkdir -p /user/$(whoami)/SchoolPerformanceMetrics/input
hdfs dfs -mkdir -p /user/$(whoami)/SchoolPerformanceMetrics/output

echo "🟡 Step 6: Uploading dataset to HDFS"
hdfs dfs -put data.csv /user/$(whoami)/SchoolPerformanceMetrics/input

echo "🟡 Step 7: Compiling Java code"
javac -classpath `hadoop classpath` -d . SchoolPerformanceMetrics.java
jar -cvf SchoolPerformanceMetrics.jar *.class

echo "🟡 Step 8: Running MapReduce job"
hadoop jar SchoolPerformanceMetrics.jar SchoolPerformanceMetrics /user/$(whoami)/SchoolPerformanceMetrics/input /user/$(whoami)/SchoolPerformanceMetrics/output

echo "🟡 Step 9: Displaying results"
hdfs dfs -cat /user/$(whoami)/SchoolPerformanceMetrics/output/part-r-00000

echo "✅ Hadoop job completed successfully!"