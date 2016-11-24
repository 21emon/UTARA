package com.example.utara;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;

public class ESRender implements Renderer {

	private ESText glText;
	private PrimitivesObject primobbg;
	private PrimitivesObject board;
	
	private PrimitivesObject mainMenu;
	private PrimitivesObject restartMenu;
	private PrimitivesObject primob1;
	private PrimitivesObject primob3;
	private PrimitivesObject[] primob = new PrimitivesObject[13];
	
	private int counter = 0;
	private int player = 2;
	private PrimitivesObject[] primobflappy = new PrimitivesObject[player];
	private int[] letak_peristiwa = {4, 11, 14, 17, 21, 22, 26, 32, 34, 41, 54, 60, 62};
	private int[] letak_tangga = {13, 18, 23, 38, 47};
	private int[] letak_ular = {12, 28, 44, 59, 63};
	
	private boolean ular = false;
	private boolean tangga = false;
	
	private PrimitivesObject[] menangPopup = new PrimitivesObject[player];
	private PrimitivesObject[] konfirmasi = new PrimitivesObject[2];
	private kaki_meja dice;
	private int diceMode = 0;
	private int[] mataDadu = {1, 2, 3, 4, 5, 6};
	private PrimitivesObject[] dadu = new PrimitivesObject[mataDadu.length];
	private float stepXDice = 10.0f;
	private float stepYDice = 10.0f;
	private float xDice = 936.0f;
	private float yDice = 150.0f;
	private float xRotate = 0;
	private float yRotate = 0;
	
	private int prevIdTurn =0;
	private int indexTurn = 0;
	private int[] posisi = new int[player];
	private float[] xPlayer = new float[player];
	private float[] yPlayer = new float[player];
	private float[] xScale = new float[player];
	private float[] stepMode = new float[player];
	int[] maju = new int[player];
	int majuTemp = 0;
	
	private int[] numIDFrame = new int [player];
	private boolean[] menang = {false, false};
	
	boolean diceStop = false;
	boolean mundur = false;
	boolean popup = false;
	boolean restart = false;
	boolean petunjuk = false;
	
	private float xStep = 2.0f;
	private float yStep = 2.0f;
	
	Context context;

	int xMin, xMax, yMin, yMax;
	private int mywidth = 0;
	private int myheight = 0;
	boolean get_size_screen=true;
	int[] textures_indek = new int[1];
	
	float black[] = new float[] { 0.8f, 0.8f, 0.8f, 0.2f };
	float yellow[] = new float[] { 1.0f, 1.0f, 0.0f, 1.0f  };
	float cyan[] = new float[] { 0.0f, 1.0f, 1.0f, 1.0f  };
	float white[] = new float[] { 1.0f, 1.0f, 1.0f, 1.0f  };
	float diffuseMaterial[] = new float[] { 0.73f, 0.13f, 0.86f, 1.0f }; // set cahaya warna ungu
	float diffuseMaterial2[] = new float[] { 0.5f, 0.5f, 0.5f, 1.0f }; // set cahaya warna ungu
	float lightAmbient[] = new float[] { 0.2f, 0.3f, 0.6f, 1.0f };
	
	float mat_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	float light_position[] = { 0.0f, 0.0f, 1.0f, 0.0f };
	float light_position2[] = { 0.0f, 0.0f, 1.0f, 0.0f };
	private float x_lebar_layar;
	private float y_tinggi_layar;
	private long fpsStartTime2;
	private float x_touch;
	private float y_touch;

	/** Constructor to set the handed over context */
	public ESRender(Context context) {
		//super();
		this.context = context;
		this.primobbg = new PrimitivesObject();
		this.board = new PrimitivesObject();
		this.mainMenu = new PrimitivesObject();
		this.restartMenu = new PrimitivesObject();
		this.primob1 = new PrimitivesObject();
		this.primob3 = new PrimitivesObject();
		for(int i = 0; i < 2; i++) {
			konfirmasi[i] = new PrimitivesObject();
		}
		for(int i = 0; i < 13; i++) {
			this.primob[i] = new PrimitivesObject();
		}
		dice = new kaki_meja(context);
		indexTurn = 0;
		
		int counter = 0;
		for(int i = 0; i < player; i++) {
			menangPopup[i] = new PrimitivesObject();
			primobflappy[i] = new PrimitivesObject();
			posisi[i] = 1;
			xPlayer[i] = 0;
			yPlayer[i] = 0;
			xScale[i] = 40;
			stepMode[i] = 0;
			maju[i] = 0;
			numIDFrame[i] = counter;
			counter += 7;
		}
		
		for(int i = 0; i < mataDadu.length; i++) {
			dadu[i] = new PrimitivesObject();
		}
	}
	

	@Override
	public void onDrawFrame(GL10 gl) {
		
		// Draw background color
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_NORMALIZE);
				
		// menangkap ukuran layar
		if(get_size_screen){
			this.x_lebar_layar = mywidth;
			this.y_tinggi_layar = myheight;
			get_size_screen=false;
		}
		
		gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_SPECULAR, black, 0);
		gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_SHININESS, black, 0);
		gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_DIFFUSE, black, 0);
		
		if(maju[indexTurn] == 0 && posisi[indexTurn] == 64) {
			stepMode[indexTurn] = 4;
			menang[indexTurn] = true;
		}
		
		//Membuat popup pesan menang
		if(menang[0] == true) {
			gl.glPushMatrix();
			    gl.glDisable(GL10.GL_LIGHTING);
			    gl.glTranslatef(240.0f, 70.0f, -62.0f);
			    gl.glScalef(600.0f, 400.0f, 0.0f);
				menangPopup[0].draw_background(gl);
				gl.glEnable(GL10.GL_LIGHTING);
			gl.glPopMatrix();
		} else if(menang[1] == true) {
			gl.glPushMatrix();
			    gl.glDisable(GL10.GL_LIGHTING);
			    gl.glTranslatef(240.0f, 70.0f, -62.0f);
			    gl.glScalef(600.0f, 400.0f, 0.0f);
				menangPopup[1].draw_background(gl);
				gl.glEnable(GL10.GL_LIGHTING);
			gl.glPopMatrix();
		}
		
		if(petunjuk == true) {
			gl.glPushMatrix();
			    gl.glDisable(GL10.GL_LIGHTING);
			    gl.glTranslatef(240.0f, 70.0f, -63.0f);
			    gl.glScalef(600.0f, 400.0f, 0.0f);
				konfirmasi[0].draw_background(gl);
				gl.glEnable(GL10.GL_LIGHTING);
			gl.glPopMatrix();
		} else if(restart == true) {
			gl.glPushMatrix();
			    gl.glDisable(GL10.GL_LIGHTING);
			    gl.glTranslatef(240.0f, 70.0f, -63.0f);
			    gl.glScalef(600.0f, 400.0f, 0.0f);
				konfirmasi[1].draw_background(gl);
				gl.glEnable(GL10.GL_LIGHTING);
			gl.glPopMatrix();
		}
		
		if(maju[indexTurn] != 0 && posisi[indexTurn] == 64 && mundur == false) {
			xPlayer[indexTurn] = 0;
			stepMode[indexTurn] = 2;
			mundur = true;
		}
		
	    //Menghitung pergerakan pemain
	    if(maju[indexTurn] != 0 && mundur == false) {
	    	
		    if(stepMode[indexTurn] == 0) {
		    	xPlayer[indexTurn] += xStep;
		    	if(xPlayer[indexTurn]%64 == 0) {
		    		maju[indexTurn]--;
		    		posisi[indexTurn]++;
		    	}
		    	if(xPlayer[indexTurn] >= 448) {
		    		stepMode[indexTurn] = 1;
		    		xScale[indexTurn] = -xScale[indexTurn];
		    		xPlayer[indexTurn] = 488.0f;
				}
		    } else if(stepMode[indexTurn] == 1) {
		    	yPlayer[indexTurn] += yStep;
		    	if(yPlayer[indexTurn]%64 == 0) {
		    		stepMode[indexTurn] = 2;
		    		maju[indexTurn]--;
		    		posisi[indexTurn]++;
		    	}
		    	
		    }  else if(stepMode[indexTurn] == 2) {
		    	xPlayer[indexTurn] -= xStep;
		    	xPlayer[indexTurn] -= 40.0f;
		    	if(xPlayer[indexTurn]%64 == 0) {
		    		maju[indexTurn]--;
		    		posisi[indexTurn]++;
		    		
		    	}
		    	if(xPlayer[indexTurn] <= 0 && posisi[indexTurn] != 64) {
		    		stepMode[indexTurn] = 3;
		    		xScale[indexTurn] = -xScale[indexTurn];
		    		xPlayer[indexTurn] = -40;
		    	}
		    	xPlayer[indexTurn] += 40;
		    } else if(stepMode[indexTurn] == 3) {
		    	xPlayer[indexTurn] = 0.0f;
		    	yPlayer[indexTurn] += yStep;
		    	if(yPlayer[indexTurn]%64 == 0) {
		    		stepMode[indexTurn] = 0;
		    		maju[indexTurn]--;
		    		posisi[indexTurn]++;
		    	}
		    }
	    } else if(maju[indexTurn] != 0 && mundur == true) {
	    	xPlayer[indexTurn] += xStep;
	    	if(xPlayer[indexTurn]%64 == 0) {
	    		maju[indexTurn]--;
	    		posisi[indexTurn]--;
	    	}
	    } else {
	    	mundur = false;
	    }
	    
	    checkUlarTangga();
	    
		//Membuat popup deskripsi
		if(diceMode == 2 && popup == true) {
			switch(posisi[indexTurn]) {
		    	case 4: 
		    		gl.glPushMatrix();
					    gl.glDisable(GL10.GL_LIGHTING);
					    gl.glTranslatef(240.0f, 31.0f, -61.0f);
					    gl.glScalef(800.0f, 490.0f, 0.0f);
						primob[0].draw_background(gl);
						gl.glEnable(GL10.GL_LIGHTING);
					gl.glPopMatrix();
					break;
				case 11: 
		    		gl.glPushMatrix();
					    gl.glDisable(GL10.GL_LIGHTING);
					    gl.glTranslatef(240.0f, 31.0f, -61.0f);
					    gl.glScalef(800.0f, 490.0f, 0.0f);
						primob[1].draw_background(gl);
						gl.glEnable(GL10.GL_LIGHTING);
					gl.glPopMatrix();
					break;
				case 14: 
		    		gl.glPushMatrix();
					    gl.glDisable(GL10.GL_LIGHTING);
					    gl.glTranslatef(240.0f, 31.0f, -61.0f);
					    gl.glScalef(800.0f, 490.0f, 0.0f);
						primob[2].draw_background(gl);
						gl.glEnable(GL10.GL_LIGHTING);
					gl.glPopMatrix();
					break;
				case 17: 
		    		gl.glPushMatrix();
					    gl.glDisable(GL10.GL_LIGHTING);
					    gl.glTranslatef(240.0f, 31.0f, -61.0f);
					    gl.glScalef(800.0f, 490.0f, 0.0f);
						primob[3].draw_background(gl);
						gl.glEnable(GL10.GL_LIGHTING);
					gl.glPopMatrix();
					break;
				case 21:
		    		gl.glPushMatrix();
					    gl.glDisable(GL10.GL_LIGHTING);
					    gl.glTranslatef(240.0f, 31.0f, -61.0f);
					    gl.glScalef(800.0f, 490.0f, 0.0f);
						primob[4].draw_background(gl);
						gl.glEnable(GL10.GL_LIGHTING);
					gl.glPopMatrix();
					break;
				case 22: 
		    		gl.glPushMatrix();
					    gl.glDisable(GL10.GL_LIGHTING);
					    gl.glTranslatef(240.0f, 31.0f, -61.0f);
					    gl.glScalef(800.0f, 490.0f, 0.0f);
						primob[5].draw_background(gl);
						gl.glEnable(GL10.GL_LIGHTING);
					gl.glPopMatrix();
					break;
				case 26: 
		    		gl.glPushMatrix();
					    gl.glDisable(GL10.GL_LIGHTING);
					    gl.glTranslatef(240.0f, 31.0f, -61.0f);
					    gl.glScalef(800.0f, 490.0f, 0.0f);
						primob[6].draw_background(gl);
						gl.glEnable(GL10.GL_LIGHTING);
					gl.glPopMatrix();
					break;
				case 32:
		    		gl.glPushMatrix();
					    gl.glDisable(GL10.GL_LIGHTING);
					    gl.glTranslatef(240.0f, 31.0f, -61.0f);
					    gl.glScalef(800.0f, 490.0f, 0.0f);
						primob[7].draw_background(gl);
						gl.glEnable(GL10.GL_LIGHTING);
					gl.glPopMatrix();
					break;
				case 35: 
		    		gl.glPushMatrix();
					    gl.glDisable(GL10.GL_LIGHTING);
					    gl.glTranslatef(240.0f, 31.0f, -61.0f);
					    gl.glScalef(800.0f, 490.0f, 0.0f);
						primob[8].draw_background(gl);
						gl.glEnable(GL10.GL_LIGHTING);
					gl.glPopMatrix();
					break;
				case 40:
		    		gl.glPushMatrix();
					    gl.glDisable(GL10.GL_LIGHTING);
					    gl.glTranslatef(240.0f, 31.0f, -61.0f);
					    gl.glScalef(800.0f, 490.0f, 0.0f);
						primob[9].draw_background(gl);
						gl.glEnable(GL10.GL_LIGHTING);
					gl.glPopMatrix();
					break;
				case 53: 
		    		gl.glPushMatrix();
					    gl.glDisable(GL10.GL_LIGHTING);
					    gl.glTranslatef(240.0f, 31.0f, -61.0f);
					    gl.glScalef(800.0f, 490.0f, 0.0f);
						primob[10].draw_background(gl);
						gl.glEnable(GL10.GL_LIGHTING);
					gl.glPopMatrix();
					break;
				case 59: 
		    		gl.glPushMatrix();
					    gl.glDisable(GL10.GL_LIGHTING);
					    gl.glTranslatef(240.0f, 31.0f, -61.0f);
					    gl.glScalef(800.0f, 490.0f, 0.0f);
						primob[11].draw_background(gl);
						gl.glEnable(GL10.GL_LIGHTING);
					gl.glPopMatrix();
					break;
				default:
		    		gl.glPushMatrix();
					    gl.glDisable(GL10.GL_LIGHTING);
					    gl.glTranslatef(240.0f, 31.0f, -61.0f);
					    gl.glScalef(800.0f, 490.0f, 0.0f);
						primob[12].draw_background(gl);
						gl.glEnable(GL10.GL_LIGHTING);
					gl.glPopMatrix();
					break;
		    }
			
		}
		
	    //Dadu
	    if(diceMode == 0 && menang[0] == false && menang[1] == false) {
    		if(prevIdTurn != indexTurn) {
    			xDice = 936.0f;
    			yDice = 150.0f;
    			xRotate = 0.0f;
        		yRotate = 0.0f;
        		prevIdTurn = indexTurn;
    		}
    	} else if(diceMode == 1) {
    		if(diceStop == false) {
	    		xRotate += stepXDice;
	    		yRotate += stepYDice;
	    		counter++;
    		} else {
    			maju[indexTurn] = (counter%6)+1;
    			majuTemp = maju[indexTurn];
	    		diceMode = 2;
    		}
    	} else if(diceMode == 2) {
    		switch(majuTemp) {
				case 1:
					gl.glPushMatrix();
					if(menang[indexTurn] == false) {
				    	gl.glDisable(GL10.GL_LIGHTING);
			    	}
							gl.glTranslatef(xDice-30, yDice-30, -30.0f);
							gl.glScalef(60.0f, 60.0f, 0.0f);
							dadu[0].draw_background(gl);
							if(menang[indexTurn] == false) {
						    	gl.glEnable(GL10.GL_LIGHTING);
					    	}
					gl.glPopMatrix();
					break;
				case 2:
					gl.glPushMatrix();
					if(menang[indexTurn] == false) {
				    	gl.glDisable(GL10.GL_LIGHTING);
			    	}
							gl.glTranslatef(xDice-30, yDice-30, -30.0f);
							gl.glScalef(60.0f, 60.0f, 0.0f);
							dadu[1].draw_background(gl);
							if(menang[indexTurn] == false) {
						    	gl.glEnable(GL10.GL_LIGHTING);
					    	}
					gl.glPopMatrix();
					break;
				case 3:
					gl.glPushMatrix();
					if(menang[indexTurn] == false) {
				    	gl.glDisable(GL10.GL_LIGHTING);
			    	}
							gl.glTranslatef(xDice-30, yDice-30, -30.0f);
							gl.glScalef(60.0f, 60.0f, 0.0f);
							dadu[2].draw_background(gl);
							if(menang[indexTurn] == false) {
						    	gl.glEnable(GL10.GL_LIGHTING);
					    	}
					gl.glPopMatrix();
					break;
				case 4:
					gl.glPushMatrix();
					if(menang[indexTurn] == false) {
				    	gl.glDisable(GL10.GL_LIGHTING);
			    	}
							gl.glTranslatef(xDice-30, yDice-30, -30.0f);
							gl.glScalef(60.0f, 60.0f, 0.0f);
							dadu[3].draw_background(gl);
							if(menang[indexTurn] == false) {
						    	gl.glEnable(GL10.GL_LIGHTING);
					    	}
					gl.glPopMatrix();
					break;
				case 5:
					gl.glPushMatrix();
					if(menang[indexTurn] == false) {
				    	gl.glDisable(GL10.GL_LIGHTING);
			    	}
							gl.glTranslatef(xDice-30, yDice-30, -30.0f);
							gl.glScalef(60.0f, 60.0f, 0.0f);
							dadu[4].draw_background(gl);
							if(menang[indexTurn] == false) {
						    	gl.glEnable(GL10.GL_LIGHTING);
					    	}
					gl.glPopMatrix();
					break;
				default:
					gl.glPushMatrix();
					if(menang[indexTurn] == false) {
				    	gl.glDisable(GL10.GL_LIGHTING);
			    	}
							gl.glTranslatef(xDice-30, yDice-30, -30.0f);
							gl.glScalef(60.0f, 60.0f, 0.0f);
							dadu[5].draw_background(gl);
							if(menang[indexTurn] == false) {
						    	gl.glEnable(GL10.GL_LIGHTING);
					    	}
					gl.glPopMatrix();
					break;
			}
    		diceStop = false;
    	}
	    
	    if(diceMode != 2) {
	    	gl.glPushMatrix();
				gl.glDisable(GL10.GL_LIGHTING);
					gl.glTranslatef(xDice, yDice, -30.0f);
					gl.glRotatef(yRotate, 0.0f, 1.0f, 0.0f);
					gl.glRotatef(xRotate, 1.0f, 0.0f, 0.0f);
					gl.glScalef(30.0f, 30.0f, 30.0f);
					dice.draw(gl);
				gl.glEnable(GL10.GL_LIGHTING);
			gl.glPopMatrix();
	    }
	    
		//Menghitung index frame karakter player
    	long fpsElapsed2 = System.currentTimeMillis() - fpsStartTime2;
    	if (fpsElapsed2 > 0.09 * 1000) { // every 5 seconds
    		fpsStartTime2 = System.currentTimeMillis();
    		
    		if(maju[indexTurn] != 0) {
  				numIDFrame[indexTurn]++;
  			}
  			
  			if(numIDFrame[indexTurn] == (indexTurn+1)*7){
  				numIDFrame[indexTurn] = indexTurn*7;  				
  			}
    	}
    	
    	//Menentukan popup
		if(diceMode == 2 && maju[indexTurn] == 0) {
			for(int i = 0; i < letak_peristiwa.length; i++) {
				if(posisi[indexTurn] == letak_peristiwa[i]) {
					popup = true;
				}
			}
		}
		
		//Membuat background
	    gl.glPushMatrix(); 
			if(diceMode != 2) {
				gl.glDisable(GL10.GL_LIGHTING);
			}
				gl.glScalef(x_lebar_layar, y_tinggi_layar, 1.0f);
				primobbg.draw_background(gl);
			if(diceMode != 2) {
			    gl.glEnable(GL10.GL_LIGHTING);
			}
		gl.glPopMatrix();
			    
		//Membuat papan
		gl.glPushMatrix();
		  		if(popup == false && menang[0] == false && menang[1] == false) {
			  		gl.glDisable(GL10.GL_LIGHTING);
			  	}
			  		gl.glTranslatef(265.0f, 5.0f, 0.0f);
			  		gl.glScalef(544.0f, 544.0f, 0.0f);
			  	    board.draw_background(gl);
			  	if(popup == false && menang[0] == false && menang[1] == false) {
			  		gl.glEnable(GL10.GL_LIGHTING);
			  	}
		gl.glPopMatrix();

		//Membuat tombol main menu
	    gl.glPushMatrix();
	    	if(menang[indexTurn] == false) {
		    	gl.glDisable(GL10.GL_LIGHTING);
	    	}
				gl.glTranslatef(50.0f, 234.0f, -61.0f);
				gl.glScalef(178.0f, 62.0f, 0.0f);
				mainMenu.draw_background(gl);
			if(menang[indexTurn] == false) {
				gl.glEnable(GL10.GL_LIGHTING);
			}
		gl.glPopMatrix();
		
		//Membuat tombol restart
	    gl.glPushMatrix();
	    	if(menang[indexTurn] == false) {
	    		gl.glDisable(GL10.GL_LIGHTING);
	    	}
			    gl.glTranslatef(50.0f, 172.0f, -61.0f);
			    gl.glScalef(178.0f, 62.0f, 0.0f);
				restartMenu.draw_background(gl);
			if(menang[indexTurn] == false) {
				gl.glEnable(GL10.GL_LIGHTING);
			}
		gl.glPopMatrix();
		
		//Membuat tombol end turn
	    gl.glPushMatrix();
	    	if(diceMode == 2 && maju[indexTurn] == 0 && menang[indexTurn] == false) {
	    		gl.glDisable(GL10.GL_LIGHTING);
	    	}
		    gl.glTranslatef(64.0f, 10.0f, -61.0f);
		    gl.glScalef(150.0f, 150.0f, 0.0f);
			primob1.draw_background(gl);
			if(diceMode == 2 && maju[indexTurn] == 0 && menang[indexTurn] == false) {
				gl.glEnable(GL10.GL_LIGHTING);
			}
		gl.glPopMatrix();
		
		//Membuat player
		for(int i = 0; i < player; i++) {
    		gl.glPushMatrix(); 
	    		if(menang[indexTurn] == false) {
			    	gl.glDisable(GL10.GL_LIGHTING);
		    	}
  				gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
  				gl.glEnable(GL10.GL_BLEND);
  				gl.glTranslatef(xPlayer[i]+290, yPlayer[i]+30, -2.0f);
  				gl.glScalef(xScale[i], 40.0f, 1.0f);
  				primobflappy[i].loadBallTexture2(gl, context, numIDFrame[i]);
  				primobflappy[i].draw_background(gl);
  				
  				gl.glDisable(GL10.GL_BLEND);
  				if(menang[indexTurn] == false) {
			    	gl.glEnable(GL10.GL_LIGHTING);
		    	}
  			gl.glPopMatrix();
    	}	
		
		
	
		moveWithCollisionDetection(this);

		if(popup == false) {
		// render text
		gl.glPushMatrix();
			gl.glDisable(GL10.GL_LIGHTING);
				
				gl.glEnable(GL10.GL_TEXTURE_2D); // Enable Texture Mapping
				gl.glEnable(GL10.GL_BLEND); // Enable Alpha Blend
				gl.glDisable(GL10.GL_DEPTH_TEST); // Turn depth testing off (NEW)
				gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
				
//				glText.begin(1.0f, 0.0f, 0.0f, 1.0f);
//				glText.draw("Posisi Touch : (" + x_touch + "," + y_touch + ")", 50, 360);	
//				glText.end();
//				
//				gl.glLoadIdentity();
//				
//				glText.begin(1.0f, 0.0f, 0.0f, 1.0f);
//				glText.draw("Ukuran Layar : (" + x_lebar_layar + "," + y_tinggi_layar + ")", 50, 340);
//				glText.end();
//				
//				gl.glLoadIdentity();
				
				glText.begin(1.0f, 0.0f, 0.0f, 1.0f);
				glText.draw((posisi[0]+1944) + "               " + (posisi[1]+1944), 927, 460);	
				glText.end();
				
				gl.glLoadIdentity();
				
			gl.glEnable(GL10.GL_LIGHTING);
		gl.glPopMatrix();
		}
	}
	
	public void checkUlarTangga() {
		if(ular == false && tangga == false && maju[indexTurn] == 0) {
			for(int i = 0; i < letak_ular.length; i ++) {
				if(posisi[indexTurn] == letak_ular[i]) {
					ular = true;
				}
			}
			for(int i = 0; i < letak_tangga.length; i ++) {
				if(posisi[indexTurn] == letak_tangga[i]) {
					tangga = true;
				}
			}
		} else if(ular == true) {
			switch(posisi[indexTurn]) {
				case 12: 
					xScale[indexTurn] = -xScale[indexTurn];
					xPlayer[indexTurn] = 320;
					yPlayer[indexTurn] = 0;
					posisi[indexTurn] = 6;
					stepMode[indexTurn] = 0;
					break;
				case 28: 
					xScale[indexTurn] = -xScale[indexTurn];
					xPlayer[indexTurn] = 448;
					yPlayer[indexTurn] = 128;
					posisi[indexTurn] = 24;
					stepMode[indexTurn] = 0;
					break;
				case 44:
					xPlayer[indexTurn] = 232;
					yPlayer[indexTurn] = 192;
					posisi[indexTurn] = 29;
					break;
				case 59:
					xScale[indexTurn] = -xScale[indexTurn];
					xPlayer[indexTurn] = 256;
					yPlayer[indexTurn] = 384;
					posisi[indexTurn] = 53;
					stepMode[indexTurn] = 0;
					break;
				case 63:
					xScale[indexTurn] = -xScale[indexTurn];
					xPlayer[indexTurn] = 128;
					yPlayer[indexTurn] = 128;
					posisi[indexTurn] = 19;
					stepMode[indexTurn] = 0;
					break;
			}
			ular = false;
		} else if(tangga == true) {
//			private int[] letak_tangga = {13, 18, 23, 38, 47};
			switch(posisi[indexTurn]) {
				case 13: 
					xScale[indexTurn] = -xScale[indexTurn];
					xPlayer[indexTurn] = 192;
					yPlayer[indexTurn] = 128;
					posisi[indexTurn] = 20;
					stepMode[indexTurn] = 0;
					break;
				case 18: 
					xPlayer[indexTurn] = 192;
					yPlayer[indexTurn] = 384;
					posisi[indexTurn] = 52;
					break;
				case 23:
					xPlayer[indexTurn] = 256;
					yPlayer[indexTurn] = 256;
					posisi[indexTurn] = 37;
					break;
				case 38:
					xScale[indexTurn] = -xScale[indexTurn];
					xPlayer[indexTurn] = 488;
					yPlayer[indexTurn] = 448;
					posisi[indexTurn] = 53;
					stepMode[indexTurn] = 2;
					break;
				case 47:
					xScale[indexTurn] = -xScale[indexTurn];
					xPlayer[indexTurn] = 0;
					yPlayer[indexTurn] = 384;
					posisi[indexTurn] = 49;
					stepMode[indexTurn] = 0;
					break;
			}
			tangga = false;
		}
	}
	
	public Context getContext() {
		return context;
	}
	
	public void resetDice() {
		this.xRotate = 0;
		this.yRotate = 0;
	}
	
	public void setStepMode(int index, int param) {
		this.stepMode[index] = param;
	}
	
	public void setMaju(int index, int param) {
		this.maju[indexTurn] = param;
	}
	
	public void setMenang(boolean menang) {
		for(int i = 0; i < player; i++) {
			this.menang[i] = menang;
		}
	}
	
	public boolean getMenang(int param) {
		return menang[param];
	}
	
	public void setMenang(int index, boolean param) {
		this.menang[index] = param;
	}
	
	public void setXPlayer(int index, float param) {
		this.xPlayer[index] = param;
	}
	
	public void setYPlayer(int index, float param) {
		this.yPlayer[index] = param;
	}
	
	public void setXScale(int index, float param) {
		this.xScale[index] = param;
	}
	
	public void setPosisi(int index, int param) {
		this.posisi[index] = param;
	}
	
	public int getMaju() {
		return maju[indexTurn];
	}
	
	public void setPopup(boolean popup) {
		this.popup = popup;
	}
	
	public int getIndexTurn() {
		return indexTurn;
	}
	
	public void setIndexTurn(int indexTurn) {
		this.indexTurn = indexTurn;
	}
	
	public int getDiceMode() {
		return diceMode;
	}
	
	public void setDiceMode(int diceMode) {
		this.diceMode = diceMode;
	}
	
	public float getX_lebar_layar() {
		return x_lebar_layar;
	}


	public void setX_lebar_layar(float x_lebar_layar) {
		this.x_lebar_layar = x_lebar_layar;
	}


	public float getY_tinggi_layar() {
		return y_tinggi_layar;
	}


	public void setY_tinggi_layar(float y_tinggi_layar) {
		this.y_tinggi_layar = y_tinggi_layar;
	}

	public int getMywidth() {
		return mywidth;
	}


	public void setMywidth(int mywidth) {
		this.mywidth = mywidth;
	}


	public int getMyheight() {
		return myheight;
	}


	public void setMyheight(int myheight) {
		this.myheight = myheight;
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		//gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f); // Set color's clear-value to
		//gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f); // Set color's clear-value to
													// black
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Set color's clear-value to
		
		gl.glClearDepthf(1.0f); // Set depth's clear-value to farthest
		gl.glEnable(GL10.GL_DEPTH_TEST); // Enables depth-buffer for hidden
		// surface removal
		gl.glDepthFunc(GL10.GL_LEQUAL); // The type of depth testing to do
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST); // nice
		// perspective
		// view
		gl.glShadeModel(GL10.GL_SMOOTH); // Enable smooth shading of color
		gl.glDisable(GL10.GL_DITHER); // Disable dithering for better
		// performance
		gl.glEnable(GL10.GL_LIGHTING);	
    	gl.glEnable(GL10.GL_LIGHT0);
    	//gl.glEnable(GL10.GL_LIGHT1);
    	

		// Create the GLText
		glText = new ESText(gl, context.getAssets());

		// Load the font from file (set size + padding), creates the texture
		// NOTE: after a successful call to this the font is ready for
		// rendering!
		glText.load("Roboto-Regular.ttf", 14, 2, 2); // Create Font (Height: 14
														// Pixels / X+Y Padding
														// 2 Pixels)

		// gl.glDisable(GL10.GL_DITHER); // Disable dithering for better
		// performance
		
		 // Setup Blending (NEW)
	 	gl.glColor4f(1.0f, 1.0f, 1.0f, 0.5f); // Full brightness, 50% alpha (NEW)
	 	gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE); // Select blending function (NEW)

		// Setup Texture, each time the surface is created (NEW)
	 	primob3.loadBallTexture(gl, context,1);
	 	primob1.loadBallTexture(gl, context,4);
	 	restartMenu.loadBallTexture(gl, context, 0);
	 	mainMenu.loadBallTexture(gl, context, 1);
		primobbg.loadBallTexture(gl, context, 19);
		board.loadBallTexture(gl, context, 20);
		for(int i = 0; i < 2; i++) {
			konfirmasi[i].loadBallTexture(gl, context, i+29);
		}
		for(int i = 0; i < player; i++) {
			primobflappy[i].loadBallTexture(gl, context, i*7);
			menangPopup[i].loadBallTexture(gl, context, i+27);
		}
		for(int i = 0; i < 6; i++) {
			dadu[i].loadBallTexture(gl, context, i+21);
		}
		for(int i =0; i < 13; i++) {
			primob[i].loadBallTexture(gl, context, i+6);
		}
		dice.loadTexture(gl, context);
		gl.glEnable(GL10.GL_TEXTURE_2D); // Enable texture (NEW)

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		mywidth = width;
		myheight = height;

		gl.glViewport(0, 0, width, height);

		// Setup orthographic projection
		gl.glMatrixMode(GL10.GL_PROJECTION); // Activate Projection Matrix
		gl.glLoadIdentity(); // Load Identity Matrix
		gl.glOrthof( // Set Ortho Projection (Left,Right,Bottom,Top,Front,Back)
				0, width, 0, height, 500.0f, -500.0f);

		// Save width and height
		// this.width = width; // Save Current Width
		// this.height = height; // Save Current Height

		gl.glMatrixMode(GL10.GL_MODELVIEW); // Select model-view matrix
		gl.glLoadIdentity(); // Reset
		
		/*float[] vertices_quad = { // Vertices for the square
				-1.0f, -1.0f, 0.0f, // 0. left-bottom
				1.0f, -1.0f, 0.0f, // 1. right-bottom
				-1.0f, 1.0f, 0.0f, // 2. left-top
				1.0f, 1.0f, 0.0f // 3. right-top
		};
		
		// set background
		Bitmap bitmap = BitmapFactory.decodeStream(context.getResources()
				.openRawResource(R.drawable.splash));
		
		float tex_quad[] = {
		-100, -100, 0,
		 100, -100, 0,
		 -100,  100, 0,
		100,  100, 0
		};    
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(tex_quad));
		gl.glTexCoordPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_quad)); // 5
		
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		//gl.glVertexPointer(2, GL_FLOAT, sizeof(GLfloat)*4, &tex_quad[0]);
		//glTexCoordPointer(2, GL_FLOAT, sizeof(GLfloat)*4, &tex_quad[2]);

		gl.glDisable(GL10.GL_DEPTH_TEST);
		gl.glDepthMask(false);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		//gl.glGenTextures(1, textures_indek, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
		

		//glDrawArrays(GL_TRIANGLE_FAN, 0, 4);

		//glEnable(GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthMask(true);
		gl.glDisable( GL10.GL_TEXTURE_2D );             // Disable Texture Mapping
		*/

	}
	
	public boolean getDiceStop() {
		return this.diceStop;
	}
	
	public void setDiceStop(boolean diceStop) {
		this.diceStop = diceStop;
	}
	
	public boolean getRestart() {
		return restart;
	}
	
	public boolean getPetunjuk() {
		return petunjuk;
	}
	
	public void setRestart(boolean restart) {
		this.restart = restart;
	}
	
	public void setPetunjuk(boolean petunjuk) {
		this.petunjuk = petunjuk;
	}

	public float getxMax() {
		return xMax;
	}

	public void setxMax(int xmax) {
		xMax = xmax;
	}

	public float getxMin() {
		return xMin;
	}

	public void setxMin(int xmin) {
		xMin = xmin;
	}

	public float getyMax() {
		return yMax;
	}

	public void setyMax(int ymax) {
		yMax = ymax;
	}

	public float getyMin() {
		return yMin;
	}

	public void setyMin(int ymin) {
		yMin = ymin;
	}
	
	public float getX_touch() {
		return x_touch;
	}
	
	
	public void setX_touch(float x_touch) {
		this.x_touch = x_touch;
	}
	
	
	public float getY_touch() {
		return y_touch;
	}
	
	
	public void setY_touch(float y_touch) {
		this.y_touch = y_touch;
	}

	public void moveWithCollisionDetection(ESRender esRender) {
		if (xDice + 30 > 1038) {
			stepXDice = -stepXDice;
			xDice = 1038 - 30;
		} else if (xDice - 30 < 840) {
			stepXDice = -stepXDice;
			xDice = 840 + 30;
		}
		if (yDice + 30 > 262) {
			stepYDice = -stepYDice;
			yDice = 262 - 30;
		} else if (yDice - 30 < 26) {
			stepYDice = -stepYDice;
			yDice = 26 + 30;
		}
	}

	public void set(int x, int y, int width, int height) {
		xMin = x;
		// xMax = x + width - 1;
		xMax = x + width;
		yMin = y;
		// yMax = y + height - 1;
		yMax = y + height;
	}


	public void setX_touch(int x) {
		// TODO Auto-generated method stub
		
	}
}