//// squids.java // Project #9:  
//// BAM:5b28

Squid otto, nono, deca;
float surface;
float boatX=0, boatDX=5;
float moonX=0, moonY=100;

//// SETUP:  size & reset.
void setup() {
  size( 800, 600 );
  reset();
}
// Constuct squid(s).
void reset() {
  surface=  random(  height/4, height/2 );
  moonY=  surface/3;
  moonY=  random( 200, surface+200 );
  // 3 squids.
  otto=  new Squid( "Octo", width/4  );
  nono=  new Squid( "Nono", width/2 );
  deca=  new Squid( "Deca", width*3/4 );
}


//// NEXT FRAME:  scene, action
void draw() {
  scene();
  action();
  show();
}
void scene() {
  background( 50,150,200 );      // Dark sky.
  // Moon
  if (moonX > width+100) {
    moonX=  -100;
    moonY=  random( 100, surface+50 );
  }
  moonX += 1;
  fill( 200,150,50 );
  ellipse( moonX, moonY-150*sin( PI * moonX/width ), 40,40 );
  // Dark water.
  fill( 0,100,50 );
  rect( 0,surface, width, height-surface );  
}
void action() {
  otto.move();
  nono.move();
  deca.move();
  if (boatX>width)  boatDX = -random( 0.5, 3 );
  if (boatX<0)      boatDX = random( 1, 5 );
  boatX += boatDX;
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
  rect( boatX+25, surface-60, 50, 20 );      // Cabin & mast.
  rect( boatX+45, surface-80, 10, 20 );
  // Smoke
  fill( 50,50,50, 200 );
  ellipse( boatX +50 -10*boatDX, surface-100, 30, 30 );
  ellipse( boatX +50 -20*boatDX, surface-110, 20, 20 );
  ellipse( boatX +50 -30*boatDX, surface-120, 10, 10 );
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
  float r,g,b;      // Color.
  //// CONSTRUCTORS ////
  Squid( String s, float x ) {
    this.name=  s;
    this.x=x;
    bottom();
    // Purplish
    r=  random(100, 255);
    g=  random(0, 100);
    b=  random(100, 250);
  }
  //// Start again at bottom.  (Random speed.)
  void bottom() {
    y=  height - h*2;
    dy=  -random( 0.1, 0.9 );
    legs=  int( random(1, 10.9) );
  }
  //// METHODS:  move & show ////
  void move() {
    x += dx;
    y += dy;
    if (y<surface) {
      bottom();
    }
  }
  //// Display the creature.
  void show() {
    fill(r,g,b);
    stroke(r,g,b);
    ellipse( x,y, w,h );         // round top
    rect( x-w/2,y, w,h/2 );      // flat bottom
    fill(255);
    float blink=10;
    if ( y%100 > 80) blink=2;
    ellipse( x,y-h/4, 10, blink );     // eye
    // Legs
    fill(r,g,b);                 // legs.
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
    //  text( name+"("+legs+")", x-w/2, y-20 );
    text( name, x-w/2, y+h/2 );
  }
}
