# CSC-440-Project
Fall 2017 CSC 440 Project

You have been hired to develop a software system for displaying students’ grades online.
This system will be used by students, professors, and TAs. All types of users are required to log
in upon accessing the system. Once logged in, the different users can do the following:
 Students will be able to view the grades for individual assignments for each of the
courses they are taking. A student should not be able to gain access to grades of any
other student.
 Professors and TAs will have access to the rosters for each of the courses they are
teaching/grading. They will be able to enter grades for individual students for any of the
assignments associated with the course.
 In addition, professors are able to add assignments to the course and remove assignments
from the course. An assignment can be removed only if no grades have been entered for
it. Also, professors can modify grades that have previously been entered (but TAs
cannot; TAs can only enter new grades).
 Information about courses and registered students will be fetched from an existing
database, maintained externally. The system must have a synchronization agent, capable of
removing grades of students who drop out of a course and removing courses that are finished.
The system will be responsible for storing the assignments for each course grades for each
assignment and student.
 The customer does not care about the specifics of the user interface design of the system
as of yet. The customer has initially stated they want an interface that is intuitive and easy to
use, with information displayed labeled and easy to read. 
