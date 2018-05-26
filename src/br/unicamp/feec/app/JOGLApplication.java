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
import br.unicamp.feec.utils.JGLU;
import br.unicamp.feec.utils.MatrixUtils;

public class JOGLApplication implements GLEventListener{
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	
	DefaultShader shader;
	Geometry triangle;
	
	@Override
	public void display(GLAutoDrawable drawable) {
		GL4 gl = drawable.getGL().getGL4();

		shader.setModelViewMatrixUniform(MatrixUtils.toPlainMatrix4x4(MatrixUtils.getTransalationMatrix4x4(0, 0, -2)));
		shader.setModelMatrixUniform(MatrixUtils.toPlainMatrix4x4(MatrixUtils.getTransalationMatrix4x4(0, 0, -2)));
		shader.setProjectionMatrixUniform(MatrixUtils.toPlainMatrix4x4(MatrixUtils.getFrustrumMatrix4x4(-1, 1, -1, 1, 1, 1000)));
		shader.setNormalMatrixUniform(MatrixUtils.toPlainMatrix3x3(MatrixUtils.getIdentityMatrix4x4()));
		shader.setAmbientColorUniform(new float[]{1, 1, 1});
		shader.setAmbientStrengthUniform(0.1f);
		shader.setLighPositionUniform(new float[]{0, 0, 10, 1});
		shader.setLightColorUniform(new float[]{1, 1, 1});
		shader.setDiffuseColorUniform(new float[]{1, 0, 0, 1});
		shader.setViewPositionUniform(new float[]{0, 0, 0, 1});
		shader.setSpecularStrengthUniform(0.5f);

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

		gl.glEnable(GL4.GL_DEPTH_TEST);
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
					-0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
					0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
					0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
					0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
					-0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
					-0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,

					-0.5f, -0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
					0.5f, -0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
					0.5f,  0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
					0.5f,  0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
					-0.5f,  0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
					-0.5f, -0.5f,  0.5f,  0.0f,  0.0f, 1.0f,

					-0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,
					-0.5f,  0.5f, -0.5f, -1.0f,  0.0f,  0.0f,
					-0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,
					-0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,
					-0.5f, -0.5f,  0.5f, -1.0f,  0.0f,  0.0f,
					-0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,

					0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,
					0.5f,  0.5f, -0.5f,  1.0f,  0.0f,  0.0f,
					0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,
					0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,
					0.5f, -0.5f,  0.5f,  1.0f,  0.0f,  0.0f,
					0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,

					-0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,
					0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,
					0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,
					0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,
					-0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,
					-0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,

					-0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,
					0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,
					0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,
					0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,
					-0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,
					-0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f
			};
	    	int[] indices = {
	    			0, 1, 2, 	2, 3, 0,
	    			4, 5, 6,	6, 7, 4,
	    			2, 6, 7,	7, 3, 2,
					0, 1, 5,	5, 4, 0
	    	};

	    	FloatBuffer verticesFB = Buffers.newDirectFloatBuffer(vertices);
	    	IntBuffer indicesIB = Buffers.newDirectIntBuffer(indices);
	    	triangle = new Geometry(shader, verticesFB, indicesIB) {
				
				@Override
				public void draw(GL4 gl) {
					this.bind();
					//gl.glDrawElements(GL4.GL_TRIANGLES, indices.length, GL4.GL_UNSIGNED_INT, 0);
					gl.glDrawArrays(GL4.GL_TRIANGLES, 0, 36);
				}
			};
	}

	@Override
	public void reshape(GLAutoDrawable pArg0, int pArg1, int pArg2, int pArg3, int pArg4) {
		// TODO Auto-generated method stub
		
	}

}
