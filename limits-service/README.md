Toda vez que alteramos as configurações no github, precisamos atualizar nossos microserviços
através do seguinte comando:

http://{host}:{port}/application/refresh

Exemplo: http://localhost:8080/application/refresh

OBS: Sem isso os microserviços não serão atualizados com as configurações que foram atualizadas no github.


