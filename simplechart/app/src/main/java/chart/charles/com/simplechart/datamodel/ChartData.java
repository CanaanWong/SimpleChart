package chart.charles.com.simplechart.datamodel;

/**
 * <b>Project:</b> simplechart<br>
 * <b>Create Date:</b> 2017/11/16<br>
 * <b>Author:</b> Charles <br>
 * <b>Description:</b> <br>
 */
public class ChartData {

    private String times;
    private String sales;


    public ChartData(String times, String sales) {
        this.times = times;
        this.sales = sales;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }
}
