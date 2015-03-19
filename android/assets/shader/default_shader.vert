#version 150

in vec4 a_position; // ShaderProgram.POSITION_ATTRIBUTE
in vec4 a_color; // ShaderProgram.COLOR_ATTRIBUTE
in vec2 a_texCoord0; // ShaderProgram.TEXCOORD_ATTRIBUTE + 0;

uniform float u_time;
vec4 position;

uniform mat4 u_projTrans;
out vec4 v_color;
out vec2 v_texCoords;

void main()
{
  position = a_position;
  position.x = sin(u_time)*100 + a_position.x;

  v_color = a_color;
  v_color.a = v_color.a * (255.0/254.0);
  v_texCoords = a_texCoord0;
  gl_Position =  u_projTrans * position;
}
