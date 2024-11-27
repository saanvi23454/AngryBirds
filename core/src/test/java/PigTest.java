import com.MK_20.game.Sprites.Bird;
import com.MK_20.game.Sprites.Pig;
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

class PigTest {

    private static World mockWorld;
    private static Pig.TestPig testPig;
    private static Body mockBody;
    private static Fixture mockFixture;

    @BeforeAll
    static void setUp() {
        // Set up the mocks for the world, body, and fixture

        mockWorld = mock(World.class);
        mockBody = mock(Body.class);
        mockFixture = mock(Fixture.class);

        // Mock the behavior of the Body
        when(mockBody.getMass()).thenReturn(10.0f);
        when(mockFixture.getDensity()).thenReturn(2f);

        // Mock the fixture list
        Array<Fixture> fixtureList = new Array<>();
        fixtureList.add(mockFixture);
        when(mockBody.getFixtureList()).thenReturn(fixtureList);

        // Initialize the testBird with the mockWorld
        testPig = new Pig.TestPig(mockWorld);
        testPig.body = mockBody;
    }

    @Test
    void testInitialization(){
        Pig.TestPig myPig = new Pig.TestPig(mockWorld);
        assertNotNull(myPig);
    }


    @Test
    void testGetHealth() {
        // Test if health is correctly initialized and returned
        assertEquals(100, testPig.getHealth(), "Health should be 100 by default");
    }

    @Test
    void testDestroySelf() {
        // Test the destroy method
        testPig.destroySelf();
        assertTrue(testPig.isTotallyDestroyed(), "The pig should be totally destroyed after calling destroySelf");
    }

    @Test
    void testGetMass() {
        // Mock the body and set mass
        assertEquals(10.0f, testPig.getMass(), "Mass should be 10.0f");
    }

    @Test
    void testGetDensity() {
        // Set the body and fixture to return a density value
        assertEquals(2f, testPig.getDensity(), "Density should be 2f");
    }
}
