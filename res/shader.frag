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

in vec3 v_normal;
in vec4 v_position;

out vec4 fColor;

void main (void) {

	vec3 normal = normalize(v_normal);

    vec3 lightDirection;
    float theta = 0;

    //directional
    if(u_light.position.w == 0){
        lightDirection = normalize(-u_light.position).xyz;
        theta = -1;

    }
    //point or spot
    else{
        lightDirection = normalize(u_light.position - v_position).xyz;

        //point
        if(u_light.spotCutoff == -1){//cos(180)
            theta = -1;
        }
        //spot
        else{
            theta = dot(lightDirection, normalize(-u_light.spotDirection));
        }
    }

    vec3 viewDirection = normalize(u_viewPosition - v_position).xyz;
    vec3 reflectDirection = reflect(-lightDirection, normal);
    float intensity = pow(clamp(theta / u_light.spotCutoff, 0.0, 1.0), u_light.spotExponent + 1);//The result is undefined if x<0 or if x=0 and y≤0.

    //ambient
    vec3 ambientColor = u_light.ambientColor * u_material.ambientColor * u_light.ambientStrength;

    //diffuse
    float diffuseStrength = max(dot(normal, lightDirection), 0.0);
    vec3 diffuseColor = u_light.diffuseColor * u_material.diffuseColor * diffuseStrength * intensity;

    //specular
    float specularStrength = pow(max(dot(viewDirection, reflectDirection), 0.0), u_material.shininess);
    vec3 specularColor = u_light.specularColor * u_material.specularColor * specularStrength * intensity;

    fColor = vec4(ambientColor + diffuseColor + specularColor, 1);
} ;
