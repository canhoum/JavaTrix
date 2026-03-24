/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tetris.lib.game;

import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author jpsil
 */
public class TetrisSounds {
    public static void main(String[] args) {
        
    }
       
    public void MenuSound() {
        try {
                // Carregue o fluxo de entrada de áudio do arquivo de som
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream("tetris/sounds/title.wav");
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(inputStream);

                // Obtem um clip para reproduzir o som
                Clip clearClip = AudioSystem.getClip();

                // Abre o fluxo de entrada de áudio com o clip
                clearClip.open(audioInput);

               
                // Define o clip para repetir continuamente
                clearClip.loop(Clip.LOOP_CONTINUOUSLY);

                
                // Inicia a reprodução do som
                clearClip.start();
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
    }
}





    


