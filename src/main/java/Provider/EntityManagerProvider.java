package Provider;

/*-
 * #%L
 * Áll az alku!
 * %%
 * Copyright (C) 2018 University of Debrecen, Faculty of Informatics
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
     http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 * #L%
 */

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Perzisztenciaegységet kezelő objektum eléréséért felelős osztály
 */
public class EntityManagerProvider {

    /**
     * Perzisztenciaegységet kezelő objektum létrehozásáért felelős objektum
     */
    private static EntityManagerFactory emf;

    /**
     * Perzisztenciaegységet kezelő objektum
     */
    private static EntityManager em;

    static{
        emf = Persistence.createEntityManagerFactory("DealOrNoDealPersistenceUnit");
        em = emf.createEntityManager();
    }

    /**
     * Visszaadja a perzisztenciaegységet kezelő objektumot
     * @return Visszaadja a perzisztenciaegységet kezelő objektumot
     */
    public static EntityManager provideEntityManager(){
        return em;
    }

    /**
     * Lezárja a kapcsolatot a perzisztenciaegységgel
     */
    public static void closeConnection(){
        em.close();
        emf.close();
    }

}
