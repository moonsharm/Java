package com.example.demo.model;

public class CReportItem extends CCar{
    public CReportItem(
            Long id,
            String name,
            Double price,
            Long totalSoldCount
    )
    {
        super(id, name, price);
        setTotalSoldCount(totalSoldCount);
    }
    private Long totalSoldCount;

    public Long getTotalSoldCount() {
        return totalSoldCount;
    }

    public void setTotalSoldCount(Long totalSoldCount) {
        if (totalSoldCount>=0)
            this.totalSoldCount = totalSoldCount;
    }
}
