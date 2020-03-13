# Run Docker File

### Tested in Windows 10 with Docker Desktop and PowerShell
Steps:

* [Docker desktop running before launch comands]

* [Navigate to folder that contains Dockerfile and JAR file. Open a PowerShell command shell]

* [docker build -t ci-service-docker .](Generate image)

* [docker run -d -p 9082:9082 ci-service-docker](Run container)


### URLs

* [Swagger](http://localhost:9082/test2/swagger-ui.html)
* [H2-Console - "connect with default credentials--> URL:jdbc:h2:mem:AC"](http://localhost:9082/test2/h2-console)