# ForoHub - API REST con Spring Boot

ForoHub es una API REST desarrollada con **Spring Boot** que permite la gestión de tópicos en un foro académico, similar al utilizado en la plataforma Alura. Esta API permite a los usuarios autenticados crear, consultar, actualizar y eliminar tópicos relacionados con cursos y dudas técnicas.

---

## Funcionalidades principales

- Registro de nuevos tópicos  
- Listado general de tópicos  
- Visualización detallada por ID  
- Actualización de tópicos existentes  
- Eliminación de tópicos  
- Autenticación de usuarios vía JWT (login con email y contraseña)

---

## Tecnologías utilizadas

- Java 21  
- Spring Boot 3.5.4  
- Spring Web  
- Spring Security  
- Spring Data JPA  
- JWT (JJWT)  
- MySQL 8  
- Flyway (migraciones de base de datos)  
- Lombok  
- Maven  

---

## Instalación y ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/DevBernal/foro_hub.git
cd foro_hub
```

### 2. Crear la base de datos

```sql
CREATE DATABASE foro_hub;
```

### 3. Configurar `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost/foro_hub
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA

api.security.secret=MiClaveSuperSecretaYSeguraParaJWT1234!
```

### 4. Ejecutar el proyecto

```bash
./mvnw spring-boot:run
```

> Flyway ejecutará las migraciones y se creará la estructura de la base de datos automáticamente.

---

## Autenticación y uso con JWT

### Login

**POST** `/login`

#### Request:

```json
{
  "email": "admin@foro.com",
  "contraseña": "123456"
}
```

#### Response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5..."
}
```

> Usa este token en el header `Authorization` para acceder a los endpoints protegidos:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5...
```

---

## Endpoints de la API

### 1. Crear nuevo tópico

**POST** `/topicos`

#### Request:

```json
{
  "titulo": "¿Cómo usar JPA con Spring Boot?",
  "mensaje": "Tengo dudas sobre las anotaciones.",
  "autor": "admin@foro.com",
  "curso": "Spring Boot"
}
```

#### Response:

```json
{
  "mensaje": "Tópico registrado correctamente."
}
```

---

### 2. Listar todos los tópicos

**GET** `/topicos`

#### Response:

```json
[
  {
    "id": 1,
    "titulo": "¿Cómo usar JPA con Spring Boot?",
    "mensaje": "Tengo dudas sobre las anotaciones.",
    "fechaCreacion": "2025-07-26T18:35:21",
    "status": "NO_RESPONDIDO",
    "autor": "admin@foro.com",
    "curso": "Spring Boot"
  }
]
```

---

### 3. Ver detalle de un tópico

**GET** `/topicos/{id}`

#### Response:

```json
{
  "id": 1,
  "titulo": "¿Cómo usar JPA con Spring Boot?",
  "mensaje": "Tengo dudas sobre las anotaciones.",
  "fechaCreacion": "2025-07-26T18:35:21",
  "status": "NO_RESPONDIDO",
  "autor": "admin@foro.com",
  "curso": "Spring Boot"
}
```

---

### 4. Actualizar un tópico

**PUT** `/topicos/{id}`

#### Request:

```json
{
  "titulo": "¿Cómo usar Spring Data JPA?",
  "mensaje": "Quiero entender más sobre JpaRepository.",
  "curso": "Spring Boot"
}
```

#### Response:

```json
{
  "mensaje": "Tópico actualizado correctamente."
}
```

---

### 5. Eliminar un tópico

**DELETE** `/topicos/{id}`

#### Response:

```json
{
  "mensaje": "Tópico eliminado correctamente."
}
```

---

## Headers requeridos

Todos los endpoints protegidos requieren el siguiente header:

```
Authorization: Bearer <token JWT>
```
