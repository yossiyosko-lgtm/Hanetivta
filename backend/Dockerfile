# ==============================
# שלב 1: BUILD - בונים את ה-JAR
# ==============================
# משתמשים ב-image עם Maven ו-Java 17 לבניית הפרויקט
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# מעתיקים את קובץ ה-pom.xml קודם (כדי לנצל Docker cache)
# Docker לא יוריד שוב את הספריות אם ה-pom.xml לא השתנה
COPY pom.xml .
RUN mvn dependency:go-offline

# מעתיקים את הקוד ובונים
COPY src ./src
RUN mvn package -DskipTests

# ==============================
# שלב 2: RUN - מריצים את ה-JAR
# ==============================
# image קטן יותר - רק Java Runtime, בלי Maven
FROM eclipse-temurin:17-jre

WORKDIR /app

# מעתיקים את ה-JAR שנבנה בשלב הקודם
COPY --from=build /app/target/*.jar app.jar

# פורט שהאפליקציה מאזינה עליו
EXPOSE 8080

# הפקודה שתרוץ כשהקונטיינר מתחיל
ENTRYPOINT ["java", "-jar", "app.jar"]
