# 📚 Sistema de Gestión de Biblioteca (Full Stack)

Aplicación web **Full Stack** (Principalmente backend, con un frontend simple) para la gestión integral de una biblioteca, que permite tanto el **alquiler (préstamo)** como la **venta** de libros. Incluye un **catálogo público** para usuarios finales y un **panel de administración** para el bibliotecario.

---

## 🚀 Características Principales

### 🏠 Catálogo Público (Frontend)

* **Buscador inteligente:** filtrado de libros por título en tiempo real.
* **Carrito híbrido:** permite combinar libros para **compra** y **alquiler** en una misma orden.
* **Checkout simplificado:** no requiere registro de usuario (Guest Checkout), solicitando únicamente datos básicos (DNI y nombre).
* **Validación visual de stock:** los botones de compra/alquiler se desactivan automáticamente cuando no hay disponibilidad suficiente.

### 🛠️ Panel de Bibliotecario (Backoffice)

* **Dashboard de gestión:** CRUD completo de libros (crear, editar y eliminar).
* **Terminal de Punto de Venta (POS):** interfaz optimizada para registrar ventas o préstamos presenciales.
* **Control de devoluciones:** flujo de estados (*Pendiente → En curso → Completado*) con reposición automática de stock al devolver un libro.
* **Historial de transacciones:** registro detallado de todas las operaciones realizadas.

---

## 🛠️ Tecnologías Utilizadas

* **Backend:** Java 17, Spring Boot 3 (Spring Web, Spring Data JPA)
* **Base de datos:** PostgreSQL
* **Frontend:** HTML5, CSS3 y JavaScript (Vanilla, sin frameworks externos)
* **Herramientas:** Maven, Lombok

---

## ⚙️ Configuración e Instalación

### 1️⃣ Requisitos Previos

* Java JDK 17 o superior
* PostgreSQL instalado y en ejecución
* Maven (opcional, el proyecto incluye `mvnw`)

### 2️⃣ Base de Datos

Crear una base de datos vacía en PostgreSQL:

```sql
CREATE DATABASE biblioteca_db;
```

### 3️⃣ Configuración de la Aplicación

Editar el archivo:

```text
src/main/resources/application.properties
```

Y configurar las credenciales de la base de datos:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/biblioteca_db
spring.datasource.username=postgres
spring.datasource.password=TU_CONTRASEÑA
```

---

## ▶️ Ejecución del Proyecto

### Opción A: IntelliJ IDEA (Recomendado)

1. Abrir IntelliJ IDEA.
2. Seleccionar **File → Open** y elegir la carpeta del proyecto.
3. Esperar a que Maven descargue las dependencias.
4. Abrir el archivo:

   ```text
   src/main/java/com/techlab/biblioteca/BibliotecaApplication.java
   ```
5. Ejecutar el proyecto haciendo clic en **Run ▶️**.

### Opción B: Visual Studio Code

1. Instalar el **Extension Pack for Java**.
2. Abrir la carpeta del proyecto en VS Code.
3. Desde el panel **Java Projects**, buscar `BibliotecaApplication`.
4. Ejecutar con **Run**.

### Opción C: Terminal

```bash
./mvnw spring-boot:run
```

---

## 🌐 Uso de la Aplicación

Una vez iniciada la aplicación:

* **Catálogo público:**

  ```text
  http://localhost:8080
  ```

* **Panel de bibliotecario:**

  ```text
  http://localhost:8080/admin.html
  ```

---

## 🧪 Datos de Prueba

En el primer inicio, si la base de datos está vacía, el sistema cargará automáticamente **datos de ejemplo**, incluyendo libros como:

* *El señor de los anillos: La comunidad del anillo*
* *Mil años de soledad*
* *La vuelta al mundo en 80 días*

---

## 📌 Notas Finales

Proyecto desarrollado como práctica de **arquitectura Full Stack, principalmente backend con un frontend simple** utilizando **Spring Boot**, enfocado en buenas prácticas, separación de responsabilidades y manejo de estados de negocio.
