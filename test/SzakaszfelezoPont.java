
import java.awt.Point;

public class SzakaszfelezoPont {

    public static void main(String[] args) {
        Point p1 = new Point(730,100);
        Point p2 = new Point(700,92);

        Point p3 = new Point(((p1.x-p2.x)/2)+p2.x,((p1.y-p2.y)/2)+p2.y);

        Point p4 = new Point(730,100);
        Point p5 = new Point(701,93);

        

        System.out.println("szakaszfelező pont: "+p3);

        double d = Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
        double e = Math.sqrt(Math.pow(p5.x - p4.x, 2) + Math.pow(p5.y - p4.y, 2));
        System.out.println("távolságuk: "+d+"  "+e);
        
    }

}