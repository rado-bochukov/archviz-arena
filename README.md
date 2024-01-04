# ArchVizArena
A place where ArchvizArtist can sowcase thier works and buyers can find the most suitable artist to visualize their dreamed project.

My Spring MVC project for the Spring Advanced course at SoftUni. (December 2023)

## How It Works
- Guest visitors:
  - browse projects and job publications;
  - browse artists and view their profiles;
- Logged Users (both artists and buyers):
  - like and comment projects;
  - report projects or users;
  - edit their profiles;
- Artists:
  - publish projects;
  - apply for job publications;
  - communicate with the buyer when their application is approved;
- Buyers:
  - add job publication;
  - activate or deactivate job publication;
  - delete job publication;
  - approve applicants on their job publications;
  - communicate with the artist who is approved;
- Admin:
  - browse user reports;
  - dismiss reports;
  - delete projects;
  - mute users;
 
## Technologies and tools used:
- Java 17
- Spring Boot 3.1.4
- MySQL
- HTML
- CSS
- Bootstrap
- Thymeleaf
- Cloudinary
  
## Configurations:
1. Data source username and password:
- These properties are set via enviroment variables and can be changed also in the `application.properties`
2. Seeding sample data:
- Once tha application is started and admin user will be configured with properties:
  - username: admin / password: 11111
3. Cloudinary Setup:
Can be found in the `application.proeprties` and is initially the properties are set as enviroment variables.

## Screenshots:
- Browsing artists:

![artist_browse](https://github.com/rado-bochukov/archviz-arena/assets/122348543/88d503d3-5873-439f-9a45-b675a4bcfa00)

- Artist profile:
  
![artist_profile](https://github.com/rado-bochukov/archviz-arena/assets/122348543/7574e949-0678-4dbc-a2f5-8d53dfed500e)

- Browsing projects:
  
![projects_browse](https://github.com/rado-bochukov/archviz-arena/assets/122348543/a1188307-3827-4c4c-a071-9490726d0640)

- Project details:
   
![project_detail](https://github.com/rado-bochukov/archviz-arena/assets/122348543/92bd1c0a-c47a-4121-82d1-286a37a549c4)
