# Fuertes, Yair Alejandro
# Contacto: alefuertes.96@gmail.com
# Weather

Weather se conecta a la API de AccuWeather para extraer información sobre el clima de diferentes ciudades del mundo. La API realiza consultas a la API de AccuWeather y guarda los resultados en una base de datos en memoria.

## Primeros Pasos

Para poder ejecutar el programa necesitaras

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Postman](https://www.postman.com/downloads/)

## Ejecutar la aplicacion localmente

Hay varias formas que tú puedes ejecutar una aplicacion de Spring Boot en tu maquina local. Una de ellos es dirigirte al metodo `main` en la clase ubicada en `com.vates.provincia.seguros.techchallenge.TechchallengeApplication` y ejecutarlo en tu IDE. 

Alternativamente tambien puedes usar el comando:

```shell
mvn spring-boot:run
```
## Pruebas

Para poder probar la API deje un archivo llamado `Weather API.postman_collection` en `src/main/resources`. Este archivo debe ser importado en la herramienta Postman desde `file -> import`

## Tecnologias utilizadas

- Java 1.8
- Maven 3.6.3
- Swagger
- Spring Boot 2.4.5
- Feign Client
