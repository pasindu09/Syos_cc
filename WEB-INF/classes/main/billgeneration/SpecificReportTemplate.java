package main.billgeneration;

import main.billgeneration.bill.ProcessData;
import main.billgeneration.bill.ReportFormatter;
import main.billgeneration.ReportTemplate;

public class SpecificReportTemplate extends ReportTemplate {
    public SpecificReportTemplate(ProcessData processDataStep, ReportFormatter reportFormatter) {
        super(processDataStep, reportFormatter);
    }

    // Any additional methods or overrides can be added here
}
