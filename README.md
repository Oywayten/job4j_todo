### TODO List Project 
> \- a simple task tracker to create and track tasks.  
The application displays a list of all tasks. Tasks can be created, deleted and edited.

## About the application
+ The application has three layers: Controller, Service, Persistence;
+ On the page with a list of all tasks
  + the table displays the name, creation date and status (completed or not);
  + button for adding task "Add task";
  + three links: All, Completed, New - when translating by links, the table displays: all tasks, only completed or only new ones;
+ Clicking on a task will take you to a page with a detailed description;
+ On the page with a detailed description there are buttons: Done, Edit, Delete;
+ If you click on the "Execute" button, then the task will be transferred to the "Completed" state;
+ Button "Edit" - transfers the user to a separate page for editing;
+ Button "Delete" - deletes the task and goes to the list of all tasks.

## Technology stack:
+ Spring boot 2,
+ Thymeleaf 3,
+ Bootstrap 5,
+ Hibernate 5,
+ PostgreSql 14.

## Environment requirements:
+ Java 17, 
+ Maven 3.8, 
+ PostgreSQL 14

## Application pages:
+ ### Main page
<img src="src/main/resources/image/index.png" width="660" title="Index page" alt="Main page with project description"/>

+ ### All tasks
<img alt="Page with all tasks list" src="src/main/resources/image/all-tasks.png" title="All tasks" width="660"/>

+ ### Completed tasks
<img alt="Page with all completed tasks list" src="src/main/resources/image/completed-tasks.png" title="All completed tasks" width="660"/>

+ ### New tasks
<img alt="Page with all new tasks list" src="src/main/resources/image/new-tasks.png" title="All new tasks" width="660"/>

+ ### Task description
<img alt="Task description" src="src/main/resources/image/task-description.png" title="Page for view task full info" width="660"/>

+ ### Edit task
<img alt="Edit task" src="src/main/resources/image/edit-task.png" title="Page for edit task" width="660"/>

+ ### Add task
<img alt="Add task" src="src/main/resources/image/add-task.png" title="Page for add task to list" width="660"/>