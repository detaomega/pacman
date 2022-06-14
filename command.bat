javac --module-path $env:PATH_TO_FX --add-modules=javafx.controls,javafx.fxml *.java
java --module-path $env:PATH_TO_FX --add-modules=javafx.controls,javafx.fxml Pacman
pause