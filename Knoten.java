public class Knoten{
  private String name = "";
  private boolean bearbeitet = false;
  private int x;
  private int y;
  
  public Knoten(String name, int x, int y){
    this.name = name;
    this.x = x;
    this.y = y;
  }
  
  public int getX(){
    return x;
    }
  
  public int getY(){
    return y;
    }
  
  public void setX(int x){
    this.x = x;
    }
  
  public void setY(int y){
    this.y = y;
    }
  
  
  public void setBearbeitet(boolean b){
    bearbeitet = b;
  }
  
  public boolean getBearbeitet(){
    return bearbeitet;
  }
  
  public void setName(String n){
    name = n;
  }
  
  public String getName(){
    return name;
  }
  



}