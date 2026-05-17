## 🇬🇧 English

This project is an e-commerce platform inspired by websites such as Amazon, Leboncoin, or Rakuten.

## 🧱 Tech Stack

- Frontend: Angular / TypeScript / SCSS / Nginx
- Backend: Java / Spring Boot / Spring Security / Maven
- Database: PostgreSQL
- DevOps: Docker / Docker Compose

---

## ⚙️ Prerequisites

- Docker and Docker Compose installation
  (or Docker Desktop, which includes both)
- Project cloning
- An IDE (VS Code, IntelliJ, etc.)

---

## 🚀 Running the project

### 1. First-time launch

From a terminal (cmd, PowerShell, or shell), run:

```bash
docker-compose up --build
```

### 2. Wait for the build

The first execution may take some time because it:

- builds Docker images
- installs frontend and backend dependencies
- starts the database

### 3. Access the application

Once the project is running:

- Frontend: http://localhost:4200
- Backend: http://localhost:8080
- Database: localhost:5432

### 4. Restart the project

After the first build, run:

```bash
docker-compose up
```

---

## Note

No installation of Node.js, Java, or Maven is required when using Docker.

---

---

---

## 🇫🇷 Français

Ce projet est une plateforme e-commerce inspirée de sites comme Amazon, Leboncoin ou Rakuten.

## 🧱 Stack Technique

- Frontend : Angular / TypeScript / SCSS / Nginx
- Backend : Java / Spring Boot / Spring Security / Maven
- Base de données : PostgreSQL
- DevOps : Docker / Docker Compose

---

## ⚙️ Prérequis

- Installation de Docker et Docker Compose
  (ou Docker Desktop qui les inclut déjà)
- Clonage du projet
- Un IDE (VS Code, IntelliJ, etc.)

---

## 🚀 Démarrer le projet

### 1. Lancer le projet pour la première fois

Depuis un terminal (cmd, PowerShell ou shell), exécutez :

```bash
docker-compose up --build
```

### 2. Attendre la compilation

La première exécution peut être longue car elle :

- construit les images Docker
- installe les dépendances frontend et backend
- démarre la base de données

### 3. Accéder à l’application

Une fois le projet démarré :

- Frontend : http://localhost:4200
- Backend : http://localhost:8080
- Base de données : localhost:5432

### 4. Relancer le projet

Après le premier build, exécutez :

```bash
docker-compose up
```

---

## Remarque

Aucune installation de Node.js, Java ou Maven n’est nécessaire si vous utilisez Docker.