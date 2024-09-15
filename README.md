
# ORION - MDD (Monde de Dév)

## Description

**ORION** est une plateforme sociale destinée aux développeurs pour le projet **MDD (Monde de Dév)**. L’application permet aux utilisateurs de suivre des sujets de programmation, de publier des articles et de commenter sur ces derniers. Le projet est structuré en deux parties distinctes :
- Un **frontend** développé avec Angular pour la gestion de l'interface utilisateur.
- Un **backend** développé avec Spring Boot pour la gestion des API REST, la sécurité et l'interaction avec la base de données.

## Table des matières

- [Fonctionnalités](#fonctionnalités)
- [Installation](#installation)
- [Technologies utilisées](#technologies-utilisées)
- [Architecture](#architecture)
- [Contribuer](#contribuer)
- [License](#license)

## Fonctionnalités

### Gestion des utilisateurs
- Inscription et connexion via e-mail, nom d’utilisateur et mot de passe.
- Connexion persistante entre les sessions.
- Modification des informations du profil utilisateur (e-mail, nom d’utilisateur, mot de passe).
- Déconnexion.

### Gestion des abonnements
- Affichage d’une liste des sujets de programmation.
- Abonnement et désabonnement à des sujets via les pages dédiées.

### Gestion des articles
- Création d’un article avec choix du thème, titre et contenu.
- Affichage des articles dans le fil d’actualité par ordre chronologique.
- Ajout de commentaires aux articles.

### Exigences particulières
- **Responsiveness** : Utilisable sur mobile et desktop.
- **Sécurité des mots de passe** : Un mot de passe valide doit respecter les règles suivantes :
  - Minimum 8 caractères.
  - Contenir au moins un chiffre, une majuscule, une minuscule, et un caractère spécial.

## Installation

### Prérequis

- **Java 17** ou une version supérieure pour le backend.
- **Node.js** et **npm** pour le frontend.
- **Angular CLI**.
- **MySQL** installé et configuré.

### Installation Backend (Spring Boot)

1. Cloner le dépôt Git :
   ```bash
   git clone https://github.com/ahmidev/Projet_6_Developpez-une-application-full-stack-complete
   ```
2. Accéder au répertoire du backend :
   ```bash
   cd back
   ```
3. Configurer la base de données MySQL :
   - Créer une base de données appelée `mdd_db`.
   - Mettre à jour les informations d'accès dans `src/main/resources/application.properties`.

4. Lancer l'application Spring Boot :
   ```bash
   ./mvnw spring-boot:run
   ```

### Installation Frontend (Angular)

1. Cloner le dépôt Git :
   ```bash
   git clone https://github.com/ahmidev/Projet_6_Developpez-une-application-full-stack-complete
   ```
2. Accéder au répertoire du frontend :
   ```bash
   cd front
   ```
3. Installer les dépendances :
   ```bash
   npm install
   ```
4. Lancer l'application Angular :
   ```bash
   ng serve
   ```

5. Ouvrir l'application dans un navigateur à l'adresse suivante : `http://localhost:4200`.

## Technologies utilisées

### Backend
- **Java 17**
- **Spring Boot 3.3.1** (API REST, sécurité avec JWT, Spring Data JPA pour l'accès aux données)
- **MySQL** (Base de données relationnelle)

### Frontend
- **Angular 14** (Framework frontend pour construire des interfaces utilisateur dynamiques)
- **TypeScript**
- **Bootstrap 5** (Framework CSS pour le design responsive)
- **ngx-toastr** (Librairie pour la gestion des notifications utilisateurs)

## Architecture

L'application ORION est construite suivant une architecture où le backend et le frontend sont distincts, mais communiquent via des API REST sécurisées. Chaque partie est développée et déployée séparément.

### Backend (Spring Boot)
- Expose une API REST pour gérer la logique métier, l'authentification des utilisateurs, et l'accès à la base de données MySQL.
- Sécurisation des API via **JSON Web Tokens (JWT)**.

### Frontend (Angular)
- Consomme les API REST exposées par le backend pour afficher les données aux utilisateurs et permettre les interactions (connexion, création d’articles, abonnements, etc.).
- Gère l’interface utilisateur de manière responsive pour garantir une bonne expérience sur mobile et desktop.

### Diagramme simplifié de l'architecture

```
[Frontend Angular] <--> [API REST - Spring Boot] <--> [Base de données MySQL]
```

- **Sécurisation des échanges** : L'API est sécurisée avec **JSON Web Tokens (JWT)** pour garantir que seuls les utilisateurs authentifiés peuvent accéder aux fonctionnalités critiques de l'application.


