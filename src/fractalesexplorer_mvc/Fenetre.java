package fractalesexplorer_mvc;

/*
    Copyright (c) 2017 BETTON Maxime & TRIJEAN Julien
    All rights reserved.

    Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in 
        the documentation and/or other materials provided with the distribution.
    * Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote 
        products derived from this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
    THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS 
    BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
    GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, 
    STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF 
    SUCH DAMAGE.
*/

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Fenetre extends FractaleVue {

    static Dessin jp;

    private FractaleControlleur controlleur;
    private FractaleModele modele;

    private JButton zoomPlus = new JButton("+");            // bouton graphique de zoom 
    private JButton zoomMoins = new JButton("-");           // et dezoom
    
    private JButton seqeuent = new JButton("sequentielle"); // bouton graphique de choix
    private JButton thread = new JButton("Thread");         // de calcule
    
    private JButton mandel = new JButton("Mandelbrot");     // bouton de choix du fractale
    private JButton triangle = new JButton("Sierpinski");   //
    
    private JTextField iteration = new JTextField("    ");  // retourne l'itération de mandelbrot
    private JLabel infoIteration= new JLabel("   Iteration: ");
    
    private GridLayout grille =  new GridLayout(10,4);      // déclaration d'une grille et d'un panel 
    private JPanel controlle = new JPanel();                // pour gérer la disposition graphique des composants
    

    static JFrame fenetre = new JFrame("FractaleExplorer -TRIJEAN-BETTON-");

    public Fenetre(FractaleControlleur controlleur, FractaleModele modele) {

        super(controlleur, modele);
        this.modele = modele;
        this.controlleur = controlleur;
        
        iteration.setText(Integer.toString((int) modele.getIteration()));   // récupère le nombre d'itération dans mandelbrot
        iteration.setHorizontalAlignment(JTextField.CENTER);                // et centre le texte
        
        controlle.setLayout(grille);
        //je place les boutonS dans ma grille 
        controlle.add(new JLabel()); controlle.add(new JLabel()); controlle.add(new JLabel()); controlle.add(new JLabel());
        controlle.add(new JLabel()); controlle.add(zoomPlus);     controlle.add(zoomMoins);    controlle.add(new JLabel());
        controlle.add(new JLabel()); controlle.add(new JLabel()); controlle.add(new JLabel()); controlle.add(new JLabel());
        controlle.add(new JLabel()); controlle.add(infoIteration);controlle.add(iteration);    controlle.add(new JLabel());
        controlle.add(new JLabel()); controlle.add(new JLabel()); controlle.add(new JLabel()); controlle.add(new JLabel());
        controlle.add(new JLabel()); controlle.add(mandel);       controlle.add(triangle);     controlle.add(new JLabel());
        controlle.add(new JLabel()); controlle.add(new JLabel()); controlle.add(new JLabel()); controlle.add(new JLabel()); 
        controlle.add(new JLabel()); controlle.add(seqeuent);       controlle.add(thread);     controlle.add(new JLabel());
        controlle.add(new JLabel()); controlle.add(new JLabel()); controlle.add(new JLabel()); controlle.add(new JLabel());
        
        fenetre.setSize(1300, 720);     // on définit par défaut une taille de fenetre
        fenetre.setLocation(200, 200);  // (on gère l'augmentation de la taille de fenetre)

        jp = new Dessin(controlleur, modele);   //appelle de la classe responsable du
                                                // dessin des fractales
        fenetre.add(jp, "Center");
        fenetre.add(controlle, "West");;
       
        fenetre.setVisible(true);

        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jp.addMouseWheelListener(new Evenement(controlleur, modele));   //
        jp.addMouseMotionListener(new Evenement(controlleur, modele));  // gestion des évènements lié a la sourie
        jp.addMouseListener(new Evenement(controlleur, modele));        //

        zoomPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {      
                controlleur.zoomer();
                controlleur.zoomerTriangle();                           //
            }                                                           // méthodes de gestion des boutons graphiques
        });                                                             // leurs fonctions est assez explicite
                                                                        //
        zoomMoins.addActionListener(new ActionListener() {              //
            @Override                                                   //
            public void actionPerformed(ActionEvent actionEvent) {
                controlleur.dezoomer();
                controlleur.dezoomerTriangle();
            }
        });
        
        iteration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent){           //permet de modifier l'itération de
                modele.setIteration(Integer.parseInt(iteration.getText())); // mandelbrot
            }
        });
        
        mandel.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent){       //                
                controlleur.initMandelbrot();                           //
            }                                                           //
        });                                                             // méthodes explicitent permettant de changé de fractale 
                                                                        // en prenant les valeurs initiales respectivent à chacune
        triangle.addActionListener(new ActionListener(){                //
            @Override                                                   //
            public void actionPerformed(ActionEvent actionEvent){       //
                controlleur.initTriangle();
            }
        });
        seqeuent.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent){                
                controlleur.initMandelbrotThread();
            }
        });
        
        thread.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent){
                controlleur.initMandelbrotSequent();
            }
        });

        System.out.println("exe Fenetre");
    }

    @Override
    public void update(Observable o, Object arg) {
        //mise a jour
        iteration.setText(Integer.toString((int) modele.getIteration()));   //permet de redessinner le fractale 
        jp.repaint();                                                       // dès qu'une valeur est modifiée + modifie la valeur affiché
                                                                            // de l'itération quand cette dernière est modifiée
    }

}
