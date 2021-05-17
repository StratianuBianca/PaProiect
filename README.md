Correct an address - Advanced Programming Project

Authors: Lazar Catalina, 2A6
         Stratianu Bianca, 2A6

Introduction
  The purpose of this project is to correct an address given by the user in order to correspond to a real address.
Example: Romania, Iasi, Dorohoi -> Romania, Botosani, Dorohoi
  The project is build on a SpringBootWeb base, use Neo4j to store data, JSP to create the interface and Docker images to deploy it.

Details about project components:
1. Logic - The logic is divided into parsing the input and checking the parsed input in order to determine the correct address. The input is transformed into a standard: without diacritics and only the first letter of a word is upper case, also on this step are eliminated all words which for sure can not be a location as token which includes numbers and dots. Each word is looked up in the database to verify if it is a real location and receive a score.
In the second part the connections between locations are checked in order to determine the best address from the input and complete it if some fields are missing. (Lazar Catalina)

2. API - It is the layer between client and logic part and it contains only one http method: POST, because a client is able only to sent an address and as a response to receive
the corrected version or the same address if it is correct. (Stratianu Bianca)

2. Database connection - The connection is created with Spring Data Neo4j. The data is managed through entities and repositories. (Lazar Catalina)

3. Docker deployment - The docker image is formed of two containers, one for the database and the other for the server side.(Stratianu Bianca)

4. Interface - Created with Java EE(JSP). (Stratianu Bianca)

5. Unit & Integration Testing - There are unit test for the input parsing step and integration test working with the database in order to correct an address.
