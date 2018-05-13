#version 400

uniform vec3 u_ambientColor;
uniform float u_ambientStrength;

uniform vec4 u_lightPosition;
uniform vec3 u_lightColor;

uniform vec4 u_diffuseColor;

in vec3 v_normal;
in vec4 v_position;

out vec4 fColor;

void main (void) {
	vec3 normal = normalize(v_normal);

	vec3 ambientColor =  u_ambientColor * u_ambientStrength;

	vec3 lightDirection = normalize(u_lightPosition - v_position).xyz;

	float diffuseStrength = max(dot(normal, lightDirection), 0.0);
	vec3 diffuseColor = u_lightColor * diffuseStrength;

	fColor = vec4(u_diffuseColor.rgb * (diffuseColor + ambientColor), u_diffuseColor.a);

} ;
