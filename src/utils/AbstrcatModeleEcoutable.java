package utils;

import java.util.ArrayList;
import java.util.List;

public class AbstrcatModeleEcoutable implements ModeleEcoutable {
    public List<EcouteurModele> ecouteurs;

    public AbstrcatModeleEcoutable() {
        ecouteurs = new ArrayList<>();
    }

    @Override
    public void ajoutEcouteur(EcouteurModele e) {
        ecouteurs.add(e);
    }

    @Override
    public void retraitEcouteur(EcouteurModele e) {
        ecouteurs.remove(e);
    }

    protected void fireChangement(){
        for (EcouteurModele ecouteur : ecouteurs){
            ecouteur.modeleMisAJour(this);
        }
    }
}
