package main.billgeneration;

import main.billgeneration.bill.ProcessData;
import main.billgeneration.bill.ReportFormatter;

public abstract class ReportTemplate implements Template{
    private final ProcessData processDataStep;
    private final ReportFormatter reportFormatter;

    public ReportTemplate(ProcessData processDataStep, ReportFormatter reportFormatter) {
        this.processDataStep = processDataStep;
        this.reportFormatter = reportFormatter;
    }
    @Override
    public void generateReport() {
        processDataStep.processData();
        reportFormatter.format();
    }
}