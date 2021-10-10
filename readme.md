# Request Log Analyzer
A coding demo by Chris Bamberry

## Overview
I have produced a basic Spring Boot command line application, which simply reports findings to the logs. A more prodictionised version might include spring batch processing and a repository layer for persisting the request details, and could export report files or etc.

I did not follow TDD for this project simply because I have been out of practice, but I have provided happy path unit test cases for at least one class. Naturally I would usually include testing error handling / negative paths.

Naturally a more collaborative approach would lead to a cleaner application with better structure, etc. 

## Function
Run the RequestAnalyzerApplication by passing:
* the name of at least one log file (such as the sample file located in the resources folder: sample-data/programming-task-example-data.log) as an argument, or multiple arguments referring to multiple files.
* The program requires an argument and will throw an exception if one is not thrown.
* It will also log an error:
  * if an argument is passed without .log, or
  * if the file is missing
  
## Out of scope
Based on time constraints:
* I did not handle failure to parse file, or missing files. 
* I also did not test the file reader class
* Finally, I did not perform integration testing or any BDD to verify the full application works