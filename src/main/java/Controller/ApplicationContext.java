package Controller;

import Logic.DealOrNoDeal;

public class ApplicationContext {
    private static ApplicationContext instance = new ApplicationContext();

    public static ApplicationContext getInstance() {
        return instance;
    }

    DealOrNoDeal dealOrNoDeal;

    private ApplicationContext() {
    }

    public DealOrNoDeal getDealOrNoDeal() {
        return dealOrNoDeal;
    }

    public void setDealOrNoDeal(DealOrNoDeal dealOrNoDeal) {
        this.dealOrNoDeal = dealOrNoDeal;
    }
}
