#version 150

#ifdef GL_ES
#define LOWP lowp
#define MED mediump
#define HIGH highp
precision mediump float;
#else
#define MED
#define LOWP
#define HIGH
#endif

// fog
uniform vec4 u_fogColor;
in float v_fog;

// light
in vec3 v_lightDiffuse;
in vec3 v_ambientLight;
in vec3 v_lightSpecular;

// texture
uniform vec4 u_diffuseUVTransform;
in MED vec2 v_diffuseUV;
uniform sampler2D u_diffuseTexture;


in vec2 v_texCoord0;


void main() {
    vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV);

    gl_FragColor.rgb = (diffuse.rgb * (v_ambientLight + v_lightDiffuse));
    gl_FragColor.rgb = mix(gl_FragColor.rgb, u_fogColor.rgb, v_fog);
    gl_FragColor.a = 1.0;
}
