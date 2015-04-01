/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package printerUtil;

import java.awt.image.BufferedImage;

/***
    * @author:Cesar Huanes
    * @since :23/10/2014
    */
public abstract class FarmaCodes {
    public abstract byte[] getInitSequence(); 
    public abstract byte[] getNewLine();    
    public abstract byte[] getImageHeader();
    public abstract int getImageWidth();
    public byte[] transImage(BufferedImage image) {
        
       CenteredImage centeredimage = new CenteredImage(image, getImageWidth());
        int iWidth = (centeredimage.getWidth() + 7) / 8; 
        int iHeight = centeredimage.getHeight();
        byte[] bData = new byte[getImageHeader().length + 4 + iWidth * iHeight];
        
        // Comando de impresion de imagen
        System.arraycopy(getImageHeader(), 0, bData, 0, getImageHeader().length);
        
        int index = getImageHeader().length;
        
        // Dimension de la imagen
        bData[index ++] = (byte) (iWidth % 256);
        bData[index ++] = (byte) (iWidth / 256);
        bData[index ++] = (byte) (iHeight % 256);
        bData[index ++] = (byte) (iHeight / 256);       
        
        // Raw data
        int iRGB;
        int p;
        for (int i = 0; i < centeredimage.getHeight(); i++) {
            for (int j = 0; j < centeredimage.getWidth(); j = j + 8) {
                p = 0x00;
                for (int d = 0; d < 8; d ++) {
                    p = p << 1;
                    if (centeredimage.isBlack(j + d, i)) {
                        p = p | 0x01;
                    }
                }
                
                bData[index ++] = (byte) p;
            }
        }        
        return bData;
    }

    protected class CenteredImage {

        private final BufferedImage image;
        private final int width;

        public CenteredImage(BufferedImage image, int width) {
            this.image = image;
            this.width = width;
        }

        public int getHeight() {
            return image.getHeight();
        }

        public int getWidth() {
            return width;
        }

        public boolean isBlack(int x, int y) {

            int centeredx = x + (image.getWidth() - width) / 2;
            if (centeredx < 0 || centeredx >= image.getWidth() || y < 0 || y >= image.getHeight()) {
                return false;
            } else {
                int rgb = image.getRGB(centeredx, y);

               /* int gray = (int)(0.30 * ((rgb >> 16) & 0xff) +
                                 0.59 * ((rgb >> 8) & 0xff) +
                                 0.11 * (rgb & 0xff));*/
                int gray = (int)(0.299 * ((rgb >> 16) & 0xff) +
                                 0.587 * ((rgb >> 8) & 0xff) +
                                 0.114 * (rgb & 0xff));
               
                return gray < 128;
            }
        }
    }
}
