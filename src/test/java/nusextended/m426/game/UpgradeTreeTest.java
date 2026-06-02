package nusextended.m426.game;

import nusextended.m426.model.ShapeType;
import nusextended.m426.model.UpgradeNode;
import nusextended.m426.model.UpgradeTree;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpgradeTreeTest {

    @Test
    @DisplayName("default tree should define the expected upgrade chain")
    void defaultTreeShouldDefineTheExpectedUpgradeChain() {
        UpgradeTree tree = UpgradeTree.createDefaultTree();

        assertEquals(3, tree.getNodes().size());
        assertEquals("vertex-growth", tree.getNodes().get(0).getName());
        assertEquals("shape-focus", tree.getNodes().get(1).getName());
        assertEquals("square-something", tree.getNodes().get(2).getName());
    }

    @Test
    @DisplayName("available nodes should unlock as prerequisites are purchased")
    void availableNodesShouldUnlockAsPrerequisitesArePurchased() {
        UpgradeTree tree = UpgradeTree.createDefaultTree();

        List<UpgradeNode> initialNodes = tree.getAvailableNodes(ShapeType.TRIANGLE, 100.0);
        assertEquals(1, initialNodes.size());
        assertEquals("vertex-growth", initialNodes.get(0).getName());

        UpgradeNode vertexGrowth = tree.getNode("vertex-growth");
        vertexGrowth.recordPurchase();

        List<UpgradeNode> unlockedNodes = tree.getAvailableNodes(ShapeType.TRIANGLE, 100.0);
        assertTrue(unlockedNodes.stream().anyMatch(node -> node.getName().equals("shape-focus")));
    }

    @Test
    @DisplayName("reset should clear purchase progress for every node")
    void resetShouldClearPurchaseProgressForEveryNode() {
        UpgradeTree tree = UpgradeTree.createDefaultTree();
        tree.getNode("vertex-growth").recordPurchase();
        tree.getNode("shape-focus").recordPurchase();

        tree.reset();

        assertEquals(0, tree.getNode("vertex-growth").getPurchaseCount());
        assertEquals(0, tree.getNode("shape-focus").getPurchaseCount());
        assertEquals(0, tree.getPurchasedNodes().size());
    }
}
