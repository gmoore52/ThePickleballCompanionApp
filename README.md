Hello! Thank you for viewing the Pickleball Webapp

In order to run the program, you need to run the backend as well as the frontend.
You must also have node.js installed with npm
You need a postgres server with the public schema and a user postgres with password postgres that has access to the public schema

To run the frontend, CD into the program's folder ( For example, here is my path C:\Users\William\Desktop\Pickleball\ThePickleballCompanionApp\PickleballCompanionAppUI> )
You then need to run the command 'npm run dev' to start the frontend

For the backend, you must run 'gradle build' in the command line 
For the backend, we used IntelliJ which has Gradle support built in, and we pressed the play/debug button in order to start the program

You then must run DDL.sql in src/main/ddl to create the database structure 
To create some default data to work with, run Adding.SQL in the same folder 

The stats page starts displaying information once the player that is being viewed has one loss, one win, and one four player game logged