# Joe Silveira

To run the program....Run from gradle-> Tasks -> application -> "Run"
*Required*
Java 11+


This project allows us to ping the github jobs api and stack overflow jobs api and write the jobs to a databse.

The UI allows for the user to make all of the requests, view the jobs in a disaply format, and view all of the jobs on the map.

To fetch all of the jobs: In the UI click "Tasks" and click "Run all tasks"

To see the jobs in the database press "View Table" and press "next" to go through each job

To see the jobs on the map press the "search" button and apply your filters.


The filters need some work for multi-filtering but they will work individually.

The search by type and search by job age will work together.

The search by location option works best by itself but will work with the job type filter

The project includes tests to test databse functions and geocoding.

The project could use some cleaning up.. commenting methods, formatting large methods, removing un-used variables
etc. but given the time frame it works!




