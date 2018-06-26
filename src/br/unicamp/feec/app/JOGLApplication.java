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
import br.unicamp.feec.graphics.geometry.*;
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

import br.unicamp.feec.graphics.shader.DefaultShader;
import br.unicamp.feec.utils.JGLU;
import br.unicamp.feec.utils.MatrixUtils;

public class JOGLApplication implements GLEventListener
{
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	
	DefaultShader phongShader, gouradShader, flatShader, currentShader;
	
	Camera camera;
	
	Geometry cubeGeometry,
			 sphereGeometry,
			 sphere1Geometry,
			 sphere2Geometry, 
			 toroidGeometry, 
			 cylinderGeometry, 
			 coneGeometry,
			 cone1Geometry;
	
	Material cubeMaterial, 
			 sphereMaterial,
			 sphere1Material, 
			 sphere2Material, 
			 toroidMaterial, 
			 cylinderMaterial, 
			 coneMaterial,
			 cone1Material;
	
	Mesh cube, 
		 sphere,
		 sphere1, 
		 sphere2, 
		 toroid, 
		 cylinder, 
		 cone,
		 cone1;
	
	Light light;
	
	float animationSpeedRotationSlow, animationSpeedRotationFast;
	
	float animationSpeedPositionSlow, animationSpeedPositionFast;

	@Override
	public void display(GLAutoDrawable drawable)
	{
		//animationSpeedRotationSlow += 0.33;
		animationSpeedRotationFast += 1;

		//animationSpeedPositionSlow += 0.0066;
		//animationSpeedPositionFast += 0.02;

		//flatSphere.setTransformationMatrix(MatrixUtils.multiplyMatrix4x4(MatrixUtils.getScaleMatrix4x4(4, 4, 4), MatrixUtils.multiplyMatrix4x4(MatrixUtils.getRotationMatrix4x4(animationSpeedRotationFast, 0, 1, 0), MatrixUtils.getTransalationMatrix4x4(10, -25 + (float)Math.sin(animationSpeedPositionFast) * 10, -60))));
		//flatCylinder.setTransformationMatrix(MatrixUtils.multiplyMatrix4x4(MatrixUtils.getRotationMatrix4x4(animationSpeedRotationFast, 0, 1, 0), MatrixUtils.getTransalationMatrix4x4(30, -25 + (float)Math.sin(animationSpeedPositionFast) * 10, -60)));
		//flatCube.setTransformationMatrix(MatrixUtils.multiplyMatrix4x4(MatrixUtils.getScaleMatrix4x4(4, 4, 4), MatrixUtils.multiplyMatrix4x4(MatrixUtils.getRotationMatrix4x4(animationSpeedRotationFast, 0, 1, 0), MatrixUtils.getTransalationMatrix4x4(-20, -25 + (float)Math.sin(animationSpeedPositionFast) * 10, -60))));
		
		//cube.setTransformationMatrix(MatrixUtils.multiplyMatrix4x4(MatrixUtils.getScaleMatrix4x4(4, 4, 4), MatrixUtils.multiplyMatrix4x4(MatrixUtils.getRotationMatrix4x4(animationSpeedRotationFast, 0, 1, 0), MatrixUtils.getTransalationMatrix4x4(-20, -5 + (float)Math.sin(animationSpeedPositionFast) * 10, -60))));
		
		sphere.setTransformationMatrix(
		    MatrixUtils.multiplyMatrix4x4(
		    	MatrixUtils.getScaleMatrix4x4(4, 4, 4),
				MatrixUtils.multiplyMatrix4x4(
					MatrixUtils.getRotationMatrix4x4(animationSpeedRotationFast, 0, 1f, 0), 
					MatrixUtils.getTransalationMatrix4x4(0, -3, -30)
				)
			)
		);

		sphere1.setTransformationMatrix(
		    MatrixUtils.multiplyMatrix4x4(
		    	MatrixUtils.getScaleMatrix4x4(4, 4, 4),
				MatrixUtils.multiplyMatrix4x4(
					MatrixUtils.getRotationMatrix4x4(animationSpeedRotationFast, 0, 1f, 0), 
					MatrixUtils.getTransalationMatrix4x4(13, -3, -35)
				)
			)
		);
		
		sphere2.setTransformationMatrix(
		    MatrixUtils.multiplyMatrix4x4(
		    	MatrixUtils.getScaleMatrix4x4(4, 4, 4),
				MatrixUtils.multiplyMatrix4x4(
					MatrixUtils.getRotationMatrix4x4(animationSpeedRotationFast, 0, 1f, 0), 
					MatrixUtils.getTransalationMatrix4x4(-13, -3, -25)
				)
			)
		);
		
		cone.setTransformationMatrix(
		    MatrixUtils.multiplyMatrix4x4(
		    	MatrixUtils.getScaleMatrix4x4(4, 4, 4),
				MatrixUtils.multiplyMatrix4x4(
					MatrixUtils.getRotationMatrix4x4(animationSpeedRotationFast, 0, 1f, 0), 
					MatrixUtils.getTransalationMatrix4x4(-7, -3, -35)
				)
			)
		);
		
		cone1.setTransformationMatrix(
		    MatrixUtils.multiplyMatrix4x4(
		    	MatrixUtils.getScaleMatrix4x4(4, 4, 4),
				MatrixUtils.multiplyMatrix4x4(
					MatrixUtils.getRotationMatrix4x4(animationSpeedRotationFast, 0, 1f, 0), 
					MatrixUtils.getTransalationMatrix4x4(7, -3, -25)
				)
			)
		);
		
		GL4 gl = drawable.getGL().getGL4();

		gl.glClear(GL4.GL_COLOR_BUFFER_BIT | GL4.GL_DEPTH_BUFFER_BIT);

		light.sendUniforms(currentShader);
		
		//flatSphere.draw(gl, currentShader, camera);
		//flatCylinder.draw(gl, currentShader, camera);
		//flatCube.draw(gl, currentShader, camera);
		
		//cube.draw(gl, currentShader, camera);
		
		sphere.draw(gl, currentShader, camera);
		sphere1.draw(gl, currentShader, camera);
		sphere2.draw(gl, currentShader, camera);
		
		cone.draw(gl, currentShader, camera);
		cone1.draw(gl, currentShader, camera);
		
		//toroid.draw(gl, currentShader, camera);
		//cylinder.draw(gl, currentShader, camera);
		//cone.draw(gl, currentShader, camera);

		gl.glFlush();
	}

	@Override
	public void dispose(GLAutoDrawable pArg0) {
		
		sphereGeometry.dispose();
		
		/*phongShader.dispose();
		gouradShader.dispose();
		flatShader.dispose();
		cubeGeometry.dispose();
		coneGeometry.dispose();
		cylinderGeometry.dispose();
		sphereGeometry.dispose();
		toroidGeometry.dispose();
		flatCubeGeometry.dispose();
		flatCylinderGeometry.dispose();
		flatSphereGeometry.dispose();*/
		System.exit(0);
	}

	@Override
	public void init(GLAutoDrawable drawable)
	{
		GL4 gl = drawable.getGL().getGL4();

		gl.glClearColor(1, 0, 1, 1);
		gl.glEnable(GL4.GL_DEPTH_TEST);
		gl.glPolygonMode(GL4.GL_FRONT_AND_BACK, GL4.GL_FILL);

		String vertexShader = null, fragmentShader = null;

		try
		{
			 vertexShader = new String(Files.readAllBytes(Paths.get("./res/shader.vert")), StandardCharsets.UTF_8);
			 fragmentShader = new String(Files.readAllBytes(Paths.get("./res/shader.frag")), StandardCharsets.UTF_8);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		 
		phongShader = new DefaultShader(vertexShader, fragmentShader);
		phongShader.init();

		try
		{
			vertexShader = new String(Files.readAllBytes(Paths.get("./res/gouraud_shader.vert")), StandardCharsets.UTF_8);
			fragmentShader = new String(Files.readAllBytes(Paths.get("./res/gouraud_shader.frag")), StandardCharsets.UTF_8);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		gouradShader = new DefaultShader(vertexShader, fragmentShader);
		gouradShader.init();

		try
		{
			vertexShader = new String(Files.readAllBytes(Paths.get("./res/flat_shader.vert")), StandardCharsets.UTF_8);
			fragmentShader = new String(Files.readAllBytes(Paths.get("./res/flat_shader.frag")), StandardCharsets.UTF_8);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		flatShader = new DefaultShader(vertexShader, fragmentShader);
		flatShader.init();

		currentShader = phongShader;
		currentShader.use();

		camera = new PerspectiveCamera(60, SCREEN_WIDTH / (float) SCREEN_HEIGHT, 1, 1000);
		camera.setViewMatrix(MatrixUtils.getLookAtMatrix4x4(0, 0, 0, 0, -10, -80, 0, 1, 0));

		sphereGeometry = new SphereGeometry(currentShader);
		
		sphereMaterial = new Material();
		sphereMaterial.setAmbientColor(ColorUtils.create(0, 1, 0, 1));
		sphereMaterial.setDiffuseColor(ColorUtils.create(0, 1, 0, 1));
		sphereMaterial.setSpecularColor(ColorUtils.create(1, 1, 1, 1));
		sphereMaterial.setShineness(256);
		
		sphere = new Mesh(sphereGeometry, sphereMaterial);
		
		sphere1Geometry = new SphereGeometry(currentShader);
		
		sphere1Material = new Material();
		sphere1Material.setAmbientColor(ColorUtils.create(0, 0, 1, 1));
		sphere1Material.setDiffuseColor(ColorUtils.create(0, 0, 1, 1));
		sphere1Material.setSpecularColor(ColorUtils.create(1, 1, 1, 1));
		sphere1Material.setShineness(256);
		
		sphere1 = new Mesh(sphere1Geometry, sphere1Material);
		
		sphere2Geometry = new SphereGeometry(currentShader);
		
		sphere2Material = new Material();
		sphere2Material.setAmbientColor(ColorUtils.create(1, 0, 0, 1));
		sphere2Material.setDiffuseColor(ColorUtils.create(1, 0, 0, 1));
		sphere2Material.setSpecularColor(ColorUtils.create(1, 1, 1, 1));
		sphere2Material.setShineness(256);
		
		sphere2 = new Mesh(sphere2Geometry, sphere2Material);
		
		coneGeometry = new ConeGeometry(currentShader);
		
		coneMaterial = new Material();
		coneMaterial.setAmbientColor(ColorUtils.create(0, 1, 0, 1));
		coneMaterial.setDiffuseColor(ColorUtils.create(0, 1, 0, 1));
		coneMaterial.setSpecularColor(ColorUtils.create(1, 1, 1, 1));
		coneMaterial.setShineness(256);
		
		cone = new Mesh(coneGeometry, coneMaterial);
		
		cone1Geometry = new ConeGeometry(currentShader);
		
		cone1Material = new Material();
		cone1Material.setAmbientColor(ColorUtils.create(0, 0, 1, 1));
		cone1Material.setDiffuseColor(ColorUtils.create(0, 0, 1, 1));
		cone1Material.setSpecularColor(ColorUtils.create(1, 1, 1, 1));
		cone1Material.setShineness(256);
		
		cone1 = new Mesh(cone1Geometry, cone1Material);
		
		/*flatSphereGeometry = new FlatSphereGeometry(currentShader);
		flatCylinderGeometry = new FlatCylinderGeometry(currentShader);
		flatCubeGeometry = new FlatConeGeometry(currentShader);
		
	    cubeGeometry = new CubeGeometry(currentShader);
	    coneGeometry = new ConeGeometry(currentShader);
	    cylinderGeometry = new CylinderGeometry(currentShader);
	    sphereGeometry = new SphereGeometry(currentShader);
	    toroidGeometry = new ToroidGeometry(currentShader);

	    flatSphereMaterial = new Material();
	    flatSphereMaterial.setAmbientColor(ColorUtils.create(1, 0, 0, 1));
	    flatSphereMaterial.setDiffuseColor(ColorUtils.create(1, 0, 0, 1));
	    flatSphereMaterial.setSpecularColor(ColorUtils.create(1, 1, 1, 1));
	    flatSphereMaterial.setShineness(256);
	    
	    flatCylinderMaterial = new Material();
	    flatCylinderMaterial.setAmbientColor(ColorUtils.create(1, 0, 0, 1));
	    flatCylinderMaterial.setDiffuseColor(ColorUtils.create(1, 0, 0, 1));
	    flatCylinderMaterial.setSpecularColor(ColorUtils.create(1, 1, 1, 1));
	    flatCylinderMaterial.setShineness(256);
	    
	    flatCubeMaterial = new Material();
	    flatCubeMaterial.setAmbientColor(ColorUtils.create(1, 0, 0, 1));
	    flatCubeMaterial.setDiffuseColor(ColorUtils.create(1, 0, 0, 1));
	    flatCubeMaterial.setSpecularColor(ColorUtils.create(1, 1, 1, 1));
	    flatCubeMaterial.setShineness(256);
	    
		cubeMaterial = new Material();
		cubeMaterial.setAmbientColor(ColorUtils.create(1, 0, 0, 1));
		cubeMaterial.setDiffuseColor(ColorUtils.create(1, 0, 0, 1));
		cubeMaterial.setSpecularColor(ColorUtils.create(1, 1, 1, 1));
		cubeMaterial.setShineness(256);

		coneMaterial = new Material();
		coneMaterial.setAmbientColor(ColorUtils.create(1, 1, 0, 1));
		coneMaterial.setDiffuseColor(ColorUtils.create(1, 1, 0, 1));
		coneMaterial.setSpecularColor(ColorUtils.create(1, 1, 1, 1));
		coneMaterial.setShineness(256);

		sphereMaterial = new Material();
		sphereMaterial.setAmbientColor(ColorUtils.create(0, 1, 0, 1));
		sphereMaterial.setDiffuseColor(ColorUtils.create(0, 1, 0, 1));
		sphereMaterial.setSpecularColor(ColorUtils.create(1, 1, 1, 1));
		sphereMaterial.setShineness(256);

		cylinderMaterial = new Material();
		cylinderMaterial.setAmbientColor(ColorUtils.create(0, 1, 1, 1));
		cylinderMaterial.setDiffuseColor(ColorUtils.create(0, 1, 1, 1));
		cylinderMaterial.setSpecularColor(ColorUtils.create(1, 1, 1, 1));
		cylinderMaterial.setShineness(256);

		toroidMaterial = new Material();
		toroidMaterial.setAmbientColor(ColorUtils.create(0, 0, 1, 1));
		toroidMaterial.setDiffuseColor(ColorUtils.create(0, 0, 1, 1));
		toroidMaterial.setSpecularColor(ColorUtils.create(1, 1, 1, 1));
		toroidMaterial.setShineness(256);

		flatSphere = new Mesh(flatSphereGeometry, flatSphereMaterial);
		flatCylinder = new Mesh(flatCylinderGeometry, flatCylinderMaterial);
		flatCube = new Mesh(flatCubeGeometry, flatCubeMaterial);
		
		cube = new Mesh(cubeGeometry, cubeMaterial);
		cone = new Mesh(coneGeometry, coneMaterial);
		cylinder = new Mesh(cylinderGeometry, cylinderMaterial);
		sphere = new Mesh(sphereGeometry, sphereMaterial);
		toroid = new Mesh(toroidGeometry, toroidMaterial);*/

		light = new DirectionalLight(VectorUtils.create(0, 0, -1));
		
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
