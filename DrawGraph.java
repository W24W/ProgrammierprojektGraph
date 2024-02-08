import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 14.01.2024
 * @author 
 */

public class DrawGraph extends JFrame {
  // Anfang Attribute
  private Graphics g;
  private Graph graph;
  private int KnotenSize = 50; //Graphische Größe Knoten
  private int letter = 'A'; // Namen der Knoten
  private Knoten verschieben = null;
  JFrame f;  // Für JOptionPane
  
  
  // Attribute Kante zeichnen
  private Point startPoint;
  private Point startPointMaus;
  private Point endPoint;
  
  // Attribute MenuBar
  private JMenuBar jMenuBar1 = new JMenuBar();
    private JMenu jMenuBar1_File = new JMenu("File");
      private JMenuItem jMenuBar1_File_New = new JMenuItem("New Graph");
      private JMenuItem jMenuBar1_File_Load = new JMenuItem("Load");
      private JMenuItem jMenuBar1_File_Save = new JMenuItem("Save");
    private JMenu jMenuBar1_Edit = new JMenu("Edit");
      private JMenuItem jMenuBar1_Edit_Delete = new JMenuItem("Delete");
      private JMenuItem jMenuBar1_Edit_Tiefensuche = new JMenuItem("Tiefensuche");
  // Ende Attribute
  
  public DrawGraph() { 
    // Frame-Initialisierung
    super();
    f = new JFrame();
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = 477; 
    int frameHeight = 469;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setTitle("DrawGraph");
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
    // Anfang Komponenten
    g = getGraphics();
    graph = new Graph(10);
    
    
    
    setJMenuBar(jMenuBar1);
    jMenuBar1.add(jMenuBar1_File);
    jMenuBar1_File.add(jMenuBar1_File_New);
    jMenuBar1_File.add(jMenuBar1_File_Load);
    jMenuBar1_File.add(jMenuBar1_File_Save);
    jMenuBar1.add(jMenuBar1_Edit);
    jMenuBar1_Edit.add(jMenuBar1_Edit_Delete);
    jMenuBar1_Edit.add(jMenuBar1_Edit_Tiefensuche);
    jMenuBar1_Edit.setVisible(true);
    jMenuBar1.setVisible(true);
    setVisible(true);
    
    jMenuBar1_File_New.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        jMenuBar1_File_New_Java_ActionPerformed(evt);
      }
    });
    
    jMenuBar1_File_Load.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        jMenuBar1_File_Load_ActionPerformed(evt);
      }
    });
    jMenuBar1_File_Save.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        jMenuBar1_File_Save_ActionPerformed(evt);
      }
    });
    
    jMenuBar1_Edit_Delete.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        jMenuBar1_Edit_Delete_ActionPerformed(evt);
      }
    });
    
    jMenuBar1_Edit_Tiefensuche.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        jMenuBar1_Edit_Tiefensuche_ActionPerformed(evt);
      }
    });
    // Ende Komponenten
  } // end of public DrawGraph
  
  
  public static void main(String[] args) {
    new DrawGraph(); 
  } // end of main
  // Anfang Methoden
  
 
  
  public void draw(){
    // Kanten zeichnen
    g = getGraphics();
    
    g.clearRect(0, 0, getWidth(), getHeight());
    setJMenuBar(jMenuBar1);
    jMenuBar1.add(jMenuBar1_File);
    jMenuBar1_File.add(jMenuBar1_File_New);
    jMenuBar1_File.add(jMenuBar1_File_Load);
    jMenuBar1_File.add(jMenuBar1_File_Save);
    jMenuBar1.add(jMenuBar1_Edit);
    jMenuBar1.setVisible(true);
    
    
    Graphics2D g2 = (Graphics2D) g;
    g2.setStroke(new BasicStroke(5));
    Kante[] kantenliste = graph.getKantenliste();
    for (int i=0; i < kantenliste.length; i++) {
      if (kantenliste[i].getbearbeitet()) {
        g.setColor(Color.RED);
      } else {
        g.setColor(Color.BLACK);
      } // end of if-else
      g.drawLine(kantenliste[i].getk1().getX(), kantenliste[i].getk1().getY(), kantenliste[i].getk2().getX(), kantenliste[i].getk2().getY());
    } // end of for
    
    // Knoten zeichnen
    Knoten[] knotenliste = graph.getKnotenliste();
    for (int i = 0; i< knotenliste.length; i++) {
      if (knotenliste[i].getBearbeitet()) {
        g.setColor(Color.RED);
      } // end of if
      else {
        g.setColor(Color.BLUE);
      } // end of if-else
      g.fillOval(knotenliste[i].getX()- KnotenSize/2, knotenliste[i].getY()- KnotenSize/2, KnotenSize, KnotenSize);
      g.setColor(Color.BLACK);
      g.setFont(new Font("Arial", Font.BOLD, 20));
      g.drawString(knotenliste[i].getName(), knotenliste[i].getX()-8, knotenliste[i].getY()+5);
    } // end of for     
  }
  
    

  public void jMenuBar1_File_New_Java_ActionPerformed(ActionEvent evt) {
    
    g = getGraphics();
    
    // MouseListener zum Zeichnen von Knoten
    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        if(e.getButton()==MouseEvent.BUTTON1){
          //Stelle sicher, dass Knoten nur gezeichnet wird, wenn ein Abstand zu bestehendem Knoten eingehalten wird!
          boolean abstand = true;
          Knoten[] knotenliste = graph.getKnotenliste();
          for (int i = 0; i < knotenliste.length ; i++) {
            int x = knotenliste[i].getX();
            int y = knotenliste[i].getY();
            if (Math.sqrt(Math.pow((x-e.getX()),2)+Math.pow((y-e.getY()),2))<KnotenSize*1.5) {
              System.out.println("Zu nah an bestehendem Knoten");
              abstand = false;
            } // end of if
          }
          if (abstand == true) {
            graph.knotenEinfuegen(new Knoten(Character.toString(letter), e.getX(), e.getY()));
            letter++;
            draw();
          } // end of if
        }  
      }
    });
    
    // MouseListener zum Verschieben von Knoten mit linker Maustaste und Strg
    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && (e.getModifiers() & InputEvent.CTRL_MASK) != 0) {
          verschieben = graph.findKnoten(e.getX(), e.getY());
        }
      }
    });
    
    addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseDragged(MouseEvent e) {
        if (verschieben != null) {
          verschieben.setX(e.getX());
          verschieben.setY(e.getY());
          draw();
        } // end of if
      }
    });
    
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        if(e.getButton()==MouseEvent.BUTTON1){
          verschieben = null;
        } 
      }
    });
    
    // MouseListener zum Zeichnen von Kanten
    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        if(e.getButton()==MouseEvent.BUTTON1){
          startPoint = e.getPoint();
          startPointMaus = e.getPoint();
          g = getGraphics();
          Graphics2D g2 = (Graphics2D) g;
          g2.setStroke(new BasicStroke(5));
        }
      }
    });
    
    addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseDragged(MouseEvent e) {
        endPoint = e.getPoint();
        g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
        startPoint = endPoint;
      }
    });
    
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        if(e.getButton()==MouseEvent.BUTTON1){
          endPoint = e.getPoint();
          
          // Überprüfe, ob neue Kante von bekannten Knoten ausgeht
          Knoten treffer1 = null;
          Knoten treffer2 = null;
          Knoten[] knotenliste = graph.getKnotenliste();
          for (int i = 0; i < knotenliste.length ; i++) {
            int x = knotenliste[i].getX();
            int y = knotenliste[i].getY();
            if (Math.sqrt(Math.pow((x-endPoint.x),2)+Math.pow((y-endPoint.y),2))<KnotenSize/2) {
              treffer2 = knotenliste[i];
            } // end of if
            if (Math.sqrt(Math.pow((x-startPointMaus.x),2)+Math.pow((y-startPointMaus.y),2))<KnotenSize/2) {
              treffer1 = knotenliste[i];
            } // end of if
          } // end of for
          
          
          if(treffer1 != null && treffer2 != null){
            System.out.println("Treffer");
            graph.kanteEinfuegen(treffer1, treffer2, 3);
            
          }
          // Clear all previously drawn lines
          g.clearRect(0, 0, getWidth(), getHeight()-100);
          
          draw();
          
          // Draw the new line
        } 
      }
    });
    
  } // end of jMenuBar1_File_New_Java

  public void jMenuBar1_File_Load_ActionPerformed(ActionEvent evt) {
    // TODO hier Quelltext einfügen

  } // end of jMenuBar1_File_Load

  public void jMenuBar1_File_Save_ActionPerformed(ActionEvent evt) {
    // TODO hier Quelltext einfügen

  } // end of jMenuBar1_File_Save


  public void jMenuBar1_Edit_Delete_ActionPerformed(ActionEvent evt) {
    // TODO hier Quelltext einfügen
    graph = new Graph(10);
    letter = 'A';
    draw();
  } // end of jMenuBar1_Edit_Delete
  
  public void jMenuBar1_Edit_Tiefensuche_ActionPerformed(ActionEvent evt) {
    // TODO hier Quelltext einfügen
    if (graph.getKnotenliste().length > 0) {
      graph.Tiefensuche(graph.getKnotenliste()[0]);
      draw();
      JOptionPane.showMessageDialog(f,"Anzahl zusammenhängender Knoten: " + graph.getZusammenhang());  
    } // end of if
  } // end of jMenuBar1_Edit_Tiefensuche
  
  
    // Ende Methoden
} // end of class DrawGraph