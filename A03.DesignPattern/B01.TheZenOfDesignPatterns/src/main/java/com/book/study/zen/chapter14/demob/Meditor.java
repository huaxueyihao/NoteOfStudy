package com.book.study.zen.chapter14.demob;

public class Meditor extends AbstractMediator {

    // 中介者最重要的方法
    @Override
    public void execute(String str, Object... objects) {
        if (str.equals("purchase.buy")) {
            // 采购电脑
            this.buyComputer((Integer) objects[0]);
        } else if (str.equals("sale.sell")) {
            // 销售电脑
            this.sellComputer((Integer) objects[0]);
        } else if (str.equals("sale.offsell")) {
            this.offSell();
        } else if (str.equals("stock.clear")) {
            this.clearStock();
        }
    }

    private void clearStock() {
        // 要求清仓销售
        super.sale.offSale();
        // 要求采购人员不要采购
        super.purchase.refuseBuyIBM();
    }

    private void offSell() {
        System.out.println("折价销售IBM电脑" + stock.getStockNumber());
    }

    private void sellComputer(Integer number) {
        if (super.stock.getStockNumber() < number) {
            super.purchase.buyIBMcomputer(number);
        }
        super.stock.decrease(number);
    }

    private void buyComputer(int number) {
        int saleStatus = super.sale.getSaleStatus();
        if (saleStatus > 80) {
            System.out.println("采购IBM电脑:" + number + "台");
            super.stock.increase(number);
        } else {
            int buyNumber = number / 2;
            System.out.println("采购IBM电脑：" + buyNumber + "台");
        }

    }


}
