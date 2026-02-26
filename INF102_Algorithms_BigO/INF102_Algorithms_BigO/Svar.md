# Answer File - Semester 2
# Description of each Implementation
Briefly describe your implementation of the different methods. What was your idea and how did you execute it? If there were any problems and/or failed implementations please add a description.

## Task 1 - mst
*Jeg vil implementere Prims algoritme. Dette gjør jeg ved å sortere alle kantene etter vekt, og plassere dem i en queue. 
For hver node i grafen, vil jeg oppdatere queuen, ved å både fjerne minste kant og legge til alle nodens kanter. 
Hvis kantene jeg fjerner fra queuen ikke lager en sykel, legger jeg dem til i en liste som jeg til slutt returnerer.
Mer detaljert, gjør jeg:
Finner mst ved å lage en priorityqueue som sorterer kantene etter vekt. 
Jeg putter alle kantene til den første noden i priorityqueuen. 
Jeg lager et set, found, som inneholder alle nodene jeg har vært innom, og som jeg oppdaterer etterhvert som jeg går gjennom grafen. 
Deretter fjerner jeg den minste kanten fra priorityqueuen, og lagrer den i en variabel, smallest. 
Hvis den ikke lager en sykel, legger jeg smallest til i mst-et mitt, og smallest.b i found. 
Så går jeg gjennom alle kantene til smallest.b som ikke er i found, og legger dem til i priorityqueuen. 
Jeg fortsetter å gå gjennom priorityqueuen helt til jeg har gått gjennom alle nodene i grafen, og de ligger i found. Returnerer mst-et.
Velger at found er et set og ikke en liste, siden jeg kun bruker found.contains, og det har lavere O kjøretid for set enn for lister.* 

## Task 2 - lca
*Min strategi var å gå gjennom alle nodene i treet ved et dybde først søk. 
Sånn kan jeg søke gjennom alle nodene i grafen, og samtidig holde styr på hvilken vei jeg har gått.
Derfor lager jeg en liste, path, som tar vare på alle foreldrene til noden jeg for øyeblikket er ved. 
Jeg vil også lage en liste toSearch, og et Set found, der jeg legger alle nodene jeg vil besøke, og alle nodene jeg har besøkt. 
found er et HashSet fordi jeg kun bruker det til å sjekke om en node allerede har blitt funnet, ved å ta found.contains(node). Dette tar O(1) tid for et HashSet, og O(n) tid for en liste.
Deretter går jeg gjennom grafen helt til jeg finner u eller v. Hvis jeg har funnet u eller v, lagrer jeg path i en ny liste pathU eller pathV.
Sånn holder jeg styr på alle forfedrene til u og v. 
Når jeg har funnet begge nodene, prøver jeg å sjekke om de har en felles forelder i sin path. 
Jeg sjekker bakfra, sånn at jeg finner nederste felles forfeder, og ikke en øvre felles forfeder. Når jeg har funnet den, returnerer jeg den.
Jeg antar at både u og v er i grafen, og at jeg dermed vil finne dem. 
Hvis man prøver å kjøre lca der u og/eller v ikke finnes i g, vil lca kjøre uendelig gjennom en while-løkke. Jeg forventer da litt sunn fornuft av de som potensielt skal bruke lca.*

## Task 3 - addRedundant
*Min strategi var å telle antall etterkommere hver node i grafen har, og deretter følge grafen til ytternoden ved å alltid velge barnet med flest etterkommere.
Dette gjør jeg to ganger, og kanten jeg vil returnere skal ligge mellom disse to ytternodene. 
Slik kan jeg lage en størst mulig sykel i grafen, hvor flest mulig noder er direkte eller indirekte koblet til sykelen. 
Det vil si at hvis en kant i sykelen ryker, vil fortsatt alle nodene i grafen være koblet til roten via andre noder. 
Sånn vil strømutbruddet bli minimert hvis en kant skulle ryke. Dette gjør jeg ved å lage to hjelpefunksjoner, mostChildren og correctNode. 
I addRedundant, lager jeg et HashMap, found, som jeg sender gjennom mostChildren. 
mostChildren går gjennom found, og legger til alle nodene i grafen som keys, og antall etterkommere denne noden har som values.
I addRedundant bruker jeg deretter found til å finne de to nabonodene til root med høyest value. 
Disse nodene sender jeg gjennom correctNode, som rekursivt går nedover grafen, ved å følge det barnet med høyest value.
correctNode returnerer til slutt den ytterste noden.
I addRedundant vil jeg til slutt returnerer en kant som er mellom de to nodene jeg fikk fra correctnode.*


# Runtime Analysis
For each method of the different strategies give a runtime analysis in Big-O notation and a description of why it has this runtime.

**If you have implemented any helper methods you must add these as well.**

* ``mst(WeightedGraph<T, E> g)``: O(mlog(n))
    *Jeg har et HashSet, found, som jeg bruker til å ta vare på alle nodene jeg finner i grafen. 
    Jeg valgte å la found være et set og ikke en liste fordi found.contains() har kjøretid O(1) når found er et set, men kjøretid O(n) når found er en liste.
    Jeg har også laget en priorityqueue, priorityWeight, som tar vare på alle kanter jeg vil gå gjennom senere. Den sorterer kanter basert på størrelsen på weight.
    Legger til alle nabokantene til root i priorityWeight, som tar O(degree) tid. (der degree er antall kanter).
    Videre har jeg en while-løkke som går gjennom alle nodene i grafen. Da vil alt inni while løkken kjøre n ganger. 
    I while-løkken fjerner jeg første element fra priorityWeight, og lagrer det i en variabel, smallest. Dette tar O(1) tid. 
    Hvis found inneholder begge nodene fra første kant, altså at denne kanten vil lage en sykel, fortsetter jeg til neste kant i priorityweight. I så tilfelle bruker da dette steget kun O(1).
    I det tilfellet at kanten ikke vil lage en sykel, legger jeg til kanten i mst, og noden som er neste kant i found (altså smallest.b).  
    Deretter går jeg gjennom alle kantene til smallest.b, og legger til de som ikke har begge nodene i found i priorityWeight. Å legge til noe i en priorityqueue tar O(log()) tid. 
    Jeg sjekker om en node finnes i found før jeg legger til en kant med den noden i priorityWeight. 
    Det vil si at priorityWeight maksimalt kan inneholde m/2 kanter, siden den bare inneholder kantene én vei. Forkorter dette til n. 
    worst-case er da at det tar O(log(n)) tid.
    Dette er siden priorityWeight må plassere input riktig i queuen for at innholdet fortsatt skal være sortert. 
    Derfor vil kjøretiden til å legge til smallest.b sine kanter i priorityWeight bli O(degree)*O(log(n)). (for-løkken kjører degree ganger). 
    Kjøretiden til hele while-løkken vil da bli O(n)*O(degree)*O(log(n)). 
    Siden degree er antall noder en kant har, og n er antall noder i grafen, vil n*degree være antall kanter i grafen. 
    Altså blir kjøretiden til for-løkken, og da hele mst, O(mlog(n)).
    Dette blir da kjøretiden til hele mst.** 
* ``lca(Graph<T> g, T root, T u, T v)``: O(n*m)
    *Siden input er et tre, vet vi at antall kanter, m, er det samme som antall noder-1, eller n-1. 
    I lca har jeg laget to set, found og pathV, og tre linkedlister, toSearch, path og pathU. Alle nodene jeg besøker legger jeg til i found, og toSearch er alle nodene jeg må søke gjennom.
    Jeg valgte å la found være et HashSet, siden contains har kjøretid O(1) for has HashSet, og ikke for lister.
    Jeg legger alle nabonoden til root i toSearch. Siden toSearch er en linkedlist, tar det O(1) tid å legge noe til i den. 
    Jeg gjør det degree ganger, altså tar det O(degree) tid å legge til naboene til root i toSearch.
    Deretter kjører jeg en while-løkke. Jeg antar at både u og v er noder i grafen g, og at jeg dermed kommer til å finne dem. 
    Det er derfor ikke et problem at jeg har while(true), for loopen brytes når man har funnet både u og v. 
    Siden det er mulig at man må kjøre gjennom alle nodene i hele grafen før man har funnet både u og v, kjører while-løkken potensielt n ganger.
    Inni while-løkken er det tre if-statements. 
    Der sjekker jeg om henholdsvis u, v og både u og v er i found. contains av et hashSet tar, som sagt, O(1) tid (når man bruker javas innebygde hashSet). 
    Hvis found inneholder u, og pathU er tom, legger jeg alle elementene til path i pathU. 
    I absolutt verste tilfellet består path av alle nodene i grafen, og da vil addAll ha kjøretid O(n).
    Det samme gjelder for andre if-statement, hvor jeg sjekker om v finnes i found. Forventet kjøretid for hashSet.add er O(1), men worst case er O(n). Vi forholder oss til O(1), siden jeg bruker javas innebygde hashset med bra hashfunksjon.
    Den siste if-statementen sjekker om både u og v er i found, og bryter deretter ut av while-løkken. Den har altså O(1) tid.
    Videre, hvis ikke både u og v er i found, fjerner jeg siste element fra toSearch, og lagrer det i en variabel kalt node. Å fjerne siste element fra en linkedlist tar O(1) tid.
    Deretter har jeg en ny while-løkke, som går gjennom path, og fjerner alle de bakerste elementene som ikke er adjacent med node. 
    path kan potensielt inneholde nesten alle nodene i grafen, men ikke root. Kjøretiden blir da O(n-1), eller O(m).
    Hvis node ikke er i found fra før av, legger jeg den til i found, og i path. Dette tar O(1) tid.
    Videre går jeg gjennom alle naboene til node i en for-løkke, altså kjører for-løkken degree ganger.
    Der sjekker jeg om found inneholder naboen, og hvis ikke, legger den til i found, med O(1). for-løkken har da kjøretid O(degree).
    Alt inni while-løkken vil da ha en kjøretid på O(m+degree)
    while-løkken har da en kjøretid på O(n*(m+degree))=O(m*n).
    Etter while-løkken reverserer jeg pathU, siden den før øyeblikket er i rekkefølgen fra root til u, og jeg vil ha den andre vei. 
    worst case for å reversere pathU er O(n). 
    Går deretter gjennom alle elementene i pathU og sjekker om de er i pathV. Det var derfor jeg lot pathV være et hashSet og ikke en liste. 
    Siden contains har O(1) for hashSet, men O(n) for liste (Når du bruker en bra hashfunksjon, som java sin innebygde).
    Dette gjør da at hele lca har en kjøretid på O(degree+m*n+n) = O(m*n).*
  
* ``addRedundant(Graph<T> g, T root)``: O(n^2)
    * *Lager et HashMap, found, som jeg bruker mostChildren på. Det tar O(n) tid.
      Går deretter gjennom alle naboene til root, noe som tar O(degree) tid. 
      Videre sjekker jeg om roten bare har én nabo. I såfall lager jeg en node r =correctNode(g, root, root, found), og returnerer en kant mellom r og rot.
      Siden correctNode har en kjøretid på O(n^2) (ved worst case) tar det O(n^2) tid.
      Videre gjennomfører jeg flere operasjoner som har O(n) tid, som collections.max og indexof. Der finner jeg nodene med høyest, og nest høyest value i found. 
      Til slutt tar jeg correctNode av de to nodene jeg fant, noe som tar O(n^2) tid hver. Hele addRedundant tar da O(n^2) tid ved worst case.**
  
* ``mostChildren(Graph<V> g, V root, HashMap<V, Integer> found, HashSet<V> visited``: O(n) 
    * *En av parameterne til mostChildren er visited. Det er et set der jeg legger til noder. 
      For hver gang jeg kjører mostChildren rekursivt, legger jeg til root-parameteret i visited. 
      Videre har jeg en for-løkke som går gjennom alle naboene til roten. Den vil da kjøre degree ganger. 
      Men, jeg kjører mostChildren rekursivt i for-løkken. Før jeg går gjennom mostChildren en gang til, sjekker jeg om naboen er i visited allerede. 
      Hvis den er det, går jeg videre til neste nabo. Slik vil for-løkken gå gjennom alle nodene i grafen, og kjøretiden til for-løkken blir O(n).
      Det er også dette som blir kjøretiden til mostChildren* * 
      
* ``correctNode(Graph<V> g, V var,V root, HashMap<V, Integer> found)``: O(n^2)
    * *correctNode er en rekursiv funksjon. Den finner den nabonoden til inputnoden, kalt var, som har flest etterkommere.
      Til slutt returnerer correctNode den ytterste noden. correctNode kjører h ganger, der h er høyden til treet. 
      h kan maks være n, så vi sier at correctNode kjører n ganger.
      I correctNode er det en for-løkke som går gjennom alle naboene til var-inputet, og dersom nabo ikke er det samme som rot (var fra den forrige repetisjonen) legges den til i en liste, toSearch.
      Samtidig som jeg legger noder til i toSearch, legger jeg til nodens value i en annen liste som heter toSearchScore.
      Nodens score får jeg fra found, hashMapet jeg fylte inn i mostChildren. 
      Deretter finner jeg maks value av toSearchScore, og bruker det til å finne indeksen til noden i toSearch.
      Collections.max tar O(n) tid, det samme gjør indexOf. Hele denne operasjonen vil da ta O(n) tid.
      Det vil si at correctNode kjøres n ganger (ved worst case), og at den for hver gang bruker O(n) tid. 
      Til sammen bruker da correctNode O(n^2) tid (ved worst case).* * 

