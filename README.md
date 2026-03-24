# JavaTrix (Tetris em Java)

Projeto desenvolvido no âmbito da unidade curricular de Programação Orientada a Objetos. Consiste numa implementação do clássico jogo Tetris utilizando Java e interface gráfica.

## Descrição

O JavaTrix é um jogo de Tetris com interface gráfica interativa, onde o jogador controla peças que descem no tabuleiro com o objetivo de completar linhas horizontais.

O projeto aplica conceitos de Programação Orientada a Objetos, incluindo encapsulamento, herança e composição.

## Funcionalidades

- Interface gráfica com menu principal
- Controlo de peças com teclado
- Sistema de pontuação
- Níveis de jogo
- Verificação de colisões
- Limpeza de linhas completas
- Sons e efeitos
- Ecrã de Game Over

## Arquitetura

O projeto está organizado em várias bibliotecas:

### `tetris.gui`
- Interface gráfica (menus, regras, jogo)
- JFrames:
  - Menu principal
  - Regras
  - About Us
  - Jogo

### `tetris.libs.blocks`
- Definição dos blocos do jogo

### `tetris.lib.game`
- Lógica principal do jogo
- Classes principais:
  - `Board` → gestão do tabuleiro
  - `TetrisGame` → regras, pontuação, níveis, fim do jogo

### `tetris.libs.pieces`
- Definição das peças
- Classe base:
  - `Piece`
- Classes derivadas:
  - `PieceI`, `PieceJ`, `PieceL`, `PieceT`, `PieceO`, `PieceS`, `PieceZ`

### `tetris.resources`
- Imagens utilizadas

### `tetris.sounds`
- Sons do jogo

## Como Jogar

### Menu Principal

- **Play** → iniciar jogo  
- **?** → ver regras  
- **About Us** → informação dos autores  
- **Quit** → sair  

### Controlo

- ← → mover peça  
- ↓ descer  
- ↑ rodar  
- **Z** → queda rápida  

### Objetivo

- Completar linhas horizontais
- Evitar que as peças cheguem ao topo

## Regras

- As peças descem automaticamente
- Linhas completas são removidas
- O jogo termina quando o tabuleiro enche

## Limitações

- Velocidade não reinicia corretamente ao recomeçar
- Pop-up simples em vez de janela dedicada

## Melhorias Futuras

- Leaderboard (ranking de jogadores)
- Melhorar interface gráfica
- Melhorar arquitetura dos blocos
- Sistema de reinício mais robusto

## Tecnologias

- Java
- Swing (interface gráfica)

## Autores

- David Reis (24858)
- João Silva (24863)

## Referências

- https://tetris.wiki/Scoring
- Sons: 101soundboards / findsounds
- Aulas da unidade curricular
