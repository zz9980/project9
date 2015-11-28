//// squids.java // Project #9:  
//// BAM:5b28

Squid otto;
float surface;

//// SETUP:  size & reset.
void setup() {
  size( 800, 600 );
  reset();
}
// Constuct squid(s).
void reset() {
  surface=  random(  height/4, height/2 );
  float squidX=  random(width*0.1, width*0.9);
  otto=  new Squid( squidX, height );
}

//// NEXT FRAME:  scene, action
void draw() {
  scene();
  action();
  camera();
}
void scene() {
  background( 50,100,200 );      // Dark sky.
  fill( 0,150,50 );              // Dark water
  rect( 0,surface, width, height-surface );
}
void action() {
  otto.move();
}
void camera() {
  otto.show(); 
}


class Squid {
  float x,y;        // Coordinates
  float dx=0,dy=-1;  // Speed
  float w=30,h=30;
  int legs=10;      // Number of legs.
  String name="";
  float r,g,b;      // Color.
  //// CONSTRUCTORS ////
  Squid( float x, float y) {
    this.x=x;
    this.y=y;
    legs=  int( random(1,10) );
    // Purplish
    r=  random(50,255);
    g=  random(100);
    r=  random(50,255);
  }
  //// METHODS ////
  void show() {
    fill(r,g,b);
    ellipse( x,y, w,h );         // round top
    rect( x-w/2,y, w,h/2 );    // flat bottom
    fill(255);
    ellipse( x,y-h/4, 5,5 );    // eye
    // Legs
    fill(r,g,b);               // legs.
    float legX=  x, foot=0;
    if (dx<0) {
      foot=5;
      if (frameCount/30 %  2 > 0) foot=  -foot;
    }
    for (int i=0; i<legs; i++) {
      line( legX, y+h/2, legX+foot, 20+y+h/2 );
    }
  }
  void move() {
    x += dx;
    y += dy;
  }
}
