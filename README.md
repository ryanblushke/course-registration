# course-registration
Academic course registration tool consisting of Java frontend and MySQL backend.
# Usage
## Compile
```
cd src/
javac Startup.java
```
## Run
```
cd src/
java Startup
```
## Changing Database
In order to register students in courses the database must be specified in Driver.java.
```Java
public Driver() {
  url = "jdbc:mysql://example.ca:1234/directory";
  username = "";
  password = "";
  T1_Schedule = new Schedule(); // HOLDS TERM 1 SCHEDULE and Schedule from database
  T2_Schedule = new Schedule(); // HOLDS TERM 2 SCHEDULE and Schedule from database
  T1_Schedule_DB = new Schedule(); // HOLDS TERM 1 SCHEDULE JUST FROM DATABASE
  T2_Schedule_DB = new Schedule(); // HOLDS TERM 2 SCHEDULE JUST FROM DATABASE
}
```
