# Association Manager - Application Mobile Android

Application mobile Android complète pour la gestion d'associations, développée en **Kotlin**.

## Fonctionnalités

### Authentification
- Inscription et connexion des membres
- Gestion de session persistante
- Attribution automatique du rôle Président au premier inscrit

### Gestion des Membres
- Liste complète des membres avec recherche
- Ajout, modification et suppression de membres
- Profils détaillés (nom, email, téléphone, adresse, rôle, statut)
- Rôles : Président, Vice-Président, Secrétaire, Trésorier, Membre
- Statuts : Actif, Inactif, Suspendu

### Gestion Financière
- Suivi des transactions (revenus et dépenses)
- Catégories : Cotisation, Don, Subvention, Événement, Équipement, etc.
- Gestion des cotisations par membre
- Tableau de bord financier (solde, total revenus, total dépenses)

### Gestion des Événements
- Création et gestion d'événements
- Types : Réunion, Assemblée Générale, Activité, Formation, Social
- Calendrier avec dates de début et fin
- Statuts : Planifié, En cours, Terminé, Annulé

### Communication
- Système d'annonces avec priorités (Basse, Normale, Haute, Urgente)
- Notification des annonces non lues
- Publication d'annonces par les administrateurs

### Gestion Documentaire
- Stockage et organisation de documents
- Types : Procès-verbal, Rapport, Budget, Statut, Courrier, Photo
- Recherche dans les documents

### Système de Votes
- Création de votes avec options multiples
- Vote des membres avec vérification de doublon
- Suivi des résultats en temps réel
- Statuts : Ouvert, Clôturé, Annulé

### Tableau de Bord
- Vue d'ensemble avec statistiques clés
- Nombre de membres, solde trésorerie
- Événements à venir
- Votes actifs et annonces non lues

## Architecture Technique

- **Langage** : Kotlin
- **Architecture** : MVVM (Model-View-ViewModel)
- **Base de données** : Room (SQLite)
- **Navigation** : Navigation Component avec Bottom Navigation
- **UI** : Material Design 3
- **Asynchrone** : Coroutines + LiveData

## Prérequis

- Android Studio Hedgehog (2023.1) ou supérieur
- JDK 17
- Android SDK 34
- Gradle 8.5

## Installation

1. Cloner le projet
2. Ouvrir avec Android Studio
3. Synchroniser Gradle
4. Lancer sur un émulateur ou appareil Android (API 24+)

## Structure du Projet

```
app/src/main/
├── java/com/association/manager/
│   ├── AssociationApp.kt          # Application class
│   ├── data/
│   │   ├── model/                 # Entités Room
│   │   ├── dao/                   # Data Access Objects
│   │   ├── database/              # Configuration Room
│   │   └── repository/            # Repositories
│   ├── ui/
│   │   ├── auth/                  # Authentification
│   │   ├── dashboard/             # Tableau de bord
│   │   ├── members/               # Gestion des membres
│   │   ├── finance/               # Finances
│   │   ├── events/                # Événements
│   │   ├── communication/         # Annonces
│   │   ├── documents/             # Documents
│   │   ├── votes/                 # Votes
│   │   └── adapters/              # RecyclerView Adapters
│   └── util/                      # Utilitaires
└── res/
    ├── layout/                    # Layouts XML
    ├── navigation/                # Navigation Graph
    ├── menu/                      # Menus
    └── values/                    # Ressources (strings, colors, themes)
```

## Licence

Ce projet est sous licence MIT.
