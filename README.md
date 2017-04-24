Time series analysis

The purpose of this project is to model a data sink on a data storage of your choice to allow time series analysis and populate it with a substantial amount of daily weather data (Manageable on a single machine) from National Climate Data Center. Once the data is in place carry out an experimental time series analysis to get some descriptive statistics about a trend of climate change.

Use Cassandra as NoSQL db storage and Apache Spark as distributed computation tool.
The request to the climate service are done in parallel from all the nodes progress is tracked in the db.
Temperature prediction is done using logistic regression from spark ml.


