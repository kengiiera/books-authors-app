# 📚 Books & Authors API

API REST que permite administrar libros y autores usando:
- 💾 PL/SQL (Oracle XE)
- ⚙️ Spring Boot + Arquitectura Hexagonal
- 🐳 Docker para levantar el entorno

---

## 🚀 Tecnologías

- Java 17 + Spring Boot
- Oracle XE 21c (Docker)
- PL/SQL
- Maven
- Docker Compose
- Lombok

---

## ✅ Requisitos previos

- Tener instalado:
    - **Docker y Docker Compose**
    - **Java 17 o superior**
    - **Maven**

---


## 🧩 Estructura general del proyecto

```
books-authors-app/
├── plsql/            # Contiene init.sql (tablas y procedimientos)
├── src/               # Aplicación Java (Spring Boot)
├── docker-compose.yml        # Levanta Oracle + scripts
└── README.md                 # Documentación
```

---

## 🚀 Paso a paso para ejecutar y probar

### 1️⃣ Clonar el repositorio

```bash
git clone https://github.com/kengiiera/books-authors-app.git
cd books-authors-app
```

---

### 2️⃣ Levantar la base de datos Oracle con Docker

```bash
docker-compose up -d
```

🔸 Esto:
- Crea un contenedor `oracle-db`
- Abre el puerto `1521`
- Ejecuta automáticamente `init.sql` (crea tablas y procedures)

✅ Espera unos 30 segundos antes de continuar, para asegurarte de que Oracle esté listo.

---

### 3️⃣ Ejecutar la API REST (backend Spring Boot)

En una nueva terminal:

```bash
./mvnw spring-boot:run
```

🔹 La aplicación se ejecutará en:  
👉 `http://localhost:8080`

---

## 🧪 Probar los endpoints

Puedes usar Postman, curl o tu navegador para probar los endpoints.

---

### 🔹 Autores

#### ➕ Crear autor
```http
POST /api/authors
Content-Type: application/json

{
  "name": "Gabriel García Márquez"
}
```

#### 🔍 Obtener todos
```http
GET /api/authors
```

#### 📝 Actualizar autor
```http
PUT /api/authors/1
Content-Type: application/json

{
  "name": "Gabo"
}
```

#### ❌ Eliminar autor
```http
DELETE /api/authors/1
```

---

### 🔹 Libros

#### ➕ Crear libro
```http
POST /api/books
Content-Type: application/json

{
  "title": "Cien Años de Soledad",
  "publicationDate": "1967-05-30",
  "isbn": "978-3-16-148410-0",
  "pages": 471,
  "author": {
    "id": 1
  }
}
```

#### 🔍 Obtener todos
```http
GET /api/books
```

#### 🔍 Buscar libros
```http
GET /api/books?title=soledad&authorId=1&publicationDate=1967-05-30
```

#### 📝 Actualizar libro
```http
PUT /api/books/1
Content-Type: application/json

{
  "title": "Cien Años de Soledad (Edición 2024)",
  "publicationDate": "1967-05-30",
  "isbn": "978-3-16-148410-0",
  "pages": 480,
  "author": {
    "id": 1
  }
}
```

#### ❌ Eliminar libro
```http
DELETE /api/books/1
```

---

## 🔁 Reiniciar base de datos (si necesitas resetear)

```bash
docker-compose down -v
docker-compose up -d
```

---

## ✅ ¡Listo!

La aplicación está funcionando con:
- Oracle corriendo en Docker
- Backend en Java
- Endpoints REST probados

---

## 👩‍💻 Autora

Kengie – Desarrolladora Full Stack 🧠
