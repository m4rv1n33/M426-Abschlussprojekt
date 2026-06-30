package nusextended.m426.game;

import nusextended.m426.model.UpgradeCost;
import nusextended.m426.model.UpgradeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrestigeTree {
    private List<UpgradeNode> nodes;

    public PrestigeTree() {
        this.nodes = new ArrayList<>();
    }

    public PrestigeTree(List<UpgradeNode> nodes) {
        this.nodes = new ArrayList<>(nodes);
    }

    public static PrestigeTree createDefaultTree() {
        UpgradeNode vertexMultiplier = new UpgradeNode(
            "vertex-multiplier",
            "Multiplies the vertex count of the active shape.",
            UpgradeCost.prestigeUpgrade(),
            true
        );
        UpgradeNode dummy1 = new UpgradeNode(
                "dummy-1",
                ":x:",
                UpgradeCost.prestigeUpgrade(),
                false
        );
        UpgradeNode dummy2 = new UpgradeNode(
                "dummy-2",
                ":x:",
                UpgradeCost.prestigeUpgrade(),
                false,
                vertexMultiplier, dummy1
        );
        UpgradeNode dummy3 = new UpgradeNode(
                "dummy-3",
                ":x:",
                UpgradeCost.prestigeUpgrade(),
                false,
                vertexMultiplier, dummy1
        );
        UpgradeNode dummy4 = new UpgradeNode(
                "dummy-4",
                ":x:",
                UpgradeCost.prestigeUpgrade(),
                false,
                vertexMultiplier, dummy1
        );
        return new PrestigeTree(List.of(vertexMultiplier, dummy1, dummy2, dummy3, dummy4));
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

    public List<UpgradeNode> getPurchasedNodes() {
        List<UpgradeNode> purchased = new ArrayList<>();
        for (UpgradeNode node : nodes) {
            if (node.isPurchased()) {
                purchased.add(node);
            }
        }
        return Collections.unmodifiableList(purchased);
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
}