import com.MK_20.game.Sprites.Bird;
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


class BirdTest {

    private static World mockWorld;
    private static Bird.TestBird testBird;
    private static Body mockBody;
    private static Fixture mockFixture;

    @BeforeAll
    static void setUp() {
        // Set up the mocks for the world, body, and fixture

        mockWorld = mock(World.class);
        mockBody = mock(Body.class);
        mockFixture = mock(Fixture.class);

        // Mock the behavior of the Body
        when(mockBody.getMass()).thenReturn(5.0f);
        when(mockFixture.getDensity()).thenReturn(0.5f);

        // Mock the fixture list
        Array<Fixture> fixtureList = new Array<>();
        fixtureList.add(mockFixture);
        when(mockBody.getFixtureList()).thenReturn(fixtureList);

        // Initialize the testBird with the mockWorld
        testBird = new Bird.TestBird(mockWorld);
        testBird.body = mockBody;
    }

    @Test
    void testInitialization(){
        Bird.TestBird myBird = new Bird.TestBird(mockWorld);
        assertNotNull(myBird);
    }

    @Test
    void testSpecialFeature() {
        // Simulate the use of special feature and check the result
        testBird.specialFeature();
        assertTrue(testBird.isSpecialFeatureUsed(), "Special feature should be marked as used");
    }

    @Test
    void testGetHealth() {
        // Test if health is correctly initialized and returned
        assertEquals(50, testBird.getHealth(), "Health should be 50 by default");
    }

    @Test
    void testDestroySelf() {
        // Test the destroy method
        testBird.destroySelf();
        assertTrue(testBird.isTotallyDestroyed(), "The bird should be totally destroyed after calling destroySelf");
    }

    @Test
    void testGetMass() {
        // Mock the body and set mass
        assertEquals(5.0f, testBird.getMass(), "Mass should be 5.0f");
    }

    @Test
    void testGetDensity() {
        // Set the body and fixture to return a density value
        assertEquals(0.5f, testBird.getDensity(), "Density should be 0.5f");
    }
}
