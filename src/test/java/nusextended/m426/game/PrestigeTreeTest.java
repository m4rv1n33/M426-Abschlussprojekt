package nusextended.m426.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrestigeTreeTest {

    private PrestigeTree tree;

    @BeforeEach
    void setUp() {
        tree = PrestigeTree.createDefaultTree();
    }

    @Test
    @DisplayName("default tree contains the vertex-multiplier node")
    void defaultTreeContainsVertexMultiplier() {
        UpgradeNode node = tree.getNode("vertex-multiplier");
        assertNotNull(node);
        assertTrue(node.isInfinitelyPurchaseable());
        assertEquals(100.0, node.getCurrentCost(), 0.0001);
    }

    @Test
    @DisplayName("getNode returns null for unknown names")
    void getNodeReturnsNullForUnknownNames() {
        assertNull(tree.getNode("non-existent"));
    }

    @Test
    @DisplayName("no nodes are purchased initially")
    void noNodesArePurchasedInitially() {
        assertTrue(tree.getPurchasedNodes().isEmpty());
    }

    @Test
    @DisplayName("getNodes returns an unmodifiable list")
    void getNodesReturnsUnmodifiableList() {
        assertThrows(UnsupportedOperationException.class, () ->
            tree.getNodes().add(null));
    }
}
