package fractalesexplorer_mvc;

import java.awt.Color;
import java.awt.image.BufferedImage;

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
public class FractaleThread extends Thread{
    
    FractaleModele modele;
    FractaleControlleur controlleur;
    Dessin dessin;
    int x1;
    int x2;
    
    public FractaleThread(int x1,int x2, FractaleControlleur controlleur, FractaleModele modele, Dessin dessin) {
        super();
        this.controlleur = controlleur;
        this.modele = modele;
        this.dessin=dessin;
        this.x1=x1;
        this.x2=x2;
    }
    @Override
    public void run(){
        dessin.image = new BufferedImage(Fenetre.fenetre.getWidth()*2/3, Fenetre.fenetre.getHeight(), BufferedImage.TYPE_INT_RGB);
        dessin.g = dessin.image.createGraphics();

        for (int x = x1; x < x2; x++) {
            for (int y = 0; y < Fenetre.fenetre.getHeight(); y++) {
                double c_r = x / modele.zoom + modele.x1;
                double c_i = y / modele.zoom + modele.y1;
                double z_r = 0;
                double z_i = 0;
                double i = 0;

                do {
                    double tmp = z_r;

                    z_r = z_r * z_r - z_i * z_i + c_r;
                    z_i = 2 * z_i * tmp + c_i;
                    i = i + 1;

                } while (z_r * z_r + z_i * z_i < 4 && i < modele.iteration_max);

                if (i == modele.iteration_max) {
                    dessin.image.setRGB(x, y, Color.BLACK.getRGB());
                } else {
                    try{
                        try{dessin.image.setRGB(x, y, new Color(0, (int) (i * 255 / modele.iteration_max), 0).getRGB());}
                        catch(ArrayIndexOutOfBoundsException e){
                            System.out.println("écriture des pixels en dehors de l'image");
                        }
                    }
                    catch(IllegalArgumentException e){
                        System.out.println("Couleur RGB définit par l'itération en dehors des paramètres possibles");
                        
                        modele.iteration_max=2;
                    }
                }
            }
        }
        Dessin.g.setColor(Color.red);
        dessin.g.drawLine(dessin.image.getWidth() / 2 - 10, dessin.image.getHeight() / 2, dessin.image.getWidth() / 2 + 10, dessin.image.getHeight() / 2);
        dessin.g.drawLine(dessin.image.getWidth() / 2, dessin.image.getHeight() / 2 - 10, dessin.image.getWidth() / 2, dessin.image.getHeight() / 2 + 10);

        Dessin.g.dispose();
    }
    
}
