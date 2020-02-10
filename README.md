# Joe Silveira


To run the program, simply run "main"

To Change the API url:
Navigate to runtimeHandler Class and change the "api" variable

<h1>Project 1 of Senior Design and Development</h1>


//Depricated in project 2<br>
This project allows for the program to ping the "jobs.github.com" API and return all of the jobs listed in on the api.
The program compiles the list of job "Titles" and prints them out to a file the user specifies.

<h1>Project 2 of Senior Design and Development</h1><br>
This version allows the user to ping the "jobs.github.com" API and return all of the jobs titles listed. THe program then
compiles the list of job titles and adds them to an sqlite database "APIDB".

<h2>Program Structure:</h2>

The main class contains the main method to run the program.<br>
The main method calls "startProgram" from the "runtimeHandler" class<br>

The runtimeHandler class method "startProgram": does the following:
<pre>1. Displays a welcome message.
2. Initializes a progress bar and frame from the "mainScreen" class method.
3. Initliazes the APIDB database from the databseHandler class method "initJobsTitleTable"
    3.1 The "initJobsTitleTable" method calls the "createTable" method from the DBFunctions class
        and then creates the table "Job_Titles" adds the column "job_title" in the APIDB database.
            3.1.1: The "createTable" method calls the "connecttoDatabase" method from the "DatabaseConnection" Class
                                    the "createTable" method is a generic method to create any table the database given a tableName and 
                                    and sql statement.
4. Makes the API request from the method "pingAPI" method until there are less than 50 jobs on the page
5. Adds the Job Titles to the database 
</pre>
