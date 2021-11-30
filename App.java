import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.*;
public class App {
    public static void main(String[] args) throws Exception {
        
        System.out.println("file location: "+ args[0]);
        
        File imageFile = new File(args[0]);
       
        if(!imageFile.exists()){
            System.out.println("getting file from src/images/"+ args[0]);
            imageFile = new File("src/images/"+args[0]);
            
         }else{
            System.out.println("file exists");
        }
        
        BufferedImage bimg = ImageIO.read(imageFile);
        
        
        Color white = new Color(255,255,255);
        Color black = new Color(0,0,0);
        Color red = new Color(255,0,0);
        Color green = new Color(0,255,0);
        Color blue = new Color(0,0,255);
        Color violet = new Color(0,255,255);
        Color yellow = new Color(255,255,0);
        Color aqua = new Color(0,255,255);
        Color grey = new Color(128, 128,128);
        Color[] colors4 = {blue, green, red, violet, yellow, aqua};
        Color[] colors3 = {black, white,  blue, green, red, violet, yellow, aqua};
        Color[] colors2 = {black, white,  blue, green, red};
        Color[] colors1 = {black, white};
        Ditherer bigD;
        if(args.length> 3){
            if(args[3].equals("1")){
                bigD = new Ditherer(colors1);
            }else if(args[3].equals("2")){
                bigD = new Ditherer(colors2);
            }else if(args[3].equals("3")){
                bigD = new Ditherer(colors3);
            }else if(args[3].equals("4")){
                bigD = new Ditherer(colors4);
            }else{
                System.out.println("Invalid color selection. select 1, 2, 3, or 4 for args[3]");
                bigD = new Ditherer(colors3);
            }
        }else{
            bigD = new Ditherer(colors3);
        }
        BufferedImage result = bigD.dither(bimg, args[1]);
        String saveLocation = "";
        if (args.length > 2){
            if(args[2].equals("result")){
                saveLocation = "src/images/result.png";
            }else{
                saveLocation = args[2];
            }
            
        }else{
            saveLocation = "src/images/result.png";
        }
        File outputfile = new File(saveLocation);
        ImageIO.write(result, "png", outputfile);
        System.out.println("Dithering Complete! Saved Result to: " +saveLocation);
       
    }

    

    
}
