/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tetris.lib.game;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import tetris.app.ClockTask;
import tetris.lib.game.Board;
import tetris.lib.blocks.Empty;

/**
 *
 * @author jpsil
 */
public class TetrisGame extends Board {

    Timer timer;
    int score;
    JLabel pontos;
    boolean velocidade;
    boolean velocidade2;
       
    public TetrisGame() {
        super();
        timer = new Timer();
        score = 0;
        velocidade = false;
        velocidade2 = false;
        startGame(400); // Começa o jogo com um delay of 400 milisegundos
     }
    
    public void startGame(int delay){
    //  Inicia o jogo com um delay especifico entre os movimentos
        timer.purge();
        timer = new Timer();
        timer.schedule(new MoveGame(), 0, delay);
    }
    
    public void stopGame() {
    // Interrompe o jogo cancelando o timer
        timer.cancel();
        //.........

    }
    
    public void restartGame() {
    // Reinicia o jogo redefinindo a matriz, gerando uma nova peça e iniciando o jogo novamente
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                matrix[y][x] = new Empty();
            }
            
        }    
            generateRandomPiece();
            score = 0;
            startGame(400);
        
    }
    public void LevelsGame() {
    //aumenta a velocidade da queda do bloco quando a pontuação é igual ou acima de 500
      if (score >= 500 && !velocidade ) {
            timer.purge();
         timer.schedule(new MoveGame(), 0, 300);
         velocidade = true;
      }
      else if (score >= 1000 && !velocidade2 ) {
      //aumenta a velocidade da queda do bloco quando a pontuação é igual ou acima de 1000
            timer.purge();
          timer.schedule(new MoveGame(), 0, 200);
         velocidade2 = true;
      }
    }
    

    public boolean isGameOVer() {
    // Verifica se o jogo acabou determinando se a peça atual está no topo e não se pode mais mover para baixo
        if (current.getPositionY() == 0 && !canMovePiece(1, 0)) {
            GameOverSound();
        int choice = JOptionPane.showConfirmDialog(null, "Game Over! Do you want to restart?", "Game Over",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            restartGame();
        } else if (choice == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
        

        return true;
    }

    return false;
}
    
    public void GameOverSound() {
        try {
            // Carrega o fluxo de entrada de áudio do arquivo de som
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("tetris/sounds/game-over.wav");
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(inputStream);

            // Obtém um clip para reproduzir o som
            Clip gameOverClip = AudioSystem.getClip();

            // Abre o fluxo de entrada de áudio com o clip
            gameOverClip.open(audioInput);

            // Inicia a reprodução do som
            gameOverClip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    private class MoveGame extends ClockTask {
    // Implementação personalizada do TimerTask para avançar o jogo
        @Override
        public void run() {
            requestFocus();
            if (isGameOVer()) {
                stopGame();
            } else if (canMovePiece(1, 0)) {
                moveDown();
               // this.pontos=pontos;
            } else {
                freezePiece();
                score += 25; // 
                pontos.setText("Pontos: " + score);
                generateRandomPiece();
               
            }
            
            LevelsGame();
        }

    }

    public boolean isLineFull(int line) {
    // Verifica se a linha específica esta completa
        for (int x = 0; x < matrix[line].length; x++) {
            if (matrix[line][x] instanceof Empty) {
                return false;
            }
        }
        return true;

    }

    public void deleteLine(int line) {
        //Desce todas as colunas acima da linha
        for (int y = line; y > 0; y--) //copy line y
        {
            for (int x = 0; x < matrix[y].length; x++) {
                matrix[y][x] = matrix[y - 1][x];
            }
        }
        //coloca uma linha vazia na primeira linha
        for (int x = 0; x < matrix[0].length; x++) {
            matrix[0][x] = new Empty();
        }
        
        score += 100;
        pontos.setText("Pontos: " + score);
    }

    public void deleteFullLines() {
        //itera as linhas do fundo
        for (int y = matrix.length - 1; y > 0; y--) {
            //verifica se as linhas estão completas
            while (isLineFull(y)) {
                //apaga a linha
                deleteLine(y);
                ClearLineSound();
            }
        }

    }
    public void ClearLineSound() {
        try {
            // Carrega o fluxo de entrada de áudio do arquivo de som
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("tetris/sounds/clear.wav");
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(inputStream);

            // Obtém um clip para reproduzir o som
            Clip gameOverClip = AudioSystem.getClip();

            // Abre o fluxo de entrada de áudio com o clip
            gameOverClip.open(audioInput);

            // Inicia a reprodução do som
            gameOverClip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void freezePiece() {
        //call freeze of board
        super.freezePiece();
        //delete lines
        deleteFullLines();
    }

    
    public int getScore() {
        return score;
    }
    
    
    public void setpontos(JLabel pontos)  {
        this.pontos = pontos;
    }
}
