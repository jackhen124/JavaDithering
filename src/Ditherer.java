
import javax.imageio.ImageIO;
import javax.print.event.PrintEvent;

import java.awt.Color;
import java.awt.image.*;
public class Ditherer {
    public int width;
    public int height;
    public BufferedImage inputImage;
    
    BufferedImage outputImage;
   
    Color[] pallete;
   
    Pixel[][] pixelError;
    public Ditherer(Color[] colorArray){
       pallete = colorArray;
    
    }
    
    public BufferedImage dither(BufferedImage image, String algo){
        
        inputImage = image;
        outputImage = image;
        width = image.getWidth();
        height = image.getHeight();
        
        pixelError = new Pixel[width][height];
        for (int x = 0; x < pixelError.length; x++){
            for (int y = 0; y < pixelError[0].length; y++){
                
                
                pixelError[x][y] = new Pixel(0,0,0);
                
            }
        }
       
        applyAlgorithm(algo);
        //System.out.println(algo+" Dithering Complete, returning image");
        return outputImage;
       
    }
    /*
    public Color[][] convertToColors(){
       
        Color[][] result = new Color[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result[row][col] = new Color(image.getRGB(col, row));
            }
        }

      return result;
    }
    */

    

    public void applyAlgorithm(String algo){
        
        int x = 0;
        int y = 0;
        while(x < width){
            y = 0;
            
            while(y < height){
                
                Pixel preErrorMatch = new Pixel(inputImage.getRGB(x,y));
              
                Pixel adjustedMatchPixel = new Pixel(preErrorMatch.r, preErrorMatch.g, preErrorMatch.b);
                
        
                adjustedMatchPixel.addPixel(pixelError[x][y]);
                //System.out.println("Pixel["+x+"]["+y+"] - error:"+pixelError[x][y].dataString()+" applied to "+preErrorMatch.dataString()+ " => " + adjustedMatchPixel.dataString());
                
                
                Pixel palletePixel = closestPixelFromPallete(adjustedMatchPixel);
               
                
                Pixel curErrorPixel = createErrorPixel(adjustedMatchPixel, palletePixel);

                
                //System.out.println("match:"+adjustedMatchPixel.dataString()+ " + error:"+ curErrorPixel.dataString()+ " should = " + palletePixel.dataString());
                
                if (algo.equals("jarvis")){
                    jarvis(x, y, curErrorPixel);
                }else if(algo.equals("floydsteinberg")){
                    floydSteinberg(x, y, curErrorPixel);
                }else if (algo.equals("stucki")){
                    stucki(x, y, curErrorPixel);
                }else{
                    System.out.println(algo+" is an invalid algorithm name. Choose from 'floydsteinberg', 'jarvis', or 'stucki'");
                    return;
                }
                //System.out.println("palleteColor"+palletePixel.dataString());
                //Color palleteColor = new Color(Math.round(palletePixel.r), Math.round(palletePixel.g), Math.round(palletePixel.b));
                //outputImage.setRGB(x, y, palleteColor.getRGB());
                outputImage.setRGB(x, y, palletePixel.getColor().getRGB());
                y++;
            }
            x++;
            //System.out.println("Dithering progress: "+x+"/"+width);
        }
           
    }

    public void floydSteinberg(int x, int y, Pixel errorPixel){
       

        //System.out.println(x+" "+y);
       
        float a = (float)1/16;
        float b = (float)3/16;
        float c = (float)5/16;
        float d = (float)7/16;
        //System.out.println(d);
        if(x < width-1){
            //System.out.println("1");
            pixelError[x+1][y].applyError(d, errorPixel);
        }
        if(x > 0 && y < height-1){
            //System.out.println("2");
            pixelError[x-1][y+1].applyError(b, errorPixel);
        }
        if( y < height - 1){
            //System.out.println("3");
            pixelError[x][y+1].applyError(c, errorPixel);
        }
        if ( x < width - 1 && y < height - 1){
            //System.out.println("4");
            pixelError[x+1][y+1].applyError(a, errorPixel);
        }
        
    }
    public void jarvis(int pixelX, int pixelY, Pixel errorPixel){
        jarvisStucki(pixelX, pixelY, errorPixel, 48);
    }

    public void stucki(int pixelX, int pixelY, Pixel errorPixel){
        jarvisStucki(pixelX, pixelY, errorPixel, 42);
    }
    public void jarvisStucki(int pixelX, int pixelY, Pixel errorPixel, int denominator){
        float a = (float)1/denominator;
        float b = (float)3/denominator;
        float c = (float)5/denominator;
        float d = (float)7/denominator;
        float[] fractions = {a,b,c,d};
            for (int x = -2; x <= 2; x++){
                
                for (int y = 0; y <= 2; y++){
                    
                    if (pixelX + x >= 0 && pixelX + x < width && pixelY + y >= 0 && pixelY + y < height){
                        
                        int fractionsIndex = (Math.abs(x) + Math.abs(y));
                        if (fractionsIndex < fractions.length && fractionsIndex >= 0){
                            //System.out.println(x+ ","+y+" fraction: "+fractionsIndex);
                            pixelError[pixelX + x][pixelY + y].applyError(fractions[fractionsIndex], errorPixel);
                        }
                        
                        
                    }
                    
                }
            }
    }

    public Pixel createErrorPixel(Pixel pixelBefore, Pixel pixelAfter){
        float rDiff = Math.abs(pixelAfter.r - pixelBefore.r);
        if (pixelAfter.r > pixelBefore.r){
            rDiff = -rDiff;
        }
        float gDiff = Math.abs(pixelAfter.g - pixelBefore.g);
        if(pixelAfter.g > pixelBefore.g){
            gDiff = -gDiff;
        }
        float bDiff = Math.abs(pixelAfter.b - pixelBefore.b);
        if(pixelAfter.b > pixelBefore.b){
            bDiff = -bDiff;
        }
        return new Pixel(rDiff, gDiff, bDiff);
    }

    

    public Pixel closestPixelFromPallete(Pixel match){
        //System.out.println("curError in closestPixel: "+curError);
        Pixel result = new Pixel(pallete[0]);
        
        for(int i = 1; i < pallete.length; i++){
            Pixel checkPixel = new Pixel(pallete[i]);
            //System.out.println("checking "+checkPixel.dataString());
            if (Math.abs(match.diffFrom(checkPixel)) < Math.abs(match.diffFrom(result))){
                //System.out.println("RESULT CHANGED to "+checkPixel.dataString()+ "from "+ result.dataString());
                result = checkPixel;
                
            }
        }
        
        //System.out.println("converted "+match.dataString()+ " => " + result.dataString());
        return result;
    }
    
}


