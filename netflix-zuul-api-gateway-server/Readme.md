#  Netflix-Zuul 

Para chamarmos uma aplicação através do zuul, devemos seguir o seguinte padrão abaixo:

http://{host}:{zuulport}/{application-name}/{uri}

Exemplo:

http://localhost:8765/currency-exchange-service/currency-exchange/from/EUR/to/INR
