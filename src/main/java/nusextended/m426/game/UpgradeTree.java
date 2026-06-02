package nusextended.m426.game;

import nusextended.m426.model.ShapeType;
import nusextended.m426.model.UpgradeCost;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpgradeTree {
    private List<UpgradeNode> nodes;

    public UpgradeTree() {
        this.nodes = new ArrayList<>();
    }

    public UpgradeTree(List<UpgradeNode> nodes) {
        this.nodes = new ArrayList<>(nodes);
    }

    public static UpgradeTree createDefaultTree() {
        UpgradeNode vertexGrowth = new UpgradeNode(
            "vertex-growth",
            "Increase the active shape by one vertex.",
            UpgradeCost.shapeUpgrade(),
            true
        );

        UpgradeNode shapeFocus = new UpgradeNode(
            "shape-focus",
            "Improve production once triangle-era shapes are reached.",
            new UpgradeCost(35.0, 1.25),
            false,
            ShapeType.TRIANGLE,
            vertexGrowth
        );

        UpgradeNode squareAutomation = new UpgradeNode(
            "square-something",
            ":thumbs_up:",
            new UpgradeCost(120.0, 1.35),
            false,
            ShapeType.SQUARE,
            shapeFocus
        );

        return new UpgradeTree(List.of(vertexGrowth, shapeFocus, squareAutomation));
    }

    public List<UpgradeNode> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    public UpgradeNode getNode(String name) {
        for (UpgradeNode node : nodes) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }

    public List<UpgradeNode> getAvailableNodes(ShapeType currentShapeType, double currency) {
        List<UpgradeNode> availableNodes = new ArrayList<>();
        for (UpgradeNode node : nodes) {
            if (node.canPurchase(currentShapeType, currency)) {
                availableNodes.add(node);
            }
        }
        return Collections.unmodifiableList(availableNodes);
    }

    public List<UpgradeNode> getPurchasedNodes() {
        List<UpgradeNode> purchasedNodes = new ArrayList<>();
        for (UpgradeNode node : nodes) {
            if (node.isPurchased()) {
                purchasedNodes.add(node);
            }
        }
        return Collections.unmodifiableList(purchasedNodes);
    }

    public void resolveReferences() {
        Map<String, UpgradeNode> nodeMap = new HashMap<>();
        for (UpgradeNode node : nodes) {
            nodeMap.put(node.getName(), node);
        }
        for (UpgradeNode node : nodes) {
            node.resolveReferences(nodeMap);
        }
    }

    public void reset() {
        for (UpgradeNode node : nodes) {
            node.resetProgress();
        }
    }

    public void syncShapeGrowthLevel(int shapeLevel) {
        UpgradeNode shapeGrowth = getNode("vertex-growth");
        if (shapeGrowth != null) {
            shapeGrowth.syncPurchaseCount(shapeLevel);
        }
    }
}