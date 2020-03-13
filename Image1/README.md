# Run Docker File

### Tested in Windows 10 with Docker Desktop and PowerShell
Steps:

* [Docker desktop running before launch comands]

* [Navigate to folder that contains Dockerfile and JAR file. Open a PowerShell command shell]

* [docker build -t repository-service-docker .](Generate image)

* [docker run -d -p 9081:9081 repository-service-docker](Run container)


### URLs

* [Swagger](http://localhost:9081/test1/swagger-ui.html)
* [H2-Console - "connect with default credentials--> URL:jdbc:h2:mem:AC"](http://localhost:9081/test1/h2-console)

### References
* [Gitlab4j API](https://git.nowcoder.com/59/2132141/tree/80c7a9675ba4379640c9b7dde9f7e5e2c56c0ad0#projectapi)