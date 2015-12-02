//// squids1.java
//// BAM:5b28a:  Sample code for project #9

Squid otto, nono, deca;
float surface;
float boatX=0, boatDX=5;
float moonX=0, moonY=100;
int cargo=0;                    // Total caught, this trip.
int score=0;

//// SETUP:  size & reset.
void setup() {
  size( 800, 600 );
  reset();
}
// Constuct squid(s).
void reset() {
  surface=  height/4;
  moonY=  surface/3;
  // Construct 3 squids.
  otto=  new Squid( "Otto", width/4  );
  nono=  new Squid( "Nono", width/2 );
  deca=  new Squid( "Deca", width*3/4 );
}


//// NEXT FRAME:  scene, action
void draw() {
  scene();
  action();
  show();
  text( "squid1.java", 100, 20 );
  if (score>0) text( "SCORE:  "+score, width*3/4, 20 );
}
void scene() {
  background( 50,150,200 );      // Dark sky.
  // Moon
  if (moonX > width+100) {
    moonX=  0;
    moonY=  random( 100, surface-50 );
  }
  moonX += 1;
  fill( 200,150,50 );
  ellipse( moonX, moonY, 40,40 );
  // Dark water.
  fill( 0,100,50 );
  rect( 0,surface, width, height-surface );  
}
void action() {
  otto.move();
  nono.move();
  deca.move();
  // Move boat.
  boatX += boatDX;
  // Caught?
  int caught=0;
  if (otto.hit( boatX,surface )) caught += otto.legs;
  if (nono.hit( boatX,surface )) caught += otto.legs;
  if (deca.hit( boatX,surface )) caught += otto.legs;
  cargo += caught;
  if (caught>0) boatX += 2*boatDX;    // Move boat away from catch.
  // End of voyage.
  if (boatX>width)  {
    boatX=  0;
    score += cargo;
    cargo=0;
  }
}
void show() {
  otto.show();
  nono.show();
  deca.show();
  // Boat.
  fill(255,0,0);
  noStroke();
  rect( boatX, surface-40, 100, 40 );
  if (boatDX>0)   triangle( boatX+100,surface, boatX+100,surface-40, boatX+140,surface-40 );
  else            triangle( boatX,surface, boatX,surface-40, boatX-40,surface-40 );
  rect( boatX+25, surface-60, 50, 20 );      // Cabin
  fill(0);
  text( cargo, boatX+10, surface-20 );  
}


//// EVENT HANDLERS:  keys, clicks ////
void keyPressed() {
  if (key == 'r') reset();
}


//// OBJECTS ////
class Squid {
  float x,y;        // Coordinates
  float dx=0,dy=0;  // Speed
  float w=30,h=30;
  int legs=10;      // Number of legs.
  String name="";
  float r=200, g=100, b=200;  // Purple.
  //// CONSTRUCTORS ////
  Squid( String s, float x ) {
    this.name=  s;
    this.x=x;
    bottom();
  }
  //// Start again at bottom.  (Random speed & legs.)
  void bottom() {
    y=  height;
    dy=  -random( 1, 2 );
    legs=  int( random(1, 10.9) );
  }
  
  //// METHODS:  move & show ////
  // Move up slowly, down fast, randomize at bottom.
  void move() {
    x += dx;
    y += dy;
    if (y>height) { 
      bottom();
    }
    else if (y<surface) { 
      dy=  -2 * dy;        // Descend fast!
    }
  }
  // Display the creature:  dome, eye, legs + name.
  void show() {
    fill(r,g,b);
    stroke(r,g,b);
    ellipse( x,y, w,h );         // round top
    rect( x-w/2,y, w,h/2 );      // flat bottom
    fill(255);
    ellipse( x,y-h/4, 10, 10 );  // eye
    // Legs
    fill(r,g,b);                 // legs animate.
    float legX=  x-w/2, foot=0;
    if (dy<0) {
      foot=5;
      if (y%50 > 25) foot=  -foot;
      //--  if (frameCount/30 %  2 > 0) foot=  -foot;
    }
    for (int i=0; i<legs; i++) {
      line( legX, y+h/2, legX+foot, 20+y+h/2 );
      legX += w / (legs-1);
    }
    fill(200,200,0);
    text( name+legs, x-2-w/2, y-2+h/2 );
  }
  
  //// Return true iff (x,y) is near me.
  boolean hit( float xx, float yy ) {
    return dist( xx,yy, x,y ) < 2*h;
  }
  
}

