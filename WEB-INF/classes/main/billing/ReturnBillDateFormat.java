package main.billing;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReturnBillDateFormat {
  private Date date;

  public ReturnBillDateFormat(Date date) {
      this.date = date;
  }

  public String returnDateFormat() {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy hh:mm a");
      String formattedDate = sdf.format(date);
      return formattedDate;
  }
}
