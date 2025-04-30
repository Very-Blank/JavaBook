{ pkgs ? import <nixpkgs> {} }:

let
  jdk = pkgs.jdk23.override { enableJavaFX = true; };
  maven = pkgs.maven.override { jdk_headless = jdk; };
in

pkgs.mkShell {
  buildInputs = with pkgs; [
    jdk
    maven
    openjfx
    xorg.libX11
    xorg.libXext
    xorg.libXrender
    xorg.libXrandr
    xorg.libXcursor
    xorg.libXi
    libxkbcommon
    gtk3
    glib
    dconf
    gsettings-desktop-schemas
  ];

  # so that `java` and `mvn` point at our JDK
  shellHook = ''
    export JAVA_HOME=${jdk}
    export PATH=$JAVA_HOME/bin:$PATH
    export XDG_DATA_DIRS=${pkgs.gsettings-desktop-schemas}/share/gsettings-schemas/${pkgs.gsettings-desktop-schemas.name}:${pkgs.gtk3}/share/gsettings-schemas/${pkgs.gtk3.name}:$XDG_DATA_DIRS
    export GIO_EXTRA_MODULES=${pkgs.dconf}/lib/gio/modules
  '';
}
