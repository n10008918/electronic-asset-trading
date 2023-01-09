CAB302 Semester 1 Assignement
EJ Akinsanmi, Jaewon Seo & Phi Long Nguyen
# electronic-asset-trading
# Well Documented Requirement Document (Requirement, Priorities & Sprint Plan) 
[Requirements, detailed_design and sprintplan.docx](https://github.com/n10008918/electronic-asset-trading/files/10374029/Requirements.detailed_design.and.sprintplan.docx)

# Installation
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

# Demos
Logged in as user1 which belong to org00001 
![5](https://user-images.githubusercontent.com/81293394/211366069-935091f9-1691-490f-8833-440741863f2f.PNG)

Error message shown becasue orgnisation 1(org00001) owns an asset1 so we cannot buy asset1
![image](https://user-images.githubusercontent.com/81293394/211366334-af07d126-da39-4c0a-b139-00f78f243790.png)

Buy asset10, quantity of 50 
![image](https://user-images.githubusercontent.com/81293394/211366880-d6a5c831-9a3a-43be-8f10-8d67d3c6be5f.png)

New transaction createed at the end on trade tab (GUI refers from database table name trades) 
![image](https://user-images.githubusercontent.com/81293394/211367145-32892fed-349a-47c5-b83d-872dadef9d91.png)

![image](https://user-images.githubusercontent.com/81293394/211367267-a96a2a42-a0c1-4af5-a83d-f21d17880dc3.png)



Below is Database table_name: assets 

Before
![2](https://user-images.githubusercontent.com/81293394/211367561-d9865406-7d7f-4ae3-beb5-5ccb71ce042a.PNG)


After
![3](https://user-images.githubusercontent.com/81293394/211367464-290ee106-2e5b-4873-984d-1b94b613b4fa.PNG)
** We can clearly see quantity of asset10's quantity is reduced to 50 (cuz user1 bought 50) 


