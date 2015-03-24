# Beskrivelse


# Krav
1. Fullskjerm grafikk / resizable
2. 3D
3. Kontrollerbar med shift, WASD, og mus.
4. Skal ha lyd
5. Skal ikke kræsje ofte
6. Trenger ikke å lagre til fil
7. Mellom 30 og 60 frames per second


# Utviklingsmetode
### Metode
Planlegger først et oppsett i UML, og lager derfra trærne med klasser fra toppen og ned. Iterativ utvikling passer ikke så bra, da all funksjonalitet må taes høyde for fra start av.
Det går an å lage Screens iterativt.

1. Starter med loading screen
2. Lager så Main menu screen
3. Deretter selve spillet


### Verktøy
| Navn              | Bruksområde   |
|-------------------|---------------|
| IntelliJ IDEA     | Programmering |
| Atom text editor  | Tekst-editor  |
| Violet UML Editor | UML           |
| Git               | VCS           |
| GitHub            | Backup / VCS hosting / potensiel teamworking-platform |
| Blender           | 3D-modelering |
| Paint.net         | 2D-grafikk; teksturering |
| fbx-conv          | fbx til g3db-konverterer |

### Bibliotek/avhengigheter
* LibGDX
  * AndroidSDK
  * OpenGL
  * LWJGL
  * OpenAL
  * Bullet
  * Freetype
