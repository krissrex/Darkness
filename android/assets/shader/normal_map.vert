#version 150


in vec3 a_position;
in vec2 a_texCoord0;

// diffuse texture
uniform vec4 u_diffuseUVTransform;
out vec2 v_diffuseUV;

// transforms
uniform mat4 u_projViewTrans;
uniform mat4 u_worldTrans;
uniform mat4 u_projTrans;
uniform mat4 u_viewTrans;

// light
out vec3 v_lightDiffuse;
uniform vec3 u_ambientLight;
out float v_fog;

// camera
uniform vec4 u_cameraPosition;

// texture
uniform sampler2D u_diffuseTexture;
uniform sampler2D u_normalTexture;

out vec2 v_texCoord0;



void main() {
    v_diffuseUV = u_diffuseUVTransform.xy + a_texCoord0 * u_diffuseUVTransform.zw;
    v_texCoord0 = a_texCoord0;

    vec4 pos = u_worldTrans * vec4(a_position, 1.0);

    gl_Position = u_projViewTrans * pos;
    
    vec3 flen = u_cameraPosition.xyz - pos.xyz;
    float fog = dot(flen, flen) * u_cameraPosition.w;
    v_fog = min(fog, 1.0);
}
