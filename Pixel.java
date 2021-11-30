import java.awt.Color;
public class Pixel {
    float r;
    float g;
    float b;
    float total;
   
    public Pixel(int red, int green, int blue){
        r = red;
        g = green;
        b = blue;
    }
    public Pixel(float red, float green, float blue){
        r = red;
        g = green;
        b = blue;
    }
    public Pixel(Color color){
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();
    }

    public Pixel(int rgb){
        
        Color color = new Color(rgb);
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();

    }

    public Color getColor(){
        
        return new Color(clamp255(Math.round(r)), clamp255(Math.round(g)), clamp255(Math.round(b)));
       
    }
    public int clamp255(int num){
       
        return  Math.max(0, Math.min(255, num));
    }

    
    
    
    public float diffFrom(Pixel otherPixel){
        //float result = sqrt((r2-r1)^2+(g2-g1)^2+(b2-b1)^2);
        float f1 = otherPixel.r-r;
        
        float f2 = otherPixel.g-g;
        float f3 = otherPixel.b-b;
        float luminance = (r + g +b);
        //float luminance = 0;
        //return (float) Math.sqrt((f1*f1)+ (f2*f2)+(f3*f3) + (luminance*luminance));
        //return f1+f2+f3; 
        return (f1*f1)+ (f2*f2)+(f3*f3) + (luminance*luminance);
       
    }

    
    public void addPixel(Pixel otherPixel){
        
        r = r + otherPixel.r;
        g = g + otherPixel.g;
        b = b + otherPixel.b;
    }

    public void applyError(float fraction, Pixel errorPixel){ 
        
       
       
        //System.out.println(fraction+ " * " + errorPixel.dataString());
        //System.out.println("apply error before:"+dataString());
        
        
        r += errorPixel.r*(fraction);
        g += errorPixel.g*(fraction);
        b += errorPixel.b*(fraction);
       
      
        //System.out.println("=> "+dataString());
        
       
    }

    

    public String dataString(){
        return "("+r+","+g+","+b+")";
    }

    

    
}
