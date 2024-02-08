public class Graph{
  
  private Knoten[] knotenListe;
  private Kante[] kantenliste;
  private int[][] adjazenzmatrix;
  //Anzahl Knoten
  private int anzahl = 0;
  //Anzahl Kanten
  private int anzahlKanten = 0;
  private int maxAnzahl = 0;
  
  public Graph(int groesse){
    maxAnzahl = groesse;
    anzahl = 0;
    knotenListe = new Knoten[groesse];
    adjazenzmatrix = new int[groesse][groesse];
    kantenliste = new Kante[(groesse*groesse)];
    for (int i = 0; i<groesse; i++) {
      for (int j = 0;j<groesse; j++) {
        adjazenzmatrix[i][j] =  0;
      } // end of for
    } // end of for
  }
  // get Adjazenz
  public int[][] getAdja(){
    return adjazenzmatrix;
  }
  //get Knoten nach index
  public Knoten getKnoten(int i){
    return knotenListe[i];
  }
  
  // Knoten getroffen?
  public Knoten findKnoten(int x, int y){
    Knoten gefunden = null;
    for(int i = 0; i < anzahl; i++){
      if (Math.sqrt(Math.pow((x-knotenListe[i].getX()),2)+Math.pow((y-knotenListe[i].getY()),2))<25) {
        gefunden = knotenListe[i];
      }
    }
    return gefunden;
  }

  // Kante getroffen?
  
  public Knoten[] getKnotenliste(){
    Knoten[] k = new Knoten[anzahl];
    for (int i = 0; i<k.length; i++) {
      k[i] = knotenListe[i];
    } // end of for
    return k;
  }
  
  public Kante[] getKantenliste(){
    Kante[] k = new Kante[anzahlKanten];
    for (int i = 0; i<k.length; i++) {
      k[i] = kantenliste[i];
    } // end of for
    return k;
  }
  
  
  
  public void kanteEinfuegen(Knoten von, Knoten bis, int gewicht){
    //In Adjazenzmatrix einfügen
    int v = knotenIndexSuchen(von);
    int b = knotenIndexSuchen(bis);
    if (v!= -1 && b!=-1) {
      adjazenzmatrix[b][v] = gewicht;
      adjazenzmatrix[v][b] = gewicht;
    } // end of if
    
    //Kantenobjekt erstellen
    kantenliste[anzahlKanten] = new Kante(von, bis, gewicht);
    anzahlKanten++;
  }
  
  //Index von Knoten suchen
  public int knotenIndexSuchen(Knoten k){
    int index = -1;
    int i = 0;
    while (index < 0 && i < knotenListe.length) { 
      if(knotenListe[i].equals(k)){
        index = i;
      }
      i++;
    } // end of while
    return index;
  }
  
  //Knoten einfügen
  public void knotenEinfuegen(Knoten k){
    if(anzahl < maxAnzahl){
      knotenListe[anzahl] = k;
      anzahl ++;
    }
    else {
      System.out.println("Maximale Anzahl an Knoten erreicht");
    } // end of if-else
  }
  
  public Knoten[] getNachbarn(Knoten k){
    Knoten[] hilf = new Knoten[0];
    int i = knotenIndexSuchen(k);
    for (int j = 0; j< maxAnzahl; j++) {
      if(adjazenzmatrix[i][j] != 0){
        Knoten[] temp = new Knoten[hilf.length + 1];
        for (int v = 0; v < hilf.length; v++) {
          temp[v] = hilf[v];
        } // end of for
        temp[hilf.length] = knotenListe[j];
        hilf = temp;
      }
    }
    return hilf;
  }
  
  //Einen Knoten nach Namen suchen
  public Knoten getKnoten(String n){
    for (int i = 0; i < anzahl; i++) {
      if (knotenListe[i].getName().equals(n)) {
        return knotenListe[i];
      } 
    } // end of for
    System.out.println("Ein Graph mit dem gegebenen Namen existiert nicht!");
    return null;
  }
  
  //liefert alle Knotennamen als String-Array
  public String[] getKnotenNamen(){
    String[] knotenNamen = new String[anzahl];
    for (int i = 0; i< anzahl ; i++) {
      knotenNamen[i] = knotenListe[i].getName();
    } // end of for
    return knotenNamen;
  }
  
  public void Tiefensuche(Knoten k){
    k.setBearbeitet(true);
    Knoten[] nachbarn = getNachbarn(k);
    for (int i = 0; i< nachbarn.length; i++) {
      if (nachbarn[i].getBearbeitet()==false) {
        Tiefensuche(nachbarn[i]);
      } // end of if
    } // end of for
    } // end of if
  
 public int getZusammenhang(){
  //Liefert die Anzahl der zusammenhängenden Knoten zurück
  Tiefensuche(knotenListe[0]);
  int n = 0;
  for(int i=0; i< anzahl; i++){
    if (knotenListe[i].getBearbeitet()) {
      n++;
    }
  }
  return n;
 }
  
}