/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author acer
 */
public class Projekt extends Application {
                
    private final int grid_x = 15; 
    private final int grid_y = 10;    
    private ImageView[][] grid;
    public char[][] mrizka;   
    public String vysledek="";
    private final Image zavrene = new Image(getClass().getResourceAsStream("ikony/prazdne.png"));
    private final Image bomba = new Image(getClass().getResourceAsStream("ikony/bomba.png"));
    private final Image prazdne = new Image(getClass().getResourceAsStream("ikony/prazdne1.png"));
    private final Image vlajka = new Image(getClass().getResourceAsStream("ikony/vlajka.png"));   
    private final Image jedna = new Image(getClass().getResourceAsStream("ikony/jedna.png"));
    private final Image dva = new Image(getClass().getResourceAsStream("ikony/dve.png"));
    private final Image tri = new Image(getClass().getResourceAsStream("ikony/tri.png"));
    private final Image ctyri = new Image(getClass().getResourceAsStream("ikony/ctyri.png"));
    private final Image pet = new Image(getClass().getResourceAsStream("ikony/pet.png"));
    private final Image sest = new Image(getClass().getResourceAsStream("ikony/sest.png"));
    private final Image sedm = new Image(getClass().getResourceAsStream("ikony/sedm.png"));
    private final Image osm = new Image(getClass().getResourceAsStream("ikony/osm.png"));
    private final Image vyhra = new Image(getClass().getResourceAsStream("ikony/vyhra.png"));
    private final Image prohra = new Image(getClass().getResourceAsStream("ikony/prohra.png"));
    private NumberBinding velObd;
    private NumberBinding velPole;
    private ImageView zobrVyhra = new ImageView();
    private ImageView zobrProhra = new ImageView();
    private Pane Panel = new Pane();
    private int miny=0;
    private int misto;
    private int oznaceno;
    private Menu zbyvaMin;

    public Projekt() 
    {   this.vysledek="Prohra";
        this.misto = 0;
        this.oznaceno = 0;        
        this.grid = new ImageView[grid_x][grid_y];
        this.mrizka = new char[grid_x][grid_y];        
        this.zobrProhra.visibleProperty().set(false);
        this.zobrVyhra.visibleProperty().set(false);
        this.zobrProhra.imageProperty().set(prohra);        
        this.zobrVyhra.imageProperty().set(vyhra);  
        this.zbyvaMin = new Menu("Zbyva min : " + Integer.toString(this.miny - oznaceno));
    }
    
    @Override
    public void start(Stage primaryStage) throws InterruptedException 
    {
        
        AnchorPane basePane = new AnchorPane();
        MenuBar nabidka = new MenuBar();
      
        nabidka.getMenus().addAll(zbyvaMin);          
        basePane.getChildren().add(nabidka);
        AnchorPane.setTopAnchor(nabidka, 1.0);
        AnchorPane.setLeftAnchor(nabidka, 1.0);
        AnchorPane.setRightAnchor(nabidka, 1.0);
        AnchorPane.setBottomAnchor(Panel, 1.0);
        basePane.getChildren().add(Panel);
        AnchorPane.setBottomAnchor(Panel, 1.0);
        AnchorPane.setLeftAnchor(Panel, 1.0);
        AnchorPane.setRightAnchor(Panel, 1.0);
        AnchorPane.setTopAnchor(Panel, 59.0);
        velObd = Bindings.min(Panel.heightProperty().divide(grid_y), Panel.widthProperty().divide(grid_x));
        velPole = Bindings.max(Panel.heightProperty().multiply(1080).divide(1920), Panel.widthProperty());
        zobrVyhra.fitHeightProperty().bind(velPole);
        zobrVyhra.fitWidthProperty().bind(velPole);
        zobrProhra.fitHeightProperty().bind(velPole);
        zobrProhra.fitWidthProperty().bind(velPole);
        Panel.getChildren().add(zobrVyhra);
        Panel.getChildren().add(zobrProhra);        
        this.miny = (grid_x*grid_y)/6;    
        Restart();            
        Scene scene = new Scene(basePane, grid_x*44, grid_y*54);
        Button btnStart = new Button();
        btnStart.setText("Restartovat hru");
        btnStart.setOnAction((ActionEvent event) -> 
        {           
            Restart();           
        });
        basePane.getChildren().add(btnStart);
        AnchorPane.setTopAnchor(btnStart, 30.0);
        AnchorPane.setLeftAnchor(btnStart, 1.0);
        AnchorPane.setRightAnchor(btnStart, 1.0);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void nezobrazovat()
    {
       zobrVyhra.visibleProperty().set(false);
       zobrProhra.visibleProperty().set(false);
    }
    
    private void vytvorBoard()
    {
        for(int x=0;x<grid_x;x++)
        {
            for (int y = 0; y <grid_y; y++)
            {
                mrizka[x][y]=' ';
            }
        }
    }
    
    private void generujMiny()
    {
        int random=0;
        int miny=this.miny;
                     
        while(miny!=0)
        { 
            for (int x=0;x<grid_x;x++)   
            {
                for (int y=0;y<grid_y;y++) 
                {
                    if(mrizka[x][y]!='X')
                    {
                        random=(int)(Math.random()*90);
                                if(random==1 && miny!=0)
                                {
                                    mrizka[x][y]='X';
                                    miny--;
                                }
                    }
                }
            }
        }
    }
    
    private void nastavMiny()
    {
        int pocet;
             
        for (int x=0;x<grid_x;x++) 
        {
            for (int y=0;y<grid_y;y++) 
            {
                pocet=0;
                
                ImageView hrpole = new ImageView();
                hrpole.xProperty().bind(velObd.multiply(x));
                hrpole.yProperty().bind(velObd.multiply(y));
                hrpole.fitHeightProperty().bind(velObd);
                hrpole.fitWidthProperty().bind(velObd);
                hrpole.setOnMouseEntered((MouseEvent e) -> 
                {
                    ImageView view = (ImageView) (e.getSource());
                    if (view.getImage() == bomba)
                    {
                        prohraPodm();
                    } 
                    
                    else 
                    {
                        vyhraPodm();
                    }
                    
                    if(view.getImage()==prazdne)
                    {
                        otevritVsechny();
                    }
                });
                
                if(mrizka[x][y]!='X')
                {
                    if(x!=0 && y!=0 && mrizka[x-1][y-1]=='X') pocet++;
                    if(x!=0 && mrizka[x-1][y]=='X') pocet++;
                    if(x!=0 && y!=(grid_y-1) && mrizka[x-1][y+1]=='X') pocet++;
                    if(y!=0 && mrizka[x][y-1]=='X') pocet++;
                    if(y!=(grid_y-1) && mrizka[x][y+1]=='X') pocet++;
                    if(x!=(grid_x-1) && y!=0 && mrizka[x+1][y-1]=='X') pocet++;
                    if(x!=(grid_x-1) && mrizka[x+1][y]=='X') pocet++;
                    if(x!=(grid_x-1) && y!=(grid_y-1) && mrizka[x+1][y+1]=='X') pocet++;
                    mrizka[x][y] = Integer.toString(pocet).charAt(0);
                }
       
                if(pocet==0) hrpole.imageProperty().set(prazdne);
                if(pocet==1) hrpole.imageProperty().set(jedna);
                if(pocet==2) hrpole.imageProperty().set(dva);
                if(pocet==3) hrpole.imageProperty().set(tri);
                if(pocet==4) hrpole.imageProperty().set(ctyri);
                if(pocet==5) hrpole.imageProperty().set(pet);
                if(pocet==6) hrpole.imageProperty().set(sest);
                if(pocet==7) hrpole.imageProperty().set(sedm);
                if(pocet==8) hrpole.imageProperty().set(osm);                
                if(mrizka[x][y]=='X') hrpole.imageProperty().set(bomba);
                Panel.getChildren().add(hrpole);               
            }
        }
        
        for (int x=0;x<grid_x;x++) 
        {
            for (int y=0;y< grid_y;y++) 
            {
                ImageView hrpole2 = new ImageView();
                hrpole2.imageProperty().set(zavrene);
                hrpole2.xProperty().bind(velObd.multiply(x));
                hrpole2.yProperty().bind(velObd.multiply(y));
                hrpole2.fitHeightProperty().bind(velObd);
                hrpole2.fitWidthProperty().bind(velObd);
                
                hrpole2.setOnMouseClicked((MouseEvent e) -> 
                {
                    ImageView view = (ImageView) (e.getSource());
                    if (view.getImage()==zavrene) 
                    {
                        if(e.getButton()==MouseButton.PRIMARY)
                        {
                            view.visibleProperty().setValue(Boolean.FALSE);
                            misto++;
                        }
                            
                        if(e.getButton()==MouseButton.SECONDARY)
                        {
                            view.setImage(vlajka);
                            misto++;
                            oznaceno++;
                            pocitadloMin();
                        }
                    }
                    
                    else
                    {
                        if (view.getImage()==vlajka) 
                        {
                            if(e.getButton()==MouseButton.SECONDARY)
                            {
                                view.setImage(zavrene);
                                misto--;
                                oznaceno--;
                                pocitadloMin();
                            }
                        }
                    }
                });
                grid[x][y]=hrpole2;
                Panel.getChildren().add(grid[x][y]);          
            }
        }
       
    }
            
    private void otevritVsechny()
    {
        for (int i=0;i<(grid_x*2);i++)  
        {
            for (int x=0;x<grid_x;x++) 
            {
                for (int y=0;y<grid_y;y++)
                {
                    otevriSpojence(x,y);
                }
            }
        }
 
            for (int x=0;x<grid_x;x++) 
            {
                for (int y=0;y<grid_y;y++)
                {
                   otevriCisla(x,y);
                }
            }
    }
    
    private void otevriSpojence(int x, int y)
    {
        if((mrizka[x][y]=='0'))
        {
            if(!grid[x][y].visibleProperty().get())
            {
                if(x!=0 && mrizka[x-1][y]=='0' && grid[x-1][y].visibleProperty().get()==true)
                {
                    grid[x-1][y].setVisible(false);
                    misto++;
                }
                
                if(y!=0 && mrizka[x][y-1]=='0' && grid[x][y-1].visibleProperty().get()==true)
                {
                    grid[x][y-1].setVisible(false);
                    misto++;
                }
                
                if(y!=grid_y-1 && mrizka[x][y+1]=='0' && grid[x][y+1].visibleProperty().get()==true)
                {
                    grid[x][y+1].setVisible(false);
                    misto++;
                }
               
                if(x!=grid_x-1 && mrizka[x+1][y]=='0' && grid[x+1][y].visibleProperty().get()==true)
                {
                    grid[x+1][y].setVisible(false);
                    misto++;
                }
            }
        }
    }
    
  private void otevriCisla(int x, int y)
    {  
      
            if(grid[x][y].visibleProperty().get()==true)
            {           
                if(x!=0 && mrizka[x-1][y]=='0' && grid[x-1][y].visibleProperty().get()==false)
                {
                    grid[x][y].setVisible(false);
                    misto++;
                    return;
                }
                
                if(y!=0 && mrizka[x][y-1]=='0' && grid[x][y-1].visibleProperty().get()==false)
                {
                    grid[x][y].setVisible(false);
                    misto++;
                    return;
                }
                
                if(y!=grid_y-1 && mrizka[x][y+1]=='0' && grid[x][y+1].visibleProperty().get()==false)
                {
                    grid[x][y].setVisible(false);
                    misto++;
                    return;
                }
                
                if(x!=grid_x-1 && mrizka[x+1][y]=='0' && grid[x+1][y].visibleProperty().get()==false)
                {
                    grid[x][y].setVisible(false);
                    misto++;
                    return;
                }                       
            }  
    }
    
    private void pocitadloMin()
    {
        this.zbyvaMin.setText("Zbyva min: " + Integer.toString(this.miny-oznaceno));
    }
    
    private void vyhraPodm()
    {   
        if((misto == grid_x*grid_y)&&(oznaceno==this.miny))
        {
            vysledek="Vyhra";
            zobrVyhra.toFront();
            zobrVyhra.visibleProperty().set(true);
            
            databaze pripoj=new databaze();
            pripoj.vlozitVysledek(vysledek);   
        }
    }
    
    private void prohraPodm()
    {       vysledek="Prohra"; 
            zobrProhra.toFront();
            zobrProhra.visibleProperty().set(true);
             
            databaze pripoj=new databaze();
            pripoj.vlozitVysledek(vysledek);
    }

    private void Restart() 
    {
        misto = 0;
        oznaceno=0;
        nezobrazovat();
        vytvorBoard();
        pocitadloMin();
        generujMiny();
        nastavMiny();
    }

    public static void main(String[] args) {       
        launch(args);
    }
}