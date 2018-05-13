package br.unicamp.feec.graphics.shader;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLException;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;

public abstract class BaseShader implements Disposable{
	protected int mProgramId;
	private int mVertexShaderId, mFragmentShaderId;
	private String mVertexShader, mFragmentShader;
	
	public BaseShader(String vertexShader, String fragmentShader){
		mVertexShader = vertexShader;
		mFragmentShader = fragmentShader;
	}
	
	public void init(){
		GL4 gl = GLContext.getCurrentGL().getGL4();
		
		mVertexShaderId = createShader(gl, GL4.GL_VERTEX_SHADER, mVertexShader);
		mFragmentShaderId = createShader(gl, GL4.GL_FRAGMENT_SHADER, mFragmentShader);
		
		
		mProgramId = gl.glCreateProgram();
		gl.glAttachShader(mProgramId, mVertexShaderId);
		gl.glAttachShader(mProgramId, mFragmentShaderId);
		
		gl.glLinkProgram(mProgramId);
		
		int[] linked = new int[1];
		gl.glGetProgramiv(mProgramId, GL4.GL_LINK_STATUS, linked, 0);
		if (linked[0] != 0) {
			System.out.println("Shaders succesfully linked");
		} else {
			int[] logLength = new int[1];
			gl.glGetProgramiv(mProgramId, GL4.GL_INFO_LOG_LENGTH,
					logLength, 0);

			byte[] log = new byte[logLength[0]];
			gl.glGetProgramInfoLog(mProgramId, logLength[0], (int[]) null,
					0, log, 0);
			
			throw new GLException("Error linking shaders: " + new String(log));
		}
	}
	
	public void use(){
		GLContext.getCurrentGL().getGL4().glUseProgram(mProgramId);
	}
	
	@Override
	public void dispose() {
		GL4 gl = GLContext.getCurrentGL().getGL4();
		
		gl.glDetachShader(mProgramId, mVertexShaderId);
		gl.glDeleteShader(mVertexShaderId);
		
		gl.glDetachShader(mProgramId, mFragmentShaderId);
		gl.glDeleteShader(mFragmentShaderId);
		
		gl.glDeleteProgram(mProgramId);
	}
	
	private static int createShader(GL4 gl, int type, String script){
		int shaderId = gl.glCreateShader(type);
		
		gl.glShaderSource(shaderId, 1, new String[] { script }, null);
		
		gl.glCompileShader(shaderId);
		
		int[] compiled = new int[1];
		gl.glGetShaderiv(shaderId, GL4.GL_COMPILE_STATUS, compiled, 0);
		if (compiled[0] != 0) {
			System.out.println("Shader succesfully compiled");
		}
		else {
			int[] logLength = new int[1];
			gl.glGetShaderiv(shaderId, GL4.GL_INFO_LOG_LENGTH, logLength, 0);

			byte[] log = new byte[logLength[0]];
			gl.glGetShaderInfoLog(shaderId, logLength[0], (int[]) null, 0, log, 0);
			
			throw new GLException("Error compiling the shader: " + new String(log));
		}
		
		return shaderId;
	}
	
	protected int getUniformLocation(GL4 gl, String name){
		int location = gl.glGetUniformLocation(mProgramId, name);
		if(location < 0){
			throw new GLException("Error location uniform: " + name);
		}
		return location;
	}
	
	public abstract void bindAttributes();
}
