public class Kante{
  private Knoten k1;
  private Knoten k2;
  private int gewicht;
  private boolean bearbeitet;
  
  public Kante(Knoten k1, Knoten k2, int gewicht){
    this.k1 = k1;
    this.k2 = k2;
    this.gewicht = gewicht;
    bearbeitet = false;
    }
  
  public Knoten getk1(){
    return  k1;
    }
  
  public Knoten getk2(){
    return k2;
    }
  
  public int getgewicht(){
    return gewicht;
    }

  public void setgewicht(int g){
    gewicht = g;
  }
  
  public boolean getbearbeitet(){
    return bearbeitet;
    }
  
  public void setbearbeitet(boolean b){
    bearbeitet = b;
    }

}