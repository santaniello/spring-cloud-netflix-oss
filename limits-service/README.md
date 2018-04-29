# Limits Service

Diagrama do projeto:

![image](https://github.com/santaniello/spring-cloud-netflix-oss/blob/master/limits-service/imgs/diagrama.png)

Rodando com um profile específico:

![image](https://github.com/santaniello/spring-cloud-netflix-oss/blob/master/limits-service/imgs/profiles.png)


Descrição: Serviço que tem como objetivo se conectar ao spring cloud configuration e obter as configurações de lá baseado em um profile específico.

Tecnologias utilizadas no projeto:

* Spring Cloud Configuration Client;
* Spring Cloud Bus; 
* Hystrix - Circuit Breaker;

Segue o pom.xml do projeto:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.felipe.microservices</groupId>
	<artifactId>limits-service</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>
	<name>limits-service</name>
	<description>Demo project for Spring Boot</description>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.0.1.RELEASE</version>
		<relativePath/> 
	</parent>
	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
		<spring-cloud.version>Finchley.M9</spring-cloud.version>
	</properties>
	<dependencies>
	    <!-- Comentado apenas por uma questão de performance...
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-config</artifactId>
		</dependency>
        <!-- Comentado apenas por uma questão de performance...
		<dependency>
		   <groupId>org.springframework.boot</groupId>
		   <artifactId>spring-boot-devtools</artifactId>
		   <scope>runtime</scope>
		</dependency> -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-bus-amqp</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
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

## Spring Cloud Configuration

A dependência abaixo indica que nós iremos obter as configurações do projeto através do spring cloud configuration (spring-cloud-config-server):

```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

Para habilitarmos a busca por configurações remotas em nosso projeto, precisamos criar o arquivo bootstrap.yml como abaixo:

```
#************************************************
# Nome da aplicação...                          *
# ----------------------------------------------*
spring.application.name=limits-service
#************************************************

#************************************************
# Desabilita a busca no spring config server... *
# ----------------------------------------------*
spring.cloud.config.enabled=false
#************************************************

#**************************************************
# Informando a URL remota do Spring cloud config  *
# ------------------------------------------------*
spring.cloud.config.uri=http://localhost:8888
#**************************************************

#*********************************************************************
# Informando de qual branch do git, vamos pegar as configurações ... *
# -------------------------------------------------------------------*
# spring.cloud.config.label=local                                    *
#*********************************************************************

#*********************************************************************
# Desabilita a segurança do spring boot actuator                     *
# -------------------------------------------------------------------*
# management.security.enabled=false
#*********************************************************************

```
Repare que a busca por configurações remotas está desabilitada através do parâmetro abaixo:

```
spring.cloud.config.enabled=false
```
Sendo assim, ele vai pegar as configurações do arquivo application.properties
```
spring.application.name=limits-service
limits-service.minimum=66
limits-service.maximum=6666
```

## Spring Cloud Bus

Artigo do **baeldung** sobre o que é spring cloud bus e como implementa-lo:

[spring cloud bus](http://www.baeldung.com/spring-cloud-bus)

Toda vez que alteramos as configurações no github, precisamos atualizar nossos microserviços (inclusive instâncias) através do seguinte comando:

http://{host}:{port}/application/refresh

Exemplo: http://localhost:8080/application/refresh

OBS: Sem isso os microserviços não serão atualizados com as configurações que foram atualizadas no github.
 
O spring cloud bus serve para atualizar todas as instâncias de um único microserviço de uma única vez. Para atualizar tods as instâncias de um microserviço, ele utiliza mensageria (neste exemplo nós utilizamos o rabbitmq para isso). 

Abaixo está a dependência utilizada:

```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
```

Para executarmos o spring cloud bus basta executarmos a seguinte url:

http://localhost:8080/bus/refresh

e todas as instâncias daquele microserviço serão atualizadas.

**OBS: Não se esqueça que devemos ter um servidor rabbitmq na maquina rodando e startado.**

Segue o link para instalação do rabbitmq: 

[RabbitMq](https://www.rabbitmq.com/install-debian.html)

## Hystrix (Circuit Breaker)

Artigo do **Felipe Adorno** sobre o que é Hystrix e como implementá-lo:

[Hystrix Circuit Breaker](https://fadorno.wordpress.com/2017/01/17/hystrix-circuit-breaker/)

Para usar o Hystrix, precisamos utilizar a dependência abaixo:
```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```

```java
@RestController
public class LimitsConfigurationController {
    @Autowired
    private Configuration conf;

    @GetMapping(path = "/limits")
    public LimitsConfiguration retrieveLimitsFromConfigurations(){
        return new LimitsConfiguration(conf.getMinimum(),conf.getMaximum());
    }

    @HystrixCommand(fallbackMethod = "fallbackRetrieveConfiguration")
    @GetMapping(path = "/fault-tolerance-example")
    public LimitsConfiguration retrieveConfigurations(){
        throw new RuntimeException("Not available");
    }

    public LimitsConfiguration fallbackRetrieveConfiguration(){
        return new LimitsConfiguration(9,99999);
    }
}
```

No exemplo acima, toda vez que o método retrieveConfigurations falhar, ele vai chamar o método fallbackRetrieveConfiguration que executará um comportamento default evitando assim que o microserviço fique fora do ar.
