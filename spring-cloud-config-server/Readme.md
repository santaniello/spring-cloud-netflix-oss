# Spring Cloud Config Server

Descrição: Serviço que tem como objetivo centralizar todas as configurações de todos os microserviços.

Tecnologias utilizadas no projeto:

* Spring Cloud Configuration Client;
* Spring Cloud Configuration Server;

Segue o pom.xml do projeto:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.felipe.microservices</groupId>
	<artifactId>spring-cloud-config-server</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>
	<name>spring-cloud-config-server</name>
	<description>Demo project for Spring Boot</description>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.0.1.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
		<spring-cloud.version>Finchley.M9</spring-cloud.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-config-server</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-config</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>
	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
	<repositories>
		<repository>
			<id>spring-milestones</id>
			<name>Spring Milestones</name>
			<url>https://repo.spring.io/milestone</url>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</repository>
	</repositories>
</project>

```

A dependência abaixo indica que nós iremos seremos o servidor de configurações:

```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-config-server</artifactId>
</dependency>
```		

A dependência abaixo indica que nós iremos obter as configurações do projeto através do spring cloud configuration (spring-cloud-config-server):

```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

o repositório git-localconfig-repo é o repositório aonde teremos as configuraççoes de todos os nossos microserviços. Neste exemplo , foi criado um repositório local na mesma aplicação porém o comum é criar um repositŕoio separado no github e passarmos a uri do repositório.

Nesse repositŕio, possuimos os 3 arquivos abaixo:

* limist-service.properties;
* limits-service-hlg.properties;
* limits-service-prd.service;

Repare que limits-service é o nome da aplicação e -hlg, -prd são os profiles que nós passamos quando vamos executar a aplicação. O arquivo limist-service.properties é o arquivo default ou seja, se não passarmos nenhum profile ele vai pegar as configurações deste arquivo.

No arquivo application.properties, possuimos as configurações do nosso servidor de configurações:

abaixo segue o arquivo application.properties:

```
spring.application.name=spring-cloud-config-server
server.port=8888
spring.cloud.config.server.git.uri=file:///home/felipe/idea-IU-181.4203.550/workspace/spring-cloud-netflix-oss/spring-cloud-config-server/git-localconfig-repo
```

No parâmetro spring.cloud.config.server.git.uri nós informamos o endereço do nosso repositório.

**OBS: Existe uma outra maneira de informar o nosso repositório da aplicação que é utilizando o eureka discover, nós registramos o nosso servidor de configuração no eureka e ele realiza o discover dos microserviços para nós, sendo assim, nós não precisariamos passar a uri do nosso repositório.**

No exemplo abaixo, nós habilitamos o servidor de configuração:

```java
@EnableConfigServer
@SpringBootApplication
public class SpringCloudConfigServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringCloudConfigServerApplication.class, args);
	}
}
```

Abaixo, segue alguns artigos sobre o Spring Cloud Config:

* [Baeldung](http://www.baeldung.com/spring-cloud-configuration);
* [domineospring](https://domineospring.wordpress.com/2018/01/09/remote-configuration-com-spring-cloud-config/);
* https://medium.com/dev-cave/spring-cloud-config-48e423446ed8
