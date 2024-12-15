package testCartes;

import personnages.*;
import carte.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BatimentTest {

    @Test
    void testMessageActionCarte() {
        Batiment m = new Batiment("maison",5, quartier.COMMERCE);
        assertEquals("La carte maison a été construite.",m.messageActionCarte());
    }

    @Test
    void testGetType() {
        Batiment m = new Batiment("maison",5, quartier.COMMERCE);
        assertEquals(quartier.COMMERCE,m.getType());
    }

    @Test
    void testType() {
        Batiment m = new Batiment("maison",5, quartier.COMMERCE);
        assertNotEquals(new Assassin().getType(),m.getType());
        assertEquals(new Marchand().getType(),m.getType());
    }
}