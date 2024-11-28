import com.MK_20.game.Sprites.Slingshot;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SlingTest {

    private Slingshot slingshot;

    @BeforeEach
    void setUp() {
        slingshot = Slingshot.getInstance();
    }

    @Test
    void testSingletonInstance() {
        // verify the singleton behavior
        Slingshot anotherSlingshot = Slingshot.getInstance(150, 250);
        assertSame(slingshot, anotherSlingshot, "Slingshot should be a singleton instance");
    }

    @Test
    void testStartDrag() {
        // simulate starting a drag
        slingshot.startDrag(); // Drag position in pixels
        assertTrue(slingshot.dragging, "Slingshot should be in dragging state");
        assertEquals(new Vector2(50f, 50f), slingshot.getDragPosition(), "Drag position should be updated correctly");
    }


    @Test
    void testRelease() {
        // simulate releasing the slingshot
        slingshot.startDrag();
        slingshot.release();
        assertFalse(slingshot.dragging, "Slingshot should not be in dragging state after release");
        assertEquals(slingshot.top, slingshot.getDragPosition(), "Drag position should reset to the top after release");
    }

    @Test
    void testDispose() {
        assertDoesNotThrow(() -> slingshot.dispose(), "Dispose should not throw any exceptions");
    }
}
