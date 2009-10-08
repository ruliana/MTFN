package mtfn;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

public class MetaphonePtBrTest {

    @Test
    public void testBasic() throws Exception {
        assertMetaphoneEquals("2N", "Ronie");
        assertMetaphoneEquals("2ND", "Ronaldo");
        assertMetaphoneEquals("LSL", "Lucélia");
        assertMetaphoneEquals("MG", "Miguel");
        assertMetaphoneEquals("ALXNDR", "Alexandre");
        assertMetaphoneEquals("XV2", "Xavier");
        assertMetaphoneEquals("SZ2", "César");
        assertMetaphoneEquals("SZ2", "CÍSAR");
        assertMetaphoneEquals("SSR", "Cícero");
        assertMetaphoneEquals("KST1S", "Castilhos");
        assertMetaphoneEquals("KST1", "Casttilho");
        assertMetaphoneEquals("KST1", "CASTTILHO");
        assertMetaphoneEquals("KX", "Koshi");
        assertMetaphoneEquals("IKMM", "HICKIMAN");
        assertMetaphoneEquals("IKMM", "HICKMANN");
        assertMetaphoneEquals("IKMM", "HIECKMANN");
        assertMetaphoneEquals("2KRD", "RICCARDO");
        assertMetaphoneEquals("BK2", "Belchior");
        assertMetaphoneEquals("FB2", "Philber");
        assertMetaphoneEquals("ABT", "Abbate");
        
    }

    private void assertMetaphoneEquals(String expected, String original) {
        assertEquals("Metaphone", expected, new MetaphonePtBr(original).toString());
    }
}
