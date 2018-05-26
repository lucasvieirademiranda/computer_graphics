#version 400

uniform vec3 u_ambientColor;
uniform float u_ambientStrength;

uniform vec4 u_lightPosition;
uniform vec3 u_lightColor;

uniform vec4 u_diffuseColor;

uniform vec4 u_viewPosition;
uniform float u_specularStrength;

in vec3 v_normal;
in vec4 v_position;

out vec4 fColor;

void main (void) {
	vec3 normal = normalize(v_normal);

	vec3 ambientColor =  u_ambientColor * u_ambientStrength * u_diffuseColor.rgb;

	vec3 lightDirection = normalize(u_lightPosition - v_position).xyz;

	float diffuseStrength = max(dot(normal, lightDirection), 0.0);
	vec3 diffuseColor = u_lightColor * diffuseStrength * u_diffuseColor.rgb;

	vec3 viewDirection = normalize(u_viewPosition - v_position).xyz;
    vec3 reflectDirection = reflect(-lightDirection, normal);
    vec3 specularColor = pow(max(dot(viewDirection, reflectDirection), 0.0), 64) * u_specularStrength * u_lightColor;

	fColor = vec4(ambientColor + diffuseColor + specularColor, u_diffuseColor.a);

} ;
