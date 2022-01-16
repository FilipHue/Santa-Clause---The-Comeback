# SantaClause Is Coming to ACS Students

**Structura claselor:**
<pre>
+- - - - - - - - -+
|     Database    |
+- - - - - - - - -+
| 
|
|
|
|  Integer     +- - - - - - - -+
|- - - - - - > | numberOfYears |
|              +- - - - - - - -+
|
|
|
|  ArrayList   +- - - - - - - -+
|- - - - - - > |  santaBudget  |                                Child
|              +- - - - - - - -+                            |- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
|                                                           |                                                                                          |
|                                                           |                               +- - - - - - - - - - - - - - - - - -+                      |
|                                                           |                               |id, lastName, firstName,           |                      |
|  ArrayList   +- - - - - - - -+            Child           +- - - - - - - -+               |city, age, giftsPreferences,       |                      |
|- - - - - - > |    children   | < - - - - - - - - - - - - -|     Child     |- - - - - - - >|averageScore, assignedBudget,      |                      |
|              +- - - - - - - -+                            +- - - - - - - -+               |niceScoreHistory, type,            |                      |
|                                                     Child |                               |receivedGifts, elf, niceScoreBonus |                      |
|                                                   | - - - |                               +- - - - - - - - - - - - - - - - - -+                      |
|                                                   |                                       +- - - - - - - - - -+                                      |
|  ArrayList   +- - - - - - - -+           Present  |       +- - - - - - - -+               |productName,       |                                      |
|- - - - - - > |    presents   | < - - - - - - - - -|- - - -|     Present   |- - - - - - - >|price,             |                                      |
|              +- - - - - - - -+                    |       +- - - - - - - -+               |category, quantity |                                      |
|                                                   |       |                               +- - - - - - - - - -+      Present                         |
|                                                   |       | - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -|         |
|                                                   |                                                                                        |         |
|  ArrayList   +- - - - - - - -+            Change  |       +- - - - - - - -+                                                                |         |
|- - - - - - > |    changes    | < - - - - - - - - -|- - - -|    Change     |- - - - - - - - - - - - - - - -|  Double    +- - - - - - -+     |         |
|              +- - - - - - - -+                    |       +- - - - - - - -+                               | < - - - - -|  newBudget  |     |         |
|                                                   |                                                       |            +- - - - - - -+     |         |
|                                                   |                                                       |                                |         |
|                                                   |                                                       | ArrayList  +- - - - - - -+     |         |
|AnnualChanges +- - - - - - - -+            NewYear |       +- - - - - - - -+                               | < - - - - -| newGiftList |< - -|         |
|- - - - - - > | annualChanges | < - - - - - - - - -|- - - -|    NewYear    |                               |            +- - - - - - -+               |
               +- - - - - - - -+                    |       +- - - - - - - -+                               |                                          |
                                                    |       |                                               | ArrayList  +- - - - - - -+               |
                                                    |       |                                               | < - - - - -| newChildren |< - - - - - - -|
                                                    |       |    ArrayList    +- - - - - - - -+             |            +- - - - - - -+               
                                                    |       |- - - - - - - -> |    children   |             |                                         
                                                    |                         +- - - - - - - -+             | ArrayList  +- - - - - - - -+
                                                    |                                  ^                    | < - - - - -|childrenUpdates|
                                                    |- - - - - - - - - - - - - - - - - |                                 +- - - - - - - -+
                                                                                                            
</pre>
        Citirea se realizeaza in clasa InputLoader, din pachetul fileio. La finalul acesteia initializez baza de date cu
    informatiile necesare, precum se observa in diagrama de mai sus. Mai departe voi explica implementarea programului,
    precum si rolul fiecarei clase.

        In pachetul **entities** sunt puse clasele principale ale programului, anume: Child (ce reprezinta copii; aici am
    folosit un builder pattern), Present (ce reprezinta cadourile), Change (ce reprezinta o lista cu schimbarile ce
    vor avea loc in anul respectiv), AnnualChanges (contine o lista cu copii carora li s-au dat cadouri in toti anii),
    si PapaNoel (ce cuprinde o lista cu copii sub 19 ani din anul respectiv, adica lista mosului).
    
        In clasa main (din pachetul **main**) este apelata metoda action, ce realizeaza urmatorele lucruri:
    1. Citeste din fisierul dat ca input (filePath1) datele initiale si creeaza baza de date
    2. Initializeaza lista mosului prin metoda initSetup, din clasa FirstRound
    3. Itereaza prin fiecare an si apeleaza metoda playRound, din clasa NextRound, pentru anul respectiv
    4. Scrie, folosind un ObjectMapper, in fisierul de output(filePath2) rezultatul final
    5. Sterge baza de date pentru a putea fi repopulata la testul urmator (sunt nevoit sa fac asta din cauza
       implementarii alese, altfel imi va memora toti copii, de la toate testele)

        Clasa FirstRound reprezinta "runda 0", momentul cand determinam tipul initial al copilului (Baby, Kid,
    YoungAdult sau Teen), calculam bugetul alocat fiecarui copil si impartim cadourile pentru prima data.
    Dupa toate aceste calcule, populam lista mosului si lista primului an (voi explica in paragraful urmator)
    si o adaugam pe cea din urma la lista cu schimbarile anuale.
        Pentru a putea sa afisez sub formatul cerut, am creat o clasa auxiliara numita NewYear, aflata in pachetul
    **auxiliars**. Aceasta contine o lista cu toti copii ce au primit cadouri din anul respectiv. AnnualChanges
    contine o lista cu elemente de tipul NewYear, lista ce va fi scrisa in fisier la finalul testului.
        
        Clasa NextRound este resonsabila de a realiza schimbarile necesare din fiecare an, anume:
    1. Verifica daca exista copii ce au peste 18 ani, caz in care sunt exclusi din lista mosului
    2. Actualizeaza bugetul mosului
    3. Verifica daca exista schimbari in anul respectiv (daca exista copii noi, daca mosul are cadouri noi de dat, daca
       exista modificari la copii existenti)
    4. Recalculeaza bugetul fiecarui copil ramas
    5. Imparte cadourile
    6. Sorteaza lista copiilor in ordinea crescatoare a ID-urilor
    7. Reactualizeaza lista mosului
    8. Creeaza lista cu schimbarile din acel an (din clasa NewYear) si o adauga in lista de schimbari totale
       (AnnualChanges)
        Pentru a nu produce modificari bazei de date, imi creez o lista noua cu copii la inceputul metodei playRound
    (aceasta este un deep-copy a listei mosului; ma folosesc de builder-ul din Child)

        Alte mentionari ar fi: 
    1. Pachetul **utils**, ce contine clasa utils, cu metodele convertJSONArray (converteste un
       enum in string), getType (determina tipul copilului), calculateScore (calculeaza scorul din anul respectiv pentru
       fiecare copil si asigneaza bugetul) si givePresents (ce imparte cadourile fiecarui copil) 
    2. Clasa ChildrenUpdate, din pachetul **auxiliars**, ce reprezinta schimbarile din fiecare an pentru copii, daca
       au loc
    3. Clasa Test, din pachetul **main**, ce ma ajuta pentru a rula un singur test, dat ca parametru.

        Nu o sa intru in detalii legate de algoritmii de calculare a scorului, a bugetului asignat si a impartirii
    cadourilor deoarece am urmat pasii explicati in enuntul problemei, asa ca nu mai este nimic de zis legat de
    implementarea lor.
