package nusextended.m426.game;

import nusextended.m426.model.UpgradeCost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    @DisplayName("getNode returns the matching node for an existing name")
    void getNodeReturnsExistingNode() {
        UpgradeNode node = tree.getNode("vertex-multiplier");
        assertNotNull(node);
        assertEquals("vertex-multiplier", node.getName());
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
    @DisplayName("getPurchasedNodes reports a single purchased node")
    void getPurchasedNodesReportsSinglePurchasedNode() {
        UpgradeNode first = newNode("first");
        UpgradeNode second = newNode("second");
        PrestigeTree customTree = new PrestigeTree(List.of(first, second));

        first.recordPurchase();

        List<UpgradeNode> purchased = customTree.getPurchasedNodes();
        assertEquals(1, purchased.size());
        assertSame(first, purchased.get(0));
    }

    @Test
    @DisplayName("getPurchasedNodes reports multiple purchased nodes")
    void getPurchasedNodesReportsMultiplePurchasedNodes() {
        UpgradeNode first = newNode("first");
        UpgradeNode second = newNode("second");
        UpgradeNode third = newNode("third");
        PrestigeTree customTree = new PrestigeTree(List.of(first, second, third));

        first.recordPurchase();
        third.recordPurchase();

        List<UpgradeNode> purchased = customTree.getPurchasedNodes();
        assertEquals(2, purchased.size());
        assertTrue(purchased.contains(first));
        assertTrue(purchased.contains(third));
        assertFalse(purchased.contains(second));
    }

    @Test
    @DisplayName("resolveReferences links previousNodes based on previousNodeNames")
    void resolveReferencesLinksPreviousNodes() {
        UpgradeNode root = newNode("root");
        UpgradeNode child = new UpgradeNode(
            "child",
            "Depends on root.",
            new UpgradeCost(20.0, 1.2),
            false,
            root
        );
        PrestigeTree customTree = new PrestigeTree(List.of(root, child));

        assertEquals(List.of("root"), child.getPreviousNodeNames());

        customTree.resolveReferences();

        List<UpgradeNode> resolved = child.getPreviousNodes();
        assertEquals(1, resolved.size());
        assertSame(customTree.getNode("root"), resolved.get(0));
    }

    @Test
    @DisplayName("resolveReferences skips names with no matching node")
    void resolveReferencesSkipsUnknownNames() {
        UpgradeNode child = new UpgradeNode(
            "child",
            "Points at a node that is not in the tree.",
            new UpgradeCost(20.0, 1.2),
            false,
            newNode("ghost")
        );
        PrestigeTree customTree = new PrestigeTree(List.of(child));

        customTree.resolveReferences();

        assertTrue(child.getPreviousNodes().isEmpty());
    }

    @Test
    @DisplayName("getNodes returns an unmodifiable list")
    void getNodesReturnsUnmodifiableList() {
        assertThrows(UnsupportedOperationException.class, () ->
            tree.getNodes().add(null));
    }

    private static UpgradeNode newNode(String name) {
        return new UpgradeNode(name, name + " description", new UpgradeCost(10.0, 1.2), false);
    }
}
