# NYC Taxi Data Analysis Using Hadoop

## About the Project

This project focuses on analyzing **1 million NYC taxi trip records** using the Hadoop ecosystem.

The dataset is stored in **HDFS** and analyzed using:

* Hadoop MapReduce
* Apache Pig

The main goal of this project is to understand how a large dataset can be stored, processed, and analyzed using big data tools.

## Dataset

The project uses the **NYC Taxi 1M dataset** in CSV format.

Some of the main columns are:

* Vendor ID
* Pickup Date & Time
* Drop-off Date & Time
* Passenger Count
* Trip Distance
* Fare Amount
* Tip Amount
* Payment Type
* Total Amount

Dataset: https://huggingface.co/datasets/oscur/taxisvis1M

## Tools Used

* Hadoop
* HDFS
* MapReduce
* Apache Pig
* Java
* Pig Latin

## Analyses Performed

Three different analyses were performed using both MapReduce and Apache Pig.

### 1. Tip Percentage Analysis

This analysis calculates the percentage of the fare given as a tip.

```text
Tip Percentage = (Tip Amount / Fare Amount) × 100
```

This helps understand the general tipping behavior of taxi passengers.

### 2. Passenger Count Distribution

This analysis counts the number of taxi trips for different passenger counts.

For example:

```text
1 passenger → Number of trips
2 passengers → Number of trips
3 passengers → Number of trips
```

This gives an idea of how people generally use taxi services.

### 3. Trip Duration Analysis

This analysis calculates the duration of taxi trips using the pickup and drop-off timestamps.

```text
Trip Duration = Drop-off Time - Pickup Time
```

The average trip duration is then calculated from the available records.

## Project Structure

```text
MapReduce/
│
├── taxisvis1M.csv
│
├── TipPercentageTaxi.java
├── PassengerCountTaxi.java
├── TripDurationTaxi.java
│
├── TipPercentage.pig
├── PassengerDistribution.pig
├── TripDuration.pig
│
├── TipPercentageTaxi.jar
├── PassengerCountTaxi.jar
└── TripDurationTaxi.jar
```

## HDFS

The dataset was uploaded to the following HDFS location:

```text
/pritam/taxisvis1M.csv
```

To check the file:

```bash
hdfs dfs -ls /pritam
```

## MapReduce

The MapReduce programs are written in Java.

### Programs

```text
TipPercentageTaxi.java
PassengerCountTaxi.java
TripDurationTaxi.java
```

The Java programs are compiled and packaged into JAR files before running them with Hadoop.

Example:

```bash
hadoop jar TipPercentageTaxi.jar TipPercentageTaxi /pritam/taxisvis1M.csv /TipOut
```

View the output:

```bash
hdfs dfs -cat /TipOut/part-r-00000
```

## Apache Pig

The Pig scripts are:

```text
TipPercentage.pig
PassengerDistribution.pig
```

Example:

```bash
pig -x local TipPercentage.pig
```

Pig was used to perform data loading, filtering, grouping, calculations, and aggregation.


## What I Learned

Through this project, I gained practical experience with:

* Uploading and managing data in HDFS
* Writing Java MapReduce programs
* Writing Pig scripts
* Running Hadoop jobs
* Working with a large CSV dataset
* Performing data analysis using Hadoop

The project also helped me understand the difference between MapReduce and Pig. MapReduce provides more control over the processing logic, while Pig makes common data analysis tasks simpler and quicker to implement.

## Conclusion

This project gave me hands-on experience in processing a real-world taxi dataset using Hadoop. By using both MapReduce and Pig, I performed different types of analysis and understood how large datasets can be processed in a Hadoop environment. Overall, the project helped me understand the basic workflow of storing, processing, and analyzing big data.

## Author

**Pritam**

Big Data Analytics Lab Project
