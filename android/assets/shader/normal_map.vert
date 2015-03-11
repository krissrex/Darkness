#version 150


attribute vec3 a_position;

uniform mat4 u_worldTrans;
uniform mat4 u_projTrans;
uniform sampler2D u_texture;

attribute vec2 a_texCoord0;
varying vec2 v_texCoord0;
// Outs:
// out gl_PerVertex {
// vec4 gl_Position;
// float gl_PointSize;
// float gl_ClipDistance[];
//}

void main() {
    v_textCoord0 = a_texCoord0;
    gl_Position = u_projTrans * u_worldTrans * vec4(a_position, 1.0);
}