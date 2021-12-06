package com.example.lotto;



import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.*;


public class HelloController {
    @FXML
    private Button gomb;
    @FXML
    private Label kulonalloSzam;
    @FXML
    private Label szamok;
    private Random random = new Random();
    private boolean maxMennyiseg = false;
    public List<Integer> szamokLista = new ArrayList<>();
    private int aktualisSzam = 0;
    public int szamMennyiseg = 0;


    public void methodSwap() {
        if (maxMennyiseg) {
            rendezTisztit();
        } else {
            szamSors();
            if (szamMennyiseg == 0){
                szamok.setText("");
            }
        }
    }



    public void szamSors() {

        Timer szamBerakas = new Timer();
        Timer szamRandomizalas = new Timer();
        aktualisSzam = 0;

        while (szamokLista.contains(aktualisSzam) || aktualisSzam == 0) {
            aktualisSzam = random.nextInt(90) + 1;
        }

        szamRandomizalas.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    kulonalloSzam.setText(Integer.toString(random.nextInt(90) + 1));
                });
            }
        }, 1, 1);

        szamBerakas.schedule(new TimerTask() {
            @Override
            public void run() {
                szamRandomizalas.cancel();
                Platform.runLater(() -> {
                kulonalloSzam.setText(Integer.toString(aktualisSzam));
                szamokLista.add(aktualisSzam);
                szamMennyiseg++;

                String textSzam = "";
                for (int i : szamokLista) {
                    textSzam += i + "   ";

                    szamok.setText(textSzam);
                }
                if (szamMennyiseg == 5) {
                    szamBerakas.cancel();
                    gomb.setText("Rendez");
                    maxMennyiseg = true;

                }
            });
        }
        }, 1500);

    }
    

    public void rendezTisztit() {
        String textSzam = "";
        gomb.setText("Sorsol");
        Collections.sort(szamokLista);

        for (int szam: szamokLista) {
            textSzam += szam + "    ";
        }
        szamok.setText(textSzam);

        szamokLista.clear();
        maxMennyiseg = false;
        szamMennyiseg= 0;
        kulonalloSzam.setText("");
    }
}