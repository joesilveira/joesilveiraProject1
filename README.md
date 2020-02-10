# joesilveiraProject1
Project 1 of Senior Design and Development

Project 1
//Depricated in project 2
This project allows for the program to ping the "jobs.github.com" API and return all of the jobs listed in on the api.
The program compiles the list of job "Titles" and prints them out to a file the user specifies.

Project 2
This version allows the user to ping the "jobs.github.com" API and return all of the jobs titles listed. THe program then
compiles the list of job titles and adds them to an sqlite database "APIDB".

To run the program, simply run "main"

To Change the API url:
Navigate to runtimeHandler Class and change the "api" variable

Program Structure:

The main class contains the main method to run the program. 
The main method calls "startProgram" from the "runtimeHandler" class

The runtimeHandler class method "startProgram": does the following:
    1. Displays a welcome message.
    2. Initializes a progress bar and frame from the "mainScreen" class method.
    3. Initliazes the APIDB database from the databseHandler class method "initJobsTitleTable"
            3.1 The "initJobsTitleTable" method calls the "createTable" method from the DBFunctions class
                 and then creates the table "Job_Titles" adds the column "job_title" in the APIDB database. 
                    3.1.1: The "createTable" method calls the "connecttoDatabase" method from the "DatabaseConnection" Class
                            the "createTable" method is a generic method to create any table the database given a tableName and 
                            and sql statement.
                            
                            
    4. Makes the API request from the method "pingAPI"
        4.1: The "pingAPI" method calls the "makeGetRequest" method from HTTPRequest adn continues to call the "makeGetRequest"
              method until there are less than 50 jobs on the page
    5. Adds the Job Titles to the database 
                    
    
    


