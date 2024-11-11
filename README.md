# Mutant Detection API
Este proyecto permite determinar si una persona es un humano o un mutante a partir de su ADN. El ADN se recibe como un parámetro en formato de matriz de cadenas, y se analiza para verificar si cumple con los patrones característicos de un mutante.

# Descripción general
El sistema recibe un ADN en formato de matriz y responde con un código de estado HTTP:

200 si la persona es un mutante.
403 si la persona es un humano.
El sistema también ofrece estadísticas sobre el número de humanos y mutantes en la base de datos.

# Tecnologías utilizadas
Java 17
Spring Boot
Maven
PostgreSQL (base de datos alojada en Railway)
Postman (para probar la API)
# Requisitos previos
Antes de ejecutar este proyecto, asegúrate de tener instalados los siguientes programas:

Java 17: Descargar Java 17
Maven: Descargar Maven
Postman (opcional, para probar la API): Descargar Postman
Configuración y ejecución del proyecto

# Clonar el repositorio

Clona el repositorio de GitHub a tu máquina local:

Copiar código
git clone https://github.com/jteban/meli_repository.git

la rama donde se ecuentra el código es: feat/meli_prueba

# Configuración de variables de entorno

Este proyecto utiliza una base de datos de PostgreSQL alojada en Railway. Las credenciales de la base de datos están almacenadas en el archivo .env llamado meli_prueba.env.

Crea un archivo .env en la raíz del proyecto y agrega las variables de entorno que proporciono en el archivo meli_prueba.env:

# Contenido del archivo meli_prueba.env (ejemplo)
DB_URL=jdbc:postgresql://<tu_db_url>:<puerto>/<nombre_db>
DB_USERNAME=<tu_usuario>
DB_PASSWORD=<tu_contraseña>

# Compilar y ejecutar el proyecto
Asegúrate de tener las dependencias necesarias con Maven y ejecuta el proyecto:

mvn clean install
mvn spring-boot:run
El servidor debería iniciarse en http://localhost:8080.

# Endpoints de la API
1. POST /meli/mutant/
   Este endpoint permite determinar si un ADN corresponde a un mutante o a un humano.

Ejemplo de solicitud:

http://localhost:8080/meli/mutant/
{
"dna": [
"ATGCGT",
"CAGTGA",
"TTATGT",
"TTAGGT",
"CAGAAC",
"ATGTCA"
]
}
Respuestas:
200 OK: Si el ADN corresponde a un mutante.
403 Forbidden: Si el ADN corresponde a un humano.
2. GET /meli/stats
   Este endpoint devuelve estadísticas sobre el número de humanos y mutantes en la base de datos.

Ejemplo de solicitud:

GET http://localhost:8080/meli/stats
Respuesta:
{
"count_human_dna": 1,
"count_mutant_dna": 3,
"ratio": 3.0
}
# Uso de Postman
He preparado una colección de Postman con ejemplos de cómo interactuar con la API. Puedes encontrarla en el repositorio de GitHub.

Importar la colección en Postman:
Descarga el archivo Meli.postman_collection.json desde el repositorio.
Importa el archivo a Postman y ejecuta las solicitudes de ejemplo.

# Pruebas
Para realizar pruebas manuales de la API, puedes usar Postman con los ejemplos de solicitudes que he proporcionado. Los resultados de las pruebas dependerán de los datos almacenados en la base de datos de PostgreSQL.

# Variables de entorno
Asegúrate de definir las siguientes variables en tu archivo .env:

DB_URL: URL de conexión a la base de datos PostgreSQL.
DB_USERNAME: Nombre de usuario para acceder a la base de datos.
DB_PASSWORD: Contraseña para acceder a la base de datos.
