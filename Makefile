# ==============================
# Makefile - Restaurant System
# ==============================
# שימוש: make <פקודה>
# דוגמה: make test

# ==============================
# Docker Compose
# ==============================

# בונה ומעלה את כל השירותים
run:
	docker compose up --build

# מעלה ברקע (בלי לתפוס את הטרמינל)
run-detached:
	docker compose up --build -d

# מוריד את כל השירותים
down:
	docker compose down

# ==============================
# Backend (Java / Maven)
# ==============================

# מריץ את הבדיקות
test:
	cd backend && mvn test

# בונה JAR בלי בדיקות (מהיר יותר)
build:
	cd backend && mvn package -DskipTests

# מנקה קבצי build ישנים
clean:
	cd backend && mvn clean

# ==============================
# Docker Images בלבד
# ==============================

# בונה את ה-images בלי להריץ
docker-build:
	docker build -t restaurant-backend ./backend
	docker build -t restaurant-frontend ./frontend

# מציג את כל ה-containers הרצים
status:
	docker compose ps

# מציג logs של כל השירותים
logs:
	docker compose logs -f

# ==============================
# עזרה
# ==============================
help:
	@echo ""
	@echo "פקודות זמינות:"
	@echo "  make run            - בנה והרץ את הכל"
	@echo "  make run-detached   - הרץ ברקע"
	@echo "  make down           - עצור הכל"
	@echo "  make test           - הרץ בדיקות Java"
	@echo "  make build          - בנה JAR"
	@echo "  make clean          - נקה קבצי build"
	@echo "  make docker-build   - בנה Docker images"
	@echo "  make status         - הצג containers רצים"
	@echo "  make logs           - הצג logs"
	@echo ""
