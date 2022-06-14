set PATH_TO_FX="C:\ProgramFiles\Java\javafx-sdk-17.0.2\lib"
javac --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml *.java
java --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml Pacman
pause