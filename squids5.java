//// squids5.java -- array of squid objects
//// BAM:5b28c;  sample code for project 9

int many=5;
Squid school[]=  new Squid[many];
String names[]=  { "Otto", "Nono", "Deca", "Ariel", "Ursala" };
float spacing;


float surface;
float boatX=0, boatDX=5;
float moonX=0, moonY=100;
int score=0, cargo=0, caught=0;

//// SETUP:  size & reset.
void setup() {
  size( 800, 600 );
  spacing=  width/(many+1);
  reset();
}
// Constuct squid(s).
void reset() {
  surface=  random(  height/4, height/2 );
  moonY=  surface/3;
  moonY=  random( 200, surface+200 );
  // Many squids.
  float x=  spacing;
  for (int i=0; i<many; i++ ) {
    school[i]=  new Squid( names[i], x );
    x += spacing;
  }
}


//// NEXT FRAME:  scene, action
void draw() {
  scene();
  if (key >= 'A' && key <= 'Z') summary( school, school.length);  
  else action();
  show();
  messages();
}
void messages() {
  fill(0);
  textSize( 20 );
  text( "Squid School", width/3, 20 );
  textSize(12);
  text( "Hold S key to show all squids", width/3, 40 );
  text( "  (Use N,L,X,Y,D keys to SORT the array.)", width/3, 60 );
  text( "BAM:  squids5.java", 10, height-10 );
  if (score>0) text( "SCORE:  "+score, width*3/4, 20 );
  if (score>10000) {
    if (key == 'q') score=0;
    text( "Maximum score.\nQUITTING NOW\nPress the 'q' key to continue", width/2, 60 );
  }
}

//// METHODS TO MOVE & DRAW.
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
  noStroke();
  rect( 0,surface, width, height-surface );  
}
void action() {
  int caught=0;
  for (int i=0; i<many; i++ ) {
    school[i].move();
    if (school[i].hit( boatX,surface )) {
      caught += school[i].legs;
    }
  }
  cargo += caught;
  // Move boat.
  boatX += boatDX;
  if (caught>0) boatX += 2*boatDX;
  if (boatX>width)  {
    boatDX = -random( 0.5, 3 );    // Variable boat speed.
  }
  if (boatX<0) {
    score += cargo;
    cargo=0;
    boatDX = random( 1, 5 );      // Slower return.
  }
}
//// Display the squids in (sorted) order.
void show() {
  float x=  spacing;
  for (int i=0; i<many; i++ ) {
    school[i].x=  x;
    x += spacing;
    school[i].show();
  }
  boat();
}
//// Draw the boat.
void boat() {
  // Boat.
  fill(255,0,0);
  noStroke();
  rect( boatX, surface-40, 100, 40 );
  if (boatDX>0)   triangle( boatX+100,surface, boatX+100,surface-40, boatX+140,surface-40 );
  else            triangle( boatX,surface, boatX,surface-40, boatX-40,surface-40 );
  rect( boatX+25, surface-60, 50, 20 );      // Cabin & mast.
  rect( boatX+45, surface-80, 10, 20 );
  fill(0);
  if (cargo>0) text( cargo, boatX+10, surface-20 );
  // Smoke
  fill( 50,50,50, 200 );
  ellipse( boatX +50 -10*boatDX, surface-100, 30, 30 );
  ellipse( boatX +50 -20*boatDX, surface-110, 20, 20 );
  ellipse( boatX +50 -30*boatDX, surface-120, 10, 10 );
}  

//// SUMMARIES:  List all objects in the array.
// Display the properties of each object in the array.
void summary( Squid[] a, int many ) {
  fill(255);
  rect( 50,surface+50, width-100, height-surface-100 );
  float x=70, y=surface+70;
  // Labels.
  fill(150,0,0);
  text( "NAME", x, y );
  text( "legs", x+50, y );
  text( "x", x+100, y );
  text( "y", x+200, y );
  text( "dy", x+300, y );
  fill(0);
  for (int i=0; i<many; i++) {
    y += 15;    // Next line.
    text( a[i].name, x, y );
    text( a[i].legs, x+50, y );
    text( a[i].x, x+100, y );
    text( a[i].y, x+200, y );
    text( a[i].dy, x+300, y );
  }
}
    
    
  


//// EVENT HANDLERS:  keys, clicks ////
void keyPressed() {
  if (key == 'r') reset();
  // Sorting keys
  if (key == 'N') sortByName( school );
  if (key == 'X') sortByX( school );
  if (key == 'Y') sortByY( school );
  if (key == 'D') sortByDY( school );
  if (key == 'L') sortByLegs( school );
}


//// METHODS TO SORT.
void sortByName( Squid[] a ) {
  
}
void sortByLegs( Squid[] a ) {
  for (int m=a.length; m>1; m-- ) {
    // Find biggest.
    int k=0;
    for (int i=1; i<m; i++ ) {
      if (a[i].legs > a[k].legs) k=i;   // k is index of greatest.
    }
    swap( a, m-1, k );                  // Move biggest to end of array.
  }
}
void sortByX( Squid[] a ) {
  for (int m=a.length; m>1; m-- ) {
    // Find biggest.
    int k=0;
    for (int i=1; i<m; i++ ) {
      if (a[i].x > a[k].x) k=i;   // k is index of greatest.
    }
    swap( a, m-1, k );                  // Move biggest to end of array.
  }
}
void sortByY( Squid[] a ) {
}
void sortByDY( Squid[] a ) {
}
void swap( Squid a[], int j, int k ) {
  Squid tmp;
  tmp=  a[j];
  a[j]=  a[k];
  a[k]= tmp;
}




//// OBJECTS ////
class Squid {
  float x,y;        // Coordinates
  float dx=0,dy=0;  // Speed
  float w=40,h=40;
  int legs=10;      // Number of legs.
  String name="";
  float r,g,b;      // Color.
  int count=0;
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
    y=  height - h;
    dy=  -random( 0.1, 0.9 );
    legs=  int( random(1, 10.9) );
  }
  //// METHODS:  move & show ////
  void move() {
    x += dx;
    y += dy;
    if (y>height) { 
      bottom();
      count++;
    }
    else if (y<surface) { 
      dy=  -3 * dy;        // Descend fast!
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
    foot=  dy>=0 ? 0 : (y%47 > 23) ? 5 : -5;
    strokeWeight(3);
    for (int i=0; i<legs; i++) {
      line( legX, y+h/2, legX+foot, 20+y+h/2 );
      legX += w / (legs-1);
    }
      strokeWeight(3);
    fill(200,200,0);
    text( name, x-w/2, y-10+h/2 );
    fill(0);
    text( legs, x+2-w/2, y+h/2 );
    fill(255);
    if (count>0) text( count, x, y+h/2 );
  }
  //// Return true if near
  boolean hit( float xx, float yy ) {
    return dist( xx,yy, x,y ) < h;
  }
}

