# Testowanie i Jakość Oprogramowania (Projekt)
## Aplikacja wspierająca wymiany wiadomości tekstowych na forum publicznym

### Krótki opis aplikacji

Aplikacja służy do publicznej wymiany wiadomości na forum publicznym (tworzenie postów). Każdy użytkownik będzie miał możliwość dodawania postów z istniejącymi tagami lub samodzielnym ich tworzeniu podczas upublicznianiu wiadomości. 
Projekt jest celowany dla każdej osoby chcącej mieć dostęp do wiadomości oraz możliwości publicznej publikacji postów.

### Funkcjonalność

Aplikacja jest podzielona na dwie warstwy : użytkownika oraz administratora. 
Każda rola będzie miał inne funkcje.

#### Użytkownik
- Tworzenie konta 
- Adaptacja profilu użytkownika
- Tworzenie postów i dodawanie ich na forum publiczne
- Wyszukiwanie oraz przegląd postów 
- Możliwość dodawania nowych tagów (funkcja dostępna podczas tworzenia postu)
- Komentowanie postu

> Użytkownik może stworzyć konto w serwisie.
> Każda funkcja jest możliwa tylko po zalogowaniu się.
> Wyszukiwanie postów odbywa się za pomocą tagów.
> Każdy post będzie posiadał minimum jeden tag (w celu uniknięcia sytuacji gdzie nie ma możliwości odnalezienia  wiadomości). 

#### Administrator
- Edycja oraz usuwanie tagów
- Blokowanie konta użytkownika

> Administrator może zablokować konto użytkownika w chwili złamania zasad korzystania z serwisu.

## Technologie 

Do stworzenia projektu posłużą technologie:

- Java
- Spring boot
- Thymeleaf

## Schemat bazy danych

![alt text](https://ibb.co/YX9PGSJ)

#### Projekt stworzony przez Bartłomieja Majewskiego



