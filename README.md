# Tienda Cool

Aplicación desarrollada con Spring Boot, Java y MySQL utilizando Docker Compose para el entorno de desarrollo.

## Tecnologías

* Java 21
* Spring Boot 4.1.1
* Maven 3.9.9 mediante Maven Wrapper
* MySQL 26.7
* Docker
* Docker Compose

## Requisitos

Antes de levantar el proyecto es necesario tener instalados:

* Docker
* Docker Compose

El proyecto utiliza Maven Wrapper, por lo que **no es necesario instalar Maven localmente**.

## Configuración

El entorno de desarrollo utiliza variables de entorno para las credenciales de MySQL.

Crear un archivo `.env` en la raíz del proyecto y agrega credenciales:

```env
copy .env.example .env
```

El archivo `.env` no debe subirse al repositorio.


## Levantar el entorno de desarrollo

Desde la raíz del proyecto:

```bash
docker compose -f compose.dev.yaml up --build
```

La primera ejecución puede tardar más porque Docker necesita construir la imagen de desarrollo y descargar las dependencias de Maven.

Después de iniciar correctamente los servicios, la aplicación estará disponible en:

```text
http://localhost:8080
```

### Ejecución

El comando utilizado anteriormente sólo la primera vez:

```bash
docker compose -f compose.dev.yaml up --build -d
```

Para la siguientes ocasiones basta con usar:

- Levantar sin reconstruir (-d=detached opcional)

```bash
docker compose -f compose.dev.yaml up -d
```

- Detener y eliminar contenedores

```bash
docker compose -f compose.dev.yaml down
```

## Comandos útiles

### Ver el estado de los contenedores

```bash
docker compose -f compose.dev.yaml ps
```

### Ver logs

```bash
docker compose -f compose.dev.yaml logs
```

### Seguir los logs en tiempo real

```bash
docker compose -f compose.dev.yaml logs -f
```

### Ver únicamente los logs de Spring Boot

```bash
docker compose -f compose.dev.yaml logs -f app
```

### Ver únicamente los logs de MySQL

```bash
docker compose -f compose.dev.yaml logs -f mysql
```

## Flujo recomendado durante el desarrollo

El flujo normal será:

```bash
docker compose -f compose.dev.yaml up --build
```

Dejar esa terminal ejecutándose mientras se trabaja.

Después:

1. Modificar el código.
2. Guardar los cambios.
3. Esperar el reinicio automático de Spring Boot DevTools.
4. Probar los cambios desde Postman.
5. Detener y eliminar los contenedores

## Maven Wrapper

El proyecto incluye Maven Wrapper:

```text
mvnw
mvnw.cmd
.mvn/
```

Por lo tanto, para ejecutar Maven directamente en el equipo local se puede utilizar:

```bash
./mvnw clean test
```

No es necesario instalar Maven globalmente.

Durante el desarrollo con Docker, Maven se ejecuta dentro del contenedor mediante:

```bash
./mvnw spring-boot:run
```

El directorio de dependencias de Maven se conserva en el volumen:

```text
maven-cache
```

Esto evita descargar nuevamente todas las dependencias cada vez que se recrea el contenedor.

## Nota sobre producción

Actualmente el proyecto cuenta con archivos separados para desarrollo y producción:

```text
Dockerfile.dev
compose.dev.yaml

Dockerfile
compose.yaml
```

Por el momento, el flujo documentado en este README corresponde únicamente al entorno de **desarrollo**.

El entorno de producción se configurará posteriormente.
