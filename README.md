# Tienda de Ropa - Backend

API REST desarrollada con Spring Boot para dar soporte a una tienda de ropa online. Según la propia documentación OpenAPI del proyecto, su objetivo es ofrecer *"los distintos endpoints y pruebas para integrarlo a tu tienda online"*.

El backend cubre:

- **Catálogo de prendas en stock**: alta de prendas con imágenes, listado paginado, búsqueda por código y búsqueda por filtros (nombre, talle, rango de precio, tipo genérico y específico).
- **Usuarios**: registro, actualización de datos, cambio de contraseña, búsqueda por id, email o apellido, y usuarios **VIP**.
- **Lista de deseos (wishlist)**: cada usuario puede marcar prendas como deseadas, quitarlas y consultar si una prenda ya está en su lista.
- **Comentarios** de usuarios sobre las prendas, con borrado lógico.
- **Facturas (`Tax`)**: registro de compras con las prendas vendidas, costo de envío y dirección.
- **Pagos con Mercado Pago**: generación de una *preferencia* de pago a partir de los ítems del carrito.
- **Emails**: envío de correos vía SMTP de Gmail, notificación automática a los usuarios VIP cuando se publica una prenda nueva y recuperación de contraseña mediante un código enviado por email.
- **Seguridad** con Spring Security y JWT (roles `ROLE_USER` y `ROLE_ADMIN`).

El frontend esperado corre en `http://localhost:4200` (Angular), según las anotaciones `@CrossOrigin` de los controllers.

## Tecnologías

| Tecnología | Versión |
|---|---|
| Java | 17 |
| Spring Boot (parent) | 3.2.4 |
| Spring Web / Data JPA / Data REST / Validation / Security / Mail / Actuator | (gestionadas por Spring Boot 3.2.4) |
| MySQL (driver `mysql-connector-j`) | gestionada por Spring Boot |
| JJWT (`jjwt-api`, `jjwt-impl`, `jjwt-jackson`) | 0.12.3 |
| Jakarta Mail (`com.sun.mail:jakarta.mail`) | 2.0.1 |
| Mercado Pago SDK Java (`com.mercadopago:sdk-java`) | 2.1.7 |
| springdoc-openapi (Swagger UI) | 2.2.0 |
| Maven (wrapper incluido) / Docker | - |

## Estructura del proyecto

Paquete base: `com.example.tienda.ropa.tienda_ropa`

```
src/main/java/com/example/tienda/ropa/tienda_ropa
├── TiendaRopaApplication.java   # Main. Toma el puerto de la variable de entorno PORT si existe
├── Interface/
│   └── IValidation.java         # Contrato para devolver errores de validación (BindingResult -> 400)
├── classes/                     # DTOs y utilidades (no son entidades)
│   ├── clothe/   ClothePubli (prenda + asunto/mensaje para VIPs), ItemMercadoPago
│   ├── elements/ CommentInfo, GeneratedCode (código aleatorio de 5 letras), ResponseEntityGenerator
│   ├── enums/    EmailEnum (asunto y mensaje del email de recuperación de contraseña)
│   └── user/     EmailDTO, RestorePasswordDataDTO, UserCommentDTO, UserDto
├── config/
│   ├── EmailConfig.java         # JavaMailSender sobre smtp.gmail.com:587 (STARTTLS), lee email.properties
│   └── SwaggerConfig.java       # Metadatos OpenAPI ("Tienda de Ropa Backend", v1.0.1)
├── controllers/
│   ├── clothe/   ClothesStockController, MercadoPagoController
│   ├── elements/ CommentController, TaxController, GlobalExceptionHandler
│   └── user/     UserController, EmailController, PasswordController
├── entities/
│   ├── clothe/   Clothe (@MappedSuperclass), ClotheStock, ClotheSold
│   ├── elements/ Comment, Image, Tax, Wish
│   └── user/     User, Role, RestorePasswordData
├── repositories/                # Interfaces JpaRepository (consultas derivadas y JPQL)
├── security/
│   ├── SpringSecurityConfig.java   # Reglas de acceso por endpoint, CORS, sesión STATELESS, BCrypt
│   ├── JwtAuthenticationFilter.java# Login: valida credenciales y genera el JWT
│   ├── JwtValidationFilter.java    # Valida el JWT en cada request (salvo rutas públicas)
│   ├── TokenJwtConfig.java         # Clave, prefijo "Bearer ", header y rutas públicas del filtro
│   └── SimpleGrantedAuthoritiesJsonCreator.java
└── services/
    ├── clothe/   ClothesStockServices, MercadoPagoService
    ├── elements/ CommentService, TaxServices, JpaUserDEtailsService
    └── user/     UserService, EmailService, RestorePasswordService
```

Todas las respuestas de los services se construyen con `ResponseEntityGenerator`, que devuelve un JSON con la forma:

```json
{ "message": "texto descriptivo", "status": 200, "body": { } }
```

Los errores de validación (`@Valid`) devuelven `400` con un mapa `campo -> "El campo X ..."`, y `GlobalExceptionHandler` transforma cualquier excepción no controlada en un `500` con `{ "error": "<mensaje>" }`.

## Modelo de datos

`Clothe` es una `@MappedSuperclass` con los atributos comunes de una prenda; de ella heredan `ClotheStock` (prenda publicada en la tienda) y `ClotheSold` (copia de la prenda al momento de la venta, asociada a una factura).

| Entidad | Tabla | Atributos principales | Relaciones |
|---|---|---|---|
| `Clothe` (superclase) | - | `id` (UUID generado en `@PrePersist`), `code`, `name`, `size`, `price`, `description`, `genericType`, `specificType`, `publication` (fecha `yyyy-MM-dd`) | - |
| `ClotheStock` | `clothesStock` (`code` único) | + `stock` | 1:N `Image`, 1:N `Comment` |
| `ClotheSold` | `clothesSold` | + `cant` | N:M `Tax` |
| `Image` | `images` | `id` (UUID), `url` | N:1 `ClotheStock` (`id_clothes_stock`) |
| `Comment` | `comments` | `id` (autoincremental), `text`, `available` (borrado lógico) | N:1 `User` (`id_user`), N:1 `ClotheStock` (`id_clothe`) |
| `Tax` (factura) | `tax` (`code` único) | `id` (UUID), `code`, `price`, `travelCost`, `adress`, `date` | N:M `ClotheSold` (tabla `taxxclothesold`), N:1 `User` |
| `Wish` | `wishes` | `id`, `url`, `name`, `photo` | N:M `User` |
| `User` | `users` (`email` único) | `id` (UUID), `name`, `lastname`, `email`, `tel`, `password` (BCrypt), `image`, `enabled`, `vip` | N:M `Role` (`rolesxusers`), N:M `Wish` (`whishesxusers`), 1:N `Comment`, 1:N `Tax`, 1:N `RestorePasswordData` |
| `Role` | `roles` | `id`, `name` (`ROLE_USER`, `ROLE_ADMIN`) | N:M `User` |
| `RestorePasswordData` | `restorepassworddata` | `id` (autoincremental), `code` (`code_value`) | N:1 `User` |

> El id de un `Wish` debe ser el mismo que el de la prenda a la que pertenece (comentario en `UserController`).

## Autenticación y seguridad

- **Login**: `POST /login` (filtro `JwtAuthenticationFilter`, ruta por defecto de `UsernamePasswordAuthenticationFilter`) con el body:
  ```json
  { "email": "usuario@mail.com", "password": "..." }
  ```
  Si las credenciales son correctas responde `200` con `{ token, username, message }` y además agrega el header `Authorization: Bearer <token>`. Si fallan, responde `401`.
- El token se firma con HS256 e incluye los roles del usuario en el claim `authorities`. La clave (`TokenJwtConfig.SECRET_KEY`) se genera en memoria al iniciar la aplicación, por lo que **los tokens dejan de ser válidos al reiniciar el servidor**.
- Las rutas protegidas requieren el header `Authorization: Bearer <token>`.
- Las contraseñas se almacenan encriptadas con `BCryptPasswordEncoder`.
- CSRF deshabilitado, sesión `STATELESS` y CORS abierto (`allowedOriginPatterns = *`, métodos `GET, POST, DELETE, PUT, OPTIONS`).
- Al registrarse, el usuario recibe el rol `ROLE_USER` (debe existir previamente en la tabla `roles`).

## Endpoints

Los parámetros `page` y `cant` indican número de página y cantidad de elementos por página (paginación de Spring Data). La columna **Acceso** refleja las reglas definidas en `SpringSecurityConfig`.

### Prendas - `/api/clothes`

| Método | Ruta | Descripción | Acceso |
|---|---|---|---|
| GET | `/api/clothes?page=&cant=` | Lista prendas en stock, ordenadas por fecha de publicación descendente | Público |
| POST | `/api/clothes/create` | Crea/actualiza una prenda (`ClothePubli`: `clothe` + `subject` + `message`), guarda sus imágenes y, si se envían asunto y mensaje, notifica por email a los usuarios VIP habilitados | ADMIN |
| GET | `/api/clothes/code/{code}?page=&cant=` | Busca prendas por código (incluye imágenes) | Público |
| GET | `/api/clothes/find?name=&size=&priceMin=&priceMax=&genericType=&specificType=&page=&cant=` | Búsqueda por filtros opcionales | Público |

### Usuarios - `/api/users`

| Método | Ruta | Descripción | Acceso |
|---|---|---|---|
| GET | `/api/users?page=&cant=` | Lista usuarios ordenados por nombre | ADMIN |
| POST | `/api/users/register/{password}` | Registra un usuario (`UserDto` en el body, contraseña en la ruta) | Público |
| PUT | `/api/users/update` | Actualiza nombre, apellido, email, teléfono, imagen y VIP | Público |
| PUT | `/api/users/updatePassword/{id}/{password}` | Cambia la contraseña | USER, ADMIN |
| GET | `/api/users/id/{id}` | Busca usuario por id | USER, ADMIN |
| GET | `/api/users/email/{email}` | Busca usuario por email | USER, ADMIN |
| GET | `/api/users/lastname/{lastname}?page=&cant=` | Busca usuarios por apellido | USER, ADMIN |
| PUT | `/api/users/wish/{idUser}` | Agrega un `Wish` a la lista de deseos | USER, ADMIN |
| DELETE | `/api/users/wish/delete/{idUser}/{idWish}` | Quita un deseo de la lista | USER, ADMIN |
| GET | `/api/users/detectWish/{idUser}/{idWish}` | Indica (`true`/`false`) si la prenda está en la lista de deseos | USER, ADMIN |

### Comentarios - `/api/comments`

| Método | Ruta | Descripción | Acceso |
|---|---|---|---|
| POST | `/api/comments/create` | Crea un comentario (`{ idUser, idProduct, comment }`) | USER, ADMIN |
| PUT | `/api/comments/delete/{id}` | Borrado lógico (`available = false`) | USER, ADMIN |
| GET | `/api/comments/find/{idClothe}?page=&cant=` | Comentarios visibles de una prenda, con nombre completo e imagen del autor | Autenticado |

### Facturas - `/api/taxs`

| Método | Ruta | Descripción | Acceso |
|---|---|---|---|
| GET | `/api/taxs?page=&cant=` | Lista todas las facturas | ADMIN |
| GET | `/api/taxs/find/{idUser}?page=&cant=` | Facturas de un usuario | Autenticado |
| POST | `/api/taxs/{idUser}` | Crea una factura para el usuario con sus prendas vendidas (`ClotheSold`) | USER, ADMIN |
| GET | `/api/taxs/code/{code}` | Busca una factura por código | USER, ADMIN |
| GET | `/api/taxs/date?date=yyyy-MM-ddTHH:mm&page=&cant=` | Facturas por fecha | USER, ADMIN |

### Mercado Pago - `/api/mercado`

| Método | Ruta | Descripción | Acceso |
|---|---|---|---|
| POST | `/api/mercado` | Recibe un conjunto de `ItemMercadoPago` (`id`, `title`, `quanty`, `unitPrice`, `currencyId`, `description`) y devuelve la `Preference` creada en Mercado Pago (categoría `ROPA`) | Público |

### Email y recuperación de contraseña

| Método | Ruta | Descripción | Acceso |
|---|---|---|---|
| POST | `/api/email` | Envía un email (`{ addressee, subject, message }`) | USER, ADMIN |
| GET | `/restore/password/{email}` | Genera un código de 5 letras, lo guarda y lo envía al email del usuario | Público |
| POST | `/restore/password/change` | Cambia la contraseña validando email + código (`{ email, code, password }`) | Público |

### Documentación (Swagger)

- Swagger UI: `http://localhost:8080/doc/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Configuración

La configuración está en `src/main/resources/application.properties` y `src/main/resources/email.properties`. Ejemplo con valores de reemplazo:

```properties
# application.properties
spring.datasource.url=jdbc:mysql://<HOST>:<PUERTO>/<BASE_DE_DATOS>
spring.datasource.username=<USUARIO_DB>
spring.datasource.password=<PASSWORD_DB>
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.show-sql=true

spring.application.name=TiendaDeRopa

springdoc.api-docs.enabled=true
springdoc.swagger-ui.enabled=true
springdoc.swagger-ui.path=/doc/swagger-ui.html
```

```properties
# email.properties (cuenta de Gmail usada para enviar correos)
email.username=<TU_EMAIL@gmail.com>
email.password=<APP_PASSWORD_DE_GMAIL>
```

Otros puntos a tener en cuenta:

- **Mercado Pago**: el access token se configura en `MercadoPagoService` (`MercadoPagoConfig.setAccessToken(...)`). Reemplazalo por el de tu cuenta.
- **Base de datos**: no se define `spring.jpa.hibernate.ddl-auto`, por lo que el esquema debe existir en MySQL (o agregar esa propiedad para que Hibernate lo genere). La tabla `roles` debe contener al menos `ROLE_USER` y `ROLE_ADMIN`.
- **Puerto**: por defecto `8080`; si existe la variable de entorno `PORT`, se usa ese valor (pensado para el despliegue).
- **Recomendación**: no versionar credenciales reales; usar variables de entorno (por ejemplo `spring.datasource.password=${DB_PASSWORD}`).

## Cómo ejecutarlo

### Requisitos

- JDK 17
- MySQL accesible con las credenciales configuradas

### Con Maven

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

O generando el JAR:

```bash
./mvnw package
java -jar target/tienda_ropa-0.0.1-SNAPSHOT.jar
```

### Con Docker

El `Dockerfile` compila el proyecto con `maven:3.8.6-amazoncorretto-17` y lo ejecuta sobre `amazoncorretto:17-alpine-jdk`:

```bash
docker build -t tienda-ropa-backend .
docker run -p 8080:8080 -e PORT=8080 tienda-ropa-backend
```

La API queda disponible en `http://localhost:8080`.
