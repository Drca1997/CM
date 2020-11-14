package com.example.guerradasestrelas;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class OutraHabilidade extends Habilidade {

    public OutraHabilidade(String nome) {
        super(nome);
    }

    @Override
    public void Execute(int turno, Jogador [] jogadores) {
        if (nome.equals("Ligacao")){
            ligacaoSkill(jogadores[turno].getCampo()[0]);
        }
    }

    public void ligacaoSkill(CardSlot[] fila){
        List<Carta> powerRangers = getPowerRangers(fila);
        int mod = 0;
        if (powerRangers.size() == 2){
            mod = 2;
        }
        else if(powerRangers.size() == 3){
            mod = 6;
        }
        for (Carta powerRanger : powerRangers){
            powerRanger.AddModifier(mod);
        }
    }

    public List<Carta> getPowerRangers(CardSlot [] fila){
        List<Carta> powerRangers = new ArrayList<Carta>();
        for (CardSlot slot : fila){
            if (slot.getCarta() != null){
                if (slot.getCarta().getHabilidade() != null){
                    if (slot.getCarta().getHabilidade().getNome().equals("Ligacao")){
                        powerRangers.add(slot.getCarta());
                    }
                }
            }
        }
        return powerRangers;
    }
}
