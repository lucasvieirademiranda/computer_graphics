package br.unicamp.feec.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import br.unicamp.feec.graphics.camera.Camera;
import br.unicamp.feec.graphics.camera.PerspectiveCamera;
import br.unicamp.feec.graphics.geometry.CubeGeometry;
import br.unicamp.feec.graphics.lighting.DirectionalLight;
import br.unicamp.feec.graphics.lighting.Light;
import br.unicamp.feec.graphics.material.Material;
import br.unicamp.feec.graphics.mesh.Mesh;
import br.unicamp.feec.utils.ColorUtils;
import br.unicamp.feec.utils.VectorUtils;
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
	Camera camera;
	CubeGeometry cubeGeometry;
	Material cubeMaterial;
	Mesh cube;
	Light light;
	
	@Override
	public void display(GLAutoDrawable drawable) {
		GL4 gl = drawable.getGL().getGL4();

		gl.glClear(GL4.GL_COLOR_BUFFER_BIT | GL4.GL_DEPTH_BUFFER_BIT);

		light.sendUniforms(shader);
		//shader.setProjectionMatrixUniform(MatrixUtils.toPlainMatrix4x4(MatrixUtils.getFrustrumMatrix4x4(-1, 1, -1, 1, 1, 1000)));
		cube.draw(gl, shader, camera);

		gl.glFlush();
	}

	@Override
	public void dispose(GLAutoDrawable pArg0) {
		shader.dispose();
		cubeGeometry.dispose();
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

		camera = new PerspectiveCamera(45, SCREEN_WIDTH / (float) SCREEN_HEIGHT, 1, 1000);

	    cubeGeometry = new CubeGeometry(shader);

		cubeMaterial = new Material();
		cubeMaterial.setAmbientColor(ColorUtils.create(1, 0, 0, 1));
		cubeMaterial.setDiffuseColor(ColorUtils.create(1, 0, 0, 1));
		cubeMaterial.setSpecularColor(ColorUtils.create(1, 1, 1, 1));
		cubeMaterial.setShineness(64);

		cube = new Mesh(cubeGeometry, cubeMaterial);

		light = new DirectionalLight(VectorUtils.create(0, 0, 1));
		light.setAmbientColor(ColorUtils.create(1, 1, 1, 1));
		light.setAmbientStrength(0.1f);
		light.setDiffuseColor(ColorUtils.create(1, 1, 1, 1));
		light.setSpecularColor(ColorUtils.create(1, 1, 1, 1));
	}

	@Override
	public void reshape(GLAutoDrawable pArg0, int pArg1, int pArg2, int pArg3, int pArg4) {
		// TODO Auto-generated method stub
		
	}

}
