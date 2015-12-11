//// Teng Lin, Project 9, 12/10/2015

int many=5;
Squid school[]=  new Squid[many];
String names[]=  { "Arl", "Ben", "Car", "Dom", "Edd" };
float spacing;


int lin=5;
Boat ships[]= new Boat[lin];
String boatnames[]=  { "A", "B", "C", "D", "E" };

//Boat bounty=  new Boat();

float surface;
float moonX=0, moonY=100;
int score=0;

//// SETUP:  size & reset.
void setup() {
  size( 800, 600 );
  spacing=  width/(many+1);
  reset();
  help();
 
  for ( int i=0; i<lin; i++){
    ships[i]= new Boat(int (random(0,width)), boatnames[i]);
  }
  
  
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
  //bounty.name=  "Bounty";
}

void help() {
  background(255);
  textSize(15);
  text( "Pressed 0 key to send 1st squid to the bottom.", 50, 100);
  text( "Pressed 1 key to send 2nd squid to the bottom.", 50, 120);
  text( "Pressed 2 key to send 3rd squid to the bottom.", 50, 140);
  text( "Pressed 3 key to send 4th squid to the bottom.", 50, 160);
  text( "Pressed 3 key to send 5th squid to the bottom.", 50, 180);
  text( "Pressed h key to send highest squid to the bottom.", 50, 200);
  text( "Pressed b key to send all squids to the bottom.", 50, 220);
  text( "Pressed t key to send all squids to the surface.", 50, 240);
  text( "Pressed any capital key to show fish report and boat report.", 50,260);
  text( "Pressed any key to exit help." , 50,500); 
  
}




//// NEXT FRAME:  scene, action
void draw() {
  scene();
  show();
  if (key >= 'A' && key <= 'Z') {
    boatReport( 50, ships, ships.length );
    fishReport( surface+50, school, school.length);
  }
  if (key == 'v') {
    help();
  }
  else action();
  messages();

}
void messages() {
  fill(0);
  textSize( 20 );
  text( "Fishing", width/3, 20 );
  textSize(12);
  text( "Hold B key to show all boats and fish", width/3, 40 );
  text( "Pressed v key for help", width/3, 60 );
  text( "Teng Lin:  Project 9", 10, height-10 );
  if (score>0) text( "SCORE:  "+score, width*3/4, 20 );
  if (score>900) {
    if (key == 'q') score=0;
    text( "Maximum score.\nQUITTING NOW\nPress the 'q' key to continue", width/2, 60 );
    if (score>10000) { exit(); }
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
  // Move squids & boats.
  for (int i=0; i<many; i++ ) {
    school[i].move();
  }
  for (int i=0; i<lin; i++ ) {
    ships[i].move();
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
  
  for (int i=0; i<lin; i++ ) {
    ships[i].show();

  }
}

//// SUMMARIES:  List all objects in the array.
// Display the properties of each object in the array.
void boatReport( float top, Boat[] b, int many ) {
  fill(255,200,200);
  rect( 50,top, width-100, 50 + 20*many );
  float x=70, y=top+20;
  // Labels.
  fill(150,0,0);
  text( "BOAT", x+20, y );
  text( "cargo", x+70, y );
  text( "x", x+105, y );
  text( "dx", x+205, y );
  fill(0);
  //
   for (int i=0; i<many; i++) {
    y += 15;    // Next line.
    text( i, x, y );
    text( b[i].name, x+20, y );
    text( b[i].cargo, x+70, y );
    text( b[i].x, x+100, y );
    text( b[i].dx, x+200, y );
  }
}
void fishReport( float top, Squid[] a, int many ) {
  fill(255,255,200);
  rect( 50,top, width-100, 50 + 20*many );
  float x=70, y=top+20;
  // Labels.
  fill(150,0,0);
  text( "FISH", x+20, y );
  text( "legs", x+70, y );
  text( "x", x+105, y );
  text( "y", x+205, y );
  text( "dy", x+305, y );
  fill(0);
  for (int i=0; i<many; i++) {
    y += 15;    // Next line.
    text( i, x, y );
    text( a[i].name, x+20, y );
    text( a[i].legs, x+70, y );
    text( a[i].x, x+100, y );
    text( a[i].y, x+200, y );
    text( a[i].dy, x+300, y );
  }
}
    

//// EVENT HANDLERS:  keys, clicks ////
void keyPressed() {
  if (key == 'r') reset();
  // Send a squid to the bottom!
  if (key == '0') school[0].bottom(); 
  if (key == '1') school[1].bottom(); 
  if (key == '2') school[2].bottom(); 
  if (key == '3') school[3].bottom();
  if (key == '4') school[4].bottom();
  //// Send highest to bottom.
  if (key == 'h') {
    int k=0;
    for (int i=1; i<many; i++ ) {
      if (school[i].y < school[k].y) k=i;           // k is index of highert.
    }
    school[k].bottom();     
  }
  
  
  // Cheat codes:
  //// Send all to top or bottom.
  if (key == 'b') {
    for (int k=0; k<many; k++ ) {
      school[k].bottom();     
    }
  }
  if (key == 't') {
    for (int k=0; k<many; k++ ) {
      school[k].y=  surface+10;
      school[k].dy=  -0.1  ;
    }
  }
  
  
  
  
  
  
  
  
  
  
}






void sortSquidLeg( int a[], int many) {
      //shrink the array.
      for (int m=many; m>1; m--) {
      //move biggest to end.
      //set k such that a[k] is biggest
      int k=0;
      for( int j=1; j<m; j++){
        if (a[j]>a[k]) 
        k=j;
     }
    // k now points to the biggest
    swap( a, m-1, k);
    }
   
}



void swap(int[] a, int j, int k){
  int tmp;
  tmp=  a[j];
  a[j]=  a[k];
  a[k]=  tmp;
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


class Boat {
  String name="";
  float y=surface, dx=5;
  int x=0;
  int cargo=0, caught=0;
  
  
  Boat( int tempx, String temps) {
    x=tempx;
    name=temps;
  }
  void move() {
    //// Fish before move:  check each squid.
    int caught=0;
    for (int i=0; i<many; i++ ) {
    if (school[i].hit( ships[i].x, surface )) {
        caught += school[i].legs;
      }
     
    }
    cargo += caught;    
    //// Now, move the boat.
    x += dx;
    if (caught>0) x += 2*dx;      //  Jump after catch.
    if (x<0) {
      score += cargo;            // Add cargo to global score.
      cargo=0;
      dx = random( 1, 5 );      // Variable boat speed.
    }
    if (x>width)  {
      dx = -random( 0.5, 3 );    // Slower return.
    }
  }
  //// Draw the boat.
  void show() {
    // Boat.
    fill(255,0,0);
    noStroke();
    rect( x, surface-20, 50, 20 );
    if (dx>0)   triangle( x+50,surface, x+50,surface-20, x+70,surface-20 );
    else        triangle( x,surface, x,surface-20, x-20,surface-20 );
    rect( x+12, surface-30, 25, 10 );      // Cabin.
    fill(0);
    rect( x+20, surface-40, 10, 10 );      // Smokestack.
    // Display name & cargo.
    fill(255);
    text( name, x+5, surface-10 );
    fill(0);
    if (cargo>0) text( cargo, x+20, surface );
    // Smoke
    fill( 50,50,50, 200 );
    ellipse( x +20 -10*dx, surface-50, 20, 20 );
    ellipse( x +20 -20*dx, surface-60, 15, 10 );
    ellipse( x +20 -30*dx, surface-70, 8, 5 );
  }    
}
