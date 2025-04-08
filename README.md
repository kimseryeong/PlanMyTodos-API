# PlanMyTodos API  (PlanMyTodos Web Application Refactoring)

PlanMyTodos Server Refactoring: Backend refactored from `Supabase` to `Spring Boot with JPA and PostgreSQL`.

#### 💡 Go to web application →  [📆 PlanMyTodos](https://planmytodos.netlify.app/#)

***

### 🧩 Features

> ✔️ Todolist (CRUD)
> <br>
> ✔️ FullCalendar API
> <br>
> ✔️ Google OAuth Login
> <br>
> ✔️ SignUp & Login

<br>

### 🔧 Tech Stack
|||
|----|----|
| **Backend** | `Springboot`, `JPA`, `PostgreSQL`(via `Docker`) |
| **Frontend** | `React`, `Typescript` |
| **Deployment** | `Netlify`, `Koyeb` |
|||

<br>

### 🧠 Domain-Driven Design (DDD)

**✔️ Entity**: `Users`, `Todos`
<br>

**✔️ Folder Structure**

```
📂 src/
├── 📂 domain/                | domain model and logic
│   ├──  📂 user/                         
│   └──  📂 todo/      
├── 📂 service/               | business logic
├── 📂 controller/            | HTTP REST API       
├── 📂 dto/                   | data transfer objects (dto) for request and response mapping
└── 📂 config/                | manage configuration (CORS, security, etc)
```
