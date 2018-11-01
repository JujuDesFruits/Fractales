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

public class FractaleControlleur {

    private FractaleModele modele;

    public FractaleControlleur(FractaleModele modele) {
        this.modele = modele;
        System.out.println("exe Controlleur");
    }

    //toutes les méthodes on un nom qui explique leur role de manière claire
    public void zoomer() {

        double oldX2 = modele.x2;
        double oldY2 = modele.y2;

        modele.zoom = modele.zoom * 1.2;
        modele.echelle = 100 / modele.zoom;

        modele.x2 = 8 * modele.echelle + modele.x1;
        modele.y2 = 8 * modele.echelle + modele.y1;

        double diffX = oldX2 - modele.x2;
        double diffY = oldY2 - modele.y2;

        modele.x1 += diffX / 2;
        modele.y1 += diffY / 2;

        modele.iteration_max = modele.iteration_max + 6;

        modele.MAJ();
    }

    public void dezoomer() {

        double oldX2 = modele.x2;
        double oldY2 = modele.y2;

        modele.zoom = modele.zoom / 1.2;
        modele.echelle = 100 / modele.zoom;

        modele.x2 = 8 * modele.echelle + modele.x1;
        modele.y2 = 8 * modele.echelle + modele.y1;

        double diffX = oldX2 - modele.x2;
        double diffY = oldY2 - modele.y2;

        modele.x1 += diffX / 2;
        modele.y1 += diffY / 2;

        modele.iteration_max = modele.iteration_max - 6;

        modele.MAJ();
    }

    public void deplacement(int getX, int getY) {
        double diffX = modele.oldPosX - getX;
        double diffY = modele.oldPosY - getY;

        modele.x1 += diffX * modele.echelle / 100;
        modele.y1 += diffY * modele.echelle / 100;

        modele.oldPosX = getX;
        modele.oldPosY = getY;

        modele.MAJ();
    }

    void zoomerTriangle() {

        modele.dMin = modele.dMin * 0.99;
        modele.x0 -= 50;
        modele.d += 100;
        modele.y0 -= 50;

        modele.MAJ();
    }

    void dezoomerTriangle() {
        if (modele.x0<=modele.d){
            modele.dMin = FractaleModele.dMin * 1.01;
            modele.x0 += 50;
            modele.d -= 100;
            modele.y0 += 50;
        }
        

        modele.MAJ();

    }

    void deplacementTraingle(int getX, int getY) {
        if (FractaleModele.mandelbrot == false) {
            double diffX = modele.oldPosX - getX;
            double diffY = modele.oldPosY - getY;

            modele.x0 -= diffX;
            modele.y0 -= diffY;

            modele.oldPosX = getX;
            modele.oldPosY = getY;

            modele.MAJ();
        }
    }

    void initMandelbrot() {
        modele.x1 = modele.X1_INIT;
        modele.x2 = modele.X2_INIT;
        modele.y1 = modele.Y1_INIT;
        modele.y2 = modele.Y2_INIT;
        modele.zoom = modele.ZOOM_INIT;
        modele.echelle = 100 / modele.zoom;
        modele.iteration_max = modele.ITERATION_INIT;
        modele.mandelbrot = true;
        
        modele.MAJ();
    }
    
    void initMandelbrotThread() {
        
        modele.thread = true;
        modele.x1 = modele.X1_INIT;
        modele.x2 = modele.X2_INIT;
        modele.y1 = modele.Y1_INIT;
        modele.y2 = modele.Y2_INIT;
        modele.zoom = modele.ZOOM_INIT;
        modele.echelle = 100 / modele.zoom;
        modele.iteration_max = modele.ITERATION_INIT;
        modele.mandelbrot = true;
               
        modele.MAJ();
    }
    
    void initMandelbrotSequent() {
        
        modele.thread = false;
        modele.x1 = modele.X1_INIT;
        modele.x2 = modele.X2_INIT;
        modele.y1 = modele.Y1_INIT;
        modele.y2 = modele.Y2_INIT;
        modele.zoom = modele.ZOOM_INIT;
        modele.echelle = 100 / modele.zoom;
        modele.iteration_max = modele.ITERATION_INIT;
        modele.mandelbrot = true;
        
        modele.MAJ();
    }

    void initTriangle() {
        modele.dMin = modele.dMin_INIT;
        modele.x0 = modele.X0_INIT;
        modele.y0 = modele.Y0_INIT;
        modele.d = modele.D_INIT;
        modele.mandelbrot = false;
        
        modele.MAJ();
    }
}
