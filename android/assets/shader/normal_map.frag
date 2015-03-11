#version 150

// gl_FragCoord: origin_upper_left,
// pixel_center_integer.
//  For in only (not with variable declarations):
// early_fragment_tests

/*
Inputs:
 in vec4 gl_FragCoord;
 in bool gl_FrontFacing;
 in float gl_ClipDistance[];
 in vec2 gl_PointCoord;
 in int gl_PrimitiveID;
 in int gl_SampleID;
 in vec2 gl_SamplePosition;
in int gl_SampleMaskIn[];
 in int gl_Layer;
in int gl_ViewportIndex;

Outputs
out float gl_FragDepth;
out int gl_SampleMask[];
*/

varying vec2 v_texCoord0;

void main() {
    gl_FragColor = vec4(v_texCoord0, 0.0, 1.0);
}