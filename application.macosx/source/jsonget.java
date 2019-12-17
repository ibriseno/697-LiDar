import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import http.requests.*; 
import java.util.ArrayList; 
import peasy.*; 
import static javax.swing.JOptionPane.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class jsonget extends PApplet {






public GetRequest get = new GetRequest("http://192.168.0.103:5000/");
public PostRequest post = new PostRequest("http://192.168.0.103:5000/delete");
public ArrayList<Point> arr = new ArrayList();
ArrayList<PVector> pointList;
PeasyCam cam;
public int counter = 0;
final float angleIncrement=0.3f;
float zoom = 1;
float xo;
float yo;

public void setup() 
{
  frameRate(1000);
	
  background(33);
  stroke(255,255,255);
  
	pointList = new ArrayList<PVector>();
  colorMode(RGB, 255, 255, 255);
  // PeasyCam
  cam = new PeasyCam(this, 1000);

}

public void draw(){
  
    
  background(33);
  scale(zoom);
  get.send(); // program will wait untill the request is completed
  
  JSONObject response = parseJSONObject(get.getContent());
  //println(response.getString("status").toString());
  if(response.getString("status").toString().equals("p")){
    
  }
   else{
  
  int x = response.getInt("X");
  int y = response.getInt("Y");
  int z = response.getInt("Z");
  //int z = response.getInt("Z");
  
  //arr.add(new Point(x+200,y+200));
  pointList.add(new PVector(PApplet.parseFloat(x+200), PApplet.parseFloat(y+200), PApplet.parseFloat(z)));
  //println("Size " + pointList.size());
  for(int i = 0; i < pointList.size(); i++){
    PVector v = pointList.get(i);
    
    stroke(255-v.z, 255-v.y, 255-v.x);
    //point(arr.get(i).x, arr.get(i).y);
    point(v.x, v.y, v.z);
    
  }
  
  }
} 
class Point { 
 
  float x; 
  float y;
  float z;
 
  Point (float x_, float y_, float z_) { 
    x = x_; 
    y = y_;
    z = z_;
  }
} // class

public void keyReleased() {
  if (key =='x') {
    // erase all points
    pointList.clear();
    post.send();
  }
  else if (key == 'z'){
    zoom += 0.2f;
  }
  else if (key == 'c'){
    zoom -= .2f; 
  }
  else if (key == CODED) {
    if (keyCode == UP) {
      cam.rotateX(angleIncrement);
    } else if (keyCode == DOWN) {
      cam.rotateX(-angleIncrement);
    }else if (keyCode == LEFT) {
      cam.rotateY(angleIncrement);
    }else if (keyCode == RIGHT) {
      cam.rotateY(-angleIncrement);
    }
  }
}
  public void settings() { 	size(1600, 900, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "jsonget" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
