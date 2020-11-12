package com.example.guerradasestrelas;

public class AddModififier extends Habilidade {

    int modificador;
    int [] filasAfetadas;

    public AddModififier(String nome, int modificador, int fila1, int fila2) {
        super(nome);
        this.modificador = modificador;
        this.filasAfetadas = new int [2];
        this.filasAfetadas[0] = fila1;
        this.filasAfetadas[1] = fila2;
}

    public CardSlot[][] getfilasAfetadas(int turno, CardSlot[][]campo1, CardSlot[][]campo2){
        CardSlot [][] filas = new CardSlot[2][5];
        if (filasAfetadas[0] < 3 && filasAfetadas[0] >= 1){
            if (turno == 0){
                filas[0] = campo1[filasAfetadas[0] - 1];
            }
            else if (turno == 1){
                filas[0] = campo2[filasAfetadas[0] - 1];
            }

        }
        else if(filasAfetadas[0] >= 3){
            if (turno == 0){
                filas[0] = campo2[filasAfetadas[0] - 3];
            }
            else if(turno == 1){
                filas[0] = campo1[filasAfetadas[0] - 3];
            }

        }
        if (filasAfetadas[1] < 3 && filasAfetadas[1] >= 1){
            if (turno == 0){
                filas[1] = campo1[filasAfetadas[1] - 1];
            }
            else if(turno == 1){
                filas[1] = campo2[filasAfetadas[1] - 1];
            }
        }
        else if(filasAfetadas[1] >= 3){
            if (turno == 0){
                filas[1] = campo2[filasAfetadas[1] - 3];
            }
            else if(turno == 1){
                filas[1] = campo1[filasAfetadas[1] - 3];
            }
        }
        else if(filasAfetadas[1] == 0){
            filas[1] = null;
        }
        return filas;
    }

    @Override
    public void Execute(int turno, CardSlot[][] campo1, CardSlot[][] campo2) {
        CardSlot [][] filasAfetadas = getfilasAfetadas(turno, campo1, campo2);
        for(int i=0; i < filasAfetadas.length; i++){
            for (int j=0; j < Singleton.TAMANHO_FILAS; j++){
                if (filasAfetadas[i] != null){
                    if (filasAfetadas[i][j].getCarta() != null){
                        if (filasAfetadas[i][j].getCarta().getHabilidade() == null ||
                                !filasAfetadas[i][j].getCarta().getHabilidade().getNome().equals("Imune")){ //Arnold Imune a modificadores
                            filasAfetadas[i][j].getCarta().AddModifier(modificador);
                        }

                    }
                }
            }
        }
    }
}
