קישורים לאתר
מסך לקוח https://hanetivta-1.onrender.com/customer.html
מסך מטבח https://hanetivta-1.onrender.com/kitchen.html

# 🍽️ Restaurant Order System

מערכת ניהול הזמנות למסעדה - פרויקט DevOps

---

## 🏗️ ארכיטקטורה

```
┌─────────────────┐        HTTP        ┌──────────────────────┐
│  מסך לקוח       │ ←───────────────→  │  Backend (Java)      │
│  customer.html  │                    │  Spring Boot :8080   │
├─────────────────┤        HTTP        │                      │
│  מסך מטבח       │ ←───────────────→  │  REST API            │
│  kitchen.html   │                    │  /api/orders         │
└─────────────────┘                    └──────────────────────┘
       ↑                                          ↑
  Nginx :3000                              H2 Database
  (Frontend)                            (בזיכרון, לפיתוח)
```

---

## 📁 מבנה הפרויקט

```
restaurant/
├── backend/                          # Java Spring Boot
│   ├── src/main/java/com/restaurant/
│   │   ├── RestaurantApplication.java   ← נקודת הכניסה
│   │   ├── model/Order.java             ← מה זה הזמנה
│   │   ├── repository/OrderRepository   ← שמירה ב-DB
│   │   └── controller/OrderController  ← REST API endpoints
│   ├── src/main/resources/
│   │   └── application.properties      ← הגדרות
│   ├── src/test/                        ← בדיקות
│   ├── Dockerfile                       ← איך לבנות את ה-image
│   └── pom.xml                          ← ספריות Java
│
├── frontend/                         # HTML סטטי
│   ├── src/
│   │   ├── customer.html                ← מסך לקוח
│   │   └── kitchen.html                 ← מסך מטבח
│   └── Dockerfile                       ← Nginx
│
├── .github/workflows/
│   └── ci.yml                           ← GitHub Actions Pipeline
│
└── docker-compose.yml                ← הרצה מקומית של הכל ביחד
```

---

## 🚀 הרצה מקומית

### דרישות מוקדמות
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- [Git](https://git-scm.com/)

### צעד 1: Clone הפרויקט
```bash
git clone https://github.com/<YOUR_USERNAME>/restaurant-system.git
cd restaurant-system
```

### צעד 2: הרץ עם Docker Compose
```bash
docker compose up --build
```

### צעד 3: פתח את המסכים
| מסך | כתובת |
|-----|--------|
| 🍽️ מסך לקוח | http://localhost:3000/customer.html |
| 👨‍🍳 מסך מטבח | http://localhost:3000/kitchen.html |
| 📊 API ישיר | http://localhost:8080/api/orders |
| 🗄️ DB Console | http://localhost:8080/h2-console |

### עצירה
```bash
docker compose down
```

---

## ⚙️ GitHub Actions (CI/CD)

### מה ה-pipeline עושה?

```
Push to main
     │
     ▼
┌─────────┐    ┌─────────┐    ┌──────────────┐
│  Test   │───▶│  Build  │───▶│  Push to     │
│         │    │ Docker  │    │  Docker Hub  │
│ mvn test│    │ images  │    │  (רק main)   │
└─────────┘    └─────────┘    └──────────────┘
```

### הגדרת Secrets ב-GitHub

נדרש פעם אחת בלבד:

1. לך ל-GitHub → Repository → Settings → Secrets → Actions
2. הוסף:
   - `DOCKERHUB_USERNAME` = שם המשתמש שלך ב-Docker Hub
   - `DOCKERHUB_TOKEN` = Access Token מ-Docker Hub

---

## 🔌 API Endpoints

| Method | URL | תיאור |
|--------|-----|-------|
| GET | `/api/orders` | כל ההזמנות |
| GET | `/api/orders/waiting` | הזמנות ממתינות |
| GET | `/api/orders/approved` | הזמנות שאושרו |
| POST | `/api/orders` | הזמנה חדשה |
| PUT | `/api/orders/{id}/approve` | אישור הזמנה |

### דוגמה - שליחת הזמנה ידנית
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{"tableNumber":"5","items":"שניצל + קולה"}'
```

---

## ☁️ מעבר ל-AWS (בעתיד)

כשתהיה מוכן לפרודקשן:

1. **ECR** – במקום Docker Hub, push ל-Amazon ECR
2. **ECS** – הרץ את ה-containers על Amazon ECS (Fargate)
3. **RDS** – החלף את H2 ב-Amazon RDS (PostgreSQL)
4. עדכן את `ci.yml` עם `aws-actions/configure-aws-credentials`

---

## 🛠️ פיתוח בלי Docker

אם רוצים להריץ בלי Docker (לבדיקות מהירות):

```bash
# Backend
cd backend
mvn spring-boot:run

# Frontend - פתח ישירות בדפדפן
open frontend/src/customer.html
open frontend/src/kitchen.html
```

> ⚠️ בלי Docker, ה-HTML חייב לפנות ל-`http://localhost:8080` (כבר מוגדר כך)
