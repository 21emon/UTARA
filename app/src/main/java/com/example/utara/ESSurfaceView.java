package com.example.utara;

import android.content.Context;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * A view container where OpenGL ES graphics can be drawn on screen. This view
 * can also be used to capture touch events, such as a user interacting with
 * drawn objects.
 */
public class ESSurfaceView extends GLSurfaceView {
	private final ESRender esRender;

	/*
	 * public float initx_stick = 425; public float inity_stick = 267; public
	 * Point _touchingPoint = new Point(425,267); public Point _pointerPosition
	 * = new Point(220,150);
	 */
	
	public float initx_stick = 1013;
	public float inity_stick = 500;
	public Point _touchingPoint = new Point(1013, 500);
	public Point _pointerPosition = new Point(220, 150);

	public ESSurfaceView(Context context) {
		super(context);

		// Set the Renderer for drawing on the GLSurfaceView
		esRender = new ESRender(context);
		setRenderer(esRender);

		// To enable keypad
		this.setFocusable(true);
		this.requestFocus();

		// To enable touch mode
		this.setFocusableInTouchMode(true);
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent v) {

		
		
		switch (v.getAction()) {
		case MotionEvent.ACTION_DOWN:update(v);
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_MOVE:
		}

		return true; 
	}
	
	public void update(MotionEvent event) {
		_touchingPoint.x = (int) event.getX();
		_touchingPoint.y = (int) event.getY();
		
		int thisTurn = esRender.getIndexTurn();
		
		esRender.setX_touch(_touchingPoint.x);
		esRender.setX_touch(_touchingPoint.y);
		
		if(esRender.getMenang(thisTurn) == true) {
			if(_touchingPoint.y < 463 && _touchingPoint.y > 392) {
				if(_touchingPoint.x < 718 && _touchingPoint.x > 645) {
					System.exit(0);
				} else if(_touchingPoint.x < 803 && _touchingPoint.x > 730) {
					esRender.setDiceMode(0);
					esRender.setIndexTurn(0);
					for(int i = 0; i < 2; i++) {
						esRender.setMenang(false);
						esRender.setXPlayer(i, 0);
						esRender.setYPlayer(i, 0);
						esRender.setXScale(i, 40);
						esRender.setPosisi(i, 1);
						esRender.setMaju(i, 0);
						esRender.setStepMode(i, 0);
						esRender.resetDice();
					}
				}
			}
		}
		
		if(_touchingPoint.x < 288 && _touchingPoint.x > 57 && esRender.getMenang(thisTurn) == false) {
			if(_touchingPoint.y < 308 && _touchingPoint.y > 263) {
				esRender.setPetunjuk(true);
			} else if(_touchingPoint.y < 370 && _touchingPoint.y > 325) {
				esRender.setRestart(true);
			}
		}
		
		if(esRender.getPetunjuk() == false && esRender.getRestart() == false) {
			if((esRender.getDiceMode() == 0 || esRender.getDiceMode() == 1) && esRender.getMenang(thisTurn) == false) {
				if(_touchingPoint.x < 1038 && _touchingPoint.x > 840 && _touchingPoint.y < 526 && _touchingPoint.y > 289) {
					
					if(esRender.getDiceMode() == 0) {
						esRender.setDiceMode(1);
					} else {
						esRender.setDiceStop(true);
					}
				}
			} else if(esRender.getDiceMode() == 2 && esRender.getMaju() == 0 && esRender.getMenang(thisTurn) == false) {
				if(Math.sqrt(Math.pow(_touchingPoint.x - 138, 2) + Math.pow(_touchingPoint.y - 468, 2)) < 70) {
					esRender.setPopup(false);
					esRender.setDiceMode(0);
					if(esRender.getIndexTurn() == 0) {
						esRender.setIndexTurn(1);
					} else {
						esRender.setIndexTurn(0);
					}
				}
			}
		} else {
			if(_touchingPoint.y < 435 && _touchingPoint.y > 328) {
				if(_touchingPoint.x < 766 && _touchingPoint.x > 563) {
					esRender.setRestart(false);
					esRender.setPetunjuk(false);
				} else if(_touchingPoint.x < 519 && _touchingPoint.x > 316) {
					if(esRender.getRestart() == true) {
						esRender.setDiceMode(0);
						esRender.setIndexTurn(0);
						for(int i = 0; i < 2; i++) {
							esRender.setMenang(false);
							esRender.setXPlayer(i, 0);
							esRender.setYPlayer(i, 0);
							esRender.setXScale(i, 40);
							esRender.setPosisi(i, 1);
							esRender.setMaju(i, 0);
							esRender.setStepMode(i, 0);
							esRender.resetDice();
						}
						esRender.setRestart(false);
					} else {
						esRender.setPetunjuk(false);
						System.exit(0);
					}
				}
			}
		}
	}

	// Key-up event handler
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return true; // Event handled
	}
}
