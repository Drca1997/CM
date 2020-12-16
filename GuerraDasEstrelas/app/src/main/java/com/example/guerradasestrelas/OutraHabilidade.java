package com.example.guerradasestrelas;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class OutraHabilidade extends Habilidade {

    public OutraHabilidade(String nome) {
        super(nome);
    }

    @Override
    public void Execute(int turno, Jogador [] jogadores, Carta carta) {
        if (nome.equals("Ligacao")){
            ligacaoSkill(jogadores[turno].getCampo()[0], carta);
        }
        else if (nome.equals("Kamikaze")){
            jogadores[Utils.getOutraFila(turno)].setGotKamikazed(true);
        }
    }

    public void ligacaoSkill(CardSlot[] fila, Carta carta){
        List<Carta> powerRangers = getPowerRangers(fila);
        for (Carta powerRanger : powerRangers){
            int mod = 0;
            int mod2 = 0;
            if (powerRangers.size() > 1){
                mod = (int) Math.pow(powerRanger.getPoderDefault(), (powerRangers.size() - 1));
                mod2 = (int) Math.pow(powerRanger.getPoderDefault(), (powerRangers.size()));
                if (powerRanger != carta){
                    powerRanger.AddModifier(mod);
                }
                else{
                    powerRanger.AddModifier(mod2 - powerRanger.getPoderDefault());
                }
            }



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
