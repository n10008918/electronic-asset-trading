CAB302 Semester 1 Assignement
EJ Akinsanmi, Jaewon Seo & Phi Long Nguyen
# electronic-asset-trading

Downloads
To run successfully run and test the application you will need the following installed:

MariaDB download here --> https://downloads.mariadb.org/


IntelliJ download here --> https://www.jetbrains.com/idea/download/#section=windows


JAVA 15 download here --> https://www.oracle.com/java/technologies/javase/jdk15-archive-downloads.html#license-lightbox



Setup and Start-up
1. Ensure java 15 is installed on your PC
2. Ensure the latest version of IntelliJ is installed on your PC
3. Install Maria DB for easy setup set server to 127.0.0.1 and port to 3306
4. For ease of use leave MariaDB username as root
5. Extract the zip folder
6. Open IntelliJ
7. Navigate to and open the project
8. Allow Maven to install dependencies and scripts
9. If script to run project doesn't not load: To run project navigate to src/main/.com.electronicAssetTrading and run ConnectToDb
10. To run test navigate to src/test/java/setup and change the password (under "ADD-PASSWORD-HERE") in MockDbConnection to the one you created for MariaDb
11. TIP: if your server address and port number are different you will have to change these too
12. Save and run tests
13. Everything should now work
