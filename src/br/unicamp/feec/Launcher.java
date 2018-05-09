package br.unicamp.feec;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;

import br.unicamp.feec.app.JOGLApplication;

public class Launcher{
	public static void main(String[] args) {
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL4));

		GLWindow glWindow = GLWindow.create(caps);

		glWindow.setTitle("JOGL - Exercicio 8");
		glWindow.setSize(JOGLApplication.SCREEN_WIDTH, JOGLApplication.SCREEN_HEIGHT);
		glWindow.setUndecorated(false);
		glWindow.setPointerVisible(true);
		glWindow.setVisible(true);
		glWindow.setResizable(false);

		glWindow.addGLEventListener(new JOGLApplication());
		Animator animator = new Animator();
		animator.add(glWindow);
		animator.start();
	}
}
