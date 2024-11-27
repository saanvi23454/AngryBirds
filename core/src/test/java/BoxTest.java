import com.MK_20.game.Sprites.Box;
import com.MK_20.game.Tools.LevelCreator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BoxTest {

    private static World mockWorld;
    private static Box.TestBox testBox;
    private static Body mockBody;
    private static Fixture mockFixture;

    @BeforeAll
    static void setUp() {
        // Set up the mocks for the world, body, and fixture

        mockWorld = mock(World.class);
        mockBody = mock(Body.class);
        mockFixture = mock(Fixture.class);

        // Mock the behavior of the Body
        when(mockBody.getMass()).thenReturn(1.0f);
        when(mockFixture.getDensity()).thenReturn(0.1f);

        // Mock the fixture list
        Array<Fixture> fixtureList = new Array<>();
        fixtureList.add(mockFixture);
        when(mockBody.getFixtureList()).thenReturn(fixtureList);

        // Initialize the testBird with the mockWorld
        testBox = new Box.TestBox(mockWorld);
        testBox.body = mockBody;
    }
    @Test
    void testInitialization(){
        Box.TestBox myBox = new Box.TestBox(mockWorld);
        assertNotNull(myBox);
    }

    @Test
    void testGetHealth() {
        // Test if health is correctly initialized and returned
        assertEquals(100, testBox.getHealth(), "Health should be 100 by default");
    }

    @Test
    void testDestroySelf() {
        // Test the destroy method
        testBox.destroySelf();
        assertTrue(testBox.isTotallyDestroyed(), "The box should be totally destroyed after calling destroySelf");
    }

    @Test
    void testGetMass() {
        // Mock the body and set mass
        assertEquals(1.0f, testBox.getMass(), "Mass should be 1.0f");
    }

    @Test
    void testGetDensity() {
        // Set the body and fixture to return a density value
        assertEquals(0.1f, testBox.getDensity(), "Density should be 0.1f");
    }
}
