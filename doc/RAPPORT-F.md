# Projet 2 PS5 : Citadelles 2024 équipe F
### Participants :
* Cholewa Théo en SI3 à Polytech Nice Sophia, France
* Bottero Adam en SI3 à Polytech Nice Sophia, France
* Roques Maxence en SI3 à Polytech Nice Sophia, France
* Heilmann Hugo en SI3 à Polytech Nice Sophia, France
>## Sommaire :
- Introduction du Projet
- Point d'avancement
    - [Liste de l'ensemble des fonctionnalité du projet](#plateau-)
    - [Explication des Logs](#explication-logs-br)
    - [Explication du CSV](#explication-csv-br)
    - [Explication Stratégie de Bot](#explication-bot-br)
- Architecture et Qualité
    - [Architecture](#architecture-)
    - [Documentation](#documentation-)
    - [Status du Code](#status-du-code-)
- Processus
    - [Répartition du Travail](#qui-a-fait-quoi--)
    - [Process de la Team](#process-de-la-team-)

>## I. Introduction du Projet :
Réalisation du jeu de société Citadelles dans lequel plusieurs adversaires s'affrontent avec divers personnages dans l'optique de finir sa ville en premier.<br/>
Dans notre projet il s'agit de faire jouer plusieurs bots entre eux et de suivre la partie via un affichage de leurs actions.<br/>
L'ensemble du projet est réalisé en **JAVA**.
### Cartes :
* Nous avons des [cartes bâtiments](#batiments) qui ont chacune un nom, un coût de construction et un type de **quartier** entre commerce, noble, militaire, religieux ou merveille. <br/>
  Certaines cartes sont en multiples exemplaires. Les bâtiments de type merveilles possèdent des capacités spéciales.<br/>
  Un [Deck](#deck) rassemble les 68 cartes bâtiments.
* Nous avons des cartes personnages au nombre de huit : [Assassin](#assassin), [Voleur](#voleur), [Magicien](#magicien), [Roi](#roi), [Evêque](#eveque), [Marchand](#marchand), [Architecte](#architecte) et [Condottiere](#condottiere).<br/>
  Chaque personnage possède un numéro (de un à huit), son nom et une ou plusieurs actions propres.
### Joueurs :
* Nous avons un [joueur](#joueur). Celui-ci possède un nom, une quantité d'argent, une cité (ensemble des bâtiments construits), une main de carte (ensemble des bâtiments pas encore construits) et un niveau d'agressivité.<br/>
  Le joueur peut obtenir ou changer de personnage, piocher ou construire un bâtiment, pioche de l'argent et affectuer l'action de son personnage.
  Le joueur peut obtenir ou changer de personnage, piocher ou construire un bâtiment, pioche de l'argent et effectuer l'action de son personnage.
* Nous avons un [bot](#bot). Celui-ci possède un joueur et une réflection pour effectuer les actions de son joueur.
### Plateau :
* Il y a pour finir un [plateau](#plateau) qui contient un deck et les bots qui s'affrontent. Le plateau permet de faire jouer une partie entre les bots. Il s'occupe de la gestion globale de la partie jusqu'à sa fin.<br/>
  Il affiche les actions de jeu ainsi que les scores en fin de partie.
>## II. Point d avancement
[Sommaire](#participants-)
### Liste de l'ensemble des fonctionnalités du projet :<br/>
Pour chaque classe seront listés : les attributs, les méthodes (hors getters et setters) et leur fonctionnement<br/>
Voici le code couleur suivant le type d'objet : </br>
<span style="color: #26B260">String</span> <span style="color: #57D1FA">int</span> <span style="color: #815151">Booléan</span><br/>
<span style="color: #BF39C3">quartier</span> <span style="color: #F87CF6">bâtiment</span> <span style="color: #FA43BA">deck</span><br/>
<span style="color: #00FFB2">personnage</span> <br/>
<span style="color: #FFFB03">joueur</span> <span style="color: #FF0303">bot</span> <br/>
---
### Cartes :
#### Batiments
Attributs : <span style="color: #26B260">nom</span>, <span style="color: #57D1FA">cout</span>, <span style="color: #BF39C3">quartier</span><br/>
Méthode :
* messageActionCarte() = renvoit  *<span style="color: #26B260">La carte x a été construite.</span>*
  <br/>[*retour*](#cartes-)
#### Deck
Attribut : <span style="color: #FA43BA">LinkedList deck</span><br/>
Méthodes :
* pioche() = donne un  <span style="color: #F87CF6">bâtiment</span>
* jette(Batiment e) = ajoute <span style="color: #F87CF6">e</span> jetée au fond du <span style="color: #FA43BA">deck</span><br/>
  <br/>[*retour*](#cartes-)
---
### Personnages :
#### Assassin
Attributs : <span style="color: #26B260">Assassin</span>, <span style="color: #57D1FA">numéro</span>, <span style="color: #BF39C3">null</span><br/>
Méthodes :
* action(Joueur joueur, Joueur cible, Deck deck, int[] nombre,int typeAction) = la <span style="color: #FFFB03">cible</span> est tuée
* message(String nom, int numero) = renvoit *<span style="color: #26B260">Assassin a tué x.</span>*
  <br/>[*retour*](#joueurs-)
#### Voleur
Attributs : <span style="color: #26B260">Voleur</span>, <span style="color: #57D1FA">numéro</span>, <span style="color: #BF39C3">null</span><br/>
Méthodes :
* action(Joueur joueur, Joueur cible, Deck deck, int[] nombre,int typeAction) = la <span style="color: #FFFB03">cible</span> est volée
* message(String nom, int numero) = renvoit *<span style="color: #26B260">Voleur a volé x.</span>*
  <br/>[*retour*](#joueurs-)
#### Magicien
Attributs : <span style="color: #26B260">Magicien</span>, <span style="color: #57D1FA">numéro</span>, <span style="color: #BF39C3">null</span>, <span style="color: #815151">messagePioche</span><br/>
Méthodes :
* action(Joueur joueur, Joueur cible, Deck deck, int[] nombre,int typeAction) = si <span style="color: #57D1FA">typeAction</span> égale 0, on échange notre main de <span style="color: #F87CF6">cartes</span> avec la <span style="color: #FFFB03">cible</span>,<br/> sinon on échange certaines <span style="color: #F87CF6">cartes</span> (suivant les valeurs de *nombre*)de notre main avec la <span style="color: #FA43BA">pioche</span>.
* message(String nom, int numero) = renvoit suivant <span style="color: #815151">messagePioche</span> *<span style="color: #26B260">Magicien a échangé ses cartes avec la pioche.</span>*  ou *<span style="color: #26B260">Magicien a échangé ses cartes avec x.</span>*
  <br/>[*retour*](#joueurs-)
#### Roi
Attributs : <span style="color: #26B260">Roi</span>, <span style="color: #57D1FA">numéro</span>, <span style="color: #BF39C3">NOBLE</span><br/>
Méthodes :
* action(Joueur joueur, Joueur cible, Deck deck, int[] nombre,int typeAction) = <span style="color: #FFFB03">joueur</span> reçoit une pièce par <span style="color: #BF39C3">quartier NOBLE</span> dans sa ville et une pièce l'<span style="color: #F87CF6">Ecole de Magie</span>
* message(String nom, int numero) = renvoit *<span style="color: #26B260">Le Roi prend la couronne.</span>*
  <br/>[*retour*](#joueurs-)
#### Eveque
Attributs : <span style="color: #26B260">Eveque</span>, <span style="color: #57D1FA">numéro</span>, <span style="color: #BF39C3">RELIGIEUX</span><br/>
Méthodes :
* action(Joueur joueur, Joueur cible, Deck deck, int[] nombre,int typeAction) = <span style="color: #FFFB03">joueur</span> reçoit une pièce par <span style="color: #BF39C3">quartier RELIGIEUX</span> dans sa ville et une pièce l'<span style="color: #F87CF6">Ecole de Magie</span>
* message(String nom, int numero) = renvoit *<span style="color: #26B260">""</span>*
  <br/>[*retour*](#joueurs-)
#### Marchand
Attributs : <span style="color: #26B260">Marchand</span>, <span style="color: #57D1FA">numéro</span>, <span style="color: #BF39C3">COMMERCE</span><br/>
Méthodes :
* action(Joueur joueur, Joueur cible, Deck deck, int[] nombre,int typeAction) = si <span style="color: #57D1FA">typeAction</span> égale 0, <span style="color: #FFFB03">joueur</span> reçoit une pièce par <span style="color: #BF39C3">quartier COMMERCE</span> dans sa ville et une pièce s'il possède l'<span style="color: #F87CF6">Ecole de Magie</span> sinon on reçoit une pièce
* message(String nom, int numero) = renvoit *<span style="color: #26B260">""</span>*
  <br/>[*retour*](#joueurs-)
#### Architecte
Attributs : <span style="color: #26B260">Architecte</span>, <span style="color: #57D1FA">numéro</span>, <span style="color: #BF39C3">null</span><br/>
Méthodes :
* action(Joueur joueur, Joueur cible, Deck deck, int[] nombre,int typeAction) =  <span style="color: #FFFB03">joueur</span> pioche <span style="color: #F87CF6">deux cartes</span>
* message(String nom, int numero) = renvoit *<span style="color: #26B260">""</span>*
  <br/>[*retour*](#joueurs-)
#### Condottiere
Attributs : <span style="color: #26B260">Condottiere</span>, <span style="color: #57D1FA">numéro</span>, <span style="color: #BF39C3">MILITAIRE</span>, <span style="color: #26B260">batimentDetruit</span><br/>
Méthodes :
* action(Joueur joueur, Joueur cible, Deck deck, int[] nombre,int typeAction) = si <span style="color: #57D1FA">typeAction</span> égale 0, <span style="color: #FFFB03">joueur</span> reçoit une pièce par <span style="color: #BF39C3">quartier MILITAIRE</span> dans sa ville et une pièce s'il possède l'<span style="color: #F87CF6">Ecole de Magie</span>
  <br/>  sinon le <span style="color: #00FFB2">Condottiere</span> tente de détruire un <span style="color: #F87CF6">bâtiment</span> de sa <span style="color: #FFFB03">cible</span> suivant la taille de la ville de la cible, son argent disponible et son degré d'aggressivité
* message(String nom, int numero) = renvoit *<span style="color: #26B260">"Condottiere cible x"</span>* ainsi que *<span style="color: #26B260">"Condottiere a attaqué x et a détruit batimentDetruit."</span>* si le Condottiere détruit un bâtiment
  <br/>[*retour*](#joueurs-)
---
### Joueurs :
#### Joueur
Attributs : <span style="color: #26B260">nom</span>, <span style="color: #57D1FA">argent</span>, <span style="color: #00FFB2">personnage</span>, <span style="color: #F87CF6">ArrayList mainJoueur</span>, <span style="color: #F87CF6">ArrayList citeJoueur</span>, <span style="color: #57D1FA">hostileLevel</span>, <span style="color: #815151">isKilled</span>, <span style="color: #815151">isRobbed</span>, <span style="color: #815151">aDetruitBatiment</span><br/>
Méthodes :
* piocherArgent(int argent) = le <span style="color: #FFFB03">joueur</span> ajoute x argent
* retireArgent(int cout) =  le <span style="color: #FFFB03">joueur</span> retire x argent
* piocherBatiment(Batiment batiment) = le <span style="color: #FFFB03">joueur</span> ajoute un <span style="color: #F87CF6">bâtiment</span> à sa <span style="color: #F87CF6">mainJoueur </span>
* popBatiment() = le <span style="color: #FFFB03">joueur</span> retire et renvoit la <span style="color: #F87CF6">première carte</span> de sa <span style="color: #F87CF6">mainJoueur </span> ou null si elle est vide
* citeContains(String nomCarte) = renvoit <span style="color: #815151">si</span> le <span style="color: #FFFB03">joueur</span> possède un <span style="color: #F87CF6">nomBatiment</span> dans sa <span style="color: #F87CF6">citeJoueur</span>
* echangeBatiment(Batiment batiment,int indice) = échange et renvoit le <span style="color: #F87CF6">bâtiment</span> à l'<span style="color: #57D1FA">indice x</span> dans la <span style="color: #F87CF6">citeJoueur</span> avec le <span style="color: #F87CF6">bâtiment</span> en entrée.
* construireBatiment(Batiment batiment) = renvoit un <span style="color: #815151">booléan</span> si le <span style="color: #F87CF6">bâtiment</span> en entrée apparaît dans la <span style="color: #F87CF6">mainJoueur</span> et le retire ainsi que déduit son <span style="color: #57D1FA">coût</span> de l'<span style="color: #57D1FA">argent</span>
* jouerAction(Joueur j, Joueur cible, Deck deck, int[] indices,int typeAction) = si <span style="color: #815151">isKilled</span> est false, il joue son action de <span style="color: #00FFB2">personnage</span>
* messagePiocheArgent() = renvoit *<span style="color: #26B260">"Le joueur a pioché 2 pièces."</span>*
* messagePiocheBatiment() = renvoit *<span style="color: #26B260">"Le joueur a pioché un batiment."</span>*
* messageAction(Joueur cible) = renvoit *<span style="color: #26B260">le message d'action </span>* du <span style="color: #00FFB2">personnage</span> du <span style="color: #FFFB03">joueur</span>
* affichageJoueur() = renvoit *<span style="color: #26B260">"Roi appelle personnageX (HostileLevelX). Pour ce tour, il possède x pièces, x batiments dans sa cité qui sont x et une main qui est x."</span>*
  <br/>[*retour*](#joueurs-)
#### Bot
Attributs : <span style="color: #FFFB03">joueur</span>, <span style="color: #26B260">messageRound</span>, <span style="color: #57D1FA">ArrayList ciblesDispo</span>
<br/>Méthodes :
* retirePersonnage() = renvoit le <span style="color: #00FFB2">personnage</span> et supprime le sien
* recoisBatiment(Batiment batiment) = utilise <span style="color: #FFFB03">joueur</span>.piocherBatiment(Batiment batiment) <br/><br/>
* jouerVol(Bot[] adversaires) = retire et donne au <span style="color: #FF0303">bot</span> qui a le <span style="color: #00FFB2">Voleur</span> l' <span style="color: #57D1FA">argent</span> qu'il possède et reset <span style="color: #815151">isRobbed</span>
* jouerForge(Deck deck) = s'il possède la <span style="color: #F87CF6">Forge</span>, peu de <span style="color: #F87CF6">bâtiments</span> dans sa <span style="color: #F87CF6">mainJoueur</span> et plus de 6 pièces :
  <br/> il perd 2 pièces, pioche 3 <span style="color: #F87CF6">bâtiments</span> et ajoute *<span style="color: #26B260">"Le joueur a pioché trois bâtiments en dépensant 2PO."</span>* à <span style="color: #26B260">messageRound</span>
* jouerLaboratoire(Deck deck) = s'il possède le <span style="color: #F87CF6">Laboratoire</span> et plus de 4 de <span style="color: #F87CF6">bâtiments</span> dans sa <span style="color: #F87CF6">mainJoueur</span> : <br/>
  il gagne 2 pièces, un retire une <span style="color: #F87CF6">carte</span> de sa <span style="color: #F87CF6">mainJoueur</span>, de préférence une déjà construite. Ajoute *<span style="color: #26B260">"Le joueur a détruit une carte de sa main pour récuperer 2PO."</span>* à <span style="color: #26B260">messageRound</span>
* jouerPiocherCarte(Deck deck) = il pioche 2 <span style="color: #F87CF6">cartes</span> ou 3 s'il possède <span style="color: #F87CF6">Observatoire</span> puis garde en une dans sa <span style="color: #F87CF6">mainJoueur</span> ou les toutes s'il possède la <span style="color: #F87CF6">Bibliothèque</span>.
  <br/> Ajoute obligatoirement <span style="color: #FFFB03">j</span>.messagePiocheBatiment() puis suivant le cas *<span style="color: #26B260">"Le joueur a utilise l'Observatoire et pioche un troisième choix."</span>*, *<span style="color: #26B260">"Le joueur a pioché trois bâtiments grâce à la bibliothèque."</span>*, *<span style="color: #26B260">Le joueur a pioché deux bâtiments grâce à la bibliothèque."</span>*, *<span style="color: #26B260">Le joueur a pioché deux bâtiments grâce à la bibliothèque."</span>*
* jouerSonTour(Bot[] adversaires,Deck deck) = va faire jouer un tour de jeu au <span style="color: #FF0303">bot</span> c'est à dire dans l'ordre :
  <br/> si  <span style="color: #815151">isKilled</span> ajoute *<span style="color: #26B260">"x a été tué."</span>* à <span style="color: #26B260">messageRound</span> et s'arrête.
  <br/> sinon :
  <br/> - si <span style="color: #815151">isRobbed</span> ajoute *<span style="color: #26B260">"x a été volé."</span>* à <span style="color: #26B260">messageRound</span> et fait <span style="color: #FF0303">bot</span>.jouerVol(Bot[] adversaires)
  <br/> - ajoute <span style="color: #FFFB03">joueur</span>.affichageJoueur() avec son <span style="color: #26B260">nom</span> à <span style="color: #26B260">messageRound</span>
  <br/> - <span style="color: #FF0303">bot</span>.jouerForge(Deck deck) , <span style="color: #FF0303">bot</span>.jouerLaboratoire(Deck deck)
  <br/> - puis <span style="color: #FF0303">bot</span>.jouerPiocherCarte(Deck deck) ou <span style="color: #FFFB03">joueur</span>.piocherArgent(2) suivant la situation du <span style="color: #FF0303">bot</span>
  <br/> - puis suivant le <span style="color: #00FFB2">personnage</span> actuel, il va jouer une stratégie différente
  <br/> - enfin ajoute *<span style="color: #26B260">toutes ses actions</span>* et <span style="color: #FFFB03">joueur</span>.affichageJoueur() à <span style="color: #26B260">messageRound</span><br/><br/>
* toutes les méthodes jouerPersonnage(...) = jouent l'action du <span style="color: #00FFB2">personnage</span> avec une stratégie différente par personnage et renvoit la <span style="color: #57D1FA">cible</span> (si elle existe)
* choixCibleCondottiere(Bot[] adversaires) = renvoit la <span style="color: #57D1FA">cible</span> du <span style="color: #00FFB2">Condottiere</span> c'est à dire le <span style="color: #FF0303">bot</span> qui a la plus grande cité mais qui a moins de 7 <span style="color: #F87CF6">bâtiments</span> et n'est pas <span style="color: #00FFB2">Evêque</span> <span style="color: #815151">non-isKilled</span>
* jouerPersoQuartier(Batiment aConstruire) = renvoit le <span style="color: #F87CF6">bâtiment</span> qui a été construit suivant une stratégie. Utilisée par <span style="color: #00FFB2">Roi</span>, <span style="color: #00FFB2">Eveque</span>, <span style="color: #00FFB2">Marchand</span> et <span style="color: #00FFB2">Condottiere</span>
* jouerBatimentAConstruire(Batiment aConstruire) = renvoit le <span style="color: #F87CF6">bâtiment</span> le plus cher qui puisse être construit. Utilisé dans <span style="color: #FF0303">bot</span>.jouerPersoQuartier(Batiment aConstruire)
* jouerMessageDuTour(int indiceCible,Batiment aConstruire,Bot[] adversaires) = si la <span style="color: #57D1FA">cible</span> a été définie (différente de -1), ajoute à <span style="color: #26B260">messageRound</span> le <span style="color: #26B260">message d'attaque</span> du <span style="color: #00FFB2">Condottiere,Voleur,Assassin</span>
  <br/> ou le <span style="color: #26B260">message </span> spécial (car plusieurs <span style="color: #F87CF6">bâtiments</span> construits) de l'<span style="color: #00FFB2">Architecte</span><br/><br/>
* nombreBatimentParQuartier() = renvoit un <span style="color: #57D1FA">int[]</span> qui contient le nombre de <span style="color: #F87CF6">bâtiments</span> par <span style="color: #BF39C3">quartier</span> dans la <span style="color: #F87CF6">citeJoueur</span>
* coutMinMaxConstructible(List<Batiment> liste,String minOuMax,int maxArgent) = renvoit le <span style="color: #F87CF6">bâtiment</span> qui le plus cher ou moins cher qui puisse être construit dans la <span style="color: #F87CF6">citeJoueur</span> contenu dans la <span style="color: #F87CF6">mainJoueur</span> <br/><br/>
* choixSuivantPersonnalite(int[] choixPersonnage) = renvoit un <span style="color: #57D1FA">int[]</span> qui pour chaque <span style="color: #57D1FA">valeur</span> (dont l'indice correspond au <span style="color: #57D1FA">numéro</span> du <span style="color: #00FFB2">personnage</span>), indice la volonté de prendre le <span style="color: #00FFB2">personnage</span> correspondant.
  <br/> Plus la volonté est importante, plus la valeur est élevée. Prend en compte plusieurs paramètres.
* choixFinDePartie(int[] choixPersonnage) = renvoit un <span style="color: #57D1FA">int[]</span> qui modifie renvoit un <span style="color: #57D1FA">choixPersonnage</span> en modifiant des valeurs suivant des stratégies de fin de partie.
* chooseCharacter(ArrayList<Personnage> liste,ArrayList<Personnage> defausse) = renvoit <span style="color: #00FFB2">liste</span> sans le <span style="color: #00FFB2">personnage</span> qui a été choisi.
  <br/> Il est choisi en utilisant choixSuivantPersonnalite() et choixFinDePartie() en retirant les personnages n'ont présent.
  <br/> De plus on actualise <span style="color: #57D1FA">ciblesDispo</span> suivant les <span style="color: #00FFB2">personnages</span> disponibles que l'on ne choisit pas.
* setCibles(ArrayList<Personnage> encoreDispo, ArrayList<Personnage> defausse) = renvoit <span style="color: #57D1FA">ciblesDispo</span>, l'ensemble des <span style="color: #00FFB2">personnages</span> potentielles à attaquer suivant la quantité de encoreDispo
* choixCible(Bot[] adversaires) = renvoit la <span style="color: #57D1FA">cible</span> si on est <span style="color: #00FFB2">Assassin</span> ou <span style="color: #00FFB2">Voleur</span> suivant les stratégies de Richard
* choixCibleAssassin(Bot[] adversaires) = renvoit la <span style="color: #57D1FA">cible</span> de l'<span style="color: #00FFB2">Assassin</span>. Utilisée par choixCible()
  <br/>[*retour*](#joueurs-)
---
#### Plateau
Attributs :  <span style="color: #FF0303">listeBots[]</span>, <span style="color: #FF0303">ArrayList ordreDeChoix</span>, <span style="color: #FF0303">actualKing</span>
<br/><span style="color: #57D1FA">banque</span>, <span style="color: #57D1FA">actualKilled</span>, <span style="color: #57D1FA">actualStolen</span>, <span style="color: #57D1FA">numRound</span>, <span style="color: #57D1FA">firstToFinish</span>,
<br/><span style="color: #FA43BA">pioche</span>, <span style="color: #26B260">messageRound</span>, random, log, writer,
<br/><span style="color: #00FFB2">ArrayList listePersonnage</span>, <span style="color: #00FFB2">ArrayList defausseeVisible</span>, <span style="color: #00FFB2">ArrayList defausseeCachee</span>,
<br/>Méthodes :
* choosingOrder() = on regarde qui est le <span style="color: #FF0303">actualKing</span> et on set <span style="color: #26B260">messageRound</span> à <span style="color: #26B260">"Le roi actuel est : X"</span> puis on ajoute à <span style="color: #FF0303">ordreDeChoix</span> les <span style="color: #FF0303">bots</span> dans l'ordre horaire (modulo)
  <br/> en partant du <span style="color: #00FFB2">roi</span>. On ajoute ensuite <span style="color: #26B260">"Les joueurs choisissent leur rôle selon l'ordre suivant: X, Y, ..."</span> à <span style="color: #26B260">messageRound</span><br/><br/>
* choosingCharacter(Bot bot) = demande à un <span style="color: #FF0303">bot</span> de choisir son <span style="color: #00FFB2">personnage</span> en utilisant <span style="color: #FF0303">bot</span>.chooseCharacter(listePersonnage, defausseeVisible) et set
  <br/> <span style="color: #26B260">messageRound</span> à <span style="color: #26B260">"Le bot x a choisi le rôle y"</span>.<br/><br/>
* personnageComeBack() = remet dans la <span style="color: #00FFB2">listePersonnage</span> les <span style="color: #00FFB2">personnages</span> que possédaient les <span style="color: #FF0303">bots</span>.
* miseEnPlaceCarte() = utilise <span style="color: #FF0303">bot</span>.recoisBatiment(<span style="color: #FA43BA">pioche</span>.pioche()) pour donner 4 <span style="color: #F87CF6">bâtiments</span> à chaque <span style="color: #FF0303">bot</span>.<br/><br/>
* tourDeJeu(boolean affichage) = Si <span style="color: #815151">affichage</span> est true, le message <span style="color: #26B260">message " Tour de jeu : x "</span> sera affiché, sinon il restera caché. On enregistre le même <span style="color: #26B260">message</span> dans le logger
  <br/> au niveau INFO. Ensuite, tous les <span style="color: #FF0303">bots</span> jouent au moment du tour du <span style="color: #00FFB2">personnage</span> qu'ils ont choisi avec this.<span style="color: #FF0303">listeBots[x]</span>.jouerSonTour(this.<span style="color: #FF0303">listeBots</span>, this.<span style="color: #FA43BA">pioche</span>). On ajoute ensuite
  <br/> le <span style="color: #26B260">messageRound</span> dans le logger au niveau INFO et on l'affiche sur la sortie standard si <span style="color: #815151">affichage</span> est true. Ensuite, on fait jouer le <span style="color: #F87CF6">cimetiere</span>, et enfin, on vérifie si le <span style="color: #FF0303">bot</span> a atteint
  <br/> les 7 <span style="color: #F87CF6">bâtiments</span> construits au cours de son tour.<br/><br/>
* cimetiere(int numeroPersoEnCours,int joueurEnCours, boolean affichage) = Si le <span style="color: #00FFB2">personnage</span> en train de jouer est le <span style="color: #00FFB2">Condottiere</span> et qu'il détruit un <span style="color: #F87CF6">bâtiment</span>, on vérifie si un <span style="color: #FF0303">bot</span>
  <br/> différent de celui qui joue le <span style="color: #00FFB2">Condottiere</span> possède le <span style="color: #F87CF6">cimetière</span> dans sa <span style="color: #F87CF6">cité</span>. Dans ce cas, nous vérifions si le <span style="color: #FF0303">bot</span>  possède <span style="color: #57D1FA">1 PO ou plus</span>, s'il ne possède pas le <span style="color: #F87CF6">bâtiment</span> détruit
  <br/> dans sa <span style="color: #F87CF6">cité</span>, et si sa main est de taille inférieure ou égale à 6. Dans ce cas, nous lui attribuons le <span style="color: #F87CF6">bâtiment</span> et lui retirons <span style="color: #57D1FA">1PO</span>. Nous enregistrons alors dans le logger au niveau
  <br/> INFO le <span style="color: #26B260">message</span> : <span style="color: #26B260">"Le joueur jx a utilisé 1 PO pour récupérer le bâtiment Y dans sa main."</span>. Si <span style="color: #815151">affichage</span> est true, ce même <span style="color: #26B260">message</span> est affiché sur la sortie standard.<br/><br/>
* allRound(boolean affichage) = Lance miseEnPlaceCarte(), puis tant que isEndGame() n'est pas <span style="color: #815151">true</span>, lance choosingOrder(), puis défausse un nombre de <span style="color: #00FFB2">carte personnage</span> en
  <br/> fonction du nombre de <span style="color: #FFFB03">joueur</span>. Enregistre le logger et affiche si nécessaire le <span style="color: #26B260">message</span> : <span style="color: #26B260">"X a été défaussé et ne jouera pas ce tour-ci."</span>. S'il y a moins de 7 <span style="color: #FFFB03">joueurs</span>,
  <br/> défausse une <span style="color: #00FFB2">carte personnage</span> supplémentaire qui est cachée aux <span style="color: #FF0303">bots</span> et enregistre dans le logger : <span style="color: #26B260">"Un autre personnage secret a été défaussé et ne jouera pas ce tour-ci."</span>.
  <br/> Ensuite, effectue les choix des <span style="color: #00FFB2">personnages</span> avec choosingCharacter(this.ordreDeChoix.get(<span style="color: #FF0303">b</span>)), lance tourDeJeu(<span style="color: #815151">affichage</span>), affiche le score (<span style="color: #815151">affichage</span>), actualise la
  <br/> possession de <span style="color: #FF0303">actualKing</span>, puis remet les <span style="color: #00FFB2">cartes personnage</span> dans la liste avec personnageComeBack().<br/><br/>
* isEndGame() = revoit un <span style="color: #815151">booléan</span> qui passe à <span style="color: #815151">true</span> si un bot possède une <span style="color: #F87CF6">citéJoueur</span> de taille supérieur ou égale à 7.
* scoreEnsembleBatiment(Bot bot) = renvoit le <span style="color: #57D1FA">score</span> d'un <span style="color: #FF0303">bot</span> correspondant à la somme du <span style="color: #57D1FA">cout</span> des <span style="color: #F87CF6">batiments</span> de sa <span style="color: #F87CF6">cité</span>.
* scoreMerveille(Bot bot) = renvoit un <span style="color: #57D1FA">int</span> correspondant au score apporté par les <span style="color: #F87CF6">merveilles</span> possédées par le <span style="color: #FF0303">bot</span>. <span style="color: #57D1FA">+2</span> si il possède le <span style="color: #F87CF6">Dracoport</span>, <span style="color: #57D1FA">+2</span> pour <span style="color: #F87CF6">Universite</span>,
  <br/> <span style="color: #57D1FA">+(son argent)</span> pour <span style="color: #F87CF6">Tresor Imperial</span> et <span style="color: #57D1FA">+(son nombre de carte)</span> pour <span style="color: #F87CF6">Salle des Cartes</span>.<br/><br/>
* scoreFinirCiteOuPremier(Bot bot, int numero) = renvoit un <span style="color: #57D1FA">int</span> égale à <span style="color: #57D1FA">4</span> si le joueur est le premier à finir sa cité, sinon égale <span style="color: #57D1FA">2</span> tant que le bot à fini sa <span style="color: #F87CF6">cité</span>.
* scoreCinqTypeBatiment(Bot bot) = renvoit un <span style="color: #815151">Booléan</span> qui est <span style="color: #815151">True</span> si le <span style="color: #FF0303">bot</span> possède dans sa cité les 5 types de <span style="color: #BF39C3">quartier</span> ou 4 et la <span style="color: #F87CF6">Cour des Miracles</span>.
* scoreBot() = renvoit un <span style="color: #57D1FA">int[]</span> des <span style="color: #57D1FA">scores</span> de chaque <span style="color: #FF0303">bot</span> en utilisant scoreEnsembleBatiment(Bot <span style="color: #FF0303">bot</span>), scoreMerveille(Bot <span style="color: #FF0303">bot</span>), scoreFinirCiteOuPremier(Bot <span style="color: #FF0303">bot</span>, int <span style="color: #57D1FA">numero</span>)
  <br/> et scoreCinqTypeBatiment(Bot <span style="color: #FF0303">bot</span>).<br/><br/>
* score(boolean affichage) = renvoit le <span style="color: #FF0303">bot</span> gagnant en utilisant scoreBot() puis en cherchant le <span style="color: #57D1FA">scoreMax</span> parmi les résultats, si deux <span style="color: #FF0303">bots</span> ont un <span style="color: #57D1FA">score</span> égale, on regarde celui
  <br/> ayant choisit le <span style="color: #00FFB2">personnage</span> jouant le plus tard dans l'ordre de jeu. On affiche ensuite score dans le message :
  <br/> <span style="color: #26B260"> "Les scores sont de :
  <br/>X pour le joueur Y
  <br/>...
  <br/>Le joueur X remporte la victoire !"</span><br/>
  <br/>[*retour*](#plateau-)

---

### Explication Logs :<br/>

Nous avons ajouté un système d'affichage permettant de choisir en fonction de la situation d'afficher uniquement certains messages. Pour cela, nous avons utilisé le système de **LOGGER**.
Ce système permet d'assigner à un **string** un certain niveau d'importance allant de **FINEST** à **SEVERE**. Il est ensuite possible de décider d'afficher 
un niveau d'importance. En utilisant ce principe, nous avons assigné un niveau d'importance **INFO** aux messages d'affichage
de la partie ainsi lorsque l'on lance une partie en mode **"--démo"** le niveau d'affichage est mis à un niveau **INFO** et l'intégralité de la partie est affichée sur la sortie standard,
mais lorsque l'on décide de lancer une autre commande, le niveau d'affichage est mis à **SEVERE** et ainsi les messages precedent ne le sont plus.
<br/><br/>La classe MyLogger dans src/main/com/vogella.logger/ est utilisée dans notre classe **plateau** ainsi que dans la classe **main**.
<br/><br/>Les methodes utilisées sont :
* **log.getLogger().info/severe(...)** : pour assigner un niveau d'importance.
* **log.getLogger().setLevel(Level.INFO/SEVERE)** : pour mettre à jour le niveau d'affichage voulu. 
<br/>[Sommaire](#participants-)
---

### Explication CSV :<br/>
Nous avons également ajouté un système de statistiques dont les données sont dans des fichiers CSV. Pour manipuler les fichiers CSV, nous avons utilisé **OPENCSV**.<br/>
Il y a trois fichiers CSV tous dans le dossier stats. Il y a :
* **botVSbot.csv**, qui contient les données de 1000 parties sur le meilleur bot contre lui-même et 1000 entre le meilleur bot et le deuxième meilleur.
* **demo.csv**, qui contient le résultat du score d'une partie avec l'agressivité de chaque bot.
* **gamestats.csv**, qui contient les statistiques de x parties entre les mêmes bots.

Pour les deux premiers fichiers nous allons juste écraser le résultat précédent en n'afficher que le récent sur le fichier csv.<br/>
Pour **gamesStats.csv** nous récupérons les données puis une partie est lancée. Les statistiques sont mises à jour et les nouvelles statistiques sont mises à jour et écrites.<br/>
La classe **WritingCSVFileExemple** dans src/main/com/opencsv/ est utilisée ainsi que la classe **main** qui effectue toutes les opérations.<br/>
La classe **WritingCSV** dans src/main/com/opencsv/ est utilisée ainsi que la classe **main** qui effectue toutes les opérations.<br/>

Les commandes à réaliser pour obtenir les différents résultats sont : <br/>
* mvn exec:java -Dexec.args="--2thousands" : pour afficher **botVSbot.csv**
* mvn exec:java -Dexec.args="--demo --csv" : pour afficher **demo.csv**
* mvn exec:java -Dexec.args="--csv" : pour afficher **gamestats.csv**
  <br/>[Sommaire](#participants-)

---

### Explication Bot :<br/>
La stratégie du Bot est axée suivant plusieurs points :
* Le choix de son rôle
* Le choix entre pioche des pièces ou des cartes
  @@ -268,67 +274,123 @@
  S'il possède peu de cartes, le Bot choisira plutôt un Architecte ou Magicien. Sinon pour chaque type de quartier qu'il possède (Noble, Religieux ...) le personnage associé sera priorisé.<br/>
  Le niveau d'agressivité du bot va définir sa personnalité (*méthode choixSuivantPersonnalite() de Bot*) :<br/>
* de 0 à 3, c'est un bâtisseur, il prendra si disponible un personnage entre Roi, Évêque, Marchand, Architecte et Condottiere. S'il a le Condottiere, il ne détruira que des bâtiments que si cela est gratuit.
* de 4 à 7, c'est un opportuniste, il prendra Assassin et Voleur en début de partie puis Évêque et Condottiere dès qu'il possède 5 bâtiments dans sa cité (proche de la fin de partie). S'il a le Condottiere, il ne détruira que des bâtiments que si cela est gratuit.
* enfin, de 8 à 10, c'est un agressif, il prendra Assassin, Voleur et Condottiere quasiment exclusivement. S'il a le Condottiere, il détruira des bâtiments même si cela lui coûtera de l'argent (jusqu'à 6 pièces s'il est à 10 d'agressivité).
  De plus, le Bot possède des stratégies en fin de partie (*méthode choixFinDePartie() de Bot*):
* S'il a au moins 4 bâtiments et 4 pièces, il ne lui que 3 bâtiments à construire. Il va donc choisir en priorité Architecte pour finir rapidement.
* S'il a au moins 5 bâtiments et qu'il peut construire au moins un bâtiment, il va vouloir prendre le Roi pour commencer premier au tour suivant et possiblement finir sa ville en premier.
* S'il a au moins 6 bâtiments, il va choisir un personnage suivant son rôle d'appel (1 pour Assassin, 2 pour Voleur, ...) en cherchant à finir le plus tôt possible. <br/>Engagement, il choisira plus facilement l'Évêque et le Condottiere pour ne pas être la cible du Condottiere et viser si possible un adversaire.
#### Choix de la Cible
##### Assassin / Voleur :
L'Assassin et le Voleur ciblent un rôle. Le Bot va récupérer l'ensemble de ses cibles (*setCible()*) quand il choisit son rôle, c'est-à-dire les autres rôles. <br/>
L'Assassin va cibler (*choixCibleAssassin()*) dans l'ordre (s'il est disponible) :
* Le condottiere si l'Assassin possède la plus grande cité.
* L'Évêque, si le condottiere est également disponible et que quelqu'un possède 6 bâtiments. Cela afin que le Condottiere puisse cible l'Évêque.
* Le Magicien, si l'Assassin possède le plus de cartes.
* Le Voleur, si l'un des joueurs est riche pour pas que le voleur s'enrichisse trop.
* L'Architecte
* Le Marchand
* Le Roi
* Le Voleur
* Le Condottiere
* L'Évêque
* Et enfin Magicien
  Le Voleur va cibler (*choixCible()*) dans l'ordre (s'il est disponible et non-tué) :
* L'Architecte
* Le Marchand
* Le Roi
* Le Condottiere
* L'Évêque
* Et enfin Magicien
##### Magicien :
Il échange sa main avec le joueur qui possède le plus de cartes en main (*jouerMagicien()*). <br/>S'il n'y a personne, il échange les cartes qu'il a déjà construites avec la pioche.
##### Condottiere :
Il cible l'adversaire qui possède la plus grande ville non finie (moins de 7 bâtiments) et qui n'est pas Évêque en vie.

#### Meilleur Bot :
Pour définir le meilleur Bot, c'est-à-dire celui qui possède le meilleur score en moyenne suivant son niveau d'agressivité nous avons fait jouer durant 1000 parties 2 bâtisseurs, 2 opportunists et 2 agressifs.
<br/> Les deux bâtisseurs ont le meilleur résultat, il s'agit donc des meilleurs Bots.
<br/> Ce résultat s'explique par le fait :
* que choisir Assassin et Voleur ne permet pas de forcèment toucher sa cible. Et dans le cas du Voleur, vu que chaque tour, les Bots construisent le bâtiment le plus cher qu'ils puissent construire, il ne leur reste que peu d'argent, rendant le Voleur moins utilise.
* que le Condottiere dans le cas des Agressifs, leur fera perdre de l'argent.

<br/>[Sommaire](#participants-)

---

>## III. Architecture et Qualité
### Architecture :
L'architecture du projet est la suivante :
* Dans src/main/java il y a toutes les classes, chacune dans son package (exemple : tous les personnages dans personnages). <br/>
  Nous avons ainsi regroupé les classes par lien entre elles : le Bot et le Joueur sont liés donc dans le même package
* Dans src/test/java il y a tous les tests, chacun dans son package.
* Dans le dossier doc/ vous avez le Rapport que vous lisez actuellement
* Dans le dossier stats/ vous avez les fichiers csv

<br/>[Sommaire](#participants-)

---

### Documentation :
Vous avez ce rapport qui documente sur beaucoup d'éléments dont les méthodes principales et attributs de chaque classe. <br/>
Il y a un README.md (très peu utile) et un CONTRIBUTING.md qui informe sur la stratégie de branche git.<br/>
Chaque classe possède des commentaires (plus ou moins fréquemment) pour l'explication du code mais il n'y a pas de Javadoc.<br/>

<br/>[Sommaire](#participants-)

---

### Status du Code :
Les dernières classes sont les moins bien écrites : Main, WritingCSV et MyLogger.<br/>
La classe Plateau a été modifiée par la même occasion et donc n'est pas totalement couverte de tests.<br/>
Sinon les classes des packages carte, joueurs et personnages sont totalement couvertes de tests.<br/>
Ainsi, nous obtenons 70% de coverage. Les derniers 30% sont répartis sur Main(0%), MyLogger(0%), WriterCSV(0%), UseLogger(66%) et Plateau(55%)<br/><br/>

Nous avons 32 code smells, majoritairement sur les dernières classes et sur les types de renvois des méthodes (List plutôt que ArrayList).<br/>

<br/>[Sommaire](#participants-)

---

>## IV. Processus

### Qui a fait quoi ?

* Architecture : Théo
* README : Théo
* CONTRIBUTING : Hugo
* RAPPORT : Théo / Maxence
  <br/>
* Carte : Théo
* Batiment : Théo
* Deck : Hugo
* Personnages : Adam / Théo
* Joueur : Théo / Maxence
* Bot : Adam / Maxence / Théo / Hugo
* Plateau : Hugo / Maxence / Théo
  <br/>
* Tests : Adam / Maxence / Théo / Hugo
* Loggers : Hugo / Maxence
* CSV : Théo / Hugo
* Jcommander : Maxence / Hugo
  <br/>[Sommaire](#participants-)

---

### Process de la Team :

Le branching strategy est dans CONTRIBUTING.md<br/>
Le process est :
* chaque semaine, le nouveau code est discuté par l'entièreté de l'équipe
* les nouvelles issues et milestones sont ainsi définies avec une idée de comment les réaliser
* l'équipe étudie souvent le jeu Citadelles de façon concrète et approfondie afin d'en comprendre les mécaniques et stratégies
* un serveur de messagerie permet d'avertir quand quelqu'un code, de ce qui est fait et à modifier

<br/>[Sommaire](#participants-)