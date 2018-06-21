#version 400

struct Material{
    vec3 ambientColor;
    vec3 diffuseColor;
    vec3 specularColor;
    float shininess;
};

struct Light{
    vec4 position;
    vec3 ambientColor;
    vec3 diffuseColor;
    vec3 specularColor;

    float ambientStrength;

    vec3 spotDirection;
    float spotExponent;
    float spotCutoff;
};

uniform Material u_material;
uniform Light u_light;
uniform vec4 u_viewPosition;

uniform mat4 u_projectionMatrix;
uniform mat4 u_modelViewMatrix;
uniform mat4 u_modelMatrix;
uniform mat4 u_viewMatrix;
uniform mat3 u_normalMatrix;

in vec3 a_position;
in vec3 a_normal;

out vec4 v_color;

void main(void) {
	vec3 normal = u_normalMatrix * a_normal;
	vec4 position = u_modelMatrix * vec4(a_position, 1);

    vec3 lightDirection;
    float theta = 0;

    //directional
    if(u_light.position.w == 0){
        lightDirection = normalize(-u_light.position).xyz;
        theta = -1;

    }
    //point or spot
    else{
        lightDirection = normalize(u_light.position - position).xyz;

        //point
        if(u_light.spotCutoff == 180){
            theta = -1;
        }
        //spot
        else{
            theta = dot(lightDirection, normalize(-u_light.spotDirection));
        }
    }

    vec3 viewDirection = normalize(u_viewPosition - position).xyz;
    vec3 reflectDirection = reflect(-lightDirection, normal);
    float intensity = pow(clamp(theta / cos(radians(u_light.spotCutoff)), 0.0, 1.0), u_light.spotExponent + 1);//The result is undefined if x<0 or if x=0 and y≤0.

    //ambient
    vec3 ambientColor = u_light.ambientColor * u_material.ambientColor * u_light.ambientStrength;

    //diffuse
    float diffuseStrength = max(dot(normal, lightDirection), 0.0);
    vec3 diffuseColor = u_light.diffuseColor * u_material.diffuseColor * diffuseStrength * intensity;

    //specular
    float specularStrength = pow(max(dot(viewDirection, reflectDirection), 0.0), u_material.shininess);
    vec3 specularColor = u_light.specularColor * u_material.specularColor * specularStrength * intensity;

    v_color = vec4(ambientColor + diffuseColor + specularColor, 1);

	gl_Position = u_projectionMatrix * u_modelViewMatrix * vec4(a_position, 1);
};
