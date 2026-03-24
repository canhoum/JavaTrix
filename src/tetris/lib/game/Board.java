//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
//::                                                                         ::
//::     I N S T I T U T O    P O L I T E C N I C O   D E   T O M A R        ::
//::     Escola Superior de Tecnologia de Tomar                              ::
//::     e-mail: manso@ipt.pt                                                ::
//::     url   : http://orion.ipt.pt/~manso                                  ::
//::                                                                         ::
//::     This software was build with the purpose of investigate and         ::
//::     learning.                                                           ::
//::                                                                         ::
//::                                                               (c)2023   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package tetris.lib.game;

import java.awt.Graphics;
import tetris.lib.blocks.Block;
import tetris.lib.blocks.Empty;
import tetris.lib.blocks.BlockMatrix;
import tetris.lib.pieces.Piece;
import tetris.lib.utils.TetrisPiece;
import javax.swing.JLabel;

/**
 * Created on 01/05/2023, 08:48:11
 *
 * represents a tetris board with a floating piece
 *
 * @author IPT - computer
 * @version 1.0
 */
public class Board extends BlockMatrix {
    
    int score = 0;
    JLabel pontos;

    @Override
    public void paintComponent(Graphics g) {
        draw(g, 0, 0, getWidth(), getHeight());
    }

    @Override
    public void draw(Graphics gr, int px, int py, int width, int height) {
        super.draw(gr, px, py, width, height);
        int sizeX = width / getColmuns();
        int sizeY = height / getLines();       
        current.draw(gr, px + current.getPositionX() * sizeX,
                py + current.getPositionY() * sizeY,
                sizeX * current.getColmuns(),
                sizeY * current.getLines());
    }

    //Peça flutuante no tabuleiro
    protected Piece current;

    public Board() {
        this(20, 10);
    }

    /**
     * Constructor
     *
     * @param mat matriz dos blocos do tabuleiro
     * @param current peça flutuante
     */
    public Board(Block[][] mat, Piece current) {
        super(mat); // chama o construtor da superclasse
        this.current = current.getClone(); // clona a peça
        this.pontos= pontos;
        generateRandomPiece(); 
    }

    /**
     * Construtor de cópia
     *
     * @param b board
     */
    public Board(Board b) {
        this(b.matrix, b.current);
    }

    /**
     * Construtor com dimensões<br>
     * Cria um tabuleiro com uma matriz bidimensional de peças vazias
     *
     * @param lines numero de linhas
     * @param columns numero de colunas
     */
    public Board(int lines, int columns) {
        resize(lines, columns);
    }

    public void resize(int lines, int columns) {
        this.matrix = new Block[lines][columns];
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {

                matrix[y][x] = new Empty();
            }
        }
        generateRandomPiece();
    }

    //Gera uma peça flutuante aleatória
    public void generateRandomPiece() {
        this.current = TetrisPiece.generateRandom();
        //update the position x of piece to the midle of the board
        this.current.setPositionX(getColmuns() / 2 - current.getColmuns() / 2);
        //top of the board
        this.current.setPositionY(0);
        repaint();
    }

    //Transfere a peça flutuante para o tabuleiro
    public void freezePiece() {
        //Itera sobre os blocos
        for (int y = 0; y < current.getLines(); y++) {
            for (int x = 0; x < current.getColmuns(); x++) {
                //Se o bloco não estiver vazio
                if (!(current.getMatrix()[y][x] instanceof Empty)) {
                    //Congela o bloco
                    this.matrix[current.getPositionY() + y][current.getPositionX() + x] = current.getMatrix()[y][x];
                }
            }
        }
        repaint();
    }

    @Override
    public String toString() {
        StringBuilder txt = new StringBuilder();
        //Clona o tabuleiro
        Board tmp = new Board(this);
        //Congela a peça flutuante
        tmp.freezePiece();
        //Obtém o tabuleiro     
        for (int y = 0; y < getLines(); y++) {
            for (int x = 0; x < getColmuns(); x++) {
                txt.append(tmp.matrix[y][x]);
            }
            txt.append("\n");
        }
        return txt.toString();
    }

    /**
     * Verifica se a peça flutuante pode se mover
     *
     * @param dy deslocamento em y
     * @param dx deslocamento em x
     * @return
     */
    public boolean canMovePiece(int dy, int dx) {
        //iterate current piece block matrix
        for (int y = 0; y < current.getLines(); y++) {
            for (int x = 0; x < current.getColmuns(); x++) {
                //Se o bloco está vazio - continua para o próximo
                if (current.getMatrix()[y][x] instanceof Empty) {
                    continue;
                }
                //nova posição
                int px = current.getPositionX() + x + dx;
                int py = current.getPositionY() + y + dy;

                //está nos limites
                if (px >= getColmuns() || px < 0 || py >= getLines() || py < 0
                        //not Empty
                        || !(matrix[py][px] instanceof Empty)) {
                    return false; // Não mexe
                }
                
            }
        }
        return true; //Mexe
    }

    /**
     * A peça flutuante pode girar
     *
     * @return A peça consegue girar
     */
    public boolean canRotatePiece() {
        //Clona a peça atual
        Piece clone = current.getClone();
        //Roda o clone
        clone.rotate();
        //A peça está fora do tabuleiro
        if (clone.getPositionX() + clone.getColmuns() > getColmuns()) {
            return false;
        }
        //Verifica todos os blocos do clone
        for (int y = 0; y < clone.getLines(); y++) {
            for (int x = 0; x < clone.getColmuns(); x++) {
                //Se o bloco está vazio - continua para o próximo
                if (clone.getMatrix()[y][x] instanceof Empty) {
                    continue;
                }
                //está nos limites
                if (x < getColmuns() && x >= 0 && y < getLines() && y >= 0
                        //não está vazio
                        && !(matrix[y][x] instanceof Empty)) {
                    return false; // Rotação não disponível
                }
            }
        }
        return true; // Pode gira
    }

    //mover a peça para a esquerda
    
    public void moveLeft() {
        if (canMovePiece(0, -1)) {
            current.moveLeft();
            repaint();
        }
    }

    //mover a peça para a direita
    
    public void moveRight() {
        if (canMovePiece(0, 1)) {
            current.moveRight();
            repaint();
        }
    }

    public void setpontos(JLabel pontos)  {
        this.pontos = pontos;
   }
    
      //mover a peça para baixo
     
    public void moveDown() {
        if (canMovePiece(1, 0)) {
            current.moveDown();   
            score += 10;  
            if (pontos != null)
            pontos.setText("Pontos: " + score);
            repaint();
            
               
        }
        
    }
    
//    public void setpontos(JLabel pontos)  {
//        this.pontos = pontos;
//   }
    
    //Queda da peça
    public void fallDown() {
        while (canMovePiece(1, 0)) {
            current.moveDown();
            
        }
    }

    //rodar a peça
    public void rotate() {
        if (canRotatePiece()) {
            current.rotate();
            repaint();
        }
    }

    //encapsulamento
    public Piece getCurrent() {
        return current;
    }

    public void setCurrent(Piece current) {
        this.current = current;
    }
    
}
    


    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //private static final long serialVersionUID = 202305010848L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2023  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////


