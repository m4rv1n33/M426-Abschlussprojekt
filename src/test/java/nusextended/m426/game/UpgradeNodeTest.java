package nusextended.m426.game;

import nusextended.m426.model.ShapeType;
import nusextended.m426.model.UpgradeCost;
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

    @Test
    @DisplayName("canPurchase rejects a node whose required shape type does not match")
    void canPurchaseRejectsWrongRequiredShapeType() {
        UpgradeNode node = new UpgradeNode(
            "square-only",
            "Needs a square to be active.",
            new UpgradeCost(10.0, 1.2),
            false,
            ShapeType.SQUARE
        );

        assertFalse(node.canPurchase(ShapeType.TRIANGLE, 1000.0));
        assertTrue(node.canPurchase(ShapeType.SQUARE, 1000.0));
    }

    @Test
    @DisplayName("canPurchase rejects a node with unmet prerequisites")
    void canPurchaseRejectsUnmetPrerequisites() {
        UpgradeNode root = new UpgradeNode(
            "root",
            "Prerequisite upgrade.",
            new UpgradeCost(10.0, 1.2),
            false
        );

        UpgradeNode child = new UpgradeNode(
            "child",
            "Locked behind root.",
            new UpgradeCost(20.0, 1.2),
            false,
            root
        );

        assertFalse(child.hasUnlockedPrerequisites());
        assertFalse(child.canPurchase(ShapeType.TRIANGLE, 1000.0));

        root.recordPurchase();

        assertTrue(child.hasUnlockedPrerequisites());
        assertTrue(child.canPurchase(ShapeType.TRIANGLE, 1000.0));
    }

    @Test
    @DisplayName("canPurchase rejects when currency is below the current cost")
    void canPurchaseRejectsInsufficientCurrency() {
        UpgradeNode node = new UpgradeNode(
            "pricey",
            "Costs more than we have.",
            new UpgradeCost(50.0, 1.2),
            false
        );

        assertFalse(node.canPurchase(ShapeType.TRIANGLE, 49.99));
        assertTrue(node.canPurchase(ShapeType.TRIANGLE, 50.0));
    }

    @Test
    @DisplayName("canPurchase rejects an already-purchased non-repeatable node")
    void canPurchaseRejectsAlreadyPurchasedNonRepeatableNode() {
        UpgradeNode node = new UpgradeNode(
            "one-time",
            "Bought once, never again.",
            new UpgradeCost(10.0, 1.2),
            false
        );

        assertTrue(node.canPurchase(ShapeType.TRIANGLE, 1000.0));

        node.recordPurchase();

        assertFalse(node.canPurchase(ShapeType.TRIANGLE, 1000.0));
    }

    @Test
    @DisplayName("resetProgress clears the purchase count and re-enables a non-repeatable node")
    void resetProgressClearsCountAndReEnablesPurchase() {
        UpgradeNode node = new UpgradeNode(
            "resettable",
            "Should be buyable again after a reset.",
            new UpgradeCost(10.0, 1.2),
            false
        );

        node.recordPurchase();
        assertTrue(node.isPurchased());
        assertFalse(node.canPurchase(ShapeType.TRIANGLE, 1000.0));

        node.resetProgress();

        assertEquals(0, node.getPurchaseCount());
        assertFalse(node.isPurchased());
        assertTrue(node.canPurchase(ShapeType.TRIANGLE, 1000.0));
    }

    @Test
    @DisplayName("getCurrentCost scales with purchase count for an infinitely purchaseable node")
    void getCurrentCostScalesAcrossPurchaseCounts() {
        UpgradeNode node = new UpgradeNode(
            "infinite",
            "Cost doubles every purchase.",
            new UpgradeCost(10.0, 2.0),
            true
        );

        assertEquals(10.0, node.getCurrentCost(), 0.0001);

        node.recordPurchase();
        assertEquals(20.0, node.getCurrentCost(), 0.0001);

        node.recordPurchase();
        assertEquals(40.0, node.getCurrentCost(), 0.0001);

        node.recordPurchase();
        assertEquals(80.0, node.getCurrentCost(), 0.0001);

        assertEquals(3, node.getPurchaseCount());
    }
}
