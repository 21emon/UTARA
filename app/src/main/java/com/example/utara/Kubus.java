package com.example.utara;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import com.example.utara.R;

/*
 * A photo cube with 6 pictures (textures) on its 6 faces.
 */
public class Kubus {
	private FloatBuffer vertexBuffer; // Vertex Buffer
	private FloatBuffer texBuffer; // Texture Coords Buffer
	private int numFaces = 6;
	private int[] imageFileIDs = { // Image file IDs
	R.drawable.dice4satu, //index 8
	R.drawable.dice4dua, //index 9
	R.drawable.dice4tiga,// index 10
	R.drawable.dice4empat, //index 11
	R.drawable.dice4lima, // index 12
	R.drawable.dice4enam // index 13
	};
	private int[] textureIDs = new int[numFaces];
	private Bitmap[] bitmap = new Bitmap[numFaces];
	private float cubeHalfSize = 1.0f;

	// Constructor - Set up the vertex buffer
	public Kubus(Context context) {
		// Allocate vertex buffer. An float has 4 bytes
		ByteBuffer vbb = ByteBuffer.allocateDirect(12 * 4 * numFaces);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		
		
		
		// Read images. Find the aspect ratio and adjust the vertices
		// accordingly.
		for (int face = 0; face < numFaces; face++) {
			bitmap[face] = BitmapFactory.decodeStream(context.getResources()
					.openRawResource(imageFileIDs[face]));
			int imgWidth = bitmap[face].getWidth();
			int imgHeight = bitmap[face].getHeight();
			float faceWidth = 2.0f;
			float faceHeight = 2.0f;
			
			// Adjust for aspect ratio
			if (imgWidth > imgHeight) {
				faceHeight = faceHeight * imgHeight / imgWidth;
			} else {
				faceWidth = faceWidth * imgWidth / imgHeight;
			}
			float faceLeft = -faceWidth / 2;
			float faceRight = -faceLeft;
			float faceTop = faceHeight / 2;
			float faceBottom = -faceTop;
			
			// Define the vertices for this face
//			float[] vertices = { faceLeft, faceBottom, 0.0f, // 0.
//																// left-bottom-front
//					faceRight, faceBottom, 0.0f, // 1. right-bottom-front
//					faceLeft, faceTop, 0.0f, // 2. left-top-front
//					faceRight, faceTop, 0.0f, // 3. right-top-front
//			};
			float[] vertices = { // Vertices for a face
					-1.0f, -1.0f, 0.0f, // 0. left-bottom-front
							1.0f, -1.0f, 0.0f, // 1. right-bottom-front
							-1.0f, 1.0f, 0.0f, // 2. left-top-front
							1.0f, 1.0f, 0.0f // 3. right-top-front
					};
			vertexBuffer.put(vertices); // Populate
		}
		vertexBuffer.position(0); // Rewind
		// Allocate texture buffer. An float has 4 bytes. Repeat for 6 faces.
		float[] texCoords = { 
				0.0f, 1.0f, // A. left-bottom
				1.0f, 1.0f, // B. right-bottom
				0.0f, 0.0f, // C. left-top
				1.0f, 0.0f // D. right-top
		};
		ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length * 4
				* numFaces);
		tbb.order(ByteOrder.nativeOrder());
		texBuffer = tbb.asFloatBuffer();
		for (int face = 0; face < numFaces; face++) {
			texBuffer.put(texCoords);
		}
		texBuffer.position(0); // Rewind
	}

	// Render the shape
	public void draw(GL10 gl) {
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texBuffer);
		
		// front
		gl.glPushMatrix();
		gl.glTranslatef(0f, 0f, cubeHalfSize);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[4]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();
		
		// left
		gl.glPushMatrix();
		gl.glRotatef(270.0f, 0f, 1f, 0f);
		gl.glTranslatef(0f, 0f, cubeHalfSize);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[3]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4, 4);
		gl.glPopMatrix();
		
		// back
		gl.glPushMatrix();
		gl.glRotatef(180.0f, 0f, 1f, 0f);
		gl.glTranslatef(0f, 0f, cubeHalfSize);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[1]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 8, 4);
		gl.glPopMatrix();
		
		// right
		gl.glPushMatrix();
		gl.glRotatef(90.0f, 0f, 1f, 0f);
		gl.glTranslatef(0f, 0f, cubeHalfSize);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[2]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 12, 4);
		gl.glPopMatrix();
		
		// top
		gl.glPushMatrix();
		gl.glRotatef(270.0f, 1f, 0f, 0f);
		gl.glTranslatef(0f, 0f, cubeHalfSize);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[5]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 16, 4);
		gl.glPopMatrix();
		
		// bottom
		gl.glPushMatrix();
		gl.glRotatef(90.0f, 1f, 0f, 0f);
		gl.glTranslatef(0f, 0f, cubeHalfSize);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[0]);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 20, 4);
		gl.glPopMatrix();
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisable(GL10.GL_TEXTURE_2D);

	}

	// Load images into 6 GL textures
	public void loadTexture(GL10 gl, Context c) {
		gl.glGenTextures(6, textureIDs, 0); // Generate texture-ID array for 6
											// IDs
		// Generate OpenGL texture images
		for (int face = 0; face < numFaces; face++) {
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[face]);
			// Build Texture from loaded bitmap for the currently-bind texture
			// ID
			
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
					GL10.GL_LINEAR);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
					GL10.GL_LINEAR);
			
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap[face], 0);
			bitmap[face].recycle();
		}
	}
}
