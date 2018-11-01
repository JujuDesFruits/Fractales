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

import java.awt.image.BufferedImage;
import java.util.Observable;

public class FractaleModele extends Observable {

    //valeur d'initialisation du mandelbrot
    public double X1_INIT = -2.1;
    public double X2_INIT = 0.6;
    public double Y1_INIT = -1.2;
    public double Y2_INIT = 1.2;
    public double ZOOM_INIT = 300;
    public double ITERATION_INIT = 100;

    //valeur de calcul
    public static double x1 = -2.1;
    public static double x2 = 0.6;
    public static double y1 = -1.2;
    public static double y2 = 1.2;
    public static double zoom = 300;
    public static double iteration_max = 100;
    public static double oldPosX;
    public static double oldPosY;
    public static double echelle = 100 / zoom;
    public static double image_x = (x2 - x1) * zoom;
    public static double image_y = (y2 - y1) * zoom;
    public static BufferedImage im;
    public static int hauteur = 800;

    //valeur d'initialisation du Sierspinski
    public double dMin_INIT = 8.0;
    public int X0_INIT = 200;
    public int D_INIT = 500;
    public int Y0_INIT = 50;

    //valeur de calcul
    public static double dMin = 7.0;    // limite à la récursion en pixels
    public static int x0 = 200;
    public static int d = 500;
    public static int y0 = 50;

    public static boolean mandelbrot = true;
    public static boolean thread = true;

    public FractaleModele() {
        System.out.println("exe Modele");
    }

    //met a jour les valeurs de définition du dessin 
    public void MAJ() {
        setChanged();
        notifyObservers();
    }

    // méthode de modification d'attribut et lecture d'attribut
    public void setOldPosX(double PosX) {
        this.oldPosX = PosX;
        MAJ();
    }

    public void setOldPosY(double PosY) {
        this.oldPosY = PosY;
        MAJ();
    }
    
    public void setIteration(double iteration){
        this.iteration_max=iteration;
        MAJ();
    }
    
    public double getIteration(){
        return this.iteration_max;
    }

}
