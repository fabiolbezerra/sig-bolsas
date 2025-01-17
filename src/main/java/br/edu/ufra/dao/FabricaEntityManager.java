package br.edu.ufra.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author fabio
 */
public class FabricaEntityManager {

    private static EntityManagerFactory fabrica;

    private FabricaEntityManager() {
    }

    public static EntityManagerFactory obterFabrica() {
        if (fabrica == null) {
            return obterFabrica("SigBolsasPU");
        } else {
            return fabrica;
        }
    }

    public static EntityManagerFactory obterFabrica(String unidadePersistencia) {
        if (fabrica != null && fabrica.isOpen()) {
            fabrica.close();
        }
        fabrica = Persistence.createEntityManagerFactory(unidadePersistencia);
        return fabrica;
    }

}
