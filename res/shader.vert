#version 400

uniform mat4 u_projectionMatrix;
uniform mat4 u_modelViewMatrix;
uniform mat4 u_modelMatrix;
uniform mat4 u_viewMatrix;
uniform mat3 u_normalMatrix;

in vec3 a_position;
in vec3 a_normal;

out vec4 v_position;
out vec3 v_normal;

void main(void) {
	v_normal = u_normalMatrix * a_normal;
	v_position = u_modelMatrix * vec4(a_position, 1);

	gl_Position = u_projectionMatrix * u_modelViewMatrix * vec4(a_position, 1);
};
