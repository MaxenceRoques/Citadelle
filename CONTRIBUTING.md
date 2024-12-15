# Contributing to CONTRIBUTING.md

## Stratégie de Branche

### Utilisation de 2 branches : master et versionStable
- Master : c'est la branche des commits classiques
- VersionStable : c'est la branche où sont déposées les commits de fin de Milestone 

Ces deux branches ont été suffisante la plupart du temps car nous faisions en sorte de ne pas coder en même temps sur les mêmes fichiers,
mais pour la semaine à plein temps nous avons anticipé que ça ne serait pas suffisant. De là, d'autres branches ont été générées.

### Création de 6 branches : strategy, simulation1000, CSV, finitions, Refactoring et Rapport
- Strategy : c'est la branche dans laquelle les stratégies de Richard ont été créé
- Simulation1000 : c'est la branche dans laquelle a été implémenté les arguments --demo et --2thousands utilisables en ligne de commande
- CSV : c'est la branche dans laquelle le système de CSV et donc l'argument --csv a été implémenté
- Finitions : branche de tests pour augmenter le coverage
- Refactoring : branche dans laquelle les codes trop longs, mal implémentés ou mal utilisés ont été refactoré pour les rendre plus lisibles et moins complexes
- Rapport : branche dans laquelle sont écrit les différents documents pour le rendu final : RAPPORT, CONTRIBUTING, README

Pour résumer, en ce qui concerne le branching stratégy, nous avons opté pour la stratégie dite "Github flow".
En effet, nous avons créé une nouvelle branche pour chaque ajout de fonctionnalité, branche qui sera ensuite merged avec la branche principale (master), puis supprimée.

## Auteurs
* Cholewa Théo SI3 à Polytech Nice Sophia, France
* Bottero Adam SI3 à Polytech Nice Sophia, France
* Roques Maxence SI3 à Polytech Nice Sophia, France
* Heilmann Hugo SI3 à Polytech Nice Sophia, France