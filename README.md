# 📄 Documentation : Sécurité Spring (Spring Security)

<div align="center">

![Spring Security](https://img.shields.io/badge/Spring%20Security-6.0+-green?style=for-the-badge&logo=spring)
![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=java)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)



</div>
## 🔐 Authentication vs Authorization

### **Authentification**
Processus permettant de vérifier l’identité d’un utilisateur.  
Exemple : vérifier email + mot de passe.

### **Autorisation**
Processus qui détermine ce qu’un utilisateur authentifié a le droit de faire.  
Exemples :
- ADMIN → accès complet
- USER → accès limité

---

# 🛡️ Attaques et protections dans Spring Security

## 🚨 1. Attaque par Brute Force
L’attaquant teste automatiquement de nombreux mots de passe jusqu’à trouver le bon.

### ✔️ Protection
- Limiter les tentatives
- Ajouter un CAPTCHA
- Mots de passe forts
- Verrouillage temporaire du compte

---

## 🧨 2. XSS (Cross-Site Scripting)
Injection de JavaScript malveillant dans une page.

### ✔️ Protection
- Échappement HTML (automatique avec Thymeleaf)
- Nettoyage des entrées
- Frameworks qui échappent les valeurs par défaut

---

## 🎯 3. CSRF (Cross-Site Request Forgery)
L’utilisateur authentifié est manipulé pour exécuter une action non voulue.

### ✔️ Protection
- Spring Security active **automatiquement** l’utilisation des tokens CSRF.

---

## 🛑 4. Session Fixation
L’attaquant force l’utilisateur à utiliser un Session ID connu, puis vole la session.

### ✔️ Protection
- Spring Security **renouvelle automatiquement le Session ID** après login.

---

## 🕵️ 5. Session Hijacking (Vol de session)
L’attaquant vole une session active (souvent via cookie).

### ✔️ Méthodes d’attaque
- Sniffing HTTP
- XSS (vol de cookies)
- Malware
- MITM (Man-in-the-middle)

### ✔️ Protection
- Utiliser HTTPS
- Cookies sécurisés (`HttpOnly`, `Secure`)
- Expiration rapide
- Régénération de session

---

# 🔒 Pourquoi HTTPS est indispensable ?

## 1. Chiffrement des données
Sans HTTPS :
- mots de passe
- emails
- cookies
- tokens

→ peuvent être lus par n’importe qui.

Avec HTTPS :  
Données illisibles même en cas d’interception.

---

## 2. Protection contre MITM
Sans HTTPS, un attaquant peut :
- lire
- modifier
- injecter du code

HTTPS empêche toute manipulation.

---

## 3. Empêche le vol de session
Les cookies sont chiffrés → inutilisables par l’attaquant.

---

## 4. Vérification de l’identité du serveur
Certificats SSL validés par des autorités.

---

## 5. SEO amélioré
Google favorise les sites HTTPS.

---

## 6. Obligatoire pour plusieurs API navigateur
- Service Workers
- Push Notifications
- Géolocalisation
- Caméra / Micro

---

# 🏰 Defense in Depth (Défense en profondeur)

Principe :  
Ne jamais dépendre d’une seule protection.  
Ajouter plusieurs couches.

Objectifs :
- rendre l’attaque plus difficile
- plus lente
- plus coûteuse
- plus détectable

---

## Les 5 couches principales

### 🛰️ 1. Sécurité réseau
- Firewall
- VPN
- HTTPS
- Nginx proxy
- Ports ouverts minimaux

---

### 🧩 2. Sécurité applicative (Spring Boot)
- Authentification
- Autorisation (rôles/permissions)
- Validation des entrées
- Protection XSS/CSRF
- BCrypt pour les mots de passe

---

### 🔐 3. Sécurité des données
- Chiffrement des données sensibles
- Variables d’environnement
- Permissions limitées dans la base
- Sauvegardes chiffrées

---

### 👤 4. Sécurité utilisateur
- Mots de passe forts
- MFA
- Anti-phishing
- Expiration des sessions

---

# 🔑 Fonctionnement de l’authentification Basic dans Spring Security

Lors d’une requête HTTP, le client envoie :
*****************************

