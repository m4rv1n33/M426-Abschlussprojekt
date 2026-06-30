package nusextended.m426.game;

import nusextended.m426.model.UpgradeNode;

public interface UpgradeStateListener {
    void onUpgradePurchased(UpgradeNode node);
}