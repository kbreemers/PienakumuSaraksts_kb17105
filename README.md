# PienakumuSaraksts_kb17105

2. uzdevuma formulējums: 

"Pienākumu sarakts"
Paredzēts pienākumu plānošanai un labākai pārredzamībai. 
Lietotājam ir iespēja izveidot pienākumu, pievienojot tā nosaukumu un aprakstu. 
Lietotājs var dzēst pienākumu, kā arī atzīmēt to kā paveiktu.
Kā datu bāzi es izmantotu SQLite, kurā saglabātu un uzturētu lietotāja informāciju par pienākumiem.

Lietotne izstrādāta valodā Kotlin

Īsumā par katru izveidoto failu šajā projektā:

  'Const.kt' - šajā failā ir izveidotas konstantes, lai katru reizi piekļūstot pie noteiktiem laukiem datubāzē nevajadzētu manuāli rakstīt tabulas lauka nosaukumu
  'PienakumuSaraksts.kt' - šajā failā ir izveidota klasē, kurā mainīgajiem no datubāzes tiek iestatītas sākuma vērtības
  'DatabaseHandler.kt' - šajā failā projektam ir pievienota SQLite datubāze, kurā ir izveidotas ar datubāzi saistītas funkcijas,
      piemēram, funckija 'addResponsibility' ļaus lietotājam izveidot jaunu ierakstu datubāzē jeb pievienot jaunu pienākumu savā sarakstā, 
      taču funkcija 'getResponsibility' ļaus iegūt datubāzē esošos pienākumus. Šajā failā ir arī citas funkcijas, bet to nosaukumi,
      manuprāt, paskaidros paši sevi (deleteResponsibilty u.c.)
  'MainActivity.kt' - galvenais fails, kurā notiek sazināšanās ar lietotāju saskarni un attiecīgajā pogām izvēlnē tiek piesaistīti
      attiecīgās funkcijas no datubāzes. Piemēram, funkcija 'updateList' parūpēsies par to, lai saraksts tiktu atjaunots pēc jauna
      ieraksta pievienošanas vai dzēšanas Kā arī šeit ir realizēti arī dialogi (dialog) priekš jauna pienākumu izveidošanas.

Šī projekta datubāzē eksistē viena tabula, kura sevī ietver: pienākuma ID (kā integer), tā nosaukumu (kā varchar),
izveidošanas laiku (kā datetime) un vai tas attiecīgais pienākums ir izdarīts (kā integer - "0" ir false un "1" ir true).

Darbības paskaidrojums pēc pievienotā video:
Lietotājs var pievienot pienākumus, tos labot, dzēst, atzīmēt kā izdarītus un atzīmēt atpakaļ kā neizdarītus. 
Kad pienākums ir atzīmēts kā izdarīts, tam būtu jāparāda ķeksīti blakus 'trīs punktu' jeb '...' izvēlnei. 
Kad lietotājs vēlas tomēr pasākumu atzīmēt kā neizdarītu, tam ir jāspiež 'Reset' un attiecīgajam pienākuma ķeksītim būtu jāpazūd

