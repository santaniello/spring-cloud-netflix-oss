Toda vez que alteramos as configurações no github, precisamos atualizar nossos microserviços
através do seguinte comando:

http://{host}:{port}/application/refresh

Exemplo: http://localhost:8080/application/refresh

OBS: Sem isso os microserviços não serão atualizados com as configurações que foram atualizadas no github.

==========================================================

Usando spring-cloud bus

O bus atualiza varias instâncias do mesmo micro serviço de uma só vez...

basta executarmos a  seguinte url:

http://localhost:8080/bus/refresh

Não esqueça de adiocionar a seguinte dependência ao projeto....

	<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-bus-amqp</artifactId>
		</dependency>


