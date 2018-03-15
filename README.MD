#Simple HTTP Server
This server application presents list of directories and files, and its 
content from working directory.

## Prerequisites
- Java 8
- Maven 3
## Basic Architecture
This application has main class, SimpleHttpServer, which uses 
Java ServerSocket to get request and process it.
### Controller
For now, there is only MainController accepting path as argument. 
The Controller is dedicated to get path information
, and return rendered HTML content as String.

This MainController also have a method to generate HTML content for
an exception thrown by application. (e.g. MethodNowAllowedException. FileNotFoundException)
### Service
There is one service defined for this application. The FileService creates
a file object, do basic check, and decide which Template to use for rendering.
### Template
Template is to render HTML content using File object passed through.
There are 2 implementation of Template interface which are DirectoryTemplate 
and FileTemplate.
DirectoryTemplate renders list of directories, and files, and FileTemplate renders
content of file.
Template interface holds HTML_TEMPLATE variable which defines basic styling for
HTML page rendered.
### Exception
When there are situation need to be handled as an error, a custom Exception can be defined
and passed to MainController to get HTML content for it.
## Running the Application
Run the command below to clean, compile, and generate jar file.
```bash
mvn clean compile package
```
The jar file will be created in target folder.

To run the application run the command below.
```bash
java -jar target/simplehttpserver-1.0-SNAPSHOT.jar [port number]
```
If no port number is passed, 8080 will be used as a default port.

To stop the application, press \<Ctrl\>+C.
## Future Improvement
- Junit test for each feature need to be added
- Better handling exceptions and errors
- Rendering HTML content need to be done in Controller level, not Service level since
it is preventing Service being re-used for other use
 (e.g. Mobile application, Desktop application)
