package nusextended.m426.game;

import nusextended.m426.model.ShapeType;
import nusextended.m426.model.UpgradeNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UpgradeNodeTest {

    @Test
    @DisplayName("single purchase node should respect prerequisites and cost")
    void singlePurchaseNodeShouldRespectPrerequisitesAndCost() {
        UpgradeNode root = new UpgradeNode(
            "root",
            "First upgrade in the chain.",
            new UpgradeCost(10.0, 1.2),
            false
        );

        UpgradeNode child = new UpgradeNode(
            "child",
            "Second upgrade in the chain.",
            new UpgradeCost(20.0, 1.2),
            false,
            ShapeType.TRIANGLE,
            root
        );

        assertFalse(child.canPurchase(ShapeType.TRIANGLE, 100.0));
        assertTrue(root.canPurchase(ShapeType.TRIANGLE, 100.0));

        root.recordPurchase();

        assertTrue(child.canPurchase(ShapeType.TRIANGLE, 100.0));
        assertEquals(12.0, root.getCurrentCost(), 0.0001);
        assertEquals(20.0, child.getCurrentCost(), 0.0001);
    }

    @Test
    @DisplayName("non-repeatable node should reject a second purchase")
    void nonRepeatableNodeShouldRejectASecondPurchase() {
        UpgradeNode node = new UpgradeNode(
            "one-time",
            "Can only be bought once.",
            new UpgradeCost(12.0, 1.3),
            false
        );

        node.recordPurchase();

        assertFalse(node.canPurchase(ShapeType.TRIANGLE, 100.0));
        assertThrows(IllegalStateException.class, node::recordPurchase);
        assertEquals(1, node.getPurchaseCount());
    }

    @Test
    @DisplayName("repeatable node should increase cost each purchase")
    void repeatableNodeShouldIncreaseCostEachPurchase() {
        UpgradeNode node = new UpgradeNode(
            "infinite",
            "Can be bought many times.",
            new UpgradeCost(10.0, 1.5),
            true
        );

        assertTrue(node.canPurchase(ShapeType.TRIANGLE, 100.0));
        assertEquals(10.0, node.getCurrentCost(), 0.0001);

        node.recordPurchase();
        assertEquals(15.0, node.getCurrentCost(), 0.0001);
        assertTrue(node.canPurchase(ShapeType.TRIANGLE, 100.0));
    }
}
