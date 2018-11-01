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

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dessin extends JPanel {

    FractaleModele modele;
    FractaleControlleur controlleur;

    public BufferedImage image;
    public static Graphics2D g;

    public Dessin(FractaleControlleur controlleur, FractaleModele modele) {
        super();
        this.controlleur = controlleur;
        this.modele = modele;
        modele.im = mandelbrot();

        System.out.println("exe Dessin");
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (modele.mandelbrot) {
            modele.x2 = 8 * modele.echelle + modele.x1;
            modele.y2 = 8 * modele.echelle + modele.y1;
            modele.im = mandelbrot();

        } else {

            int h = (int) (modele.d * Math.sqrt(3) / 2);    // hauteur
            int xA = modele.x0, yA = modele.y0 + h;    // (bas-gauche)
            int xB = modele.x0 + modele.d, yB = modele.y0 + h;    // (bas-droite)
            int xC = modele.x0 + modele.d / 2, yC = modele.y0;    // triangle équilatéral (haut-milieu)
            int[] x = {xA, xB, xC};
            int[] y = {yA, yB, yC};
            image = new BufferedImage(Fenetre.fenetre.getWidth()*2/3, Fenetre.fenetre.getHeight(), BufferedImage.TYPE_INT_RGB);
            modele.im = triangle(x, y, modele.d / 2);

        }
        g.drawImage(modele.im, 0, 0, null);
    }

    public BufferedImage mandelbrot() {
        if (!modele.thread) {
            FractaleThread t1 = new FractaleThread(0                                           , (Fenetre.fenetre.getWidth() * 2 / 3) * 1 / 4,controlleur,modele,this);
            t1.start();
            FractaleThread t2 = new FractaleThread((Fenetre.fenetre.getWidth() * 2 / 3) * 1 / 4, (Fenetre.fenetre.getWidth() * 2 / 3) * 2 / 4,controlleur,modele,this);
            t2.start();
            FractaleThread t3 = new FractaleThread((Fenetre.fenetre.getWidth() * 2 / 3) * 2 / 4, (Fenetre.fenetre.getWidth() * 2 / 3) * 3 / 4,controlleur,modele,this);
            t3.start();
            FractaleThread t4 = new FractaleThread((Fenetre.fenetre.getWidth() * 2 / 3) * 3 / 4, (Fenetre.fenetre.getWidth() * 2 / 3) * 4 / 4,controlleur,modele,this);
            t4.start();
            try {
                t1.join();
                t2.join();
                t3.join();
                t4.join();

            } catch (InterruptedException ex) {
                Logger.getLogger(Dessin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            FractaleThread t1 = new FractaleThread(0,(Fenetre.fenetre.getWidth()*2/3),controlleur,modele,this);
            t1.start();

        try {
            t1.join();



        } catch (InterruptedException ex) {
            Logger.getLogger(Dessin.class.getName()).log(Level.SEVERE, null, ex);
        }
        }

        return image;
    }
    
    public BufferedImage triangle(int[] x, int[] y, int d) {

        g = image.createGraphics();

        g.setColor(Color.red);

        if (d < FractaleModele.dMin) {
            g.drawPolygon(x, y, 3);   // fond de la récursion
        } else {
            // milieus des cotés du triangle:
            int xMc = (x[0] + x[1]) / 2, yMc = (y[0] + y[1]) / 2;
            int xMb = (x[0] + x[2]) / 2, yMb = (y[0] + y[2]) / 2;
            int xMa = (x[1] + x[2]) / 2, yMa = (y[1] + y[2]) / 2;

            int[] xNouveau1 = {x[0], xMc, xMb};
            int[] yNouveau1 = {y[0], yMc, yMb};
            triangle(xNouveau1, yNouveau1, d / 2);    // récursion

            int[] xNouveau2 = {x[1], xMc, xMa};
            int[] yNouveau2 = {y[1], yMc, yMa};
            triangle(xNouveau2, yNouveau2, d / 2);    // récursion

            int[] xNouveau3 = {x[2], xMb, xMa};
            int[] yNouveau3 = {y[2], yMb, yMa};
            triangle(xNouveau3, yNouveau3, d / 2);    // récursion
        }

        g.dispose();

        return image;

    }
}
