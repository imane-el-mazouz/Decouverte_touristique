# Documentation du Projet : Plateforme de Découverte Touristique au Maroc

## 1. Introduction
Bienvenue sur la Plateforme de Découverte Touristique au Maroc, une solution web complète qui permet aux utilisateurs de découvrir les richesses du Maroc à travers une expérience interactive et personnalisée. La plateforme centralise les informations touristiques et offre plusieurs services pour faciliter la réservation d'hôtels, d'excursions, la gestion des événements et bien plus encore.

Cette documentation vous guidera à travers les différents services et fonctionnalités offerts par la plateforme.

## 2. Services Principaux

### 2.1. Gestion des Hôtels
Ce service permet aux utilisateurs de rechercher des hôtels au Maroc avec des options de filtrage et de pagination pour affiner les résultats. Les principales fonctionnalités incluent :

- **Recherche d'hôtels** : Les utilisateurs peuvent rechercher des hôtels en fonction de critères tels que la localisation, la catégorie d'étoiles, le prix, et les équipements.
- **Filtrage avancé** :
  - `findAllByAverageRatingBetween(@Param("minRating") Long minRating, @Param("maxRating") Long maxRating)`.
  - `findHotelByCategoryHotelOrLocation(CategoryHotel category, String location)`.
- **Pagination** : Pour une meilleure expérience utilisateur, les résultats sont paginés pour éviter de surcharger l'interface.
- **Réservation** : Les utilisateurs peuvent effectuer des réservations directement via la plateforme.
- **Avis** : Après leur séjour, les clients peuvent laisser un avis et une note sur leur expérience.

### 2.2. Gestion des Excursions et Trips
Ce service permet aux utilisateurs de découvrir et de réserver des excursions et des voyages au Maroc. Les fonctionnalités sont similaires à celles de la gestion des hôtels et comprennent :

- **Recherche et filtrage** :
  - `findByDateTime(LocalDateTime dateTime)`.
  - `findByLocation(String location)`.
  - `findByDateTimeAndLocation(LocalDateTime dateTime, String location)`.
  - `findByRatingBetween(Long minRating, Long maxRating)`.
- **Pagination** : Les excursions sont listées avec pagination pour une meilleure navigation.
- **Réservation** : Les utilisateurs peuvent réserver directement des excursions.
- **Avis** : Les utilisateurs peuvent donner leur avis après avoir participé à une excursion.

### 2.3. Gestion des Événements Mondiaux
La plateforme offre également un service de gestion des événements mondiaux se déroulant au Maroc. Les utilisateurs peuvent rechercher et consulter des événements comme des festivals, conférences, concerts, etc. Les fonctionnalités principales incluent :

- **Recherche d'événements** :
  - `findEventByCategoryOrLocationOrDate(CategoryEvent category, String location, LocalDate date)`.
  - `findEventsByCriteria(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice, @Param("minRating") Integer minRating, @Param("maxRating") Integer maxRating, @Param("maxDistance") Double maxDistance)`.
- **Pagination** : Les résultats sont paginés pour faciliter la navigation.
- **Réservation de billets** : Les utilisateurs peuvent acheter des billets pour des événements directement sur la plateforme.
- **Avis** : Les utilisateurs peuvent noter et commenter les événements auxquels ils ont assisté.

### 2.4. Gestion des Demandes de Contact
Ce service permet aux utilisateurs de soumettre des demandes de contact ou des questions via la plateforme. Les administrateurs peuvent gérer ces demandes et y répondre. Les fonctionnalités incluent :

- **Formulaire de contact** : Un formulaire intuitif permet aux utilisateurs de soumettre leurs demandes.
- **Gestion des demandes** : Les administrateurs peuvent consulter, filtrer et répondre aux demandes des utilisateurs.
- **Suivi des réponses** : Les utilisateurs peuvent suivre l'état de leurs demandes directement via la plateforme.

### 2.5. Gestion des Traditions et Blogs
Ce service met en avant les traditions marocaines et offre des articles de blog associés. Les utilisateurs peuvent découvrir la culture locale et lire des articles.

- **Découverte des traditions** : Articles et informations sur les traditions marocaines (artisanat, cuisine, etc.).
- **Section blog** : Articles de blog détaillant des aspects spécifiques du tourisme et des traditions.

## 3. Page Admin
Une page d'administration dédiée permet de gérer tous les services, incluant :

- **Gestion des Hôtels** : Ajouter, modifier ou supprimer des hôtels et leurs chambres.
- **Gestion des Excursions** : Ajouter ou modifier des excursions disponibles.
- **Gestion des Événements** : Ajouter ou modifier des événements.
- **Gestion des Demandes** : Voir et répondre aux demandes des utilisateurs.
- **Gestion des Avis** : Administrer les avis laissés par les utilisateurs.
- **Gestion des Traditions et Blogs** : Ajouter ou modifier des articles et des informations culturelles.

## 4. Architecture Technique
La plateforme est développée en utilisant les technologies suivantes :

- **Frontend** : Angular pour une interface utilisateur réactive et interactive.
- **Backend** : Spring Boot pour gérer les services RESTful et les opérations côté serveur.
- **Base de données** : MySQL pour le stockage des données.

## 5. Technologies Utilisées
Le projet intègre plusieurs technologies et outils pour assurer un développement efficace et une gestion optimale des données. Les technologies et outils utilisés comprennent :

- **Outils de Gestion de Projet** :
  - **Trello** : Pour la gestion des tâches et le suivi de l'avancement du projet.
  - **Git** : Pour le contrôle de version et la gestion du code source.
  - **GitHub** : Pour héberger le code et collaborer avec d'autres développeurs.

- **Outils de Conception** :
  - **Figma** : Pour la conception des maquettes et des prototypes de l'interface utilisateur.
  - **Lucidchart** : Pour la création de diagrammes UML et la visualisation des processus.
  - **Canva** : Pour la création de contenus graphiques et visuels pour la plateforme.

## 6. Conception UML
Les diagrammes UML pour le projet comprennent :

- **Diagramme de cas d'utilisation** : Illustrant les interactions entre les utilisateurs et les services de la plateforme.
- **Diagramme de classe** : Décrivant les entités et leurs relations dans le système.

## 7. Base de Données
La base de données est conçue en SQL pour stocker les informations relatives aux hôtels, excursions, événements, utilisateurs et avis. Elle assure l'intégrité des données et permet des opérations de recherche et de filtrage efficaces.
## 8. Conclusion
La plateforme de découverte touristique au Maroc représente une solution innovante pour centraliser et faciliter l'accès à l'information touristique. Grâce à ses divers services, elle permet aux utilisateurs de rechercher et de réserver des hôtels, des excursions, et des événements, tout en découvrant la richesse culturelle du Maroc. 

L'architecture technique choisie, combinée à des outils modernes de gestion de projet et de conception, garantit une expérience utilisateur fluide et agréable. En intégrant des fonctionnalités de recherche et de filtrage avancées, ainsi que la possibilité de laisser des avis, la plateforme encourage l'interaction des utilisateurs et améliore la qualité des services proposés.

À l'avenir, la plateforme pourrait être étendue pour inclure d'autres fonctionnalités, comme des recommandations personnalisées, un système de fidélité pour les utilisateurs réguliers, et des partenariats avec d'autres entreprises locales pour enrichir encore davantage l'expérience des utilisateurs. Ce projet est un pas important vers la promotion du tourisme durable et responsable au Maroc, en mettant en avant ses traditions et sa culture uniques.

Nous remercions tous les contributeurs et utilisateurs pour leur soutien et leur engagement dans cette initiative passionnante. Ensemble, nous pouvons faire de cette plateforme un incontournable pour tous ceux qui souhaitent explorer la beauté et la diversité du Maroc.

