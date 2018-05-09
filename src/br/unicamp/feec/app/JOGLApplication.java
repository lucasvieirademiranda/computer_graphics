package br.unicamp.feec.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

import br.unicamp.feec.graphics.geometry.Geometry;
import br.unicamp.feec.graphics.shader.DefaultShader;
import br.unicamp.feec.utils.MatrixUtils;

public class JOGLApplication implements GLEventListener{
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	
	DefaultShader shader;
	Geometry triangle;
	
	@Override
	public void display(GLAutoDrawable drawable) {
		GL4 gl = drawable.getGL().getGL4();
		
		shader.setModelViewMatrixUniform(MatrixUtils.toPlainMatrix4x4(MatrixUtils.getIdentityMatrix4x4()));
		shader.setProjectionMatrixUniform(MatrixUtils.toPlainMatrix4x4(MatrixUtils.getIdentityMatrix4x4()));
		
		gl.glClear(GL4.GL_COLOR_BUFFER_BIT | GL4.GL_DEPTH_BUFFER_BIT);
		
		triangle.draw(gl);
		
		gl.glFlush();
	}

	@Override
	public void dispose(GLAutoDrawable pArg0) {
		shader.dispose();
		triangle.dispose();
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL4 gl = drawable.getGL().getGL4();
		
		gl.glPolygonMode(GL4.GL_FRONT_AND_BACK, GL4.GL_FILL);
		
		String vertexShader = null, fragmentShader = null;
		
		 try {
			 vertexShader = new String(Files.readAllBytes(Paths.get("./res/shader.vert")), StandardCharsets.UTF_8);
			 fragmentShader = new String(Files.readAllBytes(Paths.get("./res/shader.frag")), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		 shader = new DefaultShader(vertexShader, fragmentShader);
		 shader.init();
		 shader.use();
		 
	    	float[] vertices = {  
				0.0f, 0.0f, 0.0f, 1.0f,
				0.5f, 0.0f, 0.0f, 1.0f,
				0.0f, 0.5f, 0.0f, 1.0f
			};
	    	int[] indices = {
	    			0, 1, 2
	    	};

	    	FloatBuffer verticesFB = Buffers.newDirectFloatBuffer(vertices);
	    	IntBuffer indicesIB = Buffers.newDirectIntBuffer(indices);
	    	triangle = new Geometry(shader, verticesFB, indicesIB) {
				
				@Override
				public void draw(GL4 gl) {
					this.bind();
					gl.glDrawElements(GL4.GL_TRIANGLES, 3, GL4.GL_UNSIGNED_INT, 0);
				}
			};
	    	verticesFB = null;
	    	indicesIB = null;
	}

	@Override
	public void reshape(GLAutoDrawable pArg0, int pArg1, int pArg2, int pArg3, int pArg4) {
		// TODO Auto-generated method stub
		
	}

}
