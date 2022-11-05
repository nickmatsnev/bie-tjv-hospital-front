# Hospital System (frontend)
## FIT CTU BIE-TJV 2022 semester project,
### by Nikita Matsnev

## 1.  Description
    
This is a project for BIE-TJV implemented by Nikita Matsnev in 2022 winter semester.
The domain is hospital where Patients with known email(unique), name and surname can have 
Session at some specific time
and there are some Doctors at each session, and we know about them their name, surname and
specialization. we can determine actual time of Session after it is done and we track it by its status 
and each patient has a medical records book where all the performed sessions are seen and also the information 
about receiving doctors. Doctors can see finished and upcoming Sessions. They can create Session slots.
There are several constraints such as Doctor cannot be at two Sessions at the same or overlapping time.
Patient cannot attend several Sessions at the same or overlapping time.

### 1.1. Server
Server will be implemented in Java Spring. I wil use `jdk18` and `Java 17`. Spring Web will be used
to provide RestAPI.

### 1.2. Web client
I am planning on using the Reactive Stack and Spring WebFlux, and here it is.

### 1.3. Database
PostgresSQL will be used, I have already created a schema. It has two many-to-many relations.
![Scheme](databaseScheme.png)
## 2. Build
* [Official Gradle documentation](https://docs.gradle.org)
## 3. Run
* [Official Gradle documentation](https://docs.gradle.org)
## 4. Test
junit tests
